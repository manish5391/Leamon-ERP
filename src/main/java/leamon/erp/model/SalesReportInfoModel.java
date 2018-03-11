package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import leamon.erp.model.PaymentReceivedInfo.PaymentReceivedInfoBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class SalesReportInfoModel implements Serializable, Comparable<SalesReportInfoModel>{
	
	private int invoiceNumber;
	private String partyName;
	private String billNumber;
	private String date;
	private String BAmount;
	private String WAmount;
	private double Total;
	@Override
	public int compareTo(SalesReportInfoModel o) {
		// TODO Auto-generated method stub
		return (this.invoiceNumber < o.invoiceNumber) ? -1 : (this.invoiceNumber > o.invoiceNumber) ? 1 : 0;
	}
	
}
