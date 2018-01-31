package leamon.erp.util;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import com.google.common.base.Strings;

import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;

public class LeamonUtil {
	
	private static final Logger LOGGER = Logger.getLogger(LeamonUtil.class);
	private static final String CLASS_NAME = "LeamonUtil";
	
	public static void prepareAutoCompleterCombo(JComboBox combo, List<String> data){
		combo.setEditable(Boolean.TRUE);
		combo.setModel(new ListComboBoxModel<String>(data));
		AutoCompleteDecorator.decorate(combo);
	}
	
	public static void setSelected(JTextField txtFld){
		if(txtFld != null && !Strings.isNullOrEmpty(txtFld.getText())){
			txtFld.selectAll();
		}
	}
	
	public static void clickToSelect(ActionEvent e){
		if(e.getSource() instanceof JTextField){
			JTextField txtFld = (JTextField) e.getSource() ;
			setSelected(txtFld);
		}
	}
	
	public static String calcPackingAmount(InvoiceInfo invoiceInfo) throws Exception{
		final String METHOD_NAME = "calcPackingAmount";
		String billAmtVal = invoiceInfo.getBillAmount();
		String grandTotal= calcGrandTotal(invoiceInfo);

		double billAmt = 0;
		double grandTotalAmt = 0;

		try{
			grandTotalAmt = Double.parseDouble(grandTotal);
		}catch(Exception exp){
			LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+exp);
		}

		try{
			billAmt = Double.parseDouble(billAmtVal);
		}catch(Exception exp){
			LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+exp);
		}

		double packingAmt = grandTotalAmt - billAmt;
		return getRoundff(packingAmt);
	}
	
	public static String calcInvoiceItemsTotal(InvoiceInfo invoiceInfo)throws Exception{
		List<InvoiceItemInfo> invoiceItemInfos = invoiceInfo.getItems();
		
		double sum = 0;
		for(InvoiceItemInfo info : invoiceItemInfos ){
			 BigDecimal amt = new BigDecimal(info.getAmount());
			 sum = sum + amt.doubleValue();
		}
		return getRoundff(sum);
	}
	
	public static String calcInvoiceItemsDiscount(InvoiceInfo invoiceInfo) throws Exception{
		final String METHOD_NAME = "calcInvoiceItemsDiscount";
		
		List<InvoiceItemInfo> invoiceItemInfos = invoiceInfo.getItems();
		double totalDiscount = 0;
		
		for(InvoiceItemInfo info : invoiceItemInfos){
			String tdValue = info.getTd();
			String amountValue = info.getAmount();
			double td = 0;
			double amount = 0;
			try{
				td = Double.parseDouble(tdValue);
			}catch(Exception exp){
				LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+exp);
			}
			
			try{
				amount = Double.parseDouble(amountValue);
			}catch(Exception exp){
				LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+exp);
			}
			
			double dis = (amount * td)/100;
			totalDiscount = totalDiscount + dis; 
		}
		return getRoundff(totalDiscount);
	}//end discount calculation
	
	/**Calculates Taxable Amount  ( TextFieldTotal - TextFieldDiscount = TaxableValue )*/
	public static  String calcTaxableValue(InvoiceInfo invoiceInfo) throws Exception{
		final String METHOD_NAME = "calcInvoiceItemsDiscount";
		
		double totalAmt = 0;
		String totalVal = calcInvoiceItemsTotal(invoiceInfo);
		try{
		totalAmt = Double.parseDouble(totalVal);
		}catch(Exception exp){
			LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+exp);
		}
		
		double disAmt = 0;
		String disAmtVal = calcInvoiceItemsDiscount(invoiceInfo);
		try{
		disAmt = Double.parseDouble(disAmtVal);
		}catch(Exception exp){
			LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+exp);
		}
		
		double taxableValue = totalAmt - disAmt;
		return (getRoundff(taxableValue));
	}// set taxable value
	
	/** Calculates Grand Total ( ((TextFieldTaxableValue * TextFieldTAX)/100) + TextFieldTaxableValue = TextFieldGtotal2,TextFieldGtotal1 )*/
	public static String calcGrandTotal(InvoiceInfo invoiceInfo) throws Exception{
		final String METHOD_NAME = "calcGrandTotal";
		String taxValue  = invoiceInfo.getGstValue();
		double taxVal = 0;
		try{
			taxVal = Double.parseDouble(taxValue);
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		String taxableval = calcTaxableValue(invoiceInfo);
		double taxableAmt = 0;
		try{
		taxableAmt = Double.parseDouble(taxableval);
		}catch(Exception exp){
			LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] Error : "+exp);
		}
		
		double grandTotal = taxableAmt+taxVal;
		
		return getRoundff(grandTotal);
	}
	
	public static String getRoundff(double value) throws Exception{
		BigDecimal bd = new BigDecimal(value);
		bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		
			BigDecimal bdd = new BigDecimal(value);
			bdd = bdd.setScale(2,RoundingMode.HALF_UP);

			DecimalFormat df = new DecimalFormat("#.00");
			String grandTotal = df.format(bdd.doubleValue());
			return grandTotal;
	}
}
