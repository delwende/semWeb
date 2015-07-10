package de.recipeminer.models;


public class SIUnit extends SuperUnit {

    /*
      * Beim Hinzufögen einer neuen Art von Zutat:
      * die Konstante AnzahlDerArtenVonZutaten inkrementieren
      * und
      * neues feld im array besetzen
      */

    public SIUnit(String str) {
        value = 0;
        AnzahlDerArtenVonZutaten = 2; //ggf. erhöhen
        possibilities = new String[AnzahlDerArtenVonZutaten - 1];
        possibilities[0] = "PIECE";
        possibilities[1] = "GRAM";
        possibilities[2] = "LITRE";

        //hier ggf. neue felder besetzen


        set(str);
    }


}
