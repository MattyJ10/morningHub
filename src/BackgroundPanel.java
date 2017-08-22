import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

class BackgroundPanel extends JPanel
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// The Image to store the background image in.
    Image img;
    public BackgroundPanel()
    {
        // Loads the background image and stores in img object.
        img = Toolkit.getDefaultToolkit().createImage("background.jpg");
    }

    public void paint(Graphics g)
    {
        // Draws the img to the BackgroundPanel.
        g.drawImage(img, 0, 0, null);
    }
}
