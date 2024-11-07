/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.content.wizard.services.LiferayService;
import com.liferay.content.wizard.services.WizardSettingService;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class Settings extends BaseRestController {

	@DeleteMapping("/{id}")
	public void deleteSetting(@PathVariable("id") String id) {
		_liferayService.deleteContentWizardSetting(id);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getSettings() {
		return new ResponseEntity<>(
			_liferayService.getContentWizardSettings(), HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/status")
	public ResponseEntity<String> getStatus() {
		JSONObject settingJSONObject =
			_wizardSettingService.getActiveWizardSettingJSONObject();

		if (settingJSONObject == null) {
			return new ResponseEntity<>(
				new JSONObject(
				).put(
					"active", false
				).toString(),
				HttpStatus.OK);
		}

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"active", true
			).put(
				"provider",
				settingJSONObject.getJSONObject(
					"provider"
				).getString(
					"key"
				)
			).toString(),
			HttpStatus.OK);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> postSetting(@RequestBody String json) {
		return new ResponseEntity<>(
			_liferayService.postContentWizardSettings(json), HttpStatus.OK);
	}

	@Autowired
	private LiferayService _liferayService;

	@Autowired
	private WizardSettingService _wizardSettingService;

}