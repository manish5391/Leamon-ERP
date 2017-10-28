package leamon.erp.db;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.StockQuantityMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.StockItemQuantity;
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
public class StockQuantityDaoImpl implements  LeamonERPDao<StockItemQuantity> {
	
	static final Logger LOGGER = Logger.getLogger(StockQuantityDaoImpl.class);
	private static StockQuantityDaoImpl INSTANCE;
	
	
	private StockQuantityDaoImpl (){
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}
	
	public static StockQuantityDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (StockQuantityDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new StockQuantityDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<StockItemQuantity> getItemList() throws Exception {
		LOGGER.info("StockQuantityDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockQuantityMapper StockItemQuantityMapper= session.getMapper(StockQuantityMapper.class);
		List<StockItemQuantity> StockItemQuantitys = StockItemQuantityMapper.getAll();
		session.close();
		LOGGER.info("StockQuantityDaoImpl[getStockItemQuantityList] end.");
		return StockItemQuantitys;
	}
	
	

	@Override
	public void save(StockItemQuantity StockItemQuantity) throws Exception {
		LOGGER.info("StockQuantityDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockQuantityMapper stockItemQuantityMapper= session.getMapper(StockQuantityMapper.class);
		try{
			stockItemQuantityMapper.insert(StockItemQuantity);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockQuantityDaoImpl[save] id ["+StockItemQuantity.getId()+"] after insert.");
		LOGGER.info("StockQuantityDaoImpl[save] end.");
	}

	@Override
	public void disable(StockItemQuantity item) throws Exception{
		LOGGER.info("StockQuantityDaoImpl[delete] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockQuantityMapper StockItemQuantityMapper= session.getMapper(StockQuantityMapper.class);
		try{
		StockItemQuantityMapper.disableStockByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockQuantityDaoImpl[delete] end.");
	}

	@Override
	public void update(StockItemQuantity item) throws Exception{
		LOGGER.info("StockQuantityDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockQuantityMapper StockItemQuantityMapper= session.getMapper(StockQuantityMapper.class);
		try{
		StockItemQuantityMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("StockQuantityDaoImpl[update] end.");
	}

	
	
	
}
