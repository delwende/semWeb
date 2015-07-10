package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class CsvToRdfSport {
 
  public static void main(String[] args) {
 
	CsvToRdfSport obj = new CsvToRdfSport();
	obj.runSport();
 
  }
   
  public void runSport() {
 
	String csvFile = "D:\\OneDrive\\leipzig\\htwk\\master\\2.semester\\semantic_web-riechert\\projekt\\projekt-anne\\sport\\11-sport.csv"; //Dateipfad
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ";"; //Trennzeichen Semikolon
 
	try {
 
		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) { //zeilenweise lesen
 
			String[] wert = line.split(cvsSplitBy);
			//Ausgabe im RDF-Format
			System.out.println("<owl:NamedIndividual rdf:about=\"&semWeb;" + (wert[0].replace(" ", "_").replace("\"", "")) + "\">");
			System.out.println("<rdf:type rdf:resource=\"&semWeb;Sportart\"/>");
			System.out.println("<MET rdf:datatype=\"&xsd;decimal\">" + (wert[1].replace(',', '.').replace("\"", "")) + "</MET>");
			System.out.println("<Kalorien rdf:datatype=\"&xsd;integer\">" + (wert[2].replace("\"", "")) + "</Kalorien>");
			System.out.println("</owl:NamedIndividual>");
		}
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
	System.out.println("Ende erreicht");
  }
 
}