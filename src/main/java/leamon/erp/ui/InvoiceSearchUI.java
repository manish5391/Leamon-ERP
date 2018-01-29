package leamon.erp.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTextField;

import leamon.erp.util.LeamonERPConstants;

public class InvoiceSearchUI extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvoiceSearchUI frame = new InvoiceSearchUI();
					frame.setVisible(true);
					JFrame fr = new JFrame();
					fr.getContentPane().add(frame);
					fr.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InvoiceSearchUI() {
		getContentPane().setBackground(Color.WHITE);
		setTitle("Leamon-ERP-Search");
		setBounds(100, 100, 711, 453);
		getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(216, 191, 216));
		panel_1.setBounds(0, 0, 705, 128);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JXTextField textField = new JXTextField();
		textField.setBounds(134, 18, 136, 23);
		textField.setPrompt("Name");
		textField.setName("txtInventoryAccountName");
		textField.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField.setEnabled(false);
		textField.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		panel_1.add(textField);
		
		JXLabel label = new JXLabel();
		label.setBounds(134, 0, 71, 16);
		label.setText("Party Name");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		panel_1.add(label);
		
		JXTextField textField_2 = new JXTextField();
		textField_2.setPrompt("City");
		textField_2.setName("txtInventoryAccountName");
		textField_2.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField_2.setEnabled(false);
		textField_2.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textField_2.setBounds(134, 73, 136, 23);
		panel_1.add(textField_2);
		
		JXLabel lblCity = new JXLabel();
		lblCity.setText("City");
		lblCity.setForeground(Color.BLACK);
		lblCity.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblCity.setBounds(134, 52, 39, 25);
		panel_1.add(lblCity);
		
		JXLabel lblDate = new JXLabel();
		lblDate.setText("Date");
		lblDate.setForeground(Color.BLACK);
		lblDate.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblDate.setBounds(302, 0, 47, 16);
		panel_1.add(lblDate);
		
		JXDatePicker datePicker = new JXDatePicker();
		datePicker.setBounds(296, 20, 279, 22);
		panel_1.add(datePicker);
		
		JXLabel lblFrom = new JXLabel();
		lblFrom.setText("From");
		lblFrom.setForeground(Color.BLACK);
		lblFrom.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblFrom.setBounds(292, 77, 39, 16);
		panel_1.add(lblFrom);
		
		JXDatePicker datePicker_1 = new JXDatePicker();
		datePicker_1.setBounds(331, 75, 110, 22);
		panel_1.add(datePicker_1);
		
		JXLabel lblTo = new JXLabel();
		lblTo.setText("To");
		lblTo.setForeground(Color.BLACK);
		lblTo.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblTo.setBounds(451, 77, 23, 16);
		panel_1.add(lblTo);
		
		JXDatePicker datePicker_2 = new JXDatePicker();
		datePicker_2.setBounds(475, 75, 100, 22);
		panel_1.add(datePicker_2);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 94, 120);
		panel_1.add(panel);
		panel.setLayout(null);
		
		JXLabel lblFilterBy = new JXLabel();
		lblFilterBy.setBounds(15, 5, 66, 16);
		panel.add(lblFilterBy);
		lblFilterBy.setText("Filter By : ");
		lblFilterBy.setForeground(Color.BLACK);
		lblFilterBy.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		
		JList list = new JList();
		list.setBounds(10, 22, 74, 98);
		panel.add(list);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Party Name", "City", "State", "Date", "Date Range", "Invoice Range"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(586, 75, 91, 23);
		panel_1.add(btnSearch);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 127, 705, 300);
		getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);
		
		JXTable table = new JXTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		scrollPane.setViewportView(table);

	}
}
