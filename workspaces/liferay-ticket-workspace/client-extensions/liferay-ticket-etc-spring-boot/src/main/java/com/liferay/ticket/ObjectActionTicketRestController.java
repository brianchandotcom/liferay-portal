/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ticket;

import com.liferay.portal.search.rest.client.dto.v1_0.Suggestion;
import com.liferay.portal.search.rest.client.dto.v1_0.SuggestionsContributorConfiguration;
import com.liferay.portal.search.rest.client.dto.v1_0.SuggestionsContributorResults;
import com.liferay.portal.search.rest.client.pagination.Page;
import com.liferay.portal.search.rest.client.resource.v1_0.SuggestionResource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Allen Ziegenfus
 */
@RestController
public class ObjectActionTicketRestController extends BaseRestController {

	@PostMapping("/object/action/ticket")
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		log(jwt, _log, json);

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryDTOJ3Y7TicketJSONObject =
			jsonObject.getJSONObject("objectEntryDTOJ3Y7Ticket");

		JSONObject propertiesJSONObject =
			objectEntryDTOJ3Y7TicketJSONObject.getJSONObject("properties");

		propertiesJSONObject.put(
			"suggestions",
			_getSuggestionsJSONArray(
				propertiesJSONObject.getString("subject")));

		JSONObject ticketStatusJSONObject = propertiesJSONObject.getJSONObject(
			"ticketStatus");

		ticketStatusJSONObject.put("key", "queued");
		ticketStatusJSONObject.remove("name");

		if (_log.isInfoEnabled()) {
			_log.info("Properties: " + propertiesJSONObject.toString(4));
		}

		WebClient.create(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).patch(
		).uri(
			"/o/c/j3y7tickets/" +
				objectEntryDTOJ3Y7TicketJSONObject.getString("id")
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).bodyValue(
			propertiesJSONObject.toString()
		).retrieve(
		).bodyToMono(
			Void.class
		).subscribe();

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private SuggestionsContributorConfiguration
		_getSuggestionsContributorConfiguration() {

		return new SuggestionsContributorConfiguration() {
			{
				attributes = new JSONObject(
				).put(
					"includeAssetSearchSummary", true
				).put(
					"includeassetURL", true
				).put(
					"sxpBlueprintId", 3628599
				);
				contributorName = "sxpBlueprint";
				displayGroupName = "Public Nav Search Recommendations";
				size = 3;
			}
		};
	}

	private JSONArray _getSuggestionsJSONArray(String subject) {
		JSONArray suggestionsJSONArray = new JSONArray();

		try {
			SuggestionResource.Builder dataDefinitionResourceBuilder =
				SuggestionResource.builder();

			SuggestionResource suggestionResource =
				dataDefinitionResourceBuilder.header(
					HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
				).header(
					HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
				).header(
					HttpHeaders.USER_AGENT,
					ObjectActionTicketRestController.class.getName()
				).endpoint(
					"learn.liferay.com", 443, "https"
				).build();

			Page<SuggestionsContributorResults> page =
				suggestionResource.postSuggestionsPage(
					"https://learn.liferay.com", "/search", 3190049L, "", 1434L,
					"this-site", subject,
					new SuggestionsContributorConfiguration[] {
						_getSuggestionsContributorConfiguration()
					});

			for (SuggestionsContributorResults suggestionsContributorResults :
					page.getItems()) {

				for (Suggestion suggestion :
						suggestionsContributorResults.getSuggestions()) {

					JSONObject jsonObject = new JSONObject(
						String.valueOf(suggestion.getAttributes()));

					suggestionsJSONArray.put(
						new JSONObject(
						).put(
							"assetURL",
							"https://learn.liferay.com" +
								jsonObject.getString("assetURL")
						).put(
							"text", suggestion.getText()
						));
				}
			}
		}
		catch (Exception exception) {
			_log.error("Unable to get suggestions", exception);

			suggestionsJSONArray.put(
				new JSONObject(
				).put(
					"assetURL", "https://learn.liferay.com"
				).put(
					"text", "learn.liferay.com"
				));
		}

		return suggestionsJSONArray;
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionTicketRestController.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}