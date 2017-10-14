package leamon.erp.ui.model;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.InventoryInfo;
import leamon.erp.model.StockItem;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * @Copyright By Leamon India 2017
 * 
 * Inventory Model
 * @date July 11,2017
 * @author Manish Kumar mishra
 *
 */
@Getter
@Setter
public class TableInventoryItemModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TableInventoryItemModel.class);
	private static final String CLASS_NAME = "TableInventoryItemModel";
	
	private final String [] columnName = new String[] {LeamonERPConstants.TABLE_HEADER_SNO, 
			LeamonERPConstants.TABLE_HEADER_INVENTORY_DESC_OF_GOODS, LeamonERPConstants.TABLE_HEADER_INVENTORY_SIZE, 
			LeamonERPConstants.TABLE_HEADER_INVENTORY_QTY_UNIT, LeamonERPConstants.TABLE_HEADER_INVENTORY_RATE, 
			LeamonERPConstants.TABLE_HEADER_INVENTORY_DISC, LeamonERPConstants.TABLE_HEADER_INVENTORY_VALUE_OF_GOODS};
	
	private List<InventoryInfo> inventoryInfos;
	
	public TableInventoryItemModel(List<InventoryInfo> inventoryInfos){
		this.inventoryInfos = inventoryInfos;
	}
	
	@Override
	public int getRowCount() {
		if(inventoryInfos == null){
			return 0;
		}
		
		return inventoryInfos.size();
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
			case 0: temp = inventoryInfos.get(rowIndex).getSno(); break;
			case 1: temp = inventoryInfos.get(rowIndex).getDescOfGoods(); break;
			case 2: temp = inventoryInfos.get(rowIndex).getSize(); break;
			case 3: temp = inventoryInfos.get(rowIndex).getDisplayQtyUnit();break;
			case 4: temp = inventoryInfos.get(rowIndex).getRate(); break;
			case 5: temp = inventoryInfos.get(rowIndex).getDisplayDiscount(); break;
			case 6: temp = inventoryInfos.get(rowIndex).getValueOfGoods(); break;
		}
		/*if(temp!=null){
			LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]")+" temp["+temp+"]");
		}*/
		//LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
		return temp;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		final String METHOD_NAME = "setValueAt";
		//LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" inside"));
		
		if(aValue!=null){
			LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]")+"aValue["+aValue+"]");
			
			String value = ""+aValue;
			if(columnIndex == LeamonERPConstants.TABLE_INVENTORY_DESC_OF_GOODS){
				
			}else if(columnIndex == LeamonERPConstants.TABLE_INVENTORY_SIZE){
				
			}else if(columnIndex == LeamonERPConstants.TABLE_INVENTORY_QTY){
				InventoryInfo info = inventoryInfos.get(rowIndex);
				try{
					String numericValue = "";
					if(value !=null ){
						numericValue = value.replaceAll("\\D+", "");
					}
					info.setQty(Integer.parseInt(numericValue));
				}catch(Exception exp ){
					info.setQty(0);
					LOGGER.error(exp);
				}
				String displayQtyUnit = "";
				if(info.getStockItemID() != null){
					Optional<StockItem> optional = null;
					try {
						optional = StockDaoImpl.getInstance().getItemList().stream()
								.filter(item -> item.getId().intValue() == info.getStockItemID().intValue())
								.findAny();
					} catch (Exception e) {
						LOGGER.error(e);
					}
					if(optional.isPresent()){
						StockItem stockItem = optional.get();
						String saleUnit = stockItem.getSaleunit();
						displayQtyUnit = value.concat(" ").concat(saleUnit);
					}
					
					if(displayQtyUnit == null || displayQtyUnit.trim().isEmpty()){
						info.setDisplayQtyUnit(value);
					}else{
						info.setDisplayQtyUnit(displayQtyUnit);
					}
				}else{
					info.setDisplayQtyUnit(value);
				}
				
			}else if(columnIndex == LeamonERPConstants.TABLE_INVENTORY_RATE){
				InventoryInfo info = inventoryInfos.get(rowIndex);
				try{
					info.setRate(new BigDecimal(value));
				}catch(Exception exp){ 
					LOGGER.error(exp.toString());
					info.setRate(new BigDecimal(0));
				}
			}else if(columnIndex == LeamonERPConstants.TABLE_INVENTORY_DISCOUNT){
				InventoryInfo info = inventoryInfos.get(rowIndex);
				try{
					info.setDiscount(new BigDecimal(value));
					info.setDisplayDiscount(value.concat(" %"));
					
					BigDecimal vlueOfGoods = calcValueOfGoods(info.getQty(), info.getRate(), info.getDiscount());
					info.setValueOfGoods(vlueOfGoods);
					fireTableRowsUpdated(rowIndex, rowIndex);
				}catch(Exception exp){ 
					LOGGER.error(exp.toString());
					info.setDiscount(new BigDecimal(0));
					info.setDisplayDiscount(value.concat(" %"));
				}
			}/*else if (columnIndex == LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS){
				setTotal(inventoryInfos);
			}*/
		}
		setTotal(inventoryInfos);
		//LOGGER.info(CLASS_NAME.concat("[").concat(METHOD_NAME).concat("]").concat(" end."));
	}
	
	@Override
	public String getColumnName(int col) { 
	      return columnName[col]; 
	} 
	
	public void addRow(InventoryInfo info){
		if(null == inventoryInfos ){
			inventoryInfos = new ArrayList<InventoryInfo>();
		}
		inventoryInfos.add(info);
		//fireTableRowsInserted(inventoryInfos.size() - 1, inventoryInfos.size() - 1);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		/*if(columnIndex >= 1 ){
			return true;
		}*/
		switch (columnIndex) {
			case 0: return false;
			case 1: 
				if(inventoryInfos.get(rowIndex).getIsTotalRow()){
					return false;
				}else{
					return true;
				}
			case 6: return false;
			default: return columnIndex >= 1 ? true : false;
		}
		
	}
	
	public void removeRow(int row, int col){
		this.fireTableRowsDeleted(row, col);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		if(inventoryInfos.isEmpty()){
			return Object.class;
		}
		
		
		
		switch(columnIndex){
			case 0: return Integer.class;
			case 1: return String.class;
			case 2: return String.class;
			case 3: return String.class;
			case 4: return Double.class;
			case 5: return String.class;
			case 6: return Double.class;
			default : return Object.class;
		}
	}
	
	public BigDecimal calcValueOfGoods(Integer qty, BigDecimal rate, BigDecimal discount){
		BigDecimal valueOfGoods = null;

		if(discount == null){
			discount  = new BigDecimal("0");
		}
		if(rate == null ){
			rate = new BigDecimal("0");
		}
		
		if(qty == null){
			qty = 0;
		}
		
		double total =  qty*rate.doubleValue();
		double discountValue = total*(discount.doubleValue()/100);
		double grandTotal = total - discountValue;
		
		valueOfGoods = new BigDecimal(grandTotal);
		return valueOfGoods;
	}
	
	public void setRowColor(int row, Color c){
		fireTableRowsUpdated(row, row);
	}
	
	public void cutomFireTableRowInserted(int firstRow, int lastRow){
		fireTableRowsInserted(firstRow, lastRow);
	}
	
	public static void setTotal(List<InventoryInfo> infos){
		List<BigDecimal> values=  infos.stream()
				.filter(item -> null!= item.getDescOfGoods() && !item.getDescOfGoods().equals(LeamonERPConstants.TOTAL))
				.map(item -> item.getValueOfGoods())
				.filter(item -> item!=null).collect(Collectors.toList());//.stream().mapToDouble(BigDecimal::doubleValue).sum();
		double sum = values.stream().mapToDouble(BigDecimal::doubleValue).sum();
		
		OptionalInt indexOp=IntStream.range(0, infos.size())
				.filter(i-> null!= infos.get(i).getDescOfGoods() && infos.get(i).getDescOfGoods().equals(LeamonERPConstants.TOTAL))
				.findFirst();
		int row = indexOp.getAsInt();
		if(row >= 0 && row <= infos.size()-1){
			InventoryInfo info =  infos.get(row);
			info.setValueOfGoods(new BigDecimal(sum));
		}
		//fireTableRowsUpdated(row, row);
		//return new BigDecimal(sum);
	}
}
