package leamon.erp.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.PaymentReceivedMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.PaymentReceivedInfo;
import lombok.Getter;
/**
 * Database Operation
 * @author Manish Kumar Mishra
 * @date 
 */

@Getter
public class PaymentReceivedDaoImpl implements  LeamonERPDao<PaymentReceivedInfo> {

	static final Logger LOGGER = Logger.getLogger(PaymentReceivedDaoImpl.class);
	private static PaymentReceivedDaoImpl INSTANCE;

	private PaymentReceivedDaoImpl (){
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}

	public static PaymentReceivedDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (PaymentReceivedDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new PaymentReceivedDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}

	@Override
	public List<PaymentReceivedInfo> getItemList() throws Exception {
		LOGGER.info("PaymentReceivedDaoImpl[getPaymentReceivedInfoList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentReceivedMapper PaymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		List<PaymentReceivedInfo> PaymentReceivedInfos = PaymentReceivedInfoMapper.getAll();
		session.close();
		LOGGER.info("PaymentReceivedDaoImpl[getPaymentReceivedInfoList] end.");
		return PaymentReceivedInfos;
	}

	@Override
	public void save(PaymentReceivedInfo PaymentReceivedInfo) throws Exception {
		LOGGER.info("PaymentReceivedDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentReceivedMapper PaymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		try{
			PaymentReceivedInfoMapper.insert(PaymentReceivedInfo);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("PaymentReceivedDaoImpl[save] id ["+PaymentReceivedInfo.getId()+"] after insert.");
		LOGGER.info("PaymentReceivedDaoImpl[save] end.");
		//return id;
	}

	@Override
	public void disable(PaymentReceivedInfo item) throws Exception{
		LOGGER.info("PaymentReceivedDaoImpl[delete] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentReceivedMapper PaymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		try{
			PaymentReceivedInfoMapper.disablepaymentReceivedInfoByID(item);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("PaymentReceivedDaoImpl[delete] end.");
	}

	@Override
	public void update(PaymentReceivedInfo item) throws Exception{
		LOGGER.info("PaymentReceivedDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentReceivedMapper PaymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		try{
			PaymentReceivedDaoImpl.getInstance().update(item);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("PaymentReceivedDaoImpl[update] end.");
	}




}
