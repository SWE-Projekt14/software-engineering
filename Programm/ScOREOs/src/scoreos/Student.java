package scoreos;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.json.*;

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
		stGebDatum = new GregorianCalendar(GebJahr, GebMonat, GebTag);
		stKurs = Kurs;
		
		studentJSON.put("_id", getStudentID());
		studentJSON.put("Vorname", stVorName);
		studentJSON.put("Nachname", stNachName);
		studentJSON.put("Geburtsdatum", stGebDatum);
		studentJSON.put("Kurs", stKurs);
		
		JSONObject kurse = new JSONObject();
		studentJSON.put("Kurse", kurse);
	}

	public String getStKurs() {
		return stKurs;
	}

	public void setStKurs(String stKurs) {
		studentJSON.remove("Kurs");
		studentJSON.put("Kurs", stKurs);
		this.stKurs = stKurs;
	}

	public String getStNachName() {
		return stNachName;
	}
	
	public void setStNachName(String stNachname) {
		studentJSON.remove("Nachname");
		studentJSON.put("Nachname", stNachname);
		this.stNachName = stNachname;
	}

	public String getStVorName() {
		return stVorName;
	}
	
	public void setStVorname(String stVorname){
		studentJSON.remove("Vorname");
		studentJSON.put("Vorname", stVorname);
		this.stVorName = stVorname;
	}
	
	public JSONObject getStudentAsJSONObject(){
		return studentJSON;
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
		JSONObject kurseJSON = (JSONObject)studentJSON.get("Kurse");
		JSONObject vorlesungJSON = new JSONObject();
		kurseJSON.put(vorlesungID, vorlesungJSON);
	}
	
	public JSONArray getVorlesungTestate(String vorlesungID){
		JsonObject kurseJson = (JsonObject) studentJSON.get("Kurse");
		return (JSONArray) kurseJson.getJsonArray(vorlesungID);
	}
	
	public void addTestat(String testatID, String testatDokID, String vorlesungID){
		JSONObject kurseJSON = (JSONObject) studentJSON.get("Kurse");
		JSONObject vorlesungJSON = (JSONObject) kurseJSON.get(vorlesungID);
		vorlesungJSON.put(testatID, testatDokID);
	}
	
	public void addStdKurse(){
		
	}
	
	public String getStudentID(){
		StringBuilder sb = new StringBuilder();
		sb.append(stKurs);
		sb.append(stGebDatum.get(Calendar.YEAR));
		sb.append(stGebDatum.get(Calendar.MONTH));
		sb.append(stGebDatum.get(Calendar.DAY_OF_MONTH));
		sb.append(stNachName);
		sb.append(stVorName);
		return sb.toString();
	}
}
