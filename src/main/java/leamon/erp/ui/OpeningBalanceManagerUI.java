package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;

import org.jdesktop.swingx.JXDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdesktop.swingx.JXTextField;

import com.google.common.base.Strings;

import leamon.erp.component.helper.LeamonAutoAccountInfoTextFieldSuggestor;
import leamon.erp.db.AccountDaoImpl;
import leamon.erp.db.OpeningBalanceDaoImpl;
import leamon.erp.model.AccountInfo;
import leamon.erp.model.OpeningBalanceInfo;
import leamon.erp.model.OpeningBalanceManagerInfoModel;
import leamon.erp.ui.model.OpeningBalanceManagerModel;
import leamon.erp.util.LeamonERPConstants;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXTable;
import javax.swing.DefaultComboBoxModel;
import leamon.erp.util.PaymentEnum;

/**
 * @date Mar 11,2018
 * @author Manish Kumar Mishra
 *
 */
public class OpeningBalanceManagerUI extends JInternalFrame {

	static final Logger LOGGER = Logger.getLogger(OpeningBalanceManagerUI.class);
	private JXDatePicker datePickerStartDate;
	private JXDatePicker datePickerEndDate;
	private JXTextField textFieldPartyName;
	private JComboBox comboBox;
	private JXTable table;
	private LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo> leamonAutoAccountIDTextFieldSuggestor;

	public OpeningBalanceManagerUI() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Opening Balance Manager");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(3, 30, 922, 534);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(0, 0, 916, 43);
		getContentPane().add(panel);

		JLabel label = new JLabel("Start Date");
		label.setForeground((Color) null);
		label.setFont(new Font("DialogInput", Font.BOLD, 12));
		label.setBounds(3, 11, 70, 22);
		panel.add(label);

		JLabel label_1 = new JLabel("End Date");
		label_1.setForeground((Color) null);
		label_1.setFont(new Font("DialogInput", Font.BOLD, 12));
		label_1.setBounds(195, 11, 61, 22);
		panel.add(label_1);

		JLabel label_2 = new JLabel("Party");
		label_2.setForeground((Color) null);
		label_2.setFont(new Font("DialogInput", Font.BOLD, 12));
		label_2.setBounds(373, 11, 43, 22);
		panel.add(label_2);

		datePickerStartDate = new JXDatePicker((Date) null);
		datePickerStartDate.setBounds(75, 12, 116, 22);
		panel.add(datePickerStartDate);

		datePickerEndDate = new JXDatePicker((Date) null);
		datePickerEndDate.setBounds(253, 12, 116, 22);
		panel.add(datePickerEndDate);

		textFieldPartyName = new JXTextField();
		textFieldPartyName.setPrompt("Party Name");
		textFieldPartyName.setName("txtPartyName");
		textFieldPartyName.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldPartyName.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldPartyName.setBounds(410, 13, 302, 23);
		autoAccountInfoSuggestor(textFieldPartyName);
		panel.add(textFieldPartyName);

		JXButton button = new JXButton();
		button.setText("Search");
		button.setBounds(830, 12, 80, 24);
		panel.add(button);
		button.addActionListener(e -> buttonSearchClick(e));

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(PaymentEnum.values()));
		comboBox.setBounds(722, 12, 70, 22);
		panel.add(comboBox);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(0, 43, 916, 43);
		getContentPane().add(panel_1);

		JXButton buttonAddNew = new JXButton();
		buttonAddNew.setText("+");
		buttonAddNew.setBounds(10, 11, 56, 24);
		panel_1.add(buttonAddNew);

		JXButton buttonEdit = new JXButton();
		buttonEdit.setText("-");
		buttonEdit.setBounds(76, 11, 56, 24);
		panel_1.add(buttonEdit);

		JXButton btnDelete = new JXButton();
		btnDelete.setText("X");
		btnDelete.setBounds(142, 11, 56, 24);
		panel_1.add(btnDelete);

		JXButton btnRefresh = new JXButton();
		btnRefresh.setText("R");
		btnRefresh.setBounds(208, 11, 56, 24);
		panel_1.add(btnRefresh);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 86, 916, 422);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 916, 422);
		panel_2.add(scrollPane);

		table = new JXTable();
		scrollPane.setViewportView(table);
	}

	private void buttonSearchClick(ActionEvent e) {
		String startDate = datePickerStartDate.getEditor().getText();
		String endDate = datePickerEndDate.getEditor().getText();
		String partyName = textFieldPartyName.getText();
		String type = String.valueOf(comboBox.getSelectedItem());

		/* only by party name */
		if (!Strings.isNullOrEmpty(partyName) && Strings.isNullOrEmpty(startDate) && Strings.isNullOrEmpty(endDate)
				&& type.equalsIgnoreCase("N")) {
			searchByPartyName(partyName);
		}
	}

	private void searchByPartyName(String partyName) {
		try {
			List<AccountInfo> accountInfo = AccountDaoImpl.getInstance().getItemList();
			AccountInfo accountInfo1 = accountInfo.stream().filter(e1 -> e1.getName().equalsIgnoreCase(partyName))
					.findAny().orElse(null);
			int partyID = accountInfo1.getId();
			List<OpeningBalanceInfo> openingBalanceInfos = OpeningBalanceDaoImpl.getInstance()
					.getAllOpeningBalanceByPartyName(String.valueOf(partyID));

			List<OpeningBalanceManagerInfoModel> infoModel = new ArrayList<>();
			int sNo = 1;
			for (OpeningBalanceInfo balanceInfo : openingBalanceInfos) {
				OpeningBalanceManagerInfoModel model = new OpeningBalanceManagerInfoModel();
				model.setSNo(sNo);
				model.setPartyName(partyName);
				model.setBillNumber(balanceInfo.getBillnumber());
				model.setBillDate(balanceInfo.getBilldate());
				model.setType(balanceInfo.getType());
				model.setOpeningBalanceAmount(balanceInfo.getOpeningbalanceamount());
				model.setReceivedAmount(balanceInfo.getReceivedopeningbalanceamount());
				model.setRemainingAmount(balanceInfo.getRemainingopeningbalanceamount());
				infoModel.add(model);
				sNo++;
			}

			OpeningBalanceManagerModel balanceModel = new OpeningBalanceManagerModel(infoModel);
			table.setModel(balanceModel);
			table.setRowHeight(table.getRowHeight() + 20);
			table.setAutoCreateRowSorter(true);
			table.setName("Opening Balance Manager");
			table.setColumnControlVisible(true);
			table.packAll();
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

	public void autoAccountInfoSuggestor(JTextField textField) {
		final String methodName = "autoAccountInfoSuggestor";
		List<AccountInfo> accountInfos = new ArrayList<>();
		try {
			accountInfos = AccountDaoImpl.getInstance().getItemList();
		} catch (Exception e) {
			LOGGER.error(e);
		}
		leamonAutoAccountIDTextFieldSuggestor = new LeamonAutoAccountInfoTextFieldSuggestor<List<AccountInfo>, AccountInfo>(
				textField, this);
		leamonAutoAccountIDTextFieldSuggestor.setItems(accountInfos);

	}
}
