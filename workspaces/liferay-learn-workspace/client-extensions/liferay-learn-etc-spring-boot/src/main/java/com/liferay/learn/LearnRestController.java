/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.petra.string.StringBundler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author José Abelenda
 */
@RequestMapping("/object/action/course-duration")
@RestController
public class LearnRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		long moduleId = 0;

		if (valuesJSONObject.has("r_lesson_c_moduleId")) {
			moduleId = valuesJSONObject.getLong("r_lesson_c_moduleId");
		}
		else {
			moduleId = valuesJSONObject.getLong("r_quiz_c_moduleId");
		}

		JSONObject responseJSONObject = new JSONObject(
			get(
				"Bearer " + jwt.getTokenValue(),
				StringBundler.concat(
					"/o/c/courses/scopes/", _siteGroupId,
					"?fields=id,module.quizDurationMinutes,module.lesson",
					"DurationMinutes&nestedFields=module&filter=module/id eq '",
					moduleId, "'")));

		JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

		JSONObject firstItemJSONObject = itemsJSONArray.getJSONObject(0);

		JSONArray moduleJSONArray = firstItemJSONObject.getJSONArray("module");

		long courseDuration = 0;

		for (int i = 0; i < moduleJSONArray.length(); i++) {
			JSONObject moduleJSONObject = moduleJSONArray.getJSONObject(i);

			courseDuration += moduleJSONObject.getLong("lessonDurationMinutes");
			courseDuration += moduleJSONObject.getLong("quizDurationMinutes");
		}

		JSONObject payloadJSONObject = new JSONObject();

		payloadJSONObject.put("durationMinutes", courseDuration);

		patch(
			"Bearer " + jwt.getTokenValue(), payloadJSONObject.toString(),
			"/o/c/courses/" + firstItemJSONObject.getLong("id"));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Updated duration for course: " +
					firstItemJSONObject.getLong("id"));
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		LearnRestController.class);

	@Value("${liferay.learn.dxp.site.group.id}")
	private long _siteGroupId;

}