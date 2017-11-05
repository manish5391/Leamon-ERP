package leamon.erp.db;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.StockQuantityOrderHistoryMapper;
import leamon.erp.db.mapper.StockQuantityOrderHistoryMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.StockItemQuantityOrderHistory;
import lombok.Getter;
/**
 * Database Operation
 * @author Leamon India Inc.
 * @date OCT 28,2017
 * 
 * @version 1.0
 * 
 * SoftwareVersion : 3.1
 * 
 */

@Getter
public class StockQuantityOrderHistoyDaoImpl implements  LeamonERPDao<StockItemQuantityOrderHistory> {
	
	static final Logger LOGGER = Logger.getLogger(StockQuantityOrderHistoyDaoImpl.class);
	private static StockQuantityOrderHistoyDaoImpl INSTANCE;
	
	
	private StockQuantityOrderHistoyDaoImpl (){
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}
	
	public static StockQuantityOrderHistoyDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (StockQuantityOrderHistoyDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new StockQuantityOrderHistoyDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<StockItemQuantityOrderHistory> getItemList() throws Exception {
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockQuantityOrderHistoryMapper StockItemQuantityOrderHistoryMapper= session.getMapper(StockQuantityOrderHistoryMapper.class);
		List<StockItemQuantityOrderHistory> StockItemQuantityOrderHistorys = StockItemQuantityOrderHistoryMapper.getAll();
		session.close();
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[getStockItemQuantityOrderHistoryList] end.");
		return StockItemQuantityOrderHistorys;
	}
	
	

	@Override
	public void save(StockItemQuantityOrderHistory StockItemQuantityOrderHistory) throws Exception {
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockQuantityOrderHistoryMapper StockItemQuantityOrderHistoryMapper= session.getMapper(StockQuantityOrderHistoryMapper.class);
		try{
			StockItemQuantityOrderHistoryMapper.insert(StockItemQuantityOrderHistory);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[save] id ["+StockItemQuantityOrderHistory.getId()+"] after insert.");
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[save] end.");
	}

	@Override
	public void disable(StockItemQuantityOrderHistory item) throws Exception{
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[delete] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockQuantityOrderHistoryMapper StockItemQuantityOrderHistoryMapper= session.getMapper(StockQuantityOrderHistoryMapper.class);
		try{
		StockItemQuantityOrderHistoryMapper.disableStockByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[delete] end.");
	}

	@Override
	public void update(StockItemQuantityOrderHistory item) throws Exception{
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockQuantityOrderHistoryMapper StockItemQuantityOrderHistoryMapper= session.getMapper(StockQuantityOrderHistoryMapper.class);
		try{
		StockItemQuantityOrderHistoryMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockQuantityOrderHistoyDaoImpl[update] end.");
	}
	
}
