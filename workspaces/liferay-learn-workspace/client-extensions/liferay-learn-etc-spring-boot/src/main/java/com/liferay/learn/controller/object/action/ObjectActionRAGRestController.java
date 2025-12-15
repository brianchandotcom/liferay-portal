/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn.controller.object.action;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.net.URI;

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
 * @author Nilton Vieira
 */
@RequestMapping("/object/action/rag")
@RestController
public class ObjectActionRAGRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		put(
			jwt.getTokenValue(),
			new JSONObject(
			).put(
				"assetEntryId", jsonObject.getLong("classPK")
			).put(
				"assetEntryType", "Object"
			).put(
				"content",
				_getNestedKey(
					jsonObject, "objectEntryDTOLesson", "properties",
					"contentRawText")
			).put(
				"description",
				_getNestedKey(
					jsonObject, "objectEntryDTOLesson", "properties",
					"description")
			).put(
				"friendlyUrlPath", "/l/" + jsonObject.getLong("classPK")
			).put(
				"name",
				_getNestedKey(
					jsonObject, "objectEntryDTOLesson", "properties", "name")
			).toString(),
			new URI("http://localhost:58081/rag/document"));

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private String _getNestedKey(JSONObject jsonObject, String... keys) {
		if (!jsonObject.has(keys[0])) {
			return StringPool.BLANK;
		}

		if (keys.length == 1) {
			return jsonObject.getString(keys[0]);
		}

		return _getNestedKey(
			jsonObject.getJSONObject(keys[0]),
			ArrayUtil.subset(keys, 1, keys.length));
	}

}