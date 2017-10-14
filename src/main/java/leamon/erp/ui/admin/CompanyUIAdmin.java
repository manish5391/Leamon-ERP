package leamon.erp.ui.admin;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class CompanyUIAdmin extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompanyUIAdmin frame = new CompanyUIAdmin();
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
	public CompanyUIAdmin() {
		setBounds(100, 100, 450, 300);

	}

}
