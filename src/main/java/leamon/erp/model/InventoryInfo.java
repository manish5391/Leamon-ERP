package leamon.erp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
@AllArgsConstructor
public class InventoryInfo  implements Serializable {
	
	private Integer id;
	private Integer sno;
	private String descOfGoods;
	private String size;
	private Integer qty;
	private String displayQtyUnit;
	
	private BigDecimal rate;
	private BigDecimal valueOfGoods;
	
	private BigDecimal discount;
	private String displayDiscount;
	
	private String serachValue;
	
	private Timestamp createdDate;
	private Timestamp lastUpdatedDate;
	private boolean isEnable;
	
	private Integer accountInfoID;
	private Integer stockItemID;
	
	private Boolean isTotalRow = Boolean.FALSE;
}
