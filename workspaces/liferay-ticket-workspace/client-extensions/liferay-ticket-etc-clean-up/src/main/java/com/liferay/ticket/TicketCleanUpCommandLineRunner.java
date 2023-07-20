/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ticket;

import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

/**
 * @author Gregory Amerson
 */
@Component
public class TicketCleanUpCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		OAuth2AuthorizedClient oAuth2AuthorizedClient =
			_authorizedClientServiceOAuth2AuthorizedClientManager.authorize(
				OAuth2AuthorizeRequest.withClientRegistrationId(
					"liferay-ticket-etc-clean-up-oauth-application-headless-server"
				).principal(
					"TicketCleanUpCommandLineRunner"
				).build());

		if (oAuth2AuthorizedClient == null) {
			_log.error("Unable to get OAuth 2 authorized client");

			return;
		}

		OAuth2AccessToken oAuth2AccessToken =
			oAuth2AuthorizedClient.getAccessToken();

		if (_log.isInfoEnabled()) {
			_log.info("Issued: " + oAuth2AccessToken.getIssuedAt());
			_log.info("Expires At: " + oAuth2AccessToken.getExpiresAt());
			_log.info("Scopes: " + oAuth2AccessToken.getScopes());
			_log.info("Token: " + oAuth2AccessToken.getTokenValue());
		}

		WebClient.Builder builder = WebClient.builder();

		WebClient webClient = builder.baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.AUTHORIZATION,
			"Bearer " + oAuth2AccessToken.getTokenValue()
		).build();

		TicketsResponse ticketsResponse = webClient.get(
		).uri(
			"/o/c/tickets/"
		).retrieve(
		).onStatus(
			HttpStatus::isError,
			response -> {
				_log.error("Unable to get tickets: " + response.statusCode());

				return Mono.error(new Exception());
			}
		).bodyToMono(
			TicketsResponse.class
		).block();

		if (_log.isInfoEnabled()) {
			_log.info("Amount of tickets: " + ticketsResponse.items.length);
		}

		Arrays.stream(
			ticketsResponse.items
		).filter(
			ticket ->
				(ticket.resolution != null) &&
				(Objects.equals(ticket.resolution.key, "duplicate") ||
				 Objects.equals(ticket.resolution.key, "done"))
		).map(
			ticket -> ticket.id
		).forEach(
			ticketId -> {
				try {
					if (_log.isInfoEnabled()) {
						_log.info("Deleting ticket: " + ticketId);
					}

					webClient.delete(
					).uri(
						"/o/c/tickets/{ticketId}", ticketId
					).retrieve(
					).onStatus(
						HttpStatus::isError,
						response -> {
							_log.error(
								"Unable to delete ticket: " +
									response.statusCode());

							return Mono.error(new Exception());
						}
					).toEntity(
						Void.class
					).block();
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			}
		);
	}

	private static final Log _log = LogFactory.getLog(
		TicketCleanUpCommandLineRunner.class);

	@Autowired
	private AuthorizedClientServiceOAuth2AuthorizedClientManager
		_authorizedClientServiceOAuth2AuthorizedClientManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	private static class Resolution {

		public String key;

	}

	private static class Ticket {

		public String id;
		public Resolution resolution;
		public String subject;

	}

	private static class TicketsResponse {

		public Ticket[] items;

	}

}