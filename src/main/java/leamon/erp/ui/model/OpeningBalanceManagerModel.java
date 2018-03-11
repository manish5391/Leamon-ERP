package leamon.erp.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import leamon.erp.model.OpeningBalanceManagerInfoModel;
import leamon.erp.model.SalesReportInfoModel;
/**
 * Opening Balance UI
 * 
 * @date MAR 12,2018
 * @author Ghanshyam Singla
 *
 */
public class OpeningBalanceManagerModel extends AbstractTableModel{
	private final String [] columnName = new String[] {
			"S No", 
			"Party Name", 
			"Bill Number",
			"Bill Date",
			"Type",
			"Opening Balance Amount",
			"Received Amount",
			"Remaining Amount"
			};
	
	private List<OpeningBalanceManagerInfoModel> openingBalanceManagerInfoModels;
	
	public OpeningBalanceManagerModel(List<OpeningBalanceManagerInfoModel> openingBalanceManagerInfoModels){
		this.openingBalanceManagerInfoModels = openingBalanceManagerInfoModels;
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
		if(openingBalanceManagerInfoModels == null) {
			return 0;
		}
		return openingBalanceManagerInfoModels.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
		switch(columnIndex){
		case 0 : temp = openingBalanceManagerInfoModels.get(rowIndex).getSNo();break;
		case 1 : temp = openingBalanceManagerInfoModels.get(rowIndex).getPartyName();break;
		case 2 : temp = openingBalanceManagerInfoModels.get(rowIndex).getBillNumber();break;
		case 3 : temp = openingBalanceManagerInfoModels.get(rowIndex).getBillDate();break;
		case 4 : temp = openingBalanceManagerInfoModels.get(rowIndex).getType();break;
		case 5 : temp = openingBalanceManagerInfoModels.get(rowIndex).getOpeningBalanceAmount();break;
		case 6 : temp = openingBalanceManagerInfoModels.get(rowIndex).getReceivedAmount();break;
		case 7 : temp = openingBalanceManagerInfoModels.get(rowIndex).getRemainingAmount();break;
		}
		return temp;
	}

}
