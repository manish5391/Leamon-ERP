package leamon.erp.util;

public enum InvoicePaymentStatusEnum {
	//CLEAR, REMAINING, NOTHING_PAID
	NOTHING_PAID("NOTHING_PAID"),
	PARTIAL_PAID("PARTIAL_PAID"),
	ALL_CLEAR("CLEAR");
	
	private String value;
	
	InvoicePaymentStatusEnum(String value){
		this.value = value;
	}
	
}
