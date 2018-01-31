package leamon.erp.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import leamon.erp.model.StockItemQuantity;

/**
 * @author Leamon India Inc.
 * @date 29 MAY, 2017
 * 
 * @version 1.0
 * 
 * @SoftwareVersion : 3.1
 */

public interface StockQuantityMapper {
	final String getAll = "SELECT * FROM STOCK_ITEM_QUANTITY WHERE ISENABLE = TRUE ORDER BY ID"; 
	final String getById = "SELECT * FROM STOCK_ITEM_QUANTITY WHERE ID = #{id}";
	
	final String insert = "INSERT INTO STOCK_ITEM_QUANTITY (STOCK_ITEM_ID, QUANTITY, DESCRIPTION, CREATEDDATE, LASTUPDATEDDATE, ISENABLE) "
						+ "VALUES (#{stokItemid}, #{quantity}, #{description}, #{createdDate}, #{lastUpdatedDate}, #{isEnable} )";
	
	final String update = "UPDATE STOCK_ITEM_QUANTITY SET STOCK_ITEM_ID = #{stokItemid}, QUANTITY = #{quantity},  "
						+ "DESCRIPTION = #{description}, LASTUPDATEDDATE = #{lastUpdatedDate}  WHERE ID = #{id}";
	
	final String deleteById = "DELETE from STOCK_ITEM_QUANTITY WHERE ID = #{id}";
	
	final String disableByID = "UPDATE STOCK_ITEM_QUANTITY SET ISENABLE = FALSE WHERE ID = #{id}";
	
	//@Options(flushCache= FlushCachePolicy.TRUE)
	@Select(getAll)
	   @Results(value = {
	      @Result(property = "id", column = "ID"),
	      @Result(property = "stokItemid", column = "STOCK_ITEM_ID"),
	      @Result(property = "quantity", column = "QUANTITY"),
	      @Result(property = "description", column = "DESCRIPTION"),
	      @Result(property = "isEnable", column = "ISENABLE"),
	      @Result(property = "createdDate", column = "CREATEDDATE"),
	      @Result(property = "lastUpdatedDate", column = "LASTUPDATEDDATE")
	      
	})
	public List<StockItemQuantity> getAll() throws Exception;
	
	@Update(update)
	void update(StockItemQuantity stock) throws Exception;

	@Update(disableByID)
	void disableStockByID(StockItemQuantity stock) throws Exception;
	
	@Delete(deleteById)
	void delete(int id) throws Exception;
	  
	@Insert(insert)
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(StockItemQuantity stockItem) throws Exception;
	
}
