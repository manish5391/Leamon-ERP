package leamon.erp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTextField;

import leamon.erp.component.helper.LeamonAutoTableCellsValueSuggestor;
import leamon.erp.component.helper.LeamonAutoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InventoryInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.custom.CustomTableCellRenderer;
import leamon.erp.ui.event.KeyListenerHandler;
import leamon.erp.ui.model.TableInventoryItemModel;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

@Getter
public class InventoryUI extends JInternalFrame implements ActionListener {

	private static final String CLASS_NAME="InventoryUI";
	private static final Logger LOGGER = Logger.getLogger(InventoryUI.class);
	
	private AccountInfo autoAccountInfo;
	private JXTextField textName;
	private JXTextField textCity;
	private JXTextField textTransport;
	private JXTextField textCartun2;
	private JXTextField textBillNumber;
	private JXTextField textBookedBy;
	private JXTextField textMarka;
	private JXTextField textCartun1;
	private JXTable tblInventory;
	private JXDatePicker datePickerBillDate;
	private List<InventoryInfo> inventoryInfos;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryUI frame = new InventoryUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws PropertyVetoException 
	 */
	public InventoryUI()  {
		final String methodName="InventoryUI";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");
		
		Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
		
		setTitle("Inventory");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(10, 10, 1013, ((int)sc.getHeight())-100);
		
		Locale t[] = Locale.getAvailableLocales();
		List<String> list = new ArrayList<String>();
		for(Locale l : t){
			list.add(l.getDisplayCountry());
		}
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(new Color(173, 216, 230));
		toolBar.setRollover(true);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnClear = new JButton("New button");
		toolBar.add(btnClear);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JXLabel lblId = new JXLabel();
		lblId.setText("ID");
		
		JXLabel lblMsg = new JXLabel();
		lblMsg.setText("MSG");
		
		JXLabel lblDate = new JXLabel();
		lblDate.setText("Date : ");
		
		textName = new JXTextField();
		textName.setPrompt("Name");
		textName.setName(LeamonERPConstants.TEXTFIELD_INVENTORY_ACCOUNT_NAME);
		textName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		//textName.addKeyListener(new InventoryUIKeyHandler());
		
		autoAccountInfoSuggestor(textName);
		
		textCity = new JXTextField();
		textCity.setPrompt("City");
		textCity.setName(LeamonERPConstants.TEXTFIELD_INVENTORY_ACCOUNT_CITY);
		textCity.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textCity.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		
		textTransport = new JXTextField();
		textTransport.setPrompt("Transport");
		textTransport.setName(LeamonERPConstants.TEXTFIELD_INVENTORY_ACCOUNT_TRANSPORT);
		textTransport.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textTransport.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		panel_2.add(textTransport);
		
		JXLabel label = new JXLabel();
		label.setText("/");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		panel_2.add(label);
		
		textCartun1 = new JXTextField();
		textCartun1.setPrompt("Cartun");
		textCartun1.setName(LeamonERPConstants.TEXTFIELD_INVENTORY_CATRUN_A);
		textCartun1.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textCartun1.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textCartun1.addKeyListener(new InventoryUIKeyHandler());
		
		
		textCartun2 = new JXTextField();
		textCartun2.setPrompt("Cartun");
		textCartun2.setName(LeamonERPConstants.TEXTFIELD_INVENTORY_CATRUN_B);
		textCartun2.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textCartun2.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textCartun2.addKeyListener(new InventoryUIKeyHandler());
		panel_2.add(textCartun2);
		
		textBillNumber = new JXTextField();
		textBillNumber.setPrompt("Bill Number");
		textBillNumber.setName(LeamonERPConstants.TEXTFIELD_INVENTORY_BILL_NO);
		textBillNumber.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textBillNumber.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		
		datePickerBillDate = new JXDatePicker(new Date());
		
		textBookedBy = new JXTextField();
		textBookedBy.setPrompt("Booked by ");
		textBookedBy.setName(LeamonERPConstants.TEXTFIELD_INVENTORY_BOOKED_BY);
		textBookedBy.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textBookedBy.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textBookedBy.addKeyListener(new InventoryUIKeyHandler());
		
		tblInventory = new JXTable();
		tblInventory.setToolTipText("Inventory");
		tblInventory.setColumnControlVisible(true);
		tblInventory.setShowGrid(false, true);
		tblInventory.setComponentPopupMenu(createPopup());
		tblInventory.setRowHeight(30);
		tblInventory.setColumnSelectionAllowed(true);
		tblInventory.setName(LeamonERPConstants.TABLE_INVENTORY);
		//tblInventory.setAutoCreateRowSorter(false);
		//tblInventory.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		textMarka = new JXTextField();
		textMarka.setPrompt("Marka");
		textMarka.setName(LeamonERPConstants.TEXTFIELD_INVENTORY_ACCOUNT_MARKA);
		textMarka.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textMarka.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		
		JXLabel label_2 = new JXLabel();
		label_2.setText("/");
		label_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		
		
		panel_1.add(textMarka);
		panel_1.add(textCartun1);
		panel_1.add(label_2);
		
		JTableHeader header = tblInventory.getTableHeader();
	    header.setBackground(Color.WHITE);
	    header.setForeground(Color.RED);

	    inventoryInfos = new ArrayList<InventoryInfo>();
		TableInventoryItemModel inventoryItemModel = new TableInventoryItemModel(inventoryInfos);
		tblInventory.setModel(inventoryItemModel);
		setJTableColumnsWidth(tblInventory,987,0.4,3.6,1.6,1,1,0.4,2);
		
		tblInventory.doLayout();
		
		addRow();
		createBindings(tblInventory);
		/*StockItems suggestor*/
		autoStockItemSuggestor(tblInventory, 1);
		/*Total binding with ctrl+T*/
		tblInventory.addKeyListener(new KeyListenerHandler(tblInventory));
		/*inventory - ttal row color render*/
		tblInventory.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		inventoryItemModel.setRowColor(inventoryItemModel.getInventoryInfos().size()-1, Color.CYAN);
	//	tblInventory.prepareRenderer(new CustomTableCellRenderer(), tblInventory.getRowCount()-1, 0);
//		tblInventory.getColumnModel().getColumn(LeamonERPConstants.TABLE_INVENTORY_SNO).setCellRenderer(new CustomTableCellRenderer());
//		tblInventory.getColumnModel().getColumn(LeamonERPConstants.TABLE_INVENTORY_DESC_OF_GOODS).setCellRenderer(new CustomTableCellRenderer());
//		tblInventory.getColumnModel().getColumn(LeamonERPConstants.TABLE_INVENTORY_SIZE).setCellRenderer(new CustomTableCellRenderer());
//		tblInventory.getColumnModel().getColumn(LeamonERPConstants.TABLE_INVENTORY_QTY).setCellRenderer(new CustomTableCellRenderer());
//		tblInventory.getColumnModel().getColumn(LeamonERPConstants.TABLE_INVENTORY_RATE).setCellRenderer(new CustomTableCellRenderer());
//		tblInventory.getColumnModel().getColumn(LeamonERPConstants.TABLE_INVENTORY_DISCOUNT).setCellRenderer(new CustomTableCellRenderer());
//		tblInventory.getColumnModel().getColumn(LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS).setCellRenderer(new CustomTableCellRenderer());
		
		/*Auto Suggestor 
		LeamonAutoTextSuggestor textComplete = new LeamonAutoTextSuggestor(tblInventory, 2);
		textComplete.setItems(list);*/ 
		
		JSeparator separator = new JSeparator();
		
		JScrollPane scrollPaneInventory = new JScrollPane();
		scrollPaneInventory.setViewportView(tblInventory);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblMsg, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addGap(147)
					.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(textName, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(textCity, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(textBillNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(datePickerBillDate, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textBookedBy, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 977, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPaneInventory, GroupLayout.PREFERRED_SIZE, 987, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMsg, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDate, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(textName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(textCity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textBillNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(datePickerBillDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(textBookedBy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(scrollPaneInventory, GroupLayout.PREFERRED_SIZE, 461, GroupLayout.PREFERRED_SIZE))
		);
		panel.setLayout(gl_panel);
		
		LOGGER.info(CLASS_NAME+"["+methodName+"] end.");
	}
	
	public void setJTableColumnsWidth(JXTable table, int tablePreferredWidth,
	        double... percentages) {
	    double total = 0;
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	 
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int)
	                (tablePreferredWidth * (percentages[i] / total)));
	    }
	}
	
	public JPopupMenu createPopup(){
		final String methodName="createPopup";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete", new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		JMenuItem menuItemView = new JMenuItem("View",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_VIEW)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));
		
		menuItemDelete.setActionCommand(LeamonERPConstants.MENU_ACTION_DELETE);
		menuItemDelete.addActionListener(this);
		menuItemView.setActionCommand(LeamonERPConstants.MENU_ACTION_VIEW);
		menuItemView.addActionListener(this);
		menuItemRefresh.setActionCommand(LeamonERPConstants.MENU_ACTION_REFRESH);
		menuItemRefresh.addActionListener(this);
		
		popupMenu.add(menuItemRefresh);
		popupMenu.add(menuItemView);
		popupMenu.add(menuItemDelete);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end.");
		return popupMenu;
	}
	private void autoAccountInfoSuggestor(JXTextField textField){
		final String methodName="autoAccountInfoSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");
		List<AccountInfo> accountInfos  = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LeamonAutoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoTextFieldSuggestor = new 
				LeamonAutoTextFieldSuggestor<List<AccountInfo>, AccountInfo>(textField,this);
		leamonAutoTextFieldSuggestor.setItems(accountInfos);
		
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}
	
	private void autoStockItemSuggestor(JXTable table, int activeCol){
		final String methodName="autoStockItemSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");
		
		int selectedRow = table.getSelectedRow();
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			return;
		}
		
		if(table.getRowSorter() != null){
			LOGGER.debug(" Row sorter is not null");
			selectedRow = table.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		
		int selectedColumn = table.getSelectedColumn();
		TableInventoryItemModel model =(TableInventoryItemModel)table.getModel();
		if(model == null){
			return;
		}
		
		if(model.getInventoryInfos().isEmpty() || model.getInventoryInfos().get(selectedRow) == null){
			return;
		}
		
		if(model.getInventoryInfos().get(selectedRow).getIsTotalRow()){
			return ;
		}
		List<StockItem> stockItems = new ArrayList<>();
		try {
			stockItems = StockDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LeamonAutoTableCellsValueSuggestor<List<StockItem>, StockItem> leamonAutoStockItemsSuggestor = new 
				LeamonAutoTableCellsValueSuggestor<List<StockItem>, StockItem> (table, this, activeCol);
		leamonAutoStockItemsSuggestor.setItems(stockItems);
		/*LeamonAutoTextSuggestor suggestor = new LeamonAutoTextSuggestor<>(tblInventory, 1);
		suggestor.setItems(getDescList());*/
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}
	
	public void setAutoAccountInfo(AccountInfo autoAccountInfo) {
		this.autoAccountInfo = autoAccountInfo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	private void createBindings(JXTable table){
		final String METHOD_NAME="createBindings";
		LOGGER.debug(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside."));
		
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
		table.getActionMap().put("Enter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				
				if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
					return;
				}
				
				LOGGER.info("selectedRow["+selectedRow+"]");
				int selectedColumn = table.getSelectedColumn();
				
				int iniventoryInfondex = selectedRow;
				
				if(selectedColumn == LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS && 
						selectedRow == table.getRowCount()-2){
					addRow();
				}else if(selectedColumn ==LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS){
					table.changeSelection(selectedRow+1, 0, false, false);
					/*To do for calculation of TAX and Suspense amount along with grand total*/
					TableInventoryItemModel tableInventoryItemModel = (TableInventoryItemModel) tblInventory.getModel();
					if(table.getRowSorter() != null){
						LOGGER.debug(" Row sorter is not null");
						iniventoryInfondex = table.getRowSorter().convertRowIndexToModel(iniventoryInfondex);
					}
					InventoryInfo info = tableInventoryItemModel.getInventoryInfos().get(iniventoryInfondex);
					if(info.getIsTotalRow()){
//						LeamonERP.grandTotalUI.setDefaultData(info.getValueOfGoods());
//						LeamonERP.openGTCalculatr();
					}
				}else if(selectedColumn ==LeamonERPConstants.TABLE_INVENTORY_DISCOUNT){
					TableInventoryItemModel tableInventoryItemModel = (TableInventoryItemModel) tblInventory.getModel();
					if(table.getRowSorter() != null){
						LOGGER.debug(" Row sorter is not null");
						iniventoryInfondex = table.getRowSorter().convertRowIndexToModel(iniventoryInfondex);
					}
					InventoryInfo info = tableInventoryItemModel.getInventoryInfos().get(iniventoryInfondex);
					if(info.getIsTotalRow()){
						table.changeSelection(selectedRow, selectedColumn+1, false, false);
						return ;
					}
					BigDecimal valueOfGoods = tableInventoryItemModel.calcValueOfGoods(info.getQty(), info.getRate(), info.getDiscount());
					info.setValueOfGoods(valueOfGoods);
					tblInventory.repaint();
					table.changeSelection(selectedRow, selectedColumn+1, false, false);
					TableInventoryItemModel.setTotal(tableInventoryItemModel.getInventoryInfos());
				}else{
					table.changeSelection(selectedRow, selectedColumn+1, false, false);
				}
				
				if(table.getCellEditor(selectedRow, selectedColumn) != null){
					try{
						table.getCellEditor(iniventoryInfondex, selectedColumn).stopCellEditing();
					}catch(Exception exp)
					{ 
						LOGGER.info(exp.toString());
						//exp.printStackTrace();
					}
				}
			}
		});
		
		LOGGER.debug(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	
	public void addRow(){
		TableInventoryItemModel inventoryItemModel = (TableInventoryItemModel) tblInventory.getModel();
		List<InventoryInfo> infos =  inventoryItemModel.getInventoryInfos();
		if(infos.isEmpty()){
			InventoryInfo info1 = InventoryInfo.builder().sno(inventoryItemModel.getRowCount()+1).isTotalRow(Boolean.FALSE)
					.build();
			inventoryItemModel.addRow(info1);
			InventoryInfo info2 = InventoryInfo.builder().sno(inventoryItemModel.getRowCount()+1).isTotalRow(Boolean.TRUE)
								.descOfGoods("Total")
					.build();
			inventoryItemModel.addRow(info2);
			inventoryItemModel.cutomFireTableRowInserted(infos.size() -2 , infos.size() -1 );
		}else{
			try{
				InventoryInfo info = infos.get(infos.size()-1);
				if(info.getIsTotalRow()){
					infos.remove(infos.size()-1);
					InventoryInfo info1 = InventoryInfo.builder().sno(inventoryItemModel.getRowCount()+1).isTotalRow(Boolean.FALSE)
							.build();
					inventoryItemModel.addRow(info1);
					info.setSno(inventoryItemModel.getRowCount()+1);
					/*BigDecimal sum = getSumValueOfGods(infos);
					info.setValueOfGoods(sum);*/
					inventoryItemModel.addRow(info);
					inventoryItemModel.cutomFireTableRowInserted(infos.size() -1 , infos.size() -1 );
				}else{
					InventoryInfo info1 = InventoryInfo.builder().sno(inventoryItemModel.getRowCount()+1).isTotalRow(Boolean.FALSE)
							.build();
					inventoryItemModel.addRow(info1);
					InventoryInfo info2 = InventoryInfo.builder().sno(inventoryItemModel.getRowCount()+1).isTotalRow(Boolean.TRUE)
							.descOfGoods("Total")
							.build();
					/*BigDecimal sum = getSumValueOfGods(infos);
					info2.setValueOfGoods(sum);*/
					inventoryItemModel.addRow(info2);
					inventoryItemModel.cutomFireTableRowInserted(infos.size() -1 , infos.size() -1 );
				}
			}catch(Exception exp){
				LOGGER.error(exp.toString());
			}
		}
		int selectedRow = tblInventory.getSelectedRow();
		int selectedColumn = tblInventory.getSelectedColumn();
		tblInventory.changeSelection(selectedRow+1, 0, false, false);
		tblInventory.doLayout();
	}
	
	class InventoryUIKeyHandler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int KEY_CODE = e.getKeyCode();
			//LOGGER.info("Key Code : ["+KEY_CODE+"]");
			//JTextField
			if( e.getSource() instanceof JTextField && KEY_CODE == KeyEvent.VK_ENTER){

				JTextField source = (JTextField) e.getSource();
				LOGGER.info("JTextField : "+source.getName());
				
				if(source.getName().equals(LeamonERPConstants.TEXTFIELD_INVENTORY_CATRUN_A)){
					if(null == textCartun1.getText() || textCartun1.getText().isEmpty()){
						return;
					}
					textCartun2.setText(textCartun1.getText());
					textCartun2.requestFocus();
				}else if(source.getName().equals(LeamonERPConstants.TEXTFIELD_INVENTORY_CATRUN_B)){
					textBookedBy.requestFocus();
				}else if(source.getName().equals(LeamonERPConstants.TEXTFIELD_INVENTORY_BOOKED_BY)){
					tblInventory.requestFocus();
				}
			}//end if Enter key
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}
	
	public List<String> getDescList(){
		TableStockListItemModel model  = (TableStockListItemModel)LeamonERP.stockItemList.getTblStockList().getModel();
		List<StockItem> stockItems =  model.getStockItems();
		List<String> desc =stockItems.stream().map(item -> item.getName().concat("-").concat(item.getProductCode())).collect(Collectors.toList());
		return  desc;
	}
	
	public void setCellAlignment(JTable table){
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(String.class, centerRenderer);
	}
	
	public BigDecimal getSumValueOfGods(List<InventoryInfo> infos) {
		List<BigDecimal> values=  infos.stream().map(item -> item.getValueOfGoods()).filter(item -> item!=null).collect(Collectors.toList());//.stream().mapToDouble(BigDecimal::doubleValue).sum();
		double sum = values.stream().mapToDouble(BigDecimal::doubleValue).sum();
		return new BigDecimal(sum);
	}
	
	public void keyHandlerTotal(KeyEvent k){
		
	}
}
