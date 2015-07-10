package de.recipeminer.models;

public class Zutaten {

	private String name;
	private int menge;
	private String einheit;
	private int id;
	private static int zaehler = 0;

	public Zutaten(String name, int menge, String einheit) {
		super();
		this.name = name;
		this.menge = menge;
		this.einheit = einheit;
		this.id = ++zaehler; //Für die Nummerierung der konkreten Zutaten
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	public String getEinheit() {
		return einheit;
	}

	public void setEinheit(String einheit) {
		this.einheit = einheit;
	}
	
	public String toString() {
		
		String output = "";
		//Ausgabe im RDF-Format
		output += "<owl:NamedIndividual rdf:about=\"&semWeb;konkreteZutat" + id + "\">" + "\n";
		output += "<rdf:type rdf:resource=\"&semWeb;_Zutat\"/>" + "\n";
		output += "<Menge rdf:datatype=\"&xsd;integer\">" + menge  + "</Menge>" + "\n";
		output += "<Einheit rdf:datatype=\"&xsd;string\">" + einheit  + "</Einheit>" + "\n";
		output += "<hatZutat rdf:resource=\"&semWeb;" + name + "\"/>" + "\n";
		output += "</owl:NamedIndividual>" + "\n";
		
		return output;
	}

	public Zutaten() {
		super();
		this.id = ++zaehler;
	}

}
