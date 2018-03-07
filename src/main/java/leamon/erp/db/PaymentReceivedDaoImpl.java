package leamon.erp.db;

import java.util.Date;
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
		PaymentReceivedMapper paymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		List<PaymentReceivedInfo> paymentReceivedInfos = paymentReceivedInfoMapper.getAll();
		session.close();
		LOGGER.info("PaymentReceivedDaoImpl[getPaymentReceivedInfoList] end.");
		return paymentReceivedInfos;
	}
	
	/**
	 * @date Mar 07,18 
	 * @author Manish Kumar Mishra
	 */
	public List<PaymentReceivedInfo> getItemListWithAccountInfo() throws Exception {
		LOGGER.info("PaymentReceivedDaoImpl[getPaymentReceivedInfoList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentReceivedMapper paymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		List<PaymentReceivedInfo> paymentReceivedInfos = paymentReceivedInfoMapper.getAllWithAccountInfo();
		session.close();
		LOGGER.info("PaymentReceivedDaoImpl[getPaymentReceivedInfoList] end.");
		return paymentReceivedInfos;
	}

	@Override
	public void save(PaymentReceivedInfo PaymentReceivedInfo) throws Exception {
		LOGGER.info("PaymentReceivedDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentReceivedMapper paymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		try{
			paymentReceivedInfoMapper.insert(PaymentReceivedInfo);
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
		PaymentReceivedMapper paymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		try{
			paymentReceivedInfoMapper.disablepaymentReceivedInfoByID(item);
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
		PaymentReceivedMapper paymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		try{
			paymentReceivedInfoMapper.update(item);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("PaymentReceivedDaoImpl[update] end.");
	}
	
	/*------------Release 3.8 : Filter criteria----------------*/
	public List<PaymentReceivedInfo> getItemListByPartyName(String partyInfoID) throws Exception {
		LOGGER.info("PaymentReceivedDaoImpl[getItemListByPartyName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentReceivedMapper paymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		List<PaymentReceivedInfo> paymentReceivedInfos = paymentReceivedInfoMapper.getAllByPartyName(partyInfoID);
		session.close();
		LOGGER.info("PaymentReceivedDaoImpl[getPaymentReceivedInfoList] end.");
		return paymentReceivedInfos;
	}
	
	public List<PaymentReceivedInfo> getItemListByStartDate(Date startDate) throws Exception {
		LOGGER.info("PaymentReceivedDaoImpl[getItemListByPartyName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentReceivedMapper paymentReceivedInfoMapper= session.getMapper(PaymentReceivedMapper.class);
		List<PaymentReceivedInfo> paymentReceivedInfos = paymentReceivedInfoMapper.getAllByStartDate(startDate);
		session.close();
		LOGGER.info("PaymentReceivedDaoImpl[getPaymentReceivedInfoList] end.");
		return paymentReceivedInfos;
	}

	/*---------------End---------------*/
}
