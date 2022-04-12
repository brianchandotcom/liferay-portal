package com.liferay.custom.rest.service.config;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.amadeus.Amadeus;

@Configuration
public class CustomRestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${LCP_SERVICE_DOMAIN}")
	String lcpServiceDomain;

	@Value("${LCP_PROJECT_ID}")
	String lcpProjectId;

	@Value("${WEBSERVER_SERVICE_HOST}")
	String webserverServiceHost;

	@Value("${LIFERAY_OAUTH2_USER_AGENT_CLIENT_ID}")
	String clientId;

	@Value("${LIFERAY_OAUTH2_INTROSPECTION_URI}")
	String introspectionUri;

	@Value("${AMADEUS_CLIENT_ID}")
	String amadeusClientId;

	@Value("${AMADEUS_CLIENT_SECRET}")
	String amadeusClientSecret;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(
			Collections.singletonList("https://" + lcpDomain())
		);
		configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors(
		).and(
		).authorizeRequests(
		).antMatchers(
			"/"
		).permitAll(
		).anyRequest(
		).authenticated(
		).and(
		).oauth2ResourceServer(
			oauth2 -> oauth2.opaqueToken().introspector(
				new CustomSpringOpaqueTokenIntrospector(
					introspectionUri, clientId)
			)
		);
	}

	private static final Logger logger = LoggerFactory.getLogger(CustomRestSecurityConfig.class);

	@Bean
	Amadeus amadeusClient() {
		logger.info("amadeusClientId: " + amadeusClientId);
		logger.info("amadeusClientSecret: " + amadeusClientSecret);
		return Amadeus.builder(amadeusClientId, amadeusClientSecret).build();
	}

	@Bean
	String lcpDomain() {
		String lcpDomain = null;

		if ((lcpServiceDomain != null && !lcpServiceDomain.isEmpty()) &&
			(lcpProjectId != null && !lcpProjectId.isEmpty())) {

			lcpDomain = lcpProjectId.concat(".").concat(lcpServiceDomain);
		}

		if (lcpDomain != null && webserverServiceHost != null &&
			!webserverServiceHost.isEmpty()) {

			lcpDomain = "webserver-".concat(lcpDomain);
		}

		return lcpDomain;
	}

	private static class CustomSpringOpaqueTokenIntrospector extends SpringOpaqueTokenIntrospector {

		public CustomSpringOpaqueTokenIntrospector(String introspectionUri, String clientId) {
			super(introspectionUri, new RestTemplate());
			setRequestEntityConverter(customRequestEntityConverter(URI.create(introspectionUri), clientId));
		}

		private static Converter<String, RequestEntity<?>> customRequestEntityConverter(URI introspectionUri,
				String clientId) {
			return (token) -> {
				HttpHeaders headers = requestHeaders();
				MultiValueMap<String, String> body = requestBody(clientId, token);
				return new RequestEntity<>(body, headers, HttpMethod.POST, introspectionUri);
			};
		}

		private static HttpHeaders requestHeaders() {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			return headers;
		}

		private static MultiValueMap<String, String> requestBody(String clientId, String token) {
			MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
			body.add("client_id", clientId);
			body.add("token", token);
			body.add("token_type_hint", "access_token");
			return body;
		}

	}

}