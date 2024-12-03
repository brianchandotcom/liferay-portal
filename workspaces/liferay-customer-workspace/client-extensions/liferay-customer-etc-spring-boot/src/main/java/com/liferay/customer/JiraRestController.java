/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Jenny Chen
 */
@RestController
public class JiraRestController extends BaseRestController {

	public JiraRestController(
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.affected.versions.details}"
		)
		String jiraSecurityVulnerabilityFieldAffectedVersionsDetails,
		@Value("${liferay.customer.jira.security.vulnerability.field.category}")
			String jiraSecurityVulnerabilityFieldCategory,
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.customer.portal.description}"
		)
		String jiraSecurityVulnerabilityFieldCustomerPortalDescription,
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.customer.portal.summary}"
		)
		String jiraSecurityVulnerabilityFieldCustomerPortalSummary,
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.customer.publishing.date}"
		)
		String jiraSecurityVulnerabilityFieldCustomerPublishingDate,
		@Value("${liferay.customer.jira.security.vulnerability.field.cve.ids}")
			String jiraSecurityVulnerabilityFieldCVEIds,
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.cvss.base.score}"
		)
		String jiraSecurityVulnerabilityFieldCVSSBaseScore,
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.cvss.vector.string}"
		)
		String jiraSecurityVulnerabilityFieldCVSSVectorString,
		@Value("${liferay.customer.jira.security.vulnerability.field.cwe.ids}")
			String jiraSecurityVulnerabilityFieldCWEIds,
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.issue.classification}"
		)
		String jiraSecurityVulnerabilityFieldIssueClassification,
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.partner.publishing.date}"
		)
		String jiraSecurityVulnerabilityFieldPartnerPublishingDate,
		@Value(
			"${liferay.customer.jira.security.vulnerability.field.publishing.status}"
		)
		String jiraSecurityVulnerabilityFieldPublishingStatus,
		@Value("${liferay.customer.jira.security.vulnerability.field.severity}")
			String jiraSecurityVulnerabilityFieldSeverity) {

		_jiraSecurityVulnerabilityFieldAffectedVersionsDetails =
			jiraSecurityVulnerabilityFieldAffectedVersionsDetails;
		_jiraSecurityVulnerabilityFieldCategory =
			jiraSecurityVulnerabilityFieldCategory;
		_jiraSecurityVulnerabilityFieldCustomerPortalDescription =
			jiraSecurityVulnerabilityFieldCustomerPortalDescription;
		_jiraSecurityVulnerabilityFieldCustomerPortalSummary =
			jiraSecurityVulnerabilityFieldCustomerPortalSummary;
		_jiraSecurityVulnerabilityFieldCustomerPublishingDate =
			jiraSecurityVulnerabilityFieldCustomerPublishingDate;
		_jiraSecurityVulnerabilityFieldCVEIds =
			jiraSecurityVulnerabilityFieldCVEIds;
		_jiraSecurityVulnerabilityFieldCVSSBaseScore =
			jiraSecurityVulnerabilityFieldCVSSBaseScore;
		_jiraSecurityVulnerabilityFieldCVSSVectorString =
			jiraSecurityVulnerabilityFieldCVSSVectorString;
		_jiraSecurityVulnerabilityFieldCWEIds =
			jiraSecurityVulnerabilityFieldCWEIds;
		_jiraSecurityVulnerabilityFieldIssueClassification =
			jiraSecurityVulnerabilityFieldIssueClassification;
		_jiraSecurityVulnerabilityFieldPartnerPublishingDate =
			jiraSecurityVulnerabilityFieldPartnerPublishingDate;
		_jiraSecurityVulnerabilityFieldPublishingStatus =
			jiraSecurityVulnerabilityFieldPublishingStatus;
		_jiraSecurityVulnerabilityFieldSeverity =
			jiraSecurityVulnerabilityFieldSeverity;

		_securityVulnerabilitiesIssueFields = new String[] {
			_FIELD_COMPONENTS, _FIELD_FIX_VERSIONS, _FIELD_ISSUE_KEY,
			_FIELD_VERSIONS,
			jiraSecurityVulnerabilityFieldAffectedVersionsDetails,
			jiraSecurityVulnerabilityFieldCategory,
			jiraSecurityVulnerabilityFieldCustomerPortalDescription,
			jiraSecurityVulnerabilityFieldCustomerPortalSummary,
			jiraSecurityVulnerabilityFieldCustomerPublishingDate,
			jiraSecurityVulnerabilityFieldCVEIds,
			jiraSecurityVulnerabilityFieldCVSSBaseScore,
			jiraSecurityVulnerabilityFieldCVSSVectorString,
			jiraSecurityVulnerabilityFieldCWEIds,
			jiraSecurityVulnerabilityFieldIssueClassification,
			jiraSecurityVulnerabilityFieldPartnerPublishingDate,
			jiraSecurityVulnerabilityFieldPublishingStatus,
			jiraSecurityVulnerabilityFieldSeverity
		};
	}

	@RequestMapping(
		method = RequestMethod.GET,
		path = "/jira/security-vulnerabilities/versions"
	)
	public ResponseEntity<String> get() throws Exception {
		try {
			JSONArray jsonArray = _getVersionsJSONArray(
				_jiraSecurityVulnerabilityProject);

			JSONArray responseJSONArray = _flattenJSONArray(jsonArray);

			return new ResponseEntity<>(
				responseJSONArray.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/jira/issue/{issueKey}")
	public ResponseEntity<String> get(@PathVariable("issueKey") String issueKey)
		throws Exception {

		try {
			if (!issueKey.startsWith(_jiraSecurityVulnerabilityProject)) {
				throw new PrincipalException();
			}

			JSONObject jsonObject = _getIssueJSONObject(issueKey);

			JSONObject responseJSONObject = _transformIssue(jsonObject);

			return new ResponseEntity<>(
				responseJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(
		method = RequestMethod.GET,
		path = "/jira/security-vulnerabilities/search"
	)
	public ResponseEntity<String> search(
			@RequestParam(defaultValue = "", required = false) String[]
				filterAffectedVersions,
			@RequestParam(defaultValue = "", required = false) String[]
				filterCategories,
			@RequestParam(defaultValue = "", required = false) String[]
				filterClassifications,
			@RequestParam(defaultValue = "", required = false) String[]
				filterFixVersions,
			@RequestParam(defaultValue = "", required = false) String[]
				filterSeverities,
			@RequestParam(defaultValue = "", required = false) String keywords,
			@RequestParam(defaultValue = "1", required = false) int page,
			@RequestParam(defaultValue = "15", required = false) int pageSize,
			@RequestParam(defaultValue = "DESC", required = false) String
				sortOrder)
		throws Exception {

		try {
			StringBundler sb = new StringBundler(49);

			sb.append("project = '");
			sb.append(_jiraSecurityVulnerabilityProject);
			sb.append("' AND ");
			sb.append(
				_getJQLCustomField(
					_jiraSecurityVulnerabilityFieldPublishingStatus));
			sb.append(" = 'Ready for Publishing'");

			if (_isPartner()) {
				sb.append(" AND ");
				sb.append(
					_getJQLCustomField(
						_jiraSecurityVulnerabilityFieldPartnerPublishingDate));
				sb.append(" <= now()");
			}
			else {
				sb.append(" AND ");
				sb.append(
					_getJQLCustomField(
						_jiraSecurityVulnerabilityFieldCustomerPublishingDate));
				sb.append(" <= now()");
			}

			if (ArrayUtil.isNotEmpty(filterAffectedVersions)) {
				sb.append(" AND ");
				sb.append(_FIELD_AFFECTED_VERSION);
				sb.append(" in ('");
				sb.append(StringUtil.merge(filterAffectedVersions, "','"));
				sb.append("')");
			}

			if (ArrayUtil.isNotEmpty(filterCategories)) {
				sb.append(" AND ");
				sb.append(
					_getJQLCustomField(
						_jiraSecurityVulnerabilityFieldCategory));
				sb.append(" in ('");
				sb.append(StringUtil.merge(filterCategories, "','"));
				sb.append("')");
			}

			if (ArrayUtil.isNotEmpty(filterClassifications)) {
				sb.append(" AND ");
				sb.append(
					_getJQLCustomField(
						_jiraSecurityVulnerabilityFieldIssueClassification));
				sb.append(" in ('");
				sb.append(StringUtil.merge(filterClassifications, "','"));
				sb.append("')");
			}

			if (ArrayUtil.isNotEmpty(filterFixVersions)) {
				sb.append(" AND ");
				sb.append(_FIELD_FIX_VERSION);
				sb.append(" in ('");
				sb.append(StringUtil.merge(filterFixVersions, "','"));
				sb.append("')");
			}

			if (ArrayUtil.isNotEmpty(filterSeverities)) {
				sb.append(" AND ");
				sb.append(
					_getJQLCustomField(
						_jiraSecurityVulnerabilityFieldSeverity));
				sb.append(" in ('");
				sb.append(StringUtil.merge(filterSeverities, "','"));
				sb.append("')");
			}

			if (Validator.isNotNull(keywords)) {
				sb.append(" AND (");
				sb.append(
					_getJQLCustomField(
						_jiraSecurityVulnerabilityFieldCustomerPortalSummary));
				sb.append(" ~ ");
				sb.append(StringUtil.quote(keywords));
				sb.append(" OR ");
				sb.append(
					_getJQLCustomField(_jiraSecurityVulnerabilityFieldCVEIds));
				sb.append(" ~ ");
				sb.append(StringUtil.quote(keywords));
				sb.append(")");
			}

			sb.append(" ORDER BY ");

			if (_isPartner()) {
				sb.append(
					_getJQLCustomField(
						_jiraSecurityVulnerabilityFieldPartnerPublishingDate));
			}
			else {
				sb.append(
					_getJQLCustomField(
						_jiraSecurityVulnerabilityFieldCustomerPublishingDate));
			}

			sb.append(" ");
			sb.append(sortOrder);
			sb.append(", ");
			sb.append(
				_getJQLCustomField(_jiraSecurityVulnerabilityFieldSeverity));
			sb.append(" ASC");

			JSONObject jsonObject = _search(
				sb.toString(), _securityVulnerabilitiesIssueFields,
				_calculateStartAt(page, pageSize), pageSize);

			JSONObject responseJSONObject = _transformSearchResults(jsonObject);

			return new ResponseEntity<>(
				responseJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private int _calculatePage(int startAt, int maxResults) {
		return (startAt / maxResults) + 1;
	}

	private int _calculateStartAt(int page, int pageSize) {
		return (page - 1) * pageSize;
	}

	private JSONArray _flattenJSONArray(JSONArray jsonArray) {
		JSONArray flattenedJSONArray = new JSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String fieldValue = jsonObject.getString("name");

			flattenedJSONArray.put(fieldValue);
		}

		return flattenedJSONArray;
	}

	private String _getCredentials() {
		String jiraUserNameAndJiraApiToken =
			_jiraAPIEmailAddress + StringPool.COLON + _jiraAPIToken;

		return "Basic " + Base64.encode(jiraUserNameAndJiraApiToken.getBytes());
	}

	private JSONObject _getIssueJSONObject(String issueKey) throws Exception {
		try {
			return new JSONObject(
				WebClient.create(
					_jiraURL
				).get(
				).uri(
					StringBundler.concat(_URL_REST_API_2, "/issue/", issueKey)
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).retrieve(
				).bodyToMono(
					String.class
				).block());
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

	private String _getJQLCustomField(String customField) {
		int pos = customField.indexOf(StringPool.UNDERLINE);

		return "cf[" + customField.substring(pos + 1) + "]";
	}

	private String _getJSONObjectFieldValue(JSONObject jsonObject) {
		if (jsonObject != null) {
			return jsonObject.optString("value");
		}

		return null;
	}

	private JSONArray _getVersionsJSONArray(String project) throws Exception {
		try {
			return new JSONArray(
				WebClient.create(
					_jiraURL
				).get(
				).uri(
					StringBundler.concat(
						_URL_REST_API_2, "/project/", project, "/versions")
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).retrieve(
				).bodyToMono(
					String.class
				).block());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch Jira versions with project " + project,
					exception);
			}
		}

		return null;
	}

	private boolean _isPartner() {
		return true;
	}

	private JSONObject _search(
			String jql, String[] returnFields, int startAt, int maxResults)
		throws Exception {

		try {
			return new JSONObject(
				WebClient.create(
					_jiraURL
				).get(
				).uri(
					uriBuilder -> uriBuilder.path(
						_URL_REST_API_2 + "/search"
					).queryParam(
						"jql", jql
					).queryParam(
						"fields", StringUtil.merge(returnFields)
					).queryParam(
						"maxResults", maxResults
					).queryParam(
						"startAt", startAt
					).build()
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).retrieve(
				).bodyToMono(
					String.class
				).block());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch Jira issues with jql " + jql, exception);
			}
		}

		return null;
	}

	private JSONObject _transformIssue(JSONObject issueJSONObject) {
		return new JSONObject(
		).put(
			"fields",
			_transformIssueFields(issueJSONObject.getJSONObject("fields"))
		).put(
			"key", issueJSONObject.getString(_FIELD_ISSUE_KEY)
		);
	}

	private JSONObject _transformIssueFields(JSONObject issueFieldsJSONObject) {
		return new JSONObject(
		).put(
			"affectedVersionsDetails",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldAffectedVersionsDetails)
		).put(
			"affectedVersions",
			_flattenJSONArray(
				issueFieldsJSONObject.getJSONArray(_FIELD_VERSIONS))
		).put(
			"category",
			_getJSONObjectFieldValue(
				issueFieldsJSONObject.optJSONObject(
					_jiraSecurityVulnerabilityFieldCategory))
		).put(
			"components",
			_flattenJSONArray(
				issueFieldsJSONObject.getJSONArray(_FIELD_COMPONENTS))
		).put(
			"customerPortalDescription",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldCustomerPortalDescription)
		).put(
			"customerPortalSummary",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldCustomerPortalSummary)
		).put(
			"customerPublishingDate",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldCustomerPublishingDate)
		).put(
			"cveIds",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldCVEIds)
		).put(
			"cvssBaseScore",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldCVSSBaseScore)
		).put(
			"cvssVectorString",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldCVSSVectorString)
		).put(
			"cweIds",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldCWEIds)
		).put(
			"fixVersions",
			_flattenJSONArray(
				issueFieldsJSONObject.getJSONArray(_FIELD_FIX_VERSIONS))
		).put(
			"issueClassification",
			_getJSONObjectFieldValue(
				issueFieldsJSONObject.optJSONObject(
					_jiraSecurityVulnerabilityFieldIssueClassification))
		).put(
			"partnerPublishingDate",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldPartnerPublishingDate)
		).put(
			"publishingStatus",
			_getJSONObjectFieldValue(
				issueFieldsJSONObject.optJSONObject(
					_jiraSecurityVulnerabilityFieldPublishingStatus))
		).put(
			"severity",
			_getJSONObjectFieldValue(
				issueFieldsJSONObject.optJSONObject(
					_jiraSecurityVulnerabilityFieldSeverity))
		);
	}

	private JSONObject _transformSearchResults(JSONObject resultsJSONObject) {
		JSONArray jsonArray = new JSONArray();

		JSONArray issuesJSONArray = resultsJSONObject.getJSONArray("issues");

		for (int i = 0; i < issuesJSONArray.length(); i++) {
			JSONObject issueJSONObject = issuesJSONArray.getJSONObject(i);

			jsonArray.put(_transformIssue(issueJSONObject));
		}

		return new JSONObject(
		).put(
			"issues", jsonArray
		).put(
			"page",
			_calculatePage(
				resultsJSONObject.getInt("startAt"),
				resultsJSONObject.getInt("maxResults"))
		).put(
			"pageSize", resultsJSONObject.getInt("maxResults")
		).put(
			"total", resultsJSONObject.getInt("total")
		);
	}

	private static final String _FIELD_AFFECTED_VERSION = "affectedVersion";

	private static final String _FIELD_COMPONENTS = "components";

	private static final String _FIELD_FIX_VERSION = "fixVersion";

	private static final String _FIELD_FIX_VERSIONS = "fixVersions";

	private static final String _FIELD_ISSUE_KEY = "key";

	private static final String _FIELD_VERSIONS = "versions";

	private static final String _URL_REST_API_2 = "/rest/api/2";

	private static final Log _log = LogFactory.getLog(JiraRestController.class);

	@Value("${liferay.customer.jira.api.email.address}")
	private String _jiraAPIEmailAddress;

	@Value("${liferay.customer.jira.api.token}")
	private String _jiraAPIToken;

	private final String _jiraSecurityVulnerabilityFieldAffectedVersionsDetails;
	private final String _jiraSecurityVulnerabilityFieldCategory;
	private final String
		_jiraSecurityVulnerabilityFieldCustomerPortalDescription;
	private final String _jiraSecurityVulnerabilityFieldCustomerPortalSummary;
	private final String _jiraSecurityVulnerabilityFieldCustomerPublishingDate;
	private final String _jiraSecurityVulnerabilityFieldCVEIds;
	private final String _jiraSecurityVulnerabilityFieldCVSSBaseScore;
	private final String _jiraSecurityVulnerabilityFieldCVSSVectorString;
	private final String _jiraSecurityVulnerabilityFieldCWEIds;
	private final String _jiraSecurityVulnerabilityFieldIssueClassification;
	private final String _jiraSecurityVulnerabilityFieldPartnerPublishingDate;
	private final String _jiraSecurityVulnerabilityFieldPublishingStatus;
	private final String _jiraSecurityVulnerabilityFieldSeverity;

	@Value("${liferay.customer.jira.security.vulnerability.project}")
	private String _jiraSecurityVulnerabilityProject;

	@Value("${liferay.customer.jira.url}")
	private String _jiraURL;

	private final String[] _securityVulnerabilitiesIssueFields;

}