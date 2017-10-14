package leamon.erp.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import leamon.erp.db.StockDaoImpl;

/**
 * Leamon ERP
 * 
 * @Copyright All rights reserved 
 * 
 * @author Manish Kumar Mishra
 * @version 1.0
 * @date May 17,2017
 * 
 */
public class LeamonERP_2 extends JFrame{
	
	static final Logger LOGGER = Logger.getLogger(LeamonERP.class);
	
	public static final JDesktopPane desktopPane = new JDesktopPane();
	
	public static AccountInfoUI accountInfoUI;
	public static StockItemUI stockItemManager;
	public static StockItemListManager stockItemList;
	public static AccountListManager accountListManager;
	
	public static InventoryUIManager inventoryUIManager;
	public static InventoryUI inventoryUI;
	
	public static  List<String> cityCache;
	public static  List<String> countryCache;
	public static  List<String> stateCache;
	
	public LeamonERP_2() throws Exception{
		this.setTitle("Leamon ERP System");
		
		/*Stock Items IntelliSense  ready*/
		StockDaoImpl.getInstance().prepareStockIntelliSense();
		loadAddressData();
		
		stockItemManager = new StockItemUI();
		desktopPane.add(stockItemManager);
		stockItemManager.setVisible(true);
		
		stockItemList = new StockItemListManager();
		desktopPane.add(stockItemList);
		stockItemList.setVisible(true);
		
		accountListManager = new AccountListManager();
		desktopPane.add(accountListManager);
		accountListManager.setVisible(true);
		
		/*accountInfoUI = new AccountInfoUI();
		desktopPane.add(accountInfoUI);
		accountInfoUI.setVisible(true);*/
		framCreator();
		
		inventoryUI = new InventoryUI();
		desktopPane.add(inventoryUI);
		inventoryUI.setVisible(true);
		
		/*inventoryUIManager = new InventoryUIManager();
		desktopPane.add(inventoryUIManager);
		inventoryUIManager.setVisible(true);*/
		
		Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(sc);

		Dimension minimumSize = new Dimension(200, 200);
		this.setMinimumSize(minimumSize);
		
		setExtendedState(MAXIMIZED_BOTH);
		
		this.add(desktopPane, BorderLayout.CENTER);
	}
	
	
	private void loadAddressData(){
		try(InputStream cityIn = this.getClass().getClassLoader().getResourceAsStream("citylist.txt")) {
			int availableitems= cityIn.available();
			byte[] cities= new byte[availableitems];
			cityIn.read(cities);
			String cityStr = new String(cities);
			String citydata [] = cityStr.split("\\r?\\n");
			cityCache = new ArrayList<String>();
			for(String val : citydata ){
				cityCache.add(val);
			}
			LOGGER.info("city list "+cityCache);
		} catch (IOException e) {
			LOGGER.error("failed to load city "+e);
		}
	
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
		
		try(InputStream stateList = this.getClass().getClassLoader().getResourceAsStream("statelist.txt")) {
			int availableitems= stateList .available();
			byte[] states = new byte[availableitems];
			stateList.read(states);
			String stateStr = new String(states);
			String stateData [] = stateStr.split("\\r?\\n");
			stateCache = new ArrayList<String>();
			for(String val : stateData){
				stateCache.add(val);
			}
			LOGGER.info("state list "+stateCache);
		} catch (IOException e) {
			LOGGER.error("failed to load country "+e);
		}
	}
	
	
	public void framCreator(){
		SwingUtilities.invokeLater(() -> {
			accountInfoUI = new AccountInfoUI();
			desktopPane.add(accountInfoUI);
			accountInfoUI.setVisible(true);
			//accountInfoUI.setExtendedState(JInternalFrame.);
		});
	}
}
