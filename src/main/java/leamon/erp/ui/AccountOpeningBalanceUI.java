package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import org.jdesktop.swingx.JXPanel;
import javax.swing.border.BevelBorder;
import org.jdesktop.swingx.JXLabel;
import java.awt.Font;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Getter
public class AccountOpeningBalanceUI extends JInternalFrame {

	private static final String CLASS_NAME="AccountOpeningBalanceUI";
	private static final Logger LOGGER = Logger.getLogger(AccountOpeningBalanceUI.class);
	
	private JXTextField textFieldPartyName;
	private JXTextField textFieldBillNo;
	private JXTextField textFieldOpeningBalance;
	private JXTextField textFieldRemark;
	
	private JCheckBox chckbxBAmount;
	private JCheckBox chckbxWAmount;
	
	private JXDatePicker datePickerBillDate;
	
	private ButtonGroup bg;
	
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;
	
	private AccountInfo accountInfo;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountOpeningBalanceUI frame = new AccountOpeningBalanceUI();
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
	public AccountOpeningBalanceUI() {
		setTitle("Opening Balance");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setBackground(Color.WHITE);
		setBounds(230, 30, 585, 299);
		getContentPane().setLayout(null);
		
		JXPanel panel = new JXPanel();
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(189, 183, 107));
		panel.setBounds(0, 0, 580, 240);
		getContentPane().add(panel);
		
		JXLabel label_1 = new JXLabel();
		label_1.setText("*");
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_1.setBounds(87, 15, 6, 16);
		panel.add(label_1);
		
		JXLabel label_2 = new JXLabel();
		label_2.setText("*");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_2.setBounds(73, 80, 6, 16);
		panel.add(label_2);
		
		UIManager.put(CalendarHeaderHandler.uiControllerID, SpinningCalendarHeaderHandler.class.getName());
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerBillDate = new JXDatePicker((Date) null);
		datePickerBillDate.setFormats(df);
		datePickerBillDate.getEditor().setEnabled(true);
		datePickerBillDate.setBounds(148, 78, 145, 22);
		panel.add(datePickerBillDate);
		
		textFieldRemark = new JXTextField();
		textFieldRemark.setPrompt("Remark");
		textFieldRemark.setName("txtFieldBRemark");
		textFieldRemark.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldRemark.setEnabled(true);
		textFieldRemark.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldRemark.setBounds(73, 196, 497, 23);
		panel.add(textFieldRemark);
		
		textFieldOpeningBalance = new JXTextField();
		textFieldOpeningBalance.setPrompt("Opening Balance");
		textFieldOpeningBalance.setName("txtFieldPayment");
		textFieldOpeningBalance.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldOpeningBalance.setEnabled(true);
		textFieldOpeningBalance.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldOpeningBalance.setBounds(148, 162, 166, 23);
		panel.add(textFieldOpeningBalance);
		
		textFieldPartyName = new JXTextField();
		textFieldPartyName.setPrompt("Select Party Name");
		textFieldPartyName.setName("txtPartyName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyName.setBounds(148, 11, 406, 23);
		panel.add(textFieldPartyName);
		autoAccountInfoSuggestor(textFieldPartyName);
		
		JXLabel lblOpeningBalance = new JXLabel();
		lblOpeningBalance.setText("Opening Balance");
		lblOpeningBalance.setForeground(Color.BLACK);
		lblOpeningBalance.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblOpeningBalance.setBounds(10, 160, 106, 25);
		panel.add(lblOpeningBalance);
		
		JXLabel label_4 = new JXLabel();
		label_4.setText("Party Name");
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_4.setBounds(10, 9, 71, 25);
		panel.add(label_4);
		
		JXLabel label_5 = new JXLabel();
		label_5.setText("Remark");
		label_5.setForeground(Color.BLACK);
		label_5.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_5.setBounds(10, 194, 46, 25);
		panel.add(label_5);
		
		JXLabel lblBillDate = new JXLabel();
		lblBillDate.setText("Bill Date");
		lblBillDate.setForeground(Color.BLACK);
		lblBillDate.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
		lblBillDate.setBounds(10, 78, 67, 25);
		panel.add(lblBillDate);
		
		JXLabel label_7 = new JXLabel();
		label_7.setText("*");
		label_7.setForeground(Color.RED);
		label_7.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_7.setBounds(120, 164, 6, 16);
		panel.add(label_7);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBackground(new Color(102, 205, 170));
		panel_1.setBounds(148, 109, 226, 42);
		panel.add(panel_1);
		
		chckbxBAmount = new JCheckBox("B. Amount");
		chckbxBAmount.setFont(new Font("SansSerif", Font.BOLD, 13));
		panel_1.add(chckbxBAmount);
		
		chckbxWAmount = new JCheckBox("W. Amount");
		chckbxWAmount.setFont(new Font("SansSerif", Font.BOLD, 13));
		panel_1.add(chckbxWAmount);
		
		bg = new ButtonGroup();
		bg.add(chckbxBAmount);
		bg.add(chckbxWAmount);
		
		textFieldBillNo = new JXTextField();
		textFieldBillNo.setPrompt("Bill No.");
		textFieldBillNo.setName("txtFieldPayment");
		textFieldBillNo.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldBillNo.setEnabled(true);
		textFieldBillNo.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldBillNo.setBounds(148, 45, 94, 23);
		panel.add(textFieldBillNo);
		
		JXLabel lblBillNo = new JXLabel();
		lblBillNo.setText("Bill No.");
		lblBillNo.setForeground(Color.BLACK);
		lblBillNo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblBillNo.setBounds(10, 44, 61, 25);
		panel.add(lblBillNo);
		
		JXLabel label_3 = new JXLabel();
		label_3.setText("*");
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label_3.setBounds(61, 45, 6, 16);
		panel.add(label_3);
		
		JXLabel lblFs = new JXLabel();
		lblFs.setText("F/S");
		lblFs.setForeground(Color.BLACK);
		lblFs.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblFs.setBounds(10, 114, 37, 25);
		panel.add(lblFs);
		
		JXLabel label = new JXLabel();
		label.setText("*");
		label.setForeground(Color.RED);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		label.setBounds(33, 114, 6, 16);
		panel.add(label);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(292, 239, 82, 23);
		getContentPane().add(btnSave);
		
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.addActionListener(e -> buttonRefreshClick(e));
		buttonRefresh.setBounds(382, 239, 94, 23);
		getContentPane().add(buttonRefresh);
		
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> buttonCloseClick(e));
		buttonClose.setBounds(486, 239, 94, 23);
		getContentPane().add(buttonClose);

	}
	
	private void buttonCloseClick(ActionEvent e){
		this.dispose();
	}
	
	private void buttonRefreshClick(ActionEvent e){
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		textFieldBillNo.setText(LeamonERPConstants.EMPTY_STR);
		textFieldOpeningBalance.setText(LeamonERPConstants.EMPTY_STR);
		textFieldRemark.setText(LeamonERPConstants.EMPTY_STR);
		bg.clearSelection();
	}
	
	public void setAccountInfo(AccountInfo info){
		accountInfo = info;
		textFieldPartyName.setText(info.getName());
		
	}
	
	public void autoAccountInfoSuggestor(JTextField textField){
		final String methodName="autoAccountInfoSuggestor";
		LOGGER.info(CLASS_NAME+"["+methodName+"] inside");

		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoAccountIDTextFieldSuggestor 
		= new LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo>(textField, this);
		leamonAutoAccountIDTextFieldSuggestor.setItems(accountInfos);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}
}
