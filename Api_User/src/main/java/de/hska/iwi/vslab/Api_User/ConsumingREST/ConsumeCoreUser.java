package de.hska.iwi.vslab.Api_User.ConsumingREST;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration.ClientCredentials;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableOAuth2Client
public class ConsumeCoreUser {

    @Autowired
    OAuth2RestTemplate restTemplate2;

    private static final Logger log = LoggerFactory.getLogger(ConsumeCoreUser.class);
    RestTemplate restTemplate = new RestTemplate();


    public OAuth2ProtectedResourceDetails oAuth2ResourceDetails() {
        //BaseOAuth2ProtectedResourceDetails details = new BaseOAuth2ProtectedResourceDetails();
        //ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        
        details.setClientId("apiUserId");
        details.setClientSecret("apiUserSecret");
        details.setAccessTokenUri("http://oauthserver:8094/oauth/token");
        //details.setGrantType("client_credentials");
        List<String> scope = new ArrayList<>();
        scope.add("read");scope.add("write");
        details.setScope(scope);
        details.setAuthenticationScheme(AuthenticationScheme.header);
        details.setClientAuthenticationScheme(AuthenticationScheme.header);
        details.setId("1");
        details.setTokenName("Core_User");

        //log.info("OAUTH DETAILS" + clientId + clientSecret + details.getAccessTokenUri() + details.getGrantType());
        //details.set
        return details;
    }
 
    @Bean // has to be done at runtime because the authorization server would not be up otherwise
    public OAuth2RestTemplate  foo() {
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oAuth2ResourceDetails());
        
        /* To validate if required configurations are in place during startup */
        //oAuth2RestTemplate.getAccessToken();
        return oAuth2RestTemplate;
    }

    public User[] getAllUsers() {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getBaseUrl_core_user());
            OAuth2AccessToken token = restTemplate2.getAccessToken();
            //restTemplate2.
            return restTemplate2.getForObject(urlBuilder.getUserUrl(), User[].class);
        } catch (Exception e) {
            log.error("ERROR IN CONSUMECOREUSER ", e);
            throw e;
        }
    }

    public User getUser(String input) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getInputUrl(input));
            return restTemplate.getForObject(urlBuilder.getInputUrl(input), User.class);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void addUser(String firstname, String lastname, String username, String password, int roleId) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getBaseUrl_core_user());
            User user = new User(firstname, lastname, username, password, roleId);
            restTemplate.postForLocation(urlBuilder.getSlashURL_core(), user);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void updateUser(User user) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getUrlWithId_core(user.getId()));
            restTemplate.put(urlBuilder.getUrlWithId_core(user.getId()), user);
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void deleteAllUsers() {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getBaseUrl_core_user());
            restTemplate.delete(urlBuilder.getSlashURL_core());
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void deleteUser(int id) {
        try {
            UrlBuilder urlBuilder = new UrlBuilder();
            log.info("URL:" + urlBuilder.getUrlWithId_core(id));
            restTemplate.delete(urlBuilder.getUrlWithId_core(id));
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }



}