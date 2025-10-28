package com.mesago.gatewayservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "springdoc.swagger-ui")
public class SwaggerUiConfigProperties {

    private List<UrlConfig> urls = new ArrayList<>();

    public List<UrlConfig> getUrls() { return urls; }
    public void setUrls(List<UrlConfig> urls) { this.urls = urls; }

    public static class UrlConfig {
        private String name;
        private String url;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
}