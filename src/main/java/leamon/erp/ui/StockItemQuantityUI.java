package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import leamon.erp.component.helper.LeamonAutoStockItemTextFieldSuggestor;
import leamon.erp.db.StockDaoImpl;
import leamon.erp.db.StockQuantityDaoImpl;
import leamon.erp.model.StockItem;
import leamon.erp.model.StockItemQuantity;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;
/**
 * 
 * @author Leamon india Inc.
 * @Date oct 28,2017
 * @Version 3.1
 */

@Getter
public class StockItemQuantityUI extends JInternalFrame implements KeyListener{
	private JTextField textSaleUnit;
	private JTextField textShape;
	private JTextField textDescription;
	private JTextField txtProductCode;
	private JTextField txtName;
	private JTextField txtSize;
	private JTextField textFinish;
	private JLabel lblStockItemImage;
	private JLabel lblID;
	private JLabel labMsg;
	private JSpinner spinnerQuanitity;
	
	static final Logger LOGGER = Logger.getLogger(StockItemQuantityUI.class);
	private static final String CLASS_NAME="StockItemQuantityUI";
	
	private LeamonAutoStockItemTextFieldSuggestor<List<StockItem>, StockItem> leamonAutoStockItemTextFieldSuggestor;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockItemQuantityUI frame = new StockItemQuantityUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StockItemQuantityUI() {
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("Stock Item Quantity");
		setBounds(100, 100, 876, 474);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 994, 498);
		getContentPane().add(panel);
		
		lblID = new JLabel("");
		lblID.setToolTipText("StockItem ID");
		lblID.setEnabled(false);
		lblID.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		lblID.setBackground(Color.ORANGE);
		lblID.setBounds(10, 11, 126, 19);
		panel.add(lblID);
		
