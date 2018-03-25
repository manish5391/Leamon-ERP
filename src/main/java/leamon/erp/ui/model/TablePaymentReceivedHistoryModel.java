package leamon.erp.ui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import leamon.erp.model.PaymentReceivedInfo;
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
public class TablePaymentReceivedHistoryModel extends AbstractTableModel{

	private static final Logger LOGGER = Logger.getLogger(TablePaymentReceivedHistoryModel.class);
	private static final String CLASS_NAME = "TablePaymentReceivedHistoryModel";

	private final String [] columnName = new String[] {
			LeamonERPConstants.TABLE_HEADER_SNO,
			LeamonERPConstants.TABLE_HEADER_PAYMENT_ID,
			LeamonERPConstants.TABLE_HEADER_PARTY_NAME,
			LeamonERPConstants.TABLE_HEADER_AMOUNT,
			LeamonERPConstants.TABLE_HEADER_TYPE,
			LeamonERPConstants.TABLE_HEADER_DATE,
			LeamonERPConstants.TABLE_HEADER_DESC,
			LeamonERPConstants.TABLE_HEADER_PAYMENT_ADJUSTED
	};

	private List<PaymentReceivedInfo> paymentReceivedInfos;
	private List<Integer> sno;
	
	public TablePaymentReceivedHistoryModel(List<PaymentReceivedInfo> paymentReceivedInfos){
		if(paymentReceivedInfos == null){
			paymentReceivedInfos = new ArrayList<PaymentReceivedInfo>();
		}
		this.paymentReceivedInfos = paymentReceivedInfos;
	}	

	public TablePaymentReceivedHistoryModel(GenericModelWithSnp<List<PaymentReceivedInfo>, PaymentReceivedInfo> genericModelWithSnp){
		this.paymentReceivedInfos  = genericModelWithSnp.getOb();
		this.sno = genericModelWithSnp.getSno();
	}

	@Override
	public int getRowCount() {
		if(paymentReceivedInfos == null){
			return 0;
		}
		
		return paymentReceivedInfos.size();
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
			case 0: temp = /*paymentReceivedInfos.get(rowIndex).getId()*/ sno.get(rowIndex); break;
			case 1: temp = paymentReceivedInfos.get(rowIndex).getId().intValue(); break;
			case 2: temp = paymentReceivedInfos.get(rowIndex).getAccountInfo().getName(); break;
			case 3: temp = paymentReceivedInfos.get(rowIndex).getReceivedPayment(); break;
			case 4: {
				if(ERPEnum.TYPE_PAYMENT_WITH_BILL.name().equals(paymentReceivedInfos.get(rowIndex).getType()) ){
					temp = PaymentEnum.B;
				}else if(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name().equals(paymentReceivedInfos.get(rowIndex).getType()) ){
					temp = PaymentEnum.W;
				}else{
					temp = "N/A";
				}
			} break;
			case 5: temp = paymentReceivedInfos.get(rowIndex).getReceivedDate();break;
			case 6: temp = paymentReceivedInfos.get(rowIndex).getRemark(); break;
			case 7: {
				if(ERPEnum.STATUS_PAYMENT_ADJUSTMENT_NOTHING.name().equals(paymentReceivedInfos.get(rowIndex).getStatus())){
					temp = Boolean.FALSE.booleanValue();
				}else{
					temp = Boolean.TRUE.booleanValue();
				}
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
		case 7: return Boolean.class;
		default : 
			return Object.class;
		}
	}

}
