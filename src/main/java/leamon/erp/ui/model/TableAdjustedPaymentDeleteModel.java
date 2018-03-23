package leamon.erp.ui.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import leamon.erp.model.PaymentReceivedInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.model.OpeningBalanceInfo;
import leamon.erp.model.PaymentInvoiceMappingInfo;
import leamon.erp.ui.PaymentAdjustmentDeleteUI;
import leamon.erp.ui.PaymentUI;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.PaymentEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableAdjustedPaymentDeleteModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TableAdjustedPaymentDeleteModel.class);
	private static final String CLASS_NAME = "TablePaymentInvoiceOpeningBalanceModel";

	private final String [] columnName = new String[] {
			LeamonERPConstants.TABLE_HEADER_SNO,
			LeamonERPConstants.TABLE_HEADER_TYPE,
			LeamonERPConstants.TABLE_HEADER_ID,
			LeamonERPConstants.TABLE_HEADER_B_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_B_REMAINING_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_REMAINING_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_ADJUST_REMOVAL,
			LeamonERPConstants.TABLE_HEADER_B_ADJUSTED_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_ADJUSTED_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_PAID_B_STATUS,
			LeamonERPConstants.TABLE_HEADER_PAID_W_STATUS
	};

	private List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos;
	private List<Integer> sno;
	private String type;
	private List<Boolean> isAdjustRemove;
	PaymentAdjustmentDeleteUI paymentAdjustmentDeleteUI;
	
	public TableAdjustedPaymentDeleteModel(List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos){
		if(paymentInvoiceMappingInfos == null){
			paymentInvoiceMappingInfos = new ArrayList<PaymentInvoiceMappingInfo>();
		}
		this.paymentInvoiceMappingInfos = paymentInvoiceMappingInfos;
	}	

	public TableAdjustedPaymentDeleteModel(GenericModelWithSnp<List<PaymentInvoiceMappingInfo>, PaymentInvoiceMappingInfo> genericModelWithSnp){
		this.paymentInvoiceMappingInfos  = genericModelWithSnp.getOb();
		this.sno = genericModelWithSnp.getSno();
	}

	public TableAdjustedPaymentDeleteModel(GenericModelWithSnp<List<PaymentInvoiceMappingInfo>, PaymentInvoiceMappingInfo> genericModelWithSnp, String type, PaymentAdjustmentDeleteUI paymentAdjustmentDeleteUI){
		this(genericModelWithSnp);
		this.type = type;
		isAdjustRemove = new ArrayList<Boolean>();
		for(int i=0; i < genericModelWithSnp.getOb().size(); i++){
			isAdjustRemove.add(Boolean.FALSE);
		}
		this.paymentAdjustmentDeleteUI = paymentAdjustmentDeleteUI;
	}


	@Override
	public int getRowCount() {
		if(paymentInvoiceMappingInfos == null){
			return 0;
		}

		return paymentInvoiceMappingInfos.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		final String METHOD_NAME = "getValueAt";
		Object temp = null;
		switch (columnIndex) {
		case 0: {
			temp = sno.get(rowIndex); 
		}break;

		case 1: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				temp = LeamonERPConstants.OPENING_BALANCE;
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				temp = LeamonERPConstants.INVOICE;
			}else{
				temp = "N/A";
			}
		}break;
		case 2: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID();
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo().getInvoicNum();
			}else{
				temp = "N/A";
			}
		}break;

		case 3: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITH_BILL)){
					temp = openingBalanceInfo.getReceivedopeningbalanceamount(); 
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getBillAmount();
			}
		}break;

		case 4: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITHOUT_BILL)){
					temp = openingBalanceInfo.getReceivedopeningbalanceamount(); 
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getWithoutBillAmount();
			}
		} break;

		case 5: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITH_BILL)){
					temp = openingBalanceInfo.getRemainingopeningbalanceamount(); 
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getRemainingBillAmount();
			}
		}break;

		case 6: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITHOUT_BILL)){
					temp = openingBalanceInfo.getRemainingopeningbalanceamount(); 
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getRemainingWithoutBillAmount();
			}
		}break;

		case 7:{
			temp = isAdjustRemove.get(rowIndex).booleanValue();
		}break;
		case 8: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITHOUT_BILL)){
					temp = paymentInvoiceMappingInfos.get(rowIndex).getAmount();
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getRemainingWithoutBillAmount();
			}
		}break;
		case 9: {
			if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(type)){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getAmount();
			}
		}break;
		case 10: {
			if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(type)){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getAmount();
			}
		}break;
		case 11: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo().getPaidStatus();
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null){
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getType()) ){
					temp = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getStatus();
				}
			}
		}break;
		case 12: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo().getWpaidstatus();
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null){
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getType()) ){
					temp = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getStatus();
				}
			}
		}break;

		}

		return temp;
	}

	@Override
	public String getColumnName(int col) { 
		return columnName[col]; 
	} 

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
		case 0: return Integer.class;
		case 1: return String.class;
		case 2: return String.class;
		case 3: return String.class;
		case 4: return String.class;
		case 5: return String.class;
		case 6: return String.class;
		case 7: return Boolean.class;
		case 8: return String.class;
		case 9: return String.class;
		case 10: return String.class;
		case 11: return String.class;
		case 12: return String.class;
		default : 
			return Object.class;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 7:
			isAdjustRemove.set(rowIndex, (Boolean)aValue);
			if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(type)){
				if(null != aValue && aValue instanceof Boolean && aValue == Boolean.TRUE){
					calcBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}else if(null != aValue && aValue instanceof Boolean && aValue == Boolean.FALSE){
					calcBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}
				
			}else if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(type)){
				
			}
			
			break;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex == 7 ){
			return true;
		}else
			return super.isCellEditable(rowIndex, columnIndex);
	}
	
	private void calcBillAmtAdjustment(int rowIndex, int columnIndex, Boolean isChecked){
		double amount = 0;
		try{
			amount  = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldPayment().getText());
		}catch(Exception e){
			amount = 0;
			LOGGER.error(e);
		}
		if((amount ==0 || amount<0)){
			JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Payment is null hence Can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
			isAdjustRemove.set(rowIndex, Boolean.FALSE);
			return ;
		}

		
		if(Strings.isNullOrEmpty(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo().getRemainingBillAmount()) && paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID()==null){
			JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Billing amount is N/A hence Can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
			isAdjustRemove.set(rowIndex, Boolean.FALSE);
			return ;
		}

		if(isChecked){
			InvoiceInfo info =  paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
			
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID()!=null){
				//checkBCalculationOpeningBal(info, rowIndex, columnIndex,isChecked, amount);
			}else if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID()!=null){
				checkBCalculation(info, rowIndex, columnIndex,isChecked, amount);
			}
		}else if(!isChecked){
			InvoiceInfo info =  paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
			
			if(type.equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name()) && paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID()!=null){
				//unCheckBCalculationOpeningBal(info, rowIndex, columnIndex, isChecked);
			}else if(type.equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name()) && paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID()!=null){
				//unCheckBCalculation(info, rowIndex, columnIndex, isChecked);
			}
			
		}// end if
	}
	
	private void checkBCalculation(InvoiceInfo info, int rowIndex, int columnIndex, Boolean isChecked, double amount){
		double billAmt = 0;
		try{
			billAmt = Double.parseDouble(info.getRemainingBillAmount());
		}catch(Exception e){ LOGGER.error(e); }

		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){

			if(billAmt > amount){
				double totalBillAdjustment =  billAmt + amount;
				/*receivedBillAmount.set(rowIndex, amount);
				remainingBillingBalance.set(rowIndex, totalBillAdjustment);
*/
				info.setRemainingBillAmount(String.valueOf(totalBillAdjustment));
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(amount));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
			}else{
				double remainingBal =    amount - billAmt;
				//receivedBillAmount.set(rowIndex, billAmt);
				//remainingBillingBalance.set(rowIndex, 0.0);
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(billAmt));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(remainingBal));
			}
		}else{
			String adjustedAmountVal = paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText();
			String remainingAmtVal = paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText();

			double remainingAmt = 0;
			try{
				remainingAmt = Double.parseDouble(remainingAmtVal);
			}catch(Exception e){ LOGGER.error(e); }

			if(Strings.isNullOrEmpty(remainingAmtVal) ||  remainingAmt == 0){
				JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Left ZERO so can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
				//isBAmount.set(rowIndex, Boolean.FALSE);
				return ;
			}

			double adjustedAmount = 0;
			try{
				adjustedAmount = Double.parseDouble(adjustedAmountVal);
			}catch(Exception e){ LOGGER.error(e); }

			if(billAmt > remainingAmt){
				double totalBillAdjustment =  billAmt - remainingAmt;
				//receivedBillAmount.set(rowIndex, remainingAmt);
				//remainingBillingBalance.set(rowIndex, totalBillAdjustment);

				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+remainingAmt));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
			}else{
				double remainingBal =    remainingAmt - billAmt;
				//receivedBillAmount.set(rowIndex, billAmt);
				//remainingBillingBalance.set(rowIndex, 0.0);
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+billAmt));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(remainingBal));
			}
		}
	}
}
