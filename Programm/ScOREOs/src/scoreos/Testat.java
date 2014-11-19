package scoreos;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lightcouch.CouchDbClient;

public class Testat {
	
	private JSONObject testat = new JSONObject();
	
	public Testat(String tstTitel){
		JSONObject kriterienJSON = new JSONObject();
		JSONObject gesamtBewertung = new JSONObject();
		
		testat.put("Titel", tstTitel);
		testat.put("Bewertungen",kriterienJSON);
		testat.put("Gesamtbewertung", 999999);
		
	}
	
	public void addBewertung(double tstHochGewichtung, boolean tstGutSchlecht, String tstBewertungName){
		JSONObject bewertungenJSON = (JSONObject) testat.get("Bewertungen");
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
	
	public void addGesamtWertung(double gesamtWertung){
		testat.put("Gesamtbewertung", gesamtWertung);
	}
	
	public void addRateBewertung(double impact, double rate, String rateBewName){
		JSONObject alleBewertungen = (JSONObject) testat.get("Bewertungen");
		JSONObject neueBewertung = new JSONObject();
		
		neueBewertung.put("Impact", impact);	// Neue Rate Bewertung in Object schreiben
		neueBewertung.put("Rate", rate);
		neueBewertung.put("Impact", impact);
		alleBewertungen.put(rateBewName, neueBewertung);	//Neue Bewertung zu allen Bewertungen hinzufŸgen
		testat.remove("Bewertungen");	
		testat.put("Bewertungen", alleBewertungen);
		System.out.println(testat);
	}
	
	public void addScoreBewertung(double tstHochGewichtung, double impact, double benchmark, double score, String scoreBewName){
		JSONObject alleBewertungen = (JSONObject) testat.get("Bewertungen");
		JSONObject neueBewertung = new JSONObject();
		
		JSONArray h2BewArray = new JSONArray();
		h2BewArray.add(tstHochGewichtung);
		h2BewArray.add(100-tstHochGewichtung);
		
		neueBewertung.put("H2Bewertungen", h2BewArray);	
		neueBewertung.put("Score", score);
		calcS2R(benchmark, score, impact);
		neueBewertung.put("Rate", calcS2R(benchmark, score, impact));
		neueBewertung.put("Impact", impact);
		
		alleBewertungen.put(scoreBewName, neueBewertung);
		testat.remove("Bewertungen");
		testat.put("Bewertungen", alleBewertungen);
		System.out.println(testat);
	}
	
	public JSONObject getTestatAlsJSON(){
		return testat;
	}
	
	public double getH2Score(double tstHochGewichtung, boolean tstGutSchlecht){
		
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
	
	public void rechneR2Szusammen(){
		JSONObject alleBewertungen = (JSONObject) testat.get("Bewertungen");
		JSONObject aktJSONObject = new JSONObject();
		Iterator<JSONObject> iterator = alleBewertungen.values().iterator();
		int groesse = alleBewertungen.size(), i = 0;
		double[] impactBewArray = new double[groesse];
		double[] rateBewArray = new double[groesse];
		double aktGesWert = Double.parseDouble(testat.get("Gesamtbewertung").toString());
		
		if(aktGesWert != 999999 && aktGesWert != 0){
			while (iterator.hasNext()) {
				 aktJSONObject = iterator.next();
				 impactBewArray[i] = Double.parseDouble(aktJSONObject.get("Impact").toString());
				 rateBewArray[i] = Double.parseDouble(aktJSONObject.get("Rate").toString());
				 aktGesWert = calcAlleR2S(impactBewArray[i], rateBewArray[i], aktGesWert);
				 i++;
				}
		}else{
			System.out.println("Es wurde keine Gesamtbewertung angegeben");
		}
		testat.remove("Gesamtbewertung");
		testat.put("Gesamtbewertung", aktGesWert);
		
	}
	//R2S Bewertung berechnen
	public double calcAlleR2S(double impact, double rate, double scoreAktuell){
		double zaehlerS2R, nennerS2R;
		
		zaehlerS2R =((1- Math.pow(impact*(1-scoreAktuell),rate)));
		nennerS2R =(1-(impact*(1-scoreAktuell)));
			
		return (zaehlerS2R/nennerS2R) * scoreAktuell;
	}
	// S2R Bewertung berechnen
	public double calcS2R(double s0, double s, double d0){
		double refininginversoperant1, refininginversoperant2, refininginverswert = 0;
		
		if(d0==0||s0==0||s0==1)
		{
			refininginverswert = 1;
		}
		
		if(d0 != 0 && s0 !=0 && s0 !=1 && s<(s0/(1-d0*(1-s0))))
		{
			refininginversoperant1 = Math.log(1-(s/s0)*(1-d0*(1-s0)));
			refininginversoperant2 = Math.log(d0*(1-s0));
			refininginverswert = refininginversoperant1 / refininginversoperant2;
		}
		
		if(d0 != 0 && s0 !=0 && s0 !=1 && s>=(s0/(1-d0*(1-s0))))
		{
			System.out.print("FEHLER");
		}
		
		return refininginverswert;
	}
	
	public String getTestatTitel(){
		return testat.get("Titel").toString();
	}
	
}
