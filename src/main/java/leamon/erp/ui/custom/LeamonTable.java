package leamon.erp.ui.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class LeamonTable extends JTable{

	public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
		Object value = this.getValueAt(rowIndex, columnIndex);

        boolean isSelected = false;
        boolean hasFocus = false;
        
	    Component componenet = super.prepareRenderer(renderer, rowIndex, columnIndex);  
	    if(rowIndex % 2 == 0) {  
	    	Color color = new Color(255, 251, 224);
	       componenet.setBackground(color);  
	    } else {
	       componenet.setBackground(Color.white);
	    }
	 // Only indicate the selection and focused cell if not printing
        if (!isPaintingForPrint()) {
            isSelected = isCellSelected(rowIndex, columnIndex);

            boolean rowIsLead =
                (selectionModel.getLeadSelectionIndex() == rowIndex);
            boolean colIsLead =
                (columnModel.getSelectionModel().getLeadSelectionIndex() == columnIndex);

            hasFocus = (rowIsLead && colIsLead) && isFocusOwner();
        }
        //setFont(new Font("Courier New", Font.BOLD, 20));
        return renderer.getTableCellRendererComponent(this, value,
                                                      isSelected, hasFocus,
                                                      rowIndex, columnIndex);
	    //return componenet;
	} 
	public boolean getScrollableTracksViewportWidth()
    {
        return getPreferredSize().width < getParent().getWidth();
    }

}
