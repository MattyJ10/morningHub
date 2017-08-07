

import java.io.IOException;

import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class GetMusic {

	public static void main(String[] args) {
		
		//Have successfully been able to confirm if a favorite artist is found on the page. 
		
		/*ArrayList<String> favorite_artists = new ArrayList<String>();
		favorite_artists.add("Meek Mill"); 
		favorite_artists.add("Jay Z"); 
		favorite_artists.add("Tyga"); 
		favorite_artists.add("Lil Uzi Vert"); 
		favorite_artists.add("Travis $cott");
		favorite_artists.add("Logic");
		Elements newMixtapes = getMixtapes();
		Elements newSongs = getSongs(); 
		
		
		//check for mixtapes by favorite artists
		for (Element element: newMixtapes) {
			String artist = element.text(); 
			if (favorite_artists.contains(artist)){
				System.out.println("There is a new mixtape by " + artist); 
				System.out.println("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
				//label.setText("There is a new mixtape by " + artist);
			}
		}
		
		//check for songs by favorite artists
		for (Element element: newSongs) {	
			String artist = element.text(); 
			if (favorite_artists.contains(artist)){
				System.out.println("There is a new song by " + artist); 
				System.out.println("www.HotNewHipHop.com" + element.parent().siblingElements().first().attr("href"));
			}
		}*/
		
		
		Driver frame = new Driver();
		frame.main(args);
		
		
		
		
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
