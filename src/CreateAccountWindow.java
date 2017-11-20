import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.CharSequence;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent; 

public class CreateAccountWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailText;
	private JTextField passwordText;
	private JTextField confirmPasswordText;

	/**
	 * Launch the application.
	 */
	public CreateAccountWindow() {
		initialize(); 
		
	}

	/**
	 * Create the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(92, 93, 61, 16);
		contentPane.add(lblEmail);
		
		emailText = new JTextField();
		emailText.setBounds(181, 87, 134, 28);
		contentPane.add(emailText);
		emailText.setColumns(10);
		
		passwordText = new JTextField();
		passwordText.setColumns(10);
		passwordText.setBounds(181, 127, 134, 28);
		contentPane.add(passwordText);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(92, 133, 61, 16);
		contentPane.add(lblPassword);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(91, 223, 117, 29);
		contentPane.add(btnCancel);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (emailText.getText().contains(Character.toString('@')) && passwordText.getText().equals(confirmPasswordText.getText())) {
					System.out.println("Made it  here"); 
					DbConnect db = new DbConnect(); 
					java.sql.Connection conn = db.Connect();
					try {
						String query = "insert into users (email, password)" + " values (?, ?)";
						java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
						preparedStmt.setString (1, emailText.getText());
					    preparedStmt.setString (2, passwordText.getText());
					    preparedStmt.executeUpdate();
					    conn.close();
					    new MainWindow(); 
					    //create a new table to hold the users artist data and connect it to the user. << ?
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		btnCreateAccount.setBounds(231, 223, 128, 29);
		contentPane.add(btnCreateAccount);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmPassword.setBounds(25, 176, 128, 16);
		contentPane.add(lblConfirmPassword);
		
		confirmPasswordText = new JTextField();
		confirmPasswordText.setColumns(10);
		confirmPasswordText.setBounds(181, 167, 134, 28);
		contentPane.add(confirmPasswordText);
	}
}
