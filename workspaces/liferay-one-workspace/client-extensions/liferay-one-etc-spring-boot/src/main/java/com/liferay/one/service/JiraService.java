/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.one.constants.BusinessEventConstants;
import com.liferay.one.constants.BusinessEventVersionConstants;
import com.liferay.one.constants.JiraIssueConstants;
import com.liferay.one.model.BusinessEvent;
import com.liferay.one.model.BusinessEventVersion;
import com.liferay.one.model.JiraSupportIssue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jenny Chen
 */
@Component
public class JiraService extends BaseService {

	public List<BusinessEvent> createBusinessEvent(BusinessEvent businessEvent)
		throws Exception {

		_syncBusinessEvent(null, businessEvent);

		return getBusinessEvents(businessEvent.getAccountExternalKey());
	}

	public void deleteBusinessEvent(String id) throws Exception {
		_deleteAssetObjectJSONObject(_jiraWorkspaceId, id);
	}

	public String getAccountObjectKey(String externalKey) throws Exception {
		JSONObject accountResponseJSONObject =
			_searchAccountByExternalKeyJSONObject(externalKey);

		JSONArray valuesJSONArray = accountResponseJSONObject.getJSONArray(
			"values");

		JSONObject accountJSONObject = valuesJSONArray.getJSONObject(0);

		return accountJSONObject.getString("objectKey");
	}

	public BusinessEvent getBusinessEvent(String id) throws Exception {
		JSONObject assetObjectJSONObject = _getAssetObjectJSONObject(
			_jiraWorkspaceId, id);

		_injectBusinessEventAttributeNames(assetObjectJSONObject);

		return new BusinessEvent(StringPool.BLANK, assetObjectJSONObject);
	}

	public List<BusinessEvent> getBusinessEvents(
			String accountExternalReferenceCode)
		throws Exception {

		List<BusinessEvent> businessEvents = new ArrayList<>();

		String aql = StringBundler.concat(
			"objectSchema = \"Business Events\" AND objectType = \"Business ",
			"Event\" AND \"Account\".\"External Key\" = \"",
			accountExternalReferenceCode, "\"");

		JSONArray assetsObjectsJSONArray = _searchAssetsObjectsJSONArray(
			_jiraWorkspaceId, aql);

		if ((assetsObjectsJSONArray != null) &&
			!assetsObjectsJSONArray.isEmpty()) {

			for (int i = 0; i < assetsObjectsJSONArray.length(); i++) {
				JSONObject assetObjectJSONObject =
					assetsObjectsJSONArray.getJSONObject(i);

				_injectBusinessEventAttributeNames(assetObjectJSONObject);

				businessEvents.add(
					new BusinessEvent(
						accountExternalReferenceCode, assetObjectJSONObject));
			}
		}

		return businessEvents;
	}

	public List<BusinessEventVersion> getBusinessEventVersions(
			String businessEventId)
		throws Exception {

		List<BusinessEventVersion> businessEventVersions = new ArrayList<>();

		String aql = StringBundler.concat(
			"objectSchema = \"Business Events\" AND objectType = \"Business ",
			"Event Version\" AND \"Business Event\" = ", businessEventId,
			" ORDER BY Updated DESC");

		JSONArray assetsObjectsJSONArray = _searchAssetsObjectsJSONArray(
			_jiraWorkspaceId, aql);

		if ((assetsObjectsJSONArray != null) &&
			!assetsObjectsJSONArray.isEmpty()) {

			for (int i = 0; i < assetsObjectsJSONArray.length(); i++) {
				JSONObject assetObjectJSONObject =
					assetsObjectsJSONArray.getJSONObject(i);

				_injectBusinessEventVersionAttributeNames(
					assetObjectJSONObject);

				businessEventVersions.add(
					new BusinessEventVersion(assetObjectJSONObject));
			}
		}

		return businessEventVersions;
	}

