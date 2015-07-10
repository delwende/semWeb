package de.recipeminer;

import de.recipeminer.ChefkochAPICrawler;
import de.recipeminer.ChefkochJSONRecipeExtractor;
import de.recipeminer.models.Recipe;
import de.recipeminer.models.Zutaten;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


 // Kontrollklasse mit main-Methode

public class Control {

	public static int debuggingmode = 0;

	public final static String suche = "http://api.chefkoch.de/api/1.0/api-recipe-search.php?Suchbegriff="; //Link zur Chefkoch API 
	public final static String limit = "&limit=100"; //Limit für die Anzahl an Rezepten die ausgegeben werden

	static List<Recipe> list = new ArrayList<Recipe>();

	static ChefkochAPICrawler capi = new ChefkochAPICrawler();
	static ChefkochJSONRecipeExtractor rece = new ChefkochJSONRecipeExtractor(
			capi);

	public static void main(String[] args) throws IOException,
			InterruptedException {
		
		// Instanziiere den ChefkochApiCrawler, einmal Suche starten

		int seitenzahl = 1;
		boolean cancel = false;
		boolean warteAufEingabe = true;
		String s = "";
		while (cancel == false) {

			warteAufEingabe = true;

			while (warteAufEingabe == true) { //Auswahlmenü zur Eingabe, Anzeige in der Console
				try {
					BufferedReader reader;
					reader = new BufferedReader(
							new InputStreamReader(System.in));

					System.out.println("\nJetzt " + list.size() + " Rezepte in Liste.");
					System.out.println("\nSuchbegriff weitere Rezepte? 0 - Abbruch. x - Suchbegriff. ");

					s = reader.readLine();

					warteAufEingabe = false;
					if (s.startsWith("0")) { //Eingabe "0" gibt die Rezepte ind er Liste aus und beendet das Programm
						cancel = true;
						listeSpeichern();
						return;
					} else {
						if (s.contains("\n"))
							s.replaceAll("\n", "");
					}
				} catch (NumberFormatException e) {
					System.out.println(e.getMessage() + "\nFalsche Eingabe - bitte nur 0 oder 1 eingeben.");
					e.printStackTrace();
					warteAufEingabe = true;
				}

			} // end of while der Eingabeschleife

			seiteCrawlenUndExtrahieren(s); //Suche nach eingegebenen Begriff wird gestartet

		} // end of while der Hauptschleife
	} // end of main

	public static void seiteCrawlenUndExtrahieren(String catchword) {
		String s = "";

		try {
			capi.setBufferSize(ChefkochAPICrawler.bufferCapGesamtRezeptListe);
			int length = list.size();

			s = capi.crawl(suche + catchword + limit); //Suchlink wird mit dem eingegebenen Suchbegriff und Limit aufgebaut
		} catch (IOException ioe) {
			System.out.println("Fehler beim Herunterladen der Rezepte. \nFehlernachricht: " + ioe.getMessage());
		}
		if (Control.debuggingmode == 1)
			System.out.print(s);

		try {
			list.addAll(rece.extractRecipes(s)); //Rezepte der Liste hinzufügen
		} catch (IllegalArgumentException ioe) {
			System.out.println("Fehler beim Extrahieren der Rezepte. \nFehlernachricht: " + ioe.getMessage());
			ioe.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (Control.debuggingmode == 1) {
			for (int i1 = 0; i1 < list.size(); i1++) {
				System.out.println(list.get(i1).toString());
			}
			System.out.println("\nVerarbeitete Rezepte:\t" + list.size());
		}
	}

	public static void listeSpeichern() {
		//Abschließen der Operation.

		ZutatenExtractor zutatenExtractor = new ZutatenExtractor();
		zutatenExtractor.LadeZutaten(list);

		PrintWriter pWriter = null;
		try {
			pWriter = new PrintWriter(new BufferedWriter(new FileWriter("rezepte.txt", false))); //Ausgabe in TXT-File
			for (Recipe recipe : list) { //die gespeicherte Liste wird in die Datei geschrieben 
				pWriter.println(recipe);
				for (Zutaten zutat : recipe.getZutaten()) {
					pWriter.println(zutat);
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (pWriter != null) {
				pWriter.flush();
				pWriter.close();
			}
		}
	}

}
