package leamon.erp.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import leamon.erp.util.ERPEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This will record opening balance
 * 
 * @Copyright Leamon India 
 * 
 * AccountInfo Entity
 * 
 * @date 27 Jan 2018
 * @author Manish Kumar Mishra
 * 
 * @version Leamon-ERP-3.3.2
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class OpeningBalanceInfo implements Serializable, Comparable<OpeningBalanceInfo> {
	
	private Integer id;
	private Integer partyinfoid;
	private String	billnumber;					
	private String	billdate;					
	private String	openingbalanceamount;		
	private String	receivedopeningbalanceamount;
	private String	remainingopeningbalanceamount;
	private String	remark;						
	private String	type;	/*W- without bill, B- with bill  (ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())*/				
	private String  status; /*CLEAR, PARTIAL-ADJUSTED*/
	
	/*get related accountInfo*/
	private AccountInfo accountInfo;
	
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	@Override
	public int compareTo(OpeningBalanceInfo o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
