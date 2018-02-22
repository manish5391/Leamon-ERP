package leamon.erp.report.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import leamon.erp.db.CompanyInfoDaoImpl;
import leamon.erp.model.CompanyInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.ui.InvoiceUI;
import leamon.erp.ui.LeamonERP;
import leamon.erp.ui.model.TableInvoiceModel;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.LeamonERPConstants;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class InvoicePrintFactory {
	
	private InvoiceUI invoiceUI;
	
	static final Logger LOGGER = Logger.getLogger(InvoicePrintFactory.class);
	
	public InvoicePrintFactory(InvoiceUI invoiceUI){
		this.invoiceUI = invoiceUI;
	}
	
	public void print(){
		
		if(invoiceUI == null){
			JOptionPane.showMessageDialog(LeamonERP.frame, "Invoice UI Not found", "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		String invoiceNum = invoiceUI.getTextFieldInvoiceNum().getText();
		String invoiceDate = invoiceUI.getDatePickerInvoiceDate().getEditor().getText();
		String billNo = invoiceUI.getTextFieldBillNo().getText();
		String partyName =   invoiceUI.getTextFieldPartyName().getText();
		String partynickName =   invoiceUI.getTextFieldPartynickName().getText();
		String partyCity = invoiceUI.getTextAreaPartyAddress().getText();
		String partyState = invoiceUI.getTextFieldPartyState().getText();
		
		
		String partyTransport = invoiceUI.getTextFieldPartyTransportList().getText();
		String packets = invoiceUI.getTextFieldGoodsPackets1().getText();
		
		
		String total = invoiceUI.getTextFieldTotal().getText();
		String discount = invoiceUI.getTextFieldDiscount().getText();
 		String taxableValue = invoiceUI.getTextFieldTaxableValue().getText();
		String gst = invoiceUI.getTextFieldGstTAX().getText();
		String grandTotal = invoiceUI.getTextFieldGTotal1().getText();
		String billAmt = invoiceUI.getTextFieldBillAmount().getText();
		String packingAmount = invoiceUI.getTextFieldPackingAmount().getText();
		
		total = Strings.isNullOrEmpty(total) ? LeamonERPConstants.EMPTY_STR : total;
		discount = Strings.isNullOrEmpty(discount) ? LeamonERPConstants.EMPTY_STR : discount;
		taxableValue = Strings.isNullOrEmpty(taxableValue) ? LeamonERPConstants.EMPTY_STR : taxableValue;
		gst = Strings.isNullOrEmpty(gst) ? LeamonERPConstants.EMPTY_STR : gst;
		grandTotal = Strings.isNullOrEmpty(grandTotal) ? LeamonERPConstants.EMPTY_STR : grandTotal;
		billAmt = Strings.isNullOrEmpty(billAmt) ? LeamonERPConstants.EMPTY_STR : billAmt;
		packingAmount = Strings.isNullOrEmpty(packingAmount) ? LeamonERPConstants.EMPTY_STR : packingAmount;
		
		List<InvoiceItemInfo> invoiceItemInfos =  ((TableInvoiceModel)invoiceUI.getTableInvoice().getModel()).getInvoiceItemInfos();
		
		CompanyInfo companyInfo = getCompany();
		try{
			companyInfo = CompanyInfoDaoImpl.getInstance().getItemList().get(0);
		}catch(Exception e){
			LOGGER.error(e);
		}
		
		HashMap parameters = new HashMap();
		parameters.put("companyName", companyInfo.getCompanyName());
		parameters.put("companyAddress", companyInfo.getCompanyAddress());
		parameters.put("companyMobile", companyInfo.getCompanyMobile());
		parameters.put("companyState", companyInfo.getCompanyState());
		
		parameters.put("partyName", partyName);
		parameters.put("partynickName", partynickName);
		parameters.put("partyCity", partyCity);
		parameters.put("partyTransport", partyTransport);
		parameters.put("billNo", billNo);
		parameters.put("invoiceDate", invoiceDate);
		
		parameters.put("invoiceNum", invoiceNum);
		
		/*Added from 3.5*/
		try{

			BigDecimal bd = new BigDecimal(billAmt);
			bd = bd.setScale(2,RoundingMode.HALF_UP);

			DecimalFormat df = new DecimalFormat("#.00");
			billAmt = df.format(bd.doubleValue());
		}catch(Exception e){ LOGGER.error(e); }
		parameters.put("billAmount", billAmt);
		
		parameters.put("packingAmount", packingAmount);
		
		try{

			BigDecimal bd = new BigDecimal(grandTotal);
			bd = bd.setScale(2,RoundingMode.HALF_UP);

			DecimalFormat df = new DecimalFormat("#.00");
			grandTotal = df.format(bd.doubleValue());
		}catch(Exception e){ LOGGER.error(e); }
		parameters.put("grandTotal", grandTotal);

		parameters.put("packets", packets);
		parameters.put("total", total);
		parameters.put("td", discount);
		
		/*added from 3.5*/
		try{

			BigDecimal bd = new BigDecimal(gst);
			bd = bd.setScale(2,RoundingMode.HALF_UP);

			DecimalFormat df = new DecimalFormat("#.00");
			gst = df.format(bd.doubleValue());
		}catch(Exception e){ LOGGER.error(e); }
		parameters.put("gst", gst);
		
		DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
		JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
		    "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
		
		String reportPath = "";
		if(Strings.isNullOrEmpty(discount) || discount.equals(".00") || discount.equals("0.0")){
			reportPath = LeamonERP.getPropertyValue(ERPEnum.REPORTWITHOUTD.name());
		}else{
			reportPath = LeamonERP.getPropertyValue(ERPEnum.REPORTTD.name());
		}
		try (InputStream  compiledInvoiceReport = new FileInputStream(reportPath)){
			//compiledInvoiceReport = new FileInputStream(new File(this.getClass().getClassLoader().getResource("report/Blank_A5_invoice.jrxml").getPath().toString()));
			//compiledInvoiceReport = new FileInputStream(LeamonERP.rptInvoiceReportPath);
			
		
			JasperDesign jasperDesign = JRXmlLoader.load(compiledInvoiceReport);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(invoiceItemInfos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			//JasperPrint jasperPrint = JasperFillManager.fillReport(compiledInvoiceReport, parameters, beanColDataSource);
			
			JRViewer jrViewer = new JRViewer(jasperPrint);
			
			JasperViewer jasperViewer = new JasperViewer(jasperPrint);
			
			JInternalFrame frm = new JInternalFrame("Leamon-Invoice Report",true,true,true);
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
	
	public static Collection getInvoiceList(){
		Vector vector = new Vector(); 
		
		List<InvoiceItemInfo> items = new ArrayList<InvoiceItemInfo>();
		InvoiceItemInfo invoiceItemInfo = InvoiceItemInfo.builder()
				.amount("1000")
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.description("Brass Pull Handle")
				.id(1)
				.invoiceID(1)
				.lastUpdated(new Timestamp(System.currentTimeMillis()))
				.isEnable(Boolean.TRUE)
				.qty("30")
				.rate("100")
				.size("4\"")
				.sno("1")
				.stockItemId(1)
				.td("2")
				.unit("Pcs.")
				.build();
		items .add(invoiceItemInfo);
		
		InvoiceInfo info =InvoiceInfo.builder().billAmount("1000")
				.gstValue("500")
				.id(1)
				.invoicDate("sun 5 Aug")
				.invoicNum("2")
				.isEnable(Boolean.TRUE)
				.partyinfoID(1)
				.pktNumber("7")
				.transport("PLJ")
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastUpdated(new Timestamp(System.currentTimeMillis()))
				.orderBookedBy("Self")
				.items(items)
				.build();
		vector.add(info);
		return vector;
	}
	
	
	public static CompanyInfo getCompany(){
		//List companyList = new ArrayList();
		
		CompanyInfo companyInfo = CompanyInfo.builder().companyAccountNo("ABC12345")
				.companyAddress("Aligarh City")
				.companyBankName("PNB")
				.companyBillPrefix("DJ/17-18")
				//.companyBranch("ALIGARH")
				.companyName("Deepak Jha Comapny")
				.companyMobile("8364758987")
				.companyState("Uttar Pradesh")
				.build();
		
		//companyList.add(companyInfo);
		
		return companyInfo;
	}
	
	
	public void printRpt(){
		String invoiceNum = invoiceUI.getTextFieldInvoiceNum().getText();
		String invoiceDate = invoiceUI.getDatePickerInvoiceDate().getEditor().getText();
		String partyName =   invoiceUI.getTextFieldPartyName().getText();
		String partyCity = invoiceUI.getTextAreaPartyAddress().getText();
		String partyState = invoiceUI.getTextFieldPartyState().getText();
		
		String partyTransport = invoiceUI.getTextFieldPartyTransportList().getText();
		String packets = invoiceUI.getTextFieldGoodsPackets1().getText();
		
		
		String total = invoiceUI.getTextFieldTotal().getText();
		String discount = invoiceUI.getTextFieldDiscount().getText();
 		String taxableValue = invoiceUI.getTextFieldTaxableValue().getText();
		String gst = invoiceUI.getTextFieldGstTAX().getText();
		String grandTotal = invoiceUI.getTextFieldGTotal1().getText();
		String billAmt = invoiceUI.getTextFieldBillAmount().getText();
		String packingAmount = invoiceUI.getTextFieldPackingAmount().getText();
		
		List<InvoiceItemInfo> invoiceItemInfos =  ((TableInvoiceModel)invoiceUI.getTableInvoice().getModel()).getInvoiceItemInfos();
		
		CompanyInfo companyInfo = getCompany();
		
		HashMap parameters = new HashMap();
		parameters.put("companyName", companyInfo.getCompanyName());
		parameters.put("companyAddress", companyInfo.getCompanyAddress());
		parameters.put("companyMobile", companyInfo.getCompanyMobile());
		parameters.put("companyState", companyInfo.getCompanyState());
		
		parameters.put("partyName", partyName);
		parameters.put("partyCity", partyCity);
		parameters.put("partyTransport", partyTransport);
		
		parameters.put("invoiceNum", invoiceNum);
		parameters.put("billAmount", billAmt);
		parameters.put("packingAmount", packingAmount);
		parameters.put("grandTotal", grandTotal);
		
		parameters.put("total", total);
		parameters.put("td", taxableValue);
		parameters.put("gst", gst);
		
		String sourceFileName = this.getClass().getClassLoader().getResource("report/Blank_A4.jasper").getPath();
		String printFileName = null;
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(invoiceItemInfos);
		
		 try {
	    	   printFileName = JasperFillManager.fillReportToFile( 
	            sourceFileName, parameters, beanColDataSource);
	         if(printFileName != null){
	            JasperPrintManager.printReport( printFileName, true);
	         }
	      } catch (JRException e) {
	         e.printStackTrace();
	      }
	}
	
	public void fill() throws JRException{
		JasperPrint jasperPrint = getJasperPrint();
		JRSaver.saveObject(jasperPrint, "");
	}
	
	public JasperPrint getJasperPrint(){
		if(invoiceUI == null){
			JOptionPane.showMessageDialog(LeamonERP.frame, "Invoice UI Not found", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		
		String invoiceNum = invoiceUI.getTextFieldInvoiceNum().getText();
		String invoiceDate = invoiceUI.getDatePickerInvoiceDate().getEditor().getText();
		String partyName =   invoiceUI.getTextFieldPartyName().getText();
		String partyCity = invoiceUI.getTextAreaPartyAddress().getText();
		String partyState = invoiceUI.getTextFieldPartyState().getText();
		
		String partyTransport = invoiceUI.getTextFieldPartyTransportList().getText();
		String packets = invoiceUI.getTextFieldGoodsPackets1().getText();
		
		
		String total = invoiceUI.getTextFieldTotal().getText();
		String discount = invoiceUI.getTextFieldDiscount().getText();
 		String taxableValue = invoiceUI.getTextFieldTaxableValue().getText();
		String gst = invoiceUI.getTextFieldGstTAX().getText();
		String grandTotal = invoiceUI.getTextFieldGTotal1().getText();
		String billAmt = invoiceUI.getTextFieldBillAmount().getText();
		String packingAmount = invoiceUI.getTextFieldPackingAmount().getText();
		
		List<InvoiceItemInfo> invoiceItemInfos =  ((TableInvoiceModel)invoiceUI.getTableInvoice().getModel()).getInvoiceItemInfos();
		
		CompanyInfo companyInfo = getCompany();
		
		HashMap parameters = new HashMap();
		parameters.put("companyName", companyInfo.getCompanyName());
		parameters.put("companyAddress", companyInfo.getCompanyAddress());
		parameters.put("companyMobile", companyInfo.getCompanyMobile());
		parameters.put("companyState", companyInfo.getCompanyState());
		
		parameters.put("partyName", partyName);
		parameters.put("partyCity", partyCity);
		parameters.put("partyTransport", partyTransport);
		
		parameters.put("invoiceNum", invoiceNum);
		parameters.put("billAmount", billAmt);
		parameters.put("packingAmount", packingAmount);
		parameters.put("grandTotal", grandTotal);
		parameters.put("packets", packets);
		
		parameters.put("total", total);
		parameters.put("td", discount);
		parameters.put("gst", gst);
		
		DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
		JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory",
		    "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");
		String reportPath = "";
		if(Strings.isNullOrEmpty(discount)){
			reportPath = LeamonERP.getPropertyValue(ERPEnum.REPORTWITHOUTD.name());
		}else{
			reportPath = LeamonERP.getPropertyValue(ERPEnum.REPORTTD.name());
		}
		LOGGER.info("Report Path : "+ reportPath);
		try (InputStream compiledInvoiceReport = new FileInputStream(reportPath)){
			
			JasperDesign jasperDesign = JRXmlLoader.load(compiledInvoiceReport);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(invoiceItemInfos);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			return jasperPrint;
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
}
