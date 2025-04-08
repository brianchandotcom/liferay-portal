/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

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
public class ObjectActionBusinessEvent extends BaseRestController {

	@RequestMapping(
		method = RequestMethod.POST, path = "/object/action/business/event"
	)
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject businessEventJSONObject = jsonObject.getJSONObject(
			"objectEntryDTOBusinessEvent");

		JSONObject businessEventPropertiesJSONObject =
			businessEventJSONObject.getJSONObject("properties");

		JSONObject businessEventVersionJSONObject = new JSONObject(
		).put(
			"change",
			_getChangeJSONObject(jsonObject, businessEventPropertiesJSONObject)
		).put(
			"comment",
			_getComment(jsonObject, businessEventPropertiesJSONObject)
		).put(
			"r_accountEntryToBusinessEventVersions_accountEntryId",
			businessEventPropertiesJSONObject.getString(
				"r_accountEntryToBusinessEvents_accountEntryId")
		).put(
			"r_businessEventToBusinessEventVersions_c_businessEventId",
			businessEventJSONObject.getString("id")
		);

		try {
			post(
				"Bearer " + jwt.getTokenValue(),
				businessEventVersionJSONObject.toString(),
				"/o/c/businesseventversions");

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			StringBundler sb = new StringBundler(4);

			sb.append("Unable to create business event version:\n");
			sb.append(businessEventVersionJSONObject.toString());
			sb.append("\nAuthor's ID: ");
			sb.append(jwt.getClaimAsString("sub"));

			_log.error(sb.toString(), exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private JSONObject _getChangeJSONObject(
		JSONObject jsonObject, JSONObject propertiesJSONObject) {

		if (_isNewEntry(jsonObject)) {
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

	private String _getComment(
		JSONObject jsonObject, JSONObject propertiesJSONObject) {

		if (_isNewEntry(jsonObject)) {
			return "New business event has been created.";
		}

		return propertiesJSONObject.optString("lastComment");
	}

	private boolean _isCanceledEvent(JSONObject jsonObject) {
		JSONObject eventStatusJSONObject = jsonObject.getJSONObject(
			"eventStatus");

		return StringUtil.equalsIgnoreCase(
			eventStatusJSONObject.getString("key"), "canceled");
	}

	private boolean _isGoLive(JSONObject jsonObject) {
		return !StringUtil.equalsIgnoreCase(
			jsonObject.optString("actualGoLiveDateTime"), "");
	}

	private boolean _isNewEntry(JSONObject jsonObject) {
		return StringUtil.equalsIgnoreCase(
			jsonObject.getString("objectActionTriggerKey"), "onAfterAdd");
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionBusinessEvent.class);

}