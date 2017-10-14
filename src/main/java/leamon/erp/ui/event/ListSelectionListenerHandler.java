package leamon.erp.ui.event;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import leamon.erp.model.AccountInfo;
import leamon.erp.ui.LeamonERP;
import leamon.erp.ui.model.TableAccountInfoListModel;
import leamon.erp.util.LeamonERPConstants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ListSelectionListenerHandler implements ListSelectionListener {

	static final Logger LOGGER = Logger.getLogger(ListSelectionListener.class);
	private Object target; 
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(target instanceof JTable){
			JTable tbl = (JTable) target;
			if(tbl.getName()!=null && tbl.getName().equals(LeamonERPConstants.TABLE_ACCOUNT_INFO_LIST)){
				int selectedRow = tbl.getSelectedRow();
				
				if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
					//JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
					//JOptionPane.showMessageDialog(null, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				/*Get accurate selected row after filtering records*/
				if(tbl.getRowSorter() != null){
					selectedRow = tbl.getRowSorter().convertRowIndexToModel(selectedRow);
				}
				
				TableAccountInfoListModel model  = (TableAccountInfoListModel)tbl.getModel();
				AccountInfo item = model.getAccountInfoItems().get(selectedRow);
				LOGGER.debug("ListSelectionListenerHandler[valueChanged] AccountInfo ["+item+"]");
				if(item.getImagePath() != null && !item.getImagePath().isEmpty()){
					if(Files.exists(Paths.get(item.getImagePath()))){
						File f = new File(item.getImagePath());
						if(f.isFile()){
							LOGGER.debug("ListSelectionListenerHandler[valueChanged] is a file");
							LOGGER.debug("ListSelectionListenerHandler[valueChanged] Image Path Found ["+item.getImagePath()+"] imag set");
							LeamonERP.accountListManager.getLabelImage().setIcon(new ImageIcon(item.getImagePath()));
						}else{
							LOGGER.debug("ListSelectionListenerHandler[valueChanged]   is not file");
							LOGGER.debug("ListSelectionListenerHandler[valueChanged]  Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
							LeamonERP.accountListManager.getLabelImage().setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
						}
					}
				}else {
					LOGGER.debug("ListSelectionListenerHandler[valueChanged]  Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
					LeamonERP.accountListManager.getLabelImage().setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
				}
			}
		}//end table 
		
	}

}
