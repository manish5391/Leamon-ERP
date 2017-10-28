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
public class StockItemQuantity implements Serializable, Comparable<StockItemQuantity>{
	private Integer id;
	private Integer stokItemid;
	private String quantity;
	private String description;
	
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
	private boolean isEnable;
	
	@Override
	public int compareTo(StockItemQuantity o) {
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
