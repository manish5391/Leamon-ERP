package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import leamon.erp.util.LeamonERPConstants;

public class InvoiceUILegal extends JInternalFrame {

	private  JXTextField textFieldInvoiceNumList ;	
	private JXTextField textFieldAbbr; 
	private JXTextField textFieldInvoiceNum;
	private JXTextField textFieldPartyName; 
	private JXTextField textFieldPartyMobile; 
	private JXTextField textFieldPartyState; 
	private JXTextArea textAreaPartyAddress;
	private JXTextField textFieldPartyStateCode; 
	private JXTextField textFieldBookedBy;
	private JXTextField textFieldPartyGST; 
	private JXTextField textFieldOrderNo;
	private JXDatePicker datePickerOrderDate; 
	private JXTextField textFieldCartun;
	private JXTextField textFieldLRNNo;
	private JXTextField textFieldTransportList;
	private JXDatePicker datePickerLrnDate;
	private JXTextField textFieldFreight; 
	private JXTextField textFieldPlaceOfSupply; 
	private JXTextField textFieldPvtMark; 
	private JXTextField textFieldWeight;
	private JXTextField textFieldProductDesc;
	private JXTextField textFieldProductSize;
	private JXTextField textFieldProductQty;
	private JXTextField textFieldProductUnit ;
	private JXTextField textFieldProductRate;
	private JXTextField textFieldProductAmount;
	private JXTextField textFieldProductTD;
	private JXButton buttonProductAddNewEntry;
	private JXTextField textFieldProductHSN;
	private JXTextField textFieldProductGST;
	private JXTextField textFieldTotal;
	private JXTextField invoiceBillAmount;
	private JXTextField textFieldCGst;
	private JXTextField textFieldTotalGst;
	private JXTextField textFieldGrandTotal;
	private JXTextField textFieldDiscount;
	private JXTextField textFieldTaxableValue;
	private JXTextField textFieldSGst;
	private JXTextField textFieldIgst; 
	
