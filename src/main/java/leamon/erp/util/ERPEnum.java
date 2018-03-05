package leamon.erp.util;

public enum ERPEnum {
	
	TYPE_PAYMENT_WITHOUT_BILL("W"),
	TYPE_PAYMENT_WITH_BILL("B"),
	
	STATUS_PAYMENT_ADJUSTMENT_NOTHING("NOTHING"),
	STATUS_PAYMENT_ADJUSTMENT_CLEAR("CLEAR"), //everything is adjusted
	STATUS_PAYMENT_ADJUSTMENT_PARTIAL("PARTIAL"),
	
	/*Leamon-Property*/
	REPORTTD("REPORTTD"),
	REPORTWITHOUTD("REPORTWITHOUTD"),
	STOCKITEMLIST("STOCKITEMLIST")
	;
	
	
	private String value;
	
	ERPEnum(String value){
		this.value = value;
	}
}

