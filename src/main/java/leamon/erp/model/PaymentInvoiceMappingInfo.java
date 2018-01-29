package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
	
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	@Override
	public int compareTo(PaymentInvoiceMappingInfo o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
