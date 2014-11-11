package scoreos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.DocumentConflictException;
import org.lightcouch.NoDocumentException;
import org.lightcouch.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


public class DataConnection {
	
	private CouchDbProperties properties = new CouchDbProperties();
	private CouchDbClient connection;
	
	public DataConnection(	String dbName,
						String dbUserName,
						String dbUserPasswd,
						String dbProtocol,
						String dbHost,
						int dbPort){
		
		properties.setDbName(dbName)
		.setUsername(dbUserName)
		.setPassword(dbUserPasswd)
		.setProtocol(dbProtocol)
		.setHost(dbHost)
		.setPort(dbPort)
		.setCreateDbIfNotExist(true)
		.setConnectionTimeout(0)
		.setMaxConnections(100);
		
		connection = new CouchDbClient(properties);
	}
	
	public String getBwertungsID(String bwTitel, Calendar bwDatum, String stID){
		return(bwTitel+":"+
				bwDatum.get(Calendar.YEAR)+"-"+
				bwDatum.get(Calendar.MONTH)+"-"+
				bwDatum.get(Calendar.DAY_OF_MONTH)+":"+
				stID);
	}
	
	//Fügt eine neue Einzelbewertung hinzu diese muss dann noch mit unterschiedlichen Skalaren belegt werden (H2)
	public String neueEinzelBewertung(String bwTitel, Calendar bwDatum, String stID){
		JSONObject einzelBewertung = new JSONObject();
		JSONArray bewertungen = new JSONArray();
		
		einzelBewertung.put("_id", 	bwTitel+":"+
											bwDatum.get(Calendar.YEAR)+"-"+
											bwDatum.get(Calendar.MONTH)+"-"+
											bwDatum.get(Calendar.DAY_OF_MONTH)+":"+
											stID);
		einzelBewertung.put("BewertungTitel", bwTitel);
		einzelBewertung.put("Datum",bwDatum.get(Calendar.YEAR)+"-"+
											bwDatum.get(Calendar.MONTH)+"-"+
											bwDatum.get(Calendar.DAY_OF_MONTH));
		einzelBewertung.put("Bewertungen", bewertungen);
		
		try{
			connection.save(einzelBewertung);
			return(bwTitel+":"+
					bwDatum.get(Calendar.YEAR)+"-"+
					bwDatum.get(Calendar.MONTH)+"-"+
					bwDatum.get(Calendar.DAY_OF_MONTH)+":"+
					stID);
		}
		catch(DocumentConflictException e){
			// Falls Objekt mit ID schon vorhanden ist.
			System.out.println("_ID schon vergeben, Identische Bewertung schon erstellt?");
			return("ERROR");
		}
	}
	
	// Neue H2 Bewertung zu bestehender Bewertung hinzufügen
	public void fuegeEinzelH2BewertungHinzu(String bwID, String bwBeschreibung, int bwGewichtung, int bwBewertung){
		JSONObject holeBewertung = new JSONObject();
		JSONObject neueH2Bewertung = new JSONObject();
		JSONArray h2Gewichtung = new JSONArray();
		JSONArray arrayBew;
		JSONObject bearbeiteBew = new JSONObject();
		
		String h2BewertungID, test;
		Response response;
		
		//Neue H2 Bewertung in JSONObject schreiben
			//Gewichtung ausrechnen und in Array schreiben
		h2Gewichtung.add(bwGewichtung);
		h2Gewichtung.add(100-bwGewichtung);
			//Beschreibung, Gewichtungen[] und Beschreibung hinzufügen
		neueH2Bewertung.put("Beschreibung", bwBeschreibung);
		neueH2Bewertung.put("Gewichtung", h2Gewichtung);
		neueH2Bewertung.put("Bewertung", bwBewertung);
		
		//Speichere neue H2 Bewertung und bekomme Response
		try{
			response = connection.save(neueH2Bewertung);
			h2BewertungID = response.getId(); 													// Lese ID aus response aus
			
			//Neue H2 Bewertung bestehender Bewertung hinzufügen
			try{
				holeBewertung = connection.find(JSONObject.class, bwID);						// Hole Dokument mit Bewertung (z.B. Vortrag)
				bearbeiteBew.put(0, holeBewertung.get("Bewertungen"));
				System.out.println(bearbeiteBew);
				//				System.out.println(arrayBew.toString());										// Array mit Bewertungen aus Dokument extrahieren
				
																	// Neue ID von H2 Bewertung in Dokument hinzufügen													
				holeBewertung.remove("Bewertungen");											// Altes Array aus JSONObject löschen
				holeBewertung.put("Bewertungen", arrayBew);										// Neues Array mit IDs von H2 Bewertungen speichern
				connection.update(holeBewertung);												// Dokument aktualisieren	
			}
			catch(NoDocumentException e){
				System.out.println("Kein Dokument mit "+ bwID+" gefunden."); 					// Wenn kein Dokument gefunden wurde
			}
		}
		catch(DocumentConflictException e){
			System.out.println("Konnte keine H2 Bewertung erstellen, Identische Bewertung bereits erstellt?");
		}
		
	}
	
}
