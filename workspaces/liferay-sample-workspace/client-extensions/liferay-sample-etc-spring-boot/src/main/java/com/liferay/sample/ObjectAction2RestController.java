/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;

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
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
@RequestMapping("/object/action/2")
@RestController
public class ObjectAction2RestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		JSONObject jsonObject = new JSONObject(json);

		JSONObject modelDTOAccountJSONObject = jsonObject.getJSONObject(
			"modelDTOAccount");

		if (modelDTOAccountJSONObject == null) {
			return new ResponseEntity<>(json, HttpStatus.OK);
		}

		return new ResponseEntity<>(
			patch(
				"Bearer " + jwt.getTokenValue(),
				new JSONObject(
				).put(
					"alternateName",
					modelDTOAccountJSONObject.getString("givenName")
				).toString(),
				UriComponentsBuilder.fromPath(
					"/o/headless-admin-user/v1.0/user-accounts/" +
						modelDTOAccountJSONObject.getLong("id")
				).build(
				).toUri()),
			HttpStatus.OK);
	}

}