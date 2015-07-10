package de.recipeminer.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Recipe {
	LinkedList<Ingredient> inglist;
	private String instructionText;
	private String instructionShortText;
	private String name;
	private String name2;
	private String id;
	private String showId;
	public String zutatenstring; 
	public List<Zutaten> zutaten = new ArrayList<Zutaten>();

	private int portionszahl = 0;
	private int zeit_zubereitung = 0;
	private int zeit_vorbereitung = 0;
	private int zeit_ruhen = 0;

	public Recipe() {
		id = "0";
		showId = "0";
		name = "";
		name2 = "";
		instructionText = "";
		instructionShortText = "";
		zutatenstring = "";
		inglist = new LinkedList<Ingredient>();
		this.zutaten = new ArrayList<Zutaten>();
	}

	public void addIngredient(Ingredient ing) {
		inglist.add(ing);
	}

	public LinkedList getIngredientList() {
		return inglist;
	}

	public String toString() {
		int gesamtMenge = berechneGesamtmenge();

		String output = "";
		//Ausgabe im RDF-Format
		output += "<owl:NamedIndividual rdf:about=\"&semWeb;" + (name.replace(" ", "_")) + "\">" + "\n";
		output += "<rdf:type rdf:resource=\"&semWeb;Rezept\"/>" + "\n";
		output += "<RezeptID rdf:datatype=\"&xsd;integer\">" + showId  + "</RezeptID>" + "\n";
		output += "<Gesamtmenge rdf:datatype=\"&xsd;integer\">" + gesamtMenge + "</Gesamtmenge>" + "\n";
		for (Zutaten zutat : zutaten) {
			output += "<hatKonkreteZutat rdf:resource=\"&semWeb;konkreteZutat" + Integer.toString(zutat.getId()) + "\"/>" + "\n";
		}
		output += "</owl:NamedIndividual>" + "\n";

		return output;

	}

	private int berechneGesamtmenge() {
		int gesamtMenge = 0;
		for (Zutaten zutat : zutaten) {
			gesamtMenge += zutat.getMenge();
		}
		return gesamtMenge;
	}

	public void setPortionszahl(String str) {
		setPortionszahl(Integer.parseInt(str));
	}

	public void setPortionszahl(int i) {
		portionszahl = i;
	}

	public int getPortionszahl() {
		return portionszahl;
	}

	public void setId(String i) {
		id = i;
	}

	public String getId() {
		return id;
	}

	public void setShowId(String i) {
		showId = i;
	}

	public String getShowId() {
		return showId;
	}

	public void setName(String str) {
		name = str;
	}

	public String Name() {
		return name;
	}

	public void setName2(String str) {
		name2 = str;
	}

	public String Name2() {
		return name2;
	}

	public void setInstructionText(String str) {
		instructionText = str;
	}

	public String getInstructionText() {
		return instructionText;
	}

	public void setInstructionShortText(String str) {
		instructionShortText = str;
	}

	public String getInstructionShortText() {
		return instructionShortText;
	}

	public int getZeit_zubereitung() {
		return zeit_zubereitung;
	}

	public void setZeit_zubereitung(int z) {
		zeit_zubereitung = z;
	}

	public void setZeit_zubereitung(String s) {
		if (s != "")
			setZeit_zubereitung(Integer.parseInt(s));
	}

	public int getZeit_vorbereitung() {
		return zeit_vorbereitung;
	}

	public void setZeit_ruhen(int z) {
		zeit_vorbereitung = z;
	}

	public void setZeit_ruhen(String s) {
		if (s != "")
			setZeit_ruhen(Integer.parseInt(s));
	}

	public int getZeit_ruhen() {
		return zeit_ruhen;
	}

	public void setZeit_vorbereitung(int z) {
		zeit_ruhen = z;
	}

	public void setZeit_vorbereitung(String s) {
		if (s != "")
			setZeit_vorbereitung(Integer.parseInt(s));
	}

	public List<Zutaten> getZutaten() {
		return zutaten;
	}

	public void setZutaten(List<Zutaten> zutaten) {
		this.zutaten = zutaten;
	}

}
