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

import leamon.erp.model.AccountInfo;
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
			LeamonERPConstants.TABLE_HEADER_W_STATUS
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
					
					temp = 
					null == invoiceInfos.get(rowIndex).getAccountInfo()? "" : invoiceInfos.get(rowIndex).getAccountInfo().getName();
				}break;
			case 2: temp = invoiceInfos.get(rowIndex).getInvoicNum(); break;
			case 3: temp = invoiceInfos.get(rowIndex).getBillNo(); break;
			case 4: temp = invoiceInfos.get(rowIndex).getInvoicDate(); break; 
			case 5: temp = invoiceInfos.get(rowIndex).getBillAmount(); break; 
			case 6: temp = invoiceInfos.get(rowIndex).getWithoutBillAmount(); break;
			case 7: temp = getGT(invoiceInfos.get(rowIndex).getBillAmount(), 
					invoiceInfos.get(rowIndex).getWithoutBillAmount()); 
			break;
			case 8 : temp = invoiceInfos.get(rowIndex).getPaidStatus(); break;
			case 9 : temp = invoiceInfos.get(rowIndex).getWpaidstatus(); break;
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
