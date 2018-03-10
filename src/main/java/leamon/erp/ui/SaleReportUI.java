package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.UIManager;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import com.google.common.base.Strings;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.db.InvoiceItemDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.SalesReportInfoModel;
import leamon.erp.ui.model.SalesReportModel;
import leamon.erp.util.LeamonERPConstants;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXButton;
import javax.swing.JButton;
import org.jdesktop.swingx.JXPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import org.jdesktop.swingx.JXTable;

/**
 * Sale Report UI
 * 
 * @date FEB 05,2018
 * @author Manish Kumar Mishra
 *
 */
public class SaleReportUI extends JInternalFrame {

	static final Logger LOGGER = Logger.getLogger(SaleReportUI.class);
	private JXDatePicker datePickerStartDate;
	private JXDatePicker datePickerEndDate;

	private JXTextField textFieldPartyName;

	private JXButton buttonSaleRegister;
	private JXButton btnExportToExcel;
	private JXButton btnPrintBill;
	private JButton buttonClose;
	private JButton buttonRefresh;

	private JXTable tableSaleReport;

	private JScrollPane scrollPane;
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;

	public SaleReportUI() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Sale Report UI");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(3, 30, 853, 534);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(0, 0, 847, 43);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(3, 11, 70, 22);
		panel.add(lblStartDate);
		lblStartDate.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblStartDate.setFont(new Font("DialogInput", Font.BOLD, 12));

		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(195, 11, 61, 22);
		panel.add(lblEndDate);
		lblEndDate.setForeground((Color) null);
		lblEndDate.setFont(new Font("DialogInput", Font.BOLD, 12));

		JLabel lblParty = new JLabel("Party");
		lblParty.setBounds(373, 11, 43, 22);
		panel.add(lblParty);
		lblParty.setForeground((Color) null);
		lblParty.setFont(new Font("DialogInput", Font.BOLD, 12));

		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerStartDate = new JXDatePicker((Date) null);
		datePickerStartDate.setBounds(75, 12, 116, 22);
		datePickerStartDate.setFormats(df);
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
		autoAccountInfoSuggestor(textFieldPartyName);
		panel.add(textFieldPartyName);

		buttonSaleRegister = new JXButton();
		buttonSaleRegister.setText("Sale Report");
		buttonSaleRegister.setBounds(722, 11, 115, 24);
		panel.add(buttonSaleRegister);
		buttonSaleRegister.addActionListener(e -> buttonSaleRegisterClick(e));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(0, 473, 847, 34);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		btnExportToExcel = new JXButton();
		btnExportToExcel.setBounds(30, 8, 118, 23);
		btnExportToExcel.setText("Export To Excel");
		panel_1.add(btnExportToExcel);
		btnExportToExcel.addActionListener(e -> btnExportToExcelClick(e));

		btnPrintBill = new JXButton();
		btnPrintBill.setText("Print Bill");
		btnPrintBill.setBounds(177, 8, 109, 23);
		panel_1.add(btnPrintBill);
		btnPrintBill.addActionListener(e -> btnPrintBillClick(e));

		buttonClose = new JButton("Close");
		buttonClose.setBounds(743, 8, 94, 23);
		panel_1.add(buttonClose);
		buttonClose.addActionListener(e -> buttonCloseClick(e));

		buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(639, 8, 94, 23);
		panel_1.add(buttonRefresh);
		buttonRefresh.addActionListener(e -> buttonRefreshClick(e));

		JXPanel panel_2 = new JXPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(0, 41, 847, 433);
		getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		panel_2.add(scrollPane);

