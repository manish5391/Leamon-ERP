package leamon.erp.ui.admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import leamon.erp.db.util.MyBatsUtil;
import leamon.erp.util.LeamonERPConstants;

import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.awt.event.ActionEvent;

public class CompanyUIAdmin extends JFrame {

	static final Logger LOGGER = Logger.getLogger(CompanyUIAdmin.class);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompanyUIAdmin frame = new CompanyUIAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CompanyUIAdmin() {
		setBounds(0, 0, 741, 441);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnHsqlui = new JMenu("HsqlUI");
		menuBar.add(mnHsqlui);
		
		JMenuItem mntmOpenHsqlEditor = new JMenuItem("Open Editor");
		mntmOpenHsqlEditor.addActionListener(e -> mntmOpenHsqlEditorClick(e));
		mnHsqlui.add(mntmOpenHsqlEditor);

	}

	private void mntmOpenHsqlEditorClick(ActionEvent e){
		//jdbc:hsqldb:file:../db/leamonerp.db;ifexists=false;sql.syntax_ora=true;crypt_key=604a6105889da65326bf35790a923932;crypt_type=blowfish
		
		InputStream inputStream = null;
		try {
			LOGGER.debug("MyBatsUtil[static] Loading .");
			inputStream = Resources.getResourceAsStream("jdbc.properties");
			Properties prop = new Properties();
			prop.load(inputStream);
			String urlVal = prop.getProperty("jdbc.url");
			String user = prop.getProperty("jdbc.username");
			String password = prop.getProperty("jdbc.password");
			org.hsqldb.util.DatabaseManagerSwing.main(new String[] { "--url", urlVal, 
					"-user",user, "-password", password, "--noexit" });
		} catch (IOException exp) {
			LOGGER.error("CompanyUIAdmin[mntmOpenHsqlEditorClick] failed to initialize DB session factory");
			LOGGER.error("CompanyUIAdmin[mntmOpenHsqlEditorClick] "+exp);
		}
		
	}
}
