package de.recipeminer.models;


public class Ingredient {

    private String material = "";
    private String additionalInfo = "";

    private Unit unit;

    public Ingredient() {
        material = "";
        unit = new Unit();
    }

    public SIUnit getSIUnit() {
        return unit.toSIUnit();
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit un) {
        unit = un;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String str) {
        additionalInfo = str;
    }


    public String getMaterial() {
        return material;
    }

    public void setMaterial(String mat) {
        material = mat;
    }


}
