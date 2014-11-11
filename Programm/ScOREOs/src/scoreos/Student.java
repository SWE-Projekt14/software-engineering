package scoreos;

import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Student {
	private String stVorName;
	private String stNachName;
	private Calendar stGebDatum;
	private String stKurs;
	private JSONObject studentJSON = new JSONObject();
	
	public Student(String Vorname, String Nachname, int GebTag, int GebMonat, int GebJahr, 
					String Kurs){
		
		stVorName = Vorname;
		stNachName = Nachname;
		stGebDatum.set(GebJahr, GebMonat, GebTag);
		
		studentJSON.put("_id", 	stKurs+":"+stNachName+":"+stVorName+":"+stGebDatum);
		studentJSON.put("Vorname", stVorName);
		studentJSON.put("Nachnam", stNachName);
		studentJSON.put("Geburtsdatum", stGebDatum);
		studentJSON.put("Kurs", stKurs);
		
		JSONObject kurse = new JSONObject();
		studentJSON.put("Kurse", kurse);
	}

	public String getStKurs() {
		return stKurs;
	}

	public void setStKurs(String stKurs) {
		this.stKurs = stKurs;
	}

	public String getStNachName() {
		return stNachName;
	}

	public String getStVorName() {
		return stVorName;
	}
	
	public String getGebDatum(){
		StringBuilder sb = new StringBuilder();
		sb.append(stGebDatum.get(Calendar.YEAR));
		sb.append("-");
		sb.append(stGebDatum.get(Calendar.MONTH));
		sb.append("-");
		sb.append(stGebDatum.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sb.toString();
	}
	
	public void addVorlesung(String vorlesungID){
		JSONObject vorlesung = new JSONObject();
		studentJSON.put(vorlesungID, vorlesung);
	}
	
	public void addStdKurse(){
		
	}
}
