package leamon.erp.ui.custom;

import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.google.common.base.Strings;

import leamon.erp.model.StockItem;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.InvoicePaymentStatusEnum;
import lombok.val;

public class StockItemListColorRenderer extends JLabel implements TableCellRenderer {

	public StockItemListColorRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub

		//TableStockListItemModel model = (TableStockListItemModel)table.getColumnModel();
		TableStockListItemModel model = (TableStockListItemModel)table.getModel();
		
		if(model!=null && model.getStockItems() != null && !model.getStockItems().isEmpty()){

			if(value instanceof String 
					&& !Strings.isNullOrEmpty(value.toString())
					&& !isSelected){

				System.out.println("column name="+model.getColumnName(4));
				if(column==4) {
					double d=Double.parseDouble(value.toString().trim());
					if(d<10) {
						setForeground(Color.RED);
						setText(value.toString());
					}
					else {
						setBackground(table.getBackground());
						setForeground(table.getForeground());
						setText(value.toString());
					}
				}else{
					setBackground(table.getBackground());
					setForeground(table.getForeground());
					setText(value.toString());
				}
				
			}else if(!isSelected){
					setBackground(table.getBackground());
					setForeground(table.getForeground());
					setText(value!=null ? value.toString() : "");
			}else{
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
				setText(value!=null ? value.toString() : "");
			}
		}


		return this;
	}

}
