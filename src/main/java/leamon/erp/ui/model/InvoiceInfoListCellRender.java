package leamon.erp.ui.model;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import leamon.erp.model.InvoiceInfo;

public class InvoiceInfoListCellRender  extends JLabel implements ListCellRenderer{
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

	public InvoiceInfoListCellRender(){
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		InvoiceInfo info = (InvoiceInfo) value;
		setText(info.getInvoicNum());
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
