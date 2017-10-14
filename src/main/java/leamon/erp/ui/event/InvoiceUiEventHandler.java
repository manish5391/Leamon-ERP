package leamon.erp.ui.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXTextField;

import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.model.StockItem;
import leamon.erp.ui.InvoiceUI;
import leamon.erp.ui.model.TableInvoiceModel;
import leamon.erp.ui.model.TableStockListItemModel;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

@Getter
public class InvoiceUiEventHandler implements KeyListener, ActionListener, MouseListener{

	private static final Logger LOGGER = Logger.getLogger(InvoiceUiEventHandler.class);
	private JXTextField nextComponent;
	private JXButton nextBtnComponent;
	private InvoiceUI invoiceUI; 
	private JTextArea nextTextAreaComponent;
	
	public InvoiceUiEventHandler(JXTextField nextComponent){
		this.nextComponent = nextComponent;
	}

	public InvoiceUiEventHandler(JXTextField nextComponent, InvoiceUI invoiceUI){
		this.nextComponent = nextComponent;
		this.invoiceUI = invoiceUI;
	}
	
	public InvoiceUiEventHandler(JXButton nextBtnComponent, InvoiceUI ui){
		this.nextBtnComponent = nextBtnComponent;
		this.invoiceUI = ui;
	}
	
	public InvoiceUiEventHandler(JTextArea nextTextAreaComponent){
		this.nextTextAreaComponent = nextTextAreaComponent;
	}
	
	public InvoiceUiEventHandler(InvoiceUI ui){
		this.invoiceUI = ui;
	}

	public InvoiceUiEventHandler(){

	}
	
