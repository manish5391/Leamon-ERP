package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXDatePicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jdesktop.swingx.JXTextField;
import leamon.erp.util.LeamonERPConstants;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXButton;
import javax.swing.JButton;
import org.jdesktop.swingx.JXPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import com.google.common.base.Strings;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.persistence.EnumType;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.db.OpeningBalanceDaoImpl;
import leamon.erp.db.PaymentInvoiceMappingDaoImpl;
import leamon.erp.db.PaymentReceivedDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.OpeningBalanceInfo;
import leamon.erp.model.PaymentInvoiceMappingInfo;
import leamon.erp.model.PaymentReceivedInfo;
import leamon.erp.ui.custom.PaymentReceivedSummaryTableCellRenderer;
import leamon.erp.ui.model.GenericModelWithSnp;
import leamon.erp.ui.model.TableAccountInfoListModel;
import leamon.erp.ui.model.TablePaymentInvoiceOpeningBalanceModel;
import leamon.erp.ui.model.TablePaymentReceivedHistoryModel;
import leamon.erp.ui.model.TablePaymentReceivedSummaryModel;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.InvoicePaymentStatusEnum;
import leamon.erp.util.PaymentEnum;
import lombok.Getter;
import javax.swing.JSeparator;

@Getter
public class PaymentUiManager extends JInternalFrame {

	private JXTable tablePayment;
	private JXTable tablePaymentInvoice;

	private JXDatePicker datePickerStartDate;
	private JXDatePicker datePickerEndDate;

	private JXTextField textFieldPartyName;

	private JXButton btnSearch;
	private JXButton buttonExcel;
	private JXButton buttonPrint;
	private JButton buttonClose;
	private JButton buttonRefresh;

	private JLabel labelTotalPayment;
	private JLabel labelTotalBInvoice;
	private JLabel labelTotalWOpenigBalance;
	private JLabel labelTotalWPaymentInvoice;
	private JLabel labelTotalWInvoice;
	private JLabel labelBInvoicePayment;
	private JLabel labelTotalBOpeningBal;
	private JLabel labelBBalance;
	private JLabel labelWBalance;
	private JComboBox comboBox;

	private JTabbedPane tabbedPane;

	private AccountInfo accountInfo;
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;

	private static final Logger LOGGER = Logger.getLogger(PaymentUiManager.class);
	private static final String CLASS_NAME="PaymentUiManager";
	private final JSeparator separator = new JSeparator();

	public PaymentUiManager() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Payment Manager");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(3, 30, 922, 534);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(0, 0, 916, 43);
		getContentPane().add(panel);

		JLabel label = new JLabel("Start Date");
		label.setForeground((Color) null);
		label.setFont(new Font("DialogInput", Font.BOLD, 12));
		label.setBounds(3, 11, 70, 22);
		panel.add(label);

		JLabel label_1 = new JLabel("End Date");
		label_1.setForeground((Color) null);
		label_1.setFont(new Font("DialogInput", Font.BOLD, 12));
		label_1.setBounds(195, 11, 61, 22);
		panel.add(label_1);

		JLabel label_2 = new JLabel("Party");
		label_2.setForeground((Color) null);
		label_2.setFont(new Font("DialogInput", Font.BOLD, 12));
		label_2.setBounds(373, 11, 43, 22);
		panel.add(label_2);

		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerStartDate = new JXDatePicker((Date) null);
		datePickerStartDate.setFormats(df);
		datePickerStartDate.setBounds(75, 12, 116, 22);
		panel.add(datePickerStartDate);
		datePickerStartDate.getMonthView().setZoomable(true);

		datePickerEndDate = new JXDatePicker((Date) null);
		datePickerEndDate.setFormats(df);
		datePickerEndDate.setBounds(253, 12, 116, 22);
		panel.add(datePickerEndDate);
		datePickerEndDate.getMonthView().setZoomable(true);

		textFieldPartyName = new JXTextField();
		textFieldPartyName.setPrompt("Party Name");
		textFieldPartyName.setName("txtPartyName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyName.setBounds(410, 13, 302, 23);
		panel.add(textFieldPartyName);
		autoAccountInfoSuggestor(textFieldPartyName);

		btnSearch = new JXButton();
		btnSearch.setText("Search");
		btnSearch.setBounds(830, 12, 80, 24);
		panel.add(btnSearch);
		btnSearch.addActionListener(e -> btnSearchClick(e));

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(PaymentEnum.values()));
		comboBox.setBounds(722, 12, 70, 22);
		panel.add(comboBox);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(0, 474, 914, 34);
		getContentPane().add(panel_1);

		buttonExcel = new JXButton();
		buttonExcel.setText("Export To Excel");
		buttonExcel.setBounds(30, 6, 118, 23);
		panel_1.add(buttonExcel);

		buttonPrint = new JXButton();
		buttonPrint.setText("Print Bill");
		buttonPrint.setBounds(177, 6, 109, 23);
		panel_1.add(buttonPrint);

