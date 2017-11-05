package leamon.erp.ui;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginAdapter;
import org.jdesktop.swingx.auth.LoginEvent;
import org.jdesktop.swingx.auth.LoginListener;
import org.jdesktop.swingx.auth.LoginService;

import com.google.common.base.Strings;

import leamon.erp.db.UserInfoDaoImpl;
import leamon.erp.model.UserInfo;
import leamon.erp.ui.admin.CompanyUIAdmin;

public class LoginUI {

	//private JPanel contentPane;
	private int failedAttemptsCount = 0;
	private LeamonERP leamonERP;
	private CompanyUIAdmin uiAdmin;
	
	private String uname;
	private String upassword;

	public static String rptInvoiceReportPath;
	private static final Logger LOGGER = Logger.getLogger(LoginUI.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args == null ){
			LOGGER.error("Please supply argument");
			return;
		}

		if(args.length > 0 ){
			rptInvoiceReportPath = args[0];
			if(rptInvoiceReportPath == null){
				LOGGER.error("Report not found. "+rptInvoiceReportPath);
			}else{
				LOGGER.info("Report Path : "+rptInvoiceReportPath);
			}
		}else{
			LOGGER.error("No Arg found");
			return;
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
					LeamonERP.main(args);
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginUI() {
		JXLoginPane loginPane = new JXLoginPane();

		LoginListener loginListener = new LoginAdapter() {
			@Override
			public void loginFailed(LoginEvent source) {
				failedAttemptsCount++;
				String message;
				switch(failedAttemptsCount) {
				case 1: message = "Wrong username and password! What happened?"; break;
				case 2: message = "Did you just fail again?"; break;
				case 3: message = "This is embarrassing..."; break;
				default: message = "You should probably go home and get some sleep...";
				}
				loginPane.setErrorMessage(message);
			}

			@Override
			public void loginSucceeded(LoginEvent source) {
				if(uname.equals("admin")){
					uiAdmin = new CompanyUIAdmin();
					uiAdmin.setVisible(true);
				}else{
				leamonERP = new LeamonERP();
				//leamonERP.initComponents();
				leamonERP.setVisible(true);
				}
			}
		};

		LoginService loginService = new LoginService() {
			@Override
			public boolean authenticate(String name, char[] password, String server) throws Exception {
				boolean isValid = isValidUser(name, new String(password));
				uname = name;
				return isValid;
			}
		};

		loginService.addLoginListener(loginListener);
		loginPane.setLoginService(loginService);

		//(Frame)null
		JXLoginPane.JXLoginDialog dialog = new JXLoginPane.JXLoginDialog((Frame)null, loginPane);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);

		if(loginPane.getStatus() == JXLoginPane.Status.CANCELLED) {
			//leamonERP.dispatchEvent(new WindowEvent(leamonERP, WindowEvent.WINDOW_CLOSING));
		}
	}


	/**
	 * user/abc123
	 * @param uname
	 * @param password
	 * @return
	 */
	private boolean isValidUser(String uname , String password){
		
		try{
			UserInfo userInfo = UserInfo.builder()
					.username(uname)
					.password(password)
					.build();
			userInfo =  UserInfoDaoImpl.getInstance().matchUser(userInfo);
			if(userInfo != null && null != userInfo.getId()){
				return true;
			}else if (uname.equals("admin") && !Strings.isNullOrEmpty(password) && password.equals("sakun@123")){
				return true;
			}
			//return false;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.toString(),"Exception", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
}
