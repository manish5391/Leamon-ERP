package leamon.erp.model;

import java.io.Serializable;
import java.math.BigInteger;
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
 * @date 23 Dec 2017
 * @author Manish Kumar Mishra
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class LeamonProperty implements Serializable, Comparable<LeamonProperty> {
	private Integer id;

	public String propertyname;
	private String propertyvalue;
	private String description;
	
	private Timestamp createddate;
	private Timestamp lastupdated;
	private boolean isenable;
	
	@Override
	public int compareTo(LeamonProperty o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
