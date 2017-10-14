package leamon.erp.db;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import leamon.erp.db.util.MyBatsUtil;

/**
 * @Copyright  Leamon india 2017
 * 
 * It creates necessary database tables and fill the 
 * default data for created tables.
 *  
 * @author Manish Kumar Mishra
 * @date 31 May , 2017
 */
public class DbScriptRunner {
	static final Logger LOGGER = Logger.getLogger(DbScriptRunner.class);
	
	public static void main(String[] args) {
		LOGGER.info("DbScriptRunner[main] inside.");
		scriptRunner();
		LOGGER.info("DbScriptRunner[main] end.");
	}
	
	static void scriptRunner(){
		LOGGER.info("DbScriptRunner[scriptRunner] inside.");
		SqlSession session= MyBatsUtil.getSqlSessionFactory().openSession();
		ScriptRunner runner = new ScriptRunner(session.getConnection());
		runner.setAutoCommit(true);
		runner.setStopOnError(true);
		try {
			
			//runner.runScript(Resources.getResourceAsReader("sql/create_stock_item.sql"));
			//LOGGER.debug("DbScriptRunner[scriptRunner] : sql/create_stock_item.sql executed.");
			runner.runScript(Resources.getResourceAsReader("sql/insert_stock_item.sql"));
			LOGGER.debug("DbScriptRunner[scriptRunner] : sql/insert_stock_item.sql executed.");
		} catch (IOException e) {
			LOGGER.error("DbScriptRunner[scriptRunner] : "+e);
		}
		
		runner.closeConnection();
		LOGGER.info("DbScriptRunner[scriptRunner] End.");
	}
}
