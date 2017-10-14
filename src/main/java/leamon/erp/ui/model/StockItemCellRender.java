package leamon.erp.ui.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.log4j.Logger;

import leamon.erp.model.StockItem;
import leamon.erp.util.LeamonERPConstants;

public class StockItemCellRender extends JLabel implements ListCellRenderer{
	
	private static final Logger LOGGER = Logger.getLogger(StockItemCellRender.class);
	
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

	public StockItemCellRender(){
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		StockItem info = (StockItem) value;
		//if(inf)
		String name = info.getName();
		String productoCode = info.getProductCode();
		String size = info.getSize();
		String unit = info.getUnit();
		if(unit!=null && unit.equals("inch")){
			unit="\"";
		}
		String finish = info.getFinish();
		String shape= "(".concat(info.getShape()).concat(")");
		
		String displayName = name.concat(LeamonERPConstants.HYPHEN)
							.concat(productoCode).concat(LeamonERPConstants.SPACE)
							.concat(size).concat(unit).concat(LeamonERPConstants.SPACE)
							.concat(shape).concat(LeamonERPConstants.SPACE)
							.concat(finish);
		
		setText(displayName);
		if (isSelected) {
			setBackground(HIGHLIGHT_COLOR);
			setForeground(Color.white);
		} else {
			setBackground(Color.white);
			setForeground(Color.black);
		}
		return this;
	}

}
