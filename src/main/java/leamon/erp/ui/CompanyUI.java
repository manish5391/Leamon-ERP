package leamon.erp.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.google.common.base.Strings;

import leamon.erp.db.CompanyInfoDaoImpl;
import leamon.erp.model.CompanyInfo;
import leamon.erp.model.StateCityInfo;
import leamon.erp.util.LeamonUtil;

public class CompanyUI extends JInternalFrame{

	private JPanel contentPane;

	private JXTextField textFieldBranch;

	private JXTextField textFieldCompanyName;
	private JXTextField textFieldSupplier;
	private JXTextArea textAreaAddress;
	private JXTextField textFieldCode;
	private JXTextField textFieldGST;
	private JXTextField textFieldBillPrefix;
	private JXTextField textFieldMobile;
	private JXTextField textFieldBankName;
	private JXTextField textFieldAccouontNo;
	private JXTextField textFieldIFSCCode;

	private JXTextField textFieldState;
	private JXTextField textFieldAbbr;

	private JComboBox comboBoxCity;
	private JTextField cityEditor;
	
	private JButton btnSave;

	static final Logger LOGGER = Logger.getLogger(CompanyUI.class);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompanyUI frame = new CompanyUI();
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
	public CompanyUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 375, 558);
		setTitle("Leamon-ERP: Comapny Info");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Company Name");
		lblNewLabel.setBounds(28, 0, 97, 26);
		contentPane.add(lblNewLabel);

		textFieldCompanyName = new JXTextField();
		textFieldCompanyName.setBounds(28, 23, 330, 26);
		contentPane.add(textFieldCompanyName);

		JLabel lblSupplier = new JLabel("Mfg.\\Supplier of");
		lblSupplier.setBounds(28, 48, 97, 19);
		contentPane.add(lblSupplier);

		textFieldSupplier = new JXTextField();
		textFieldSupplier.setBounds(28, 69, 330, 26);
		contentPane.add(textFieldSupplier);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(28, 94, 97, 26);
		contentPane.add(lblAddress);

		textAreaAddress = new JXTextArea();
		textAreaAddress.setName("address");
		textAreaAddress.setBounds(28, 119, 326, 89);
		contentPane.add(textAreaAddress);

		JLabel lblState = new JLabel("State");
		lblState.setBounds(28, 248, 97, 20);
		contentPane.add(lblState);

		JLabel lblCode = new JLabel("Code");
		lblCode.setBounds(222, 249, 32, 19);
		contentPane.add(lblCode);

		textFieldCode = new JXTextField();
		textFieldCode.setBounds(212, 266, 64, 26);
		contentPane.add(textFieldCode);

		JLabel lblGst = new JLabel("GSTIN\\UID");
		lblGst.setBounds(32, 289, 97, 26);
		contentPane.add(lblGst);

		textFieldGST = new JXTextField();
		textFieldGST.setBounds(28, 313, 219, 26);
		contentPane.add(textFieldGST);

		JLabel lblBillPrefix = new JLabel("Bill Prefix");
		lblBillPrefix.setBounds(261, 296, 97, 19);
		contentPane.add(lblBillPrefix);

		textFieldBillPrefix = new JXTextField();
		textFieldBillPrefix.setBounds(261, 313, 97, 26);
		contentPane.add(textFieldBillPrefix);

		JLabel lblMobile = new JLabel("Mobile");
		lblMobile.setBounds(28, 345, 71, 26);
		contentPane.add(lblMobile);

		textFieldMobile = new JXTextField();
		textFieldMobile.setBounds(109, 345, 249, 26);
		contentPane.add(textFieldMobile);

		JLabel lblBank = new JLabel("Bank Name");
		lblBank.setBounds(28, 373, 71, 26);
		contentPane.add(lblBank);

		textFieldBankName = new JXTextField();
		textFieldBankName.setBounds(109, 373, 249, 26);
		contentPane.add(textFieldBankName);

		JLabel lblBankAc = new JLabel("Bank A/C No.");
		lblBankAc.setBounds(28, 401, 71, 26);
		contentPane.add(lblBankAc);

		textFieldAccouontNo = new JXTextField();
		textFieldAccouontNo.setBounds(109, 401, 249, 26);
		contentPane.add(textFieldAccouontNo);

		JLabel lblIfscCode = new JLabel("IFSC Code");
		lblIfscCode.setBounds(28, 431, 71, 26);
		contentPane.add(lblIfscCode);

		textFieldIFSCCode = new JXTextField();
		textFieldIFSCCode.setBounds(109, 431, 249, 26);
		contentPane.add(textFieldIFSCCode);

		JLabel lblBranch = new JLabel("Branch");
		lblBranch.setBounds(28, 461, 71, 26);
		contentPane.add(lblBranch);

		textFieldBranch = new JXTextField();
		textFieldBranch.setBounds(109, 461, 249, 26);
		contentPane.add(textFieldBranch);

		btnSave = new JButton("Save");
		btnSave.setBounds(109, 498, 91, 23);
		contentPane.add(btnSave);
		btnSave.addActionListener(e -> btnSaveClick(e));
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "Save");
		btnSave.getActionMap().put("Save", getSaveAction());

		JButton btnClose = new JButton("Close");
		btnClose.setBounds(267, 498, 91, 23);
		contentPane.add(btnClose);
		btnClose.addActionListener(e -> btnCloseClick(e));

		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(28, 219, 32, 26);
		contentPane.add(lblCity);
		
		//cityEditor.addKeyListener(new AccountInfoKeyListener());


		textFieldState = new JXTextField();
		textFieldState.setBounds(28, 266, 174, 26);
		contentPane.add(textFieldState);

		comboBoxCity = new JComboBox();
		comboBoxCity.setBounds(57, 221, 297, 22);
		contentPane.add(comboBoxCity);
		LeamonUtil.prepareAutoCompleterCombo(comboBoxCity, LeamonERP.cityCache);
		cityEditor = (JTextField)comboBoxCity.getEditor().getEditorComponent();
		PromptSupport.setPrompt("- - Select City - -", cityEditor);
		cityEditor.setName("CompanyCity");
		cityEditor.addKeyListener(new CompanyInfoKeyListener());
		
		textFieldAbbr = new JXTextField();
		textFieldAbbr.setBounds(279, 266, 64, 26);
		contentPane.add(textFieldAbbr);
		
		JLabel lblAbbr = new JLabel("Abbr");
		lblAbbr.setBounds(289, 249, 32, 19);
		contentPane.add(lblAbbr);

		load();
		registerKeyEvent();
	}

	public void save(){
		String companyName = textFieldCompanyName.getText();
		String supplier = textFieldSupplier.getText();
		String address = textAreaAddress.getText();
		String code = textFieldCode.getText();
		String gst = textFieldGST.getText();
		String billPrefix = textFieldBillPrefix.getText();
		String mobile = textFieldMobile.getText();
		String bankName = textFieldBankName.getText();
		String accountNo = textFieldAccouontNo.getText();
		String ifscCode = textFieldIFSCCode.getText();
		String city = (String)comboBoxCity.getSelectedItem();
		String state = (String)textFieldState.getText();
		String appr =textFieldAbbr.getText();

		Integer id = 0;
		try{
			id = CompanyInfoDaoImpl.getInstance().getItemList().get(0).getId();
		}catch(Exception e){
			LOGGER.error(e);
		}
		
		try{
			
			CompanyInfo info = CompanyInfo.builder().companySupplierOf(supplier)
					.id(id)
					.companyAddress(address)
					.companyCity(city)
					.companyState(state)
					.companyStateCode(code)
					.companyGstNumber(gst)
					.companyBillPrefix(billPrefix)
					.companyMobile(mobile)
					.companyBankName(bankName)
					.companyAccountNo(accountNo)
					.companyBankIfsc(ifscCode)
					.lastupdated(new Timestamp(System.currentTimeMillis()))
					.isEnable(Boolean.TRUE)
					.build();
			CompanyInfoDaoImpl.getInstance().update(info);
			JOptionPane.showMessageDialog(this, "saved successfully.","Leamon-ERP",JOptionPane.PLAIN_MESSAGE);
		}catch(Exception e){
			LOGGER.error(e);
			JOptionPane.showMessageDialog(this, "Failed du to : "+e.getMessage(),"Exception",JOptionPane.PLAIN_MESSAGE);
		}

	}

	private void load(){
		LOGGER.info("inside load");
		try {
			List<CompanyInfo> companyInfos =  CompanyInfoDaoImpl.getInstance().getItemList();
			if(null != companyInfos && !companyInfos.isEmpty()){
				CompanyInfo info =companyInfos.get(0);
				setCompanyInfo(info);
			}else{
				LOGGER.error("No Company Found");
				JOptionPane.showMessageDialog(this, "Company is not configured", "Leamon-ERP", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			LOGGER.error(e);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Leamon-ERP(Exception)", JOptionPane.ERROR_MESSAGE);
		}
		LOGGER.info("load end.");
	}

	public void setCompanyInfo(CompanyInfo companyInfo){
		if(companyInfo == null){
			return ;
		}
		textFieldCompanyName.setText(companyInfo.getCompanyName());
		textAreaAddress.setText(companyInfo.getCompanyAddress());
		textFieldSupplier.setText(companyInfo.getCompanySupplierOf());
		textFieldGST.setText(companyInfo.getCompanyGstNumber());
		textFieldBillPrefix.setText(companyInfo.getCompanyBillPrefix());
		textFieldMobile.setText(companyInfo.getCompanyMobile());
		textFieldBankName.setText(companyInfo.getCompanyBankName());
		textFieldAccouontNo.setText(companyInfo.getCompanyAccountNo());
		textFieldIFSCCode.setText(companyInfo.getCompanyBankIfsc());
		comboBoxCity.setSelectedItem(companyInfo.getCompanyCity());
		setState(companyInfo.getCompanyCity());
	}

	private void btnCloseClick(ActionEvent e){
		this.dispose();
	}

	private void btnSaveClick(ActionEvent e){
		save();
	}
	
	
	private void registerKeyEvent(){
		textFieldBranch.addKeyListener(new CompanyInfoKeyListener());

		textFieldCompanyName.addKeyListener(new CompanyInfoKeyListener());
		textFieldSupplier.addKeyListener(new CompanyInfoKeyListener());
		textAreaAddress.addKeyListener(new CompanyInfoKeyListener());
		textFieldCode.addKeyListener(new CompanyInfoKeyListener());
		textFieldGST.addKeyListener(new CompanyInfoKeyListener());
		textFieldBillPrefix.addKeyListener(new CompanyInfoKeyListener());
		textFieldMobile.addKeyListener(new CompanyInfoKeyListener());
		textFieldBankName.addKeyListener(new CompanyInfoKeyListener());
		textFieldAccouontNo.addKeyListener(new CompanyInfoKeyListener());
		textFieldIFSCCode.addKeyListener(new CompanyInfoKeyListener());

		textFieldState.addKeyListener(new CompanyInfoKeyListener());
		textFieldAbbr.addKeyListener(new CompanyInfoKeyListener());

		//comboBoxCity.addKeyListener(new CompanyInfoKeyListener());
		cityEditor.addKeyListener(new CompanyInfoKeyListener());
	}

	class CompanyInfoKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			int KEY_CODE = e.getKeyCode();
			//LOGGER.info("Key Code : ["+KEY_CODE+"]");
			//JTextField
			if( e.getSource() instanceof JTextField && KEY_CODE == KeyEvent.VK_ENTER){
				JTextField source = (JTextField) e.getSource();
				
				if(source!=null && source.getName()!=null && source.getName().equals("CompanyCity")){
					String item = (String) comboBoxCity.getSelectedItem();
					if(Strings.isNullOrEmpty(item)){
						return ;
					}
					StateCityInfo stateCityInfo = LeamonERP.stateCityInfos.stream().filter(ele -> ele.getCity().equals(item)).findFirst().get();
					if(null != stateCityInfo){
						textFieldState.setText(stateCityInfo.getState());
						textFieldCode.setText(stateCityInfo.getStateCode());
						textFieldAbbr.setText(stateCityInfo.getAbbreviations());
					}else{
						LOGGER.error("city not matched from list of objects");
					}
					textFieldState.requestFocus();
				}else if(source.equals(textFieldCompanyName)){
					textFieldSupplier.requestFocus();
				}else if(source.equals(textFieldSupplier)){
					textAreaAddress.requestFocus();
				}else if(source.equals(textFieldState)){
					textFieldGST.requestFocus();
				}else if(source.equals(textFieldGST)){
					textFieldBillPrefix.requestFocus();
				}else if(source.equals(textFieldBillPrefix)){
					textFieldMobile.requestFocus();
				}else if(source.equals(textFieldMobile)){
					textFieldBankName.requestFocus();
				}else if(source.equals(textFieldBankName)){
					textFieldAccouontNo.requestFocus();
				}else if(source.equals(textFieldAccouontNo)){
					textFieldIFSCCode.requestFocus();
				}else if(source.equals(textFieldIFSCCode)){
					textFieldBranch.requestFocus();
				}else if(source.equals(textFieldBranch)){
					textFieldCompanyName.requestFocus();
				}

			}else if  (e.getSource() instanceof JTextArea && KEY_CODE == KeyEvent.VK_ENTER){//end if enter
				comboBoxCity.requestFocus();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}
	
	public void setState(String city){
		if(Strings.isNullOrEmpty(city)){
			return ;
		}
		StateCityInfo stateCityInfo = LeamonERP.stateCityInfos.stream().filter(ele -> ele.getCity().equals(city)).findFirst().get();
		if(null != stateCityInfo){
			textFieldState.setText(stateCityInfo.getState());
			textFieldCode.setText(stateCityInfo.getStateCode());
			textFieldAbbr.setText(stateCityInfo.getAbbreviations());
		}else{
			LOGGER.error("city not matched from list of objects");
		}
	}
	
	private Action getSaveAction(){

		Action saveAction = new AbstractAction("Save") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + s clicked");
				save();
			}
		};
		saveAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		return saveAction;
	}

}
