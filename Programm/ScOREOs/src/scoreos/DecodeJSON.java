package scoreos;

import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.NoDocumentException;

public class DecodeJSON {

	private CouchDbClient connection;

	public DecodeJSON(){
	
	}

	public Student holeJSONStudent(String stID, CouchDbProperties dbName){
		connection = new CouchDbClient(dbName);
		JSONObject studentJSON = new JSONObject();
		
		try{
			studentJSON = connection.find(JSONObject.class, stID);
			Student retStudent = new Student(studentJSON.get(""), Nachname, GebTag, GebMonat, GebJahr, Kurs)
			return ;
		}
		catch(NoDocumentException ndE){
			
			connection.shutdown();
			System.out.println("Kein Datensatz mit dieser ID gefunden");
			return null;
		}
		
	}
	
	public Testat holeJSONTestat(CouchDbProperties dbName){
		connection = new CouchDbClient(dbName);
	}

}