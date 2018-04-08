package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTextField;

import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.db.OpeningBalanceDaoImpl;
import leamon.erp.db.PaymentInvoiceMappingDaoImpl;
import leamon.erp.db.PaymentReceivedDaoImpl;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.OpeningBalanceInfo;
import leamon.erp.model.PaymentInvoiceMappingInfo;
import leamon.erp.model.PaymentReceivedInfo;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.model.GenericModelWithSnp;
import leamon.erp.ui.model.TableAdjustedPaymentDeleteModel;
import leamon.erp.ui.model.TablePaymentInvoiceOpeningBalanceModel;
import leamon.erp.ui.model.TableAdjustedPaymentDeleteModel;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.PaymentEnum;
import lombok.Getter;

import java.awt.Font;
import java.beans.PropertyVetoException;
import java.text.DateFormat;

import org.jdesktop.swingx.JXLabel;
import java.awt.Color;
import org.jdesktop.swingx.JXDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXButton;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

@Getter
public class PaymentAdjustmentDeleteUI extends JInternalFrame {

	private static final Logger LOGGER = Logger.getLogger(PaymentAdjustmentDeleteUI.class);
	private static final String CLASS_NAME="PaymentAdjustmentDeleteUI";

	private JXTextField textFieldPartyName;
	private JXTextField textFieldPayment;
	private JXTextField datePicker;
	private JCheckBox chckbxAdjustBillAmount;
	private JCheckBox chckbxWamountAdjust;
	private JXTextField textFieldAdjAmt;
	private JXTextField textFieldRemainingAmt;
	private JXTextField textFieldRemark;
	private JXTable tableAdjustments;
	private JXLabel labelHiddenID;
	private  ButtonGroup bg;

	private JXButton btnSave;
	private JXButton btnRefresh;
	private JXButton btnClose;
	
	private PaymentReceivedInfo paymentReceivedInfo;
	private List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfosBackup;

	public PaymentAdjustmentDeleteUI() {

		setTitle("Adjustment Removal");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(3, 30, 871, 534);
		getContentPane().setLayout(null);

		textFieldPartyName = new JXTextField();
		textFieldPartyName.setPrompt(" Party Name");
		textFieldPartyName.setName("txtPartyName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyName.setBounds(103, 22, 582, 23);
		getContentPane().add(textFieldPartyName);

		JXLabel label = new JXLabel();
		label.setText("Party Name");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label.setBounds(10, 24, 71, 18);
		getContentPane().add(label);

		JXLabel label_1 = new JXLabel();
		label_1.setText("Payment");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_1.setBounds(10, 48, 71, 18);
		getContentPane().add(label_1);

		textFieldPayment = new JXTextField();
		textFieldPayment.setPrompt("Payment");
		textFieldPayment.setName("txtFieldPayment");
		textFieldPayment.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPayment.setEnabled(true);
		textFieldPayment.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPayment.setBounds(103, 44, 184, 23);
		getContentPane().add(textFieldPayment);

		datePicker = new JXTextField();
		datePicker.setBounds(695, 22, 145, 22);
		getContentPane().add(datePicker);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(102, 205, 170));
		panel.setBounds(103, 70, 190, 38);
		getContentPane().add(panel);

		chckbxAdjustBillAmount = new JCheckBox("B. Adjust");
		chckbxAdjustBillAmount.setFont(new Font("SansSerif", Font.BOLD, 13));
		panel.add(chckbxAdjustBillAmount);

		chckbxWamountAdjust = new JCheckBox("W. Adjust");
		chckbxWamountAdjust.setFont(new Font("SansSerif", Font.BOLD, 13));
		panel.add(chckbxWamountAdjust);

		textFieldAdjAmt = new JXTextField();
		textFieldAdjAmt.setPrompt("Adj. Amt.");
		textFieldAdjAmt.setName("txtFieldPayment");
		textFieldAdjAmt.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldAdjAmt.setEnabled(true);
		textFieldAdjAmt.setEditable(false);
		textFieldAdjAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldAdjAmt.setBounds(303, 83, 156, 23);
		getContentPane().add(textFieldAdjAmt);

		JXLabel label_2 = new JXLabel();
		label_2.setText("Adjusted");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_2.setBounds(303, 63, 59, 18);
		getContentPane().add(label_2);

		JXLabel label_3 = new JXLabel();
		label_3.setText("Remaining");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_3.setBounds(507, 63, 71, 18);
		getContentPane().add(label_3);

		textFieldRemainingAmt = new JXTextField();
		textFieldRemainingAmt.setPrompt("R. Amt.");
		textFieldRemainingAmt.setName("txtFieldPayment");
		textFieldRemainingAmt.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldRemainingAmt.setEnabled(true);
		textFieldRemainingAmt.setEditable(false);
		textFieldRemainingAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldRemainingAmt.setBounds(500, 83, 156, 23);
		getContentPane().add(textFieldRemainingAmt);

		textFieldRemark = new JXTextField();
		textFieldRemark.setPrompt("Remark");
		textFieldRemark.setName("txtFieldBRemark");
		textFieldRemark.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldRemark.setEnabled(true);
		textFieldRemark.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldRemark.setBounds(103, 113, 737, 23);
		getContentPane().add(textFieldRemark);

		JXLabel label_4 = new JXLabel();
		label_4.setText("Remark");
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_4.setBounds(10, 113, 46, 18);
		getContentPane().add(label_4);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 142, 842, 316);
		getContentPane().add(scrollPane);

