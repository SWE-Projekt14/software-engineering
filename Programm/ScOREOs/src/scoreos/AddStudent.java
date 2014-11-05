package scoreos;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.DocumentConflictException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class AddStudent {
	private CouchDbClient connection;
	
	public AddStudent(CouchDbProperties dbProperties, CouchDbClient Client){
		connection = new CouchDbClient(dbProperties);
	}
	
	public boolean addStudent(	String stVorName,
							String stNachName,
							Calendar stGeburtstag){
		
		JsonObject student = new JsonObject();
		student.addProperty("_id", 	stGeburtstag.get(Calendar.DAY_OF_MONTH)+
									stGeburtstag.get(Calendar.MONTH)+
									stGeburtstag.get(Calendar.YEAR)+":"
									+stVorName+":"
									+stNachName);
		student.addProperty("Vorname", stVorName);
		student.addProperty("Nachname", stNachName);
		
		Map<String, Object> gebDatum = new HashMap<String, Object>();
			gebDatum.put("Tag", stGeburtstag.get(Calendar.DAY_OF_MONTH));
			gebDatum.put("Monat", stGeburtstag.get(Calendar.MONTH));
			gebDatum.put("Jahr", stGeburtstag.get(Calendar.YEAR));
			
			JsonObject json=new JsonObject();
			  json.put(nameKey,new JsonString(name));
			  json.put("test, "test");
			  JsonArray tagArray= getJSONMapArray(tags);
			  JSONArray infoArray=getJSONMapArray(infos);
			  tagArray.set(tagArray.size(),getJSONCategories());
			  json.put(infoKey,infoArray);
			  json.put(tagsKey,tagArray);
			
			
		try{
			System.out.println(connection.save(student));
			
		}
		catch(DocumentConflictException e){
			System.out.println("Student konnte nicht angelegt werden.");
			return false;
		}
		connection.shutdown();
		return true;
	}
	
	public void addStudentOrt(	String stID, 
								String stWohnOrt, 
								int stPLZ, 
								String stStrasse, 
								int stHausNumemr){
		CouchDbClient connection = new CouchDbClient(properties);
		Map <String, Object> wohnort = new HashMap<String, Object>();
	}
}
