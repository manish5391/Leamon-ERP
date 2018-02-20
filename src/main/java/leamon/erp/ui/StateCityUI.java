package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;

import org.apache.log4j.Logger;
import org.apache.xpath.operations.Bool;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.db.StateCityDaoImpl;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.StateCityInfo;
import leamon.erp.model.StockItem;
import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

import java.awt.Font;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXTextField;

import com.google.common.base.Strings;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * State City UI
 * 
 * @author Manish Kumar  Mishra
 * @date FEB 19,2018
 * 
 */
@Getter
public class StateCityUI extends JInternalFrame implements KeyListener{

	static final Logger LOGGER = Logger.getLogger(StateCityManagerUI.class);
	static final String CLASS_NAME = "StateCityUI";
	
	private JXTextField textFieldCity;
	private JXTextField textFieldState;
	private JXTextField textFieldStateCode;
	private JXTextField textFieldAbbr;
	
	private JButton buttonSave;
	private JButton buttonDelete;
	private JButton buttonEdit;
	private JButton buttonClear;
	
	
	private JLabel lblID;
	private JLabel labelMsg;
	
	public StateCityUI() {
		setTitle("State & City UI");
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(3, 30, 513, 306);
		getContentPane().setLayout(null);
		
		labelMsg = new JLabel("");
		labelMsg.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		labelMsg.setFont(new Font("DialogInput", Font.BOLD, 16));
		labelMsg.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		labelMsg.setBounds(49, 0, 458, 22);
		getContentPane().add(labelMsg);
		
		JLabel lblCity = new JLabel("City");
		lblCity.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		lblCity.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblCity.setBounds(10, 27, 40, 22);
		getContentPane().add(lblCity);
		
		JLabel label_2 = new JLabel("*");
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_2.setBounds(49, 27, 17, 22);
		getContentPane().add(label_2);
		
		textFieldCity = new JXTextField();
		textFieldCity.setPrompt("City");
		textFieldCity.setName("txtAccountName");
		textFieldCity.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldCity.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldCity.setBounds(140, 27, 219, 23);
		getContentPane().add(textFieldCity);
		
		JLabel lblState = new JLabel("State");
		lblState.setForeground((Color) null);
		lblState.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblState.setBounds(10, 75, 50, 22);
		getContentPane().add(lblState);
		
		JLabel label_4 = new JLabel("*");
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("DialogInput", Font.BOLD, 16));
		label_4.setBounds(60, 75, 17, 22);
		getContentPane().add(label_4);
		
		textFieldState = new JXTextField();
		textFieldState.setPrompt("State");
		textFieldState.setName("txtAccountName");
		textFieldState.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldState.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldState.setBounds(140, 75, 219, 23);
		getContentPane().add(textFieldState);
		
		JLabel lblStateCode = new JLabel("State Code");
		lblStateCode.setForeground((Color) null);
		lblStateCode.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblStateCode.setBounds(10, 114, 100, 22);
		getContentPane().add(lblStateCode);
		
		textFieldStateCode = new JXTextField();
		textFieldStateCode.setPrompt("State Code");
		textFieldStateCode.setName("txtAccountName");
		textFieldStateCode.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldStateCode.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldStateCode.setBounds(140, 117, 219, 23);
		getContentPane().add(textFieldStateCode);
		
		JLabel lblAbbre = new JLabel("Abbreviation");
		lblAbbre.setForeground((Color) null);
		lblAbbre.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblAbbre.setBounds(10, 159, 120, 22);
		getContentPane().add(lblAbbre);
		
		textFieldAbbr = new JXTextField();
		textFieldAbbr.setPrompt("Avvreviation");
		textFieldAbbr.setName("txtAccountName");
		textFieldAbbr.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldAbbr.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldAbbr.setBounds(140, 162, 219, 23);
		getContentPane().add(textFieldAbbr);
		
