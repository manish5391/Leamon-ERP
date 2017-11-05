package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
@AllArgsConstructor
public class StockItemQuantityOrderHistory implements Serializable, Comparable<StockItemQuantityOrderHistory>{
	private Integer id;
	private Integer stokItemid;
	private Integer invoiceid;
	private Integer invoiceItemId;
	
	private String quantity;
	private String description;
	
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
	private boolean isEnable;
	
	@Override
	public int compareTo(StockItemQuantityOrderHistory o) {
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
