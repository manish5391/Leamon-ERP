package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;

/**
 * Payment Report UI
 * 
 * @date FEB 05,2018
 * @author Manish Kumar Mishra
 *
 */
public class PaymentReportUI extends JInternalFrame {
	
	public PaymentReportUI() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Payment Report UI");
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(3, 30, 853, 534);
		getContentPane().setLayout(null);
	}
}
