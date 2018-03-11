package leamon.erp.model;

import java.io.Serializable;

import leamon.erp.model.SalesReportInfoModel.SalesReportInfoModelBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * Opening Balance UI
 * 
 * @date MAR 12,2018
 * @author Ghanshyam Singla
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class OpeningBalanceManagerInfoModel implements Serializable{
	
	private int sNo;
	private String partyName;
	private String billNumber;
	private String billDate;
	private String type;
	private String openingBalanceAmount;
	private String receivedAmount;
	private String remainingAmount;
}
