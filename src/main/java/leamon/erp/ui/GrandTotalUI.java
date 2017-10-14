package leamon.erp.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.JXTextField;

import leamon.erp.util.LeamonERPConstants;
import lombok.Getter;

@Getter
public class GrandTotalUI extends JInternalFrame {
	private JXTextField textFieldTax;
	private JXTextField textFieldTotal;
	private JXTextField textFieldAmount;

	private JLabel labelTaxValue;
	private JLabel labelTotalValue1;
	private JLabel labelGrandTotalValue1;
	private JLabel labelTotalValue2;
	private JLabel labelSomeAmount;
	private JLabel labelSuspenseAmount;
	private JLabel labelGrandTotalValue2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrandTotalUI frame = new GrandTotalUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws PropertyVetoException 
	 */
	public GrandTotalUI() {
		setTitle("Grand Total Calculator");
		getContentPane().setBackground(Color.BLACK);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setBounds(100, 100, 447, 435);
		
		//getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, new Color(139, 0, 0), null, null, null), "Grand Total Calculator", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(205, 92, 92)));
		panel.setBackground(Color.WHITE);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLUE);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(Color.BLUE);
		
		JButton button_1 = new JButton("Clear");
		button_1.setForeground((Color) null);
		button_1.setFont(new Font("DialogInput", Font.BOLD, 14));
		button_1.setBackground(Color.WHITE);
		
		JButton button_3 = new JButton("Save");
		button_3.setForeground((Color) null);
		button_3.setFont(new Font("DialogInput", Font.BOLD, 14));
		button_3.setBackground(Color.WHITE);
		button_3.setActionCommand("AddStockItem");
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(Color.ORANGE);
		
		JLabel lblTax = new JLabel("GST/TAX");
		lblTax.setForeground((Color) null);
		lblTax.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setForeground((Color) null);
		lblTotal.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblSuspense = new JLabel("S.Amount");
		lblSuspense.setForeground((Color) null);
		lblSuspense.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		textFieldTax = new JXTextField();
		textFieldTax.setHorizontalAlignment(SwingConstants.RIGHT);
		textFieldTax.setName(LeamonERPConstants.TEXTFIELD_GTCALC_TAX);
		textFieldTax.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldTax.setColumns(10);
		textFieldTax.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTax.addKeyListener(new GTKeyHandler());
		
		textFieldTotal = new JXTextField();
		textFieldTotal.setName(LeamonERPConstants.TEXTFIELD_GTCALC_TOTAL);
		textFieldTotal.setFont(new Font("DialogInput", Font.BOLD, 16));
		textFieldTotal.setColumns(10);
		textFieldTotal.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldTotal.addKeyListener(new GTKeyHandler());
		