		buttonClear = new JButton("Clear");
		buttonClear.setMnemonic('C');
		buttonClear.setMnemonic(KeyEvent.VK_R);
		buttonClear.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		buttonClear.setFont(new Font("DialogInput", Font.BOLD, 14));
		buttonClear.setBackground(Color.WHITE);
		buttonClear.setBounds(10, 225, 121, 43);
		// 3.6 ghan code
		try {
			buttonClear.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_TOOLS_STATE_CITY_CLEAR)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		getContentPane().add(buttonClear);
		buttonClear.addActionListener(e -> buttonClearClick(e));
		
		buttonEdit = new JButton("Edit");
		buttonEdit.setName("EditAccountInfo");
		buttonEdit.setMnemonic('E');
		buttonEdit.setMnemonic(KeyEvent.VK_E);
		buttonEdit.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		buttonEdit.setFont(new Font("DialogInput", Font.BOLD, 14));
		buttonEdit.setBackground(Color.WHITE);
		buttonEdit.setBounds(263, 225, 118, 43);
		// 3.6 ghan code
		try {
			buttonEdit.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_TOOLS_STATE_CITY_EDIT)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		getContentPane().add(buttonEdit);
		buttonEdit.addActionListener(e -> buttonEditClick(e));
		
		buttonSave = new JButton("Save");
		buttonSave.setName("AddAccountInfo");
		buttonSave.setMnemonic('S');
		buttonSave.setMnemonic(KeyEvent.VK_S);
		buttonSave.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		buttonSave.setFont(new Font("DialogInput", Font.BOLD, 14));
		buttonSave.setBackground(Color.WHITE);
		buttonSave.setBounds(140, 225, 118, 43);
		// 3.6 ghan code
		try {
			buttonSave.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_TOOLS_STATE_CITY_SAVE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		getContentPane().add(buttonSave);
		buttonSave.addActionListener(e -> buttonSaveClick(e));
		
		buttonDelete = new JButton("Delete");
		buttonDelete.setName("DeleteAccountInfo");
		buttonDelete.setMnemonic('D');
		buttonDelete.setMnemonic(KeyEvent.VK_D);
		buttonDelete.setForeground(UIManager.getColor("OptionPane.warningDialog.titlePane.foreground"));
		buttonDelete.setFont(new Font("DialogInput", Font.BOLD, 14));
		buttonDelete.setBackground(Color.WHITE);
		buttonDelete.setBounds(391, 224, 110, 43);
		// 3.6 ghan code
		try {
			buttonDelete.setIcon(new ImageIcon(
					LeamonERPConstants.IMAGE_PATH_LEAMON_ERP.concat(LeamonERPConstants.IMG_TOOLS_STATE_CITY_DELETE)));
		} catch (Exception e) {
			LOGGER.error(e);
		}
		// 3.6 end of ghan code
		getContentPane().add(buttonDelete);
		buttonDelete.addActionListener(e -> buttonDeleteClick(e));
		
		lblID = new JLabel("");
		lblID.setForeground((Color) null);
		lblID.setFont(new Font("DialogInput", Font.BOLD, 16));
		lblID.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		lblID.setBounds(0, 0, 48, 22);
		getContentPane().add(lblID);
		
		registerEvent();
	}
	
	public void registerEvent(){
		textFieldCity.addKeyListener(this);
		textFieldState.addKeyListener(this);
		textFieldStateCode.addKeyListener(this);
		textFieldAbbr.addKeyListener(this);
	}
	
