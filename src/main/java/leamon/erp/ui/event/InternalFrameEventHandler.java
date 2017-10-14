package leamon.erp.ui.event;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.log4j.Logger;

public class InternalFrameEventHandler implements InternalFrameListener{

	static final Logger LOGGER = Logger.getLogger(InternalFrameEventHandler.class);
	
	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		LOGGER.info("click");
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		LOGGER.info("Activared");	
		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		LOGGER.info("DeActivared");
	}

}
