package leamon.erp.ui.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.log4j.Logger;

import leamon.erp.ui.model.TableInventoryItemModel;
import leamon.erp.util.LeamonERPConstants;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

	private static final String CLASS_NAME="CustomTableCellRenderer";
	private static final Logger LOGGER = Logger.getLogger(CustomTableCellRenderer.class);

	/*@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column){
		JLabel d = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		//table.getModel().getRowCount()
		//if ((row == 0) && (column == 0)) {
		if (row == table.getRowCount()-1 && column <= table.getColumnCount()) {
		    d.setBackground(new java.awt.Color(255, 72, 72));
		}
		else {
		    d.setBackground(Color.WHITE);
		}
		return d;
	}*/

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		final String methodName="getTableCellRendererComponent";
		//LOGGER.info(CLASS_NAME+"["+methodName+"] inside");

		TableInventoryItemModel model =  (TableInventoryItemModel) table.getModel();

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		//LOGGER.info(CLASS_NAME+"["+methodName+"] end");
		if(model!=null && 
				model.getInventoryInfos().get(row).getDescOfGoods()!=null && 
				model.getInventoryInfos().get(row).getDescOfGoods().equals(LeamonERPConstants.TOTAL)){
			c.setBackground(Color.YELLOW);
			this.repaint();
		}else{
			c.setBackground(Color.CYAN);
			this.repaint();
		}
		/*if(table.getColumnName(LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS)
				.equals(LeamonERPConstants.TABLE_HEADER_INVENTORY_VALUE_OF_GOODS) ) {
			 String totalVal = (String) value;
			 JLabel d = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS);
			 if(totalVal!=null && totalVal.equals(LeamonERPConstants.TOTAL)){
				 d.setForeground(Color.RED);
	             d.setFont(new Font("Dialog", Font.BOLD, 18));
			 }else{
				 d.setForeground(Color.BLACK);
			 }
			
		}*/
		paintTotal(table, value, isSelected, hasFocus, row, column);
		return c;
	}
	
	private void paintTotal(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column){
		TableInventoryItemModel tableInventoryItemModel = (TableInventoryItemModel) table.getModel();
		if(column == LeamonERPConstants.TABLE_INVENTORY_DESC_OF_GOODS && table.getValueAt(row, column) instanceof String){
			String val =(String)table.getValueAt(row, column);
			if(val!=null && val.equals(LeamonERPConstants.TOTAL)){
				JLabel d = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS);
				d.setForeground(Color.RED);
	            d.setFont(new Font("Dialog", Font.BOLD, 18));
	            d.setText(val);
	            this.repaint();
			}
		}else if(column == LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS && table.getValueAt(row, column) instanceof BigDecimal){
			BigDecimal val =(BigDecimal)table.getValueAt(row, column);
			if(val!=null && val.toString().equals(tableInventoryItemModel.getInventoryInfos().get(tableInventoryItemModel.getInventoryInfos().size()-1).getValueOfGoods().toString())){
				JLabel d = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, LeamonERPConstants.TABLE_INVENTORY_VALUE_OF_GOODS);
				d.setForeground(Color.RED);
	            d.setFont(new Font("Dialog", Font.BOLD, 18));
	            d.setText(val.toString());
	            this.repaint();
			}
		}
		
	}//end
}