		tableAdjustments = new JXTable();
		tableAdjustments.setColumnControlVisible(true);
		tableAdjustments.setName(LeamonERPConstants.TABLE_PAYMNET_INVOICE_MAPPING_DELETE);
		tableAdjustments.addMouseListener(new MouseClickHandler());
		scrollPane.setViewportView(tableAdjustments);

		bg=new ButtonGroup();
		bg.add(chckbxAdjustBillAmount);
		bg.add(chckbxWamountAdjust);

		labelHiddenID = new JXLabel();
		labelHiddenID.setForeground(Color.BLACK);
		labelHiddenID.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		labelHiddenID.setBounds(10, 0, 122, 18);
		getContentPane().add(labelHiddenID);

		btnClose = new JXButton();
		btnClose.setText("Close");
		btnClose.setBounds(757, 469, 98, 27);
		try {
			btnClose.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_CLOSE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		btnClose.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK), "Close");
		btnClose.getActionMap().put("Close", getCloseAction());
		btnClose.addActionListener(e -> btnCloseClick(e));
		getContentPane().add(btnClose);

		btnRefresh = new JXButton();
		btnRefresh.setText("Refresh");
		btnRefresh.setMnemonic(KeyEvent.VK_R);
		btnRefresh.setBounds(649, 469, 98, 27);
		try {
			btnRefresh.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_REFERESH)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		btnRefresh.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.ALT_DOWN_MASK), "Refresh");
		btnRefresh.getActionMap().put("Refresh", getRefreshAction());
		btnRefresh.addActionListener(e -> btnRefreshClickHandler(e));
		getContentPane().add(btnRefresh);

		btnSave = new JXButton();
		btnSave.setText("Save");
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.setBounds(541, 469, 98, 27);
		try {
			btnSave.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_SAVE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK), "Save");
		btnSave.getActionMap().put("Save", getSaveAction());
		getContentPane().add(btnSave);
		btnSave.addActionListener(e -> btnSaveClickHandler(e));

		isEnableOnLoad(Boolean.FALSE); 
	}

	private void isEnableOnLoad(boolean isEditable){
		textFieldPartyName.setEditable(isEditable);
		textFieldPayment.setEditable(isEditable);
		textFieldRemark.setEditable(isEditable);
		//textFieldPartyName.setEditable(isEditable);
		chckbxWamountAdjust.setEnabled(isEditable);
		chckbxAdjustBillAmount.setEnabled(isEditable);
		datePicker.setEditable(isEditable);
	}
	public void setPaymentInfo(PaymentReceivedInfo paymentReceivedInfo ,List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos){
		clear();
		labelHiddenID.setText("Id: "+paymentReceivedInfo.getId().intValue());
		textFieldPartyName.setText(paymentReceivedInfo.getAccountInfo().getName());
		textFieldPayment.setText(paymentReceivedInfo.getReceivedPayment());
		textFieldRemark.setText(paymentReceivedInfo.getRemark());
		String receiveDate = paymentReceivedInfo.getReceivedDate();
		datePicker.setText(receiveDate);
		
		this.paymentReceivedInfo = paymentReceivedInfo;
		if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(paymentReceivedInfo.getType())){
			chckbxWamountAdjust.setSelected(Boolean.TRUE);
		}
		if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentReceivedInfo.getType())){
			chckbxAdjustBillAmount.setSelected(Boolean.TRUE);
		}
		
		boolean isOpeningBalExist = false;

		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfosOpeningBalList= new  ArrayList<PaymentInvoiceMappingInfo>();
		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfosInvoiceList= new  ArrayList<PaymentInvoiceMappingInfo>();
		for(PaymentInvoiceMappingInfo info : paymentInvoiceMappingInfos){
			if(info.getOpeningBalanceID() != null){
				paymentInvoiceMappingInfosOpeningBalList.add(info);
				isOpeningBalExist = true;
			}

			if(info.getInvoiceInfoID()!=null){
				paymentInvoiceMappingInfosInvoiceList.add(info);
			}
		}

		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfosList =  new  ArrayList<PaymentInvoiceMappingInfo>();
		paymentInvoiceMappingInfosList.addAll(0, paymentInvoiceMappingInfosOpeningBalList);
		paymentInvoiceMappingInfosList.addAll(paymentInvoiceMappingInfosInvoiceList);

		List<Integer> snos = IntStream.range(1, 1+paymentInvoiceMappingInfosList.size()).boxed().collect(Collectors.toList());
		GenericModelWithSnp<List<PaymentInvoiceMappingInfo>, PaymentInvoiceMappingInfo> paymentInvoiceMappingListModel = 
				new GenericModelWithSnp<List<PaymentInvoiceMappingInfo>, PaymentInvoiceMappingInfo>(paymentInvoiceMappingInfosList, snos);
		TableAdjustedPaymentDeleteModel  tableModel = new TableAdjustedPaymentDeleteModel(paymentInvoiceMappingListModel,paymentReceivedInfo.getType(),this);
		tableAdjustments.setModel(tableModel);
		tableAdjustments.packAll();
		
		/*show hide column*/
		if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentReceivedInfo.getType())){
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_AMOUNT).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_REMAINING_AMOUNT).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_PAID_W_STATUS).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_RECEIVED_AMOUNT).setVisible(false);
			if(isOpeningBalExist){
				tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_BILL_NO).setVisible(true);
			}else{
				tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_BILL_NO).setVisible(false);
			}
		}
		if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(paymentReceivedInfo.getType())){
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_AMOUNT).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_REMAINING_AMOUNT).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_PAID_B_STATUS).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_RECEIVED_AMOUNT).setVisible(false);
			if(isOpeningBalExist){
				tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_BILL_NO).setVisible(true);
			}else{
				tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_BILL_NO).setVisible(false);
			}
		}
		
		paymentInvoiceMappingInfosBackup = new ArrayList<PaymentInvoiceMappingInfo>();
		paymentInvoiceMappingInfosBackup.addAll(paymentInvoiceMappingInfos);
	}

	public void clear(){
		paymentReceivedInfo = null;
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPayment.setText(LeamonERPConstants.EMPTY_STR);
		textFieldRemark.setText(LeamonERPConstants.EMPTY_STR);
		textFieldAdjAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldRemainingAmt.setText(LeamonERPConstants.EMPTY_STR);
		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos = new ArrayList<PaymentInvoiceMappingInfo>();
		TableAdjustedPaymentDeleteModel  tableModel = new TableAdjustedPaymentDeleteModel(paymentInvoiceMappingInfos);
		tableAdjustments.setModel(tableModel);
		tableAdjustments.packAll();
		bg.clearSelection();
	}

	public void openInvoice(){
		LOGGER.info(CLASS_NAME+"[openInvoice] inside");
		int selectedRow = tableAdjustments.getSelectedRow();

		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		/*Get accurate selected row after filtering records*/
		if(tableAdjustments.getRowSorter() != null){
			selectedRow = tableAdjustments.getRowSorter().convertRowIndexToModel(selectedRow);
		}

		TableAdjustedPaymentDeleteModel model  = (TableAdjustedPaymentDeleteModel)tableAdjustments.getModel();
		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos = model.getPaymentInvoiceMappingInfos();

		//List<InvoiceInfo> invoiceInfos = model.getInvoiceInfos();
		if(paymentInvoiceMappingInfos.get(selectedRow).getInvoiceInfoID()==null){
			JOptionPane.showMessageDialog(this, "It's not invoice","Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		//InvoiceInfo invoiceInfo = invoiceInfos.get(selectedRow);
		InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(selectedRow).getInvoiceInfo();
		if(invoiceInfo.isOpeningBalance()){
			JOptionPane.showMessageDialog(this, "It's not invoice","Leamon-ERP Error Message", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!LeamonERP.invoiceUI.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.invoiceUI);
		}
		LeamonERP.invoiceUI.requestFocus();
		try {
			LeamonERP.invoiceUI.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error(CLASS_NAME+"[openInvoice] "+e1);
		}
		LeamonERP.invoiceUI.setInvoiceInfo(invoiceInfo);
		LeamonERP.invoiceUI.setVisible(true);
		LeamonERP.invoiceUI.moveToFront();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info(CLASS_NAME+"[openInvoice] End.");
	}

	private Action getCloseAction() {
		Action closeAction = new AbstractAction("Close") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + C clicked");
				dispose();
			}
		};
		closeAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK));
		return closeAction;
	}
	
	private Action getRefreshAction(){
		Action editAction = new AbstractAction("Refresh") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ALT + R clicked");
				refresh();
			}
		};

		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.ALT_DOWN_MASK));
		return editAction;
	}
	
	private Action getSaveAction(){
		Action editAction = new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + S clicked");
				save();
			}
		};

		editAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK));
		return editAction;
	}

	private void save(){
		TableAdjustedPaymentDeleteModel model  = (TableAdjustedPaymentDeleteModel)tableAdjustments.getModel();
		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos = model.getPaymentInvoiceMappingInfos();
		boolean errorFlag = false;
		for(int i=0; i<paymentInvoiceMappingInfos.size(); i++){
			InvoiceInfo invoiceInfo = null;
			OpeningBalanceInfo openingBalanceInfo = null;
			invoiceInfo = paymentInvoiceMappingInfos.get(i).getInvoiceInfo();
			openingBalanceInfo = paymentInvoiceMappingInfos.get(i).getOpenigBalanceInfo();
			
			if(invoiceInfo != null){
				try{
					if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(paymentReceivedInfo.getType()) ){
						InvoiceDaoImpl.getInstance().updatePaidWBillAmount(invoiceInfo);
						PaymentInvoiceMappingDaoImpl.getInstance().disable(paymentInvoiceMappingInfos.get(i));
					}else if (ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentReceivedInfo.getType()) ){
						InvoiceDaoImpl.getInstance().updatePaidBillAmount(invoiceInfo);
						PaymentInvoiceMappingDaoImpl.getInstance().disable(paymentInvoiceMappingInfos.get(i));
					}
				}catch(Exception exp){
					LOGGER.error(exp);
					JOptionPane.showMessageDialog(this, "Failed to delete payment from invoice number : "+invoiceInfo.getInvoicNum());
					errorFlag = true;
				}
			}
			
			if(openingBalanceInfo != null){
				try{
					OpeningBalanceDaoImpl.getInstance().updateAdjustRemoval(openingBalanceInfo);
					PaymentInvoiceMappingDaoImpl.getInstance().disable(paymentInvoiceMappingInfos.get(i));
				}catch(Exception exp){
					LOGGER.error(exp);
					JOptionPane.showMessageDialog(this, "Failed to delete payment from opening balance : "+openingBalanceInfo.getId());
					errorFlag = true;
				}
			}
		}//end for
		
		if(!errorFlag){
			try{
				PaymentReceivedDaoImpl.getInstance().disable(paymentReceivedInfo);
				JOptionPane.showMessageDialog(this, "successfully deleted", "Leamon-ERP Message",JOptionPane.PLAIN_MESSAGE);
				LeamonERP.paymentUiManager.menuItemRefreshClick(null);
				btnCloseClick(null);
			}catch(Exception exp){
				LOGGER.error(exp);
				JOptionPane.showMessageDialog(this, "Failed to delete.", "Leamon-ERP Error Message",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	private void refresh(){
		setPaymentInfo(paymentReceivedInfo, paymentInvoiceMappingInfosBackup);
	}
	private void btnSaveClickHandler(ActionEvent e){
		save();
	}

	private void btnRefreshClickHandler(ActionEvent e){
		refresh();
	}

	private void btnCloseClick(ActionEvent e){
		this.dispose();
	}
}
