/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.customer.constants.HeatTagConstants;
import com.liferay.customer.constants.JiraIssueConstants;
import com.liferay.customer.model.BusinessEvent;
import com.liferay.customer.model.JiraSupportIssue;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jenny Chen
 */
@Component
public class JiraService extends BaseService {

	public void addComment(String issueKey, String body) {
		post(
			body,
			HashMapBuilder.put(
				HttpHeaders.AUTHORIZATION, _getCredentials()
			).put(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build(),
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_jiraURL, "/rest/api/3/issue/", issueKey, "/comment")
			).build(
			).toUri());
	}

	public int calculateStartAt(int page, int pageSize) {
		return (page - 1) * pageSize;
	}

	public String createBusinessEvent(
			String accountExternalReferenceCode, String json)
		throws Exception {

		JSONObject businessEventJSONObject = new JSONObject(json);

		businessEventJSONObject.put(
			"accountEntryToBusinessEventsERC", accountExternalReferenceCode);

		syncBusinessEvent(new BusinessEvent(businessEventJSONObject));

		return getBusinessEvents(accountExternalReferenceCode);
	}

	public void deleteBusinessEvent(String id) throws Exception {
		_deleteJSMAssetObjectJSONObject(_jiraWorkspaceId, id);
	}

	public Set<String> getAccountERCsWithActiveBusinessEvents()
		throws Exception {

		String aql =
			"objectType = \"Business Event\" AND \"Status\" NOT IN " +
				"(\"canceled\", \"completed\")";

		JSONObject jsonObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray jsonArray = jsonObject.getJSONArray("values");

		Set<String> syncedAccountExternalReferenceCodes = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsmJSONObject = jsonArray.getJSONObject(i);

			BusinessEvent businessEvent = new BusinessEvent(
				null, jsmJSONObject);

			syncedAccountExternalReferenceCodes.add(
				businessEvent.getAccountExternalReferenceCode());
		}

		return syncedAccountExternalReferenceCodes;
	}

	public String getAccountObjectKey(String externalKey) throws Exception {
		JSONObject accountResponseJSONObject =
			_searchAccountByExternalKeyJSONObject(externalKey);

		JSONArray valuesJSONArray = accountResponseJSONObject.getJSONArray(
			"values");

		JSONObject accountJSONObject = valuesJSONArray.getJSONObject(0);

		return accountJSONObject.getString("objectKey");
	}

	@Cacheable("affectedVersions")
	public JSONArray getAffectedVersionsJSONArray() throws Exception {
		try {
			Set<String> affectedVersions = new TreeSet<>();

			String[] issueFields = {_FIELD_VERSIONS};

			StringBundler sb = new StringBundler(7);

			sb.append("project = '");
			sb.append(_jiraSecurityVulnerabilityProject);
			sb.append("' AND ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldPublishingStatus));
			sb.append(" = 'Ready for Publishing' AND ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldPartnerPublishingDate));
			sb.append(" <= now()");

			String nextPageToken = StringPool.BLANK;

			while (true) {
				JSONObject jsonObject = _searchJSONObject(
					sb.toString(), 100, nextPageToken, issueFields);

				if (jsonObject == null) {
					break;
				}

				JSONArray issuesJSONArray = jsonObject.getJSONArray("issues");

				for (int i = 0; i < issuesJSONArray.length(); i++) {
					JSONObject issueJSONObject = issuesJSONArray.getJSONObject(
						i);

					JSONObject fieldsJSONObject = issueJSONObject.getJSONObject(
						"fields");

					JSONArray versionsJSONArray = fieldsJSONObject.getJSONArray(
						"versions");

					for (int j = 0; j < versionsJSONArray.length(); j++) {
						JSONObject versionJSONObject =
							versionsJSONArray.getJSONObject(j);

						affectedVersions.add(
							versionJSONObject.optString("name"));
					}
				}

				nextPageToken = jsonObject.optString("nextPageToken");

				if (Validator.isNull(nextPageToken)) {
					break;
				}
			}

			return new JSONArray(affectedVersions);
		}
		catch (Exception exception) {
			_log.error("Unable to get affected versions", exception);
		}

		return null;
	}

	public JSONObject getAssetObjectJSONObject(
		String workspaceId, String objectId) {

		JSONObject jsonObject = new JSONObject(
			get(
				_getCredentials(),
				UriComponentsBuilder.fromUriString(
					StringBundler.concat(
						_JIRA_CLOUD_API_URL, "/ex/jira/", _jiraCloudId,
						"/jsm/assets/workspace/", workspaceId, "/v1/object/",
						objectId)
				).build(
				).toUri()));

		if (jsonObject.has("errorMessages")) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get asset object " +
						jsonObject.getJSONArray("errorMessages"));
			}

			return null;
		}

		return jsonObject;
	}

	public String getBusinessEvent(String id) throws Exception {
		JSONObject jsmJSONObject = _getJSMAssetObjectJSONObject(
			_jiraWorkspaceId, id);

		_injectAttributeNames(jsmJSONObject);

		BusinessEvent businessEvent = new BusinessEvent(null, jsmJSONObject);

		return businessEvent.toJSONObject(
		).toString();
	}

	public String getBusinessEvents(String accountExternalReferenceCode)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				"Getting business events for account: " +
					accountExternalReferenceCode);
		}

		String aql = StringBundler.concat(
			"objectType = \"Business Event\" AND (\"Account\" = \"",
			accountExternalReferenceCode,
			"\" OR \"Account\".\"External Key\" = \"",
			accountExternalReferenceCode, "\")");

		if (_log.isInfoEnabled()) {
			_log.info("AQL: " + aql);
		}

		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray valuesJSONArray = jsmAssetsJSONObject.optJSONArray("values");

		JSONArray itemsJSONArray = new JSONArray();

		if ((valuesJSONArray != null) && !valuesJSONArray.isEmpty()) {
			JSONArray objectTypeAttributesJSONArray =
				_getJSMObjectTypeAttributesJSONArray(
					_getJSMObjectTypeId(valuesJSONArray.getJSONObject(0)));

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				JSONObject jsmJSONObject = valuesJSONArray.getJSONObject(i);

				_injectAttributeNames(
					jsmJSONObject, objectTypeAttributesJSONArray);

				BusinessEvent businessEvent = new BusinessEvent(
					accountExternalReferenceCode, jsmJSONObject);

				itemsJSONArray.put(businessEvent.toJSONObject());
			}
		}

		JSONObject responseJSONObject = new JSONObject();

		responseJSONObject.put("items", itemsJSONArray);

		return responseJSONObject.toString();
	}

	public List<BusinessEvent> getBusinessEventsList(
			String accountExternalReferenceCode)
		throws Exception {

		String aql = StringBundler.concat(
			"objectType = \"Business Event\" AND (\"Account\" = \"",
			accountExternalReferenceCode,
			"\" OR \"Account\".\"External Key\" = \"",
			accountExternalReferenceCode, "\")");

		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray valuesJSONArray = jsmAssetsJSONObject.optJSONArray("values");

		List<BusinessEvent> businessEvents = new ArrayList<>();

		if ((valuesJSONArray != null) && !valuesJSONArray.isEmpty()) {
			JSONArray objectTypeAttributesJSONArray =
				_getJSMObjectTypeAttributesJSONArray(
					_getJSMObjectTypeId(valuesJSONArray.getJSONObject(0)));

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				JSONObject jsmJSONObject = valuesJSONArray.getJSONObject(i);

				_injectAttributeNames(
					jsmJSONObject, objectTypeAttributesJSONArray);

				businessEvents.add(
					new BusinessEvent(
						accountExternalReferenceCode, jsmJSONObject));
			}
		}

		return businessEvents;
	}

	public List<BusinessEvent> getBusinessEventsSince(String fromDate)
		throws Exception {

		String aql = StringBundler.concat(
			"objectType = \"Business Event\" AND updated > \"", fromDate, "\"");

		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray valuesJSONArray = jsmAssetsJSONObject.optJSONArray("values");

		List<BusinessEvent> businessEvents = new ArrayList<>();

		if ((valuesJSONArray != null) && !valuesJSONArray.isEmpty()) {
			JSONArray objectTypeAttributesJSONArray =
				_getJSMObjectTypeAttributesJSONArray(
					_getJSMObjectTypeId(valuesJSONArray.getJSONObject(0)));

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				JSONObject jsmJSONObject = valuesJSONArray.getJSONObject(i);

				_injectAttributeNames(
					jsmJSONObject, objectTypeAttributesJSONArray);

				businessEvents.add(new BusinessEvent(null, jsmJSONObject));
			}
		}

		return businessEvents;
	}

	public String getBusinessEventsSummary(List<BusinessEvent> businessEvents) {
		List<String> businessEventsSummaries = new ArrayList<>();

		for (BusinessEvent businessEvent : businessEvents) {
			List<String> businessEventFieldValues = new ArrayList<>();

			if (Validator.isNotNull(businessEvent.getName())) {
				businessEventFieldValues.add(
					"name: " + businessEvent.getName());
			}

			if (Validator.isNotNull(businessEvent.getDescription())) {
				businessEventFieldValues.add(
					"description: " + businessEvent.getDescription());
			}

			if (Validator.isNotNull(businessEvent.getEventTypeName())) {
				businessEventFieldValues.add(
					"type: " + businessEvent.getEventTypeName());
			}

			if (Validator.isNotNull(businessEvent.getEventStatusKey())) {
				businessEventFieldValues.add(
					"status: " + businessEvent.getEventStatusKey());
			}

			if (Validator.isNotNull(businessEvent.getPlannedEventDate())) {
				businessEventFieldValues.add(
					"targetGoLiveDateTime: " +
						businessEvent.getPlannedEventDate());
			}

			if (!businessEventFieldValues.isEmpty()) {
				businessEventsSummaries.add(
					StringUtil.merge(businessEventFieldValues, ",\n"));
			}
		}

		return StringUtil.merge(businessEventsSummaries, "\n\n");
	}

	public String getBusinessEventVersions(String filter, String sort)
		throws Exception {

		String aql = _translateFilterToAQL(filter);

		if (Validator.isNull(aql)) {
			aql = "objectType = \"Business Event Version\"";
		}
		else {
			aql = "objectType = \"Business Event Version\" AND " + aql;
		}

		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray valuesJSONArray = jsmAssetsJSONObject.optJSONArray("values");

		JSONArray itemsJSONArray = new JSONArray();

		if ((valuesJSONArray != null) && !valuesJSONArray.isEmpty()) {
			JSONArray objectTypeAttributesJSONArray =
				_getJSMObjectTypeAttributesJSONArray(
					_getJSMObjectTypeId(valuesJSONArray.getJSONObject(0)));

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				JSONObject jsmJSONObject = valuesJSONArray.getJSONObject(i);

				_injectAttributeNames(
					jsmJSONObject, objectTypeAttributesJSONArray);

				itemsJSONArray.put(_toVersionJSONObject(jsmJSONObject));
			}
		}

		JSONObject responseJSONObject = new JSONObject();

		responseJSONObject.put("items", itemsJSONArray);

		return responseJSONObject.toString();
	}

	public JSONArray getCustomFieldOptions(String fieldKey) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject(
				get(
					_getCredentials(),
					UriComponentsBuilder.fromUriString(
						StringBundler.concat(
							_jiraURL, _URL_REST_API_3, "/field/", fieldKey,
							"/context")
					).build(
					).toUri()));

			JSONArray valuesJSONArray = jsonObject.getJSONArray("values");

			if (valuesJSONArray.length() > 0) {
				JSONObject contextJSONObject = valuesJSONArray.getJSONObject(0);

				String contextId = contextJSONObject.getString("id");

				JSONObject optionsJSONObject = new JSONObject(
					get(
						_getCredentials(),
						UriComponentsBuilder.fromUriString(
							StringBundler.concat(
								_jiraURL, _URL_REST_API_3, "/field/", fieldKey,
								"/context/", contextId, "/option")
						).build(
						).toUri()));

				return optionsJSONObject.getJSONArray("values");
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get options for field " + fieldKey, exception);
			}
		}

		return null;
	}

	public String getFieldId(String fieldName) throws Exception {
		JSONArray fieldsJSONArray = getFields();

		if (fieldsJSONArray == null) {
			return null;
		}

		for (int i = 0; i < fieldsJSONArray.length(); i++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(i);

			if (StringUtil.equalsIgnoreCase(
					fieldName, fieldJSONObject.getString("name"))) {

				return fieldJSONObject.getString("id");
			}
		}

		return null;
	}

	@Cacheable("jsmObjects")
	public String getFieldOptions(String fieldName) throws Exception {
		String fieldId = getFieldId(fieldName);

		if (Validator.isNull(fieldId)) {
			return new JSONArray(
			).toString();
		}

		JSONArray optionsJSONArray = getCustomFieldOptions(fieldId);

		JSONArray itemsJSONArray = new JSONArray();

		if (optionsJSONArray != null) {
			for (int i = 0; i < optionsJSONArray.length(); i++) {
				JSONObject optionJSONObject = optionsJSONArray.getJSONObject(i);

				itemsJSONArray.put(
					new JSONObject(
					).put(
						"key", optionJSONObject.optString("value")
					).put(
						"name", optionJSONObject.optString("value")
					));
			}
		}

		return itemsJSONArray.toString();
	}

	public JSONArray getFields() throws Exception {
		try {
			return new JSONArray(
				get(
					_getCredentials(),
					UriComponentsBuilder.fromUriString(
						StringBundler.concat(
							_jiraURL, _URL_REST_API_3, "/field")
					).build(
					).toUri()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get Jira fields", exception);
			}
		}

		return null;
	}

	@Cacheable("issue")
	public JSONObject getIssueJSONObject(String issueKey) throws Exception {
		try {
			JSONObject issueJSONObject = new JSONObject(
				get(
					_getCredentials(),
					UriComponentsBuilder.fromUriString(
						StringBundler.concat(
							_jiraURL, _URL_REST_API_3, "/issue/", issueKey)
					).queryParam(
						"expand", "renderedFields"
					).build(
					).toUri()));

			return _transformIssueJSONObject(issueJSONObject);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get Jira issue with key " + issueKey, exception);
			}
		}

		return null;
	}

	public JiraSupportIssue getJiraSupportIssue(String issueKey)
		throws Exception {

		try {
			JSONObject issueJSONObject = new JSONObject(
				get(
					_getCredentials(),
					UriComponentsBuilder.fromUriString(
						StringBundler.concat(
							_jiraURL, _URL_REST_API_3, "/issue/", issueKey)
					).queryParam(
						"expand", "renderedFields"
					).build(
					).toUri()));

			JSONObject issueFieldsJSONObject = issueJSONObject.optJSONObject(
				"fields");

			String organizationObjectFieldId = _getAssetObjectFieldId(
				issueFieldsJSONObject.optJSONArray(
					_jiraSupportHCFieldOrganization));

			if (Validator.isNotNull(organizationObjectFieldId)) {
				String[] parts = StringUtil.split(
					organizationObjectFieldId, CharPool.COLON);

				return new JiraSupportIssue(
					issueJSONObject, parts[1], parts[0]);
			}

			return new JiraSupportIssue(issueJSONObject);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get Jira issue with key " + issueKey, exception);
			}
		}

		return null;
	}

	public Map<String, String> getJSMAssociatedTicketsHeatTags(
		List<BusinessEvent> businessEvents) {

		Map<String, String> associatedTicketsHeatTags = new HashMap<>();

		for (BusinessEvent businessEvent : businessEvents) {
			String heatTag = _getJSMHeatTag(businessEvent);

			if (Validator.isNull(businessEvent.getAssociatedTickets())) {
				continue;
			}

			JSONArray associatedTicketIdsJSONArray = new JSONArray(
				businessEvent.getAssociatedTickets());

			for (int j = 0; j < associatedTicketIdsJSONArray.length(); j++) {
				String associatedTicketId =
					associatedTicketIdsJSONArray.getString(j);

				String highestHeatTag = associatedTicketsHeatTags.get(
					associatedTicketId);

				if (Validator.isNull(highestHeatTag) ||
					(HeatTagConstants.getScore(highestHeatTag) <=
						HeatTagConstants.getScore(heatTag))) {

					associatedTicketsHeatTags.put(associatedTicketId, heatTag);
				}
			}
		}

		return associatedTicketsHeatTags;
	}

	public String getJSMIdByLiferayId(long liferayId) throws Exception {
		String aql =
			"objectType = \"Business Event\" AND \"ID\" = " + liferayId;

		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray jsonArray = jsmAssetsJSONObject.getJSONArray("values");

		if (jsonArray.isEmpty()) {
			return StringPool.BLANK;
		}

		JSONObject businessEventJSONObject = jsonArray.getJSONObject(0);

		return businessEventJSONObject.getString("id");
	}

	@Cacheable("jsmObjects")
	public String getJSMObjects(String objectTypeName) throws Exception {
		String aql = "objectType = \"" + objectTypeName + "\"";

		JSONArray itemsJSONArray = new JSONArray();

		int page = 1;
		boolean hasMore = true;

		while (hasMore) {
			JSONObject jsonObject = _searchJSMAssetsObjectsPageJSONObject(
				_jiraWorkspaceId, aql, page, _JSM_OBJECTS_PAGE_SIZE);

			JSONArray jsonArray = jsonObject.optJSONArray("values");

			if ((jsonArray == null) || jsonArray.isEmpty()) {
				break;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsmJSONObject = jsonArray.getJSONObject(i);

				itemsJSONArray.put(
					new JSONObject(
					).put(
						"key", jsmJSONObject.getString("id")
					).put(
						"name", jsmJSONObject.getString("name")
					));
			}

			int totalFilterCount = jsonObject.optInt(
				"totalFilterCount", jsonArray.length());

			hasMore = (page * _JSM_OBJECTS_PAGE_SIZE) < totalFilterCount;

			page++;
		}

		return itemsJSONArray.toString();
	}

	public String getJSMTickets(
			String externalReferenceCode, String[] ticketIds)
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

		if (ArrayUtil.isNotEmpty(ticketIds)) {
			sb.append(" or key in ('");
			sb.append(StringUtil.merge(ticketIds, "','"));
			sb.append("')");
		}

		List<JiraSupportIssue> jiraSupportIssues = search(
			sb.toString(), new String[] {"key", "labels", "status", "summary"});

		JSONArray jsonArray = new JSONArray();

		for (JiraSupportIssue jiraSupportIssue : jiraSupportIssues) {
			jsonArray.put(_toJSONObject(jiraSupportIssue));
		}

		return jsonArray.toString();
	}

	public String getLiferayId(String id) throws Exception {
		JSONObject rawBusinessEventJSONObject = _getJSMAssetObjectJSONObject(
			_jiraWorkspaceId, id);

		JSONArray objectTypeAttributesJSONArray =
			_getJSMObjectTypeAttributesJSONArray(
				_getJSMObjectTypeId(rawBusinessEventJSONObject));

		return _getLiferayId(
			rawBusinessEventJSONObject, objectTypeAttributesJSONArray);
	}

	public JSONArray getProjectVersions(String projectKey) throws Exception {
		try {
			return new JSONArray(
				get(
					_getCredentials(),
					UriComponentsBuilder.fromUriString(
						StringBundler.concat(
							_jiraURL, _URL_REST_API_3, "/project/", projectKey,
							"/versions")
					).build(
					).toUri()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get versions for project " + projectKey,
					exception);
			}
		}

		return null;
	}

	@CacheEvict(allEntries = true, value = "affectedVersions")
	@Scheduled(
		cron = "${liferay.customer.jira.service.affected.versions.cache.eviction.cron}"
	)
	public void scheduledAffectedVersionsCacheEviction() throws Exception {
	}

	@CacheEvict(allEntries = true, value = {"issue", "issues"})
	@Scheduled(
		cron = "${liferay.customer.jira.service.issues.cache.eviction.cron}"
	)
	public void scheduledIssuesCacheEviction() throws Exception {
	}

	@CacheEvict(allEntries = true, value = "jsmObjects")
	@Scheduled(
		cron = "${liferay.customer.jira.service.jsm.objects.cache.eviction.cron}"
	)
	public void scheduledJSMObjectsCacheEviction() throws Exception {
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

	@Cacheable("issues")
	public List<JSONObject> search(
			String[] filterAffectedVersions, String[] filterCategories,
			String[] filterClassifications, String[] filterFixVersions,
			String[] filterSeverities, String keywords, String sortOrder,
			boolean hasEarlyPublishAccess)
		throws Exception {

		List<JSONObject> jsonObjects = new ArrayList<>();

		String nextPageToken = StringPool.BLANK;

		StringBundler sb = new StringBundler(49);

		sb.append("project = '");
		sb.append(_jiraSecurityVulnerabilityProject);
		sb.append("' AND ");
		sb.append(
			JiraIssueConstants.toJQLCustomField(
				_jiraSecurityVulnerabilityFieldPublishingStatus));
		sb.append(" = 'Ready for Publishing'");

		if (hasEarlyPublishAccess) {
			sb.append(" AND ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldPartnerPublishingDate));
			sb.append(" <= now()");
		}
		else {
			sb.append(" AND ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
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
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldCategories));
			sb.append(" in ('");
			sb.append(StringUtil.merge(filterCategories, "','"));
			sb.append("')");
		}

		if (ArrayUtil.isNotEmpty(filterClassifications)) {
			sb.append(" AND ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldIssueClassification));
			sb.append(" in ('");
			sb.append(StringUtil.merge(filterClassifications, "','"));
			sb.append("')");
		}

		if (ArrayUtil.isNotEmpty(filterFixVersions)) {
			sb.append(" AND ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldFixVersions));
			sb.append(" in ('");
			sb.append(StringUtil.merge(filterFixVersions, "','"));
			sb.append("')");
		}

		if (ArrayUtil.isNotEmpty(filterSeverities)) {
			sb.append(" AND ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldSeverity));
			sb.append(" in ('");
			sb.append(StringUtil.merge(filterSeverities, "','"));
			sb.append("')");
		}

		if (Validator.isNotNull(keywords)) {
			sb.append(" AND (");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldCustomerPortalSummary));
			sb.append(" ~ ");
			sb.append(StringUtil.quote(keywords));
			sb.append(" OR ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldCVEIds));
			sb.append(" ~ ");
			sb.append(StringUtil.quote(keywords));
			sb.append(")");
		}

		sb.append(" ORDER BY ");

		if (hasEarlyPublishAccess) {
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldPartnerPublishingDate));
		}
		else {
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSecurityVulnerabilityFieldCustomerPublishingDate));
		}

		sb.append(" ");
		sb.append(sortOrder);
		sb.append(", ");
		sb.append(
			JiraIssueConstants.toJQLCustomField(
				_jiraSecurityVulnerabilityFieldSeverity));
		sb.append(" ASC");

		String[] securityVulnerabilitiesIssueFields = {
			_FIELD_COMPONENTS, _FIELD_ISSUE_KEY, _FIELD_VERSIONS,
			_jiraSecurityVulnerabilityFieldAffectedVersionsDetails,
			_jiraSecurityVulnerabilityFieldAffects,
			_jiraSecurityVulnerabilityFieldCategories,
			_jiraSecurityVulnerabilityFieldCustomerPortalDescription,
			_jiraSecurityVulnerabilityFieldCustomerPortalSummary,
			_jiraSecurityVulnerabilityFieldCustomerPublishingDate,
			_jiraSecurityVulnerabilityFieldCVEIds,
			_jiraSecurityVulnerabilityFieldCVSSBaseScore,
			_jiraSecurityVulnerabilityFieldCVSSVectorString,
			_jiraSecurityVulnerabilityFieldCWEIds,
			_jiraSecurityVulnerabilityFieldFixVersions,
			_jiraSecurityVulnerabilityFieldIssueClassification,
			_jiraSecurityVulnerabilityFieldPartnerPublishingDate,
			_jiraSecurityVulnerabilityFieldPublishingStatus,
			_jiraSecurityVulnerabilityFieldSeverity
		};

		while (true) {
			JSONObject searchResponseJSONObject = _searchJSONObject(
				sb.toString(), 100, nextPageToken,
				securityVulnerabilitiesIssueFields);

			if (searchResponseJSONObject == null) {
				break;
			}

			JSONArray issuesJSONArray = searchResponseJSONObject.getJSONArray(
				"issues");

			for (int i = 0; i < issuesJSONArray.length(); i++) {
				jsonObjects.add(
					_transformIssueJSONObject(
						issuesJSONArray.getJSONObject(i)));
			}

			nextPageToken = searchResponseJSONObject.optString("nextPageToken");

			if (Validator.isNull(nextPageToken)) {
				break;
			}
		}

		return jsonObjects;
	}

	public void syncBusinessEvent(BusinessEvent businessEvent)
		throws Exception {

		syncBusinessEvent(null, businessEvent);
	}

	public void syncBusinessEvent(String id, BusinessEvent businessEvent)
		throws Exception {

		String objectTypeId = _getBusinessEventObjectTypeId();

		JSONArray objectTypeAttributesJSONArray =
			_getJSMObjectTypeAttributesJSONArray(objectTypeId);

		JSONObject attributesJSONObject = new JSONObject();

		attributesJSONObject.put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Actual Go-Live Date"),
			businessEvent.getActualEventDate()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Associated Tickets"),
			businessEvent.getAssociatedTickets()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Current Version"),
			businessEvent.getCurrentLiferayVersionName()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Description"),
			businessEvent.getDescription()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Event Status"),
			businessEvent.getEventStatusKey()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Event Type"),
			businessEvent.getEventTypeName()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Last Comment"),
			businessEvent.getLastComment()
		).put(
			_getObjectTypeAttributeId(objectTypeAttributesJSONArray, "Name"),
			businessEvent.getName()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "New Version"),
			businessEvent.getNewLiferayVersionName()
		).put(
			_getObjectTypeAttributeId(objectTypeAttributesJSONArray, "Status"),
			businessEvent.getEventStatusKey()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Target Event Date"),
			businessEvent.getPlannedEventDate()
		).put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Time Zone"),
			businessEvent.getTimeZoneName()
		);

		String liferayId = businessEvent.getBusinessEventId();

		if (Validator.isNotNull(liferayId) && !liferayId.equals("0")) {
			attributesJSONObject.put(
				_getObjectTypeAttributeId(objectTypeAttributesJSONArray, "ID"),
				liferayId);
		}

		String accountExternalReferenceCode =
			businessEvent.getAccountExternalReferenceCode();

		if (Validator.isNotNull(accountExternalReferenceCode)) {
			attributesJSONObject.put(
				_getObjectTypeAttributeId(
					objectTypeAttributesJSONArray, "Account"),
				_getJSMAccountObjectId(accountExternalReferenceCode));
		}

		if (Validator.isNotNull(id)) {
			_updateJSMAssetObjectJSONObject(
				_jiraWorkspaceId, id, attributesJSONObject);
		}
		else {
			_createJSMAssetObjectJSONObject(
				_jiraWorkspaceId, objectTypeId, attributesJSONObject);
		}
	}

	public void syncBusinessEvents(
			String koroneikiAccountKey, String businessEvents,
			Map<String, String> associatedTicketsHeatTags)
		throws Exception {

		updateAccountObject(koroneikiAccountKey, businessEvents);

		StringBundler sb = new StringBundler(5);

		sb.append("Organization in aqlFunction('\"External Key\" = \"");
		sb.append(koroneikiAccountKey);
		sb.append("\"') and (status not in ('");
		sb.append(StringUtil.merge(JiraIssueConstants.STATUSES_CLOSED, "','"));
		sb.append("'))");

		List<JiraSupportIssue> jiraSupportIssues = search(
			sb.toString(), new String[] {"key", "labels", "status", "summary"});

		for (JiraSupportIssue jiraSupportIssue : jiraSupportIssues) {
			List<String> addLabels = new ArrayList<>();
			List<String> removeLabels = new ArrayList<>();

			if (associatedTicketsHeatTags.containsKey(
					jiraSupportIssue.getKey())) {

				addLabels.add("impacting_business_event");

				String heatTag = _getJSMHeatTag(jiraSupportIssue.getLabels());

				String highestHeatTag = associatedTicketsHeatTags.get(
					jiraSupportIssue.getKey());

				if ((HeatTagConstants.getScore(heatTag) <=
						HeatTagConstants.getScore(highestHeatTag)) &&
					!heatTag.equals(highestHeatTag)) {

					addLabels.add(
						highestHeatTag + _JSM_AUTOMATION_HEAT_TAG_SUFFIX);
				}
			}
			else {
				removeLabels.add("impacting_business_event");
			}

			updateIssue(
				jiraSupportIssue.getKey(), businessEvents,
				addLabels.toArray(new String[0]),
				removeLabels.toArray(new String[0]));
		}
	}

	public void syncBusinessEventVersion(
			JSONObject businessEventVersionJSONObject)
		throws Exception {

		JSONObject propertiesJSONObject =
			businessEventVersionJSONObject.optJSONObject("properties");

		if (propertiesJSONObject == null) {
			propertiesJSONObject = businessEventVersionJSONObject;
		}

		String objectTypeId = _getBusinessEventVersionObjectTypeId();

		JSONArray objectTypeAttributesJSONArray =
			_getJSMObjectTypeAttributesJSONArray(objectTypeId);

		JSONObject attributesJSONObject = new JSONObject();

		JSONObject changeJSONObject = propertiesJSONObject.getJSONObject(
			"change");

		attributesJSONObject.put(
			_getObjectTypeAttributeId(objectTypeAttributesJSONArray, "Author"),
			_getCreatorName(businessEventVersionJSONObject)
		).put(
			_getObjectTypeAttributeId(objectTypeAttributesJSONArray, "Change"),
			changeJSONObject.getString("name")
		).put(
			_getObjectTypeAttributeId(objectTypeAttributesJSONArray, "Comment"),
			propertiesJSONObject.getString("comment")
		);

		long accountId = propertiesJSONObject.getLong(
			"r_accountEntryToBusinessEventVersions_accountEntryId");

		attributesJSONObject.put(
			_getObjectTypeAttributeId(objectTypeAttributesJSONArray, "Account"),
			_getJSMAccountObjectId(accountId));

		long businessEventId = propertiesJSONObject.getLong(
			"r_businessEventToBusinessEventVersions_c_businessEventId");

		attributesJSONObject.put(
			_getObjectTypeAttributeId(
				objectTypeAttributesJSONArray, "Business Event"),
			getJSMIdByLiferayId(businessEventId));

		_createJSMAssetObjectJSONObject(
			_jiraWorkspaceId, objectTypeId, attributesJSONObject);
	}

	public void updateAccountHeatTags(String externalReferenceCode)
		throws Exception {

		List<BusinessEvent> businessEvents = getBusinessEventsList(
			externalReferenceCode);

		_updateJSMTickets(
			externalReferenceCode,
			getJSMAssociatedTicketsHeatTags(businessEvents));
	}

	public void updateAccountObject(
			String koroneikiAccountKey, String businessEvents)
		throws Exception {

		JSONObject accountResponseJSONObject =
			_searchAccountByExternalKeyJSONObject(koroneikiAccountKey);

		JSONArray valuesJSONArray = accountResponseJSONObject.getJSONArray(
			"values");

		if (valuesJSONArray == null) {
			throw new Exception(
				"Unable to find account with key " + koroneikiAccountKey);
		}

		String businessEventsAttributeId = _getObjectTypeAttributeId(
			accountResponseJSONObject.getJSONArray("objectTypeAttributes"),
			"Business Events");

		JSONObject updateJSONObject = new JSONObject(
		).put(
			"attributes",
			new JSONArray(
			).put(
				new JSONObject(
				).put(
					"objectAttributeValues",
					new JSONArray(
					).put(
						new JSONObject(
						).put(
							"value", businessEvents
						)
					)
				).put(
					"objectTypeAttributeId", businessEventsAttributeId
				)
			)
		);

		JSONObject accountJSONObject = valuesJSONArray.getJSONObject(0);

		put(
			updateJSONObject.toString(),
			HashMapBuilder.put(
				HttpHeaders.AUTHORIZATION, _getCredentials()
			).put(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build(),
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_JIRA_CLOUD_API_URL, "/jsm/assets/workspace/",
					_jiraWorkspaceId, "/v1/object/",
					accountJSONObject.getString("id"))
			).build(
			).toUri());
	}

	public String updateBusinessEvent(String id, String json) throws Exception {
		JSONObject rawBusinessEventJSONObject = _getJSMAssetObjectJSONObject(
			_jiraWorkspaceId, id);

		JSONArray objectTypeAttributesJSONArray =
			_getJSMObjectTypeAttributesJSONArray(
				_getJSMObjectTypeId(rawBusinessEventJSONObject));

		JSONObject businessEventJSONObject = new JSONObject(json);

		if (!businessEventJSONObject.has("id")) {
			String liferayId = _getLiferayId(
				rawBusinessEventJSONObject, objectTypeAttributesJSONArray);

			if (Validator.isNotNull(liferayId)) {
				businessEventJSONObject.put("id", liferayId);
			}
		}

		syncBusinessEvent(id, new BusinessEvent(businessEventJSONObject));

		JSONObject updatedBusinessEventJSONObject =
			_getJSMAssetObjectJSONObject(_jiraWorkspaceId, id);

		_injectAttributeNames(
			updatedBusinessEventJSONObject, objectTypeAttributesJSONArray);

		return updatedBusinessEventJSONObject.toString();
	}

	public void updateIssue(
		String issueKey, String businessEvents, String[] addLabels,
		String[] removeLabels) {

		JSONArray labelsJSONArray = new JSONArray();

		for (String label : addLabels) {
			JSONObject addLabelJSONObject = new JSONObject();

			addLabelJSONObject.put("add", label);

			labelsJSONArray.put(addLabelJSONObject);
		}

		for (String label : removeLabels) {
			JSONObject removeLabelJSONObject = new JSONObject();

			removeLabelJSONObject.put("remove", label);

			labelsJSONArray.put(removeLabelJSONObject);
		}

		put(
			new JSONObject(
			).put(
				"update",
				new JSONObject(
				).put(
					_jiraSupportHCFieldBusinessEvent,
					_transformADFTextArea(businessEvents)
				).put(
					"labels", labelsJSONArray
				)
			).toString(),
			HashMapBuilder.put(
				HttpHeaders.AUTHORIZATION, _getCredentials()
			).put(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build(),
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_jiraURL, _URL_REST_API_3, "/issue/", issueKey)
			).build(
			).toUri());
	}

	public void updateOverdueBusinessEvents() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

		String aql = StringBundler.concat(
			"objectType = \"Business Event\" AND \"Status\" = \"open\" AND ",
			"\"Target Go Live\" < \"", dateFormat.format(new Date()), "\"");

		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray valuesJSONArray = jsmAssetsJSONObject.optJSONArray("values");

		if (valuesJSONArray == null) {
			return;
		}

		for (int i = 0; i < valuesJSONArray.length(); i++) {
			JSONObject businessEventJSONObject = valuesJSONArray.getJSONObject(
				i);

			try {
				updateBusinessEvent(
					businessEventJSONObject.getString("id"),
					new JSONObject(
					).put(
						"eventStatus", "overdue"
					).toString());
			}
			catch (Exception exception) {
				_log.error(
					"Unable to update business event:\n" +
						businessEventJSONObject.toString(),
					exception);
			}
		}
	}

	private JSONObject _createJSMAssetObjectJSONObject(
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
					_JIRA_CLOUD_API_URL, "/ex/jira/", _jiraCloudId,
					"/jsm/assets/workspace/", workspaceId, "/v1/object/create")
			).build(
			).toUri());

		return new JSONObject(response);
	}

	private JSONObject _deleteJSMAssetObjectJSONObject(
			String workspaceId, String objectId)
		throws Exception {

		String response = delete(
			_getCredentials(), StringPool.BLANK,
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_JIRA_CLOUD_API_URL, "/ex/jira/", _jiraCloudId,
					"/jsm/assets/workspace/", workspaceId, "/v1/object/",
					objectId)
			).build(
			).toUri());

		if (Validator.isNull(response)) {
			return new JSONObject();
		}

		return new JSONObject(response);
	}

	private JSONArray _flattenJSONArray(JSONArray jsonArray) {
		if (jsonArray == null) {
			return new JSONArray();
		}

		JSONArray flattenedJSONArray = new JSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject itemJSONObject = jsonArray.getJSONObject(i);

			String name = itemJSONObject.optString("name");

			if (Validator.isNotNull(name)) {
				flattenedJSONArray.put(name);
			}

			String value = itemJSONObject.optString("value");

			if (Validator.isNotNull(value)) {
				flattenedJSONArray.put(value);
			}
		}

		return flattenedJSONArray;
	}

	private String _getAssetObjectFieldId(JSONArray jsonArray) {
		if ((jsonArray != null) && (jsonArray.length() > 0)) {
			JSONObject assetJSONObject = jsonArray.getJSONObject(0);

			return assetJSONObject.getString("id");
		}

		return null;
	}

	private String _getBusinessEventObjectTypeId() throws Exception {
		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, "objectType = \"Business Event\"");

		JSONArray jsonArray = jsmAssetsJSONObject.getJSONArray("values");

		if (jsonArray.length() > 0) {
			String objectTypeId = _getJSMObjectTypeId(
				jsonArray.getJSONObject(0));

			if (Validator.isNotNull(objectTypeId)) {
				return objectTypeId;
			}
		}

		throw new Exception("Unable to find Business Event object type");
	}

	private String _getBusinessEventVersionObjectTypeId() throws Exception {
		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, "objectType = \"Business Event Version\"");

		JSONArray jsonArray = jsmAssetsJSONObject.getJSONArray("values");

		if (jsonArray.length() > 0) {
			String objectTypeId = _getJSMObjectTypeId(
				jsonArray.getJSONObject(0));

			if (Validator.isNotNull(objectTypeId)) {
				return objectTypeId;
			}
		}

		throw new Exception(
			"Unable to find Business Event Version object type");
	}

	private String _getCreatorName(JSONObject jsonObject) {
		JSONObject creatorJSONObject = jsonObject.optJSONObject("creator");

		if (creatorJSONObject != null) {
			return creatorJSONObject.optString("name");
		}

		return StringPool.BLANK;
	}

	private String _getCredentials() {
		Base64.Encoder encoder = Base64.getEncoder();

		String jiraUserNameAndJiraApiToken =
			_jiraAPIEmailAddress + StringPool.COLON + _jiraAPIToken;

		return "Basic " +
			encoder.encodeToString(jiraUserNameAndJiraApiToken.getBytes());
	}

	private String _getJSMAccountObjectId(long accountId) throws Exception {
		String aql =
			"objectType = \"Account\" AND \"Liferay ID\" = " + accountId;

		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray jsonArray = jsmAssetsJSONObject.getJSONArray("values");

		if (jsonArray.isEmpty()) {
			throw new Exception("No account found for ID " + accountId);
		}

		JSONObject accountJSONObject = jsonArray.getJSONObject(0);

		return accountJSONObject.getString("id");
	}

	private String _getJSMAccountObjectId(String accountExternalReferenceCode)
		throws Exception {

		String aql = StringBundler.concat(
			"objectType = \"Account\" AND \"External Key\" = \"",
			accountExternalReferenceCode, "\"");

		JSONObject jsmAssetsJSONObject = _searchJSMAssetsObjectsJSONObject(
			_jiraWorkspaceId, aql);

		JSONArray jsonArray = jsmAssetsJSONObject.getJSONArray("values");

		if (jsonArray.isEmpty()) {
			throw new Exception(
				"No account found for " + accountExternalReferenceCode);
		}

		JSONObject accountJSONObject = jsonArray.getJSONObject(0);

		return accountJSONObject.getString("id");
	}

	private JSONObject _getJSMAssetObjectJSONObject(
			String workspaceId, String objectId)
		throws Exception {

		String response = get(
			_getCredentials(),
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_JIRA_CLOUD_API_URL, "/ex/jira/", _jiraCloudId,
					"/jsm/assets/workspace/", workspaceId, "/v1/object/",
					objectId)
			).build(
			).toUri());

		return new JSONObject(response);
	}

	private String _getJSMHeatTag(BusinessEvent businessEvent) {
		if (Validator.isNull(businessEvent.getAssociatedTickets())) {
			return StringPool.BLANK;
		}

		JSONArray associatedTicketIdsJSONArray = new JSONArray(
			businessEvent.getAssociatedTickets());

		if (associatedTicketIdsJSONArray.length() == 0) {
			return StringPool.BLANK;
		}

		String targetGoLiveDateTime = businessEvent.getPlannedEventDate();

		if (Validator.isNull(targetGoLiveDateTime)) {
			return StringPool.BLANK;
		}

		return HeatTagConstants.getHeatTag(
			businessEvent.getEventTypeName(),
			ChronoUnit.DAYS.between(
				LocalDate.now(),
				LocalDate.parse(targetGoLiveDateTime.substring(0, 10))));
	}

	private String _getJSMHeatTag(String[] issueLabels) {
		for (String label : issueLabels) {
			if (ArrayUtil.contains(
					HeatTagConstants.SUPPORT_ISSUE_LABELS, label)) {

				return label;
			}
		}

		return StringPool.BLANK;
	}

	private JSONArray _getJSMObjectTypeAttributesJSONArray(String objectTypeId)
		throws Exception {

		String response = get(
			_getCredentials(),
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_JIRA_CLOUD_API_URL, "/ex/jira/", _jiraCloudId,
					"/jsm/assets/workspace/", _jiraWorkspaceId,
					"/v1/objecttype/", objectTypeId, "/attributes")
			).build(
			).toUri());

		return new JSONArray(response);
	}

	private String _getJSMObjectTypeId(JSONObject jsonObject) {
		if (jsonObject.has("objectTypeId")) {
			return String.valueOf(jsonObject.get("objectTypeId"));
		}

		JSONObject objectTypeJSONObject = jsonObject.optJSONObject(
			"objectType");

		if (objectTypeJSONObject != null) {
			return String.valueOf(objectTypeJSONObject.get("id"));
		}

		return StringPool.BLANK;
	}

	private String _getJSONObjectFieldValue(JSONObject jsonObject, String key) {
		if (jsonObject != null) {
			return jsonObject.optString(key);
		}

		return null;
	}

	private String _getLiferayId(
		JSONObject jsmJSONObject, JSONArray objectTypeAttributesJSONArray) {

		String liferayIdAttributeId = _getObjectTypeAttributeId(
			objectTypeAttributesJSONArray, "ID");

		if (Validator.isNull(liferayIdAttributeId)) {
			return StringPool.BLANK;
		}

		JSONArray attributesJSONArray = jsmJSONObject.optJSONArray(
			"attributes");

		if (attributesJSONArray == null) {
			return StringPool.BLANK;
		}

		for (int i = 0; i < attributesJSONArray.length(); i++) {
			JSONObject attributeJSONObject = attributesJSONArray.getJSONObject(
				i);

			if (StringUtil.equals(
					String.valueOf(
						attributeJSONObject.get("objectTypeAttributeId")),
					liferayIdAttributeId)) {

				JSONArray valuesJSONArray = attributeJSONObject.optJSONArray(
					"objectAttributeValues");

				if ((valuesJSONArray != null) && !valuesJSONArray.isEmpty()) {
					JSONObject valueJSONObject = valuesJSONArray.getJSONObject(
						0);

					String value = valueJSONObject.optString("displayValue");

					if (Validator.isNull(value)) {
						value = valueJSONObject.optString("value");
					}

					return value;
				}
			}
		}

		return StringPool.BLANK;
	}

	private String _getObjectTypeAttributeId(
		JSONArray objectTypeAttributesJSONArray, String attributeName) {

		for (int i = 0; i < objectTypeAttributesJSONArray.length(); i++) {
			JSONObject objectTypeAttributeJSONObject =
				objectTypeAttributesJSONArray.getJSONObject(i);

			String name = objectTypeAttributeJSONObject.getString("name");

			if (StringUtil.equalsIgnoreCase(name, attributeName)) {
				return objectTypeAttributeJSONObject.getString("id");
			}
		}

		return StringPool.BLANK;
	}

	private void _injectAttributeNames(JSONObject businessEventJSONObject)
		throws Exception {

		_injectAttributeNames(
			businessEventJSONObject,
			_getJSMObjectTypeAttributesJSONArray(
				_getJSMObjectTypeId(businessEventJSONObject)));
	}

	private void _injectAttributeNames(
		JSONObject businessEventJSONObject,
		JSONArray objectTypeAttributesJSONArray) {

		JSONArray attributesJSONArray = businessEventJSONObject.optJSONArray(
			"attributes");

		if (attributesJSONArray == null) {
			return;
		}

		for (int i = 0; i < attributesJSONArray.length(); i++) {
			JSONObject attributeJSONObject = attributesJSONArray.getJSONObject(
				i);

			String objectTypeAttributeId = String.valueOf(
				attributeJSONObject.get("objectTypeAttributeId"));

			for (int j = 0; j < objectTypeAttributesJSONArray.length(); j++) {
				JSONObject objectTypeAttributeJSONObject =
					objectTypeAttributesJSONArray.getJSONObject(j);

				if (Objects.equals(
						String.valueOf(objectTypeAttributeJSONObject.get("id")),
						objectTypeAttributeId)) {

					attributeJSONObject.put(
						"objectTypeAttribute", objectTypeAttributeJSONObject);

					break;
				}
			}
		}
	}

	private JSONObject _searchAccountByExternalKeyJSONObject(
		String externalKey) {

		StringBundler sb = new StringBundler(4);

		sb.append("objectSchema = \"Koroneiki\" and objectType = \"Account\" ");
		sb.append("and \"External Key\" = \"");
		sb.append(externalKey);
		sb.append("\"");

		JSONObject queryJSONObject = new JSONObject(
		).put(
			"qlQuery", sb.toString()
		);

		return new JSONObject(
			post(
				queryJSONObject.toString(),
				HashMapBuilder.put(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).put(
					HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
				).build(),
				UriComponentsBuilder.fromUriString(
					StringBundler.concat(
						_JIRA_CLOUD_API_URL, "/ex/jira/", _jiraCloudId,
						"/jsm/assets/workspace/", _jiraWorkspaceId,
						"/v1/object/aql")
				).build(
				).toUri()));
	}

	private JSONObject _searchJSMAssetsObjectsJSONObject(
			String workspaceId, String aql)
		throws Exception {

		return _searchJSMAssetsObjectsPageJSONObject(
			workspaceId, aql, 1, _JSM_OBJECTS_PAGE_SIZE);
	}

	private JSONObject _searchJSMAssetsObjectsPageJSONObject(
			String workspaceId, String aql, int page, int pageSize)
		throws Exception {

		JSONObject bodyJSONObject = new JSONObject(
		).put(
			"maxResults", pageSize
		).put(
			"page", page
		).put(
			"qlQuery", aql
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
					_JIRA_CLOUD_API_URL, "/ex/jira/", _jiraCloudId,
					"/jsm/assets/workspace/", workspaceId, "/v1/object/aql")
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

	private JSONObject _toJSONObject(JiraSupportIssue jiraSupportIssue) {
		return new JSONObject(
		).put(
			"link", jiraSupportIssue.getTicketURL()
		).put(
			"status", jiraSupportIssue.getStatus()
		).put(
			"subject", jiraSupportIssue.getSummary()
		).put(
			"ticketId", jiraSupportIssue.getKey()
		);
	}

	private JSONObject _toVersionJSONObject(JSONObject jsmJSONObject) {
		JSONArray attributesJSONArray = jsmJSONObject.getJSONArray(
			"attributes");

		Map<String, String> attributes = new HashMap<>();

		for (int i = 0; i < attributesJSONArray.length(); i++) {
			JSONObject attributeJSONObject = attributesJSONArray.getJSONObject(
				i);

			String name = attributeJSONObject.getJSONObject(
				"objectTypeAttribute"
			).getString(
				"name"
			);

			JSONArray valuesJSONArray = attributeJSONObject.optJSONArray(
				"objectAttributeValues");

			if ((valuesJSONArray != null) && !valuesJSONArray.isEmpty()) {
				JSONObject valueJSONObject = valuesJSONArray.getJSONObject(0);

				String value = valueJSONObject.optString("displayValue");

				if (Validator.isNull(value)) {
					value = valueJSONObject.optString("value");
				}

				attributes.put(name, value);
			}
		}

		String changeName = attributes.getOrDefault("Change", StringPool.BLANK);
		String authorName = attributes.getOrDefault("Author", StringPool.BLANK);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"change",
			new JSONObject(
			).put(
				"key", changeName
			).put(
				"name", changeName
			)
		).put(
			"comment", attributes.getOrDefault("Comment", StringPool.BLANK)
		).put(
			"creator",
			new JSONObject(
			).put(
				"name", authorName
			)
		).put(
			"dateModified", jsmJSONObject.optString("updated")
		).put(
			"id", jsmJSONObject.optString("id")
		);

		return jsonObject;
	}

	private JSONArray _transformADFTextArea(String text) {
		return new JSONArray(
		).put(
			new JSONObject(
			).put(
				"set",
				new JSONObject(
				).put(
					"content",
					new JSONArray(
					).put(
						new JSONObject(
						).put(
							"content",
							new JSONArray(
							).put(
								new JSONObject(
								).put(
									"text", text
								).put(
									"type", "text"
								)
							)
						).put(
							"type", "paragraph"
						)
					)
				).put(
					"type", "doc"
				).put(
					"version", 1
				)
			)
		);
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

	private JSONObject _transformIssueFieldsJSONObject(
		JSONObject issueFieldsJSONObject,
		JSONObject issueRenderedFieldsJSONObject) {

		JSONObject transformedFieldsJSONObject = new JSONObject();

		if (issueFieldsJSONObject != null) {
			transformedFieldsJSONObject.put(
				"affectedVersions",
				_flattenJSONArray(
					issueFieldsJSONObject.optJSONArray(_FIELD_VERSIONS))
			).put(
				"affectedVersionsDetails",
				issueFieldsJSONObject.optString(
					_jiraSecurityVulnerabilityFieldAffectedVersionsDetails)
			).put(
				"affects",
				issueFieldsJSONObject.optString(
					_jiraSecurityVulnerabilityFieldAffects)
			).put(
				"categories",
				_flattenJSONArray(
					issueFieldsJSONObject.optJSONArray(
						_jiraSecurityVulnerabilityFieldCategories))
			).put(
				"components",
				_flattenJSONArray(
					issueFieldsJSONObject.optJSONArray(_FIELD_COMPONENTS))
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
					issueFieldsJSONObject.optJSONArray(
						_jiraSecurityVulnerabilityFieldFixVersions))
			).put(
				"issueClassification",
				_getJSONObjectFieldValue(
					issueFieldsJSONObject.optJSONObject(
						_jiraSecurityVulnerabilityFieldIssueClassification),
					"value")
			).put(
				"organization",
				_getAssetObjectFieldId(
					issueFieldsJSONObject.optJSONArray(
						_jiraSupportHCFieldOrganization))
			).put(
				"partnerPublishingDate",
				issueFieldsJSONObject.optString(
					_jiraSecurityVulnerabilityFieldPartnerPublishingDate)
			).put(
				"publishingStatus",
				_getJSONObjectFieldValue(
					issueFieldsJSONObject.optJSONObject(
						_jiraSecurityVulnerabilityFieldPublishingStatus),
					"value")
			).put(
				"severity",
				_getJSONObjectFieldValue(
					issueFieldsJSONObject.optJSONObject(
						_jiraSecurityVulnerabilityFieldSeverity),
					"value")
			).put(
				"status",
				_getJSONObjectFieldValue(
					issueFieldsJSONObject.optJSONObject(_FIELD_STATUS), "name")
			);
		}

		if (issueRenderedFieldsJSONObject != null) {
			transformedFieldsJSONObject.put(
				"customerPortalDescription",
				issueRenderedFieldsJSONObject.optString(
					_jiraSecurityVulnerabilityFieldCustomerPortalDescription));
		}

		return transformedFieldsJSONObject;
	}

	private JSONObject _transformIssueJSONObject(JSONObject issueJSONObject) {
		return new JSONObject(
		).put(
			"fields",
			_transformIssueFieldsJSONObject(
				issueJSONObject.optJSONObject("fields"),
				issueJSONObject.optJSONObject("renderedFields"))
		).put(
			"key", issueJSONObject.getString(_FIELD_ISSUE_KEY)
		);
	}

	private String _translateFilterToAQL(String filter) {
		if (Validator.isNull(filter)) {
			return StringPool.BLANK;
		}

		String aql = filter;

		aql = aql.replaceAll(
			"r_businessEventToBusinessEventVersions_c_businessEventId eq " +
				"'(\\d+)'",
			"\"Business Event\" = $1");

		aql = aql.replaceAll(
			"r_businessEventToBusinessEventVersions_c_businessEventJSMId eq " +
				"'([^']+)'",
			"\"Business Event\" = $1");

		aql = aql.replaceAll(
			"r_accountEntryToBusinessEvents_accountEntryERC eq '([^']+)'",
			"\"Account\".\"External Key\" = \"$1\"");

		aql = aql.replaceAll(
			"r_accountEntryToBusinessEvents_accountEntryId eq '(\\d+)'",
			"\"Account\".\"Liferay ID\" = $1");

		return aql;
	}

	private JSONObject _updateJSMAssetObjectJSONObject(
			String workspaceId, String objectId,
			JSONObject attributesJSONObject)
		throws Exception {

		JSONObject bodyJSONObject = new JSONObject(
		).put(
			"attributes", _transformAttributes(attributesJSONObject)
		);

		String response = put(
			bodyJSONObject.toString(),
			HashMapBuilder.put(
				HttpHeaders.AUTHORIZATION, _getCredentials()
			).put(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build(),
			UriComponentsBuilder.fromUriString(
				StringBundler.concat(
					_JIRA_CLOUD_API_URL, "/ex/jira/", _jiraCloudId,
					"/jsm/assets/workspace/", workspaceId, "/v1/object/",
					objectId)
			).build(
			).toUri());

		return new JSONObject(response);
	}

	private void _updateJSMTickets(
			String koroneikiAccountKey,
			Map<String, String> associatedTicketsHeatTags)
		throws Exception {

		StringBundler sb = new StringBundler(9);

		sb.append("Organization in aqlFunction('\"External Key\" = \"");
		sb.append(koroneikiAccountKey);
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

		List<JiraSupportIssue> jiraSupportIssues = search(
			sb.toString(), new String[] {"key", "labels", "status", "summary"});

		for (JiraSupportIssue jiraSupportIssue : jiraSupportIssues) {
			if (!associatedTicketsHeatTags.containsKey(
					jiraSupportIssue.getKey())) {

				continue;
			}

			String heatTag = _getJSMHeatTag(jiraSupportIssue.getLabels());

			String highestHeatTag = associatedTicketsHeatTags.get(
				jiraSupportIssue.getKey());

			if ((HeatTagConstants.getScore(heatTag) > HeatTagConstants.getScore(
					highestHeatTag)) ||
				heatTag.equals(highestHeatTag)) {

				continue;
			}

			updateIssue(
				jiraSupportIssue.getKey(), null,
				new String[] {highestHeatTag + _JSM_AUTOMATION_HEAT_TAG_SUFFIX},
				new String[0]);
		}
	}

	private static final String _FIELD_AFFECTED_VERSION = "affectedVersion";

	private static final String _FIELD_COMPONENTS = "components";

	private static final String _FIELD_ISSUE_KEY = "key";

	private static final String _FIELD_STATUS = "status";

	private static final String _FIELD_VERSIONS = "versions";

	private static final String _JIRA_CLOUD_API_URL =
		"https://api.atlassian.com";

	private static final String _JSM_AUTOMATION_HEAT_TAG_SUFFIX = "_be";

	private static final int _JSM_OBJECTS_PAGE_SIZE = 500;

	private static final String _URL_REST_API_3 = "/rest/api/3";

	private static final Log _log = LogFactory.getLog(JiraService.class);

	@Value("${liferay.customer.jira.api.email.address}")
	private String _jiraAPIEmailAddress;

	@Value("${liferay.customer.jira.api.token}")
	private String _jiraAPIToken;

	@Value("${liferay.customer.jira.cloud.id}")
	private String _jiraCloudId;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.affected." +
			"versions.details}"
	)
	private String _jiraSecurityVulnerabilityFieldAffectedVersionsDetails;

	@Value("${liferay.customer.jira.security.vulnerability.field.affects}")
	private String _jiraSecurityVulnerabilityFieldAffects;

	@Value("${liferay.customer.jira.security.vulnerability.field.categories}")
	private String _jiraSecurityVulnerabilityFieldCategories;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.customer.portal." +
			"description}"
	)
	private String _jiraSecurityVulnerabilityFieldCustomerPortalDescription;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.customer.portal." +
			"summary}"
	)
	private String _jiraSecurityVulnerabilityFieldCustomerPortalSummary;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.customer." +
			"publishing.date}"
	)
	private String _jiraSecurityVulnerabilityFieldCustomerPublishingDate;

	@Value("${liferay.customer.jira.security.vulnerability.field.cve.ids}")
	private String _jiraSecurityVulnerabilityFieldCVEIds;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.cvss.base.score}"
	)
	private String _jiraSecurityVulnerabilityFieldCVSSBaseScore;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.cvss.vector." +
			"string}"
	)
	private String _jiraSecurityVulnerabilityFieldCVSSVectorString;

	@Value("${liferay.customer.jira.security.vulnerability.field.cwe.ids}")
	private String _jiraSecurityVulnerabilityFieldCWEIds;

	@Value("${liferay.customer.jira.security.vulnerability.field.fix.versions}")
	private String _jiraSecurityVulnerabilityFieldFixVersions;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.issue." +
			"classification}"
	)
	private String _jiraSecurityVulnerabilityFieldIssueClassification;

	@Value(
		"${liferay.customer.jira.security.vulnerability.field.partner." +
			"publishing.date}"
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

	@Value("${liferay.customer.jira.support.fls.portal.url}")
	private String _jiraSupportFLSPortalURL;

	@Value("${liferay.customer.jira.support.fls.project}")
	private String _jiraSupportFLSProject;

	@Value("${liferay.customer.jira.support.hc.field.business.event}")
	private String _jiraSupportHCFieldBusinessEvent;

	@Value("${liferay.customer.jira.support.hc.field.organization}")
	private String _jiraSupportHCFieldOrganization;

	@Value("${liferay.customer.jira.support.hc.field.request.type}")
	private String _jiraSupportHCFieldRequestType;

	@Value("${liferay.customer.jira.support.hc.portal.url}")
	private String _jiraSupportHCPortalURL;

	@Value("${liferay.customer.jira.url}")
	private String _jiraURL;

	@Value("${liferay.customer.jira.workspace.id}")
	private String _jiraWorkspaceId;

}