/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.google.search.console.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Pei-Jung Lan
 */
@Service
public class StateTokenService {

	public StateTokenService(
		@Value("${liferay.seostudio.gsc.state.secret}") String stateSecret) {

		if (stateSecret.startsWith("${") || (stateSecret.length() < 32)) {
			throw new IllegalArgumentException(
				"\"liferay.seostudio.gsc.state.secret\" must be set to a " +
					"value of at least 32 characters");
		}

		_secretKey = Keys.hmacShaKeyFor(
			stateSecret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateState(String redirectURL, long seoStudioInstanceId) {
		Date date = new Date();

		return Jwts.builder(
		).claim(
			"redirectURL", redirectURL
		).claim(
			"seoStudioInstanceId", seoStudioInstanceId
		).expiration(
			new Date(date.getTime() + 3600000)
		).issuedAt(
			date
		).signWith(
			_secretKey
		).compact();
	}

	public Claims verifyState(String state) {
		JwtParser jwtParser = Jwts.parser(
		).verifyWith(
			_secretKey
		).build();

		Jws<Claims> jws = jwtParser.parseSignedClaims(state);

		return jws.getPayload();
	}

	private final SecretKey _secretKey;

}