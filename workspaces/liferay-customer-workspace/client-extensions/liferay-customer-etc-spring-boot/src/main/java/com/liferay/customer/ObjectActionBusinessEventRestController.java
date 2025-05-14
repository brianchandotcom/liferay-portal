/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.customer.constants.NotificationTemplateERCConstants;
import com.liferay.customer.permission.BusinessEventPermission;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

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

			JSONObject businessEventJSONObject = jsonObject.getJSONObject(
				"objectEntryDTOBusinessEvent");

			JSONObject propertiesJSONObject =
				businessEventJSONObject.getJSONObject("properties");

			_businessEventPermission.check(
				jwt,
				propertiesJSONObject.getString(
					"accountEntryToBusinessEventsERC"),
				ActionKeys.UPDATE);

			_createBusinessEventVersion(jwt, jsonObject);

			_sendNotification(jsonObject);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void _createBusinessEventVersion(Jwt jwt, JSONObject jsonObject)
		throws Exception {

		String action = _getAction(jsonObject);

		JSONObject businessEventJSONObject = jsonObject.getJSONObject(
			"objectEntryDTOBusinessEvent");

		JSONObject propertiesJSONObject = businessEventJSONObject.getJSONObject(
			"properties");

		JSONObject businessEventVersionJSONObject = new JSONObject(
		).put(
			"change", _getChangeJSONObject(action, propertiesJSONObject)
		).put(
			"comment", _getComment(action, propertiesJSONObject)
		).put(
			"r_accountEntryToBusinessEventVersions_accountEntryId",
			propertiesJSONObject.getString(
				"r_accountEntryToBusinessEvents_accountEntryId")
		).put(
			"r_businessEventToBusinessEventVersions_c_businessEventId",
			businessEventJSONObject.getString("id")
		);

		_postBusinessEventVersion(jwt, businessEventVersionJSONObject);
	}

	private String _getAction(JSONObject jsonObject) throws Exception {
		String action = jsonObject.getString("objectActionTriggerKey");

		if (!StringUtil.equals(action, "onAfterAdd") &&
			!StringUtil.equals(action, "onAfterUpdate")) {

			throw new Exception("Invalid action: " + action);
		}

		return action;
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	private JSONObject _getChangeJSONObject(
		String action, JSONObject propertiesJSONObject) {

		if (StringUtil.equals(action, "onAfterAdd")) {
			return new JSONObject(
			).put(
				"key", "created"
			).put(
				"name", "Created"
			);
		}

		if (_isCanceledEvent(propertiesJSONObject)) {
			return new JSONObject(
			).put(
				"key", "eventCanceled"
			).put(
				"name", "Event Canceled"
			);
		}

		if (_isGoLive(propertiesJSONObject)) {
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

	private String _getComment(String action, JSONObject propertiesJSONObject) {
		if (StringUtil.equals(action, "onAfterAdd")) {
			return "New business event has been created.";
		}

		return propertiesJSONObject.optString("lastComment");
	}

	private String _getDetailPageLink(
		String accountExternalReferenceCode, String businessEventId) {

		StringBundler sb = new StringBundler(4);

		sb.append("https://support.liferay.com/project/#/");
		sb.append(accountExternalReferenceCode);
		sb.append("/business-events/");
		sb.append(businessEventId);

		return sb.toString();
	}

	private String _getEmailAddressByName(String name) throws Exception {
		if (name.equals("AUSTRALIA_CX_LEAD")) {
			return _australiaCXLeadEmailAddress;
		}
		else if (name.equals("AUSTRALIA_RSM")) {
			return _australiaRSMEmailAddress;
		}
		else if (name.equals("BRAZIL_CX_LEAD")) {
			return _brazilCXLeadEmailAddress;
		}
		else if (name.equals("BRAZIL_RSM")) {
			return _brazilRSMEmailAddress;
		}
		else if (name.equals("CHINA_CX_LEAD")) {
			return _chinaCXLeadEmailAddress;
		}
		else if (name.equals("CHINA_RSM")) {
			return _chinaRSMEmailAddress;
		}
		else if (name.equals("GLOBAL_CX_LEAD")) {
			return _globalCXLeadEmailAddress;
		}
		else if (name.equals("GLOBAL_RSM")) {
			return _globalRSMEmailAddress;
		}
		else if (name.equals("HUNGARY_CX_LEAD")) {
			return _hungaryCXLeadEmailAddress;
		}
		else if (name.equals("HUNGARY_RSM")) {
			return _hungaryRSMEmailAddress;
		}
		else if (name.equals("INDIA_CX_LEAD")) {
			return _indiaCXLeadEmailAddress;
		}
		else if (name.equals("INDIA_RSM")) {
			return _indiaRSMEmailAddress;
		}
		else if (name.equals("JAPAN_CX_LEAD")) {
			return _japanCXLeadEmailAddress;
		}
		else if (name.equals("JAPAN_RSM")) {
			return _japanRSMEmailAddress;
		}
		else if (name.equals("SPAIN_CX_LEAD")) {
			return _spainCXLeadEmailAddress;
		}
		else if (name.equals("SPAIN_RSM")) {
			return _spainRSMEmailAddress;
		}
		else if (name.equals("UNITED_STATES_CX_LEAD")) {
			return _unitedStatesCXLeadEmailAddress;
		}
		else if (name.equals("UNITED_STATES_RSM")) {
			return _unitedStatesRSMEmailAddress;
		}

		StringBundler sb = new StringBundler(2);

		sb.append("No email address found for name ");
		sb.append(name);

		throw new Exception(sb.toString());
	}

	private String _getEventName(JSONObject eventTypeJSONObject) {
		return eventTypeJSONObject.optString("name");
	}

	private String _getFormattedComment(String lastComment) {
		if (StringUtil.equals(lastComment, "")) {
			return lastComment;
		}

		return "<p>" + lastComment + "</p>";
	}

	private JSONObject _getKoroneikiAccountJSONObject(
			String externalReferenceCode)
		throws Exception {

		JSONObject koroneikiAccountJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				"/o/c/koroneikiaccounts/by-external-reference-code/" +
					externalReferenceCode));

		if (koroneikiAccountJSONObject.isEmpty()) {
			throw new Exception(
				"No koroneiki account found for external reference code " +
					externalReferenceCode);
		}

		return koroneikiAccountJSONObject;
	}

	private String _getNotificationTemplateERC(
		String action, JSONObject propertiesJSONObject) {

		JSONObject changeJSONObject = _getChangeJSONObject(
			action, propertiesJSONObject);

		String changeKey = changeJSONObject.getString("key");

		if (StringUtil.equals(changeKey, "created")) {
			return NotificationTemplateERCConstants.
				CREATED_BUSINESS_EVENTS_NOTIFICATION_TEMPLATE;
		}

		if (StringUtil.equals(changeKey, "eventCanceled")) {
			return NotificationTemplateERCConstants.
				CANCELED_BUSINESS_EVENTS_NOTIFICATION_TEMPLATE;
		}

		if (StringUtil.equals(changeKey, "goLive")) {
			return NotificationTemplateERCConstants.
				COMPLETED_BUSINESS_EVENTS_NOTIFICATION_TEMPLATE;
		}

		return NotificationTemplateERCConstants.
			UPDATED_BUSINESS_EVENTS_NOTIFICATION_TEMPLATE;
	}

	private JSONObject _getNotificationTemplateJSONObject(
			String externalReferenceCode)
		throws Exception {

		JSONObject notificationTemplateJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				"/o/notification/v1.0/notification-templates" +
					"/by-external-reference-code/" + externalReferenceCode));

		if (notificationTemplateJSONObject.isEmpty()) {
			throw new Exception(
				"No notification template found for external reference code " +
					externalReferenceCode);
		}

		return notificationTemplateJSONObject;
	}

	private String _getPayload(
			JSONObject businessEventJSONObject,
			JSONObject koroneikiAccountJSONObject,
			JSONObject notificationTemplateJSONObject,
			JSONObject propertiesJSONObject)
		throws Exception {

		JSONObject notificationTemplateBodyJSONObject =
			notificationTemplateJSONObject.getJSONObject("body");

		Map<String, String> placeholderValuesMap = _getPlaceholderValuesMap(
			businessEventJSONObject, koroneikiAccountJSONObject,
			propertiesJSONObject);

		String notificationTemplateBody = _replaceEmailPlaceholders(
			notificationTemplateBodyJSONObject.getString("en_US"),
			placeholderValuesMap);

		JSONArray notificationTemplateRecipientsJSONArray =
			_parseRecipientsJSONArray(
				koroneikiAccountJSONObject,
				notificationTemplateJSONObject.getJSONArray("recipients"));

		JSONObject notificationTemplateSubjectJSONObject =
			notificationTemplateJSONObject.getJSONObject("subject");

		String notificationTemplateSubject = _replaceEmailPlaceholders(
			notificationTemplateSubjectJSONObject.getString("en_US"),
			placeholderValuesMap);

		return new JSONObject(
		).put(
			"body", notificationTemplateBody
		).put(
			"recipients", notificationTemplateRecipientsJSONArray
		).put(
			"subject", notificationTemplateSubject
		).put(
			"type", "email"
		).toString();
	}

	private Map<String, String> _getPlaceholderValuesMap(
		JSONObject businessEventJSONObject,
		JSONObject koroneikiAccountJSONObject,
		JSONObject propertiesJSONObject) {

		String detailPageLink = _getDetailPageLink(
			propertiesJSONObject.getString("accountEntryToBusinessEventsERC"),
			businessEventJSONObject.getString("id"));

		return HashMapBuilder.put(
			"[%BUSINESSEVENT_ACTIVITY_HISTORY_PAGE_LINK%]",
			detailPageLink + "/activity-history"
		).put(
			"[%BUSINESSEVENT_DETAIL_PAGE_LINK%]", detailPageLink
		).put(
			"[%BUSINESSEVENT_EVENTTYPE%]",
			_getEventName(propertiesJSONObject.getJSONObject("eventType"))
		).put(
			"[%BUSINESSEVENT_LASTCOMMENT%]",
			_getFormattedComment(propertiesJSONObject.optString("lastComment"))
		).put(
			"[%BUSINESSEVENT_NAME%]", propertiesJSONObject.getString("name")
		).put(
			"[%BUSINESSEVENT_TARGETGOLIVEDATETIME%]",
			_getTargetGoLiveDate(
				propertiesJSONObject.getString("targetGoLiveDateTime"))
		).put(
			"[%PROJECT_NAME%]", koroneikiAccountJSONObject.getString("name")
		).build();
	}

	private String _getRecipientsTo(JSONObject koroneikiAccountJSONObject)
		throws Exception {

		String region = koroneikiAccountJSONObject.getString("region");

		String formattedRegion = region.toUpperCase(
		).replace(
			" ", "_"
		);

		String rsmEmailName = formattedRegion + "_RSM";

		String rsmEmailAddress = _getEmailAddressByName(rsmEmailName);

		boolean hasTAMServiceSubscription = _hasTAMServiceSubscription(
			koroneikiAccountJSONObject.getString("accountKey"));

		if (hasTAMServiceSubscription) {
			String cxLeadEmailName = formattedRegion + "_CX_LEAD";

			String cxLeadEmailAddress = _getEmailAddressByName(cxLeadEmailName);

			return rsmEmailAddress + ", " + cxLeadEmailAddress;
		}

		return rsmEmailAddress;
	}

	private String _getTargetGoLiveDate(String targetGoLiveDateTime) {
		return targetGoLiveDateTime.split("T")[0];
	}

	private boolean _hasTAMServiceSubscription(String externalReferenceCode)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("/o/c/accountsubscriptions/?filter=contains(name, ");
		sb.append("'Technical Account Management Services') and accountKey ");
		sb.append("eq '");
		sb.append(externalReferenceCode);
		sb.append("'");

		JSONObject accountSubscriptionsJSONObject = new JSONObject(
			get(_getAuthorization(), sb.toString()));

		JSONArray accountSubscriptionsJSONArray =
			accountSubscriptionsJSONObject.getJSONArray("items");

		if (accountSubscriptionsJSONArray.length() > 0) {
			return true;
		}

		return false;
	}

	private boolean _isCanceledEvent(JSONObject propertiesJSONObject) {
		JSONObject eventStatusJSONObject = propertiesJSONObject.getJSONObject(
			"eventStatus");

		return StringUtil.equals(
			eventStatusJSONObject.getString("key"), "canceled");
	}

	private boolean _isGoLive(JSONObject propertiesJSONObject) {
		JSONObject eventStatusJSONObject = propertiesJSONObject.getJSONObject(
			"eventStatus");

		return StringUtil.equals(
			eventStatusJSONObject.getString("key"), "completed");
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

	private void _postBusinessEventVersion(
			Jwt jwt, JSONObject businessEventVersionJSONObject)
		throws Exception {

		try {
			post(
				"Bearer " + jwt.getTokenValue(),
				businessEventVersionJSONObject.toString(),
				"/o/c/businesseventversions");
		}
		catch (Exception exception) {
			StringBundler sb = new StringBundler(2);

			sb.append("Unable to create business event version:\n");
			sb.append(businessEventVersionJSONObject.toString());

			throw new Exception(sb.toString(), exception);
		}
	}

	private String _replaceEmailPlaceholders(
		String emailField, Map<String, String> placeholderValuesMap) {

		String replacedEmailField = emailField;

		for (Map.Entry<String, String> entry :
				placeholderValuesMap.entrySet()) {

			replacedEmailField = StringUtil.replace(
				replacedEmailField, entry.getKey(), entry.getValue());
		}

		return replacedEmailField;
	}

	private void _sendNotification(JSONObject jsonObject) throws Exception {
		JSONObject businessEventJSONObject = jsonObject.getJSONObject(
			"objectEntryDTOBusinessEvent");

		JSONObject propertiesJSONObject = businessEventJSONObject.getJSONObject(
			"properties");

		post(
			_getAuthorization(),
			_getPayload(
				businessEventJSONObject,
				_getKoroneikiAccountJSONObject(
					propertiesJSONObject.getString(
						"accountEntryToBusinessEventsERC")),
				_getNotificationTemplateJSONObject(
					_getNotificationTemplateERC(
						_getAction(jsonObject), propertiesJSONObject)),
				propertiesJSONObject),
			"/o/notification/v1.0/notification-queue-entries");
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionBusinessEventRestController.class);

	@Value("${liferay.customer.email.address.australia.cx.lead}")
	private String _australiaCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.australia.rsm}")
	private String _australiaRSMEmailAddress;

	@Value("${liferay.customer.email.address.brazil.cx.lead}")
	private String _brazilCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.brazil.rsm}")
	private String _brazilRSMEmailAddress;

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Value("${liferay.customer.email.address.china.cx.lead}")
	private String _chinaCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.china.rsm}")
	private String _chinaRSMEmailAddress;

	@Value("${liferay.customer.email.address.global.cx.lead}")
	private String _globalCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.global.rsm}")
	private String _globalRSMEmailAddress;

	@Value("${liferay.customer.email.address.hungary.cx.lead}")
	private String _hungaryCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.hungary.rsm}")
	private String _hungaryRSMEmailAddress;

	@Value("${liferay.customer.email.address.india.cx.lead}")
	private String _indiaCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.india.rsm}")
	private String _indiaRSMEmailAddress;

	@Value("${liferay.customer.email.address.japan.cx.lead}")
	private String _japanCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.japan.rsm}")
	private String _japanRSMEmailAddress;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.customer.email.address.spain.cx.lead}")
	private String _spainCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.spain.rsm}")
	private String _spainRSMEmailAddress;

	@Value("${liferay.customer.email.address.united.states.cx.lead}")
	private String _unitedStatesCXLeadEmailAddress;

	@Value("${liferay.customer.email.address.united.states.rsm}")
	private String _unitedStatesRSMEmailAddress;

}