		tableSaleReport = new JXTable();
	}

	private void buttonSaleRegisterClick(ActionEvent e) {

		String startDate = datePickerStartDate.getEditor().getText();
		String endDate = datePickerEndDate.getEditor().getText();
		String partyName = textFieldPartyName.getText();

		/* Only by party name */
		if (!Strings.isNullOrEmpty(partyName) && Strings.isNullOrEmpty(startDate)) {
			searchByPartyName(partyName);
		}

		/* Only by start date */
		if (!Strings.isNullOrEmpty(startDate) && Strings.isNullOrEmpty(endDate) && Strings.isNullOrEmpty(partyName)) {
			Date startDateValue = datePickerStartDate.getDate();
			searchByStartDate(startDateValue);
		}

		/* Between start date and end date */
		if (!Strings.isNullOrEmpty(startDate) && !Strings.isNullOrEmpty(endDate) && Strings.isNullOrEmpty(partyName)) {
			Date startDateValue = datePickerStartDate.getDate();
			Date endDateValue = datePickerEndDate.getDate();
			searchByStartEndDate(startDateValue, endDateValue);
		}

		/* Between start date and end date and party name */
		if (!Strings.isNullOrEmpty(startDate) && !Strings.isNullOrEmpty(endDate) && !Strings.isNullOrEmpty(partyName)) {
			Date startDateValue = datePickerStartDate.getDate();
			Date endDateValue = datePickerEndDate.getDate();
			searchByStartEndDatePartyName(startDateValue, endDateValue, partyName);
		}

		/* Start date and party name */
		if (!Strings.isNullOrEmpty(startDate) && Strings.isNullOrEmpty(endDate) && !Strings.isNullOrEmpty(partyName)) {
			Date startDateValue = datePickerStartDate.getDate();
			searchByStartDateAndPartyName(startDateValue, partyName);
		}

	}

	private void btnExportToExcelClick(ActionEvent e) {

	}

	private void btnPrintBillClick(ActionEvent e) {

	}

	private void buttonCloseClick(ActionEvent e) {
		this.dispose();
	}

	private void buttonRefreshClick(ActionEvent e) {
		datePickerStartDate.getEditor().setText(LeamonERPConstants.EMPTY_STR);
		datePickerEndDate.getEditor().setText(LeamonERPConstants.EMPTY_STR);
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		List<SalesReportInfoModel> salesReportInfoModels = new ArrayList<>();
		SalesReportModel model = new SalesReportModel(salesReportInfoModels);
		tableSaleReport.setModel(model);
	}

	public void autoAccountInfoSuggestor(JTextField textField) {
		final String methodName = "autoAccountInfoSuggestor";
		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoAccountIDTextFieldSuggestor = new LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo>(
				textField, this);
		leamonAutoAccountIDTextFieldSuggestor.setItems(accountInfos);

	}

	private void searchByPartyName(String partyName) {
		try {
			List<AccountInfo> accountInfo = AccountDaoImpl.getInstance().getItemList();
			AccountInfo accountInfo1 = accountInfo.stream().filter(e1 -> e1.getName().equalsIgnoreCase(partyName))
					.findAny().orElse(null);
			int partyID = accountInfo1.getId();

			List<InvoiceInfo> invoiceInfo = InvoiceDaoImpl.getInstance().getAllByPartyInfoID(partyID);
			List<SalesReportInfoModel> salesReportInfoModels = new ArrayList<>();

			for (InvoiceInfo invoice : invoiceInfo) {
				SalesReportInfoModel saleReport = new SalesReportInfoModel();
				saleReport.setInvoiceNumber(Integer.valueOf(invoice.getInvoicNum()));
				saleReport.setPartyName(partyName);
				saleReport.setBillNumber(invoice.getBillNo());
				saleReport.setDate(invoice.getInvoicDate());
				saleReport.setBAmount(invoice.getBillAmount());
				saleReport.setWAmount(invoice.getWithoutBillAmount());
				double bAmount = Double
						.valueOf(Strings.isNullOrEmpty(invoice.getBillAmount()) ? "0" : invoice.getBillAmount());
				double wAmount = Double.valueOf(
						Strings.isNullOrEmpty(invoice.getWithoutBillAmount()) ? "0" : invoice.getWithoutBillAmount());
				try {
					saleReport.setTotal(bAmount + wAmount);
				} catch (Exception exp) {
					LOGGER.error(exp);
				}
				salesReportInfoModels.add(saleReport);
			}
			SalesReportModel model = new SalesReportModel(salesReportInfoModels);
			tableSaleReport.setModel(model);
			tableSaleReport.setRowHeight(tableSaleReport.getRowHeight() + 20);
			tableSaleReport.setAutoCreateRowSorter(true);
			tableSaleReport.setName("Sale Report");
			tableSaleReport.setColumnControlVisible(true);
			tableSaleReport.packAll();
			scrollPane.setViewportView(tableSaleReport);
		} catch (Exception exp) {
			LOGGER.error(exp);
		}
	}

	private void searchByStartDate(Date startDate) {
		try {
			List<AccountInfo> accountInfo = AccountDaoImpl.getInstance().getItemList();
			List<InvoiceInfo> invoiceInfo = InvoiceDaoImpl.getInstance().getAllByStartDate(startDate);
			List<SalesReportInfoModel> salesReportInfoModels = new ArrayList<>();

			for (InvoiceInfo invoice : invoiceInfo) {
				SalesReportInfoModel saleReport = new SalesReportInfoModel();
				saleReport.setInvoiceNumber(Integer.valueOf(invoice.getInvoicNum()));
				int partyID = invoice.getPartyinfoID();
				for (AccountInfo acInfo : accountInfo) {
					if (acInfo.getId() == partyID) {
						saleReport.setPartyName(acInfo.getName());
						break;
					}
				}
				saleReport.setBillNumber(invoice.getBillNo());
				saleReport.setDate(invoice.getInvoicDate());
				saleReport.setBAmount(invoice.getBillAmount());
				saleReport.setWAmount(invoice.getWithoutBillAmount());
				double bAmount = Double
						.valueOf(Strings.isNullOrEmpty(invoice.getBillAmount()) ? "0" : invoice.getBillAmount());
				double wAmount = Double.valueOf(
						Strings.isNullOrEmpty(invoice.getWithoutBillAmount()) ? "0" : invoice.getWithoutBillAmount());
				try {
					saleReport.setTotal(bAmount + wAmount);
				} catch (Exception exp) {
					LOGGER.error(exp);
				}
				salesReportInfoModels.add(saleReport);
			}
			SalesReportModel model = new SalesReportModel(salesReportInfoModels);
			tableSaleReport.setModel(model);
			tableSaleReport.setRowHeight(tableSaleReport.getRowHeight() + 20);
			tableSaleReport.setAutoCreateRowSorter(true);
			tableSaleReport.setName("Sale Report");
			tableSaleReport.setColumnControlVisible(true);
			tableSaleReport.packAll();
			scrollPane.setViewportView(tableSaleReport);
		} catch (Exception exp) {
			LOGGER.error(exp);
		}
	}

	private void searchByStartEndDate(Date startDate, Date endDate) {
		try {
			List<AccountInfo> accountInfo = AccountDaoImpl.getInstance().getItemList();
			List<InvoiceInfo> invoiceInfo = InvoiceDaoImpl.getInstance().getAllByStartEndDate(startDate, endDate);
			List<SalesReportInfoModel> salesReportInfoModels = new ArrayList<>();

			for (InvoiceInfo invoice : invoiceInfo) {
				SalesReportInfoModel saleReport = new SalesReportInfoModel();
				saleReport.setInvoiceNumber(Integer.valueOf(invoice.getInvoicNum()));
				int partyID = invoice.getPartyinfoID();
				for (AccountInfo acInfo : accountInfo) {
					if (acInfo.getId() == partyID) {
						saleReport.setPartyName(acInfo.getName());
						break;
					}
				}
				saleReport.setBillNumber(invoice.getBillNo());
				saleReport.setDate(invoice.getInvoicDate());
				saleReport.setBAmount(invoice.getBillAmount());
				saleReport.setWAmount(invoice.getWithoutBillAmount());
				double bAmount = Double
						.valueOf(Strings.isNullOrEmpty(invoice.getBillAmount()) ? "0" : invoice.getBillAmount());
				double wAmount = Double.valueOf(
						Strings.isNullOrEmpty(invoice.getWithoutBillAmount()) ? "0" : invoice.getWithoutBillAmount());
				try {
					saleReport.setTotal(bAmount + wAmount);
				} catch (Exception exp) {
					LOGGER.error(exp);
				}
				salesReportInfoModels.add(saleReport);
			}
			SalesReportModel model = new SalesReportModel(salesReportInfoModels);
			tableSaleReport.setModel(model);
			tableSaleReport.setRowHeight(tableSaleReport.getRowHeight() + 20);
			tableSaleReport.setAutoCreateRowSorter(true);
			tableSaleReport.setName("Sale Report");
			tableSaleReport.setColumnControlVisible(true);
			tableSaleReport.packAll();
			scrollPane.setViewportView(tableSaleReport);
		} catch (Exception exp) {
			LOGGER.error(exp);
		}
	}

	private void searchByStartEndDatePartyName(Date startDate, Date endDate, String partyName) {
		try {
			List<AccountInfo> accountInfo = AccountDaoImpl.getInstance().getItemList();
			AccountInfo accountInfo1 = accountInfo.stream().filter(e1 -> e1.getName().equalsIgnoreCase(partyName))
					.findAny().orElse(null);
			int partyID = accountInfo1.getId();

			List<InvoiceInfo> invoiceInfo = InvoiceDaoImpl.getInstance().getAllByStartEndDatePartyInfoID(startDate,
					endDate, partyID);
			List<SalesReportInfoModel> salesReportInfoModels = new ArrayList<>();

			for (InvoiceInfo invoice : invoiceInfo) {
				SalesReportInfoModel saleReport = new SalesReportInfoModel();
				saleReport.setInvoiceNumber(Integer.valueOf(invoice.getInvoicNum()));
				saleReport.setPartyName(partyName);
				saleReport.setBillNumber(invoice.getBillNo());
				saleReport.setDate(invoice.getInvoicDate());
				saleReport.setBAmount(invoice.getBillAmount());
				saleReport.setWAmount(invoice.getWithoutBillAmount());
				double bAmount = Double
						.valueOf(Strings.isNullOrEmpty(invoice.getBillAmount()) ? "0" : invoice.getBillAmount());
				double wAmount = Double.valueOf(
						Strings.isNullOrEmpty(invoice.getWithoutBillAmount()) ? "0" : invoice.getWithoutBillAmount());
				try {
					saleReport.setTotal(bAmount + wAmount);
				} catch (Exception exp) {
					LOGGER.error(exp);
				}
				salesReportInfoModels.add(saleReport);
			}
			SalesReportModel model = new SalesReportModel(salesReportInfoModels);
			tableSaleReport.setModel(model);
			tableSaleReport.setRowHeight(tableSaleReport.getRowHeight() + 20);
			tableSaleReport.setAutoCreateRowSorter(true);
			tableSaleReport.setName("Sale Report");
			tableSaleReport.setColumnControlVisible(true);
			tableSaleReport.packAll();
			scrollPane.setViewportView(tableSaleReport);
		} catch (Exception exp) {
			LOGGER.error(exp);
		}

	}

	private void searchByStartDateAndPartyName(Date startDate, String partyName) {
		try {
			List<AccountInfo> accountInfo = AccountDaoImpl.getInstance().getItemList();
			AccountInfo accountInfo1 = accountInfo.stream().filter(e1 -> e1.getName().equalsIgnoreCase(partyName))
					.findAny().orElse(null);
			int partyID = accountInfo1.getId();

			List<InvoiceInfo> invoiceInfo = InvoiceDaoImpl.getInstance().getAllByStartDateAndPartyName(startDate,
					partyID);
			List<SalesReportInfoModel> salesReportInfoModels = new ArrayList<>();

			for (InvoiceInfo invoice : invoiceInfo) {
				SalesReportInfoModel saleReport = new SalesReportInfoModel();
				saleReport.setInvoiceNumber(Integer.valueOf(invoice.getInvoicNum()));
				saleReport.setPartyName(partyName);
				saleReport.setBillNumber(invoice.getBillNo());
				saleReport.setDate(invoice.getInvoicDate());
				saleReport.setBAmount(invoice.getBillAmount());
				saleReport.setWAmount(invoice.getWithoutBillAmount());
				double bAmount = Double
						.valueOf(Strings.isNullOrEmpty(invoice.getBillAmount()) ? "0" : invoice.getBillAmount());
				double wAmount = Double.valueOf(
						Strings.isNullOrEmpty(invoice.getWithoutBillAmount()) ? "0" : invoice.getWithoutBillAmount());
				try {
					saleReport.setTotal(bAmount + wAmount);
				} catch (Exception exp) {
					LOGGER.error(exp);
				}
				salesReportInfoModels.add(saleReport);
			}
			SalesReportModel model = new SalesReportModel(salesReportInfoModels);
			tableSaleReport.setModel(model);
			tableSaleReport.setRowHeight(tableSaleReport.getRowHeight() + 20);
			tableSaleReport.setAutoCreateRowSorter(true);
			tableSaleReport.setName("Sale Report");
			tableSaleReport.setColumnControlVisible(true);
			tableSaleReport.packAll();
			scrollPane.setViewportView(tableSaleReport);
		} catch (Exception exp) {
			LOGGER.error(exp);
		}

	}
}
