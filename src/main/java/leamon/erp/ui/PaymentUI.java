package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import java.awt.Color;
import org.jdesktop.swingx.JXLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import com.google.common.base.Strings;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.db.PaymentInvoiceMappingDaoImpl;
import leamon.erp.db.PaymentReceivedDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.model.PaymentInvoiceMappingInfo;
import leamon.erp.model.PaymentReceivedInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.event.PaymentUIKeyHndler;
import leamon.erp.ui.model.GenericModelWithSnp;
import leamon.erp.ui.model.TableInvoiceModel;
import leamon.erp.ui.model.TablePaymentReceivedModel;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.InvoicePaymentStatusEnum;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.LeamonUtil;
import lombok.Getter;

import org.apache.log4j.Logger;
import org.apache.xpath.operations.Bool;
import org.jdesktop.swingx.JXDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jdesktop.swingx.JXPanel;
import javax.swing.border.BevelBorder;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

@Getter
public class PaymentUI extends JInternalFrame {

	private JXTextField textFieldPartyName;
	private JXDatePicker datePickerPaymentDate;
	private JXTextField textFieldRemark;
	private JXTextField textFieldPayment;
	private JXTextField textFieldAdjAmt;
	private JXTextField textFieldRemainingAmt;
	
	/*Added in Release 3.2*/
	private PaymentReceivedInfo paymentReceivedInfo;
	private JCheckBox chckbxWamountAdjust;
	private JCheckBox chckbxAdjustBillAmount;
	private ButtonGroup bg;
	
	private AccountInfo accountInfo;
	private JXTable table;
	
	
	private static final Logger LOGGER = Logger.getLogger(PaymentUI.class);
	private static final String CLASS_NAME="PaymentUI";
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentUI frame = new PaymentUI();
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
	public PaymentUI() {
		setTitle("Payment");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setBackground(Color.WHITE);
		setBounds(230, 30, 858, 620);
		getContentPane().setLayout(null);
		
		JXPanel panel = new JXPanel();
		panel.setBackground(new Color(222, 184, 135));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 0, 850, 162);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JXLabel label_5 = new JXLabel();
		label_5.setBounds(61, 135, 6, 16);
		panel.add(label_5);
		label_5.setText("*");
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		JXLabel label_1 = new JXLabel();
		label_1.setBounds(87, 15, 6, 16);
		panel.add(label_1);
		label_1.setText("*");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		JXLabel label_2 = new JXLabel();
		label_2.setBounds(550, 14, 6, 16);
		panel.add(label_2);
		label_2.setText("*");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerPaymentDate = new JXDatePicker(new Date());
		datePickerPaymentDate.setFormats(df);
		datePickerPaymentDate.setBounds(566, 12, 145, 22);
		datePickerPaymentDate.getMonthView().setZoomable(true);
		panel.add(datePickerPaymentDate);
		
		
		textFieldRemark = new JXTextField();
		textFieldRemark.setBounds(103, 131, 737, 23);
		panel.add(textFieldRemark);
		textFieldRemark.setPrompt("Remark");
		textFieldRemark.setName("txtFieldBRemark");
		textFieldRemark.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldRemark.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		
		textFieldPayment = new JXTextField();
		textFieldPayment.setBounds(103, 42, 184, 23);
		panel.add(textFieldPayment);
		textFieldPayment.setPrompt("Payment");
		textFieldPayment.setName("txtFieldPayment");
		textFieldPayment.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPayment.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		
		textFieldPartyName = new JXTextField();
		textFieldPartyName.setBounds(103, 11, 406, 23);
		panel.add(textFieldPartyName);
		textFieldPartyName.setPrompt("Select Party Name");
		textFieldPartyName.setName("txtPartyName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		autoAccountInfoSuggestor(textFieldPartyName);
		
		JXLabel lblPayment = new JXLabel();
		lblPayment.setBounds(10, 42, 71, 25);
		panel.add(lblPayment);
		lblPayment.setText("Payment");
		lblPayment.setForeground(Color.BLACK);
		lblPayment.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		JXLabel label = new JXLabel();
		label.setBounds(10, 9, 71, 25);
		panel.add(label);
		label.setText("Party Name");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		JXLabel lblRemark = new JXLabel();
		lblRemark.setBounds(10, 129, 46, 25);
		panel.add(lblRemark);
		lblRemark.setText("Remark");
		lblRemark.setForeground(Color.BLACK);
		lblRemark.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		JXLabel label_3 = new JXLabel();
		label_3.setBounds(517, 11, 39, 25);
		panel.add(label_3);
		label_3.setText("Date");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		
		JXLabel label_4 = new JXLabel();
		label_4.setText("*");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_4.setBounds(75, 46, 6, 16);
		panel.add(label_4);
		
		textFieldAdjAmt = new JXTextField();
		textFieldAdjAmt.setPrompt("Adj. Amt.");
		textFieldAdjAmt.setName("txtFieldPayment");
		textFieldAdjAmt.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldAdjAmt.setEnabled(true);
		textFieldAdjAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldAdjAmt.setBounds(358, 95, 156, 23);
		textFieldAdjAmt.setEditable(false);
		panel.add(textFieldAdjAmt);
		
		textFieldRemainingAmt = new JXTextField();
		textFieldRemainingAmt.setPrompt("R. Amt.");
		textFieldRemainingAmt.setName("txtFieldPayment");
		textFieldRemainingAmt.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldRemainingAmt.setEnabled(true);
		textFieldRemainingAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldRemainingAmt.setBounds(555, 95, 156, 23);
		textFieldRemainingAmt.setEditable(false);
		panel.add(textFieldRemainingAmt);
		
		JXLabel lblAdjustedAmt = new JXLabel();
		lblAdjustedAmt.setText("Adjusted");
		lblAdjustedAmt.setForeground(Color.BLACK);
		lblAdjustedAmt.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblAdjustedAmt.setBounds(361, 60, 59, 25);
		panel.add(lblAdjustedAmt);
		
		JXLabel lblRemainingAmt = new JXLabel();
		lblRemainingAmt.setText("Remaining");
		lblRemainingAmt.setForeground(Color.BLACK);
		lblRemainingAmt.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblRemainingAmt.setBounds(565, 60, 71, 25);
		panel.add(lblRemainingAmt);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(102, 205, 170));
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(103, 76, 226, 42);
		panel.add(panel_2);
		
