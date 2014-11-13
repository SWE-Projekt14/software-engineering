package scoreos;

import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.DocumentConflictException;
import org.lightcouch.Response;

public class WriteJSON {
	
	private CouchDbClient connection;

	public WriteJSON(){
	}
	
	public String neueEinzelBewertung(String bwTitel, Calendar bwDatum, String stID, CouchDbProperties datenbank){
		connection = new CouchDbClient(datenbank);
		JSONObject einzelBewertung = new JSONObject();
		JSONArray bewertungen = new JSONArray();
		
		//Lege ID von Objekt fest "Titel+Datum+Student"
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
			connection.shutdown();
			return(bwTitel+":"+
					bwDatum.get(Calendar.YEAR)+"-"+
					bwDatum.get(Calendar.MONTH)+"-"+
					bwDatum.get(Calendar.DAY_OF_MONTH)+":"+
					stID);
		}
		catch(DocumentConflictException e){
			// Falls Objekt mit ID schon vorhanden ist.
			System.out.println("_ID schon vergeben, Identische Bewertung schon erstellt?");
			connection.shutdown(); //ggf. Object = NULL dann wird es gelöscht vom Garbage Collection
			return("ERROR");
		}
	}
	
	public void fuegeStudentenHinzu(Student[] studenten, CouchDbProperties datenbank){
		
	}
	public void fuegeStudentenHinzu(Student studenten, CouchDbProperties datenbank){
		connection = new CouchDbClient(datenbank);
		
	}
	
	public Response speichereJSONinDB(JSONObject jsonObject, CouchDbProperties properties){
		connection = new CouchDbClient(properties);
		try{
			return connection.save(jsonObject);
		}catch(DocumentConflictException e){
			System.out.println("Fehler beim speichern von JSON Dokument");
			return null;
		}
	}

}
