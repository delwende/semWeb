package de.recipeminer.models;

import java.util.HashMap;
import java.util.List;

/*
@author Georg Mühlenberg
@version 0.2
@date 18 Jan 2012
*/

public class ChefkochRecipe extends Recipe {

    static final String NAME_KEY_1 = "rezept_name";
    static final String NAME_KEY_2 = "rezept_name2";
    static final String TIME_PREP_KEY = "rezept_preparation_time";
    static final String TIME_COOK_KEY = "rezept_cooking_time";
    static final String TIME_REST_KEY = "rezept_resting_time";


    static final String ID_KEY = "rezept_show_id";
    static final String PREPARATION_TEXT_KEY = "rezept_zubereitung";
    static final String PORTION_KEY = "rezept_portionen";
    static final String INGREDIENT_LIST_KEY = "rezept_zutaten";
    static final String MATERIAL_KEY = "name"; 
    static final String ADDITIONAL_INFO_KEY = "eigenschaft"; 
    static final String AMOUNT_KEY = "menge";
    static final String UNIT_KEY = "einheit"; 

    public ChefkochRecipe(String uuid, HashMap<String, Object> data) {

        this.uuid = uuid;
        this.data = data;
    }

    String uuid;
    HashMap<String, Object> data;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChefkochRecipe that = (ChefkochRecipe) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public Recipe parseRecipe() {
        Recipe rec = new Recipe();

        /*
        Werte aus der HashMap übernehmen
        */
        if (data.containsKey(NAME_KEY_1)) rec.setName((String) data.get(NAME_KEY_1));
        if (data.containsKey(NAME_KEY_2)) rec.setName2((String) data.get(NAME_KEY_2));
        if (data.containsKey(ID_KEY)) rec.setShowId((String) data.get(ID_KEY));

        if (data.containsKey(PREPARATION_TEXT_KEY)) rec.setInstructionText((String) data.get(PREPARATION_TEXT_KEY));
        if (data.containsKey(TIME_COOK_KEY)) rec.setZeit_zubereitung((String) data.get(TIME_COOK_KEY));
        if (data.containsKey(TIME_PREP_KEY)) rec.setZeit_vorbereitung((String) data.get(TIME_PREP_KEY));
        if (data.containsKey(TIME_REST_KEY)) rec.setZeit_ruhen((String) data.get(TIME_REST_KEY));
        if (data.containsKey(PORTION_KEY)) rec.setPortionszahl((String) data.get(PORTION_KEY));

        if (data.containsKey(INGREDIENT_LIST_KEY)) {

            /*
            Verarbeitung der Zutaten - unter dem Value-Pointer INGREDIENT_LIST_KEY liegt eine Liste von HashMap<String, Object>
             */

            List<HashMap<String, String>> zutaten = (List<HashMap<String, String>>) data.get(INGREDIENT_LIST_KEY);


            HashMap<String, String> hm = new HashMap<String, String>();

            for (int i = 0; i < zutaten.size(); i++) {
                if (zutaten.get(i) != null) {

                    /*
                    Aus der Liste wird eine HashMap entnommmen
                     */
                    hm = zutaten.get(i);

                    /*
                    Klassen Ingredient und Unit werden instanziiert, um Daten einzutragen
                     */
                    Ingredient ing = new Ingredient();
                    Unit un = new Unit();

                    ing.setMaterial((String) hm.get(MATERIAL_KEY));
                    ing.setAdditionalInfo((String) hm.get(ADDITIONAL_INFO_KEY));
                    un.setAmount((String) hm.get(AMOUNT_KEY));
                    un.set((String) hm.get(UNIT_KEY));


                    ing.setUnit(un);
                    rec.addIngredient(ing);

                }
            }
        }
        return rec;
    }
}
