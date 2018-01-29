package leamon.erp.ui.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXDatePicker;

import com.google.common.base.Strings;


public class AccountOpeningBalanceUIKeyHandler implements KeyListener {
	
	private static final Logger LOGGER = Logger.getLogger(InvoiceUiEventHandler.class);
	private JTextField nextComponent;
	private JCheckBox nextComponentCheckBox;
	private JButton nextComponentButton;
	
	public AccountOpeningBalanceUIKeyHandler(JTextField nextComponent){
		this.nextComponent = nextComponent;
	}
	
	public AccountOpeningBalanceUIKeyHandler(JCheckBox nextComponent){
		this.nextComponentCheckBox = nextComponent;
	}

	public AccountOpeningBalanceUIKeyHandler(JButton btnSave) {
		this.nextComponentButton = btnSave;
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
				if(nextComponent instanceof JFormattedTextField){
					((JFormattedTextField)nextComponent).requestFocus();
				}else{
					nextComponent.requestFocus();
					if(!Strings.isNullOrEmpty(nextComponent.getName()) && nextComponent.getName().equals("txtFieldWRemark")){
						nextComponent.setText(textField.getText());
					}
				}
			}else if(nextComponentCheckBox != null){
				nextComponentCheckBox.requestFocus();
			}else if(nextComponentButton != null){
				nextComponentButton.requestFocus();
			}
		}else if(e.getSource() instanceof JCheckBox){
			if(nextComponentCheckBox != null){
				nextComponentCheckBox.requestFocus();
			}else if (nextComponent != null ){
				nextComponent.requestFocus();
			}
		}
	}//end
	
}
