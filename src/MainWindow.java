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
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ListSelectionModel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;


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
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}}); 
		
	}

	private void initialize() {
			
		
		//Set up overall frame
		frame = new JFrame();
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
		musicPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
		tabbedPane.addTab("Music", null, musicPanel, null);
		musicPanel.setLayout(null);
		
		JPanel checksPanel = new JPanel();
		checksPanel.setForeground(new Color(51, 204, 255));
		checksPanel.setBounds(175, 296, 329, 200);
		checksPanel.setMaximumSize(new Dimension(200, 200));
		musicPanel.add(checksPanel);
		checksPanel.setLayout(new BoxLayout(checksPanel, BoxLayout.X_AXIS));
		

		JCheckBox chckbxSongs = new JCheckBox("Songs");
		checksPanel.add(chckbxSongs);
		chckbxSongs.setHorizontalAlignment(SwingConstants.CENTER);
		
		JCheckBox chckbxDatpiff = new JCheckBox("DatPiff");
		checksPanel.add(chckbxDatpiff);
		chckbxDatpiff.setHorizontalAlignment(SwingConstants.CENTER);
		
		JCheckBox chckbxHnhh = new JCheckBox("HnHH");
		checksPanel.add(chckbxHnhh);
		chckbxHnhh.setSelected(true);
		chckbxHnhh.setHorizontalAlignment(SwingConstants.CENTER);
		
		JCheckBox chckbxMixtapes = new JCheckBox("Mixtapes");
		checksPanel.add(chckbxMixtapes);
		chckbxMixtapes.setSelected(true);
		chckbxMixtapes.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane artistPane = new JScrollPane();
		artistPane.setBounds(175, 47, 329, 200);
		artistPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		artistPane.setMaximumSize(new Dimension(100, 200));
		musicPanel.add(artistPane);
		artistPane.setMinimumSize(new Dimension(0, 0));
		
		JList<String> artistList = new JList<String>(favoriteArtists);
		artistPane.setViewportView(artistList);
		artistList.setBackground(new Color(255, 255, 255));
		artistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		System.out.println("initialize: " + favoriteArtists); 
		
		JButton addArtistButton = new JButton("Add");
		artistPane.setColumnHeaderView(addArtistButton);
		addArtistButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddArtistWindow add = new AddArtistWindow(); 
				add.setMinimumSize(new Dimension(400, 300));
				add.setVisible(true);
			}
		});
		
		addArtistButton.setForeground(new Color(0, 255, 0));
		
		JScrollPane musicPane = new JScrollPane();
		musicPane.setBounds(516, 6, 347, 592);
		musicPanel.add(musicPane);
		musicPane.setPreferredSize(new Dimension(300, 400));
		
		JList<String> musicList = new JList<String>();
		musicPane.setViewportView(musicList);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setForeground(new Color(30, 144, 255));
		refreshButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Elements values = new Elements(); 
				DefaultListModel<String> items = new DefaultListModel<String>();
				
				if (chckbxHnhh.isSelected() && chckbxMixtapes.isSelected()) {
					values = GetMusic.getMixtapesHnHH();
					outputMixtapesHnHH(items, values); 
				}
				if (chckbxHnhh.isSelected() && chckbxSongs.isSelected()) {
					items.removeAllElements();
					values = GetMusic.getSongsHnHH();
					outputSongsHnHH(items, values); 
				}
				if (chckbxHnhh.isSelected() && chckbxSongs.isSelected() && chckbxMixtapes.isSelected()) {
					items.removeAllElements();
					values = GetMusic.getSongsHnHH();
					outputSongsHnHH(items, values);
					values = GetMusic.getMixtapesHnHH();
					outputMixtapesHnHH(items, values); 
				}
				
				
				musicList.setModel(items);
			}
		});
		musicPane.setColumnHeaderView(refreshButton);
		
		JButton removeArtistButton = new JButton("Remove");
		removeArtistButton.setBounds(175, 244, 314, 29);
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
					
					//DELETE FROM orders WHERE id_users = 1 AND id_product = 2 LIMIT 1
					String query = "DELETE FROM artists WHERE artist = '" + artistList.getSelectedValue() + "' LIMIT 1"; 
					PreparedStatement preparedStmt = (PreparedStatement) conn.prepareStatement(query);
					preparedStmt.execute(); 
					favoriteArtists.removeElement(artistList.getSelectedValue()); 
					refreshArtists(); 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
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
			System.out.println();
			String artist = element.text(); 
			if (favoriteArtists.contains(artist)){
				items.addElement("There is a new mixtape by " + artist); 
				items.addElement("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
			}
		}
	}
	
	public void outputSongsHnHH(DefaultListModel<String> items, Elements values) {
		for (Element element: values) {
			System.out.println(); 
			String artist = element.text(); 
			if (favoriteArtists.contains(artist)) {
				
				items.addElement("There is a new song by " + artist); 
				items.addElement("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
				
			}
		}
	}
}

