package leamon.erp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.math.BigInteger;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.google.common.base.Strings;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.StateCityInfo;
import leamon.erp.ui.event.FocusListenerHandler;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.LeamonUtil;
import lombok.Getter;

@Getter
public class AccountInfoUI extends JInternalFrame implements ActionListener {

	static final Logger LOGGER = Logger.getLogger(AccountInfoUI.class);

	private JXDatePicker txtEngageDate;
	private JXTextField txtPhone;
	private JXTextField txtAlternatePhone;//3.5 Ghanshyam code to add alternate number
	private JXTextField txtName;
	private JXTextField txtHouseShopNum;
	private JXTextField txtStreet;
	private JXTextField txtLandMark;
	private JXTextField txtPan;
	private JXTextField txtLicence;
	//private JFormattedTextField fmtTxtPinCode;
	private JXTextField fmtTxtPinCode;
	private JComboBox comboBoxCity;
	private JComboBox comboBoxState;
	private JComboBox comboBoxCountry;

	private JTextField cityEditor;
	private JTextField stateEditor;
	private JTextField countryEditor;
	private JXTextField txtNickName;
	private JXTextField txtGSTNumber;
	private JXTextField txtTransport;
	private JXTextField textFieldSCode;
	private JXTextField textFieldAbbr;

	private JLabel lblMsg;
	private JLabel lblImagePath;
	private JLabel lblAcountImage;

	private JButton btnClear;
	private JButton btnSave;
	private JButton btnDelete; 
	private JButton btnEdit;
	private JButton btnUploadImage;

	private JTextArea textAreaDesc;
	private JLabel lblID;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel lblCode;

	//3.4 Ghanshyam code for account name auto suggestor
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;
	//3.4 end of ghanshyam code 

