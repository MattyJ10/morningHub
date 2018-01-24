import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPasswordField;

public class LoginWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailTextField;
	private JPasswordField passwordTextField;
	private JLabel emailLabel;
	private JLabel passwordLabel;
	protected static String email; 

	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		emailTextField = new JTextField();
		emailTextField.setBounds(186, 99, 134, 28);
		contentPane.add(emailTextField);
		emailTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(186, 139, 134, 28);
		contentPane.add(passwordTextField);
		
		emailLabel = new JLabel("Email");
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailLabel.setBounds(113, 105, 61, 16);
		contentPane.add(emailLabel);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setBounds(113, 145, 61, 16);
		contentPane.add(passwordLabel);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(113, 201, 117, 29);
		contentPane.add(btnLogin);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DbConnect db = new DbConnect(); 
				java.sql.Connection conn = db.Connect();
				try {
					//find password connected to entered email from database
					String query = "SELECT email, password FROM musicApp.Users where email = (?)";
					java.sql.PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, emailTextField.getText());
					ResultSet rs = preparedStmt.executeQuery();
					
					//get the entered text as a char array then convert it to a string to 
					//test against value in database
					char[] enteredText = passwordTextField.getPassword();
					String enteredString = new String(enteredText); 
					if (rs.next() && rs.getString("password").equals(enteredString)) {
						LoginWindow.email = emailTextField.getText(); 
						new MainWindow(); 
						conn.close();
						dispose();
					} else {
						conn.close();
						System.out.println("error"); 
					}
				} catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.setBounds(242, 201, 134, 29);
		contentPane.add(btnCreateAccount);
		btnCreateAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CreateAccountWindow w = new CreateAccountWindow(); 
				w.setVisible(true);
				dispose(); 
				
			}
		});
		
		JLabel titleLabel = new JLabel("Morning Hub");
		titleLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(152, 6, 146, 40);
		contentPane.add(titleLabel);
	}
}