	/*////////////////Key Events////////////////*/
	@Override
	public void keyTyped(KeyEvent e) {
		final int KEY_CODE = e.getKeyCode();
		if(KEY_CODE == KeyEvent.VK_DOWN || KEY_CODE == KeyEvent.VK_KP_DOWN){
			enterKeyTableHandler(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		final int KEY_CODE = e.getKeyCode();
		if(KEY_CODE == KeyEvent.VK_ENTER){
			enterKeyNavigation(e);
			enterKeyTableHandler(e);
		}
		
		if(KEY_CODE == 18 || KEY_CODE==KeyEvent.VK_TAB){
			tabKeyNavigation(e);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		enterKeyTableHandler(e);
	}
	
	
	/*///////////////mouse Events///////////////////////*/
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof JTable){
			mouseClickTableHandler(e);
		}
		/*Double-Click*/
		if(e.getClickCount() == 2){
			if(e.getSource() instanceof JTextField){
				JTextField fld = (JTextField) e.getSource();
				setSubTotalAmountOnClick(fld, invoiceUI);
				setDiscount(invoiceUI);
				setTaxableValue(invoiceUI);
				calcGrandTotal(invoiceUI);
			}
		}
		
		
	}//end mouse click

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void enterKeyNavigation(KeyEvent e){
		if(e.getSource() instanceof JTextField){

			JTextField textField = (JTextField) e.getSource();
			if(textField.getName()!=null){
				
				/*Invoice Item navigation*/
				if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_DESC)){
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_SIZE)){
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_QTY)){
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_UNIT)){
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_RATE)){
					amountCalc();
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_AMOUNT)){
					setAmount(invoiceUI);
					setDiscount(invoiceUI);
					setTaxableValue(invoiceUI);
					calcGrandTotal(invoiceUI);
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_TD)){
					calcTDPercentage(invoiceUI);
					nextBtnComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_TAX)){
					calcGrandTotal(invoiceUI);
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_BILL_AMT)){
					calcPackingAmt(invoiceUI);
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_PACKET1)){
					copyPacket(invoiceUI);
					nextComponent.requestFocus();
				}else if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_PACKET2)){
					copyPacket2(invoiceUI);
					nextComponent.requestFocus();
				}
				else{
					if(nextComponent!=null){
						nextComponent.requestFocus();
					}
					if(nextTextAreaComponent!=null && nextTextAreaComponent.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_ADDRESS)){
						nextTextAreaComponent.requestFocus();
					}
				}
			}else if (e.getSource() instanceof JTextArea){
				
			}
		}// end if jtextfiled
		else if(e.getSource() instanceof JButton){
			JButton btn = (JButton)e.getSource();
			if(btn.getName()!=null){
				if(btn.getName().equals(LeamonERPConstants.BUTTON_INVOICE_ACTION_ADD)){
					if(validateData()){
						invoiceItemTableRocrding();
					}
					clearProductFields();
					nextComponent.requestFocus();
				}
			}
		}//end jbutton
		else if(e.getSource() instanceof JTextArea){
			JTextArea textArea = (JTextArea) e.getSource();
			if(textArea.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_ADDRESS)){
				nextComponent.requestFocus();
			}
		}
	}//end method
	
	public void tabKeyNavigation(KeyEvent e){
		if(e.getSource() instanceof JTextField){
			JTextField textField = (JTextField) e.getSource();
			if(textField.getName()!=null){
				if(textField.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_PACKET1)){
					copyPacket(invoiceUI);
					nextComponent.requestFocus();
				}
			}
		}
	}//end tab key
	
	private boolean validateData(){
		if(invoiceUI == null){
			return false;
		}
		
		if(null == invoiceUI.getTextFieldProductDesc().getText() || invoiceUI.getTextFieldProductDesc().getText().isEmpty()){
			JOptionPane.showMessageDialog(invoiceUI, "Product description can't be blank","Leamon-ERP",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(null == invoiceUI.getTextFieldProductQty().getText() || invoiceUI.getTextFieldProductQty().getText().isEmpty()){
			JOptionPane.showMessageDialog(invoiceUI, "Product quantity can't be blank","Leamon-ERP",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if(null == invoiceUI.getTextFieldProductUnit().getText() || invoiceUI.getTextFieldProductUnit().getText().isEmpty()){
			JOptionPane.showMessageDialog(invoiceUI, "Product unit can't be blank","Leamon-ERP",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(null == invoiceUI.getTextFieldProductRate().getText() || invoiceUI.getTextFieldProductRate().getText().isEmpty()){
			JOptionPane.showMessageDialog(invoiceUI, "Product rate can't be blank","Leamon-ERP",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	private void amountCalc(){
		if(invoiceUI == null){
			return ;
		}
		
		String qtyStr = invoiceUI.getTextFieldProductQty().getText();
		String rateStr = invoiceUI.getTextFieldProductRate().getText();
		
		if(null == qtyStr || qtyStr.isEmpty()){
			return ;
		}
		
		if(null == rateStr || rateStr.isEmpty() ){
			invoiceUI.getTextFieldProductAmount().setText("0.0");
			return;
		}
		
		Integer qtyValue  = 0;
		try{
			qtyValue = Integer.valueOf(qtyStr);
		}catch(Exception exp){
			invoiceUI.getTextFieldProductQty().setText("0");
		}
		
		Double rateValue = 0.0;
		try{
			rateValue = Double.valueOf(rateStr);
		}catch(Exception exp){
			invoiceUI.getTextFieldProductRate().setText("0.0");
		}
		
		Double amount = qtyValue * rateValue;
		
		
		invoiceUI.getTextFieldProductAmount().setText(String.valueOf(getRoundff(amount)));
		setTtoal(invoiceUI.getTableInvoice());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton btn   = (JButton) e.getSource();
			
			if( null != btn.getName() && btn.getName().equals(LeamonERPConstants.BUTTON_INVOICE_ACTION_ADD)){
				if(validateData()){
					invoiceItemTableRocrding();
				}
			}
		}//jbutton event
	}//end
	
	public void invoiceItemTableRocrding(){
		
		String productDescValue 	=   invoiceUI.getTextFieldProductDesc().getText();
		String productSize			=	invoiceUI.getTextFieldProductSize().getText();
		String productQty			=	invoiceUI.getTextFieldProductQty().getText();
		String productUnit 			= 	invoiceUI.getTextFieldProductUnit().getText();
		String productRate			=	invoiceUI.getTextFieldProductRate().getText();
		String productAmount		=	invoiceUI.getTextFieldProductAmount().getText();
		String productTD			=	invoiceUI.getTextFieldProductTD().getText();
		
		try{
			BigDecimal bd = new BigDecimal(productAmount);
			bd = bd.setScale(2,RoundingMode.HALF_UP);

			DecimalFormat df = new DecimalFormat("#.00");
			productAmount = df.format(bd.doubleValue());
		}catch(Exception e){ LOGGER.error(e); }
		
		InvoiceItemInfo info = InvoiceItemInfo.builder()
				.description(productDescValue)
				.size(productSize)
				.qty(productQty)
				.unit(productUnit)
				.rate(productRate)
				.amount(productAmount)
				.td(productTD)
				.isEnable(Boolean.TRUE)
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastUpdated(new Timestamp(System.currentTimeMillis()))
				.build();
		if(invoiceUI == null){
			return ;
		}
		
		/*invoiceUI.getTextFieldProductDesc().setText(LeamonERPConstants.EMPTY_STR);
		invoiceUI.getTextFieldProductSize().setText(LeamonERPConstants.EMPTY_STR);
		invoiceUI.getTextFieldProductQty().setText(LeamonERPConstants.EMPTY_STR);
		invoiceUI.getTextFieldProductUnit().setText(LeamonERPConstants.EMPTY_STR);
		invoiceUI.getTextFieldProductRate().setText(LeamonERPConstants.EMPTY_STR);
		invoiceUI.getTextFieldProductAmount().setText(LeamonERPConstants.EMPTY_STR);
		invoiceUI.getTextFieldProductTD().setText(LeamonERPConstants.EMPTY_STR);*/

		clearProductFields();
		
		int selectedRow = invoiceUI.getTableInvoice().getSelectedRow();
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			TableInvoiceModel model = (TableInvoiceModel)invoiceUI.getTableInvoice().getModel();
			int size = model.getInvoiceItemInfos().size();
			info.setSno(String.valueOf(size+1));
			model.addRow(info);
		}else{
			if(invoiceUI.getTableInvoice().getRowSorter() != null){
				selectedRow = invoiceUI.getTableInvoice().getRowSorter().convertRowIndexToModel(selectedRow);
			}
			TableInvoiceModel model = (TableInvoiceModel)invoiceUI.getTableInvoice().getModel();
			List<InvoiceItemInfo> invoiceinfos =  model.getInvoiceItemInfos();
			info.setSno(invoiceinfos.get(selectedRow).getSno());
			invoiceinfos.set(selectedRow, info);
			((AbstractTableModel)invoiceUI.getTableInvoice().getModel()).fireTableDataChanged();
			invoiceUI.getTableInvoice().repaint();
		}
		
		
	}//end
	
	private void enterKeyTableHandler(KeyEvent e){
		if(e.getSource() instanceof JTable){
			JTable tbl = (JTable) e.getSource();
			if(tbl.getName()!=null && tbl.getName().equals(LeamonERPConstants.TABLE_INVOICE)){
				int selectedRow = getTableSeletedRow(tbl);
				if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
					return;
				}
				TableInvoiceModel model = (TableInvoiceModel)tbl.getModel();
				InvoiceItemInfo info = model.getInvoiceItemInfos().get(selectedRow);
				fillinvoiceTextFiled(info);
			}
		}//end jtable eevent
	}//end
	
	private void mouseClickTableHandler(MouseEvent e){
		if(e.getSource() instanceof JTable){
			JTable tbl = (JTable) e.getSource();
			if(tbl.getName()!=null && tbl.getName().equals(LeamonERPConstants.TABLE_INVOICE)){
				int selectedRow = getTableSeletedRow(tbl);
				if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
					return;
				}
				TableInvoiceModel model = (TableInvoiceModel)tbl.getModel();
				InvoiceItemInfo info = model.getInvoiceItemInfos().get(selectedRow);
				fillinvoiceTextFiled(info);
			}
		}//end jtable eevent
	}//end
	
	private int getTableSeletedRow(JTable tbl){
		int selectedRow = tbl.getSelectedRow();
		
		if(selectedRow == LeamonERPConstants.NO_ROW_SELECTED){
			return LeamonERPConstants.NO_ROW_SELECTED;
		}
		
		if(tbl.getRowSorter() != null){
			//LOGGER.debug(" Row sorter is not null");
			selectedRow = tbl.getRowSorter().convertRowIndexToModel(selectedRow);
		}
		return selectedRow;
	}
	
	void fillinvoiceTextFiled(InvoiceItemInfo info){
		invoiceUI.getTextFieldProductDesc().setText(info.getDescription());
		invoiceUI.getTextFieldProductSize().setText(info.getSize());
		invoiceUI.getTextFieldProductQty().setText(info.getQty());
		invoiceUI.getTextFieldProductUnit().setText(info.getUnit());
		invoiceUI.getTextFieldProductRate().setText(info.getRate());
		invoiceUI.getTextFieldProductAmount().setText(info.getAmount());
		invoiceUI.getTextFieldProductTD().setText(info.getTd());
	}

	public boolean isNumerictextField(){
		return false;
	}
	
	/** Calculates total Amount from table invoice items And set it in TextFieldTotal */
	public void setTtoal(JTable tbl){
		if(tbl == null || invoiceUI == null){
			return ;
		}
		
		TableInvoiceModel model = (TableInvoiceModel) tbl.getModel();
		List<InvoiceItemInfo> infos = model.getInvoiceItemInfos();
		
		double sum = 0;
		for(InvoiceItemInfo info : infos ){
			 BigDecimal amt = new BigDecimal(info.getAmount());
			 sum = sum + amt.doubleValue();
		}
		//BigDecimal subTotal = new BigDecimal(sum);
		//subTotal = subTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		invoiceUI.getTextFieldTotal().setText(String.valueOf(getRoundff(sum)));
	}//end total
	
	/**Calculates total Discount Amount  from underlying list of invoice items in table and set this in TextFieldDiscount*/
	private void setDiscount(InvoiceUI ui){
		if(ui == null){
			return ;
		}
		
		JTable tbl = ui.getTableInvoice();
		TableInvoiceModel model = (TableInvoiceModel)tbl.getModel();
		List<InvoiceItemInfo> infos =  model.getInvoiceItemInfos();
		
		double totalDiscount = 0;
		
		for(InvoiceItemInfo info : infos){
			String tdValue = info.getTd();
			String amountValue = info.getAmount();
			double td = 0;
			double amount = 0;
			try{
				td = Double.parseDouble(tdValue);
			}catch(Exception e){
				
			}
			amount = Double.parseDouble(amountValue);
			double dis = (amount * td)/100;
			totalDiscount = totalDiscount + dis; 
		}
		ui.getTextFieldDiscount().setText(String.valueOf(getRoundff(totalDiscount)));
	}//end discount calculation
	
	/**Calculates Taxable Amount  ( TextFieldTotal - TextFieldDiscount = TaxableValue )*/
	private void setTaxableValue(InvoiceUI ui){
		if(ui == null){
			return ;
		}
		double totalAmt = 0;
		String totalVal = ui.getTextFieldTotal().getText();
		try{
			totalAmt = Double.parseDouble(totalVal);
		}catch(Exception e){}
		
		double disAmt = 0;
		String disAmtVal = ui.getTextFieldDiscount().getText();
		try{
			disAmt = Double.parseDouble(disAmtVal);
		}catch(Exception e){}
		
		double taxableValue = totalAmt - disAmt;
		ui.getTextFieldTaxableValue().setText(String.valueOf(getRoundff(taxableValue)));
	}// set taxable value
	
	/** Calculates Grand Total ( ((TextFieldTaxableValue * TextFieldTAX)/100) + TextFieldTaxableValue = TextFieldGtotal2,TextFieldGtotal1 )*/
	private void calcGrandTotal(InvoiceUI ui){
		if(ui == null){return;}
		
		String taxValue  = ui.getTextFieldGstTAX().getText();
		double taxVal = 0;
		try{
			taxVal = Double.parseDouble(taxValue);
		}catch(Exception e){}
		 
		String taxableval = ui.getTextFieldTaxableValue().getText();
		double taxableAmt = 0;
		try{
			taxableAmt = Double.parseDouble(taxableval);
		}catch(Exception e){}
		
		//double taxAmt = (taxableAmt*taxVal)/100;
		//double taxAmt = (taxableAmt+taxVal);
		
		double grandTotal = taxableAmt+taxVal;
		/*BigDecimal bd = new BigDecimal(grandTotal);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_EVEN);*/
		ui.getTextFieldGTotal1().setText(String.valueOf(getRoundff(grandTotal)));
		ui.getTextFieldGtotal2().setText(String.valueOf(getRoundff(grandTotal)));
	}
	
	/**Calculates TD Percentage from list of invoice items under lying into table.*/
	public void calcTDPercentage(InvoiceUI ui){
		if(ui == null){
			return ;
		}
		String tdPercentage = ui.getTextFieldProductTD().getText();
		double d = 0;
		try{
			d  = Double.parseDouble(tdPercentage);
		}catch(Exception exp){
			//exp.printStackTrace();
			ui.getTextFieldProductTD().setText(LeamonERPConstants.EMPTY_STR);
		}
		double amount = 0;
		try{
			amount  = Double.parseDouble(ui.getTextFieldProductAmount().getText());
		}catch(Exception e){
			e.printStackTrace();
			ui.getTextFieldProductAmount().setText(LeamonERPConstants.EMPTY_STR);
		}
		
		double discountAmt = (amount*d)/100;
		String discountOldValue = ui.getTextFieldDiscount().getText();
		
		double discountOldAmt = 0;
		try{
			discountOldAmt = Double.parseDouble(discountOldValue);
		}catch(Exception e){
			//e.printStackTrace();
		}
		
		discountAmt = discountOldAmt + discountAmt;
		ui.getTextFieldDiscount().setText(String.valueOf(discountAmt));
		
		String totalValue = ui.getTextFieldTotal().getText();
		double totalAmt  = 0;
		try{
			totalAmt  = Double.parseDouble(totalValue);
		}catch(Exception e){
		}
		
		double taxableAmt  = totalAmt - discountAmt; 
		ui.getTextFieldTaxableValue().setText(String.valueOf(getRoundff(taxableAmt)));
	}//end cacPercentage
	
	
	
	public void setAmount(InvoiceUI ui){
		String amtValue = ui.getTextFieldProductAmount().getText();
		double amtProductAmt = 0;
		try{
			amtProductAmt = Double.parseDouble(amtValue);
		}catch(Exception e){}
		
		String totalValue = ui.getTextFieldTotal().getText();
		double totalAmt = 0;
		try{
			totalAmt = Double.parseDouble(totalValue);
		}catch(Exception e){}
		
		totalAmt = totalAmt +  amtProductAmt;
		ui.getTextFieldTotal().setText(String.valueOf(getRoundff(totalAmt)));
	}// end amount
	
	private void setSubTotalAmountOnClick(JTextField fld , InvoiceUI ui){
		if(fld.getName() == null){ return; }
		if(fld.getName().equals(LeamonERPConstants.TEXTFIELD_INVOICE_SUB_TOTAL)){
			setTtoal(ui.getTableInvoice());
		}
	}

	public void calcPackingAmt(InvoiceUI ui){
		if(ui == null){
			return ;
		}
		
		String billAmtVal = ui.getTextFieldBillAmount().getText();
		String gvalue= ui.getTextFieldGtotal2().getText();
		
		double billAmt = 0;
		double gAmt = 0;
		
		try{
			gAmt = Double.parseDouble(gvalue);
		}catch(Exception e) {}
		
		try{
			billAmt = Double.parseDouble(billAmtVal);
		}catch(Exception e) {}
		
		double packingAmt = gAmt - billAmt;
		ui.getTextFieldPackingAmount().setText(String.valueOf(getRoundff(packingAmt)));
	}
	
	private String getRoundff(double value){
		BigDecimal bd = new BigDecimal(value);
		bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		try{

			BigDecimal bdd = new BigDecimal(value);
			bdd = bdd.setScale(2,RoundingMode.HALF_UP);

			DecimalFormat df = new DecimalFormat("#.00");
			String grandTotal = df.format(bdd.doubleValue());
			return grandTotal;
		}catch(Exception e){ LOGGER.error(e); }
		
		return String.valueOf(bd.doubleValue());
	}
	
	private void copyPacket(InvoiceUI ui){
		if(ui == null){ return; }
		String txt = ui.getTextFieldGoodsPackets1().getText();
		ui.getTextFieldGoodsPackets2().setText(txt);
	}
	private void copyPacket2(InvoiceUI ui){
		if(ui == null){ return; }
		String txt = ui.getTextFieldGoodsPackets2().getText();
		ui.getTextFieldGoodsPackets1().setText(txt);
	}
	
	public void clearProductFields(){
		if(invoiceUI == null){
			return;
		}
		invoiceUI.getTextFieldProductDesc().setText(LeamonERPConstants.EMPTY_STR);
		//invoiceUI.getTextFieldProductDesc().repaint();
		
		invoiceUI.getTextFieldProductSize().setText(LeamonERPConstants.EMPTY_STR);
		//invoiceUI.getTextFieldProductSize().repaint();
		
		invoiceUI.getTextFieldProductQty().setText(LeamonERPConstants.EMPTY_STR);
		//invoiceUI.getTextFieldProductQty().repaint();
		
		invoiceUI.getTextFieldProductUnit().setText(LeamonERPConstants.EMPTY_STR);
		//invoiceUI.getTextFieldProductUnit().repaint();
		
		invoiceUI.getTextFieldProductRate().setText(LeamonERPConstants.EMPTY_STR);
		//invoiceUI.getTextFieldProductRate().repaint();
		
		invoiceUI.getTextFieldProductAmount().setText(LeamonERPConstants.EMPTY_STR);
		//invoiceUI.getTextFieldProductAmount().repaint();
		
		invoiceUI.getTextFieldProductTD().setText(LeamonERPConstants.EMPTY_STR);
		//invoiceUI.getTextFieldProductTD().repaint();
	}
}
