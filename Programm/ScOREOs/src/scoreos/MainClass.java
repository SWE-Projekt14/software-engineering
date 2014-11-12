package scoreos;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.lightcouch.CouchDbClient;

public class MainClass {

	public static void main(String[] args){

		Student test = new Student("Christian", "Gmeiner", 28, 8, 1994, "TINF13IN");
		System.out.println(test.getStudentAsJSONObject().toString());
	}
	
}
