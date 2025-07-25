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
public class SolutionsTrialStrategy implements TrialStrategy {

	@Override
	public void configureContext(
		TrialProvisioningContext.TrialProvisioningContextBuilder
			trialProvisioningContextBuilder) {

		trialProvisioningContextBuilder.setConsoleCluster(
			_consoleTrialCluster
		).setConsoleProjectPrefix(
			_consoleTrialProjectPrefix
		).setConsoleProjectUid(
			_consoleTrialProjectUid
		).setDomain(
			_trialDXPDomain
		).setDeployable(
			true
		).setTrialAuthorizationERC(
			"external-trial"
		).setTrialHomePageURL(
			_externalTrialHomePageURL
		);
	}

	@Override
	public boolean supports(String orderTypeExternalReferenceCode) {
		return Objects.equals(orderTypeExternalReferenceCode, "SOLUTIONS7");
	}

	@Value("${liferay.marketplace.console.cluster}")
	private String _consoleTrialCluster;

	@Value("${liferay.marketplace.console.project.prefix}")
	private String _consoleTrialProjectPrefix;

	@Value("${liferay.marketplace.console.project.uid}")
	private String _consoleTrialProjectUid;

	@Value("${external.trial.oauth2.headless.server.home.page.url}")
	private URL _externalTrialHomePageURL;

	@Value("${liferay.marketplace.trial.dxp.domain}")
	private String _trialDXPDomain;

}