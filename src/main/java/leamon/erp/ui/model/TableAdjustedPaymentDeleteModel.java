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
import leamon.erp.util.InvoicePaymentStatusEnum;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.PaymentEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

@Getter
@Setter
public class TableAdjustedPaymentDeleteModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TableAdjustedPaymentDeleteModel.class);
	private static final String CLASS_NAME = "TablePaymentInvoiceOpeningBalanceModel";

	private final String [] columnName = new String[] {
			LeamonERPConstants.TABLE_HEADER_SNO,
			LeamonERPConstants.TABLE_HEADER_TYPE,
			LeamonERPConstants.TABLE_HEADER_ID,
			LeamonERPConstants.TABLE_HEADER_DATE,
			LeamonERPConstants.TABLE_HEADER_B_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_B_REMAINING_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_B_RECEIVED_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_REMAINING_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_RECEIVED_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_ADJUST_REMOVAL,
			LeamonERPConstants.TABLE_HEADER_ADJUSTED_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_PAID_B_STATUS,
			LeamonERPConstants.TABLE_HEADER_PAID_W_STATUS,
			LeamonERPConstants.TABLE_HEADER_BILL_NO
	};

	private List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos;
	private List<Integer> sno;
	private String type;
	private List<Boolean> isAdjustRemove;
	private PaymentAdjustmentDeleteUI paymentAdjustmentDeleteUI;
	private List<Double> receivedBillAmount = new ArrayList<Double>();
	private List<Double> OldRemainingAmount = new ArrayList<Double>();
	private List<Double> OldReceivingAmount = new ArrayList<Double>();
	private List<String> OldBstatus= new ArrayList<String>();
	private List<String> OldWstatus= new ArrayList<String>();

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
		
		OldBstatus = new ArrayList<String>(genericModelWithSnp.getOb().size());
		OldWstatus = new ArrayList<String>(genericModelWithSnp.getOb().size());
		
		for(int i=0; i < genericModelWithSnp.getOb().size(); i++){
			isAdjustRemove.add(Boolean.FALSE);
			receivedBillAmount.add(Double.parseDouble(String.valueOf("0.0")));
			if(genericModelWithSnp.getOb().get(i).getInvoiceInfoID() != null){
				double invoiceRemainingBillAmount = 0;
				try{
					invoiceRemainingBillAmount = Double.parseDouble(String.valueOf(genericModelWithSnp.getOb().get(i).getInvoiceInfo().getRemainingBillAmount()));
				}catch(Exception exp){
					LOGGER.error(exp);
				}
				OldRemainingAmount.add(invoiceRemainingBillAmount);
				
				double invoicePaidBillAmount = 0;
				try{
					invoicePaidBillAmount = Double.parseDouble(String.valueOf(genericModelWithSnp.getOb().get(i).getInvoiceInfo().getPaidBillAmount()));
				}catch(Exception exp){
					LOGGER.error(exp);
				}
				OldReceivingAmount.add(invoicePaidBillAmount);
				
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(type)){
					OldBstatus.add(i,genericModelWithSnp.getOb().get(i).getInvoiceInfo().getPaidStatus());
				}
				if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(type)){
					OldBstatus.add(i,genericModelWithSnp.getOb().get(i).getInvoiceInfo().getWpaidstatus());
				}
			}else if (genericModelWithSnp.getOb().get(i).getOpeningBalanceID() !=null ){
				
				double remainingopeningbalanceamount = 0;
				try{
					remainingopeningbalanceamount = Double.parseDouble(String.valueOf(genericModelWithSnp.getOb().get(i).getOpenigBalanceInfo().getRemainingopeningbalanceamount()));
				}catch(Exception exp){
					LOGGER.error(exp);
				}
				OldRemainingAmount.add(remainingopeningbalanceamount);
				
				
				double receivedopeningbalanceamount = 0;
				try{
					receivedopeningbalanceamount = Double.parseDouble(String.valueOf(genericModelWithSnp.getOb().get(i).getOpenigBalanceInfo().getReceivedopeningbalanceamount()));
				}catch(Exception exp){
					LOGGER.error(exp);
				}
				OldReceivingAmount.add(receivedopeningbalanceamount);
				
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(type)){
					OldBstatus.add(i,genericModelWithSnp.getOb().get(i).getOpenigBalanceInfo().getStatus());
				}
				if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(type)){
					OldBstatus.add(i,genericModelWithSnp.getOb().get(i).getOpenigBalanceInfo().getStatus());
				}
			}
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
				temp = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getBilldate();
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo().getInvoicDate();
			}else{
				temp = "N/A";
			} 
		}break;
		case 4: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITH_BILL)){
					temp = openingBalanceInfo.getOpeningbalanceamount(); 
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getBillAmount();
			}
		}break;

		case 5: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITHOUT_BILL)){
					temp = openingBalanceInfo.getOpeningbalanceamount(); 
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getWithoutBillAmount();
			}
		} break;

		case 6: {
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
		
		case 7: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITH_BILL)){
					temp = openingBalanceInfo.getReceivedopeningbalanceamount(); 
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getPaidBillAmount();
			}
		}break;
		case 8: {
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
		
		case 9:{
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				OpeningBalanceInfo openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
				if(openingBalanceInfo.getType().equalsIgnoreCase(LeamonERPConstants.INVOICE_TYPE_WITHOUT_BILL)){
					temp = openingBalanceInfo.getReceivedopeningbalanceamount(); 
				}
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null ){
				InvoiceInfo invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
				temp = invoiceInfo.getPaidWithoutBillAmount();
			}
		}break;
		
		case 10:{
			temp = isAdjustRemove.get(rowIndex).booleanValue();
		}break;
		
		case 11: {
			temp = paymentInvoiceMappingInfos.get(rowIndex).getAmount();
		}break;
		
		case 12: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo().getPaidStatus();
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null){
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getType()) ){
					temp = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getStatus();
				}
			}
		}break;
		
		case 13: {
			if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo().getWpaidstatus();
			}else if (paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null){
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getType()) ){
					temp = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getStatus();
				}
			}
		}break;
		
		case 14:{
			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null ){
				temp = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo().getBillnumber();
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
		case 7: return String.class;
		case 8: return String.class;
		case 9: return String.class;
		case 10: return Boolean.class;
		case 11: return String.class;
		case 12: return String.class;
		case 13: return String.class;
		case 14: return String.class;
		default : 
			return Object.class;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 10:
			isAdjustRemove.set(rowIndex, (Boolean)aValue);
			if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(type)){
				if(null != aValue && aValue instanceof Boolean && aValue == Boolean.TRUE){
					calcBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}else if(null != aValue && aValue instanceof Boolean && aValue == Boolean.FALSE){
					calcBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}

			}else if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(type)){
				isAdjustRemove.set(rowIndex, (Boolean)aValue);
				if(null != aValue && aValue instanceof Boolean && aValue == Boolean.TRUE){
					calcWithoutBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}else if(null != aValue && aValue instanceof Boolean && aValue == Boolean.FALSE){
					calcWithoutBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}
			}

			break;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex == 10 ){
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

		InvoiceInfo invoiceInfo  = InvoiceInfo.builder().build();
		OpeningBalanceInfo openingBalanceInfo = OpeningBalanceInfo.builder().build();
		if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null){
			invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
		}
		
		if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null){
			openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
		}
		
		double invoiceRemainigBal = 0;
		double openingRemainingBal = 0;
		try{
			if(invoiceInfo != null){
				invoiceRemainigBal = Double.parseDouble(invoiceInfo.getRemainingBillAmount());
			}
		}catch(Exception exp){LOGGER.error(exp);}
		
		try{
			if(openingBalanceInfo != null){
				openingRemainingBal = Double.parseDouble(openingBalanceInfo.getRemainingopeningbalanceamount());
			}
		}catch(Exception exp){LOGGER.error(exp);}

		if(isChecked){
			InvoiceInfo info =  paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();

			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID()!=null){
				checkBCalculationOpeningBal(openingBalanceInfo, rowIndex, columnIndex,isChecked, amount);
			}else if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID()!=null){
				checkBCalculation(info, rowIndex, columnIndex,isChecked, amount);
			}
		}else if(!isChecked){
			InvoiceInfo info =  paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();

			if(type.equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name()) && paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID()!=null){
				unCheckBCalculationOpeningBal(openingBalanceInfo, rowIndex, columnIndex, isChecked);
			}else if(type.equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name()) && paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID()!=null){
				unCheckBCalculation(info, rowIndex, columnIndex, isChecked);
			}

		}// end if
	}

	private void checkBCalculation(InvoiceInfo info, int rowIndex, int columnIndex, Boolean isChecked, double amount){
		double billAmt = 0;
		try{
			billAmt = Double.parseDouble(info.getRemainingBillAmount());
		}catch(Exception e){ LOGGER.error(e); }

		double recordedAmount = 0;
		try{
			recordedAmount = Double.parseDouble(paymentInvoiceMappingInfos.get(rowIndex).getAmount());
		}catch(Exception e){ LOGGER.error(e); }
		
		double oldReceivedAmt = 0;
		try{
			oldReceivedAmt = Double.parseDouble(info.getPaidBillAmount());
		}catch(Exception e){ LOGGER.error(e); }
		OldReceivingAmount.set(rowIndex, oldReceivedAmt);
		
		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){
			if(recordedAmount > 0){
				double totalBillAdjustment =  billAmt + amount;
				receivedBillAmount.set(rowIndex, recordedAmount);
				OldRemainingAmount.set(rowIndex, billAmt);
				OldReceivingAmount.set(rowIndex, oldReceivedAmt);
				
				info.setRemainingBillAmount(String.valueOf(recordedAmount+billAmt));
				info.setPaidBillAmount(String.valueOf(OldReceivingAmount.get(rowIndex) - recordedAmount));
				
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(recordedAmount));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-recordedAmount));
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getPaidStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getBillAmount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setPaidStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setPaidStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
			}else {
				double actualBillAmount = 0;
				try{
					actualBillAmount = Double.parseDouble(info.getBillAmount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				double val2 = 0;
				try{
					val2 = Double.parseDouble(info.getRemainingBillAmount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				double removeAdjustAmt = 0;
				if( (amount+val2) > actualBillAmount){
					removeAdjustAmt = actualBillAmount - val2;
				}else{
					removeAdjustAmt = amount + val2;
				}
				info.setRemainingBillAmount(String.valueOf(removeAdjustAmt));
				if(amount > actualBillAmount){
					receivedBillAmount.set(rowIndex, actualBillAmount);
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(actualBillAmount));
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-actualBillAmount));
					if(actualBillAmount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckBCalculation(info, rowIndex, columnIndex, false);
						return;
					}else{
						info.setPaidBillAmount(String.valueOf(oldReceivedAmt-actualBillAmount));
					}
				}else{
					receivedBillAmount.set(rowIndex, amount);
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(amount));
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
					
					if(amount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckBCalculation(info, rowIndex, columnIndex, false);
						return;
					}else{
						info.setPaidBillAmount(String.valueOf(oldReceivedAmt-amount));
					}
				}
				OldRemainingAmount.set(rowIndex, billAmt);
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getPaidStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getBillAmount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setPaidStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else if(actualBillAmt < amount){
						info.setPaidStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setPaidStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
				
			}
		}else{
			if(recordedAmount == 0){
				String adjustedAmountVal = paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText();
				String remainingAmtVal = paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText();
				double remainingAmt = 0;
				try{
					remainingAmt = Double.parseDouble(remainingAmtVal);
				}catch(Exception e){ LOGGER.error(e); }

				if(Strings.isNullOrEmpty(remainingAmtVal) ||  remainingAmt == 0){
					JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Left ZERO so can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
					isAdjustRemove.set(rowIndex, Boolean.FALSE);
					return ;
				}
				double adjustedAmount = 0;
				try{
					adjustedAmount = Double.parseDouble(adjustedAmountVal);
				}catch(Exception e){ LOGGER.error(e); }
				double totalBillAdjustment =  billAmt + remainingAmt;
				
				double actualBillAmount = 0;
				try{
					actualBillAmount = Double.parseDouble(info.getBillAmount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				
				double adjustRemovalAmt = 0;
				if(remainingAmt > (actualBillAmount - billAmt)){
					adjustRemovalAmt = remainingAmt - (actualBillAmount - billAmt);
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(adjustRemovalAmt));
					receivedBillAmount.set(rowIndex, actualBillAmount);
					info.setRemainingBillAmount(String.valueOf(actualBillAmount));
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+actualBillAmount));
					if(actualBillAmount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckBCalculation(info, rowIndex, columnIndex, false);
						return;
					}else{
						info.setPaidBillAmount(String.valueOf(oldReceivedAmt-actualBillAmount));
					}
					
				}else{
					adjustRemovalAmt = remainingAmt;
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
					receivedBillAmount.set(rowIndex, adjustRemovalAmt);
					info.setRemainingBillAmount(String.valueOf(totalBillAdjustment));
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+remainingAmt));
					if(adjustRemovalAmt > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckBCalculation(info, rowIndex, columnIndex, false);
						return;
					}else{
						info.setPaidBillAmount(String.valueOf(oldReceivedAmt-adjustRemovalAmt));
					}
					
				}
				
				OldRemainingAmount.set(rowIndex, billAmt);
				
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getPaidStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getBillAmount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setPaidStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else if(remainingAmt >= actualBillAmount){
						info.setPaidStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setPaidStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
				
			}else{

				double val2 = 0;
				try{
					val2 = Double.parseDouble(info.getRemainingBillAmount());
				}catch(Exception exp){LOGGER.error(exp);}

				double total = recordedAmount + val2;
				info.setRemainingBillAmount(String.valueOf(total));
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getPaidStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getBillAmount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setPaidStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setPaidStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}

				double totalAdjust = 0;
				try{
					totalAdjust = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText());
				}catch(Exception exp){ LOGGER.error(exp); }
				totalAdjust = totalAdjust + recordedAmount;
				
				receivedBillAmount.set(rowIndex, recordedAmount);
				OldRemainingAmount.set(rowIndex, billAmt);
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(totalAdjust));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-totalAdjust));
				if(recordedAmount > oldReceivedAmt){
					JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
					isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
					unCheckBCalculation(info, rowIndex, columnIndex, false);
					return;
				}else{
					info.setPaidBillAmount(String.valueOf(oldReceivedAmt-recordedAmount));
				}
				
			}
		}
	}

	private void unCheckBCalculation(InvoiceInfo info, int rowIndex, int columnIndex, Boolean isChecked){
		double receivedAmount =  receivedBillAmount.get(rowIndex);
		double billAmt = 0;

		try{
			billAmt = Double.parseDouble(info.getRemainingBillAmount());
		}catch(Exception e){
			LOGGER.error(e);
		}

		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){
			paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText("0.0");
		}else{
			double val = 0;
			try{
				val = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText());
			}catch(Exception e){LOGGER.error(e);}

			paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(val-receivedAmount));
			info.setRemainingBillAmount(String.valueOf(OldRemainingAmount.get(rowIndex)));

			double val2=0;
			try{
				val2 = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText());
			}catch(Exception e){
				LOGGER.error(e);
			}
			
			paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(val2+receivedAmount));
			info.setPaidStatus(OldBstatus.get(rowIndex));
			receivedBillAmount.set(rowIndex, 0.0);
			
			info.setPaidBillAmount(String.valueOf(OldReceivingAmount.get(rowIndex)));
		}
	}
	
	private void checkBCalculationOpeningBal(OpeningBalanceInfo info, int rowIndex, int columnIndex, Boolean isChecked, double amount){
		double billAmt = 0;
		try{
			billAmt = Double.parseDouble(info.getRemainingopeningbalanceamount());
		}catch(Exception e){ LOGGER.error(e); }

		double recordedAmount = 0;
		try{
			recordedAmount = Double.parseDouble(paymentInvoiceMappingInfos.get(rowIndex).getAmount());
		}catch(Exception e){ LOGGER.error(e); }
		
		double oldReceivedAmt = 0;
		try{
			oldReceivedAmt = Double.parseDouble(info.getReceivedopeningbalanceamount());
		}catch(Exception e){ LOGGER.error(e); }
		OldReceivingAmount.set(rowIndex, oldReceivedAmt);
		
		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){
			if(recordedAmount > 0){
				double totalBillAdjustment =  billAmt + amount;
				receivedBillAmount.set(rowIndex, recordedAmount);
				OldRemainingAmount.set(rowIndex, billAmt);
				OldReceivingAmount.set(rowIndex, oldReceivedAmt);
				
				info.setRemainingopeningbalanceamount(String.valueOf(recordedAmount+billAmt));
				info.setReceivedopeningbalanceamount(String.valueOf(OldReceivingAmount.get(rowIndex) - recordedAmount));
				
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(recordedAmount));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-recordedAmount));
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getOpeningbalanceamount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
			}else {
				double actualBillAmount = 0;
				try{
					actualBillAmount = Double.parseDouble(info.getOpeningbalanceamount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				double val2 = 0;
				try{
					val2 = Double.parseDouble(info.getRemainingopeningbalanceamount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				double removeAdjustAmt = 0;
				if( (amount+val2) > actualBillAmount){
					removeAdjustAmt = actualBillAmount - val2;
				}else{
					removeAdjustAmt = amount + val2;
				}
				info.setRemainingopeningbalanceamount(String.valueOf(removeAdjustAmt));
				if(amount > actualBillAmount){
					receivedBillAmount.set(rowIndex, actualBillAmount);
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(actualBillAmount));
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-actualBillAmount));
					if(actualBillAmount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckBCalculationOpeningBal(info, rowIndex, columnIndex, false);											
						return;
					}else{
						info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-actualBillAmount));
					}
				}else{
					receivedBillAmount.set(rowIndex, amount);
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(amount));
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
					if(amount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckBCalculationOpeningBal(info, rowIndex, columnIndex, false);											
						return;
					}else{
						info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-amount));
					}
				}
				OldRemainingAmount.set(rowIndex, billAmt);
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getOpeningbalanceamount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else if(actualBillAmt < amount){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
				
			}
		}else{
			if(recordedAmount == 0){
				String adjustedAmountVal = paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText();
				String remainingAmtVal = paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText();
				double remainingAmt = 0;
				try{
					remainingAmt = Double.parseDouble(remainingAmtVal);
				}catch(Exception e){ LOGGER.error(e); }

				if(Strings.isNullOrEmpty(remainingAmtVal) ||  remainingAmt == 0){
					JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Left ZERO so can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
					isAdjustRemove.set(rowIndex, Boolean.FALSE);
					return ;
				}
				double adjustedAmount = 0;
				try{
					adjustedAmount = Double.parseDouble(adjustedAmountVal);
				}catch(Exception e){ LOGGER.error(e); }
				double totalBillAdjustment =  billAmt + remainingAmt;
				
				double actualBillAmount = 0;
				try{
					actualBillAmount = Double.parseDouble(info.getOpeningbalanceamount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				
				double adjustRemovalAmt = 0;
				if(remainingAmt > (actualBillAmount - billAmt)){
					adjustRemovalAmt = remainingAmt - (actualBillAmount - billAmt);
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(adjustRemovalAmt));
					receivedBillAmount.set(rowIndex, actualBillAmount);
					info.setRemainingopeningbalanceamount(String.valueOf(actualBillAmount));
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+actualBillAmount));
					if(actualBillAmount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckBCalculationOpeningBal(info, rowIndex, columnIndex, false);											
						return;
					}else{
						info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-actualBillAmount));
					}
					
				}else{
					adjustRemovalAmt = remainingAmt;
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
					receivedBillAmount.set(rowIndex, adjustRemovalAmt);
					info.setRemainingopeningbalanceamount(String.valueOf(totalBillAdjustment));
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+remainingAmt));
					if(adjustRemovalAmt > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckBCalculationOpeningBal(info, rowIndex, columnIndex, false);											
						return;
					}else{
						info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-adjustRemovalAmt));
					}
				}
				
				
				OldRemainingAmount.set(rowIndex, billAmt);
				//info.setRemainingopeningbalanceamount(String.valueOf(totalBillAdjustment));
				
				
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getOpeningbalanceamount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else if(remainingAmt >= actualBillAmount){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
				
			}else{

				double val2 = 0;
				try{
					val2 = Double.parseDouble(info.getRemainingopeningbalanceamount());
				}catch(Exception exp){LOGGER.error(exp);}

				double total = recordedAmount + val2;
				info.setRemainingopeningbalanceamount(String.valueOf(total));
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getOpeningbalanceamount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}

				double totalAdjust = 0;
				try{
					totalAdjust = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText());
				}catch(Exception exp){ LOGGER.error(exp); }
				totalAdjust = totalAdjust + recordedAmount;
				
				receivedBillAmount.set(rowIndex, recordedAmount);
				OldRemainingAmount.set(rowIndex, billAmt);
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(totalAdjust));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-totalAdjust));
				if(recordedAmount > oldReceivedAmt){
					JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
					isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
					unCheckBCalculationOpeningBal(info, rowIndex, columnIndex, false);											
					return;
				}else{
					info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-recordedAmount));
				}
				
			}
		}
	}//end
	
	private void unCheckBCalculationOpeningBal(OpeningBalanceInfo info, int rowIndex, int columnIndex, Boolean isChecked){

		double receivedAmount =  receivedBillAmount.get(rowIndex);
		double billAmt = 0;

		try{
			billAmt = Double.parseDouble(info.getRemainingopeningbalanceamount());
		}catch(Exception e){
			LOGGER.error(e);
		}

		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){
			paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText("0.0");
		}else{
			double val = 0;
			try{
				val = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText());
			}catch(Exception e){LOGGER.error(e);}

			paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(val-receivedAmount));
			info.setRemainingopeningbalanceamount(String.valueOf(OldRemainingAmount.get(rowIndex)));

			double val2=0;
			try{
				val2 = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText());
			}catch(Exception e){
				LOGGER.error(e);
			}
			
			paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(val2+receivedAmount));
			info.setStatus(OldBstatus.get(rowIndex));
			receivedBillAmount.set(rowIndex, 0.0);
			
			info.setReceivedopeningbalanceamount(String.valueOf(OldReceivingAmount.get(rowIndex)));
		}
	
	}//end
	
	public void calcWithoutBillAmtAdjustment(int rowIndex, int columnIndex, Boolean isChecked){

		double amount = 0;
		try{ 
			amount  = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldPayment().getText());
		}catch(Exception e){ amount = 0; LOGGER.error(e); }
		
		if((amount ==0 || amount<0)){
			JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Payment is null hence Can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
			isAdjustRemove.set(rowIndex, Boolean.FALSE);
			return ;
		}

		InvoiceInfo invoiceInfo  = InvoiceInfo.builder().build();
		OpeningBalanceInfo openingBalanceInfo = OpeningBalanceInfo.builder().build();
		if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID() != null){
			invoiceInfo = paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();
		}
		
		if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID() != null){
			openingBalanceInfo = paymentInvoiceMappingInfos.get(rowIndex).getOpenigBalanceInfo();
		}
		
		double invoiceRemainigBal = 0;
		double openingRemainingBal = 0;
		try{
			invoiceRemainigBal = Double.parseDouble(invoiceInfo.getRemainingWithoutBillAmount());
		}catch(Exception exp){LOGGER.error(exp);}
		
		try{
			openingRemainingBal = Double.parseDouble(openingBalanceInfo.getRemainingopeningbalanceamount());
		}catch(Exception exp){LOGGER.error(exp);}

		if(isChecked){
			InvoiceInfo info =  paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();

			if(paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID()!=null){
				checkWithoutBillOpeningBalanceCalculation(openingBalanceInfo, rowIndex, columnIndex, isChecked, amount);
			}else if(paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID()!=null){
				checkWithoutBillCalculation(info, rowIndex, columnIndex, isChecked, amount);
			}
		}else if(!isChecked){
			InvoiceInfo info =  paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfo();

			if(type.equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name()) && paymentInvoiceMappingInfos.get(rowIndex).getOpeningBalanceID()!=null){
				unCheckWithoutBillOpeningBalCalculation(openingBalanceInfo, rowIndex, columnIndex, isChecked, amount);
			}else if(type.equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name()) && paymentInvoiceMappingInfos.get(rowIndex).getInvoiceInfoID()!=null){
				unCheckWithoutBillCalculation(info, rowIndex, columnIndex, isChecked, amount);
			}

		}// end if
	
	}//end method
	
	public void checkWithoutBillCalculation(InvoiceInfo info, int rowIndex, int columnIndex, Boolean isChecked, double amount){

		double billAmt = 0;
		try{
			billAmt = Double.parseDouble(info.getRemainingWithoutBillAmount());
		}catch(Exception e){ LOGGER.error(e); }

		double recordedAmount = 0;
		try{
			recordedAmount = Double.parseDouble(paymentInvoiceMappingInfos.get(rowIndex).getAmount());
		}catch(Exception e){ LOGGER.error(e); }
		
		double oldReceivedAmt = 0;
		try{
			oldReceivedAmt = Double.parseDouble(info.getPaidWithoutBillAmount());
		}catch(Exception e){ LOGGER.error(e); }
		OldReceivingAmount.set(rowIndex, oldReceivedAmt);
		
		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){
			if(recordedAmount > 0){
				double totalBillAdjustment =  billAmt + amount;
				receivedBillAmount.set(rowIndex, recordedAmount);
				OldRemainingAmount.set(rowIndex, billAmt);
				OldReceivingAmount.set(rowIndex, oldReceivedAmt);
				
				info.setRemainingWithoutBillAmount(String.valueOf(recordedAmount+billAmt));
				info.setPaidWithoutBillAmount(String.valueOf(OldReceivingAmount.get(rowIndex) - recordedAmount));
				
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(recordedAmount));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-recordedAmount));
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getWpaidstatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getWithoutBillAmount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setWpaidstatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setWpaidstatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
			}else {
				double actualBillAmount = 0;
				try{
					actualBillAmount = Double.parseDouble(info.getWithoutBillAmount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				double val2 = 0;
				try{
					val2 = Double.parseDouble(info.getRemainingWithoutBillAmount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				double removeAdjustAmt = 0;
				if( (amount+val2) > actualBillAmount){
					removeAdjustAmt = actualBillAmount - val2;
				}else{
					removeAdjustAmt = amount + val2;
				}
				info.setRemainingWithoutBillAmount(String.valueOf(removeAdjustAmt));
				if(amount > actualBillAmount){
					receivedBillAmount.set(rowIndex, actualBillAmount);
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(actualBillAmount));
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-actualBillAmount));
					
					if(actualBillAmount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckWithoutBillCalculation(info, rowIndex, columnIndex, false, amount);											
						return;
					}else{
						info.setPaidWithoutBillAmount(String.valueOf(oldReceivedAmt-actualBillAmount));
					}
					
				}else{
					receivedBillAmount.set(rowIndex, amount);
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(amount));
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
					if(amount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE);
						unCheckWithoutBillCalculation(info, rowIndex, columnIndex, isChecked, amount);
						return;
					}else{
						info.setPaidWithoutBillAmount(String.valueOf(oldReceivedAmt-amount));
					}
				}
				OldRemainingAmount.set(rowIndex, billAmt);
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getWpaidstatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getWithoutBillAmount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setWpaidstatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else if(actualBillAmt < amount){
						info.setWpaidstatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setWpaidstatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
			}
		}else{
			if(recordedAmount == 0){
				String adjustedAmountVal = paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText();
				String remainingAmtVal = paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText();
				double remainingAmt = 0;
				try{
					remainingAmt = Double.parseDouble(remainingAmtVal);
				}catch(Exception e){ LOGGER.error(e); }

				if(Strings.isNullOrEmpty(remainingAmtVal) ||  remainingAmt == 0){
					JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Left ZERO so can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
					isAdjustRemove.set(rowIndex, Boolean.FALSE);
					return ;
				}
				double adjustedAmount = 0;
				try{
					adjustedAmount = Double.parseDouble(adjustedAmountVal);
				}catch(Exception e){ LOGGER.error(e); }
				double totalBillAdjustment =  billAmt + remainingAmt;
				
				double actualBillAmount = 0;
				try{
					actualBillAmount = Double.parseDouble(info.getWithoutBillAmount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				
				double adjustRemovalAmt = 0;
				if(remainingAmt > (actualBillAmount - billAmt)){
					adjustRemovalAmt = remainingAmt - (actualBillAmount - billAmt);
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(adjustRemovalAmt));
					receivedBillAmount.set(rowIndex, actualBillAmount);
					info.setRemainingWithoutBillAmount(String.valueOf(actualBillAmount));
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+actualBillAmount));
					if(actualBillAmount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckWithoutBillCalculation(info, rowIndex, columnIndex, false, amount);											
						return;
					}else{
						info.setPaidWithoutBillAmount(String.valueOf(oldReceivedAmt-actualBillAmount));
					}
				}else{
					adjustRemovalAmt = remainingAmt;
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
					receivedBillAmount.set(rowIndex, adjustRemovalAmt);
					info.setRemainingWithoutBillAmount(String.valueOf(totalBillAdjustment));
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+remainingAmt));
					
					if(adjustRemovalAmt > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckWithoutBillCalculation(info, rowIndex, columnIndex, false, amount);											
						return;
					}else{
						info.setPaidWithoutBillAmount(String.valueOf(oldReceivedAmt-adjustRemovalAmt));
					}
					
				}
				
				OldRemainingAmount.set(rowIndex, billAmt);

				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getWpaidstatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getWithoutBillAmount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setWpaidstatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else if(remainingAmt >= actualBillAmount){
						info.setWpaidstatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setWpaidstatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
				
			}else{

				double val2 = 0;
				try{
					val2 = Double.parseDouble(info.getRemainingWithoutBillAmount());
				}catch(Exception exp){LOGGER.error(exp);}

				double total = recordedAmount + val2;
				info.setRemainingWithoutBillAmount(String.valueOf(total));
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getWpaidstatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getWithoutBillAmount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setWpaidstatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setWpaidstatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}

				double totalAdjust = 0;
				try{
					totalAdjust = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText());
				}catch(Exception exp){ LOGGER.error(exp); }
				totalAdjust = totalAdjust + recordedAmount;
				
				receivedBillAmount.set(rowIndex, recordedAmount);
				OldRemainingAmount.set(rowIndex, billAmt);
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(totalAdjust));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-totalAdjust));
				
				if(recordedAmount > oldReceivedAmt){
					JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
					isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
					unCheckWithoutBillCalculation(info, rowIndex, columnIndex, false, amount);											
					return;
				}else{
					info.setPaidWithoutBillAmount(String.valueOf(oldReceivedAmt-recordedAmount));
				}
			}
		}
	
	}
	
	public void checkWithoutBillOpeningBalanceCalculation(OpeningBalanceInfo info, int rowIndex, int columnIndex, Boolean isChecked, double amount){

		double billAmt = 0;
		try{
			billAmt = Double.parseDouble(info.getRemainingopeningbalanceamount());
		}catch(Exception e){ LOGGER.error(e); }

		double recordedAmount = 0;
		try{
			recordedAmount = Double.parseDouble(paymentInvoiceMappingInfos.get(rowIndex).getAmount());
		}catch(Exception e){ LOGGER.error(e); }
		
		double oldReceivedAmt = 0;
		try{
			oldReceivedAmt = Double.parseDouble(info.getReceivedopeningbalanceamount());
		}catch(Exception e){ LOGGER.error(e); }
		OldReceivingAmount.set(rowIndex, oldReceivedAmt);
		
		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){
			if(recordedAmount > 0){
				double totalBillAdjustment =  billAmt + amount;
				receivedBillAmount.set(rowIndex, recordedAmount);
				OldRemainingAmount.set(rowIndex, billAmt);
				OldReceivingAmount.set(rowIndex, oldReceivedAmt);
				
				info.setRemainingopeningbalanceamount(String.valueOf(recordedAmount+billAmt));
				info.setReceivedopeningbalanceamount(String.valueOf(OldReceivingAmount.get(rowIndex) - recordedAmount));
				
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(recordedAmount));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-recordedAmount));
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getOpeningbalanceamount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
			}else {
				double actualBillAmount = 0;
				try{
					actualBillAmount = Double.parseDouble(info.getOpeningbalanceamount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				double val2 = 0;
				try{
					val2 = Double.parseDouble(info.getRemainingopeningbalanceamount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				double removeAdjustAmt = 0;
				if( (amount+val2) > actualBillAmount){
					removeAdjustAmt = actualBillAmount - val2;
				}else{
					removeAdjustAmt = amount + val2;
				}
				info.setRemainingopeningbalanceamount(String.valueOf(removeAdjustAmt));
				if(amount > actualBillAmount){
					receivedBillAmount.set(rowIndex, actualBillAmount);
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(actualBillAmount));
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-actualBillAmount));
					if(actualBillAmount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckWithoutBillOpeningBalCalculation(info, rowIndex, columnIndex, false, amount);											
						return;
					}else{
						info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-actualBillAmount));
					}
					
				}else{
					receivedBillAmount.set(rowIndex, amount);
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(amount));
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
					if(amount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckWithoutBillOpeningBalCalculation(info, rowIndex, columnIndex, false, amount);											
						return;
					}else{
						info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-amount));
					}
				}
				OldRemainingAmount.set(rowIndex, billAmt);
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getOpeningbalanceamount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else if(actualBillAmt < amount){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
				
			}
		}else{
			if(recordedAmount == 0){
				String adjustedAmountVal = paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText();
				String remainingAmtVal = paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText();
				double remainingAmt = 0;
				try{
					remainingAmt = Double.parseDouble(remainingAmtVal);
				}catch(Exception e){ LOGGER.error(e); }

				if(Strings.isNullOrEmpty(remainingAmtVal) ||  remainingAmt == 0){
					JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Left ZERO so can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
					isAdjustRemove.set(rowIndex, Boolean.FALSE);
					return ;
				}
				double adjustedAmount = 0;
				try{
					adjustedAmount = Double.parseDouble(adjustedAmountVal);
				}catch(Exception e){ LOGGER.error(e); }
				double totalBillAdjustment =  billAmt + remainingAmt;
				
				double actualBillAmount = 0;
				try{
					actualBillAmount = Double.parseDouble(info.getOpeningbalanceamount());
				}catch(Exception exp){LOGGER.error(exp);}
				
				
				double adjustRemovalAmt = 0;
				if(remainingAmt > (actualBillAmount - billAmt)){
					adjustRemovalAmt = remainingAmt - (actualBillAmount - billAmt);
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(adjustRemovalAmt));
					receivedBillAmount.set(rowIndex, actualBillAmount);
					info.setRemainingopeningbalanceamount(String.valueOf(actualBillAmount));
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+actualBillAmount));
					if(actualBillAmount > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckWithoutBillOpeningBalCalculation(info, rowIndex, columnIndex, false, amount);																						
						return;
					}else{
						info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-actualBillAmount));
					}
					
				}else{
					adjustRemovalAmt = remainingAmt;
					paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
					receivedBillAmount.set(rowIndex, adjustRemovalAmt);
					info.setRemainingopeningbalanceamount(String.valueOf(totalBillAdjustment));
					paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+remainingAmt));
					if(adjustRemovalAmt > oldReceivedAmt){
						JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
						isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
						unCheckWithoutBillOpeningBalCalculation(info, rowIndex, columnIndex, false, amount);											
						return;
					}else{
						info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-adjustRemovalAmt));
					}
				}
				
				
				OldRemainingAmount.set(rowIndex, billAmt);
				//info.setRemainingopeningbalanceamount(String.valueOf(totalBillAdjustment));
				
				
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getOpeningbalanceamount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else if(remainingAmt >= actualBillAmount){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}
				
			}else{

				double val2 = 0;
				try{
					val2 = Double.parseDouble(info.getRemainingopeningbalanceamount());
				}catch(Exception exp){LOGGER.error(exp);}

				double total = recordedAmount + val2;
				info.setRemainingopeningbalanceamount(String.valueOf(total));
				if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(info.getStatus()) ){
					double actualBillAmt = 0;
					try{
						actualBillAmt = Double.parseDouble(info.getOpeningbalanceamount());
					}catch(Exception e){ LOGGER.error(e); }
					if(recordedAmount == actualBillAmt){
						info.setStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name());
					}else{
						info.setStatus(InvoicePaymentStatusEnum.PARTIAL_PAID.name());
					}
				}

				double totalAdjust = 0;
				try{
					totalAdjust = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText());
				}catch(Exception exp){ LOGGER.error(exp); }
				totalAdjust = totalAdjust + recordedAmount;
				
				receivedBillAmount.set(rowIndex, recordedAmount);
				OldRemainingAmount.set(rowIndex, billAmt);
				paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(totalAdjust));
				paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(amount-totalAdjust));
				if(recordedAmount > oldReceivedAmt){
					JOptionPane.showMessageDialog(paymentAdjustmentDeleteUI, "Wrong adjustment removal.","Leamon-ERP Error", JOptionPane.ERROR_MESSAGE);
					isAdjustRemove.set(rowIndex, Boolean.FALSE.booleanValue());
					unCheckWithoutBillOpeningBalCalculation(info, rowIndex, columnIndex, false, amount);											
					return;
				}else{
					info.setReceivedopeningbalanceamount(String.valueOf(oldReceivedAmt-recordedAmount));
				}
				
			}
		}
	}
	
	public void unCheckWithoutBillCalculation(InvoiceInfo info, int rowIndex, int columnIndex, Boolean isChecked, double amount){

		double receivedAmount =  receivedBillAmount.get(rowIndex);
		double billAmt = 0;

		try{
			billAmt = Double.parseDouble(info.getRemainingWithoutBillAmount()); }catch(Exception e){ LOGGER.error(e); }

		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){
			paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText("0.0");
		}else{
			double val = 0;
			try{
				val = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText());
			}catch(Exception e){LOGGER.error(e);}

			paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(val-receivedAmount));
			info.setRemainingWithoutBillAmount(String.valueOf(OldRemainingAmount.get(rowIndex)));

			double val2=0;
			try{
				val2 = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText());
			}catch(Exception e){
				LOGGER.error(e);
			}
			
			paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(val2+receivedAmount));
			info.setWpaidstatus(OldBstatus.get(rowIndex));
			receivedBillAmount.set(rowIndex, 0.0);
			
			info.setPaidWithoutBillAmount(String.valueOf(OldReceivingAmount.get(rowIndex)));
		}
	
	}
	public void unCheckWithoutBillOpeningBalCalculation(OpeningBalanceInfo info, int rowIndex, int columnIndex, Boolean isChecked, double amount){


		double receivedAmount =  receivedBillAmount.get(rowIndex);
		double billAmt = 0;

		try{
			billAmt = Double.parseDouble(info.getRemainingopeningbalanceamount());
		}catch(Exception e){
			LOGGER.error(e);
		}

		if(Strings.isNullOrEmpty(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText())){
			paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText("0.0");
		}else{
			double val = 0;
			try{
				val = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldAdjAmt().getText());
			}catch(Exception e){LOGGER.error(e);}

			paymentAdjustmentDeleteUI.getTextFieldAdjAmt().setText(String.valueOf(val-receivedAmount));
			info.setRemainingopeningbalanceamount(String.valueOf(OldRemainingAmount.get(rowIndex)));

			double val2=0;
			try{
				val2 = Double.parseDouble(paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().getText());
			}catch(Exception e){
				LOGGER.error(e);
			}
			
			paymentAdjustmentDeleteUI.getTextFieldRemainingAmt().setText(String.valueOf(val2+receivedAmount));
			info.setStatus(OldBstatus.get(rowIndex));
			receivedBillAmount.set(rowIndex, 0.0);
			
			info.setReceivedopeningbalanceamount(String.valueOf(OldReceivingAmount.get(rowIndex)));
		}
	}
}
