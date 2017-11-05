package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
public class InvoiceInfo implements Serializable, Comparable<InvoiceInfo>{
	
	private Integer id;
	private Integer partyinfoID;
	
	private String invoicNum;
	private String invoicDate;
	
	private String orderBookedBy;;
	private String transport;
	private String pktNumber;
	/*bill number : release 3.1*/
	private String billNo;
	
	private String billAmount;
	private String packingAmount;
	private String gstValue;
	
	private List<InvoiceItemInfo> items;
	
	/*added for handling payment @ 11 OCT,2017*/
	
	/*isBillingAmountPaidClear : this amount is fully paid nothing is remaining.*/
	private boolean isBillingAmountPaidClear;
	/*isPackingAmountPaidClear : this amount is fully paid nothing is remaining.*/
	private boolean isPackingAmountPaidClear;
	
	/*billAmountPaid : how much billing amount has been paid. It is used to calculate remaining payment*/
	private String billAmountPaid;
	private String packingAmountPaid;
	
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	@Override
	public int compareTo(InvoiceInfo o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
