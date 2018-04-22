package leamon.erp.ui.trash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
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
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;

import leamon.erp.db.AccountDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.ui.AccountListManager;
import leamon.erp.ui.event.FocusEventHandler;
import leamon.erp.ui.event.KeyListenerHandler;
import leamon.erp.ui.event.ListSelectionListenerHandler;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.model.TableAccountInfoListModel;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

/**
 * @author Ghanshyam Singla
 * @date 24, Feb, 2018
 */

@Getter
public class AccountTrashUI extends JInternalFrame implements ActionListener {

	static final Logger LOGGER = Logger.getLogger(AccountListManager.class);

	private JToolBar toolBar;
	private JPanel panel;

	private JButton btnRefresh;
	private JButton btnRestore;
	private JButton btnDelete;
	private JXSearchField textSearchField;
	// private LeamonTable leamonTableAccountInfo;
	private JXTable leamonTableAccountInfo;

	public AccountTrashUI() {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		setTitle("Account Trash");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(3, 30, 1200, 660);

		leamonTableAccountInfo = new JXTable();
		AccountDaoImpl daoImpl = AccountDaoImpl.getInstance();
		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = daoImpl.getDeletedAccount();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		TableAccountInfoListModel accountInfoListModel = new TableAccountInfoListModel(accountInfos);
		leamonTableAccountInfo.setModel(accountInfoListModel);
		leamonTableAccountInfo.setRowHeight(leamonTableAccountInfo.getRowHeight() + 20);
		leamonTableAccountInfo.setAutoCreateRowSorter(true);
		leamonTableAccountInfo.setName(LeamonERPConstants.TABLE_ACCOUNT_INFO_LIST);
		leamonTableAccountInfo.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		leamonTableAccountInfo.setComponentPopupMenu(createPopup());
		leamonTableAccountInfo.addKeyListener(new KeyListenerHandler(leamonTableAccountInfo));
		leamonTableAccountInfo.addMouseListener(new MouseClickHandler());
		leamonTableAccountInfo.getSelectionModel()
				.addListSelectionListener(new ListSelectionListenerHandler(leamonTableAccountInfo));
		leamonTableAccountInfo.setColumnControlVisible(true);
		leamonTableAccountInfo.packAll();

		toolBar = new JToolBar();
		toolBar.setRollover(true);
		toolBar.setBackground(new Color(173, 216, 230));
		toolBar.setToolTipText("Account Info Manager Toolbar");
		toolBar.setBackground(Color.WHITE);

		getContentPane().add(toolBar, BorderLayout.NORTH);

		btnRefresh = new JButton();
		btnRefresh.setToolTipText("Refresh");
		btnRefresh.setIcon(
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON)));
		btnRefresh.setActionCommand(LeamonERPConstants.BUTTON_ACTION_REFRESH_ACCOUNT_TRASH);
		btnRefresh.addActionListener(this);
		btnRefresh.setBackground(Color.WHITE);
		try {
			btnRefresh.setIcon(new ImageIcon(LeamonERPConstants.IMAGE_PATH_LEAMON_ERP
					.concat(LeamonERPConstants.IMG_TOOLS_ACCOUNT_TRASH_REFRESH)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		toolBar.add(btnRefresh);
		toolBar.addSeparator();

		btnRestore = new JButton();
		btnRestore.setToolTipText("Restore");
		btnRestore.setIcon(
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_VIEW_BUTTON)));
		btnRestore.setActionCommand(LeamonERPConstants.BUTTON_ACTION_RESTORE_ACCOUNT_TRASH);
		btnRestore.addActionListener(this);
		btnRestore.setBackground(Color.WHITE);
		try {
			btnRestore.setIcon(new ImageIcon(LeamonERPConstants.IMAGE_PATH_LEAMON_ERP
					.concat(LeamonERPConstants.IMG_TOOLS_ACCOUNT_TRASH_RESTORE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		toolBar.add(btnRestore);
		toolBar.addSeparator();

		btnDelete = new JButton();
		btnDelete.setToolTipText("Delete");
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setIcon(
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setActionCommand(LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO);
		btnDelete.addActionListener(this);
		toolBar.add(btnDelete);
		toolBar.addSeparator();

		textSearchField = new JXSearchField("Search");
		textSearchField.setName(LeamonERPConstants.TEXTFIELD_NAME_ACCOUNT_INFO_SERACH);
		textSearchField.setFont(new Font("Courier New", Font.BOLD, 36));
		textSearchField.setColumns(5);
		// textSearchField.addKeyListener(new KeyListenerHandler());
		textSearchField.addFocusListener(new FocusEventHandler(leamonTableAccountInfo));
		toolBar.add(textSearchField);

		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.CYAN, Color.YELLOW));
		getContentPane().add(panel, BorderLayout.CENTER);

		JScrollPane scrollPaneAccountInfoList = new JScrollPane((Component) null);
		scrollPaneAccountInfoList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneAccountInfoList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(scrollPaneAccountInfoList, GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(
								scrollPaneAccountInfoList, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(54, Short.MAX_VALUE)));
		scrollPaneAccountInfoList.setViewportView(leamonTableAccountInfo);
		panel.setLayout(gl_panel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			LOGGER.debug("AccountListManager[actionPerformed] " + btn.getActionCommand() + " clicked");
			if (btn.getActionCommand() != null
					&& btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO)) {
				LOGGER.debug("AccountListManager[actionPerformed] action command ["
						+ LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO + "] inside");
				deleteAccountInfo();
				LOGGER.debug("AccountListManager[actionPerformed] action command ["
						+ LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO + "] end");
			} else if (btn.getActionCommand() != null
					&& btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_REFRESH_ACCOUNT_TRASH)) {
				refreshAccountInfoTable();
			} else if (btn.getActionCommand() != null
					&& btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_RESTORE_ACCOUNT_TRASH)) {
				restoreAccountInfoTable();
			}
		} // end source button
		else if (e.getSource() instanceof JMenuItem) {
			JMenuItem menuItem = (JMenuItem) e.getSource();
			if (menuItem.getActionCommand() != null
					&& menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_DELETE_ACCOUNT_INFO)) {
				deleteAccountInfo();
			} else if (menuItem.getActionCommand() != null
					&& menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_REFRESH_ACCOUNT_INFO)) {
				refreshAccountInfoTable();
			}
		}
	}

	public JPopupMenu createPopup() {
		LOGGER.info("AccountListManager[createPopup] inside");
		// new
		// ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON))
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete",
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));

		menuItemDelete.setActionCommand(LeamonERPConstants.MENU_ACTION_DELETE_ACCOUNT_INFO);
		menuItemDelete.addActionListener(this);
		menuItemRefresh.setActionCommand(LeamonERPConstants.MENU_ACTION_REFRESH_ACCOUNT_INFO);
		menuItemRefresh.addActionListener(this);

		popupMenu.add(menuItemRefresh);
		popupMenu.add(menuItemDelete);
		LOGGER.info("AccountListManager[createPopup] end.");
		SwingUtilities.updateComponentTreeUI(this);
		return popupMenu;
	}

	private void deleteAccountInfo() {
		LOGGER.info("AccountListManager[deleteAccountInfo] inside");

		int selectedRow = leamonTableAccountInfo.getSelectedRow();
		if (selectedRow == LeamonERPConstants.NO_ROW_SELECTED) {
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int op = JOptionPane.showConfirmDialog(this, "Are you sure to delete it permanently ?", "Leamon-ERP",
				JOptionPane.YES_NO_OPTION);

		if (!(op == JOptionPane.YES_OPTION)) {
			return;
		}

		TableAccountInfoListModel model = (TableAccountInfoListModel) leamonTableAccountInfo.getModel();

		if (leamonTableAccountInfo.getRowSorter() != null) {
			selectedRow = leamonTableAccountInfo.getRowSorter().convertRowIndexToModel(selectedRow);
			LOGGER.debug("Selcted row : " + selectedRow);
		}

		int selectedRows[] = leamonTableAccountInfo.getSelectedRows();
		IntStream.of(selectedRows).forEach(val -> {
			LOGGER.info("AccountListManager[deleteAccountInfo] selected rows[" + val + "]");
		});
		List<AccountInfo> accountInfos = model.getAccountInfoItems();
		int i = 0;
		int f = 0;
		for (int row : selectedRows) {
			if (i == 0) {
				f = row;
			}
			i++;
			if (row == selectedRow) {
				LOGGER.debug("Selcted row selectedRow equal: " + (row));
				AccountInfo si = accountInfos.get(row);
				LOGGER.debug("Selected Row : " + si);
				try {
					AccountDaoImpl.getInstance().delete(si);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			} else {
				LOGGER.debug("Selcted row only : " + (selectedRow + row - f));
				AccountInfo si = accountInfos.get(selectedRow + row - f);
				LOGGER.debug("Selected Row : " + si);
				try {
					AccountDaoImpl.getInstance().delete(si);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		try {
			accountInfos = AccountDaoImpl.getInstance().getDeletedAccount();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LOGGER.debug("Reloading AccountInfos [" + accountInfos.size() + "]");
		model.setAccountInfoItems(accountInfos);
		leamonTableAccountInfo.setModel(model);
		LOGGER.info(" Successfully disabled Accoount Info items ");
		((AbstractTableModel) leamonTableAccountInfo.getModel()).fireTableDataChanged();
		leamonTableAccountInfo.repaint();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info("AccountListManager[deleteAccountInfo] end");
	}

	public void refreshAccountInfoTable() {
		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getDeletedAccount();
		} catch (Exception e) {
			LOGGER.error(e);
		}

		TableAccountInfoListModel model = (TableAccountInfoListModel) leamonTableAccountInfo.getModel();
		model.setAccountInfoItems(accountInfos);
		leamonTableAccountInfo.setModel(model);
		((AbstractTableModel) leamonTableAccountInfo.getModel()).fireTableDataChanged();
		leamonTableAccountInfo.repaint();
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void restoreAccountInfoTable() {

		LOGGER.info("AccountListManager[restoreAccountInfoTable] inside");

		int selectedRow = leamonTableAccountInfo.getSelectedRow();
		if (selectedRow == LeamonERPConstants.NO_ROW_SELECTED) {
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int op = JOptionPane.showConfirmDialog(this, "Are you sure to restore this ?", "Leamon-ERP",
				JOptionPane.YES_NO_OPTION);

		if (!(op == JOptionPane.YES_OPTION)) {
			return;
		}

		TableAccountInfoListModel model = (TableAccountInfoListModel) leamonTableAccountInfo.getModel();

		if (leamonTableAccountInfo.getRowSorter() != null) {
			selectedRow = leamonTableAccountInfo.getRowSorter().convertRowIndexToModel(selectedRow);
			LOGGER.debug("Selcted row : " + selectedRow);
		}

		int selectedRows[] = leamonTableAccountInfo.getSelectedRows();
		IntStream.of(selectedRows).forEach(val -> {
			LOGGER.info("AccountListManager[deleteAccountInfo] selected rows[" + val + "]");
		});
		List<AccountInfo> accountInfos = model.getAccountInfoItems();
		int i = 0;
		int f = 0;
		for (int row : selectedRows) {
			if (i == 0) {
				f = row;
			}
			i++;
			if (row == selectedRow) {
				LOGGER.debug("Selcted row selectedRow equal: " + (row));
				AccountInfo si = accountInfos.get(row);
				LOGGER.debug("Selected Row : " + si);
				try {
					AccountDaoImpl.getInstance().updateByID(si);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			} else {
				LOGGER.debug("Selcted row only : " + (selectedRow + row - f));
				AccountInfo si = accountInfos.get(selectedRow + row - f);
				LOGGER.debug("Selected Row : " + si);
				try {
					AccountDaoImpl.getInstance().updateByID(si);
				} catch (Exception e) {
					LOGGER.error(e);
				}
			}
		}
		try {
			accountInfos = AccountDaoImpl.getInstance().getDeletedAccount();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		LOGGER.debug("Reloading AccountInfos [" + accountInfos.size() + "]");
		model.setAccountInfoItems(accountInfos);
		leamonTableAccountInfo.setModel(model);
		LOGGER.info(" Successfully restored Accoount Info items ");
		((AbstractTableModel) leamonTableAccountInfo.getModel()).fireTableDataChanged();
		leamonTableAccountInfo.repaint();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info("AccountListManager[restoreAccountInfoTable] end");

	}
}
