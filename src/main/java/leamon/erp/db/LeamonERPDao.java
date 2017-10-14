package leamon.erp.db;

import java.util.List;
/**
 * 
 * @author Manish Kumar Mishra
 * @date 03 May,2017
 */
public interface LeamonERPDao<T> {
	
	List<T> getItemList() throws Exception;
	void save(T item) throws Exception;
	void disable(T item) throws Exception;
	void update(T item) throws Exception;
}
