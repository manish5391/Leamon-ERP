package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.InvoiceItemInfo;


public interface InvoiceMapper {
	
	final String getAll = "SELECT * FROM INVOICE_BILL WHERE ISENABLE = TRUE ORDER BY ID";
	final String  getAllWithDisabled = "SELECT * FROM INVOICE_BILL ORDER BY ID";
	final String getById = "SELECT * FROM INVOICE_BILL WHERE ID = #{id}";
	
	final String insert = "INSERT INTO INVOICE_BILL (PARTYINFOID, INVOICENUM, INVOICEDATE, ORDERBOOKBY, TRANSPORT, PACKETNUM, GSTAMOUNT, BILLAMOUNT, "
							+ " CREATEDDATE, LASTUPDATEDDATE, ISENABLE) "
						+ "VALUES (#{partyinfoID}, #{invoicNum}, #{invoicDate}, #{orderBookedBy}, #{transport}, #{pktNumber}, #{gstValue}, #{billAmount}, "
						+ "#{createdDate}, #{lastUpdated}, #{isEnable})";
	
	final String update = "UPDATE INVOICE_BILL SET PARTYINFOID = #{partyinfoID}, INVOICENUM = #{invoicNum}, INVOICEDATE = #{invoicDate}, ORDERBOOKBY = #{orderBookedBy},  TRANSPORT = #{transport}, PACKETNUM = #{pktNumber}, GSTAMOUNT = #{gstValue}, "
						+ "BILLAMOUNT = #{billAmount}, "
						+ "LASTUPDATEDDATE = #{lastUpdated} "
						+ " WHERE ID = #{id}";
	
	final String deleteById = "DELETE from INVOICE_BILL WHERE ID = #{id}";
	
	final String disableByID = "UPDATE INVOICE_BILL SET ISENABLE = FALSE WHERE ID = #{id}";
	
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "partyinfoID", column = "PARTYINFOID"),
	      @Result(property = "invoicNum", column = "INVOICENUM"),
	      @Result(property = "invoicDate", column = "INVOICEDATE"),
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
	      @Result(property = "orderBookedBy", column = "ORDERBOOKBY"),
	      @Result(property = "transport", column = "TRANSPORT"),
	      @Result(property = "pktNumber", column = "PACKETNUM"),
	      @Result(property = "billAmount", column = "BILLAMOUNT"), //GSTAMOUNT
	      @Result(property = "gstValue", column = "GSTAMOUNT"), //BILLAMOUNT
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
	
}
