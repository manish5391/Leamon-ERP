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
public class StateCityInfo implements Serializable, Comparable<StateCityInfo>{
	
	private Integer id;
	private String city;
	private String state;
	private String stateCode;
	private String abbreviations;
	
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
	private boolean isEnable;

	public StateCityInfo(String state, String stateCode, String abbreviations){
		this.state = state;
		this.stateCode = stateCode;
		this.abbreviations = abbreviations;
	}
	
	@Override
	public int compareTo(StateCityInfo o) {
		/*if(null == o.id ){
			return 1;
		}else{
			return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
		}*/
		return 1;
	}
}
