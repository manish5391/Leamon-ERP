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
 * This will record partypayment
 * 
 * @Copyright Leamon India 
 * 
 * AccountInfo Entity
 * 
 * @date 15 Jun 2017
 * @author Manish Kumar Mishra
 * 
 * @version Leamon-ERP-3.0
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class PaymentReceivedInfo implements Serializable, Comparable<PaymentReceivedInfo> {
	
	private Integer id;
	private Integer partyInfoID;
	private String  receivedPayment;
	private String  adjustedPayment;
	private String  remainingAmount;  // receivedPayment - adjustedPayment  
	private String  status; /*CLEAR, PARTIAL-ADJUSTED*/
	private String 	remark;
	private String 	type;
	private String 	receivedDate;
	
	/*get related accountInfo*/
	private AccountInfo accountInfo;
	
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	@Override
	public int compareTo(PaymentReceivedInfo o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
