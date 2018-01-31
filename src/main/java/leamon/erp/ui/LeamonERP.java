package leamon.erp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import com.google.common.base.Strings;

import leamon.erp.db.LeamonPropertyDaoImpl;
import leamon.erp.db.StateCityDaoImpl;
import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.LeamonProperty;
import leamon.erp.model.StateCityInfo;
import leamon.erp.ui.custom.BGImagePanel;
import leamon.erp.util.LeamonERPConstants;

public class LeamonERP extends JFrame {

	private JPanel contentPane;

	private static final Logger LOGGER = Logger.getLogger(LeamonERP.class);
	public static JDesktopPane desktopPane  = new JDesktopPane();

	public static AccountInfoUI accountInfoUI;
	public static StockItemUI stockItemManager;
	public static StockItemListManager stockItemList;
	public static AccountListManager accountListManager;

	public static InventoryUIManager inventoryUIManager;
	public static InventoryUI inventoryUI;
	public static GrandTotalUI grandTotalUI;
	public static CompanyUI companyUI;
	public static InvoiceSearchUI invoiceSearchUI;
	public static PaymentUI paymentUI;
	public static PaymentReceivedSummaryUI paymentReceivedUI;
	public static StockItemQuantityUI stockItemQuantityUI;
	public static AccountOpeningBalanceUI accountOpeningBalanceUI;

	public static  List<String> cityCache;
	public static  List<String> countryCache;
	public static  List<String> stateCache;
	
	public static List<StateCityInfo> stateCityInfos;
	
	public static InvoiceUI invoiceUI;
	public static InvoiceUILegal invoiceUILegal;
	
	public static LeamonERP frame;

	public static  List<LeamonProperty> leamonProperties = new ArrayList<LeamonProperty>();
	public static boolean isleamonPropertiesLoaded = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {}

	/**
	 * Create the frame.
	 */
	public LeamonERP() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Leamon-ERP");
		Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(sc);
		//setUndecorated(true);
		/*JRootPane root = getRootPane( );
		root.setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);*/
		//root.putClientProperty( "apple.awt.brushMetalLook", Boolean.TRUE );
		//root.putClientProperty( "Window.style", "small" );
		//setDefaultLookAndFeelDecorated( false );
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		Dimension minimumSize = new Dimension(200, 200);
		this.setMinimumSize(minimumSize);
		
		setBounds(0, 0, (int)sc.getWidth(), (int)sc.getHeight());
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(Color.WHITE);
		//splitPane.setLeftComponent(comp);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPaneRecentItems = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneRecentItems);

		JScrollPane scrollPaneContent = new JScrollPane();
		splitPane.setRightComponent(scrollPaneContent);

		//desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(128, 128, 128));
		scrollPaneContent.setViewportView(desktopPane);
		
		JXTaskPane taskPaneInventory = new JXTaskPane();
		taskPaneInventory.getContentPane().setBackground(new Color(255, 255, 255));
		
		taskPaneInventory.setTitle("Invoice");
		taskPaneInventory.setBounds(0, 33, 224, 223);
		//taskPaneInventory.add(createTextAction());
		desktopPane.add(taskPaneInventory);
		taskPaneInventory.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//this.getClass().getClassLoader().getResource(LeamonERPConstants.BILLING).getPath()
		JPanel panelInVoiceBGImage = new BGImagePanel(LeamonERPConstants.IMAGE_PATH_LEAMON.concat(LeamonERPConstants.BILLING));
		panelInVoiceBGImage.setBackground(new Color(255, 255, 255));
		taskPaneInventory.getContentPane().add(panelInVoiceBGImage, BorderLayout.CENTER);
		panelInVoiceBGImage.setLayout(null);

		JXHyperlink hyperlinkInventory = new JXHyperlink();
		hyperlinkInventory.setClickedColor(new Color(25, 25, 112));
		hyperlinkInventory.setBackground(new Color(240, 230, 140));
		hyperlinkInventory.setForeground(new Color(0, 0, 0));
		hyperlinkInventory.setHorizontalAlignment(SwingConstants.CENTER);
		hyperlinkInventory.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkInventory.setText("Invoice");
		hyperlinkInventory.setBounds(24, 11, 168, 33);
		//panelInVoiceBGImage.add(hyperlinkInventory);
		
		JXHyperlink hyperlinkInvoice = new JXHyperlink();
		hyperlinkInvoice.setForeground(new Color(0, 0, 0));
		hyperlinkInvoice.addActionListener(e -> hyperlinkInvoiceClick(e));
		hyperlinkInvoice.setText("Invoice");
		hyperlinkInvoice.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkInvoice.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkInvoice.setBounds(0, 45, 135, 23);
		
		panelInVoiceBGImage.add(hyperlinkInvoice);
		
		JXHyperlink hprlnkEinvoice = new JXHyperlink();
		hprlnkEinvoice.setText("E-Biling");
		hprlnkEinvoice.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkEinvoice.setForeground(Color.BLACK);
		hprlnkEinvoice.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkEinvoice.setBounds(0, 11, 135, 23);
		hprlnkEinvoice.addActionListener(e -> hprlnkEinvoiceClick(e));
		panelInVoiceBGImage.add(hprlnkEinvoice);
		hyperlinkInventory.addActionListener(e -> hyperlinkInventoryClick(e));

