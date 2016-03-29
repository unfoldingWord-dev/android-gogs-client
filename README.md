# android-gogs-client
A client library for interacting with the [Gogs](https://gogs.io) REST api. This library is written to communicate according to the api defined in [gogits/go-gogs-client](https://github.com/gogits/go-gogs-client/wiki).

##Supported Operations
* create user
* edit user
* search users
* get user
* delete user
* search repositories
* list user repositories
* create repository
* delete repository
* create application token
* list application tokens
* create public key
* get public key
* list public keys
* delete public key

##Installation
To use this library your Android project must be configured to use the JCenter or Maven Central repositories.

Add the following to your package dependencies and sync gradle.
```
compile 'org.unfoldingword.tools:gogs-client:1.1.0'
```

##Usage
```
GogsAPI api = new GogsAPI("https://try.gogs.io/api/v1"); // change to any gogs server
List<User> users = api.searchUsers("some-user-name", 5, null);
// do something
... 

// inspect actual response for more details if needed
Response response = api.getLastResponse();
```
