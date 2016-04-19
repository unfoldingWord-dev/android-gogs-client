package org.unfoldingword.gogsclient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a gogs repository
 */
public class Repository {

    private String name = "";
    private String description = "";
    private boolean isPrivate;
    private int id = 0;
    private String fullName = "";
    private boolean isFork;
    private String htmlUrl = "";
    private String cloneUrl = "";
    private String sshUrl = "";
    private User owner;

    private Repository() {}

    public Repository(String name, String description, boolean isPrivate) {
        this.name = name;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    /**
     * Returns a repository parsed from json
     * @param json
     * @return
     */
    public static Repository fromJSON(JSONObject json) {
        if(json != null) {
            Repository repo = new Repository();
            repo.id = (int)Util.getFromJSON(json, "id", 0);
            repo.name = (String)Util.getFromJSON(json, "name", null);
            repo.description = (String)Util.getFromJSON(json, "description", null);
            repo.fullName = (String)Util.getFromJSON(json, "full_name", null);
            repo.isPrivate = (boolean)Util.getFromJSON(json, "private", true);
            repo.isFork = (boolean)Util.getFromJSON(json, "fork", false);
            repo.htmlUrl = (String)Util.getFromJSON(json, "html_url", null);
            repo.cloneUrl = (String)Util.getFromJSON(json, "clone_url", null);
            repo.sshUrl = (String)Util.getFromJSON(json, "ssh_url", null);
            if(json.has("owner")) {
                try {
                    repo.owner = User.fromJSON(json.getJSONObject("owner"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // TODO: 2/24/2016 get permissions
            return repo;
        }
        return null;
    }

    /**
     * Returns the json form of the repository
     * @return
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json = Util.addToJSON(json, "id", this.id);
        json = Util.addToJSON(json, "name", this.name);
        json = Util.addToJSON(json, "description", this.description);
        json = Util.addToJSON(json, "full_name", this.fullName);
        json = Util.addToJSON(json, "private", this.isPrivate);
        json = Util.addToJSON(json, "fork", this.isFork);
        json = Util.addToJSON(json, "html_url", this.htmlUrl);
        json = Util.addToJSON(json, "clone_url", this.cloneUrl);
        json = Util.addToJSON(json, "ssh_url", this.sshUrl);
        if(this.owner != null) {
            try {
                json.put("owner", this.owner.toJSON());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public String getFullName() {
        return fullName;
    }

    public int getId() {
        return id;
    }

    public boolean isFork() {
        return isFork;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public String getSshUrl() {
        return sshUrl;
    }

    public User getOwner() {
        return owner;
    }
}
