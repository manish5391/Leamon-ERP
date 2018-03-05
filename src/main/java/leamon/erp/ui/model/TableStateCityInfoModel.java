package leamon.erp.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import leamon.erp.model.StateCityInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * @Copyright By Leamon India 2018
 * 
 * @author Manish Kumar Mishra
 * @date FEB 13,2017
 * @version 1.0
 */
@Getter
@Setter
public class TableStateCityInfoModel extends AbstractTableModel{
	
	private static final Logger LOGGER = Logger.getLogger(TableStateCityInfoModel.class);
	
	private final String [] columnName = new String[] {
			"ID", 
			"CITY", 
			"STATE", 
			"STATECODE", 
			"ABBREVIATIONS"
			
			};
	private List<StateCityInfo> stateCityInfos;
	
	public TableStateCityInfoModel(List<StateCityInfo> stateCityInfos){
		this.stateCityInfos = stateCityInfos;
	}
	
	@Override
	public int getRowCount() {
		if(stateCityInfos == null){
			return 0;
		}
		
		return stateCityInfos.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
		switch (columnIndex) {
			case 0: temp = stateCityInfos.get(rowIndex).getId(); break;
			case 1: temp = stateCityInfos.get(rowIndex).getCity(); break;
			case 2: temp = stateCityInfos.get(rowIndex).getState(); break;
			case 3: temp = stateCityInfos.get(rowIndex).getStateCode(); break;
			case 4: temp = stateCityInfos.get(rowIndex).getAbbreviations(); break;
		}
		return temp;
	}
	@Override
	public String getColumnName(int col) { 
	      return columnName[col]; 
	} 
}
