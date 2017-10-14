package leamon.erp.ui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

public class InventoryUIManager extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryUIManager frame = new InventoryUIManager();
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
	public InventoryUIManager() {
		setToolTipText("Inventory UI Manager");
		setClosable(true);
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);
		setBounds(100, 100, 539, 409);
		

	}
}
