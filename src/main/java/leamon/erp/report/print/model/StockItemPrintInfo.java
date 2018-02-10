package leamon.erp.report.print.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class StockItemPrintInfo {
	private int sno;
	private String name;
	private String size;
	private String quantity;
	private String saleunit;

}
