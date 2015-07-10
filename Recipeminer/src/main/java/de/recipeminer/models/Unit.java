package de.recipeminer.models;

public class Unit extends SuperUnit {

    /*
      * Beim Hinzufügen einer neuen Art von Zutat:
      * die Konstante AnzahlDerArtenVonZutaten inkrementieren
      * und
      * neues feld im array besetzen
      */

    public Unit(String str) {

        value = 0;
        AnzahlDerArtenVonZutaten = 5; //ggf. erhöhen
        possibilities = new String[AnzahlDerArtenVonZutaten];
        possibilities[0] = "PIECE";
        possibilities[1] = "BIGSPOON";
        possibilities[2] = "CUP";
        possibilities[3] = "PINCH";
        possibilities[4] = "TEASPOON";

        //hier neue felder besetzen

        set(str);
    }

    public Unit() {
        value = 0;
        AnzahlDerArtenVonZutaten = 5; //ggf. erhöhen
        possibilities = new String[AnzahlDerArtenVonZutaten];
        possibilities[0] = "PIECE";
        possibilities[1] = "BIGSPOON";
        possibilities[2] = "CUP";
        possibilities[3] = "PINCH";
        possibilities[4] = "TEASPOON";

        //hier neue felder besetzen

    }

    /**
     * Hier findet die Umrechnung in SI-Einheiten statt.
     */
    public SIUnit toSIUnit() {
        //TODO: Umrechnung Überprüfen, Fantasiewerte eingetragen. g.
        SIUnit si = new SIUnit("PIECE");

        if (value == 0) {
            si.setAmount(value);
            si.set("PIECE");
        }
        if (value == 1) {
            si.setAmount(value * 3);
            si.set("GRAM");
        }
        if (value == 2) {
            si.setAmount(value * 0.150);
            si.set("LITRE");
        }
        if (value == 3) {
            si.setAmount(value * 0.25);
            si.set("GRAM");
        }
        if (value == 4) {
            si.setAmount(value * 0.50);
            si.set("GRAM");
        }
        return si;
    }
}
