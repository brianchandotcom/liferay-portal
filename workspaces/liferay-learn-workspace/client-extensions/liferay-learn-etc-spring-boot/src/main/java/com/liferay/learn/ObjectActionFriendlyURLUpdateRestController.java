/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

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
 * @author Francesco Acciaro
 */
@RequestMapping("/object/action/friendly-url-update")
@RestController
public class ObjectActionFriendlyURLUpdateRestController
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

		if (title.isEmpty() || !_isTitleChanged(title, jsonObject)) {
			return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
		}

		String authorization = "Bearer " + jwt.getTokenValue();
		long objectEntryId = objectEntryJSONObject.getLong("id");

		if (objectDefinitionExternalReferenceCode.equals("P2S3_COURSE")) {
			_updateCourseFriendlyURL(
				authorization, jsonObject, objectEntryId, title);
		}
		else if (objectDefinitionExternalReferenceCode.equals("P2S3_MODULE")) {
			_updateModuleFriendlyURL(
				authorization, jsonObject, objectEntryId, objectEntryJSONObject,
				title);
		}
		else {
			_updateLessonOrQuizFriendlyURL(
				authorization, jsonObject,
				objectDefinitionExternalReferenceCode.equals("P2S3_LESSON"),
				objectEntryId, objectEntryJSONObject, title);
		}

		return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
	}

	private JSONObject _fetchModule(String authorization, long moduleId) {
		return new JSONObject(
			get(
				authorization,
				UriComponentsBuilder.fromPath(
					"/o/c/p2s3modules"
				).pathSegment(
					String.valueOf(moduleId)
				).queryParam(
					"fields",
					"id,title,r_p2s3CourseToP2S3Modules_c_p2s3CourseId"
				).build(
				).toUri()));
	}

	private JSONArray _fetchModulesWithChildren(
		String authorization, String filter) {

		return new JSONObject(
			get(
				authorization,
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

	private String _fetchTitleSlug(
		String authorization, long id, String urlBase) {

		JSONObject responseJSONObject = new JSONObject(
			get(
				authorization,
				UriComponentsBuilder.fromPath(
					urlBase
				).pathSegment(
					String.valueOf(id)
				).queryParam(
					"fields", "id,title"
				).build(
				).toUri()));

		return _normalizeSlug(responseJSONObject.optString("title", ""));
	}

	private String _getCurrentFriendlyURLPath(
		String dtoKey, JSONObject jsonObject) {

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

	private String _getSlugFromTitle(JSONObject jsonObject) {
		return _normalizeSlug(jsonObject.optString("title", ""));
	}

	private String _getTitle(JSONObject objectEntryJSONObject) {
		JSONObject valuesJSONObject = objectEntryJSONObject.optJSONObject(
			"values");

		if (valuesJSONObject == null) {
			return "";
		}

		return valuesJSONObject.optString("title", "");
	}

	private boolean _isTitleChanged(
		String currentTitle, JSONObject jsonObject) {

		JSONObject originalObjectEntryJSONObject = jsonObject.optJSONObject(
			"originalObjectEntry");

		if (originalObjectEntryJSONObject == null) {
			return true;
		}

		return !currentTitle.equals(_getTitle(originalObjectEntryJSONObject));
	}

	private String _normalizeSlug(String value) {
		String slug = Normalizer.normalize(value, Normalizer.Form.NFD);

		slug = slug.replaceAll("\\p{M}", "");

		slug = StringUtil.toLowerCase(slug);

		slug = slug.replaceAll("[^a-z0-9_-]+", "-");

		slug = slug.replaceAll("-+", "-");

		return slug.replaceAll("^-|-$", "");
	}

	private void _patchChildren(
		String authorization, String childrenKey, String childURLBase,
		JSONObject moduleJSONObject, String pathPrefix) {

		if (!moduleJSONObject.has(childrenKey)) {
			return;
		}

		JSONArray childrenJSONArray = moduleJSONObject.getJSONArray(
			childrenKey);

		for (int i = 0; i < childrenJSONArray.length(); i++) {
			JSONObject childJSONObject = childrenJSONArray.getJSONObject(i);

			_patchFriendlyURL(
				authorization, childJSONObject.optString("friendlyUrlPath", ""),
				childJSONObject.getLong("id"),
				StringBundler.concat(
					pathPrefix, "/", _getSlugFromTitle(childJSONObject)),
				childURLBase);
		}
	}

	private void _patchFriendlyURL(
		String authorization, String currentPath, long id, String newPath,
		String urlBase) {

		if (newPath.equals(currentPath)) {
			return;
		}

		String body = new JSONObject(
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
			authorization, body,
			UriComponentsBuilder.fromPath(
				urlBase
			).pathSegment(
				String.valueOf(id)
			).build(
			).toUri());
	}

	private void _propagateFromCourse(
		String authorization, long courseId, String courseSlug) {

		JSONArray itemsJSONArray = _fetchModulesWithChildren(
			authorization,
			"r_p2s3CourseToP2S3Modules_c_p2s3CourseId eq '" + courseId + "'");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject moduleJSONObject = itemsJSONArray.getJSONObject(i);

			String modulePath = StringBundler.concat(
				courseSlug, "/", _getSlugFromTitle(moduleJSONObject));

			_patchFriendlyURL(
				authorization,
				moduleJSONObject.optString("friendlyUrlPath", ""),
				moduleJSONObject.getLong("id"), modulePath, "/o/c/p2s3modules");

			_patchChildren(
				authorization, "p2s3ModuleToP2S3Lessons", "/o/c/p2s3lessons",
				moduleJSONObject, modulePath);
			_patchChildren(
				authorization, "p2s3ModuleToP2S3Quizzes", "/o/c/p2s3quizes",
				moduleJSONObject, modulePath);
		}
	}

	private void _propagateFromModule(
		String authorization, String courseSlug, long moduleId,
		String moduleSlug) {

		JSONArray itemsJSONArray = _fetchModulesWithChildren(
			authorization, "id eq '" + moduleId + "'");

		if (itemsJSONArray.isEmpty()) {
			return;
		}

		JSONObject moduleJSONObject = itemsJSONArray.getJSONObject(0);

		String pathPrefix = StringBundler.concat(courseSlug, "/", moduleSlug);

		_patchChildren(
			authorization, "p2s3ModuleToP2S3Lessons", "/o/c/p2s3lessons",
			moduleJSONObject, pathPrefix);
		_patchChildren(
			authorization, "p2s3ModuleToP2S3Quizzes", "/o/c/p2s3quizes",
			moduleJSONObject, pathPrefix);
	}

	private void _updateCourseFriendlyURL(
		String authorization, JSONObject jsonObject, long objectEntryId,
		String title) {

		String courseSlug = _normalizeSlug(title);

		_patchFriendlyURL(
			authorization,
			_getCurrentFriendlyURLPath("objectEntryDTOP2S3Course", jsonObject),
			objectEntryId, courseSlug, "/o/c/p2s3courses");

		_propagateFromCourse(authorization, objectEntryId, courseSlug);
	}

	private void _updateLessonOrQuizFriendlyURL(
		String authorization, JSONObject jsonObject, boolean lesson,
		long objectEntryId, JSONObject objectEntryJSONObject, String title) {

		long moduleId = objectEntryJSONObject.getJSONObject(
			"values"
		).getLong(
			lesson ? "r_p2s3ModuleToP2S3Lessons_c_p2s3ModuleId" :
				"r_p2s3ModuleToP2S3Quizzes_c_p2s3ModuleId"
		);

		JSONObject moduleJSONObject = _fetchModule(authorization, moduleId);

		String courseSlug = _fetchTitleSlug(
			authorization,
			moduleJSONObject.getLong(
				"r_p2s3CourseToP2S3Modules_c_p2s3CourseId"),
			"/o/c/p2s3courses");

		String childPath = StringBundler.concat(
			courseSlug, "/", _getSlugFromTitle(moduleJSONObject), "/",
			_normalizeSlug(title));

		_patchFriendlyURL(
			authorization,
			_getCurrentFriendlyURLPath(
				lesson ? "objectEntryDTOP2S3Lesson" :
					"objectEntryDTOP2S3Quizzes",
				jsonObject),
			objectEntryId, childPath,
			lesson ? "/o/c/p2s3lessons" : "/o/c/p2s3quizes");
	}

	private void _updateModuleFriendlyURL(
		String authorization, JSONObject jsonObject, long objectEntryId,
		JSONObject objectEntryJSONObject, String title) {

		String courseSlug = _fetchTitleSlug(
			authorization,
			objectEntryJSONObject.getJSONObject(
				"values"
			).getLong(
				"r_p2s3CourseToP2S3Modules_c_p2s3CourseId"
			),
			"/o/c/p2s3courses");
		String moduleSlug = _normalizeSlug(title);

		_patchFriendlyURL(
			authorization,
			_getCurrentFriendlyURLPath("objectEntryDTOP2S3Module", jsonObject),
			objectEntryId, StringBundler.concat(courseSlug, "/", moduleSlug),
			"/o/c/p2s3modules");

		_propagateFromModule(
			authorization, courseSlug, objectEntryId, moduleSlug);
	}

}