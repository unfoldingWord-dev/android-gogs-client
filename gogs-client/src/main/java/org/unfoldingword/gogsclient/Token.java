package org.unfoldingword.gogsclient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents an application authentication token
 */
public class Token {
    private String name = null;
    private String sha1 = null;

    private Token() {}

    public Token(String name) {
        this.name = name;
    }

    /**
     * Returns a token parsed from json
     * @param json
     * @return
     */
    public static Token parse(JSONObject json) {
        if(json != null) {
            Token token = new Token();
            token.name = (String)Util.getFromJSON(json, "name", null);
            token.sha1 = (String)Util.getFromJSON(json, "sha1", null);
            return token;
        }
        return null;
    }

    @Override
    public String toString() {
        return this.sha1;
    }

    public String getName() {
        return name;
    }
}
