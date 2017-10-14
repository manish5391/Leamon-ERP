package leamon.erp.ui.event;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class FocusListenerHandler implements FocusListener{

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() instanceof JTextField){
			((JTextField)e.getSource()).selectAll();
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		
	}

}
