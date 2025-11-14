# Dropwizard Dropbox API

This is a Dropwizard-based Java application that demonstrates Dropbox OAuth2 authentication and fetching **team info** via the Dropbox API.

---

## Set Up

### Build the project

> mvn clean package

Run the Dropwizard server

For example:

> java -jar target/cloud-eagle-anushree-1.0-SNAPSHOT.jar server src/main/resources/config.yml

### Usage

1. Authorize the App
Open in browser:
GET http://localhost:8080/dropbox/auth
This will redirect you to Dropbox to log in and grant permissions.
After authorization, Dropbox redirects to:
http://localhost:8080/dropbox/callback?code=<code>
The access token will be visible in the browser as below:

<img width="1896" height="361" alt="Screenshot 2025-11-14 195023" src="https://github.com/user-attachments/assets/c02dbbe5-aa15-4d13-957b-08ce7507701b" />


2. Fetch Team Info
Use Postman or curl to hit /getTeamInfo with the Authorization header:

POST http://localhost:8080/dropbox/getTeamInfo
Authorization: Bearer <access_token>
Returns team info JSON.
Also prints team info in server logs.

Server logs example:
<img width="1761" height="476" alt="Screenshot 2025-11-14 195235" src="https://github.com/user-attachments/assets/87828afa-a372-4822-b979-311334c43830" />


Response of the /getTeamInfo API endpoint running locally:
<img width="1899" height="978" alt="Screenshot 2025-11-14 194636" src="https://github.com/user-attachments/assets/a9b13154-6df5-4905-ac3e-d78eadc03ca6" />
