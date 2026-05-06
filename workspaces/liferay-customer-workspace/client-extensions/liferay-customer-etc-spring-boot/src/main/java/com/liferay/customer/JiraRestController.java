/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.customer.constants.RoleConstants;
import com.liferay.customer.model.BusinessEvent;
import com.liferay.customer.model.BusinessEventVersion;
import com.liferay.customer.model.JiraSupportIssue;
import com.liferay.customer.permission.BusinessEventPermission;
import com.liferay.customer.service.JiraService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jenny Chen
 */
@RequestMapping("/jira")
@RestController
public class JiraRestController extends BaseRestController {

	@DeleteMapping("/accounts/{externalReferenceCode}/business-events/{id}")
	public ResponseEntity<String> deleteAccountsBusinessEvents(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@PathVariable("id") String id)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.UPDATE);

			_jiraService.deleteBusinessEvent(id);

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/cache")
	public ResponseEntity<String> deleteCache(
		@AuthenticationPrincipal Jwt jwt) {

		try {
			if (!_hasAdministrator(jwt)) {
				throw new PrincipalException();
			}

			_jiraService.scheduledAffectedVersionsCacheEviction();
			_jiraService.scheduledAssetObjectsCacheEviction();
			_jiraService.scheduledIssuesCacheEviction();

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/accounts/{externalReferenceCode}/business-events")
	public ResponseEntity<String> getAccountsBusinessEvents(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.VIEW);

			JSONArray itemsJSONArray = new JSONArray();

			List<BusinessEvent> businessEvents = _jiraService.getBusinessEvents(
				externalReferenceCode);

			for (BusinessEvent businessEvent : businessEvents) {
				itemsJSONArray.put(businessEvent.toJSONObject());
			}

			JSONObject responseJSONObject = new JSONObject();

			responseJSONObject.put("items", itemsJSONArray);

			return new ResponseEntity<>(
				responseJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/accounts/{externalReferenceCode}/business-events/{id}")
	public ResponseEntity<String> getAccountsBusinessEvents(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@PathVariable("id") String id)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.VIEW);

			BusinessEvent businessEvent = _jiraService.getBusinessEvent(id);

			JSONObject jsonObject = businessEvent.toJSONObject();

			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(
		"/accounts/{externalReferenceCode}/business-events/{id}/versions"
	)
	public ResponseEntity<String> getAccountsBusinessEventsVersions(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@PathVariable("id") String id)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.VIEW);

			List<BusinessEventVersion> businessEventVersions =
				_jiraService.getBusinessEventVersions(id);

			JSONArray itemsJSONArray = new JSONArray();

			for (BusinessEventVersion businessEventVersion :
					businessEventVersions) {

				itemsJSONArray.put(businessEventVersion.toJSONObject());
			}

			JSONObject responseJSONObject = new JSONObject();

			responseJSONObject.put("items", itemsJSONArray);

			return new ResponseEntity<>(
				responseJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/accounts/{externalReferenceCode}/tickets")
	public ResponseEntity<String> getAccountsTickets(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@RequestParam(defaultValue = "", required = false) String[]
				ticketIds)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.VIEW);

			JSONArray jsonArray = new JSONArray();

			List<JiraSupportIssue> jiraSupportIssues =
				_jiraService.getJSMJiraSupportIssues(
					externalReferenceCode, ticketIds);

			for (JiraSupportIssue jiraSupportIssue : jiraSupportIssues) {
				jsonArray.put(jiraSupportIssue.toJSONObject());
			}

			return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/field-options/{fieldName}")
	public ResponseEntity<String> getFieldOptions(
			@PathVariable("fieldName") String fieldName)
		throws Exception {

		try {
			JSONArray jsonArray = _jiraService.getFieldOptions(fieldName);

			return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/issue/{issueKey}")
	public ResponseEntity<String> getIssue(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("issueKey") String issueKey)
		throws Exception {

		try {
			if (!issueKey.startsWith(_jiraSecurityVulnerabilityProject)) {
				throw new PrincipalException();
			}

			JSONObject jsonObject = _jiraService.getIssueJSONObject(issueKey);

			if (_hasIssuePermission(jwt, jsonObject)) {
				return new ResponseEntity<>(
					jsonObject.toString(), HttpStatus.OK);
			}

			return new ResponseEntity<>(
				"No issue found with key " + issueKey, HttpStatus.NOT_FOUND);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/product-versions")
	public ResponseEntity<String> getProductVersions() throws Exception {
		try {
			JSONArray jsonArray = _jiraService.getAssetObjects(
				_OBJECT_SCHEMA_BUSINESS_EVENTS, _OBJECT_TYPE_PRODUCT_VERSION);

			return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/security-vulnerabilities/affected-versions")
	public ResponseEntity<String> getSecurityVulnerabilitiesAffectedVersions()
		throws Exception {

		try {
			JSONArray affectedVersionsJSONArray =
				_jiraService.getAffectedVersionsJSONArray();

			return new ResponseEntity<>(
				affectedVersionsJSONArray.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/security-vulnerabilities/search")
	public ResponseEntity<String> getSecurityVulnerabilitiesSearch(
			@AuthenticationPrincipal Jwt jwt,
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
			List<JSONObject> jsonObjects = _jiraService.search(
				filterAffectedVersions, filterCategories, filterClassifications,
				filterFixVersions, filterSeverities, keywords, sortOrder,
				_hasEarlyPublishAccess(jwt));

			JSONObject jsonObject = _toJSONObject(jsonObjects, page, pageSize);

			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/accounts/{externalReferenceCode}/business-events")
	public ResponseEntity<String> postAccountsBusinessEvents(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@RequestBody String json)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.UPDATE);

			JSONObject userJSONObject = _getMyUserAccountJSONObject(jwt);

			BusinessEvent businessEvent = new BusinessEvent(
				externalReferenceCode, userJSONObject.getString("emailAddress"),
				json);

			List<BusinessEvent> businessEvents =
				_jiraService.createBusinessEvent(businessEvent);

			JSONArray itemsJSONArray = new JSONArray();

			for (BusinessEvent curBusinessEvent : businessEvents) {
				itemsJSONArray.put(curBusinessEvent.toJSONObject());
			}

			JSONObject responseJSONObject = new JSONObject();

			responseJSONObject.put("items", itemsJSONArray);

			return new ResponseEntity<>(
				responseJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/accounts/{externalReferenceCode}/business-events/{id}")
	public ResponseEntity<String> putAccountsBusinessEvents(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@PathVariable("id") String id, @RequestBody String json)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("PUT business event " + id);
		}

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.UPDATE);

			JSONObject userJSONObject = _getMyUserAccountJSONObject(jwt);

			BusinessEvent businessEvent = new BusinessEvent(
				externalReferenceCode, userJSONObject.getString("emailAddress"),
				json);

			businessEvent = _jiraService.updateBusinessEvent(id, businessEvent);

			JSONObject jsonObject = businessEvent.toJSONObject();

			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error("Unable to update business event " + id, exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private JSONObject _getMyUserAccountJSONObject(Jwt jwt) throws Exception {
		try {
			return new JSONObject(
				get(
					"Bearer " + jwt.getTokenValue(),
					UriComponentsBuilder.fromPath(
						"/o/headless-admin-user/v1.0/my-user-account"
					).build(
					).toUri()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get user account", exception);
			}

			throw new PrincipalException();
		}
	}

	private List<String> _getUserRoleNames(Jwt jwt) throws Exception {
		List<String> roleNames = new ArrayList<>();

		JSONObject userJSONObject = _getMyUserAccountJSONObject(jwt);

		JSONArray roleBriefsJSONArray = userJSONObject.getJSONArray(
			"roleBriefs");

		for (int i = 0; i < roleBriefsJSONArray.length(); i++) {
			JSONObject roleBriefJSONObject = roleBriefsJSONArray.getJSONObject(
				i);

			roleNames.add(roleBriefJSONObject.getString("name"));
		}

		return roleNames;
	}

	private boolean _hasAdministrator(Jwt jwt) throws Exception {
		List<String> userRoleNames = _getUserRoleNames(jwt);

		return userRoleNames.contains(RoleConstants.NAME_ADMINISTRATOR);
	}

	private boolean _hasEarlyPublishAccess(Jwt jwt) throws Exception {
		List<String> userRoleNames = _getUserRoleNames(jwt);

		if (userRoleNames.contains(RoleConstants.NAME_ADMINISTRATOR) ||
			userRoleNames.contains(RoleConstants.NAME_LIFERAY_STAFF) ||
			userRoleNames.contains(RoleConstants.NAME_PARTNER)) {

			return true;
		}

		return false;
	}

	private boolean _hasIssuePermission(Jwt jwt, JSONObject issueJSONObject)
		throws Exception {

		JSONObject fieldsJSONObject = issueJSONObject.getJSONObject("fields");

		String publishingStatus = fieldsJSONObject.optString(
			"publishingStatus");

		if (publishingStatus.equals("Ready for Publishing")) {
			LocalDateTime localDateTime = _parseLocalDateTime(
				jwt, issueJSONObject);

			if (localDateTime.isBefore(LocalDateTime.now())) {
				return true;
			}
		}

		return false;
	}

	private LocalDateTime _parseLocalDateTime(
			Jwt jwt, JSONObject issueJSONObject)
		throws Exception {

		JSONObject fieldsJSONObject = issueJSONObject.getJSONObject("fields");

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
			"yyyy-MM-dd'T'HH:mm:ss.SSSx");

		if (_hasEarlyPublishAccess(jwt)) {
			return LocalDateTime.parse(
				fieldsJSONObject.optString("partnerPublishingDate"),
				dateTimeFormatter);
		}

		return LocalDateTime.parse(
			fieldsJSONObject.optString("customerPublishingDate"),
			dateTimeFormatter);
	}

	private JSONObject _toJSONObject(
		List<JSONObject> jsonObjects, int page, int pageSize) {

		return new JSONObject(
		).put(
			"issues",
			jsonObjects.subList(
				_jiraService.calculateStartAt(page, pageSize),
				Math.min(
					_jiraService.calculateStartAt(page, pageSize) + pageSize,
					jsonObjects.size()))
		).put(
			"page", page
		).put(
			"pageSize", pageSize
		).put(
			"total", jsonObjects.size()
		);
	}

	private static final String _OBJECT_SCHEMA_BUSINESS_EVENTS =
		"Business Events";

	private static final String _OBJECT_TYPE_PRODUCT_VERSION =
		"Product Version";

	private static final Log _log = LogFactory.getLog(JiraRestController.class);

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Value("${liferay.customer.jira.security.vulnerability.project}")
	private String _jiraSecurityVulnerabilityProject;

	@Autowired
	private JiraService _jiraService;

}