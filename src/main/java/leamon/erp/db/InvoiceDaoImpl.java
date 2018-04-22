package leamon.erp.db;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.InvoiceMapper;
import leamon.erp.db.mapper.StockMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.StockItem;
import lombok.Getter;

@Getter
public class InvoiceDaoImpl  implements LeamonERPDao<InvoiceInfo>{

	static final Logger LOGGER = Logger.getLogger(InvoiceDaoImpl.class);
	private static InvoiceDaoImpl INSTANCE;

	public static InvoiceDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (InvoiceDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new InvoiceDaoImpl();

				}
			}
		}//end if
		return INSTANCE;
	}

	@Override
	public List<InvoiceInfo> getItemList() throws Exception{
		LOGGER.info("InvoiceDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAll();
		session.close();
		LOGGER.info("InvoiceDaoImpl[getItemList] end.");
		return InvoiceInfos;
	}
	
	public List<InvoiceInfo> getItemListIncludeDisabled() {
		LOGGER.info("InvoiceDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllWithDisabled();
		session.close();
		LOGGER.info("InvoiceDaoImpl[getItemList] end.");
		return InvoiceInfos;
	}

	@Override
	public void save(InvoiceInfo item) throws Exception {

		LOGGER.info("InvoiceDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper InvoiceMapper= session.getMapper(InvoiceMapper.class);
		try{
			InvoiceMapper.insert(item);
			session.commit();
			LOGGER.info("InvoiceDaoImpl[save] id ["+item.getId()+"] after insert.");
		}catch(Throwable t){
			throw t;
		}finally{
			session.close();
		}
		LOGGER.info("InvoiceDaoImpl[save] end.");

	}

	@Override
	public void disable(InvoiceInfo item) throws Exception{
		LOGGER.info("InvoiceDaoImpl[disable] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceMapper= session.getMapper(InvoiceMapper.class);
		try{
		invoiceMapper.disableInvoiceById(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("InvoiceDaoImpl[disable] end.");
	}

	@Override
	public void update(InvoiceInfo item) throws Exception{
		LOGGER.info("InvoiceDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper InvoiceMapper= session.getMapper(InvoiceMapper.class);
		try{
		InvoiceMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("InvoiceDaoImpl[update] end.");
	}

	public void updatePaidBillAmount(InvoiceInfo item) throws Exception{
		LOGGER.info("InvoiceDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper InvoiceMapper= session.getMapper(InvoiceMapper.class);
		try{
		InvoiceMapper.updatePaidBillAmount(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("InvoiceDaoImpl[update] end.");
	}
	
	public void updatePaidWBillAmount(InvoiceInfo item) throws Exception{
		LOGGER.info("InvoiceDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceMapper= session.getMapper(InvoiceMapper.class);
		try{
		invoiceMapper.updatePaidWBillAmount(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("InvoiceDaoImpl[update] end.");
	}
	
	public List<InvoiceInfo> getItemListWithInvoiceItemList() {
		LOGGER.info("InvoiceDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllWithChild();
		session.close();
		LOGGER.info("InvoiceDaoImpl[getItemList] end.");
		return InvoiceInfos;
	}
	
	public List<InvoiceInfo> getItemListWithInvoiceItemListAndAccountInfo() {
		LOGGER.info("InvoiceDaoImpl[getItemListWithInvoiceItemListAndAccountInfo] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllWithChildAndAccount();
		session.close();
		LOGGER.info("InvoiceDaoImpl[getItemListWithInvoiceItemListAndAccountInfo] end.");
		return InvoiceInfos;
	}
	
	//public List<InvoiceInfo> getAllWithChildAndAccountByDateRange(String fromDate, String toDate) {
	public List<InvoiceInfo> getAllWithChildAndAccountByDateRange(java.sql.Date fromDate, java.sql.Date toDate) {
		LOGGER.info("InvoiceDaoImpl[getAllWithChildAndAccountByDateRange] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllWithChildAndAccountByDateRange(fromDate, toDate);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllWithChildAndAccountByDateRange] end.");
		return InvoiceInfos;
	}
	

	/*-------Release 3.8---Criteria Sql---------------*/
	public List<InvoiceInfo> getAllInvoiceByPartyName(String partyinfoID)throws Exception{
		LOGGER.info("InvoiceDaoImpl[getInvoicByPartyName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllInvoiceByPartyName(partyinfoID);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getInvoicByPartyName] end.");
		return InvoiceInfos;
	}
	public List<InvoiceInfo> getAllInvoiceByStartDate(Date startDate)throws Exception{
		LOGGER.info("InvoiceDaoImpl[getInvoicByPartyName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllInvoiceByStartDate(startDate);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getInvoicByPartyName] end.");
		return InvoiceInfos;
	}
	public List<InvoiceInfo> getAllInvoiceByStartEndDate(Date startDate,Date endDate)throws Exception{
		LOGGER.info("InvoiceDaoImpl[getAllInvoiceByStartEndDate] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllInvoiceByStartEndDate(startDate,endDate);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllInvoiceByStartEndDate] end.");
		return InvoiceInfos;
	}
	public List<InvoiceInfo> getAllInvoiceByStartDatePartName(Date startDate, String partyInfoID)throws Exception{
		LOGGER.info("InvoiceDaoImpl[getAllInvoiceByStartDatePartName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllInvoiceByStartDatePartName(startDate,partyInfoID);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllInvoiceByStartDatePartName] end.");
		return InvoiceInfos;
	}
	public List<InvoiceInfo> getAllInvoiceByStartEndDatePartyName(Date startDate, Date endDate, String partyInfoID)throws Exception{
		LOGGER.info("InvoiceDaoImpl[getAllInvoiceByStartEndDatePartName] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllInvoiceByStartEndDatePartName(startDate,endDate,partyInfoID);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllInvoiceByStartEndDatePartName] end.");
		return InvoiceInfos;
	}
	/*----------End------------------------*/

	// 3.8 ghan code
	public List<InvoiceInfo> getAllByPartyInfoID(int id) throws Exception {
		LOGGER.info("InvoiceDaoImpl[getAllByPartyInfoID] inside.");
		SqlSession session = MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper = session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllByPartyInfoID(id);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllByPartyInfoID] end.");
		return InvoiceInfos;
	}

	public List<InvoiceInfo> getAllByStartDate(Date startDate) throws Exception {
		LOGGER.info("InvoiceDaoImpl[getAllByStartDate] inside.");
		SqlSession session = MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper = session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllByStartDate(startDate);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllByStartDate] end.");
		return InvoiceInfos;
	}

	public List<InvoiceInfo> getAllByStartEndDate(Date startDate, Date endDate) throws Exception {
		LOGGER.info("InvoiceDaoImpl[getAllByStartEndDate] inside.");
		SqlSession session = MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper = session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllByStartEndDate(startDate, endDate);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllByStartEndDate] end.");
		return InvoiceInfos;
	}

	public List<InvoiceInfo> getAllByStartEndDatePartyInfoID(Date startDate, Date endDate, int id) throws Exception {
		LOGGER.info("InvoiceDaoImpl[getAllByStartEndDatePartyInfoID] inside.");
		SqlSession session = MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper = session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllByStartEndDatePartyInfoID(startDate, endDate, id);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllByStartEndDatePartyInfoID] end.");
		return InvoiceInfos;
	}

	public List<InvoiceInfo> getAllByStartDateAndPartyName(Date startDate, int id) throws Exception {
		LOGGER.info("InvoiceDaoImpl[getAllByStartDateAndPartyName] inside.");
		SqlSession session = MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper = session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllByStartDateAndPartyName(startDate, id);
		session.close();
		LOGGER.info("InvoiceDaoImpl[getAllByStartDateAndPartyName] end.");
		return InvoiceInfos;
	}
	// 3.8 end of ghan code
	
	public List<InvoiceInfo> getItemDisabledOnlyListWithInvoiceItemListAndAccountInfo() {
		LOGGER.info("InvoiceDaoImpl[getItemDisabledOnlyListWithInvoiceItemListAndAccountInfo] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceInfoMapper= session.getMapper(InvoiceMapper.class);
		List<InvoiceInfo> InvoiceInfos = invoiceInfoMapper.getAllDisabledWithChildAndAccount();
		session.close();
		LOGGER.info("InvoiceDaoImpl[getItemDisabledOnlyListWithInvoiceItemListAndAccountInfo] end.");
		return InvoiceInfos;
	}
	
	public void updateByID(InvoiceInfo item) throws Exception {
		LOGGER.info("InvoiceDaoImpl[updateByID] inside.");
		SqlSession session = MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceMapper invoiceItemMapper = session.getMapper(InvoiceMapper.class);
		try {
			invoiceItemMapper.updateByID(item.getId());
			session.commit();
		} catch (Exception exp) {
			session.rollback();
			throw exp;
		} finally {
			session.close();
		}
		LOGGER.info("InvoiceDaoImpl[updateByID] end.");
	}
}
