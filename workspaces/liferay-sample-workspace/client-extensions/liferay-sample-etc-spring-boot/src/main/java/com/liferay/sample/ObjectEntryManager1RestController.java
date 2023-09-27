/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Feliphe Marinho
 * @author Brian Wing Shun Chan
 */
@RequestMapping("/object/entry/manager/1")
@RestController
public class ObjectEntryManager1RestController extends BaseRestController {

	@DeleteMapping(
		"/{objectDefinitionExternalReferenceCode}/{externalReferenceCode}"
	)
	public ResponseEntity<String> delete(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable String objectDefinitionExternalReferenceCode,
		@PathVariable String externalReferenceCode,
		@RequestParam Map<String, String> parameters) {

		log(jwt, _log, parameters);

		Map<String, JSONObject> objectEntryJSONObjects =
			_getObjectEntryJSONObjects(objectDefinitionExternalReferenceCode);

		JSONObject objectEntryJSONObject = objectEntryJSONObjects.remove(
			externalReferenceCode);

		if (objectEntryJSONObject == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(
			objectEntryJSONObject.toString(), HttpStatus.OK);
	}

	@GetMapping("/{objectDefinitionExternalReferenceCode}")
	public ResponseEntity<String> get(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable String objectDefinitionExternalReferenceCode,
		@RequestParam Map<String, String> parameters) {

		log(jwt, _log, parameters);

		Map<String, JSONObject> objectEntryJSONObjects =
			_getObjectEntryJSONObjects(objectDefinitionExternalReferenceCode);

		return new ResponseEntity<>(
			new JSONObject(
			).put(
				"items", objectEntryJSONObjects.values()
			).put(
				"totalCount", objectEntryJSONObjects.size()
			).toString(),
			HttpStatus.OK);
	}

	@GetMapping(
		"/{objectDefinitionExternalReferenceCode}/{externalReferenceCode}"
	)
	public ResponseEntity<String> get(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable String objectDefinitionExternalReferenceCode,
		@PathVariable String externalReferenceCode,
		@RequestParam Map<String, String> parameters) {

		log(jwt, _log, parameters);

		Map<String, JSONObject> objectEntryJSONObjects =
			_getObjectEntryJSONObjects(objectDefinitionExternalReferenceCode);

		JSONObject objectEntryJSONObject = objectEntryJSONObjects.get(
			externalReferenceCode);

		if (objectEntryJSONObject == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(
			objectEntryJSONObject.toString(), HttpStatus.OK);
	}

	@PostMapping("/{objectDefinitionExternalReferenceCode}")
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable String objectDefinitionExternalReferenceCode,
		@RequestBody String json) {

		log(jwt, _log, json);

		Map<String, JSONObject> objectEntryJSONObjects =
			_getObjectEntryJSONObjects(objectDefinitionExternalReferenceCode);

		JSONObject objectEntryJSONObject = _getObjectEntryJSONObject(json);

		if (objectEntryJSONObject.isNull("creator")) {
			Faker faker = new Faker();

			Name name = faker.name();

			objectEntryJSONObject.put(
				"creator", Collections.singletonMap("name", name.fullName()));
		}

		String externalReferenceCode =
			!objectEntryJSONObject.isNull("externalReferenceCode") ?
				objectEntryJSONObject.getString("externalReferenceCode") : null;

		if ((externalReferenceCode == null) ||
			externalReferenceCode.isEmpty()) {

			externalReferenceCode = String.valueOf(UUID.randomUUID());

			objectEntryJSONObject.put(
				"externalReferenceCode", externalReferenceCode);
		}

		if (objectEntryJSONObjects.containsKey(externalReferenceCode)) {
			return new ResponseEntity<>(json, HttpStatus.CONFLICT);
		}

		objectEntryJSONObjects.put(
			externalReferenceCode, objectEntryJSONObject);

		return new ResponseEntity<>(
			objectEntryJSONObject.toString(), HttpStatus.OK);
	}

	@PutMapping(
		"/{objectDefinitionExternalReferenceCode}/{externalReferenceCode}"
	)
	public ResponseEntity<String> put(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable String objectDefinitionExternalReferenceCode,
		@PathVariable String externalReferenceCode, @RequestBody String json) {

		log(jwt, _log, json);

		Map<String, JSONObject> objectEntryJSONObjects =
			_getObjectEntryJSONObjects(objectDefinitionExternalReferenceCode);

		if (!objectEntryJSONObjects.containsKey(externalReferenceCode)) {
			return new ResponseEntity<>(json, HttpStatus.NOT_FOUND);
		}

		JSONObject objectEntryJSONObject = _getObjectEntryJSONObject(json);

		objectEntryJSONObjects.put(
			externalReferenceCode, objectEntryJSONObject);

		return new ResponseEntity<>(
			objectEntryJSONObject.toString(), HttpStatus.OK);
	}

	private JSONObject _getObjectEntryJSONObject(String json) {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		if (objectEntryJSONObject == null) {
			throw new IllegalArgumentException("Object entry is null");
		}

		return objectEntryJSONObject;
	}

	private Map<String, JSONObject> _getObjectEntryJSONObjects(
		String objectDefinitionExternalReferenceCode) {

		return _objectEntryJSONObjectsMap.computeIfAbsent(
			objectDefinitionExternalReferenceCode, key -> new HashMap<>());
	}

	private static final Log _log = LogFactory.getLog(
		ObjectEntryManager1RestController.class);

	private static final Map<String, Map<String, JSONObject>>
		_objectEntryJSONObjectsMap = new HashMap<>();

}