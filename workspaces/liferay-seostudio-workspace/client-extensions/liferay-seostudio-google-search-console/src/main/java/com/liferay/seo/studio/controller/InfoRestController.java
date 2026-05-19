/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.controller;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;

import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Pei-Jung Lan
 */
@CrossOrigin("*")
@RequestMapping("/info")
@RestController
public class InfoRestController extends BaseRestController {

	@GetMapping
	public ResponseEntity<String> get(HttpServletRequest httpServletRequest) {
		return ResponseEntity.ok(
			new JSONObject(
			).put(
				"callbackURL",
				ServletUriComponentsBuilder.fromContextPath(
					httpServletRequest
				).path(
					"/callback"
				).toUriString()
			).toString());
	}

}