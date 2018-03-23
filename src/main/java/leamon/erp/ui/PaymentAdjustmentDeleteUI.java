package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTextField;

import leamon.erp.model.InvoiceInfo;
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
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTable;

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
		textFieldPartyName.setBounds(103, 13, 582, 23);
		getContentPane().add(textFieldPartyName);
		
		JXLabel label = new JXLabel();
		label.setText("Party Name");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label.setBounds(10, 11, 71, 18);
		getContentPane().add(label);
		
		JXLabel label_1 = new JXLabel();
		label_1.setText("Payment");
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_1.setBounds(10, 37, 71, 18);
		getContentPane().add(label_1);
		
		textFieldPayment = new JXTextField();
		textFieldPayment.setPrompt("Payment");
		textFieldPayment.setName("txtFieldPayment");
		textFieldPayment.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPayment.setEnabled(true);
		textFieldPayment.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPayment.setBounds(103, 38, 184, 23);
		getContentPane().add(textFieldPayment);
		
		datePicker = new JXTextField();
		datePicker.setBounds(695, 15, 145, 22);
		getContentPane().add(datePicker);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(102, 205, 170));
		panel.setBounds(103, 62, 190, 38);
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
		textFieldAdjAmt.setBounds(303, 75, 156, 23);
		getContentPane().add(textFieldAdjAmt);
		
		JXLabel label_2 = new JXLabel();
		label_2.setText("Adjusted");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_2.setBounds(303, 58, 59, 18);
		getContentPane().add(label_2);
		
		JXLabel label_3 = new JXLabel();
		label_3.setText("Remaining");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_3.setBounds(507, 58, 71, 18);
		getContentPane().add(label_3);
		
		textFieldRemainingAmt = new JXTextField();
		textFieldRemainingAmt.setPrompt("R. Amt.");
		textFieldRemainingAmt.setName("txtFieldPayment");
		textFieldRemainingAmt.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldRemainingAmt.setEnabled(true);
		textFieldRemainingAmt.setEditable(false);
		textFieldRemainingAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldRemainingAmt.setBounds(500, 75, 156, 23);
		getContentPane().add(textFieldRemainingAmt);
		
		textFieldRemark = new JXTextField();
		textFieldRemark.setPrompt("Remark");
		textFieldRemark.setName("txtFieldBRemark");
		textFieldRemark.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldRemark.setEnabled(true);
		textFieldRemark.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldRemark.setBounds(103, 103, 737, 23);
		getContentPane().add(textFieldRemark);
		
		JXLabel label_4 = new JXLabel();
		label_4.setText("Remark");
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_4.setBounds(10, 100, 46, 18);
		getContentPane().add(label_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 137, 842, 316);
		getContentPane().add(scrollPane);
		
		tableAdjustments = new JXTable();
		tableAdjustments.setColumnControlVisible(true);
		tableAdjustments.setName(LeamonERPConstants.TABLE_PAYMNET_INVOICE_MAPPING_DELETE);
		tableAdjustments.addMouseListener(new MouseClickHandler());
		scrollPane.setViewportView(tableAdjustments);
		
		
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
		textFieldPartyName.setText(paymentReceivedInfo.getAccountInfo().getName());
		textFieldPayment.setText(paymentReceivedInfo.getReceivedPayment());
		textFieldRemark.setText(paymentReceivedInfo.getRemark());
		String receiveDate = paymentReceivedInfo.getReceivedDate();
		datePicker.setText(receiveDate);
		
		if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(paymentReceivedInfo.getType())){
			chckbxWamountAdjust.setSelected(Boolean.TRUE);
		}
		if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentReceivedInfo.getType())){
			chckbxAdjustBillAmount.setSelected(Boolean.TRUE);
		}
		
		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfosOpeningBalList= new  ArrayList<PaymentInvoiceMappingInfo>();
		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfosInvoiceList= new  ArrayList<PaymentInvoiceMappingInfo>();
		for(PaymentInvoiceMappingInfo info : paymentInvoiceMappingInfos){
			if(info.getOpeningBalanceID() != null){
				paymentInvoiceMappingInfosOpeningBalList.add(info);
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
		/*show hide column*/
		if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentReceivedInfo.getType())){
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_AMOUNT).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_REMAINING_AMOUNT).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_ADJUSTED_AMOUNT).setVisible(false);
		}
		if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(paymentReceivedInfo.getType())){
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_AMOUNT).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_REMAINING_AMOUNT).setVisible(false);
			tableAdjustments.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_ADJUSTED_AMOUNT).setVisible(false);
		}
		
	}
	
	public void clear(){
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPayment.setText(LeamonERPConstants.EMPTY_STR);
		textFieldRemark.setText(LeamonERPConstants.EMPTY_STR);
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
}