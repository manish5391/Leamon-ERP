package leamon.erp.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;

import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.StockItem;
import leamon.erp.report.factory.InvoicePrintFactory;
import leamon.erp.report.factory.StockItemListFactory;
import leamon.erp.ui.custom.StockItemListColorRenderer;
import leamon.erp.ui.event.FocusEventHandler;
import leamon.erp.ui.event.KeyListenerHandler;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.report.factory.InvoicePrintFactory;
import lombok.Getter;
/**
 * @author Manish Kumar Mishra
 * @date 3 May, 2017
 */
@Getter
public class StockItemListManager extends JInternalFrame implements ActionListener{
	
	static final Logger LOGGER = Logger.getLogger(StockItemListManager.class);
	
	//private JTable tblStockList;
	private JXTable tblStockList;
	private JXSearchField textSearchField;
	private JLabel lblImage;
	
	private JButton btnView;
	
	public StockItemListManager() {
		setTitle("Stock Item List");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(3, 30, 1200, 660);
		
		//tblStockList = new LeamonTable();
		tblStockList = new JXTable();
        
		StockDaoImpl daoImpl = StockDaoImpl.getInstance();
		List<StockItem> stockItems = new ArrayList<StockItem>();
		try{
		stockItems = daoImpl.getItemListWithQuantity();
		}catch(Exception e){
			LOGGER.error(e);
		}
		TableStockListItemModel stockListItemModel = new TableStockListItemModel(stockItems);
		tblStockList.setModel(stockListItemModel);
		tblStockList.setRowHeight(tblStockList.getRowHeight()+20);
		tblStockList.setAutoCreateRowSorter(true);
		tblStockList.setColumnControlVisible(true);
		tblStockList.packAll();
		tblStockList.setDefaultRenderer(Object.class, new StockItemListColorRenderer());//3.5 ghan code
		// 3.4 ghanshyam code for stock alignment
		setStockAlignment(tblStockList);
		// 3.4 end of ghanshyam code
		tblStockList.setComponentPopupMenu(createPopup());
		tblStockList.setName(LeamonERPConstants.TABLE_STOCK_ITEMS);
		tblStockList.addKeyListener(new KeyListenerHandler(tblStockList));
		tblStockList.addMouseListener(new MouseClickHandler());
		
		JScrollPane scrollPaneTable = new JScrollPane(tblStockList);
		scrollPaneTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneTable.getViewport().setBackground(Color.WHITE);
		
		JScrollPane scrollPaneImage = new JScrollPane();
		scrollPaneImage.setViewportBorder(new TitledBorder(new LineBorder(new Color(0, 255, 255), 2, true), "Item Image", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		scrollPaneImage.getViewport().setBackground(Color.WHITE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 255, 255), 2, true), "Operation", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1097, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPaneTable, GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
							.addGap(36)
							.addComponent(scrollPaneImage, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneImage, GroupLayout.PREFERRED_SIZE, 516, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneTable, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE))
					.addGap(32))
		);
		
		JButton btnAddItem = new JButton();
		btnAddItem.setBackground(Color.WHITE);
		btnAddItem.setActionCommand(LeamonERPConstants.BUTTON_ACTION_ADD_STOCK_ITEM);
		btnAddItem.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_ADD_BUTTON)));
		btnAddItem.addActionListener(this);
		
		JButton btnedit = new JButton();
		btnedit.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON)));
		btnedit.setActionCommand(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM);
		btnedit.addActionListener(this);
		btnedit.setBackground(Color.WHITE);
		
		btnView = new JButton();
		btnView.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_VIEW_BUTTON)));
		btnView.setActionCommand(LeamonERPConstants.BUTTON_ACTION_VIEW_STOCK_ITEM);
		btnView.addActionListener(this);
		btnView.setBackground(Color.WHITE);
		
		JButton btnDelete = new JButton();
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setActionCommand(LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM);
		btnDelete.addActionListener(this);
		
		// 3.5 Ghanshyam code for print button
		JButton btnPrint = new JButton();
		btnPrint.setBackground(Color.WHITE);
		btnPrint.setIcon(
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_PRINT_BUTTON)));
		btnPrint.setActionCommand(LeamonERPConstants.BUTTON_ACTION_PRINT_STOCK_ITEM);
		btnPrint.addActionListener(this);
		// 3.5 end of ghanshyam code
		//textSearchField = new JTextField();
		textSearchField = new JXSearchField("Search");
		textSearchField.setFont(new Font("Courier New", Font.BOLD, 36));
		textSearchField.setColumns(10);
		textSearchField.setName(LeamonERPConstants.TEXTFIELD_NAME_SOCK_ITEM_SERACH);
		textSearchField.addFocusListener(new FocusEventHandler(tblStockList));
		textSearchField.addKeyListener(new KeyListenerHandler());
		
		JXHyperlink hprlnkAddStockQuantity = new JXHyperlink();
		hprlnkAddStockQuantity.addActionListener(e -> hprlnkAddStockQuantityClick(e) );
		hprlnkAddStockQuantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		hprlnkAddStockQuantity.setText("add stock quantity");
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAddItem)
					.addGap(26)
					.addComponent(btnedit)
					.addGap(33)
					.addComponent(btnView)
					.addGap(28)
					.addComponent(btnDelete)
					.addGap(36)
					.addComponent(btnPrint)
					.addGap(28)
					.addComponent(textSearchField, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(hprlnkAddStockQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAddItem)
						.addComponent(btnedit)
						.addComponent(btnView)
						.addComponent(btnDelete)
						.addComponent(btnPrint)
						.addComponent(textSearchField, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(hprlnkAddStockQuantity, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		lblImage = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		scrollPaneImage.setViewportView(lblImage);
		
		getContentPane().setLayout(groupLayout);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton ){
			JButton btn = (JButton) e.getSource();
			LOGGER.debug("StockItemList[actionPerformed] "+btn.getActionCommand()+" clicked");
			if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_ADD_STOCK_ITEM)){
				LOGGER.info("StockItemList[actionPerformed] "+LeamonERPConstants.BUTTON_ACTION_ADD_STOCK_ITEM+" inside");
				addStockItem();
				LOGGER.info("StockItemList[actionPerformed] "+LeamonERPConstants.BUTTON_ACTION_ADD_STOCK_ITEM+" end");
			}else if(btn.getActionCommand() !=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_VIEW_STOCK_ITEM)){
				LOGGER.debug("StockItemList[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_VIEW_STOCK_ITEM+"] inside");
				viewStockItem(LeamonERPConstants.BUTTON_ACTION_VIEW_STOCK_ITEM);
				LOGGER.debug("StockItemList[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_VIEW_STOCK_ITEM+"] end");
			}else if(btn.getActionCommand()!= null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM)){
				LOGGER.debug("StockItemList[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM+"] inside");
				deleteStockItem();
				LOGGER.debug("StockItemList[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM+"] end");
			}else if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM)){
				LOGGER.debug("StockItemList[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM+"] inside");
				viewStockItem(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM);
				LOGGER.debug("StockItemList[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM+"] end");
			}else if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_PRINT_STOCK_ITEM)){
				print();
			}
		}// end source button 
		else if (e.getSource() instanceof JMenuItem){  
			JMenuItem menuItem = (JMenuItem) e.getSource();
			if(menuItem.getActionCommand()!=null && menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_DELETE_STOCK_ITEM)){
			 	deleteStockItem();
			}else if(menuItem.getActionCommand()!=null && menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_REFRESH_STOCK_ITEM)){
				refreshStockTable();
			}else if(menuItem.getActionCommand()!=null && menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_VIEW_STOCK_ITEM)){
				viewStockItem(LeamonERPConstants.MENU_ACTION_VIEW_STOCK_ITEM);
			}
		}
			
		
		
	}// end action command
	
	public JPopupMenu createPopup(){
		LOGGER.info("StockItemList[createPopup] inside");
//		new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON))
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete", new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		JMenuItem menuItemView = new JMenuItem("View",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_VIEW)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));
		
		menuItemDelete.setActionCommand(LeamonERPConstants.MENU_ACTION_DELETE_STOCK_ITEM);
		menuItemDelete.addActionListener(this);
		menuItemView.setActionCommand(LeamonERPConstants.MENU_ACTION_VIEW_STOCK_ITEM);
		menuItemView.addActionListener(this);
		menuItemRefresh.setActionCommand(LeamonERPConstants.MENU_ACTION_REFRESH_STOCK_ITEM);
		menuItemRefresh.addActionListener(this);
		
		popupMenu.add(menuItemRefresh);
		popupMenu.add(menuItemView);
		popupMenu.add(menuItemDelete);
		LOGGER.info("StockItemList[createPopup] end.");
		SwingUtilities.updateComponentTreeUI(this);
		return popupMenu;
	}
	
	public void viewStockItem(String actionCommand){
		LOGGER.info("StockItemList[viewStockItem] inside");
		int selectedRow = tblStockList.getSelectedRow();
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		/*Get accurate selected row after filtering records*/
		if(tblStockList.getRowSorter() != null){
			selectedRow = tblStockList.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		
		TableStockListItemModel model  = (TableStockListItemModel)tblStockList.getModel();
		List<StockItem> stockItems =  model.getStockItems();
		StockItem si = stockItems.get(selectedRow);
		
		if(!LeamonERP.stockItemManager.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.stockItemManager);
		}
		LeamonERP.stockItemManager.requestFocus();
		try {
			LeamonERP.stockItemManager.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error("StockItemList[actionPerformed] "+e1);
		}
		LeamonERP.stockItemManager.setStockItem(si);
		if(actionCommand.equals(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM)){
			LeamonERP.stockItemManager.getBtnSave().setEnabled(false);
		}
		LeamonERP.stockItemManager.setVisible(true);
		LeamonERP.stockItemManager.moveToFront();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info("StockItemList[viewStockItem] end");
	}
	
	private void deleteStockItem(){
		LOGGER.info("StockItemList[deleteStockItem] inside");
		TableStockListItemModel model  = (TableStockListItemModel)tblStockList.getModel();

		int selectedRow = tblStockList.getSelectedRow();
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
	 	if(tblStockList.getRowSorter() != null){
			selectedRow = tblStockList.getRowSorter().convertRowIndexToModel(selectedRow);
			LOGGER.debug("Selcted row : "+ selectedRow);
		}
	 	
	 	int selectedRows[] = tblStockList.getSelectedRows();
	 	IntStream.of(selectedRows).forEach(val -> {
	 		LOGGER.info("StockItemList[deleteStockItem] selected rows["+val+"]");
	 	});
	 	List<StockItem> stockItems =  model.getStockItems();
	 	int i=0;
	 	int f=0;
	 	for(int row  : selectedRows){
	 		if(i==0){
	 			f=row;
	 		}
	 		i++;
	 		if(row == selectedRow ){
	 			LOGGER.debug("Selcted row selectedRow equal: "+ (row));
 				StockItem si = stockItems.get(row);
 				LOGGER.debug("Selected Row : "+si);
 				try{
 				StockDaoImpl.getInstance().disable(si);
 				}catch(Exception e){
 					LOGGER.error(e);
 				}
	 		}else{
	 				LOGGER.debug("Selcted row only : "+ (selectedRow+row-f));
	 				StockItem si = stockItems.get(selectedRow+row-f);
	 				LOGGER.debug("Selected Row : "+si);
	 				try{
	 				StockDaoImpl.getInstance().disable(si);
	 				}catch(Exception e){
	 					LOGGER.error(e);
	 				}
	 		}
	 	}
	 	try{
	 	stockItems = StockDaoImpl.getInstance().getItemListWithQuantity();
	 	}catch(Exception e){
				LOGGER.error(e);
		}
	 	LOGGER.debug("Reloading stockitems ["+stockItems.size()+"]");
	 	model.setStockItems(stockItems);
	 	tblStockList.setModel(model);
	 	tblStockList.setDefaultRenderer(Object.class, new StockItemListColorRenderer());//3.5 ghan code
	 	LOGGER.info(" Successfully disabled stock items ");
	 	((AbstractTableModel)tblStockList.getModel()).fireTableDataChanged();
		tblStockList.repaint();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info("StockItemList[deleteStockItem] end");
	}
	
	public void addStockItem(){
		LOGGER.info("StockItemList[addStockItem] inside");
		if(!LeamonERP.stockItemManager.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.stockItemManager);
		}
		LeamonERP.stockItemManager.requestFocus();
		try {
			LeamonERP.stockItemManager.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error("StockItemList[actionPerformed] "+e1);
		}
		LeamonERP.stockItemManager.clear();
		LeamonERP.stockItemManager.setVisible(true);
		LeamonERP.stockItemManager.getTxtName().requestFocus();
		LeamonERP.stockItemManager.getLblStockItemImage().setText(LeamonERPConstants.EMPTY_STR);
		LeamonERP.stockItemManager.moveToFront();
		SwingUtilities.updateComponentTreeUI(LeamonERP.stockItemManager);
		LOGGER.info("StockItemList[addStockItem] end");
	}
	
	public void refreshStockTable(){
		List<StockItem> stockItems = new  ArrayList<StockItem>();
		try{
		stockItems = StockDaoImpl.getInstance().getItemListWithQuantity();
		}catch(Exception e){
				LOGGER.error(e);
		}
		TableStockListItemModel model  = (TableStockListItemModel)tblStockList.getModel();
		model.setStockItems(stockItems);
	 	tblStockList.setModel(model);
	 	tblStockList.setDefaultRenderer(Object.class, new StockItemListColorRenderer());//3.5 ghan code
	 	((AbstractTableModel)tblStockList.getModel()).fireTableDataChanged();
		tblStockList.repaint();
		//SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void hprlnkAddStockQuantityClick(ActionEvent e){
		LOGGER.info("StockItemList[hprlnkAddStockQuantityClick] inside");
		int selectedRow = tblStockList.getSelectedRow();
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			//JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			if(!LeamonERP.stockItemQuantityUI.isVisible()){
				LeamonERP.desktopPane.add(LeamonERP.stockItemQuantityUI);
			}
			LeamonERP.stockItemQuantityUI.requestFocus();
			try {
				LeamonERP.stockItemQuantityUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error("StockItemList[actionPerformed] "+e1);
			}
			
			LeamonERP.stockItemQuantityUI.setVisible(true);
			LeamonERP.stockItemQuantityUI.moveToFront();
			SwingUtilities.updateComponentTreeUI(this);
			return;
		}
		
		/*Get accurate selected row after filtering records*/
		if(tblStockList.getRowSorter() != null){
			selectedRow = tblStockList.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		
		TableStockListItemModel model  = (TableStockListItemModel)tblStockList.getModel();
		List<StockItem> stockItems =  model.getStockItems();
		StockItem si = stockItems.get(selectedRow);
		
		if(!LeamonERP.stockItemQuantityUI.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.stockItemQuantityUI);
		}
		LeamonERP.stockItemQuantityUI.requestFocus();
		try {
			LeamonERP.stockItemQuantityUI.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error("StockItemList[actionPerformed] "+e1);
		}
		LeamonERP.stockItemQuantityUI.setStockItem(si);
		/*if(actionCommand.equals(LeamonERPConstants.BUTTON_ACTION_EDIT_STOCK_ITEM)){
			LeamonERP.stockItemQuantityUI.getBtnSave().setEnabled(false);
		}*/
		LeamonERP.stockItemQuantityUI.setVisible(true);
		LeamonERP.stockItemQuantityUI.moveToFront();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info("StockItemList[viewStockItem] end");
	}
	
	// 3.4 ghanshyam code for stock alignment
	private void setStockAlignment(JXTable tblStockList) {
		tblStockList.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = tblStockList.getColumnModel();
		DefaultTableCellRenderer dtcr1 = new DefaultTableCellRenderer();
		dtcr1.setHorizontalAlignment(SwingConstants.RIGHT);
		columnModel.getColumn(4).setCellRenderer(dtcr1);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.LEFT);
		columnModel.getColumn(5).setCellRenderer(dtcr);
	}
	// 3.4 end of ghanshyam code
	// 3.5 ghanshyam code for print
	private void print() {
		StockItemListFactory stockItemListFactory = new StockItemListFactory(this);
		stockItemListFactory.print();
	}
	// 3.5 end of ghanshyam code for print
}
