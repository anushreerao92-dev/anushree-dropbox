# Dropwizard Dropbox API

This is a Dropwizard-based Java application that demonstrates Dropbox OAuth2 authentication and fetching **team info** via the Dropbox API.

# Set Up
Build the project

> mvn clean package

Run the Dropwizard server

For eg : 
> java -jar target/cloud-eagle-anushree-1.0-SNAPSHOT.jar server src/main/resources/config.yml

# Usage

1 . Authorize the App

    Open in browser:

    GET http://localhost:8080/dropbox/auth

    This will redirect you to Dropbox to log in and grant permissions.

    After authorization, Dropbox redirects to:

    http://localhost:8080/dropbox/callback?code=<code>
    
    The access token will be visible in the browser as below 

    <img width="948" height="181" alt="image" src="https://github.com/user-attachments/assets/f9929b59-62a4-4132-aa02-b9d464de250d" />

2. Fetch Team Info

   Use Postman or curl to hit /userinfo with the Authorization header:

   POST http://localhost:8080/dropbox/getTeamInfo
   Authorization: Bearer <access_token>
   Returns team info JSON.
   Also prints team info in server logs.

   Server logs

<img width="881" height="238" alt="image" src="https://github.com/user-attachments/assets/a5e63a9e-bafb-4d72-8159-c7bb72dd7432" />

  Response of the team get info API endpoint running in my localhost

<img width="950" height="489" alt="image" src="https://github.com/user-attachments/assets/590729f1-833d-462a-a529-1f70f584dcea" />
