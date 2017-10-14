package leamon.erp.db;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.mapper.StockMapper;
import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.model.StockItem;
import lombok.Getter;
/**
 * Database Operation
 * @author Manish Kumar Mishra
 * @date 
 */

@Getter
public class StockDaoImpl implements  LeamonERPDao<StockItem> {
	
	static final Logger LOGGER = Logger.getLogger(StockDaoImpl.class);
	private static StockDaoImpl INSTANCE;
	
	private List<String> stockItemNames;
	private List<String> stockItemSizes;
	private List<String> stockItemProductCodes;
	private List<String> stockItemFinishs;
	private List<String> stockItemUnits;
	private List<String> stockItemShapes;
	private List<String> stockItemSaleUnits;
	private List<String> stockItemDescriptions;
	
	private StockDaoImpl (){
		System.setProperty("net.sf.ehcache.skipUpdateCheck", "true");
	}
	
	public static StockDaoImpl getInstance(){
		if(INSTANCE==null){
			synchronized (StockDaoImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new StockDaoImpl();
				}
			}
		}//end if
		return INSTANCE;
	}
	
	@Override
	public List<StockItem> getItemList() throws Exception {
		LOGGER.info("LeamonERPDaoImpl[getStockItemList] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockMapper stockItemMapper= session.getMapper(StockMapper.class);
		List<StockItem> stockItems = stockItemMapper.getAll();
		session.close();
		LOGGER.info("LeamonERPDaoImpl[getStockItemList] end.");
		return stockItems;
	}
	
	public void prepareStockIntelliSense() throws Exception{
		LOGGER.info("LeamonERPDaoImpl[stockIntellisense] inside.");
		List<StockItem> stockItems = getItemList();
		stockItemNames			= new ArrayList<String>();
		stockItemProductCodes	= new ArrayList<String>();
		stockItemSizes			= new ArrayList<String>();
		stockItemDescriptions	= new ArrayList<String>();
		stockItemFinishs		= new ArrayList<String>();
		stockItemUnits			= new ArrayList<String>();
		stockItemShapes			= new ArrayList<String>();
		stockItemSaleUnits		= new ArrayList<String>();
		stockItemDescriptions 	= new ArrayList<String>();
		
		for(StockItem item : stockItems){
			if(item.getName()!=null && !item.getName().isEmpty()){
				stockItemNames.add(item.getName());
			}
			if(item.getProductCode()!=null && !item.getProductCode().isEmpty()){
				stockItemProductCodes.add(item.getProductCode());
			}
			if(item.getSize()!=null && !item.getSize().isEmpty()){
				stockItemSizes.add(item.getSize());
			}
			if(item.getDescription()!=null && !item.getDescription().isEmpty()){
				stockItemDescriptions.add(item.getDescription());
			}
			if(item.getFinish()!=null && !item.getFinish().isEmpty()){
				stockItemFinishs.add(item.getFinish());
			}
			if(item.getUnit()!=null && !item.getUnit().isEmpty()){
				stockItemUnits.add(item.getUnit());
			}
			if(item.getShape()!=null && !item.getShape().isEmpty()){
				stockItemShapes.add(item.getShape());
			}
			if(item.getSaleunit()!=null && !item.getSaleunit().isEmpty()){
				stockItemSaleUnits.add(item.getSaleunit());
			}
			if(item.getDescription()!=null && !item.getDescription().isEmpty()){
				stockItemDescriptions.add(item.getDescription());
			}
		}
		
		LinkedHashSet<String> namesWithourDuplicate = new LinkedHashSet<String>(stockItemNames);
		stockItemNames.clear();
		stockItemNames.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemNames["+stockItemNames+"].");
		
		namesWithourDuplicate.addAll(stockItemProductCodes);
		stockItemProductCodes.clear();
		stockItemProductCodes.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemProductCodes["+stockItemProductCodes+"].");

		namesWithourDuplicate.addAll(stockItemSizes);
		stockItemSizes.clear();
		stockItemSizes.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemSizes["+stockItemSizes+"].");
		
		namesWithourDuplicate.addAll(stockItemDescriptions);
		stockItemDescriptions.clear();
		stockItemDescriptions.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemDescriptions["+stockItemDescriptions+"].");
		
		namesWithourDuplicate.addAll(stockItemFinishs);
		stockItemFinishs.clear();
		stockItemFinishs.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemFinishs["+stockItemFinishs+"].");
		
		namesWithourDuplicate.addAll(stockItemUnits);
		stockItemUnits.clear();
		stockItemUnits.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemUnits["+stockItemUnits+"].");
		
		namesWithourDuplicate.addAll(stockItemShapes);
		stockItemShapes.clear();
		stockItemShapes.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemShapes["+stockItemShapes+"].");
		
		namesWithourDuplicate.addAll(stockItemSaleUnits);
		stockItemSaleUnits.clear();
		stockItemSaleUnits.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemSaleUnits["+stockItemSaleUnits+"].");
		
		namesWithourDuplicate.addAll(stockItemDescriptions);
		stockItemDescriptions.clear();
		stockItemDescriptions.addAll(namesWithourDuplicate);
		namesWithourDuplicate.clear();
		LOGGER.debug("LeamonERPDaoImpl[stockIntellisense] unique stockItemDescriptions["+stockItemDescriptions+"].");
		
		LOGGER.info("LeamonERPDaoImpl[stockIntellisense] end.");
	}

	@Override
	public void save(StockItem stockItem) throws Exception {
		LOGGER.info("LeamonERPDaoImpl[save] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockMapper stockItemMapper= session.getMapper(StockMapper.class);
		try{
			stockItemMapper.insert(stockItem);
			session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("LeamonERPDaoImpl[save] id ["+stockItem.getId()+"] after insert.");
		LOGGER.info("LeamonERPDaoImpl[save] end.");
		//return id;
	}

	@Override
	public void disable(StockItem item) throws Exception{
		LOGGER.info("LeamonERPDaoImpl[delete] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockMapper stockItemMapper= session.getMapper(StockMapper.class);
		try{
		stockItemMapper.disableStockByID(item);
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
	public void update(StockItem item) throws Exception{
		LOGGER.info("LeamonERPDaoImpl[update] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		StockMapper stockItemMapper= session.getMapper(StockMapper.class);
		try{
		stockItemMapper.update(item);
		session.commit();
		}catch(Exception exp){
			session.rollback();
			throw exp;
		}finally{
			session.close();
		}
		LOGGER.info("LeamonERPDaoImpl[update] end.");
	}

	
	
	
}
