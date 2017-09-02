package org.unbrokendome.jsonwebtoken.springsecurity.oauth2.autoconfigure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "security.oauth2.resource")
public class ResourceServerProperties {

    @JsonIgnore
    private final String clientId;
    @JsonIgnore
    private final String clientSecret;

    private String id;

    /**
     * The order of the filter chain used to authenticate tokens. Default puts it after
     * the actuator endpoints and before the default HTTP basic filter chain (catchall).
     */
    private int filterOrder = SecurityProperties.ACCESS_OVERRIDE_ORDER - 1;

    public ResourceServerProperties(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }


    public String getClientId() {
        return clientId;
    }


    public String getClientSecret() {
        return clientSecret;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getResourceId() {
        return id;
    }


    public int getFilterOrder() {
        return filterOrder;
    }


    public void setFilterOrder(int filterOrder) {
        this.filterOrder = filterOrder;
    }
}
