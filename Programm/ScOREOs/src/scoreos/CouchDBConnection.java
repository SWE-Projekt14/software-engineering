package scoreos;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;

import com.google.gson.JsonObject;

public class CouchDBConnection {

	public static void main(String[] args) {
		CouchDbProperties properties = new CouchDbProperties()
		  .setDbName("scoreos")
		  .setCreateDbIfNotExist(true)
		  .setProtocol("http")
		  .setHost("127.0.0.1")
		  .setUsername("christian")
		  .setPassword("test123")
		  .setPort(5984)
		  .setMaxConnections(100)
		  .setConnectionTimeout(0);
		
		CouchDbClient server = new CouchDbClient(properties);
		JsonObject root = new JsonObject();
		root.addProperty("_id", "Fabi");
		root.addProperty("alter", 70);
		server.save(root);
		
		JsonObject jo = server.find(JsonObject.class, "hanswurst");
		System.out.println(jo.toString());
		String revnr = jo.get("_rev").toString().replace("\"","");
		String idnr = jo.get("_id").toString().replace("\"", "");
		String alter = jo.get("alter").toString().replace("\"","");
		System.out.println(alter);
		System.out.println(revnr);
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
