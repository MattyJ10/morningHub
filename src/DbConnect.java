import java.sql.*;

public class DbConnect {

	public static void main(String[] args) {
		
		try {
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicApp", "user", "password"); 
			
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from test"); 
			while (myRs.next()) {
				System.out.println(myRs.getString("id"));
			}
			
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}
	

}
