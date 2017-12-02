package leamon.erp.util;

public enum InvoicePaymentStatusEnum {
	//CLEAR, REMAINING, NOTHING_PAID
	NOTHING_PAID("NOTHING_PAID"),
	ALL_CLEAR("CLEAR"),
	REMAINING("REMAINING");
	
	private String value;
	
	InvoicePaymentStatusEnum(String value){
		this.value = value;
	}
	
}
