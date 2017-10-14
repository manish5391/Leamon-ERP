package leamon.erp.ui.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

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
public class TableStockListItemModel extends AbstractTableModel{
	
	private static final Logger LOGGER = Logger.getLogger(TableStockListItemModel.class);
	
	private final String [] columnName = new String[] {"ID", "NAME", "PRODUCT CODE", "SIZE", "UNIT", "FINISH", "SHAPE", "SALE UNIT", "DESCRIPTION"};
	private List<StockItem> stockItems;
	
	public TableStockListItemModel(List<StockItem> stockItems){
		this.stockItems = stockItems;
	}
	
	@Override
	public int getRowCount() {
		if(stockItems == null){
			return 0;
		}
		
		return stockItems.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object temp = null;
		switch (columnIndex) {
			case 0: temp = stockItems.get(rowIndex).getId(); break;
			case 1: temp = stockItems.get(rowIndex).getName(); break;
			case 2: temp = stockItems.get(rowIndex).getProductCode(); break;
			case 3: temp = stockItems.get(rowIndex).getSize(); break;
			case 4: temp = stockItems.get(rowIndex).getUnit(); break;
			case 5: temp = stockItems.get(rowIndex).getFinish(); break;
			case 6: temp = stockItems.get(rowIndex).getShape(); break;
			case 7: temp = stockItems.get(rowIndex).getSaleunit(); break;
			case 8: temp = stockItems.get(rowIndex).getDescription(); break;
		}
		return temp;
	}
	@Override
	public String getColumnName(int col) { 
	      return columnName[col]; 
	} 
}
