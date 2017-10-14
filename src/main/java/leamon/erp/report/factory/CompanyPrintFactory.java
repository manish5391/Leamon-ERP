package leamon.erp.report.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JFrame;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.CompanyInfo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

public class CompanyPrintFactory {

	public static CompanyInfo getCompany(){
		//List companyList = new ArrayList();
		
		CompanyInfo companyInfo = CompanyInfo.builder().companyAccountNo("ABC12345")
				.companyAddress("Aligarh City")
				.companyBankName("PNB")
				.companyBillPrefix("DJ/17-18")
				.companyBankBranch("ALIGARH")
				.companyName("Deepak Jha Comapny")
				.companyMobile("8364758987")
				.companyState("Uttar Pradesh")
				.build();
		
		//companyList.add(companyInfo);
		
		return companyInfo;
	}
	
	public static void main(String[] args) throws FileNotFoundException, JRException {
		
		String rptNm ="C:\\Users\\mmishra\\Documents\\workspace-sts-3.8.4.RELEASE\\Leamon-ERP\\src\\main\\report\\RptInvoice.jrxml";
		InputStream inputStream = new FileInputStream("C:\\Users\\mmishra\\Documents\\workspace-sts-3.8.4.RELEASE\\Leamon-ERP\\src\\main\\report\\RptInvoice.jrxml");
		
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

		String printFileName = null;
		HashMap parameters = new HashMap();
		Collection companyList = InvoicePrintFactory.getInvoiceList();
		
		CompanyInfo companyInfo = getCompany();
		
		AccountInfo partyInfo = AccountInfo.builder()
				.name("National Lock House")
				.city("Vijaywada")
				.transport("TLS")
				.build();
		
		parameters.put("companyName", companyInfo.getCompanyName());
		parameters.put("companyAddress", companyInfo.getCompanyAddress());
		parameters.put("companyMobile", companyInfo.getCompanyMobile());
		parameters.put("companyState", companyInfo.getCompanyState());
		
		parameters.put("partyName", partyInfo.getName());
		parameters.put("partyCity", partyInfo.getCity());
		parameters.put("partyTransport", partyInfo.getTransport());
		
		
		//companyList.add(partyInfo);
		
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(companyList);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
		//JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/test_jasper.pdf");
		String rptCompiled = "C:\\Users\\mmishra\\Documents\\workspace-sts-3.8.4.RELEASE\\Leamon-ERP\\src\\main\\report\\RptInvoice.jasper";
		 /*try {
	    	   printFileName = JasperFillManager.fillReportToFile(rptCompiled, parameters, beanColDataSource);
	    	   
	         if(printFileName != null){
	        	 JasperPrintManager.printReport( jasperPrint,true);
	          //  JasperPrintManager.printReport( printFileName, true);
	         }
	      } catch (JRException e) {
	         e.printStackTrace();
	      }*/
		 
		
		JRViewer jrViewer = new JRViewer(jasperPrint);
		
		JFrame frm = new JFrame();
		frm.getContentPane().add(jrViewer);
		frm.setSize(500, 500);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setVisible(true);
		
		/*String pfile = JasperFillManager.fillReportToFile(rptCompiled, parameters, beanColDataSource);
		JasperPrintManager.printReport( pfile, true);*/
	}
}