	public AccountInfoUI() {
		setTitle("Account info");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(3, 30, 799, 534);


		JPanel pnlStock = new JPanel();
		pnlStock.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 0, 0), null, null, null), "Account Info Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(205, 92, 92)));
		pnlStock.setBackground(Color.WHITE);
		getContentPane().add(pnlStock, BorderLayout.CENTER);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(18, 46, 40, 22);
		lblName.setForeground(new Color(102, 51, 0));
		lblName.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblProductCode = new JLabel("Phone Number");
		lblProductCode.setBounds(18, 249, 120, 22);
		lblProductCode.setForeground(new Color(102, 51, 0));
		lblProductCode.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblSize = new JLabel("House/Shop Number");
		lblSize.setBounds(18, 284, 170, 22);
		lblSize.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblSize.setForeground(new Color(102, 51, 0));

		JLabel lblFinish = new JLabel("Street");
		lblFinish.setBounds(18, 318, 60, 22);
		lblFinish.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblFinish.setForeground(new Color(102, 51, 0));

		JLabel lblUnit = new JLabel("City");
		lblUnit.setBounds(18, 110, 40, 22);
		lblUnit.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblUnit.setForeground(new Color(102, 51, 0));
		String usrdir = System.getProperty("user.dir");
		String path=usrdir+File.separatorChar+"resource"+File.separatorChar+"image-0_51-0_54"
				+File.separatorChar+"save_0.51-0.54.png";

		JLabel lblState = new JLabel("State");
		lblState.setBounds(18, 138, 50, 22);
		lblState.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblState.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblPinCode = new JLabel("Pin Code");
		lblPinCode.setBounds(18, 352, 85, 22);
		lblPinCode.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblPinCode.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblCountry = new JLabel("Country");
		lblCountry.setBounds(18, 382, 75, 22);
		lblCountry.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblCountry.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblLandMark = new JLabel("Land Mark");
		lblLandMark.setBounds(18, 410, 104, 22);
		lblLandMark.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblLandMark.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblEngageDate = new JLabel("Business Date");
		lblEngageDate.setBounds(18, 459, 141, 22);
		lblEngageDate.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblEngageDate.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblPan = new JLabel("Pan");
		lblPan.setBounds(18, 499, 68, 22);
		lblPan.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblPan.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblLicence = new JLabel("Licence");
		lblLicence.setBounds(18, 528, 95, 22);
		lblLicence.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblLicence.setFont(new Font("DialogInput", Font.BOLD, 16));

		txtName = new JXTextField();
		txtName.setBounds(86, 46, 444, 23);
		txtName.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_NAME); 
		txtName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtName.addKeyListener(new AccountInfoKeyListener());
		txtName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtName.setPrompt("Account Name");
		//3.4 Ghanshyam code for autoNamesuggestor
		autoNameSuggestor(txtName);
		//3.4 end of ghanshyam code

		txtNickName = new JXTextField();
		txtNickName.setBounds(246, 75, 244, 23);
		txtNickName.setPrompt("Marka/Nick Name");
		txtNickName.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_NICK_NAME);
		txtNickName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtNickName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtNickName.addKeyListener(new AccountInfoKeyListener());

		txtGSTNumber = new JXTextField();
		txtGSTNumber.setBounds(246, 214, 244, 23);
		txtGSTNumber.setPrompt("TIN/GST");
		txtGSTNumber.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_GST);
		txtGSTNumber.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtGSTNumber.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtGSTNumber.addKeyListener(new AccountInfoKeyListener());

		txtTransport = new JXTextField();
		txtTransport.setBounds(246, 172, 244, 23);
		txtTransport.setPrompt("Transport Name");
		txtTransport.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_TRANSPORT);
		txtTransport.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtTransport.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtTransport.addKeyListener(new AccountInfoKeyListener());

		txtPhone = new JXTextField();
		txtPhone.setBounds(246, 248, 120, 23);//3.5 Ghanshyam code to decrease width of phone number
		txtPhone.setPrompt("Phone Number");
		txtPhone.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_PHONE);
		txtPhone.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtPhone.addKeyListener(new AccountInfoKeyListener());
		txtPhone.setFont(new Font("DialogInput", Font.PLAIN, 16));

		// 3.5 Ghanshyam code to add alternate number
		txtAlternatePhone = new JXTextField();
		txtAlternatePhone.setBounds(390, 248, 130, 23);// ghan code
		txtAlternatePhone.setPrompt("Alternate No.");
		txtAlternatePhone.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_ALTERNATE_PHONE);
		txtAlternatePhone.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtAlternatePhone.addKeyListener(new AccountInfoKeyListener());
		txtAlternatePhone.setFont(new Font("DialogInput", Font.PLAIN, 16));
		// end of Ghanshyam code

		txtHouseShopNum = new JXTextField();
		txtHouseShopNum.setBounds(246, 283, 244, 23);
		txtHouseShopNum.setPrompt("H./NO. Or Shop No.");
		txtHouseShopNum.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_HOUSE);
		txtHouseShopNum.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtHouseShopNum.addKeyListener(new AccountInfoKeyListener());
		txtHouseShopNum.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		txtStreet = new JXTextField();
		txtStreet.setBounds(246, 312, 244, 23);
		txtStreet.setPrompt("Street");
		txtStreet.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_STREET);
		txtStreet.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtStreet.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtStreet.addKeyListener(new AccountInfoKeyListener());

		comboBoxCity = new JComboBox();
		comboBoxCity.setBounds(246, 108, 244, 25);
		comboBoxCity.setBackground(Color.WHITE);
		comboBoxCity.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_CITY);
		comboBoxCity.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		/*- City - Autocompleter */
		LeamonUtil.prepareAutoCompleterCombo(comboBoxCity, LeamonERP.cityCache);
		cityEditor = (JTextField)comboBoxCity.getEditor().getEditorComponent();
		PromptSupport.setPrompt("- - Select City - -", cityEditor);
		cityEditor.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_CITY);
		cityEditor.addKeyListener(new AccountInfoKeyListener());

		comboBoxState = new JComboBox();
		comboBoxState.setBounds(99, 138, 150, 25);
		comboBoxState.setBackground(Color.WHITE);
		comboBoxState.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_STATE);
		/*- State - Autocompleter */
		LeamonUtil.prepareAutoCompleterCombo(comboBoxState, LeamonERP.stateCache);
		stateEditor = (JTextField)comboBoxState.getEditor().getEditorComponent();
		PromptSupport.setPrompt("- - Select State - -", stateEditor);
		stateEditor.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_STATE);
		stateEditor.addKeyListener(new AccountInfoKeyListener());


		//fmtTxtPinCode = new JFormattedTextField(createMaskFormatter());
		fmtTxtPinCode = new JXTextField();
		fmtTxtPinCode.setBounds(246, 341, 244, 23);
		fmtTxtPinCode.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_PINCODE);
		fmtTxtPinCode.setFont(new Font("DialogInput", Font.PLAIN, 16));
		fmtTxtPinCode.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		fmtTxtPinCode.addKeyListener(new AccountInfoKeyListener());

		comboBoxCountry = new JComboBox();
		comboBoxCountry.setBounds(246, 378, 244, 25);
		comboBoxCountry.setBackground(Color.WHITE);
		comboBoxCountry.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_COUNTRY);
		/*- Country - Autocompleter */
		LeamonUtil.prepareAutoCompleterCombo(comboBoxCountry, LeamonERP.countryCache);
		countryEditor = (JTextField)comboBoxCountry.getEditor().getEditorComponent();
		PromptSupport.setPrompt("- - Select Country - -", countryEditor);
		countryEditor.setText("INDIA");
		comboBoxCountry.setSelectedItem("India");
		countryEditor.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_COUNTRY);
		countryEditor.addKeyListener(new AccountInfoKeyListener());


		txtLandMark = new JXTextField();
		txtLandMark.setBounds(246, 409, 229, 23);
		txtLandMark.setPrompt("Landmark");
		txtLandMark.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_LANDMARK);
		txtLandMark.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtLandMark.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtLandMark.addKeyListener(new AccountInfoKeyListener());

		txtEngageDate = new JXDatePicker();
		txtEngageDate.setBounds(246, 452, 227, 29);
		txtEngageDate.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_ENGAGEDDATE);
		txtEngageDate.getEditor().setFont(new Font("Dialog", Font.PLAIN, 16));
		txtEngageDate.setFormats(new String[] {"dd/MMMM/yyyy"});
		txtEngageDate.setDate(new Date());
		txtEngageDate.getEditor().setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_ENGAGEDDATE);
		txtEngageDate.getEditor().addKeyListener(new AccountInfoKeyListener());
		PromptSupport.setPrompt("- - Select Engage Date - -", txtEngageDate.getEditor());

		txtPan = new JXTextField();
		txtPan.setBounds(246, 499, 227, 23);
		txtPan.setPrompt("PAN Number");
		txtPan.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_PAN);
		txtPan.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtPan.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtPan.addKeyListener(new AccountInfoKeyListener());

		txtLicence = new JXTextField();
		txtLicence.setBounds(246, 528, 227, 23);
		txtLicence.setPrompt("Licence Number");
		txtLicence.setName(LeamonERPConstants.TEXTFIELD_ACCOUNT_LICENCE);
		txtLicence.setFont(new Font("DialogInput", Font.PLAIN, 16));
		txtLicence.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		txtLicence.addKeyListener(new AccountInfoKeyListener());

		btnClear = new JButton("CleaR");
		btnClear.setBounds(18, 416, 121, 72);
		btnClear.setMnemonic('C');
		btnClear.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_CLEAR_BUTTON)));
		btnClear.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnClear.setForeground((Color) null);
		btnClear.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnClear.setBackground(Color.WHITE);
		btnClear.setMnemonic(KeyEvent.VK_R);
		btnClear.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "Clear");
		btnClear.getActionMap().put("Clear", getClearAction());
		btnClear.addActionListener(e -> btnClearClick(e));

		btnEdit = new JButton("Edit");
		btnEdit.setBounds(151, 416, 118, 72);
		btnEdit.setMnemonic('E');
		btnEdit.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_EDIT_BUTTON)));
		btnEdit.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnEdit.setForeground((Color) null);
		btnEdit.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setName(LeamonERPConstants.BUTTON_ACTION_EDIT_ACCOUNT_INFO);
		btnEdit.setMnemonic(KeyEvent.VK_E);
		btnEdit.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), "Edit");
		btnEdit.getActionMap().put("Edit", getEditAction());
		btnEdit.addActionListener(this);

		btnSave = new JButton("Save");
		btnSave.setBounds(275, 416, 118, 72);
		btnSave.setMnemonic('S');
		btnSave.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_SAVE_BTN)));
		btnSave.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnSave.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnSave.setName(LeamonERPConstants.BUTTON_ACTION_ADD_ACCOUNT_INFO);
		btnSave.setBackground(Color.WHITE);
		btnSave.setMnemonic(KeyEvent.VK_S);
		btnSave.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "Save");
		btnSave.getActionMap().put("Save", getSaveAction());
		btnSave.addActionListener(this);

		btnDelete = new JButton("Delete");
		btnDelete.setBounds(399, 417, 127, 70);
		btnDelete.setMnemonic('D');
		btnDelete.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		btnDelete.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_DELETE_BUTTON)));
		btnDelete.setForeground((Color) null);
		btnDelete.setFont(new Font("DialogInput", Font.BOLD, 14));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setMnemonic(KeyEvent.VK_D);
		btnDelete.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), "Delete");
		btnDelete.getActionMap().put("Delete", getDeleteAction());
		btnDelete.setName(LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO);

		btnDelete.addActionListener(this);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 707, 0, 2);
		separator.setBackground(Color.ORANGE);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 203, 500, 2);
		separator_1.setBackground(Color.BLUE);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 438, 0, 8);
		separator_2.setBackground(Color.BLUE);

		lblMsg = new JLabel("");
		lblMsg.setBounds(76, 18, 453, 22);
		lblMsg.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblMsg.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblMsg.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(506, 557, 0, 3);
		separator_3.setBackground(Color.BLUE);

		JLabel lblMarkanickName = new JLabel("Marka/Nick Name");
		lblMarkanickName.setBounds(18, 75, 150, 22);
		lblMarkanickName.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblMarkanickName.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblTinGst = new JLabel("Tin/GST Number");
		lblTinGst.setBounds(18, 214, 150, 22);
		lblTinGst.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblTinGst.setFont(new Font("DialogInput", Font.BOLD, 16));

		JLabel lblTransportName = new JLabel("Transport name");
		lblTransportName.setBounds(18, 172, 140, 22);
		lblTransportName.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblTransportName.setFont(new Font("DialogInput", Font.BOLD, 16));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(542, 75, 239, 206);
		scrollPane.setToolTipText("account info image");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		btnUploadImage = new JButton("Upload Image");
		btnUploadImage.setBounds(542, 42, 132, 27);
		btnUploadImage.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUploadImage.setForeground(new Color(255, 255, 255));
		btnUploadImage.setBackground(new Color(70, 130, 180));
		btnUploadImage.setActionCommand(LeamonERPConstants.BUTTON_ACTION_UPLOAD_IMAGE_ACCOUNT_INFO);
		btnUploadImage.addActionListener(this);
		btnUploadImage.addKeyListener(new CustomKeyListener());
		btnUploadImage.setMnemonic(KeyEvent.VK_U);
		btnUploadImage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				KeyEvent.CTRL_DOWN_MASK), "Upload Image");
		btnUploadImage.getActionMap().put("Upload Image", getUploadImageAction());

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(588, 323, 121, 22);
		lblDescription.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblDescription.setFont(new Font("DialogInput", Font.BOLD, 16));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(537, 363, 214, 118);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		lblImagePath = new JLabel("");
		lblImagePath.setBounds(542, 287, 239, 24);
		lblImagePath.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);

		lblID = new JLabel("");
		lblID.setBounds(18, 18, 0, 0);

		lblAcountImage = new JLabel("");
		scrollPane.setViewportView(lblAcountImage);
		loadDefaultImage();

		textAreaDesc = new JTextArea();
		textAreaDesc.setName(LeamonERPConstants.TEXTAREA_DESCRIPTION);
		scrollPane_1.setViewportView(textAreaDesc);
		pnlStock.setLayout(null);
		pnlStock.add(lblName);
		pnlStock.add(lblID);
		pnlStock.add(txtName);
		pnlStock.add(txtNickName);
		pnlStock.add(txtGSTNumber);
		pnlStock.add(btnUploadImage);
		pnlStock.add(scrollPane);
		pnlStock.add(lblImagePath);
		pnlStock.add(lblMsg);
		pnlStock.add(separator);
		pnlStock.add(lblMarkanickName);
		pnlStock.add(lblTinGst);
		pnlStock.add(lblProductCode);
		pnlStock.add(lblTransportName);
		pnlStock.add(txtTransport);
		pnlStock.add(txtPhone);
		pnlStock.add(txtAlternatePhone);//3.5 Ghanshyam code to add alternate phone number
		pnlStock.add(btnClear);
		pnlStock.add(btnEdit);
		pnlStock.add(btnSave);
		pnlStock.add(btnDelete);
		//pnlStock.add(lblPan);
		//pnlStock.add(lblLicence);
		//pnlStock.add(lblEngageDate);
		//pnlStock.add(txtLicence);
		//pnlStock.add(txtPan);
		//pnlStock.add(txtEngageDate);
		pnlStock.add(separator_3);
		pnlStock.add(separator_2);
		pnlStock.add(separator_1);
		pnlStock.add(lblFinish);
		pnlStock.add(lblSize);
		pnlStock.add(lblUnit);
		//pnlStock.add(lblLandMark);
		pnlStock.add(lblCountry);
		pnlStock.add(lblPinCode);
		pnlStock.add(lblState);
		pnlStock.add(comboBoxCountry);
		pnlStock.add(comboBoxCity);
		pnlStock.add(comboBoxState);
		pnlStock.add(fmtTxtPinCode);
		//pnlStock.add(txtLandMark);
		pnlStock.add(lblDescription);
		pnlStock.add(scrollPane_1);
		pnlStock.add(txtHouseShopNum);
		pnlStock.add(txtStreet);

		label = new JLabel("*");
		label.setForeground(Color.RED);
		label.setFont(new Font("DialogInput", Font.BOLD, 16));
		label.setBounds(57, 46, 17, 22);
		pnlStock.add(label);

		label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_2.setBounds(76, 138, 17, 22);
		pnlStock.add(label_2);

		label_3 = new JLabel("*");
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_3.setBounds(57, 111, 17, 22);
		pnlStock.add(label_3);

		lblCode = new JLabel("CODE:");
		lblCode.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblCode.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblCode.setBounds(256, 145, 50, 19);
		pnlStock.add(lblCode);

		textFieldSCode = new JXTextField();
		textFieldSCode.setPrompt("Code");
		textFieldSCode.setName("txtAccountName");
		textFieldSCode.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldSCode.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldSCode.setBounds(309, 140, 40, 23);
		pnlStock.add(textFieldSCode);

		JLabel lblSabbr = new JLabel("S-Abbr:");
		lblSabbr.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblSabbr.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblSabbr.setBounds(367, 142, 70, 25);
		pnlStock.add(lblSabbr);

		textFieldAbbr = new JXTextField();
		textFieldAbbr.setPrompt("Abbr");
		textFieldAbbr.setName("txtAccountName");
		textFieldAbbr.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldAbbr.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldAbbr.setBounds(440, 140, 50, 23);
		pnlStock.add(textFieldAbbr);
		
		registerFocusEvent();
	}

	class AccountInfoKeyListener implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {

		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e!=null){
				int KEY_CODE = e.getKeyCode();
				//LOGGER.info("Key Code : ["+KEY_CODE+"]");
				//JTextField
				if( e.getSource() instanceof JTextField && KEY_CODE == KeyEvent.VK_ENTER){

					JTextField source = (JTextField) e.getSource();
					LOGGER.info("JTextField : "+source.getName());
					if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_NAME)){
						txtNickName.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_NICK_NAME)){
						comboBoxCity.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_TRANSPORT)){
						txtGSTNumber.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_GST)){
						txtPhone.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_PHONE)){
						txtAlternatePhone.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_ALTERNATE_PHONE)){
						txtHouseShopNum.requestFocus();
					}
					else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_HOUSE)){
						txtStreet.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_STREET)){
						fmtTxtPinCode.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_PINCODE)){
						comboBoxCountry.requestFocus();
					}/*else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_LANDMARK)){
						txtEngageDate.requestFocus();
						//txtEngageDate.do
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_PAN)){
						txtLicence.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_ENGAGEDDATE)){
						txtPan.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_LICENCE)){
						btnUploadImage.requestFocus();
					}*/else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.BUTTON_ACTION_UPLOAD_IMAGE_ACCOUNT_INFO)){
						textAreaDesc.requestFocus();
					}

					/*combo-box editor*/
					else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_CITY)){
						String item = (String) comboBoxCity.getSelectedItem();
						if(Strings.isNullOrEmpty(item)){
							return ;
						}
						StateCityInfo stateCityInfo = LeamonERP.stateCityInfos.stream().filter(ele -> ele.getCity().equals(item)).findFirst().get();
						if(null != stateCityInfo){
							comboBoxState.setSelectedItem(stateCityInfo.getState());
							textFieldSCode.setText(stateCityInfo.getStateCode());
							textFieldAbbr.setText(stateCityInfo.getAbbreviations());
						}else{
							LOGGER.error("city not matched from list of objects");
						}
						comboBoxState.requestFocus();
					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_STATE)){
						/*fmtTxtPinCode.requestFocus();*/
						txtTransport.requestFocus();

					}else if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_COUNTRY)){
						btnUploadImage.requestFocus();
					}

				}/*else if(e.getSource() instanceof JComboBox && KEY_CODE == KeyEvent.VK_ENTER){
					JComboBox source = (JComboBox)e.getSource();
					if(source!=null && source.getName()!=null && source.getName().equals(LeamonERPConstants.TEXTFIELD_ACCOUNT_CITY)){
						String item = (String) source.getSelectedItem();
						StateCityInfo stateCityInfo = LeamonERP.stateCityInfos.stream().filter(ele -> ele.getCity().equals(item)).findFirst().get();
						if(null != stateCityInfo){
							comboBoxState.setSelectedItem(stateCityInfo.getState());
							textFieldSCode.setText(stateCityInfo.getStateCode());
							textFieldAbbr.setText(stateCityInfo.getAbbreviations());
						}else{
							LOGGER.error("city not matched from list of objects");
						}
					}
				}*///end if

			}//end if
		}//end event

		@Override
		public void keyReleased(KeyEvent e) {
		}

	}

	public MaskFormatter createMaskFormatter(){
		MaskFormatter maskFormatter = null;
		try {
			maskFormatter = new MaskFormatter("######");
		} catch (ParseException e) {
			LOGGER.error(e);
		}
		return maskFormatter;
	}

	class MyComboBoxRenderer extends JLabel implements ListCellRenderer
	{
		private String _title;

		public MyComboBoxRenderer(String title)
		{
			_title = title;
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus)
		{
			if (index == -1 && value == null) setText(_title);
			else setText(value.toString());
			return this;
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton){
			JButton btn = (JButton)e.getSource();
			if(btn.getName()!=null && btn.getName().equals(LeamonERPConstants.BUTTON_ACTION_ADD_ACCOUNT_INFO)){
				save();
			}else if(btn.getName()!=null && btn.getName().equals(LeamonERPConstants.BUTTON_ACTION_EDIT_ACCOUNT_INFO)){
				edit();
			}else if(btn.getName()!=null && btn.getName().equals(LeamonERPConstants.BUTTON_ACTION_DELETE_ACCOUNT_INFO)){
				delete();
			}else if (btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_UPLOAD_IMAGE_ACCOUNT_INFO)){
				imageShower();
			}
		}//end if button instance
	}

	public void save(){
		LOGGER.info("AccountInfoManager[save] inside");

		if(!isValidated()){
			LOGGER.error("validation fails on save.");
			return;
		}
		String name = txtName.getText();
		String city = ((JTextField)comboBoxCity.getEditor().getEditorComponent()).getText();
		String state = ((JTextField)comboBoxState.getEditor().getEditorComponent()).getText();

		String phone = txtPhone.getText();
		String alternatePhone=txtAlternatePhone.getText();//3.5 Ghanshyam code to alternate number
		String houseNum = txtHouseShopNum.getText();
		String street = txtStreet.getText();


		String pinCode = fmtTxtPinCode.getText();
		String country = ((JTextField)comboBoxCountry.getEditor().getEditorComponent()).getText();
		String landMark = txtLandMark.getText();
		String engageDate = txtEngageDate.getEditor().getText();
		String pan = txtPan.getText();
		String licence =  txtLicence.getText();

		String nickName = txtNickName.getText();
		String tinGST = txtGSTNumber.getText();
		String transport = txtTransport.getText();

		String description = textAreaDesc.getText();
		String imagePath = lblImagePath.getText();



		LOGGER.debug("name ["+name+"]");
		LOGGER.debug("phone ["+phone+"]");
		LOGGER.debug("houseNum ["+houseNum+"]");
		LOGGER.debug("street ["+street+"]");
		LOGGER.debug("city ["+city+"]");
		LOGGER.debug("state ["+state+"]");
		LOGGER.debug("pinCode ["+pinCode+"]");
		LOGGER.debug("country ["+country+"]");
		LOGGER.debug("landMark ["+landMark+"]");
		LOGGER.debug("engageDate ["+engageDate+"]");
		LOGGER.debug("pan ["+pan+"]");
		LOGGER.debug("licence ["+licence+"]");

		LOGGER.debug("nickName ["+nickName+"]");
		LOGGER.debug("tinGST ["+tinGST+"]");
		LOGGER.debug("transport ["+transport+"]");

		LOGGER.debug("description ["+description+"]");
		LOGGER.debug("imagePath ["+imagePath+"]");

		AccountInfo accountInfo =AccountInfo.builder().build();

		BigInteger bigPhone;
		BigInteger bigAlternatePhone;//ghan code
		try{
			bigPhone = new BigInteger(phone);
			//ghan code
			if(!Strings.isNullOrEmpty(alternatePhone)) {
				bigAlternatePhone= new BigInteger(alternatePhone);
			}else {
				bigAlternatePhone =new BigInteger("0");
			}
			//end ghan code
		}catch(Exception e){
			LOGGER.error(e);
			bigPhone = new BigInteger("0");
			bigAlternatePhone =new BigInteger("0");//ghan code
		}

		try{
			accountInfo = AccountInfo.builder().name(name).nickName(nickName).gstNumber(tinGST).transport(transport).
					phone(bigPhone).alternatePhone(bigAlternatePhone).houseShopNumber(houseNum)
					.street(street).city(city).state(state).country(country).landMark(landMark)
					.engagedDate(new Timestamp(txtEngageDate.getDate().getTime())).panCard(pan).licence(licence)
					.createdDate(new Timestamp(System.currentTimeMillis())).description(description)
					.lastUpdated(new Timestamp(System.currentTimeMillis())).isEnable(Boolean.TRUE).build();
		}catch(Exception e){
			LOGGER.error(e);
			lblMsg.setText("Account info ID["+accountInfo.getId()+"] "+e.getMessage());
			JOptionPane.showInternalMessageDialog(this, "Failed due to "+e.getMessage());
			return;
		}
		if(Strings.isNullOrEmpty(pinCode) || Strings.isNullOrEmpty(pinCode.trim())){
			accountInfo.setPinCode(0);
		}else{
			accountInfo.setPinCode(Integer.parseInt(pinCode));
		}
		try {
			AccountDaoImpl.getInstance().save(accountInfo);
			lblID.setText(""+accountInfo.getId());
			JOptionPane.showMessageDialog(this, "Saved sccessfully","Message",JOptionPane.PLAIN_MESSAGE);
			LeamonERP.accountListManager.refreshAccountInfoTable();
			lblMsg.setBackground(Color.GREEN);
			lblMsg.setText("save successfully.");
		} catch (Exception e) {
			LOGGER.error(e);
			lblMsg.setText("Account info ID["+accountInfo.getId()+"] "+e.getMessage());
			if(e.getMessage().contains("integrity constraint violation")){
				JOptionPane.showInternalMessageDialog(this, "Failed due to already exists","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				JOptionPane.showInternalMessageDialog(this, e.getMessage(),"Exception",JOptionPane.ERROR_MESSAGE);
			}
			return ;
		}
		
		if(Strings.isNullOrEmpty(imagePath)){
			LOGGER.error("Image path is blank "+imagePath);
			return ;
		}
		
		File file= new File(imagePath);
		CopyOption [] options = new CopyOption[]{
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES
		};
		String toImageName = LeamonERPConstants.IMAGE_PATH_ACCOUNT+accountInfo.getId()+System.currentTimeMillis()+".jpg";
		LOGGER.info("AccountInfoUI[save]  ImagePath "+toImageName);
		try {
			Files.copy(Paths.get(imagePath), 
					Paths.get(toImageName), 
					options);
			accountInfo.setImagePath(toImageName);
			AccountDaoImpl.getInstance().update(accountInfo);
			LOGGER.error("AccountInfoUI[save] image path updated in db");
		} catch (Exception e1) {
			LOGGER.error("AccountInfoUI[save] "+e1);
			LOGGER.error(e1);
			lblMsg.setBackground(Color.RED);
			lblMsg.setText("Item ID["+accountInfo.getId()+"] failed: "+e1.getMessage());
			JOptionPane.showInternalMessageDialog(this, "failed to upload image.");
		}

		LOGGER.info("AccountInfoUI[save] end.");
	}

	public void edit() {
		LOGGER.info("AccountInfoManager[edit] inside");
		String id = lblID.getText();
		if(Strings.isNullOrEmpty(id)){
			JOptionPane.showMessageDialog(this, "Failed to edit due to no ID association","Error",JOptionPane.ERROR_MESSAGE);
			return ;
		}
		
		if(!isValidated()){
			LOGGER.error("Validation fail");
			return;
		}
		
		String name = txtName.getText();
		String nickName = txtNickName.getText();
		String tinGST = txtGSTNumber.getText();
		String transport = txtTransport.getText();
		String phone = txtPhone.getText();
		String alternatePhone=txtAlternatePhone.getText();//3.5 Ghanshyam code to alternate number
		String houseNum = txtHouseShopNum.getText();
		String street = txtStreet.getText();
		String city = ((JTextField)comboBoxCity.getEditor().getEditorComponent()).getText();
		String state = ((JTextField)comboBoxState.getEditor().getEditorComponent()).getText();
		String pinCode = fmtTxtPinCode.getText();
		String country = ((JTextField)comboBoxCountry.getEditor().getEditorComponent()).getText();
		String landMark = txtLandMark.getText();
		String engageDate = txtEngageDate.getEditor().getText();
		String pan = txtPan.getText();
		String licence =  txtLicence.getText();
		String description = textAreaDesc.getText();
		String imagePath = lblImagePath.getText();

		LOGGER.debug("name ["+name+"]");
		LOGGER.debug("phone ["+phone+"]");
		LOGGER.debug("houseNum ["+houseNum+"]");
		LOGGER.debug("street ["+street+"]");
		LOGGER.debug("city ["+city+"]");
		LOGGER.debug("state ["+state+"]");
		LOGGER.debug("pinCode ["+pinCode+"]");
		LOGGER.debug("country ["+country+"]");
		LOGGER.debug("landMark ["+landMark+"]");
		LOGGER.debug("engageDate ["+engageDate+"]");
		LOGGER.debug("pan ["+pan+"]");
		LOGGER.debug("licence ["+licence+"]");

		LOGGER.debug("nickName ["+nickName+"]");
		LOGGER.debug("tinGST ["+tinGST+"]");
		LOGGER.debug("transport ["+transport+"]");

		LOGGER.debug("description ["+description+"]");
		LOGGER.debug("imagePath ["+imagePath+"]");

		AccountInfo accountInfo = AccountInfo.builder().build();

		BigInteger bigPhone;
		BigInteger bigAlternatePhone;//ghan code
		try{
			bigPhone = new BigInteger(phone);
			//ghan code
			if(!Strings.isNullOrEmpty(alternatePhone)) {
				bigAlternatePhone= new BigInteger(alternatePhone);
			}else {
				bigAlternatePhone =new BigInteger("0");
			}
			//end ghan code
		}catch(Exception e){
			LOGGER.error(e);
			bigPhone = new BigInteger("0");
			bigAlternatePhone =new BigInteger("0");
		}
		
		try{
			accountInfo = AccountInfo.builder().name(name).nickName(nickName).gstNumber(tinGST).transport(transport).
					phone(bigPhone).alternatePhone(bigAlternatePhone).houseShopNumber(houseNum)
					.street(street).city(city).state(state).country(country).landMark(landMark)
					.engagedDate(new Timestamp(txtEngageDate.getDate().getTime())).panCard(pan).licence(licence)
					.createdDate(new Timestamp(System.currentTimeMillis())).description(description)
					.lastUpdated(new Timestamp(System.currentTimeMillis())).isEnable(Boolean.TRUE).build();
			accountInfo.setId(Integer.parseInt(lblID.getText()));
		}catch(Exception e){
			LOGGER.error(e);
			lblMsg.setText("Account info ID["+accountInfo.getId()+"] "+e.getMessage());
			JOptionPane.showInternalMessageDialog(this, "Failed due to "+e.getMessage(),"Exception",JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(Strings.isNullOrEmpty(pinCode) || Strings.isNullOrEmpty(pinCode.trim())){
			accountInfo.setPinCode(0);
		}else{
			try{
			accountInfo.setPinCode(Integer.parseInt(pinCode));
			}catch(Exception e){
				accountInfo.setPinCode(Integer.parseInt("0"));
				LOGGER.error(e);
			}
		}
		try {
			AccountDaoImpl.getInstance().update(accountInfo);
			lblMsg.setBackground(Color.GREEN);
			lblMsg.setText("Item ID["+accountInfo.getId()+"] save successfully.");
			JOptionPane.showInternalMessageDialog(this, "Saved successfully","Leamon-ERP", JOptionPane.PLAIN_MESSAGE);
			LeamonERP.accountListManager.refreshAccountInfoTable();
		} catch (Exception e) {
			LOGGER.error(e);
			lblMsg.setText("Account info ID["+accountInfo.getId()+"] "+e.getMessage());
			JOptionPane.showInternalMessageDialog(this, "Failed due to :"+e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			return ;
		}

		if(Strings.isNullOrEmpty(""+accountInfo.getId()) || Strings.isNullOrEmpty(imagePath)){
			return;
		}
		File file= new File(imagePath);
		CopyOption [] options = new CopyOption[]{
				StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES
		};
		String toImageName = LeamonERPConstants.IMAGE_PATH_ACCOUNT+accountInfo.getId()+System.currentTimeMillis()+".jpg";
		LOGGER.info("AccountInfoUI[edit]  ImagePath "+toImageName);
		try {
			Files.copy(Paths.get(imagePath), 
					Paths.get(toImageName), 
					options);
			accountInfo.setImagePath(toImageName);
			AccountDaoImpl.getInstance().update(accountInfo);
			LeamonERP.accountListManager.refreshAccountInfoTable();
			LOGGER.info("AccountInfoUI[save] image path updated in db");
		} catch (Exception e1) {
			LOGGER.error("AccountInfoUI[save] "+e1);
			lblMsg.setBackground(Color.RED);
			lblMsg.setText("Item ID["+accountInfo.getId()+"] failed: "+e1.getMessage());
			JOptionPane.showInternalMessageDialog(this, "failed to upload image due to : "+e1.getMessage(),"Exception", JOptionPane.ERROR_MESSAGE);
		}

		LeamonERP.accountListManager.refreshAccountInfoTable();
		LOGGER.info("AccountInfoUI[edit] end.");
	}

	public void delete(){
		LOGGER.info("AccountInfoUI[delete] inside.");
		String idVal = lblID.getText();
		if(Strings.isNullOrEmpty(idVal)){
			JOptionPane.showMessageDialog(this, "No ID Associated", "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		Integer id = Integer.parseInt(idVal);
		AccountInfo item = AccountInfo.builder().id(id).build();
		try {
			AccountDaoImpl.getInstance().disable(item);
			lblMsg.setBackground(Color.GREEN);
			lblMsg.setText("Item "+id +" deleted successfully.");
			JOptionPane.showMessageDialog(this, "Deleted successfully.", "Leamon-ERP", JOptionPane.PLAIN_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Failed due to : "+e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
			LOGGER.error(e);
		}

		LeamonERP.accountListManager.refreshAccountInfoTable();
		LOGGER.info("AccountInfoUI[delete] end.");
	}

	public void setAccountInfo(AccountInfo si) {
		lblID.setText(""+si.getId());
		txtName.setText(si.getName());
		txtNickName.setText(si.getNickName());
		txtGSTNumber.setText(si.getGstNumber());
		txtTransport.setText(si.getTransport());
		txtPhone.setText(""+si.getPhone());
		
		if(null == si.getAlternatePhone()){
			txtAlternatePhone.setText(LeamonERPConstants.EMPTY_STR);
		}else{
			txtAlternatePhone.setText(si.getAlternatePhone().toString());
		}
		
		//3.5 Ghanshyam code to alternate number
		txtHouseShopNum.setText(si.getHouseShopNumber());
		txtStreet.setText(si.getStreet());
		fmtTxtPinCode.setText(""+si.getPinCode());
		txtLandMark.setText(si.getLandMark());
		txtEngageDate.setDate(si.getEngagedDate());
		txtPan.setText(si.getPanCard());
		txtLicence.setText(si.getLicence());
		textAreaDesc.setText(si.getDescription());
		lblMsg.setText(LeamonERPConstants.EMPTY_STR);
		String imagePath = si.getImagePath();
		if(!Strings.isNullOrEmpty(imagePath )){
			File f = new File(imagePath);
			if(f.isFile()){
				LOGGER.debug("AccountInfoUI[setAccountInfo] is a file");
				LOGGER.debug("AccountInfoUI[setAccountInfo] Image Path Found ["+imagePath+"] imag set");
				lblAcountImage.setIcon(new ImageIcon(imagePath));
			}else{
				LOGGER.debug("AccountInfoUI[setAccountInfo]  is not file");
				LOGGER.debug("AccountInfoUI[setAccountInfo] Setting default Image ["+LeamonERPConstants.NO_IMAGE+"] ");
				lblAcountImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
			}
		}else{
			lblAcountImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		}

		comboBoxCity.getEditor().setItem(si.getCity());
		String item =si.getCity();
		if(Strings.isNullOrEmpty(item)){
			return ;
		}
		StateCityInfo stateCityInfo = LeamonERP.stateCityInfos.stream().filter(ele -> ele.getCity().equals(item)).findFirst().get();
		if(null != stateCityInfo){
			comboBoxState.setSelectedItem(stateCityInfo.getState());
			textFieldSCode.setText(stateCityInfo.getStateCode());
			textFieldAbbr.setText(stateCityInfo.getAbbreviations());
		}else{
			LOGGER.error("city not matched from list of objects");
		}
		
		comboBoxCountry.getEditor().setItem(si.getCountry());

		lblImagePath.setText(LeamonERPConstants.EMPTY_STR);
	}

	class CustomKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e!=null){
				int KEY_CODE = e.getKeyCode();
				if(e.getSource() instanceof JButton){
					JButton btn = (JButton) e.getSource();
					if(btn.getActionCommand()!=null && btn.getActionCommand().equals(LeamonERPConstants.BUTTON_ACTION_UPLOAD_IMAGE_ACCOUNT_INFO) && KEY_CODE == KeyEvent.VK_ENTER){
						btn.doClick();
					}
				}//end if jbutton
			}//end if event code
		}// end event

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}//en if custome event

	private void imageShower(){
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setDialogTitle("Account Info Image");
		chooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("PNG, GIF, JPEG, JPG images", "png","gif","jpg","jpeg");
		chooser.addChoosableFileFilter(fileNameExtensionFilter);

		int x = chooser.showDialog(LeamonERP.stockItemManager, "Select Image");
		LOGGER.info("AccountInfoUI[imageShower]  dialog is opened.");
		if(x == JFileChooser.APPROVE_OPTION){
			LOGGER.info("AccountInfoUI[imageShower] approve button pressed.");
			File file = chooser.getSelectedFile();

			lblImagePath.setText(file.getPath());
			lblAcountImage.setIcon(new ImageIcon(file.getPath()));
			LOGGER.info("AccountInfoUI[imageShower]  image has been set.");
		}

		btnSave.requestFocus();
	}

	public void loadDefaultImage(){
		//lblAcountImage
		lblAcountImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
	}

	private boolean isValidated(){
		String name = txtName.getText();
		String city = ((JTextField)comboBoxCity.getEditor().getEditorComponent()).getText();
		String state = ((JTextField)comboBoxState.getEditor().getEditorComponent()).getText();

		if(Strings.isNullOrEmpty(name)){
			JOptionPane.showMessageDialog(this, "Name can not be left blank. It is mendatory", "Validation-Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if(Strings.isNullOrEmpty(city)){
			JOptionPane.showMessageDialog(this, "City can not be left blank. It is mendatory", "Validation-Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if(Strings.isNullOrEmpty(state)){
			JOptionPane.showMessageDialog(this, "State can not be left blank. It is mendatory", "Validation-Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private void registerFocusEvent(){
		txtPhone.addFocusListener(new FocusListenerHandler());
		txtAlternatePhone.addFocusListener(new FocusListenerHandler());//3.5 Ghanshyam code to alternate number
		txtName.addFocusListener(new FocusListenerHandler());
		txtHouseShopNum.addFocusListener(new FocusListenerHandler());
		txtStreet.addFocusListener(new FocusListenerHandler());
		txtLandMark.addFocusListener(new FocusListenerHandler());
		txtPan.addFocusListener(new FocusListenerHandler());
		txtLicence.addFocusListener(new FocusListenerHandler());
		fmtTxtPinCode.addFocusListener(new FocusListenerHandler());

		cityEditor.addFocusListener(new FocusListenerHandler());
		stateEditor.addFocusListener(new FocusListenerHandler());
		countryEditor.addFocusListener(new FocusListenerHandler());
		txtNickName.addFocusListener(new FocusListenerHandler());
		txtGSTNumber.addFocusListener(new FocusListenerHandler());
		txtTransport.addFocusListener(new FocusListenerHandler());
		textFieldSCode.addFocusListener(new FocusListenerHandler());
		textFieldAbbr.addFocusListener(new FocusListenerHandler());
	}
	
	public void clear(){
		txtName.setText(LeamonERPConstants.EMPTY_STR);
		txtNickName.setText(LeamonERPConstants.EMPTY_STR);
		txtGSTNumber.setText(LeamonERPConstants.EMPTY_STR);
		txtTransport.setText(LeamonERPConstants.EMPTY_STR);
		txtPhone.setText(LeamonERPConstants.EMPTY_STR);
		txtAlternatePhone.setText(LeamonERPConstants.EMPTY_STR);//3.5 Ghanshyam code to alternate number
		txtHouseShopNum.setText(LeamonERPConstants.EMPTY_STR);
		txtStreet.setText(LeamonERPConstants.EMPTY_STR);
		((JTextField)comboBoxCity.getEditor().getEditorComponent()).setText(LeamonERPConstants.EMPTY_STR);
		((JTextField)comboBoxState.getEditor().getEditorComponent()).setText(LeamonERPConstants.EMPTY_STR);
		((JTextField)comboBoxCountry.getEditor().getEditorComponent()).setText(LeamonERPConstants.EMPTY_STR);
		fmtTxtPinCode.setText(LeamonERPConstants.EMPTY_STR);
		txtLandMark.setText(LeamonERPConstants.EMPTY_STR);
		txtLicence.setText(LeamonERPConstants.EMPTY_STR);
		txtEngageDate.getEditor().setText(LeamonERPConstants.EMPTY_STR);
		txtPan.setText(LeamonERPConstants.EMPTY_STR);
		textAreaDesc.setText(LeamonERPConstants.EMPTY_STR);
		textFieldSCode.setText(LeamonERPConstants.EMPTY_STR);
		textFieldAbbr.setText(LeamonERPConstants.EMPTY_STR);
		lblAcountImage.setText(LeamonERPConstants.EMPTY_STR);
		lblAcountImage.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.NO_IMAGE)));
		lblImagePath.setText(LeamonERPConstants.EMPTY_STR);
		lblID.setText(LeamonERPConstants.EMPTY_STR);
		lblMsg.setText(LeamonERPConstants.EMPTY_STR);
	}

	public void btnClearClick(ActionEvent e){
		clear();
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

	public Action getDeleteAction(){
		Action deleteAction = new AbstractAction("Delete") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + D clicked");
				delete();
			}
		};
		deleteAction.putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		return deleteAction;
	}

	public Action getEditAction(){
		Action editAction = new AbstractAction("Edit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + E clicked");
				edit();
			}
		};
		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	public Action getClearAction(){
		Action editAction = new AbstractAction("Clear") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + R clicked");
				clear();
			}
		};
		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}
	
	public Action  getUploadImageAction(){
		Action editAction = new AbstractAction("Upload Image") {
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("ctrl + U clicked");
				imageShower();
			}
		};
		editAction .putValue(Action.MNEMONIC_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		return editAction;
	}

	// 3.4 Ghanshyam code for account name auto suggestor
	private void autoNameSuggestor(JTextField txtName) {
		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoAccountIDTextFieldSuggestor = new LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo>(
				txtName, this);
		leamonAutoAccountIDTextFieldSuggestor.setItems(accountInfos);
	}
	// 3.4 end of ghanshyam code
}
