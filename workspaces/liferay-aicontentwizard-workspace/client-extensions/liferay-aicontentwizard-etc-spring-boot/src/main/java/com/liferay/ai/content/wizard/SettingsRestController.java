/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard;

import com.liferay.ai.content.wizard.service.LiferayService;
import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public void delete(@PathVariable("id") String id) {
		delete(
			_getAuthorization(), "", "/o/c/k9l6aicontentwizardsettings" + id);
	}

	@GetMapping
	public ResponseEntity<String> get() {
		return new ResponseEntity<>(_get(), HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<String> getStatus() {
		JSONObject jsonObject = new JSONObject(_get());

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			if (itemJSONObject.getBoolean("active")) {
				return new ResponseEntity<>(
					new JSONObject(
					).put(
						"active", true
					).put(
						"provider",
						itemJSONObject.getJSONObject(
							"provider"
						).getString(
							"key"
						)
					).toString(),
					HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"active", false
			).toString(),
			HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> post(@RequestBody String json) {
		return new ResponseEntity<>(
			post(_getAuthorization(), json, "/o/c/k9l6aicontentwizardsettings"),
			HttpStatus.OK);
	}

	private String _get() {
		return get(_getAuthorization(), "/o/c/k9l6aicontentwizardsettings");
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-aicontentwizard-oauth-application-headless-server");
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private LiferayService _liferayService;

}