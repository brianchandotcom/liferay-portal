/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Pei-Jung Lan
 */
@Component
public class StateTokenService {

	public String generateState(String redirectURL, long seoStudioDomainId) {
		Date date = new Date();

		return Jwts.builder(
		).claim(
			"redirectURL", redirectURL
		).claim(
			"seoStudioDomainId", seoStudioDomainId
		).expiration(
			new Date(date.getTime() + 3600000)
		).issuedAt(
			date
		).signWith(
			_secretKey
		).compact();
	}

	public Claims verifyState(String state) {
		Jws<Claims> jws = _jwtParser.parseSignedClaims(state);

		return jws.getPayload();
	}

	@PostConstruct
	private void _initialize() {
		if (_stateSecret.startsWith("${") || (_stateSecret.length() < 32)) {
			throw new IllegalArgumentException(
				"\"liferay.seostudio.gsc.state.secret\" must be set to a " +
					"value of at least 32 characters");
		}

		_secretKey = Keys.hmacShaKeyFor(
			_stateSecret.getBytes(StandardCharsets.UTF_8));

		_jwtParser = Jwts.parser(
		).verifyWith(
			_secretKey
		).build();
	}

	private JwtParser _jwtParser;
	private SecretKey _secretKey;

	@Value("${liferay.seostudio.gsc.state.secret}")
	private String _stateSecret;

}