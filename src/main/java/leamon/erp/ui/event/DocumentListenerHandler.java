package leamon.erp.ui.event;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.search.TableSearchable;
import org.jdesktop.swingx.sort.TableSortController;

public class DocumentListenerHandler implements DocumentListener {

	static final Logger LOGGER = Logger.getLogger(DocumentListenerHandler.class);
	
	private Object component;
	private TableRowSorter<TableModel> rowSorter;
	private TableSortController<TableModel> rowSorterX;
	
	private RowSorter<?> rowSorterT;
	
	/*public DocumentListenerHandler(Object component, TableRowSorter<TableModel> rowSorter){
		this.component=component;
		this.rowSorter = rowSorter;
	}*/
	
	private JXTable table;
	
	public DocumentListenerHandler(Object component, RowSorter<?> rowSorter){
		this.component=component;
		this.rowSorterT = rowSorter;
		LOGGER.info("DocumentListenerHandler[constructor] "+rowSorter);
		if(rowSorter instanceof TableRowSorter<?> ){
			LOGGER.info("DocumentListenerHandler[constructor] instance of "+rowSorter);
			this.rowSorter = (TableRowSorter<TableModel>)rowSorter;
		}else if (rowSorter instanceof TableSortController<?>){
			this.rowSorterX = (TableSortController<TableModel>)rowSorter;
		}
	}
	
	public DocumentListenerHandler(Object component, JXTable table){
		this.component=component;
		this.table = table;
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		if(component instanceof JTextField){
			JTextField txtField = (JTextField) component;
			String text = txtField.getText();
			if(rowSorter == null && rowSorterX == null){
				return;
			}
			if (text.trim().length() == 0) {
				if(rowSorter!=null){
					rowSorter.setRowFilter(null);
				}
				
				if(rowSorterX!=null){
					rowSorterX.setRowFilter(null);
				}
			} else {
				if(text.contains("\\") || text.contains(")") || text.contains("(") ){
					return ;
				}
				if(rowSorter!=null){
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
				if(rowSorterX!=null){
					rowSorterX.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		
		if(component instanceof JTextField){
			JTextField txtField = (JTextField) component;
			String text = txtField.getText();
			if(rowSorter == null && rowSorterX == null){
				return;
			}
			
			if (text.trim().length() == 0) {
				if(rowSorter!=null){
					rowSorter.setRowFilter(null);
				}
				
				if(rowSorterX!=null){
					rowSorterX.setRowFilter(null);
				}
			} else {
				if(text.contains("\\") || text.contains(")") || text.contains("(")){
					return ;
				}
				if(rowSorter!=null){
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
				if(rowSorterX!=null){
					rowSorterX.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		 //To change body of generated methods, choose Tools | Templates.
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
