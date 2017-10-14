package leamon.erp.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.StateCityInfoMapper;
import leamon.erp.db.mapper.StockMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.StateCityInfo;

public class StateCityDaoImpl implements  LeamonERPDao<StateCityInfo>{

	static final Logger LOGGER = Logger.getLogger(StateCityDaoImpl.class);
	private static StateCityDaoImpl INSTANCE;
	
	public static StateCityDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (StockDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new StateCityDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<StateCityInfo> getItemList() throws Exception {
		LOGGER.info("StateCityDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StateCityInfoMapper stateCityMapper= session.getMapper(StateCityInfoMapper.class);
		List<StateCityInfo> stateCityItems = stateCityMapper.getAll();
		session.close();
		LOGGER.info("StateCityDaoImpl[getItemList]");
		return stateCityItems;
	}

	@Override
	public void save(StateCityInfo item) throws Exception {
		
	}

	@Override
	public void disable(StateCityInfo item) throws Exception {
		
	}

	@Override
	public void update(StateCityInfo item) throws Exception {
		
	}
	
}
