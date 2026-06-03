/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.one.model.BusinessEvent;
import com.liferay.one.model.BusinessEventVersion;
import com.liferay.one.model.JiraSupportIssue;
import com.liferay.one.permission.BusinessEventPermission;
import com.liferay.one.service.JiraService;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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

	@PostMapping("/accounts/{externalReferenceCode}/business-events")
	public ResponseEntity<String> postAccountsBusinessEvents(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@RequestBody String json)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.UPDATE);

			JSONObject myUserAccountJSONObject = _getMyUserAccountJSONObject(
				jwt);

			BusinessEvent businessEvent = new BusinessEvent(
				externalReferenceCode,
				myUserAccountJSONObject.getString("emailAddress"), json);

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

			JSONObject myUserAccountJSONObject = _getMyUserAccountJSONObject(
				jwt);

			BusinessEvent businessEvent = new BusinessEvent(
				externalReferenceCode,
				myUserAccountJSONObject.getString("emailAddress"), json);

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

	private static final Log _log = LogFactory.getLog(JiraRestController.class);

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Autowired
	private JiraService _jiraService;

}