	public List<JiraSupportIssue> getJSMJiraSupportIssues(
			String externalReferenceCode, String[] issueKeys)
		throws Exception {

		StringBundler sb = new StringBundler(12);

		sb.append("Organization in aqlFunction('\"External Key\" = \"");
		sb.append(externalReferenceCode);
		sb.append("\"') and (status not in ('");
		sb.append(
			StringUtil.merge(
				JiraIssueConstants.STATUSES_SOLVED_AND_CLOSED, "','"));
		sb.append("')) and ");
		sb.append(
			JiraIssueConstants.toJQLCustomField(
				_jiraSupportHCFieldRequestType));
		sb.append(" = '");
		sb.append(JiraIssueConstants.TYPE_GENERAL_REQUEST);
		sb.append("'");

		if (ArrayUtil.isNotEmpty(issueKeys)) {
			sb.append(" or key in ('");
			sb.append(StringUtil.merge(issueKeys, "','"));
			sb.append("')");
		}

		return search(
			sb.toString(), new String[] {"key", "labels", "status", "summary"});
	}

	public List<JiraSupportIssue> search(String jql, String[] returnFields)
		throws Exception {

		List<JiraSupportIssue> jiraSupportIssues = new ArrayList<>();

		String nextPageToken = StringPool.BLANK;

		while (true) {
			JSONObject searchResponseJSONObject = _searchJSONObject(
				jql, 100, nextPageToken, returnFields);

			if (searchResponseJSONObject == null) {
				break;
			}

			JSONArray issuesJSONArray = searchResponseJSONObject.getJSONArray(
				"issues");

			for (int i = 0; i < issuesJSONArray.length(); i++) {
				JSONObject issueJSONObject = issuesJSONArray.getJSONObject(i);

				String issueKey = issueJSONObject.getString("key");

				String ticketURL =
					_jiraSupportHCPortalURL + StringPool.SLASH + issueKey;

				if (issueKey.startsWith(_jiraSupportFLSProject)) {
					ticketURL =
						_jiraSupportFLSPortalURL + StringPool.SLASH + issueKey;
				}

				JiraSupportIssue jiraSupportIssue = new JiraSupportIssue(
					issueJSONObject, ticketURL);

				jiraSupportIssues.add(jiraSupportIssue);
			}

			nextPageToken = searchResponseJSONObject.optString("nextPageToken");

			if (Validator.isNull(nextPageToken)) {
				break;
			}
		}

		return jiraSupportIssues;
	}

	public BusinessEvent updateBusinessEvent(
			String id, BusinessEvent businessEvent)
		throws Exception {

		_syncBusinessEvent(id, businessEvent);

		JSONObject assetObjectJSONObject = _getAssetObjectJSONObject(
			_jiraWorkspaceId, id);

		return new BusinessEvent(StringPool.BLANK, assetObjectJSONObject);
	}

	private JSONObject _createAssetObjectJSONObject(
			String workspaceId, String objectTypeId,
			JSONObject attributesJSONObject)
		throws Exception {

		JSONObject bodyJSONObject = new JSONObject(
		).put(
			"attributes", _transformAttributes(attributesJSONObject)
		).put(
			"objectTypeId", objectTypeId
		);

		String response = post(
			bodyJSONObject.toString(),
			HashMapBuilder.put(
				HttpHeaders.AUTHORIZATION, _getCredentials()
			).put(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build(),
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_JIRA_CLOUD_API_URL, "/jsm/assets/workspace/", workspaceId,
					"/v1/object/create")
			).build(
			).toUri());

