/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Ricardo Mariz
 */
@Component
public class HubSpotService extends BaseService {

	public String createCompany(
			String name, String numberOfEmployees, String phone,
			String websiteURL)
		throws Exception {

		JSONObject bodyJSONObject = new JSONObject(
		).put(
			"properties",
			new JSONObject(
			).put(
				"domain", websiteURL
			).put(
				"name", name
			).put(
				"numberofemployees", numberOfEmployees
			).put(
				"phone", phone
			)
		);

		return post(
			getAuthorization(), bodyJSONObject.toString(),
			UriComponentsBuilder.fromUriString(
				_hubspotAuthURL
			).path(
				"/crm/v3/objects/companies"
			).build(
			).toUri());
	}

	public String createContact(
			String email, String firstName, String lastName, String phone)
		throws Exception {

		String contact = post(
			getAuthorization(),
			new JSONObject(
			).put(
				"properties",
				new JSONObject(
				).put(
					"email", email
				).put(
					"firstname", firstName
				).put(
					"lastname", lastName
				).put(
					"phone", phone
				)
			).toString(),
			UriComponentsBuilder.fromUriString(
				_hubspotAuthURL
			).path(
				"/crm/v3/objects/contacts"
			).build(
			).toUri());

		if (_log.isInfoEnabled()) {
			_log.info("Contact created: " + contact);
		}

		return contact;
	}

	public void createLead(
			String email, String firstName, String lastName, String phone,
			String companyName, String numberOfEmployees, String websiteURL)
		throws Exception {

		String contactId = getOrCreateContact(
			email, firstName, lastName, phone
		).getString(
			"id"
		);

		createLeadWithContactAssociation(
			contactId, "WARM", "Lead Capture for " + companyName,
			"NEW_BUSINESS");
	}

	public void createLeadWithContactAssociation(
			String contactId, String leadLabel, String leadName,
			String leadType)
		throws Exception {

		post(
			getAuthorization(),
			new JSONObject(
			).put(
				"associations",
				new JSONArray(
				).put(
					new JSONObject(
					).put(
						"to",
						new JSONObject(
						).put(
							"id", contactId
						)
					).put(
						"types",
						new JSONArray(
						).put(
							new JSONObject(
							).put(
								"associationCategory", "HUBSPOT_DEFINED"
							).put(
								"associationTypeId",
								_CONTACT_TO_LEAD_ASSOCIATION_TYPE_ID
							)
						)
					)
				)
			).put(
				"properties",
				new JSONObject(
				).put(
					"hs_lead_label", leadLabel
				).put(
					"hs_lead_name", leadName
				).put(
					"hs_lead_type", leadType
				)
			).toString(),
			UriComponentsBuilder.fromUriString(
				_hubspotAuthURL
			).path(
				"/crm/v3/objects/leads"
			).build(
			).toUri());
	}

	public JSONObject findCompanyByName(String name) throws Exception {
		String response = post(
			getAuthorization(),
			new JSONObject(
			).put(
				"filterGroups",
				new JSONArray(
				).put(
					new JSONObject(
					).put(
						"filters",
						new JSONArray(
						).put(
							new JSONObject(
							).put(
								"operator", "EQ"
							).put(
								"propertyName", "name"
							).put(
								"value", name
							)
						)
					)
				)
			).put(
				"limit", 10
			).toString(),
			UriComponentsBuilder.fromUriString(
				_hubspotAuthURL
			).path(
				"/crm/v3/objects/companies/search"
			).build(
			).toUri());

		JSONArray resultsJSONArray = new JSONObject(
			response
		).optJSONArray(
			"results"
		);

		if ((resultsJSONArray != null) && !resultsJSONArray.isEmpty()) {
			return resultsJSONArray.getJSONObject(0);
		}

		return null;
	}

	public JSONObject findContactByEmail(String email) throws Exception {
		String response = post(
			getAuthorization(),
			new JSONObject(
			).put(
				"filterGroups",
				new JSONArray(
				).put(
					new JSONObject(
					).put(
						"filters",
						new JSONArray(
						).put(
							new JSONObject(
							).put(
								"operator", "EQ"
							).put(
								"propertyName", "email"
							).put(
								"value", email
							)
						)
					)
				)
			).put(
				"limit", 10
			).toString(),
			UriComponentsBuilder.fromUriString(
				_hubspotAuthURL
			).path(
				"/crm/v3/objects/contacts/search"
			).build(
			).toUri());

		JSONArray resultsJSONArray = new JSONObject(
			response
		).optJSONArray(
			"results"
		);

		if ((resultsJSONArray != null) && !resultsJSONArray.isEmpty()) {
			return resultsJSONArray.getJSONObject(0);
		}

		return null;
	}

	public void getOrCreateCompany(
			String name, String numberOfEmployees, String phone,
			String websiteURL)
		throws Exception {

		JSONObject existingJSONObject = findCompanyByName(name);

		if (existingJSONObject != null) {
			if (_log.isInfoEnabled()) {
				_log.info("Existing company: " + existingJSONObject);
			}

			return;
		}

		JSONObject createdJSONObject = new JSONObject(
			createCompany(name, numberOfEmployees, phone, websiteURL));

		if (_log.isInfoEnabled()) {
			_log.info("New company created: " + createdJSONObject);
		}
	}

	public JSONObject getOrCreateContact(
			String email, String firstName, String lastName, String phone)
		throws Exception {

		JSONObject existingJSONObject = findContactByEmail(email);

		if (existingJSONObject != null) {
			if (_log.isInfoEnabled()) {
				_log.info("Existing contact returned: " + existingJSONObject);
			}

			return existingJSONObject;
		}

		JSONObject createdJSONObject = new JSONObject(
			createContact(email, firstName, lastName, phone));

		if (_log.isInfoEnabled()) {
			_log.info("New contact created: " + createdJSONObject);
		}

		return createdJSONObject;
	}

	protected String getAuthorization() throws Exception {
		if (_authToken == null) {
			throw new Exception("Unable to get token authorization");
		}

		return "Bearer " + _authToken;
	}

	private static final int _CONTACT_TO_LEAD_ASSOCIATION_TYPE_ID = 578;

	private static final Log _log = LogFactory.getLog(HubSpotService.class);

	@Value("${liferay.hubspot.auth.token}")
	private String _authToken;

	@Value("${liferay.hubspot.auth.url}")
	private String _hubspotAuthURL;

}