		textFieldAmount = new JXTextField();
		textFieldAmount.setName(LeamonERPConstants.TEXTFIELD_GTCALC_AMOUNT);
		textFieldAmount.setFont(new Font("DialogInput", Font.PLAIN, 16));
		textFieldAmount.setColumns(10);
		textFieldAmount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER);
		textFieldAmount.addKeyListener(new GTKeyHandler());
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBackground(Color.BLUE);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBackground(Color.BLUE);
		
		JLabel label = new JLabel("%");
		label.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource(LeamonERPConstants.IMG_PERCENTAGE_LABEL)));
		label.setForeground((Color) null);
		label.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		
		JLabel lblGrandTotal = new JLabel("Grand Total");
		lblGrandTotal.setForeground(Color.YELLOW);
		lblGrandTotal.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		JLabel label_2 = new JLabel("Total");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		labelTotalValue2 = new JLabel("0");
		labelTotalValue2.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalValue2.setForeground(Color.WHITE);
		labelTotalValue2.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		JLabel lblAmount_1 = new JLabel("S.Amount");
		lblAmount_1.setForeground(Color.WHITE);
		lblAmount_1.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		labelSomeAmount = new JLabel("- 0");
		labelSomeAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSomeAmount.setForeground(Color.WHITE);
		labelSomeAmount.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		JLabel label_5 = new JLabel("Suspense");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		labelSuspenseAmount = new JLabel("0");
		labelSuspenseAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSuspenseAmount.setForeground(Color.WHITE);
		labelSuspenseAmount.setFont(new Font("DialogInput", Font.BOLD, 18));
		labelSuspenseAmount.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER_WHITE);
		
		labelGrandTotalValue2 = new JLabel("0");
		labelGrandTotalValue2.setHorizontalAlignment(SwingConstants.RIGHT);
		labelGrandTotalValue2.setForeground(Color.WHITE);
		labelGrandTotalValue2.setFont(new Font("DialogInput", Font.BOLD, 18));
		labelGrandTotalValue2.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER_YELLOW);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAmount_1, Alignment.LEADING)
								.addComponent(lblGrandTotal)))
						.addComponent(label_5)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(9)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(labelTotalValue2, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(labelSomeAmount, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(labelSuspenseAmount, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(labelGrandTotalValue2, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelTotalValue2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAmount_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelSomeAmount, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelSuspenseAmount, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGrandTotal, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelGrandTotalValue2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(201)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button_3))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblTax))
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblTotal)
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(12)
											.addComponent(textFieldTax, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(label, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
										.addComponent(textFieldTotal, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(15)
							.addComponent(lblSuspense)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(116)
									.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(textFieldAmount, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(8)
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE))
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(separator_2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(separator_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)))
					.addGap(40))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTotal)
								.addComponent(textFieldTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addComponent(separator, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(30)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblTax)
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
										.addComponent(textFieldTax, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))))
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblSuspense)
							.addComponent(textFieldAmount, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(79)
							.addComponent(separator_4, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)))
					.addGap(3)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(53)
							.addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(2)
							.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))))
		);
		
		JLabel label_1 = new JLabel("Total");
		label_1.setForeground(SystemColor.window);
		label_1.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		JLabel lblTax_1 = new JLabel("Tax");
		lblTax_1.setForeground(Color.WHITE);
		lblTax_1.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		JLabel lblGt = new JLabel("GT");
		lblGt.setForeground(Color.YELLOW);
		lblGt.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		labelTotalValue1 = new JLabel("0");
		labelTotalValue1.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTotalValue1.setForeground(Color.WHITE);
		labelTotalValue1.setFont(new Font("DialogInput", Font.BOLD, 18));
		//labelTotalValue.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER_WHITE);
		
		labelTaxValue = new JLabel("+ 0");
		labelTaxValue.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTaxValue.setForeground(Color.WHITE);
		labelTaxValue.setFont(new Font("DialogInput", Font.BOLD, 18));
		
		labelGrandTotalValue1 = new JLabel("0");
		labelGrandTotalValue1.setHorizontalAlignment(SwingConstants.RIGHT);
		labelGrandTotalValue1.setForeground(Color.WHITE);
		labelGrandTotalValue1.setFont(new Font("DialogInput", Font.BOLD, 18));
		labelGrandTotalValue1.setBorder(LeamonERPConstants.TEXT_FILED_BOTTOM_BORDER_YELLOW);
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTax_1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGt, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(labelTotalValue1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
						.addComponent(labelGrandTotalValue1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
						.addComponent(labelTaxValue, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelTotalValue1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTax_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelTaxValue, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGt, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelGrandTotalValue1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 421, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 387, Short.MAX_VALUE)
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);

	}
	
	public void setDefaultData(BigDecimal total){
		if(total == null){
			textFieldTotal.setText("N/A");
		}else{
			textFieldTotal.setText(total.toString());
			labelTotalValue1.setText(total.toEngineeringString());
		}
		//textFieldTotal.setEnabled(false);
		textFieldTax.requestFocus();
	}
	
	class GTKeyHandler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(e.getSource() instanceof JTextField){
					JTextField fld = (JTextField) e.getSource();
					if(fld.getName()!=null && fld.getName().equals(LeamonERPConstants.TEXTFIELD_GTCALC_AMOUNT)){
						labelGrandTotalValue2.setText(labelGrandTotalValue1.getText());
						labelTotalValue2.setText(labelTotalValue1.getText());
						double someAmount = 0;
						try{
							someAmount  = Double.parseDouble(textFieldAmount.getText());
							labelSomeAmount.setText(textFieldAmount.getText());
						}catch(Exception exp){
							labelSomeAmount.setText("0");
							someAmount = 0;
						}
						double suspense =  Double.parseDouble(labelTotalValue1.getText()) - someAmount;
						labelSuspenseAmount.setText(new BigDecimal(suspense).toEngineeringString());
					}else if(fld.getName()!=null && fld.getName().equals(LeamonERPConstants.TEXTFIELD_GTCALC_TAX)){
						double taxValue = 0;
						try{
							taxValue = Double.parseDouble(textFieldTax.getText());
						}catch(Exception exp ){
							taxValue = 0;
						}						
						BigDecimal total = new BigDecimal(0);
						try{
							total = new BigDecimal(textFieldTotal.getText());
						}catch(Exception exp){
							total = new BigDecimal(0);
						}
						
						double taxValueOnTotal = total.doubleValue()*(taxValue / 100);
						labelTaxValue.setText(new BigDecimal(taxValueOnTotal).toEngineeringString());
						labelTotalValue1.setText(total.toEngineeringString());
						
						double grandTotal = total.doubleValue() + taxValueOnTotal;
						
						labelGrandTotalValue1.setText(new BigDecimal(grandTotal).toEngineeringString());
						textFieldAmount.requestFocus();
					}else if(fld.getName()!=null && fld.getName().equals(LeamonERPConstants.TEXTFIELD_GTCALC_TOTAL)){
						textFieldTax.requestFocus();
					}
				}//end for textfield
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	} //end keyhandler
}
