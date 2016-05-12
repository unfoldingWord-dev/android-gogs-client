package org.unfoldingword.gogsclient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A gogs user
 */
public class User {

    private String username = "";
    public String password = "";
    public String email = "";
    public String fullName = "";
    private String avatarUrl = "";
    public Token token = null;
    private String loginName = null;
    private String website = null;
    private String location = null;
    private boolean active = false;
    private boolean admin = false;
    private boolean allowImportLocal = false;
    private boolean allowGitHook = false;
    private int id = 0;

    private User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns a user parsed from json
     * @param json
     * @return
     */
    public static User fromJSON(JSONObject json) {
        if(json != null) {
            User user = new User();
            user.id = (int)Util.getFromJSON(json, "id", 0);
            user.username = (String)Util.getFromJSON(json, "username", null);
            user.password = (String)Util.getFromJSON(json, "password", null);
            user.email = (String)Util.getFromJSON(json, "email", null);
            user.avatarUrl = (String)Util.getFromJSON(json, "avatar_url", null);
            user.fullName = (String)Util.getFromJSON(json, "full_name", null);
            user.loginName = (String)Util.getFromJSON(json, "login_name", null);
            user.website = (String)Util.getFromJSON(json, "website", null);
            user.location = (String)Util.getFromJSON(json, "location", null);
            user.active = (boolean)Util.getFromJSON(json, "active", true);
            user.admin = (boolean)Util.getFromJSON(json, "admin", false);
            user.allowGitHook = (boolean)Util.getFromJSON(json, "allow_git_hook", true);
            user.allowImportLocal = (boolean)Util.getFromJSON(json, "allow_import_local", true);
            return user;
        }
        return null;
    }

    /**
     * Returns the token.
     * @deprecated please used the public property `token` instead
     * @return
     */
    @Deprecated
    public Token getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Converts the user to a json object
     * @return
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        if(this.id > 0) {
            json = Util.addToJSON(json, "id", this.id);
        }
        json = Util.addToJSON(json, "full_name", this.fullName);
        json = Util.addToJSON(json, "email", this.email);
        json = Util.addToJSON(json, "username", this.username);
        json = Util.addToJSON(json, "password", this.password);
        json = Util.addToJSON(json, "login_name", this.loginName);
        json = Util.addToJSON(json, "website", this.website);
        json = Util.addToJSON(json, "location", this.location);
        json = Util.addToJSON(json, "avatar_url", this.avatarUrl);
        json = Util.addToJSON(json, "active", this.active);
        json = Util.addToJSON(json, "admin", this.admin);
        json = Util.addToJSON(json, "allow_git_hook", this.allowGitHook);
        json = Util.addToJSON(json, "allow_import_local", this.allowImportLocal);
        return json;
    }

    /**
     * Returns the id of the gogs user
     * @return
     */
    public int getId() {
        return id;
    }
}
