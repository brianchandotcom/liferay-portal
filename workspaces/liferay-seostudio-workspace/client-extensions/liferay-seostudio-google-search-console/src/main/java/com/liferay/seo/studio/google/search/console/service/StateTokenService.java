/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.google.search.console.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

/**
 * @author Pei-Jung Lan
 */
@Service
public class StateTokenService {

	public StateTokenService() {
		_secretKey = Jwts.SIG.HS256.key(
		).build();
	}

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
		JwtParser jwtParser = Jwts.parser(
		).verifyWith(
			_secretKey
		).build();

		Jws<Claims> jws = jwtParser.parseSignedClaims(state);

		return jws.getPayload();
	}

	private final SecretKey _secretKey;

}