		JXTaskPane taskPaneStockItems = new JXTaskPane();
		taskPaneStockItems.setTitle("Stock Items");
		taskPaneStockItems.setBounds(0, 255, 224, 208);
		desktopPane.add(taskPaneStockItems);
		taskPaneStockItems.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel panelBGImageStock = new BGImagePanel(LeamonERPConstants.IMAGE_PATH_LEAMON.concat(LeamonERPConstants.STOCK_IMAGE));
		panelBGImageStock.setBackground(new Color(255, 255, 255));
		taskPaneStockItems.getContentPane().add(panelBGImageStock, BorderLayout.CENTER);
		panelBGImageStock.setLayout(null);

		JXHyperlink hprlnkStockItemAddNew = new JXHyperlink();
		hprlnkStockItemAddNew.setForeground(new Color(0, 0, 0));
		hprlnkStockItemAddNew.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkStockItemAddNew.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemAddNew.setText("Add New");
		hprlnkStockItemAddNew.setBounds(7, 4, 135, 27);
		panelBGImageStock.add(hprlnkStockItemAddNew);
		hprlnkStockItemAddNew.addActionListener(e -> hprlnkStockItemAddNewClick(e));

		JXHyperlink hprlnkStockItemDelete = new JXHyperlink();
		hprlnkStockItemDelete.setForeground(new Color(0, 0, 0));
		hprlnkStockItemDelete.addActionListener(e -> hprlnkStockItemDeleteClick(e));
		hprlnkStockItemDelete.setText("Delete");
		hprlnkStockItemDelete.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkStockItemDelete.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemDelete.setBounds(7, 30, 114, 27);
		panelBGImageStock.add(hprlnkStockItemDelete);

		JXHyperlink hprlnkStockItemEdit = new JXHyperlink();
		hprlnkStockItemEdit.setForeground(new Color(0, 0, 0));
		hprlnkStockItemEdit.setText("Edit");
		hprlnkStockItemEdit.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkStockItemEdit.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemEdit.setBounds(7, 68, 117, 27);
		panelBGImageStock.add(hprlnkStockItemEdit);
		hprlnkStockItemEdit.addActionListener(e -> hprlnkStockItemEditClick(e));

		JXHyperlink hprlnkStockItemSearch = new JXHyperlink();
		hprlnkStockItemSearch.setForeground(new Color(0, 0, 0));
		hprlnkStockItemSearch.setText("Search");
		hprlnkStockItemSearch.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkStockItemSearch.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemSearch.setBounds(7, 103, 93, 27);
		panelBGImageStock.add(hprlnkStockItemSearch);
		hprlnkStockItemSearch.addActionListener(e -> hprlnkStockItemSearchClick(e));

		JXTaskPane taskPane_2 = new JXTaskPane();
		
		taskPane_2.setTitle("Company Master");
		taskPane_2.setBounds(1101, 33, 224, 223);
		desktopPane.add(taskPane_2);
		taskPane_2.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBGImgCompany = new BGImagePanel(LeamonERPConstants.IMAGE_PATH_LEAMON.concat(LeamonERPConstants.COMPANY_IMAGE));
		panelBGImgCompany.setBackground(new Color(255, 255, 255));
		taskPane_2.getContentPane().add(panelBGImgCompany, BorderLayout.CENTER);
		
		JXHyperlink hprlnkCompany = new JXHyperlink();
		hprlnkCompany.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		hprlnkCompany.setForeground(new Color(0, 0, 0));
		hprlnkCompany.addActionListener(e -> hprlnkCompanyClick(e));
		panelBGImgCompany.setLayout(null);
		hprlnkCompany.setText("Company Info");
		hprlnkCompany.setHorizontalAlignment(SwingConstants.CENTER);
		hprlnkCompany.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkCompany.setBounds(10, 0, 145, 23);
		panelBGImgCompany.add(hprlnkCompany);

		JXTaskPane taskPaneAccounts = new JXTaskPane();
		taskPaneAccounts.setBackground(new Color(255, 255, 255));
		taskPaneAccounts.setTitle("Party Master");
		taskPaneAccounts.setBounds(0, 462, 224, 208);
		desktopPane.add(taskPaneAccounts);
		taskPaneAccounts.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBGParty = new BGImagePanel(LeamonERPConstants.IMAGE_PATH_LEAMON.concat(LeamonERPConstants.PARTY_IMAGE));
		panelBGParty.setBackground(new Color(255, 255, 255));
		taskPaneAccounts.getContentPane().add(panelBGParty, BorderLayout.CENTER);
		panelBGParty.setLayout(null);

		JXHyperlink hyperlinkAccountAddNew = new JXHyperlink();
		hyperlinkAccountAddNew.setForeground(new Color(0, 0, 0));
		hyperlinkAccountAddNew.setText("Add New");
		hyperlinkAccountAddNew.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkAccountAddNew.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountAddNew.setBounds(9, 14, 96, 27);
		panelBGParty.add(hyperlinkAccountAddNew);
		hyperlinkAccountAddNew.addActionListener(e -> hyperlinkAccountAddNewClick(e));

