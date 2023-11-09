/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package liferay.payment.integration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.HashMap;
import java.util.Map;

import liferay.payment.integration.constants.CommercePaymentIntegrationConstants;
import liferay.payment.integration.dto.CommercePaymentEntryDto;

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

	protected String processPayment(String json, int paymentStatus)
		throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, String> requestBodyMap = objectMapper.readValue(
			json, HashMap.class);

		if (requestBodyMap.containsKey(
				CommercePaymentIntegrationConstants.NAME)) {

			CommercePaymentEntryDto commercePaymentEntryDto =
				objectMapper.readValue(
					requestBodyMap.get(
						CommercePaymentIntegrationConstants.NAME),
					CommercePaymentEntryDto.class);

			commercePaymentEntryDto.setPaymentStatus(paymentStatus);

			ObjectWriter objectWriter = new ObjectMapper(
			).writer(
			).withDefaultPrettyPrinter();

			return objectWriter.writeValueAsString(commercePaymentEntryDto);
		}

		return null;
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	protected String lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	protected String lxcDXPServerProtocol;

}