# Sigsense RESTful Server

This server uses Spring Boot framework to enable all features required in the exercise. 

For simplicity I just used the built in Authorization server from Spring Security library to issue access tokens based on client application credentials and for the purpose of this exercise there is only one client configured. Access tokens will expire after 30 minutes of being issued.
AuthenticationManager
### Reference Documentation

To run the server:

    `java -jar build/libs/rest-api-0.0.1-SNAPSHOT.jar`

Available endpoints:

* Access Token
    
    This endpoint provides the access tokens given the proper client/user credentials.
    
    Basic Auth header param must be created using these values:
    `username=sigsense-client` and `password=secret`

    `curl -X POST
     'http://localhost:8080/rest/oauth/token' 
     -H 'Authorization: Basic c2lnc2Vuc2UtY2xpZW50OnNlY3JldA==' 
     -H 'Content-Type: application/x-www-form-urlencoded' 
     -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' 
     -F 'username=<Best Candidate OR Savy Admin>' 
     -F 'password=<Team Player OR S3cuR3 P4ssW0rD>' 
     -F 'grant_type=password'`

* Greeting

    This endpoint returns the string `“Hello from Sigsense”` to all authenticated users.

    `curl -X GET 
     'http://localhost:8080/rest' 
     -H 'Authorization: Bearer <USER OR ADMIN ACCESS TOKEN>'`
     
* List usernames

    This endpoint returns the list of usernames stored in the database **only** to authenticated users that have the `ADMIN` role assigned.

    `curl -X GET 
     'http://localhost:8080/rest/user' 
     -H 'Authorization: Bearer <ONLY ADMIN ACCESS TOKEN>'`
