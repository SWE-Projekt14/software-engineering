package scoreos;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

public class MainClass {

	public static void main(String[] args){
		CouchDbProperties properties = new CouchDbProperties("scoreos", false, "http", "127.0.0.1", 5984, null, null);
		
		Student test = new Student("Christian", "Gmeiner", 28, 8, 1994, "TINF13IN");
		System.out.println(test.getStudentAsJSONObject());
		System.out.println(test.getStKurs().toString());
		test.addVorlesung("Software Engineering");
		test.addVorlesung("Mathematik");
		Testat eins = new Testat("Programm");
		eins.addBewertung(98, true, "Code");
		eins.addBewertung(30, false, "Umgebung");
		Testat zwei = new Testat("Pflichtenheft");
		zwei.addBewertung(80, true, "Cover");
		zwei.addBewertung(60, false, "Inhalt");
		test.addTestat(zwei, "Mathematik", properties);
		test.addTestat(eins, "Software Engineering", properties);
	}
	
}
