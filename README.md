# android-gogs-client
A client library for interacting with the Gogs REST api. This library is written to communicate according to the api defined in [gogits/go-gogs-wiki](https://github.com/gogits/go-gogs-client/wiki)

##Supported Operations
* create user
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

Add the following to your package dependencies and rebuild sync gradle.
```
compile 'org.unfoldingword.tools:gogs-client:1.0.0'
```
