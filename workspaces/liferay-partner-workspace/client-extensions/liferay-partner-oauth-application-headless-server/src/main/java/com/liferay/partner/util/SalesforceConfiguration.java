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

package com.liferay.partner.util;

import com.sforce.async.AsyncApiException;
import com.sforce.async.BulkConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author José Abelenda
 */
@Configuration
public class SalesforceConfiguration {

	public BulkConnection bulkConnection()
		throws AsyncApiException, ConnectionException {

		ConnectorConfig partnerConfig = new ConnectorConfig();

		partnerConfig.setUsername(_salesforceAuthUserName);
		partnerConfig.setPassword(
			_salesforceAuthPassword + _salesforceSecurityToken);
		partnerConfig.setAuthEndpoint(
			_salesforceAuthEndpoint + "/" + _salesforceApiVersion);

		new PartnerConnection(partnerConfig);

		ConnectorConfig config = new ConnectorConfig();

		config.setSessionId(partnerConfig.getSessionId());

		String soapEndpoint = partnerConfig.getServiceEndpoint();

		String restEndpoint =
			soapEndpoint.substring(0, soapEndpoint.indexOf("Soap/")) +
				"async/" + _salesforceApiVersion;

		config.setRestEndpoint(restEndpoint);

		config.setCompression(true);
		config.setTraceMessage(false);

		return new BulkConnection(config);
	}

	@Value("${salesforce.api.version}")
	private String _salesforceApiVersion;

	@Value("${salesforce.auth.endpoint}")
	private String _salesforceAuthEndpoint;

	@Value("${salesforce.auth.password}")
	private String _salesforceAuthPassword;

	@Value("${salesforce.auth.userName}")
	private String _salesforceAuthUserName;

	@Value("${salesforce.security.token}")
	private String _salesforceSecurityToken;

}