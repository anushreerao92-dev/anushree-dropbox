package org.dropwizard.anushree.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class MyConfig extends Configuration {

    @JsonProperty("dropbox")
    private DropboxConfig dropboxConfig;

    public DropboxConfig getDropboxConfig() {
        return dropboxConfig;
    }

    public static class DropboxConfig {
        @JsonProperty
        private String clientId;
        @JsonProperty
        private String clientSecret;
        @JsonProperty
        private String redirectUri;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public void setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
        }
    }
}
