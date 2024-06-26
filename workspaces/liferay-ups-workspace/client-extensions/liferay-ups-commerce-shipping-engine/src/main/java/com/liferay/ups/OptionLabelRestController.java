/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ups;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alessio Antonio Rendina
 */
@RequestMapping("/option-label")
@RestController
public class OptionLabelRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		log(jwt, _log, json);

		JSONObject jsonObject = new JSONObject(json);

		if (!jsonObject.has("name")) {
			return null;
		}

		String name = jsonObject.getString("name");

		JSONObject responseJSONObject = get(
			"Bearer " + jwt.getTokenValue(),
			"/o/headless-admin-list-type/v1.0/list-type-definitions" +
				"/by-external-reference-code/L_UPS_CODES/list-type-entries");

		JSONArray listTypeEntriesJSONArray = responseJSONObject.getJSONArray(
			"items");

		for (int i = 0; i < listTypeEntriesJSONArray.length(); i++) {
			JSONObject curJSONObject = listTypeEntriesJSONArray.getJSONObject(
				i);

			String key = curJSONObject.getString("key");

			if ((key != null) && key.equals(name)) {
				String bcp47LanguageId = jsonObject.getString(
					"bcp47LanguageId");

				JSONObject nameI18nJSONObject = curJSONObject.getJSONObject(
					"name_i18n");

				if (nameI18nJSONObject.has(bcp47LanguageId)) {
					return new ResponseEntity<>(
						new JSONObject(
						).put(
							"name",
							nameI18nJSONObject.getString(bcp47LanguageId)
						).toString(),
						HttpStatus.OK);
				}

				return new ResponseEntity<>(
					new JSONObject(
					).put(
						"name", curJSONObject.getString("name")
					).toString(),
					HttpStatus.OK);
			}
		}

		return null;
	}

	private static final Log _log = LogFactory.getLog(
		OptionLabelRestController.class);

}