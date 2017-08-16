import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.sql.Connection;
import java.sql.SQLException;



public class GetMusic {

	public static void main(String[] args) {
		
		new MainWindow();
		
	}
	
	//get mixtapes from HNHH
	public static Elements getMixtapesHnHH() {
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
	public static Elements getSongsHnHH() {
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
