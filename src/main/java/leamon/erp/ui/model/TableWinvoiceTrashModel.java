package leamon.erp.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.StockItem;
import lombok.Getter;
import lombok.Setter;

/**
 * @Copyright By Leamon India 2017
 * 
 * @author Manish Kumar Mishra
 * @date 03 MAY, 2017
 * @version 1.0
 */
@Getter
@Setter
public class TableWinvoiceTrashModel extends AbstractTableModel{
	
	private static final Logger LOGGER = Logger.getLogger(TableWinvoiceTrashModel.class);
	
	private final String [] columnNames = new String[] {
			"ID", 
			"Party Name", 
			"Invoice Num", 
			"Date",
			"Bill No",
			
			"B Amt",
			"Rec. B Amt.",
			"Rem. B Amt.",
			
			"W Amt",
			"Rec. W Amt",
			"Rem. W Amt",
			
			"B Status",
			"W Status",
			
			"GST Amount",
			
			"Book By",
			"Transport",
			"Packet Num.",
			"Gr Number",
			
			"Col1",
			"Col1 Val",
			
			"Col2",
			"Col2 Val"
			};
	private List<InvoiceInfo> invoiceInfos;
	
	public TableWinvoiceTrashModel(List<InvoiceInfo> invoiceInfos){
		this.invoiceInfos = invoiceInfos;
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
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
		switch (columnIndex) {
		case 0:{
			temp = invoiceInfos.get(rowIndex).getId().toString();
		}break;
		
		case 1:{
			temp = invoiceInfos.get(rowIndex).getAccountInfo().getName();
		}break;
		
		case 2:{
			temp = invoiceInfos.get(rowIndex).getInvoicNum();
		}break;
		
		case 3:{
			temp = invoiceInfos.get(rowIndex).getInvoicDate();
		}break;
		
		case 4:{
			temp = invoiceInfos.get(rowIndex).getBillNo();
		}break;
		
		case 5:{
			temp = invoiceInfos.get(rowIndex).getBillAmount();
		}break;
		
		case 6:{
			temp = invoiceInfos.get(rowIndex).getPaidBillAmount();
		}break;
		
		case 7:{
			temp = invoiceInfos.get(rowIndex).getRemainingBillAmount();
		}break;
		
		case 8:{
			temp = invoiceInfos.get(rowIndex).getWithoutBillAmount();
		}break;
		
		case 9:{
			temp = invoiceInfos.get(rowIndex).getPaidWithoutBillAmount();
		}break;
		
		case 10:{
			temp = invoiceInfos.get(rowIndex).getRemainingWithoutBillAmount();
		}break;
		
		case 11:{
			temp = invoiceInfos.get(rowIndex).getPaidStatus();
		}break;
		
		case 12:{
			temp = invoiceInfos.get(rowIndex).getWpaidstatus();
		}break;
		
		case 13:{
			temp = invoiceInfos.get(rowIndex).getGstValue();
		}break;
		
		case 14:{
			temp = invoiceInfos.get(rowIndex).getOrderBookedBy();
		}break;
		
		case 15:{
			temp = invoiceInfos.get(rowIndex).getTransport();
		}break;
		
		case 16:{
			temp = invoiceInfos.get(rowIndex).getPktNumber();
		}break;
		
		case 17:{
			temp = invoiceInfos.get(rowIndex).getGrBiltyNumber();
		}break;
		
		case 18:{
			temp = invoiceInfos.get(rowIndex).getCol1Name();
		}break;
		
		case 19:{
			temp = invoiceInfos.get(rowIndex).getCol1Val();
		}break;
		
		case 20:{
			temp = invoiceInfos.get(rowIndex).getCol2Name();
		}break;
		
		case 21:{
			temp = invoiceInfos.get(rowIndex).getCol2Val();
		}break;
		
		default :{
			temp = "";
		}
		}
		return temp;
	}
	@Override
	public String getColumnName(int col) { 
	      return columnNames[col]; 
	} 
}
