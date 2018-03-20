package leamon.erp.ui.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.LeamonERP;
import leamon.erp.ui.model.TableAccountInfoListModel;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.LeamonERPConstants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class KeyListenerHandler implements KeyListener{

	static final Logger LOGGER = Logger.getLogger(KeyListenerHandler.class);

	private Object targetComponent;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource() instanceof JTextField){
			//LOGGER.info("KeyListenerHandler[keyPressed] JTextField inside");
			JTextField txtField = (JTextField)e.getSource(); 
			if(e.getKeyCode() == KeyEvent.VK_KP_DOWN){
				if(txtField .getName()!=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SOCK_ITEM_SERACH) ){
					if(targetComponent instanceof JTable){
						JTable table = (JTable) targetComponent;
						try{
						table.setRowSelectionInterval(0, 0);
						}catch (Exception exp) {
							LOGGER.error("KeyListenerHandler[keyPressed] No Row found in "+table.getName()+" "+exp.getMessage());
						}
					}
				}
			}

			//LOGGER.info("KeyListenerHandler[keyPressed] JTextField end.");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getSource() instanceof JTable){
			JTable table = (JTable) e.getSource();
			LOGGER.info("KeyListenerHandler[keyReleased] jtable");
			if(table.getName()!= null && table.getName().equals(LeamonERPConstants.TABLE_STOCK_ITEMS)){
				stockImageDisplayer(table);
			}else if(table.getName()!= null && table.getName().equals(LeamonERPConstants.TABLE_ACCOUNT_INFO_LIST)){
				accountInfoImageDisplayer(table);
			}else if(table.getName()!= null && table.getName().equals(LeamonERPConstants.TABLE_INVENTORY)){
				
			}
		}

	}
	
	public void stockImageDisplayer(JTable table){

		LOGGER.debug("KeyListenerHandler[keyReleased] Table Name ["+LeamonERPConstants.TABLE_STOCK_ITEMS+"]");
		int selectedRow = table.getSelectedRow();
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			return;
		}
		/*Get accurate selected row after filtering records*/
		if(table.getRowSorter() != null){
			selectedRow = table.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			LOGGER.warn("KeyListenerHandler[keyReleased] Selected Row["+LeamonERPConstants.NO_ROW_SELECTED+"]");
			return ;
		}

		TableStockListItemModel model = (TableStockListItemModel)table.getModel();
		StockItem item = model.getStockItems().get(selectedRow);
		LOGGER.debug("KeyListenerHandler[keyReleased] StockItem ["+item+"]");
		if(item.getImagePath() != null && !item.getImagePath().isEmpty()){
			if(Files.exists(Paths.get(item.getImagePath()))){
				File f = new File(item.getImagePath());
				if(f.isFile()){
					LOGGER.debug("KeyListenerHandler[keyReleased] is a file");
					LOGGER.debug("KeyListenerHandler[keyReleased] Image Path Found ["+item.getImagePath()+"] imag set");
					LeamonERP.stockItemList.getLblImage().setIcon(new ImageIcon(item.getImagePath()));
				}else{
					LOGGER.debug("KeyListenerHandler[keyReleased]  is not file");
					LOGGER.debug("KeyListenerHandler[keyReleased] Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
					LeamonERP.stockItemList.getLblImage().setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
				}
			}
		}else {
			LOGGER.debug("KeyListenerHandler[keyReleased] Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
			LeamonERP.stockItemList.getLblImage().setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		}
		LOGGER.info("KeyListenerHandler[keyReleased] end for Table "+LeamonERPConstants.TABLE_STOCK_ITEMS+"");
	
	}

	private void accountInfoImageDisplayer(JTable table){

		LOGGER.debug("KeyListenerHandler[keyReleased] Table Name ["+LeamonERPConstants.TABLE_ACCOUNT_INFO_LIST+"]");
		int selectedRow = table.getSelectedRow();

		/*Get accurate selected row after filtering records*/
		if(table.getRowSorter() != null){
			selectedRow = table.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			LOGGER.warn("KeyListenerHandler[keyReleased] Selected Row["+LeamonERPConstants.NO_ROW_SELECTED+"]");
			return ;
		}

		TableAccountInfoListModel model = (TableAccountInfoListModel)table.getModel();
		AccountInfo item = model.getAccountInfoItems().get(selectedRow);
		LOGGER.debug("KeyListenerHandler[keyReleased] AccountInfo ["+item+"]");
		if(item.getImagePath() != null && !item.getImagePath().isEmpty()){
			if(Files.exists(Paths.get(item.getImagePath()))){
				File f = new File(item.getImagePath());
				if(f.isFile()){
					LOGGER.debug("KeyListenerHandler[keyReleased] is a file");
					LOGGER.debug("KeyListenerHandler[keyReleased] Image Path Found ["+item.getImagePath()+"] imag set");
					LeamonERP.accountListManager.getLabelImage().setIcon(new ImageIcon(item.getImagePath()));
				}else{
					LOGGER.debug("KeyListenerHandler[keyReleased]  is not file");
					LOGGER.debug("KeyListenerHandler[keyReleased] Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
					LeamonERP.accountListManager.getLabelImage().setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
				}
			}
		}else {
			LOGGER.debug("KeyListenerHandler[keyReleased] Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
			LeamonERP.accountListManager.getLabelImage().setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		}
		LOGGER.info("KeyListenerHandler[keyReleased] end for Table "+LeamonERPConstants.TABLE_ACCOUNT_INFO_LIST+"");
	}
}
