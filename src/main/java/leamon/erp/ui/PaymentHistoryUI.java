package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

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
		setBounds(100, 100, 450, 300);

	}

}
