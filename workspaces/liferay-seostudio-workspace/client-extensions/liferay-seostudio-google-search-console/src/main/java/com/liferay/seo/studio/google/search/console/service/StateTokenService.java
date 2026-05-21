/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.google.search.console.service;

import io.jsonwebtoken.Claims;
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

	public String generateState(long seoStudioInstanceId, String redirectURL) {
		Date nowDate = new Date();

		return Jwts.builder(
		).claim(
			"redirectURL", redirectURL
		).claim(
			"seoStudioInstanceId", seoStudioInstanceId
		).expiration(
			new Date(nowDate.getTime() + _ONE_HOUR_MS)
		).issuedAt(
			nowDate
		).signWith(
			_secretKey
		).compact();
	}

	public Claims verifyState(String state) {
		return Jwts.parser(
		).verifyWith(
			_secretKey
		).build(
		).parseSignedClaims(
			state
		).getPayload();
	}

	private static final long _ONE_HOUR_MS = 3600000;

	private final SecretKey _secretKey;

}