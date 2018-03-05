package leamon.erp.util;

/**
 * Payment Enum
 * 
 * @date Feb 06,2018 
 * @author Manish Kumar Mishra
 *
 */
public enum PaymentEnum {
	
	W("W"),
	B("B"),
	BOTH("Both");
	
	private String value;
	
	PaymentEnum(String value){
		this.value = value;
	}
}

