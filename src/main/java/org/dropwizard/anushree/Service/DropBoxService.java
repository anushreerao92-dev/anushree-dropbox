package org.dropwizard.anushree.Service;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.dropwizard.anushree.Configuration.MyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class DropBoxService {
    public static final String OAUTH_2_AUTHORIZE_URL = "https://www.dropbox.com/oauth2/authorize";
    public static final String OAUTH_2_TOKEN_URL = "https://api.dropboxapi.com/oauth2/token";
    public static final String TEAM_GET_INFO_URL = "https://api.dropboxapi.com/2/team/get_info";
    private static final Logger LOGGER = LoggerFactory.getLogger(DropBoxService.class);

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public DropBoxService(MyConfig.DropboxConfig cfg) {
        this.clientId = cfg.getClientId();
        this.clientSecret = cfg.getClientSecret();
        this.redirectUri = cfg.getRedirectUri();
    }

    public String buildAuthUrl() {
        String scope = "account_info.read events.read members.read team_data.governance.read team_data.governance.write team_data.member team_info.read";
        return OAUTH_2_AUTHORIZE_URL
                + "?client_id=" + urlEnc(clientId)
                + "&response_type=code"
                + "&redirect_uri=" + urlEnc(redirectUri)
                + "&token_access_type=offline"
                + "&scope=" + urlEnc(scope);
    }

    private static String urlEnc(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    // Exchange code for token
    public String exchangeCodeForToken(String code) throws Exception {
        HttpPost post = new HttpPost(OAUTH_2_TOKEN_URL);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("grant_type", "authorization_code"));
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("redirect_uri", redirectUri));

        post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        try (CloseableHttpResponse resp = httpClient.execute(post)) {
            int status = resp.getCode();
            String body = EntityUtils.toString(resp.getEntity());
            if (status >= 200 && status < 300) {
                return body; // JSON with access_token, scope, etc.
            } else {
                throw new RuntimeException("Token exchange failed: " + status + " -> " + body);
            }
        }
    }

    // Example: team/get_info
    public String getTeamInfo(String accessToken) throws Exception {
        HttpPost post = new HttpPost(TEAM_GET_INFO_URL);
        post.addHeader("Authorization", "Bearer " + accessToken);
        post.addHeader("Content-Type", "application/json");
        post.setEntity(new org.apache.hc.core5.http.io.entity.StringEntity("null", StandardCharsets.UTF_8));

        try (CloseableHttpResponse resp = httpClient.execute(post)) {
            String response = EntityUtils.toString(resp.getEntity());
            LOGGER.info("The result from the API is {}", response);
            if (resp.getCode() >= 200 && resp.getCode() < 300) return response;
            throw new RuntimeException("getTeamInfo failed: " + resp.getCode() + " -> " + response);
        }
    }

}
