package org.unfoldingword.gogsclient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joel on 3/29/2016.
 */
public class Util {

    /**
     * Adds a value to the json object if the value is not null
     * @param json
     * @param field
     * @param value
     * @return
     */
    public static JSONObject addToJSON(JSONObject json, String field, Object value) {
        if(value != null) {
            try {
                json.put(field, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    /**
     * Retrieves a value from a json object or returns the default if it does not exist
     * @param json the json object to inspect
     * @param field the field to return
     * @param defaultValue the default value to return if the field does not exist
     * @return
     */
    public static Object getFromJSON(JSONObject json, String field, Object defaultValue) {
        if(json.has(field)) {
            try {
                return json.get(field);
            } catch (JSONException e) {
                e.printStackTrace();
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
}
