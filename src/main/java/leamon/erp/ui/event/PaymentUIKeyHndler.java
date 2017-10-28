package leamon.erp.ui.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import com.google.common.base.Strings;


public class PaymentUIKeyHndler implements KeyListener {
	
	private static final Logger LOGGER = Logger.getLogger(InvoiceUiEventHandler.class);
	private JTextField nextComponent;

	public PaymentUIKeyHndler(JTextField nextComponent){
		this.nextComponent = nextComponent;
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		final int KEY_CODE = e.getKeyCode();
		if(KEY_CODE == KeyEvent.VK_ENTER){
			enterKeyNavigation(e);
			//enterKeyTableHandler(e);
		}
		
		if(KEY_CODE == 18 || KEY_CODE==KeyEvent.VK_TAB){
			//tabKeyNavigation(e);
		}
		
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void enterKeyNavigation(KeyEvent e){
		if(e.getSource() instanceof JTextField){
			JTextField textField = (JTextField) e.getSource();
			if(nextComponent != null ){
				nextComponent.requestFocus();
				if(!Strings.isNullOrEmpty(nextComponent.getName()) && nextComponent.getName().equals("txtFieldWRemark")){
					nextComponent.setText(textField.getText());
				}
				
			}
		}
	}//end
	
}
