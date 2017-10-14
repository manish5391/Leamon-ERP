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
 * @date 15 Jun 2017
 * @author Manish Kumar Mishra
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class AccountInfo implements Serializable, Comparable<AccountInfo> {
	private Integer id;
	public String name;
	private BigInteger phone;
	private String houseShopNumber;
	private String street;
	private String city;
	private String state;
	
	private String nickName;
	private String gstNumber;
	private String transport;
	
	private Integer pinCode;
	private String country;
	private String landMark;
	private Timestamp engagedDate;
	private String panCard;
	private String licence;
	private String description;
	private String imagePath;
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
	
	@Override
	public int compareTo(AccountInfo o) {
		
		return (this.id < o.id) ? -1 : (this.id > o.id) ? 1 : 0;
	}
}
