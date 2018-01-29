package leamon.erp.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Manish Kumar Mishra
 * @version 1.1
 * 
 * 
 * Enhanced By : Manish Kumar Mishra on JAN 28,2018
 * To accomodate Release 3.3.2 changes for opening balance
 *
 */
@Getter
@Setter
public class TablePaymentReceivedSummaryModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TablePaymentReceivedSummaryModel.class);
	private static final String CLASS_NAME = "TablePaymentReceivedSummaryModel";

	Integer sno = 0;
	private final String [] columnName = new String[] {
			LeamonERPConstants.TABLE_HEADER_SNO,
			LeamonERPConstants.TABLE_HEADER_PARTY_NAME,
			LeamonERPConstants.TABLE_HEADER_INVOICE_NO,
			LeamonERPConstants.TABLE_HEADER_BILL_NO,
			LeamonERPConstants.TABLE_HEADER_BILL_DATE,
			LeamonERPConstants.TABLE_HEADER_B_AMOUNT, 
			LeamonERPConstants.TABLE_HEADER_W_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_G_TOTAL,
			LeamonERPConstants.TABLE_HEADER_B_STATUS,
			LeamonERPConstants.TABLE_HEADER_W_STATUS,
			LeamonERPConstants.TABLE_HEADER_DESC
	};

	private List<InvoiceInfo> invoiceInfos;
	private List<AccountInfo> partyInfos;
	private List<Integer> serialNumbers;
	
	
	public TablePaymentReceivedSummaryModel(List<InvoiceInfo> invoiceInfos){
		if(invoiceInfos == null){
			invoiceInfos = new ArrayList<InvoiceInfo>();
		}
		this.invoiceInfos = invoiceInfos;
	}
	
	public TablePaymentReceivedSummaryModel(GenericModelWithSnp<List<InvoiceInfo>, 
			InvoiceInfo> genericModelWithSnp){
		this.invoiceInfos  = genericModelWithSnp.getOb();
		this.serialNumbers = genericModelWithSnp.getSno();
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
		case 1:	{
			if(invoiceInfos.get(rowIndex).isOpeningBalance()){
				temp = invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getAccountInfo().getName();
			}else{
				temp = 
						null == invoiceInfos.get(rowIndex).getAccountInfo()
						? "" 
								: invoiceInfos.get(rowIndex).getAccountInfo().getName();
			}
		}break;
		case 2: {
			temp = invoiceInfos.get(rowIndex).getInvoicNum();
		}break;
		case 3: {
			if(invoiceInfos.get(rowIndex).isOpeningBalance()){
				temp = invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getBillnumber();
			}else{
				temp = invoiceInfos.get(rowIndex).getBillNo(); 
			}
		}break;
		case 4: {
			if(invoiceInfos.get(rowIndex).isOpeningBalance()){
				temp = invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getBilldate();
			}else{
				temp = invoiceInfos.get(rowIndex).getInvoicDate(); 
			}
		}break; 
		case 5: {
			if(invoiceInfos.get(rowIndex).isOpeningBalance()){
				if(invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getType()
						.equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					temp = invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getOpeningbalanceamount();
				}
			}else{
				temp = invoiceInfos.get(rowIndex).getBillAmount(); 
			}
		}break; 
		case 6: {
			if(invoiceInfos.get(rowIndex).isOpeningBalance()){
				if(invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getType()
						.equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					temp = invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getOpeningbalanceamount();
				}
			}else{
				temp = invoiceInfos.get(rowIndex).getWithoutBillAmount(); 
			}
		}break;
		case 7:{
			if(invoiceInfos.get(rowIndex).isOpeningBalance()){
				temp = temp = invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getOpeningbalanceamount();
			}else{
				temp = getGT(invoiceInfos.get(rowIndex).getBillAmount(), 
				invoiceInfos.get(rowIndex).getWithoutBillAmount());
			}
		}  
		break;
		case 8 :{
			if(invoiceInfos.get(rowIndex).isOpeningBalance()){
				if(invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getType()
						.equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					temp = invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getStatus();
				}
			}else{
				temp = invoiceInfos.get(rowIndex).getPaidStatus(); 
			}
		} break;
		case 9 : 
			if(invoiceInfos.get(rowIndex).isOpeningBalance()){
				if(invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getType()
						.equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					temp = invoiceInfos.get(rowIndex).getOpenigBalanceInfo().getStatus();
				}
			}else{
				temp = invoiceInfos.get(rowIndex).getWpaidstatus();  
			}break;
		case 10:{
				if(invoiceInfos.get(rowIndex).isOpeningBalance()){
					temp = "Opening Balance";
				}else{
					temp = "Invoice";
				}
			}break;
		default : temp = new Object();
		}

		return temp;
	}

	@Override
	public String getColumnName(int col) { 
		return columnName[col]; 
	} 

	/*@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
		case 0: return Integer.class;
		case 1: return String.class;
		case 2: return String.class;
		case 3: return Boolean.class;
		case 4: return String.class;
		case 5: return String.class;
		default : 
			return Object.class;
		}
	}*/
	
	private double getGT(String bAmount, String wAmount){
		double bAmountVal = 0;
		double wAmountVal = 0;
		try{
			bAmountVal = Double.parseDouble(bAmount);
		}catch(Exception exp){}
		
		try{
			wAmountVal = Double.parseDouble(wAmount);
		}catch(Exception exp){}
		
		return bAmountVal + wAmountVal; 
	}
	
}
