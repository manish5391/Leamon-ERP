package leamon.erp.db.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;


public interface InvoiceMapper {
	
	final String getAll = "SELECT * FROM INVOICE_BILL WHERE ISENABLE = TRUE ORDER BY ID";
	final String  getAllWithDisabled = "SELECT * FROM INVOICE_BILL ORDER BY ID";
	final String getById = "SELECT * FROM INVOICE_BILL WHERE ID = #{id}";
	final String getAllBetweenDate = "SELECT * FROM INVOICE_BILL WHERE TO_DATE(INVOICEDATE, 'DY DD/MM/YYYY')  BETWEEN {D '{fromDate}'} AND {D '{toDate}'}";
	
	final String insert = "INSERT INTO INVOICE_BILL (PARTYINFOID, INVOICENUM, INVOICEDATE, BILL_NO, GRNUMBER, ORDERBOOKBY, "
							+ "TRANSPORT, PACKETNUM, GSTAMOUNT, "
							+ "BILLAMOUNT, PAIDBILLAMOUNT, REMAININGBILLAMOUNT, PAIDSTATUS, "
							+ "WITHOUTBILLAMOUNT, PAIDWITHOUTBILLAMOUNT, REMAININGWITHOUTBILLAMOUNT, WPAIDSTATUS, "
							+ "COL1NAME, COL1VAL, COL2NAME, COL2VAL, COL1OPERATOR, COL2OPERATOR, "
							+ "CREATEDDATE, LASTUPDATEDDATE, ISENABLE) "
						+ "VALUES ( #{partyinfoID}, #{invoicNum}, #{invoicDate}, #{billNo}, #{grBiltyNumber},  #{orderBookedBy}, "
						+ "#{transport}, #{pktNumber}, #{gstValue}, "
						+ "#{billAmount}, #{paidBillAmount}, #{remainingBillAmount}, #{paidStatus}, "
						+ "#{withoutBillAmount}, #{paidWithoutBillAmount}, #{remainingWithoutBillAmount}, #{wpaidstatus}, "
						+ "#{col1Name}, #{col1Val}, #{col2Name}, #{col2Val}, #{col1Operator}, #{col2Operator},"
						+ "#{createdDate}, #{lastUpdated}, #{isEnable})";
	
	final String update = "UPDATE INVOICE_BILL SET PARTYINFOID = #{partyinfoID}, INVOICENUM = #{invoicNum}, INVOICEDATE = #{invoicDate}, "
						+ "BILL_NO = #{billNo}, ORDERBOOKBY = #{orderBookedBy},  TRANSPORT = #{transport}, PACKETNUM = #{pktNumber}, GSTAMOUNT = #{gstValue}, "
						+ "BILLAMOUNT = #{billAmount}, WITHOUTBILLAMOUNT= #{withoutBillAmount}, GRNUMBER = #{grBiltyNumber},"
						+ "COL1NAME = #{col1Name}, COL1VAL = #{col1Val}, COL2NAME = #{col2Name}, COL2VAL = #{col2Val}, COL1OPERATOR = #{col1Operator}, COL2OPERATOR = #{col2Operator},"
						+ "LASTUPDATEDDATE = #{lastUpdated} "
						+ " WHERE ID = #{id}";
	
	final String UPDATE_BILLING_PAYMENT = "UPDATE INVOICE_BILL SET PAIDBILLAMOUNT = #{paidBillAmount}, REMAININGBILLAMOUNT = #{remainingBillAmount}, "
			+ "PAIDSTATUS = #{paidStatus}, LASTUPDATEDDATE = #{lastUpdated}  "
			+ "WHERE ID = #{id}";
	
	final String UPDATE_WITHOUT_BILLING_PAYMENT = "UPDATE INVOICE_BILL SET PAIDWITHOUTBILLAMOUNT = #{paidWithoutBillAmount}, "
			+ "REMAININGWITHOUTBILLAMOUNT= #{remainingWithoutBillAmount}, WPAIDSTATUS = #{wpaidstatus}, "
			+ "LASTUPDATEDDATE = #{lastUpdated} "
			+ "WHERE ID = #{id}";
	
	final String deleteById = "DELETE from INVOICE_BILL WHERE ID = #{id}";
	
