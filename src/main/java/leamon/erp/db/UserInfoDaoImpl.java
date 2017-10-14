package leamon.erp.db;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.UserInfoMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.UserInfo;
import leamon.erp.model.UserInfo;
import lombok.Getter;
/**
 * Database Operation
 * @author Manish Kumar Mishra
 * @date 
 */

@Getter
public class UserInfoDaoImpl implements  LeamonERPDao<UserInfo> {
	
	static final Logger LOGGER = Logger.getLogger(UserInfoDaoImpl.class);
	private static UserInfoDaoImpl INSTANCE;
	
	private UserInfoDaoImpl (){
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}
	
	public static UserInfoDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (UserInfoDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new UserInfoDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<UserInfo> getItemList() throws Exception {
		LOGGER.info("LeamonERPDaoImpl[getUserInfoList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		UserInfoMapper UserInfoMapper= session.getMapper(UserInfoMapper.class);
		List<UserInfo> UserInfos = UserInfoMapper.getAll();
		session.close();
		LOGGER.info("LeamonERPDaoImpl[getUserInfoList] end.");
		
		return UserInfos;
	}
	

	@Override
	public void save(UserInfo UserInfo) throws Exception {
		LOGGER.info("LeamonERPDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		UserInfoMapper UserInfoMapper= session.getMapper(UserInfoMapper.class);
		try{
			UserInfoMapper.insert(UserInfo);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("LeamonERPDaoImpl[save] id ["+UserInfo.getId()+"] after insert.");
		LOGGER.info("LeamonERPDaoImpl[save] end.");
		//return id;
	}

	@Override
	public void disable(UserInfo item) throws Exception{
		LOGGER.info("LeamonERPDaoImpl[delete] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		UserInfoMapper UserInfoMapper= session.getMapper(UserInfoMapper.class);
		try{
		UserInfoMapper.disableStockByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("LeamonERPDaoImpl[delete] end.");
	}

	@Override
	public void update(UserInfo item) throws Exception{
		LOGGER.info("LeamonERPDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		UserInfoMapper UserInfoMapper= session.getMapper(UserInfoMapper.class);
		try{
		UserInfoMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("LeamonERPDaoImpl[update] end.");
	}
	
	public UserInfo matchUser(UserInfo item) throws Exception {
		LOGGER.info("LeamonERPDaoImpl[getUserInfoList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		UserInfoMapper UserInfoMapper= session.getMapper(UserInfoMapper.class);
		UserInfo userInfo = UserInfoMapper.matchUser(item);
		session.close();
		LOGGER.info("LeamonERPDaoImpl[getUserInfoList] end.");
		
		return userInfo;
	}
	
	
	
}
