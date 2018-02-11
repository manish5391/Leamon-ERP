package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class InvoicHistoryUI extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvoicHistoryUI frame = new InvoicHistoryUI();
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
	public InvoicHistoryUI() {
		setBounds(3, 30, 450, 300);

	}

}
