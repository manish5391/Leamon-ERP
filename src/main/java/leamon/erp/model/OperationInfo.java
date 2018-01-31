package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Copyright Leamon India 
 * 
 * AccountInfo Entity
 * 
 * @date 02 Dec 2017
 * @author Manish Kumar Mishra
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class OperationInfo implements Serializable, Comparable<OperationInfo> {
	private Integer id;
	
	private String key;
	public String propertyname;
	private String propertyvalue;
	private String description;
	
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	@Override
	public int compareTo(OperationInfo o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
