package leamon.erp.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.AccountMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.AccountInfo;
import lombok.Getter;

@Getter
public class AccountDaoImpl implements LeamonERPDao<AccountInfo>{
	
	static final Logger LOGGER = Logger.getLogger(AccountDaoImpl.class);
	private static AccountDaoImpl INSTANCE;

	public static AccountDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (AccountDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new AccountDaoImpl();
					
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<AccountInfo> getItemList() throws Exception{
		LOGGER.info("AccountDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		AccountMapper accountInfoMapper= session.getMapper(AccountMapper.class);
		List<AccountInfo> accountInfos = accountInfoMapper.getAll();
		session.close();
		LOGGER.info("AccountDaoImpl[getItemList] end.");
		return accountInfos;
	}

	@Override
	public void save(AccountInfo item) throws Exception {
		LOGGER.info("AccountDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		AccountMapper accountMapper= session.getMapper(AccountMapper.class);
		try{
		accountMapper.insert(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("AccountDaoImpl[save] id ["+item.getId()+"] after insert.");
		LOGGER.info("AccountDaoImpl[save] end.");
	}

	@Override
	public void disable(AccountInfo item) throws Exception{
		LOGGER.info("AccountDaoImpl[disable] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		AccountMapper accountItemMapper= session.getMapper(AccountMapper.class);
		try{
		accountItemMapper.disableStockByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("AccountDaoImpl[disable] end.");
	}

	@Override
	public void update(AccountInfo item) throws Exception {
		LOGGER.info("AccountDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		AccountMapper accountMapper= session.getMapper(AccountMapper.class);
		try{
		accountMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("AccountDaoImpl[update] end.");
	}
	
	
	
}
