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
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXTable;

import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.StockItem;
import leamon.erp.ui.event.FocusEventHandler;
import leamon.erp.ui.event.KeyListenerHandler;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

/**
 * @author Ghanshyam Singla
 * @date 24, Feb, 2018
 */
@Getter
public class StockItemTrash extends JInternalFrame implements ActionListener {

	static final Logger LOGGER = Logger.getLogger(StockItemListManager.class);

	// private JTable tblStockList;
	private JXTable tblStockList;
	private JXSearchField textSearchField;

	public StockItemTrash() {
		setTitle("Stock Item Trash");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(3, 30, 1200, 660);

		// tblStockList = new LeamonTable();
		tblStockList = new JXTable();

		StockDaoImpl daoImpl = StockDaoImpl.getInstance();
		List<StockItem> stockItems = new ArrayList<StockItem>();
		try {
			stockItems = daoImpl.getDeletedStockItems();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		TableStockListItemModel stockListItemModel = new TableStockListItemModel(stockItems);
		tblStockList.setModel(stockListItemModel);
		tblStockList.setRowHeight(tblStockList.getRowHeight() + 20);
		tblStockList.setAutoCreateRowSorter(true);
		tblStockList.setColumnControlVisible(true);
		tblStockList.packAll();
		tblStockList.setComponentPopupMenu(createPopup());
		tblStockList.setName(LeamonERPConstants.TABLE_STOCK_ITEMS);
		tblStockList.addKeyListener(new KeyListenerHandler(tblStockList));
		tblStockList.addMouseListener(new MouseClickHandler());

		JScrollPane scrollPaneTable = new JScrollPane(tblStockList);
		scrollPaneTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPaneTable.getViewport().setBackground(Color.WHITE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 255, 255), 2, true), "Operation",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1097, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(scrollPaneTable, GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
										.addGap(36)))
						.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(
										scrollPaneTable, GroupLayout.PREFERRED_SIZE, 526, GroupLayout.PREFERRED_SIZE))
								.addGap(32)));

		JButton btnRefresh = new JButton();
		btnRefresh.setBackground(Color.WHITE);
		try {
			btnRefresh.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_TOOLS_STOCK_TRASH_REFRESH)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		btnRefresh.setActionCommand(LeamonERPConstants.BUTTON_ACTION_REFRESH_STOCK_ITEM);
		btnRefresh.addActionListener(this);

		JButton btnRestore = new JButton();
		btnRestore.setBackground(Color.WHITE);
		try {
			btnRestore.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_TOOLS_STOCK_TRASH_RESTORE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		btnRestore.setActionCommand(LeamonERPConstants.BUTTON_ACTION_RESTORE_STOCK_ITEM);
		btnRestore.addActionListener(this);

		JButton btnDelete = new JButton();
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setIcon(
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setActionCommand(LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM);
		btnDelete.addActionListener(this);
		// textSearchField = new JTextField();
		textSearchField = new JXSearchField("Search");
		textSearchField.setFont(new Font("Courier New", Font.BOLD, 36));
		textSearchField.setColumns(10);
		textSearchField.setName(LeamonERPConstants.TEXTFIELD_NAME_SOCK_ITEM_SERACH);
		textSearchField.addFocusListener(new FocusEventHandler(tblStockList));
		textSearchField.addKeyListener(new KeyListenerHandler());

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(btnRefresh).addGap(36)
						.addComponent(btnRestore).addGap(36).addComponent(btnDelete).addGap(36)
						.addComponent(textSearchField, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(btnRefresh)
								.addComponent(btnRestore).addComponent(btnDelete)
								.addComponent(textSearchField, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup().addContainerGap()))
						.addContainerGap()));
		panel.setLayout(gl_panel);

		getContentPane().setLayout(groupLayout);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton btn = (JButton) e.getSource();
			LOGGER.debug("StockItemList[actionPerformed] " + btn.getActionCommand() + " clicked");
			if (btn.getActionCommand() != null
					&& btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM)) {
				LOGGER.debug("StockItemList[actionPerformed] action command ["
						+ LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM + "] inside");
				deleteStockItem();
				LOGGER.debug("StockItemList[actionPerformed] action command ["
						+ LeamonERPConstants.BUTTON_ACTION_DELETE_STOCK_ITEM + "] end");
			} else if (btn.getActionCommand() != null
					&& btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_REFRESH_STOCK_ITEM)) {
				refreshStockTable();
			} else if (btn.getActionCommand() != null
					&& btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_RESTORE_STOCK_ITEM)) {
				restoreStockTable();
			}

			else if (e.getSource() instanceof JMenuItem) {
				JMenuItem menuItem = (JMenuItem) e.getSource();
				if (menuItem.getActionCommand() != null
						&& menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_DELETE_STOCK_ITEM)) {
					deleteStockItem();
				} else if (menuItem.getActionCommand() != null
						&& menuItem.getActionCommand().equals(LeamonERPConstants.MENU_ACTION_REFRESH_STOCK_ITEM)) {
					refreshStockTable();
				}
			}
		}

	}// end action command

	public JPopupMenu createPopup() {
		LOGGER.info("StockItemList[createPopup] inside");
		// new
		// ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON))
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete",
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));

		menuItemDelete.setActionCommand(LeamonERPConstants.MENU_ACTION_DELETE_STOCK_ITEM);
		menuItemDelete.addActionListener(this);
		menuItemRefresh.setActionCommand(LeamonERPConstants.MENU_ACTION_REFRESH_STOCK_ITEM);
		menuItemRefresh.addActionListener(this);

		popupMenu.add(menuItemRefresh);
		popupMenu.add(menuItemDelete);
		LOGGER.info("StockItemList[createPopup] end.");
		SwingUtilities.updateComponentTreeUI(this);
		return popupMenu;
	}

	private void deleteStockItem() {
		LOGGER.info("StockItemList[deleteStockItem] inside");
		TableStockListItemModel model = (TableStockListItemModel) tblStockList.getModel();

		int selectedRow = tblStockList.getSelectedRow();
		if (selectedRow == LeamonERPConstants.NO_ROW_SELECTED) {
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		int option = JOptionPane.showConfirmDialog(this, "Do you really want to delete permanently ?");
		if (option == JOptionPane.YES_OPTION) {
			if (tblStockList.getRowSorter() != null) {
				selectedRow = tblStockList.getRowSorter().convertRowIndexToModel(selectedRow);
				LOGGER.debug("Selcted row : " + selectedRow);
			}

			int selectedRows[] = tblStockList.getSelectedRows();
			IntStream.of(selectedRows).forEach(val -> {
				LOGGER.info("StockItemList[deleteStockItem] selected rows[" + val + "]");
			});
			List<StockItem> stockItems = model.getStockItems();
			int i = 0;
			int f = 0;
			for (int row : selectedRows) {
				if (i == 0) {
					f = row;
				}
				i++;
				if (row == selectedRow) {
					LOGGER.debug("Selcted row selectedRow equal: " + (row));
					StockItem si = stockItems.get(row);
					LOGGER.debug("Selected Row : " + si);
					try {
						StockDaoImpl.getInstance().delete(si);
					} catch (Exception e) {
						LOGGER.error(e);
					}
				} else {
					LOGGER.debug("Selcted row only : " + (selectedRow + row - f));
					StockItem si = stockItems.get(selectedRow + row - f);
					LOGGER.debug("Selected Row : " + si);
					try {
						StockDaoImpl.getInstance().delete(si);
					} catch (Exception e) {
						LOGGER.error(e);
					}
				}
			}
			try {
				stockItems = StockDaoImpl.getInstance().getDeletedStockItems();
			} catch (Exception e) {
				LOGGER.error(e);
			}
			LOGGER.debug("Reloading stockitems [" + stockItems.size() + "]");
			model.setStockItems(stockItems);
			tblStockList.setModel(model);
			LOGGER.info(" Successfully disabled stock items ");
			((AbstractTableModel) tblStockList.getModel()).fireTableDataChanged();
			tblStockList.repaint();
			SwingUtilities.updateComponentTreeUI(this);
			LOGGER.info("StockItemList[deleteStockItem] end");
		} else {
			refreshStockTable();
		}

	}

	public void refreshStockTable() {
		List<StockItem> stockItems = new ArrayList<StockItem>();
		try {
			stockItems = StockDaoImpl.getInstance().getDeletedStockItems();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		TableStockListItemModel model = (TableStockListItemModel) tblStockList.getModel();
		model.setStockItems(stockItems);
		tblStockList.setModel(model);
		((AbstractTableModel) tblStockList.getModel()).fireTableDataChanged();
		tblStockList.repaint();
		// SwingUtilities.updateComponentTreeUI(this);
	}

	private void restoreStockTable() {

		LOGGER.info("StockItemList[restoreStockItem] inside");
		TableStockListItemModel model = (TableStockListItemModel) tblStockList.getModel();

		int selectedRow = tblStockList.getSelectedRow();
		if (selectedRow == LeamonERPConstants.NO_ROW_SELECTED) {
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		int option = JOptionPane.showConfirmDialog(this, "Do you really want to restore ?");
		if (option == JOptionPane.YES_OPTION) {
			if (tblStockList.getRowSorter() != null) {
				selectedRow = tblStockList.getRowSorter().convertRowIndexToModel(selectedRow);
				LOGGER.debug("Selcted row : " + selectedRow);
			}

			int selectedRows[] = tblStockList.getSelectedRows();
			IntStream.of(selectedRows).forEach(val -> {
				LOGGER.info("StockItemList[deleteStockItem] selected rows[" + val + "]");
			});
			List<StockItem> stockItems = model.getStockItems();
			int i = 0;
			int f = 0;
			for (int row : selectedRows) {
				if (i == 0) {
					f = row;
				}
				i++;
				if (row == selectedRow) {
					LOGGER.debug("Selcted row selectedRow equal: " + (row));
					StockItem si = stockItems.get(row);
					LOGGER.debug("Selected Row : " + si);
					try {
						StockDaoImpl.getInstance().updateByID(si);
					} catch (Exception e) {
						LOGGER.error(e);
					}
				} else {
					LOGGER.debug("Selcted row only : " + (selectedRow + row - f));
					StockItem si = stockItems.get(selectedRow + row - f);
					LOGGER.debug("Selected Row : " + si);
					try {
						StockDaoImpl.getInstance().updateByID(si);
					} catch (Exception e) {
						LOGGER.error(e);
					}
				}
			}
			try {
				stockItems = StockDaoImpl.getInstance().getDeletedStockItems();
			} catch (Exception e) {
				LOGGER.error(e);
			}
			LOGGER.debug("Reloading stockitems [" + stockItems.size() + "]");
			model.setStockItems(stockItems);
			tblStockList.setModel(model);
			LOGGER.info(" Successfully restored stock items ");
			((AbstractTableModel) tblStockList.getModel()).fireTableDataChanged();
			tblStockList.repaint();
			SwingUtilities.updateComponentTreeUI(this);
			LOGGER.info("StockItemList[restoreStockItem] end");
		} else {
			refreshStockTable();
		}

	}
}
