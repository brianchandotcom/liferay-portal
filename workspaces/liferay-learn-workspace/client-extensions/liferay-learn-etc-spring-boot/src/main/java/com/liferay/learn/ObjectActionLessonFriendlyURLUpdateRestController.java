/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringBundler;

import java.text.Normalizer;

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
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Ana Beatriz Alves
 */
@RequestMapping("/object/action/lesson-friendly-url-update")
@RestController
public class ObjectActionLessonFriendlyURLUpdateRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		String objectDefinitionExternalReferenceCode =
			_getObjectDefinitionExternalReferenceCode(jsonObject);

		if (objectDefinitionExternalReferenceCode == null) {
			return new ResponseEntity<>(
				"Unsupported Object Type", HttpStatus.BAD_REQUEST);
		}

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		String title = _getTitle(objectEntryJSONObject);

		if (title.isEmpty() || !_isTitleChanged(jsonObject, title)) {
			return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
		}

		String bearer = "Bearer " + jwt.getTokenValue();
		long objectEntryId = objectEntryJSONObject.getLong("id");

		if (objectDefinitionExternalReferenceCode.equals("P2S3_COURSE")) {
			_handleCourse(bearer, objectEntryId, title, jsonObject);
		}
		else if (objectDefinitionExternalReferenceCode.equals("P2S3_MODULE")) {
			_handleModule(
				bearer, objectEntryId, title, objectEntryJSONObject,
				jsonObject);
		}
		else {
			_handleLessonOrQuiz(
				bearer, objectEntryId, title, objectEntryJSONObject, jsonObject,
				objectDefinitionExternalReferenceCode.equals("P2S3_LESSON"));
		}

		return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
	}

	private JSONObject _fetchModule(String jwt, long moduleId) {
		return new JSONObject(
			get(
				jwt,
				UriComponentsBuilder.fromPath(
					"/o/c/p2s3modules/" + moduleId
				).queryParam(
					"fields",
					"id,title,r_p2s3CourseToP2S3Modules_c_p2s3CourseId"
				).build(
				).toUri()));
	}

	private JSONArray _fetchModulesWithChildren(String jwt, String filter) {
		return new JSONObject(
			get(
				jwt,
				UriComponentsBuilder.fromPath(
					"/o/c/p2s3modules"
				).queryParam(
					"filter", filter
				).queryParam(
					"nestedFields",
					"p2s3ModuleToP2S3Lessons,p2s3ModuleToP2S3Quizzes"
				).queryParam(
					"pageSize", -1
				).build(
				).toUri())
		).getJSONArray(
			"items"
		);
	}

	private String _fetchTitleSlug(String jwt, String urlBase, long id) {
		JSONObject responseJSONObject = new JSONObject(
			get(
				jwt,
				UriComponentsBuilder.fromPath(
					urlBase + id
				).queryParam(
					"fields", "id,title"
				).build(
				).toUri()));

		return _normalizeSlug(responseJSONObject.optString("title", ""));
	}

	private String _getCurrentFriendlyUrlPath(
		JSONObject jsonObject, String dtoKey) {

		JSONObject dtoJSONObject = jsonObject.optJSONObject(dtoKey);

		if (dtoJSONObject == null) {
			return "";
		}

		return dtoJSONObject.optString("friendlyUrlPath", "");
	}

	private String _getObjectDefinitionExternalReferenceCode(
		JSONObject jsonObject) {

		if (jsonObject.has("objectEntryDTOP2S3Course")) {
			return "P2S3_COURSE";
		}

		if (jsonObject.has("objectEntryDTOP2S3Module")) {
			return "P2S3_MODULE";
		}

		if (jsonObject.has("objectEntryDTOP2S3Lesson")) {
			return "P2S3_LESSON";
		}

		if (jsonObject.has("objectEntryDTOP2S3Quizzes")) {
			return "P2S3_QUIZZES";
		}

		return null;
	}

	private String _getTitle(JSONObject objectEntryJSONObject) {
		JSONObject valuesJSONObject = objectEntryJSONObject.optJSONObject(
			"values");

		if (valuesJSONObject == null) {
			return "";
		}

		return valuesJSONObject.optString("title", "");
	}

	private void _handleCourse(
		String bearer, long objectEntryId, String title,
		JSONObject jsonObject) {

		String courseSlug = _normalizeSlug(title);

		_patchFriendlyUrl(
			bearer, "/o/c/p2s3courses/", objectEntryId, courseSlug,
			_getCurrentFriendlyUrlPath(jsonObject, "objectEntryDTOP2S3Course"));

		_propagateFromCourse(bearer, objectEntryId, courseSlug);
	}

	private void _handleLessonOrQuiz(
		String bearer, long objectEntryId, String title,
		JSONObject objectEntryJSONObject, JSONObject jsonObject,
		boolean lesson) {

		long moduleId = objectEntryJSONObject.getJSONObject(
			"values"
		).getLong(
			lesson ? "r_p2s3ModuleToP2S3Lessons_c_p2s3ModuleId" :
				"r_p2s3ModuleToP2S3Quizzes_c_p2s3ModuleId"
		);

		JSONObject moduleJSONObject = _fetchModule(bearer, moduleId);

		String courseSlug = _fetchTitleSlug(
			bearer, "/o/c/p2s3courses/",
			moduleJSONObject.getLong(
				"r_p2s3CourseToP2S3Modules_c_p2s3CourseId"));

		String childPath = StringBundler.concat(
			courseSlug, "/", _slugFromTitle(moduleJSONObject), "/",
			_normalizeSlug(title));

		_patchFriendlyUrl(
			bearer, lesson ? "/o/c/p2s3lessons/" : "/o/c/p2s3quizes/",
			objectEntryId, childPath,
			_getCurrentFriendlyUrlPath(
				jsonObject,
				lesson ? "objectEntryDTOP2S3Lesson" :
					"objectEntryDTOP2S3Quizzes"));
	}

	private void _handleModule(
		String bearer, long objectEntryId, String title,
		JSONObject objectEntryJSONObject, JSONObject jsonObject) {

		String courseSlug = _fetchTitleSlug(
			bearer, "/o/c/p2s3courses/",
			objectEntryJSONObject.getJSONObject(
				"values"
			).getLong(
				"r_p2s3CourseToP2S3Modules_c_p2s3CourseId"
			));
		String moduleSlug = _normalizeSlug(title);

		_patchFriendlyUrl(
			bearer, "/o/c/p2s3modules/", objectEntryId,
			courseSlug + "/" + moduleSlug,
			_getCurrentFriendlyUrlPath(jsonObject, "objectEntryDTOP2S3Module"));

		_propagateFromModule(bearer, objectEntryId, courseSlug, moduleSlug);
	}

	private boolean _isTitleChanged(
		JSONObject jsonObject, String currentTitle) {

		JSONObject originalObjectEntryJSONObject = jsonObject.optJSONObject(
			"originalObjectEntry");

		if (originalObjectEntryJSONObject == null) {
			return true;
		}

		return !currentTitle.equals(_getTitle(originalObjectEntryJSONObject));
	}

	private String _normalizeSlug(String value) {
		String slug = Normalizer.normalize(value, Normalizer.Form.NFD);

		return slug.replaceAll(
			"\\p{M}", ""
		).toLowerCase(
		).replaceAll(
			"[^a-z0-9_-]+", "-"
		).replaceAll(
			"-+", "-"
		).replaceAll(
			"^-|-$", ""
		);
	}

	private void _patchChildren(
		String jwt, JSONObject moduleJSONObject, String childrenKey,
		String childUrlBase, String pathPrefix) {

		if (!moduleJSONObject.has(childrenKey)) {
			return;
		}

		JSONArray childrenJSONArray = moduleJSONObject.getJSONArray(
			childrenKey);

		for (int i = 0; i < childrenJSONArray.length(); i++) {
			JSONObject childJSONObject = childrenJSONArray.getJSONObject(i);

			_patchFriendlyUrl(
				jwt, childUrlBase, childJSONObject.getLong("id"),
				pathPrefix + _slugFromTitle(childJSONObject),
				childJSONObject.optString("friendlyUrlPath", ""));
		}
	}

	private void _patchFriendlyUrl(
		String jwt, String urlBase, long id, String newPath,
		String currentPath) {

		if (newPath.equals(currentPath)) {
			return;
		}

		String requestBody = new JSONObject(
		).put(
			"friendlyUrlPath", newPath
		).put(
			"friendlyUrlPath_i18n",
			new JSONObject(
			).put(
				"en_US", newPath
			)
		).toString();

		patch(
			jwt, requestBody,
			UriComponentsBuilder.fromPath(
				urlBase + id
			).build(
			).toUri());
	}

	private void _propagateFromCourse(
		String jwt, long courseId, String courseSlug) {

		JSONArray itemsJSONArray = _fetchModulesWithChildren(
			jwt,
			"r_p2s3CourseToP2S3Modules_c_p2s3CourseId eq '" + courseId + "'");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject moduleJSONObject = itemsJSONArray.getJSONObject(i);

			String modulePath =
				courseSlug + "/" + _slugFromTitle(moduleJSONObject);

			_patchFriendlyUrl(
				jwt, "/o/c/p2s3modules/", moduleJSONObject.getLong("id"),
				modulePath, moduleJSONObject.optString("friendlyUrlPath", ""));

			_patchChildren(
				jwt, moduleJSONObject, "p2s3ModuleToP2S3Lessons",
				"/o/c/p2s3lessons/", modulePath + "/");
			_patchChildren(
				jwt, moduleJSONObject, "p2s3ModuleToP2S3Quizzes",
				"/o/c/p2s3quizes/", modulePath + "/");
		}
	}

	private void _propagateFromModule(
		String jwt, long moduleId, String courseSlug, String moduleSlug) {

		JSONArray itemsJSONArray = _fetchModulesWithChildren(
			jwt, "id eq '" + moduleId + "'");

		if (itemsJSONArray.isEmpty()) {
			return;
		}

		JSONObject moduleJSONObject = itemsJSONArray.getJSONObject(0);

		String pathPrefix = StringBundler.concat(
			courseSlug, "/", moduleSlug, "/");

		_patchChildren(
			jwt, moduleJSONObject, "p2s3ModuleToP2S3Lessons",
			"/o/c/p2s3lessons/", pathPrefix);
		_patchChildren(
			jwt, moduleJSONObject, "p2s3ModuleToP2S3Quizzes",
			"/o/c/p2s3quizes/", pathPrefix);
	}

	private String _slugFromTitle(JSONObject jsonObject) {
		return _normalizeSlug(jsonObject.optString("title", ""));
	}

}