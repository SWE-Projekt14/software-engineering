package scoreos;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainClass {

	public static void main(String[] args){
		DataConnection test = new DataConnection("scoreos", "christian", "test123", "http", "127.0.0.1", 5984);
		Calendar heute = new GregorianCalendar(2014,11,6);
		String zwei;
		System.out.println(heute.get(Calendar.YEAR));
		System.out.println(heute.get(Calendar.MONTH));
		System.out.println(heute.get(Calendar.DAY_OF_MONTH));
		test.neueEinzelBewertung("testbewertung", heute, "teststudenteID");
		test.fuegeEinzelH2BewertungHinzu("testbewertung:2014-11-6:teststudenteID", "Aussehen", 20, 1);
		test.fuegeEinzelH2BewertungHinzu("testbewertung:2014-11-6:teststudenteID", "Präsentation", 50, 1);
	}
	
}
