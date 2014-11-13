package scoreos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;

public class Testat {
	
	private JSONObject testat = new JSONObject();
	
	public Testat(String tstTitel){
		JSONObject kriterienJSON = new JSONObject();
		JSONObject gesamtBewertung = new JSONObject();
		
		testat.put("Titel", tstTitel);
		testat.put("Bewertung",kriterienJSON);
		testat.put("Gesamtwertung", gesamtBewertung);
		
	}
	
	public void addBewertung(double tstHochGewichtung, boolean tstGutSchlecht, String tstBewertungName){
		JSONObject bewertungenJSON = (JSONObject) testat.get("Bewertung");
		JSONObject neueBewertungJSON = new JSONObject();
		JSONArray gewichtungArray = new JSONArray();
		
		if(tstHochGewichtung >= 50){
			gewichtungArray.add(0, tstHochGewichtung);		// Höhere Bewertung
			gewichtungArray.add(1, 100.0-tstHochGewichtung);
		}else{
			gewichtungArray.add(0,100.0-tstHochGewichtung); 	// Höhere Bewertung
			gewichtungArray.add(1,tstHochGewichtung);	
		}
		
		neueBewertungJSON.put("Gewichtung", gewichtungArray);
		neueBewertungJSON.put("BewertungErgebnis", tstGutSchlecht);
		neueBewertungJSON.put("H2Ergebnis", getH2Bewertung(tstHochGewichtung, tstGutSchlecht));
		bewertungenJSON.put(tstBewertungName, neueBewertungJSON);
		System.out.println(bewertungenJSON.toString());
	}
	
	private double getH2Bewertung(double tstHochGewichtung, boolean tstGutSchlecht){
		if(tstGutSchlecht == true){
			if(tstHochGewichtung >= 50){
				return tstHochGewichtung/100.0;
			}else{
				return (100.0-tstHochGewichtung)/100.0;
			}
		}else 
			if(tstHochGewichtung < 50){
				return tstHochGewichtung/100.0;
			}else{
				return (100.0-tstHochGewichtung)/100.0;
			}
	}
	
	public JSONObject getTestatAlsJSON(){
		return testat;
	}
	
	public String getTestatTitel(){
		return testat.get("Titel").toString();
	}
}
