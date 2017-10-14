package leamon.erp.ui.custom;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import leamon.erp.util.LeamonERPConstants;

public class BGImagePanel extends JPanel{
	Image bgimage = null;
	
	public BGImagePanel(String imagePath) {
	    MediaTracker mt = new MediaTracker(this);
	    bgimage = Toolkit.getDefaultToolkit().getImage(imagePath);
	    mt.addImage(bgimage, 0);
	    try {
	      mt.waitForAll();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }

	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    int imwidth = bgimage.getWidth(null);
	    int imheight = bgimage.getHeight(null);
	    g.drawImage(bgimage, 1, 1, null);
	  }
	  
	  public static void main(String[] args) {
		JFrame fr = new JFrame("");
		String imagePath = "C:\\Users\\mmishra\\Documents\\workspace-sts-3.8.4.RELEASE\\Leamon-ERP\\src\\main\\resources\\bill1.jpg";
		fr.add(new BGImagePanel(imagePath));
		fr.setVisible(true);
		
	}
}
