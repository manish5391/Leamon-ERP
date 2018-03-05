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
import java.util.Date;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import leamon.erp.util.LeamonERPConstants;
import org.jdesktop.swingx.JXButton;
import javax.swing.JButton;
import org.jdesktop.swingx.JXPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
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

	private JXDatePicker datePickerStartDate;
	private JXDatePicker datePickerEndDate;
	
	private JXTextField textFieldPartyName;
	
	private JXButton buttonSaleRegister;
	private JXButton btnExportToExcel;
	private JXButton btnPrintBill;
	private JButton buttonClose;
	private JButton buttonRefresh;
	
	private JXTable tableSaleReport;
	
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
		panel.add(textFieldPartyName);
		
		buttonSaleRegister = new JXButton();
		buttonSaleRegister.setText("Sale Register");
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
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		
		tableSaleReport = new JXTable();
		scrollPane.setViewportView(tableSaleReport);
	}
	
	private void buttonSaleRegisterClick(ActionEvent e){
		
	}
	
	private void btnExportToExcelClick(ActionEvent e){
		
	}
	
	private void btnPrintBillClick(ActionEvent e){
		
	}
	
	private void buttonCloseClick(ActionEvent e){
		
	}
	
	private void buttonRefreshClick(ActionEvent e){
		
	}
}
