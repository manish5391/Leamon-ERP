package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import org.jdesktop.swingx.JXSearchField;

import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.StateCityDaoImpl;
import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.StateCityInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.custom.StockItemListColorRenderer;
import leamon.erp.ui.event.FocusEventHandler;
import leamon.erp.ui.event.KeyListenerHandler;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.model.TableAccountInfoListModel;
import leamon.erp.ui.model.TableStateCityInfoModel;
import leamon.erp.ui.model.TableStateCityInfoModel;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXHyperlink;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTable;
import javax.swing.JTable;

/**
 * State City manager UI
 * @date 15 Feb,2018
 * @author Manish Kumar Mishra
 */
@Getter
public class StateCityManagerUI extends JInternalFrame {

	static final Logger LOGGER = Logger.getLogger(StateCityManagerUI.class);

	static final String CLASS_NAME = "StateCityManagerUI";
	
	private JXTable table;
	private JXSearchField searchField;

	private JButton btnAddItem;
	private JButton btnedit;
	private JButton btnView;
	private JButton btnDelete;
	private JButton btnPrint;

	public StateCityManagerUI() {
		setTitle("State & City Manager");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(3, 30, 657, 660);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 255, 255), 2, true), "Operation", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 650, 73);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnAddItem = new JButton();
		btnAddItem.setBackground(Color.WHITE);
		btnAddItem.setBounds(10, 17, 75, 49);
		btnAddItem.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_ADD_BUTTON)));
		panel.add(btnAddItem);
		btnAddItem.addActionListener(e -> btnAddItemClick(e));

		btnedit = new JButton();
		btnedit.setBackground(Color.WHITE);
		btnedit.setActionCommand("EditStockItem");
		btnedit.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON)));
		btnedit.setBounds(95, 17, 75, 49);
		panel.add(btnedit);
		btnedit.addActionListener(e -> btneditClick(e));

		btnView = new JButton();
		btnView.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_VIEW_BUTTON)));
		btnView.setBackground(Color.WHITE);
		btnView.setActionCommand("ViewStockItem");
		btnView.setBounds(180, 17, 89, 47);
		panel.add(btnView);
		btnView.addActionListener(e -> btnViewClick(e));

		btnDelete = new JButton();
		btnDelete.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setActionCommand("DeleteStockItem");
		btnDelete.setBounds(280, 17, 75, 49);
		panel.add(btnDelete);
		btnDelete.addActionListener(e -> btnDeleteClick(e));

		btnPrint = new JButton();
		btnPrint.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_PRINT_BUTTON)));
		btnPrint.setBackground(Color.WHITE);
		btnPrint.setActionCommand("PrintStockItem");
		btnPrint.setBounds(366, 17, 75, 49);
		panel.add(btnPrint);
		btnPrint.addActionListener(e -> btnPrintClick(e));

		JScrollPane scrollPane = new JScrollPane((Component) null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 72, 650, 557);
		getContentPane().add(scrollPane);

		table = new JXTable();
		table.setName(LeamonERPConstants.TABLE_STATE_CITY);

		StateCityDaoImpl daoImpl = StateCityDaoImpl.getInstance();
		List<StateCityInfo> stateCityInfos = new ArrayList<StateCityInfo>();
		try{
			stateCityInfos = daoImpl.getItemList();
		}catch(Exception e){
			LOGGER.error(e);
		}
		TableStateCityInfoModel stateCityInfoModel = new TableStateCityInfoModel(stateCityInfos);
		stateCityInfoModel.setStateCityInfos(stateCityInfos);
		table.setModel(stateCityInfoModel);

		table.setRowHeight(table.getRowHeight()+20);

		table.setAutoCreateRowSorter(true);
		table.setColumnControlVisible(true);
		table.packAll();
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		table.setComponentPopupMenu(createPopup());
		table.addMouseListener(new MouseClickHandler());
		
		searchField = new JXSearchField("Search");
		searchField.setName(LeamonERPConstants.TEXTFIELD_NAME_STATE_CITY_SERACH);
		searchField.setFont(new Font("Courier New", Font.BOLD, 26));
		searchField.setColumns(10);
		searchField.setBounds(450, 17, 190, 37);
		panel.add(searchField);
		searchField.addFocusListener(new FocusEventHandler(table));

	}
	
	public JPopupMenu createPopup(){
		final String METHOD_NAME = "createPopup";
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside");
		
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete", new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		JMenuItem menuItemView = new JMenuItem("View",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_VIEW)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));
		
		menuItemDelete.setActionCommand(LeamonERPConstants.MENU_ACTION_DELETE_STOCK_ITEM);
		menuItemDelete.addActionListener(e -> menuItemDeleteClick(e));
		
		menuItemView.setActionCommand(LeamonERPConstants.MENU_ACTION_VIEW_STOCK_ITEM);
		menuItemView.addActionListener(e -> btnViewClick(e));
		menuItemRefresh.setActionCommand(LeamonERPConstants.MENU_ACTION_REFRESH_STOCK_ITEM);
		menuItemRefresh.addActionListener(e -> menuItemRefreshClick(e) );
		
		popupMenu.add(menuItemRefresh);
		popupMenu.add(menuItemView);
		popupMenu.add(menuItemDelete);
		LOGGER.info("StockItemList[createPopup] end.");
		SwingUtilities.updateComponentTreeUI(this);
		return popupMenu;
	}

	private void btnViewClick(ActionEvent e){
		view();
	}
	private void btnAddItemClick(ActionEvent e){
		add();
	}
	private void btneditClick(ActionEvent e){
		edit();
	}
	private void btnDeleteClick(ActionEvent e){
		delete();
	}
	private void btnPrintClick(ActionEvent e){
		print();
	}
	private void menuItemRefreshClick(ActionEvent e){
		refreshTable();
	}
	
	private void menuItemDeleteClick(ActionEvent e){
		final String METHOD_NAME = "menuItemDeleteClick";
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside");
		
		int op =JOptionPane.showConfirmDialog(this, "Are you sure ?","Leamon-ERP",JOptionPane.YES_NO_OPTION);
		
		if(! (op==JOptionPane.YES_OPTION)){
			return;
		}
		
		TableStateCityInfoModel model  = (TableStateCityInfoModel)table.getModel();

		int selectedRow = table.getSelectedRow();
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
	 	if(table.getRowSorter() != null){
			selectedRow = table.getRowSorter().convertRowIndexToModel(selectedRow);
			LOGGER.debug("Selcted row : "+ selectedRow);
		}
	 	
	 	int selectedRows[] = table.getSelectedRows();
	 	IntStream.of(selectedRows).forEach(val -> {
	 		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] selected rows["+val+"]");
	 	});
	 	List<StateCityInfo> stateCityInfos=  model.getStateCityInfos();
	 	int i=0;
	 	int f=0;
	 	for(int row  : selectedRows){
	 		if(i==0){
	 			f=row;
	 		}
	 		i++;
	 		if(row == selectedRow ){
	 			LOGGER.debug("Selcted row selectedRow equal: "+ (row));
 				StateCityInfo si = stateCityInfos.get(row);
 				LOGGER.debug("Selected Row : "+si);
 				try{
 					StateCityDaoImpl.getInstance().disable(si);
 				}catch(Exception exp){
 					LOGGER.error(exp);
 				}
	 		}else{
	 				LOGGER.debug("Selcted row only : "+ (selectedRow+row-f));
	 				StateCityInfo si = stateCityInfos.get(selectedRow+row-f);
	 				LOGGER.debug("Selected Row : "+si);
	 				try{
	 					StateCityDaoImpl.getInstance().disable(si);
	 				}catch(Exception exp){
	 					LOGGER.error(exp);
	 				}
	 		}
	 	}
	 	try{
	 		stateCityInfos = StateCityDaoImpl.getInstance().getItemList();
	 	}catch(Exception exp){
			LOGGER.error(exp);
		}
	 	LOGGER.debug("Reloading AccountInfos ["+stateCityInfos.size()+"]");
	 	model.setStateCityInfos(stateCityInfos);
	 	table.setModel(model);
	 	LOGGER.info(" Successfully disabled Accoount Info items ");
	 	((AbstractTableModel)table.getModel()).fireTableDataChanged();
	 	table.repaint();
	 	SwingUtilities.updateComponentTreeUI(this);
	 	LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] end.");
	}
	
	public void view(){
		final String METHOD_NAME = "view";
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside");
		int selectedRow = table.getSelectedRow();
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		/*Get accurate selected row after filtering records*/
		if(table.getRowSorter() != null){
			selectedRow = table.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		
		TableStateCityInfoModel model  = (TableStateCityInfoModel)table.getModel();
		List<StateCityInfo> stateCityInfos =  model.getStateCityInfos();
		StateCityInfo si = stateCityInfos.get(selectedRow);
		
		if(!LeamonERP.stateCityUI.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.stateCityUI);
		}
		LeamonERP.stateCityUI.requestFocus();
		try {
			LeamonERP.stateCityUI.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] "+e1);
		}
		LeamonERP.stateCityUI.setStateCityInfo(si);
		/*if(actionCommand.equals(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM)){
			LeamonERP.stockItemManager.getBtnSave().setEnabled(false);
		}*/
		LeamonERP.stateCityUI.setVisible(true);
		LeamonERP.stateCityUI.moveToFront();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside");
	}
	
	public void delete(){
		final String METHOD_NAME = "delete";
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside");
		
		TableStateCityInfoModel model  = (TableStateCityInfoModel)table.getModel();

		int selectedRow = table.getSelectedRow();
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
	 	if(table.getRowSorter() != null){
			selectedRow = table.getRowSorter().convertRowIndexToModel(selectedRow);
			LOGGER.debug("Selcted row : "+ selectedRow);
		}
	 	
	 	int selectedRows[] = table.getSelectedRows();
	 	IntStream.of(selectedRows).forEach(val -> {
	 		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] selected rows["+val+"]");
	 	});
	 	List<StateCityInfo> stateCityInfos =  model.getStateCityInfos();
	 	int i=0;
	 	int f=0;
	 	for(int row  : selectedRows){
	 		if(i==0){
	 			f=row;
	 		}
	 		i++;
	 		if(row == selectedRow ){
	 			LOGGER.debug("Selcted row selectedRow equal: "+ (row));
 				StateCityInfo si = stateCityInfos.get(row);
 				LOGGER.debug("Selected Row : "+si);
 				try{
 				StateCityDaoImpl.getInstance().disable(si);
 				}catch(Exception e){
 					LOGGER.error(e);
 				}
	 		}else{
	 				LOGGER.debug("Selcted row only : "+ (selectedRow+row-f));
	 				StateCityInfo si = stateCityInfos.get(selectedRow+row-f);
	 				LOGGER.debug("Selected Row : "+si);
	 				try{
	 					StateCityDaoImpl.getInstance().disable(si);
	 				}catch(Exception e){
	 					LOGGER.error(e);
	 				}
	 		}
	 	}
	 	try{
	 		stateCityInfos = StateCityDaoImpl.getInstance().getItemList();
	 	}catch(Exception e){
				LOGGER.error(e);
		}
	 	LOGGER.debug("Reloading statecityinfos ["+stateCityInfos.size()+"]");
	 	model.setStateCityInfos(stateCityInfos);
	 	table.setModel(model);
	 	LOGGER.info(" Successfully disabled stock items ");
	 	((AbstractTableModel)table.getModel()).fireTableDataChanged();
		table.repaint();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] End");
	}
	public void edit(){
		LeamonERP.stateCityUI.getButtonEdit().setEnabled(Boolean.TRUE);
		LeamonERP.stateCityUI.getButtonSave().setEnabled(Boolean.TRUE);
		view();
		
	}
	public void print(){
		
	}
	public void add(){
		final String METHOD_NAME = "view";
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside");
		if(!LeamonERP.stateCityUI.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.stateCityUI);
		}
		LeamonERP.stockItemManager.requestFocus();
		try {
			LeamonERP.stateCityUI.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] "+e1);
		}
		LeamonERP.stateCityUI.buttonClearClick(null);
		LeamonERP.stateCityUI.setVisible(true);
		LeamonERP.stateCityUI.getTextFieldCity().requestFocus();
		LeamonERP.stateCityUI.moveToFront();
		LeamonERP.stateCityUI.getButtonSave().setEnabled(Boolean.TRUE);
		SwingUtilities.updateComponentTreeUI(LeamonERP.stateCityUI);
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] End.");
	}
	
	public void refreshTable(){
		final String METHOD_NAME  = "refreshTable";
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside");
		
		List<StateCityInfo> stateCityInfos = new  ArrayList<StateCityInfo>();
		try{
			stateCityInfos = StateCityDaoImpl.getInstance().getItemList();
		}catch(Exception e){
				LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] : "+ e);
		}
		TableStateCityInfoModel model  = (TableStateCityInfoModel)table.getModel();
		model.setStateCityInfos(stateCityInfos);
	 	table.setModel(model);
	 	((AbstractTableModel)table.getModel()).fireTableDataChanged();
		table.repaint();
		
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] End.");
	}
}

