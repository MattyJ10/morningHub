import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mysql.jdbc.Connection;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Statement;

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
		
		JButton btnAddArtist = new JButton("Add");
		btnAddArtist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DbConnect db = new DbConnect(); 
				Connection conn = (Connection) db.Connect(); 
				try {
					Statement stmt = conn.createStatement();
					//INSERT into artists (artist) value ("test"); 
					stmt.executeUpdate("INSERT into artists (artist) value (\"" + textField.getText() + "\")");
					setVisible(false);
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
	
	
}
