package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    // LOG info variables
    public static final String LOG_TAG = JsonUtils.class.getSimpleName();
    public static final String ERROR_PARSE = "Error parsing JSON";
    public static final String ERROR_PARSE_ARRAY = "Error parsing JSON Array";

    // JSON object name variables
    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            // JSON objects parsing
            JSONObject root = new JSONObject(json);
            JSONObject name = root.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            JSONArray alsoKnownAsArray = name.getJSONArray(ALSO_KNOWN_AS);
            String placeOfOrigin = root.getString(PLACE_OF_ORIGIN);
            String description = root.getString(DESCRIPTION);
            String image = root.getString(IMAGE);
            JSONArray ingredientsArray = root.getJSONArray(INGREDIENTS);

            // JSON list parsing
            ArrayList<String> alsoKnownAs = jsonToListConverter(alsoKnownAsArray);
            ArrayList<String> ingredients = jsonToListConverter(ingredientsArray);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(LOG_TAG, ERROR_PARSE, e);
        }
        return null;
    }

    // JSON list parsing - helper method
    private static ArrayList<String> jsonToListConverter(JSONArray jsonArray) {
        ArrayList<String> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    list.add(jsonArray.getString(i));
                } catch (JSONException e) {
                    Log.e(LOG_TAG, ERROR_PARSE_ARRAY, e);
                }
            }
        }
        return list;
    }
}