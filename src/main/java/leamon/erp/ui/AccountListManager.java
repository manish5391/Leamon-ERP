package leamon.erp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;

import leamon.erp.db.AccountDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.ui.event.FocusEventHandler;
import leamon.erp.ui.event.KeyListenerHandler;
import leamon.erp.ui.event.ListSelectionListenerHandler;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.model.TableAccountInfoListModel;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

@Getter
public class AccountListManager extends JInternalFrame implements ActionListener{
	
	static final Logger LOGGER = Logger.getLogger(AccountListManager.class);

	private JToolBar toolBar;
	private JPanel panel;
	
	private JButton btnAddItem;
	private JButton btnedit;
	private JButton btnView;
	private JButton btnDelete;
	private JXSearchField textSearchField;
	//private LeamonTable leamonTableAccountInfo;
	private JXTable leamonTableAccountInfo;
	private JLabel labelImage;
	
	public AccountListManager() {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		setTitle("Account Info List");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(3, 30, 1200, 660);
		
		leamonTableAccountInfo = new JXTable();
		AccountDaoImpl daoImpl = AccountDaoImpl.getInstance();
		List<AccountInfo> accountInfos = new ArrayList<>();
		try{
		 accountInfos = daoImpl.getItemList();
		}catch(Exception e){
			LOGGER.error(e);
		}
		TableAccountInfoListModel accountInfoListModel = new TableAccountInfoListModel(accountInfos);
		leamonTableAccountInfo.setModel(accountInfoListModel);
		leamonTableAccountInfo.setRowHeight(leamonTableAccountInfo.getRowHeight()+20);
		leamonTableAccountInfo.setAutoCreateRowSorter(true);
		leamonTableAccountInfo.setName(LeamonERPConstants.TABLE_ACCOUNT_INFO_LIST);
		leamonTableAccountInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		leamonTableAccountInfo.setComponentPopupMenu(createPopup());
		leamonTableAccountInfo.addKeyListener(new KeyListenerHandler(leamonTableAccountInfo));
		leamonTableAccountInfo.addMouseListener(new MouseClickHandler());
		leamonTableAccountInfo.getSelectionModel().addListSelectionListener(new ListSelectionListenerHandler(leamonTableAccountInfo));
		leamonTableAccountInfo.setColumnControlVisible(true);
		leamonTableAccountInfo.packAll();
		
		toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setBackground(new Color(173, 216, 230));
		toolBar.setToolTipText("Account Info Manager Toolbar");
		toolBar.setBackground(Color.WHITE);

		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		btnAddItem = new JButton();
		btnAddItem.setToolTipText("Add");
		btnAddItem.setBackground(Color.WHITE);
		btnAddItem.setActionCommand(LeamonERPConstants.BUTTON_ACTION_ADD_ACCOUNT_INFO);
		btnAddItem.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_ADD_BUTTON)));
		btnAddItem.addActionListener(this);
		toolBar.add(btnAddItem);
		toolBar.addSeparator();
		
		btnedit = new JButton();
		btnedit.setToolTipText("Edit");
		btnedit.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON)));
		btnedit.setActionCommand(LeamonERPConstants.BUTTON_ACTION_EDIT_ACCOUNT_INFO);
		btnedit.addActionListener(this);
		btnedit.setBackground(Color.WHITE);
		toolBar.add(btnedit);
		toolBar.addSeparator();
		
		btnView = new JButton();
		btnView.setToolTipText("View");
		btnView.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_VIEW_BUTTON)));
		btnView.setActionCommand(LeamonERPConstants.BUTTON_ACTION_VIEW_ACCOUNT_INFO);
		btnView.addActionListener(this);
		btnView.setBackground(Color.WHITE);
		toolBar.add(btnView);
		toolBar.addSeparator();
		
		btnDelete = new JButton();
		btnDelete.setToolTipText("Delete");
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setActionCommand(LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO);
		btnDelete.addActionListener(this);
		toolBar.add(btnDelete);
		toolBar.addSeparator();
		
		textSearchField = new JXSearchField("Search");
		textSearchField.setName(LeamonERPConstants.TEXTFIELD_NAME_ACCOUNT_INFO_SERACH);
		textSearchField.setFont(new Font("Courier New", Font.BOLD, 36));
		textSearchField.setColumns(5);
	//	textSearchField.addKeyListener(new KeyListenerHandler());
		textSearchField.addFocusListener(new FocusEventHandler(leamonTableAccountInfo));
		toolBar.add(textSearchField);
		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.YELLOW));
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPaneAccountInfoList = new JScrollPane((Component) null);
		scrollPaneAccountInfoList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneAccountInfoList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollPaneImage = new JScrollPane();
		scrollPaneImage.setViewportBorder(new TitledBorder(new LineBorder(new Color(0, 255, 255), 2, true), "Item Image", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneAccountInfoList, GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(scrollPaneImage, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneAccountInfoList, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneImage, GroupLayout.PREFERRED_SIZE, 516, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(54, Short.MAX_VALUE))
		);
		
		labelImage = new JLabel((Icon) null);
		scrollPaneImage.setViewportView(labelImage);
		scrollPaneAccountInfoList.setViewportView(leamonTableAccountInfo);
		panel.setLayout(gl_panel);
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton ){
			JButton btn = (JButton) e.getSource();
			LOGGER.debug("AccountListManager[actionPerformed] "+btn.getActionCommand()+" clicked");
			if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_ADD_ACCOUNT_INFO)){
				LOGGER.info("AccountListManager[actionPerformed] "+LeamonERPConstants.BUTTON_ACTION_ADD_ACCOUNT_INFO+" inside");
				addAccountInfo();
				LOGGER.info("AccountListManager[actionPerformed] "+LeamonERPConstants.BUTTON_ACTION_ADD_ACCOUNT_INFO+" end");
			}else if(btn.getActionCommand() !=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_VIEW_ACCOUNT_INFO)){
				LOGGER.debug("AccountListManager[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_VIEW_ACCOUNT_INFO+"] inside");
				viewAccountInfo(LeamonERPConstants.BUTTON_ACTION_VIEW_ACCOUNT_INFO);
				LOGGER.debug("AccountListManager[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_VIEW_ACCOUNT_INFO+"] end");
			}else if(btn.getActionCommand()!= null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO)){
				LOGGER.debug("AccountListManager[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO+"] inside");
				deleteAccountInfo();
				LOGGER.debug("AccountListManager[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO+"] end");
			}else if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_EDIT_ACCOUNT_INFO)){
				LOGGER.debug("AccountListManager[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_EDIT_ACCOUNT_INFO+"] inside");
				viewAccountInfo(LeamonERPConstants.BUTTON_ACTION_EDIT_ACCOUNT_INFO);
				LOGGER.debug("AccountListManager[actionPerformed] action command ["+LeamonERPConstants.BUTTON_ACTION_EDIT_ACCOUNT_INFO+"] end");
			}
		}// end source button 
		else if (e.getSource() instanceof JMenuItem){  
			JMenuItem menuItem = (JMenuItem) e.getSource();
			if(menuItem.getActionCommand()!=null && menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_DELETE_ACCOUNT_INFO)){
				deleteAccountInfo();
			}else if(menuItem.getActionCommand()!=null && menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_REFRESH_ACCOUNT_INFO)){
				refreshAccountInfoTable();
			}else if(menuItem.getActionCommand()!=null && menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_VIEW_ACCOUNT_INFO)){
				viewAccountInfo(LeamonERPConstants.MENU_ACTION_VIEW_ACCOUNT_INFO);
			}
		}
	}

	public JPopupMenu createPopup(){
		LOGGER.info("AccountListManager[createPopup] inside");
//		new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON))
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete", new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		JMenuItem menuItemView = new JMenuItem("View",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_VIEW)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));
		
		menuItemDelete.setActionCommand(LeamonERPConstants.MENU_ACTION_DELETE_ACCOUNT_INFO);
		menuItemDelete.addActionListener(this);
		menuItemView.setActionCommand(LeamonERPConstants.MENU_ACTION_VIEW_ACCOUNT_INFO);
		menuItemView.addActionListener(this);
		menuItemRefresh.setActionCommand(LeamonERPConstants.MENU_ACTION_REFRESH_ACCOUNT_INFO);
		menuItemRefresh.addActionListener(this);
		
		popupMenu.add(menuItemRefresh);
		popupMenu.add(menuItemView);
		popupMenu.add(menuItemDelete);
		LOGGER.info("AccountListManager[createPopup] end.");
		SwingUtilities.updateComponentTreeUI(this);
		return popupMenu;
	}

	private void deleteAccountInfo() {
		LOGGER.info("AccountListManager[deleteAccountInfo] inside");
		
		int op =JOptionPane.showConfirmDialog(this, "Are you sure ?","Leamon-ERP",JOptionPane.YES_NO_OPTION);
		
		if(! (op==JOptionPane.YES_OPTION)){
			return;
		}
		
		TableAccountInfoListModel model  = (TableAccountInfoListModel)leamonTableAccountInfo.getModel();

		int selectedRow = leamonTableAccountInfo.getSelectedRow();
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
	 	if(leamonTableAccountInfo.getRowSorter() != null){
			selectedRow = leamonTableAccountInfo.getRowSorter().convertRowIndexToModel(selectedRow);
			LOGGER.debug("Selcted row : "+ selectedRow);
		}
	 	
	 	int selectedRows[] = leamonTableAccountInfo.getSelectedRows();
	 	IntStream.of(selectedRows).forEach(val -> {
	 		LOGGER.info("AccountListManager[deleteAccountInfo] selected rows["+val+"]");
	 	});
	 	List<AccountInfo> accountInfos=  model.getAccountInfoItems();
	 	int i=0;
	 	int f=0;
	 	for(int row  : selectedRows){
	 		if(i==0){
	 			f=row;
	 		}
	 		i++;
	 		if(row == selectedRow ){
	 			LOGGER.debug("Selcted row selectedRow equal: "+ (row));
 				AccountInfo si = accountInfos.get(row);
 				LOGGER.debug("Selected Row : "+si);
 				try{
 				AccountDaoImpl.getInstance().disable(si);
 				}catch(Exception e){
 					LOGGER.error(e);
 				}
	 		}else{
	 				LOGGER.debug("Selcted row only : "+ (selectedRow+row-f));
	 				AccountInfo si = accountInfos.get(selectedRow+row-f);
	 				LOGGER.debug("Selected Row : "+si);
	 				try{
	 				AccountDaoImpl.getInstance().disable(si);
	 				}catch(Exception e){
	 					LOGGER.error(e);
	 				}
	 		}
	 	}
	 	try{
	 	accountInfos = AccountDaoImpl.getInstance().getItemList();
	 	}catch(Exception e){
			LOGGER.error(e);
		}
	 	LOGGER.debug("Reloading AccountInfos ["+accountInfos.size()+"]");
	 	model.setAccountInfoItems(accountInfos);
	 	leamonTableAccountInfo.setModel(model);
	 	LOGGER.info(" Successfully disabled Accoount Info items ");
	 	((AbstractTableModel)leamonTableAccountInfo.getModel()).fireTableDataChanged();
	 	leamonTableAccountInfo.repaint();
	 	SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info("AccountListManager[deleteAccountInfo] end");
	}

	public void viewAccountInfo(String actionCommand) {
		LOGGER.info("AccountListManager[viewAccountInfo] inside");
		int selectedRow = leamonTableAccountInfo.getSelectedRow();
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		/*Get accurate selected row after filtering records*/
		if(leamonTableAccountInfo.getRowSorter() != null){
			selectedRow = leamonTableAccountInfo.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		
		TableAccountInfoListModel model  = (TableAccountInfoListModel)leamonTableAccountInfo.getModel();
		List<AccountInfo> AccountInfos =  model.getAccountInfoItems();
		AccountInfo si = AccountInfos.get(selectedRow);
		
		if(!LeamonERP.accountInfoUI.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.accountInfoUI);
		}
		LeamonERP.accountInfoUI.requestFocus();
		try {
			LeamonERP.accountInfoUI.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error("AccountListManager[actionPerformed] "+e1);
		}
		LeamonERP.accountInfoUI.setAccountInfo(si);
		
		LeamonERP.accountInfoUI.setVisible(true);
		LeamonERP.accountInfoUI.getBtnSave().setEnabled(Boolean.FALSE);
		LeamonERP.accountInfoUI.moveToFront();
		if(actionCommand.equals(LeamonERPConstants.BUTTON_ACTION_EDIT_ACCOUNT_INFO)){
			LeamonERP.accountInfoUI.getBtnSave().setEnabled(false);
		}
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info("AccountListManager[viewAccountInfo] end");
	}

	private void addAccountInfo() {
		LOGGER.info("AccountListManager[addAccountInfo] inside");
		if(!LeamonERP.accountInfoUI.isVisible()){
			LeamonERP.desktopPane.add(LeamonERP.accountInfoUI);
		}
		LeamonERP.accountInfoUI.requestFocus();
		try {
			LeamonERP.accountInfoUI.setSelected(true);
		} catch (PropertyVetoException e1) {
			LOGGER.error("AccountListManager[actionPerformed] "+e1);
		}
		LeamonERP.accountInfoUI.getBtnClear().doClick();
		LeamonERP.accountInfoUI.getBtnSave().setEnabled(Boolean.TRUE);
		LeamonERP.accountInfoUI.getLblAcountImage().setText(LeamonERPConstants.EMPTY_STR);
		LeamonERP.accountInfoUI.setVisible(true);
		LeamonERP.accountInfoUI.moveToFront();
		SwingUtilities.updateComponentTreeUI(LeamonERP.accountInfoUI);
		LOGGER.info("AccountListManager[addAccountInfo] end");
	}

	public void refreshAccountInfoTable() {
		List<AccountInfo> accountInfos = new ArrayList<>(); 
		try{
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		}catch(Exception e){
			LOGGER.error(e);
		}
		
		TableAccountInfoListModel model  = (TableAccountInfoListModel)leamonTableAccountInfo.getModel();
		model.setAccountInfoItems(accountInfos);
		leamonTableAccountInfo.setModel(model);
	 	((AbstractTableModel)leamonTableAccountInfo.getModel()).fireTableDataChanged();
	 	leamonTableAccountInfo.repaint();
	 	SwingUtilities.updateComponentTreeUI(this);
	}
}
