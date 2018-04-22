package leamon.erp.ui.trash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.AccountListManager;
import leamon.erp.ui.event.KeyListenerHandler;
import leamon.erp.ui.event.MouseClickHandler;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.ui.model.TableWinvoiceTrashModel;
import leamon.erp.ui.model.TableWinvoiceTrashModel;
import leamon.erp.util.LeamonERPConstants;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import org.jdesktop.swingx.JXSearchField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTable;

public class WinvoiceTrash extends JInternalFrame {

	static final Logger LOGGER = Logger.getLogger(WinvoiceTrash.class);
	
	private JXTable tableInvoice;
	public WinvoiceTrash() {
		
		setTitle("W-Invoice Trash");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(3, 30, 1200, 660);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 255, 255), 2, true), "Operation",
						TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1174, 76);
		getContentPane().add(panel);
		
		JButton btnRefresh = new JButton();
		btnRefresh.setBackground(Color.WHITE);
		try {
			btnRefresh.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_TOOLS_STOCK_TRASH_REFRESH)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		btnRefresh.addActionListener(e -> btnRefreshClick(e));
		btnRefresh.setToolTipText("Refresh");
		
		JButton btnRestore = new JButton();
		btnRestore.setBackground(Color.WHITE);
		try {
			btnRestore.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_TOOLS_STOCK_TRASH_RESTORE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		btnRestore.addActionListener(e -> btnRestoreClick(e));
		btnRestore.setToolTipText("Restore");
		
		JButton btnDelete = new JButton();
		btnDelete.setBackground(Color.WHITE);
		try {
		btnDelete.setIcon(
				new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		}catch (Exception e) {
			LOGGER.error(e);
		}
		btnDelete.addActionListener(e -> btnDeleteClick(e));
		btnDelete.setToolTipText("Permanent Delete");
		
		JXSearchField textSearchField = new JXSearchField("Search");
		textSearchField.setName("textSearchField");
		textSearchField.setFont(new Font("Courier New", Font.BOLD, 36));
		textSearchField.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(btnRefresh)
					.addGap(36)
					.addComponent(btnRestore)
					.addGap(36)
					.addComponent(btnDelete)
					.addGap(36)
					.addComponent(textSearchField, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnRefresh)
				.addComponent(btnRestore)
				.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
					.addComponent(btnDelete, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
					.addComponent(textSearchField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 84, 1138, 526);
		getContentPane().add(scrollPane);
		
		tableInvoice = new JXTable();
		scrollPane.setViewportView(tableInvoice);
		
		List<InvoiceInfo>  invoiceInfos = InvoiceDaoImpl.getInstance().getItemDisabledOnlyListWithInvoiceItemListAndAccountInfo();
		TableWinvoiceTrashModel tableWinvoiceTrashModel  = new TableWinvoiceTrashModel(invoiceInfos);
		tableInvoice.setModel(tableWinvoiceTrashModel);
		tableInvoice.setRowHeight(tableInvoice.getRowHeight() + 20);
		tableInvoice.setAutoCreateRowSorter(true);
		tableInvoice.setColumnControlVisible(true);
		tableInvoice.packAll();
		//tableInvoice.setComponentPopupMenu(createPopup());
		//tableInvoice.addKeyListener(new KeyListenerHandler(tableInvoice));
		//tableInvoice.addMouseListener(new MouseClickHandler());
	}
	
	private void btnRefreshClick(ActionEvent e){
		refreshStockTable();
	}
	
	private void btnDeleteClick(ActionEvent e){
		JOptionPane.showMessageDialog(this, "Leamon-ERP Message","N/A", JOptionPane.PLAIN_MESSAGE);
	}
	
	private void btnRestoreClick(ActionEvent e){

		LOGGER.info("WinvoiceTrash[restoreStockItem] inside");
		TableWinvoiceTrashModel model = (TableWinvoiceTrashModel) tableInvoice.getModel();

		int selectedRow = tableInvoice.getSelectedRow();
		if (selectedRow == LeamonERPConstants.NO_ROW_SELECTED) {
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		int option = JOptionPane.showConfirmDialog(this, "Do you really want to restore ?");
		if (option == JOptionPane.YES_OPTION) {
			if (tableInvoice.getRowSorter() != null) {
				selectedRow = tableInvoice.getRowSorter().convertRowIndexToModel(selectedRow);
				LOGGER.debug("Selcted row : " + selectedRow);
			}

			int selectedRows[] = tableInvoice.getSelectedRows();
			IntStream.of(selectedRows).forEach(val -> {
				LOGGER.info("WinvoiceTrash[deleteStockItem] selected rows[" + val + "]");
			});
			List<InvoiceInfo> invoiceItems = model.getInvoiceInfos();
			int i = 0;
			int f = 0;
			for (int row : selectedRows) {
				if (i == 0) {
					f = row;
				}
				i++;
				if (row == selectedRow) {
					LOGGER.debug("Selcted row selectedRow equal: " + (row));
					InvoiceInfo si = invoiceItems.get(row);
					LOGGER.debug("Selected Row : " + si);
					try {
						InvoiceDaoImpl.getInstance().updateByID(si);
					} catch (Exception e1) {
						LOGGER.error(e1);
					}
				} else {
					LOGGER.debug("Selcted row only : " + (selectedRow + row - f));
					InvoiceInfo si = invoiceItems.get(selectedRow + row - f);
					LOGGER.debug("Selected Row : " + si);
					try {
						InvoiceDaoImpl.getInstance().updateByID(si);
					} catch (Exception e1) {
						LOGGER.error(e1);
					}
				}
			}
			try {
				invoiceItems = InvoiceDaoImpl.getInstance().getItemDisabledOnlyListWithInvoiceItemListAndAccountInfo();
			} catch (Exception e1) {
				LOGGER.error(e1);
			}
			LOGGER.debug("Reloading stockitems [" + invoiceItems.size() + "]");
			model.setInvoiceInfos(invoiceItems);
			tableInvoice.setModel(model);
			LOGGER.info(" Successfully restored stock items ");
			((AbstractTableModel) tableInvoice.getModel()).fireTableDataChanged();
			tableInvoice.repaint();
			SwingUtilities.updateComponentTreeUI(this);
			LOGGER.info("WinvoiceTrash[restoreStockItem] end");
		} else {
			refreshStockTable();
		}
	
	}
	
	public void refreshStockTable() {
		List<InvoiceInfo> invoiceItems = new ArrayList<InvoiceInfo>();
		try {
			invoiceItems = InvoiceDaoImpl.getInstance().getItemDisabledOnlyListWithInvoiceItemListAndAccountInfo();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		TableWinvoiceTrashModel model = (TableWinvoiceTrashModel) tableInvoice.getModel();
		model.setInvoiceInfos(invoiceItems);
		tableInvoice.setModel(model);
		((AbstractTableModel) tableInvoice.getModel()).fireTableDataChanged();
		tableInvoice.repaint();
	}
}
