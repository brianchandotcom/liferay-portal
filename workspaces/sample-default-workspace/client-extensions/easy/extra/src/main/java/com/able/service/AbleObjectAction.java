/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.able.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Brian Wing Shun Chan
 */
@RestController
public class AbleObjectAction {

	@PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE,
		value = "/able-object/action"
	)
	public ResponseEntity<String> action(
			@AuthenticationPrincipal Jwt jwt, @RequestBody JsonNode jsonNode)
		throws JsonMappingException, JsonProcessingException {

		System.out.println("JWT ID: " + jwt.getId());
		System.out.println("JWT SUBJECT: " + jwt.getSubject());
		System.out.println("JWT CLAIMS: " + jwt.getClaims());

		String msg = jsonNode.toString();

		System.out.println("ACTION PAYLOAD: " + msg);

		return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}

}