		chckbxAdjustBillAmount = new JCheckBox("B. Adjust");
		chckbxAdjustBillAmount.setFont(new Font("SansSerif", Font.BOLD, 13));
		chckbxAdjustBillAmount.addActionListener(e -> chckbxAdjustBillAmountClick(e));
		panel_2.add(chckbxAdjustBillAmount);
		
		chckbxWamountAdjust = new JCheckBox("W. Adjust");
		chckbxWamountAdjust.setFont(new Font("SansSerif", Font.BOLD, 13));
		chckbxWamountAdjust.addActionListener(e -> chckbxWamountAdjustClick(e));
		panel_2.add(chckbxWamountAdjust);
		
		bg = new ButtonGroup();
		bg.add(chckbxAdjustBillAmount);
		bg.add(chckbxWamountAdjust);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 162, 842, 383);
		getContentPane().add(scrollPane);
		
		table = new JXTable();
		table.setToolTipText("invoice items");
		table.setName(LeamonERPConstants.TABLE_PAYMENT);
		table.setColumnControlVisible(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		table.setModel(new TablePaymentReceivedModel((List<InvoiceInfo>)null));
		table.setComponentPopupMenu(createPopup());
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 546, 850, 48);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnSaveAdjustment = new JButton("Save Adjustment");
		btnSaveAdjustment.setBounds(504, 11, 130, 23);
		btnSaveAdjustment.addActionListener(e -> btnSaveAdjustmentClick(e));
		panel_1.add(btnSaveAdjustment);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(746, 11, 94, 23);
		btnClose.addActionListener(e -> btnCloseClick(e));
		panel_1.add(btnClose);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(642, 11, 94, 23);
		btnRefresh.addActionListener(e -> btnRefreshClick (e));
		panel_1.add(btnRefresh);
		
		
		table.addMouseListener(new MouseClickHandler());
		setOnLoadDisable(Boolean.TRUE);
		
		
		registerKeyEventHandler();
		
	}
	
	public JPopupMenu createPopup(){
		LOGGER.info("StockItemList[createPopup] inside");
//		new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON))
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete", new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		JMenuItem menuItemView = new JMenuItem("View",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_VIEW)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));
		
		menuItemDelete.setActionCommand(LeamonERPConstants.MENU_ACTION_DELETE_STOCK_ITEM);
		//menuItemDelete.addActionListener(this);
		menuItemView.setActionCommand(LeamonERPConstants.MENU_ACTION_VIEW_STOCK_ITEM);
		//menuItemView.addActionListener(this);
		menuItemRefresh.setActionCommand(LeamonERPConstants.MENU_ACTION_REFRESH_STOCK_ITEM);
		//menuItemRefresh.addActionListener(this);
		
		popupMenu.add(menuItemRefresh);
		popupMenu.add(menuItemView);
		popupMenu.add(menuItemDelete);
		LOGGER.info("StockItemList[createPopup] end.");
		SwingUtilities.updateComponentTreeUI(this);
		return popupMenu;
	}
	private void registerKeyEventHandler(){
		//textFieldPartyName.addKeyListener(new PaymentUIKeyHndler(textFieldPayment));
		textFieldPayment.addKeyListener(new PaymentUIKeyHndler(chckbxAdjustBillAmount));
		chckbxAdjustBillAmount.addKeyListener(new PaymentUIKeyHndler(chckbxWamountAdjust));
		chckbxWamountAdjust.addKeyListener(new PaymentUIKeyHndler(textFieldRemark));
		datePickerPaymentDate.getEditor().addKeyListener(new PaymentUIKeyHndler(textFieldRemark));
	}
	
	private void setOnLoadDisable(boolean isDisable){
		table.setEnabled(isDisable);
		//textFieldpartyName.setEnabled(isDisable);
		datePickerPaymentDate.getEditor().setEnabled(isDisable);
		textFieldRemark.setEnabled(isDisable);
		textFieldPayment.setEnabled(isDisable);
	}
	
	public void autoAccountInfoSuggestor(JTextField textField){
		final String methodName="autoAccountInfoSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");

		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoAccountIDTextFieldSuggestor = new LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo>(textField, this); 
		leamonAutoAccountIDTextFieldSuggestor.setItems(accountInfos);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}
	
	public void setAccountInfo(AccountInfo info){
		accountInfo = info;
		textFieldPartyName.setText(info.getName());
		
		textFieldPayment.setText(LeamonERPConstants.EMPTY_STR);
		textFieldAdjAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldRemainingAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPayment.requestFocus();
		bg.clearSelection();
		table.setModel(new TablePaymentReceivedModel((List<InvoiceInfo>)null));
		textFieldPayment.setEditable(true);
		//fillPymentTable(info);
	}
	
	public List<InvoiceInfo> getAllInvoice(AccountInfo info){
		List<InvoiceInfo> invoiceInfos = new ArrayList<InvoiceInfo>();
		try {
			invoiceInfos = InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemList();
			if(accountInfo == null){
				return invoiceInfos;
			}
			invoiceInfos= invoiceInfos.stream().filter(e->e.getPartyinfoID().equals(info.getId())).collect(Collectors.toList());
			
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		return invoiceInfos;
	}//end
	
	public List<InvoiceInfo> filterinvoiceByType(List<InvoiceInfo> invoiceInfosParam, String type){
		List<InvoiceInfo> invoiceInfos = new ArrayList<InvoiceInfo>();
		try {
			if(!Strings.isNullOrEmpty(type) && type.equals(LeamonERPConstants.TYPE_AMOUNT_WITHOUT_BILL_ADJUSTMENT)){
				invoiceInfos= invoiceInfosParam.stream().filter(e-> !InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(e.getWpaidstatus())).collect(Collectors.toList());
			}else if(!Strings.isNullOrEmpty(type) && type.equals(LeamonERPConstants.TYPE_BILL_AMOUNT_ADJUSTMENT)){
				invoiceInfos= invoiceInfosParam.stream().filter(e-> !InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(e.getPaidStatus())).collect(Collectors.toList());
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		return invoiceInfos;
	}//end
	
	public void openInvoice(String actionCommand){
		LOGGER.info("PaymentUI[openInvoice] inside");
		int selectedRow = table.getSelectedRow();
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		/*Get accurate selected row after filtering records*/
		if(table.getRowSorter() != null){
			selectedRow = table.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		
		TablePaymentReceivedModel model  = (TablePaymentReceivedModel)table.getModel();
		List<InvoiceInfo> invoiceInfos = model.getInvoiceInfos();
		
		InvoiceInfo invoiceInfo = invoiceInfos.get(selectedRow);
		
		if(!LeamonERP.invoiceUI.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.invoiceUI);
		}
		LeamonERP.invoiceUI.requestFocus();
		try {
			LeamonERP.invoiceUI.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error("PaymentUI[openInvoice] "+e1);
		}
		LeamonERP.invoiceUI.setInvoiceInfo(invoiceInfo);
		/*if(actionCommand.equals(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM)){
			LeamonERP.stockItemManager.getBtnSave().setEnabled(false);
		}*/
		LeamonERP.invoiceUI.setVisible(true);
		LeamonERP.invoiceUI.moveToFront();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info("PaymentUI[openInvoice] end");
	}
	
	private void clear (){
		table.setModel(new TablePaymentReceivedModel((List<InvoiceInfo>)null));
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		textFieldRemark.setText(LeamonERPConstants.EMPTY_STR);
		//textFieldWRemark.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPayment.setText(LeamonERPConstants.EMPTY_STR);
		textFieldAdjAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldRemainingAmt.setText(LeamonERPConstants.EMPTY_STR);
		//btnPaymentSave.setEnabled(Boolean.TRUE);
		bg.clearSelection();
		paymentReceivedInfo = null;
		accountInfo = null;
		textFieldPayment.setEditable(true);
	}
	
	private void chckbxWamountAdjustClick(ActionEvent e){
		final String METHOD_NAME = "chckbxWamountAdjustClick";
		if(!isValidAmouont()){
			bg.clearSelection();
			return ;
		}
		TablePaymentReceivedModel model  = (TablePaymentReceivedModel)table.getModel();
		if(model!=null && LeamonERPConstants.TYPE_BILL_AMOUNT_ADJUSTMENT.equals(model.getType()) 
				&& model.getIsBAmount().contains(Boolean.TRUE)){
			chckbxAdjustBillAmount.setSelected(true);
			JOptionPane.showMessageDialog(this, "please save b-adjustments", "Leamon-ERP : Message", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		if(chckbxWamountAdjust.isSelected()){
			textFieldPayment.setEditable(false);
			List<InvoiceInfo> filteredInvoiceInfo = getAllInvoice(accountInfo);
			filteredInvoiceInfo = filterinvoiceByType(filteredInvoiceInfo, LeamonERPConstants.TYPE_AMOUNT_WITHOUT_BILL_ADJUSTMENT);
			List<Integer> snos = IntStream.range(1, 1+filteredInvoiceInfo.size()).boxed().collect(Collectors.toList());
			GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo> paymentModel =new GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo>(filteredInvoiceInfo, snos);
			TablePaymentReceivedModel tablePaymentReceivedModel= new TablePaymentReceivedModel(paymentModel, this,LeamonERPConstants.TYPE_AMOUNT_WITHOUT_BILL_ADJUSTMENT);
			table.setModel(tablePaymentReceivedModel);
		}
	}
	
	private void chckbxAdjustBillAmountClick(ActionEvent e){
		if(!isValidAmouont()){
			bg.clearSelection();
			return ;
		}
		
		/*checking for payment*/
		TablePaymentReceivedModel model  = (TablePaymentReceivedModel)table.getModel();
		if(model!=null && LeamonERPConstants.TYPE_AMOUNT_WITHOUT_BILL_ADJUSTMENT.equals(model.getType()) 
				&& model.getIsWAmount().contains(Boolean.TRUE)){
			chckbxWamountAdjust.setSelected(true);
			JOptionPane.showMessageDialog(this, "please save w-adjustments", "Leamon-ERP : Message", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		
		if(chckbxAdjustBillAmount.isSelected()){
			
			textFieldPayment.setEditable(false);
			
			List<InvoiceInfo> filteredInvoiceInfo = getAllInvoice(accountInfo);
			filteredInvoiceInfo = filterinvoiceByType(filteredInvoiceInfo, LeamonERPConstants.TYPE_BILL_AMOUNT_ADJUSTMENT);
			List<Integer> snos = IntStream.range(1, 1+filteredInvoiceInfo.size()).boxed().collect(Collectors.toList());
			GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo> paymentModel =new GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo>(filteredInvoiceInfo, snos);
			TablePaymentReceivedModel tablePaymentReceivedModel= new TablePaymentReceivedModel(paymentModel, this,LeamonERPConstants.TYPE_BILL_AMOUNT_ADJUSTMENT);
			table.setModel(tablePaymentReceivedModel);
		}
	}
	
	private boolean isValidAmouont(){
		if(Strings.isNullOrEmpty(textFieldPayment.getText())){
			JOptionPane.showMessageDialog(this, "Please enter some amount in payment field", "Leamon-Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try{
			Double.parseDouble(textFieldPayment.getText());
		}catch(Exception exp){
			JOptionPane.showMessageDialog(this, "only numeric is accepted in pyment field", "Leamon-Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private boolean checkValidation(){
		if(Strings.isNullOrEmpty(textFieldPartyName.getText())){
			JOptionPane.showMessageDialog(this, "Party name can't be left blank", "Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(accountInfo == null){
			JOptionPane.showMessageDialog(this, "Please select party name","Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(datePickerPaymentDate.getEditor().getText())){
			JOptionPane.showMessageDialog(this, "date can't be left blank", "Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(textFieldPayment.getText())){
			JOptionPane.showMessageDialog(this, "payment can't be left blank", "Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(!chckbxAdjustBillAmount.isSelected() && !chckbxWamountAdjust.isSelected()){
			JOptionPane.showMessageDialog(this, "No adjustment check is enable", "Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(((TablePaymentReceivedModel)table.getModel()).getInvoiceInfos() == null 
				|| ((TablePaymentReceivedModel)table.getModel()).getInvoiceInfos().isEmpty()){
			JOptionPane.showMessageDialog(this, "No record in table", "Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		return true;
	}
	
	private void saveBillingPaymentAdjustment(PaymentReceivedInfo paymentReceivedInfo, TablePaymentReceivedModel model ){

		
		try{
			PaymentReceivedDaoImpl.getInstance().save(paymentReceivedInfo);
			
			/*Invoice payment handling*/
			if(!LeamonERPConstants.TYPE_BILL_AMOUNT_ADJUSTMENT.equals(model.getType()) || Strings.isNullOrEmpty(model.getType())){
				JOptionPane.showMessageDialog(this, "No appropriate data for billing amount adjustments. Hence Payment is saved with no adjustment.", "Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
				return ;
			}
			
			List<InvoiceInfo> invoiceInfos = model.getInvoiceInfos();
			List<Boolean> isBAmount = model.getIsBAmount();
			List<Double> receivedBillAmount = model.getReceivedBillAmount();
			List<Double> reminingBillinigBalance = model.getRemainingBillingBalance();
			
			boolean isSaved = true;
			for(int i=0; i<isBAmount.size(); i++ ){
				if(Boolean.TRUE.equals(isBAmount.get(i))){
					InvoiceInfo invoiceInfo = invoiceInfos.get(i);
					double receivedBal = receivedBillAmount.get(i);
					double remiaingBal = reminingBillinigBalance.get(i);
					if(remiaingBal == 0){ /*Remianing is zero*/
						invoiceInfo.setPaidStatus(InvoicePaymentStatusEnum.ALL_CLEAR.name());
					}else{
						double invoicePaidBillAmount = 0;
						try{
							invoicePaidBillAmount = Double.parseDouble(invoiceInfo.getPaidBillAmount());
						}catch(Exception exp){
							LOGGER.error(exp);
						}

						receivedBal = invoicePaidBillAmount + receivedBal; 
						invoiceInfo.setPaidStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
					invoiceInfo.setRemainingBillAmount(String.valueOf(remiaingBal));
					invoiceInfo.setPaidBillAmount(String.valueOf(receivedBal));
					invoiceInfo.setLastUpdated(new Timestamp(System.currentTimeMillis()));
					try{
						InvoiceDaoImpl.getInstance().updatePaidBillAmount(invoiceInfo);
					}catch(Exception exp){
						LOGGER.error(exp);
						isSaved = false;
						JOptionPane.showMessageDialog(this, "Invoice id '"+invoiceInfo.getInvoicNum()+"' "+exp.getMessage(),"Leamon-ERP Error Meesage", JOptionPane.ERROR_MESSAGE);
						break;
					}
					
					/*saving mapping of payment id with invoice id*/
					PaymentInvoiceMappingInfo paymentInvoiceMappingInfo = PaymentInvoiceMappingInfo.builder()
							.paymentReceivedInfo(paymentReceivedInfo.getId())
							.invoiceInfoID(invoiceInfo.getId())
							.createdDate(new Timestamp(System.currentTimeMillis()))
							.lastUpdated(new Timestamp(System.currentTimeMillis()))
							.isEnable(Boolean.TRUE)
							.build();
					try{
						PaymentInvoiceMappingDaoImpl.getInstance().save(paymentInvoiceMappingInfo);
					}catch(Exception exp){
						LOGGER.error(exp);
					}
				}else{
					continue;
				}
			}
			
			if(isSaved){
				JOptionPane.showMessageDialog(this, "adjustment saved.", "Leamon-ERP Error Meesage", JOptionPane.PLAIN_MESSAGE);
				clear();
			}else{
				JOptionPane.showMessageDialog(this, "Failed to save adjustment", "Leamon-ERP Error Meesage", JOptionPane.ERROR_MESSAGE);
			}
			
		}catch(Exception exp){
			LOGGER.error(exp);
			JOptionPane.showMessageDialog(this, "Failed to saved because ["+exp+"]","Leamon-ERP",JOptionPane.ERROR_MESSAGE);
		}
	
	}
	private void saveWithoutBillingPaymentAdjustment(PaymentReceivedInfo paymentReceivedInfo, TablePaymentReceivedModel model ){
		
		try{
			PaymentReceivedDaoImpl.getInstance().save(paymentReceivedInfo);
			
			/*Invoice payment handling*/
			if(!LeamonERPConstants.TYPE_AMOUNT_WITHOUT_BILL_ADJUSTMENT.equals(model.getType()) || Strings.isNullOrEmpty(model.getType())){
				JOptionPane.showMessageDialog(this, "No appropriate data for without billing amount adjustments. Hence Payment is saved with no adjustment.", "Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
				return ;
			}
			
			List<InvoiceInfo> invoiceInfos = model.getInvoiceInfos();
			List<Boolean> isWAmount = model.getIsWAmount();
			List<Double> receivedWBillAmount = model.getReceivedWithOutBillAmount();
			List<Double> reminingWBillinigBalance = model.getRemainingWithOutBillBalance();
			
			boolean isSaved = true;
			for(int i=0; i<isWAmount.size(); i++ ){
				if(Boolean.TRUE.equals(isWAmount.get(i))){
					InvoiceInfo invoiceInfo = invoiceInfos.get(i);
					double receivedBal = receivedWBillAmount.get(i);
					double remiaingBal = reminingWBillinigBalance.get(i);
					if(remiaingBal == 0){ /*Remianing is zero*/
						invoiceInfo.setWpaidstatus(InvoicePaymentStatusEnum.ALL_CLEAR.name());
					}else{
						double invoicePaidWBillAmount = 0;
						try{
							invoicePaidWBillAmount = Double.parseDouble(invoiceInfo.getPaidWithoutBillAmount());
						}catch(Exception exp){
							LOGGER.error(exp);
						}

						receivedBal = invoicePaidWBillAmount + receivedBal; 
						invoiceInfo.setWpaidstatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
					invoiceInfo.setRemainingWithoutBillAmount(String.valueOf(remiaingBal));
					invoiceInfo.setPaidWithoutBillAmount(String.valueOf(receivedBal));
					invoiceInfo.setLastUpdated(new Timestamp(System.currentTimeMillis()));
					try{
						InvoiceDaoImpl.getInstance().updatePaidWBillAmount(invoiceInfo);
					}catch(Exception exp){
						LOGGER.error(exp);
						isSaved = false;
						JOptionPane.showMessageDialog(this, "Invoice id '"+invoiceInfo.getInvoicNum()+"' "+exp.getMessage(),"Leamon-ERP Error Meesage", JOptionPane.ERROR_MESSAGE);
						break;
					}
					
					/*saving mapping of payment id with invoice id*/
					PaymentInvoiceMappingInfo paymentInvoiceMappingInfo = PaymentInvoiceMappingInfo.builder()
							.paymentReceivedInfo(paymentReceivedInfo.getId())
							.invoiceInfoID(invoiceInfo.getId())
							.createdDate(new Timestamp(System.currentTimeMillis()))
							.lastUpdated(new Timestamp(System.currentTimeMillis()))
							.isEnable(Boolean.TRUE)
							.build();
					try{
						PaymentInvoiceMappingDaoImpl.getInstance().save(paymentInvoiceMappingInfo);
					}catch(Exception exp){
						LOGGER.error(exp);
					}
				}else{
					continue;
				}
			}
			
			if(isSaved){
				JOptionPane.showMessageDialog(this, "adjustment saved.", "Leamon-ERP Error Meesage", JOptionPane.PLAIN_MESSAGE);
				clear();
			}else{
				JOptionPane.showMessageDialog(this, "Failed to save adjustment", "Leamon-ERP Error Meesage", JOptionPane.ERROR_MESSAGE);
			}
			
		}catch(Exception exp){
			LOGGER.error(exp);
			JOptionPane.showMessageDialog(this, "Failed to saved because ["+exp+"]","Leamon-ERP",JOptionPane.ERROR_MESSAGE);
		}
	
	
	}
	private void btnSaveAdjustmentClick(ActionEvent e){
		
		if(!checkValidation()){
			return ;
		}
		
		/*get all value to save*/
		Integer partyId = accountInfo.getId();
		String receivedDate = datePickerPaymentDate.getEditor().getText();
		String payment = textFieldPayment.getText();
		String adjustedAmount = textFieldAdjAmt.getText();
		String remainingAdjustmentAmount = textFieldRemainingAmt.getText();
		String remark = textFieldRemark.getText();

		String status = "";
		if(Strings.isNullOrEmpty(remainingAdjustmentAmount) && Strings.isNullOrEmpty(adjustedAmount)){
			status = ERPEnum.STATUS_PAYMENT_ADJUSTMENT_NOTHING.name();
		}else if("0.0".equals(remainingAdjustmentAmount.trim())){
			status = ERPEnum.STATUS_PAYMENT_ADJUSTMENT_CLEAR.name();
		}else{
			status = ERPEnum.STATUS_PAYMENT_ADJUSTMENT_PARTIAL.name();
		}
			
		TablePaymentReceivedModel model  = (TablePaymentReceivedModel)table.getModel();
		
		PaymentReceivedInfo paymentReceivedInfo = PaymentReceivedInfo.builder()
				.partyInfoID(partyId)
				.receivedDate(receivedDate)
				.receivedPayment(payment)
				.adjustedPayment(adjustedAmount)
				.remainingAmount(remainingAdjustmentAmount)
				.remark(remark)
				.status(status)
				
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastUpdated(new Timestamp(System.currentTimeMillis()))
				.isEnable(Boolean.TRUE)
				.build();
		/*save bill amount adjustments*/
		if(chckbxAdjustBillAmount.isSelected()){
			paymentReceivedInfo.setType(ERPEnum.TYPE_PAYMENT_WITH_BILL.name());
			
			if(!model.getIsBAmount().contains(Boolean.TRUE)){
				int choice =  JOptionPane.showConfirmDialog(this, "Nothing adjusted\n Still do you want to save?", "Leamon-ERP : Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
				if(choice == JOptionPane.NO_OPTION){
					return;
				}
			}
			saveBillingPaymentAdjustment(paymentReceivedInfo, model);
		}else if(chckbxWamountAdjust.isSelected()){
			paymentReceivedInfo.setType(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name());
			
			if(!model.getIsWAmount().contains(Boolean.TRUE)){
				int choice =  JOptionPane.showConfirmDialog(this, "Nothing adjusted\n Still do you want to save?", "Leamon-ERP : Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
				if(choice == JOptionPane.NO_OPTION){
					return;
				}
			}
			saveWithoutBillingPaymentAdjustment(paymentReceivedInfo, model);
		}else{
			JOptionPane.showMessageDialog(this, "No adjustment found.", "Leamon-ERP Message", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void btnCloseClick(ActionEvent e){
		this.dispose();
	}
	private void btnRefreshClick(ActionEvent e){
		clear();
	}
}
