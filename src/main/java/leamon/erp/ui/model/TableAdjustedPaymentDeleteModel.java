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
import leamon.erp.model.PaymentInvoiceMappingInfo;
import leamon.erp.ui.PaymentUI;
import leamon.erp.util.ERPEnum;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.PaymentEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableAdjustedPaymentDeleteModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TableAdjustedPaymentDeleteModel.class);
	private static final String CLASS_NAME = "TablePaymentInvoiceOpeningBalanceModel";

	private final String [] columnName = new String[] {
			LeamonERPConstants.TABLE_HEADER_SNO,
			LeamonERPConstants.TABLE_HEADER_TYPE,
			LeamonERPConstants.TABLE_HEADER_B_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_B_REMAINING_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_REMAINING_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_B_ADJUSTED_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_W_ADJUSTED_AMOUNT
	};

	private List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos;
	private List<Integer> sno;

	public TableAdjustedPaymentDeleteModel(List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos){
		if(paymentInvoiceMappingInfos == null){
			paymentInvoiceMappingInfos = new ArrayList<PaymentInvoiceMappingInfo>();
		}
		this.paymentInvoiceMappingInfos = paymentInvoiceMappingInfos;
	}	

	public TableAdjustedPaymentDeleteModel(GenericModelWithSnp<List<PaymentInvoiceMappingInfo>, PaymentInvoiceMappingInfo> genericModelWithSnp){
		this.paymentInvoiceMappingInfos  = genericModelWithSnp.getOb();
		this.sno = genericModelWithSnp.getSno();
	}


	@Override
	public int getRowCount() {
		if(paymentInvoiceMappingInfos == null){
			return 0;
		}

		return paymentInvoiceMappingInfos.size();
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

		case 1: {}break;

		case 2: {}break;

		case 3: {} break;

		case 4: {}break;

		case 5: {}break;

		case 6: {}break;

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
