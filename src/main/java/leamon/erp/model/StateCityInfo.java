package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import leamon.erp.model.StockItem.StockItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
@AllArgsConstructor
public class StateCityInfo implements Serializable, Comparable<StateCityInfo>{
	
	private Integer id;
	private String city;
	private String state;
	private String stateCode;
	private String abbreviations;
	
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
	private boolean isEnable;
	
	@Override
	public int compareTo(StateCityInfo o) {
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