		labMsg = new JLabel("");
		labMsg.setToolTipText("StockItem ID");
		labMsg.setForeground(Color.BLACK);
		labMsg.setEnabled(false);
		labMsg.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		labMsg.setBackground(Color.LIGHT_GRAY);
		labMsg.setBounds(174, 11, 814, 19);
		panel.add(labMsg);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 0, 0), null, null, null), "Stock Item Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(205, 92, 92)));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 33, 536, 412);
		panel.add(panel_1);
		
		JLabel label_2 = new JLabel("Shape");
		label_2.setForeground((Color) null);
		label_2.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_2.setBounds(16, 236, 61, 22);
		panel_1.add(label_2);
		
		JLabel label_3 = new JLabel("Sale Unit");
		label_3.setForeground((Color) null);
		label_3.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_3.setBounds(336, 87, 90, 22);
		panel_1.add(label_3);
		
		JLabel label_4 = new JLabel("Description");
		label_4.setForeground((Color) null);
		label_4.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_4.setBounds(16, 270, 110, 22);
		panel_1.add(label_4);
		
		textSaleUnit = new JTextField();
		textSaleUnit.setName("txtSaleUnit");
		textSaleUnit.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textSaleUnit.setColumns(10);
		textSaleUnit.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textSaleUnit.setBounds(336, 120, 96, 26);
		panel_1.add(textSaleUnit);
		
		textShape = new JTextField();
		textShape.setName("txtShape");
		textShape.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textShape.setColumns(10);
		textShape.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textShape.setBounds(194, 235, 232, 26);
		panel_1.add(textShape);
		
		textDescription = new JTextField();
		textDescription.setName("txtDescription");
		textDescription.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textDescription.setColumns(10);
		textDescription.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textDescription.setBounds(194, 269, 290, 26);
		panel_1.add(textDescription);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLUE);
		separator.setBounds(6, 157, 527, 11);
		panel_1.add(separator);
		
		JButton btnClear = new JButton("CleaR");
		btnClear.setBounds(6, 318, 121, 72);
		btnClear.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_CLEAR_BUTTON)));
		btnClear.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnClear.setForeground((Color) null);
		btnClear.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnClear.setBackground(Color.WHITE);
		btnClear.setActionCommand("CleaR");
		btnClear.setMnemonic(KeyEvent.VK_R);
		btnClear.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "Clear");
		btnClear.getActionMap().put("Clear", getClearAction());
		btnClear.addActionListener(e -> btnClearClick(e));
		panel_1.add(btnClear);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(133, 318, 118, 72);
		btnEdit.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON)));
		btnEdit.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnEdit.setForeground((Color) null);
		btnEdit.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setActionCommand(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM);
		btnEdit.setMnemonic(KeyEvent.VK_E);
		btnEdit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), "Edit");
		btnEdit.getActionMap().put("Edit", getEditAction());
		btnEdit.addActionListener(e -> btnEditClick(e));
		panel_1.add(btnEdit);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(269, 318, 118, 72);
		btnSave.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_SAVE_BTN)));
		btnSave.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnSave.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnSave.setActionCommand(LeamonERPConstants.BUTTON_ACTION_ADD_STOCK_ITEM);
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "Save");
		btnSave.getActionMap().put("Save", getSaveAction());
		btnSave.addActionListener(e -> btnSaveClick(e));
		panel_1.add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(397, 319, 127, 70);
		btnDelete.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnDelete.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setForeground((Color) null);
		btnDelete.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setActionCommand(LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM);
		btnDelete.setMnemonic(KeyEvent.VK_D);
		btnDelete.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), "Delete");
		btnDelete.getActionMap().put("Delete", getDeleteAction());
		btnDelete.addActionListener(e -> btnDeleteClick(e));
		panel_1.add(btnDelete);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(Color.ORANGE);
		separator_1.setBounds(6, 303, 521, 11);
		panel_1.add(separator_1);
		
		JLabel label_6 = new JLabel("Catalog Code");
		label_6.setForeground((Color) null);
		label_6.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_6.setBounds(16, 160, 120, 22);
		panel_1.add(label_6);
		
		JLabel label_7 = new JLabel("Name");
		label_7.setForeground((Color) null);
		label_7.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_7.setBounds(16, 21, 40, 22);
		panel_1.add(label_7);
		
		JLabel label_8 = new JLabel("Finish");
		label_8.setForeground((Color) null);
		label_8.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_8.setBounds(17, 193, 60, 22);
		panel_1.add(label_8);
		
		JLabel label_9 = new JLabel("Size");
		label_9.setForeground((Color) null);
		label_9.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_9.setBounds(16, 50, 40, 22);
		panel_1.add(label_9);
		
		txtProductCode = new JTextField();
		txtProductCode.setName("txtProductCode");
		txtProductCode.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtProductCode.setColumns(10);
		txtProductCode.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtProductCode.setBounds(193, 161, 233, 26);
		panel_1.add(txtProductCode);
		
		txtName = new JTextField();
		txtName.setName("txtStockName");
		txtName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtName.setColumns(10);
		txtName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtName.setBounds(194, 21, 233, 23);
		//txtName.addKeyListener(this);
		panel_1.add(txtName);
		autoStockItemDescSuggestor(txtName);
		
		txtSize = new JTextField();
		txtSize.setName("txtSize");
		txtSize.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtSize.setColumns(10);
		txtSize.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtSize.setBounds(191, 50, 235, 26);
		panel_1.add(txtSize);
		
		textFinish = new JTextField();
		textFinish.setName("txtFinish");
		textFinish.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFinish.setColumns(10);
		textFinish.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFinish.setBounds(193, 192, 233, 26);
		panel_1.add(textFinish);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(Color.BLUE);
		separator_2.setBounds(4, 226, 526, 17);
		panel_1.add(separator_2);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setForeground((Color) null);
		lblQuantity.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblQuantity.setBounds(194, 87, 84, 22);
		panel_1.add(lblQuantity);
		
		JLabel label_10 = new JLabel("*");
		label_10.setForeground(Color.RED);
		label_10.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_10.setBounds(277, 88, 16, 21);
		panel_1.add(label_10);
		
		spinnerQuanitity = new JSpinner();
		spinnerQuanitity.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerQuanitity.setBounds(194, 123, 99, 28);
		panel_1.add(spinnerQuanitity);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("stock item image");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(556, 41, 305, 260);
		panel.add(scrollPane);
		
		lblStockItemImage = new JLabel("");
		lblStockItemImage.setBackground(Color.WHITE);
		scrollPane.setViewportView(lblStockItemImage);

	}
	
	public void setStockItem(StockItem item){
		LOGGER.info("StockItemQuantityUI[setStockItem] inside.");
		LOGGER.debug("StockItemQuantityUI[setStockItem] ."+item);
		txtName.setText(item.getName());
		textDescription.setText(item.getDescription());
		textFinish.setText(item.getFinish());
		
		textSaleUnit.setText(item.getSaleunit());
		textShape.setText(item.getShape());
		//textUnit.setText(item.getUnit());
		txtProductCode.setText(item.getProductCode());
		txtSize.setText(item.getSize());
		lblID.setText(""+item.getId());
		labMsg.setText(LeamonERPConstants.EMPTY_STR);
		
		List<StockItemQuantity> stockItemQuantities =new ArrayList<StockItemQuantity>();
	 	try {
	 		stockItemQuantities = StockQuantityDaoImpl.getInstance().getItemList();
		} catch (Exception exp) {
			LOGGER.error(exp);
		}
	 	 
	 	StockItemQuantity matchedItemQuantity = stockItemQuantities.stream()
	 			.filter(s -> s.getStokItemid().equals(item.getId())).findFirst().orElse(null);
	 	if(matchedItemQuantity != null){
	 		try{
	 		int quantity = Integer.parseInt(matchedItemQuantity.getQuantity());
	 		((SpinnerNumberModel)spinnerQuanitity.getModel()).setValue(quantity);
	 		}catch (Exception exp) {
	 			LOGGER.error(exp);
			}
	 	}else{
	 		((SpinnerNumberModel)spinnerQuanitity.getModel()).setValue(0);
	 	}
	 	
		//btnSave.setEnabled(false);
		if(item.getImagePath() != null && !item.getImagePath().isEmpty()){
			File f = new File(item.getImagePath());
			if(f.isFile()){
				LOGGER.debug("StockItemQuantityUI[setStockItem] is a file");
				LOGGER.debug("StockItemQuantityUI[setStockItem] Image Path Found ["+item.getImagePath()+"] imag set");
				lblStockItemImage.setIcon(new ImageIcon(item.getImagePath()));
			}else{
				LOGGER.debug("StockItemQuantityUI[setStockItem]  is not file");
				LOGGER.debug("StockItemQuantityUI[setStockItem] Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
				lblStockItemImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
			}
			
		}else{
			lblStockItemImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		}
		LOGGER.info("StockItemQuantityUI[setStockItem] end.");
	}
	
	public Action getSaveAction(){
		Action saveAction = new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + s clicked");
				saveStockItemQuantity();
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
				//deleteStockItemQuantity();
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
				//editStockItemQuantity();
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
	
	private void clear(){
		LOGGER.info("StockItemQuantityUI[clear] btnClear inside");
		txtName.setText(LeamonERPConstants.EMPTY_STR);
		txtProductCode.setText(LeamonERPConstants.EMPTY_STR);
		txtSize.setText(LeamonERPConstants.EMPTY_STR);
		textFinish.setText(LeamonERPConstants.EMPTY_STR);
		//textUnit.setText(LeamonERPConstants.EMPTY_STR);
		textShape.setText(LeamonERPConstants.EMPTY_STR);
		textSaleUnit.setText(LeamonERPConstants.EMPTY_STR);
		textDescription.setText(LeamonERPConstants.EMPTY_STR);
		//textImage.setText(LeamonERPConstants.EMPTY_STR);
		lblID.setText(LeamonERPConstants.EMPTY_STR);
		lblStockItemImage.setText(LeamonERPConstants.EMPTY_STR);
		//btnSave.setEnabled(true);
		txtName.requestFocus();
		lblStockItemImage.setText(LeamonERPConstants.EMPTY_STR);
		lblStockItemImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		//txtName.requestFocus();
		((SpinnerNumberModel)spinnerQuanitity.getModel()).setValue("0");
		LOGGER.info("StockItemQuantityUI[clear] btnClear end");
	}
	private boolean validateToSave(){
		
		return true;
	}
	private void saveStockItemQuantity(){
		LOGGER.info("StockItemQuantityUI[saveStockItemQuantity] inside.");
		
		/*if(!validateToSave()){
			return;
		}*/
		
		SpinnerNumberModel numberModel = (SpinnerNumberModel)spinnerQuanitity.getModel();
		Integer quantity = numberModel.getNumber().intValue();
		String description = textDescription.getText();
		String stockItemId  = lblID.getText().trim();
		if(Strings.isNullOrEmpty(stockItemId)){
			JOptionPane.showMessageDialog(this, "Stock Item ID Not Found","Leamon-ERP-Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		Integer stockItemIdInt = 0;
		try{
			stockItemIdInt = Integer.parseInt(stockItemId);
		}catch(Exception exp){
			JOptionPane.showMessageDialog(this, "Stock Item ID is not a Integer","Leamon-ERP-Error",JOptionPane.ERROR_MESSAGE);
			LOGGER.error(exp);
			return ;
		}
		
		StockItemQuantity stockItemQuantity = StockItemQuantity.builder()
				.stokItemid(stockItemIdInt)
				.quantity(String.valueOf(quantity))
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastUpdatedDate(new Timestamp(System.currentTimeMillis()))
				.isEnable(Boolean.TRUE)
				.build();
		
		/*check if already exist */
		List<StockItemQuantity> stockItemQuantities =new ArrayList<StockItemQuantity>();
	 	try {
	 		stockItemQuantities = StockQuantityDaoImpl.getInstance().getItemList();
		} catch (Exception exp) {
			LOGGER.error(exp);
		}
	 	 
	 	StockItemQuantity matchedItemQuantity = stockItemQuantities.stream()
	 			.filter(s -> s.getStokItemid().equals(stockItemQuantity.getStokItemid())).findFirst().orElse(null);
	 	if(matchedItemQuantity==null){
	 		/*save logic*/
	 		try{
				StockQuantityDaoImpl.getInstance().save(stockItemQuantity);
				JOptionPane.showMessageDialog(this, "Saved Successfully.", "Leamon-ERP : Message", JOptionPane.PLAIN_MESSAGE);
			}catch(Exception exp){
				JOptionPane.showMessageDialog(this, exp, "Leamon-ERP : ERROR", JOptionPane.ERROR_MESSAGE);
				LOGGER.error(exp);
				return;
			}
	 	}else{
	 		//Update it.
	 		try{
	 			stockItemQuantity.setId(matchedItemQuantity.getId());
				StockQuantityDaoImpl.getInstance().update(stockItemQuantity);
				JOptionPane.showMessageDialog(this, "updated Successfully.", "Leamon-ERP : Message", JOptionPane.PLAIN_MESSAGE);
			}catch(Exception exp){
				JOptionPane.showMessageDialog(this, exp, "Leamon-ERP : ERROR", JOptionPane.ERROR_MESSAGE);
				LOGGER.error(exp);
				return;
			}
	 	}
		LOGGER.info("StockItemQuantityUI[saveStockItemQuantity] End.");
	}
	
	private void btnClearClick(ActionEvent e){
		clear();
	}
	private void btnDeleteClick(ActionEvent e){
		//dele
	}
	private void btnEditClick(ActionEvent e){
		
	}
	private void btnSaveClick(ActionEvent e){
		saveStockItemQuantity();
	}
	
	public void autoStockItemDescSuggestor(JTextField textField){
		final String methodName="autoItemDescSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");

		List<StockItem> stockItemList = new ArrayList<>();
		try {
			stockItemList = StockDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoStockItemTextFieldSuggestor 
		= new LeamonAutoStockItemTextFieldSuggestor<List<StockItem>, StockItem>(textField, this);
		leamonAutoStockItemTextFieldSuggestor.setItems(stockItemList);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}
	
	public void setStockItemInfo(StockItem info){
		if(info ==null){
			return ;
		}
		setStockItem(info);
	}
	
	public void setStockItemQuantity(StockItemQuantity stockItemQuantity){
		if(stockItemQuantity == null){
			return ;
		}
		StockItem stockItemPresent =null;
		try{
			List<StockItem> stockItems = StockDaoImpl.getInstance().getItemList();
			stockItemPresent = stockItems.stream().filter(e -> e.getId() == stockItemQuantity.getStokItemid()).findAny().orElse(null);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		
		setStockItemInfo(stockItemPresent);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		final int KEY_CODE = e.getKeyCode();
		if(KEY_CODE == KeyEvent.VK_ENTER){
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
