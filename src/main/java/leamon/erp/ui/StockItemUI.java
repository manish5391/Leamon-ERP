package leamon.erp.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.StockItem;
import leamon.erp.ui.event.FocusEventHandler;
import leamon.erp.ui.event.FocusListenerHandler;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

/**
 * @author Leamonindia
 * @date 23 MAY, 2015
 *
 */
@Getter
public class StockItemUI extends JInternalFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textFinish;
	private JTextField txtSize;
	private JTextField txtName;
	private JTextField txtProductCode;
	private JTable tableStockItems;

	static final Logger LOGGER = Logger.getLogger(StockItemUI.class);
	private JTextField textShape;
	private JTextField textSaleUnit;
	private JTextField textDescription;
	
	private JButton btnSave;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnClear;
	
	private JLabel lblID;
	private JLabel labMsg;
	private JScrollPane scrollPaneImage;
	private JLabel lblStockItemImage;
	private JButton btnUploadImage;
	private JTextField textImage;
	private JTextField textUnit;
	private JLabel label_2;
	private JLabel label_4;
	private JLabel label_5;
	/**
	 * Create the frame.
	 */
	public StockItemUI() {
		//super();
		setTitle("Stock Manager");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setBounds(3, 30, 1000, 524);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(575, 33, 413, 258);
	//	scrollPane.getViewport().setBackground(Color.WHITE);
		tableStockItems = new JTable(){
        	public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){  
        	    Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);  

        	    if(rowIndex % 2 == 0) {  
        	    	Color color = new Color(255, 251, 224);
        	       componenet.setBackground(color);  
        	    } else {
        	       componenet.setBackground(Color.white);
        	    }
        	    return componenet;
        	} 
        };
		tableStockItems.setBackground(Color.WHITE);
        UIManager.put("Table.focusCellHighlightBorder", 
                new BorderUIResource.LineBorderUIResource(Color.BLACK)); 
		tableStockItems.addKeyListener(new CustomKeyListener());
		tableStockItems.setRowHeight(tableStockItems.getRowHeight()+10);
		tableStockItems.setCellSelectionEnabled(true);
		tableStockItems.setFont(new Font("Courier New", Font.PLAIN, 16));
		scrollPane.setViewportView(tableStockItems);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 33, 536, 451);
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 0, 0), null, null, null), "Stock Item Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(205, 92, 92)));
		panel.setBackground(Color.WHITE);
		
		txtName = new JTextField();
		txtName.setBounds(194, 27, 233, 23);
		txtName.setName(LeamonERPConstants.TEXTFIELD_NAME_STOCK_NAME);
		txtName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtName.setColumns(10);
		txtName.addFocusListener(new FocusEventHandler(tableStockItems));
		txtName.addKeyListener(new CustomKeyListener());
		
		txtProductCode = new JTextField();
		txtProductCode.setBounds(191, 156, 233, 26);
		txtProductCode.addKeyListener(new CustomKeyListener());
		txtProductCode.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtProductCode.setName(LeamonERPConstants.TEXTFIELD_NAME_PRODUCT_CODE);
		txtProductCode.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtProductCode.setColumns(10);
		txtProductCode.addFocusListener(new FocusEventHandler(tableStockItems));
		
		txtSize = new JTextField();
		txtSize.setBounds(191, 59, 235, 26);
		txtSize.addKeyListener(new CustomKeyListener());
		txtSize.setName(LeamonERPConstants.TEXTFIELD_NAME_SIZE);
		txtSize.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtSize.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtSize.setColumns(10);
		txtSize.addFocusListener(new FocusEventHandler(tableStockItems));
		
		textUnit = new JTextField();
		textUnit.setBounds(194, 145, 232, 26);
		textUnit.addKeyListener(new CustomKeyListener());
		textUnit.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textUnit.setName(LeamonERPConstants.TEXTFIELD_NAME_UNIT);
		textUnit.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textUnit.setColumns(10);
		textUnit.addFocusListener(new FocusEventHandler(tableStockItems));
		
		textFinish = new JTextField();
		textFinish.setBounds(193, 192, 233, 26);
		textFinish.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFinish.addKeyListener(new CustomKeyListener());
		textFinish.setName(LeamonERPConstants.TEXTFIELD_NAME_FINISH);
		textFinish.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFinish.setColumns(10);
		textFinish.addFocusListener(new FocusEventHandler(tableStockItems));

		textShape = new JTextField();
		textShape.setBounds(194, 235, 232, 26);
		textShape.setName(LeamonERPConstants.TEXTFIELD_NAME_SHAPE);
		textShape.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textShape.setColumns(10);
		textShape.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textShape.addFocusListener(new FocusEventHandler(tableStockItems));
		textShape.addKeyListener(new CustomKeyListener());
		
		textSaleUnit = new JTextField();
		textSaleUnit.setBounds(194, 93, 232, 26);
		textSaleUnit.setName(LeamonERPConstants.TEXTFIELD_NAME_SALE_UNIT);
		textSaleUnit.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textSaleUnit.setColumns(10);
		textSaleUnit.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textSaleUnit.addFocusListener(new FocusEventHandler(tableStockItems));
		textSaleUnit.addKeyListener(new CustomKeyListener());
		
		textDescription = new JTextField();
		textDescription.setBounds(194, 269, 290, 26);
		textDescription.setName(LeamonERPConstants.TEXTFIELD_NAME_DESCRIPTION);
		textDescription.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textDescription.setColumns(10);
		textDescription.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textDescription.addFocusListener(new FocusEventHandler(tableStockItems));
		textDescription.addKeyListener(new CustomKeyListener());
		
		btnSave = new JButton("Save");
		btnSave.setBounds(269, 354, 118, 72);
		btnSave.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_SAVE_BTN)));
		btnSave.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnSave.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnSave.setActionCommand(LeamonERPConstants.BUTTON_ACTION_ADD_STOCK_ITEM);
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "Save");
		btnSave.getActionMap().put("Save", getSaveAction());
		btnSave.addActionListener(this);
		
		btnSave.setBackground(Color.WHITE);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(5, 345, 521, 11);
		separator.setBackground(Color.ORANGE);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(397, 355, 127, 70);
		btnDelete.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnDelete.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setForeground((Color) null);
		btnDelete.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setActionCommand(LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM);
		btnDelete.setMnemonic(KeyEvent.VK_D);
		btnDelete.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), "Delete");
		btnDelete.getActionMap().put("Delete", getDeleteAction());
		btnDelete.addActionListener(this);
		
		btnEdit = new JButton("Edit");
		btnEdit.setBounds(133, 354, 118, 72);
		btnEdit.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON)));
		btnEdit.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnEdit.setForeground((Color) null);
		btnEdit.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setActionCommand(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM);
		btnEdit.setMnemonic(KeyEvent.VK_E);
		btnEdit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), "Edit");
		btnEdit.getActionMap().put("Edit", getEditAction());
		btnEdit.addActionListener(this);
		
		btnClear = new JButton("CleaR");
		btnClear.setBounds(6, 354, 121, 72);
		btnClear.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_CLEAR_BUTTON)));
		btnClear.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnClear.setForeground((Color) null);
		btnClear.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnClear.setBackground(Color.WHITE);
		btnClear.setActionCommand("CleaR");
		btnClear.setMnemonic(KeyEvent.VK_R);
		btnClear.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "Clear");
		btnClear.getActionMap().put("Clear", getClearAction());
		btnClear.addActionListener(this);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(5, 134, 527, 11);
		separator_1.setBackground(Color.BLUE);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(4, 226, 526, 17);
		separator_4.setBackground(Color.BLUE);
		
		JLabel label = new JLabel("Finish");
		label.setBounds(17, 193, 60, 22);
		label.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		label.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel label_3 = new JLabel("Unit");
		label_3.setBounds(16, 146, 40, 22);
		label_3.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		label_3.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblCatalogCode = new JLabel("Catalog Code");
		lblCatalogCode.setBounds(16, 160, 120, 22);
		lblCatalogCode.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblCatalogCode.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(16, 27, 40, 22);
		lblName.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblName.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel label_1 = new JLabel("Size");
		label_1.setBounds(16, 60, 40, 22);
		label_1.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		label_1.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblImage = new JLabel("Image");
		lblImage.setBounds(16, 312, 59, 22);
		lblImage.setForeground((Color) null);
		lblImage.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblShape = new JLabel("Shape");
		lblShape.setBounds(16, 236, 61, 22);
		lblShape.setForeground((Color) null);
		lblShape.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblSaleUnit = new JLabel("Unit");
		lblSaleUnit.setBounds(16, 94, 59, 22);
		lblSaleUnit.setForeground((Color) null);
		lblSaleUnit.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(16, 270, 110, 22);
		lblDescription.setForeground((Color) null);
		lblDescription.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		btnUploadImage = new JButton("Upload Image");
		btnUploadImage.setBounds(93, 310, 132, 27);
		btnUploadImage.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUploadImage.setForeground(Color.WHITE);
		btnUploadImage.setBackground(new Color(70, 130, 180));
		btnUploadImage.setActionCommand(LeamonERPConstants.BUTTON_ACTION_UPLOAD_IMAGE_STOCK_ITEM);
		btnUploadImage.addActionListener(this);
		btnUploadImage.addKeyListener(new CustomKeyListener());
		btnUploadImage.setMnemonic(KeyEvent.VK_U);
		btnUploadImage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK), "Upload Image");
		btnUploadImage.getActionMap().put("Upload Image", getBtnUploadImageAction());
		
		textImage = new JTextField();
		textImage.setBounds(235, 312, 275, 24);
		textImage.setEnabled(false);
		textImage.setFont(new Font("Tahoma", Font.BOLD, 14));
		textImage.setColumns(10);

		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		lblID = new JLabel("");
		lblID.setBounds(10, 11, 126, 19);
		lblID.setBackground(Color.ORANGE);
		lblID.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		lblID.setToolTipText("StockItem ID");
		lblID.setEnabled(false);
		
		labMsg = new JLabel("");
		labMsg.setBounds(174, 11, 814, 19);
		labMsg.setForeground(Color.BLACK);
		labMsg.setBackground(Color.LIGHT_GRAY);
		labMsg.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		labMsg.setToolTipText("StockItem ID");
		labMsg.setEnabled(false);
		
		scrollPaneImage = new JScrollPane();
		scrollPaneImage.setBounds(575, 302, 413, 179);
		scrollPaneImage.setToolTipText("stock item image");
		scrollPaneImage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.setLayout(null);
		panel.add(lblShape);
		panel.add(lblSaleUnit);
		panel.add(lblDescription);
		panel.add(textSaleUnit);
		panel.add(textShape);
		panel.add(textDescription);
		panel.add(lblImage);
		panel.add(btnUploadImage);
		panel.add(textImage);
		panel.add(separator_1);
		panel.add(btnClear);
		panel.add(btnEdit);
		panel.add(btnSave);
		panel.add(btnDelete);
		panel.add(separator);
		panel.add(lblCatalogCode);
		panel.add(lblName);
		panel.add(label);
		panel.add(label_1);
		//panel.add(label_3);  //unit
		panel.add(txtProductCode);
		panel.add(txtName);
		//panel.add(textUnit); //unit
		panel.add(txtSize);
		panel.add(textFinish);
		panel.add(separator_4);
		
		label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_2.setBounds(56, 27, 16, 22);
		panel.add(label_2);
		
		label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_4.setBounds(56, 94, 16, 22);
		panel.add(label_4);
		
		label_5 = new JLabel("*");
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_5.setBounds(61, 61, 16, 21);
		panel.add(label_5);
		
		lblStockItemImage = new JLabel("");
		lblStockItemImage.setBackground(Color.WHITE);
		scrollPaneImage.setViewportView(lblStockItemImage);
		contentPane.setLayout(null);
		contentPane.add(lblID);
		contentPane.add(labMsg);
		contentPane.add(panel);
		contentPane.add(scrollPaneImage);
		contentPane.add(scrollPane);
		
		registerFocusEvent();
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	
	class CustomKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e!=null){
				int KEY_CODE = e.getKeyCode();
				
				if( e.getSource() instanceof JTextField && KEY_CODE == KeyEvent.VK_DOWN){
					JTextField source = (JTextField) e.getSource();
					if(source!=null && source.getName()!=null && 
							(source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_STOCK_NAME) 
									|| source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_FINISH)
									|| source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_PRODUCT_CODE)
									|| source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SIZE)
									|| source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_UNIT)
									|| source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SHAPE)
									|| source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SALE_UNIT)
									|| source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_DESCRIPTION)
							)
					  ){
						tableStockItems.requestFocus();
					}
				}else if( e.getSource() instanceof JTextField && KEY_CODE == KeyEvent.VK_ENTER){
					JTextField source = (JTextField) e.getSource();
					if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_STOCK_NAME)){
						txtSize.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SIZE);
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SIZE)){
						textSaleUnit.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SALE_UNITS);
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SALE_UNIT)){
						txtProductCode.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_PRODUCT_CODES);
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_PRODUCT_CODE)){
						textFinish.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_FINISHES);
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_FINISH)){
						textShape.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SHAPES);
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SHAPE)){
						textDescription.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_DESCRIPTION);
					}/*else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_UNIT)){
						textFinish.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_UNITS);
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_FINISH)){
						textShape.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_FINISHES);
					}*/else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_DESCRIPTION)){
						btnUploadImage.requestFocus();
						tableStockItems.setName(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_DESCRIPTION);
					}
				}else if( e.getSource() instanceof JTable && KEY_CODE == KeyEvent.VK_ENTER){
					JTable source = (JTable) e.getSource();
					LOGGER.info("CustomKeyListener[keyPressed] component ["+source+"] Enter Key pressed");
					int selectedRow = source.getSelectedRow();
					LOGGER.debug("CustomKeyListener[keyPressed] Selected row ["+selectedRow+"]");
					if(selectedRow != -1){
						String val = (String)source.getValueAt(selectedRow, 0);
						LOGGER.debug("CustomKeyListener[keyPressed] Selected row value ["+val+"]");
						LOGGER.debug("CustomKeyListener[keyPressed] Table Name ["+source.getName()+"]");
						if(source.getName()!=null && source.getName().equals(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_NAME)){
							txtName.setText(val);
							txtName.requestFocus();
						}else if(source.getName()!=null && source.getName().equals(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_FINISHES)){
							textFinish.setText(val);
							textFinish.requestFocus();
						}else if(source.getName()!=null && source.getName().equals(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_PRODUCT_CODES)){
							txtProductCode.setText(val);
							txtProductCode.requestFocus();
						}else if(source.getName()!=null && source.getName().equals(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SIZE)){
							txtSize.setText(val);
							txtSize.requestFocus();
						}else if(source.getName()!=null && source.getName().equals(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_UNITS)){
							textUnit.setText(val);
							textUnit.requestFocus();
						}else if(source.getName()!=null && source.getName().equals(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SHAPES)){
							textShape.setText(val);
							textShape.requestFocus();
						}else if(source.getName()!=null && source.getName().equals(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SALE_UNITS)){
							textSaleUnit.setText(val);
							textSaleUnit.requestFocus();
						}else if(source.getName()!=null && source.getName().equals(LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_DESCRIPTION)){
							textDescription.setText(val);
							textDescription.requestFocus();
						}
					}
				}else if(e.getSource() instanceof JButton){
					JButton btn = (JButton) e.getSource();
					if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_UPLOAD_IMAGE_STOCK_ITEM) && KEY_CODE == KeyEvent.VK_ENTER){
						btn.doClick();
					}
				}// end if
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton btn = (JButton) e.getSource();
			LOGGER.info("StockItemManager[actionPerformed] Button Clicked ActionCommand["+btn.getActionCommand()+"].");
			if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_ADD_STOCK_ITEM)){
				saveStockItem();
			}else if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM)){
				deleteStockItem();
			}else if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM)){
				editStockItem();
			}else if (btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_UPLOAD_IMAGE_STOCK_ITEM)){
				imageShower();
			}else if (btn.getActionCommand()!=null && btn.getActionCommand().equals("Clear")){
				clear();
			}
			LOGGER.info("StockItemManager[actionPerformed] Button Clicked ActionCommand["+btn.getActionCommand()+"] End.");
		}
	}//action performed
	
	public void setStockItem(StockItem item){
		LOGGER.info("StockItemManager[setStockItem] inside.");
		LOGGER.debug("StockItemManager[setStockItem] ."+item);
		txtName.setText(item.getName());
		textDescription.setText(item.getDescription());
		textFinish.setText(item.getFinish());
		textImage.setText(item.getImagePath());
		textSaleUnit.setText(item.getSaleunit());
		textShape.setText(item.getShape());
		textUnit.setText(item.getUnit());
		txtProductCode.setText(item.getProductCode());
		txtSize.setText(item.getSize());
		lblID.setText(""+item.getId());
		labMsg.setText(LeamonERPConstants.EMPTY_STR);
		btnSave.setEnabled(false);
		if(item.getImagePath() != null && !item.getImagePath().isEmpty()){
			File f = new File(item.getImagePath());
			if(f.isFile()){
				LOGGER.debug("StockItemManager[setStockItem] is a file");
				LOGGER.debug("StockItemManager[setStockItem] Image Path Found ["+item.getImagePath()+"] imag set");
				lblStockItemImage.setIcon(new ImageIcon(item.getImagePath()));
			}else{
				LOGGER.debug("StockItemManager[setStockItem]  is not file");
				LOGGER.debug("StockItemManager[setStockItem] Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
				lblStockItemImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
			}
			
		}else{
			lblStockItemImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		}
		LOGGER.info("StockItemManager[setStockItem] end.");
	}
	
	private void saveStockItem(){
		LOGGER.info("StockItemManager[saveStockItem] inside.");
		
		if(!validateToSave()){
			return;
		}
		
		String name = txtName.getText();
		String productCode = txtProductCode.getText();
		String size = txtSize.getText();
		String finish = textFinish.getText();
		String unit = textUnit.getText();
		String shape = textShape.getText();
		String  saleUnit = textSaleUnit.getText();
		String description = textDescription.getText();
		String imagePath = textImage.getText();
		
		StockItem stockItem = StockItem.builder().name(name).productCode(productCode)
		.size(size).finish(finish).unit(unit).shape(shape).saleunit(saleUnit).imagePath(LeamonERPConstants.EMPTY_STR)
		.description(description).createdDate(new Timestamp(System.currentTimeMillis()))
		.lastUpdatedDate(new Timestamp(System.currentTimeMillis())).isEnable(Boolean.TRUE).build();
		
		try {
			List<StockItem> stockItems = StockDaoImpl.getInstance().getItemList();
			
			StockItem stockItemPresent = stockItems.stream().filter(e ->
			!Strings.isNullOrEmpty(e.getName()) && e.getName().equals(stockItem.getName()) 
			/*&& (!Strings.isNullOrEmpty(e.getProductCode()) && e.getProductCode().equals(stockItem.getProductCode()))*/
			&& (!Strings.isNullOrEmpty(e.getSize())  && e.getSize().equals(stockItem.getSize()))
			/*&& (!Strings.isNullOrEmpty(e.getFinish())  && e.getFinish().equals(stockItem.getFinish()))
			&& (!Strings.isNullOrEmpty(e.getUnit())  && e.getUnit().equals(stockItem.getUnit()))
			&& (!Strings.isNullOrEmpty(e.getShape())  && e.getShape().equals(stockItem.getShape()))*/
			&& (!Strings.isNullOrEmpty(e.getSaleunit())  && e.getSaleunit().equals(stockItem.getSaleunit()))
			/*&& (!Strings.isNullOrEmpty(e.getDescription())  && e.getDescription().equals(stockItem.getDescription()))*/
			
					).findAny().orElse(null);
			
			if(stockItemPresent!=null){
				JOptionPane.showInternalMessageDialog(this, "This item is already present.","Warning-Duplicate",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			StockDaoImpl.getInstance().save(stockItem);
			labMsg.setBackground(Color.GREEN);
			labMsg.setText("Item ID["+stockItem.getId()+"] save successfully.");
			JOptionPane.showInternalMessageDialog(this, "Saved.");
			LeamonERP.stockItemList.refreshStockTable();
			
			if(Strings.isNullOrEmpty(imagePath)){
				return;
			}
			File file= new File(imagePath);
			CopyOption [] options = new CopyOption[]{
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES
			};
			String toImageName = LeamonERPConstants.IMAGE_PATH+String.valueOf(stockItem.getId()).concat("_")+System.currentTimeMillis()+".jpg";
			LOGGER.info("StockItemManager[saveStockItem]  ImagePath "+toImageName);
			try {
				Files.copy(Paths.get(imagePath), 
					Paths.get(toImageName), 
					options);
				stockItem.setImagePath(toImageName);
				StockDaoImpl.getInstance().update(stockItem);
				LeamonERP.stockItemList.refreshStockTable();
				LOGGER.info("StockItemManager[saveStockItem] image path updated in db");
			} catch (IOException e) {
				LOGGER.error("StockItemManager[saveStockItem] "+e);
				JOptionPane.showInternalMessageDialog(this, "failed.");
			}
		} catch (Exception e1) {
			LOGGER.error(e1);
			labMsg.setBackground(Color.RED);
			labMsg.setText("Item ID["+stockItem.getId()+"] failed: "+e1.getMessage());
			if(e1.getMessage().contains(" unique constraint or index violation")){
				JOptionPane.showInternalMessageDialog(this, "This item is already present.");
			}else{
				JOptionPane.showInternalMessageDialog(this, "Failed to save: "+e1.getMessage(), "Save-Exception",JOptionPane.ERROR_MESSAGE);
			}
		}
		LOGGER.info("StockItemManager[saveStockItem] end.");
	}
	
	private void deleteStockItem(){
		LOGGER.info("StockItemManager[editStockItem] inside.");
		if(lblID.getText() == null || lblID.getText().isEmpty()){
			JOptionPane.showMessageDialog(this, "No ID Associated", "Delete", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		Integer id = Integer.parseInt(lblID.getText());
		StockItem item = StockItem.builder().id(id).build();
		try{
		StockDaoImpl.getInstance().disable(item);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "Failed to delete becuse : "+e.getMessage(), "Delete-Exception", JOptionPane.ERROR_MESSAGE);
			LOGGER.error(e);
		}
		labMsg.setBackground(Color.GREEN);
		labMsg.setText("Item "+id +" deleted successfully.");
		JOptionPane.showMessageDialog(this, "Item "+id +" deleted successfully.", "Delete-Success", JOptionPane.PLAIN_MESSAGE);
		LeamonERP.stockItemList.refreshStockTable();
		clear();
		LOGGER.info("StockItemManager[editStockItem] end.");
	}
	
	private void editStockItem(){
		LOGGER.info("StockItemManager[editStockItem] inside.");
		String name = txtName.getText();
		String productCode = txtProductCode.getText();
		String size = txtSize.getText();
		String finish = textFinish.getText();
		String unit = textUnit.getText();
		String shape = textShape.getText();
		String  saleUnit = textSaleUnit.getText();
		String description = textDescription.getText();
		String imagePath = textImage.getText();
		Integer id=null;
		
		if(!validateToSave()){
			LOGGER.error("failed to edit due to validation fails");
			return;
		}
		
		try{
			id = Integer.parseInt(lblID.getText());
			
			File file= new File(imagePath);
			CopyOption [] options = new CopyOption[]{
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES
			};
			StockItem stockItem = StockItem.builder().name(name).productCode(productCode)
					.size(size).finish(finish).unit(unit).shape(shape).saleunit(saleUnit)
					.description(description)
					.lastUpdatedDate(new Timestamp(System.currentTimeMillis())).id(id).build();
			
			String toImageName = LeamonERPConstants.IMAGE_PATH+String.valueOf(stockItem.getId()).concat("_")+System.currentTimeMillis()+".jpg";
			LOGGER.info("StockItemManager[editStockItem]  ImagePath "+toImageName);
			try {
				Files.copy(Paths.get(imagePath), 
					Paths.get(toImageName), 
					options);
				stockItem.setImagePath(toImageName);
				try{
				StockDaoImpl.getInstance().update(stockItem);
				}catch(Exception e){
					JOptionPane.showMessageDialog(this, "Failed to edit "+e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
					LOGGER.error(e);
					return;
				}
				LOGGER.info("StockItemManager[saveStockItem] image path updated in db");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Failed to set image "+e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
				LOGGER.error("StockItemManager[saveStockItem] "+e);
				return;
			}
			labMsg.setForeground(Color.GREEN);
			labMsg.setText("save changes succesfully.");
			JOptionPane.showMessageDialog(this, "Changes has been saved succesfully.", "Sucess", JOptionPane.PLAIN_MESSAGE);
			LeamonERP.stockItemList.refreshStockTable();
		}catch(NumberFormatException exp){
			LOGGER.error("StockItemManager[editStockItem] "+exp);
			labMsg.setText("Fail to change, Invalid ID.");
			JOptionPane.showMessageDialog(this, "Failed due to :  "+exp.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
		}
		LOGGER.info("StockItemManager[editStockItem] end.");
	}
	
	private void imageShower(){
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setDialogTitle("Stck Item Image");
		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("PNG, GIF, JPEG, JPG images", "png","gif","jpg","jpeg");
		chooser.addChoosableFileFilter(fileNameExtensionFilter);
		
		int x = chooser.showDialog(LeamonERP.stockItemManager, "Select Image");
		LOGGER.info("CustomKeyListener[keyPressed] dialog is opened.");
		if(x == JFileChooser.APPROVE_OPTION){
			LOGGER.info("CustomKeyListener[keyPressed] approve button pressed.");
			File file = chooser.getSelectedFile();
			textImage.setText(file.getPath());
			textImage.setEnabled(false);
			lblStockItemImage.setIcon(new ImageIcon(file.getPath()));
			LOGGER.info("CustomKeyListener[keyPressed] image has been set.");
		}
		
		btnSave.requestFocus();
	}
	
	private void btnClearClick(ActionEvent e){
		clear();
	}
	
	private void registerFocusEvent(){
		textFinish.addFocusListener(new FocusListenerHandler());
		txtSize.addFocusListener(new FocusListenerHandler());
		txtName.addFocusListener(new FocusListenerHandler());
		txtProductCode.addFocusListener(new FocusListenerHandler());
		textShape.addFocusListener(new FocusListenerHandler());
		textSaleUnit.addFocusListener(new FocusListenerHandler());
		textDescription.addFocusListener(new FocusListenerHandler());
	}
	
	public void clear(){
		LOGGER.info("StockItemManager[clear] btnClear inside");
		txtName.setText(LeamonERPConstants.EMPTY_STR);
		txtProductCode.setText(LeamonERPConstants.EMPTY_STR);
		txtSize.setText(LeamonERPConstants.EMPTY_STR);
		textFinish.setText(LeamonERPConstants.EMPTY_STR);
		textUnit.setText(LeamonERPConstants.EMPTY_STR);
		textShape.setText(LeamonERPConstants.EMPTY_STR);
		textSaleUnit.setText(LeamonERPConstants.EMPTY_STR);
		textDescription.setText(LeamonERPConstants.EMPTY_STR);
		textImage.setText(LeamonERPConstants.EMPTY_STR);
		lblID.setText(LeamonERPConstants.EMPTY_STR);
		lblStockItemImage.setText(LeamonERPConstants.EMPTY_STR);
		btnSave.setEnabled(true);
		txtName.requestFocus();
		lblStockItemImage.setText(LeamonERPConstants.EMPTY_STR);
		lblStockItemImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		txtName.requestFocus();
		LOGGER.info("StockItemManager[clear] btnClear end");
	}
	
	public Action getSaveAction(){
		Action saveAction = new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + s clicked");
				saveStockItem();
			}
		};
		saveAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		return saveAction;
	}
	
	public Action getDeleteAction(){
		Action deleteAction = new AbstractAction("Delete") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + D clicked");
				deleteStockItem();
			}
		};
		deleteAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		return deleteAction;
	}
	
	public Action getEditAction(){
		Action editAction = new AbstractAction("Edit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + E clicked");
				editStockItem();
			}
		};
		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	public Action getClearAction(){
		Action editAction = new AbstractAction("Clear") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + R clicked");
				clear();
			}
		};
		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	
	public Action getBtnUploadImageAction(){
		Action editAction = new AbstractAction("Upload Image") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + U clicked");
				imageShower();
			}
		};
		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	
	private boolean validateToSave(){
		if(Strings.isNullOrEmpty(txtName.getText())){
			JOptionPane.showMessageDialog(this, "Name can't left blank! It's mendatory", "Validation Fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(Strings.isNullOrEmpty(txtSize.getText())){
			JOptionPane.showMessageDialog(this, "Size can't left blank! It's mendatory", "Validation Fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(textSaleUnit.getText())){
			JOptionPane.showMessageDialog(this, "Sale Unit can't left blank! It's mendatory", "Validation Fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
