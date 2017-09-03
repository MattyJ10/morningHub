import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.EventQueue;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.border.LineBorder;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class MainWindow {

	private static JFrame frame;
	static DefaultListModel<String> favoriteArtists = new DefaultListModel<String>();

	public MainWindow() {
		
		initialize();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					refreshArtists();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}); 
		
	}

	private void initialize() {
			
		//Set up overall frame
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaptionText);
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 900, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//main tab layout
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 5, 900, 650);
		frame.getContentPane().add(tabbedPane);
		tabbedPane.setPreferredSize(new Dimension(900, 650));
		
		
		//Music tab
		JPanel musicPanel = new JPanel();
		musicPanel.setBackground(Color.BLACK);
		musicPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		tabbedPane.addTab("Music", null, musicPanel, null);
		musicPanel.setLayout(null);
		
		JScrollPane artistPane = new JScrollPane();
		artistPane.setBackground(Color.GRAY);
		artistPane.setBounds(285, 374, 329, 200);
		artistPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		artistPane.setMaximumSize(new Dimension(100, 200));
		musicPanel.add(artistPane);
		artistPane.setMinimumSize(new Dimension(0, 0));
		
		JList<String> artistList = new JList<String>(favoriteArtists);
		artistList.setForeground(Color.WHITE);
		artistPane.setViewportView(artistList);
		artistList.setBackground(Color.GRAY);
		artistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton addArtistButton = new JButton("Add");
		addArtistButton.setBackground(Color.GRAY);
		artistPane.setColumnHeaderView(addArtistButton);
		addArtistButton.setOpaque(true);
		addArtistButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddArtistWindow add = new AddArtistWindow(); 
				add.setMinimumSize(new Dimension(400, 300));
				add.setVisible(true);
			}
		});
		addArtistButton.setForeground(new Color(0, 153, 0));
		
		JScrollPane musicPane = new JScrollPane();
		musicPane.setBackground(Color.GRAY);
		musicPane.setBounds(160, 6, 554, 367);
		musicPanel.add(musicPane);
		musicPane.setPreferredSize(new Dimension(300, 400));
		
		
		JList<String> musicList = new JList<String>();
		musicList.setBorder(new LineBorder(SystemColor.menu, 1, true));
		musicList.setForeground(Color.WHITE);
		musicList.setBackground(Color.GRAY);
		musicPane.setViewportView(musicList);
		MouseListener ml = new MouseAdapter() {
		      public void mouseClicked(MouseEvent e) {
		    	  openLink(musicList); 
		      }
		    };
		musicList.addMouseListener(ml);
		    
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setOpaque(true);
		refreshButton.setBackground(Color.GRAY);
		refreshButton.setForeground(new Color(0, 153, 0));
		refreshButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Elements values = new Elements(); 
				DefaultListModel<String> items = new DefaultListModel<String>();
				
				outputAll(items, values); 
				
				musicList.setModel(items);
			}
		});
		musicPane.setColumnHeaderView(refreshButton);
		
		JButton removeArtistButton = new JButton("Remove");
		removeArtistButton.setBackground(Color.GRAY);
		removeArtistButton.setBounds(285, 575, 314, 29);
		musicPanel.add(removeArtistButton);
		removeArtistButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		removeArtistButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DbConnect db = new DbConnect(); 
				Connection conn = (Connection) db.Connect();
				try {
					String query = "DELETE FROM artists WHERE artist = '" + artistList.getSelectedValue() + "' LIMIT 1"; 
					PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
					preparedStmt.execute(); 
					favoriteArtists.removeElement(artistList.getSelectedValue()); 
					refreshArtists();
					conn.close(); 
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
				
			}
		});
		removeArtistButton.setForeground(new Color(255, 0, 0));
		
		//news tab
		JPanel newsPanel = new JPanel();
		tabbedPane.addTab("News", null, newsPanel, null);
		
		frame.setVisible(true);
	}
	
	public static void refreshArtists() throws SQLException {
		DbConnect db = new DbConnect(); 
		Connection conn = (Connection) db.Connect(); 
		//select the data from the webpage and add it to the local list
		//"SELECT * FROM table_name
		Statement stmt = (Statement) conn.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT * FROM artists");
		while (rs.next()) {
			String artist = rs.getString("artist"); 
			if (!favoriteArtists.contains(artist)) {
				favoriteArtists.addElement(artist);
			}
		}
		System.out.println(favoriteArtists); 
		conn.close();
	}
	
	public void outputMixtapesHnHH(DefaultListModel<String> items, Elements values) {
		for (Element element: values) {
			String artist = element.text(); 
			if (favoriteArtists.contains(artist)) {
				items.addElement("There is a new mixtape by " + artist); 
				items.addElement("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
			}
		}
	}
	
	public void outputSongsHnHH(DefaultListModel<String> items, Elements values) {
		for (Element element: values) {
			String artist = element.text(); 
			if (favoriteArtists.contains(artist)) {
				items.addElement("There is a new song by " + artist); 
				items.addElement("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
			}
		}
	}
	
	public void outputMixtapesDatpiff(DefaultListModel<String> items, Elements values) {
		for (Element element: values) {
			String artist = element.text(); 
			if (favoriteArtists.contains(artist)) {
				items.addElement("There is a new mixtape by " + artist);
				items.addElement("www.Datpiff.com" + element.parent().child(0).attr("href")); 
			}
		}
	}
	
	public void outputAll(DefaultListModel<String> items, Elements values) {
		values = GetMusic.getMixtapesHnHH(); 
		outputMixtapesHnHH(items, values); 
		values = GetMusic.getSongsHnHH(); 
		outputSongsHnHH(items, values); 
		values = GetMusic.getMixtapesDatpiff();
		outputMixtapesDatpiff(items, values); 
	}
	
	private void openLink(JList<String> musicList) {
		String url = null; 
  	  	if (musicList.getSelectedValue().contains("www")) {
  	  		url = "http://" + (String)musicList.getSelectedValue();
  	  		try {
						Desktop.getDesktop().browse(new URI(url));
					} catch (IOException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
						e1.printStackTrace();
					}
  	  		}
  	  
		}
}

