import java.io.IOException;
import java.sql.SQLException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class GetMusic {

	public static void main(String[] args) throws SQLException {
		getMixtapesDatpiff(); 
		new MainWindow();
		
		
	}
	
	//get mixtapes from HNHH
	public static Elements getMixtapesHnHH() {
		Elements vals = new Elements(); 
		try {
			//Need to revisit this (.validateTLSCertificates(false)) because cert validation is needed for live version
			Document doc = Jsoup.connect("http://www.hotnewhiphop.com/mixtapes/").validateTLSCertificates(false).get();
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
			Document doc = Jsoup.connect("http://www.hotnewhiphop.com/songs/").validateTLSCertificates(false).get();
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
			doc = Jsoup.connect("http://www.datpiff.com/mixtapes").validateTLSCertificates(false).get();
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
}
