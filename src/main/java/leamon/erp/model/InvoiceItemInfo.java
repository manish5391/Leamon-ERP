package leamon.erp.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
public class InvoiceItemInfo implements Serializable{
	
	private Integer id;
	private Integer invoiceID;
	private Integer stockItemId;
	
	private String sno;
	private String description;
	private String size;
	private String qty;
	private String unit;
	private String rate;
	private String amount;
	private String td;
	
	private Timestamp createdDate;
	private Timestamp lastUpdated;
	private boolean isEnable;
}
