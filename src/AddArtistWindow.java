import java.awt.Font;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
public class AddArtistWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JPanel contentPane;
	private JTextField textField;

	public AddArtistWindow() {
		
		initialize(); 
	}
	
	private void initialize() {
		
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(118, 126, 134, 28);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblArtistName = new JLabel("Artist Name: ");
		lblArtistName.setBounds(30, 132, 93, 16);
		getContentPane().add(lblArtistName);
		
		//change this to add the unique ID
		JButton btnAddArtist = new JButton("Add");
		btnAddArtist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int userid = GetMusic.getUserId(); 
				int id = AddArtistWindow.generateID(); 
				DbConnect db = new DbConnect(); 
				Connection conn = (Connection) db.Connect(); 
				try {
					String query = "INSERT into artists (id, userid, artist)" + " values (?, ?, ?)";
					java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, id);
					preparedStmt.setInt(2, userid);
					preparedStmt.setString(3, textField.getText());
					preparedStmt.executeUpdate();
					MainWindow.refreshArtists();
					conn.close();
					dispose(); 
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
				
				
			}
		});
		btnAddArtist.setBounds(128, 166, 117, 29);
		getContentPane().add(btnAddArtist);
		
		JLabel title = new JLabel("Add A New Artist", SwingConstants.CENTER);
		title.setBounds(96, 6, 266, 78);
		title.setFont(new Font("Charter", Font.PLAIN, 25));
		getContentPane().add(title);
	}
	
	protected static int generateID() {
		DbConnect db = new DbConnect(); 
		java.sql.Connection conn = db.Connect();
		int ret = 0;
		Random r = new Random();
		Boolean x = true;
		while(x) {
			int test = r.nextInt();
			String query = "SELECT id FROM artists WHERE id = " + test;
			try {
				java.sql.PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				if (!rs.first()) {
					ret = test;
					x = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret; 
	}
	
	
	
}