	final String disableByID = "UPDATE INVOICE_BILL SET ISENABLE = FALSE WHERE ID = #{id}";
	
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoID", column = "PARTYINFOID"),
	      @Result(property = "invoicNum", column = "INVOICENUM"),
	      @Result(property = "invoicDate", column = "INVOICEDATE"),
	      @Result(property = "billNo", column = "BILL_NO"),
	      @Result(property = "orderBookedBy", column = "ORDERBOOKBY"),
	      @Result(property = "transport", column = "TRANSPORT"),
	      @Result(property = "pktNumber", column = "PACKETNUM"),
	      @Result(property = "billAmount", column = "BILLAMOUNT"), 
	      @Result(property = "gstValue", column = "GSTAMOUNT"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATEDDATE"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<InvoiceInfo> getAll();
	
	@Select(getAllWithDisabled)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoID", column = "PARTYINFOID"),
	      @Result(property = "invoicNum", column = "INVOICENUM"),
	      @Result(property = "invoicDate", column = "INVOICEDATE"),
	      @Result(property = "billNo", column = "BILL_NO"),
	      @Result(property = "orderBookedBy", column = "ORDERBOOKBY"),
	      @Result(property = "transport", column = "TRANSPORT"),
	      @Result(property = "pktNumber", column = "PACKETNUM"),
	      @Result(property = "billAmount", column = "BILLAMOUNT"),   //GSTAMOUNT
	      @Result(property = "gstValue", column = "GSTAMOUNT"),	//BILLAMOUNT
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATEDDATE"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<InvoiceInfo> getAllWithDisabled();
	
	@Update(update)
	void update(InvoiceInfo invoiceInfo);

	@Update(UPDATE_BILLING_PAYMENT)
	void updatePaidBillAmount(InvoiceInfo invoiceInfo);
	
	@Update(UPDATE_WITHOUT_BILLING_PAYMENT)
	void updatePaidWBillAmount(InvoiceInfo invoiceInfo);
	
	@Update(disableByID)
	void disableInvoiceById(InvoiceInfo invoiceInfo);
	
	@Delete(deleteById)
	void delete(int id);
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(InvoiceInfo invoiceInfo);
	
	
	@Select("SELECT * FROM INVOICE_BILL WHERE ISENABLE = TRUE")
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoID", column = "PARTYINFOID"),
	      @Result(property = "invoicNum", column = "INVOICENUM"),
	      @Result(property = "invoicDate", column = "INVOICEDATE"),
	      @Result(property = "billNo", column = "BILL_NO"),
	      @Result(property = "orderBookedBy", column = "ORDERBOOKBY"),
	      @Result(property = "transport", column = "TRANSPORT"),
	      @Result(property = "pktNumber", column = "PACKETNUM"),
	      @Result(property = "billAmount", column = "BILLAMOUNT"), //GSTAMOUNT
	      @Result(property = "gstValue", column = "GSTAMOUNT"), //BILLAMOUNT
	      @Result(property = "grBiltyNumber", column = "GRNUMBER"), //BILLAMOUNT
	      
	      @Result(property = "col1Name", column = "COL1NAME"), 
	      @Result(property = "col2Name", column = "COL2NAME"), 
	      @Result(property = "col1Val", column = "COL1VAL"), 
	      @Result(property = "col2Val", column = "COL2VAL"), 
	      @Result(property = "col1Operator", column = "COL1OPERATOR"), 
	      @Result(property = "col2Operator", column = "COL2OPERATOR"), 
	      @Result(property = "withoutBillAmount", column = "WITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      
	      /*Release 3.6*/
	      @Result(property = "remainingBillAmount", column = "REMAININGBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "remainingWithoutBillAmount", column = "REMAININGWITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidBillAmount", column = "PAIDBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidWithoutBillAmount", column = "PAIDWITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidStatus", column = "PAIDSTATUS"), //WITHOUTBILLAMOUNT
	      @Result(property = "wpaidstatus", column = "WPAIDSTATUS"), //WITHOUTBILLAMOUNT
	      /*End*/
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATEDDATE"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      @Result(property = "items", javaType=List.class, column = "ID"
	      , many=@Many(select="getAllInvoiceItems")),
	})
	public List<InvoiceInfo> getAllWithChild();
	
	
	@Select("SELECT * FROM INVOICE_ITEM WHERE ISENABLE = TRUE AND invoiceID = #{ID}")
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "invoiceID", column = "INVOICEID"),
	      @Result(property = "stockItemId", column = "STOCKITEMID"),
	      @Result(property = "sno", column = "SNO"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "size", column = "SIZE"),
	      @Result(property = "qty", column = "QTY"),
	      @Result(property = "unit", column = "UNIT"),
	      @Result(property = "rate", column = "RATE"),
	      @Result(property = "amount", column = "AMOUNT"),
	      @Result(property = "td", column = "TD"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATEDDATE"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<InvoiceItemInfo> getAllInvoiceItems(Integer id);
	
	
	/*added to get account info as well*/
	@Select("SELECT * FROM INVOICE_BILL WHERE ISENABLE = TRUE")
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoID", column = "PARTYINFOID"),
	      @Result(property = "invoicNum", column = "INVOICENUM"),
	      @Result(property = "invoicDate", column = "INVOICEDATE"),
	      @Result(property = "billNo", column = "BILL_NO"),
	      @Result(property = "orderBookedBy", column = "ORDERBOOKBY"),
	      @Result(property = "transport", column = "TRANSPORT"),
	      @Result(property = "pktNumber", column = "PACKETNUM"),
	      @Result(property = "billAmount", column = "BILLAMOUNT"), //GSTAMOUNT
	      @Result(property = "gstValue", column = "GSTAMOUNT"), //BILLAMOUNT
	      @Result(property = "grBiltyNumber", column = "GRNUMBER"), //BILLAMOUNT
	      
	      @Result(property = "col1Name", column = "COL1NAME"), 
	      @Result(property = "col2Name", column = "COL2NAME"), 
	      @Result(property = "col1Val", column = "COL1VAL"), 
	      @Result(property = "col2Val", column = "COL2VAL"), 
	      @Result(property = "col1Operator", column = "COL1OPERATOR"), 
	      @Result(property = "col2Operator", column = "COL2OPERATOR"), 
	      @Result(property = "withoutBillAmount", column = "WITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      
	      /*Release 3.6*/
	      @Result(property = "remainingBillAmount", column = "REMAININGBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "remainingWithoutBillAmount", column = "REMAININGWITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidBillAmount", column = "PAIDBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidWithoutBillAmount", column = "PAIDWITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidStatus", column = "PAIDSTATUS"), //WITHOUTBILLAMOUNT
	      @Result(property = "wpaidstatus", column = "WPAIDSTATUS"), //WITHOUTBILLAMOUNT
	      /*End*/
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATEDDATE"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      @Result(property = "items", javaType=List.class, column = "ID"
	      , many=@Many(select="getAllInvoiceItems")),
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	})
	public List<InvoiceInfo> getAllWithChildAndAccount();
	
	@Select("SELECT * FROM ACCOUNT_INFO WHERE ID = #{PARTYINFOID}")
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "name", column = "NAME"),
	      @Result(property = "nickName", column = "NICKNAME"),
	      @Result(property = "gstNumber", column = "GST_TIN_NUMBER"),
	      @Result(property = "transport", column = "TRANSPORT"),
	      @Result(property = "phone", column = "PHONE"),
	      @Result(property = "houseShopNumber", column = "HOUSENUBER"),
	      @Result(property = "street", column = "STREET"),
	      @Result(property = "city", column = "CITY"),
	      @Result(property = "state", column = "STATE"),
	      @Result(property = "pinCode", column = "PINCODE"),
	      @Result(property = "country", column = "COUNTRY"),
	      @Result(property = "landMark", column = "LANDMARK"),
	      @Result(property = "engagedDate", column = "ENGAGEDATE"),
	      @Result(property = "panCard", column = "PAN"),
	      @Result(property = "licence", column = "LICENCE"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "imagePath", column = "IMAGEPATH"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public AccountInfo getAccountInfo();
	
	/*Since Release 3.3*/
	@Select("SELECT * FROM INVOICE_BILL WHERE TO_DATE(INVOICEDATE, 'DY DD/MM/YYYY')  BETWEEN #{fromDate,jdbcType=DATE} AND #{toDate, jdbcType=DATE}")
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoID", column = "PARTYINFOID"),
	      @Result(property = "invoicNum", column = "INVOICENUM"),
	      @Result(property = "invoicDate", column = "INVOICEDATE"),
	      @Result(property = "billNo", column = "BILL_NO"),
	      @Result(property = "orderBookedBy", column = "ORDERBOOKBY"),
	      @Result(property = "transport", column = "TRANSPORT"),
	      @Result(property = "pktNumber", column = "PACKETNUM"),
	      @Result(property = "billAmount", column = "BILLAMOUNT"), //GSTAMOUNT
	      @Result(property = "gstValue", column = "GSTAMOUNT"), //BILLAMOUNT
	      @Result(property = "grBiltyNumber", column = "GRNUMBER"), //BILLAMOUNT
	      
	      @Result(property = "col1Name", column = "COL1NAME"), 
	      @Result(property = "col2Name", column = "COL2NAME"), 
	      @Result(property = "col1Val", column = "COL1VAL"), 
	      @Result(property = "col2Val", column = "COL2VAL"), 
	      @Result(property = "col1Operator", column = "COL1OPERATOR"), 
	      @Result(property = "col2Operator", column = "COL2OPERATOR"), 
	      @Result(property = "withoutBillAmount", column = "WITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      
	      /*Release 3.6*/
	      @Result(property = "remainingBillAmount", column = "REMAININGBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "remainingWithoutBillAmount", column = "REMAININGWITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidBillAmount", column = "PAIDBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidWithoutBillAmount", column = "PAIDWITHOUTBILLAMOUNT"), //WITHOUTBILLAMOUNT
	      @Result(property = "paidStatus", column = "PAIDSTATUS"), //WITHOUTBILLAMOUNT
	      @Result(property = "wpaidstatus", column = "WPAIDSTATUS"), //WITHOUTBILLAMOUNT
	      /*End*/
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATEDDATE"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      @Result(property = "items", javaType=List.class, column = "ID"
	      , many=@Many(select="getAllInvoiceItems")),
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	})
	public List<InvoiceInfo> getAllWithChildAndAccountByDateRange(@Param("fromDate") Date fromDate, @Param("toDate")Date toDate);
	
}
