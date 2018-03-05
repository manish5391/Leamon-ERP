package leamon.erp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXTaskPane;

import leamon.erp.db.StateCityDaoImpl;
import leamon.erp.db.StockDaoImpl;
import leamon.erp.model.StateCityInfo;
import leamon.erp.ui.custom.BGImagePanel;
import leamon.erp.util.LeamonERPConstants;

public class LeamonERP3 extends JFrame {

	private JPanel contentPane;

	private static final Logger LOGGER = Logger.getLogger(LeamonERP3.class);
	public static JDesktopPane desktopPane  = new JDesktopPane();

	public static AccountInfoUI accountInfoUI;
	public static StockItemUI stockItemManager;
	public static StockItemListManager stockItemList;
	public static AccountListManager accountListManager;

	public static InventoryUIManager inventoryUIManager;
	public static InventoryUI inventoryUI;
//	public static GrandTotalUI grandTotalUI;
	public static CompanyUI companyUI;
	public static InvoiceSearchUI invoiceSearchUI;

	public static  List<String> cityCache;
	public static  List<String> countryCache;
	public static  List<String> stateCache;
	
	public static List<StateCityInfo> stateCityInfos;
	
	public static InvoiceUI invoiceUI;
	
	public static LeamonERP3 frame;

	public static String rptInvoiceReportPath;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		if(args == null ){
			LOGGER.error("Please supply argument");
			return;
		}
		
