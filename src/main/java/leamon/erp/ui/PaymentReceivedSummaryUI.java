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
import java.text.ParseException;
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
import leamon.erp.db.OpeningBalanceDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.InvoiceInfo;
import leamon.erp.model.OpeningBalanceInfo;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import leamon.erp.util.ERPEnum;
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
		panel_1.setBounds(0, 0, 975, 85);
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
		panel_2.setBounds(0, 80, 975, 436);
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
		panel_3.setBounds(0, 516, 975, 78);
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

		setBounds(230, 30, 980, 620);
	}

	private void search(){

		Date dateFrom = datePickerFrom.getDate();
		Date dateTo = datePickerTo.getDate();

		boolean isB = chckbxB.isSelected();
		boolean isW = chckbxW.isSelected();
		boolean isBoth = chckbxBoth.isSelected();

		String city = cityEditor.getText();

		//boolean isDateCriteriaApplied = false;

		boolean isPartyNameCriteriaSelected = false;
		boolean isCityCriteriaSelected = false;
		boolean isStatusCriteriaSelected = false;
		boolean isDateFromCriteriaSelected = false;
		boolean isDateRangeCriteriaSelected = false;


		List<InvoiceInfo> invoiceInfos = new ArrayList<InvoiceInfo>();

		if(Strings.isNullOrEmpty(textFieldPartyName.getText()) 
				&& dateFrom == null && dateTo == null 
				&& !isB && !isW && !isBoth 
				&& LeamonERPConstants.CITY_PROMPT_MSG.equals(city) 
				&& "SELECT_STATUS".equals(comboBoxSatus.getSelectedItem().toString())){
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
			isDateRangeCriteriaSelected = true;
			try{
				invoiceInfos  = InvoiceDaoImpl.getInstance().getAllWithChildAndAccountByDateRange(new java.sql.Date(dateFrom.getTime()), new java.sql.Date(dateTo.getTime()));

				/*opening balance : 3.3.2*/
				List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosBetweenDateFromAndTo();
				for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
					invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
				}
				/*End*/

				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}catch(Exception exp){
				JOptionPane.showMessageDialog(this, exp.getMessage(), "Leamon-ERP : Error", JOptionPane.ERROR_MESSAGE);
				isTrue = false;
				LOGGER.error(exp);
				setModel(new ArrayList<InvoiceInfo>());
				return ;
			}

		}else if(dateFrom  != null){ /* Only date from is selected*/
			isDateFromCriteriaSelected = true;
			SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy");
			String dateStr =  sdf.format(dateFrom);
			invoiceInfos =  InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
			invoiceInfos= invoiceInfos.stream().filter(e -> dateStr.equals(e.getInvoicDate())).collect(Collectors.toList());

			/*Opening Balance : 3.3.2*/
			List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByDateFrom();
			for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
				invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
			}
			/*-End-*/

			if(invoiceInfos.isEmpty()){
				isTrue = false;
			}
		}

		/* ***********************************************************
		 * By account info and city both, 
		 * only By Account info, 
		 * only City serach criteria handling 
		 * ***********************************************************/
		if(isDateFromCriteriaSelected && invoiceInfos.isEmpty()){
			JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
			setModel(new ArrayList<InvoiceInfo>());
			return ;
		}
		
		if(isDateRangeCriteriaSelected && invoiceInfos.isEmpty()){
			JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
			setModel(new ArrayList<InvoiceInfo>());
			return ;
		}
		if(accountInfo != null && !LeamonERPConstants.CITY_PROMPT_MSG.equals(city)){
			isPartyNameCriteriaSelected = true;
			isCityCriteriaSelected = true;
			if(!invoiceInfos .isEmpty()){
				if(!Strings.isNullOrEmpty(accountInfo.getCity()) && accountInfo.getCity().equals(city)){
					invoiceInfos= invoiceInfos.stream().filter(e->e.getPartyinfoID().equals(accountInfo.getId())).collect(Collectors.toList());

					/*Opening Balance : 3.3.2*/
					if(invoiceInfos.stream().filter(e -> e.isOpeningBalance()).findAny().isPresent()){
						List<InvoiceInfo> filteredOpeningBals = invoiceInfos.stream().filter(e -> e.getOpenigBalanceInfo().getPartyinfoid().equals(accountInfo.getId())).collect(Collectors.toList());
						invoiceInfos = invoiceInfos.stream().filter(e-> !e.isOpeningBalance()).collect(Collectors.toList());
						invoiceInfos.addAll(0, filteredOpeningBals);
					}else{
						List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByAccountInfoAndCity();
						for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
							invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
						}
					}
					/*-End--*/

					if(invoiceInfos.isEmpty()){
						isTrue = false;
					}
				}else{
					/*Accountinfo and city criteria fail*/
					isTrue = false;
				}
			}else{ /*date filter is not selected so get all invoice for same account with city*/
				if(!Strings.isNullOrEmpty(accountInfo.getCity()) && accountInfo.getCity().equals(city)){
					invoiceInfos = getAllInvoiceByAccountInfo(accountInfo);

					/*Opening Balance : 3.3.2*/
					List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByAccountInfoAndCity();
					for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
						invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
					}
					/*-End--*/
					if(invoiceInfos.isEmpty()){
						isTrue = false;
					}
				}else{
					/*Accountinfo and city criteria fail*/
					isTrue = false;
				}
			}
		}else if( !LeamonERPConstants.CITY_PROMPT_MSG.equals(city)){ /*filter only on city basis */
			isCityCriteriaSelected = true;
			if(!invoiceInfos .isEmpty()){
				invoiceInfos= invoiceInfos.stream().filter(e-> e.getAccountInfo()!=null && city.equals(e.getAccountInfo().getCity()) )
						.collect(Collectors.toList());

				/*Opening Balance : 3.3.2*/
				if(invoiceInfos.stream().filter(e -> e.isOpeningBalance()).findAny().isPresent()){
					List<InvoiceInfo> filteredOpeningBals = invoiceInfos
							.stream()
							.filter(e ->  city.equals(e.getOpenigBalanceInfo().getAccountInfo().getCity()))
							.collect(Collectors.toList());
					invoiceInfos = invoiceInfos.stream().filter(e-> !e.isOpeningBalance()).collect(Collectors.toList());
					invoiceInfos.addAll(0, filteredOpeningBals);
				}else{
					List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByCity();
					for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
						invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
					}
				}
				/*--End--*/
				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}else{/*date filter is not selected so get all invoice for same account*/
				invoiceInfos = InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
				invoiceInfos= invoiceInfos.stream().filter(e-> e.getAccountInfo()!=null && city.equals(e.getAccountInfo().getCity()) )
						.collect(Collectors.toList());
				/*Opening Balance : 3.3.2*/
				List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByCity();
				for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
					invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
				}
				/*--End--*/
				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}
		}else if(accountInfo != null && LeamonERPConstants.CITY_PROMPT_MSG.equals(city)){
			isCityCriteriaSelected= false;
			if(!invoiceInfos .isEmpty()){

				invoiceInfos= invoiceInfos.stream().filter(e->accountInfo.getId().equals(e.getPartyinfoID())).collect(Collectors.toList());

				/*Opening Balance : 3.3.2*/
				if(invoiceInfos.stream().filter(e -> e.isOpeningBalance()).findAny().isPresent()){
					List<InvoiceInfo> filteredOpeningBals = invoiceInfos
							.stream()
							.filter(e ->  e.isOpeningBalance() && e.getPartyinfoID().equals(accountInfo.getId()))
							.collect(Collectors.toList());
					invoiceInfos = invoiceInfos.stream().filter(e-> !e.isOpeningBalance()).collect(Collectors.toList());
					invoiceInfos.addAll(0, filteredOpeningBals);
				}else{
					List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByAccountInfoAndCity();
					for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
						invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
					}

				}

				/*-End--*/

				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}else{ /*date filter is not selected so get all invoice for same account*/
				invoiceInfos = getAllInvoiceByAccountInfo(accountInfo);

				/*Opening Balance : 3.3.2*/
				List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByAccountInfoAndCity();
				for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
					invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
				}
				/*-End--*/

				if(invoiceInfos.isEmpty()){
					isTrue = false;
				}
			}
		}

		boolean isSetModel = false;
		/*Association filter for date range, Account info, City B, W, Both*/
		if(!invoiceInfos.isEmpty() ){
			if(isTrue){
				if(isB){
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus()))
								.collect(Collectors.toList());
					}else{
						if(invoiceInfos.stream().filter(e -> e.isOpeningBalance()).findAny().isPresent()){
							List<InvoiceInfo> filteredOpeningBals =  invoiceInfos.stream().filter(e-> 
							e.isOpeningBalance() && e.getOpenigBalanceInfo().getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())
									).collect(Collectors.toList());

							invoiceInfos =  invoiceInfos.stream().filter(e-> !e.isOpeningBalance()).collect(Collectors.toList());

							for(int i=0;i<filteredOpeningBals.size(); i++){
								invoiceInfos.add(i,filteredOpeningBals.get(i));
							}
						}
					}
					removeDuplicate(invoiceInfos);
					setModel(invoiceInfos);
					isSetModel = false;
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_AMOUNT).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_STATUS).setVisible(false);
					table.getColumnExt(LeamonERPConstants.TABLE_HEADER_G_TOTAL).setVisible(false);
				}else if(isW){
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()))
								.collect(Collectors.toList());
					}else{
						if(invoiceInfos.stream().filter(e -> e.isOpeningBalance()).findAny().isPresent()){
							List<InvoiceInfo> filteredOpeningBals =  invoiceInfos.stream().filter(e-> 
							e.isOpeningBalance() && e.getOpenigBalanceInfo().getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())
									).collect(Collectors.toList());

							invoiceInfos =  invoiceInfos.stream().filter(e-> !e.isOpeningBalance()).collect(Collectors.toList());

							for(int i=0;i<filteredOpeningBals.size(); i++){
								invoiceInfos.add(i,filteredOpeningBals.get(i));
							}
						}

					}
					removeDuplicate(invoiceInfos);
					setModel(invoiceInfos);
					isSetModel = false;
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
					/*setModel(invoiceInfos);*/
					isSetModel = true;
				}else{
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()) 
										&& comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus())
										)
								.collect(Collectors.toList());
					}
					if(invoiceInfos.isEmpty()){
						/*setModel(invoiceInfos);*/
						isSetModel = true;
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
						setModel(new ArrayList<InvoiceInfo>());
						return ;
					}else /*setModel(invoiceInfos);*/ isSetModel = true;
				}
			}else{
				/*setModel(invoiceInfos);*/ isSetModel = true;
				clearAmountField();
				JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
				setModel(new ArrayList<InvoiceInfo>());
				return ;
			}
		}else{
			if(isB){
				if( (isCityCriteriaSelected ||isPartyNameCriteriaSelected || isDateFromCriteriaSelected || isDateRangeCriteriaSelected) && invoiceInfos.isEmpty() ){
					JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					setModel(new ArrayList<InvoiceInfo>());
					return ;
				}
				invoiceInfos =  InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
				if(!invoiceInfos.isEmpty()){
					if(isTrue){
						if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
							invoiceInfos = invoiceInfos.stream()
									.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus()))
									.collect(Collectors.toList());
							/*Opening Balance : 3.3.2*/
							List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByBandStatus();
							for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
								invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
							}
							/*End*/
						}
						/*Opening Balance : 3.3.2*/
						List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByB();
						for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
							invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
						}
						/*End*/	
						removeDuplicate(invoiceInfos);
						setModel(invoiceInfos); isSetModel = false;
						table.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_AMOUNT).setVisible(false);
						table.getColumnExt(LeamonERPConstants.TABLE_HEADER_W_STATUS).setVisible(false);
						table.getColumnExt(LeamonERPConstants.TABLE_HEADER_G_TOTAL).setVisible(false);
					}else{
						/*setModel(invoiceInfos);*/ isSetModel = true;
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
						setModel(new ArrayList<InvoiceInfo>());
						return ;
					}
				}else{
					/*setModel(invoiceInfos);*/ isSetModel = true;
					clearAmountField();
					JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					setModel(new ArrayList<InvoiceInfo>());
					return ;
				}
			}else if(isW){
				if( (isCityCriteriaSelected ||isPartyNameCriteriaSelected || isDateFromCriteriaSelected || isDateRangeCriteriaSelected) && invoiceInfos.isEmpty() ){
					JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					setModel(new ArrayList<InvoiceInfo>());
					return ;
				}
				invoiceInfos =  InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
				if(!invoiceInfos.isEmpty()){
					if(isTrue){
						if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
							invoiceInfos = invoiceInfos.stream()
									.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()))
									.collect(Collectors.toList());
							/*Opening Balance : 3.3.2*/
							List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByWandStatus();
							for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
								invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
							}
							/*End*/
						}
						/*Opening Balance : 3.3.2*/
						List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByW();
						for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
							invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
						}
						/*End*/
						removeDuplicate(invoiceInfos);
						setModel(invoiceInfos); isSetModel = false;
						table.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_AMOUNT).setVisible(false);
						table.getColumnExt(LeamonERPConstants.TABLE_HEADER_B_STATUS).setVisible(false);
						table.getColumnExt(LeamonERPConstants.TABLE_HEADER_G_TOTAL).setVisible(false);
					}else{
						/*setModel(invoiceInfos);*/ isSetModel = true;
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
						return;
					}
				}else{
					/*setModel(invoiceInfos);*/ isSetModel = true;
					clearAmountField();
					JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					return;
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
						/*setModel(invoiceInfos);*/ isSetModel = true;
					}else{
						/*setModel(invoiceInfos);*/ isSetModel = true;
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
						return;
					}
				}else{
					if( (isCityCriteriaSelected ||isPartyNameCriteriaSelected || isDateFromCriteriaSelected || isDateRangeCriteriaSelected) && invoiceInfos.isEmpty() ){
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
						setModel(new ArrayList<InvoiceInfo>());
						return ;
					}
					invoiceInfos =  InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
					if(!InvoicePaymentStatusEnum.SELECT_STATUS.name().equals(comboBoxSatus.getSelectedItem().toString())){
						invoiceInfos = invoiceInfos.stream()
								.filter(e -> comboBoxSatus.getSelectedItem().toString().equals(e.getWpaidstatus()) 
										&& comboBoxSatus.getSelectedItem().toString().equals(e.getPaidStatus())
										)
								.collect(Collectors.toList());
						//TODO : 3.3.2
						/*Opening Balance : 3.3.2*/
						List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByWandStatus();
						for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
							invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
						}
						invoiceInfosOpeningBal = openingBalanceInfosByBandStatus();
						for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
							invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
						}
						/*End*/
					}else{
						/*Opening Balance : 3.3.2*/
						List<InvoiceInfo> invoiceInfosOpeningBal = openingBalanceInfosByBandW();
						for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
							invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
						}
						invoiceInfosOpeningBal = openingBalanceInfosByBandStatus();
						for(int i=0; i< invoiceInfosOpeningBal.size(); i++){
							invoiceInfos.add(i,invoiceInfosOpeningBal.get(i));
						}
						/*End*/
					}

					if(invoiceInfos.isEmpty()){
						/*setModel(invoiceInfos);*/ isSetModel = true;
						clearAmountField();
						JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
						setModel(new ArrayList<InvoiceInfo>());
						return ;
					}else /*setModel(invoiceInfos);*/ isSetModel = true;
					//JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
				}
			}else{
				/*setModel(invoiceInfos);*/ isSetModel = true;
				/*None of the creteria is selected except status(Nothig paid, partial paid, clear)*/
				/*if(!"SELECT_STATUS".equals(comboBoxSatus.getSelectedItem().toString()) 
						&&  (!isPartyNameCriteriaSelected && !isDateFromCriteriaSelected && !isDateRangeCriteriaSelected 
								&& !isCityCriteriaSelected && !isB && !isW && !isBoth)) {
					invoiceInfos  = InvoiceDaoImpl.getInstance().getItemListWithInvoiceItemListAndAccountInfo();
					invoiceInfos.stream().filter(e-> InvoicePaymentStatusEnum.NOTHING_PAID.name().equals(e.getPaidStatus()) )
				}else{*/
					clearAmountField();
					JOptionPane.showMessageDialog(this, "Sorry! No record found ", "Leamon-ERP Message", JOptionPane.PLAIN_MESSAGE);
					setModel(new ArrayList<InvoiceInfo>());
					return ;
				//}
			}
		}

		/*Release : 3.3.2*/
		if(isSetModel){
			removeDuplicate(invoiceInfos);
			setModel(invoiceInfos);
			boolean isOpeningBalPresent = invoiceInfos.stream().filter(e-> e.isOpeningBalance()).findAny().isPresent();
			if(isOpeningBalPresent){
				table.getColumnExt(LeamonERPConstants.TABLE_HEADER_DESC).setVisible(true);
			}else{
				table.getColumnExt(LeamonERPConstants.TABLE_HEADER_DESC).setVisible(false);
			}
		}

		/*set total amount in text fields*/
		if(!invoiceInfos.isEmpty() && isTrue){
			
			//|| item.isOpeningBalance() && item.getOpenigBalanceInfo().getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())
			double sumBillAmount = invoiceInfos.stream()
					.filter(item -> !Strings.isNullOrEmpty(item.getBillAmount()))
					.map(item -> item.getBillAmount()).mapToDouble(Double::parseDouble).sum();
			
			sumBillAmount = sumBillAmount + invoiceInfos.stream()
					.filter(item -> item.isOpeningBalance() && item.getOpenigBalanceInfo().getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name()))
					.map(item -> item.getOpenigBalanceInfo().getOpeningbalanceamount()).mapToDouble(Double::parseDouble).sum();
			
			double sumWAmount = invoiceInfos.stream()
					.filter(item -> !Strings.isNullOrEmpty(item.getWithoutBillAmount()))
					.map(item -> item.getWithoutBillAmount()).mapToDouble(Double::parseDouble).sum();

			sumWAmount = sumWAmount + invoiceInfos.stream()
					.filter(item -> item.isOpeningBalance() && item.getOpenigBalanceInfo().getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name()))
					.map(item -> item.getOpenigBalanceInfo().getOpeningbalanceamount()).mapToDouble(Double::parseDouble).sum();
			
			double sum = 0; //= sumBillAmount + sumWAmount;
			
			if(isB){
				textFieldTotalBAmt.setText(String.valueOf(sumBillAmount));
				textFieldTotalWAmt.setText(String.valueOf(0.0));
				sum = sumBillAmount;
				textFieldTotalAmt.setText(String.valueOf(sum));
			}
			
			if(isW){
				textFieldTotalBAmt.setText(String.valueOf(0.0));
				textFieldTotalWAmt.setText(String.valueOf(sumWAmount));
				sum = sumWAmount;
				textFieldTotalAmt.setText(String.valueOf(sum));
			}
			
			if(!isB && !isW){
				textFieldTotalBAmt.setText(String.valueOf(sumBillAmount));
				textFieldTotalWAmt.setText(String.valueOf(sumWAmount));
				sum = sumBillAmount + sumWAmount;
				textFieldTotalAmt.setText(String.valueOf(sum));
			}
			
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


	/*opening balance by only dateFrom Handling*/
	private List<InvoiceInfo> openingBalanceInfosByDateFrom(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy");
		Date dateFrom = datePickerFrom.getDate();
		String dateStr =  sdf.format(dateFrom);
		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();
			openingBalanceInfos = openingBalanceInfos.stream().filter(e -> dateStr.equals(e.getBilldate())).collect(Collectors.toList());
			for(OpeningBalanceInfo info : openingBalanceInfos ){
				InvoiceInfo info2 = InvoiceInfo.builder()
						.isOpeningBalance(Boolean.TRUE)
						.openigBalanceInfo(info)
						.partyinfoID(info.getAccountInfo().getId())
						.build();


				boolean isB = chckbxB.isSelected();
				boolean isW = chckbxW.isSelected();
				//boolean isBoth = chckbxBoth.isSelected();
				if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					info2.setPaidStatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
				if(!isB && !isW) {
					info2.setPaidStatus(info.getStatus());
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}

			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}

	/**
	 * opening balance by between dateFrom & dateTo Handling
	 * @return
	 */
	private List<InvoiceInfo> openingBalanceInfosBetweenDateFromAndTo(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy");
		Date dateFrom = datePickerFrom.getDate();
		Date dateTo = datePickerTo.getDate();

		String dateFromStr =  sdf.format(dateFrom);
		String dateToStr =  sdf.format(dateTo);


		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();

			openingBalanceInfos = openingBalanceInfos.stream()
					.filter(e -> {
						try {
							return sdf.parse(e.getBilldate()).getTime() >= sdf.parse(dateFromStr).getTime()
									&& 
									sdf.parse(e.getBilldate()).getTime() <= sdf.parse(dateToStr).getTime() ;
						} catch (ParseException e1) {
							LOGGER.error(e1);
							return false;
						}
					}).collect(Collectors.toList());
			for(OpeningBalanceInfo info : openingBalanceInfos ){
				InvoiceInfo info2 = InvoiceInfo.builder()
						.isOpeningBalance(Boolean.TRUE)
						.openigBalanceInfo(info)
						.partyinfoID(info.getAccountInfo().getId())
						.build();


				boolean isB = chckbxB.isSelected();
				boolean isW = chckbxW.isSelected();
				//boolean isBoth = chckbxBoth.isSelected();
				if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					info2.setPaidStatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
				if(!isB && !isW) {
					info2.setPaidStatus(info.getStatus());
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}

			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}

	/**
	 * Opening Balance by accountinfo & city
	 * @return
	 */
	private List<InvoiceInfo> openingBalanceInfosByAccountInfoAndCity(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();
			openingBalanceInfos= openingBalanceInfos.stream().filter(e->e.getPartyinfoid().equals(accountInfo.getId())).collect(Collectors.toList());
			for(OpeningBalanceInfo info : openingBalanceInfos ){
				InvoiceInfo info2 = InvoiceInfo.builder()
						.isOpeningBalance(Boolean.TRUE)
						.openigBalanceInfo(info)
						.partyinfoID(info.getAccountInfo().getId())
						.build();


				boolean isB = chckbxB.isSelected();
				boolean isW = chckbxW.isSelected();
				//boolean isBoth = chckbxBoth.isSelected();
				if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					info2.setPaidStatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
				if(!isB && !isW) {
					info2.setPaidStatus(info.getStatus());
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}

			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}

	/**
	 * Opening Balance by  city
	 * @return
	 */
	private List<InvoiceInfo> openingBalanceInfosByCity(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		String city = cityEditor.getText();
		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();
			openingBalanceInfos= openingBalanceInfos.stream().filter(e->city.equals(e.getAccountInfo().getCity())).collect(Collectors.toList());
			for(OpeningBalanceInfo info : openingBalanceInfos ){
				InvoiceInfo info2 = InvoiceInfo.builder()
						.isOpeningBalance(Boolean.TRUE)
						.openigBalanceInfo(info)
						.partyinfoID(info.getAccountInfo().getId())
						.build();


				boolean isB = chckbxB.isSelected();
				boolean isW = chckbxW.isSelected();
				//boolean isBoth = chckbxBoth.isSelected();
				if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					info2.setPaidStatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
				if(!isB && !isW) {
					info2.setPaidStatus(info.getStatus());
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}

	private List<InvoiceInfo> openingBalanceInfosByW(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();
			openingBalanceInfos= openingBalanceInfos.stream().filter(e->e.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())).collect(Collectors.toList());
			for(OpeningBalanceInfo info : openingBalanceInfos ){
				InvoiceInfo info2 = InvoiceInfo.builder()
						.isOpeningBalance(Boolean.TRUE)
						.openigBalanceInfo(info)
						.partyinfoID(info.getAccountInfo().getId())
						.build();


				boolean isB = chckbxB.isSelected();
				boolean isW = chckbxW.isSelected();
				//boolean isBoth = chckbxBoth.isSelected();
				if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					info2.setPaidStatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
				if(!isB && !isW) {
					info2.setPaidStatus(info.getStatus());
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}

	private List<InvoiceInfo> openingBalanceInfosByB(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();
			openingBalanceInfos= openingBalanceInfos.stream().filter(e->e.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())).collect(Collectors.toList());
			for(OpeningBalanceInfo info : openingBalanceInfos ){
				InvoiceInfo info2 = InvoiceInfo.builder()
						.isOpeningBalance(Boolean.TRUE)
						.openigBalanceInfo(info)
						.partyinfoID(info.getAccountInfo().getId())
						.build();


				boolean isB = chckbxB.isSelected();
				boolean isW = chckbxW.isSelected();
				//boolean isBoth = chckbxBoth.isSelected();
				if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					info2.setPaidStatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
				if(!isB && !isW) {
					info2.setPaidStatus(info.getStatus());
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}

	private List<InvoiceInfo> openingBalanceInfosByBandW(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();
			for(OpeningBalanceInfo info : openingBalanceInfos ){
				InvoiceInfo info2 = InvoiceInfo.builder()
						.isOpeningBalance(Boolean.TRUE)
						.openigBalanceInfo(info)
						.partyinfoID(info.getAccountInfo().getId())
						.build();


				boolean isB = chckbxB.isSelected();
				boolean isW = chckbxW.isSelected();
				//boolean isBoth = chckbxBoth.isSelected();
				if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					info2.setPaidStatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
				if(!isB && !isW) {
					info2.setPaidStatus(info.getStatus());
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}

	private List<InvoiceInfo> openingBalanceInfosByBandStatus(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();
			openingBalanceInfos= openingBalanceInfos.stream().filter(e->
			e.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())
			&&  e.getStatus().equals(comboBoxSatus.getSelectedItem().toString()) )
					.collect(Collectors.toList());

			for(OpeningBalanceInfo info : openingBalanceInfos ){InvoiceInfo info2 = InvoiceInfo.builder()
					.isOpeningBalance(Boolean.TRUE)
					.openigBalanceInfo(info)
					.partyinfoID(info.getAccountInfo().getId())
					.build();


			boolean isB = chckbxB.isSelected();
			boolean isW = chckbxW.isSelected();
			//boolean isBoth = chckbxBoth.isSelected();
			if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
				info2.setPaidStatus(info.getStatus());
				invoiceInfosOpeningBal.add(info2);
			}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
				info2.setWpaidstatus(info.getStatus());
				invoiceInfosOpeningBal.add(info2);
			}
			if(!isB && !isW) {
				info2.setPaidStatus(info.getStatus());
				info2.setWpaidstatus(info.getStatus());
				invoiceInfosOpeningBal.add(info2);
			}}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}

	private List<InvoiceInfo> openingBalanceInfosByWandStatus(){
		List<InvoiceInfo> invoiceInfosOpeningBal = new ArrayList<InvoiceInfo>();
		List<OpeningBalanceInfo> openingBalanceInfos = new ArrayList<OpeningBalanceInfo>();
		try{
			openingBalanceInfos = OpeningBalanceDaoImpl.getInstance().getItemList();
			openingBalanceInfos= openingBalanceInfos.stream().filter(e->
			e.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())
			&&  e.getStatus().equals(comboBoxSatus.getSelectedItem().toString())
					)
					.collect(Collectors.toList());
			for(OpeningBalanceInfo info : openingBalanceInfos ){
				InvoiceInfo info2 = InvoiceInfo.builder()
						.isOpeningBalance(Boolean.TRUE)
						.openigBalanceInfo(info)
						.partyinfoID(info.getAccountInfo().getId())
						.build();


				boolean isB = chckbxB.isSelected();
				boolean isW = chckbxW.isSelected();
				//boolean isBoth = chckbxBoth.isSelected();
				if(isB && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITH_BILL.name())){
					info2.setPaidStatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}else if(isW && info.getType().equals(ERPEnum.TYPE_PAYMENT_WITHOUT_BILL.name())){
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
				if(!isB && !isW) {
					info2.setPaidStatus(info.getStatus());
					info2.setWpaidstatus(info.getStatus());
					invoiceInfosOpeningBal.add(info2);
				}
			}
		}catch(Exception exp){
			LOGGER.error(exp);
		}
		return invoiceInfosOpeningBal;
	}
	
	public static <T> Predicate<T> distinceByKey(Function<? super T, Object> keyExtractor){
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t-> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	private void removeDuplicate(List<InvoiceInfo> invoiceInfos){
		List<InvoiceInfo> invoiceInfosOpeningBal = invoiceInfos.stream().filter(e -> e.isOpeningBalance()).collect(Collectors.toList());
		List<InvoiceInfo> invoiceInfosOnly = invoiceInfos.stream().filter(e -> !e.isOpeningBalance()).collect(Collectors.toList());
		
		invoiceInfosOpeningBal = invoiceInfosOpeningBal.stream().filter(distinceByKey(e->e.getOpenigBalanceInfo().getId())).collect(Collectors.toList());
		invoiceInfosOnly = invoiceInfosOnly.stream().filter(distinceByKey(e->e.getId())).collect(Collectors.toList());
		invoiceInfos.clear();
		invoiceInfos.addAll(invoiceInfosOnly);
		invoiceInfos.addAll(0,invoiceInfosOpeningBal);
	}
}
