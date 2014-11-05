package scoreos;

import java.util.Calendar;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.DocumentConflictException;

import com.google.gson.JsonObject;

import scoreos.CouchDBProperties;

public class MainClass {

	public static void main(String[] args) {
		ScOREOs connection = new ScOREOs();
		CouchDbProperties properties = new CouchDbProperties();
		CouchDbClient server = new CouchDbClient(
				connection.setConnectionSettings("scoreos", "christian", "test123", "http" ,"127.0.0.1", 5984, properties)
				);
		
		
		JsonObject root = new JsonObject();
		root.addProperty("_id", "Fabi");
		root.addProperty("alter", 70);
		try{
			System.out.println("hallo"+server.save(root));
		}
		catch(DocumentConflictException e){
			System.out.println("_ID schon vergeben, Benutzer schon angelegt");
		}
//		JsonObject jo = server.find(JsonObject.class, "hanswurst");
//		System.out.println(jo.toString());
//		String revnr = jo.get("_rev").toString().replace("\"","");
//		String idnr = jo.get("_id").toString().replace("\"", "");
//		String alter = jo.get("alter").toString().replace("\"","");
//		System.out.println(alter);
//		System.out.println(revnr);
//		server.remove(idnr, revnr);
//		jo.remove("_rev");
//		jo.addProperty("_id", "test");
//		jo.addProperty("alter", 50);
//		jo.addProperty("Name","Hans");
//		server.save(jo);
//		jo = server.find(JsonObject.class, "test");
//		System.out.println(jo.toString());
	}
	
}
