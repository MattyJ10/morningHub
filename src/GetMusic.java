import java.io.IOException;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class GetMusic {

	public static void main(String[] args) {
		
		new MainWindow();
		DbConnect.main(null);
		
	}
	
	
	public static Elements getMixtapes() {
		Elements vals = new Elements(); 
		try {
			Document doc = Jsoup.connect("http://www.hotnewhiphop.com/mixtapes/").get();
			vals = doc.getElementsByClass("default-artist");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vals; 
	}
	
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
