package leamon.erp.ui.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Creating an another seperate class CalculatorFrame(Step 4)
public class CalculatorFrame extends JFrame implements ActionListener {
	/*
	 * Creating all components and a container that is going to be used in our
	 * program (Step 6)
	 */
	Container c;
	JLabel lbl;
	JTextField tf;
	JRadioButton on;
	JRadioButton off;
	JButton zero;
	JButton one;
	JButton two;
	JButton three;
	JButton four;
	JButton five;
	JButton six;
	JButton seven;
	JButton eight;
	JButton nine;
	JButton dot;
	JButton clear;
	JButton backspace;
	JButton equal;
	JButton mul;
	JButton div;
	JButton plus;
	JButton minus;
	JButton sqroot;

	// Creating two double and one integer type variables(Step 7)
	double num, ans;
	int calculation;

	// Creating constructor of CalculatorFrame class (Step 8)
	public CalculatorFrame() {
		// getting the content pane using getContentPane() method (Step 9)
		c = this.getContentPane();
		// Setting layout of the container to null (Step 10);
		c.setLayout(null);
		// Setting the background color of container (Step 11)
		c.setBackground(Color.PINK);
		// creating objects of all components (Step 12)
		tf = new JTextField();
		lbl = new JLabel();
		on = new JRadioButton("on");
		off = new JRadioButton("off");
		seven = new JButton("7");
		eight = new JButton("8");
		nine = new JButton("9");
		four = new JButton("4");
		five = new JButton("5");
		six = new JButton("6");
		one = new JButton("1");
		two = new JButton("2");
		three = new JButton("3");
		dot = new JButton(".");
		zero = new JButton("0");
		equal = new JButton("=");
		plus = new JButton("+");
		minus = new JButton("-");
		mul = new JButton("x");
		div = new JButton("/");
		clear = new JButton("C");
		backspace = new JButton("<--");
		sqroot = new JButton("sqrt");

		// Adjusting alignment to right for text entered in textfield(Step 13)
		tf.setHorizontalAlignment(SwingConstants.RIGHT);

		// Creating object of button group(Step 14)
		ButtonGroup grp = new ButtonGroup();
		grp.add(on);
		grp.add(off);
		on.setSelected(true);
		on.setEnabled(false);
		off.setEnabled(true);

		// Setting font size for components(Step 15)
		Font fo = new Font("Arial", Font.BOLD, 20);
		tf.setFont(fo);
		seven.setFont(fo);
		eight.setFont(fo);
		nine.setFont(fo);
		four.setFont(fo);
		five.setFont(fo);
		six.setFont(fo);
		one.setFont(fo);
		two.setFont(fo);
		three.setFont(fo);
		dot.setFont(fo);
		zero.setFont(fo);
		equal.setFont(fo);
		plus.setFont(fo);
		minus.setFont(fo);
		mul.setFont(fo);
		div.setFont(fo);
		clear.setFont(fo);

		Font font = new Font("Arial", Font.BOLD, 15);
		backspace.setFont(font);

		/*
		 * Setting size and location of components using setBounds() method(Step 16)
		 */

		tf.setBounds(10, 40, 270, 40);
		lbl.setBounds(250, 0, 50, 50);
		on.setBounds(10, 95, 60, 40);
		off.setBounds(10, 120, 60, 40);
		clear.setBounds(80, 110, 60, 40);
		seven.setBounds(10, 170, 60, 40);
		eight.setBounds(80, 170, 60, 40);
		nine.setBounds(150, 170, 60, 40);
		four.setBounds(10, 230, 60, 40);
		five.setBounds(80, 230, 60, 40);
		six.setBounds(150, 230, 60, 40);
		one.setBounds(10, 290, 60, 40);
		two.setBounds(80, 290, 60, 40);
		three.setBounds(150, 290, 60, 40);
		dot.setBounds(10, 350, 60, 40);
		zero.setBounds(80, 350, 60, 40);
		equal.setBounds(150, 350, 60, 40);
		plus.setBounds(220, 110, 60, 40);
		minus.setBounds(220, 170, 60, 40);
		mul.setBounds(220, 230, 60, 40);
		div.setBounds(220, 290, 60, 40);
		backspace.setBounds(150, 110, 60, 40);
		sqroot.setBounds(220, 350, 60, 40);

		/*
		 * Adding components to container using add() method(Step 17)
		 */
		c.add(tf);
		c.add(lbl);
		c.add(on);
		c.add(off);
		c.add(seven);
		c.add(eight);
		c.add(nine);
		c.add(four);
		c.add(five);
		c.add(six);
		c.add(one);
		c.add(two);
		c.add(three);
		c.add(dot);
		c.add(zero);
		c.add(equal);
		c.add(plus);
		c.add(minus);
		c.add(mul);
		c.add(div);
		c.add(clear);
		c.add(backspace);
		c.add(sqroot);

		// Registering action listener to buttons(Step 18)
		on.addActionListener(this);
		off.addActionListener(this);
		seven.addActionListener(this);
		eight.addActionListener(this);
		nine.addActionListener(this);
		four.addActionListener(this);
		five.addActionListener(this);
		six.addActionListener(this);
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		dot.addActionListener(this);
		zero.addActionListener(this);
		equal.addActionListener(this);
		plus.addActionListener(this);
		minus.addActionListener(this);
		mul.addActionListener(this);
		div.addActionListener(this);
		clear.addActionListener(this);
		backspace.addActionListener(this);
		sqroot.addActionListener(this);

	}

