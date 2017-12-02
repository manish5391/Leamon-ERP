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

import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import com.google.common.base.Strings;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.db.PaymentReceivedDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.model.PaymentReceivedInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.event.PaymentUIKeyHndler;
import leamon.erp.ui.model.GenericModelWithSnp;
import leamon.erp.ui.model.TableInvoiceModel;
import leamon.erp.ui.model.TablePaymentReceivedModel;
import leamon.erp.ui.model.TableStockListItemModel;
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
	private JXTextField textFieldBRemark;
	private JXTextField textFieldPayment;
	private JXTextField textFieldAdjAmt;
	private JXTextField textFieldRemainingAmt;
	
	/*Added in Release 3.2*/
	private PaymentReceivedInfo paymentReceivedInfo;
	private JCheckBox chckbxWamountAdjust;
	private JCheckBox chckbxAdjustBillAmount;
	
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
		label_2.setBounds(385, 15, 6, 16);
		panel.add(label_2);
		label_2.setText("*");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		datePickerPaymentDate = new JXDatePicker(new Date());
		datePickerPaymentDate.setBounds(401, 13, 145, 22);
		datePickerPaymentDate.getMonthView().setZoomable(true);
		panel.add(datePickerPaymentDate);
		
		
		textFieldBRemark = new JXTextField();
		textFieldBRemark.setBounds(103, 131, 737, 23);
		panel.add(textFieldBRemark);
		textFieldBRemark.setPrompt("Remark");
		textFieldBRemark.setName("txtFieldBRemark");
		textFieldBRemark.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldBRemark.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		
		textFieldPayment = new JXTextField();
		textFieldPayment.setBounds(103, 42, 118, 23);
		panel.add(textFieldPayment);
		textFieldPayment.setPrompt("Payment");
		textFieldPayment.setName("txtFieldPayment");
		textFieldPayment.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPayment.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		
		textFieldPartyName = new JXTextField();
		textFieldPartyName.setBounds(103, 11, 226, 23);
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
		label_3.setBounds(352, 12, 39, 25);
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
		textFieldAdjAmt.setBounds(331, 95, 103, 23);
		panel.add(textFieldAdjAmt);
		
		textFieldRemainingAmt = new JXTextField();
		textFieldRemainingAmt.setPrompt("R. Amt.");
		textFieldRemainingAmt.setName("txtFieldPayment");
		textFieldRemainingAmt.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldRemainingAmt.setEnabled(true);
		textFieldRemainingAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldRemainingAmt.setBounds(444, 95, 126, 23);
		panel.add(textFieldRemainingAmt);
		
		JXLabel lblAdjustedAmt = new JXLabel();
		lblAdjustedAmt.setText("Adjusted");
		lblAdjustedAmt.setForeground(Color.BLACK);
		lblAdjustedAmt.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblAdjustedAmt.setBounds(341, 72, 59, 25);
		panel.add(lblAdjustedAmt);
		
		JXLabel lblRemainingAmt = new JXLabel();
		lblRemainingAmt.setText("Remaining");
		lblRemainingAmt.setForeground(Color.BLACK);
		lblRemainingAmt.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblRemainingAmt.setBounds(454, 72, 71, 25);
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
		
		ButtonGroup bg = new ButtonGroup();
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
		chckbxWamountAdjust.addKeyListener(new PaymentUIKeyHndler(textFieldBRemark));
		datePickerPaymentDate.getEditor().addKeyListener(new PaymentUIKeyHndler(textFieldBRemark));
	}
	
	private void setOnLoadDisable(boolean isDisable){
		table.setEnabled(isDisable);
		//textFieldpartyName.setEnabled(isDisable);
		datePickerPaymentDate.getEditor().setEnabled(isDisable);
		textFieldBRemark.setEnabled(isDisable);
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
		
		//fillPymentTable(info);
	}
	
	private void fillPymentTable(AccountInfo info){
		List<InvoiceInfo> filteredInvoiceInfo = getAllInvoice(info);
		
		List<Integer> snos = IntStream.range(1, 1+filteredInvoiceInfo.size()).boxed().collect(Collectors.toList());
		GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo> paymentModel =new GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo>(filteredInvoiceInfo, snos);
		/*TablePaymentReceivedModel tablePaymentReceivedModel= new TablePaymentReceivedModel(paymentModel, this);
		table.setModel(tablePaymentReceivedModel);

		textFieldPayment.requestFocus();*/
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
				//invoiceInfos= invoiceInfosParam.stream().filter(e-> !Strings.isNullOrEmpty(e.getPackingAmount())).collect(Collectors.toList());
			}else if(!Strings.isNullOrEmpty(type) && type.equals(LeamonERPConstants.TYPE_BILL_AMOUNT_ADJUSTMENT)){
				invoiceInfos= invoiceInfosParam.stream().filter(e-> !Strings.isNullOrEmpty(e.getBillAmount())).collect(Collectors.toList());
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
	
	private void btnPaymentSaveClick(ActionEvent e){
		
		if(!validateToSave()){
			return ;
		}
		
		Integer partyId = accountInfo.getId();
		String receivedDate = datePickerPaymentDate.getEditor().getText();
		String payment = textFieldPayment.getText();
		String bRemark = textFieldBRemark.getText();
		//String wRemark  = textFieldWRemark.getText();
		
		PaymentReceivedInfo paymentReceivedInfo = PaymentReceivedInfo.builder()
				.partyInfoID(partyId)
				.receivedDate(receivedDate)
				.receivedPayment(payment)
				.bRemark(bRemark)
				.wRemark("")
				.build();
		try{
			PaymentReceivedDaoImpl.getInstance().save(paymentReceivedInfo);
			this.paymentReceivedInfo = paymentReceivedInfo; 
			JOptionPane.showMessageDialog(this, "Payment saved.","Leamon-ERP",JOptionPane.PLAIN_MESSAGE);
			//btnPaymentSave.setEnabled(Boolean.FALSE);
		}catch(Exception exp){
			LOGGER.error(exp);
			JOptionPane.showMessageDialog(this, "Failed to saved because ["+exp+"]","Leamon-ERP",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean validateToSave(){
		
		if(accountInfo == null){
			JOptionPane.showMessageDialog(this, "Please select party name","Leamon-ERP", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(datePickerPaymentDate.getEditor().getText())){
			JOptionPane.showMessageDialog(this, "Please enter payment date","Leamon-ERP", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(textFieldPayment.getText())){
			JOptionPane.showMessageDialog(this, "Please enter payment amount","Leamon-ERP", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(textFieldBRemark.getText())){
			JOptionPane.showMessageDialog(this, "Please enter billing remark","Leamon-ERP", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		/*if(Strings.isNullOrEmpty(textFieldWRemark.getText())){
			JOptionPane.showMessageDialog(this, "Please enter w-remark","Leamon-ERP", JOptionPane.ERROR_MESSAGE);
			return false;
		}*/
		return true;
	}
	
	private void clear (){
		table.setModel(new TablePaymentReceivedModel((List<InvoiceInfo>)null));
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		textFieldBRemark.setText(LeamonERPConstants.EMPTY_STR);
		//textFieldWRemark.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPayment.setText(LeamonERPConstants.EMPTY_STR);
		textFieldAdjAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldRemainingAmt.setText(LeamonERPConstants.EMPTY_STR);
		//btnPaymentSave.setEnabled(Boolean.TRUE);
		
		paymentReceivedInfo = null;
		accountInfo = null;
		
	}
	
	private void btnCloseClick(ActionEvent e){
		this.dispose();
	}
	private void btnRefreshClick(ActionEvent e){
		clear();
	}
	
	private void chckbxWamountAdjustClick(ActionEvent e){
		final String METHOD_NAME = "chckbxWamountAdjustClick";
		if(chckbxWamountAdjust.isSelected()){
			List<InvoiceInfo> filteredInvoiceInfo = getAllInvoice(accountInfo);

			String packingAmt = "error";
			for(int rowIndex =0; rowIndex < filteredInvoiceInfo.size(); rowIndex++ ){
				try{
					packingAmt = LeamonUtil.calcPackingAmount(filteredInvoiceInfo.get(rowIndex));
					//filteredInvoiceInfo.get(rowIndex).setPackingAmount(packingAmt);
				}catch(Exception exp){
					packingAmt = exp.getMessage();
					LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+exp);
					packingAmt = "";
				}
			}
			filteredInvoiceInfo = filterinvoiceByType(filteredInvoiceInfo, LeamonERPConstants.TYPE_AMOUNT_WITHOUT_BILL_ADJUSTMENT);
			List<Integer> snos = IntStream.range(1, 1+filteredInvoiceInfo.size()).boxed().collect(Collectors.toList());
			GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo> paymentModel =new GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo>(filteredInvoiceInfo, snos);
			TablePaymentReceivedModel tablePaymentReceivedModel= new TablePaymentReceivedModel(paymentModel, this,LeamonERPConstants.TYPE_AMOUNT_WITHOUT_BILL_ADJUSTMENT);
			table.setModel(tablePaymentReceivedModel);
		}
	}
	
	private void chckbxAdjustBillAmountClick(ActionEvent e){
		if(chckbxAdjustBillAmount.isSelected()){
			List<InvoiceInfo> filteredInvoiceInfo = getAllInvoice(accountInfo);
			filteredInvoiceInfo = filterinvoiceByType(filteredInvoiceInfo, LeamonERPConstants.TYPE_BILL_AMOUNT_ADJUSTMENT);
			List<Integer> snos = IntStream.range(1, 1+filteredInvoiceInfo.size()).boxed().collect(Collectors.toList());
			GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo> paymentModel =new GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo>(filteredInvoiceInfo, snos);
			TablePaymentReceivedModel tablePaymentReceivedModel= new TablePaymentReceivedModel(paymentModel, this,LeamonERPConstants.TYPE_BILL_AMOUNT_ADJUSTMENT);
			table.setModel(tablePaymentReceivedModel);
		}
	}
	
	private void btnSaveAdjustmentClick(ActionEvent e){
		
	}
}
