package scoreos;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.*;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.DocumentConflictException;
import org.lightcouch.NoDocumentException;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

public class Student {
	private JSONObject studentJSON = new JSONObject();
	CouchDbClient connection;
	String revID;
	
	public void createStudent(String stVorName, String stNachName, Calendar stGebDatum, String stKurs){

		studentJSON.put("Vorname", stVorName);
		studentJSON.put("Nachname", stNachName);
		studentJSON.put("Geburtsdatum", getGebDatumString(stGebDatum));
		studentJSON.put("Kurs", stKurs);
		studentJSON.put("_id", getStudentID(stVorName, stNachName, stGebDatum, stKurs));
		
		JSONObject kurse = new JSONObject();
		studentJSON.put("Vorlesungen", kurse);
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
		connection = new CouchDbClient("couchdb.properties");
		String stID = getStudentID(stVorName, stNachName, stGebDatum, stKurs);
		try {
			JSONObject holeJSON = new JSONObject();
			holeJSON = connection.find(JSONObject.class, stID);
			studentJSON = holeJSON;
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
		Object alleVorlesungen = studentJSON.get("Vorlesungen");      
		JSONObject object = (JSONObject) alleVorlesungen;
		JSONObject neu = (JSONObject) object.get(vorlesungID);		
		JSONObject neueVorlesung = new JSONObject();
//		alleVorlesungen.put(vorlesungID, neueVorlesung);
		studentJSON.remove("Vorlesungen");
		studentJSON.put("Vorlesungen", alleVorlesungen);
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
	}
	
	public void addTestat(Testat testat, String vorlesungID){
		connection = new CouchDbClient("couchdb.properties");
		JSONObject alleVorlesungenJSON = (JSONObject) studentJSON.get("Vorlesungen");
		JSONObject vorlesungJSON = (JSONObject) alleVorlesungenJSON.get(vorlesungID);
		
		vorlesungJSON.put(testat.getTestatTitel(), 
				connection.save(testat.getTestatAlsJSON()).getId());
	}
	
	public void writeToDB(){
		connection = new CouchDbClient("couchdb.properties");
		connection.save(studentJSON);
	}
	
	public void updateStudentJSON(){
		connection = new CouchDbClient("couchdb.properties");
		studentJSON.remove("_rev");
		studentJSON.put("_rev", revID);
		try {
			revID = connection.update(studentJSON).getRev();
		} catch (DocumentConflictException e) {
			System.out.println("Object Student wurde bearbeitet während Sie schreiben wollten, es wurde nichts übermittelt.");
		}
	}
}
