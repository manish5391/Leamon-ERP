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
	//private String packingAmount;
	private String gstValue;
	
	private List<InvoiceItemInfo> items;

	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	/*---Release 3.2--- Nov 15,2017-*/
	private String grBiltyNumber; //bilty nuber
	private String col1Name;
	private String col2Name;
	private String col1Val;
	private String col2Val;
	private String col1Operator;
	private String col2Operator;
	private String withoutBillAmount; //packing amount
	private String remainingBillAmount; //how much is left to paid
	private String remainingWithoutBillAmount; //how much is left to paid in packing amount
	private String paidBillAmount; //how much is paid
	private String paidWithoutBillAmount; //how much is paid in packing amount
	private String paidStatus; //CLEAR, REMAINING, NOTHING PAID
	private String remainingStatus; //clear, remaining, Nothing Paid
	
	@Override
	public int compareTo(InvoiceInfo o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
