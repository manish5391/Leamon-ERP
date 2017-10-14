package leamon.erp.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import leamon.erp.ui.LeamonERP;
import leamon.erp.util.LeamonERPConstants;

public class LeamonERPMain {
	
	static final Logger LOGGER = Logger.getLogger(LeamonERPMain.class);
	
	/*static{
		String usrdir = System.getProperty("user.dir");
		//String path=usrdir+File.separatorChar+LeamonERPConstants.RESOURCE+File.separatorChar+"log4j.properties";
		//String path=LeamonERPMain.class.getClassLoader().getResource("log4j.properties").getPath();
		//PropertyConfigurator.configure(path);
	}*/
	
	public static void main(String[] args) {
		LOGGER.info("LeamonERPMain[main] inside");
		LOGGER.info("LeamonERPMain[main] user.dir["+LeamonERPConstants.USER_DIR+"]");
		SwingUtilities.invokeLater(() -> {
			LeamonERP leamonERP  = new LeamonERP();
			
			Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
			leamonERP.setSize(sc);
			leamonERP.setBounds(0, 0, (int)sc.getWidth(), (int)sc.getHeight());
			leamonERP.pack();
			leamonERP.setVisible(true);
			//leamonERP.setExtendedState(leamonERP.MAXIMIZED_BOTH);
			leamonERP.setDefaultCloseOperation(leamonERP.EXIT_ON_CLOSE);
		});
		
		LOGGER.info("LeamonERPMain[main] end");
	}
}
