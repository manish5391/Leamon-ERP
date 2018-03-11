package leamon.erp.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.SalesReportInfoModel;

public class SalesReportModel extends AbstractTableModel{

	private final String [] columnName = new String[] {
			"Invoice No", 
			"Party Name", 
			"Bill Number", 
			"Date", 
			"B Amount",
			"W Amount", 
			"Total"
			};
	
	private List<SalesReportInfoModel> saleReportInfoModel;
	
	public SalesReportModel(List<SalesReportInfoModel> saleReportInfoModel){
		this.saleReportInfoModel = saleReportInfoModel;
	}
	
	@Override
	public String getColumnName(int col) { 
	      return columnName[col]; 
	} 
	
	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		if(saleReportInfoModel == null) {
			return 0;
		}
		return saleReportInfoModel.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
		switch(columnIndex){
		case 0 : temp = saleReportInfoModel.get(rowIndex).getInvoiceNumber();break;
		case 1 : temp = saleReportInfoModel.get(rowIndex).getPartyName();break;
		case 2 : temp = saleReportInfoModel.get(rowIndex).getBillNumber();break;
		case 3 : temp = saleReportInfoModel.get(rowIndex).getDate();break;
		case 4 : temp = saleReportInfoModel.get(rowIndex).getBAmount();break;
		case 5 : temp = saleReportInfoModel.get(rowIndex).getWAmount();break;
		case 6 : temp = saleReportInfoModel.get(rowIndex).getTotal();break;
		}
		return temp;
	}

}
