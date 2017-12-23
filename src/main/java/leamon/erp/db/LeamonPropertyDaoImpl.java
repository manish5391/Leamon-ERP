package leamon.erp.db;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.LeamonPropertyMapper;
import leamon.erp.db.mapper.LeamonPropertyMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.LeamonProperty;
import leamon.erp.model.LeamonProperty;
import lombok.Getter;
/**
 * Database Operation
 * @author Manish Kumar Mishra
 * @date  23 DEC 2017
 */

@Getter
public class LeamonPropertyDaoImpl implements  LeamonERPDao<LeamonProperty> {
	
	static final Logger LOGGER = Logger.getLogger(LeamonPropertyDaoImpl.class);
	private static LeamonPropertyDaoImpl INSTANCE;
	
	
	private LeamonPropertyDaoImpl (){
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}
	
	public static LeamonPropertyDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (LeamonPropertyDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new LeamonPropertyDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<LeamonProperty> getItemList() throws Exception {
		LOGGER.info("LeamonERPDaoImpl[getStockItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		LeamonPropertyMapper leamonPropertyMapper= session.getMapper(LeamonPropertyMapper.class);
		List<LeamonProperty> stockItems = leamonPropertyMapper.getAll();
		session.close();
		LOGGER.info("LeamonERPDaoImpl[getStockItemList] end.");
		return stockItems;
	}
	
	
	@Override
	public void save(LeamonProperty stockItem) throws Exception {}

	@Override
	public void disable(LeamonProperty item) throws Exception{}

	@Override
	public void update(LeamonProperty item) throws Exception{}
	
}
