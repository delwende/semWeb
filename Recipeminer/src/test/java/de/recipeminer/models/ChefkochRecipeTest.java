package de.recipeminer.models;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;


/*
@author Georg Mühlenberg
@version 0.2
@date 18 Jan 2012
*/

public class ChefkochRecipeTest

{
    @Test
    public void testParse() {
        ChefkochRecipe crp = createTestRecipe();
        Recipe rec = new Recipe();
        rec = crp.parseRecipe();

        assertTrue(rec.getInstructionText() == "Etwa 100 ml Olivenöl und 150 ml Wasser mit Zitronensaft und Salz verquirlen und mit dem Couscous vermischen. Nach 5 Minuten evtl. noch etwas Öl und Zitronensaft nachgießen, falls der Couscous noch zu trocken erscheint. Knoblauch dazupressen und unterheben.\\r\\n\\r\\nDie Schale einer Zitrone abraspeln und alle o.g. Gewürze mörsern oder fein hacken, wenn nicht schon in Pulverform vorhanden, nach Geschmack dazugeben. Besonders wichtig sind Kreuzkümmel, Kümmel, Koriander und Petersilie, alles andere kann auch variiert werden.\\r\\nIch habe speziell für dieses und ähnliche Rezepte eine Gewürzmischung zusammengemörsert, bei der ich einige der in der Einkaufsliste genannten Gewürze verwendet habe.\\r\\n\\r\\nPaprika und Tomaten entkernen und fein Würfeln, Gurke waschen und stückeln. Alles in die Schüssel geben.\\r\\n\\r\\nDie rote Zwiebel abziehen und in etwas Olivenöl bei mittlerer Hitze einige Minuten glasig schwitzen. Dann den Balsamico dazugeben und einköcheln lassen. Die Zwiebeln zum restlichen Salat geben. Kräftig umrühren und mit Salz und Pfeffer sowie den wichtigen Gewürzen nachbessern. Der Couscous saugt sehr viel Aroma auf und der Salat sollte wirklich farbenfroh und intensiv schmecken.\\r\\n\\r\\nDen Schafskäse mit der Hand fein zerbröseln und in den Salat mischen. Wer Lust hat, kann nun noch geröstete Pinienkerne, Pistazienkerne oder Mandeln dazugeben. Nochmals etwas Olivenöl untermischen und ausgarnieren.");


        assertTrue(rec.getShowId() == "506631145837800");

        LinkedList li = rec.getIngredientList();

        assertTrue(rec.getIngredientList().size() == 3);

    }

    /*
   Erstellt ein Test-Recipe mit den Daten, die per Copy&Paste aus dem Testfile couscoussalat.json entnommen
    und hier in die Hashmap eingetragen wurden.
    Dabei lasse ich die ellenlangen Einträge über rezept_bilder und dergleichen weg,
    welche für uns nicht verwertbare Informationen enthalten. Aufgrund der Struktur der HashMap, bei der
    nur interessante Einträge per Schlüssel abgerufen werden, sollte dies das Testergebnis nicht verschlechtern.
                       Georg Mühlenberg, 18 Jan 2012
    */
    public static ChefkochRecipe createTestRecipe() {

        /*
      TODO zum Testen zB. der Einheitenumrechnung und der Synonyme weitere Zutaten aufnehmen.
              Georg Mühlenberg, 18 Jan 2012
        */

        String uuid = "506631145837800";
        HashMap<String, Object> hm = new HashMap<String, Object>();

        hm.put("rezept_show_id", "506631145837800");
        hm.put("rezept_id", "50663");
        hm.put("rezept_name", "Cous - Cous Salat à la Foe");
        hm.put("rezept_name2", "Beilage zum Grillen oder zu orientalischem Fleisch");
        hm.put("rezept_zubereitung", "Etwa 100 ml Olivenöl und 150 ml Wasser mit Zitronensaft und Salz verquirlen und mit dem Couscous vermischen. Nach 5 Minuten evtl. noch etwas Öl und Zitronensaft nachgießen, falls der Couscous noch zu trocken erscheint. Knoblauch dazupressen und unterheben.\\r\\n\\r\\nDie Schale einer Zitrone abraspeln und alle o.g. Gewürze mörsern oder fein hacken, wenn nicht schon in Pulverform vorhanden, nach Geschmack dazugeben. Besonders wichtig sind Kreuzkümmel, Kümmel, Koriander und Petersilie, alles andere kann auch variiert werden.\\r\\nIch habe speziell für dieses und ähnliche Rezepte eine Gewürzmischung zusammengemörsert, bei der ich einige der in der Einkaufsliste genannten Gewürze verwendet habe.\\r\\n\\r\\nPaprika und Tomaten entkernen und fein Würfeln, Gurke waschen und stückeln. Alles in die Schüssel geben.\\r\\n\\r\\nDie rote Zwiebel abziehen und in etwas Olivenöl bei mittlerer Hitze einige Minuten glasig schwitzen. Dann den Balsamico dazugeben und einköcheln lassen. Die Zwiebeln zum restlichen Salat geben. Kräftig umrühren und mit Salz und Pfeffer sowie den wichtigen Gewürzen nachbessern. Der Couscous saugt sehr viel Aroma auf und der Salat sollte wirklich farbenfroh und intensiv schmecken.\\r\\n\\r\\nDen Schafskäse mit der Hand fein zerbröseln und in den Salat mischen. Wer Lust hat, kann nun noch geröstete Pinienkerne, Pistazienkerne oder Mandeln dazugeben. Nochmals etwas Olivenöl untermischen und ausgarnieren.");
        hm.put("rezept_portionen", "6");
        hm.put("rezept_preparation_time", "30");
        hm.put("rezept_cooking_time", "0");
        hm.put("rezept_resting_time", "0");
        hm.put("rezept_schwierigkeit", "normal");
        hm.put("rezept_kcal", "0");

        HashMap<String, String> zutat1 = new HashMap<String, String>();
        HashMap<String, String> zutat2 = new HashMap<String, String>();
        HashMap<String, String> zutat3 = new HashMap<String, String>();

        zutat1.put("id", "1529");
        zutat1.put("name", "Couscous");
        zutat1.put("eigenschaft", " (Instant)");
        zutat1.put("cominfo", "0");
        zutat1.put("ist_basic", "0");
        zutat1.put("menge", "500");
        zutat1.put("einheit", "g");
        zutat1.put("menge_berechnet", "500");
        zutat1.put("has_additional_info", "false");

        zutat2.put("id", "375");
        zutat2.put("name", "Paprikaschote(n)");
        zutat2.put("eigenschaft", " rot");
        zutat2.put("cominfo", "0");
        zutat2.put("ist_basic", "0");
        zutat2.put("menge", "1");
        zutat2.put("einheit", "false");
        zutat2.put("menge_berechnet", "1");
        zutat2.put("has_additional_info", "false");

        zutat3.put("id", "1564");
        zutat3.put("name", "Fleischtomate(n)");
        zutat3.put("eigenschaft", "");
        zutat3.put("cominfo", "0");
        zutat3.put("ist_basic", "0");
        zutat3.put("menge", "2");
        zutat3.put("einheit", "false");
        zutat3.put("menge_berechnet", "2");
        zutat3.put("has_additional_info", "false");

        ArrayList<HashMap<String, String>> zutatenliste = new ArrayList<HashMap<String, String>>();
        zutatenliste.add(zutat1);
        zutatenliste.add(zutat2);
        zutatenliste.add(zutat3);
        hm.put("rezept_zutaten", zutatenliste);

        ChefkochRecipe crp = new ChefkochRecipe("506631145837800", hm);

        return crp;


    }


}
