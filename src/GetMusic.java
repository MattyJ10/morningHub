import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.sql.Connection;



public class GetMusic {

	public static void main(String[] args) {
		
		new MainWindow();
		DbConnect db = new DbConnect(); 
		Connection conn = db.Connect();
		
	}
	
	//get mixtapes from HNHH
	public static Elements getMixtapesHNHH() {
		Elements vals = new Elements(); 
		try {
			Document doc = Jsoup.connect("http://www.hotnewhiphop.com/mixtapes/").get();
			vals = doc.getElementsByClass("default-artist");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vals; 
	}
	
	//get songs from HNHH
	public static Elements getSongs() {
		Elements vals = new Elements(); 
		try {
			Document doc = Jsoup.connect("http://www.hotnewhiphop.com/songs/").get();
			vals = doc.getElementsByClass("default-artist"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vals; 
	}
}
