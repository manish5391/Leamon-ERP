package leamon.erp.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.InvoiceItemMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.InvoiceItemInfo;
import lombok.Getter;

@Getter
public class InvoiceItemDaoImpl implements LeamonERPDao<InvoiceItemInfo>{

	static final Logger LOGGER = Logger.getLogger(InvoiceItemDaoImpl.class);
	private static InvoiceItemDaoImpl INSTANCE;

	public static InvoiceItemDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (InvoiceItemDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new InvoiceItemDaoImpl();

				}
			}
		}//end if
		return INSTANCE;
	}

	@Override
	public List<InvoiceItemInfo> getItemList() throws Exception{
		LOGGER.info("InvoiceItemDaoImpl[getItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceItemMapper InvoiceItemInfoMapper= session.getMapper(InvoiceItemMapper.class);
		List<InvoiceItemInfo> InvoiceItemInfos = InvoiceItemInfoMapper.getAll();
		session.close();
		LOGGER.info("InvoiceItemDaoImpl[getItemList] end.");
		return InvoiceItemInfos;
	}

	@Override
	public void save(InvoiceItemInfo item) throws Exception {

		LOGGER.info("InvoiceItemDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceItemMapper InvoiceItemMapper= session.getMapper(InvoiceItemMapper.class);
		try{
			InvoiceItemMapper.insert(item);
			session.commit();
			LOGGER.info("InvoiceItemDaoImpl[save] id ["+item.getId()+"] after insert.");
		}catch(Throwable t){
			throw t;
		}finally{
			session.close();
		}
		LOGGER.info("InvoiceItemDaoImpl[save] end.");

	}

	@Override
	public void disable(InvoiceItemInfo item) throws Exception{
		LOGGER.info("InvoiceItemDaoImpl[disable] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceItemMapper InvoiceItemMapper= session.getMapper(InvoiceItemMapper.class);
		InvoiceItemMapper.disableInvoiceById(item);
		session.commit();
		session.close();
		LOGGER.info("InvoiceItemDaoImpl[disable] end.");
	}

	@Override
	public void update(InvoiceItemInfo item) throws Exception{
		LOGGER.info("InvoiceItemDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		InvoiceItemMapper InvoiceItemMapper= session.getMapper(InvoiceItemMapper.class);
		InvoiceItemMapper.update(item);
		session.commit();
		session.close();
		LOGGER.info("InvoiceItemDaoImpl[update] end.");
	}

}
