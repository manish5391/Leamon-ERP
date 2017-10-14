package leamon.erp.db.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import leamon.erp.util.LeamonERPConstants;

public class MyBatsUtil {
	
	static final Logger LOGGER = Logger.getLogger(MyBatsUtil.class);
	
	private static SqlSessionFactory sqlSessionFactory;

	static{
		LOGGER.info("MyBatsUtil[static] inside.");
		InputStream inputStream = null;
		try {
			LOGGER.debug("MyBatsUtil[static] Loading .");
			inputStream = Resources.getResourceAsStream(LeamonERPConstants.MYBATIS_CONFIG_FILE);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			LOGGER.error("MyBatsUtil[static] failed to initialize DB session factory");
			LOGGER.error("MyBatsUtil[static] "+e);
		}
		LOGGER.info("MyBatsUtil[static] End.");
	}
	
	public static SqlSessionFactory getSqlSessionFactory(){
		return sqlSessionFactory;
	}
}
