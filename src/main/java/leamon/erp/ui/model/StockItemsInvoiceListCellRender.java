package leamon.erp.ui.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.google.common.base.Strings;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.StockItem;

public class StockItemsInvoiceListCellRender extends JLabel implements ListCellRenderer{
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

	public StockItemsInvoiceListCellRender(){
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		StockItem info = (StockItem) value;
		String pcode = "";
		/*if(!Strings.isNullOrEmpty(info.getProductCode())){
			pcode = info.getProductCode().concat(" ");
		}*/
		setText(info.getName().concat(" ").concat(pcode).concat(info.getSize()));
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
