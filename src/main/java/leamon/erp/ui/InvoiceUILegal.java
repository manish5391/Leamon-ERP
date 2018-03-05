package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import com.google.common.base.Strings;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.StateCityInfo;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

import javax.swing.border.BevelBorder;
import org.jdesktop.swingx.JXTable;
import javax.swing.JTable;
import javax.swing.JTextField;

@Getter
public class InvoiceUILegal extends JInternalFrame {

	private static final String CLASS_NAME="InvoiceUILegal";
	private static final Logger LOGGER = Logger.getLogger(InvoiceUILegal.class);
	
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
	private JXTextField textFieldCol1NameValue;
	private JXTextField textFieldCGst;
	private JXTextField textFieldTotalGst;
	private JXTextField textFieldGrandTotal;
	private JXTextField textFieldDiscount;
	private JXTextField textFieldTaxableValue;
	private JXTextField textFieldSGst;
	private JXTextField textFieldIgst; 
	private JXTextField textFieldCol1Name;
	private JXTextField textFieldCol2Name; 
	private JXTextField textFieldCol2NameValue;
	
	private JXTextField textFieldTotal2;
	private JXButton buttonAddNew;
	private JXButton buttonUpdate;
	private JXButton buttonDelete;
	private JXButton buttonPrint;
	private JXButton buttonRefresh;
	private JXButton buttonSave;
	
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;

