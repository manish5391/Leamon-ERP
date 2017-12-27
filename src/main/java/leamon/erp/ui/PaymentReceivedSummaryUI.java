package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import lombok.Getter;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXMonthView;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.calendar.DateSelectionModel;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;
import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.table.ColumnControlButton;
import org.jdesktop.swingx.table.TableColumnModelExt;

import com.google.common.base.Strings;


import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.InvoiceDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.ui.model.GenericModelWithSnp;
import leamon.erp.ui.model.TablePaymentReceivedModel;
import leamon.erp.ui.model.TablePaymentReceivedSummaryModel;
import leamon.erp.util.LeamonERPConstants;
import leamon.erp.util.LeamonUtil;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import leamon.erp.util.InvoicePaymentStatusEnum;

@Getter
public class PaymentReceivedSummaryUI extends JInternalFrame {
	
	private static final Logger LOGGER = Logger.getLogger(PaymentReceivedSummaryUI.class);
	private static final String CLASS_NAME="PaymentReceivedUI";
	
	
	private JXTextField textFieldPartyName; 
	private JXDatePicker datePickerFrom;
	private JXDatePicker datePickerTo;
	private JCheckBox chckbxB;
	private JCheckBox chckbxW;
	private JCheckBox chckbxBoth;
	private JXButton btnSearch;
	private JXTable table;
	private JTextField cityEditor;
	private JComboBox comboBoxCity;
	private JXTextField textFieldTotalWAmt;
	private JXTextField textFieldTotalBAmt;
	private JXTextField textFieldTotalAmt;
	private JComboBox comboBoxSatus;
	
	private ButtonGroup bg;
	
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;
	private AccountInfo accountInfo;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentReceivedSummaryUI frame = new PaymentReceivedSummaryUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public PaymentReceivedSummaryUI() {
		
		setTitle("Payment Received Summary");
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(204, 255, 153));
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(0, 0, 852, 85);
		panel.add(panel_1);
		
		JXLabel label = new JXLabel();
		label.setText("Party Name");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		panel_1.add(label);
		
