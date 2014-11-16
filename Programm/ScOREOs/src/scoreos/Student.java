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
import org.lightcouch.DocumentConflictException;
import org.lightcouch.NoDocumentException;

public class Student {
	private JSONObject studentJSON = new JSONObject();
	private CouchDbClient connection = new CouchDbClient("couchdb.properties");
	String revID;
	
	public void createStudent(String stVorName, String stNachName, Calendar stGebDatum, String stKurs){

		studentJSON.put("Vorname", stVorName);
		studentJSON.put("Nachname", stNachName);
		studentJSON.put("Geburtsdatum", getGebDatumString(stGebDatum));
		studentJSON.put("Kurs", stKurs);
		studentJSON.put("_id", getStudentID(stVorName, stNachName, stGebDatum, stKurs));
		
		JSONObject kurse = new JSONObject();
		studentJSON.put("Vorlesungen", kurse);
		revID = connection.save(studentJSON).getRev();
	}

	public String getStudentID(String stVorName, String stNachName, Calendar stGebDatum, String stKurs){
		StringBuilder sb = new StringBuilder();
		sb.append(stKurs);
		sb.append(getGebDatumString(stGebDatum));
		sb.append(stNachName);
		sb.append(stVorName);
		return sb.toString();
	}
	
	public void getStudentFromDB(String stVorName, String stNachName, Calendar stGebDatum, String stKurs){
		String stID = getStudentID(stVorName, stNachName, stGebDatum, stKurs);
		try {
			studentJSON = connection.find(JSONObject.class, stID);
			revID = studentJSON.get("_rev").toString();
		} catch (NoDocumentException e) {
			System.out.println("Kein Student mit "+stID+" vorhanden");
		}
	}
	
	private String getGebDatumString(Calendar stGebDatum){
		StringBuilder stringDatum = new StringBuilder();
		stringDatum.append(stGebDatum.get(Calendar.YEAR));
		stringDatum.append("-");
		stringDatum.append(stGebDatum.get(Calendar.MONTH));
		stringDatum.append("-");
		stringDatum.append(stGebDatum.get(Calendar.DAY_OF_MONTH));
		return stringDatum.toString();
	}

	public void setStVorname(String stVorname){
		studentJSON.remove("Vorname"); // Wenn Name geändert wird muss auch ID geändert werden
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
		JSONObject vorlesungJSON = (JSONObject) alleVorlesungenJSON.get(vorlesungID);
		
		vorlesungJSON.put(testat.getTestatTitel(), 
				connection.save(testat.getTestatAlsJSON()).getId());
	}
	
	public void addStdKurse(){
		
	}
	
	public void updateStudentJSON(){
		studentJSON.remove("_rev");
		studentJSON.put("_rev", revID);
		try {
			revID = connection.update(studentJSON).getRev();
		} catch (DocumentConflictException e) {
			System.out.println("Object Student wurde bearbeitet während Sie schreiben wollten, es wurde nichts übermittelt.");
		}
	}
}
