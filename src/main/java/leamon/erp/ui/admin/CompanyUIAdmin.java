package leamon.erp.ui.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.ibatis.io.Resources;
import org.apache.log4j.Logger;

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
		mnHsqlui.add(mntmOpenHsqlEditor);
		
		mntmOpenHsqlEditor.addActionListener(e -> mntmOpenHsqlEditorClick(e));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void mntmOpenHsqlEditorClick(ActionEvent e){
		//jdbc:hsqldb:file:../db/leamonerp.db;ifexists=false;sql.syntax_ora=true;crypt_key=604a6105889da65326bf35790a923932;crypt_type=blowfish
		Runnable task = () ->{
			InputStream inputStream = null;
			try {
				LOGGER.debug("MyBatsUtil[static] Loading .");
				inputStream = Resources.getResourceAsStream("jdbc.properties");
				Properties prop = new Properties();
				prop.load(inputStream);
				String urlVal = prop.getProperty("jdbc.url");
				String user = prop.getProperty("jdbc.username");
				String password = prop.getProperty("jdbc.password");
				/*org.hsqldb.util.DatabaseManagerSwing.main(new String[] { "--url", urlVal, 
						"-user",user, "-password", password, "--noexit" });*/
				
				org.hsqldb.util.DatabaseManager.main(new String[] { "--url", urlVal, 
						"-user",user, "-password", password, "--noexit" });
			} catch (Exception exp) {
				LOGGER.error("CompanyUIAdmin[mntmOpenHsqlEditorClick] failed to initialize DB session factory");
				LOGGER.error("CompanyUIAdmin[mntmOpenHsqlEditorClick] "+exp);
			}
		};
		EventQueue.invokeLater(task);
	}
}