		if(args.length > 0 ){
			rptInvoiceReportPath = args[0];
			if(rptInvoiceReportPath == null){
				LOGGER.error("Report not found. "+rptInvoiceReportPath);
			}else{
				LOGGER.info("Report Path : "+rptInvoiceReportPath);
			}
		}else{
			LOGGER.error("No Arg found");
			return;
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LeamonERP3();
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
	public LeamonERP3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(sc);
		setUndecorated(true);
		JRootPane root = getRootPane( );
		root.setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
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
		desktopPane.setBackground(new Color(238, 232, 170));
		scrollPaneContent.setViewportView(desktopPane);

		JXTaskPane taskPaneInventory = new JXTaskPane();
		
		taskPaneInventory.setTitle("Inventory");
		taskPaneInventory.setBounds(62, 89, 224, 223);
		//taskPaneInventory.add(createTextAction());
		desktopPane.add(taskPaneInventory);
		taskPaneInventory.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelInVoiceBGImage = new BGImagePanel(this.getClass().getClassLoader().getResource(LeamonERPConstants.BILLING).getPath());
		taskPaneInventory.getContentPane().add(panelInVoiceBGImage, BorderLayout.CENTER);
		panelInVoiceBGImage.setLayout(null);

		JXHyperlink hyperlinkInventory = new JXHyperlink();
		hyperlinkInventory.setHorizontalAlignment(SwingConstants.CENTER);
		hyperlinkInventory.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkInventory.setText("Inventory");
		hyperlinkInventory.setBounds(24, 11, 168, 33);
		//taskPaneInventory.getContentPane().add(hyperlinkInventory);
		
		JXHyperlink hyperlinkInvoice = new JXHyperlink();
		hyperlinkInvoice.addActionListener(e -> hyperlinkInvoiceClick(e));
		hyperlinkInvoice.setText("Invoice");
		hyperlinkInvoice.setHorizontalAlignment(SwingConstants.CENTER);
		hyperlinkInvoice.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkInvoice.setBounds(24, 55, 168, 33);
		
		//taskPaneInventory.getContentPane().add(hyperlinkInvoice);
		hyperlinkInventory.addActionListener(e -> hyperlinkInventoryClick(e));

		JXTaskPane taskPaneStockItems = new JXTaskPane();
		taskPaneStockItems.setTitle("Stock Items");
		taskPaneStockItems.setBounds(419, 89, 224, 223);
		desktopPane.add(taskPaneStockItems);
		taskPaneStockItems.getContentPane().setLayout(null);

		JXHyperlink hprlnkStockItemAddNew = new JXHyperlink();
		hprlnkStockItemAddNew.setHorizontalAlignment(SwingConstants.CENTER);
		hprlnkStockItemAddNew.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemAddNew.setText("Add New");
		hprlnkStockItemAddNew.setBounds(24, 11, 165, 27);
		taskPaneStockItems.getContentPane().add(hprlnkStockItemAddNew);
		hprlnkStockItemAddNew.addActionListener(e -> hprlnkStockItemAddNewClick(e));

		JXHyperlink hprlnkStockItemDelete = new JXHyperlink();
		hprlnkStockItemDelete.addActionListener(e -> hprlnkStockItemDeleteClick(e));
		hprlnkStockItemDelete.setText("Delete");
		hprlnkStockItemDelete.setHorizontalAlignment(SwingConstants.CENTER);
		hprlnkStockItemDelete.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemDelete.setBounds(34, 49, 165, 27);
		taskPaneStockItems.getContentPane().add(hprlnkStockItemDelete);

		JXHyperlink hprlnkStockItemEdit = new JXHyperlink();
		hprlnkStockItemEdit.setText("Edit");
		hprlnkStockItemEdit.setHorizontalAlignment(SwingConstants.CENTER);
		hprlnkStockItemEdit.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemEdit.setBounds(24, 87, 165, 27);
		taskPaneStockItems.getContentPane().add(hprlnkStockItemEdit);
		hprlnkStockItemEdit.addActionListener(e -> hprlnkStockItemEditClick(e));

		JXHyperlink hprlnkStockItemSearch = new JXHyperlink();
		hprlnkStockItemSearch.setText("Search");
		hprlnkStockItemSearch.setHorizontalAlignment(SwingConstants.CENTER);
		hprlnkStockItemSearch.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkStockItemSearch.setBounds(24, 125, 165, 27);
		taskPaneStockItems.getContentPane().add(hprlnkStockItemSearch);
		hprlnkStockItemSearch.addActionListener(e -> hprlnkStockItemSearchClick(e));

		JXTaskPane taskPane_2 = new JXTaskPane();
		
		taskPane_2.setTitle("Company Master");
		taskPane_2.setBounds(713, 89, 224, 223);
		desktopPane.add(taskPane_2);
		taskPane_2.getContentPane().setLayout(null);
		
		JXHyperlink hprlnkCompany = new JXHyperlink();
		hprlnkCompany.addActionListener(e -> hprlnkCompanyClick(e));
		hprlnkCompany.setText("Company Info");
		hprlnkCompany.setHorizontalAlignment(SwingConstants.CENTER);
		hprlnkCompany.setFont(new Font("Courier New", Font.BOLD, 20));
		hprlnkCompany.setBounds(10, 11, 168, 33);
		taskPane_2.getContentPane().add(hprlnkCompany);

		JXTaskPane taskPaneAccounts = new JXTaskPane();
		taskPaneAccounts.setTitle("Party Master");
		taskPaneAccounts.setBounds(62, 420, 224, 223);
		desktopPane.add(taskPaneAccounts);
		taskPaneAccounts.getContentPane().setLayout(null);

		JXHyperlink hyperlinkAccountAddNew = new JXHyperlink();
		hyperlinkAccountAddNew.setText("Add New");
		hyperlinkAccountAddNew.setHorizontalAlignment(SwingConstants.CENTER);
		hyperlinkAccountAddNew.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountAddNew.setBounds(24, 11, 165, 27);
		taskPaneAccounts.getContentPane().add(hyperlinkAccountAddNew);
		hyperlinkAccountAddNew.addActionListener(e -> hyperlinkAccountAddNewClick(e));

		JXHyperlink hyperlinkAccountDelete = new JXHyperlink();
		hyperlinkAccountDelete.setText("Delete");
		hyperlinkAccountDelete.setHorizontalAlignment(SwingConstants.CENTER);
		hyperlinkAccountDelete.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountDelete.setBounds(24, 49, 165, 27);
		taskPaneAccounts.getContentPane().add(hyperlinkAccountDelete);
		hyperlinkAccountDelete.addActionListener(e-> hyperlinkAccountDeleteClick(e));

		JXHyperlink hyperlinkAccountEdit = new JXHyperlink();
		hyperlinkAccountEdit.setText("Edit");
		hyperlinkAccountEdit.setHorizontalAlignment(SwingConstants.CENTER);
		hyperlinkAccountEdit.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountEdit.setBounds(24, 87, 165, 27);
		taskPaneAccounts.getContentPane().add(hyperlinkAccountEdit);
		hyperlinkAccountEdit.addActionListener(e -> hyperlinkAccountEditClick(e));

		JXHyperlink hyperlinkAccountSearch = new JXHyperlink();
		hyperlinkAccountSearch.setText("Search");
		hyperlinkAccountSearch.setHorizontalAlignment(SwingConstants.CENTER);
		hyperlinkAccountSearch.setFont(new Font("Courier New", Font.BOLD, 20));
		hyperlinkAccountSearch.setBounds(24, 125, 165, 27);
		taskPaneAccounts.getContentPane().add(hyperlinkAccountSearch);
		hyperlinkAccountSearch.addActionListener(e ->  hyperlinkAccountSearchClick(e));

		JXTaskPane taskPaneReport = new JXTaskPane();
		taskPaneReport.setTitle("Payment Master");
		taskPaneReport.setBounds(419, 420, 224, 223);
		desktopPane.add(taskPaneReport);

		JXTaskPane taskPane_5 = new JXTaskPane();
		taskPane_5.setTitle("Tarnsaction Master");
		taskPane_5.setBounds(713, 420, 224, 223);
		desktopPane.add(taskPane_5);
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

//		grandTotalUI = new GrandTotalUI();
		
		companyUI = new CompanyUI();
		
		invoiceSearchUI = new InvoiceSearchUI();
		
		/*JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
		internalFrame.setBounds(48, 55, 279, 236);
		desktopPane.add(internalFrame);
		internalFrame.setVisible(true);*/


		/*Stock Items IntelliSense  ready*/
		/*StockDaoImpl.getInstance().prepareStockIntelliSense();
		loadAddressData();*/

		accountInfoUI = new AccountInfoUI();
		invoiceUI = new InvoiceUI();
		/*accountInfoUI = new AccountInfoUI();
		desktopPane.add(accountInfoUI);
		accountInfoUI.setVisible(true);*/
		/*framCreator();*/
	}

