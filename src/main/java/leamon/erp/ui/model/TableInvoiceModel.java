package leamon.erp.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableInvoiceModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TableInvoiceModel.class);
	private static final String CLASS_NAME = "TableInvoiceModel";
	
	private final String [] columnName = new String[] {LeamonERPConstants.TABLE_HEADER_SNO, 
			LeamonERPConstants.TABLE_HEADER_INVOICE_DESC, LeamonERPConstants.TABLE_HEADER_INVOICE_SIZE, 
			LeamonERPConstants.TABLE_HEADER_INVOICE_QTY,  LeamonERPConstants.TABLE_HEADER_INVOICE_UNIT, 
			LeamonERPConstants.TABLE_HEADER_INVOICE_RATE, 
			LeamonERPConstants.TABLE_HEADER_INVOICE_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_INVOICE_TD};
	
	private List<InvoiceItemInfo> invoiceItemInfos;
	
	public TableInvoiceModel(List<InvoiceItemInfo> invoiceItemInfos){
		if(invoiceItemInfos == null){
			invoiceItemInfos = new ArrayList<InvoiceItemInfo>();
		}
		this.invoiceItemInfos = invoiceItemInfos;
	}
	
	@Override
	public int getRowCount() {
		if(invoiceItemInfos == null){
			return 0;
		}
		
		return invoiceItemInfos.size();
	}
	
	@Override
	public int getColumnCount() {
		return columnName.length;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		final String METHOD_NAME = "getValueAt";
		//LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		Object temp = null;
		switch (columnIndex) {
			case 0: temp = invoiceItemInfos.get(rowIndex).getSno(); break;
			case 1: temp = invoiceItemInfos.get(rowIndex).getDescription(); break;
			case 2: temp = invoiceItemInfos.get(rowIndex).getSize(); break;
			case 3: temp = invoiceItemInfos.get(rowIndex).getQty();break;
			case 4: temp = invoiceItemInfos.get(rowIndex).getUnit();break;
			case 5: temp = invoiceItemInfos.get(rowIndex).getRate(); break;
			case 6: temp = invoiceItemInfos.get(rowIndex).getAmount(); break;
			case 7: temp = invoiceItemInfos.get(rowIndex).getTd(); break;
		}
		/*if(temp!=null){
			LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]")+" temp["+temp+"]");
		}*/
		//LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
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
			case 4: return Double.class;
			case 5: return String.class;
			case 6: return String.class;
			case 7: return String.class;
			default : return Object.class;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
		InvoiceItemInfo info  = null;
		if(null != invoiceItemInfos && invoiceItemInfos.size()<=rowIndex){
			info = invoiceItemInfos.get(rowIndex);
		}
		
		if(info == null){
			return;
		}
		
		switch (columnIndex) {
			case 0:
					info.setSno((String)aValue);
				break;
			case 1:
					info.setDescription((String)aValue);
				break;
			case 2:
				info.setSize((String)aValue);
				break;
			case 3:
				info.setQty((String)aValue);
				break;
			case 4:
				info.setUnit((String)aValue);
				break;
			case 5:
				info.setRate((String)aValue);
				break;
			case 6:
				info.setAmount((String)aValue);
				break;
			case 7:
				info.setTd((String)aValue);
				break;
		}
	}
	
	public void addRow(InvoiceItemInfo info){
		if(null == info ){
			invoiceItemInfos = new ArrayList<InvoiceItemInfo>();
		}
		invoiceItemInfos.add(info);
		fireTableRowsInserted(invoiceItemInfos.size() - 1, invoiceItemInfos.size() - 1);
	}
}
