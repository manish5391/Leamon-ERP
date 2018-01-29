package leamon.erp.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.OperationInfoMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.OperationInfo;
import lombok.Getter;

@Getter
public class OperationInfoDaoImpl implements LeamonERPDao<OperationInfo>{
	
	static final Logger LOGGER = Logger.getLogger(OperationInfoDaoImpl.class);
	private static OperationInfoDaoImpl INSTANCE;

	public static OperationInfoDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (OperationInfoDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new OperationInfoDaoImpl();
					
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<OperationInfo> getItemList() throws Exception{
		LOGGER.info("OperationInfoDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OperationInfoMapper accountInfoMapper= session.getMapper(OperationInfoMapper.class);
		List<OperationInfo> accountInfos = accountInfoMapper.getAll();
		session.close();
		LOGGER.info("OperationInfoDaoImpl[getItemList] end.");
		return accountInfos;
	}

	@Override
	public void save(OperationInfo item) throws Exception {}

	@Override
	public void disable(OperationInfo item) throws Exception{
		LOGGER.info("OperationInfoDaoImpl[disable] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OperationInfoMapper accountItemMapper= session.getMapper(OperationInfoMapper.class);
		try{
		accountItemMapper.disableByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("OperationInfoDaoImpl[disable] end.");
	}

	@Override
	public void update(OperationInfo item) throws Exception {
		
	}
	
	public OperationInfo getByParam(OperationInfo info){
		LOGGER.info("OperationInfoDaoImpl[getByParam] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		OperationInfoMapper operationInfoMapper= session.getMapper(OperationInfoMapper.class);
		info = operationInfoMapper.getbyParam(info);
		session.close();
		LOGGER.info("OperationInfoDaoImpl[getByParam] end.");
		return info;
	}
	
}
