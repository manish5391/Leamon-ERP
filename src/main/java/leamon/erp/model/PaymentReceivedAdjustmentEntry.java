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
 * @date 15 Jun 2017
 * @author Manish Kumar Mishra
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class PaymentReceivedAdjustmentEntry implements Serializable, Comparable<PaymentReceivedAdjustmentEntry> {
	
	private Integer id;
	private Integer sno;
	private Integer accountInfoID;
	private String amount;
	private String receivedDate;
	private String remark;
	
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	@Override
	public int compareTo(PaymentReceivedAdjustmentEntry o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
