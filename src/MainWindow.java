import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import java.awt.Dimension;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import java.awt.Color;

//NEXT STEP, CREATE NEW WINDOW TO ADD A NEW ARTIST TO YOUR LIST

public class MainWindow {

	private JFrame frame;

	public MainWindow() {
		initialize();
	}

	private void initialize() {
		
		DefaultListModel<String> favoriteArtists = new DefaultListModel<String>();	
		favoriteArtists.addElement("Meek Mill"); 
		favoriteArtists.addElement("Jay Z"); 
		favoriteArtists.addElement("Tyga"); 
		favoriteArtists.addElement("Lil Uzi Vert"); 
		favoriteArtists.addElement("Travis Scott");
		favoriteArtists.addElement("Future"); 
		favoriteArtists.addElement("Big Sean"); 
		favoriteArtists.addElement("A Boogie"); 
		favoriteArtists.addElement("Drake"); 
		favoriteArtists.addElement("G Herbo");
		favoriteArtists.addElement("Lil Bibby");
		favoriteArtists.addElement("Kevin Gates"); 
		favoriteArtists.addElement("Kid Ink"); 
		favoriteArtists.addElement("Kanye West"); 
		favoriteArtists.addElement("Kendrick Lamar"); 
		favoriteArtists.addElement("Lil Durk");
		favoriteArtists.addElement("Nav");
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
		
		JScrollPane artistPane = new JScrollPane();
		musicPanel.add(artistPane);
		
		JList<String> artistList = new JList<String>();
		artistList.setBackground(new Color(255, 255, 255));
		artistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		artistList.setModel(favoriteArtists);
		artistPane.setViewportView(artistList);
		
		JButton addArtistButton = new JButton("+");
		addArtistButton.setBackground(new Color(255, 255, 255));
		addArtistButton.setForeground(new Color(0, 255, 0));
		artistPane.setColumnHeaderView(addArtistButton);
		
		JScrollPane hnhhPane = new JScrollPane();
		musicPanel.add(hnhhPane);
		hnhhPane.setPreferredSize(new Dimension(300, 400));
		
		JList<String> hnhhList = new JList<String>();
		hnhhPane.setViewportView(hnhhList);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.setForeground(new Color(30, 144, 255));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Elements newMixtapes = GetMusic.getMixtapesHNHH();
				
				
				DefaultListModel<String> items = new DefaultListModel<String>();
				//check for mixtapes by favorite artists
				for (Element element: newMixtapes) {
					String artist = element.text(); 
					if (favoriteArtists.contains(artist)){
						items.addElement("There is a new mixtape by " + artist); 
						items.addElement("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
						hnhhList.setModel(items);
						
						
					}
				}
			}
		});
		hnhhPane.setColumnHeaderView(btnNewButton);
		
		//news tab
		JPanel newsPanel = new JPanel();
		tabbedPane.addTab("News", null, newsPanel, null);
		
		frame.setVisible(true);
		

	}
	
	
	}

