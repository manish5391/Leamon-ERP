package leamon.erp.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.StockItemQuantityHistoryMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.StockItemQuantityHistory;
import lombok.Getter;
/**
 * Database Operation
 * @author Leamon India Inc.
 * @date MAR 20,2018
 * 
 * @version 1.0
 * 
 * SoftwareVersion : 3.9
 * 
 */

@Getter
public class StockItemQuantityHistoryDaoImpl implements  LeamonERPDao<StockItemQuantityHistory> {
	
	static final Logger LOGGER = Logger.getLogger(StockItemQuantityHistoryDaoImpl.class);
	private static StockItemQuantityHistoryDaoImpl INSTANCE;
	
	
	private StockItemQuantityHistoryDaoImpl (){
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}
	
	public static StockItemQuantityHistoryDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (StockItemQuantityHistoryDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new StockItemQuantityHistoryDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<StockItemQuantityHistory> getItemList() throws Exception {
		LOGGER.info("StockItemQuantityHistoyDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockItemQuantityHistoryMapper stockItemQuantityHistoryMapper= session.getMapper(StockItemQuantityHistoryMapper.class);
		List<StockItemQuantityHistory> stockItemQuantityHistorys = stockItemQuantityHistoryMapper.getAll();
		session.close();
		LOGGER.info("StockItemQuantityHistoyDaoImpl[getStockItemQuantityHistoryList] end.");
		return stockItemQuantityHistorys;
	}
	
	

	@Override
	public void save(StockItemQuantityHistory stockItemQuantityHistory) throws Exception {
		LOGGER.info("StockItemQuantityHistoyDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockItemQuantityHistoryMapper stockItemQuantityHistoryMapper= session.getMapper(StockItemQuantityHistoryMapper.class);
		try{
			stockItemQuantityHistoryMapper.insert(stockItemQuantityHistory);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockItemQuantityHistoyDaoImpl[save] id ["+stockItemQuantityHistory.getId()+"] after insert.");
		LOGGER.info("StockItemQuantityHistoyDaoImpl[save] end.");
	}

	@Override
	public void disable(StockItemQuantityHistory item) throws Exception{
		LOGGER.info("StockItemQuantityHistoyDaoImpl[delete] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockItemQuantityHistoryMapper stockItemQuantityHistoryMapper= session.getMapper(StockItemQuantityHistoryMapper.class);
		try{
			stockItemQuantityHistoryMapper.disableStockByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockItemQuantityHistoyDaoImpl[delete] end.");
	}

	@Override
	public void update(StockItemQuantityHistory item) throws Exception{
		LOGGER.info("StockItemQuantityHistoyDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockItemQuantityHistoryMapper stockItemQuantityHistoryMapper= session.getMapper(StockItemQuantityHistoryMapper.class);
		try{
			stockItemQuantityHistoryMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockItemQuantityHistoyDaoImpl[update] end.");
	}
}