	public void setStateCityInfo(StateCityInfo item){
		final String METHOD_NAME = "setStateCityInfo";
		
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside.");
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] "+item);
		textFieldCity.setText(item.getCity());
		textFieldState.setText(item.getState());
		textFieldStateCode.setText(item.getStateCode());
		textFieldAbbr.setText(item.getAbbreviations());
		lblID.setText(""+item.getId());
		labelMsg.setText(LeamonERPConstants.EMPTY_STR);
		buttonSave.setEnabled(false);
		
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] End.");
	}
	
	private void save(){
		final String METHOD_NAME = "save";
		LOGGER.info(CLASS_NAME+"["+METHOD_NAME+"] inside.");
		
		String city = textFieldCity.getText();
		String state = textFieldState.getText();
		String stateCode = textFieldStateCode.getText();
		String abbr = textFieldAbbr.getText();
		
		//3.6 ghan code
		if(Strings.isNullOrEmpty(city)) {
			JOptionPane.showMessageDialog(this, "City can not be blank it's mandatory", "Error", JOptionPane.ERROR_MESSAGE);
			textFieldCity.requestFocus();
			return;
		}else if(Strings.isNullOrEmpty(state)){
			JOptionPane.showMessageDialog(this, "State can not be blank it's mandatory", "Error", JOptionPane.ERROR_MESSAGE);
			textFieldState.requestFocus();
			return;
		}
		//3.6 end of ghan code
		
		Integer nextId = 0;
		try{
			List<StateCityInfo> stateCityInfos = StateCityDaoImpl.getInstance().getItemListIncludingDisabled();
			nextId =  stateCityInfos.get(stateCityInfos.size()-1).getId()+1;
		}catch(Exception e){
			LOGGER.error(e);
		}
		
		StateCityInfo stateCityInfo = StateCityInfo.builder()
				.id(nextId)
				.city(city)
				.state(state)
				.stateCode(stateCode)
				.abbreviations(abbr)
				.createdDate(new Timestamp(System.currentTimeMillis()))
				.lastUpdatedDate(new Timestamp(System.currentTimeMillis()))
				.isEnable(Boolean.TRUE)
		.build();
		
		
		try{
			StateCityDaoImpl.getInstance().save(stateCityInfo);
			labelMsg.setText("ID["+stateCityInfo.getId()+"] save successfully.");
			JOptionPane.showInternalMessageDialog(this, "Saved.");
			buttonClearClick(null);
		}catch(Exception exp){
			LOGGER.error(CLASS_NAME+"["+METHOD_NAME+"] : "+exp);
			JOptionPane.showInternalMessageDialog(this, "failed.");
		}
		
	}
	
	public void buttonClearClick(ActionEvent e){
		textFieldCity.setText(LeamonERPConstants.EMPTY_STR);
		textFieldState.setText(LeamonERPConstants.EMPTY_STR);
		textFieldStateCode.setText(LeamonERPConstants.EMPTY_STR);
		textFieldAbbr.setText(LeamonERPConstants.EMPTY_STR);
		lblID.setText(LeamonERPConstants.EMPTY_STR);
		labelMsg.setText(LeamonERPConstants.EMPTY_STR);
	}	
	
	private void buttonEditClick(ActionEvent e){
		
	}

	private void buttonDeleteClick(ActionEvent e){
		
	}

	private void buttonSaveClick(ActionEvent e){
		save();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int KEY_CODE = e.getKeyCode();
		if( e.getSource() instanceof JTextField && KEY_CODE == KeyEvent.VK_ENTER){
			JTextField source = (JTextField) e.getSource();
			
			if(source.equals(textFieldCity)){
				textFieldState.requestFocus();
			}else if(source.equals(textFieldState)){
				checkStateCode();//3.6 ghan code
				textFieldStateCode.requestFocus();
			}else if(source.equals(textFieldStateCode)){
				textFieldAbbr.requestFocus();
			}else if(source.equals(textFieldAbbr)){
				buttonSave.requestFocus();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// 3.6 ghan code
	private void checkStateCode() {
		StateCityDaoImpl daoImpl = StateCityDaoImpl.getInstance();
		List<StateCityInfo> stateCityInfos = new ArrayList<StateCityInfo>();
		try {
			stateCityInfos = daoImpl.getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}

		StateCityInfo stateInfo = stateCityInfos.stream()
				.filter(e -> e.getState().equalsIgnoreCase(textFieldState.getText())).findAny().orElse(null);
		if (null != stateInfo) {
			textFieldStateCode.setText(stateInfo.getStateCode());
			textFieldAbbr.setText(stateInfo.getAbbreviations());
		}
	}
	// 3.6 end of ghan code
	
}
