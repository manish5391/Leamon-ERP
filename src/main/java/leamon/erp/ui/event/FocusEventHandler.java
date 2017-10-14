package leamon.erp.ui.event;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXSearchField;

import leamon.erp.db.StockDaoImpl;
import leamon.erp.util.LeamonERPConstants;

public class FocusEventHandler implements FocusListener {

	static final Logger LOGGER = Logger.getLogger(FocusEventHandler.class);

	private JTable table;
	
	public FocusEventHandler(JTable table){
		this.table = table;
	}
	
	public FocusEventHandler(){
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField){
			JTextField txtField = (JTextField) e.getSource();
			if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_STOCK_NAME)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_STOCK_NAME);
				refreshIntellisense();
				List<String> stockItemNames = StockDaoImpl.getInstance().getStockItemNames();
				setDataModel(stockItemNames,LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_NAME);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_FINISH)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_FINISH);
				List<String> stockItemFinish = StockDaoImpl.getInstance().getStockItemFinishs();
				setDataModel(stockItemFinish,LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_FINISHES);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_PRODUCT_CODE)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_PRODUCT_CODE);
				List<String> stockItemProductCode = StockDaoImpl.getInstance().getStockItemProductCodes();
				setDataModel(stockItemProductCode,LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_PRODUCT_CODES);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SIZE)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_SIZE);
				List<String> stockItemSizes = StockDaoImpl.getInstance().getStockItemSizes();
				setDataModel(stockItemSizes,LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SIZE);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_UNIT)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_UNIT);
				List<String> stockItemUnits = StockDaoImpl.getInstance().getStockItemUnits();
				setDataModel(stockItemUnits,LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_UNITS);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SHAPE)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_SHAPE);
				List<String> stockItemSahpes = StockDaoImpl.getInstance().getStockItemShapes();
				setDataModel(stockItemSahpes,LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SHAPES);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SALE_UNIT)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_SALE_UNIT);
				List<String> stockItemSaleUnits = StockDaoImpl.getInstance().getStockItemSaleUnits();
				setDataModel(stockItemSaleUnits,LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_SALE_UNITS);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_DESCRIPTION)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_DESCRIPTION);
				List<String> stockItemDesc = StockDaoImpl.getInstance().getStockItemDescriptions();
				setDataModel(stockItemDesc,LeamonERPConstants.TABLE_HEADER_STOCK_ITEM_DESCRIPTION);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_SOCK_ITEM_SERACH)){ /*Stock Items Search*/
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_SOCK_ITEM_SERACH);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}else if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_ACCOUNT_INFO_SERACH)){ /*Stock Items Search*/
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_ACCOUNT_INFO_SERACH);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}
		}//end if text field instance
		else if(e.getSource() instanceof JXSearchField){
			
			JXSearchField txtField = (JXSearchField) e.getSource();
			LOGGER.debug("inside "+txtField.getName());
			
			if(txtField.getName() !=null && txtField.getName().equals(LeamonERPConstants.TEXTFIELD_NAME_ACCOUNT_INFO_SERACH)){
				LOGGER.debug("inside "+LeamonERPConstants.TEXTFIELD_NAME_ACCOUNT_INFO_SERACH);
				txtField.getDocument().addDocumentListener(new DocumentListenerHandler(txtField,table.getRowSorter()));
			}
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		
	}
	
	public void setDataModel(List<String> list, String columnName){
		Object [][]data =new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            data[i][0]=item;
        }
		DefaultTableModel tableModel = new DefaultTableModel(data, new String [] {columnName});
		table.setName(columnName);
		table.setModel(tableModel);
		TableRowSorter<TableModel> rowSorter = new TableRowSorter(table.getModel());
		table.setRowSorter(rowSorter);
	}
	
	public void refreshIntellisense(){
		try{
			StockDaoImpl.getInstance().prepareStockIntelliSense();
			}catch(Exception e){
				LOGGER.error(e);
			}
	}

}
