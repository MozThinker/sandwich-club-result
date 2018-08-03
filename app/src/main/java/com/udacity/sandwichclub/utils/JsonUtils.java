package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class JsonUtils {



    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        if(json!=null){

            try {
                JSONObject jsonObj = new JSONObject(json);

                // Getting JSON Know as Array node
                JSONArray jsonArrayKnowAs = jsonObj.getJSONObject("name").getJSONArray("alsoKnownAs");
                // Getting JSON Ingredients as Array node
                JSONArray jsonArrayIngredients = jsonObj.getJSONArray("ingredients");

                sandwich.setMainName(jsonObj.getJSONObject("name").getString("mainName"));

                List<String> listNames = new ArrayList<String>();

                for (int i = 0; i < jsonArrayKnowAs.length(); i++) {
                    String c = jsonArrayKnowAs.getString(i);
                    listNames.add(c);
                }

                sandwich.setAlsoKnownAs(listNames);

                sandwich.setPlaceOfOrigin(jsonObj.getString("placeOfOrigin"));

                sandwich.setDescription(jsonObj.getString("description"));

                sandwich.setImage(jsonObj.getString("image"));

                List<String> listIngredients = new ArrayList<String>();
                for (int i = 0; i < jsonArrayIngredients.length(); i++) {
                    String d = jsonArrayIngredients.getString(i);
                    listIngredients.add(d);
                }
                sandwich.setIngredients(listIngredients);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        return sandwich;

    }
}
