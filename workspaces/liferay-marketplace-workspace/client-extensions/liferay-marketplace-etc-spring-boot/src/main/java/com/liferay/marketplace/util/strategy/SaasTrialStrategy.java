/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.util.strategy;

import com.liferay.marketplace.util.TrialProvisioningContext;

import java.net.URL;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Eduardo Diniz
 */
@Component
public class SaasTrialStrategy implements TrialStrategy {

	@Override
	public void configureContext(
		TrialProvisioningContext.TrialProvisioningContextBuilder
			trialProvisioningContextBuilder) {

		trialProvisioningContextBuilder.setConsoleCluster(
			_consoleSSACluster
		).setConsoleProjectPrefix(
			_consoleSSAProjectPrefix
		).setConsoleProjectUid(
			_consoleSSAProjectUid
		).setDeployable(
			false
		).setDomain(
			_ssaTrialDXPDomain
		).setTrialAuthorizationERC(
			"external-ssa"
		).setTrialHomePageURL(
			_externalSSAHomePageURL
		);
	}

	@Override
	public boolean supports(String orderTypeExternalReferenceCode) {
		return Objects.equals(orderTypeExternalReferenceCode, "SSA_SAAS");
	}

	@Value("${liferay.marketplace.console.ssa.cluster}")
	private String _consoleSSACluster;

	@Value("${liferay.marketplace.console.ssa.project.prefix}")
	private String _consoleSSAProjectPrefix;

	@Value("${liferay.marketplace.console.ssa.project.uid}")
	private String _consoleSSAProjectUid;

	@Value("${external.ssa.oauth2.headless.server.home.page.url}")
	private URL _externalSSAHomePageURL;

	@Value("${liferay.marketplace.ssa.dxp.domain}")
	private String _ssaTrialDXPDomain;

}