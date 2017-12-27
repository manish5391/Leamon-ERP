package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StockItem implements Serializable, Comparable<StockItem>{
	private Integer id;
	private String name;
	private String productCode;
	private String size;
	private String finish;
	private String unit;
	private String shape;
	private String saleunit;
	private String description;
	
	private String imagePath;
	
	
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
	private boolean isEnable;
	
	/*Added 3.3.1 DEC 24,2017*/
	private StockItemQuantity stockItemQuantity;
	
	@Override
	public int compareTo(StockItem o) {
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
