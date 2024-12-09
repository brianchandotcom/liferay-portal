/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard;

import com.liferay.ai.content.wizard.model.Settings;
import com.liferay.ai.content.wizard.service.SettingsService;
import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.petra.string.StringBundler;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RequestMapping("/settings")
@RestController
public class SettingsRestController extends BaseRestController {

	@DeleteMapping("/{id}")
	public void delete(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("id") String id) {

		delete(
			"Bearer " + jwt.getTokenValue(), "",
			"/o/c/k9l6aicontentwizardsettings/" + id);
	}

	@GetMapping
	public ResponseEntity<String> get(@AuthenticationPrincipal Jwt jwt) {
		return new ResponseEntity<>(_get(jwt, null), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getSettings(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("id") String id) {

		return new ResponseEntity<>(_get(jwt, id), HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<String> getStatus(@AuthenticationPrincipal Jwt jwt) {
		Settings settings = _settingsService.getActiveSettings(jwt);

		JSONObject jsonObject = new JSONObject(
		).put(
			"active", false
		);

		if (settings != null) {
			jsonObject.put(
				"active", true
			).put(
				"provider", settings.provider
			);
		}

		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<String> saveSettings(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject settingsJSONObject = _save(jwt, json);

		if (jsonObject.getBoolean("active")) {
			_settingsService.setActiveSettings(settingsJSONObject);

			_deactivateSettings(jwt, settingsJSONObject);
		}

		return new ResponseEntity<>(
			settingsJSONObject.toString(), HttpStatus.OK);
	}

	private void _deactivateSettings(Jwt jwt, JSONObject settingsJSONObject) {
		String filter =
			"active eq true and id ne '" + settingsJSONObject.getLong("id") +
				"'";

		JSONArray jsonArray = new JSONObject(
			get(
				"Bearer " + jwt.getTokenValue(),
				"/o/c/k9l6aicontentwizardsettings?filter=" + filter)
		).getJSONArray(
			"items"
		);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject itemJSONObject = jsonArray.getJSONObject(i);

			patch(
				"Bearer " + jwt.getTokenValue(),
				new JSONObject(
				).put(
					"active", false
				).toString(),
				"/o/c/k9l6aicontentwizardsettings/" +
					itemJSONObject.getInt("id"));

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Deactivation of ",
						settingsJSONObject.getJSONObject(
							"provider"
						).getString(
							"name"
						),
						" Provider with ID: ", itemJSONObject.getInt("id")));
			}
		}
	}

	private String _get(Jwt jwt, String id) {
		String url = "/o/c/k9l6aicontentwizardsettings/";

		if (id != null) {
			url += id;
		}

		return get("Bearer " + jwt.getTokenValue(), url);
	}

	private JSONObject _save(Jwt jwt, String json) {
		JSONObject jsonObject = new JSONObject(json);

		if (jsonObject.has("id")) {
			return new JSONObject(
				patch(
					"Bearer " + jwt.getTokenValue(), json,
					"/o/c/k9l6aicontentwizardsettings/" +
						jsonObject.getLong("id")));
		}

		return new JSONObject(
			post(
				"Bearer " + jwt.getTokenValue(), json,
				"/o/c/k9l6aicontentwizardsettings"));
	}

	private static final Log _log = LogFactory.getLog(
		SettingsRestController.class);

	@Autowired
	private SettingsService _settingsService;

}