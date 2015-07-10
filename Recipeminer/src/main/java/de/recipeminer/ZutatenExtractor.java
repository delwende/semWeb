package de.recipeminer;

import java.util.ArrayList;
import java.util.List;

import de.recipeminer.models.Recipe;
import de.recipeminer.models.Zutaten;

public class ZutatenExtractor {

	List<Zutaten> zutaten = new ArrayList<Zutaten>();

	private static final String name = "\"name\":\"";
	private static final String eigenschaft = "\"eigenschaft\":\"";
	private static final String menge = "\"menge\":\"";
	private static final String einheit = "\"einheit\":\"";
	private static final String menge_berechnet = "\"menge_berechnet\":";

	public ZutatenExtractor() {
		super();
	}

	public void LadeZutaten(List<Recipe> Rezepte) {

		for (Recipe recipe : Rezepte) {
			String[] zutatenstrings = recipe.zutatenstring.split("\\{");
			for (String zutatString : zutatenstrings) {

				try {
					if (!zutatString
							.substring(zutatString.indexOf("\"id\""), 7)
							.contains("\"id\":\"\"")) {

						StringBuffer sb = new StringBuffer(zutatString);
						sb.ensureCapacity(ChefkochAPICrawler.bufferCapGesamtRezeptListe);

						Zutaten zutat = new Zutaten();
						String string;

						//Name
						sb.delete(0, sb.indexOf(name) + name.length());
						string = (sb
								.subSequence(0, sb.indexOf(eigenschaft) - 2))
								.toString();
						zutat.setName(sonderzeichen(string)); 

						//Menge
						sb.delete(0, sb.indexOf(menge) + menge.length());
						string = (sb.subSequence(0, sb.indexOf(einheit) - 2))
								.toString();
						zutat.setMenge(Integer.parseInt(cutMenge(string)));

						//Einheit
						sb.delete(0, sb.indexOf(einheit) + einheit.length());
						string = (sb.subSequence(0,
								sb.indexOf(menge_berechnet) - 2)).toString();
						zutat.setEinheit(sonderzeichen(string));

						einheitenUmrechnen(zutat);

						recipe.getZutaten().add(zutat);
					}
				} catch (StringIndexOutOfBoundsException e) {
				} catch (NumberFormatException e) {
				}
			}
		}

	}

	private String cutMenge(String s) { //Kommazahlen bei Mengen nach Komma abschneiden
		s = s.replaceAll("\\.", "");
		s = s.replaceAll("\"", "");
		s = s.replaceAll("\\,", "");
		int indexOfKomma = s.indexOf(",");
		if (indexOfKomma >= 0) {
			return s.substring(0, indexOfKomma - 1);
		}
		return s;
	}

	public static String sonderzeichen(String s) { //Sonderzeichen-Darstellung korrigieren
		s = s.replaceAll("\\\\u00c4", "Ä");
		s = s.replaceAll("\\\\u00e4", "ä");
		s = s.replaceAll("\\\\u00dc", "Ü");
		s = s.replaceAll("\\\\u00fc", "ü");
		s = s.replaceAll("\\\\u00d6", "Ö");
		s = s.replaceAll("\\\\u00f6", "ö");
		s = s.replaceAll("\\\\u00df", "ß");
		s = s.replaceAll("\\\\u0026", "&");
		s = s.replaceAll("\\\\u0025", "%");
		return s;
	}

	public static void einheitenUmrechnen(Zutaten zutat) { //Einheiten in Gramm umrechnen
		String einheit = zutat.getEinheit();
		int menge = zutat.getMenge();
		switch (einheit) {
		case ("g"):
			zutat.setMenge(menge * 1);
			zutat.setEinheit("g");
			break;
		case ("ml"):
			zutat.setMenge(menge * 1);
			zutat.setEinheit("g");
			break;
		case ("kg"):
			zutat.setMenge(menge * 1000);
			zutat.setEinheit("g");
			break;
		case ("TL"):
			zutat.setMenge(menge * 4);
			zutat.setEinheit("g");
			break;
		case ("TL, gestr."):
			zutat.setMenge(menge * 3);
			zutat.setEinheit("g");
			break;
		case ("EL"):
			zutat.setMenge(menge * 12);
			zutat.setEinheit("g");
			break;
		case ("cl"):
			zutat.setMenge(menge * 10);
			zutat.setEinheit("g");
			break;
		case ("Tasse"):
			zutat.setMenge(menge * 200);
			zutat.setEinheit("g");
			break;
		case ("Liter"):
			zutat.setMenge(menge * 1000);
			zutat.setEinheit("g");
			break;
		case ("Tafel"):
			zutat.setMenge(menge * 100);
			zutat.setEinheit("g");
			break;
		default: //Unbekannte Einheiten auf 0g setzen
			zutat.setMenge(0);
			zutat.setEinheit("g");
		}
	}

}
