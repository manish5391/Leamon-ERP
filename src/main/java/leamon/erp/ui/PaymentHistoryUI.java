package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXTextField;
import leamon.erp.util.LeamonERPConstants;
import java.awt.Font;
import org.jdesktop.swingx.JXLabel;

public class PaymentHistoryUI extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentHistoryUI frame = new PaymentHistoryUI();
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
	public PaymentHistoryUI() {
		getContentPane().setBackground(Color.WHITE);
		setClosable(true);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setTitle("Payment Received History");
		setBounds(100, 100, 732, 477);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 726, 78);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JXTextField textField = new JXTextField();
		textField.setBounds(89, 5, 323, 23);
		textField.setPrompt("Select Party Name");
		textField.setName("txtPartyName");
		textField.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textField.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		panel.add(textField);
		
		JXLabel label = new JXLabel();
		label.setBounds(10, 9, 71, 16);
		label.setText("Party Name");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		panel.add(label);

	}
}