		JXHyperlink hyperlinkAccountDelete = new JXHyperlink();
		hyperlinkAccountDelete.setForeground(new Color(0, 0, 0));
		hyperlinkAccountDelete.setText("Delete");
		hyperlinkAccountDelete.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkAccountDelete.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountDelete.setBounds(9, 52, 105, 27);
		panelBGParty.add(hyperlinkAccountDelete);
		hyperlinkAccountDelete.addActionListener(e-> hyperlinkAccountDeleteClick(e));

		JXHyperlink hyperlinkAccountEdit = new JXHyperlink();
		hyperlinkAccountEdit.setForeground(new Color(0, 0, 0));
		hyperlinkAccountEdit.setText("Edit");
		hyperlinkAccountEdit.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkAccountEdit.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountEdit.setBounds(9, 87, 93, 27);
		panelBGParty.add(hyperlinkAccountEdit);
		hyperlinkAccountEdit.addActionListener(e -> hyperlinkAccountEditClick(e));

		JXHyperlink hyperlinkAccountSearch = new JXHyperlink();
		hyperlinkAccountSearch.setForeground(new Color(0, 0, 0));
		hyperlinkAccountSearch.setText("Search");
		hyperlinkAccountSearch.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkAccountSearch.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountSearch.setBounds(9, 125, 93, 27);
		panelBGParty.add(hyperlinkAccountSearch);
		hyperlinkAccountSearch.addActionListener(e ->  hyperlinkAccountSearchClick(e));

		JXTaskPane taskPaneReport = new JXTaskPane();
		taskPaneReport.setTitle("Payment Master");
		taskPaneReport.setBounds(1101, 255, 224, 223);
		desktopPane.add(taskPaneReport);
		taskPaneReport.getContentPane().setLayout(null);
		
		JXHyperlink hprlnkPaymentRegister = new JXHyperlink();
		hprlnkPaymentRegister.setText("Payment Register");
		hprlnkPaymentRegister.setHorizontalAlignment(SwingConstants.CENTER);
		hprlnkPaymentRegister.setForeground(Color.BLACK);
		hprlnkPaymentRegister.setFont(new Font("Courier New", Font.BOLD, 18));
		hprlnkPaymentRegister.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		hprlnkPaymentRegister.setBounds(4, 11, 193, 23);
		taskPaneReport.getContentPane().add(hprlnkPaymentRegister);
		hprlnkPaymentRegister.addActionListener(e -> hprlnkPaymentRegisterClick(e));

