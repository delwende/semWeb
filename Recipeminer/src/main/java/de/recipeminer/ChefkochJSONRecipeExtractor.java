package de.recipeminer;


import de.recipeminer.models.Recipe;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ChefkochJSONRecipeExtractor extends AbstractRecipeExtractor {
    @Deprecated
    /*
     *Now acknowledged by Ruby Model."
     *Georg, 18. Jan. 12
    */

    private static final String detailRezeptIDFeld = "\"rezept_show_id\":\"";
    private static final String detailRezeptZubereitungstextFeld = "\"rezept_zubereitung\":\"";
    private static final String detailRezeptPortionzahlFeld = "\",\"rezept_portionen\":\"";
    private static final String detailRezeptZutatenBeginn = "rezept_zutaten\":[";

    private static final String rezeptIDFeld = ",\"RezeptShowID\":\"";
    private static final String rezeptNamensFeld1 = "\",\"RezeptName\":\"";
    private static final String rezeptNamensFeld2 = "\",\"RezeptName2\":\"";

    private int anzahlRezepte = 0;
    private ChefkochAPICrawler capi;
    private int rezepteProSeite = 0;

    List<Recipe> list = new ArrayList<Recipe>();

    public ChefkochJSONRecipeExtractor(ChefkochAPICrawler cpi) {
        this.capi = cpi;
    }

    public List<Recipe> extractRecipes(String s) throws IllegalArgumentException, MalformedURLException, IOException, StringIndexOutOfBoundsException {
        //Liste leeren
        if (list != null) list.clear();
        if (s == "") throw new IllegalArgumentException("Kein Text- kein Rezept zu extrahieren.");
        //Anzahl der Rezepte im string zählen
        anzahlRezepte = occurenceInOf(s, rezeptIDFeld);
        //Dekrementieren, da letztes Rezept wegen des im ChefkochAPICrawler festgeschriebenen BufferCap ab gewisser Länge wahrscheinlich abgeschnitten ist
        if (anzahlRezepte > 5) anzahlRezepte--;
        if (Control.debuggingmode == 1) java.lang.System.out.println("\nAnzahl der gezählten Rezepte:" + anzahlRezepte);

        StringBuffer sb = new StringBuffer(s);
        sb.ensureCapacity(ChefkochAPICrawler.bufferCapGesamtRezeptListe);

        StringBuffer sb2 = new StringBuffer("");
        sb2.ensureCapacity(ChefkochAPICrawler.bufferCapEinzelRezept);

        String string = "";

        for (int i = 1; i <= anzahlRezepte; i++) {
            //Rezept erstellen
            Recipe re = new Recipe();

            //ID-Feld extrahieren und ID übertragen
            sb.delete(0, sb.indexOf(rezeptIDFeld) + rezeptIDFeld.length());
            string = (sb.subSequence(0, sb.indexOf(rezeptNamensFeld1))).toString();
            re.setShowId(string);

            //Namen extrahieren und übertragen
            sb.delete(0, sb.indexOf(rezeptNamensFeld1) + rezeptNamensFeld1.length());
            string = (sb.subSequence(0, sb.indexOf(rezeptNamensFeld2))).toString();
            re.setName(ZutatenExtractor.sonderzeichen(string));


            //Namen2 extrahieren und übertragen
            sb.delete(0, sb.indexOf(rezeptNamensFeld2) + rezeptNamensFeld2.length());
            string = (sb.subSequence(0, sb.indexOf("\",\""))).toString();
            re.setName2(ZutatenExtractor.sonderzeichen(string));

            //Detailierte Rezeptinformationen abrufen
            String detailtext = "";

            detailtext = capi.crawlForDetailedRecipe(re.getShowId());


            //Stringbuffer neu aufüllen
            sb2 = new StringBuffer(detailtext);
            sb2.ensureCapacity(ChefkochAPICrawler.bufferCapEinzelRezept);

            //bis zum Zubereitungstextfeld alles löschen, Text extrahieren und übertragen
            sb2.delete(0, sb2.indexOf(detailRezeptZubereitungstextFeld) + detailRezeptZubereitungstextFeld.length());

            if (Control.debuggingmode == 1) System.out.println(sb2.toString() + "/n");

            int index = sb2.indexOf(("\",\""));
            if (index >= 0) {
                string = sb2.subSequence(0, index).toString();
                re.setInstructionText(string);
            }

            //bis zum Portionszahlfeld alles löschen, Text extrahieren und übertragen
            sb2.delete(0, sb2.indexOf(detailRezeptPortionzahlFeld) + detailRezeptPortionzahlFeld.length());
            string = ((sb2.subSequence(0, sb2.indexOf("\",\""))).toString());
            re.setPortionszahl(string);

            //zu den Zutaten springen, erstmal als Text extrahieren und übertragen
            sb2.delete(0, sb2.indexOf(detailRezeptZutatenBeginn) + detailRezeptZutatenBeginn.length());
            string = (sb2.subSequence(0, sb2.indexOf("]"))).toString();
            re.zutatenstring = string;
            if (Control.debuggingmode == 1) System.out.println(i);
            list.add(re);
        }
        return list;
    }



    //Hilfsmethode, zählt wie oft searchString in string vorkommt
    public int occurenceInOf(String string, String searchString) {
        int occurences = 0;
        if (0 != searchString.length()) {
            for (int index = string.indexOf(searchString, 0); index != -1; index = string
                    .indexOf(searchString, index + 1)) {
                occurences++;
            }
        }
        return occurences;
    }
}
