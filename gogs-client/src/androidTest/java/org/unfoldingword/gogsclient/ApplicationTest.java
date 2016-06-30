package org.unfoldingword.gogsclient;

import android.test.InstrumentationTestCase;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends InstrumentationTestCase {

    private User adminUser;
    private User demoUser;
    private User fakeUser;
    private Token demoToken;
    private Repository demoRepo;
    private GogsAPI api;
    private PublicKey demoKey;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // load config
        InputStream is = getInstrumentation().getContext().getAssets().open("config.json");
        String configString = TestUtil.readStreamToString(is);
        JSONObject config = new JSONObject(configString);

        // set up data
        this.demoUser = User.fromJSON(config.getJSONObject("demoUser"));
        this.fakeUser = User.fromJSON(config.getJSONObject("fakeUser"));
        this.demoToken = Token.fromJSON(config.getJSONObject("demoToken"));
        this.adminUser = User.fromJSON(config.getJSONObject("adminUser"));
        this.demoRepo = Repository.fromJSON(config.getJSONObject("demoRepo"));
        this.demoKey = PublicKey.fromJSON(config.getJSONObject("demoKey"));
        this.api = new GogsAPI(config.getString("api"));
    }

    public void test01CreateUser() throws Exception {
        User createdUser = api.createUser(demoUser, adminUser, false);
        assertNotNull(createdUser);
        assertEquals(createdUser.getUsername(), demoUser.getUsername());

        User anotherNewUser = new User("demo-user-that-is-not-created", "bla bla bla");
        User badUser = api.createUser(anotherNewUser, null, false);
        assertNull(badUser);
    }

    public void test02UpdateUser() throws Exception {
        demoUser.fullName = "My test full name";
        User updatedUser = api.editUser(demoUser, adminUser);
        assertNotNull(updatedUser);
        assertEquals(updatedUser.fullName, demoUser.fullName);
    }

    public void test03SearchUsers() throws Exception {
        List<User> users = api.searchUsers(demoUser.getUsername(), 5, null);
        assertTrue(users.size() > 0);
        assertTrue(users.get(0).email.isEmpty());

        List<User> completeUsers = api.searchUsers("d", 5, adminUser);
        assertTrue(completeUsers.size() > 0);
        assertTrue(!completeUsers.get(0).email.isEmpty());
    }

    public void test04GetUser() throws Exception {
        User completeUser = api.getUser(demoUser, adminUser);
        assertNotNull(completeUser);
        assertTrue(!completeUser.email.isEmpty());

        User foundUser = api.getUser(demoUser, null);
        assertNotNull(foundUser);
        assertTrue(foundUser.email.isEmpty());

        User unknownUser = api.getUser(fakeUser, adminUser);
        assertNull(unknownUser);
    }

    public void test05SearchRepos() throws Exception {
        int limit = 2;
        List<Repository> repos = api.searchRepos("demo", 0, limit);
        assertTrue(repos.size() > 0);
        assertTrue(repos.size() <= limit);
    }

    public void test06CreateRepo() throws Exception {
        Repository repo = api.createRepo(demoRepo, demoUser);
        assertNotNull(repo);
        assertEquals(repo.getFullName(), demoUser.getUsername() + "/" + demoRepo.getName());

        Repository badRepoSpec = new Repository("repo-never-created", "This is never created", false);
        Repository badRepo = api.createRepo(badRepoSpec, fakeUser);
        assertNull(badRepo);
    }

    public void test07ListRepos() throws Exception {
        List<Repository> repos = api.listRepos(demoUser);
        assertTrue(repos.size() > 0);
        assertEquals(repos.get(0).getFullName(), demoUser.getUsername() + "/" + demoRepo.getName());

        // unknown user returns empty set
        List<Repository> emptyRepos =  api.listRepos(fakeUser);
        assertEquals(emptyRepos.size(), 0);
    }

    public void test08GetRepo() throws Exception {
        int limit = 2;
        List<Repository> repos = api.searchRepos("d", 0, limit);
        Repository repo = api.getRepo(repos.get(0), adminUser);
        assertNotNull(repo);
        assertFalse(repo.getHtmlUrl().equals(""));
    }

    public void test09DeleteRepo() throws Exception {
        assertTrue(api.deleteRepo(demoRepo, demoUser));
        assertEquals(api.listRepos(demoUser).size(), 0);

        // unknown user is an error
        assertFalse(api.deleteRepo(demoRepo, fakeUser));
        assertEquals(api.getLastResponse().code, 401);

        // unknown repo is an error
        Repository fakeRepo = new Repository("fake-repository", "", false);
        assertFalse(api.deleteRepo(fakeRepo, demoUser));
        assertEquals(api.getLastResponse().code, 404);
    }

    public void test10CreateToken() throws Exception {
        Token token = api.createToken(demoToken, demoUser);
        assertNotNull(token);
        assertEquals(token.getName(), demoToken.getName());

        Token badToken = api.createToken(demoToken, null);
        assertNull(badToken);
    }

    public void test11ListTokens() throws Exception {
        List<Token> tokens = api.listTokens(demoUser);
        assertTrue(tokens.size() > 0);
        assertEquals(tokens.get(0).getName(), demoToken.getName());

        List<Token> badTokens = api.listTokens(fakeUser);
        assertEquals(badTokens.size(), 0);
    }

    public void test12GetUserWithToken() throws Exception {
        List<Token> tokens = api.listTokens(demoUser);
        assertTrue(tokens.size() > 0);
        demoUser.token = tokens.get(0);
        demoUser.password = null;

        User foundUser = api.getUser(demoUser, adminUser);
        assertNotNull(foundUser);
        assertTrue(!foundUser.email.isEmpty());
    }

    public void test13CreatePublicKey() throws Exception {
        PublicKey key = api.createPublicKey(demoKey, demoUser);
        assertNotNull(key);
        assertEquals(key.getTitle(), demoKey.getTitle());
    }

    public void test14ListPublicKeys() throws Exception {
        List<PublicKey> keys = api.listPublicKeys(demoUser);
        assertTrue(keys.size() > 0);
        assertEquals(keys.get(0).getTitle(), demoKey.getTitle());
    }

    public void test15GetPublicKey() throws Exception {
        // get key id first
        List<PublicKey> keys = api.listPublicKeys(demoUser);
        PublicKey key = new PublicKey(keys.get(0).getId());

        PublicKey fetchedKey = api.getPublicKey(key, demoUser);
        assertNotNull(fetchedKey);
        assertEquals(fetchedKey.getTitle(), demoKey.getTitle());
    }

    public void test16DeletePublicKey() throws Exception {
        // get key id first
        List<PublicKey> keys = api.listPublicKeys(demoUser);
        PublicKey key = new PublicKey(keys.get(0).getId());

        assertTrue(api.deletePublicKey(key, demoUser));
        assertEquals(api.listPublicKeys(demoUser).size(), 0);
    }

    public void test17DeleteUser() throws Exception {
        // users cannot delete themselves
        assertFalse(api.deleteUser(demoUser, demoUser));

        // delete user
        assertTrue(api.deleteUser(demoUser, adminUser));
        assertNull(api.getUser(demoUser, adminUser));

        // unknown user is an error on delete
        assertFalse(api.deleteUser(fakeUser, adminUser));
        assertEquals(api.getLastResponse().code, 404);
    }
}