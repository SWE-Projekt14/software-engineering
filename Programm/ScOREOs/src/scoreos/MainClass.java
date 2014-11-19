package scoreos;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

public class MainClass {

	public static void main(String[] args){
		Calendar gebDatum = new GregorianCalendar(1991, 5, 28);
		Student student1 = new Student();
		student1.getStudentFromDB("Alexander", "Rezmer", gebDatum, "TINF13IN");
		student1.setStNachName("Gmeiner");
		student1.addVorlesung("Mathe");
//		student1.createStudent("Alexander", "Rezmer", gebDatum, "TINF13IN");
//		student1.addVorlesung("Software Engineering");
//		Testat test = new Testat("Vortrag");
//		test.addRateBewertung(0.8, 4, "Aussehen");
//		test.addScoreBewertung(80, 0.8, 0.8, 0.7, "Folien");
//		test.addRateBewertung(0.85, 7, "Schei§e");
//		test.addScoreBewertung(90, 0.9, 0.5, 0.7, "Zwei");
//		test.addGesamtWertung(0.8);
//		test.rechneR2Szusammen();
//		student1.addTestat(test, "Software Engineering");
		
//		student1.writeToDB();
		student1.updateStudentJSON();
	}
	
}
