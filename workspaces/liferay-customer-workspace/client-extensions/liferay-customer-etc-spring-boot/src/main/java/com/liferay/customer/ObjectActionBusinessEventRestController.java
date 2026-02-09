/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.customer.constants.NotificationTemplateConstants;
import com.liferay.customer.model.BusinessEvent;
import com.liferay.customer.permission.BusinessEventPermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Felipe Franca
 */
@RequestMapping("/object/action/business/event")
@RestController
public class ObjectActionBusinessEventRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		try {
			JSONObject jsonObject = new JSONObject(json);

			BusinessEvent businessEvent = new BusinessEvent(
				jsonObject.getJSONObject("objectEntryDTOBusinessEvent"));

			_businessEventPermission.check(
				jwt, businessEvent.getAccountExternalReferenceCode(),
				ActionKeys.UPDATE);

			String objectActionTriggerKey = _getObjectActionTriggerKey(
				jsonObject);

			boolean systemUpdate = _isSystemUpdate(jwt);

			_createBusinessEventVersion(
				jwt, businessEvent, objectActionTriggerKey, systemUpdate);

			if (businessEvent.isOverdue() && systemUpdate) {
				_sendNotification(businessEvent);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void _createBusinessEventVersion(
			Jwt jwt, BusinessEvent businessEvent, String objectActionTriggerKey,
			boolean systemUpdate)
		throws Exception {

		String businessEventVersionJSON = new JSONObject(
		).put(
			"change",
			_getChangeJSONObject(businessEvent, objectActionTriggerKey)
		).put(
			"comment",
			_getComment(businessEvent, objectActionTriggerKey, systemUpdate)
		).put(
			"r_accountEntryToBusinessEventVersions_accountEntryId",
			businessEvent.getAccountId()
		).put(
			"r_businessEventToBusinessEventVersions_c_businessEventId",
			businessEvent.getBusinessEventId()
		).toString();

		try {
			post(
				"Bearer " + jwt.getTokenValue(), businessEventVersionJSON,
				UriComponentsBuilder.fromPath(
					"/o/c/businesseventversions"
				).build(
				).toUri());
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to create business event version:\n" +
					businessEventVersionJSON,
				exception);
		}
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	private JSONObject _getChangeJSONObject(
		BusinessEvent businessEvent, String objectActionTriggerKey) {

		if (StringUtil.equals(objectActionTriggerKey, "onAfterAdd")) {
			return new JSONObject(
			).put(
				"key", "created"
			).put(
				"name", "Created"
			);
		}

		if (businessEvent.isCanceled()) {
			return new JSONObject(
			).put(
				"key", "eventCanceled"
			).put(
				"name", "Event Canceled"
			);
		}

		if (businessEvent.isCompleted()) {
			return new JSONObject(
			).put(
				"key", "goLive"
			).put(
				"name", "Go Live"
			);
		}

		return new JSONObject(
		).put(
			"key", "edited"
		).put(
			"name", "Edited"
		);
	}

	private String _getComment(
		BusinessEvent businessEvent, String objectActionTriggerKey,
		boolean systemUpdate) {

		if (StringUtil.equals(objectActionTriggerKey, "onAfterAdd")) {
			return "New business event has been created.";
		}

		if (businessEvent.isOverdue() && systemUpdate) {
			return "Business event status changed to overdue.";
		}

		return businessEvent.getLastComment();
	}

	private JSONObject _getNotificationTemplateJSONObject() throws Exception {
		JSONObject notificationTemplateJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/notification/v1.0/notification-templates" +
						"/by-external-reference-code/" +
							NotificationTemplateConstants.
								EXTERNAL_REFERENCE_CODE_OVERDUE_BUSINESS_EVENTS
				).build(
				).toUri()));

		if (notificationTemplateJSONObject.isEmpty()) {
			throw new Exception(
				"No notification template found for external reference code " +
					NotificationTemplateConstants.
						EXTERNAL_REFERENCE_CODE_OVERDUE_BUSINESS_EVENTS);
		}

		return notificationTemplateJSONObject;
	}

	private String _getObjectActionTriggerKey(JSONObject jsonObject)
		throws Exception {

		String objectActionTriggerKey = jsonObject.getString(
			"objectActionTriggerKey");

		if (!StringUtil.equals(objectActionTriggerKey, "onAfterAdd") &&
			!StringUtil.equals(objectActionTriggerKey, "onAfterUpdate")) {

			throw new Exception(
				"Invalid object objectActionTriggerKey trigger key: " +
					objectActionTriggerKey);
		}

		return objectActionTriggerKey;
	}

	private Map<String, String[]> _getPlaceholderValues(
		BusinessEvent businessEvent) {

		return HashMapBuilder.put(
			"placeholders",
			new String[] {
				"[%BUSINESSEVENT_AUTHOR_FIRST_NAME%]",
				"[%BUSINESSEVENT_DETAIL_PAGE_LINK%]",
				"[%BUSINESSEVENT_EDIT_PAGE_LINK%]", "[%BUSINESSEVENT_NAME%]"
			}
		).put(
			"values",
			() -> {
				List<String> values = new ArrayList<>();

				values.add(businessEvent.getCreatorGivenName());
				values.add(businessEvent.getURL(_customerPortalURL));
				values.add(businessEvent.getEditURL(_customerPortalURL));
				values.add(businessEvent.getName());

				return ArrayUtil.toStringArray(values);
			}
		).build();
	}

	private String _getSystemUserId() throws Exception {
		JSONObject systemUserJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/headless-admin-user/v1.0/my-user-account"
				).build(
				).toUri()));

		return String.valueOf(systemUserJSONObject.getLong("id"));
	}

	private JSONObject _getUserAccountJSONObject(Long id) throws Exception {
		JSONObject userAccountJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/headless-admin-user/v1.0/user-accounts/" + id
				).build(
				).toUri()));

		if (userAccountJSONObject.isEmpty()) {
			throw new Exception("No user account found for ID " + id);
		}

		return userAccountJSONObject;
	}

	private boolean _isSystemUpdate(Jwt jwt) throws Exception {
		return StringUtil.equals(jwt.getSubject(), _getSystemUserId());
	}

	private JSONArray _parseRecipientsJSONArray(
			JSONArray recipientsJSONArray, JSONObject userAccountJSONObject)
		throws Exception {

		JSONObject recipientJSONObject = recipientsJSONArray.getJSONObject(0);

		JSONObject fromNameJSONObject = recipientJSONObject.getJSONObject(
			"fromName");

		recipientJSONObject.put(
			"fromName", fromNameJSONObject.getString("en_US")
		).put(
			"to", userAccountJSONObject.getString("emailAddress")
		);

		return new JSONArray(
		).put(
			recipientJSONObject
		);
	}

	private void _sendNotification(BusinessEvent businessEvent)
		throws Exception {

		JSONObject userAccountJSONObject = _getUserAccountJSONObject(
			businessEvent.getCreatorId());

		JSONObject notificationTemplateJSONObject =
			_getNotificationTemplateJSONObject();

		JSONObject notificationTemplateBodyJSONObject =
			notificationTemplateJSONObject.getJSONObject("body");
		JSONObject notificationTemplateSubjectJSONObject =
			notificationTemplateJSONObject.getJSONObject("subject");

		Map<String, String[]> placeholderValues = _getPlaceholderValues(
			businessEvent);

		post(
			_getAuthorization(),
			new JSONObject(
			).put(
				"body",
				StringUtil.replace(
					notificationTemplateBodyJSONObject.getString("en_US"),
					placeholderValues.get("placeholders"),
					placeholderValues.get("values"))
			).put(
				"recipients",
				_parseRecipientsJSONArray(
					notificationTemplateJSONObject.getJSONArray("recipients"),
					userAccountJSONObject)
			).put(
				"subject",
				StringUtil.replace(
					notificationTemplateSubjectJSONObject.getString("en_US"),
					placeholderValues.get("placeholders"),
					placeholderValues.get("values"))
			).put(
				"type", "email"
			).toString(),
			UriComponentsBuilder.fromPath(
				"/o/notification/v1.0/notification-queue-entries"
			).build(
			).toUri());
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionBusinessEventRestController.class);

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Value("${liferay.customer.portal.url}")
	private String _customerPortalURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}