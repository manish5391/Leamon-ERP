package leamon.erp.ui.model;

/**
 * @copyright Leamon India 
 * @author Manish Mishra
 * @date Jul 1,2017 
 */
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import leamon.erp.model.AccountInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableAccountInfoListModel extends AbstractTableModel{
	
	static final Logger LOGGER = Logger.getLogger(TableAccountInfoListModel.class);
	
	private final String [] columnName = new String[] {"ID", "Name", "Marka/Nick N", "GST Number", "Transport", "House No", "Street", "City", "PAN", "Licence"};
	private List<AccountInfo> accountInfoItems;
	
	public TableAccountInfoListModel(List<AccountInfo> accountInfoItems){
		this.accountInfoItems = accountInfoItems;
	}

	@Override
	public int getRowCount() {
		if(accountInfoItems == null){
			return 0;
		}
		
		return accountInfoItems.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
		switch (columnIndex) {
			case 0: temp = accountInfoItems.get(rowIndex).getId(); break;
			case 1: temp = accountInfoItems.get(rowIndex).getName(); break;
			case 2: temp = accountInfoItems.get(rowIndex).getNickName(); break;
			case 3: temp = accountInfoItems.get(rowIndex).getGstNumber(); break;
			case 4: temp = accountInfoItems.get(rowIndex).getTransport(); break;
			case 5: temp = accountInfoItems.get(rowIndex).getHouseShopNumber(); break;
			case 6: temp = accountInfoItems.get(rowIndex).getStreet(); break;
			case 7: temp = accountInfoItems.get(rowIndex).getCity(); break;
			case 8: temp = accountInfoItems.get(rowIndex).getPanCard(); break;
			case 9: temp = accountInfoItems.get(rowIndex).getLicence(); break;
		}
		return temp;
	}

	@Override
	public String getColumnName(int col) { 
	      return columnName[col]; 
	} 
}
