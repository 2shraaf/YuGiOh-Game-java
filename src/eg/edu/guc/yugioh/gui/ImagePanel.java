package eg.edu.guc.yugioh.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	  private Image img;

	  public ImagePanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }
	  public void setImage(String img)
	  {
		  this.img=new ImageIcon(img).getImage();
	  }
	  public ImagePanel(Image img) {
	    this.img = img;
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }

	}
