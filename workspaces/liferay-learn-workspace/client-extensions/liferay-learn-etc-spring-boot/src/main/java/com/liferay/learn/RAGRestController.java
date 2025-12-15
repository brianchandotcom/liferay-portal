/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.learn.service.RAGService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nilton Vieira
 */
@RequestMapping("/rag")
@RestController
public class RAGRestController extends BaseRestController {

	@DeleteMapping("document/{assetEntryId}")
	public ResponseEntity deleteDocument(
		@AuthenticationPrincipal Jwt jwt, @PathVariable long assetEntryId) {

		_ragService.deleteDocument(assetEntryId);

		return ResponseEntity.ok(
		).build();
	}

	@GetMapping("/search")
	public ResponseEntity<Object> getSearch(@RequestParam String query) {
		try {
			return new ResponseEntity<>(
				_ragService.search(query), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception);

			return ResponseEntity.internalServerError(
			).build();
		}
	}

	@PutMapping("document")
	public ResponseEntity putDocument(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		_ragService.addOrUpdateDocument(
			jsonObject.getLong("assetEntryId"),
			jsonObject.getString("assetEntryType"),
			jsonObject.getString("content"),
			jsonObject.getString("description"),
			jsonObject.getString("friendlyUrlPath"),
			jsonObject.getString("name"));

		return ResponseEntity.noContent(
		).build();
	}

	private static final Log _log = LogFactory.getLog(RAGRestController.class);

	@Autowired
	private RAGService _ragService;

}