	public void hyperlinkAccountEditClick(ActionEvent e) {
		hyperlinkAccountSearchClick(e);
	}

	public void  hyperlinkAccountDeleteClick(ActionEvent e) {
		hyperlinkAccountSearchClick(e);
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
	}

	public void hprlnkStockItemDeleteClick(ActionEvent e){
		hprlnkStockItemAddNewClick(e);
	}

	public void hprlnkStockItemEditClick(ActionEvent e){
		hprlnkStockItemAddNewClick(e);
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
	}
	
	private void loadAddressData(){
		
		if(null == stateCityInfos || stateCityInfos.isEmpty()){
			 LOGGER.error("Failed to load city-state-code-mapping");
			 return;
		 }
		 
		 cityCache = stateCityInfos.stream().map(StateCityInfo::getCity).collect(Collectors.toList());
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
	
//	public static void openGTCalculatr(){
//		if(grandTotalUI.isVisible()){
//			try {
//				grandTotalUI.setSelected(true);
//			} catch (PropertyVetoException e1) {
//				LOGGER.error(e1.toString());
//			}
//			grandTotalUI.moveToFront();
//		}else{
//			desktopPane.add(grandTotalUI);
//			grandTotalUI.setVisible(true);
//			try {
//				grandTotalUI.setSelected(true);
//			} catch (PropertyVetoException e1) {
//				LOGGER.error(e1.toString());
//			}
//			grandTotalUI.moveToFront();
//		}
//	}

	public void framCreator(){
		SwingUtilities.invokeLater(() -> {
			accountInfoUI = new AccountInfoUI();
			//desktopPane.add(accountInfoUI);
			accountInfoUI.setVisible(true);
			//accountInfoUI.setExtendedState(JInternalFrame.);
		});
	}
	
	public void paintImages(JPanel panel, String imageName) throws IOException{
		String path = this.getClass().getClassLoader().getResource(imageName).getPath();
		
		Image backGroundImage = ImageIO.read(new File(path));
		Graphics g = panel.getGraphics();
		panel.getParent().paintComponents(g);
		
		g.drawImage(backGroundImage, 0,0,panel);
	}
}
