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
public class StockItemQuantityHistory implements Serializable, Comparable<StockItemQuantityHistory>{
	private Integer id;
	private Integer stokItemid;
	private String oldQuantity; /*Previous quantity*/
	private String plusQuantity; /*only how much is added in existing*/
	private String minusQuantity; /* only how much is deducted from existing */
	private String newQuantity;  /* after adding and subtracting quantity*/
	private String action;      /*insert, update, delete*/
	private String description; /*description if any*/
	
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
	private boolean isEnable;
	
	@Override
	public int compareTo(StockItemQuantityHistory o) {
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
