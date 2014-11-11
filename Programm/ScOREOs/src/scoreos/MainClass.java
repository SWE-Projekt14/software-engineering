package scoreos;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.lightcouch.CouchDbClient;

public class MainClass {

	public static void main(String[] args){
		CouchDbClient connection = new CouchDbClient("scoreos", false, "http", "127.0.0.1", 5984, null, null);
		Calendar heute = new GregorianCalendar(2014,11,6);
		String zwei;
		System.out.println(heute.get(Calendar.YEAR));
		System.out.println(heute.get(Calendar.MONTH));
		System.out.println(heute.get(Calendar.DAY_OF_MONTH));
		WriteJSON writer = new WriteJSON(connection);
		writer.neueEinzelBewertung("Testbewertung", heute, "testID");
	}
	
}
