package leamon.erp.ui.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import leamon.erp.model.PaymentReceivedInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.model.OpeningBalanceInfo;
import leamon.erp.ui.PaymentUI;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.PaymentEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TablePaymentInvoiceOpeningBalanceModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TablePaymentInvoiceOpeningBalanceModel.class);
	private static final String CLASS_NAME = "TablePaymentInvoiceOpeningBalanceModel";

	private final String [] columnName = new String[] {
			LeamonERPConstants.TABLE_HEADER_SNO,
			LeamonERPConstants.TABLE_HEADER_PARTY_NAME,
			LeamonERPConstants.TABLE_HEADER_DATE,
			LeamonERPConstants.TABLE_HEADER_TYPE,
			LeamonERPConstants.TABLE_HEADER_B_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_G_TOTAL
	};

	private List<Object> list;
	private List<Integer> sno;

	public TablePaymentInvoiceOpeningBalanceModel(List<Object> list){
		if(list == null){
			list = new ArrayList<Object>();
		}
		this.list = list;
	}	

	public TablePaymentInvoiceOpeningBalanceModel(GenericModelWithSnp<List<Object>, Object> genericModelWithSnp){
		this.list  = genericModelWithSnp.getOb();
		this.sno = genericModelWithSnp.getSno();
	}

	@Override
	public int getRowCount() {
		if(list == null){
			return 0;
		}

		return list.size();
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		final String METHOD_NAME = "getValueAt";
		Object temp = null;
		switch (columnIndex) {
		case 0: {
			temp = sno.get(rowIndex); 
		}break;

		case 1: {
			if(list.get(rowIndex) instanceof OpeningBalanceInfo){
				temp = ((OpeningBalanceInfo)list.get(rowIndex)).getAccountInfo().getName();
			}else if(list.get(rowIndex) instanceof PaymentReceivedInfo){
				temp = ((PaymentReceivedInfo)list.get(rowIndex)).getAccountInfo().getName();
			}else if(list.get(rowIndex) instanceof InvoiceInfo){
				temp = ((InvoiceInfo)list.get(rowIndex)).getAccountInfo().getName();
			}else{
				temp = "";
			} 
		}break;

		case 2: {
			DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
			if(list.get(rowIndex) instanceof OpeningBalanceInfo){
				try{
					temp = df.format(new Date(((OpeningBalanceInfo)list.get(rowIndex)).getLastUpdated().getTime()));
				}catch(Exception e){
					LOGGER.error(e);
					temp = ((OpeningBalanceInfo)list.get(rowIndex)).getLastUpdated().toLocalDateTime();
				}
			}else if(list.get(rowIndex) instanceof PaymentReceivedInfo){
				try{
					temp = df.format(new Date(((PaymentReceivedInfo)list.get(rowIndex)).getLastUpdated().getTime()));
				}catch(Exception e){
					LOGGER.error(e);
					temp = ((PaymentReceivedInfo)list.get(rowIndex)).getLastUpdated().toLocalDateTime();
				}
			}else if(list.get(rowIndex) instanceof InvoiceInfo){
				try{
					temp = df.format(new Date(((InvoiceInfo)list.get(rowIndex)).getLastUpdated().getTime()));
				}catch(Exception e){
					LOGGER.error(e);
					temp = ((InvoiceInfo)list.get(rowIndex)).getLastUpdated().toLocalDateTime();
				}
			}else{
				temp = "";
			} 
		}break;

		case 3: {
			if(list.get(rowIndex) instanceof OpeningBalanceInfo){
				temp = LeamonERPConstants.OPENING_BALANCE;
			}else if(list.get(rowIndex) instanceof PaymentReceivedInfo){
				temp = LeamonERPConstants.PAYMENT;
			}else if(list.get(rowIndex) instanceof InvoiceInfo){
				temp = LeamonERPConstants.INVOICE;
			}else{
				temp = "";
			}	
		} break;

		case 4: { /*B Amount*/
			if(list.get(rowIndex) instanceof OpeningBalanceInfo){
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(((OpeningBalanceInfo)list.get(rowIndex)).getType())){
					temp = ((OpeningBalanceInfo)list.get(rowIndex)).getOpeningbalanceamount();
				}
			}else if(list.get(rowIndex) instanceof PaymentReceivedInfo){
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(((PaymentReceivedInfo)list.get(rowIndex)).getType())){
					temp = ((PaymentReceivedInfo)list.get(rowIndex)).getReceivedPayment();
				}
			}else if(list.get(rowIndex) instanceof InvoiceInfo){
				temp = ((InvoiceInfo)list.get(rowIndex)).getBillAmount();
			}else{
				temp = "";
			}
		}break;

		case 5: { /*W Amount*/
			if(list.get(rowIndex) instanceof OpeningBalanceInfo){
				if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(((OpeningBalanceInfo)list.get(rowIndex)).getType())){
					temp = ((OpeningBalanceInfo)list.get(rowIndex)).getOpeningbalanceamount();
				}
			}else if(list.get(rowIndex) instanceof PaymentReceivedInfo){
				if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(((PaymentReceivedInfo)list.get(rowIndex)).getType())){
					temp = ((PaymentReceivedInfo)list.get(rowIndex)).getReceivedPayment();
				}
			}else if(list.get(rowIndex) instanceof InvoiceInfo){
				temp = ((InvoiceInfo)list.get(rowIndex)).getWithoutBillAmount();
			}else{
				temp = "";
			}
		}break;

		case 6: { /*Grand Total*/
			/*if(list.get(rowIndex) instanceof OpeningBalanceInfo){
					temp = LeamonERPConstants.OPENING_BALANCE;
				}else if(list.get(rowIndex) instanceof PaymentReceivedInfo){
					temp = LeamonERPConstants.PAYMENT;
				}else if(list.get(rowIndex) instanceof InvoiceInfo){
					temp = LeamonERPConstants.INVOICE;
				}else{
					temp = "";
				}*/
			temp = "";
		}break;

		}
		return temp;
	}

	@Override
	public String getColumnName(int col) { 
		return columnName[col]; 
	} 

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch(columnIndex){
		case 0: return Integer.class;
		case 1: return String.class;
		case 2: return String.class;
		case 3: return String.class;
		case 4: return String.class;
		case 5: return String.class;
		case 6: return String.class;
		default : 
			return Object.class;
		}
	}

}
