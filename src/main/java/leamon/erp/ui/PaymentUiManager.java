package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import org.jdesktop.swingx.JXDatePicker;
import java.util.Date;
import org.jdesktop.swingx.JXTextField;
import leamon.erp.util.LeamonERPConstants;
import org.jdesktop.swingx.JXButton;
import javax.swing.JButton;
import org.jdesktop.swingx.JXPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;
import javax.swing.JTabbedPane;
import org.jdesktop.swingx.JXTreeTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.persistence.EnumType;
import leamon.erp.util.InvoicePaymentStatusEnum;
import leamon.erp.util.PaymentEnum;

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
		
		datePickerStartDate = new JXDatePicker((Date) null);
		datePickerStartDate.setBounds(75, 12, 116, 22);
		panel.add(datePickerStartDate);
		
		datePickerEndDate = new JXDatePicker((Date) null);
		datePickerEndDate.setBounds(253, 12, 116, 22);
		panel.add(datePickerEndDate);
		
		textFieldPartyName = new JXTextField();
		textFieldPartyName.setPrompt("Party Name");
		textFieldPartyName.setName("txtPartyName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyName.setBounds(410, 13, 302, 23);
		panel.add(textFieldPartyName);
		
		btnSearch = new JXButton();
		btnSearch.setText("Search");
		btnSearch.setBounds(791, 12, 115, 24);
		panel.add(btnSearch);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(PaymentEnum.values()));
		comboBox.setBounds(722, 12, 59, 22);
		panel.add(comboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(0, 474, 847, 34);
		getContentPane().add(panel_1);
		
		buttonExcel = new JXButton();
		buttonExcel.setText("Export To Excel");
		buttonExcel.setBounds(30, 8, 118, 23);
		panel_1.add(buttonExcel);
		
		buttonPrint = new JXButton();
		buttonPrint.setText("Print Bill");
		buttonPrint.setBounds(177, 8, 109, 23);
		panel_1.add(buttonPrint);
		
		buttonClose = new JButton("Close");
		buttonClose.setBounds(743, 8, 94, 23);
		panel_1.add(buttonClose);
		
		buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(639, 8, 94, 23);
		panel_1.add(buttonRefresh);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 44, 847, 431);
		getContentPane().add(tabbedPane);
		
		JXPanel panelPayment = new JXPanel();
		panelPayment.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Payment", null, panelPayment, null);
		panelPayment.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelPayment.add(scrollPane);
		
		tablePayment = new JXTable();
		scrollPane.setViewportView(tablePayment);
		
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
}
