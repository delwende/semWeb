package de.recipeminer.models;


public abstract class SuperUnit {

    String[] possibilities;
    int value;
    int AnzahlDerArtenVonZutaten;
    private double amount;

    public void set(String str) {
        boolean gueltig = false;
        for (int i = 1; i <= AnzahlDerArtenVonZutaten - 1; i++) {
            if (str == possibilities[i]) value = i;
            gueltig = true;
        }

        if (gueltig == false) throw new IllegalArgumentException("Invalid kind of unit");
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double am) {
        amount = am;
    }


    public void setAmount(String am) {
        amount = Double.parseDouble(am);
    }

    public int get() {
        return value;
    }

    public String toString() {
        return possibilities[value];
    }


}
