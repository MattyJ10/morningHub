import java.sql.*;

public class DbConnect {

	/* used to connect to SQL database hosted on my laptop,
	 * next step is to add socket programming to allow outside users to connect to it.
	 */
	public Connection Connect() {
		
		try {
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/musicApp?autoReconnect=true&useSSL=false", "user", "password"); 
			return myConn; 
			/*Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from test"); 
			while (myRs.next()) {
				System.out.println(myRs.getString("id"));
			} */
			
		} catch (Exception exc) {
			exc.printStackTrace();
			return null; 
		}
		
		
	}
	

}
