package scoreos;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.json.*;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.NoDocumentException;

public class Student {
	private JSONObject studentJSON = new JSONObject();
	private CouchDbClient connection;
	private CouchDbProperties properties;
	
	public Student(String stVorName, String stNachName, int GebTag, int GebMonat, int GebJahr, 
					String stKurs, CouchDbProperties dbInfos){
		
		properties = dbInfos;
		
		StringBuilder stGebDatum = new StringBuilder();
		stGebDatum.append(GebJahr);
		stGebDatum.append("-");
		stGebDatum.append(GebMonat);
		stGebDatum.append("-");
		stGebDatum.append(GebTag);
		
		studentJSON.put("Vorname", stVorName);
		studentJSON.put("Nachname", stNachName);
		studentJSON.put("Geburtsdatum", stGebDatum);
		studentJSON.put("Kurs", stKurs);
		studentJSON.put("_id", getStudentID());
		
		JSONObject kurse = new JSONObject();
		studentJSON.put("Vorlesungen", kurse);
		saveStudentJSON();
	}

	public String getStudentID(){
		StringBuilder sb = new StringBuilder();
		sb.append(studentJSON.get("Kurs"));
		sb.append(studentJSON.get("Geburtsdatum"));
		sb.append(studentJSON.get("Nachname").toString());
		sb.append(studentJSON.get("Vorname").toString());
		return sb.toString();
	}

	public void setStVorname(String stVorname){
		studentJSON.remove("Vorname");
		studentJSON.put("Vorname", stVorname);
	}

	public String getStVorName() {
		return studentJSON.get("Vorname").toString();
	}

	public void setStNachName(String stNachname) {
		studentJSON.remove("Nachname");
		studentJSON.put("Nachname", stNachname);
	}

	public String getStNachName() {
		return studentJSON.get("Nachname").toString();
	}

	public String getGebDatum(){
		return studentJSON.get("Geburtsdatum").toString();
	}

	public void setStKurs(String stKurs) {
		studentJSON.remove("Kurs");
		studentJSON.put("Kurs", stKurs);
	}

	public String getStKurs() {
		return studentJSON.get("Kurs").toString();
	}

	public void addVorlesung(String vorlesungID){
		JSONObject kurseJSON = (JSONObject)studentJSON.get("Vorlesungen");
		JSONObject vorlesungJSON = new JSONObject();
		kurseJSON.put(vorlesungID, vorlesungJSON);
	}

	public JSONObject getAlleVorlesungen(){
		return (JSONObject) studentJSON.get("Vorlesungen");
	}

	public JSONObject getStudentAsJSONObject(){
		return studentJSON;
	}
	
	public String getReversionNummer(){
		return studentJSON.get("_rev").toString();
	}

	public void getTestateVorlesung(String vorlesungID){
		JSONObject vorlesungenJSON = (JSONObject) studentJSON.get("Vorlesungen");
		Iterator<JSONObject> iterator = vorlesungenJSON.values().iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next().get(vorlesungID));
			
		}
//		return (JSONArray) kurseJson.getJsonArray(vorlesungID);
	}
	
	public void addTestat(Testat testat, String vorlesungID){
		JSONObject alleVorlesungenJSON = (JSONObject) studentJSON.get("Vorlesungen");
		WriteJSON connection = new WriteJSON();
		JSONObject vorlesungJSON = (JSONObject) alleVorlesungenJSON.get(vorlesungID);
		
		vorlesungJSON.put(testat.getTestatTitel(), 
				connection.speichereJSONinDB(testat.getTestatAlsJSON(), properties).getId());
	}
	
	public void addStdKurse(){
		
	}
	
	private void saveStudentJSON(){
		WriteJSON connection = new WriteJSON();
		connection.speichereJSONinDB(studentJSON, properties);
	}
}
