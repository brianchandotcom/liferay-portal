/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.zendesk.service;

import com.liferay.customer.zendesk.model.ZendeskOrganization;
import com.liferay.customer.zendesk.model.ZendeskTicket;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Amos Fong
 */
@Component
public class ZendeskWebService {

	public ZendeskOrganization getZendeskOrganization(
			long zendeskOrganizationId)
		throws Exception {

		JSONObject jsonObject = new JSONObject(
			WebClient.create(
				_zendeskURL
			).get(
			).uri(
				"/api/v2/organizations/" + zendeskOrganizationId + ".json"
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, _zendeskAuthorization
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		return new ZendeskOrganization(
			jsonObject.getJSONObject("organization"));
	}

	public ZendeskTicket getZendeskTicket(long zendeskTicketId)
		throws Exception {

		JSONObject jsonObject = new JSONObject(
			WebClient.create(
				_zendeskURL
			).get(
			).uri(
				"/api/v2/tickets/" + zendeskTicketId + ".json"
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, _zendeskAuthorization
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		return new ZendeskTicket(jsonObject.getJSONObject("ticket"));
	}

	@PostConstruct
	public void init() throws Exception {
		String zendeskCredentials =
			_zendeskAPIEmailAddress + "/token:" + _zendeskAPIToken;

		Base64.Encoder encoder = Base64.getEncoder();

		String encodedZendeskCredentials = new String(
			encoder.encode(zendeskCredentials.getBytes("UTF-8")), "UTF-8");

		_zendeskAuthorization = "Basic " + encodedZendeskCredentials;
	}

	@Value("${liferay.customer.zendesk.api.email.address}")
	private String _zendeskAPIEmailAddress;

	@Value("${liferay.customer.zendesk.api.token}")
	private String _zendeskAPIToken;

	private String _zendeskAuthorization;

	@Value("${liferay.customer.zendesk.url}")
	private String _zendeskURL;

}