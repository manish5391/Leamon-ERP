package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import com.google.common.base.Strings;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.persistence.EnumType;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.PaymentReceivedDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.PaymentReceivedInfo;
import leamon.erp.ui.custom.PaymentReceivedSummaryTableCellRenderer;
import leamon.erp.ui.model.GenericModelWithSnp;
import leamon.erp.ui.model.TableAccountInfoListModel;
import leamon.erp.ui.model.TablePaymentReceivedHistoryModel;
import leamon.erp.ui.model.TablePaymentReceivedSummaryModel;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.InvoicePaymentStatusEnum;
import leamon.erp.util.PaymentEnum;
import lombok.Getter;

@Getter
public class PaymentUiManager extends JInternalFrame {

	private JXTable tablePayment;

	private JXDatePicker datePickerStartDate;
	private JXDatePicker datePickerEndDate;

	private JXTextField textFieldPartyName;

	private JXButton btnSearch;
	private JXButton buttonExcel;
	private JXButton buttonPrint;
	private JButton buttonClose;
	private JButton buttonRefresh;

	private JLabel labelTotalPayment;
	private JComboBox comboBox;

	private AccountInfo accountInfo;
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;

	private static final Logger LOGGER = Logger.getLogger(PaymentUiManager.class);
	private static final String CLASS_NAME="PaymentUiManager";

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
		panel_1.setBounds(0, 474, 847, 34);
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
		buttonClose.setBounds(743, 6, 94, 23);
		panel_1.add(buttonClose);

		buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(639, 6, 94, 23);
		panel_1.add(buttonRefresh);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 44, 906, 431);
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
		panelPaymentInvoice.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panelPaymentInvoice.add(scrollPane_1, BorderLayout.CENTER);

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

	private void btnSearchClick(ActionEvent e){
		String startDate= datePickerStartDate.getEditor().getText();
		String endDate= datePickerEndDate.getEditor().getText();
		String type = comboBox.getSelectedItem().toString();

		if(Strings.isNullOrEmpty(textFieldPartyName.getText())){
			accountInfo = null;
		}
		
		/*only by party name*/
		if(accountInfo!=null && Strings.isNullOrEmpty(startDate) && PaymentEnum.N.name().equals(type)){
			searchByPartyName();
		}
		
		/*only be start date*/
		if(!Strings.isNullOrEmpty(startDate) && accountInfo==null && PaymentEnum.N.name().equals(type)){
			Date startDateValue = datePickerStartDate.getDate();
			searchByStartDate(startDateValue);
		}
		
	}

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
}
