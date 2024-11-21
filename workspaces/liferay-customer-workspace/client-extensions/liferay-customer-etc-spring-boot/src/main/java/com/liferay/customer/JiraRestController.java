/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.customer.service.JiraService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jenny Chen
 */
@RestController
public class JiraRestController extends BaseRestController {

	public JiraRestController() {
		_securityVulnerabilitiesIssueFields = new String[] {
			_JIRA_FIELD_COMPONENTS, _JIRA_FIELD_FIX_VERSIONS,
			_JIRA_FIELD_ISSUE_KEY, _JIRA_FIELD_VERSIONS,
			_jiraSecurityVulnerabilityFieldAffectedVersionsDetails,
			_jiraSecurityVulnerabilityFieldCategory,
			_jiraSecurityVulnerabilityFieldCustomerPortalDescription,
			_jiraSecurityVulnerabilityFieldCustomerPortalSummary,
			_jiraSecurityVulnerabilityFieldCustomerPublishingDate,
			_jiraSecurityVulnerabilityFieldCVEIds,
			_jiraSecurityVulnerabilityFieldCVSSBaseScore,
			_jiraSecurityVulnerabilityFieldCVSSVectorString,
			_jiraSecurityVulnerabilityFieldCWEIds,
			_jiraSecurityVulnerabilityFieldIssueClassification,
			_jiraSecurityVulnerabilityFieldPartnerPublishingDate,
			_jiraSecurityVulnerabilityFieldPublishingStatus,
			_jiraSecurityVulnerabilityFieldSeverity
		};
	}

	@RequestMapping(
		method = RequestMethod.GET,
		path = "/jira/security-vulnerabilities/versions"
	)
	public ResponseEntity<String> get() throws Exception {
		try {
			JSONArray jsonArray = _jiraService.getVersions(
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

			JSONObject jsonObject = _jiraService.getIssue(issueKey);

			JSONObject responseJSONObject = _translateIssue(jsonObject);

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
			@RequestParam(defaultValue = "", required = false) String keywords)
		throws Exception {

		try {
			StringBundler sb = new StringBundler(10);

			sb.append("project = '");
			sb.append(_jiraSecurityVulnerabilityProject);
			sb.append("' AND 'Publishing Status' = 'Ready for Publishing'");

			if (_isPartner()) {
				sb.append(" AND 'Partner Publishing Date' <= now()");
			}
			else {
				sb.append(" AND 'Customer Publishing Date' <= now()");
			}

			if (Validator.isNotNull(keywords)) {
				sb.append(" AND ('Customer Portal Summary' ~ ");
				sb.append(StringUtil.quote(keywords));
				sb.append(" OR 'CVE IDs' ~ ");
				sb.append(StringUtil.quote(keywords));
				sb.append(")");
			}

			JSONObject jsonObject = _jiraService.search(
				sb.toString(), _securityVulnerabilitiesIssueFields);

			JSONObject responseJSONObject = _translateSearchResults(jsonObject);

			return new ResponseEntity<>(
				responseJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
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

	private String _getJSONObjectFieldValue(JSONObject jsonObject) {
		if (jsonObject != null) {
			return jsonObject.optString("value");
		}

		return null;
	}

	private boolean _isPartner() {
		return true;
	}

	private JSONObject _translateIssue(JSONObject issueJSONObject) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"fields",
			_translateIssueFields(issueJSONObject.getJSONObject("fields"))
		).put(
			"key", issueJSONObject.getString(_JIRA_FIELD_ISSUE_KEY)
		);

		return jsonObject;
	}

	private JSONObject _translateIssueFields(JSONObject issueFieldsJSONObject) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"affectedVersionsDetails",
			issueFieldsJSONObject.optString(
				_jiraSecurityVulnerabilityFieldAffectedVersionsDetails)
		).put(
			"affectsVersion",
			_flattenJSONArray(
				issueFieldsJSONObject.getJSONArray(_JIRA_FIELD_VERSIONS))
		).put(
			"category",
			_getJSONObjectFieldValue(
				issueFieldsJSONObject.optJSONObject(
					_jiraSecurityVulnerabilityFieldCategory))
		).put(
			"components",
			_flattenJSONArray(
				issueFieldsJSONObject.getJSONArray(_JIRA_FIELD_COMPONENTS))
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
				issueFieldsJSONObject.getJSONArray(_JIRA_FIELD_FIX_VERSIONS))
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

		return jsonObject;
	}

	private JSONObject _translateSearchResults(JSONObject resultsJSONObject) {
		JSONObject jsonObject = new JSONObject();

		JSONArray jsonArray = new JSONArray();

		JSONArray issuesJSONArray = resultsJSONObject.getJSONArray("issues");

		for (int i = 0; i < issuesJSONArray.length(); i++) {
			JSONObject issueJSONObject = issuesJSONArray.getJSONObject(i);

			jsonArray.put(_translateIssue(issueJSONObject));
		}

		jsonObject.put(
			"issues", jsonArray
		).put(
			"page", resultsJSONObject.getInt("startAt") + 1
		).put(
			"pageSize", resultsJSONObject.getInt("maxResults")
		).put(
			"total", resultsJSONObject.getInt("total")
		);

		return jsonObject;
	}

	private static final String _JIRA_FIELD_COMPONENTS = "components";

	private static final String _JIRA_FIELD_FIX_VERSIONS = "fixVersions";

	private static final String _JIRA_FIELD_ISSUE_KEY = "key";

	private static final String _JIRA_FIELD_VERSIONS = "versions";

	private static final Log _log = LogFactory.getLog(JiraRestController.class);

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.affected.versions.details}"
	)
	private String _jiraSecurityVulnerabilityFieldAffectedVersionsDetails;

	@Value("${liferay.customer.jira.security.vulnerability.field.category}")
	private String _jiraSecurityVulnerabilityFieldCategory;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.customer.portal.description}"
	)
	private String _jiraSecurityVulnerabilityFieldCustomerPortalDescription;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.customer.portal.summary}"
	)
	private String _jiraSecurityVulnerabilityFieldCustomerPortalSummary;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.customer.publishing.date}"
	)
	private String _jiraSecurityVulnerabilityFieldCustomerPublishingDate;

	@Value("${liferay.customer.jira.security.vulnerability.field.cve.ids}")
	private String _jiraSecurityVulnerabilityFieldCVEIds;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.cvss.base.score}"
	)
	private String _jiraSecurityVulnerabilityFieldCVSSBaseScore;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.cvss.vector.string}"
	)
	private String _jiraSecurityVulnerabilityFieldCVSSVectorString;

	@Value("${liferay.customer.jira.security.vulnerability.field.cwe.ids}")
	private String _jiraSecurityVulnerabilityFieldCWEIds;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.issue.classification}"
	)
	private String _jiraSecurityVulnerabilityFieldIssueClassification;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.partner.publishing.date}"
	)
	private String _jiraSecurityVulnerabilityFieldPartnerPublishingDate;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.publishing.status}"
	)
	private String _jiraSecurityVulnerabilityFieldPublishingStatus;

	@Value("${liferay.customer.jira.security.vulnerability.field.severity}")
	private String _jiraSecurityVulnerabilityFieldSeverity;

	@Value("${liferay.customer.jira.security.vulnerability.project}")
	private String _jiraSecurityVulnerabilityProject;

	@Autowired
	private JiraService _jiraService;

	private final String[] _securityVulnerabilitiesIssueFields;

}