package leamon.erp.report.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import leamon.erp.db.CompanyInfoDaoImpl;
import leamon.erp.model.CompanyInfo;
import leamon.erp.model.StockItem;
import leamon.erp.report.print.model.StockItemPrintInfo;
import leamon.erp.ui.LeamonERP;
import leamon.erp.ui.StockItemListManager;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.LeamonERPConstants;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class StockItemListFactory {
	private StockItemListManager stockItemListManager;

	public static int sno = 0;

	static final Logger LOGGER = Logger.getLogger(StockItemListManager.class);

	public StockItemListFactory(StockItemListManager stockItemListManager) {
		this.stockItemListManager = stockItemListManager;
	}

	public void print() {

		if (stockItemListManager == null) {
			JOptionPane.showMessageDialog(LeamonERP.frame, "stockItemListManager UI Not found", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<StockItem> stockItem = ((TableStockListItemModel) stockItemListManager.getTblStockList().getModel())
				.getStockItems();

		List<StockItemPrintInfo> stockItemPrintInfoList = new ArrayList<>();

		for (StockItem stock : stockItem) {
			StockItemPrintInfo stockItemPrintInfo = new StockItemPrintInfo();
			stockItemPrintInfo.setSno(++StockItemListFactory.sno);
			stockItemPrintInfo.setName(stock.getName());
			stockItemPrintInfo.setSize(stock.getSize());
			if (null == stock.getStockItemQuantity()) {
				stockItemPrintInfo.setQuantity(LeamonERPConstants.EMPTY_STR);
			} else {
				stockItemPrintInfo.setQuantity(stock.getStockItemQuantity().getQuantity());
			}

			stockItemPrintInfo.setSaleunit(stock.getSaleunit());
			stockItemPrintInfoList.add(stockItemPrintInfo);
		}

		CompanyInfo companyInfo = getCompany();
		try {
			companyInfo = CompanyInfoDaoImpl.getInstance().getItemList().get(0);
		} catch (Exception e) {
			LOGGER.error(e);
		}

		HashMap parameters = new HashMap();
		parameters.put("companyName", companyInfo.getCompanyName());
		parameters.put("companyAddress", companyInfo.getCompanyAddress());
		parameters.put("companyState", companyInfo.getCompanyState());

		DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
		JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
				"net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");

		String reportPath = LeamonERP.getPropertyValue(ERPEnum.STOCKITEMLIST.name());

		try (InputStream compiledInvoiceReport = new FileInputStream(reportPath)) {
			JasperDesign jasperDesign = JRXmlLoader.load(compiledInvoiceReport);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(stockItemPrintInfoList);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			JRViewer jrViewer = new JRViewer(jasperPrint);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint);
			JInternalFrame frm = new JInternalFrame("Leamon-Invoice Report", true, true, true);
			frm.getContentPane().add(jrViewer);
			frm.setSize(500, 500);
			frm.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
			LeamonERP.desktopPane.add(frm);
			frm.setVisible(true);

		} catch (JRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static CompanyInfo getCompany() {
		CompanyInfo companyInfo = CompanyInfo.builder().companyAccountNo("ABC12345").companyAddress("Aligarh City")
				.companyBankName("PNB").companyBillPrefix("DJ/17-18").companyName("Deepak Jha Comapny")
				.companyMobile("8364758987").companyState("Uttar Pradesh").build();
		return companyInfo;
	}

}
