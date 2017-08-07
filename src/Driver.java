import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.SwingConstants;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("unused")
public class Driver {

	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//connect to database
		DbConnect.main(null);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Driver window = new Driver();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Driver() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		
		//Set up overall frame
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set up the different regions in the mainFrame
		JScrollPane scrollPaneCenter = new JScrollPane();
		scrollPaneCenter.setBounds(300, 100, 300, 478);
		
		JScrollPane scrollPaneLeft = new JScrollPane();
		scrollPaneLeft.setBounds(0, 100, 300, 478);
		
		JScrollPane scrollPaneRight = new JScrollPane();
		scrollPaneRight.setBounds(600, 100, 300, 478);
		
		JPanel top = new JPanel();
		top.setBounds(0, 0, 900, 100);
		top.setPreferredSize(new Dimension(500, 100));
		
		JPanel bottom = new JPanel();
		bottom.setBounds(0, 578, 900, 100);
		bottom.setPreferredSize(new Dimension(500, 100));
		
		
		scrollPaneLeft.setPreferredSize(new Dimension(300, 300));
		scrollPaneRight.setPreferredSize(new Dimension(300, 300));
		frame.getContentPane().setLayout(null);
		
		JList<String> centerList = new JList<String>();
		scrollPaneCenter.setViewportView(centerList);
		frame.getContentPane().add(scrollPaneCenter);
		frame.getContentPane().add(scrollPaneLeft);
		frame.getContentPane().add(scrollPaneRight);
		frame.getContentPane().add(top);
		frame.getContentPane().add(bottom);
		
		//Make this cleaner
		ArrayList<String> favorite_artists = new ArrayList<String>();
		favorite_artists.add("Meek Mill"); 
		favorite_artists.add("Jay Z"); 
		favorite_artists.add("Tyga"); 
		favorite_artists.add("Lil Uzi Vert"); 
		favorite_artists.add("Travis $cott");
		favorite_artists.add("Logic");
		Elements newMixtapes = GetMusic.getMixtapes();
		
		
		DefaultListModel<String> items = new DefaultListModel<String>();
		//check for mixtapes by favorite artists
		for (Element element: newMixtapes) {
			String artist = element.text(); 
			if (favorite_artists.contains(artist)){
				items.addElement("There is a new mixtape by " + artist); 
				items.addElement("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
				centerList.setModel(items);
				
			}
		}
		

	}
}