		buttonClose = new JButton("Close");
		buttonClose.setBounds(802, 6, 94, 23);
		buttonClose.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK), "Close");
		buttonClose.getActionMap().put("Close", getCloseAction());
		try {
			buttonClose.setIcon(new ImageIcon(LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_PAYMENT_MASTER_CLOSE_BUTTON)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		panel_1.add(buttonClose);
		buttonClose.addActionListener(e -> buttonCloseClick(e));

		buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(698, 6, 94, 23);
		buttonRefresh.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.ALT_DOWN_MASK), "Clear");
		buttonRefresh.getActionMap().put("Clear", getClearAction());
		try {
			buttonRefresh.setIcon(new ImageIcon(LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_PAYMENT_MASTER_REFRESH_BUTTON)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		panel_1.add(buttonRefresh);
		buttonRefresh.addActionListener(e -> buttonRefreshClick(e));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 44, 914, 431);
		getContentPane().add(tabbedPane);

		JXPanel panelPayment = new JXPanel();
		panelPayment.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Payment", null, panelPayment, null);
		panelPayment.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(2, 2, 838, 363);
		panelPayment.add(scrollPane);

		tablePayment = new JXTable();
		scrollPane.setViewportView(tablePayment);
		tablePayment.setComponentPopupMenu(createPopup());

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(216, 191, 216));
		panel_2.setBounds(2, 369, 838, 37);
		panelPayment.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblTotal = new JLabel("Total : ");
		lblTotal.setBounds(267, 5, 56, 17);
		lblTotal.setForeground((Color) null);
		lblTotal.setFont(new Font("DialogInput", Font.BOLD, 12));
		panel_2.add(lblTotal);

		labelTotalPayment = new JLabel("");
		labelTotalPayment.setBackground(new Color(240, 248, 255));
		labelTotalPayment.setForeground((Color) null);
		labelTotalPayment.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelTotalPayment.setBounds(334, 5, 109, 17);
		panel_2.add(labelTotalPayment);
		try{
			getPaymentInfo();
		}catch(Exception exp){
			LOGGER.error(exp);
		}

		JXPanel panelPaymentInvoice = new JXPanel();
		panelPaymentInvoice.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Payment-Invoice(+/-)", null, panelPaymentInvoice, null);
		panelPaymentInvoice.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(2, 2, 810, 402);
		panelPaymentInvoice.add(scrollPane_1);

		tablePaymentInvoice = new JXTable();
		scrollPane_1.setViewportView(tablePaymentInvoice);

		JLabel lblTotal_1 = new JLabel("B.Invoice");
		lblTotal_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal_1.setForeground((Color) null);
		lblTotal_1.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblTotal_1.setBounds(839, 29, 65, 18);
		panelPaymentInvoice.add(lblTotal_1);

		JLabel lblWinvoice = new JLabel("W.Invoice");
		lblWinvoice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWinvoice.setForeground((Color) null);
		lblWinvoice.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblWinvoice.setBounds(835, 162, 70, 18);
		panelPaymentInvoice.add(lblWinvoice);

		JLabel lblPayment = new JLabel("B.Payment");
		lblPayment.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPayment.setForeground((Color) null);
		lblPayment.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblPayment.setBounds(839, 98, 66, 18);
		panelPaymentInvoice.add(lblPayment);

		labelTotalWPaymentInvoice = new JLabel("0.00");
		labelTotalWPaymentInvoice.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalWPaymentInvoice.setForeground((Color) null);
		labelTotalWPaymentInvoice.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelTotalWPaymentInvoice.setBackground(new Color(240, 248, 255));
		labelTotalWPaymentInvoice.setBounds(813, 258, 93, 17);
		panelPaymentInvoice.add(labelTotalWPaymentInvoice);

		labelTotalWOpenigBalance = new JLabel("0.00");
		labelTotalWOpenigBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalWOpenigBalance.setForeground((Color) null);
		labelTotalWOpenigBalance.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelTotalWOpenigBalance.setBackground(new Color(240, 248, 255));
		labelTotalWOpenigBalance.setBounds(813, 219, 93, 17);
		panelPaymentInvoice.add(labelTotalWOpenigBalance);

		labelTotalBInvoice = new JLabel("0.00");
		labelTotalBInvoice.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalBInvoice.setForeground((Color) null);
		labelTotalBInvoice.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelTotalBInvoice.setBackground(new Color(240, 248, 255));
		labelTotalBInvoice.setBounds(813, 48, 93, 17);
		panelPaymentInvoice.add(labelTotalBInvoice);
		
		JLabel lblBobal = new JLabel("B.O.Bal");
		lblBobal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBobal.setForeground((Color) null);
		lblBobal.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblBobal.setBounds(839, 63, 66, 18);
		panelPaymentInvoice.add(lblBobal);
		
		labelTotalBOpeningBal = new JLabel("0.00");
		labelTotalBOpeningBal.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalBOpeningBal.setForeground((Color) null);
		labelTotalBOpeningBal.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelTotalBOpeningBal.setBackground(new Color(240, 248, 255));
		labelTotalBOpeningBal.setBounds(813, 81, 93, 17);
		panelPaymentInvoice.add(labelTotalBOpeningBal);
		
		labelBInvoicePayment = new JLabel("0.00");
		labelBInvoicePayment.setHorizontalAlignment(SwingConstants.RIGHT);
		labelBInvoicePayment.setForeground((Color) null);
		labelBInvoicePayment.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelBInvoicePayment.setBackground(new Color(240, 248, 255));
		labelBInvoicePayment.setBounds(813, 118, 93, 17);
		panelPaymentInvoice.add(labelBInvoicePayment);
		
		JLabel lblWobal = new JLabel("W.O.Bal");
		lblWobal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWobal.setForeground((Color) null);
		lblWobal.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblWobal.setBounds(839, 198, 66, 18);
		panelPaymentInvoice.add(lblWobal);
		
		JLabel lblWpayment = new JLabel("W.Payment");
		lblWpayment.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWpayment.setForeground((Color) null);
		lblWpayment.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblWpayment.setBounds(839, 238, 66, 18);
		panelPaymentInvoice.add(lblWpayment);
		
		labelTotalWInvoice = new JLabel("0.00");
		labelTotalWInvoice.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalWInvoice.setForeground((Color) null);
		labelTotalWInvoice.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelTotalWInvoice.setBackground(new Color(240, 248, 255));
		labelTotalWInvoice.setBounds(813, 181, 93, 17);
		panelPaymentInvoice.add(labelTotalWInvoice);
		
		JLabel lblBbalance = new JLabel("B.Balance");
		lblBbalance.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBbalance.setForeground((Color) null);
		lblBbalance.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblBbalance.setBounds(839, 301, 65, 18);
		panelPaymentInvoice.add(lblBbalance);
		
		JLabel lblWinvoice_1 = new JLabel("W.Balance");
		lblWinvoice_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWinvoice_1.setForeground((Color) null);
		lblWinvoice_1.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblWinvoice_1.setBounds(839, 340, 65, 18);
		panelPaymentInvoice.add(lblWinvoice_1);
		
		labelBBalance = new JLabel("0.00");
		labelBBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		labelBBalance.setForeground((Color) null);
		labelBBalance.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelBBalance.setBackground(new Color(240, 248, 255));
		labelBBalance.setBounds(813, 317, 93, 17);
		panelPaymentInvoice.add(labelBBalance);
		
		labelWBalance = new JLabel("0.00");
		labelWBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		labelWBalance.setForeground((Color) null);
		labelWBalance.setFont(new Font("DialogInput", Font.BOLD, 12));
		labelWBalance.setBackground(new Color(240, 248, 255));
		labelWBalance.setBounds(813, 358, 93, 17);
		panelPaymentInvoice.add(labelWBalance);
		separator.setBounds(822, 292, 82, 2);
		panelPaymentInvoice.add(separator);

		JXPanel panelPaymentInvoiceMapping = new JXPanel();
		panelPaymentInvoiceMapping.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Payment-Invoice-Mapping", null, panelPaymentInvoiceMapping, null);
		panelPaymentInvoiceMapping.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		panelPaymentInvoiceMapping.add(scrollPane_2, BorderLayout.CENTER);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_2.setViewportView(scrollPane_3);

		JXTreeTable treeTable = new JXTreeTable();
		scrollPane_3.setViewportView(treeTable);
	}

	private void getPaymentInfo() throws Exception{
		PaymentReceivedDaoImpl dao = PaymentReceivedDaoImpl.getInstance();
		List<PaymentReceivedInfo> paymentReceivedInfos = dao.getItemListWithAccountInfo();
		setModel(paymentReceivedInfos);
	}

	private void setModel(List<PaymentReceivedInfo> paymentReceivedInfos){
		List<Integer> snos = IntStream.range(1, 1+paymentReceivedInfos.size()).boxed().collect(Collectors.toList());
		GenericModelWithSnp<List<PaymentReceivedInfo>, PaymentReceivedInfo> paymentHistoryModel = 
				new GenericModelWithSnp<List<PaymentReceivedInfo>, PaymentReceivedInfo>(paymentReceivedInfos, snos);
		TablePaymentReceivedHistoryModel tablePaymentReceivedHistoryModel = 
				new TablePaymentReceivedHistoryModel(paymentHistoryModel);
		tablePayment.setModel(tablePaymentReceivedHistoryModel);
		tablePayment.packAll();
		setAmountAlignment(tablePayment);

		double sum =  paymentReceivedInfos.stream().map(e-> e.getReceivedPayment()).mapToDouble(Double::parseDouble).sum();
		BigDecimal bd = new BigDecimal(String.valueOf(sum));
		labelTotalPayment.setText(bd.toPlainString());
	}

	public void autoAccountInfoSuggestor(JTextField textField){
		final String methodName="autoAccountInfoSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");

		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoAccountIDTextFieldSuggestor 
		= new LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo>(textField, this);
		leamonAutoAccountIDTextFieldSuggestor.setItems(accountInfos);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}

	public void setAccountInfo(AccountInfo info){
		accountInfo = info;
		textFieldPartyName.setText(info.getName());

	}

	public JPopupMenu createPopup(){
		LOGGER.info(CLASS_NAME+"[createPopup] inside");
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete", new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		JMenuItem menuItemView = new JMenuItem("View",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_VIEW)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));

		menuItemDelete.addActionListener(e -> menuItemDeleteClick(e));
		menuItemView.addActionListener(e -> menuItemViewClick(e));
		menuItemRefresh.addActionListener(e -> menuItemRefreshClick(e));

		popupMenu.add(menuItemRefresh);
		popupMenu.add(menuItemView);
		popupMenu.add(menuItemDelete);
		LOGGER.info(CLASS_NAME+"[createPopup] end.");
		SwingUtilities.updateComponentTreeUI(this);
		return popupMenu;
	}

	private void menuItemDeleteClick(ActionEvent e){
		deletePaymentReceivedInfo();
	}

	private void menuItemViewClick(ActionEvent e){

	}

	private void menuItemRefreshClick(ActionEvent e){
		try{
			getPaymentInfo();
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void deletePaymentReceivedInfo() {
		LOGGER.info(CLASS_NAME+"[deleteAccountInfo] inside");

		int op =JOptionPane.showConfirmDialog(this, "Are you sure ?","Leamon-ERP",JOptionPane.YES_NO_OPTION);

		if(! (op==JOptionPane.YES_OPTION)){
			return;
		}

		TablePaymentReceivedHistoryModel model  = (TablePaymentReceivedHistoryModel)tablePayment.getModel();

		int selectedRow = tablePayment.getSelectedRow();
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(tablePayment.getRowSorter() != null){
			selectedRow = tablePayment.getRowSorter().convertRowIndexToModel(selectedRow);
			LOGGER.debug("Selcted row : "+ selectedRow);
		}

		int selectedRows[] = tablePayment.getSelectedRows();
		IntStream.of(selectedRows).forEach(val -> {
			LOGGER.info(CLASS_NAME+"[deleteAccountInfo] selected rows["+val+"]");
		});
		List<PaymentReceivedInfo> paymentReceivedInfos=  model.getPaymentReceivedInfos();
		int i=0;
		int f=0;
		for(int row  : selectedRows){
			if(i==0){
				f=row;
			}
			i++;
			if(row == selectedRow ){
				LOGGER.debug("Selcted row selectedRow equal: "+ (row));
				PaymentReceivedInfo si = paymentReceivedInfos.get(row);
				LOGGER.debug("Selected Row : "+si);
				try{
					if(ERPEnum.STATUS_PAYMENT_ADJUSTMENT_NOTHING.name().equals(si.getStatus())){
						PaymentReceivedDaoImpl.getInstance().disable(si);
					}else{
						JOptionPane.showMessageDialog(this, "Failed ! it is adjusted.","Leamon-ERP-Error",JOptionPane.ERROR_MESSAGE);
						List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos = PaymentInvoiceMappingDaoImpl.getInstance().getItemListByPaymentId(si.getId());
						LOGGER.debug("value are : "+paymentInvoiceMappingInfos);
						openPaymentAdjustmentDeleteUI(si,paymentInvoiceMappingInfos);
					}
				}catch(Exception e){
					LOGGER.error(e);
				}
			}else{
				LOGGER.debug("Selcted row only : "+ (selectedRow+row-f));
				PaymentReceivedInfo si = paymentReceivedInfos.get(selectedRow+row-f);
				LOGGER.debug("Selected Row : "+si);
				try{
					if(ERPEnum.STATUS_PAYMENT_ADJUSTMENT_NOTHING.name().equals(si.getStatus())){
						PaymentReceivedDaoImpl.getInstance().disable(si);
					}else{
						JOptionPane.showMessageDialog(this, "Failed ! it is adjusted.","Leamon-ERP-Error",JOptionPane.ERROR_MESSAGE);
						List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos = PaymentInvoiceMappingDaoImpl.getInstance().getItemListByPaymentId(si.getId());
						LOGGER.debug("value are : "+paymentInvoiceMappingInfos);
						openPaymentAdjustmentDeleteUI(si,paymentInvoiceMappingInfos);
					}
				}catch(Exception e){
					LOGGER.error(e);
				}
			}
		}
		menuItemRefreshClick(null);
		LOGGER.info(" Successfully disabled Accoount Info items ");
		((AbstractTableModel)tablePayment.getModel()).fireTableDataChanged();
		tablePayment.repaint();
		SwingUtilities.updateComponentTreeUI(this);
		LOGGER.info(CLASS_NAME+"[deleteAccountInfo] end");
	}

	private void buttonCloseClick(ActionEvent e){
		this.dispose();
	}

	private void buttonRefreshClick(ActionEvent e){
		clear();
		setModel(new ArrayList<PaymentReceivedInfo>());
	}
	private void btnSearchClick(ActionEvent e){
		String startDate= datePickerStartDate.getEditor().getText();
		String endDate= datePickerEndDate.getEditor().getText();
		String type = comboBox.getSelectedItem().toString();

		if(Strings.isNullOrEmpty(textFieldPartyName.getText())){
			accountInfo = null;
		}

		/*only by party name*/
		if(accountInfo!=null && Strings.isNullOrEmpty(startDate) && PaymentEnum.N.name().equals(type)){
			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT){
				searchByPartyName();
			}
			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE){
				searchPaymentInvoiceByPartyName();
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE_MAPPING){
				//TODO
			}
		}

		/*only by start date*/
		if(!Strings.isNullOrEmpty(startDate) && Strings.isNullOrEmpty(endDate) && accountInfo==null && PaymentEnum.N.name().equals(type)){
			Date startDateValue = datePickerStartDate.getDate();
			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT){
				searchByStartDate(startDateValue);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE){
				searchPaymentInvoiceByStartDate(startDateValue);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE_MAPPING){
				//TODO
			}
		}

		/*only by type*/
		if(!PaymentEnum.N.name().equals(type) && Strings.isNullOrEmpty(startDate) && accountInfo==null){
			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT){
				searchByType(type);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE){
				JOptionPane.showMessageDialog(this, "Not Applicable", "Leamon-ERP Warning",JOptionPane.WARNING_MESSAGE);
				return ;
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE_MAPPING){
				//TODO
			}
		}

		/*between date range*/
		if(!Strings.isNullOrEmpty(startDate) && !Strings.isNullOrEmpty(endDate) && accountInfo == null && PaymentEnum.N.name().equals(type)){
			Date startDateValue = datePickerStartDate.getDate();
			Date endDateValue = datePickerEndDate.getDate();
			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT){
				searchByStartEndDate(startDateValue,endDateValue);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE){
				searchPaymentInvoiceByStartEndDate(startDateValue,endDateValue);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE_MAPPING){
				//TODO
			}
		}

		/*between date range & Party Name*/
		if(!Strings.isNullOrEmpty(startDate) && !Strings.isNullOrEmpty(endDate) && accountInfo != null && PaymentEnum.N.name().equals(type)){
			Date startDateValue = datePickerStartDate.getDate();
			Date endDateValue = datePickerEndDate.getDate();
			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT){
				searchByStartEndDatePartyName(startDateValue,endDateValue);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE){
				searchPaymentInvoiceByStartEndDatePartyName(startDateValue,endDateValue);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE_MAPPING){
				//TODO
			}
		}

		/*between date range & Type*/
		if(!Strings.isNullOrEmpty(startDate) && !Strings.isNullOrEmpty(endDate) && accountInfo == null && !PaymentEnum.N.name().equals(type)){
			Date startDateValue = datePickerStartDate.getDate();
			Date endDateValue = datePickerEndDate.getDate();
			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT){
				searchByStartEndDateType(startDateValue,endDateValue,type);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE){
				JOptionPane.showMessageDialog(this, "Not Applicable", "Leamon-ERP Warning",JOptionPane.WARNING_MESSAGE);
				return ;
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE_MAPPING){
				//TODO
			}
		}

		/*between date range & Party Name & Type*/
		if(!Strings.isNullOrEmpty(startDate) && !Strings.isNullOrEmpty(endDate) && accountInfo != null && !PaymentEnum.N.name().equals(type)){
			Date startDateValue = datePickerStartDate.getDate();
			Date endDateValue = datePickerEndDate.getDate();
			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT){
				searchByStartEndDatePartyNameType(startDateValue,endDateValue,type);
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE){
				JOptionPane.showMessageDialog(this, "Not Applicable", "Leamon-ERP Warning",JOptionPane.WARNING_MESSAGE);
				return ;
			}

			if(tabbedPane.getSelectedIndex() == LeamonERPConstants.TABBED_PAYMENT_INVOICE_MAPPING){
				//TODO
			}
		}
	}//end button serach click

	private void searchByPartyName(){
		try{
			PaymentReceivedDaoImpl dao = PaymentReceivedDaoImpl.getInstance();
			List<PaymentReceivedInfo> paymentReceivedInfos = dao.getItemListByPartyName(accountInfo.getId().toString());
			setModel(paymentReceivedInfos);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void searchByStartDate(Date startDate){
		try{
			PaymentReceivedDaoImpl dao = PaymentReceivedDaoImpl.getInstance();
			List<PaymentReceivedInfo> paymentReceivedInfos = dao.getItemListByStartDate(startDate);
			setModel(paymentReceivedInfos);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void searchByType(String type){
		try{
			if(PaymentEnum.B.name().equals(type)){
				type = ERPEnum.TYPE_PAYMENT_WITH_BILL.name();
			}

			if(PaymentEnum.W.name().equals(type)){
				type = ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name();
			}

			if(PaymentEnum.BOTH.name().equals(type)){
				menuItemRefreshClick(null);
				return;
			}
			PaymentReceivedDaoImpl dao = PaymentReceivedDaoImpl.getInstance();
			List<PaymentReceivedInfo> paymentReceivedInfos = dao.getItemListByType(type);
			setModel(paymentReceivedInfos);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void searchByStartEndDate(Date startDate, Date endDate){
		try{
			PaymentReceivedDaoImpl dao = PaymentReceivedDaoImpl.getInstance();
			List<PaymentReceivedInfo> paymentReceivedInfos = dao.getItemListByStartEndDate(startDate, endDate);
			setModel(paymentReceivedInfos);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void searchByStartEndDatePartyName(Date startDate, Date endDate){
		try{
			PaymentReceivedDaoImpl dao = PaymentReceivedDaoImpl.getInstance();
			List<PaymentReceivedInfo> paymentReceivedInfos = dao.searchByStartEndDatePartyName(startDate, endDate,accountInfo.getId().toString());
			setModel(paymentReceivedInfos);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void searchByStartEndDateType(Date startDate, Date endDate, String type){
		if(PaymentEnum.B.name().equals(type)){
			type = ERPEnum.TYPE_PAYMENT_WITH_BILL.name();
		}

		if(PaymentEnum.W.name().equals(type)){
			type = ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name();
		}

		if(PaymentEnum.BOTH.name().equals(type)){
			searchByStartEndDate(startDate,endDate);
			return;
		}
		try{
			PaymentReceivedDaoImpl dao = PaymentReceivedDaoImpl.getInstance();
			List<PaymentReceivedInfo> paymentReceivedInfos = dao.searchByStartEndDateType(startDate, endDate,type);
			setModel(paymentReceivedInfos);
		}catch(Exception exp){
			LOGGER.error(exp);
		}

	}

	private void searchByStartEndDatePartyNameType(Date startDate, Date endDate, String type){

		if(PaymentEnum.B.name().equals(type)){
			type = ERPEnum.TYPE_PAYMENT_WITH_BILL.name();
		}

		if(PaymentEnum.W.name().equals(type)){
			type = ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name();
		}

		if(PaymentEnum.BOTH.name().equals(type)){
			searchByStartEndDatePartyName(startDate,endDate);
			return;
		}

		try{
			PaymentReceivedDaoImpl dao = PaymentReceivedDaoImpl.getInstance();
			List<PaymentReceivedInfo> paymentReceivedInfos = dao.searchByStartEndDatePartyNameType(startDate, endDate,accountInfo.getId().toString(),type);
			setModel(paymentReceivedInfos);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void searchPaymentInvoiceByPartyName(){
		try{
			List<PaymentReceivedInfo> paymentReceivedInfos = PaymentReceivedDaoImpl.getInstance().getItemListByPartyName(accountInfo.getId().toString());
			List<OpeningBalanceInfo> openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getAllOpeningBalanceByPartyName(accountInfo.getId().toString());
			List<InvoiceInfo> invoiceInfos = InvoiceDaoImpl.getInstance().getAllInvoiceByPartyName(accountInfo.getId().toString());

			java.util.LinkedList<Object> list = new java.util.LinkedList<Object>();

			list.addAll(paymentReceivedInfos);
			list.addAll(openingBalanceInfos);
			list.addAll(invoiceInfos);
			
			sortByDate(list);
			setPaymentInvoiceModel(list);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void searchPaymentInvoiceByStartDate(Date startDate){

		try{
			List<PaymentReceivedInfo> paymentReceivedInfos = PaymentReceivedDaoImpl.getInstance().getItemListByStartDate(startDate);
			List<OpeningBalanceInfo> openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getAllOpeningBalanceByStartDate(startDate);
			List<InvoiceInfo> invoiceInfos = InvoiceDaoImpl.getInstance().getAllInvoiceByStartDate(startDate);

			java.util.LinkedList<Object> list = new java.util.LinkedList<Object>();

			list.addAll(paymentReceivedInfos);
			list.addAll(openingBalanceInfos);
			list.addAll(invoiceInfos);

			sortByDate(list);
			setPaymentInvoiceModel(list);
		}catch(Exception exp){
			LOGGER.error(exp);
		}

	}

	private void searchPaymentInvoiceByStartEndDate(Date startDate, Date endDate){

		try{
			List<PaymentReceivedInfo> paymentReceivedInfos = PaymentReceivedDaoImpl.getInstance().getItemListByStartEndDate(startDate, endDate);
			List<OpeningBalanceInfo> openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getAllOpeningBalanceByStartEndDate(startDate, endDate);
			List<InvoiceInfo> invoiceInfos = InvoiceDaoImpl.getInstance().getAllInvoiceByStartEndDate(startDate, endDate);

			java.util.LinkedList<Object> list = new java.util.LinkedList<Object>();

			list.addAll(paymentReceivedInfos);
			list.addAll(openingBalanceInfos);
			list.addAll(invoiceInfos);

			sortByDate(list);
			setPaymentInvoiceModel(list);
		}catch(Exception exp){
			LOGGER.error(exp);
		}

	}

	private void searchPaymentInvoiceByStartEndDatePartyName(Date startDate, Date endDate){
		try{
			List<PaymentReceivedInfo> paymentReceivedInfos = PaymentReceivedDaoImpl.getInstance().searchByStartEndDatePartyName(startDate, endDate, accountInfo.getId().toString());
			List<OpeningBalanceInfo> openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getAllOpeningBalanceByStartEndDatePartyName(startDate, endDate, accountInfo.getId().toString());
			List<InvoiceInfo> invoiceInfos = InvoiceDaoImpl.getInstance().getAllInvoiceByStartEndDatePartyName(startDate, endDate, accountInfo.getId().toString());

			java.util.LinkedList<Object> list = new java.util.LinkedList<Object>();

			list.addAll(paymentReceivedInfos);
			list.addAll(openingBalanceInfos);
			list.addAll(invoiceInfos);

			sortByDate(list);
			setPaymentInvoiceModel(list);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private void setPaymentInvoiceModel(List<Object> list){
		List<Integer> snos = IntStream.range(1, 1+list.size()).boxed().collect(Collectors.toList());
		GenericModelWithSnp<List<Object>, Object> paymentOpeningBalanceInvoiceHistoryModel = 
				new GenericModelWithSnp<List<Object>, Object>(list, snos);
		TablePaymentInvoiceOpeningBalanceModel  tableModel = new TablePaymentInvoiceOpeningBalanceModel(paymentOpeningBalanceInvoiceHistoryModel);
		tablePaymentInvoice.setModel(tableModel);
		tablePaymentInvoice.packAll();
		setAmountAlignment(tablePaymentInvoice);
		setPaymentInvoiceTotal(list);
		setInvoiceTotal(list);
		setOpeningBalanceTotal(list);
		setBalance();
	}

	public void clear(){
		accountInfo = null;
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		datePickerEndDate.getEditor().setText(LeamonERPConstants.EMPTY_STR);
		datePickerStartDate.getEditor().setText(LeamonERPConstants.EMPTY_STR);
		datePickerStartDate.setDate((Date)null);
		datePickerEndDate.setDate((Date)null);
		comboBox.setSelectedIndex(0);
		try{
			getPaymentInfo();
		}catch(Exception exp){
			LOGGER.error(exp);
		}
	}

	private Action getCloseAction() {

		Action closeAction = new AbstractAction("Close") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("alt + c clicked");
				dispose();
			}
		};
		closeAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK));
		return closeAction;
	}
	public Action getClearAction() {
		Action clearAction = new AbstractAction("Clear") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("alt + R clicked");
				clear();
			}
		};
		clearAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.ALT_DOWN_MASK));
		return clearAction;
	}

	private void sortByDate(java.util.LinkedList<Object> list){
		try{
			Collections.sort(list, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {

					Timestamp t1 = null;
					Timestamp t2 = null;
					if(o1 instanceof PaymentReceivedInfo){
						t1 = ((PaymentReceivedInfo)o1).getLastUpdated();
					}else if(o1 instanceof OpeningBalanceInfo){
						t1 = ((OpeningBalanceInfo)o1).getLastUpdated();
					}else if(o1 instanceof InvoiceInfo){
						t1 = ((InvoiceInfo)o1).getLastUpdated();
					}

					if(o2 instanceof PaymentReceivedInfo){
						t2 = ((PaymentReceivedInfo)o2).getLastUpdated();
					}else if(o2 instanceof OpeningBalanceInfo){
						t2 = ((OpeningBalanceInfo)o2).getLastUpdated();
					}else if(o2 instanceof InvoiceInfo){
						t2 = ((InvoiceInfo)o2).getLastUpdated();
					}
					return t1.compareTo(t2);
				}

			});
		}catch(Exception exp){
			LOGGER.error("Failed to sort by date : "+exp);
		}
	}

	private void setAmountAlignment(JXTable table) {
		//table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		TableColumnModel columnModel = table.getColumnModel();
		DefaultTableCellRenderer dtcrBamt = new DefaultTableCellRenderer();
		dtcrBamt.setHorizontalAlignment(SwingConstants.RIGHT);
		if(table == tablePayment){
			columnModel.getColumn(2).setCellRenderer(dtcrBamt);
		}

		DefaultTableCellRenderer dtcrWamt = new DefaultTableCellRenderer();
		dtcrWamt.setHorizontalAlignment(SwingConstants.RIGHT);
		if(table == tablePaymentInvoice){
			columnModel.getColumn(2).setCellRenderer(dtcrWamt);
			columnModel.getColumn(4).setCellRenderer(dtcrWamt);
			columnModel.getColumn(5).setCellRenderer(dtcrWamt);
		}
	}

	private void setPaymentInvoiceTotal(List<Object> list){
		setBPaymentInvoiceTotal(list);
		setWPaymentInvoiceTotal(list);
	}
	private void setInvoiceTotal(List<Object> list){
		setBInvoiceTotal(list);
		setWInvoiceTotal(list);
	}
	private void setOpeningBalanceTotal(List<Object> list){
		setBOpeningBalanceTotal(list);
		setWOpeningBalanceTotal(list);
	}
	
	private void setBPaymentInvoiceTotal(List<Object> list){
		double sum = list.stream().filter(item -> item instanceof PaymentReceivedInfo &&  ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(((PaymentReceivedInfo)item).getType()))
				.map(item -> (PaymentReceivedInfo)item)
				.map(item -> Strings.isNullOrEmpty(item.getReceivedPayment())?"0":item.getReceivedPayment())
				.mapToDouble(Double::parseDouble).sum();
		labelBInvoicePayment.setText(getRoundff(sum));
	}
	private void setWPaymentInvoiceTotal(List<Object> list){
		double sum = list.stream().filter(item -> item instanceof PaymentReceivedInfo &&  ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(((PaymentReceivedInfo)item).getType()))
				.map(item -> (PaymentReceivedInfo)item)
				.map(item -> Strings.isNullOrEmpty(item.getReceivedPayment())?"0":item.getReceivedPayment())
				.mapToDouble(Double::parseDouble).sum();
		labelTotalWPaymentInvoice.setText(getRoundff(sum));
	}
	private void setBInvoiceTotal(List<Object> list){
		double sum = list.stream().filter(item -> item instanceof InvoiceInfo)
		.map(item -> (InvoiceInfo)item)
		.map(item -> Strings.isNullOrEmpty(item.getBillAmount())?"0":item.getBillAmount())
		.mapToDouble(Double::parseDouble).sum();
		labelTotalBInvoice.setText(getRoundff(sum));
	}
	private void setWInvoiceTotal(List<Object> list){
		double sum = list.stream().filter(item -> item instanceof InvoiceInfo)
				.map(item -> (InvoiceInfo)item)
				.map(item -> Strings.isNullOrEmpty(item.getWithoutBillAmount())?"0":item.getWithoutBillAmount())
				.mapToDouble(Double::parseDouble).sum();
		labelTotalWInvoice.setText(getRoundff(sum));
	}
	private void setBOpeningBalanceTotal(List<Object> list){
		double sum = list.stream().filter(item -> item instanceof OpeningBalanceInfo &&  ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(((OpeningBalanceInfo)item).getType()))
				.map(item -> (OpeningBalanceInfo)item)
				.map(item -> Strings.isNullOrEmpty(item.getOpeningbalanceamount())?"0":item.getOpeningbalanceamount())
				.mapToDouble(Double::parseDouble).sum();
		labelTotalBOpeningBal.setText(getRoundff(sum));
	}
	private void setWOpeningBalanceTotal(List<Object> list){
		double sum = list.stream().filter(item -> item instanceof OpeningBalanceInfo &&  ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(((OpeningBalanceInfo)item).getType()))
				.map(item -> (OpeningBalanceInfo)item)
				.map(item -> Strings.isNullOrEmpty(item.getOpeningbalanceamount())?"0":item.getOpeningbalanceamount())
				.mapToDouble(Double::parseDouble).sum();
		labelTotalWOpenigBalance.setText(getRoundff(sum));
	}
	
	private void setBalance(){
		double sumBInvoicePayment = 0;
		double sumWInvoicePayment = 0;
		double sumTotalBInvoice = 0;
		double sumTotalWInvoice = 0;
		double sumTotalBOpeningBal = 0;
		double sumTotalWOpenigBalance = 0;
		
		try{ sumBInvoicePayment = Double.parseDouble(labelBInvoicePayment.getText()); }catch(Exception e){LOGGER.error(e);}
		try{ sumWInvoicePayment = Double.parseDouble(labelTotalWPaymentInvoice.getText()); }catch(Exception e){LOGGER.error(e);}
		try{ sumTotalBInvoice = Double.parseDouble(labelTotalBInvoice.getText()); }catch(Exception e){LOGGER.error(e);}
		try{ sumTotalWInvoice = Double.parseDouble(labelTotalWInvoice.getText()); }catch(Exception e){LOGGER.error(e);}
		try{ sumTotalBOpeningBal = Double.parseDouble(labelTotalBOpeningBal.getText()); }catch(Exception e){LOGGER.error(e);}
		try{ sumTotalWOpenigBalance = Double.parseDouble(labelTotalWOpenigBalance.getText()); }catch(Exception e){LOGGER.error(e);}
		
		double bSum = sumBInvoicePayment - (sumTotalBInvoice + sumTotalBOpeningBal);
		double wSum = sumWInvoicePayment - (sumTotalWInvoice + sumTotalWOpenigBalance);
		
		labelBBalance.setText(getRoundff(bSum));
		labelWBalance.setText(getRoundff(wSum));
	}
	
	private String getRoundff(double value){
		BigDecimal bd = new BigDecimal(value);
		bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		try{

			BigDecimal bdd = new BigDecimal(value);
			bdd = bdd.setScale(2,RoundingMode.HALF_UP);

			DecimalFormat df = new DecimalFormat("#.00");
			String grandTotal = df.format(bdd.doubleValue());
			return grandTotal;
		}catch(Exception e){ LOGGER.error(e); }
		
		return String.valueOf(bd.doubleValue());
	}
	
	private void openPaymentAdjustmentDeleteUI(PaymentReceivedInfo paymentReceivedInfo ,List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos){
		if(LeamonERP.paymentAdjustmentDeleteUI.isVisible()){
			try {
				LeamonERP.paymentAdjustmentDeleteUI.clear();
				LeamonERP.paymentAdjustmentDeleteUI.setPaymentInfo(paymentReceivedInfo, paymentInvoiceMappingInfos);
				LeamonERP.paymentAdjustmentDeleteUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			LeamonERP.paymentAdjustmentDeleteUI.moveToFront();
		}else{
			LeamonERP.desktopPane.add(LeamonERP.paymentAdjustmentDeleteUI);
			LeamonERP.paymentAdjustmentDeleteUI.clear();
			LeamonERP.paymentAdjustmentDeleteUI.setPaymentInfo(paymentReceivedInfo, paymentInvoiceMappingInfos);
			LeamonERP.paymentAdjustmentDeleteUI.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(LeamonERP.paymentAdjustmentDeleteUI);
	}
}
