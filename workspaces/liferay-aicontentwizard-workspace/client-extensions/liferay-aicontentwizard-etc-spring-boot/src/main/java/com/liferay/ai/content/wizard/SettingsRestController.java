/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard;

import com.liferay.ai.content.wizard.model.Settings;
import com.liferay.ai.content.wizard.service.SettingsService;
import com.liferay.client.extension.util.spring.boot3.BaseRestController;
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
import org.springframework.web.util.UriComponentsBuilder;

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
			createURI("/o/c/k9l6aicontentwizardsettings/", id));
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
		JSONObject jsonObject = new JSONObject();

		Settings settings = _settingsService.getActiveSettings(jwt);

		if (settings != null) {
			jsonObject.put(
				"active", true
			).put(
				"provider", settings.providerName
			);
		}
		else {
			jsonObject.put("active", false);
		}

		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject settingsJSONObject = null;

		if (jsonObject.has("id")) {
			settingsJSONObject = new JSONObject(
				patch(
					"Bearer " + jwt.getTokenValue(), json,
					createURI(
						"/o/c/k9l6aicontentwizardsettings/",
						jsonObject.getLong("id"))));
		}
		else {
			settingsJSONObject = new JSONObject(
				post(
					"Bearer " + jwt.getTokenValue(), json,
					createURI("/o/c/k9l6aicontentwizardsettings")));
		}

		if (!jsonObject.getBoolean("active")) {
			return new ResponseEntity<>(
				settingsJSONObject.toString(), HttpStatus.OK);
		}

		_settingsService.setActiveSettings(settingsJSONObject);

		JSONArray jsonArray = new JSONObject(
			get(
				"Bearer " + jwt.getTokenValue(),
				UriComponentsBuilder.fromPath(
					"/o/c/k9l6aicontentwizardsettings"
				).queryParam(
					"filter",
					"active eq true and id ne '" +
						settingsJSONObject.getLong("id") + "'"
				).build(
				).toUri())
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
				createURI(
					"/o/c/k9l6aicontentwizardsettings/",
					itemJSONObject.getInt("id")));

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Deactivated ", itemJSONObject.getInt("id"),
						" with provider ",
						settingsJSONObject.getJSONObject(
							"provider"
						).getString(
							"name"
						)));
			}
		}

		return new ResponseEntity<>(
			settingsJSONObject.toString(), HttpStatus.OK);
	}

	private String _get(Jwt jwt, String id) {
		String url = "/o/c/k9l6aicontentwizardsettings/";

		if (id != null) {
			url += id;
		}

		return get("Bearer " + jwt.getTokenValue(), createURI(url));
	}

	private static final Log _log = LogFactory.getLog(
		SettingsRestController.class);

	@Autowired
	private SettingsService _settingsService;

}