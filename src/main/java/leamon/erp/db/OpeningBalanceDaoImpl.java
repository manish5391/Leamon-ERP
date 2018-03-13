package leamon.erp.db;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.OpeningBalanceMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.OpeningBalanceInfo;
import lombok.Getter;

@Getter
public class OpeningBalanceDaoImpl implements LeamonERPDao<OpeningBalanceInfo>{
	
	static final Logger LOGGER = Logger.getLogger(OpeningBalanceDaoImpl.class);
	private static OpeningBalanceDaoImpl INSTANCE;

	public static OpeningBalanceDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (OpeningBalanceDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new OpeningBalanceDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<OpeningBalanceInfo> getItemList() throws Exception{
		LOGGER.info("OpeningBalanceDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalInfoMapper= session.getMapper(OpeningBalanceMapper.class);
		List<OpeningBalanceInfo> openingBalanceInfos = openingBalInfoMapper.getAll();
		session.close();
		LOGGER.info("OpeningBalanceDaoImpl[getItemList] end.");
		return openingBalanceInfos;
	}

	@Override
	public void save(OpeningBalanceInfo item) throws Exception {
		LOGGER.info("OpeningBalanceDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalanceMapper= session.getMapper(OpeningBalanceMapper.class);
		try{
		openingBalanceMapper.insert(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("OpeningBalanceDaoImpl[save] id ["+item.getId()+"] after insert.");
		LOGGER.info("OpeningBalanceDaoImpl[save] end.");
	}

	@Override
	public void disable(OpeningBalanceInfo item) throws Exception{
		LOGGER.info("OpeningBalanceDaoImpl[disable] inside.");
		/*SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalanceMapper= session.getMapper(OpeningBalanceMapper.class);
		try{
		openingBalanceMapper.disableStockByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}*/
		LOGGER.info("OpeningBalanceDaoImpl[disable] end.");
	}

	@Override
	public void update(OpeningBalanceInfo item) throws Exception {
		LOGGER.info("OpeningBalanceDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalanceMapper= session.getMapper(OpeningBalanceMapper.class);
		try{
		openingBalanceMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("OpeningBalanceDaoImpl[update] end.");
	}
	
	/*-------Release 3.8---Filtere Criteria Sql----------------*/
	public List<OpeningBalanceInfo> getAllOpeningBalanceByPartyName(String partyInfoID) throws Exception{
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByPartyName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalInfoMapper= session.getMapper(OpeningBalanceMapper.class);
		List<OpeningBalanceInfo> openingBalanceInfos = openingBalInfoMapper.getAllOpeningBalanceByPartyName(partyInfoID);
		session.close();
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByPartyName] end.");
		return openingBalanceInfos;
	}
	
	public List<OpeningBalanceInfo> getAllOpeningBalanceByStartDate(java.util.Date startDate) throws Exception{
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByStartDate] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalInfoMapper= session.getMapper(OpeningBalanceMapper.class);
		List<OpeningBalanceInfo> openingBalanceInfos = openingBalInfoMapper.getAllOpeningBalanceByStartDate(startDate);
		session.close();
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByStartDate] end.");
		return openingBalanceInfos;
	}
	
	public List<OpeningBalanceInfo> getAllOpeningBalanceByStartEndDate(Date startDate, Date endDate) throws Exception{
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByStartEndDate] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalInfoMapper= session.getMapper(OpeningBalanceMapper.class);
		List<OpeningBalanceInfo> openingBalanceInfos = openingBalInfoMapper.getAllOpeningBalanceByStartEndDate(startDate,endDate);
		session.close();
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByStartEndDate] end.");
		return openingBalanceInfos;
	}
	
	public List<OpeningBalanceInfo> getAllOpeningBalanceByStartDatePartyName(Date startDate, String partyInfoID) throws Exception{
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByStartDatePartyName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalInfoMapper= session.getMapper(OpeningBalanceMapper.class);
		List<OpeningBalanceInfo> openingBalanceInfos = openingBalInfoMapper.getAllOpeningBalanceByStartDatePartyName(startDate,partyInfoID);
		session.close();
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByStartDatePartyName] end.");
		return openingBalanceInfos;
	}
	
	public List<OpeningBalanceInfo> getAllOpeningBalanceByStartEndDatePartyName(Date startDate, Date endDate, String partyInfoID) throws Exception{
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByPartyName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OpeningBalanceMapper openingBalInfoMapper= session.getMapper(OpeningBalanceMapper.class);
		List<OpeningBalanceInfo> openingBalanceInfos = openingBalInfoMapper
				.getAllOpeningBalanceByStartEndDatePartyName(startDate, endDate, partyInfoID);
		session.close();
		LOGGER.info("OpeningBalanceDaoImpl[getAllOpeningBalanceByPartyName] end.");
		return openingBalanceInfos;
	}
	/*----------End----------------*/
}
