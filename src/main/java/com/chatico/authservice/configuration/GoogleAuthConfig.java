package com.chatico.authservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;


@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class GoogleAuthConfig {
    private final OAuth2ClientProperties oAuth2ClientProperties;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public GoogleAuthConfig(OAuth2ClientProperties oAuth2ClientProperties, 
                            ClientRegistrationRepository clientRegistrationRepository) {
        this.oAuth2ClientProperties = oAuth2ClientProperties;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/google").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login/google")
                .authorizationEndpoint()
                .baseUri("/login/google")
                .authorizationRequestResolver(googleAuthorizationRequestResolver())
                .and()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/google")
                .and()
                .userInfoEndpoint()
                .userService(googleOAuth2UserService());
    }

    @Bean
    public ServerOAuth2AuthorizationRequestResolver googleAuthorizationRequestResolver() {
        return new DefaultServerOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository(),
                authorizationRequest -> {
                    OAuth2AuthorizationRequest.Builder builder = OAuth2AuthorizationRequest.from(authorizationRequest);
                    builder.authorizationUri("https://accounts.google.com/o/oauth2/auth");
                    builder.tokenUri("https://www.googleapis.com/oauth2/v3/token");
                    builder.redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}");
                    builder.clientId(oAuth2ClientProperties.getRegistration().get("google").getClientId());
                    builder.scopes("openid", "profile", "email");
                    return builder.build();
                },
                authorizationRequest -> {
                    OAuth2AuthorizationRequest.Builder builder = OAuth2AuthorizationRequest.from(authorizationRequest);
                    builder.authorizationUri("https://accounts.google.com/o/oauth2/auth");
                    builder.tokenUri("https://www.googleapis.com/oauth2/v3/token");
                    builder.redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}");
                    builder.clientId(oAuth2ClientProperties.getRegistration().get("google").getClientId());
                    builder.scopes("openid", "profile", "email");
                    return builder.build();
                }
        );
    }

    private Object clientRegistrationRepository() {
    }

    @Bean
    public WebClient googleWebClient(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepository, authorizedClientRepository);
        oauth2.setDefaultClientRegistrationId("google");
        return WebClient.builder()
                .filter(oauth2)
                .build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository authorizedClientRepository) {
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken()
                .clientCredentials()
                .password()
                .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public GoogleOAuth2UserService googleOAuth2UserService() {
        return new GoogleOAuth2UserService();
    }
}
