package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;
import leamon.erp.model.OpeningBalanceInfo;
import leamon.erp.model.PaymentInvoiceMappingInfo;

//@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface PaymentInvoiceMappingMapper {
	
	final String getAll = "SELECT * FROM PAYMENT_INVOICE_MAPPING WHERE ISENABLE = TRUE"; 
	final String getById = "SELECT * FROM PAYMENT_INVOICE_MAPPING WHERE ID = #{id}";
	
	final String insert = "INSERT INTO PAYMENT_INVOICE_MAPPING (PAYMENTID, INVOICEID, OPENINGBALANCEID, AMOUNT, CREATEDDATE, LASTUPDATED, ISENABLE) VALUES ( #{paymentReceivedInfo}, #{invoiceInfoID}, #{openingBalanceID}, #{amount}, #{createdDate}, #{lastUpdated}, #{isEnable})";
	
	final String update = "UPDATE PAYMENT_INVOICE_MAPPING SET PAYMENTID = #{paymentReceivedInfo}, INVOICEID = #{invoiceInfoID}, "
						+ "OPENINGBALANCEID = #{openingBalanceID}, LASTUPDATED = #{lastUpdated} "
						+ " WHERE ID = #{id}";
	
	final String deleteById = "DELETE from PAYMENT_INVOICE_MAPPING WHERE ID = #{id}";
	
	final String disableByID = "UPDATE PAYMENT_INVOICE_MAPPING SET ISENABLE = FALSE WHERE ID = #{id}";
	
	/*Release 3.9*/
	final String getAllByPamentID = "SELECT * FROM PAYMENT_INVOICE_MAPPING WHERE ISENABLE = TRUE AND PAYMENTID = #{paymentReceivedId}";
	/*--End--*/
	

	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      
	      @Result(property = "paymentReceivedInfo", column = "PAYMENTID"),
	      @Result(property = "invoiceInfoID", column = "INVOICEID"),
	      @Result(property = "openingBalanceID", column = "OPENINGBALANCEID"),
	      @Result(property = "amount", column = "AMOUNT"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<PaymentInvoiceMappingInfo> getAll();
	
	@Update(update)
	void update(PaymentInvoiceMappingInfo paymentInvoiceMappingInfo);

	@Update(disableByID)
	void disableByID(PaymentInvoiceMappingInfo paymentInvoiceMappingInfo);
	
	@Delete(deleteById)
	void delete(int id);
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(PaymentInvoiceMappingInfo paymentInvoiceMappingInfo);
	
	@Select(getAllByPamentID)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      
	      @Result(property = "paymentReceivedInfo", column = "PAYMENTID"),
	      
	      @Result(property = "invoiceInfoID", column = "INVOICEID"),
	      
	      @Result(property = "invoiceInfo", javaType=InvoiceInfo.class, column = "INVOICEID"
	      ,one =@One(select="getAllWithChildAndAccount")),
	      
	      @Result(property = "openingBalanceID", column = "OPENINGBALANCEID"),
	      @Result(property = "openigBalanceInfo", javaType=OpeningBalanceInfo.class, column = "OPENINGBALANCEID"
	      , one =@One(select="getAllOpeningBal")),
	      
	      @Result(property = "amount", column = "AMOUNT"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE")
	})
	public List<PaymentInvoiceMappingInfo> getAllByPaymentId(Integer paymentReceivedId);
	
	
	/*added to get account info as well*/
	@Select("SELECT * FROM INVOICE_BILL WHERE ISENABLE = TRUE AND id = #{INVOICEID}")
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
	public InvoiceInfo getAllWithChildAndAccount();
	
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
	
	/*----Opening Balance----*/
	@Select("SELECT * FROM OPENING_BALANCE_INFO WHERE ISENABLE = TRUE AND ID = #{OPENINGBALANCEID}")
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoid", column = "PARTYINFOID"),
	      @Result(property = "billnumber", column = "BILLNUMBER"),
	      @Result(property = "billdate", column = "BILLDATE"),
	      @Result(property = "openingbalanceamount", column = "OPENINGBALANCEAMOUNT"),
	      @Result(property = "receivedopeningbalanceamount", column = "RECEIVEDOPENINGBALANCEAMOUNT"),
	      @Result(property = "remainingopeningbalanceamount", column = "REMAININGOPENINGBALANCEAMOUNT"),
	      @Result(property = "remark", column = "REMARK"),
	      @Result(property = "type", column = "TYPE"),
	      @Result(property = "status", column = "STATUS"),
	      
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdated", column = "LASTUPDATED"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      
	      @Result(property = "accountInfo", javaType=AccountInfo.class, column = "PARTYINFOID"
	      , one =@One(select="getAccountInfo"))
	      
	})
	public OpeningBalanceInfo getAllOpeningBal() throws Exception;
}
