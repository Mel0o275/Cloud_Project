package com.example.essayfeedback.auth.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "cloudinary")
@Getter
@Setter
public class CloudinaryProperties {
    private String cloud_name;
    private String api_key;
    private String api_secret;

    // Manual Getters
    public String getCloud_name() { return cloud_name; }
    public String getApi_key() { return api_key; }
    public String getApi_secret() { return api_secret; }

    // Manual Setters
    public void setCloudName(String cloud_name) { this.cloud_name = cloud_name; }
    public void setApiKey(String api_key) { this.api_key = api_key; }
    public void setApiSecret(String api_secret) { this.api_secret = api_secret; }
}