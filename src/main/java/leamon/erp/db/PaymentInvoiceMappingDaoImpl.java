package leamon.erp.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.PaymentInvoiceMappingMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.PaymentInvoiceMappingInfo;
import lombok.Getter;

@Getter
public class PaymentInvoiceMappingDaoImpl implements LeamonERPDao<PaymentInvoiceMappingInfo>{
	
	static final Logger LOGGER = Logger.getLogger(PaymentInvoiceMappingDaoImpl.class);
	private static PaymentInvoiceMappingDaoImpl INSTANCE;

	public static PaymentInvoiceMappingDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (PaymentInvoiceMappingDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new PaymentInvoiceMappingDaoImpl();
					
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<PaymentInvoiceMappingInfo> getItemList() throws Exception{
		LOGGER.info("PaymentInvoiceMappingDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentInvoiceMappingMapper paymentInvoiceInfoMapper= session.getMapper(PaymentInvoiceMappingMapper.class);
		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos = paymentInvoiceInfoMapper.getAll();
		session.close();
		LOGGER.info("PaymentInvoiceMappingDaoImpl[getItemList] end.");
		return paymentInvoiceMappingInfos;
	}

	@Override
	public void save(PaymentInvoiceMappingInfo item) throws Exception {
		LOGGER.info("PaymentInvoiceMappingDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentInvoiceMappingMapper paymentInvoiceInfoMapper= session.getMapper(PaymentInvoiceMappingMapper.class);
		try{
		paymentInvoiceInfoMapper.insert(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("PaymentInvoiceMappingDaoImpl[save] id ["+item.getId()+"] after insert.");
		LOGGER.info("PaymentInvoiceMappingDaoImpl[save] end.");
	}

	@Override
	public void disable(PaymentInvoiceMappingInfo item) throws Exception{
		LOGGER.info("PaymentInvoiceMappingDaoImpl[disable] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentInvoiceMappingMapper accountItemMapper= session.getMapper(PaymentInvoiceMappingMapper.class);
		try{
		accountItemMapper.disableByID(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("PaymentInvoiceMappingDaoImpl[disable] end.");
	}

	@Override
	public void update(PaymentInvoiceMappingInfo item) throws Exception {
		LOGGER.info("PaymentInvoiceMappingDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentInvoiceMappingMapper paymentInvoiceInfoMapper= session.getMapper(PaymentInvoiceMappingMapper.class);
		try{
		paymentInvoiceInfoMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("PaymentInvoiceMappingDaoImpl[update] end.");
	}
	
	
	public List<PaymentInvoiceMappingInfo> getItemListByPaymentId(Integer paymentReceivedId) throws Exception{
		LOGGER.info("PaymentInvoiceMappingDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		PaymentInvoiceMappingMapper paymentInvoiceInfoMapper= session.getMapper(PaymentInvoiceMappingMapper.class);
		List<PaymentInvoiceMappingInfo> paymentInvoiceMappingInfos = paymentInvoiceInfoMapper.getAllByPaymentId(paymentReceivedId);
		session.close();
		LOGGER.info("PaymentInvoiceMappingDaoImpl[getItemList] end.");
		return paymentInvoiceMappingInfos;
	}
}
