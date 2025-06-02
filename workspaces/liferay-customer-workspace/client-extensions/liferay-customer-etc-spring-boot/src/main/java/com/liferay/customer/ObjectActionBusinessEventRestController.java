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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felipe Franca
 */
@RestController
public class ObjectActionBusinessEventRestController
	extends BaseRestController {

	@RequestMapping(
		method = RequestMethod.POST, path = "/object/action/business/event"
	)
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

			_createBusinessEventVersion(
				jwt, businessEvent, objectActionTriggerKey);

			_sendNotification(businessEvent, objectActionTriggerKey);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void _createBusinessEventVersion(
			Jwt jwt, BusinessEvent businessEvent, String objectActionTriggerKey)
		throws Exception {

		String businessEventVersionJSON = new JSONObject(
		).put(
			"change",
			_getChangeJSONObject(businessEvent, objectActionTriggerKey)
		).put(
			"comment", _getComment(businessEvent, objectActionTriggerKey)
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
				createURI("/o/c/businesseventversions"));
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
		BusinessEvent businessEvent, String objectActionTriggerKey) {

		if (StringUtil.equals(objectActionTriggerKey, "onAfterAdd")) {
			return "New business event has been created.";
		}

		return businessEvent.getLastComment();
	}

	private JSONObject _getKoroneikiAccountJSONObject(
			String externalReferenceCode)
		throws Exception {

		JSONObject koroneikiAccountJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				createURI(
					"/o/c/koroneikiaccounts/by-external-reference-code/",
					externalReferenceCode)));

		if (koroneikiAccountJSONObject.isEmpty()) {
			throw new Exception(
				"No koroneiki account found for external reference code " +
					externalReferenceCode);
		}

		return koroneikiAccountJSONObject;
	}

	private JSONObject _getNotificationTemplateJSONObject(
			BusinessEvent businessEvent, String objectActionTriggerKey)
		throws Exception {

		String externalReferenceCode = null;

		if (StringUtil.equals(objectActionTriggerKey, "onAfterAdd")) {
			externalReferenceCode =
				NotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_CREATED_BUSINESS_EVENTS;
		}
		else if (businessEvent.isCanceled()) {
			externalReferenceCode =
				NotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_CANCELED_BUSINESS_EVENTS;
		}
		else if (businessEvent.isCompleted()) {
			externalReferenceCode =
				NotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_COMPLETED_BUSINESS_EVENTS;
		}
		else {
			externalReferenceCode =
				NotificationTemplateConstants.
					EXTERNAL_REFERENCE_CODE_UPDATED_BUSINESS_EVENTS;
		}

		JSONObject notificationTemplateJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				createURI(
					"/o/notification/v1.0/notification-templates",
					"/by-external-reference-code/", externalReferenceCode)));

		if (notificationTemplateJSONObject.isEmpty()) {
			throw new Exception(
				"No notification template found for external reference code " +
					externalReferenceCode);
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

	private Map<String, String> _getPlaceholderValues(
		BusinessEvent businessEvent, JSONObject koroneikiAccountJSONObject) {

		String formattedComment = "";

		if (!StringUtil.equals(businessEvent.getLastComment(), "")) {
			formattedComment = "<p>" + businessEvent.getLastComment() + "</p>";
		}

		return HashMapBuilder.put(
			"[%BUSINESSEVENT_ACTIVITY_HISTORY_PAGE_LINK%]",
			businessEvent.getActivityHistoryURL(
				lxcDXPServerProtocol, lxcDXPMainDomain)
		).put(
			"[%BUSINESSEVENT_DETAIL_PAGE_LINK%]",
			businessEvent.getURL(lxcDXPServerProtocol, lxcDXPMainDomain)
		).put(
			"[%BUSINESSEVENT_EVENTTYPE%]", businessEvent.getEventTypeName()
		).put(
			"[%BUSINESSEVENT_LASTCOMMENT%]", formattedComment
		).put(
			"[%BUSINESSEVENT_NAME%]", businessEvent.getName()
		).put(
			"[%BUSINESSEVENT_TARGETGOLIVEDATETIME%]",
			businessEvent.getTargetGoLiveDate()
		).put(
			"[%PROJECT_NAME%]", koroneikiAccountJSONObject.getString("name")
		).build();
	}

	private String _getRecipientsTo(JSONObject koroneikiAccountJSONObject)
		throws Exception {

		String region = koroneikiAccountJSONObject.getString("region");

		String regionPropertyKey = region.toLowerCase(
		).replace(
			" ", "."
		);

		String rsmEmailAddress = _environment.getProperty(
			"liferay.customer.email.address." + regionPropertyKey + ".rsm");

		if (rsmEmailAddress == null) {
			throw new Exception("No email address was found for " + region);
		}

		boolean hasTAMServiceSubscription = _hasTAMServiceSubscription(
			koroneikiAccountJSONObject.getString("accountKey"));

		if (hasTAMServiceSubscription) {
			String cxLeadEmailAddress = _environment.getProperty(
				"liferay.customer.email.address." + regionPropertyKey +
					".cx.lead");

			if (cxLeadEmailAddress == null) {
				throw new Exception("No email address was found for " + region);
			}

			return rsmEmailAddress + ", " + cxLeadEmailAddress;
		}

		return rsmEmailAddress;
	}

	private boolean _hasTAMServiceSubscription(
			String accountExternalReferenceCode)
		throws Exception {

		JSONObject accountSubscriptionsJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				createURI(
					"/o/c/accountsubscriptions?filter=accountKey eq '",
					accountExternalReferenceCode,
					"' and contains(name, 'Technical Account Management ",
					"Services')")));

		JSONArray accountSubscriptionsJSONArray =
			accountSubscriptionsJSONObject.getJSONArray("items");

		if (accountSubscriptionsJSONArray.length() > 0) {
			return true;
		}

		return false;
	}

	private JSONArray _parseRecipientsJSONArray(
			JSONObject koroneikiAccountJSONObject,
			JSONArray recipientsJSONArray)
		throws Exception {

		JSONObject recipientJSONObject = recipientsJSONArray.getJSONObject(0);

		JSONObject fromNameJSONObject = recipientJSONObject.getJSONObject(
			"fromName");

		recipientJSONObject.put(
			"fromName", fromNameJSONObject.getString("en_US")
		).put(
			"to", _getRecipientsTo(koroneikiAccountJSONObject)
		);

		return new JSONArray(
		).put(
			recipientJSONObject
		);
	}

	private String _replace(
		Map<String, String> placeholderValues, String string) {

		for (Map.Entry<String, String> entry : placeholderValues.entrySet()) {
			return StringUtil.replace(string, entry.getKey(), entry.getValue());
		}

		return string;
	}

	private void _sendNotification(
			BusinessEvent businessEvent, String objectActionTriggerKey)
		throws Exception {

		JSONObject notificationTemplateJSONObject =
			_getNotificationTemplateJSONObject(
				businessEvent, objectActionTriggerKey);

		JSONObject notificationTemplateBodyJSONObject =
			notificationTemplateJSONObject.getJSONObject("body");
		JSONObject notificationTemplateSubjectJSONObject =
			notificationTemplateJSONObject.getJSONObject("subject");

		JSONObject koroneikiAccountJSONObject = _getKoroneikiAccountJSONObject(
			businessEvent.getAccountExternalReferenceCode());

		Map<String, String> placeholderValues = _getPlaceholderValues(
			businessEvent, koroneikiAccountJSONObject);

		post(
			_getAuthorization(),
			new JSONObject(
			).put(
				"body",
				_replace(
					placeholderValues,
					notificationTemplateBodyJSONObject.getString("en_US"))
			).put(
				"recipients",
				_parseRecipientsJSONArray(
					koroneikiAccountJSONObject,
					notificationTemplateJSONObject.getJSONArray("recipients"))
			).put(
				"subject",
				_replace(
					placeholderValues,
					notificationTemplateSubjectJSONObject.getString("en_US"))
			).put(
				"type", "email"
			).toString(),
			createURI("/o/notification/v1.0/notification-queue-entries"));
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionBusinessEventRestController.class);

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Autowired
	private Environment _environment;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}