	private JXTextField textFieldTotal2;
	private JXButton buttonAddNew;
	private JXButton buttonUpdate;
	private JXButton buttonDelete;
	private JXButton buttonPrint;
	private JXButton buttonRefresh;
	private JXButton buttonSave;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvoiceUILegal frame = new InvoiceUILegal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InvoiceUILegal() {
		setTitle("E-Biling");
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(3, 30, 1059, 700);
		getContentPane().setLayout(null);
		setName("Leamon-ERP E-Biling");
		
		JXPanel panel = new JXPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(Color.DARK_GRAY));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1050, 36);
		getContentPane().add(panel);
		
		
		JLabel label = new JLabel("Invoice No.");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(1, 18, 67, 14);
		panel.add(label);
		
		textFieldInvoiceNumList = new JXTextField();
		textFieldInvoiceNumList.setPrompt("Invoice No.");
		textFieldInvoiceNumList.setName("txtInventoryAccountName");
		textFieldInvoiceNumList.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldInvoiceNumList.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldInvoiceNumList.setBounds(78, 8, 121, 23);
		panel.add(textFieldInvoiceNumList);
		
		JXLabel label_1 = new JXLabel();
		label_1.setText("Invoice");
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		label_1.setBounds(980, 8, 60, 21);
		panel.add(label_1);
		
		JXPanel panel_1 = new JXPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(Color.DARK_GRAY));
		panel_1.setBackground(new Color(0, 255, 255));
		panel_1.setBounds(0, 36, 1050, 182);
		getContentPane().add(panel_1);
		
		textFieldAbbr = new JXTextField();
		textFieldAbbr.setPrompt("Abbr");
		textFieldAbbr.setName("txtInventoryAccountName");
		textFieldAbbr.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldAbbr.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldAbbr.setBounds(656, 110, 39, 23);
		panel_1.add(textFieldAbbr);
		
		textFieldInvoiceNum = new JXTextField();
		textFieldInvoiceNum.setPrompt("Invoice No.");
		textFieldInvoiceNum.setName("txtInventoryBillNumber");
		textFieldInvoiceNum.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldInvoiceNum.setEnabled(false);
		textFieldInvoiceNum.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldInvoiceNum.setBounds(11, 31, 110, 23);
		panel_1.add(textFieldInvoiceNum);
		
		JXLabel label_2 = new JXLabel();
		label_2.setText("Invoice No.");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		label_2.setBounds(11, 1, 80, 25);
		panel_1.add(label_2);
		
		JXLabel label_3 = new JXLabel();
		label_3.setText("Date");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		label_3.setBounds(140, 1, 80, 25);
		panel_1.add(label_3);
		
		JXLabel label_4 = new JXLabel();
		label_4.setText("Party Name");
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_4.setBounds(302, 2, 71, 25);
		panel_1.add(label_4);
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		JXDatePicker datePickerInvoiceDate = new JXDatePicker((Date) null);
		datePickerInvoiceDate.getEditor().setEnabled(false);
		datePickerInvoiceDate.setBounds(131, 33, 145, 22);
		panel_1.add(datePickerInvoiceDate);
		
		JXLabel label_5 = new JXLabel();
		label_5.setText("GST No.");
		label_5.setForeground(Color.BLACK);
		label_5.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_5.setBounds(314, 146, 49, 25);
		panel_1.add(label_5);
		
		JXLabel label_6 = new JXLabel();
		label_6.setText("State");
		label_6.setForeground(Color.BLACK);
		label_6.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_6.setBounds(314, 110, 39, 25);
		panel_1.add(label_6);
		
		textFieldPartyName = new JXTextField();
		textFieldPartyName.setPrompt("Name");
		textFieldPartyName.setName("txtInventoryAccountName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setEnabled(false);
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyName.setBounds(395, 2, 303, 23);
		panel_1.add(textFieldPartyName);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scrollPane.setBounds(395, 33, 303, 76);
		panel_1.add(scrollPane);
		
		textAreaPartyAddress = new JXTextArea();
		textAreaPartyAddress.setPrompt("Address");
		textAreaPartyAddress.setName("textFieldADDRESS");
		textAreaPartyAddress.setEnabled(false);
		scrollPane.setViewportView(textAreaPartyAddress);
		
		textFieldPartyMobile = new JXTextField();
		textFieldPartyMobile.setPrompt("Mobile");
		textFieldPartyMobile.setName("txtInventoryAccountName");
		textFieldPartyMobile.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyMobile.setEnabled(false);
		textFieldPartyMobile.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyMobile.setBounds(574, 146, 124, 23);
		panel_1.add(textFieldPartyMobile);
		
		textFieldPartyState = new JXTextField();
		textFieldPartyState.setPrompt("State");
		textFieldPartyState.setName("txtInventoryAccountName");
		textFieldPartyState.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyState.setEnabled(false);
		textFieldPartyState.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyState.setBounds(395, 112, 136, 23);
		panel_1.add(textFieldPartyState);
		
		JXLabel label_7 = new JXLabel();
		label_7.setText("Code");
		label_7.setForeground(Color.BLACK);
		label_7.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_7.setBounds(541, 110, 39, 25);
		panel_1.add(label_7);
		
		textFieldPartyStateCode = new JXTextField();
		textFieldPartyStateCode.setPrompt("Code");
		textFieldPartyStateCode.setName("txtInventoryAccountName");
		textFieldPartyStateCode.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyStateCode.setEnabled(false);
		textFieldPartyStateCode.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyStateCode.setBounds(574, 110, 39, 23);
		panel_1.add(textFieldPartyStateCode);
		
		JXLabel label_11 = new JXLabel();
		label_11.setText("Booked By.");
		label_11.setForeground(Color.BLACK);
		label_11.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_11.setBounds(721, 31, 68, 25);
		panel_1.add(label_11);
		
		textFieldBookedBy = new JXTextField();
		textFieldBookedBy.setPrompt("Booked by ");
		textFieldBookedBy.setName("txtInventoryBookedBy");
		textFieldBookedBy.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldBookedBy.setEnabled(false);
		textFieldBookedBy.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldBookedBy.setBounds(787, 31, 71, 23);
		panel_1.add(textFieldBookedBy);
		
		textFieldPartyGST = new JXTextField();
		textFieldPartyGST.setPrompt("GST No.");
		textFieldPartyGST.setName("txtInventoryAccountName");
		textFieldPartyGST.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyGST.setEnabled(false);
		textFieldPartyGST.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyGST.setBounds(395, 146, 136, 23);
		panel_1.add(textFieldPartyGST);
		
		JXLabel label_12 = new JXLabel();
		label_12.setText("Mob.");
		label_12.setForeground(Color.BLACK);
		label_12.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_12.setBounds(543, 146, 30, 25);
		panel_1.add(label_12);
		
		JXLabel label_13 = new JXLabel();
		label_13.setText("*");
		label_13.setForeground(Color.RED);
		label_13.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_13.setBounds(379, 6, 6, 16);
		panel_1.add(label_13);
		
		JXLabel label_14 = new JXLabel();
		label_14.setText("*");
		label_14.setForeground(Color.RED);
		label_14.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_14.setBounds(347, 110, 6, 16);
		panel_1.add(label_14);
		
		JXLabel label_15 = new JXLabel();
		label_15.setText("*");
		label_15.setForeground(Color.RED);
		label_15.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_15.setBounds(95, 4, 6, 16);
		panel_1.add(label_15);
		
		JXLabel label_16 = new JXLabel();
		label_16.setText("*");
		label_16.setForeground(Color.RED);
		label_16.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_16.setBounds(176, 1, 6, 16);
		panel_1.add(label_16);
		
		JXLabel label_17 = new JXLabel();
		label_17.setText("Abbr");
		label_17.setForeground(Color.BLACK);
		label_17.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_17.setBounds(623, 110, 39, 25);
		panel_1.add(label_17);
		
		JXLabel lblOrderNo = new JXLabel();
		lblOrderNo.setText("Order No.");
		lblOrderNo.setForeground(Color.BLACK);
		lblOrderNo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblOrderNo.setBounds(721, 2, 68, 25);
		panel_1.add(lblOrderNo);
		
		textFieldOrderNo = new JXTextField();
		textFieldOrderNo.setPrompt("Order No.");
		textFieldOrderNo.setName("txtInventoryBookedBy");
		textFieldOrderNo.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldOrderNo.setEnabled(false);
		textFieldOrderNo.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldOrderNo.setBounds(787, 2, 71, 23);
		panel_1.add(textFieldOrderNo);
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		datePickerOrderDate = new JXDatePicker((Date) null);
		datePickerOrderDate.getEditor().setEnabled(false);
		datePickerOrderDate.setBounds(923, 4, 117, 22);
		panel_1.add(datePickerOrderDate);
		
		JXLabel lblDate = new JXLabel();
		lblDate.setText("Date");
		lblDate.setForeground(Color.BLACK);
		lblDate.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblDate.setBounds(874, 2, 39, 25);
		panel_1.add(lblDate);
		
		textFieldCartun = new JXTextField();
		textFieldCartun.setPrompt("Cartun");
		textFieldCartun.setName("textFieldGoodsPackets1");
		textFieldCartun.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldCartun.setEnabled(false);
		textFieldCartun.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldCartun.setBounds(947, 31, 93, 23);
		panel_1.add(textFieldCartun);
		
		JXLabel label_40 = new JXLabel();
		label_40.setText("No. of Pkt.");
		label_40.setForeground(Color.GRAY);
		label_40.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_40.setBounds(868, 35, 83, 16);
		panel_1.add(label_40);
		
		JXLabel lblLrnNo = new JXLabel();
		lblLrnNo.setText("LRN No.");
		lblLrnNo.setForeground(Color.BLACK);
		lblLrnNo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblLrnNo.setBounds(721, 57, 68, 25);
		panel_1.add(lblLrnNo);
		
		textFieldLRNNo = new JXTextField();
		textFieldLRNNo.setPrompt("LRN No.");
		textFieldLRNNo.setName("txtInventoryBookedBy");
		textFieldLRNNo.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldLRNNo.setEnabled(false);
		textFieldLRNNo.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldLRNNo.setBounds(787, 57, 71, 23);
		panel_1.add(textFieldLRNNo);
		
		JXLabel label_41 = new JXLabel();
		label_41.setText("Date");
		label_41.setForeground(Color.BLACK);
		label_41.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_41.setBounds(874, 65, 39, 25);
		panel_1.add(label_41);
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		datePickerLrnDate = new JXDatePicker((Date) null);
		datePickerLrnDate.getEditor().setEnabled(false);
		datePickerLrnDate.setBounds(923, 62, 117, 22);
		panel_1.add(datePickerLrnDate);
		
		textFieldTransportList = new JXTextField();
		textFieldTransportList.setPrompt("Transport");
		textFieldTransportList.setName("txtInventoryAccountTransport");
		textFieldTransportList.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTransportList.setEnabled(false);
		textFieldTransportList.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTransportList.setBounds(786, 91, 254, 23);
		panel_1.add(textFieldTransportList);
		
		JXLabel label_42 = new JXLabel();
		label_42.setText("Transport");
		label_42.setForeground(Color.GRAY);
		label_42.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_42.setBounds(721, 93, 68, 16);
		panel_1.add(label_42);
		
		JXLabel lblFreight = new JXLabel();
		lblFreight.setText("Freight");
		lblFreight.setForeground(Color.BLACK);
		lblFreight.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblFreight.setBounds(721, 120, 68, 25);
		panel_1.add(lblFreight);
		
		textFieldFreight = new JXTextField();
		textFieldFreight.setPrompt("Freight");
		textFieldFreight.setName("txtInventoryBookedBy");
		textFieldFreight.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldFreight.setEnabled(false);
		textFieldFreight.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldFreight.setBounds(787, 120, 71, 23);
		panel_1.add(textFieldFreight);
		
		JXLabel lblPlaceOfSupply = new JXLabel();
		lblPlaceOfSupply.setText("Place Of Supply");
		lblPlaceOfSupply.setForeground(Color.BLACK);
		lblPlaceOfSupply.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblPlaceOfSupply.setBounds(721, 146, 102, 25);
		panel_1.add(lblPlaceOfSupply);
		
		textFieldPlaceOfSupply = new JXTextField();
		textFieldPlaceOfSupply.setPrompt("Place of supply");
		textFieldPlaceOfSupply.setName("txtInventoryBookedBy");
		textFieldPlaceOfSupply.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPlaceOfSupply.setEnabled(false);
		textFieldPlaceOfSupply.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPlaceOfSupply.setBounds(821, 146, 145, 23);
		panel_1.add(textFieldPlaceOfSupply);
		
		JXLabel lblPvtMark = new JXLabel();
		lblPvtMark.setText("Pvt. Mark");
		lblPvtMark.setForeground(Color.BLACK);
		lblPvtMark.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblPvtMark.setBounds(874, 120, 68, 25);
		panel_1.add(lblPvtMark);
		
		JXTextField textFieldPvtMark = new JXTextField();
		textFieldPvtMark.setPrompt("Pvt. Mark");
		textFieldPvtMark.setName("txtInventoryBookedBy");
		textFieldPvtMark.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPvtMark.setEnabled(false);
		textFieldPvtMark.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPvtMark.setBounds(938, 120, 102, 23);
		panel_1.add(textFieldPvtMark);
		
		JXLabel lblWt = new JXLabel();
		lblWt.setText("Wt.");
		lblWt.setForeground(Color.BLACK);
		lblWt.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblWt.setBounds(965, 146, 30, 25);
		panel_1.add(lblWt);
		
		textFieldWeight = new JXTextField();
		textFieldWeight.setPrompt("Wt.");
		textFieldWeight.setName("txtInventoryBookedBy");
		textFieldWeight.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldWeight.setEnabled(false);
		textFieldWeight.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldWeight.setBounds(991, 149, 49, 23);
		panel_1.add(textFieldWeight);
		
		JXPanel panel_4 = new JXPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(0, 221, 1050, 49);
		getContentPane().add(panel_4);
		
		textFieldProductDesc = new JXTextField();
		textFieldProductDesc.setPrompt("Description");
		textFieldProductDesc.setName("invoiceDescription");
		textFieldProductDesc.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductDesc.setEnabled(false);
		textFieldProductDesc.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductDesc.setBounds(26, 21, 282, 23);
		panel_4.add(textFieldProductDesc);
		
		JLabel label_21 = new JLabel("Description:");
		label_21.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_21.setBounds(26, 6, 67, 14);
		panel_4.add(label_21);
		
		JLabel label_22 = new JLabel("*");
		label_22.setForeground(Color.RED);
		label_22.setBounds(97, 6, 14, 14);
		panel_4.add(label_22);
		
		textFieldProductSize = new JXTextField();
		textFieldProductSize.setPrompt("Size");
		textFieldProductSize.setName("invoiceSize");
		textFieldProductSize.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductSize.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductSize.setEnabled(false);
		textFieldProductSize.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductSize.setBounds(318, 21, 104, 23);
		panel_4.add(textFieldProductSize);
		
		JLabel label_23 = new JLabel("Size:");
		label_23.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_23.setBounds(339, 6, 41, 14);
		panel_4.add(label_23);
		
		JLabel label_24 = new JLabel("Qty:");
		label_24.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_24.setBounds(542, 6, 30, 14);
		panel_4.add(label_24);
		
		JLabel label_25 = new JLabel("*");
		label_25.setForeground(Color.RED);
		label_25.setBounds(546, 6, 14, 14);
		panel_4.add(label_25);
		
		textFieldProductQty = new JXTextField();
		textFieldProductQty.setPrompt("Qty.");
		textFieldProductQty.setName("invoiceQty");
		textFieldProductQty.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductQty.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductQty.setEnabled(false);
		textFieldProductQty.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductQty.setBounds(534, 21, 77, 23);
		panel_4.add(textFieldProductQty);
		
		JLabel label_26 = new JLabel("Unit:");
		label_26.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_26.setBounds(633, 6, 26, 14);
		panel_4.add(label_26);
		
		JLabel label_27 = new JLabel("*");
		label_27.setForeground(Color.RED);
		label_27.setBounds(662, 6, 14, 14);
		panel_4.add(label_27);
		
		textFieldProductUnit = new JXTextField();
		textFieldProductUnit.setPrompt("Unit");
		textFieldProductUnit.setName("invoiceUnit");
		textFieldProductUnit.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductUnit.setEnabled(false);
		textFieldProductUnit.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductUnit.setBounds(621, 21, 65, 23);
		panel_4.add(textFieldProductUnit);
		
		JLabel label_28 = new JLabel("Rate:");
		label_28.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_28.setBounds(705, 6, 35, 14);
		panel_4.add(label_28);
		
		JLabel label_29 = new JLabel("*");
		label_29.setForeground(Color.RED);
		label_29.setBounds(737, 6, 14, 14);
		panel_4.add(label_29);
		
		textFieldProductRate = new JXTextField();
		textFieldProductRate.setPrompt("Rate");
		textFieldProductRate.setName("invoiceRate");
		textFieldProductRate.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductRate.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductRate.setEnabled(false);
		textFieldProductRate.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductRate.setBounds(696, 21, 71, 23);
		panel_4.add(textFieldProductRate);
		
		JLabel label_30 = new JLabel("Amount:");
		label_30.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_30.setBounds(798, 6, 53, 14);
		panel_4.add(label_30);
		
		textFieldProductAmount = new JXTextField();
		textFieldProductAmount.setPrompt("Amount");
		textFieldProductAmount.setName("invoiceAmount");
		textFieldProductAmount.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductAmount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductAmount.setEnabled(false);
		textFieldProductAmount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductAmount.setBounds(770, 21, 137, 23);
		panel_4.add(textFieldProductAmount);
		
		JLabel label_31 = new JLabel("TD%");
		label_31.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_31.setBounds(931, 6, 24, 14);
		panel_4.add(label_31);
		
		textFieldProductTD = new JXTextField();
		textFieldProductTD.setPrompt("TD%");
		textFieldProductTD.setName("invoiceTd");
		textFieldProductTD.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductTD.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductTD.setEnabled(false);
		textFieldProductTD.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductTD.setBounds(925, 21, 42, 23);
		panel_4.add(textFieldProductTD);
		
		buttonProductAddNewEntry = new JXButton();
		buttonProductAddNewEntry.setText("+");
		buttonProductAddNewEntry.setName("invoiceAddRow");
		buttonProductAddNewEntry.setMnemonic('+');
		buttonProductAddNewEntry.setEnabled(false);
		buttonProductAddNewEntry.setBounds(980, 6, 43, 43);
		panel_4.add(buttonProductAddNewEntry);
		
		JLabel lblHsn = new JLabel("HSN");
		lblHsn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHsn.setBounds(432, 6, 41, 14);
		panel_4.add(lblHsn);
		
		textFieldProductHSN = new JXTextField();
		textFieldProductHSN.setPrompt("HSN");
		textFieldProductHSN.setName("invoiceSize");
		textFieldProductHSN.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductHSN.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductHSN.setEnabled(false);
		textFieldProductHSN.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductHSN.setBounds(432, 21, 41, 23);
		panel_4.add(textFieldProductHSN);
		
		textFieldProductGST = new JXTextField();
		textFieldProductGST.setPrompt("GST");
		textFieldProductGST.setName("invoiceSize");
		textFieldProductGST.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductGST.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductGST.setEnabled(false);
		textFieldProductGST.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductGST.setBounds(483, 21, 41, 23);
		panel_4.add(textFieldProductGST);
		
		JLabel lblGst = new JLabel("GST%");
		lblGst.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGst.setBounds(483, 6, 41, 14);
		panel_4.add(lblGst);
		
		JXPanel panel_5 = new JXPanel();
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(0, 255, 255));
		panel_5.setBounds(0, 553, 1050, 70);
		getContentPane().add(panel_5);
		
		textFieldTotal = new JXTextField();
		textFieldTotal.setPrompt("Total");
		textFieldTotal.setName("invoiceSubTotal");
		textFieldTotal.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTotal.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTotal.setEnabled(false);
		textFieldTotal.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotal.setBounds(10, 38, 90, 23);
		panel_5.add(textFieldTotal);
		
		JXLabel label_32 = new JXLabel();
		label_32.setText("Total");
		label_32.setForeground(new Color(0, 51, 0));
		label_32.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_32.setBackground(Color.WHITE);
		label_32.setBounds(22, 16, 39, 16);
		panel_5.add(label_32);
		
		invoiceBillAmount = new JXTextField();
		invoiceBillAmount.setPrompt("");
		invoiceBillAmount.setName("InvoiceBillAmount");
		invoiceBillAmount.setHorizontalAlignment(SwingConstants.CENTER);
		invoiceBillAmount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		invoiceBillAmount.setEnabled(false);
		invoiceBillAmount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		invoiceBillAmount.setBounds(722, 38, 92, 23);
		panel_5.add(invoiceBillAmount);
		
		JXTextField textField_22 = new JXTextField();
		textField_22.setPrompt("");
		textField_22.setName((String) null);
		textField_22.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_22.setEnabled(false);
		textField_22.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_22.setBounds(824, 38, 96, 23);
		panel_5.add(textField_22);
		
		JXLabel lblCgst = new JXLabel();
		lblCgst.setText("CGST");
		lblCgst.setForeground(new Color(0, 102, 51));
		lblCgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblCgst.setBounds(294, 16, 60, 16);
		panel_5.add(lblCgst);
		
		textFieldCGst = new JXTextField();
		textFieldCGst.setPrompt("GST");
		textFieldCGst.setName("tax");
		textFieldCGst.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldCGst.setEnabled(false);
		textFieldCGst.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldCGst.setBounds(294, 38, 60, 23);
		panel_5.add(textFieldCGst);
		
		JXLabel lblTotalGst = new JXLabel();
		lblTotalGst.setText("Total GST");
		lblTotalGst.setForeground(new Color(0, 102, 51));
		lblTotalGst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTotalGst.setBounds(500, 16, 71, 16);
		panel_5.add(lblTotalGst);
		
		textFieldTotalGst = new JXTextField();
		textFieldTotalGst.setPrompt("Total GST");
		textFieldTotalGst.setName((String) null);
		textFieldTotalGst.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTotalGst.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTotalGst.setEnabled(false);
		textFieldTotalGst.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotalGst.setBounds(500, 38, 96, 23);
		panel_5.add(textFieldTotalGst);
		
		JXLabel label_37 = new JXLabel();
		label_37.setText("G.Total");
		label_37.setForeground(new Color(0, 102, 51));
		label_37.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_37.setBounds(942, 16, 71, 16);
		panel_5.add(label_37);
		
		textFieldGrandTotal = new JXTextField();
		textFieldGrandTotal.setPrompt("G.Total");
		textFieldGrandTotal.setName((String) null);
		textFieldGrandTotal.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldGrandTotal.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldGrandTotal.setEnabled(false);
		textFieldGrandTotal.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldGrandTotal.setBounds(943, 38, 107, 23);
		panel_5.add(textFieldGrandTotal);
		
		JXLabel label_38 = new JXLabel();
		label_38.setText("Discount");
		label_38.setForeground(new Color(0, 51, 0));
		label_38.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_38.setBounds(122, 16, 71, 16);
		panel_5.add(label_38);
		
		textFieldDiscount = new JXTextField();
		textFieldDiscount.setPrompt("Discount");
		textFieldDiscount.setName((String) null);
		textFieldDiscount.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldDiscount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldDiscount.setEnabled(false);
		textFieldDiscount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldDiscount.setBounds(110, 38, 81, 23);
		panel_5.add(textFieldDiscount);
		
		textFieldTaxableValue = new JXTextField();
		textFieldTaxableValue.setPrompt("Taxable V.");
		textFieldTaxableValue.setName((String) null);
		textFieldTaxableValue.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTaxableValue.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTaxableValue.setEnabled(false);
		textFieldTaxableValue.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTaxableValue.setBounds(194, 38, 90, 23);
		panel_5.add(textFieldTaxableValue);
		
		JXLabel label_39 = new JXLabel();
		label_39.setText("Taxable value");
		label_39.setForeground(new Color(0, 102, 51));
		label_39.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_39.setBounds(193, 16, 90, 16);
		panel_5.add(label_39);
		
		textFieldSGst = new JXTextField();
		textFieldSGst.setPrompt("GST");
		textFieldSGst.setName("tax");
		textFieldSGst.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldSGst.setEnabled(false);
		textFieldSGst.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldSGst.setBounds(360, 38, 60, 23);
		panel_5.add(textFieldSGst);
		
		JXLabel lblSgst = new JXLabel();
		lblSgst.setText("SGST");
		lblSgst.setForeground(new Color(0, 102, 51));
		lblSgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblSgst.setBounds(360, 16, 60, 16);
		panel_5.add(lblSgst);
		
		textFieldIgst = new JXTextField();
		textFieldIgst.setPrompt("GST");
		textFieldIgst.setName("tax");
		textFieldIgst.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldIgst.setEnabled(false);
		textFieldIgst.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldIgst.setBounds(430, 38, 60, 23);
		panel_5.add(textFieldIgst);
		
		JXLabel lblIgst = new JXLabel();
		lblIgst.setText("IGST");
		lblIgst.setForeground(new Color(0, 102, 51));
		lblIgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblIgst.setBounds(430, 16, 60, 16);
		panel_5.add(lblIgst);
		
		textFieldTotal2 = new JXTextField();
		textFieldTotal2.setPrompt("Total GST");
		textFieldTotal2.setName((String) null);
		textFieldTotal2.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTotal2.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTotal2.setEnabled(false);
		textFieldTotal2.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotal2.setBounds(604, 38, 96, 23);
		panel_5.add(textFieldTotal2);
		
		JXLabel lblTotal = new JXLabel();
		lblTotal.setText("Total");
		lblTotal.setForeground(new Color(0, 102, 51));
		lblTotal.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTotal.setBounds(604, 16, 71, 16);
		panel_5.add(lblTotal);
		
		JXTextField textField_37 = new JXTextField();
		textField_37.setPrompt("");
		textField_37.setName((String) null);
		textField_37.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_37.setEnabled(false);
		textField_37.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_37.setBounds(824, 11, 96, 23);
		panel_5.add(textField_37);
		
		JXTextField textField_38 = new JXTextField();
		textField_38.setPrompt("");
		textField_38.setName("InvoiceBillAmount");
		textField_38.setHorizontalAlignment(SwingConstants.CENTER);
		textField_38.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_38.setEnabled(false);
		textField_38.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_38.setBounds(722, 11, 92, 23);
		panel_5.add(textField_38);
		
		JXPanel panel_6 = new JXPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_6.setBackground(new Color(255, 245, 238));
		panel_6.setBounds(0, 623, 1050, 57);
		getContentPane().add(panel_6);
		
		buttonAddNew = new JXButton();
		buttonAddNew.setText("Add New");
		buttonAddNew.setMnemonic(KeyEvent.VK_N);
		buttonAddNew.setBounds(20, 13, 98, 27);
		panel_6.add(buttonAddNew);
		
		buttonUpdate = new JXButton();
		buttonUpdate.setText("Update");
		buttonUpdate.setMnemonic(KeyEvent.VK_U);
		buttonUpdate.setEnabled(false);
		buttonUpdate.setBounds(124, 13, 98, 27);
		panel_6.add(buttonUpdate);
		
		buttonDelete = new JXButton();
		buttonDelete.setText("Delete");
		buttonDelete.setMnemonic(KeyEvent.VK_D);
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(232, 13, 98, 27);
		panel_6.add(buttonDelete);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(502, 13, 89, 22);
		panel_6.add(comboBox);
		
		buttonPrint = new JXButton();
		buttonPrint.setText("Print");
		buttonPrint.setMnemonic(KeyEvent.VK_P);
		buttonPrint.setBounds(597, 13, 98, 27);
		panel_6.add(buttonPrint);
		
		buttonSave = new JXButton();
		buttonSave.setText("Save");
		buttonSave.setMnemonic(KeyEvent.VK_S);
		buttonSave.setEnabled(false);
		buttonSave.setBounds(705, 13, 98, 27);
		panel_6.add(buttonSave);
		
		buttonRefresh = new JXButton();
		buttonRefresh.setText("Refresh");
		buttonRefresh.setMnemonic(KeyEvent.VK_R);
		buttonRefresh.setBounds(813, 13, 98, 27);
		panel_6.add(buttonRefresh);
		
		JXButton buttonClose = new JXButton();
		buttonClose.setText("Close");
		buttonClose.setBounds(921, 13, 98, 27);
		buttonClose.addActionListener(e -> buttonCloseClick(e));
		panel_6.add(buttonClose);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 272, 1050, 280);
		getContentPane().add(scrollPane_1);

	}
	
	public void setEnableOnLoad(boolean isTurnOn){

		textFieldProductDesc.setEnabled(isTurnOn);
		textFieldProductUnit.setEnabled(isTurnOn);
		textFieldProductSize.setEnabled(isTurnOn);

		textFieldTotal.setEnabled(isTurnOn);
		/*textFieldBillAmount.setEnabled(isTurnOn);
		textFieldPackingAmount.setEnabled(isTurnOn);

		textFieldTotal.setEnabled(isTurnOn);
		textFieldBillAmount.setEnabled(isTurnOn);
		textFieldPackingAmount.setEnabled(isTurnOn);
		textFieldInvoiceNum.setEnabled(isTurnOn);
		datePickerInvoiceDate.getEditor().setEnabled(isTurnOn);
		textFieldPartyName.setEnabled(isTurnOn);
		textFieldPartyState.setEnabled(isTurnOn);
		textFieldStateCode.setEnabled(isTurnOn);
		textFieldPartyGST.setEnabled(isTurnOn);
		textFieldPartyMobile.setEnabled(isTurnOn);
		textFieldPartyTransportList.setEnabled(isTurnOn);
		textFieldGoodsPackets1.setEnabled(isTurnOn);
		textFieldOrderBookedBy.setEnabled(isTurnOn);

		textFieldGoodsPackets2.setEnabled(isTurnOn);
		textAreaPartyAddress.setEnabled(isTurnOn);

		textFieldProductDesc.setEnabled(isTurnOn);
		textFieldProductSize.setEnabled(isTurnOn);
		textFieldProductQty.setEnabled(isTurnOn);
		textFieldProductUnit.setEnabled(isTurnOn);
		textFieldProductRate.setEnabled(isTurnOn);
		textFieldProductAmount.setEnabled(isTurnOn);
		textFieldProductTD.setEnabled(isTurnOn);
		textFieldPartynickName.setEnabled(isTurnOn);

		textFieldTAX.setEnabled(isTurnOn);
		textFieldGTotal1.setEnabled(isTurnOn);
		textFieldGtotal2.setEnabled(isTurnOn);

		textFieldDiscount.setEnabled(isTurnOn);
		textFieldTaxableValue.setEnabled(isTurnOn);
		btnAddInvoiceEntry.setEnabled(isTurnOn);
		btnSave.setEnabled(isTurnOn);
		tableInvoice.setEnabled(isTurnOn);*/
	}

	
	private void buttonCloseClick(ActionEvent e){
		this.dispose();
	}
}
