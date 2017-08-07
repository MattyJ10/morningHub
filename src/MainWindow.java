import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import java.awt.Dimension;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;

public class MainWindow {

	private JFrame frame;

	public MainWindow() {
		initialize();
	}

	private void initialize() {
		
		
		
		//Set up overall frame
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//main tab layout
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane);
		tabbedPane.setPreferredSize(new Dimension(900, 650));
		
		//Music tab
		JPanel musicPanel = new JPanel();
		tabbedPane.addTab("Music", null, musicPanel, null);
		musicPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane();
		musicPanel.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(300, 400));
		
		JList<String> centerList = new JList<String>();
		scrollPane.setViewportView(centerList);
		
		//news tab
		JPanel newsPanel = new JPanel();
		tabbedPane.addTab("News", null, newsPanel, null);
		
		
		
		
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
		frame.setVisible(true);
		

	}
}
