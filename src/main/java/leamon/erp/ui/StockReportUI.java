package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import org.jdesktop.swingx.JXDatePicker;
import java.util.Date;
import org.jdesktop.swingx.JXTextField;
import leamon.erp.util.LeamonERPConstants;
import org.jdesktop.swingx.JXButton;
import javax.swing.JButton;
import org.jdesktop.swingx.JXPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import org.jdesktop.swingx.JXTable;

/**
 * Stock Report UI
 * 
 * @date FEB 05,2018
 * @author Manish Kumar Mishra
 *
 */
public class StockReportUI extends JInternalFrame {
	
	public StockReportUI() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Stock Report UI");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(3, 30, 850, 534);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(0, 0, 847, 43);
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
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setForeground((Color) null);
		lblStock.setFont(new Font("DialogInput", Font.BOLD, 12));
		lblStock.setBounds(373, 11, 43, 22);
		panel.add(lblStock);
		
		JXDatePicker datePicker = new JXDatePicker((Date) null);
		datePicker.setBounds(75, 12, 116, 22);
		panel.add(datePicker);
		
		JXDatePicker datePicker_1 = new JXDatePicker((Date) null);
		datePicker_1.setBounds(253, 12, 116, 22);
		panel.add(datePicker_1);
		
		JXTextField textField = new JXTextField();
		textField.setPrompt("Stock Item");
		textField.setName("txtPartyName");
		textField.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField.setBounds(410, 13, 302, 23);
		panel.add(textField);
		
		JXButton btnStockRegister = new JXButton();
		btnStockRegister.setText("Stock Register");
		btnStockRegister.setBounds(722, 11, 115, 24);
		panel.add(btnStockRegister);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(0, 474, 847, 34);
		getContentPane().add(panel_1);
		
		JXButton button = new JXButton();
		button.setText("Export To Excel");
		button.setBounds(30, 6, 118, 23);
		panel_1.add(button);
		
		JXButton button_1 = new JXButton();
		button_1.setText("Print Bill");
		button_1.setBounds(177, 6, 109, 23);
		panel_1.add(button_1);
		
		JButton button_2 = new JButton("Close");
		button_2.setBounds(743, 6, 94, 23);
		panel_1.add(button_2);
		
		JButton button_3 = new JButton("Refresh");
		button_3.setBounds(639, 6, 94, 23);
		panel_1.add(button_3);
		
		JXPanel panel_2 = new JXPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(0, 41, 847, 433);
		getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		JXTable table = new JXTable();
		scrollPane.setViewportView(table);
	}
}
