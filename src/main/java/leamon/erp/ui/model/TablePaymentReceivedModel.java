package leamon.erp.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.IStabilityClassifier;

import com.google.common.base.Strings;

import leamon.erp.model.InventoryInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.ui.PaymentUI;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.LeamonUtil;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.data.cache.BooleanStore;

@Getter
@Setter
public class TablePaymentReceivedModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TablePaymentReceivedModel.class);
	private static final String CLASS_NAME = "TablePaymentReceivedModel";

	Integer sno = 0;
	private final String [] columnName = new String[] {LeamonERPConstants.TABLE_HEADER_SNO,
			LeamonERPConstants.TABLE_HEADER_INVOICE_NO,
			LeamonERPConstants.TABLE_HEADER_BILLING_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_PACKING_AMOUNT,
			"B. Adjustment", "W. Adjustment", 
			"Recv. B.Payment" , "Rem. B.Payment",
			"Recv. W.Payment" , "Rem. W.Payment"
	};



	private List<InvoiceInfo> invoiceInfos;
	private List<Integer> serialNumbers;
	private PaymentUI paymentUI;

	private List<Boolean> isBAmount = new ArrayList<Boolean>();
	private List<Boolean> isWAmount = new ArrayList<Boolean>();
	

	private List<Double> receivedBillAmount = new ArrayList<Double>();
	private List<Double> reminingBillinigBalance = new ArrayList<Double>();
	
	private List<Double> receivedWithOutBillAmount = new ArrayList<Double>();
	private List<Double> reminingWithOutBillBalance = new ArrayList<Double>();
	
	public TablePaymentReceivedModel(List<InvoiceInfo> invoiceInfos){
		if(invoiceInfos == null){
			invoiceInfos = new ArrayList<InvoiceInfo>();
		}
		this.invoiceInfos = invoiceInfos;
		for(int i=0; i< invoiceInfos.size(); i++){
			isBAmount.add(Boolean.FALSE);
			isWAmount.add(Boolean.FALSE);
			receivedBillAmount.add(Double.parseDouble(String.valueOf("0.0")));
			reminingBillinigBalance.add(Double.parseDouble(String.valueOf("0.0")));
			
			receivedWithOutBillAmount.add(Double.parseDouble(String.valueOf("0.0")));
			reminingWithOutBillBalance.add(Double.parseDouble(String.valueOf("0.0")));
		}
	}

	public TablePaymentReceivedModel(GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo> genericModelWithSnp){
		this.invoiceInfos  = genericModelWithSnp.getOb();
		this.serialNumbers = genericModelWithSnp.getSno();

		for(int i=0; i< invoiceInfos.size(); i++){
			isBAmount.add(Boolean.FALSE);
			isWAmount.add(Boolean.FALSE);
			
			receivedBillAmount.add(Double.parseDouble(String.valueOf("0.0")));
			reminingBillinigBalance.add(Double.parseDouble(String.valueOf("0.0")));
			
			receivedWithOutBillAmount.add(Double.parseDouble(String.valueOf("0.0")));
			reminingWithOutBillBalance.add(Double.parseDouble(String.valueOf("0.0")));
		}
	}
	
	public TablePaymentReceivedModel(GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo> genericModelWithSnp, PaymentUI paymentUI){
		this.invoiceInfos  = genericModelWithSnp.getOb();
		this.serialNumbers = genericModelWithSnp.getSno();
		this.paymentUI = paymentUI;

		for(int i=0; i< invoiceInfos.size(); i++){
			isBAmount.add(Boolean.FALSE);
			isWAmount.add(Boolean.FALSE);
			
			receivedBillAmount.add(Double.parseDouble(String.valueOf("0.0")));
			reminingBillinigBalance.add(Double.parseDouble(String.valueOf("0.0")));
			
			receivedWithOutBillAmount.add(Double.parseDouble(String.valueOf("0.0")));
			reminingWithOutBillBalance.add(Double.parseDouble(String.valueOf("0.0")));
		}
	}

	@Override
	public int getRowCount() {
		if(invoiceInfos == null){
			return 0;
		}

		return invoiceInfos.size();
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
		case 0: temp = serialNumbers.get(rowIndex); break;
		case 1: temp = invoiceInfos.get(rowIndex).getInvoicNum();break;
		case 2: if(Strings.isNullOrEmpty(invoiceInfos.get(rowIndex).getBillAmount())){
			temp = "N/A";
		}else{
			temp = invoiceInfos.get(rowIndex).getBillAmount();
		}
		break;
		case 3:
			String packingAmt = "error";
			try{
				packingAmt = LeamonUtil.calcPackingAmount(invoiceInfos.get(rowIndex));
				invoiceInfos.get(rowIndex).setPackingAmount(packingAmt);
			}catch(Exception e){
				packingAmt = e.getMessage();
				LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+e);
				packingAmt = "N/A";
			}
			temp = packingAmt; 
			break;
		case 4: 
			if(!isBAmount.isEmpty() && rowIndex < isBAmount.size()){ 
				temp =  isBAmount.get(rowIndex);
			}else{ 
				temp = Boolean.FALSE; 
			}break;
		case 5: 
			if(!isBAmount.isEmpty() && rowIndex < isBAmount.size()){ 
				temp = isWAmount.get(rowIndex); 
			}else{ 
				temp = Boolean.FALSE; 
			}break;
		case 6:  
			if(!receivedBillAmount.isEmpty() && rowIndex < receivedBillAmount.size()){ 
				temp = receivedBillAmount.get(rowIndex); 
			}else{ 
				temp = Double.parseDouble(String.valueOf("0.0")); 
			}break;
		case 7:
			if(!reminingBillinigBalance.isEmpty() && rowIndex < reminingBillinigBalance.size()){ 
				temp = reminingBillinigBalance.get(rowIndex); 
			}else{ 
				temp = Double.parseDouble(String.valueOf("0.0")); 
			}break;
		case 8:
			if(!receivedWithOutBillAmount.isEmpty() && rowIndex < receivedWithOutBillAmount.size()){ 
				temp = receivedWithOutBillAmount.get(rowIndex); 
			}else{ 
				temp = Double.parseDouble(String.valueOf("0.0")); 
			}break;
		case 9:
			if(!reminingWithOutBillBalance.isEmpty() && rowIndex < reminingWithOutBillBalance.size()){ 
				temp = reminingWithOutBillBalance.get(rowIndex); 
			}else{ 
				temp = Double.parseDouble(String.valueOf("0.0")); 
			}break;
		default : temp = new Object();
		}

		return temp;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 4:
				isBAmount.set(rowIndex, (Boolean)aValue);
				if(null != aValue && aValue instanceof Boolean && aValue == Boolean.TRUE){
					calcBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}else if(null != aValue && aValue instanceof Boolean && aValue == Boolean.FALSE){
					calcBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}
				break;
			case 5:
				isWAmount.set(rowIndex, (Boolean)aValue);
				if(null != aValue && aValue instanceof Boolean && aValue == Boolean.TRUE){
					calcWithoutBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}else if(null != aValue && aValue instanceof Boolean && aValue == Boolean.FALSE){
					calcWithoutBillAmtAdjustment(rowIndex, columnIndex, (Boolean)aValue);
				}
				break;
		}
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
		case 4: return Boolean.class;
		case 5: return Boolean.class;
		default : 
			return Object.class;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(columnIndex == 4 || columnIndex ==5 || columnIndex ==6){
			return true;
		}else
			return super.isCellEditable(rowIndex, columnIndex);
	}
	
	private void calcBillAmtAdjustment(int rowIndex, int columnIndex, Boolean isChecked){
		double amount = 0;
		try{
			amount  = Double.parseDouble(paymentUI.getTextFieldPayment().getText());
		}catch(Exception e){
			amount = 0;
			LOGGER.error(e);
		}
		if((amount ==0 || amount<0)){
			JOptionPane.showMessageDialog(paymentUI, "Payment is null hence Can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
			isBAmount.set(rowIndex, Boolean.FALSE);
			return ;
		}
		
		if(Strings.isNullOrEmpty(invoiceInfos.get(rowIndex).getBillAmount())){
			JOptionPane.showMessageDialog(paymentUI, "Billing amount is N/A hence Can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
			isBAmount.set(rowIndex, Boolean.FALSE);
			return ;
		}
		
		if(isChecked){
			InvoiceInfo info =  invoiceInfos.get(rowIndex);
			double billAmt = 0;
			try{
				billAmt = Double.parseDouble(info.getBillAmount());
			}catch(Exception e){ LOGGER.error(e); }
			
			if(Strings.isNullOrEmpty(paymentUI.getTextFieldAdjAmt().getText())){
				
				if(billAmt > amount){
					double totalBillAdjustment =  billAmt - amount;
					receivedBillAmount.set(rowIndex, amount);
					reminingBillinigBalance.set(rowIndex, totalBillAdjustment);
					
					paymentUI.getTextFieldAdjAmt().setText(String.valueOf(amount));
					paymentUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
				}else{
					double remainingBal =    amount - billAmt;
					receivedBillAmount.set(rowIndex, billAmt);
					reminingBillinigBalance.set(rowIndex, 0.0);
					paymentUI.getTextFieldAdjAmt().setText(String.valueOf(billAmt));
					paymentUI.getTextFieldRemainingAmt().setText(String.valueOf(remainingBal));
				}
			}else{
				String adjustedAmountVal = paymentUI.getTextFieldAdjAmt().getText();
				String remainingAmtVal = paymentUI.getTextFieldRemainingAmt().getText();
				
				double remainingAmt = 0;
				try{
					remainingAmt = Double.parseDouble(remainingAmtVal);
				}catch(Exception e){ LOGGER.error(e); }
				
				if(Strings.isNullOrEmpty(remainingAmtVal) ||  remainingAmt == 0){
					JOptionPane.showMessageDialog(paymentUI, "Left ZERO so can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
					isBAmount.set(rowIndex, Boolean.FALSE);
					return ;
				}
				
				double adjustedAmount = 0;
				try{
					adjustedAmount = Double.parseDouble(adjustedAmountVal);
				}catch(Exception e){ LOGGER.error(e); }
				
				if(billAmt > remainingAmt){
					double totalBillAdjustment =  billAmt - remainingAmt;
					receivedBillAmount.set(rowIndex, remainingAmt);
					reminingBillinigBalance.set(rowIndex, totalBillAdjustment);
					
					paymentUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+remainingAmt));
					paymentUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
				}else{
					double remainingBal =    remainingAmt - billAmt;
					receivedBillAmount.set(rowIndex, billAmt);
					reminingBillinigBalance.set(rowIndex, 0.0);
					paymentUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+billAmt));
					paymentUI.getTextFieldRemainingAmt().setText(String.valueOf(remainingBal));
				}
			}
			
			
		}else if(!isChecked){
			InvoiceInfo info =  invoiceInfos.get(rowIndex);
			double receivedAmount =  receivedBillAmount.get(rowIndex);
			double billAmt = 0;
			
			try{
				billAmt = Double.parseDouble(info.getBillAmount());
			}catch(Exception e){
				LOGGER.error(e);
			}
			
			if(Strings.isNullOrEmpty(paymentUI.getTextFieldAdjAmt().getText())){
				paymentUI.getTextFieldAdjAmt().setText("0.0");
			}else{
				double val = 0;
				try{
					val = Double.parseDouble(paymentUI.getTextFieldAdjAmt().getText());
				}catch(Exception e){LOGGER.error(e);}
				
				paymentUI.getTextFieldAdjAmt().setText(String.valueOf(val-receivedAmount));
				receivedBillAmount.set(rowIndex, 0.0);
				reminingBillinigBalance.set(rowIndex, 0.0);
				
				double val2=0;
				try{
					val2 = Double.parseDouble(paymentUI.getTextFieldRemainingAmt().getText());
				}catch(Exception e){
					LOGGER.error(e);
				}
				paymentUI.getTextFieldRemainingAmt().setText(String.valueOf(val2+receivedAmount));
			}
			
			
		}// end if
	}
	
	public void calcWithoutBillAmtAdjustment(int rowIndex, int columnIndex, Boolean isChecked){
		double amount = 0;
		try{
			amount  = Double.parseDouble(paymentUI.getTextFieldPayment().getText());
		}catch(Exception e){
			amount = 0;
			LOGGER.error(e);
		}
		if((amount ==0 || amount<0)){
			JOptionPane.showMessageDialog(paymentUI, "Payment is null hence Can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
			isWAmount.set(rowIndex, Boolean.FALSE);
			return ;
		}

		if(Strings.isNullOrEmpty(invoiceInfos.get(rowIndex).getPackingAmount())){
			JOptionPane.showMessageDialog(paymentUI, "W amount is N/A hence Can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
			isWAmount.set(rowIndex, Boolean.FALSE);
			return ;
		}

		if(isChecked){
			InvoiceInfo info =  invoiceInfos.get(rowIndex);
			double billAmt = 0;
			try{
				billAmt = Double.parseDouble(info.getPackingAmount());
			}catch(Exception e){ LOGGER.error(e); }

			if(Strings.isNullOrEmpty(paymentUI.getTextFieldAdjAmt().getText())){

				if(billAmt > amount){
					double totalBillAdjustment =  billAmt - amount;
					receivedWithOutBillAmount.set(rowIndex, amount);
					reminingWithOutBillBalance.set(rowIndex, totalBillAdjustment);

					paymentUI.getTextFieldAdjAmt().setText(String.valueOf(amount));
					paymentUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
				}else{
					double remainingBal =    amount - billAmt;
					receivedWithOutBillAmount.set(rowIndex, billAmt);
					reminingWithOutBillBalance.set(rowIndex, 0.0);
					paymentUI.getTextFieldAdjAmt().setText(String.valueOf(billAmt));
					paymentUI.getTextFieldRemainingAmt().setText(String.valueOf(remainingBal));
				}
			}else{
				String adjustedAmountVal = paymentUI.getTextFieldAdjAmt().getText();
				String remainingAmtVal = paymentUI.getTextFieldRemainingAmt().getText();

				double remainingAmt = 0;
				try{
					remainingAmt = Double.parseDouble(remainingAmtVal);
				}catch(Exception e){ LOGGER.error(e); }

				if(Strings.isNullOrEmpty(remainingAmtVal) ||  remainingAmt == 0){
					JOptionPane.showMessageDialog(paymentUI, "Left ZERO so can't be adjusted ", "Leamon-ERP-Payment", JOptionPane.ERROR_MESSAGE);
					isWAmount.set(rowIndex, Boolean.FALSE);
					return ;
				}

				double adjustedAmount = 0;
				try{
					adjustedAmount = Double.parseDouble(adjustedAmountVal);
				}catch(Exception e){ LOGGER.error(e); }

				if(billAmt > remainingAmt){
					double totalBillAdjustment =  billAmt - remainingAmt;
					receivedWithOutBillAmount.set(rowIndex, remainingAmt);
					reminingWithOutBillBalance.set(rowIndex, totalBillAdjustment);

					paymentUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+remainingAmt));
					paymentUI.getTextFieldRemainingAmt().setText(String.valueOf("0.0"));
				}else{
					double remainingBal =    remainingAmt - billAmt;
					receivedWithOutBillAmount.set(rowIndex, billAmt);
					reminingWithOutBillBalance.set(rowIndex, 0.0);
					paymentUI.getTextFieldAdjAmt().setText(String.valueOf(adjustedAmount+billAmt));
					paymentUI.getTextFieldRemainingAmt().setText(String.valueOf(remainingBal));
				}
			}

		}else if(!isChecked){
			InvoiceInfo info =  invoiceInfos.get(rowIndex);
			double receivedAmount =  receivedWithOutBillAmount.get(rowIndex);
			double billAmt = 0;

			try{
				billAmt = Double.parseDouble(info.getPackingAmount());
			}catch(Exception e){
				LOGGER.error(e);
			}

			if(Strings.isNullOrEmpty(paymentUI.getTextFieldAdjAmt().getText())){
				paymentUI.getTextFieldAdjAmt().setText("0.0");
			}else{
				double val = 0;
				try{
					val = Double.parseDouble(paymentUI.getTextFieldAdjAmt().getText());
				}catch(Exception e){LOGGER.error(e);}

				paymentUI.getTextFieldAdjAmt().setText(String.valueOf(val-receivedAmount));
				receivedWithOutBillAmount.set(rowIndex, 0.0);
				reminingWithOutBillBalance.set(rowIndex, 0.0);

				double val2=0;
				try{
					val2 = Double.parseDouble(paymentUI.getTextFieldRemainingAmt().getText());
				}catch(Exception e){
					LOGGER.error(e);
				}
				paymentUI.getTextFieldRemainingAmt().setText(String.valueOf(val2+receivedAmount));
			}


		}// end if
	}//end method
	
}
