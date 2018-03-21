package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Copyright Leamon India 
 * 
 * AccountInfo Entity
 * 
 * @date 09 Dec 2017
 * @author Manish Kumar Mishra
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class PaymentInvoiceMappingInfo implements Serializable, Comparable<PaymentInvoiceMappingInfo> {
	private Integer id;
	
	private Integer paymentReceivedInfo;
	private Integer invoiceInfoID;
	private String amount;
	
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	/*Release 3.4*/
	private Integer openingBalanceID;
	
	/*----Release 3.9-----*/
	private List<InvoiceInfo> invoiceInfos;
	private List<OpeningBalanceInfo> openigBalanceInfos;
	/*----End------------*/
	
	@Override
	public int compareTo(PaymentInvoiceMappingInfo o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
