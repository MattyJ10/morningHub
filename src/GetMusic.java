import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.mysql.jdbc.Connection;

public class GetMusic {

	public static void main(String[] args) throws SQLException {
		LoginWindow w = new LoginWindow(); 
		w.setVisible(true);
		//new MainWindow(); 
		
	}
	
	//get mixtapes from HNHH
	public static Elements getMixtapesHnHH() {
		Elements vals = new Elements(); 
		try {
			//Need to revisit this (.validateTLSCertificates(false)) because cert validation is needed for live version
			Document doc = Jsoup.connect("http://www.hotnewhiphop.com/mixtapes/").get();
			vals = doc.getElementsByClass("default-artist");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vals; 
	}
	
	//get songs from HNHH
	public static Elements getSongsHnHH() {
		Elements vals = new Elements(); 
		try {
			//Need to revisit this (.validateTLSCertificates(false)) because cert validation is needed for live version
			Document doc = Jsoup.connect("http://www.hotnewhiphop.com/songs/").get();
			vals = doc.getElementsByClass("default-artist"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vals; 
	}
	
	public static Elements getMixtapesDatpiff() {
		Elements vals = new Elements(); 
		Document doc;
		try {
			//Need to revisit this (.validateTLSCertificates(false)) because cert validation is needed for live version
			doc = Jsoup.connect("http://www.datpiff.com/mixtapes").get();
			vals = doc.getElementsByClass("artist"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return vals; 
		
	}
	/* Songs not supported greatly on Datpiff
	public static Elements getSongsDatpiff() {
		Elements vals = new Elements(); 
		try {
			Document doc = Jsoup.connect("http://www.datpiff.com/singles").get();
			//vals = doc.g
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vals; 
	}*/
	
	protected static String getUserEmail() {
		if (CreateAccountWindow.email == null) {
			return LoginWindow.email;
		} else {
			return CreateAccountWindow.email; 
		}
	}
	
	protected static int getUserId() {
		int ret = 0; 
		DbConnect db = new DbConnect(); 
		Connection conn = (Connection) db.Connect(); 
		String email = getUserEmail();
		String query = 	"SELECT ID FROM musicApp.Users where email = (?)";
		try {
			java.sql.PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				ret = rs.getInt(1); 
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ret; 
	}
}
