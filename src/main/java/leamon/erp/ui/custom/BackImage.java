package leamon.erp.ui.custom;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class BackImage extends JComponent{
	Image i;

	//Creating Constructer
	public BackImage(Image i) {
		this.i = i;

	}

	//Overriding the paintComponent method
	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(i, 0, 0, null);  // Drawing image using drawImage method

	}
}
