import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import java.awt.Dimension;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ListSelectionModel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

//NEXT STEP, CREATE NEW WINDOW TO ADD A NEW ARTIST TO YOUR LIST

public class MainWindow {

	private static JFrame frame;
	static DefaultListModel<String> favoriteArtists = new DefaultListModel<String>();

	public MainWindow() {
		initialize();
		try {
			refreshArtists();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
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
		
		JScrollPane artistPane = new JScrollPane();
		artistPane.setBounds(157, 124, 260, 161);
		musicPanel.add(artistPane);
		artistPane.setMinimumSize(new Dimension(200, 200));
		
		JList<String> artistList = new JList<String>();
		artistList.setBackground(new Color(255, 255, 255));
		artistList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		artistList.setModel(favoriteArtists);
		artistPane.setViewportView(artistList);
		
		JButton addArtistButton = new JButton("+");
		addArtistButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AddArtistWindow add = new AddArtistWindow(); 
				add.setMinimumSize(new Dimension(400, 300));
				add.setVisible(true);
			}
		});
		
		addArtistButton.setBackground(new Color(0, 255, 0));
		addArtistButton.setForeground(new Color(0, 255, 0));
		artistPane.setColumnHeaderView(addArtistButton);
		
		JScrollPane hnhhPane = new JScrollPane();
		hnhhPane.setBounds(422, 5, 300, 400);
		musicPanel.add(hnhhPane);
		hnhhPane.setPreferredSize(new Dimension(300, 400));
		
		JList<String> hnhhList = new JList<String>();
		hnhhPane.setViewportView(hnhhList);
		

		JCheckBox chckbxSongs = new JCheckBox("Songs");
		chckbxSongs.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxSongs.setBounds(734, 37, 128, 23);
		musicPanel.add(chckbxSongs);
		
		JCheckBox chckbxMixtapes = new JCheckBox("Mixtapes");
		chckbxMixtapes.setSelected(true);
		chckbxMixtapes.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxMixtapes.setBounds(744, 72, 128, 23);
		musicPanel.add(chckbxMixtapes);
		
		JCheckBox chckbxHnhh = new JCheckBox("HnHH");
		chckbxHnhh.setSelected(true);
		chckbxHnhh.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxHnhh.setBounds(734, 107, 128, 23);
		musicPanel.add(chckbxHnhh);
		
		JCheckBox chckbxDatpiff = new JCheckBox("DatPiff");
		chckbxDatpiff.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxDatpiff.setBounds(734, 140, 128, 23);
		musicPanel.add(chckbxDatpiff);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.setForeground(new Color(30, 144, 255));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Elements values = new Elements(); 
				DefaultListModel<String> items = new DefaultListModel<String>();
				
				if (chckbxHnhh.isSelected() && chckbxMixtapes.isSelected()) {
					values = GetMusic.getMixtapesHnHH();
					outputMixtapesHnHH(items, values); 
				} else if (chckbxHnhh.isSelected() && chckbxSongs.isSelected()) {
					values = GetMusic.getSongsHnHH(); 
					//need to make songs show up. 
				}
				
				hnhhList.setModel(items);
			}
		});
		hnhhPane.setColumnHeaderView(btnNewButton);
		
		
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
		}
	
	public void refreshChecks(JCheckBox box1, JCheckBox box2) {
		if (box1.isSelected()) {
			box2.setSelected(false);
		}
	}
	
	public void outputMixtapesHnHH(DefaultListModel<String> items, Elements values) {
		for (Element element: values) {
			String artist = element.text(); 
			if (favoriteArtists.contains(artist)){
				//need to generalize this based on which boxes are checked. -> create a function for each case and call it when needed. 
				items.addElement("There is a new mixtape by " + artist); 
				items.addElement("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
			}
		}
	}
	
	//outputSongsHnHH
	//outputMixtapesDatPiff
	//outputSongsDatPiff
	
	}

