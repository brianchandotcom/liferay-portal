/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.pubsub.credentials;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
@Component
public class ServiceAccountCredentialsProvider {

	public static final String SCOPE =
		"https://www.googleapis.com/auth/cloud-platform";

	public CredentialsProvider getCredentialsProvider() throws IOException {
		if (Validator.isNotNull(_clientEmailAddress) &&
			Validator.isNotNull(_clientId) &&
			Validator.isNotNull(_privateKeyId) &&
			Validator.isNotNull(_privateKeyPkcs8)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Using service account credentials for " +
						_clientEmailAddress);
			}

			Credentials serviceAccountCredentials =
				ServiceAccountCredentials.fromPkcs8(
					_clientId, _clientEmailAddress, _privateKeyPkcs8,
					_privateKeyId, Arrays.asList(SCOPE));

			return FixedCredentialsProvider.create(serviceAccountCredentials);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Using application default credentials");
		}

		GoogleCredentials googleCredentials =
			GoogleCredentials.getApplicationDefault();

		return FixedCredentialsProvider.create(
			googleCredentials.createScoped(Arrays.asList(SCOPE)));
	}

	private static final Log _log = LogFactory.getLog(
		ServiceAccountCredentialsProvider.class);

	@Value(
		"${liferay.one.pubsub.service.account.credentials.client.email.address:}"
	)
	private String _clientEmailAddress;

	@Value("${liferay.one.pubsub.service.account.credentials.client.id:}")
	private String _clientId;

	@Value("${liferay.one.pubsub.service.account.credentials.private.key.id:}")
	private String _privateKeyId;

	@Value(
		"${liferay.one.pubsub.service.account.credentials.private.key.pkcs8:}"
	)
	private String _privateKeyPkcs8;

}