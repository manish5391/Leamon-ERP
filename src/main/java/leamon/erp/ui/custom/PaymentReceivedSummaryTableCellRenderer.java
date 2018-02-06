package leamon.erp.ui.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import leamon.erp.ui.model.TablePaymentReceivedSummaryModel;
import leamon.erp.util.InvoicePaymentStatusEnum;
import leamon.erp.util.LeamonERPConstants;

/**
 * This class is to color the columns background based on their value for InvoicePaymentStatusEnum.
 * E.g  
 * NOTHING_PAID - RED  
 * CLEAR		- GREEN
 * PARTIAL_PAID	- YELLOW
 * 
 * 
 * @author Manish Kumar Mishra
 * @date  FEB 06,2018
 * @since Leamon-ERPv3.5
 */
public class PaymentReceivedSummaryTableCellRenderer  extends JLabel implements TableCellRenderer {
	//public class PaymentReceivedSummaryTableCellRenderer  extends DefaultTableCellRenderer{

	private static final String CLASS_NAME="PaymentReceivedSummaryTableCellRenderer";
	private static final Logger LOGGER = Logger.getLogger(PaymentReceivedSummaryTableCellRenderer.class);

	public PaymentReceivedSummaryTableCellRenderer(){
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		TablePaymentReceivedSummaryModel model = (TablePaymentReceivedSummaryModel)table.getModel();

		if(model!=null && model.getInvoiceInfos() != null && !model.getInvoiceInfos().isEmpty()){

			if(value instanceof String 
					&& !Strings.isNullOrEmpty(value.toString())
					&& !isSelected){

				if(InvoicePaymentStatusEnum.NOTHING_PAID.name().equals(value.toString())){
					setBackground(Color.RED);
					setText(value.toString());
				}else if(InvoicePaymentStatusEnum.PARTIAL_PAID.name().equals(value.toString())){
					setBackground(Color.YELLOW);
					setText(value.toString());
				}else if(InvoicePaymentStatusEnum.ALL_CLEAR.name().equals(value.toString())){
					setBackground(Color.GREEN);
					setText(value.toString());
				}else{
					setBackground(table.getBackground());
					setForeground(table.getForeground());
					setText(value.toString());
				}
			}else if(!isSelected){
					setBackground(table.getBackground());
					setForeground(table.getForeground());
					setText(value!=null ? value.toString() : "");
			}else{
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
				setText(value!=null ? value.toString() : "");
			}
		}


		return this;
	}
}