		textFieldPartyName = new JXTextField();
		textFieldPartyName.setPrompt("Select Party Name                ");
		textFieldPartyName.setName("txtPartyName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 14));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		autoAccountInfoSuggestor(textFieldPartyName);
		panel_1.add(textFieldPartyName);
		
		JXLabel lblSearchBy = new JXLabel();
		lblSearchBy.setText("From");
		lblSearchBy.setForeground(Color.BLACK);
		lblSearchBy.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		panel_1.add(lblSearchBy);
		
		DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
		datePickerFrom = new JXDatePicker((Date) null);
		datePickerFrom .setFormats(df);
		datePickerFrom.getEditor().setEnabled(true);
		datePickerFrom.getMonthView().setZoomable(true);
		panel_1.add(datePickerFrom);
		
		JXLabel lblTo = new JXLabel();
		lblTo.setText("To");
		lblTo.setForeground(Color.BLACK);
		lblTo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		panel_1.add(lblTo);
		
		datePickerTo = new JXDatePicker((Date) null);
		datePickerTo .setFormats(df);
		datePickerTo.getEditor().setEnabled(true);
		datePickerTo.getMonthView().setZoomable(true);
		panel_1.add(datePickerTo);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_4.setBackground(new Color(102, 205, 170));
		panel_1.add(panel_4);
		
		
		
		chckbxB = new JCheckBox("B.");
		chckbxB.setFont(new Font("SansSerif", Font.BOLD, 13));
		panel_4.add(chckbxB);
		
		chckbxW = new JCheckBox("W.");
		chckbxW.setFont(new Font("SansSerif", Font.BOLD, 13));
		panel_4.add(chckbxW);
		
		chckbxBoth = new JCheckBox("Both");
		chckbxBoth.setFont(new Font("SansSerif", Font.BOLD, 13));
		panel_4.add(chckbxBoth);
		
		bg = new ButtonGroup();
		bg.add(chckbxB);
		bg.add(chckbxW);
		bg.add(chckbxBoth);
		
		btnSearch = new JXButton();
		btnSearch.setText("Search Payment History");
		btnSearch.addActionListener(e -> btnSearchClick(e));
		
		comboBoxCity = new JComboBox();
		comboBoxCity.setName("txtAccountCity");
		comboBoxCity.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		comboBoxCity.setBackground(Color.WHITE);
		LeamonUtil.prepareAutoCompleterCombo(comboBoxCity, LeamonERP.cityCache);
		cityEditor = (JTextField)comboBoxCity.getEditor().getEditorComponent();
		
		panel_1.add(comboBoxCity);
		
		comboBoxSatus = new JComboBox();
		comboBoxSatus.setModel(new DefaultComboBoxModel(InvoicePaymentStatusEnum.values()));
		comboBoxSatus.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		comboBoxSatus.setBackground(Color.WHITE);
		panel_1.add(comboBoxSatus);
		
		
		panel_1.add(btnSearch);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(153, 204, 255));
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBounds(0, 80, 852, 436);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane);
		
		table = new JXTable();
		table.setColumnControlVisible(true);
		table.packAll();
		scrollPane.setViewportView(table);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 204));
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBounds(0, 516, 852, 78);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JButton buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(769, 11, 73, 23);
		buttonRefresh.addActionListener(e -> buttonRefreshClick(e));
		panel_3.add(buttonRefresh);
		
		textFieldTotalWAmt = new JXTextField();
		textFieldTotalWAmt.setPrompt("Total W Amt");
		textFieldTotalWAmt.setName("txtPartyName");
		textFieldTotalWAmt.setFont(new Font("DialogInput", Font.PLAIN, 14));
		textFieldTotalWAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotalWAmt.setBounds(292, 40, 105, 21);
		panel_3.add(textFieldTotalWAmt);
		
		JXLabel lblWAmount = new JXLabel();
		lblWAmount.setText("W Amount");
		lblWAmount.setForeground(Color.BLACK);
		lblWAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblWAmount.setBounds(292, 13, 66, 16);
		panel_3.add(lblWAmount);
		
		JXLabel lblBAmount = new JXLabel();
		lblBAmount.setText("B Amount");
		lblBAmount.setForeground(Color.BLACK);
		lblBAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblBAmount.setBounds(174, 13, 66, 16);
		panel_3.add(lblBAmount);
		
		textFieldTotalBAmt = new JXTextField();
		textFieldTotalBAmt.setPrompt("Total B Amt");
		textFieldTotalBAmt.setName("txtPartyName");
		textFieldTotalBAmt.setFont(new Font("DialogInput", Font.PLAIN, 14));
		textFieldTotalBAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotalBAmt.setBounds(167, 40, 105, 21);
		panel_3.add(textFieldTotalBAmt);
		
		JXLabel lblTotal = new JXLabel();
		lblTotal.setText("Total");
		lblTotal.setForeground(Color.BLACK);
		lblTotal.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTotal.setBounds(456, 13, 66, 16);
		panel_3.add(lblTotal);
		
		textFieldTotalAmt = new JXTextField();
		textFieldTotalAmt.setPrompt("Total");
		textFieldTotalAmt.setName("txtPartyName");
		textFieldTotalAmt.setFont(new Font("DialogInput", Font.PLAIN, 14));
		textFieldTotalAmt.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotalAmt.setBounds(449, 40, 105, 21);
		panel_3.add(textFieldTotalAmt);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setBounds(677, 11, 73, 23);
		panel_3.add(btnPrint);
		
		setBounds(230, 30, 858, 620);
	}
	
	private void search(){
		
		Date dateFrom = datePickerFrom.getDate();
		Date dateTo = datePickerTo.getDate();
		
		boolean isB = chckbxB.isSelected();
		boolean isW = chckbxW.isSelected();
		boolean isBoth = chckbxBoth.isSelected();
		
		String city = cityEditor.getText();

		List<InvoiceInfo> invoiceInfos = new ArrayList<InvoiceInfo>();
		
		if(Strings.isNullOrEmpty(textFieldPartyName.getText()) 
				&& dateFrom == null && dateTo == null 
				&& !isB && !isW && !isBoth 
				&& LeamonERPConstants.CITY_PROMPT_MSG.equals(city) ){
			setModel(invoiceInfos);
			JOptionPane.showMessageDialog(this, "Oops! No criteria selected", "Leamon-ERP Warning", JOptionPane.WARNING_MESSAGE);
			return ;
		}
		
		if(Strings.isNullOrEmpty(textFieldPartyName.getText())){
			accountInfo = null;
		}
		
		boolean isTrue = true;
		
		/*date range parameter*/
		if(dateTo != null && dateFrom !=null ){
			try{
				invoiceInfos  = InvoiceDaoImpl.getInstance().getAllWithChildAndAccountByDateRange(new java.sql.Date(dateFrom.getTime()), new java.sql.Date(dateTo.getTime()));
				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}catch(Exception exp){
				JOptionPane.showMessageDialog(this, exp.getMessage(), "Leamon-ERP : Error", JOptionPane.ERROR_MESSAGE);
				isTrue = false;
				LOGGER.error(exp);
			}
			
		}else if(dateFrom  != null){ /* Only date from is selected*/
			SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy");
			String dateStr =  sdf.format(dateFrom);
			invoiceInfos =  InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
			invoiceInfos= invoiceInfos.stream().filter(e -> dateStr.equals(e.getInvoicDate())).collect(Collectors.toList());
			if(invoiceInfos.isEmpty()){
				isTrue = false;
			}
		}
		
		/* ***********************************************************
		 * By account info and city both, 
		 * only By Account info, 
		 * only City serach criteria handling 
		 * ***********************************************************/
		if(accountInfo != null && !LeamonERPConstants.CITY_PROMPT_MSG.equals(city)){
			if(!invoiceInfos .isEmpty()){
				if(!Strings.isNullOrEmpty(accountInfo.getCity()) && accountInfo.getCity().equals(city)){
					invoiceInfos= invoiceInfos.stream().filter(e->e.getPartyinfoID().equals(accountInfo.getId())).collect(Collectors.toList());
					if(invoiceInfos.isEmpty()){
						isTrue = false;
					}
				}else{
					/*Accountinfo and city criteria fail*/
					isTrue = false;
				}
			}else{ /*date filter is not selected so get all invoice for same account*/
				if(!Strings.isNullOrEmpty(accountInfo.getCity()) && accountInfo.getCity().equals(city)){
					invoiceInfos = getAllInvoiceByAccountInfo(accountInfo);
					if(invoiceInfos.isEmpty()){
						isTrue = false;
					}
				}else{
					/*Accountinfo and city criteria fail*/
					isTrue = false;
				}
			}
		}else if( !LeamonERPConstants.CITY_PROMPT_MSG.equals(city)){ /*filter only on city basis */
			if(!invoiceInfos .isEmpty()){
				invoiceInfos= invoiceInfos.stream().filter(e-> e.getAccountInfo()!=null && city.equals(e.getAccountInfo().getCity()) )
						.collect(Collectors.toList());
				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}else{/*date filter is not selected so get all invoice for same account*/
				invoiceInfos = InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
				invoiceInfos= invoiceInfos.stream().filter(e-> e.getAccountInfo()!=null && city.equals(e.getAccountInfo().getCity()) )
						.collect(Collectors.toList());
				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}
		}else if(accountInfo != null && LeamonERPConstants.CITY_PROMPT_MSG.equals(city)){
			if(!invoiceInfos .isEmpty()){
				invoiceInfos= invoiceInfos.stream().filter(e->e.getPartyinfoID().equals(accountInfo.getId())).collect(Collectors.toList());
				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}else{ /*date filter is not selected so get all invoice for same account*/
				invoiceInfos = getAllInvoiceByAccountInfo(accountInfo);
				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}
		}
		
		
		/*Association filter for date range, Account info, City B, W, Both*/
		if(!invoiceInfos.isEmpty() ){
			if(isTrue){
				if(isB){
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus()))
								.collect(Collectors.toList());
					}
					setModel(invoiceInfos);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_AMOUNT).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_STATUS).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_G_TOTAL).setVisible(false);
				}else if(isW){
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()))
								.collect(Collectors.toList());
					}
					setModel(invoiceInfos);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_AMOUNT).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_STATUS).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_G_TOTAL).setVisible(false);
				}else if(isBoth){
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()) 
											&& comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus())
										)
								.collect(Collectors.toList());
					}
					setModel(invoiceInfos);
				}else{
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()) 
											&& comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus())
										)
								.collect(Collectors.toList());
					}
					if(invoiceInfos.isEmpty()){
						setModel(invoiceInfos);
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					}else setModel(invoiceInfos);
				}
			}else{
				setModel(invoiceInfos);
				clearAmountField();
				JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
			}
		}else{
			if(isB){
				invoiceInfos =  InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
				if(!invoiceInfos.isEmpty()){
					if(isTrue){
						if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
							invoiceInfos = invoiceInfos.stream()
									.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus()))
									.collect(Collectors.toList());
						}
					setModel(invoiceInfos);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_AMOUNT).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_STATUS).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_G_TOTAL).setVisible(false);
					}else{
						setModel(invoiceInfos);
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					}
				}else{
					setModel(invoiceInfos);
					clearAmountField();
					JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
				}
			}else if(isW){
				invoiceInfos =  InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
				if(!invoiceInfos.isEmpty()){
					if(isTrue){
						if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
							invoiceInfos = invoiceInfos.stream()
									.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()))
									.collect(Collectors.toList());
						}
					setModel(invoiceInfos);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_AMOUNT).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_STATUS).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_G_TOTAL).setVisible(false);
					}else{
						setModel(invoiceInfos);
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					}
				}else{
					setModel(invoiceInfos);
					clearAmountField();
					JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
				}
			}else if(isBoth){ /*show all invoice*/
				
				if(!invoiceInfos.isEmpty()){
					if(isTrue){
						if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
							invoiceInfos = invoiceInfos.stream()
									.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()) 
												&& comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus())
											)
									.collect(Collectors.toList());
						}
						setModel(invoiceInfos);
					}else{
						setModel(invoiceInfos);
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					}
				}else{
					invoiceInfos =  InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()) 
											&& comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus())
										)
								.collect(Collectors.toList());
					}
					if(invoiceInfos.isEmpty()){
						setModel(invoiceInfos);
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					}else setModel(invoiceInfos);
					//JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
				}
			}else{
				setModel(invoiceInfos);
				clearAmountField();
				JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		/*set total amount in text fields*/
		if(!invoiceInfos.isEmpty() && isTrue){
			
			double sumBillAmount = invoiceInfos.stream()
					.filter(item -> !Strings.isNullOrEmpty(item.getBillAmount()))
					.map(item -> item.getBillAmount()).mapToDouble(Double::parseDouble).sum();
			
			double sumWAmount = invoiceInfos.stream()
					.filter(item -> !Strings.isNullOrEmpty(item.getWithoutBillAmount()))
					.map(item -> item.getWithoutBillAmount()).mapToDouble(Double::parseDouble).sum();
			
			double sum = sumBillAmount + sumWAmount;
			textFieldTotalWAmt.setText(String.valueOf(sumWAmount));
			textFieldTotalBAmt.setText(String.valueOf(sumBillAmount));
			textFieldTotalAmt.setText(String.valueOf(sum));
		}
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
		leamonAutoAccountIDTextFieldSuggestor = new LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo>(textField, this); 
		leamonAutoAccountIDTextFieldSuggestor.setItems(accountInfos);
		LOGGER.info(CLASS_NAME+"["+methodName+"] end");
	}
	public void setAccountInfo(AccountInfo info){
		accountInfo = info;
		textFieldPartyName.setText(info.getName());
		
	}
	
	public List<InvoiceInfo> getAllInvoiceByAccountInfo(AccountInfo info){
		List<InvoiceInfo> invoiceInfos = new ArrayList<InvoiceInfo>();
		try {
				invoiceInfos = InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
			if(info == null){
				return invoiceInfos;
			}
			invoiceInfos= invoiceInfos.stream().filter(e->e.getPartyinfoID().equals(info.getId())).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error(e);
		}
		
		return invoiceInfos;
	}//end
	
	public List<AccountInfo> getAccountInfoByCity(String city){
		List<AccountInfo> accountInfos = new ArrayList<AccountInfo>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
			if(accountInfos == null){
				return accountInfos;
			}
			accountInfos= accountInfos.stream().filter(e->e.getCity().equals(city)).collect(Collectors.toList());
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		}
		
		return accountInfos;
	}//end
	
	private void refresh(){
		textFieldPartyName.setText(LeamonERPConstants.EMPTY_STR);
		datePickerFrom.getEditor().setText(LeamonERPConstants.EMPTY_STR);
		datePickerTo.getEditor().setText(LeamonERPConstants.EMPTY_STR);
		LeamonUtil.prepareAutoCompleterCombo(comboBoxCity, LeamonERP.cityCache);
		bg.clearSelection();
		textFieldTotalWAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldTotalBAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldTotalAmt.setText(LeamonERPConstants.EMPTY_STR);
		
		comboBoxSatus.setSelectedIndex(0);
		setModel(new ArrayList<InvoiceInfo>());
	}
	
	private void clearAmountField(){
		textFieldTotalWAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldTotalBAmt.setText(LeamonERPConstants.EMPTY_STR);
		textFieldTotalAmt.setText(LeamonERPConstants.EMPTY_STR);
	}
	private void setModel(List<InvoiceInfo> invoiceInfos){
		List<Integer> snos = IntStream.range(1, 1+invoiceInfos.size()).boxed().collect(Collectors.toList());
		GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo> invoicePaymentSummaryModel = 
				new GenericModelWithSnp<List<InvoiceInfo>, InvoiceInfo>(invoiceInfos, snos);
		TablePaymentReceivedSummaryModel tablePaymentReceivedSummaryModel = 
				new TablePaymentReceivedSummaryModel(invoicePaymentSummaryModel);
		table.setModel(tablePaymentReceivedSummaryModel);
		table.packAll();
	}
	private void btnSearchClick(ActionEvent e){
		search();
	}
	private void buttonRefreshClick(ActionEvent e){
		refresh();
		
	}
}