		JXTaskPane taskPane_5 = new JXTaskPane();
		taskPane_5.setTitle("Tarnsaction Master");
		taskPane_5.setBounds(1101, 477, 224, 223);
		desktopPane.add(taskPane_5);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, ((int)sc.getWidth())-10, 30);
		desktopPane.add(menuBar);
		
		JMenu mnCompanyMaster = new JMenu("Company Master");
		menuBar.add(mnCompanyMaster);
		
		JMenuItem mntmCompanyInfo = new JMenuItem("Company Info");
		mntmCompanyInfo.addActionListener(e -> hprlnkCompanyClick(e));
		mnCompanyMaster.add(mntmCompanyInfo);
		
		JMenu mnInvoiceMaster = new JMenu("Invoice Master");
		menuBar.add(mnInvoiceMaster);
		
		JMenuItem mntmB_Invoice = new JMenuItem("B-Invoice");
		mntmB_Invoice.addActionListener(e -> hprlnkEinvoiceClick(e));
		mnInvoiceMaster.add(mntmB_Invoice);
		
		JMenuItem mntmW_Invoice = new JMenuItem("W-Invoice");
		mntmW_Invoice.addActionListener(e -> hyperlinkInvoiceClick(e));
		mnInvoiceMaster.add(mntmW_Invoice);
		
		JMenu mnStockMaster = new JMenu("Stock Master");
		menuBar.add(mnStockMaster);
		
		JMenuItem mntmAddNewStock = new JMenuItem("Add New");
		mntmAddNewStock.addActionListener(e -> hprlnkStockItemAddNewClick(e));
		mnStockMaster.add(mntmAddNewStock);
		
		JMenuItem mntmEditStock = new JMenuItem("Edit");
		mntmEditStock.addActionListener(e -> hprlnkStockItemEditClick(e));
		mnStockMaster.add(mntmEditStock);
		
		JMenuItem mntmSearchStock = new JMenuItem("Search");
		mntmSearchStock.addActionListener(e -> hprlnkStockItemSearchClick(e));
		mnStockMaster.add(mntmSearchStock);
		
		JMenuItem mntmDeleteStock = new JMenuItem("Delete");
		mntmDeleteStock.addActionListener(e -> hprlnkStockItemDeleteClick(e));
		mnStockMaster.add(mntmDeleteStock);
		
		JMenu mnPartyMaster = new JMenu("Party Master");
		menuBar.add(mnPartyMaster);
		
		JMenuItem mntmAddNewParty = new JMenuItem("Add New");
		mntmAddNewParty.addActionListener(e -> hyperlinkAccountAddNewClick(e));
		mnPartyMaster.add(mntmAddNewParty);
		
		JMenuItem mntmEditParty = new JMenuItem("Edit");
		mntmEditParty.addActionListener(e -> hyperlinkAccountEditClick(e));
		mnPartyMaster.add(mntmEditParty);
		
		JMenuItem mntmSearchParty = new JMenuItem("Search");
		mntmSearchParty.addActionListener(e ->  hyperlinkAccountSearchClick(e));
		mnPartyMaster.add(mntmSearchParty);
		
		JMenuItem mntmDeleteParty = new JMenuItem("Delete");
		mntmDeleteParty.addActionListener(e-> hyperlinkAccountDeleteClick(e));
		mnPartyMaster.add(mntmDeleteParty);
		
		JMenu mnPaymentMaster = new JMenu("Payment Master");
		menuBar.add(mnPaymentMaster);
		
		JMenuItem mntmAdjustmentPayment = new JMenuItem("Adjustments");
		mntmAdjustmentPayment.addActionListener(e -> hprlnkPaymentRegisterClick(e));
		mnPaymentMaster.add(mntmAdjustmentPayment);
		
		JMenuItem mntmSummaryPayment = new JMenuItem("Summary");
		mntmSummaryPayment.addActionListener(e -> mntmSummaryPaymentClick(e) );
		mnPaymentMaster.add(mntmSummaryPayment);
		
		JMenuItem mntmOpeningBalance = new JMenuItem("Opening Balance");
		mntmOpeningBalance.addActionListener(e -> mntmOpeningBalanceClick(e));
		mnPaymentMaster.add(mntmOpeningBalance);
		
		JMenu mnTheme = new JMenu("Theme");
		menuBar.add(mnTheme);
		
		JMenuItem mntmWindow = new JMenuItem("Window");
		mntmWindow.addActionListener(e -> mntmWindowClick(e));
		mnTheme.add(mntmWindow);
		
		JMenuItem mntmClassic = new JMenuItem("Classic");
		mnTheme.add(mntmClassic);
		mntmClassic.addActionListener(e -> mntmClassicClick(e));
		
		JMenuItem mntmMetal = new JMenuItem("Mortis");
		mnTheme.add(mntmMetal);
		mntmMetal.addActionListener(e -> mntmMortisClick(e));
		
		JMenuItem mntmNimus = new JMenuItem("Nimbus");
		mnTheme.add(mntmNimus);
		mntmNimus.addActionListener(e -> mntmNimusClick(e));
		
		JMenuItem mntmDefault = new JMenuItem("Default");
		mnTheme.add(mntmDefault);
		mntmDefault.addActionListener(e -> mntmDefaultClick(e));
		
		try{
		StockDaoImpl.getInstance().prepareStockIntelliSense();
		stateCityInfosLoader();
		loadAddressData();
		}catch(Exception e){
			LOGGER.error(e);
		}
		
		/*preparing LeamonProeprty*/
		try{
			if(!isleamonPropertiesLoaded){
				leamonProperties = LeamonPropertyDaoImpl.getInstance().getItemList();
				isleamonPropertiesLoaded = true;
			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		
		stockItemManager = new StockItemUI();
		stockItemList = new StockItemListManager();
		accountListManager = new AccountListManager();
		//framCreator();
		inventoryUI = new InventoryUI();

		grandTotalUI = new GrandTotalUI();
		
		companyUI = new CompanyUI();
		
		invoiceSearchUI = new InvoiceSearchUI();

		accountInfoUI = new AccountInfoUI();
		invoiceUI = new InvoiceUI();
		invoiceUILegal =new InvoiceUILegal();
		paymentUI = new PaymentUI();
		paymentReceivedUI = new PaymentReceivedSummaryUI();
		stockItemQuantityUI = new StockItemQuantityUI();
		accountOpeningBalanceUI = new AccountOpeningBalanceUI();
	
		}
	
	public void initComponents(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Leamon-ERP");
		Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(sc);
		//setUndecorated(true);
		/*JRootPane root = getRootPane( );
		root.setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);*/
		//root.putClientProperty( "apple.awt.brushMetalLook", Boolean.TRUE );
		//root.putClientProperty( "Window.style", "small" );
		//setDefaultLookAndFeelDecorated( false );
		
		Dimension minimumSize = new Dimension(200, 200);
		this.setMinimumSize(minimumSize);

		setBounds(0, 0, (int)sc.getWidth(), (int)sc.getHeight());
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(Color.WHITE);
		//splitPane.setLeftComponent(comp);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPaneRecentItems = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneRecentItems);

		JScrollPane scrollPaneContent = new JScrollPane();
		splitPane.setRightComponent(scrollPaneContent);

		//desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(128, 128, 128));
		scrollPaneContent.setViewportView(desktopPane);

		JXTaskPane taskPaneInventory = new JXTaskPane();
		taskPaneInventory.getContentPane().setBackground(new Color(255, 255, 255));
		
		taskPaneInventory.setTitle("Invoice");
		taskPaneInventory.setBounds(0, 33, 224, 223);
		//taskPaneInventory.add(createTextAction());
		desktopPane.add(taskPaneInventory);
		taskPaneInventory.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//this.getClass().getClassLoader().getResource(LeamonERPConstants.BILLING).getPath()
		JPanel panelInVoiceBGImage = new BGImagePanel(LeamonERPConstants.IMAGE_PATH_LEAMON.concat(LeamonERPConstants.BILLING));
		panelInVoiceBGImage.setBackground(new Color(255, 255, 255));
		taskPaneInventory.getContentPane().add(panelInVoiceBGImage, BorderLayout.CENTER);
		panelInVoiceBGImage.setLayout(null);

		JXHyperlink hyperlinkInventory = new JXHyperlink();
		hyperlinkInventory.setClickedColor(new Color(25, 25, 112));
		hyperlinkInventory.setBackground(new Color(240, 230, 140));
		hyperlinkInventory.setForeground(new Color(0, 0, 0));
		hyperlinkInventory.setHorizontalAlignment(SwingConstants.CENTER);
		hyperlinkInventory.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkInventory.setText("Invoice");
		hyperlinkInventory.setBounds(24, 11, 168, 33);
		//panelInVoiceBGImage.add(hyperlinkInventory);
		
		JXHyperlink hyperlinkInvoice = new JXHyperlink();
		hyperlinkInvoice.setForeground(new Color(0, 0, 0));
		hyperlinkInvoice.addActionListener(e -> hyperlinkInvoiceClick(e));
		hyperlinkInvoice.setText("Invoice");
		hyperlinkInvoice.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkInvoice.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkInvoice.setBounds(0, 0, 135, 23);
		
		panelInVoiceBGImage.add(hyperlinkInvoice);
		hyperlinkInventory.addActionListener(e -> hyperlinkInventoryClick(e));

		JXTaskPane taskPaneStockItems = new JXTaskPane();
		taskPaneStockItems.setTitle("Stock Items");
		taskPaneStockItems.setBounds(0, 255, 224, 208);
		desktopPane.add(taskPaneStockItems);
		taskPaneStockItems.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel panelBGImageStock = new BGImagePanel(LeamonERPConstants.IMAGE_PATH_LEAMON.concat(LeamonERPConstants.STOCK_IMAGE));
		panelBGImageStock.setBackground(new Color(255, 255, 255));
		taskPaneStockItems.getContentPane().add(panelBGImageStock, BorderLayout.CENTER);
		panelBGImageStock.setLayout(null);

		JXHyperlink hprlnkStockItemAddNew = new JXHyperlink();
		hprlnkStockItemAddNew.setForeground(new Color(0, 0, 0));
		hprlnkStockItemAddNew.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkStockItemAddNew.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemAddNew.setText("Add New");
		hprlnkStockItemAddNew.setBounds(7, 4, 135, 27);
		panelBGImageStock.add(hprlnkStockItemAddNew);
		hprlnkStockItemAddNew.addActionListener(e -> hprlnkStockItemAddNewClick(e));

		JXHyperlink hprlnkStockItemDelete = new JXHyperlink();
		hprlnkStockItemDelete.setForeground(new Color(0, 0, 0));
		hprlnkStockItemDelete.addActionListener(e -> hprlnkStockItemDeleteClick(e));
		hprlnkStockItemDelete.setText("Delete");
		hprlnkStockItemDelete.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkStockItemDelete.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemDelete.setBounds(7, 30, 114, 27);
		panelBGImageStock.add(hprlnkStockItemDelete);

		JXHyperlink hprlnkStockItemEdit = new JXHyperlink();
		hprlnkStockItemEdit.setForeground(new Color(0, 0, 0));
		hprlnkStockItemEdit.setText("Edit");
		hprlnkStockItemEdit.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkStockItemEdit.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemEdit.setBounds(7, 68, 117, 27);
		panelBGImageStock.add(hprlnkStockItemEdit);
		hprlnkStockItemEdit.addActionListener(e -> hprlnkStockItemEditClick(e));

		JXHyperlink hprlnkStockItemSearch = new JXHyperlink();
		hprlnkStockItemSearch.setForeground(new Color(0, 0, 0));
		hprlnkStockItemSearch.setText("Search");
		hprlnkStockItemSearch.setHorizontalAlignment(SwingConstants.LEFT);
		hprlnkStockItemSearch.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemSearch.setBounds(7, 103, 93, 27);
		panelBGImageStock.add(hprlnkStockItemSearch);
		hprlnkStockItemSearch.addActionListener(e -> hprlnkStockItemSearchClick(e));

		JXTaskPane taskPane_2 = new JXTaskPane();
		
		taskPane_2.setTitle("Company Master");
		taskPane_2.setBounds(1101, 33, 224, 223);
		desktopPane.add(taskPane_2);
		taskPane_2.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBGImgCompany = new BGImagePanel(LeamonERPConstants.IMAGE_PATH_LEAMON.concat(LeamonERPConstants.COMPANY_IMAGE));
		panelBGImgCompany.setBackground(new Color(255, 255, 255));
		taskPane_2.getContentPane().add(panelBGImgCompany, BorderLayout.CENTER);
		
		JXHyperlink hprlnkCompany = new JXHyperlink();
		hprlnkCompany.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		hprlnkCompany.setForeground(new Color(0, 0, 0));
		hprlnkCompany.addActionListener(e -> hprlnkCompanyClick(e));
		panelBGImgCompany.setLayout(null);
		hprlnkCompany.setText("Company Info");
		hprlnkCompany.setHorizontalAlignment(SwingConstants.CENTER);
		hprlnkCompany.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkCompany.setBounds(10, 0, 145, 23);
		panelBGImgCompany.add(hprlnkCompany);

		JXTaskPane taskPaneAccounts = new JXTaskPane();
		taskPaneAccounts.setBackground(new Color(255, 255, 255));
		taskPaneAccounts.setTitle("Party Master");
		taskPaneAccounts.setBounds(0, 462, 224, 208);
		desktopPane.add(taskPaneAccounts);
		taskPaneAccounts.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelBGParty = new BGImagePanel(LeamonERPConstants.IMAGE_PATH_LEAMON.concat(LeamonERPConstants.PARTY_IMAGE));
		panelBGParty.setBackground(new Color(255, 255, 255));
		taskPaneAccounts.getContentPane().add(panelBGParty, BorderLayout.CENTER);
		panelBGParty.setLayout(null);

		JXHyperlink hyperlinkAccountAddNew = new JXHyperlink();
		hyperlinkAccountAddNew.setForeground(new Color(0, 0, 0));
		hyperlinkAccountAddNew.setText("Add New");
		hyperlinkAccountAddNew.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkAccountAddNew.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountAddNew.setBounds(9, 14, 96, 27);
		panelBGParty.add(hyperlinkAccountAddNew);
		hyperlinkAccountAddNew.addActionListener(e -> hyperlinkAccountAddNewClick(e));

		JXHyperlink hyperlinkAccountDelete = new JXHyperlink();
		hyperlinkAccountDelete.setForeground(new Color(0, 0, 0));
		hyperlinkAccountDelete.setText("Delete");
		hyperlinkAccountDelete.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkAccountDelete.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountDelete.setBounds(9, 52, 105, 27);
		panelBGParty.add(hyperlinkAccountDelete);
		hyperlinkAccountDelete.addActionListener(e-> hyperlinkAccountDeleteClick(e));

		JXHyperlink hyperlinkAccountEdit = new JXHyperlink();
		hyperlinkAccountEdit.setForeground(new Color(0, 0, 0));
		hyperlinkAccountEdit.setText("Edit");
		hyperlinkAccountEdit.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkAccountEdit.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountEdit.setBounds(9, 87, 93, 27);
		panelBGParty.add(hyperlinkAccountEdit);
		hyperlinkAccountEdit.addActionListener(e -> hyperlinkAccountEditClick(e));

		JXHyperlink hyperlinkAccountSearch = new JXHyperlink();
		hyperlinkAccountSearch.setForeground(new Color(0, 0, 0));
		hyperlinkAccountSearch.setText("Search");
		hyperlinkAccountSearch.setHorizontalAlignment(SwingConstants.LEFT);
		hyperlinkAccountSearch.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountSearch.setBounds(9, 125, 93, 27);
		panelBGParty.add(hyperlinkAccountSearch);
		hyperlinkAccountSearch.addActionListener(e ->  hyperlinkAccountSearchClick(e));

		JXTaskPane taskPaneReport = new JXTaskPane();
		taskPaneReport.setTitle("Payment Master");
		taskPaneReport.setBounds(1101, 255, 224, 223);
		desktopPane.add(taskPaneReport);

		JXTaskPane taskPane_5 = new JXTaskPane();
		taskPane_5.setTitle("Tarnsaction Master");
		taskPane_5.setBounds(1101, 477, 224, 223);
		desktopPane.add(taskPane_5);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, ((int)sc.getWidth())-10, 30);
		desktopPane.add(menuBar);
		try{
		StockDaoImpl.getInstance().prepareStockIntelliSense();
		stateCityInfosLoader();
		loadAddressData();
		}catch(Exception e){
			LOGGER.error(e);
		}

		stockItemManager = new StockItemUI();
		stockItemList = new StockItemListManager();
		accountListManager = new AccountListManager();
		//framCreator();
		inventoryUI = new InventoryUI();

		grandTotalUI = new GrandTotalUI();
		
		companyUI = new CompanyUI();
		
		invoiceSearchUI = new InvoiceSearchUI();

		accountInfoUI = new AccountInfoUI();
		invoiceUI = new InvoiceUI();
	
	}

	public void hyperlinkAccountEditClick(ActionEvent e) {
		hyperlinkAccountSearchClick(e);
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void  hyperlinkAccountDeleteClick(ActionEvent e) {
		hyperlinkAccountSearchClick(e);
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void  hyperlinkAccountAddNewClick(ActionEvent e) {
		if(accountInfoUI.isVisible()){
			try {
				accountInfoUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			accountInfoUI.moveToFront();
		}else{
			desktopPane.add(accountInfoUI);
			accountInfoUI.setVisible(true);
			accountInfoUI.moveToFront();
		}
		//hyperlinkAccountSearchClick(e);
		SwingUtilities.updateComponentTreeUI(accountInfoUI);
	}
	
	public void hyperlinkAccountSearchClick(ActionEvent e){
		if(accountListManager.isVisible()){
			try {
				accountListManager.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			accountListManager.moveToFront();
		}else{
			desktopPane.add(accountListManager);
			accountListManager.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(accountListManager);
	}

	public void hyperlinkInventoryClick(ActionEvent e){
		if(inventoryUI.isVisible()){
			try {
				inventoryUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			inventoryUI.moveToFront();
		}else{
			desktopPane.add(inventoryUI);
			inventoryUI.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(inventoryUI);
	}

	public void hyperlinkInvoiceClick(ActionEvent e){
		if(invoiceUI.isVisible()){
			try {
				invoiceUI.setSelected(true);
				invoiceUI.requestFocusOnLoad();
				invoiceUI.setEnableOnLoad(Boolean.FALSE);
				invoiceUI.getBtnUpdate().setEnabled(Boolean.FALSE);
				invoiceUI.getBtnDelete().setEnabled(Boolean.FALSE);
				invoiceUI.getBtnPrint().setEnabled(Boolean.FALSE);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			invoiceUI.moveToFront();
		}else{
			JInternalFrame frames [] =desktopPane.getAllFrames();
			if(frames!=null && frames.length>=0){
				int i=0;
				for(JInternalFrame frm : frames){
					if(frm.getName()!=null && frm.getName().equals("Leamon-ERP-Invoice")){
						invoiceUI.setEnableOnLoad(Boolean.FALSE);
						invoiceUI.getBtnRefresh().doClick();
						invoiceUI.setVisible(true);
						break;
					}
					i++;
				}//end for
				if(i == frames.length){
					invoiceUI.setEnableOnLoad(Boolean.FALSE);
					invoiceUI.getBtnRefresh().doClick();
					desktopPane.add(invoiceUI);
					invoiceUI.setVisible(true);
				}
			}else{
				invoiceUI.setEnableOnLoad(Boolean.FALSE);
				desktopPane.add(invoiceUI);
				invoiceUI.getBtnRefresh().doClick();
				invoiceUI.setVisible(true);
			}
		}
		SwingUtilities.updateComponentTreeUI(invoiceUI);
	}
	
	private void hprlnkEinvoiceClick(ActionEvent e){
		if(invoiceUILegal.isVisible()){
			try {
				invoiceUILegal.setSelected(true);
				/*invoiceUILegal.requestFocusOnLoad();
				invoiceUILegal.setEnableOnLoad(Boolean.FALSE);
				invoiceUILegal.getBtnUpdate().setEnabled(Boolean.FALSE);
				invoiceUILegal.getBtnDelete().setEnabled(Boolean.FALSE);
				invoiceUILegal.getBtnPrint().setEnabled(Boolean.FALSE);*/
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			invoiceUILegal.moveToFront();
		}else{
			JInternalFrame frames [] =desktopPane.getAllFrames();
			if(frames!=null && frames.length>=0){
				int i=0;
				for(JInternalFrame frm : frames){
					if(frm.getName()!=null && frm.getName().equals("Leamon-ERP E-Biling")){
						//invoiceUILegal.setEnableOnLoad(Boolean.FALSE);
						//invoiceUI.getBtnRefresh().doClick();
						invoiceUILegal.setVisible(true);
						break;
					}
					i++;
				}//end for
				if(i == frames.length){
					//invoiceUI.setEnableOnLoad(Boolean.FALSE);
					//invoiceUI.getBtnRefresh().doClick();
					desktopPane.add(invoiceUILegal);
					invoiceUILegal.setVisible(true);
				}
			}else{
				//invoiceUI.setEnableOnLoad(Boolean.FALSE);
				desktopPane.add(invoiceUILegal);
				//invoiceUILegal.getBtnRefresh().doClick();
				invoiceUILegal.setVisible(true);
			}
		}
		SwingUtilities.updateComponentTreeUI(invoiceUILegal);
	}
	public void hprlnkStockItemAddNewClick(ActionEvent e){
		if(stockItemManager.isVisible()){
			try {
				stockItemManager.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			stockItemManager.moveToFront();
		}else{
			
			desktopPane.add(stockItemManager);
			stockItemManager.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(stockItemManager);
	}

	public void hprlnkStockItemDeleteClick(ActionEvent e){
		hprlnkStockItemAddNewClick(e);
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void hprlnkStockItemEditClick(ActionEvent e){
		hprlnkStockItemAddNewClick(e);
		SwingUtilities.updateComponentTreeUI(this);
	}

	public void hprlnkStockItemSearchClick(ActionEvent e){
		if(stockItemList.isVisible()){
			try {
				stockItemList.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			stockItemList.moveToFront();
		}else{
			desktopPane.add(stockItemList);
			stockItemList.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(stockItemList);
	}
	
	private void hprlnkCompanyClick(ActionEvent e){
		if(companyUI.isVisible()){
			try {
				companyUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			companyUI.moveToFront();
		}else{
			desktopPane.add(companyUI);
			companyUI.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(companyUI);
	}
	
	private void hprlnkPaymentRegisterClick(ActionEvent e){

		if(paymentUI.isVisible()){
			try {
				paymentUI.autoAccountInfoSuggestor(paymentUI.getTextFieldPartyName());
				paymentUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			paymentUI.moveToFront();
		}else{
			desktopPane.add(paymentUI);
			paymentUI.autoAccountInfoSuggestor(paymentUI.getTextFieldPartyName());
			paymentUI.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(paymentUI);
	}
	
	private void mntmSummaryPaymentClick(ActionEvent e){

		if(paymentReceivedUI.isVisible()){
			try {
				paymentReceivedUI.autoAccountInfoSuggestor(paymentReceivedUI.getTextFieldPartyName());
				paymentReceivedUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			paymentReceivedUI.moveToFront();
		}else{
			desktopPane.add(paymentReceivedUI);
			paymentReceivedUI.autoAccountInfoSuggestor(paymentReceivedUI.getTextFieldPartyName());
			paymentReceivedUI.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(paymentReceivedUI);
	}
	
	private void mntmOpeningBalanceClick(ActionEvent e){
		if(accountOpeningBalanceUI.isVisible()){
			try {
				accountOpeningBalanceUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			accountOpeningBalanceUI.moveToFront();
		}else{
			desktopPane.add(accountOpeningBalanceUI);
			accountOpeningBalanceUI.setVisible(true);
		}
		SwingUtilities.updateComponentTreeUI(accountOpeningBalanceUI);
	}
	
	private void loadAddressData(){
		
		if(null == stateCityInfos || stateCityInfos.isEmpty()){
			 LOGGER.error("Failed to load city-state-code-mapping");
			 return;
		 }
		 
		 cityCache = stateCityInfos.stream().map(StateCityInfo::getCity).collect(Collectors.toList());
		 cityCache.add(0, LeamonERPConstants.CITY_PROMPT_MSG);
		 stateCache = stateCityInfos.stream().map(StateCityInfo::getState).collect(Collectors.toList());

		try(InputStream countryList = this.getClass().getClassLoader().getResourceAsStream("countrylist.txt")) {
			int availableitems= countryList.available();
			byte[] countries= new byte[availableitems];
			countryList.read(countries);
			String countryStr = new String(countries);
			String countryData [] = countryStr.split("\\r?\\n");
			countryCache = new ArrayList<String>();
			for(String val : countryData){
				countryCache.add(val);
			}
			LOGGER.info("Country list "+countryCache);
		} catch (IOException e) {
			LOGGER.error("failed to load country "+e);
		}
		
	}
	
	public void stateCityInfosLoader(){
		try{
			stateCityInfos = StateCityDaoImpl.getInstance().getItemList();
		}catch(Exception e){
			LOGGER.error(e);
		}
	}
	
	public static void openGTCalculatr(){
		if(grandTotalUI.isVisible()){
			try {
				grandTotalUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			grandTotalUI.moveToFront();
		}else{
			desktopPane.add(grandTotalUI);
			grandTotalUI.setVisible(true);
			try {
				grandTotalUI.setSelected(true);
			} catch (PropertyVetoException e1) {
				LOGGER.error(e1.toString());
			}
			grandTotalUI.moveToFront();
		}
	}

	private void mntmWindowClick(ActionEvent e){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//SwingUtilities.updateComponentTreeUI(this);
			SwingUtilities.updateComponentTreeUI(this);
			this.pack();
		}catch(Exception exp){
			LOGGER.error(exp);
			JOptionPane.showMessageDialog(this, exp.toString(),"Leamon-ERP-Exception",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void mntmClassicClick(ActionEvent e){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			this.pack();
		}catch(Exception exp){
			LOGGER.error(exp);
			JOptionPane.showMessageDialog(this, exp.toString(),"Leamon-ERP-Exception",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void mntmMortisClick(ActionEvent e){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			this.pack();
		}catch(Exception exp){
			LOGGER.error(exp);
			JOptionPane.showMessageDialog(this, exp.toString(),"Leamon-ERP-Exception",JOptionPane.ERROR_MESSAGE);
		}
	}
	private void mntmNimusClick(ActionEvent e){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			this.pack();
		}catch(Exception exp){
			LOGGER.error(exp);
			JOptionPane.showMessageDialog(this, exp.toString(),"Leamon-ERP-Exception",JOptionPane.ERROR_MESSAGE);
		}
	}
	private void mntmDefaultClick(ActionEvent e){
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			this.pack();
		}catch(Exception exp){
			LOGGER.error(exp);
			JOptionPane.showMessageDialog(this, exp.toString(),"Leamon-ERP-Exception",JOptionPane.ERROR_MESSAGE);
		}
	}

	public static String getPropertyValue(String propertyName){
		
		if(Strings.isNullOrEmpty(propertyName)){
			return propertyName;
		}
		
		
		try{
			if(!isleamonPropertiesLoaded){
				leamonProperties = LeamonPropertyDaoImpl.getInstance().getItemList();
				isleamonPropertiesLoaded = true;
			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		
		Optional<LeamonProperty> leamonPropertyOp = leamonProperties.stream()
				.filter(e -> propertyName.equals(e.getPropertyname()))
				.findFirst();
		if(leamonPropertyOp.isPresent()){
			return leamonPropertyOp.get().getPropertyvalue();
		}else{
			return LeamonERPConstants.EMPTY_STR;
		}
	}

	public void framCreator(){
		SwingUtilities.invokeLater(() -> {
			accountInfoUI = new AccountInfoUI();
			//desktopPane.add(accountInfoUI);
			accountInfoUI.setVisible(true);
			//accountInfoUI.setExtendedState(JInternalFrame.);
		});
	}
}
