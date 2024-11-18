/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Jenny Chen
 */
@Component
public class JiraWebService {

	public String getJiraIssue(String issueKey) throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append(_URL_REST_API_2);
		sb.append("/issue/");
		sb.append(issueKey);

		try {
			JSONObject jsonObject = new JSONObject(
				WebClient.create(
					_jiraURL
				).get(
				).uri(
					sb.toString()
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).retrieve(
				).bodyToMono(
					String.class
				).block());

			jsonObject = _processResponse(jsonObject);

			return jsonObject.toString();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch Jira issue with key " + issueKey,
					exception);
			}
		}

		return null;
	}

	public String getJiraSearch(String keywords, String role) throws Exception {
		StringBundler sb = new StringBundler(12);

		String[] projects = StringUtil.split(_jiraSecurityProjects);

		sb.append("project in ('");
		sb.append(StringUtil.merge(projects, "','"));
		sb.append("')");

		sb.append(" AND 'Publishing Status' = 'Ready for Publishing'");

		if (role.equals("partner")) {
			sb.append(" AND 'Partner Publishing Date' <= now()");
		}
		else {
			sb.append(" AND 'Customer Publishing Date' <= now()");
		}

		if (Validator.isNotNull(keywords)) {
			sb.append(" AND (description ~ ");
			sb.append(StringUtil.quote(keywords));
			sb.append(" OR summary ~ ");
			sb.append(StringUtil.quote(keywords));
			sb.append(" OR 'CVE IDs' ~ ");
			sb.append(StringUtil.quote(keywords));
			sb.append(")");
		}

		String[] jiraSearchFields = {
			_JIRA_FIELD_AFFECTS_VERSIONS, _JIRA_FIELD_COMPONENTS,
			_JIRA_FIELD_ISSUE_KEY, _jiraSecurityFieldAffectedVersionsDetails,
			_jiraSecurityFieldCategory,
			_jiraSecurityFieldCustomerPortalDescription,
			_jiraSecurityFieldCustomerPortalSummary,
			_jiraSecurityFieldCustomerPublishingDate, _jiraSecurityFieldCVEIds,
			_jiraSecurityFieldCVSSBaseScore, _jiraSecurityFieldCVSSVectorString,
			_jiraSecurityFieldCWEIds, _jiraSecurityFieldIssueClassification,
			_jiraSecurityFieldPartnerPublishingDate,
			_jiraSecurityFieldPublishingStatus, _jiraSecurityFieldSeverity
		};

		try {
			JSONObject jsonObject = new JSONObject(
				WebClient.create(
					_jiraURL
				).get(
				).uri(
					uriBuilder -> uriBuilder.path(
						_URL_REST_API_2 + "/search"
					).queryParam(
						"jql", sb.toString()
					).queryParam(
						"fields", StringUtil.merge(jiraSearchFields)
					).build()
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).retrieve(
				).bodyToMono(
					String.class
				).block());

			jsonObject = _processResponse(jsonObject);

			return jsonObject.toString();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch Jira issues with jql " + sb.toString(),
					exception);
			}
		}

		return null;
	}

	private String _getCredentials() {
		String jiraUserNameAndJiraApiToken =
			_jiraAPIEmailAddress + StringPool.COLON + _jiraAPIToken;

		return "Basic " + Base64.encode(jiraUserNameAndJiraApiToken.getBytes());
	}

	private JSONArray _processFieldsJSONArray(JSONArray fieldsJSONArray) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject jsonObject = fieldsJSONArray.getJSONObject(i);

			JSONObject nameJSONObject = new JSONObject();

			nameJSONObject.put("name", jsonObject.getString("name"));

			jsonArray.put(nameJSONObject);
		}

		return jsonArray;
	}

	private String _processFieldsJSONObject(JSONObject fieldsJSONObject) {
		if (fieldsJSONObject != null) {
			return fieldsJSONObject.optString("value");
		}

		return null;
	}

	private JSONObject _processIssue(JSONObject issueJSONObject) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"fields",
			_processIssueFields(issueJSONObject.getJSONObject("fields"))
		).put(
			"key", issueJSONObject.getString(_JIRA_FIELD_ISSUE_KEY)
		);

		return jsonObject;
	}

	private JSONObject _processIssueFields(JSONObject issueFieldsJSONObject) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"affectedVersionsDetails",
			issueFieldsJSONObject.optString(
				_jiraSecurityFieldAffectedVersionsDetails)
		).put(
			"affectsVersion",
			_processFieldsJSONArray(
				issueFieldsJSONObject.getJSONArray(
					_JIRA_FIELD_AFFECTS_VERSIONS))
		).put(
			"category",
			_processFieldsJSONObject(
				issueFieldsJSONObject.optJSONObject(_jiraSecurityFieldCategory))
		).put(
			"components",
			_processFieldsJSONArray(
				issueFieldsJSONObject.getJSONArray(_JIRA_FIELD_COMPONENTS))
		).put(
			"customerPortalDescription",
			issueFieldsJSONObject.optString(
				_jiraSecurityFieldCustomerPortalDescription)
		).put(
			"customerPortalSummary",
			issueFieldsJSONObject.optString(
				_jiraSecurityFieldCustomerPortalSummary)
		).put(
			"customerPublishingDate",
			issueFieldsJSONObject.optString(
				_jiraSecurityFieldCustomerPublishingDate)
		).put(
			"cveIds", issueFieldsJSONObject.optString(_jiraSecurityFieldCVEIds)
		).put(
			"cvssBaseScore",
			issueFieldsJSONObject.optString(_jiraSecurityFieldCVSSBaseScore)
		).put(
			"cvssVectorString",
			issueFieldsJSONObject.optString(_jiraSecurityFieldCVSSVectorString)
		).put(
			"cweIds", issueFieldsJSONObject.optString(_jiraSecurityFieldCWEIds)
		).put(
			"issueClassification",
			_processFieldsJSONObject(
				issueFieldsJSONObject.optJSONObject(
					_jiraSecurityFieldIssueClassification))
		).put(
			"partnerPublishingDate",
			issueFieldsJSONObject.optString(
				_jiraSecurityFieldPartnerPublishingDate)
		).put(
			"publishingStatus",
			_processFieldsJSONObject(
				issueFieldsJSONObject.optJSONObject(
					_jiraSecurityFieldPublishingStatus))
		).put(
			"severity",
			_processFieldsJSONObject(
				issueFieldsJSONObject.optJSONObject(_jiraSecurityFieldSeverity))
		);

		return jsonObject;
	}

	private JSONObject _processResponse(JSONObject responseJSONObject) {
		JSONObject jsonObject = new JSONObject();

		if (responseJSONObject.has("issues")) {
			JSONArray jsonArray = new JSONArray();

			JSONArray issuesJSONArray = responseJSONObject.getJSONArray(
				"issues");

			for (int i = 0; i < issuesJSONArray.length(); i++) {
				JSONObject issueJSONObject = issuesJSONArray.getJSONObject(i);

				jsonArray.put(_processIssue(issueJSONObject));
			}

			jsonObject.put(
				"issues", jsonArray
			).put(
				"maxResults", responseJSONObject.getInt("maxResults")
			).put(
				"startAt", responseJSONObject.getInt("startAt")
			).put(
				"total", responseJSONObject.getInt("total")
			);
		}
		else {
			jsonObject = _processIssue(responseJSONObject);
		}

		return jsonObject;
	}

	private static final String _JIRA_FIELD_AFFECTS_VERSIONS = "versions";

	private static final String _JIRA_FIELD_COMPONENTS = "components";

	private static final String _JIRA_FIELD_ISSUE_KEY = "key";

	private static final String _URL_REST_API_2 = "/rest/api/2";

	private static final Log _log = LogFactory.getLog(JiraWebService.class);

	@Value("${liferay.customer.jira.api.email.address}")
	private String _jiraAPIEmailAddress;

	@Value("${liferay.customer.jira.api.token}")
	private String _jiraAPIToken;

	@Value("${liferay.customer.jira.security.field.affected.versions.details}")
	private String _jiraSecurityFieldAffectedVersionsDetails;

	@Value("${liferay.customer.jira.security.field.category}")
	private String _jiraSecurityFieldCategory;

	@Value(
		"${liferay.customer.jira.security.field.customer.portal.description}"
	)
	private String _jiraSecurityFieldCustomerPortalDescription;

	@Value("${liferay.customer.jira.security.field.customer.portal.summary}")
	private String _jiraSecurityFieldCustomerPortalSummary;

	@Value("${liferay.customer.jira.security.field.customer.publishing.date}")
	private String _jiraSecurityFieldCustomerPublishingDate;

	@Value("${liferay.customer.jira.security.field.cve.ids}")
	private String _jiraSecurityFieldCVEIds;

	@Value("${liferay.customer.jira.security.field.cvss.base.score}")
	private String _jiraSecurityFieldCVSSBaseScore;

	@Value("${liferay.customer.jira.security.field.cvss.vector.string}")
	private String _jiraSecurityFieldCVSSVectorString;

	@Value("${liferay.customer.jira.security.field.cwe.ids}")
	private String _jiraSecurityFieldCWEIds;

	@Value("${liferay.customer.jira.security.field.issue.classification}")
	private String _jiraSecurityFieldIssueClassification;

	@Value("${liferay.customer.jira.security.field.partner.publishing.date}")
	private String _jiraSecurityFieldPartnerPublishingDate;

	@Value("${liferay.customer.jira.security.field.publishing.status}")
	private String _jiraSecurityFieldPublishingStatus;

	@Value("${liferay.customer.jira.security.field.severity}")
	private String _jiraSecurityFieldSeverity;

	@Value("${liferay.customer.jira.security.projects}")
	private String _jiraSecurityProjects;

	@Value("${liferay.customer.jira.url}")
	private String _jiraURL;

}