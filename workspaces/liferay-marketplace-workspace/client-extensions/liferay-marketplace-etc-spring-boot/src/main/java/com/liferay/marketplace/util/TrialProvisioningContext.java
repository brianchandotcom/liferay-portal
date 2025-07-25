/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.util;

import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.util.strategy.TrialStrategy;

import java.net.URL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author Eduardo Diniz
 */
public class TrialProvisioningContext {

	public Boolean deployable() {
		return _deployable;
	}

	public String getConsoleCluster() {
		return _consoleCluster;
	}

	public String getConsoleProjectPrefix() {
		return _consoleProjectPrefix;
	}

	public String getConsoleProjectUid() {
		return _consoleProjectUid;
	}

	public String getDomain() {
		return _domain;
	}

	public String getTrialAuthorizationERC() {
		return _trialAuthorizationERC;
	}

	public URL getTrialHomePageURL() {
		return _trialHomePageURL;
	}

	@Component
	public static class Factory {

		public TrialProvisioningContext create(
				String orderTypeExternalReferenceCode)
			throws Exception {

			for (TrialStrategy trialStrategy : _trialStrategy) {
				if (trialStrategy.supports(orderTypeExternalReferenceCode)) {
					TrialProvisioningContextBuilder
						trialProvisioningContextBuilder =
							new TrialProvisioningContextBuilder();

					trialStrategy.configureContext(
						trialProvisioningContextBuilder);

					return trialProvisioningContextBuilder.build();
				}
			}

			throw new IllegalArgumentException(
				"Unsupported orderType: " + orderTypeExternalReferenceCode);
		}

		@Autowired
		@Lazy
		private MarketplaceService _marketplaceService;

		@Autowired
		private List<TrialStrategy> _trialStrategy;

	}

	public static class TrialProvisioningContextBuilder {

		public TrialProvisioningContext build() {
			return new TrialProvisioningContext(
				_consoleCluster, _consoleProjectPrefix, _consoleProjectUid,
				_deployable, _domain, _trialAuthorizationERC,
				_trialHomePageURL);
		}

		public TrialProvisioningContextBuilder setConsoleCluster(String value) {
			_consoleCluster = value;

			return this;
		}

		public TrialProvisioningContextBuilder setConsoleProjectPrefix(
			String value) {

			_consoleProjectPrefix = value;

			return this;
		}

		public TrialProvisioningContextBuilder setConsoleProjectUid(
			String value) {

			_consoleProjectUid = value;

			return this;
		}

		public TrialProvisioningContextBuilder setDeployable(boolean value) {
			_deployable = value;

			return this;
		}

		public TrialProvisioningContextBuilder setDomain(String value) {
			_domain = value;

			return this;
		}

		public TrialProvisioningContextBuilder setTrialAuthorizationERC(
			String trialAuthorizationERC) {

			_trialAuthorizationERC = trialAuthorizationERC;

			return this;
		}

		public TrialProvisioningContextBuilder setTrialHomePageURL(URL value) {
			_trialHomePageURL = value;

			return this;
		}

		private String _consoleCluster;
		private String _consoleProjectPrefix;
		private String _consoleProjectUid;
		private boolean _deployable;
		private String _domain;
		private String _trialAuthorizationERC;
		private URL _trialHomePageURL;

	}

	private TrialProvisioningContext(
		String consoleCluster, String consoleProjectPrefix,
		String consoleProjectUid, boolean deployable, String domain,
		String trialAuthorizationERC, URL trialHomePageURL) {

		_consoleCluster = consoleCluster;
		_consoleProjectPrefix = consoleProjectPrefix;
		_consoleProjectUid = consoleProjectUid;
		_deployable = deployable;
		_domain = domain;
		_trialAuthorizationERC = trialAuthorizationERC;
		_trialHomePageURL = trialHomePageURL;
	}

	private final String _consoleCluster;
	private final String _consoleProjectPrefix;
	private final String _consoleProjectUid;
	private final boolean _deployable;
	private final String _domain;
	private final String _trialAuthorizationERC;
	private final URL _trialHomePageURL;

}