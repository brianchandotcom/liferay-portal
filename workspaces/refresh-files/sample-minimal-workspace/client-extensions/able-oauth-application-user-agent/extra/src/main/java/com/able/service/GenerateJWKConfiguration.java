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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;

/**
 * This demonstrates how to generate an JWK Public and Private Key Pair for DXP
 * in order to enable JWT.
 *
 * @author Brian Wing Shun Chan
 */
public class GenerateJWKConfiguration {

	public static void main(String[] args) throws Exception {
		RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);

		rsaJsonWebKey.setAlgorithm("RS256");
		rsaJsonWebKey.setKeyId("authServer");

		String privateKeyString = rsaJsonWebKey.toJson(
			JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);

		Path path = Paths.get(
			"..", "..", "..", "configs", "common", "osgi", "configs",
			"com.liferay.oauth2.provider.rest.internal.configuration." +
				"OAuth2AuthorizationServerConfiguration.config");

		File configFile = path.toFile();

		File parentDir = configFile.getParentFile();

		parentDir.mkdirs();

		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(configFile))) {

			writer.write(
				"oauth2.authorization.server.issue.jwt.access.token=" +
					"B\"true\"\n");
			writer.write(
				"oauth2.authorization.server.jwt.access.token.signing.json." +
					"web.key=\"");
			writer.write(privateKeyString.replaceAll("\"", "\\\\\""));
			writer.write("\"");
		}
	}

}