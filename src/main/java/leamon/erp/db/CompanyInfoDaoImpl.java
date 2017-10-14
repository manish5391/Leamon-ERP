package leamon.erp.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.CompanyInfoMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.CompanyInfo;
import lombok.Getter;

@Getter
public class CompanyInfoDaoImpl implements LeamonERPDao<CompanyInfo>{
	
	static final Logger LOGGER = Logger.getLogger(CompanyInfoDaoImpl.class);
	private static CompanyInfoDaoImpl INSTANCE;

	public static CompanyInfoDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (CompanyInfoDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new CompanyInfoDaoImpl();
					
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<CompanyInfo> getItemList() throws Exception{
		LOGGER.info("CompanyInfoDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		CompanyInfoMapper companyInfoDaoImplMapper= session.getMapper(CompanyInfoMapper.class);
		List<CompanyInfo> CompanyInfoDaoImpls = companyInfoDaoImplMapper.getAll();
		session.close();
		LOGGER.info("CompanyInfoDaoImpl[getItemList] end.");
		return CompanyInfoDaoImpls;
	}

	@Override
	public void save(CompanyInfo item) throws Exception {
		LOGGER.info("CompanyInfoDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		CompanyInfoMapper CompanyInfoMapper= session.getMapper(CompanyInfoMapper.class);
		try{
		CompanyInfoMapper.insert(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("CompanyInfoDaoImpl[save] id ["+item.getId()+"] after insert.");
		LOGGER.info("CompanyInfoDaoImpl[save] end.");
	}

	@Override
	public void disable(CompanyInfo item) throws Exception{
		LOGGER.info("CompanyInfoDaoImpl[disable] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		CompanyInfoMapper accountItemMapper= session.getMapper(CompanyInfoMapper.class);
		try{
		accountItemMapper.disableStockByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("CompanyInfoDaoImpl[disable] end.");
	}

	@Override
	public void update(CompanyInfo item) throws Exception {
		LOGGER.info("CompanyInfoDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		CompanyInfoMapper CompanyInfoMapper= session.getMapper(CompanyInfoMapper.class);
		try{
		CompanyInfoMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("CompanyInfoDaoImpl[update] end.");
	}

}
