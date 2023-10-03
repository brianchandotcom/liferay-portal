/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.clarity;

import java.util.Map;

import org.apache.commons.logging.Log;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
public abstract class BaseRestController {

	protected void log(Jwt jwt, Log log) {
		if (log.isInfoEnabled()) {
			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
		}
	}

	protected void log(Jwt jwt, Log log, Map<String, String> parameters) {
		if (log.isInfoEnabled()) {
			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
			log.info("Parameters: " + parameters);
		}
	}

	protected void log(Jwt jwt, Log log, String json) {
		if (log.isInfoEnabled()) {
			try {
				JSONObject jsonObject = new JSONObject(json);

				log.info("JSON: " + jsonObject.toString(4));
			}
			catch (Exception exception) {
				log.error("JSON: " + json, exception);
			}

			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
		}
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	protected String lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	protected String lxcDXPServerProtocol;

}