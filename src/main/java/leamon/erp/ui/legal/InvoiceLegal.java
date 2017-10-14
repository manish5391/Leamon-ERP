package leamon.erp.ui.legal;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import org.jdesktop.swingx.JXPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import org.jdesktop.swingx.JXTextField;
import leamon.erp.util.LeamonERPConstants;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXDatePicker;
import java.util.Date;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXButton;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;

public class InvoiceLegal extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvoiceLegal frame = new InvoiceLegal();
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
	public InvoiceLegal() {
		setBounds(100, 100, 1056, 705);
		getContentPane().setLayout(null);
		
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
		
		JXTextField textFieldInvoiceNumList = new JXTextField();
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
		panel_1.setBackground(new Color(238, 232, 170));
		panel_1.setBounds(0, 36, 1050, 182);
		getContentPane().add(panel_1);
		
		JXTextField textFieldPartyAbbr = new JXTextField();
		textFieldPartyAbbr.setPrompt("Abbr");
		textFieldPartyAbbr.setName("txtInventoryAccountName");
		textFieldPartyAbbr.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyAbbr.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyAbbr.setBounds(656, 110, 39, 23);
		panel_1.add(textFieldPartyAbbr);
		
		JXTextField textFieldInvoiceNum = new JXTextField();
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
		
		JXTextField textFieldPartyName = new JXTextField();
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
		
		JXTextField textFieldPartyMobile = new JXTextField();
		textFieldPartyMobile.setPrompt("Mobile");
		textFieldPartyMobile.setName("txtInventoryAccountName");
		textFieldPartyMobile.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyMobile.setEnabled(false);
		textFieldPartyMobile.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyMobile.setBounds(574, 146, 124, 23);
		panel_1.add(textFieldPartyMobile);
		
		JXTextField textFieldPartyState = new JXTextField();
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
		
		JXTextField textFieldPartyStateCode = new JXTextField();
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
		label_11.setBounds(708, 29, 68, 25);
		panel_1.add(label_11);
		
		JXTextField textFieldInvoiceBookedBy = new JXTextField();
		textFieldInvoiceBookedBy.setPrompt("Booked by ");
		textFieldInvoiceBookedBy.setName("txtInventoryBookedBy");
		textFieldInvoiceBookedBy.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldInvoiceBookedBy.setEnabled(false);
		textFieldInvoiceBookedBy.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldInvoiceBookedBy.setBounds(786, 29, 71, 23);
		panel_1.add(textFieldInvoiceBookedBy);
		
		JXTextField textFieldPartyGSTNum = new JXTextField();
		textFieldPartyGSTNum.setPrompt("GST No.");
		textFieldPartyGSTNum.setName("txtInventoryAccountName");
		textFieldPartyGSTNum.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyGSTNum.setEnabled(false);
		textFieldPartyGSTNum.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyGSTNum.setBounds(395, 146, 136, 23);
		panel_1.add(textFieldPartyGSTNum);
		
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
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(11, 65, 277, 65);
		panel_1.add(panel_3);
		
		JXTextField textFieldPartyMarka = new JXTextField();
		textFieldPartyMarka.setPrompt("Marka");
		textFieldPartyMarka.setName("txtInventoryAccountMarka");
		textFieldPartyMarka.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyMarka.setEnabled(false);
		textFieldPartyMarka.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyMarka.setBounds(10, 34, 180, 23);
		panel_3.add(textFieldPartyMarka);
		
		JXLabel label_18 = new JXLabel();
		label_18.setText("/");
		label_18.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		label_18.setBounds(194, 35, 9, 20);
		panel_3.add(label_18);
		
		JXTextField textFieldPartyPackets1 = new JXTextField();
		textFieldPartyPackets1.setPrompt("Cartun");
		textFieldPartyPackets1.setName("textFieldGoodsPackets2");
		textFieldPartyPackets1.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyPackets1.setEnabled(false);
		textFieldPartyPackets1.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyPackets1.setBounds(207, 34, 60, 23);
		panel_3.add(textFieldPartyPackets1);
		
		JXLabel label_19 = new JXLabel();
		label_19.setText("Marka");
		label_19.setForeground(Color.GRAY);
		label_19.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_19.setBounds(10, 0, 68, 16);
		panel_3.add(label_19);
		
		JXLabel label_20 = new JXLabel();
		label_20.setText("No. of Pkt.");
		label_20.setForeground(Color.GRAY);
		label_20.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_20.setBounds(209, 0, 68, 16);
		panel_3.add(label_20);
		
		JXTextField textField = new JXTextField();
		textField.setPrompt("Booked by ");
		textField.setName("txtInventoryBookedBy");
		textField.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField.setEnabled(false);
		textField.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField.setBounds(784, 1, 71, 23);
		panel_1.add(textField);
		
		JXLabel lblOrderNo = new JXLabel();
		lblOrderNo.setText("Order No.");
		lblOrderNo.setForeground(Color.BLACK);
		lblOrderNo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblOrderNo.setBounds(708, 2, 68, 25);
		panel_1.add(lblOrderNo);
		
		JXDatePicker datePicker = new JXDatePicker((Date) null);
		datePicker.getEditor().setEnabled(false);
		datePicker.setBounds(940, 4, 110, 22);
		panel_1.add(datePicker);
		
		JXLabel lblDate = new JXLabel();
		lblDate.setText("Date");
		lblDate.setForeground(Color.BLACK);
		lblDate.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblDate.setBounds(890, 2, 39, 25);
		panel_1.add(lblDate);
		
		JXLabel label_40 = new JXLabel();
		label_40.setText("No. of Pkt.");
		label_40.setForeground(Color.GRAY);
		label_40.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_40.setBounds(867, 35, 68, 16);
		panel_1.add(label_40);
		
		JXTextField textField_1 = new JXTextField();
		textField_1.setPrompt("Cartun");
		textField_1.setName("textFieldGoodsPackets2");
		textField_1.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_1.setEnabled(false);
		textField_1.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_1.setBounds(940, 31, 110, 23);
		panel_1.add(textField_1);
		
		JXLabel lblLRNo = new JXLabel();
		lblLRNo.setText("L. R. No.");
		lblLRNo.setForeground(Color.BLACK);
		lblLRNo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblLRNo.setBounds(708, 55, 68, 25);
		panel_1.add(lblLRNo);
		
		JXTextField textField_2 = new JXTextField();
		textField_2.setPrompt("Booked by ");
		textField_2.setName("txtInventoryBookedBy");
		textField_2.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_2.setEnabled(false);
		textField_2.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_2.setBounds(786, 55, 71, 23);
		panel_1.add(textField_2);
		
		JXLabel label_41 = new JXLabel();
		label_41.setText("Date");
		label_41.setForeground(Color.BLACK);
		label_41.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_41.setBounds(890, 55, 39, 25);
		panel_1.add(label_41);
		
		JXDatePicker datePicker_1 = new JXDatePicker((Date) null);
		datePicker_1.getEditor().setEnabled(false);
		datePicker_1.setBounds(940, 57, 110, 22);
		panel_1.add(datePicker_1);
		
		JXLabel label_9 = new JXLabel();
		label_9.setBounds(705, 80, 68, 16);
		panel_1.add(label_9);
		label_9.setText("Transport");
		label_9.setForeground(Color.GRAY);
		label_9.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		JXTextField textFieldPartyTransport = new JXTextField();
		textFieldPartyTransport.setBounds(786, 86, 264, 23);
		panel_1.add(textFieldPartyTransport);
		textFieldPartyTransport.setPrompt("Transport");
		textFieldPartyTransport.setName("txtInventoryAccountTransport");
		textFieldPartyTransport.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyTransport.setEnabled(false);
		textFieldPartyTransport.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		
		JXLabel lblFreight = new JXLabel();
		lblFreight.setText("Freight");
		lblFreight.setForeground(Color.BLACK);
		lblFreight.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblFreight.setBounds(708, 110, 68, 25);
		panel_1.add(lblFreight);
		
		JXTextField textField_3 = new JXTextField();
		textField_3.setPrompt("Booked by ");
		textField_3.setName("txtInventoryBookedBy");
		textField_3.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_3.setEnabled(false);
		textField_3.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_3.setBounds(819, 144, 231, 23);
		panel_1.add(textField_3);
		
		JXTextField textField_4 = new JXTextField();
		textField_4.setPrompt("Booked by ");
		textField_4.setName("txtInventoryBookedBy");
		textField_4.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_4.setEnabled(false);
		textField_4.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_4.setBounds(940, 113, 100, 23);
		panel_1.add(textField_4);
		
		JXLabel lblPvtMark = new JXLabel();
		lblPvtMark.setText("Pvt Mark.");
		lblPvtMark.setForeground(Color.BLACK);
		lblPvtMark.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblPvtMark.setBounds(867, 108, 68, 25);
		panel_1.add(lblPvtMark);
		
		JXLabel lblPlaceOfSupply = new JXLabel();
		lblPlaceOfSupply.setText("Place of Supply");
		lblPlaceOfSupply.setForeground(Color.BLACK);
		lblPlaceOfSupply.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblPlaceOfSupply.setBounds(708, 146, 104, 25);
		panel_1.add(lblPlaceOfSupply);
		
		JXTextField textField_5 = new JXTextField();
		textField_5.setPrompt("Booked by ");
		textField_5.setName("txtInventoryBookedBy");
		textField_5.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_5.setEnabled(false);
		textField_5.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_5.setBounds(786, 110, 71, 23);
		panel_1.add(textField_5);
		
		JXPanel panel_4 = new JXPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(0, 218, 1050, 49);
		getContentPane().add(panel_4);
		
		JXTextField textFieldProductDesc = new JXTextField();
		textFieldProductDesc.setPrompt("Description");
		textFieldProductDesc.setName("invoiceDescription");
		textFieldProductDesc.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductDesc.setEnabled(false);
		textFieldProductDesc.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductDesc.setBounds(35, 21, 282, 23);
		panel_4.add(textFieldProductDesc);
		
		JLabel label_21 = new JLabel("Description:");
		label_21.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_21.setBounds(79, 6, 67, 14);
		panel_4.add(label_21);
		
		JLabel label_22 = new JLabel("*");
		label_22.setForeground(Color.RED);
		label_22.setBounds(150, 6, 14, 14);
		panel_4.add(label_22);
		
		JXTextField textFieldProductSize = new JXTextField();
		textFieldProductSize.setPrompt("Size");
		textFieldProductSize.setName("invoiceSize");
		textFieldProductSize.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductSize.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductSize.setEnabled(false);
		textFieldProductSize.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductSize.setBounds(327, 21, 95, 23);
		panel_4.add(textFieldProductSize);
		
		JLabel label_23 = new JLabel("Size:");
		label_23.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_23.setBounds(344, 6, 41, 14);
		panel_4.add(label_23);
		
		JLabel label_24 = new JLabel("Qty:");
		label_24.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_24.setBounds(546, 6, 30, 14);
		panel_4.add(label_24);
		
		JLabel label_25 = new JLabel("*");
		label_25.setForeground(Color.RED);
		label_25.setBounds(573, 6, 14, 14);
		panel_4.add(label_25);
		
		JXTextField textFieldProductQty = new JXTextField();
		textFieldProductQty.setPrompt("Qty.");
		textFieldProductQty.setName("invoiceQty");
		textFieldProductQty.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductQty.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductQty.setEnabled(false);
		textFieldProductQty.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductQty.setBounds(544, 21, 59, 23);
		panel_4.add(textFieldProductQty);
		
		JLabel label_26 = new JLabel("Unit:");
		label_26.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_26.setBounds(626, 6, 26, 14);
		panel_4.add(label_26);
		
		JLabel label_27 = new JLabel("*");
		label_27.setForeground(Color.RED);
		label_27.setBounds(655, 6, 14, 14);
		panel_4.add(label_27);
		
		JXTextField textFieldProductUnit = new JXTextField();
		textFieldProductUnit.setPrompt("Unit");
		textFieldProductUnit.setName("invoiceUnit");
		textFieldProductUnit.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductUnit.setEnabled(false);
		textFieldProductUnit.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductUnit.setBounds(614, 21, 65, 23);
		panel_4.add(textFieldProductUnit);
		
		JLabel label_28 = new JLabel("Rate:");
		label_28.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_28.setBounds(697, 6, 35, 14);
		panel_4.add(label_28);
		
		JLabel label_29 = new JLabel("*");
		label_29.setForeground(Color.RED);
		label_29.setBounds(731, 6, 14, 14);
		panel_4.add(label_29);
		
		JXTextField textFieldProductRate = new JXTextField();
		textFieldProductRate.setPrompt("Rate");
		textFieldProductRate.setName("invoiceRate");
		textFieldProductRate.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductRate.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductRate.setEnabled(false);
		textFieldProductRate.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductRate.setBounds(689, 21, 71, 23);
		panel_4.add(textFieldProductRate);
		
		JLabel label_30 = new JLabel("Amount:");
		label_30.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_30.setBounds(798, 6, 53, 14);
		panel_4.add(label_30);
		
		JXTextField textFieldProductAmount = new JXTextField();
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
		
		JXTextField textFieldProductTDDiscount = new JXTextField();
		textFieldProductTDDiscount.setPrompt("TD%");
		textFieldProductTDDiscount.setName("invoiceTd");
		textFieldProductTDDiscount.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductTDDiscount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductTDDiscount.setEnabled(false);
		textFieldProductTDDiscount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldProductTDDiscount.setBounds(925, 21, 42, 23);
		panel_4.add(textFieldProductTDDiscount);
		
		JXButton buttonAddEntry = new JXButton();
		buttonAddEntry.setText("+");
		buttonAddEntry.setName("invoiceAddRow");
		buttonAddEntry.setMnemonic('+');
		buttonAddEntry.setEnabled(false);
		buttonAddEntry.setBounds(980, 6, 43, 43);
		panel_4.add(buttonAddEntry);
		
		JXTextField textField_6 = new JXTextField();
		textField_6.setPrompt("Qty.");
		textField_6.setName("invoiceQty");
		textField_6.setHorizontalAlignment(SwingConstants.CENTER);
		textField_6.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_6.setEnabled(false);
		textField_6.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_6.setBounds(429, 21, 60, 23);
		panel_4.add(textField_6);
		
		JLabel lblHsn = new JLabel("HSN");
		lblHsn.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHsn.setBounds(428, 6, 30, 14);
		panel_4.add(lblHsn);
		
		JLabel lblGst = new JLabel("GST%");
		lblGst.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGst.setBounds(495, 6, 41, 14);
		panel_4.add(lblGst);
		
		JXTextField textField_7 = new JXTextField();
		textField_7.setPrompt("Qty.");
		textField_7.setName("invoiceQty");
		textField_7.setHorizontalAlignment(SwingConstants.CENTER);
		textField_7.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_7.setEnabled(false);
		textField_7.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_7.setBounds(496, 21, 41, 23);
		panel_4.add(textField_7);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 275, 1050, 280);
		getContentPane().add(scrollPane_1);
		
		JXPanel panel_5 = new JXPanel();
		panel_5.setLayout(null);
		panel_5.setBackground(new Color(240, 230, 140));
		panel_5.setBounds(0, 553, 1050, 70);
		getContentPane().add(panel_5);
		
		JXTextField textFieldTotal = new JXTextField();
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
		
		JXTextField textField_21 = new JXTextField();
		textField_21.setPrompt("Bill Amount");
		textField_21.setName("InvoiceBillAmount");
		textField_21.setHorizontalAlignment(SwingConstants.CENTER);
		textField_21.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_21.setEnabled(false);
		textField_21.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_21.setBounds(624, 38, 107, 23);
		panel_5.add(textField_21);
		
		JXLabel lblTotal = new JXLabel();
		lblTotal.setText("Total");
		lblTotal.setForeground(new Color(0, 102, 51));
		lblTotal.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTotal.setBounds(635, 16, 71, 16);
		panel_5.add(lblTotal);
		
		JXTextField textField_22 = new JXTextField();
		textField_22.setPrompt("Pkg. Amt.");
		textField_22.setName((String) null);
		textField_22.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_22.setEnabled(false);
		textField_22.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_22.setBounds(741, 38, 98, 23);
		panel_5.add(textField_22);
		
		JXLabel lblCgst = new JXLabel();
		lblCgst.setText("CGST");
		lblCgst.setForeground(new Color(0, 102, 51));
		lblCgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblCgst.setBounds(294, 16, 39, 16);
		panel_5.add(lblCgst);
		
		JXTextField textField_23 = new JXTextField();
		textField_23.setPrompt("GST");
		textField_23.setName("tax");
		textField_23.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_23.setEnabled(false);
		textField_23.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_23.setBounds(294, 38, 60, 23);
		panel_5.add(textField_23);
		
		JXLabel lblTotalGst = new JXLabel();
		lblTotalGst.setText("Total GST");
		lblTotalGst.setForeground(new Color(0, 102, 51));
		lblTotalGst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTotalGst.setBounds(530, 16, 71, 16);
		panel_5.add(lblTotalGst);
		
		JXTextField textField_24 = new JXTextField();
		textField_24.setPrompt("G.Total");
		textField_24.setName((String) null);
		textField_24.setHorizontalAlignment(SwingConstants.LEFT);
		textField_24.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_24.setEnabled(false);
		textField_24.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_24.setBounds(518, 38, 90, 23);
		panel_5.add(textField_24);
		
		JXLabel label_37 = new JXLabel();
		label_37.setText("G.Total");
		label_37.setForeground(new Color(0, 102, 51));
		label_37.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_37.setBounds(942, 16, 71, 16);
		panel_5.add(label_37);
		
		JXTextField textField_25 = new JXTextField();
		textField_25.setPrompt("G.Total");
		textField_25.setName((String) null);
		textField_25.setHorizontalAlignment(SwingConstants.LEFT);
		textField_25.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_25.setEnabled(false);
		textField_25.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_25.setBounds(942, 38, 108, 23);
		panel_5.add(textField_25);
		
		JXLabel label_38 = new JXLabel();
		label_38.setText("Discount");
		label_38.setForeground(new Color(0, 51, 0));
		label_38.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_38.setBounds(122, 16, 71, 16);
		panel_5.add(label_38);
		
		JXTextField textFieldDiscount = new JXTextField();
		textFieldDiscount.setPrompt("Discount");
		textFieldDiscount.setName((String) null);
		textFieldDiscount.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldDiscount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldDiscount.setEnabled(false);
		textFieldDiscount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldDiscount.setBounds(110, 38, 81, 23);
		panel_5.add(textFieldDiscount);
		
		JXTextField textFieldTaxaleValue = new JXTextField();
		textFieldTaxaleValue.setPrompt("Taxable V.");
		textFieldTaxaleValue.setName((String) null);
		textFieldTaxaleValue.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTaxaleValue.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTaxaleValue.setEnabled(false);
		textFieldTaxaleValue.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTaxaleValue.setBounds(194, 38, 90, 23);
		panel_5.add(textFieldTaxaleValue);
		
		JXLabel label_39 = new JXLabel();
		label_39.setText("Taxable value");
		label_39.setForeground(new Color(0, 102, 51));
		label_39.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_39.setBounds(193, 16, 90, 16);
		panel_5.add(label_39);
		
		JXLabel lblSgst = new JXLabel();
		lblSgst.setText("SGST");
		lblSgst.setForeground(new Color(0, 102, 51));
		lblSgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblSgst.setBounds(364, 16, 39, 16);
		panel_5.add(lblSgst);
		
		JXTextField textField_8 = new JXTextField();
		textField_8.setPrompt("GST");
		textField_8.setName("tax");
		textField_8.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_8.setEnabled(false);
		textField_8.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_8.setBounds(364, 38, 71, 23);
		panel_5.add(textField_8);
		
		JXLabel lblIgst = new JXLabel();
		lblIgst.setText("IGST");
		lblIgst.setForeground(new Color(0, 102, 51));
		lblIgst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblIgst.setBounds(437, 16, 39, 16);
		panel_5.add(lblIgst);
		
		JXTextField textField_9 = new JXTextField();
		textField_9.setPrompt("GST");
		textField_9.setName("tax");
		textField_9.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_9.setEnabled(false);
		textField_9.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_9.setBounds(437, 38, 71, 23);
		panel_5.add(textField_9);
		
		JXTextField textField_10 = new JXTextField();
		textField_10.setPrompt("Pkg. Amt.");
		textField_10.setName((String) null);
		textField_10.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_10.setEnabled(false);
		textField_10.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_10.setBounds(840, 38, 90, 23);
		panel_5.add(textField_10);
		
		JXTextField textField_11 = new JXTextField();
		textField_11.setPrompt("Pkg. Amt.");
		textField_11.setName((String) null);
		textField_11.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_11.setEnabled(false);
		textField_11.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_11.setBounds(741, 9, 98, 23);
		panel_5.add(textField_11);
		
		JXTextField textField_12 = new JXTextField();
		textField_12.setPrompt("Pkg. Amt.");
		textField_12.setName((String) null);
		textField_12.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_12.setEnabled(false);
		textField_12.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_12.setBounds(840, 9, 90, 23);
		panel_5.add(textField_12);
		
		JXPanel panel_6 = new JXPanel();
		panel_6.setLayout(null);
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_6.setBackground(new Color(255, 245, 238));
		panel_6.setBounds(0, 623, 1050, 57);
		getContentPane().add(panel_6);
		
		JXButton buttonAddNew = new JXButton();
		buttonAddNew.setText("Add New");
		buttonAddNew.setMnemonic(KeyEvent.VK_N);
		buttonAddNew.setBounds(20, 13, 98, 27);
		panel_6.add(buttonAddNew);
		
		JXButton buttonUpdate = new JXButton();
		buttonUpdate.setText("Update");
		buttonUpdate.setMnemonic(KeyEvent.VK_U);
		buttonUpdate.setEnabled(false);
		buttonUpdate.setBounds(124, 13, 98, 27);
		panel_6.add(buttonUpdate);
		
		JXButton buttonDelete = new JXButton();
		buttonDelete.setText("Delete");
		buttonDelete.setMnemonic(KeyEvent.VK_D);
		buttonDelete.setEnabled(false);
		buttonDelete.setBounds(232, 13, 98, 27);
		panel_6.add(buttonDelete);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(502, 13, 89, 22);
		panel_6.add(comboBox);
		
		JXButton buttonPrint = new JXButton();
		buttonPrint.setText("Print");
		buttonPrint.setMnemonic(KeyEvent.VK_P);
		buttonPrint.setBounds(597, 13, 98, 27);
		panel_6.add(buttonPrint);
		
		JXButton buttonSave = new JXButton();
		buttonSave.setText("Save");
		buttonSave.setMnemonic(KeyEvent.VK_S);
		buttonSave.setEnabled(false);
		buttonSave.setBounds(705, 13, 98, 27);
		panel_6.add(buttonSave);
		
		JXButton buttonRefresh = new JXButton();
		buttonRefresh.setText("Refresh");
		buttonRefresh.setMnemonic(KeyEvent.VK_R);
		buttonRefresh.setBounds(813, 13, 98, 27);
		panel_6.add(buttonRefresh);
		
		JXButton buttonClose = new JXButton();
		buttonClose.setText("Close");
		buttonClose.setBounds(921, 13, 98, 27);
		panel_6.add(buttonClose);

	}
}