		return new JSONObject(response);
	}

	private JSONObject _deleteAssetObjectJSONObject(
			String workspaceId, String objectId)
		throws Exception {

		String response = delete(
			_getCredentials(), StringPool.BLANK,
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_JIRA_CLOUD_API_URL, "/jsm/assets/workspace/", workspaceId,
					"/v1/object/", objectId)
			).build(
			).toUri());

		if (Validator.isNull(response)) {
			return new JSONObject();
		}

		return new JSONObject(response);
	}

	private JSONObject _getAssetObjectJSONObject(
			String workspaceId, String objectId)
		throws Exception {

		return new JSONObject(
			get(
				_getCredentials(),
				UriComponentsBuilder.fromUriString(
					StringBundler.concat(
						_JIRA_CLOUD_API_URL, "/jsm/assets/workspace/",
						workspaceId, "/v1/object/", objectId)
				).build(
				).toUri()));
	}

	private String _getCredentials() {
		Base64.Encoder encoder = Base64.getEncoder();

		String jiraUserNameAndJiraApiToken =
			_jiraAPIEmailAddress + StringPool.COLON + _jiraAPIToken;

		return "Basic " +
			encoder.encodeToString(jiraUserNameAndJiraApiToken.getBytes());
	}

	private void _injectBusinessEventAttributeNames(
			JSONObject assetObjectJSONObject)
		throws Exception {

		JSONArray attributesJSONArray = assetObjectJSONObject.optJSONArray(
			"attributes");

		if (attributesJSONArray == null) {
			return;
		}

		for (int i = 0; i < attributesJSONArray.length(); i++) {
			JSONObject attributeJSONObject = attributesJSONArray.getJSONObject(
				i);

			String objectTypeAttributeId = attributeJSONObject.optString(
				"objectTypeAttributeId");

			if (Validator.isNull(objectTypeAttributeId)) {
				continue;
			}

			if (objectTypeAttributeId.equals(
					_jiraBusinessEventAssetObjectTypeAttributeAccount)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_ACCOUNT);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeActualEventDate)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_ACTUAL_EVENT_DATE);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeAssociatedTickets)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_ASSOCIATED_TICKETS);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeAuthor)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_AUTHOR);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeCurrentVersion)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_CURRENT_VERSION);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeDescription)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_DESCRIPTION);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeEventStatus)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_EVENT_STATUS);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeEventType)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_EVENT_TYPE);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeLastComment)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_LAST_COMMENT);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeLastUpdatedAuthor)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_LAST_UPDATED_AUTHOR);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeName)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_NAME);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeNewVersion)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_NEW_VERSION);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributePlannedEventDate)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_PLANNED_EVENT_DATE);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventAssetObjectTypeAttributeTimeZone)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventConstants.ATTRIBUTE_TIME_ZONE);
			}
		}
	}

	private void _injectBusinessEventVersionAttributeNames(
			JSONObject assetObjectJSONObject)
		throws Exception {

		JSONArray attributesJSONArray = assetObjectJSONObject.optJSONArray(
			"attributes");

		if (attributesJSONArray == null) {
			return;
		}

		for (int i = 0; i < attributesJSONArray.length(); i++) {
			JSONObject attributeJSONObject = attributesJSONArray.getJSONObject(
				i);

			String objectTypeAttributeId = attributeJSONObject.optString(
				"objectTypeAttributeId");

			if (Validator.isNull(objectTypeAttributeId)) {
				continue;
			}

			if (objectTypeAttributeId.equals(
					_jiraBusinessEventVersionAssetObjectTypeAttributeAuthor)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventVersionConstants.ATTRIBUTE_AUTHOR);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventVersionAssetObjectTypeAttributeChange)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventVersionConstants.ATTRIBUTE_CHANGE);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventVersionAssetObjectTypeAttributeComment)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventVersionConstants.ATTRIBUTE_COMMENT);
			}
			else if (objectTypeAttributeId.equals(
						_jiraBusinessEventVersionAssetObjectTypeAttributeCreated)) {

				attributeJSONObject.put(
					"objectTypeAttributeName",
					BusinessEventVersionConstants.ATTRIBUTE_CREATED);
			}
		}
	}

	private JSONObject _searchAccountByExternalKeyJSONObject(
		String externalKey) {

		StringBundler sb = new StringBundler(4);

		sb.append("objectSchema = \"Koroneiki\" AND objectType = \"Account\" ");
		sb.append("AND \"External Key\" = \"");
		sb.append(externalKey);
		sb.append("\"");

		return new JSONObject(
			post(
				new JSONObject(
				).put(
					"qlQuery", sb.toString()
				).toString(),
				HashMapBuilder.put(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).put(
					HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
				).build(),
				UriComponentsBuilder.fromUriString(
					StringBundler.concat(
						_JIRA_CLOUD_API_URL, "/jsm/assets/workspace/",
						_jiraWorkspaceId, "/v1/object/aql")
				).build(
				).toUri()));
	}

	private JSONArray _searchAssetsObjectsJSONArray(
			String workspaceId, String aql)
		throws Exception {

		JSONArray itemsJSONArray = new JSONArray();

		boolean last = false;
		int startAt = 0;

		while (!last) {
			JSONObject jsonObject = _searchAssetsObjectsPageJSONObject(
				workspaceId, aql, _ASSET_OBJECTS_MAX_RESULTS, startAt);

			JSONArray jsonArray = jsonObject.optJSONArray("values");

			if ((jsonArray == null) || jsonArray.isEmpty()) {
				break;
			}

			itemsJSONArray.putAll(jsonArray);

			last = jsonObject.optBoolean("last");

			startAt += _ASSET_OBJECTS_MAX_RESULTS;
		}

		return itemsJSONArray;
	}

	private JSONObject _searchAssetsObjectsPageJSONObject(
			String workspaceId, String aql, int maxResults, int startAt)
		throws Exception {

		String response = post(
			new JSONObject(
			).put(
				"qlQuery", aql
			).toString(),
			HashMapBuilder.put(
				HttpHeaders.AUTHORIZATION, _getCredentials()
			).put(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build(),
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_JIRA_CLOUD_API_URL, "/jsm/assets/workspace/", workspaceId,
					"/v1/object/aql")
			).queryParam(
				"maxResults", maxResults
			).queryParam(
				"startAt", startAt
			).build(
			).toUri());

		if (Validator.isNull(response)) {
			return new JSONObject();
		}

		return new JSONObject(response);
	}

	private JSONObject _searchJSONObject(
			String jql, int maxResults, String nextPageToken,
			String[] returnFields)
		throws Exception {

		try {
			return new JSONObject(
				get(
					_getCredentials(),
					UriComponentsBuilder.fromUriString(
						StringBundler.concat(
							_jiraURL, _URL_REST_API_3, "/search/jql")
					).queryParam(
						"expand", "renderedFields"
					).queryParam(
						"fields", StringUtil.merge(returnFields)
					).queryParam(
						"jql", jql
					).queryParam(
						"maxResults", maxResults
					).queryParam(
						"nextPageToken", nextPageToken
					).build(
					).toUri()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get Jira issues with JQL " + jql, exception);
			}
		}

		return null;
	}

	private void _syncBusinessEvent(String id, BusinessEvent businessEvent)
		throws Exception {

		JSONObject attributesJSONObject = new JSONObject();

		attributesJSONObject.put(
			_jiraBusinessEventAssetObjectTypeAttributeActualEventDate,
			businessEvent.getActualEventDate()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeAssociatedTickets,
			businessEvent.getAssociatedTickets()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeCurrentVersion,
			businessEvent.getCurrentLiferayVersionKey()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeDescription,
			businessEvent.getDescription()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeEventStatus,
			businessEvent.getEventStatusName()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeEventType,
			businessEvent.getEventTypeName()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeLastComment,
			businessEvent.getLastComment()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeLastUpdatedAuthor,
			businessEvent.getLastUpdatedAuthorEmailAddress()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeName,
			businessEvent.getName()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeNewVersion,
			businessEvent.getNewLiferayVersionKey()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributePlannedEventDate,
			businessEvent.getPlannedEventDate()
		).put(
			_jiraBusinessEventAssetObjectTypeAttributeTimeZone,
			businessEvent.getTimeZoneName()
		);

		if (Validator.isNull(id)) {
			attributesJSONObject.put(
				_jiraBusinessEventAssetObjectTypeAttributeAccount,
				getAccountObjectKey(businessEvent.getAccountExternalKey())
			).put(
				_jiraBusinessEventAssetObjectTypeAttributeAuthor,
				businessEvent.getAuthorEmailAddress()
			);

			_createAssetObjectJSONObject(
				_jiraWorkspaceId, _jiraBusinessEventAssetObjectTypeId,
				attributesJSONObject);
		}
		else {
			_updateAssetObjectJSONObject(
				_jiraWorkspaceId, id, attributesJSONObject);
		}
	}

	private JSONArray _transformAttributes(JSONObject attributesJSONObject) {
		JSONArray jsonArray = new JSONArray();

		for (String key : attributesJSONObject.keySet()) {
			if (Validator.isNull(key)) {
				continue;
			}

			jsonArray.put(
				new JSONObject(
				).put(
					"objectAttributeValues",
					new JSONArray(
					).put(
						new JSONObject(
						).put(
							"value", attributesJSONObject.get(key)
						)
					)
				).put(
					"objectTypeAttributeId", key
				));
		}

		return jsonArray;
	}

	private JSONObject _updateAssetObjectJSONObject(
			String workspaceId, String objectId,
			JSONObject attributesJSONObject)
		throws Exception {

		return new JSONObject(
			put(
				new JSONObject(
				).put(
					"attributes", _transformAttributes(attributesJSONObject)
				).toString(),
				HashMapBuilder.put(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).put(
					HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
				).build(),
				UriComponentsBuilder.fromUriString(
					StringBundler.concat(
						_JIRA_CLOUD_API_URL, "/jsm/assets/workspace/",
						workspaceId, "/v1/object/", objectId)
				).build(
				).toUri()));
	}

	private static final int _ASSET_OBJECTS_MAX_RESULTS = 500;

	private static final String _JIRA_CLOUD_API_URL =
		"https://api.atlassian.com";

	private static final String _URL_REST_API_3 = "/rest/api/3";

	private static final Log _log = LogFactory.getLog(JiraService.class);

	@Value("${liferay.one.jira.api.email.address}")
	private String _jiraAPIEmailAddress;

	@Value("${liferay.one.jira.api.token}")
	private String _jiraAPIToken;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.account}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeAccount;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.actual." +
			"event.date}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeActualEventDate;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute." +
			"associated.tickets}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeAssociatedTickets;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.author}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeAuthor;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute." +
			"current.version}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeCurrentVersion;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute." +
			"description}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeDescription;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.event." +
			"status}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeEventStatus;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.event." +
			"type}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeEventType;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.last." +
			"comment}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeLastComment;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.last." +
			"updated.author}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeLastUpdatedAuthor;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.name}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeName;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.new." +
			"version}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeNewVersion;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute." +
			"planned.event.date}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributePlannedEventDate;

	@Value(
		"${liferay.one.jira.business.event.asset.object.type.attribute.time." +
			"zone}"
	)
	private String _jiraBusinessEventAssetObjectTypeAttributeTimeZone;

	@Value("${liferay.one.jira.business.event.asset.object.type.id}")
	private String _jiraBusinessEventAssetObjectTypeId;

	@Value(
		"${liferay.one.jira.business.event.version.asset.object.type." +
			"attribute.author}"
	)
	private String _jiraBusinessEventVersionAssetObjectTypeAttributeAuthor;

	@Value(
		"${liferay.one.jira.business.event.version.asset.object.type." +
			"attribute.change}"
	)
	private String _jiraBusinessEventVersionAssetObjectTypeAttributeChange;

	@Value(
		"${liferay.one.jira.business.event.version.asset.object.type." +
			"attribute.comment}"
	)
	private String _jiraBusinessEventVersionAssetObjectTypeAttributeComment;

	@Value(
		"${liferay.one.jira.business.event.version.asset.object.type." +
			"attribute.created}"
	)
	private String _jiraBusinessEventVersionAssetObjectTypeAttributeCreated;

	@Value("${liferay.one.jira.support.fls.portal.url}")
	private String _jiraSupportFLSPortalURL;

	@Value("${liferay.one.jira.support.fls.project}")
	private String _jiraSupportFLSProject;

	@Value("${liferay.one.jira.support.hc.field.request.type}")
	private String _jiraSupportHCFieldRequestType;

	@Value("${liferay.one.jira.support.hc.portal.url}")
	private String _jiraSupportHCPortalURL;

	@Value("${liferay.one.jira.url}")
	private String _jiraURL;

	@Value("${liferay.one.jira.workspace.id}")
	private String _jiraWorkspaceId;

}