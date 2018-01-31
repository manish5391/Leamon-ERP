package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.NumberFormatter;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import com.google.common.base.Strings;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.component.helper.LeamonAutoCityFieldSuggestor;
import leamon.erp.component.helper.LeamonAutoInvoiceIDTextFieldSuggestor;
import leamon.erp.component.helper.LeamonAutoStockItemTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.db.InvoiceItemDaoImpl;
import leamon.erp.db.OperationInfoDaoImpl;
import leamon.erp.db.StockDaoImpl;
import leamon.erp.db.StockQuantityDaoImpl;
import leamon.erp.db.StockQuantityOrderHistoyDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.model.OperationInfo;
import leamon.erp.model.StateCityInfo;
import leamon.erp.model.StockItem;
import leamon.erp.model.StockItemQuantity;
import leamon.erp.model.StockItemQuantityOrderHistory;
import leamon.erp.report.factory.InvoicePrintFactory;
import leamon.erp.ui.event.FocusListenerHandler;
import leamon.erp.ui.event.InvoiceUiEventHandler;
import leamon.erp.ui.model.TableInvoiceModel;
import leamon.erp.util.InvoicePaymentStatusEnum;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceUI extends JInternalFrame {

	private static final String CLASS_NAME="InvoiceUI";
	private static final Logger LOGGER = Logger.getLogger(InvoiceUI.class);

	private JXTextField textFieldTotal;
	private JXTextField textFieldBillAmount;
	private JXTextField textFieldPackingAmount;
	private JXTextField textFieldInvoiceNum;
	private JXDatePicker datePickerInvoiceDate;
	private JXTextField textFieldPartyName;
	private JXTextField textFieldPartyState;
	private JXTextField textFieldStateCode;
	private JXTextField textFieldPartyGST;
	private JXTextField textFieldPartyMobile;
	private JXTextField textFieldPartyTransportList;
	private JXTextField textFieldGoodsPackets1;
	private JXTextField textFieldOrderBookedBy;
	private JXTextField textFieldPartynickName;
	private JXTextField textFieldGoodsPackets2;
	private JXTextArea  textAreaPartyAddress;
	private JXTextField textFieldInvoicenumList;
	
	private JXTextField textFieldProductDesc;
	private JXTextField textFieldProductSize;
	//private JXTextField textFieldProductQty;
	private JXTextField textFieldProductQty;
	private JXTextField textFieldProductUnit;
	private JXTextField textFieldProductRate;
	private JXTextField textFieldProductAmount;
	private JXTextField textFieldProductTD;
	private JXTextField textFieldGTotal1;
	private JXTextField textFieldGstTAX;
	private JXTextField textFieldDiscount;
	private JXTextField textFieldTaxableValue;
	private JXTextField textFieldGtotal2;
	private JXTextField textFieldStateAbbr;
	private JXTextField textFieldBillNo;

	private JComboBox comboBoxPrintCopiess;
	private JScrollPane scrollPane_1;

	private JXButton btnAdd;
	private JXButton btnUpdate;
	private JXButton btnDelete;
	private JXButton btnPrint;
	private JXButton btnSave;
	private JXButton btnRefresh;
	private JXButton btnClose;
	private JXButton btnAddInvoiceEntry;
	private JXTable tableInvoice;

	private String action;
	private InvoiceInfo actionObjectInvoiceInfo;

	final String ACTION_DELETE = "DELETE";
	final String ACTION_UPDATE = "UPDATE";
	final String ACTION_DO_NOTHING = "DoNothing";

	private LeamonAutoInvoiceIDTextFieldSuggestor<List<InvoiceInfo>, InvoiceInfo> leamonAutoInvoiceIDTextFieldSuggestor;
	private LeamonAutoStockItemTextFieldSuggestor<List<StockItem>, StockItem> leamonAutoStockItemTextFieldSuggestor;
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;
	
	/*Added fro Release 3.1*/
	private JLabel hiddenLabelStockId;
	
	/*Added from Release 3.2 */
	private JXTextField textFieldCol1;
	private JXTextField textFieldCol1Val;
	private JXTextField textFieldCol2;
	private JXTextField textFieldCol2Val;
	private JXTextField textFieldGrNumber;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvoiceUI frame = new InvoiceUI();
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
	public InvoiceUI() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Leamon-ERP-Invoice");
		setName("Leamon-ERP-Invoice");
		setResizable(true);
		setMaximizable(true);
		setClosable(true);
		setIconifiable(true);
		setBounds(100, 5, 1059, 705);

		JPanel panel = new JPanel();
		panel.setBounds(0, 275, 1050, 405);
		panel.setBackground(Color.WHITE);

		JXPanel panel_6 = new JXPanel();
		panel_6.setBounds(0, 348, 1050, 57);
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel_6.setBackground(new Color(255, 245, 238));

		JXPanel panel_7 = new JXPanel();
		panel_7.setBounds(0, 278, 1050, 70);
		panel_7.setBackground(new Color(240, 230, 140));

		btnAdd = new JXButton();
		btnAdd.addActionListener(e -> btnAddClick(e));
		btnAdd.setBounds(20, 13, 98, 27);
		btnAdd.setText("Add New");
		btnAdd.setMnemonic(KeyEvent.VK_N);
		btnAdd.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "Add");
		btnAdd.getActionMap().put("Add", getAddAction());

		btnUpdate = new JXButton();
		btnUpdate.setBounds(124, 13, 98, 27);
		btnUpdate.setText("Update");
		btnUpdate.setMnemonic(KeyEvent.VK_U);
		btnUpdate.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK), "Update");
		btnUpdate.getActionMap().put("Update", getUpdateAction());
		btnUpdate.addActionListener(e -> btnUpdateClick(e));

		btnDelete = new JXButton();
		btnDelete.setBounds(232, 13, 98, 27);
		btnDelete.setText("Delete");
		btnDelete.addActionListener(e -> btnDeleteClick(e));
		btnDelete.setMnemonic(KeyEvent.VK_D);
		btnDelete.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), "Delete");
		btnDelete.getActionMap().put("Update", getDeleteAction());

		comboBoxPrintCopiess = new JComboBox();
		comboBoxPrintCopiess.setModel(new DefaultComboBoxModel(new String[] {"", "ORIGINAL", "DUPLICATE", "TRIPLET"}));
		comboBoxPrintCopiess.setBounds(502, 13, 89, 22);

		btnPrint = new JXButton();
		btnPrint.setBounds(597, 13, 98, 27);
		btnPrint.setText("Print");
		btnPrint.addActionListener(e -> btnPrintClick(e));
		btnPrint.setMnemonic(KeyEvent.VK_P);
		btnPrint.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), "Print");
		btnPrint.getActionMap().put("Print", getPrintAction());

		btnSave = new JXButton();
		btnSave.setBounds(705, 13, 98, 27);
		btnSave.addActionListener(e -> btnSaveClickHandler(e));
		btnSave.setText("Save");
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "Save");
		btnSave.getActionMap().put("Save", getSaveAction());


		btnRefresh = new JXButton();
		btnRefresh.setBounds(813, 13, 98, 27);
		btnRefresh.setText("Refresh");
		btnRefresh.setMnemonic(KeyEvent.VK_R);
		btnRefresh.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "Refresh");
		btnRefresh.getActionMap().put("Refresh", getRefreshAction());
		btnRefresh.addActionListener(e -> btnRefreshClickHandler(e));

		btnClose = new JXButton();
		btnClose.setBounds(921, 13, 98, 27);
		btnClose.addActionListener(e -> btnCloseClick(e));
		btnClose.setText("Close");

		textFieldTotal = new JXTextField();
		textFieldTotal.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTotal.setBounds(10, 38, 90, 23);
		textFieldTotal.setPrompt("Total");
		textFieldTotal.setName(LeamonERPConstants.TEXTFIELD_INVOICE_SUB_TOTAL);
		textFieldTotal.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTotal.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JXLabel lblAmount = new JXLabel();
		lblAmount.setBackground(new Color(255, 255, 255));
		lblAmount.setBounds(22, 16, 39, 16);
		lblAmount.setText("Total");
		lblAmount.setForeground(new Color(0, 51, 0));
		lblAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		JXLabel lblBillAmount = new JXLabel();
		lblBillAmount.setBounds(707, 16, 71, 16);
		lblBillAmount.setText("Bill Amount");
		lblBillAmount.setForeground(new Color(0, 102, 51));
		lblBillAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		textFieldBillAmount = new JXTextField();
		textFieldBillAmount.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldBillAmount.setBounds(696, 38, 107, 23);
		textFieldBillAmount.setPrompt("Bill Amount");
		textFieldBillAmount.setName(LeamonERPConstants.TEXTFIELD_INVOICE_BILL_AMT);
		textFieldBillAmount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldBillAmount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JXLabel lblPackingAmt = new JXLabel();
		lblPackingAmt.setBounds(813, 16, 81, 16);
		lblPackingAmt.setText("W Amt.");
		lblPackingAmt.setForeground(new Color(0, 102, 51));
		lblPackingAmt.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		textFieldPackingAmount = new JXTextField();
		textFieldPackingAmount.setBounds(813, 38, 107, 23);
		textFieldPackingAmount.setPrompt("W. Amt.");
		textFieldPackingAmount.setName((String) null);
		textFieldPackingAmount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPackingAmount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);


		JXPanel panel_3 = new JXPanel();
		panel_3.setBounds(0, 0, 1050, 36);
		panel_3.setBorder(new LineBorder(Color.DARK_GRAY));
		panel_3.setBackground(Color.WHITE);

		JXPanel panel_4 = new JXPanel();
		panel_4.setBounds(0, 36, 1050, 182);
		panel_4.setBorder(new LineBorder(Color.DARK_GRAY));
		panel_4.setBackground(new Color(238, 232, 170));

		JXLabel lblInvoiceNo = new JXLabel();
		lblInvoiceNo.setBounds(11, 1, 80, 25);
		lblInvoiceNo.setForeground(new Color(0, 0, 0));
		lblInvoiceNo.setText("Invoice No.");
		lblInvoiceNo.setFont(new Font("Trebuchet MS", Font.BOLD, 15));

		textFieldInvoiceNum = new JXTextField();
		textFieldInvoiceNum.setBounds(11, 31, 110, 23);
		textFieldInvoiceNum.setPrompt("Invoice No.");
		textFieldInvoiceNum.setName("txtInventoryBillNumber");
		textFieldInvoiceNum.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldInvoiceNum.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerInvoiceDate = new JXDatePicker(new Date());
		datePickerInvoiceDate.setFormats(df);
		datePickerInvoiceDate.setBounds(131, 33, 145, 22);
		datePickerInvoiceDate.getMonthView().setZoomable(true);

		JXLabel lblDate = new JXLabel();
		lblDate.setBounds(140, 1, 39, 25);
		lblDate.setText("Date");
		lblDate.setForeground(new Color(0, 0, 0));
		lblDate.setFont(new Font("Trebuchet MS", Font.BOLD, 15));

		textFieldPartyName = new JXTextField();
		textFieldPartyName.setBounds(395, 2, 303, 23);
		textFieldPartyName.setPrompt("Name");
		textFieldPartyName.setName("txtInventoryAccountName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		/*Auto - textFieldPartyName*/
		autoAccountInfoSuggestor(textFieldPartyName);

		JXLabel lblPartyName = new JXLabel();
		lblPartyName.setBounds(302, 2, 71, 25);
		lblPartyName.setText("Party Name");
		lblPartyName.setForeground(new Color(0, 0, 0));
		lblPartyName.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(395, 33, 303, 76);
		scrollPane_1.setViewportBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		JXLabel lblState = new JXLabel();
		lblState.setBounds(314, 110, 39, 25);
		lblState.setText("State");
		lblState.setForeground(new Color(0, 0, 0));
		lblState.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		textFieldPartyState = new JXTextField();
		textFieldPartyState.setBounds(395, 112, 136, 23);
		textFieldPartyState.setPrompt("State");
		textFieldPartyState.setName("txtInventoryAccountName");
		textFieldPartyState.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyState.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JXLabel lblCode = new JXLabel();
		lblCode.setBounds(541, 110, 39, 25);
		lblCode.setText("Code");
		lblCode.setForeground(new Color(0, 0, 0));
		lblCode.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		textFieldStateCode = new JXTextField();
		textFieldStateCode.setBounds(574, 110, 39, 23);
		textFieldStateCode.setPrompt("Code");
		textFieldStateCode.setName("txtInventoryAccountName");
		textFieldStateCode.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldStateCode.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		textFieldStateAbbr = new JXTextField();
		textFieldStateAbbr.setPrompt("Abbr");
		textFieldStateAbbr.setName("txtInventoryAccountName");
		textFieldStateAbbr.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldStateAbbr.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldStateAbbr.setBounds(656, 110, 39, 23);
		panel_4.add(textFieldStateAbbr);

		JXLabel lblGst = new JXLabel();
		lblGst.setBounds(314, 146, 49, 25);
		lblGst.setText("GST No.");
		lblGst.setForeground(new Color(0, 0, 0));
		lblGst.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		textFieldPartyGST = new JXTextField();
		textFieldPartyGST.setBounds(395, 146, 136, 23);
		textFieldPartyGST.setPrompt("GST No.");
		textFieldPartyGST.setName("txtInventoryAccountName");
		textFieldPartyGST.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyGST.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JXLabel lblMob = new JXLabel();
		lblMob.setBounds(543, 146, 30, 25);
		lblMob.setText("Mob.");
		lblMob.setForeground(new Color(0, 0, 0));
		lblMob.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		textFieldPartyMobile = new JXTextField();
		textFieldPartyMobile.setBounds(574, 146, 124, 23);
		textFieldPartyMobile.setPrompt("Mobile");
		textFieldPartyMobile.setName("txtInventoryAccountName");
		textFieldPartyMobile.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyMobile.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(757, 125, 277, 46);
		panel_2.setBackground(new Color(255, 250, 250));

		textFieldPartyTransportList = new JXTextField();
		textFieldPartyTransportList.setBounds(10, 22, 165, 23);
		textFieldPartyTransportList.setPrompt("Transport");
		textFieldPartyTransportList.setName("txtInventoryAccountTransport");
		textFieldPartyTransportList.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyTransportList.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JXLabel label_4 = new JXLabel();
		label_4.setBounds(179, 23, 9, 20);
		label_4.setText("/");
		label_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));

		textFieldGoodsPackets1 = new JXTextField();
		textFieldGoodsPackets1.setBounds(194, 22, 60, 23);
		textFieldGoodsPackets1.setPrompt("Cartun");
		textFieldGoodsPackets1.setName(LeamonERPConstants.TEXTFIELD_INVOICE_PACKET1);
		textFieldGoodsPackets1.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldGoodsPackets1.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		textFieldOrderBookedBy = new JXTextField();
		textFieldOrderBookedBy.setBounds(835, 1, 199, 23);
		textFieldOrderBookedBy.setPrompt("Booked by ");
		textFieldOrderBookedBy.setName("txtInventoryBookedBy");
		textFieldOrderBookedBy.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldOrderBookedBy.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JXLabel lblBookedBy = new JXLabel();
		lblBookedBy.setBounds(757, 1, 68, 25);
		lblBookedBy.setText("Booked By.");
		lblBookedBy.setForeground(new Color(0, 0, 0));
		lblBookedBy.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		JXLabel lblTransport = new JXLabel();
		lblTransport.setBounds(10, 0, 68, 16);
		lblTransport.setText("Transport");
		lblTransport.setForeground(Color.GRAY);
		lblTransport.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		JXLabel lblNo = new JXLabel();
		lblNo.setBounds(194, 0, 83, 16);
		lblNo.setText("No. of Pkt.");
		lblNo.setForeground(Color.GRAY);
		lblNo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		textAreaPartyAddress = new JXTextArea();
		textAreaPartyAddress.setPrompt("Address");
		textAreaPartyAddress.setName(LeamonERPConstants.TEXTFIELD_INVOICE_ADDRESS);
		scrollPane_1.setViewportView(textAreaPartyAddress);
		autoCitySuggestor(textAreaPartyAddress);

		textFieldInvoicenumList = new JXTextField();
		textFieldInvoicenumList.setBounds(78, 8, 121, 23);
		textFieldInvoicenumList.setPrompt("Invoice No.");
		textFieldInvoicenumList.setName("txtInventoryAccountName");
		textFieldInvoicenumList.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldInvoicenumList.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		/*Auto populating invoice list*/
		autoInvoiceIdSuggestor(textFieldInvoicenumList);

		JXLabel lblInvoice = new JXLabel();
		lblInvoice.setBounds(980, 8, 60, 21);
		lblInvoice.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		lblInvoice.setText("Invoice");

		JLabel lblInvoiceNo_1 = new JLabel("Invoice No.");
		lblInvoiceNo_1.setBounds(1, 18, 67, 14);
		lblInvoiceNo_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		getContentPane().setLayout(null);
		getContentPane().add(panel);
		panel.setLayout(null);
		panel.add(panel_7);
		panel_7.setLayout(null);
		panel_7.add(textFieldTotal);
		panel_7.add(lblAmount);
		panel_7.add(textFieldBillAmount);
		panel_7.add(lblBillAmount);
		panel_7.add(textFieldPackingAmount);
		panel_7.add(lblPackingAmt);

		JXLabel lblTax = new JXLabel();
		lblTax.setText("GST");
		lblTax.setForeground(new Color(0, 102, 51));
		lblTax.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTax.setBounds(293, 16, 39, 16);
		panel_7.add(lblTax);

		textFieldGstTAX = new JXTextField();
		textFieldGstTAX.setPrompt("GST");
		textFieldGstTAX.setName(LeamonERPConstants.TEXTFIELD_INVOICE_TAX);
		textFieldGstTAX.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldGstTAX.setEnabled(false);
		textFieldGstTAX.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldGstTAX.setBounds(293, 38, 48, 23);
		panel_7.add(textFieldGstTAX);

		JXLabel lblGtotal = new JXLabel();
		lblGtotal.setText("G.Total");
		lblGtotal.setForeground(new Color(0, 102, 51));
		lblGtotal.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblGtotal.setBounds(363, 16, 71, 16);
		panel_7.add(lblGtotal);

		textFieldGTotal1 = new JXTextField();
		textFieldGTotal1.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldGTotal1.setPrompt("G.Total");
		textFieldGTotal1.setName((String) null);
		textFieldGTotal1.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldGTotal1.setEnabled(false);
		textFieldGTotal1.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldGTotal1.setBounds(351, 38, 141, 23);
		panel_7.add(textFieldGTotal1);

		JXLabel label_14 = new JXLabel();
		label_14.setText("G.Total");
		label_14.setForeground(new Color(0, 102, 51));
		label_14.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_14.setBounds(942, 16, 71, 16);
		panel_7.add(label_14);

		textFieldGtotal2 = new JXTextField();
		textFieldGtotal2.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldGtotal2.setPrompt("G.Total");
		textFieldGtotal2.setName((String) null);
		textFieldGtotal2.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldGtotal2.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldGtotal2.setBounds(930, 38, 120, 23);
		panel_7.add(textFieldGtotal2);

		JXLabel lblDiscount = new JXLabel();
		lblDiscount.setText("Discount");
		lblDiscount.setForeground(new Color(0, 51, 0));
		lblDiscount.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblDiscount.setBounds(122, 16, 71, 16);
		panel_7.add(lblDiscount);

		textFieldDiscount = new JXTextField();
		textFieldDiscount.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldDiscount.setPrompt("Discount");
		textFieldDiscount.setName((String) null);
		textFieldDiscount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldDiscount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldDiscount.setBounds(110, 38, 81, 23);
		panel_7.add(textFieldDiscount);

		textFieldTaxableValue = new JXTextField();
		textFieldTaxableValue.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldTaxableValue.setPrompt("Net Value");
		textFieldTaxableValue.setName((String) null);
		textFieldTaxableValue.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTaxableValue.setEnabled(false);
		textFieldTaxableValue.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTaxableValue.setBounds(194, 38, 90, 23);
		panel_7.add(textFieldTaxableValue);

		JXLabel lblTaxableValue = new JXLabel();
		lblTaxableValue.setText("Net value");
		lblTaxableValue.setForeground(new Color(0, 102, 51));
		lblTaxableValue.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTaxableValue.setBounds(193, 16, 90, 16);
		panel_7.add(lblTaxableValue);
		
		textFieldCol1 = new JXTextField();
		textFieldCol1.setBounds(514, 11, 71, 26);
		panel_7.add(textFieldCol1);
		
		textFieldCol1Val = new JXTextField();
		textFieldCol1Val.setBounds(514, 43, 71, 26);
		panel_7.add(textFieldCol1Val);
		
		textFieldCol2 = new JXTextField();
		textFieldCol2.setBounds(596, 11, 71, 25);
		panel_7.add(textFieldCol2);
		
		textFieldCol2Val = new JXTextField();
		textFieldCol2Val.setBounds(596, 42, 71, 26);
		panel_7.add(textFieldCol2Val);
		panel.add(panel_6);
		panel_6.setLayout(null);
		panel_6.add(btnAdd);
		panel_6.add(btnUpdate);
		panel_6.add(btnDelete);
		panel_6.add(comboBoxPrintCopiess);
		panel_6.add(btnPrint);
		panel_6.add(btnSave);
		panel_6.add(btnRefresh);
		panel_6.add(btnClose);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1050, 280);
		panel.add(scrollPane);

		tableInvoice = new JXTable();
		tableInvoice.setToolTipText("invoice items");
		tableInvoice.setColumnControlVisible(true);
		tableInvoice.setComponentPopupMenu(createPopup());
		tableInvoice.setName(LeamonERPConstants.TABLE_INVOICE);
		tableInvoice.setModel(new TableInvoiceModel(null));
		scrollPane.setViewportView(tableInvoice);

		setColumnWidth(tableInvoice);

		getContentPane().add(panel_3);
		panel_3.setLayout(null);
		panel_3.add(lblInvoiceNo_1);
		panel_3.add(textFieldInvoicenumList);
		panel_3.add(lblInvoice);
		getContentPane().add(panel_4);
		panel_4.setLayout(null);
		panel_4.add(textFieldInvoiceNum);
		panel_4.add(lblInvoiceNo);
		panel_4.add(lblDate);
		panel_4.add(lblPartyName);
		panel_4.add(datePickerInvoiceDate);
		panel_4.add(lblGst);
		panel_4.add(lblState);
		panel_4.add(textFieldPartyName);
		panel_4.add(scrollPane_1);
		panel_4.add(textFieldPartyMobile);
		panel_4.add(textFieldPartyState);
		panel_4.add(lblCode);
		panel_4.add(textFieldStateCode);
		panel_4.add(panel_2);
		panel_2.setLayout(null);
		panel_2.add(textFieldPartyTransportList);
		panel_2.add(label_4);
		panel_2.add(lblTransport);
		panel_2.add(lblNo);
		panel_2.add(textFieldGoodsPackets1);
		panel_4.add(lblBookedBy);
		panel_4.add(textFieldOrderBookedBy);
		panel_4.add(textFieldPartyGST);
		panel_4.add(lblMob);

		JXLabel label_15 = new JXLabel();
		label_15.setText("*");
		label_15.setForeground(new Color(255, 0, 0));
		label_15.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_15.setBounds(379, 6, 6, 16);
		panel_4.add(label_15);

		JXLabel label_16 = new JXLabel();
		label_16.setText("*");
		label_16.setForeground(Color.RED);
		label_16.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_16.setBounds(347, 110, 6, 16);
		panel_4.add(label_16);

		JXLabel label_17 = new JXLabel();
		label_17.setText("*");
		label_17.setForeground(Color.RED);
		label_17.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_17.setBounds(95, 4, 6, 16);
		panel_4.add(label_17);

		JXLabel label_18 = new JXLabel();
		label_18.setText("*");
		label_18.setForeground(Color.RED);
		label_18.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_18.setBounds(176, 1, 6, 16);
		panel_4.add(label_18);

		JXLabel lblAbbr = new JXLabel();
		lblAbbr.setText("Abbr");
		lblAbbr.setForeground(Color.BLACK);
		lblAbbr.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblAbbr.setBounds(623, 110, 39, 25);
		panel_4.add(lblAbbr);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(757, 44, 277, 65);
		panel_4.add(panel_1);
		panel_1.setBackground(new Color(255, 255, 255));

		textFieldPartynickName = new JXTextField();
		textFieldPartynickName.setBounds(10, 34, 180, 23);
		textFieldPartynickName.setPrompt("Marka");
		textFieldPartynickName.setName("txtInventoryAccountMarka");
		textFieldPartynickName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartynickName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JXLabel label = new JXLabel();
		label.setBounds(194, 35, 9, 20);
		label.setText("/");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));

		textFieldGoodsPackets2 = new JXTextField();
		textFieldGoodsPackets2.setBounds(207, 34, 60, 23);
		textFieldGoodsPackets2.setPrompt("Cartun");
		textFieldGoodsPackets2.setName(LeamonERPConstants.TEXTFIELD_INVOICE_PACKET2);
		textFieldGoodsPackets2.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldGoodsPackets2.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JXLabel lblMarka = new JXLabel();
		lblMarka.setBounds(10, 0, 68, 16);
		lblMarka.setText("Marka");
		lblMarka.setForeground(Color.GRAY);
		lblMarka.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

		JXLabel label_1 = new JXLabel();
		label_1.setBounds(209, 0, 68, 16);
		label_1.setText("No. of Pkt.");
		label_1.setForeground(Color.GRAY);
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		panel_1.setLayout(null);
		panel_1.add(textFieldPartynickName);
		panel_1.add(label);
		panel_1.add(textFieldGoodsPackets2);
		panel_1.add(lblMarka);
		panel_1.add(label_1);

		JXLabel lblBillNodate = new JXLabel();
		lblBillNodate.setText("Bill No.");
		lblBillNodate.setForeground(Color.BLACK);
		lblBillNodate.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblBillNodate.setBounds(11, 65, 59, 25);
		panel_4.add(lblBillNodate);

		textFieldBillNo = new JXTextField();
		textFieldBillNo.setPrompt("Bill No.");
		textFieldBillNo.setName("txtInventoryBillNumber");
		textFieldBillNo.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldBillNo.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldBillNo.setBounds(11, 91, 110, 23);
		panel_4.add(textFieldBillNo);
		
		JXLabel lblGrNo = new JXLabel();
		lblGrNo.setText("Bilty No.");
		lblGrNo.setForeground(Color.BLACK);
		lblGrNo.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblGrNo.setBounds(143, 65, 80, 25);
		panel_4.add(lblGrNo);
		
		textFieldGrNumber = new JXTextField();
		textFieldGrNumber.setPrompt("Bilty No.");
		textFieldGrNumber.setName("txtInventoryBillNumber");
		textFieldGrNumber.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldGrNumber.setEnabled(false);
		textFieldGrNumber.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldGrNumber.setBounds(140, 91, 110, 23);
		panel_4.add(textFieldGrNumber);

		JXPanel panel_5 = new JXPanel();
		panel_5.setBounds(0, 218, 1050, 49);
		getContentPane().add(panel_5);
		panel_5.setBackground(new Color(255, 255, 255));

		JLabel label_2 = new JLabel("Description:");
		label_2.setBounds(79, 6, 67, 14);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));

		textFieldProductDesc = new JXTextField();
		textFieldProductDesc.setBounds(77, 21, 282, 23);
		textFieldProductDesc.setPrompt("Description");
		textFieldProductDesc.setName(LeamonERPConstants.TEXTFIELD_INVOICE_DESC);
		textFieldProductDesc.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductDesc.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		autoStockItemDescSuggestor(textFieldProductDesc);

		textFieldProductSize = new JXTextField();
		textFieldProductSize.setBounds(389, 21, 104, 23);
		textFieldProductSize.setPrompt("Size");
		textFieldProductSize.setName(LeamonERPConstants.TEXTFIELD_INVOICE_SIZE);
		textFieldProductSize.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductSize.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductSize.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JLabel label_3 = new JLabel("Size:");
		label_3.setBounds(410, 6, 41, 14);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));

		JLabel label_5 = new JLabel("Qty:");
		label_5.setBounds(519, 6, 30, 14);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 11));

		JLabel label_6 = new JLabel("*");
		label_6.setBounds(546, 6, 14, 14);
		label_6.setForeground(Color.RED);

		textFieldProductQty = new JXTextField();
		textFieldProductQty.setBounds(511, 21, 77, 23);
		textFieldProductQty.setPrompt("Qty.");
		textFieldProductQty.setName(LeamonERPConstants.TEXTFIELD_INVOICE_QTY);
		textFieldProductQty.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductQty.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductQty.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		textFieldProductUnit = new JXTextField();
		textFieldProductUnit.setBounds(600, 21, 65, 23);
		textFieldProductUnit.setPrompt("Unit");
		textFieldProductUnit.setName(LeamonERPConstants.TEXTFIELD_INVOICE_UNIT);
		textFieldProductUnit.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductUnit.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JLabel label_7 = new JLabel("Unit:");
		label_7.setBounds(612, 6, 26, 14);
		label_7.setFont(new Font("Tahoma", Font.BOLD, 11));

		JLabel label_8 = new JLabel("*");
		label_8.setBounds(641, 6, 14, 14);
		label_8.setForeground(Color.RED);

		textFieldProductRate = new JXTextField();
		textFieldProductRate.setBounds(681, 21, 71, 23);
		textFieldProductRate.setPrompt("Rate");
		textFieldProductRate.setName(LeamonERPConstants.TEXTFIELD_INVOICE_RATE);
		textFieldProductRate.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductRate.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductRate.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JLabel label_9 = new JLabel("Rate:");
		label_9.setBounds(690, 6, 35, 14);
		label_9.setFont(new Font("Tahoma", Font.BOLD, 11));

		JLabel label_10 = new JLabel("*");
		label_10.setBounds(722, 6, 14, 14);
		label_10.setForeground(Color.RED);

		textFieldProductAmount = new JXTextField();
		textFieldProductAmount.setBounds(770, 21, 137, 23);
		textFieldProductAmount.setPrompt("Amount");
		textFieldProductAmount.setName(LeamonERPConstants.TEXTFIELD_INVOICE_AMOUNT);
		textFieldProductAmount.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductAmount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductAmount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JLabel label_11 = new JLabel("Amount:");
		label_11.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_11.setBounds(798, 6, 53, 14);

		textFieldProductTD = new JXTextField();
		textFieldProductTD.setBounds(925, 21, 42, 23);
		textFieldProductTD.setPrompt("TD%");
		textFieldProductTD.setName(LeamonERPConstants.TEXTFIELD_INVOICE_TD);
		textFieldProductTD.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldProductTD.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldProductTD.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JLabel label_12 = new JLabel("TD%");
		label_12.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_12.setBounds(931, 6, 24, 14);

		btnAddInvoiceEntry = new JXButton();
		btnAddInvoiceEntry.setBounds(980, 6, 43, 43);
		btnAddInvoiceEntry.setText("+");
		btnAddInvoiceEntry.setName(LeamonERPConstants.BUTTON_INVOICE_ACTION_ADD);
		btnAddInvoiceEntry.setMnemonic('+');

		JLabel label_13 = new JLabel("*");
		label_13.setBounds(150, 6, 14, 14);
		label_13.setForeground(Color.RED);
		panel_5.setLayout(null);
		panel_5.add(textFieldProductDesc);
		panel_5.add(label_2);
		panel_5.add(label_13);
		panel_5.add(textFieldProductSize);
		panel_5.add(label_3);
		panel_5.add(label_5);
		panel_5.add(label_6);
		panel_5.add(textFieldProductQty);
		panel_5.add(label_7);
		panel_5.add(label_8);
		panel_5.add(textFieldProductUnit);
		panel_5.add(label_9);
		panel_5.add(label_10);
		panel_5.add(textFieldProductRate);
		panel_5.add(label_11);
		panel_5.add(textFieldProductAmount);
		panel_5.add(label_12);
		panel_5.add(textFieldProductTD);
		panel_5.add(btnAddInvoiceEntry);

		hiddenLabelStockId = new JLabel("");
		hiddenLabelStockId.setFont(new Font("Tahoma", Font.BOLD, 11));
		hiddenLabelStockId.setBounds(195, 6, 67, 14);
		panel_5.add(hiddenLabelStockId);

		setEnableOnLoad(Boolean.FALSE);
		registerKeyEvent();
		btnUpdate.setEnabled(Boolean.FALSE);
		btnDelete.setEnabled(Boolean.FALSE);
		//setinvoiceNextNum();
		registerFocusEvent();

	}

	public void setEnableOnLoad(boolean isTurnOn){

		textFieldProductDesc.setEnabled(isTurnOn);
		textFieldProductUnit.setEnabled(isTurnOn);
		textFieldProductSize.setEnabled(isTurnOn);

		textFieldTotal.setEnabled(isTurnOn);
		textFieldBillAmount.setEnabled(isTurnOn);
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

		textFieldGstTAX.setEnabled(isTurnOn);
		textFieldGTotal1.setEnabled(isTurnOn);
		textFieldGtotal2.setEnabled(isTurnOn);

		textFieldDiscount.setEnabled(isTurnOn);
		textFieldTaxableValue.setEnabled(isTurnOn);
		btnAddInvoiceEntry.setEnabled(isTurnOn);
		btnSave.setEnabled(isTurnOn);
		tableInvoice.setEnabled(isTurnOn);
		
		textFieldBillNo.setEnabled(isTurnOn);
		textFieldGrNumber.setEnabled(isTurnOn);
		
		textFieldCol1.setEnabled(isTurnOn);
		textFieldCol2.setEnabled(isTurnOn);
		textFieldCol1Val.setEnabled(isTurnOn);
		textFieldCol2Val.setEnabled(isTurnOn);
		
	}

	public void getTextFromField(){

		String invoiceNum			=	textFieldInvoiceNum.getText();
		String invoiceDate =  datePickerInvoiceDate.getEditor().getText();

		/*PartyData*/
		String partyName			=	textFieldPartyName.getText();
		String textAreaPartyAddress	=	"";

		byte addreddData []  =  textAreaPartyAddress.getBytes();
		if(addreddData!=null){
			textAreaPartyAddress = new String(addreddData);
		}

		String partyState			=	textFieldPartyState.getText();
		String partyCode			=	textFieldStateCode.getText();
		String partyGST				=	textFieldPartyGST.getText();
		String partyMobile			=	textFieldPartyMobile.getText();
		/*----------*/

		/*---Order Booked data---*/
		String orderBookedBy		=	textFieldOrderBookedBy.getText();
		String partyTransportList	=	textFieldPartyTransportList.getText();
		String goodsPackets1		=	textFieldGoodsPackets1.getText();
		String goodsPackets2		=	textFieldGoodsPackets2.getText();
		String partynickName		=	textFieldPartynickName.getText();

		String productDescValue 	=   textFieldProductDesc.getText();
		String productSize			=	textFieldProductSize.getText();
		String productQty			=	textFieldProductQty.getText();
		String productUnit 			= 	textFieldProductUnit.getText();
		String productRate			=	textFieldProductRate.getText();
		String productAmount		=	textFieldProductAmount.getText();
		String productTD			=	textFieldProductTD.getText();

		String total				=	textFieldTotal.getText();
		String billAmount			=	textFieldBillAmount.getText();
		String packingAmount		=	textFieldPackingAmount.getText();

	}

	public void registerKeyEvent(){

		//textFieldPartyName.addKeyListener(new InvoiceUiEventHandler(textAreaPartyAddress));
		//textAreaPartyAddress.addKeyListener(new InvoiceUiEventHandler(textFieldPartyState));
		textFieldCol1.addKeyListener(new InvoiceUiEventHandler(textFieldCol1Val));
		textFieldCol1Val.addKeyListener(new InvoiceUiEventHandler(textFieldCol2));
		textFieldCol2.addKeyListener(new InvoiceUiEventHandler(textFieldCol2Val));
		textFieldCol2Val.addKeyListener(new InvoiceUiEventHandler(textFieldBillAmount));
		
		textFieldBillNo.addKeyListener(new InvoiceUiEventHandler(textFieldGrNumber));
		textFieldGrNumber.addKeyListener(new InvoiceUiEventHandler(textFieldPartyGST));
		//textFieldPartyGST.addKeyListener(new InvoiceUiEventHandler(textFieldPartyState));
		
		textFieldPartyState.addKeyListener(new InvoiceUiEventHandler(textFieldPartyGST));
		textFieldPartyGST.addKeyListener(new InvoiceUiEventHandler(textFieldPartyMobile));
		textFieldPartyMobile.addKeyListener(new InvoiceUiEventHandler(textFieldOrderBookedBy));
		textFieldOrderBookedBy.addKeyListener(new InvoiceUiEventHandler(textFieldPartynickName));
		textFieldPartynickName.addKeyListener(new InvoiceUiEventHandler(textFieldGoodsPackets2));
		textFieldGoodsPackets2.addKeyListener(new InvoiceUiEventHandler(textFieldPartyTransportList,this));//textFieldProductDesc
		textFieldPartyTransportList.addKeyListener(new InvoiceUiEventHandler(textFieldGoodsPackets1));//textFieldGoodsPackets1
		
		textFieldGoodsPackets1.addKeyListener(new InvoiceUiEventHandler(textFieldProductDesc,this));
		textFieldGstTAX.addKeyListener(new InvoiceUiEventHandler(textFieldCol1,this));

		/*invoice item fields */
		//textFieldProductDesc.addKeyListener(new InvoiceUiEventHandler(textFieldProductSize));
		textFieldProductSize.addKeyListener(new InvoiceUiEventHandler(textFieldProductQty));
		textFieldProductQty.addKeyListener(new InvoiceUiEventHandler(textFieldProductUnit,this));
		textFieldProductUnit.addKeyListener(new InvoiceUiEventHandler(textFieldProductRate,this));
		textFieldProductRate.addKeyListener(new InvoiceUiEventHandler(textFieldProductAmount,this));
		textFieldProductAmount.addKeyListener(new InvoiceUiEventHandler(textFieldProductTD,this));
		textFieldProductTD.addKeyListener(new InvoiceUiEventHandler(btnAddInvoiceEntry,this));

		btnAddInvoiceEntry.addKeyListener(new InvoiceUiEventHandler(textFieldProductDesc,this));
		//btnAddInvoiceEntry.addActionListener(new InvoiceUiEventHandler(this));
		btnAddInvoiceEntry.addMouseListener(new InvoiceUiEventHandler(this));
		tableInvoice.addKeyListener(new InvoiceUiEventHandler(this));
		tableInvoice.addMouseListener(new InvoiceUiEventHandler(this));

		textFieldBillAmount.addKeyListener(new InvoiceUiEventHandler(textFieldPackingAmount,this));

		/**/
		//register mouse event
		textFieldTotal.addMouseListener(new InvoiceUiEventHandler(this));
	}
	
	private void registerFocusEvent(){
		textFieldTotal.addFocusListener(new FocusListenerHandler());
		textFieldBillAmount.addFocusListener(new FocusListenerHandler());
		textFieldPackingAmount.addFocusListener(new FocusListenerHandler());
		textFieldInvoiceNum.addFocusListener(new FocusListenerHandler());

		textFieldPartyState.addFocusListener(new FocusListenerHandler());
		textFieldStateCode.addFocusListener(new FocusListenerHandler());
		textFieldPartyGST.addFocusListener(new FocusListenerHandler());
		textFieldPartyMobile.addFocusListener(new FocusListenerHandler());
		textFieldPartyTransportList.addFocusListener(new FocusListenerHandler());
		textFieldGoodsPackets1.addFocusListener(new FocusListenerHandler());
		textFieldOrderBookedBy.addFocusListener(new FocusListenerHandler());
		textFieldPartynickName.addFocusListener(new FocusListenerHandler());
		textFieldGoodsPackets2.addFocusListener(new FocusListenerHandler());
		textAreaPartyAddress.addFocusListener(new FocusListenerHandler());

		textFieldProductSize.addFocusListener(new FocusListenerHandler());
		textFieldProductQty.addFocusListener(new FocusListenerHandler());
		textFieldProductUnit.addFocusListener(new FocusListenerHandler());
		textFieldProductRate.addFocusListener(new FocusListenerHandler());
		textFieldProductAmount.addFocusListener(new FocusListenerHandler());
		textFieldProductTD.addFocusListener(new FocusListenerHandler());
		textFieldGTotal1.addFocusListener(new FocusListenerHandler());
		textFieldGstTAX.addFocusListener(new FocusListenerHandler());
		textFieldDiscount.addFocusListener(new FocusListenerHandler());
		textFieldTaxableValue.addFocusListener(new FocusListenerHandler());
		textFieldGtotal2.addFocusListener(new FocusListenerHandler());
		textFieldStateAbbr.addFocusListener(new FocusListenerHandler());
	}

	/*-------Create table popup---------*/
	public JPopupMenu createPopup(){
		final String methodName="createPopup";
		//LOGGER.info(CLASS_NAME+"["+methodName+"] inside");
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItemDelete = new JMenuItem("Delete", new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_DELETE)));
		//JMenuItem menuItemView = new JMenuItem("View",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_VIEW)));
		JMenuItem menuItemRefresh = new JMenuItem("Refresh",new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_POPUP_MENU_REFRESH)));

		menuItemDelete.setActionCommand(LeamonERPConstants.MENU_ACTION_DELETE);
		menuItemDelete.addActionListener(e -> menuItemDeleteClick(e));
		//menuItemView.setActionCommand(LeamonERPConstants.MENU_ACTION_VIEW);
		//menuItemView.addActionListener(this);
		menuItemRefresh.setActionCommand(LeamonERPConstants.MENU_ACTION_REFRESH);
		menuItemRefresh.addActionListener(e -> menuItemRefreshClick(e));

		popupMenu.add(menuItemRefresh);
		//popupMenu.add(menuItemView);
		popupMenu.add(menuItemDelete);
		//LOGGER.info(CLASS_NAME+"["+methodName+"] end.");
		return popupMenu;
	}

	public void setColumnWidth(JXTable table){
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);

		TableColumnModel columnModel =  table.getColumnModel();
		//columnModel.getColumn(0).setPreferredWidth(70);
		columnModel.getColumn(1).setPreferredWidth(310);
		columnModel.getColumn(2).setPreferredWidth(115);
		columnModel.getColumn(3).setPreferredWidth(100);
		
		//		columnModel.getColumn(4).setPreferredWidth(80);
		columnModel.getColumn(5).setPreferredWidth(100);
		columnModel.getColumn(6).setPreferredWidth(150);
		//		columnModel.getColumn(7).setPreferredWidth(80);
	}

	private void addnextInvoice(){
		btnRefresh.doClick();
		setEnableOnLoad(Boolean.TRUE);
		setinvoiceNextNum();
		setTodayDateForPicker();
		textFieldPartyName.requestFocus();
		textFieldInvoiceNum.setEditable(Boolean.TRUE);
		
	}
	
	//---Events ----------/////////
	private void btnAddClick(ActionEvent e){
		addnextInvoice();
	}

	private void btnCloseClick(ActionEvent e){
		this.dispose();
	}
	private void btnUpdateClick(ActionEvent e){
		update();
	}
	private void btnDeleteClick(ActionEvent e){
		delete();
	}
	private void btnSaveClickHandler(ActionEvent e){
		save();
	}

	public void save(){

		/*delete invoice info*/
		if(!Strings.isNullOrEmpty(action)){
			if(action.equals(ACTION_DELETE)){
				action = ACTION_DO_NOTHING;
				int option = JOptionPane.showConfirmDialog(this, "Do you really want to delete ?");
				if(option == JOptionPane.YES_OPTION){
					deleteInvoicInfo();
					autoInvoiceIdSuggestor(textFieldInvoicenumList);
					btnRefresh.doClick(); 
					textFieldTaxableValue.setText(LeamonERPConstants.EMPTY_STR);
					textFieldGTotal1.setText(LeamonERPConstants.EMPTY_STR);
					textFieldGTotal1.setText(LeamonERPConstants.EMPTY_STR);
					btnDelete.setEnabled(Boolean.FALSE);
					btnUpdate.setEnabled(Boolean.FALSE);
				}else{
					btnDelete.setEnabled(Boolean.TRUE);
					btnUpdate.setEnabled(Boolean.TRUE);
					btnAdd.setEnabled(Boolean.TRUE);
				}
				return ;
			}else if(action.equals(ACTION_UPDATE)){
				action = ACTION_DO_NOTHING;
				int option = JOptionPane.showConfirmDialog(this, "Do you want to save the changes ?");
				if(option == JOptionPane.YES_OPTION){
					UpdateInvoicInfo();
					autoInvoiceIdSuggestor(textFieldInvoicenumList);
					btnRefresh.doClick(); 
					textFieldTaxableValue.setText(LeamonERPConstants.EMPTY_STR);
					textFieldGTotal1.setText(LeamonERPConstants.EMPTY_STR);
					textFieldGTotal1.setText(LeamonERPConstants.EMPTY_STR);
					btnDelete.setEnabled(Boolean.FALSE);
					btnUpdate.setEnabled(Boolean.FALSE);
				}else{
					setEnableOnLoad(Boolean.FALSE);
					btnDelete.setEnabled(Boolean.TRUE);
					btnUpdate.setEnabled(Boolean.TRUE);
					btnAdd.setEnabled(Boolean.TRUE);
				}
				return ;
			}else if(action.equals(ACTION_DO_NOTHING)){
				JOptionPane.showMessageDialog(this, "Please select an action.");
			}
		}

		if(!isValidatedToSave()){
			LOGGER.error("validation failed while saving");
			return ;
		}
		
		String invoiceNum			=	textFieldInvoiceNum.getText();
		String invoiceDate 			=  datePickerInvoiceDate.getEditor().getText();
		String billNo 				= textFieldBillNo.getText();

		/*PartyData*/
		String partyName			=	textFieldPartyName.getText();
		String partyAddress 		=   textAreaPartyAddress.getText();
		String partyState			=	textFieldPartyState.getText();
		String partyCode			=	textFieldStateCode.getText();
		String partyGST				=	textFieldPartyGST.getText();
		String partyMobile			=	textFieldPartyMobile.getText();
		/*----------*/

		/*---Order Booked data---*/
		String orderBookedBy		=	textFieldOrderBookedBy.getText();
		String partyTransportList	=	textFieldPartyTransportList.getText();
		String goodsPackets1		=	textFieldGoodsPackets1.getText();
		String goodsPackets2		=	textFieldGoodsPackets2.getText();
		String partynickName		=	textFieldPartynickName.getText();

		String total				=	textFieldTotal.getText();
		String billAmount			=	textFieldBillAmount.getText();
		String packingAmount		=	textFieldPackingAmount.getText();
		String gstAmount 			=	textFieldGstTAX.getText();
		
		/*------Release 3.2---------*/
		String grBiltyNumber = Strings.isNullOrEmpty(textFieldGrNumber.getText()) ?"" :textFieldGrNumber.getText();
		String col1Name= Strings.isNullOrEmpty(textFieldCol1.getText())?"" :textFieldCol1.getText();
		String col2Name = Strings.isNullOrEmpty(textFieldCol2.getText())?"" :textFieldCol2.getText();
		String col1Val= Strings.isNullOrEmpty(textFieldCol1Val.getText())?"" : textFieldCol1Val.getText();
		String col2Val = Strings.isNullOrEmpty(textFieldCol2Val.getText())?"" : textFieldCol2Val.getText();; 
		
		AccountInfo accountInfoVal = null;
		boolean isFound = false;
		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}

		for(AccountInfo accountInfo : accountInfos){
			if(null != accountInfo.getCity() && null != accountInfo.getName() 
					&& partyName.equalsIgnoreCase(accountInfo.getName()) && partyAddress.contains(accountInfo.getCity())){
				accountInfoVal = accountInfo;
				isFound = true;
				break;
			}
		} //nd for

		boolean isSavedAccount = false;

		if(!isFound){
			accountInfoVal = AccountInfo.builder()
					.description(partyAddress)
					.name(partyName)
					.city(partyAddress)
					.description(partyAddress)
					.nickName(partynickName).transport(partyTransportList).state(partyState)
					.gstNumber(partyGST)
					.engagedDate(new Timestamp(System.currentTimeMillis()))
					.createdDate(new Timestamp(System.currentTimeMillis()))
					.lastUpdated(new Timestamp(System.currentTimeMillis())).isEnable(Boolean.TRUE).build();

			try{
				Integer pin = Integer.parseInt(partyCode);
				accountInfoVal.setPinCode(pin);
			}catch(Exception e){
				LOGGER.error(e);
				LOGGER.error("invalid pin found that's why setting 0");
				accountInfoVal.setPinCode(0);
			}

			try{

				BigInteger partyMob = new BigInteger(partyMobile);
				accountInfoVal.setPhone(partyMob);
			}catch(Exception e){
				LOGGER.error(e);
				LOGGER.error("invalid mobile found that's why setting 0");
				accountInfoVal.setPhone(new BigInteger("0"));
			}

			try {
				AccountDaoImpl.getInstance().save(accountInfoVal);
				isSavedAccount = true;
			} catch (Exception e) {
				JOptionPane.showInternalMessageDialog(this, e.toString());
				e.printStackTrace();
				return ;
			}
		}

		InvoiceInfo invoiceInfo = null;
		List<InvoiceItemInfo> invoiceItemInfos =  ((TableInvoiceModel)tableInvoice.getModel()).getInvoiceItemInfos();
		List<StockItem> stockItems = new ArrayList<StockItem>();
		try {
			stockItems = StockDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}


		for(int i=0; i< invoiceItemInfos.size(); i++){
			InvoiceItemInfo invoiceItemInfo = invoiceItemInfos.get(i);
			if(null != invoiceItemInfo.getStockItemId() && invoiceItemInfo.getStockItemId() > 0){
				continue;
			}
			boolean isStockItemFound =  false;
			for(int j=0;j < stockItems.size(); j++){
				StockItem stockItem = stockItems.get(j);
				if(null != stockItem.getName() && stockItem.getName().equals(invoiceItemInfo.getDescription())
						&& (!Strings.isNullOrEmpty(stockItem.getSize()) && stockItem.getSize().equals(invoiceItemInfo.getSize()) )
						&& (!Strings.isNullOrEmpty(stockItem.getSaleunit()) && stockItem.getSaleunit().equals(invoiceItemInfo.getUnit()) )
						){
					invoiceItemInfo.setStockItemId(stockItem.getId());
					isStockItemFound = true;
					break;
				}
			}

			if(!isStockItemFound){
				StockItem stockItem = StockItem.builder().name(invoiceItemInfo.getDescription())
						.size(invoiceItemInfo.getSize())
						.saleunit(invoiceItemInfo.getUnit())
						.createdDate(new Timestamp(System.currentTimeMillis()))
						.lastUpdatedDate(new Timestamp(System.currentTimeMillis()))
						.isEnable(Boolean.TRUE)
						.build();
				try{
					StockDaoImpl.getInstance().save(stockItem);
				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showInternalMessageDialog(this, e.toString());
					return ;
				}
				invoiceItemInfo.setStockItemId(stockItem.getId());
			}

		}//end for
		
		/*getting operation for col1val & col2 val*/
		String col1Operator = getCol1Operator(col1Val);
		String col2Operator = getCol2Operator(col2Val);
			
		invoiceInfo = InvoiceInfo.builder().items(invoiceItemInfos)
				.invoicNum(invoiceNum)
				.invoicDate(invoiceDate)
				.orderBookedBy(orderBookedBy)
				.transport(partyTransportList)
				.pktNumber(goodsPackets2)
				.billAmount(billAmount)
				.gstValue(gstAmount)
				.partyinfoID(accountInfoVal.getId())
				
				.col1Name(col1Name)
				.col1Val(col1Val)
				.col1Operator(col1Operator)
				
				.col2Name(col2Name)
				.col2Val(col2Val)
				.col2Operator(col2Operator)
				.billNo(billNo)	
				.withoutBillAmount(packingAmount)	
				.grBiltyNumber(grBiltyNumber)
				
				.remainingBillAmount(billAmount)
				.paidBillAmount(String.valueOf(0))
				.paidStatus(InvoicePaymentStatusEnum.NOTHING_PAID.name())
				
				.remainingWithoutBillAmount(packingAmount)
				.paidWithoutBillAmount(String.valueOf(0))
				.wpaidstatus(InvoicePaymentStatusEnum.NOTHING_PAID.name())
				
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastUpdated(new Timestamp(System.currentTimeMillis()))
				.isEnable(Boolean.TRUE)
				.build();
		try{
			InvoiceDaoImpl.getInstance().save(invoiceInfo);
		}catch(Exception e){
			JOptionPane.showInternalMessageDialog(this, e.toString());
			return;
		}

		Integer invoiceId= invoiceInfo.getId();
		for(int i=0; i<invoiceItemInfos.size(); i++){
			InvoiceItemInfo itemInfo = invoiceItemInfos.get(i);
			itemInfo.setInvoiceID(invoiceId);
			try {
				InvoiceItemDaoImpl.getInstance().save(itemInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		LinkedHashSet<Integer> uniqueStockIds = new LinkedHashSet<Integer>(); 
		/*Update stock storage*/
		for(int i=0; i < invoiceItemInfos.size(); i++){
			uniqueStockIds.add(invoiceItemInfos.get(i).getStockItemId());
		}
		
		//3.3.2 Ghanshyam code data type changed from Integer to double
				for (Integer si : uniqueStockIds){
					double totalOrderedQuantity = invoiceItemInfos.stream().filter(e -> (null !=e.getStockItemId())
							&& e.getStockItemId() == si).map(InvoiceItemInfo::getQty)
							.mapToDouble(Double::parseDouble).sum();
				//3.3.2 end of ghanshyam code
			
			/*fetch quantity from DB to update*/
			List<StockItemQuantity> stockItemQuantities =new ArrayList<StockItemQuantity>();
			try {
				stockItemQuantities = StockQuantityDaoImpl.getInstance().getItemList();
			} catch (Exception exp) {
				LOGGER.error(exp);
			}
			StockItemQuantity matchedItemQuantity = stockItemQuantities.stream()
					.filter(s -> s.getStokItemid().equals(si))
					.findFirst().orElse(null);
			if(matchedItemQuantity != null){
				double fetchedQuantity = 0;// 3.3.2 ghanshyam code data type changed from int to double
				try{
					fetchedQuantity = Integer.valueOf(matchedItemQuantity.getQuantity());
					if(fetchedQuantity >= totalOrderedQuantity){
						fetchedQuantity = fetchedQuantity - totalOrderedQuantity;
						matchedItemQuantity.setQuantity(String.valueOf(fetchedQuantity));
						//update matchedItemQuantity in DB
						matchedItemQuantity.setLastUpdatedDate(new Timestamp(System.currentTimeMillis()));
						try{
							StockQuantityDaoImpl.getInstance().update(matchedItemQuantity);
							/*saving order history for ordered quantity*/
							List<InvoiceItemInfo> invoiceItemInfos2 = invoiceItemInfos.stream().filter(e -> e.getStockItemId() == si).collect(Collectors.toList());
							for(InvoiceItemInfo invoiceItemInfoToOrderHistory : invoiceItemInfos2){
								StockItemQuantityOrderHistory stockItemQuantityOrderHistory = StockItemQuantityOrderHistory.builder()
										.invoiceid(invoiceId)
										.invoiceItemId(invoiceItemInfoToOrderHistory.getId())
										.stokItemid(si)
										.quantity(invoiceItemInfoToOrderHistory.getQty())
										.description("")
										.createdDate(new Timestamp(System.currentTimeMillis()))
										.lastUpdatedDate(new Timestamp(System.currentTimeMillis()))
										.build();
								try{
									StockQuantityOrderHistoyDaoImpl.getInstance().save(stockItemQuantityOrderHistory);
								}catch(Exception exp){
									LOGGER.error(exp);
								}
							}
						}catch(Exception exp){
							LOGGER.error(exp);
						}
					}else{
						JOptionPane.showMessageDialog(this, "Stock quantity is greater than fetched stock storage.","Leamon-Erp:Stock Error",JOptionPane.ERROR_MESSAGE);
						return ;
					}
				}catch(Exception exp){
					LOGGER.error(exp);
				}
			}
		}//end unique id loop
		
		//JOptionPane.showMessageDialog(this, "Saved successfully.","Message",JOptionPane.PLAIN_MESSAGE);
		int option = JOptionPane.showConfirmDialog(this, "Saved successfully.\n Do you want to print ?");
		if(option == JOptionPane.OK_OPTION){
			btnPrint.setEnabled(Boolean.TRUE);
			btnPrint.doClick();
		}

		btnRefresh.doClick();
	}

	public void setNumberFormatter(){
		NumberFormatter nf = new NumberFormatter();
		nf.setAllowsInvalid(false);
	}


	public void autoInvoiceIdSuggestor(JTextField textField){
		final String methodName="autoInvoiceIdSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");
		List<InvoiceInfo> invoiceInfos = InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemList();
		if(leamonAutoInvoiceIDTextFieldSuggestor==null){ 
			leamonAutoInvoiceIDTextFieldSuggestor = new LeamonAutoInvoiceIDTextFieldSuggestor<List<InvoiceInfo>, InvoiceInfo>(textField, this);
		}
		leamonAutoInvoiceIDTextFieldSuggestor.setItems(invoiceInfos);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}

	public void autoCitySuggestor(JTextArea textArea){
		final String methodName="autoInvoiceIdSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");
		List<StateCityInfo> stateCityInfos =  LeamonERP.stateCityInfos;
		LeamonAutoCityFieldSuggestor<List<StateCityInfo>, StateCityInfo> leamonAutoCityFieldSuggestor =
				new LeamonAutoCityFieldSuggestor<List<StateCityInfo>, StateCityInfo>(textArea, this);
		leamonAutoCityFieldSuggestor.setItems(stateCityInfos);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}

	public void setInvoiceInfo(InvoiceInfo info){
		if(info== null){
			return ;
		}
		//this.btnAdd.doClick();
		//setDisableOnLoad(Boolean.TRUE);
		String billAmount =  info.getBillAmount();
		String gstvalue = info.getGstValue();
		String invoiceDate = info.getInvoicDate();
		String invoiceNum = info.getInvoicNum();
		List<InvoiceItemInfo> invoiceItemInfos = info.getItems();
		String orderBookedBy = info.getOrderBookedBy();
		Integer partyInfoId = info.getPartyinfoID();
		String packtNumber = info.getPktNumber();
		String transportName = info.getTransport();
		String billNo = info.getBillNo();
		
		String grNumber = info.getGrBiltyNumber();

		List<AccountInfo>  accountInfos  =  new ArrayList<AccountInfo>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e1) {
			LOGGER.error(e1);
		}
		AccountInfo accountinfo = accountInfos.stream().filter(e->e.getId().equals(partyInfoId)).findFirst().get();

		setAccountInfo(accountinfo);

		textFieldBillAmount.setText(billAmount);
		textFieldGstTAX.setText(gstvalue);
		textFieldInvoiceNum.setText(invoiceNum);
		
		datePickerInvoiceDate.getEditor().setText(invoiceDate);
		textFieldOrderBookedBy.setText(orderBookedBy);
		textFieldGoodsPackets1.setText(packtNumber);
		textFieldGoodsPackets2.setText(packtNumber);
		textFieldPartyTransportList.setText(transportName);
		textFieldBillNo.setText(billNo);
		textFieldGrNumber.setText(grNumber);
		textFieldCol1.setText(Strings.isNullOrEmpty(info.getCol1Name())? "" : info.getCol1Name());
		textFieldCol1Val.setText(Strings.isNullOrEmpty(info.getCol1Val())? "" : info.getCol1Val());
		textFieldCol2.setText(Strings.isNullOrEmpty(info.getCol1Name())? "" : info.getCol2Name());
		textFieldCol2Val.setText(Strings.isNullOrEmpty(info.getCol1Val())? "" : info.getCol2Val());

		TableInvoiceModel model = (TableInvoiceModel) tableInvoice.getModel();
		model.setInvoiceItemInfos(invoiceItemInfos);
		tableInvoice.setModel(model);
		InvoiceUiEventHandler invoiceiHandler = new InvoiceUiEventHandler(this);
		MouseEvent me = new MouseEvent(textFieldTotal, MouseEvent.MOUSE_CLICKED, 1, 0, 0, 0, 2, false);
		invoiceiHandler.mouseClicked(me);
		invoiceiHandler.calcPackingAmt(this);
		//invoiceiHandler.setTtoal(tableInvoice);
		setEnableOnLoad(Boolean.FALSE);
		btnSave.setEnabled(false);
		btnPrint.setEnabled(true);
		//setDisableOnLoad(true);
		actionObjectInvoiceInfo = info;
		btnUpdate.setEnabled(Boolean.TRUE);
		btnDelete.setEnabled(Boolean.TRUE);
		textFieldInvoiceNum.setEnabled(Boolean.FALSE);
	}
	
	public void setStateCityInfo(StateCityInfo stateCityInfo){
		//if(!Strings.isNullOrEmpty(info.getCity())){
			//String item = info.getCity();
			//StateCityInfo stateCityInfo = LeamonERP.stateCityInfos.stream().filter(ele -> ele.getCity().equals(item)).findAny().orElse(null);
			if(stateCityInfo!=null){
				textFieldPartyState.setText(stateCityInfo.getState());
				textFieldStateCode.setText(stateCityInfo.getStateCode());
				textFieldStateAbbr.setText(stateCityInfo.getAbbreviations());
			}
			textFieldBillNo.requestFocus();
			//textFieldPartyGST.requestFocus();
	}

	private void refresh(){
		textFieldInvoiceNum.setText(LeamonERPConstants.EMPTY_STR);
		datePickerInvoiceDate.getEditor().setText(LeamonERPConstants.EMPTY_STR);

		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		textAreaPartyAddress.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPartyState.setText(LeamonERPConstants.EMPTY_STR);
		textFieldStateCode.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPartyGST.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPartyMobile.setText(LeamonERPConstants.EMPTY_STR);

		textFieldOrderBookedBy.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPartyTransportList.setText(LeamonERPConstants.EMPTY_STR);
		textFieldGoodsPackets1.setText(LeamonERPConstants.EMPTY_STR);
		textFieldGoodsPackets2.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPartynickName.setText(LeamonERPConstants.EMPTY_STR);

		textFieldTotal.setText(LeamonERPConstants.EMPTY_STR);
		textFieldBillAmount.setText(LeamonERPConstants.EMPTY_STR);
		textFieldPackingAmount.setText(LeamonERPConstants.EMPTY_STR);
		textFieldGstTAX.setText(LeamonERPConstants.EMPTY_STR);
		textFieldTaxableValue.setText(LeamonERPConstants.EMPTY_STR);
		textFieldInvoicenumList.setText(LeamonERPConstants.EMPTY_STR);
		tableInvoice.setModel(new TableInvoiceModel(null));
		setColumnWidth(tableInvoice);

		textFieldGTotal1.setText(LeamonERPConstants.EMPTY_STR);
		textFieldGtotal2.setText(LeamonERPConstants.EMPTY_STR);
		
		textFieldProductDesc.setText(LeamonERPConstants.EMPTY_STR);
		textFieldProductSize.setText(LeamonERPConstants.EMPTY_STR);
		textFieldProductQty.setText(LeamonERPConstants.EMPTY_STR);
		textFieldProductUnit.setText(LeamonERPConstants.EMPTY_STR);
		textFieldProductRate.setText(LeamonERPConstants.EMPTY_STR);
		textFieldProductAmount.setText(LeamonERPConstants.EMPTY_STR);
		textFieldProductTD.setText(LeamonERPConstants.EMPTY_STR);
		textFieldDiscount.setText(LeamonERPConstants.EMPTY_STR);
		hiddenLabelStockId.setText(LeamonERPConstants.EMPTY_STR);
		textFieldBillNo.setText(LeamonERPConstants.EMPTY_STR);
		textFieldGrNumber.setText(LeamonERPConstants.EMPTY_STR);
		textFieldCol1.setText(LeamonERPConstants.EMPTY_STR);
		textFieldCol1Val.setText(LeamonERPConstants.EMPTY_STR);
		textFieldCol2.setText(LeamonERPConstants.EMPTY_STR);
		textFieldCol2Val.setText(LeamonERPConstants.EMPTY_STR);
		
		btnPrint.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);

		autoInvoiceIdSuggestor(textFieldInvoicenumList);
		/*refresh stockitem description*/
		List<StockItem> stockItemList = new ArrayList<>();
		try {
			stockItemList = StockDaoImpl.getInstance().getItemList();
		} catch (Exception exp) {
			LOGGER.error(exp);
		}
		leamonAutoStockItemTextFieldSuggestor.setItems(stockItemList);
		requestFocusOnLoad();
		
		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoAccountIDTextFieldSuggestor.setItems(accountInfos);
	}
	private void btnRefreshClickHandler(ActionEvent e){
		action = null;
		refresh();
	}

	public void requestFocusOnLoad(){
		textFieldInvoiceNum.requestFocus();
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

	public void setinvoiceNextNum(){
		List<InvoiceInfo> invoiceInfos = InvoiceDaoImpl.getInstance().getItemListIncludeDisabled();
		try{
			Integer nextId =  Integer.parseInt(invoiceInfos.get(invoiceInfos.size()-1).getInvoicNum())+1;
			textFieldInvoiceNum.setText(String.valueOf(nextId));
		}catch(Exception e){
			textFieldInvoiceNum.setText(String.valueOf(1));
			LOGGER.error(e);
		}
	}

	public void setTodayDateForPicker(){
		Date dt = new Date();
		SimpleDateFormat format = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerInvoiceDate.getEditor().setText(format.format(dt));
	}

	public void setAccountInfo(AccountInfo info){
		textFieldPartyName.setText(info.getName());
		textAreaPartyAddress.setText(info.getCity());
		textAreaPartyAddress.repaint();
		
		if(!Strings.isNullOrEmpty(info.getCity())){
			String item = info.getCity();
			StateCityInfo stateCityInfo = LeamonERP.stateCityInfos.stream().filter(ele -> ele.getCity().equals(item)).findAny().orElse(null);
			if(stateCityInfo!=null){
				textFieldPartyState.setText(stateCityInfo.getState());
				textFieldStateCode.setText(stateCityInfo.getStateCode());
				textFieldStateAbbr.setText(stateCityInfo.getAbbreviations());
			}
		}else{
			textFieldPartyState.setText(info.getState());
			textFieldStateCode.setText(String.valueOf(info.getPinCode()));
		}

		textFieldPartyGST.setText(info.getGstNumber());
		textFieldPartyMobile.setText(String.valueOf(info.getPhone()));

		textFieldPartyTransportList.setText(info.getTransport());
		textFieldPartynickName.setText(info.getNickName());
		textFieldPartyGST.setText(info.getGstNumber());
		
	}

	private boolean isValidatedToSave(){
		
		String invoiceNum			=	textFieldInvoiceNum.getText();
		String invoiceDate =  datePickerInvoiceDate.getEditor().getText();

		/*PartyData*/
		String partyName			=	textFieldPartyName.getText();
		String partyAddress 		=   textAreaPartyAddress.getText();
		String partyState			=	textFieldPartyState.getText();
		
		if(Strings.isNullOrEmpty(invoiceNum)){
			JOptionPane.showMessageDialog(this, "Invoice Number can not be left blank.", "Validation fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		try{
			Integer validInvoiceNum = Integer.parseInt(invoiceNum);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "Only poitive integer invoice number accepted", "Validation fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(invoiceDate)){
			JOptionPane.showMessageDialog(this, "Invoice Date can not be left blank.", "Validation fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(partyName)){
			JOptionPane.showMessageDialog(this, "Party name can not be left blank.", "Validation fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(partyAddress)){
			JOptionPane.showMessageDialog(this, "Party address can not be left blank.", "Validation fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(Strings.isNullOrEmpty(partyState)){
			JOptionPane.showMessageDialog(this, "Party state can not be left blank.", "Validation fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		List<InvoiceItemInfo> invoiceItemInfos =  ((TableInvoiceModel)tableInvoice.getModel()).getInvoiceItemInfos();
		if(null != invoiceItemInfos && invoiceItemInfos.isEmpty()){
			JOptionPane.showMessageDialog(this, "No invoice entry.", "Validation fails",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void autoStockItemDescSuggestor(JTextField textField){
		final String methodName="autoItemDescSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");

		List<StockItem> stockItemList = new ArrayList<>();
		try {
			stockItemList = StockDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoStockItemTextFieldSuggestor 
		= new LeamonAutoStockItemTextFieldSuggestor<List<StockItem>, StockItem>(textField, this);
		leamonAutoStockItemTextFieldSuggestor.setItems(stockItemList);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}

	public void setStockItemInfo(StockItem info){
		textFieldProductDesc.setText(info.getName()
				//.concat(Strings.isNullOrEmpty(info.getProductCode())? "" : " ".concat(info.getProductCode()))
				);
		textFieldProductSize.setText(info.getSize());
		hiddenLabelStockId.setText(String.valueOf(info.getId()));
		textFieldProductUnit.setText(info.getSaleunit());

		InvoiceUiEventHandler invoiceiHandler = new InvoiceUiEventHandler(textFieldProductSize,this);
		KeyEvent ke = new KeyEvent(textFieldProductDesc, KeyEvent.KEY_PRESSED, 1, 0, KeyEvent.VK_ENTER, (char)KeyEvent.VK_ENTER);
		//MouseEvent me = new MouseEvent(textFieldTotal, MouseEvent.MOUSE_CLICKED, 1, 0, 0, 0, 2, false);
		invoiceiHandler.keyPressed(ke);
	}

	private void print(){
		InvoicePrintFactory invoicePrintFactory = new InvoicePrintFactory(this);
		invoicePrintFactory.print();
		//invoicePrintFactory.printRpt();
	}
	
	private void btnPrintClick(ActionEvent e){
		print();
	}

	private void menuItemDeleteClick(ActionEvent e){
		TableInvoiceModel model = (TableInvoiceModel) tableInvoice.getModel();
		int selectedRow = tableInvoice.getSelectedRow();
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			JOptionPane.showMessageDialog(this, "Please select atleast one item", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
	 	if(tableInvoice.getRowSorter() != null){
			selectedRow = tableInvoice.getRowSorter().convertRowIndexToModel(selectedRow);
			LOGGER.debug("Selcted row : "+ selectedRow);
		}
	 	
	 	int selectedRows[] = tableInvoice.getSelectedRows();
	 	IntStream.of(selectedRows).forEach(val -> {
	 		LOGGER.info("StockItemList[deleteStockItem] selected rows["+val+"]");
	 	});
	 	
	 	List<InvoiceItemInfo> invoiceItemInfos =  model.getInvoiceItemInfos();
	 	
	 	List<InvoiceItemInfo>  elementToBeRemoved = new ArrayList<InvoiceItemInfo>(); 
	 	for(int row : selectedRows){
	 		elementToBeRemoved.add(invoiceItemInfos.get(row));
	 	}//end for
	 	
	 	for(int i=0; i < elementToBeRemoved.size() ; i++){
	 		invoiceItemInfos.remove(elementToBeRemoved.get(i));
	 	}
	 	
	 	List<InvoiceItemInfo> newInvoiceItemInfos =  new ArrayList<InvoiceItemInfo>();
	 	for(int i=0; i<invoiceItemInfos.size(); i++){
	 		InvoiceItemInfo newItem = invoiceItemInfos.get(i);
	 		newItem.setSno(String.valueOf(i+1));
	 		newInvoiceItemInfos.add(newItem);
	 	}
	 	
	 	model.setInvoiceItemInfos(newInvoiceItemInfos);
	 	tableInvoice.setModel(model);
	 	((AbstractTableModel)tableInvoice.getModel()).fireTableDataChanged();
	 	tableInvoice.repaint();
	}//end
	
	private void menuItemRefreshClick(ActionEvent e){
		//TableInvoiceModel model = (TableInvoiceModel) tableInvoice.getModel();
		tableInvoice.clearSelection();
	}
	
	private void update(){
		action = ACTION_UPDATE;
		setEnableOnLoad(Boolean.TRUE);
		textFieldInvoiceNum.setEnabled(Boolean.FALSE);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnAdd.setEnabled(false);
		btnPrint.setEnabled(false);
	}
	private void delete(){
		setEnableOnLoad(Boolean.FALSE);
		action = ACTION_DELETE;
		btnDelete.setEnabled(false);
		btnDelete.setEnabled(false);
		btnAdd.setEnabled(false);
		btnSave.setEnabled(true);
	}
	public void deleteInvoicInfo(){
		if(actionObjectInvoiceInfo == null){
			return ;
		}
		try {
			InvoiceDaoImpl.getInstance().disable(actionObjectInvoiceInfo);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		List<InvoiceItemInfo> infos = actionObjectInvoiceInfo.getItems();
		for(InvoiceItemInfo info : infos ){
			try {
				InvoiceItemDaoImpl.getInstance().disable(info);
			} catch (Exception e) {
				LOGGER.error(e);
			}
		}
	}
	public void UpdateInvoicInfo(){
		
		if(actionObjectInvoiceInfo == null){
			return ;
		}
		
		String invoiceDate =  datePickerInvoiceDate.getEditor().getText();
		String billNo 				= textFieldBillNo.getText();

		/*PartyData*/
		String partyName			=	textFieldPartyName.getText();
		String partyAddress 		=   textAreaPartyAddress.getText();
		String partyState			=	textFieldPartyState.getText();
		String partyCode			=	textFieldStateCode.getText();
		String partyGST				=	textFieldPartyGST.getText();
		String partyMobile			=	textFieldPartyMobile.getText();
		/*----------*/

		/*---Order Booked data---*/
		String orderBookedBy		=	textFieldOrderBookedBy.getText();
		String partyTransportList	=	textFieldPartyTransportList.getText();
		String goodsPackets1		=	textFieldGoodsPackets1.getText();
		String goodsPackets2		=	textFieldGoodsPackets2.getText();
		String partynickName		=	textFieldPartynickName.getText();

		String total				=	textFieldTotal.getText();
		String billAmount			=	textFieldBillAmount.getText();
		String packingAmount		=	textFieldPackingAmount.getText();
		String gstAmount 			=	textFieldGstTAX.getText();

		String grBiltyNumber = Strings.isNullOrEmpty(textFieldGrNumber.getText()) ?"" :textFieldGrNumber.getText();
		String col1Name= Strings.isNullOrEmpty(textFieldCol1.getText())?"" :textFieldCol1.getText();
		String col2Name = Strings.isNullOrEmpty(textFieldCol2.getText())?"" :textFieldCol2.getText();
		String col1Val= Strings.isNullOrEmpty(textFieldCol1Val.getText())?"" : textFieldCol1Val.getText();
		String col2Val = Strings.isNullOrEmpty(textFieldCol2Val.getText())?"" : textFieldCol2Val.getText();;
		String col1Operator = getCol1Operator(col1Val);
		String col2Operator = getCol2Operator(col2Val);
		
		/*-------------Getting account info if not found then saving into db-------------*/
		AccountInfo accountInfoVal = null;
		boolean isFound = false;
		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}

		for(AccountInfo accountInfo : accountInfos){
			if(null != accountInfo.getCity() && null != accountInfo.getName() 
					&& partyName.equalsIgnoreCase(accountInfo.getName()) && partyAddress.contains(accountInfo.getCity())){
				accountInfoVal = accountInfo;
				isFound = true;
				break;
			}
		} //nd for
		
		boolean isSavedAccount = false;

		if(!isFound){
			accountInfoVal = AccountInfo.builder()
					.description(partyAddress)
					.name(partyName)
					.city(partyAddress)
					.description(partyAddress)
					.nickName(partynickName).transport(partyTransportList).state(partyState)
					.gstNumber(partyGST)
					.engagedDate(new Timestamp(System.currentTimeMillis()))
					.createdDate(new Timestamp(System.currentTimeMillis()))
					.lastUpdated(new Timestamp(System.currentTimeMillis())).isEnable(Boolean.TRUE).build();

			try{
				Integer pin = Integer.parseInt(partyCode);
				accountInfoVal.setPinCode(pin);
			}catch(Exception e){
				LOGGER.error(e);
				LOGGER.error("invalid pin found that's why setting 0");
				accountInfoVal.setPinCode(0);
			}

			try{

				BigInteger partyMob = new BigInteger(partyMobile);
				accountInfoVal.setPhone(partyMob);
			}catch(Exception e){
				LOGGER.error(e);
				LOGGER.error("invalid mobile found that's why setting 0");
				accountInfoVal.setPhone(new BigInteger("0"));
			}

			try {
				AccountDaoImpl.getInstance().save(accountInfoVal);
				isSavedAccount = true;
			} catch (Exception e) {
				JOptionPane.showInternalMessageDialog(this, e.toString());
				e.printStackTrace();
				return ;
			}
		}
		/*--------------------End Account info -------------------------*/
		
		InvoiceInfo invoiceInfo = null;
		
		/* ---------------Getting all invoice info from invoice table and if stock item not found in DB then saving it--------------*/
		List<InvoiceItemInfo> invoiceItemInfos =  ((TableInvoiceModel)tableInvoice.getModel()).getInvoiceItemInfos();
		List<StockItem> stockItems = new ArrayList<StockItem>();
		try {
			stockItems = StockDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}


		for(int i=0; i< invoiceItemInfos.size(); i++){
			InvoiceItemInfo invoiceItemInfo = invoiceItemInfos.get(i);
			boolean isStockItemFound =  false;
			for(int j=0;j < stockItems.size(); j++){
				StockItem stockItem = stockItems.get(j);
				if(null != stockItem.getName() && stockItem.getName().equals(invoiceItemInfo.getDescription())){
					invoiceItemInfo.setStockItemId(stockItem.getId());
					isStockItemFound = true;
					break;
				}
			}

			if(!isStockItemFound){
				StockItem stockItem = StockItem.builder().name(invoiceItemInfo.getDescription())
						.size(invoiceItemInfo.getSize())
						.saleunit(invoiceItemInfo.getUnit())
						.createdDate(new Timestamp(System.currentTimeMillis()))
						.lastUpdatedDate(new Timestamp(System.currentTimeMillis()))
						.isEnable(Boolean.TRUE)
						.build();
				try{
					StockDaoImpl.getInstance().save(stockItem);
				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showInternalMessageDialog(this, e.toString());
					return ;
				}
				invoiceItemInfo.setStockItemId(stockItem.getId());
			}

		}//end for
		/*-----------End stock items and invoice table entry-----------------*/
		
		
		invoiceInfo = InvoiceInfo.builder().items(invoiceItemInfos)
				.id(actionObjectInvoiceInfo.getId())
				.invoicNum(actionObjectInvoiceInfo.getInvoicNum())
				.invoicDate(invoiceDate)
				.billNo(billNo)
				.orderBookedBy(orderBookedBy)
				.transport(partyTransportList)
				.pktNumber(goodsPackets2)
				.gstValue(gstAmount)
				
				.billAmount(billAmount)
				.grBiltyNumber(grBiltyNumber)
				//.createdDate(new Timestamp(System.currentTimeMillis()))
				.col1Name(col1Name)
				.col1Val(col1Val)
				.col2Name(col2Name)
				.col2Val(col2Val)
				.col1Operator(col1Operator)
				.col2Operator(col2Operator)
				.withoutBillAmount(packingAmount)	
				
				.lastUpdated(new Timestamp(System.currentTimeMillis()))
				.isEnable(Boolean.TRUE)
				.partyinfoID(accountInfoVal.getId())
				
				.build();
		try{
			InvoiceDaoImpl.getInstance().update(invoiceInfo);
		}catch(Exception e){
			JOptionPane.showInternalMessageDialog(this, e.toString());
			return;
		}

		Integer invoiceId= invoiceInfo.getId();
		for(int i=0; i<invoiceItemInfos.size(); i++){
			InvoiceItemInfo itemInfo = invoiceItemInfos.get(i);
			itemInfo.setInvoiceID(invoiceId);
			try {
				if(null != itemInfo.getId()){
					InvoiceItemDaoImpl.getInstance().disable(itemInfo);
				}
				InvoiceItemDaoImpl.getInstance().save(itemInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//JOptionPane.showMessageDialog(this, "Saved successfully.","Message",JOptionPane.PLAIN_MESSAGE);
		int option = JOptionPane.showConfirmDialog(this, "Changes saved successfully.\n Do you want to print ?");
		if(option == JOptionPane.OK_OPTION){
			btnPrint.setEnabled(Boolean.TRUE);
			btnPrint.doClick();
		}

		btnRefresh.doClick();
	}
	

	private Action getRefreshAction(){
		Action editAction = new AbstractAction("Refresh") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + R clicked");
				refresh();
			}
		};

		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	
	private Action getSaveAction(){
		Action editAction = new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + S clicked");
				save();
			}
		};

		editAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	
	private Action getAddAction(){
		Action editAction = new AbstractAction("Add") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + N clicked");
				addnextInvoice();
			}
		};

		editAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	
	private Action getUpdateAction(){
		Action editAction = new AbstractAction("Update") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + U clicked");
				//TODO
			}
		};

		editAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}

	private Action getDeleteAction(){
		Action editAction = new AbstractAction("Delete") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + D clicked");
				delete();
			}
		};
		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	
	private Action getPrintAction(){
		Action editAction = new AbstractAction("Print") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + P clicked");
				print();
			}
		};

		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	
	private String getCol1Operator(String col1Val){
		/*getting operation for col1val & col2 val*/
		String col1Operator = "";
		
		if(!Strings.isNullOrEmpty(col1Val)){
			try{
				OperationInfo operationInfo = OperationInfo.builder()
						.key(CLASS_NAME)
						.propertyname("textFieldCol1Val")
						.build();
				operationInfo = OperationInfoDaoImpl.getInstance().getByParam(operationInfo);
				if(operationInfo!=null && !Strings.isNullOrEmpty(operationInfo.getPropertyvalue()) ){
					col1Operator = operationInfo.getPropertyvalue();
				}
			}catch(Exception exp){
				LOGGER.equals(exp);
			}
		}
		return  col1Operator;
	}
	
	private String getCol2Operator(String col2Val){
		String col2Operator = "";
		if(!Strings.isNullOrEmpty(col2Val)){
			try{
				OperationInfo operationInfo = OperationInfo.builder()
						.key(CLASS_NAME)
						.propertyname("textFieldCol2Val")
						.build();
				operationInfo = OperationInfoDaoImpl.getInstance().getByParam(operationInfo);
				if(operationInfo!=null && !Strings.isNullOrEmpty(operationInfo.getPropertyvalue()) ){
					col2Operator = operationInfo.getPropertyvalue(); 
				}
			}catch(Exception exp){
				LOGGER.equals(exp);
			}
		}
		return col2Operator;
	}
}
