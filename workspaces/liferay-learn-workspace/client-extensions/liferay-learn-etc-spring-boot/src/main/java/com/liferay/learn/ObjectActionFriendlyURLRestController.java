/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;

import java.text.Normalizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Francesco Acciaro
 */
@RequestMapping("/object/action/friendly-url")
@RestController
public class ObjectActionFriendlyURLRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		String title = valuesJSONObject.optString("title", "");

		if (title.isEmpty() || !_isTitleChanged(title, jsonObject)) {
			return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
		}

		String dtoKey = _getDTOKey(jsonObject);

		if (dtoKey == null) {
			return new ResponseEntity<>(
				"Unsupported Object Type", HttpStatus.BAD_REQUEST);
		}

		String authorization = "Bearer " + jwt.getTokenValue();

		String newFriendlyURLPath = _getFriendlyURLPath(
			authorization, dtoKey, valuesJSONObject);

		long objectEntryId = objectEntryJSONObject.getLong("id");

		EntryType entryType = _entryTypes.get(dtoKey);

		_patchFriendlyURL(
			authorization, _getCurrentFriendlyURLPath(dtoKey, jsonObject),
			objectEntryId, newFriendlyURLPath, entryType.getRESTPath());

		_propagateFriendlyURL(
			authorization, dtoKey, objectEntryId, newFriendlyURLPath);

		return new ResponseEntity<>(json, HttpStatus.ACCEPTED);
	}

	private List<String> _getChildDTOKeys(String dtoKey) {
		List<String> childDTOKeys = new ArrayList<>();

		for (Map.Entry<String, EntryType> entry : _entryTypes.entrySet()) {
			EntryType entryType = entry.getValue();

			if (dtoKey.equals(entryType.getParentDTOKey())) {
				childDTOKeys.add(entry.getKey());
			}
		}

		return childDTOKeys;
	}

	private String _getCurrentFriendlyURLPath(
		String dtoKey, JSONObject jsonObject) {

		JSONObject dtoJSONObject = jsonObject.optJSONObject(dtoKey);

		if (dtoJSONObject == null) {
			return "";
		}

		return dtoJSONObject.optString("friendlyUrlPath", "");
	}

	private String _getDTOKey(JSONObject jsonObject) {
		for (String dtoKey : _entryTypes.keySet()) {
			if (jsonObject.has(dtoKey)) {
				return dtoKey;
			}
		}

		return null;
	}

	private String _getFriendlyURLPath(
		String authorization, String dtoKey, JSONObject valuesJSONObject) {

		EntryType entryType = _entryTypes.get(dtoKey);

		String slug = _normalizeSlug(valuesJSONObject.optString("title", ""));

		if (entryType.getFKFieldName() == null) {
			return slug;
		}

		EntryType parentEntryType = _entryTypes.get(
			entryType.getParentDTOKey());

		String fields = "id,title";

		if (parentEntryType.getFKFieldName() != null) {
			fields += "," + parentEntryType.getFKFieldName();
		}

		long parentId = valuesJSONObject.getLong(entryType.getFKFieldName());

		JSONObject parentJSONObject = new JSONObject(
			get(
				authorization,
				UriComponentsBuilder.fromPath(
					parentEntryType.getRESTPath()
				).pathSegment(
					String.valueOf(parentId)
				).queryParam(
					"fields", fields
				).build(
				).toUri()));

		String parentPath = _getFriendlyURLPath(
			authorization, entryType.getParentDTOKey(), parentJSONObject);

		return parentPath + "/" + slug;
	}

	private boolean _isTitleChanged(
		String currentTitle, JSONObject jsonObject) {

		JSONObject originalObjectEntryJSONObject = jsonObject.optJSONObject(
			"originalObjectEntry");

		if (originalObjectEntryJSONObject == null) {
			return true;
		}

		JSONObject valuesJSONObject =
			originalObjectEntryJSONObject.getJSONObject("values");

		return !currentTitle.equals(valuesJSONObject.optString("title", ""));
	}

	private String _normalizeSlug(String value) {
		String slug = Normalizer.normalize(value, Normalizer.Form.NFD);

		slug = slug.replaceAll("\\p{M}", "");

		slug = StringUtil.toLowerCase(slug);

		slug = slug.replaceAll("[^a-z0-9_-]+", "-");

		slug = slug.replaceAll("-+", "-");

		return slug.replaceAll("^-|-$", "");
	}

	private void _patchFriendlyURL(
		String authorization, String currentPath, long id, String newPath,
		String restPath) {

		if (newPath.equals(currentPath)) {
			return;
		}

		UriComponents uriComponents = UriComponentsBuilder.fromPath(
			restPath
		).pathSegment(
			String.valueOf(id)
		).build();

		patch(
			authorization,
			new JSONObject(
			).put(
				"friendlyUrlPath", newPath
			).put(
				"friendlyUrlPath_i18n",
				new JSONObject(
				).put(
					"en_US", newPath
				)
			).toString(),
			uriComponents.toUri());
	}

	private void _propagateFriendlyURL(
		String authorization, String dtoKey, long parentId, String pathPrefix) {

		List<String> childDTOKeys = _getChildDTOKeys(dtoKey);

		for (String childDTOKey : childDTOKeys) {
			EntryType childEntryType = _entryTypes.get(childDTOKey);

			JSONArray itemsJSONArray = new JSONObject(
				get(
					authorization,
					UriComponentsBuilder.fromPath(
						childEntryType.getRESTPath()
					).queryParam(
						"filter",
						StringBundler.concat(
							childEntryType.getFKFieldName(), " eq '", parentId,
							"'")
					).queryParam(
						"pageSize", -1
					).build(
					).toUri())
			).getJSONArray(
				"items"
			);

			for (int i = 0; i < itemsJSONArray.length(); i++) {
				JSONObject childJSONObject = itemsJSONArray.getJSONObject(i);

				long childId = childJSONObject.getLong("id");

				String childPath =
					pathPrefix + "/" +
						_normalizeSlug(childJSONObject.optString("title", ""));

				_patchFriendlyURL(
					authorization,
					childJSONObject.optString("friendlyUrlPath", ""), childId,
					childPath, childEntryType.getRESTPath());

				_propagateFriendlyURL(
					authorization, childDTOKey, childId, childPath);
			}
		}
	}

	private static final Map<String, EntryType> _entryTypes =
		TreeMapBuilder.put(
			"objectEntryDTOP2S3Course",
			new EntryType(null, null, "/o/c/p2s3courses")
		).put(
			"objectEntryDTOP2S3Lesson",
			new EntryType(
				"r_p2s3ModuleToP2S3Lessons_c_p2s3ModuleId",
				"objectEntryDTOP2S3Module", "/o/c/p2s3lessons")
		).put(
			"objectEntryDTOP2S3Module",
			new EntryType(
				"r_p2s3CourseToP2S3Modules_c_p2s3CourseId",
				"objectEntryDTOP2S3Course", "/o/c/p2s3modules")
		).put(
			"objectEntryDTOP2S3Quizzes",
			new EntryType(
				"r_p2s3ModuleToP2S3Quizzes_c_p2s3ModuleId",
				"objectEntryDTOP2S3Module", "/o/c/p2s3quizes")
		).build();

	private static class EntryType {

		public EntryType(
			String fkFieldName, String parentDTOKey, String restPath) {

			_fkFieldName = fkFieldName;
			_parentDTOKey = parentDTOKey;
			_restPath = restPath;
		}

		public String getFKFieldName() {
			return _fkFieldName;
		}

		public String getParentDTOKey() {
			return _parentDTOKey;
		}

		public String getRESTPath() {
			return _restPath;
		}

		private final String _fkFieldName;
		private final String _parentDTOKey;
		private final String _restPath;

	}

}