	/**
	 * Create the frame.
	 */
	public InvoiceUILegal() {
		setTitle("E-Biling");
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		setBounds(3, 30, 1059, 660);
		getContentPane().setLayout(null);
		setName("Leamon-ERP E-Biling");
		
		JXPanel panel = new JXPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(Color.DARK_GRAY));
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1050, 31);
		getContentPane().add(panel);
		
		
		JLabel label = new JLabel("Invoice No.");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(10, 4, 67, 14);
		panel.add(label);
		
		textFieldInvoiceNumList = new JXTextField();
		textFieldInvoiceNumList.setPrompt("Invoice No.");
		textFieldInvoiceNumList.setName("txtInventoryAccountName");
		textFieldInvoiceNumList.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldInvoiceNumList.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldInvoiceNumList.setBounds(75, 4, 82, 18);
		panel.add(textFieldInvoiceNumList);
		
		JXLabel labelTitle = new JXLabel();
		labelTitle.setText("Invoice");
		labelTitle.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		labelTitle.setBounds(980, 4, 60, 21);
		panel.add(labelTitle);
		
		JXPanel panel_1 = new JXPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBackground(new Color(0, 255, 255));
		panel_1.setBounds(0, 32, 1050, 163);
		getContentPane().add(panel_1);
		
		textFieldAbbr = new JXTextField();
		textFieldAbbr.setPrompt("Abbr");
		textFieldAbbr.setName("txtInventoryAccountName");
		textFieldAbbr.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldAbbr.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldAbbr.setBounds(656, 90, 39, 23);
		panel_1.add(textFieldAbbr);
		
		textFieldInvoiceNum = new JXTextField();
		textFieldInvoiceNum.setPrompt("Invoice No.");
		textFieldInvoiceNum.setName("txtInventoryBillNumber");
		textFieldInvoiceNum.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldInvoiceNum.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldInvoiceNum.setBounds(11, 31, 110, 23);
		panel_1.add(textFieldInvoiceNum);
		
		JXLabel label_2 = new JXLabel();
		label_2.setText("Invoice No.");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		label_2.setBounds(11, 7, 80, 18);
		panel_1.add(label_2);
		
		JXLabel label_3 = new JXLabel();
		label_3.setText("Date");
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		label_3.setBounds(140, 9, 55, 18);
		panel_1.add(label_3);
		
		JXLabel label_4 = new JXLabel();
		label_4.setText("Party Name");
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_4.setBounds(302, 2, 71, 25);
		panel_1.add(label_4);
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		JXDatePicker datePickerInvoiceDate = new JXDatePicker(new Date());
		datePickerInvoiceDate.setFormats(df);
		datePickerInvoiceDate.setBounds(131, 33, 145, 22);
		datePickerInvoiceDate.getMonthView().setZoomable(true);
		
		panel_1.add(datePickerInvoiceDate);
		
		JXLabel label_5 = new JXLabel();
		label_5.setText("GST No.");
		label_5.setForeground(Color.BLACK);
		label_5.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_5.setBounds(314, 120, 49, 25);
		panel_1.add(label_5);
		
		JXLabel label_6 = new JXLabel();
		label_6.setText("State");
		label_6.setForeground(Color.BLACK);
		label_6.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_6.setBounds(314, 90, 39, 25);
		panel_1.add(label_6);
		
		textFieldPartyName = new JXTextField();
		textFieldPartyName.setPrompt("Party Name");
		textFieldPartyName.setName("txtInventoryAccountName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyName.setBounds(395, 8, 303, 23);
		panel_1.add(textFieldPartyName);
		/*Auto - textFieldPartyName*/
		autoAccountInfoSuggestor(textFieldPartyName);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		scrollPane.setBounds(395, 33, 303, 55);
		panel_1.add(scrollPane);
		
		textAreaPartyAddress = new JXTextArea();
		textAreaPartyAddress.setPrompt("Address");
		textAreaPartyAddress.setName("textFieldADDRESS");
		scrollPane.setViewportView(textAreaPartyAddress);
		
		textFieldPartyMobile = new JXTextField();
		textFieldPartyMobile.setPrompt("Mobile");
		textFieldPartyMobile.setName("txtInventoryAccountName");
		textFieldPartyMobile.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyMobile.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyMobile.setBounds(574, 120, 124, 23);
		panel_1.add(textFieldPartyMobile);
		
		textFieldPartyState = new JXTextField();
		textFieldPartyState.setPrompt("State");
		textFieldPartyState.setName("txtInventoryAccountName");
		textFieldPartyState.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyState.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyState.setBounds(395, 90, 136, 23);
		panel_1.add(textFieldPartyState);
		
		JXLabel label_7 = new JXLabel();
		label_7.setText("Code");
		label_7.setForeground(Color.BLACK);
		label_7.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_7.setBounds(541, 90, 39, 25);
		panel_1.add(label_7);
		
		textFieldPartyStateCode = new JXTextField();
		textFieldPartyStateCode.setPrompt("Code");
		textFieldPartyStateCode.setName("txtInventoryAccountName");
		textFieldPartyStateCode.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyStateCode.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyStateCode.setBounds(574, 90, 39, 23);
		panel_1.add(textFieldPartyStateCode);
		
		JXLabel label_11 = new JXLabel();
		label_11.setText("Booked By.");
		label_11.setForeground(Color.BLACK);
		label_11.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_11.setBounds(721, 31, 68, 18);
		panel_1.add(label_11);
		
		textFieldBookedBy = new JXTextField();
		textFieldBookedBy.setPrompt("Booked by ");
		textFieldBookedBy.setName("txtInventoryBookedBy");
		textFieldBookedBy.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldBookedBy.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldBookedBy.setBounds(787, 31, 71, 23);
		panel_1.add(textFieldBookedBy);
		
		textFieldPartyGST = new JXTextField();
		textFieldPartyGST.setPrompt("GST No.");
		textFieldPartyGST.setName("txtInventoryAccountName");
		textFieldPartyGST.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyGST.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyGST.setBounds(395, 120, 136, 23);
		panel_1.add(textFieldPartyGST);
		
		JXLabel label_12 = new JXLabel();
		label_12.setText("Mob.");
		label_12.setForeground(Color.BLACK);
		label_12.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_12.setBounds(543, 120, 30, 25);
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
		label_14.setBounds(347, 90, 6, 16);
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
		label_16.setBounds(176, 9, 6, 16);
		panel_1.add(label_16);
		
		JXLabel label_17 = new JXLabel();
		label_17.setText("Abbr");
		label_17.setForeground(Color.BLACK);
		label_17.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_17.setBounds(623, 90, 39, 25);
		panel_1.add(label_17);
		
		JXLabel lblOrderNo = new JXLabel();
		lblOrderNo.setText("Order No.");
		lblOrderNo.setForeground(Color.BLACK);
		lblOrderNo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblOrderNo.setBounds(721, 8, 68, 18);
		panel_1.add(lblOrderNo);
		
		textFieldOrderNo = new JXTextField();
		textFieldOrderNo.setPrompt("Order No.");
		textFieldOrderNo.setName("txtInventoryBookedBy");
		textFieldOrderNo.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldOrderNo.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldOrderNo.setBounds(787, 7, 71, 23);
		panel_1.add(textFieldOrderNo);
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		df = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerOrderDate = new JXDatePicker(new Date());
		datePickerOrderDate.setFormats(df);
		datePickerOrderDate.setBounds(923, 7, 117, 22);
		panel_1.add(datePickerOrderDate);
		datePickerOrderDate.getMonthView().setZoomable(true);
		
		JXLabel lblDate = new JXLabel();
		lblDate.setText("Date");
		lblDate.setForeground(Color.BLACK);
		lblDate.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblDate.setBounds(874, 8, 39, 18);
		panel_1.add(lblDate);
		
		textFieldCartun = new JXTextField();
		textFieldCartun.setPrompt("Cartun");
		textFieldCartun.setName("textFieldGoodsPackets1");
		textFieldCartun.setFont(new Font("DialogInput", Font.PLAIN, 16));
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
		lblLrnNo.setText("LR No.");
		lblLrnNo.setForeground(Color.BLACK);
		lblLrnNo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblLrnNo.setBounds(721, 57, 68, 18);
		panel_1.add(lblLrnNo);
		
		textFieldLRNNo = new JXTextField();
		textFieldLRNNo.setPrompt("LR No.");
		textFieldLRNNo.setName("txtInventoryBookedBy");
		textFieldLRNNo.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldLRNNo.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldLRNNo.setBounds(787, 57, 71, 23);
		panel_1.add(textFieldLRNNo);
		
		JXLabel label_41 = new JXLabel();
		label_41.setText("Date");
		label_41.setForeground(Color.BLACK);
		label_41.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_41.setBounds(874, 58, 39, 18);
		panel_1.add(label_41);
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		df = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerLrnDate = new JXDatePicker(new Date());
		datePickerLrnDate.setBounds(923, 58, 117, 22);
		datePickerLrnDate.setFormats(df);
		datePickerLrnDate.getMonthView().setZoomable(true);
		panel_1.add(datePickerLrnDate);
		
		textFieldTransportList = new JXTextField();
		textFieldTransportList.setPrompt("Transport");
		textFieldTransportList.setName("txtInventoryAccountTransport");
		textFieldTransportList.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTransportList.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTransportList.setBounds(786, 85, 254, 23);
		panel_1.add(textFieldTransportList);
		
		JXLabel label_42 = new JXLabel();
		label_42.setText("Transport");
		label_42.setForeground(Color.GRAY);
		label_42.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_42.setBounds(721, 85, 64, 18);
		panel_1.add(label_42);
		
		JXLabel lblFreight = new JXLabel();
		lblFreight.setText("Freight");
		lblFreight.setForeground(Color.BLACK);
		lblFreight.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblFreight.setBounds(721, 110, 50, 18);
		panel_1.add(lblFreight);
		
		textFieldFreight = new JXTextField();
		textFieldFreight.setPrompt("Freight");
		textFieldFreight.setName("txtInventoryBookedBy");
		textFieldFreight.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldFreight.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldFreight.setBounds(787, 110, 71, 23);
		panel_1.add(textFieldFreight);
		
		JXLabel lblPlaceOfSupply = new JXLabel();
		lblPlaceOfSupply.setText("Place Of Supply");
		lblPlaceOfSupply.setForeground(Color.BLACK);
		lblPlaceOfSupply.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblPlaceOfSupply.setBounds(721, 136, 102, 18);
		panel_1.add(lblPlaceOfSupply);
		
		textFieldPlaceOfSupply = new JXTextField();
		textFieldPlaceOfSupply.setPrompt("Place of supply");
		textFieldPlaceOfSupply.setName("txtInventoryBookedBy");
		textFieldPlaceOfSupply.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPlaceOfSupply.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPlaceOfSupply.setBounds(821, 136, 145, 23);
		panel_1.add(textFieldPlaceOfSupply);
		
		JXLabel lblPvtMark = new JXLabel();
		lblPvtMark.setText("Pvt. Mark");
		lblPvtMark.setForeground(Color.BLACK);
		lblPvtMark.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblPvtMark.setBounds(874, 110, 62, 18);
		panel_1.add(lblPvtMark);
		
		JXTextField textFieldPvtMark = new JXTextField();
		textFieldPvtMark.setPrompt("Pvt. Mark");
		textFieldPvtMark.setName("txtInventoryBookedBy");
		textFieldPvtMark.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPvtMark.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPvtMark.setBounds(938, 110, 102, 23);
		panel_1.add(textFieldPvtMark);
		
		JXLabel lblWt = new JXLabel();
		lblWt.setText("Wt.");
		lblWt.setForeground(Color.BLACK);
		lblWt.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblWt.setBounds(965, 136, 24, 18);
		panel_1.add(lblWt);
		
		textFieldWeight = new JXTextField();
		textFieldWeight.setPrompt("Wt.");
		textFieldWeight.setName("txtInventoryBookedBy");
		textFieldWeight.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldWeight.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldWeight.setBounds(991, 139, 49, 23);
		panel_1.add(textFieldWeight);
		
		JXPanel panel_4 = new JXPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(0, 196, 1050, 43);
		getContentPane().add(panel_4);
		
		textFieldProductDesc = new JXTextField();
		textFieldProductDesc.setPrompt("Description");
		textFieldProductDesc.setName("invoiceDescription");
		textFieldProductDesc.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductDesc.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductDesc.setBounds(26, 18, 282, 23);
		panel_4.add(textFieldProductDesc);
		
		JLabel label_21 = new JLabel("Description:");
		label_21.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_21.setBounds(26, 3, 67, 14);
		panel_4.add(label_21);
		
		JLabel label_22 = new JLabel("*");
		label_22.setForeground(Color.RED);
		label_22.setBounds(97, 7, 10, 9);
		panel_4.add(label_22);
		
		textFieldProductSize = new JXTextField();
		textFieldProductSize.setPrompt("Size");
		textFieldProductSize.setName("invoiceSize");
		textFieldProductSize.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductSize.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductSize.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductSize.setBounds(318, 18, 104, 23);
		panel_4.add(textFieldProductSize);
		
		JLabel label_23 = new JLabel("Size:");
		label_23.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_23.setBounds(339, 3, 25, 14);
		panel_4.add(label_23);
		
		JLabel label_24 = new JLabel("Qty:");
		label_24.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_24.setBounds(542, 3, 25, 14);
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
		textFieldProductQty.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductQty.setBounds(534, 18, 77, 23);
		panel_4.add(textFieldProductQty);
		
		JLabel label_26 = new JLabel("Unit:");
		label_26.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_26.setBounds(633, 3, 26, 14);
		panel_4.add(label_26);
		
		JLabel label_27 = new JLabel("*");
		label_27.setForeground(Color.RED);
		label_27.setBounds(662, 8, 10, 9);
		panel_4.add(label_27);
		
		textFieldProductUnit = new JXTextField();
		textFieldProductUnit.setPrompt("Unit");
		textFieldProductUnit.setName("invoiceUnit");
		textFieldProductUnit.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductUnit.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductUnit.setBounds(621, 18, 65, 23);
		panel_4.add(textFieldProductUnit);
		
		JLabel label_28 = new JLabel("Rate:");
		label_28.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_28.setBounds(705, 3, 35, 14);
		panel_4.add(label_28);
		
		JLabel label_29 = new JLabel("*");
		label_29.setForeground(Color.RED);
		label_29.setBounds(737, 8, 10, 9);
		panel_4.add(label_29);
		
		textFieldProductRate = new JXTextField();
		textFieldProductRate.setPrompt("Rate");
		textFieldProductRate.setName("invoiceRate");
		textFieldProductRate.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductRate.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductRate.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductRate.setBounds(696, 18, 71, 23);
		panel_4.add(textFieldProductRate);
		
		JLabel label_30 = new JLabel("Amount:");
		label_30.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_30.setBounds(798, 3, 45, 14);
		panel_4.add(label_30);
		
		textFieldProductAmount = new JXTextField();
		textFieldProductAmount.setPrompt("Amount");
		textFieldProductAmount.setName("invoiceAmount");
		textFieldProductAmount.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductAmount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductAmount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductAmount.setBounds(770, 18, 137, 23);
		panel_4.add(textFieldProductAmount);
		
		JLabel label_31 = new JLabel("TD%");
		label_31.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_31.setBounds(931, 3, 24, 14);
		panel_4.add(label_31);
		
		textFieldProductTD = new JXTextField();
		textFieldProductTD.setPrompt("TD%");
		textFieldProductTD.setName("invoiceTd");
		textFieldProductTD.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductTD.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductTD.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductTD.setBounds(925, 18, 42, 23);
		panel_4.add(textFieldProductTD);
		
		buttonProductAddNewEntry = new JXButton();
		buttonProductAddNewEntry.setText("+");
		buttonProductAddNewEntry.setName("invoiceAddRow");
		buttonProductAddNewEntry.setMnemonic('+');
		buttonProductAddNewEntry.setBounds(980, 6, 43, 35);
		panel_4.add(buttonProductAddNewEntry);
		
		JLabel lblHsn = new JLabel("HSN");
		lblHsn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHsn.setBounds(432, 3, 25, 14);
		panel_4.add(lblHsn);
		
		textFieldProductHSN = new JXTextField();
		textFieldProductHSN.setPrompt("HSN");
		textFieldProductHSN.setName("invoiceSize");
		textFieldProductHSN.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductHSN.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductHSN.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductHSN.setBounds(432, 18, 41, 23);
		panel_4.add(textFieldProductHSN);
		
		textFieldProductGST = new JXTextField();
		textFieldProductGST.setPrompt("GST");
		textFieldProductGST.setName("invoiceSize");
		textFieldProductGST.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductGST.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductGST.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductGST.setBounds(483, 18, 41, 23);
		panel_4.add(textFieldProductGST);
		
		JLabel lblGst = new JLabel("GST%");
		lblGst.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGst.setBounds(483, 3, 32, 14);
		panel_4.add(lblGst);
		
		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.RED);
		label_1.setBounds(570, 8, 10, 9);
		panel_4.add(label_1);
		
		JXPanel panel_5 = new JXPanel();
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(0, 255, 255));
		panel_5.setBounds(0, 529, 1050, 60);
		getContentPane().add(panel_5);
		
		textFieldTotal = new JXTextField();
		textFieldTotal.setPrompt("Total");
		textFieldTotal.setName("invoiceSubTotal");
		textFieldTotal.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTotal.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTotal.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotal.setBounds(10, 33, 90, 23);
		panel_5.add(textFieldTotal);
		
		JXLabel label_32 = new JXLabel();
		label_32.setText("Total");
		label_32.setForeground(new Color(0, 51, 0));
		label_32.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_32.setBackground(Color.WHITE);
		label_32.setBounds(22, 5, 39, 16);
		panel_5.add(label_32);
		
		textFieldCol1NameValue = new JXTextField();
		textFieldCol1NameValue.setPrompt("");
		textFieldCol1NameValue.setName("InvoiceBillAmount");
		textFieldCol1NameValue.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCol1NameValue.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldCol1NameValue.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldCol1NameValue.setBounds(722, 33, 92, 23);
		panel_5.add(textFieldCol1NameValue);
		
		textFieldCol2NameValue = new JXTextField();
		textFieldCol2NameValue.setPrompt("");
		textFieldCol2NameValue.setName((String) null);
		textFieldCol2NameValue.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldCol2NameValue.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldCol2NameValue.setBounds(824, 33, 96, 23);
		panel_5.add(textFieldCol2NameValue);
		
		JXLabel lblCgst = new JXLabel();
		lblCgst.setText("CGST");
		lblCgst.setForeground(new Color(0, 102, 51));
		lblCgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblCgst.setBounds(294, 5, 60, 16);
		panel_5.add(lblCgst);
		
		textFieldCGst = new JXTextField();
		textFieldCGst.setPrompt("GST");
		textFieldCGst.setName("tax");
		textFieldCGst.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldCGst.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldCGst.setBounds(294, 33, 60, 23);
		panel_5.add(textFieldCGst);
		
		JXLabel lblTotalGst = new JXLabel();
		lblTotalGst.setText("Total GST");
		lblTotalGst.setForeground(new Color(0, 102, 51));
		lblTotalGst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTotalGst.setBounds(500, 5, 71, 16);
		panel_5.add(lblTotalGst);
		
		textFieldTotalGst = new JXTextField();
		textFieldTotalGst.setPrompt("Total GST");
		textFieldTotalGst.setName((String) null);
		textFieldTotalGst.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTotalGst.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTotalGst.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotalGst.setBounds(500, 33, 96, 23);
		panel_5.add(textFieldTotalGst);
		
		JXLabel label_37 = new JXLabel();
		label_37.setText("G.Total");
		label_37.setForeground(new Color(0, 102, 51));
		label_37.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_37.setBounds(942, 5, 71, 16);
		panel_5.add(label_37);
		
		textFieldGrandTotal = new JXTextField();
		textFieldGrandTotal.setPrompt("G.Total");
		textFieldGrandTotal.setName((String) null);
		textFieldGrandTotal.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldGrandTotal.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldGrandTotal.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldGrandTotal.setBounds(940, 33, 107, 23);
		panel_5.add(textFieldGrandTotal);
		
		JXLabel label_38 = new JXLabel();
		label_38.setText("Discount");
		label_38.setForeground(new Color(0, 51, 0));
		label_38.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_38.setBounds(122, 5, 71, 16);
		panel_5.add(label_38);
		
		textFieldDiscount = new JXTextField();
		textFieldDiscount.setPrompt("Discount");
		textFieldDiscount.setName((String) null);
		textFieldDiscount.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldDiscount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldDiscount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldDiscount.setBounds(110, 33, 81, 23);
		panel_5.add(textFieldDiscount);
		
		textFieldTaxableValue = new JXTextField();
		textFieldTaxableValue.setPrompt("Taxable V.");
		textFieldTaxableValue.setName((String) null);
		textFieldTaxableValue.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTaxableValue.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTaxableValue.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTaxableValue.setBounds(194, 33, 90, 23);
		panel_5.add(textFieldTaxableValue);
		
		JXLabel label_39 = new JXLabel();
		label_39.setText("Taxable value");
		label_39.setForeground(new Color(0, 102, 51));
		label_39.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_39.setBounds(193, 5, 90, 16);
		panel_5.add(label_39);
		
		textFieldSGst = new JXTextField();
		textFieldSGst.setPrompt("GST");
		textFieldSGst.setName("tax");
		textFieldSGst.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldSGst.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldSGst.setBounds(360, 33, 60, 23);
		panel_5.add(textFieldSGst);
		
		JXLabel lblSgst = new JXLabel();
		lblSgst.setText("SGST");
		lblSgst.setForeground(new Color(0, 102, 51));
		lblSgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblSgst.setBounds(360, 5, 60, 16);
		panel_5.add(lblSgst);
		
		textFieldIgst = new JXTextField();
		textFieldIgst.setPrompt("GST");
		textFieldIgst.setName("tax");
		textFieldIgst.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldIgst.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldIgst.setBounds(430, 33, 60, 23);
		panel_5.add(textFieldIgst);
		
		JXLabel lblIgst = new JXLabel();
		lblIgst.setText("IGST");
		lblIgst.setForeground(new Color(0, 102, 51));
		lblIgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblIgst.setBounds(430, 5, 60, 16);
		panel_5.add(lblIgst);
		
		textFieldTotal2 = new JXTextField();
		textFieldTotal2.setPrompt("Total");
		textFieldTotal2.setName((String) null);
		textFieldTotal2.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTotal2.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTotal2.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotal2.setBounds(604, 33, 96, 23);
		panel_5.add(textFieldTotal2);
		
		JXLabel lblTotal = new JXLabel();
		lblTotal.setText("Total");
		lblTotal.setForeground(new Color(0, 102, 51));
		lblTotal.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTotal.setBounds(604, 5, 71, 16);
		panel_5.add(lblTotal);
		
		textFieldCol2Name = new JXTextField();
		textFieldCol2Name.setPrompt("");
		textFieldCol2Name.setName((String) null);
		textFieldCol2Name.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldCol2Name.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldCol2Name.setBounds(824, 5, 96, 23);
		panel_5.add(textFieldCol2Name);
		
		textFieldCol1Name = new JXTextField();
		textFieldCol1Name.setPrompt("");
		textFieldCol1Name.setName("InvoiceBillAmount");
		textFieldCol1Name.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCol1Name.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldCol1Name.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldCol1Name.setBounds(722, 5, 92, 23);
		panel_5.add(textFieldCol1Name);
		
		JXPanel panel_6 = new JXPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_6.setBackground(new Color(255, 245, 238));
		panel_6.setBounds(0, 590, 1050, 45);
		getContentPane().add(panel_6);
		
		buttonAddNew = new JXButton();
		buttonAddNew.setText("Add New");
		buttonAddNew.setMnemonic(KeyEvent.VK_N);
		buttonAddNew.setBounds(20, 5, 98, 27);
		// 3.6 ghan code
		try {
			buttonAddNew.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_ADD)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		panel_6.add(buttonAddNew);
		buttonAddNew.addActionListener(e -> buttonAddNewClick(e));
		
		buttonUpdate = new JXButton();
		buttonUpdate.setText("Update");
		buttonUpdate.setMnemonic(KeyEvent.VK_U);
		buttonUpdate.setBounds(124, 5, 98, 27);
		// 3.6 ghan code
		try {
			buttonUpdate.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_UPDATE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		panel_6.add(buttonUpdate);
		
		buttonDelete = new JXButton();
		buttonDelete.setText("Delete");
		buttonDelete.setMnemonic(KeyEvent.VK_D);
		buttonDelete.setBounds(232, 5, 98, 27);
		// 3.6 ghan code
		try {
			buttonDelete.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_DELETE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		panel_6.add(buttonDelete);
		
		JComboBox comboBoxPrint = new JComboBox();
		comboBoxPrint.setBounds(502, 5, 89, 22);
		panel_6.add(comboBoxPrint);
		
		buttonPrint = new JXButton();
		buttonPrint.setText("Print");
		buttonPrint.setMnemonic(KeyEvent.VK_P);
		buttonPrint.setBounds(597, 5, 98, 27);
		// 3.6 ghan code
		try {
			buttonPrint.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_PRINT)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		panel_6.add(buttonPrint);
		
		buttonSave = new JXButton();
		buttonSave.setText("Save");
		buttonSave.setMnemonic(KeyEvent.VK_S);
		buttonSave.setBounds(705, 5, 98, 27);
		// 3.6 ghan code
		try {
			buttonSave.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_SAVE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		panel_6.add(buttonSave);
		
		buttonRefresh = new JXButton();
		buttonRefresh.setText("Refresh");
		buttonRefresh.setMnemonic(KeyEvent.VK_R);
		buttonRefresh.setBounds(813, 5, 98, 27);
		// 3.6 ghan code
		try {
			buttonRefresh.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_REFERESH)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		panel_6.add(buttonRefresh);
		
		JXButton buttonClose = new JXButton();
		buttonClose.setText("Close");
		buttonClose.setBounds(921, 5, 98, 27);
		// 3.6 ghan code
		try {
			buttonClose.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_B_W_INVOICE_CLOSE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		buttonClose.addActionListener(e -> buttonCloseClick(e));
		panel_6.add(buttonClose);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 242, 1050, 284);
		getContentPane().add(scrollPane_1);
		
		JXTable table = new JXTable();
		table.setToolTipText("invoice items");
		table.setName("TableInoice");
		table.setColumnControlVisible(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_1.setViewportView(table);

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
	
	/**
	 * It sets party information.
	 * 
	 * @since Release 3.6
	 * @date FEB 13,2018
	 * @author Manish Kumar Mishra
	 * @param info
	 * 
	 */
	public void setAccountInfo(AccountInfo info){
		textFieldPartyName.setText(info.getName());
		textAreaPartyAddress.setText(info.getCity());
		textAreaPartyAddress.repaint();
		
		if(!Strings.isNullOrEmpty(info.getCity())){
			String item = info.getCity();
			StateCityInfo stateCityInfo = LeamonERP.stateCityInfos.stream().filter(ele -> ele.getCity().equals(item)).findAny().orElse(null);
			if(stateCityInfo!=null){
				textFieldPartyState.setText(stateCityInfo.getState());
				textFieldPartyStateCode.setText(stateCityInfo.getStateCode());
				textFieldAbbr.setText(stateCityInfo.getAbbreviations());
			}
		}else{
			textFieldPartyState.setText(info.getState());
			textFieldPartyStateCode.setText(String.valueOf(info.getPinCode()));
		}

		textFieldPartyGST.setText(info.getGstNumber());
		textFieldPartyMobile.setText(String.valueOf(info.getPhone()));

		textFieldTransportList.setText(info.getTransport());
		//textFieldPartynickName.setText(info.getNickName());
		textFieldPartyGST.setText(info.getGstNumber());
		
	}
	
	private void save(){
		textFieldInvoiceNum.getText();
	//	datePickerInvoiceDate.getText();
		textFieldPartyName.getText();
		textAreaPartyAddress.getText();
		textFieldPartyState.getText();
		textFieldPartyGST.getText();
		textFieldPartyMobile.getText();
		textFieldPartyStateCode.getText();
		textFieldAbbr.getText();
		textFieldOrderNo.getText();
		textFieldBookedBy.getText();
		textFieldLRNNo.getText();
		textFieldTransportList.getText();
		textFieldFreight.getText();
		textFieldPlaceOfSupply.getText();
		//datePickerOrderDate.getText();
		textFieldCartun.getText();
		//datePickerLrnDate.getText();
		textFieldPvtMark.getText();
		textFieldWeight.getText();

		textFieldProductDesc.getText();
		textFieldProductSize.getText();
		textFieldProductHSN.getText();
		textFieldProductGST.getText();
		textFieldProductQty.getText();
		textFieldProductUnit.getText();
		textFieldProductRate.getText();
		textFieldProductAmount.getText();
		textFieldProductTD.getText();


		textFieldTotal.getText();
		textFieldDiscount.getText();
		textFieldTaxableValue.getText();
		textFieldCGst.getText();
		textFieldSGst.getText();
		textFieldIgst.getText();
		textFieldTotalGst.getText();
		textFieldTotal2.getText();
		textFieldCol1Name.getText();
		textFieldCol1NameValue.getText();
		textFieldCol2Name.getText();
		textFieldCol2NameValue.getText();
		textFieldGrandTotal.getText();

	}
	
	public void setEnableOnLoad(boolean isTurnOn){
		textFieldInvoiceNum.setEnabled(isTurnOn);
		//datePickerInvoiceDate.setEnabled(isTurnOn);
		textFieldPartyName.setEnabled(isTurnOn);
		textAreaPartyAddress.setEnabled(isTurnOn);
		textFieldPartyState.setEnabled(isTurnOn);
		textFieldPartyGST.setEnabled(isTurnOn);
		textFieldPartyMobile.setEnabled(isTurnOn);
		textFieldPartyStateCode.setEnabled(isTurnOn);
		textFieldAbbr.setEnabled(isTurnOn);
		textFieldOrderNo.setEnabled(isTurnOn);
		textFieldBookedBy.setEnabled(isTurnOn);
		textFieldLRNNo.setEnabled(isTurnOn);
		textFieldTransportList.setEnabled(isTurnOn);
		textFieldFreight.setEnabled(isTurnOn);
		textFieldPlaceOfSupply.setEnabled(isTurnOn);
		datePickerOrderDate.setEnabled(isTurnOn);
		textFieldCartun.setEnabled(isTurnOn);
		datePickerLrnDate.setEnabled(isTurnOn);
		textFieldPvtMark.setEnabled(isTurnOn);
		textFieldWeight.setEnabled(isTurnOn);

		textFieldProductDesc.setEnabled(isTurnOn);
		textFieldProductSize.setEnabled(isTurnOn);
		textFieldProductHSN.setEnabled(isTurnOn);
		textFieldProductGST.setEnabled(isTurnOn);
		textFieldProductQty.setEnabled(isTurnOn);
		textFieldProductUnit.setEnabled(isTurnOn);
		textFieldProductRate.setEnabled(isTurnOn);
		textFieldProductAmount.setEnabled(isTurnOn);
		textFieldProductTD.setEnabled(isTurnOn);


		textFieldTotal.setEnabled(isTurnOn);
		textFieldDiscount.setEnabled(isTurnOn);
		textFieldTaxableValue.setEnabled(isTurnOn);
		textFieldCGst.setEnabled(isTurnOn);
		textFieldSGst.setEnabled(isTurnOn);
		textFieldIgst.setEnabled(isTurnOn);
		textFieldTotalGst.setEnabled(isTurnOn);
		textFieldTotal2.setEnabled(isTurnOn);
		textFieldCol1Name.setEnabled(isTurnOn);
		textFieldCol1NameValue.setEnabled(isTurnOn);
		textFieldCol2Name.setEnabled(isTurnOn);
		textFieldCol2NameValue.setEnabled(isTurnOn);
		textFieldGrandTotal.setEnabled(isTurnOn);

		
	}

	private void refresh(){
		textFieldInvoiceNum.setText(LeamonERPConstants.EMPTY_STR);;
		//datePickerInvoiceDate.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);;
		textAreaPartyAddress.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldPartyState.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldPartyGST.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldPartyMobile.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldPartyStateCode.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldAbbr.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldOrderNo.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldBookedBy.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldLRNNo.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldTransportList.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldFreight.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldPlaceOfSupply.setText(LeamonERPConstants.EMPTY_STR);;
		//datePickerOrderDate.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldCartun.setText(LeamonERPConstants.EMPTY_STR);;
		//datePickerLrnDate.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldPvtMark.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldWeight.setText(LeamonERPConstants.EMPTY_STR);;

		textFieldProductDesc.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldProductSize.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldProductHSN.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldProductGST.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldProductQty.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldProductUnit.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldProductRate.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldProductAmount.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldProductTD.setText(LeamonERPConstants.EMPTY_STR);;


		textFieldTotal.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldDiscount.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldTaxableValue.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldCGst.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldSGst.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldIgst.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldTotalGst.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldTotal2.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldCol1Name.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldCol1NameValue.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldCol2Name.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldCol2NameValue.setText(LeamonERPConstants.EMPTY_STR);;
		textFieldGrandTotal.setText(LeamonERPConstants.EMPTY_STR);;

		//datePickerInvoiceDate.getEditor().setEnabled(false);
	}
	private void buttonAddNewClick(ActionEvent e){
		setEnableOnLoad(Boolean.TRUE);
	}
	
	private void buttonCloseClick(ActionEvent e){
		this.dispose();
	}
}
