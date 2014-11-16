package scoreos;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

public class MainClass {

	public static void main(String[] args){
		Calendar gebDatum = new GregorianCalendar(1991, 5, 28);
		Student test = new Student();
//		test.createStudent("Alex", "Rezmer", gebDatum, "TINF13IN");
		test.getStudentFromDB("Alex", "Rezmer", gebDatum, "TINF13IN");
		test.setStVorname("Louis");
		test.updateStudentJSON();
		
		test.setStNachName("Steinkampf");
		test.updateStudentJSON();
		System.out.println(test.getStVorName());
	}
	
}