	// Performing actionPerformed() method for each registered buttons(Step 19)
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == on) {
			enable();
		}

		else if (e.getSource() == off) {
			disable();
		} else if (e.getSource() == clear) {
			tf.setText(" ");
			lbl.setText(" ");
		}

		else if (e.getSource() == backspace) {
			int length = tf.getText().length();
			int number = length - 1;

			if (length > 0) {
				StringBuilder back = new StringBuilder(tf.getText());
				back.deleteCharAt(number);
				tf.setText(back.toString());

			}

		} else if (e.getSource() == zero) {
			tf.setText(tf.getText() + "0");
		}

		else if (e.getSource() == one) {
			tf.setText(tf.getText() + "1");
		}

		else if (e.getSource() == two) {
			tf.setText(tf.getText() + "2");
		}

		else if (e.getSource() == three) {
			tf.setText(tf.getText() + "3");
		}

		else if (e.getSource() == four) {
			tf.setText(tf.getText() + "4");
		}

		else if (e.getSource() == five) {
			tf.setText(tf.getText() + "5");
		}

		else if (e.getSource() == six) {
			tf.setText(tf.getText() + "6");
		}

		else if (e.getSource() == seven) {
			tf.setText(tf.getText() + "7");
		}

		else if (e.getSource() == eight) {
			tf.setText(tf.getText() + "8");
		} else if (e.getSource() == nine) {
			tf.setText(tf.getText() + "9");

		}

		else if (e.getSource() == dot) {
			tf.setText(tf.getText() + ".");
		} else if (e.getSource() == plus) {
			num = Double.parseDouble(tf.getText());
			tf.setText(" ");
			lbl.setText(num + "+");
			calculation = 1;
		} else if (e.getSource() == minus) {
			num = Double.parseDouble(tf.getText());
			tf.setText(" ");
			lbl.setText(num + "-");
			calculation = 2;
		} else if (e.getSource() == mul) {
			num = Double.parseDouble(tf.getText());
			tf.setText(" ");
			lbl.setText(num + "x");
			calculation = 3;
		}

		else if (e.getSource() == div) {
			num = Double.parseDouble(tf.getText());
			tf.setText(" ");
			lbl.setText(num + "/");
			calculation = 4;
		} else if (e.getSource() == sqroot) {
			num = Double.parseDouble(tf.getText());
			ans = Math.sqrt(Double.parseDouble(tf.getText()));
			tf.setText(Double.toString(ans));
		} else if (e.getSource() == equal) {
			switch (calculation) {
			case 1:
				ans = num + Double.parseDouble(tf.getText());
				tf.setText(Double.toString(ans));
				lbl.setText(" ");
				break;
			case 2:
				ans = num - Double.parseDouble(tf.getText());
				tf.setText(Double.toString(ans));
				lbl.setText(" ");
				break;
			case 3:
				ans = num * Double.parseDouble(tf.getText());
				tf.setText(Double.toString(ans));
				lbl.setText(" ");
				break;
			case 4:
				ans = num / Double.parseDouble(tf.getText());
				tf.setText(Double.toString(ans));
				lbl.setText(" ");
				break;

			}
		}

	}

	public void disable() {
		tf.setText(" ");
		lbl.setText(" ");
		off.setEnabled(false);
		on.setEnabled(true);
		tf.setEnabled(false);
		dot.setEnabled(false);
		zero.setEnabled(false);
		one.setEnabled(false);
		two.setEnabled(false);
		three.setEnabled(false);
		four.setEnabled(false);
		five.setEnabled(false);
		six.setEnabled(false);
		seven.setEnabled(false);
		eight.setEnabled(false);
		nine.setEnabled(false);
		plus.setEnabled(false);
		minus.setEnabled(false);
		mul.setEnabled(false);
		div.setEnabled(false);
		equal.setEnabled(false);
		clear.setEnabled(false);
		backspace.setEnabled(false);
	}

	public void enable() {
		on.setEnabled(false);
		off.setEnabled(true);
		tf.setEnabled(true);
		dot.setEnabled(true);
		zero.setEnabled(true);
		one.setEnabled(true);
		two.setEnabled(true);
		three.setEnabled(true);
		four.setEnabled(true);
		five.setEnabled(true);
		six.setEnabled(true);
		seven.setEnabled(true);
		eight.setEnabled(true);
		nine.setEnabled(true);
		plus.setEnabled(true);
		minus.setEnabled(true);
		mul.setEnabled(true);
		div.setEnabled(true);
		equal.setEnabled(true);
		clear.setEnabled(true);
		backspace.setEnabled(true);

	}

}