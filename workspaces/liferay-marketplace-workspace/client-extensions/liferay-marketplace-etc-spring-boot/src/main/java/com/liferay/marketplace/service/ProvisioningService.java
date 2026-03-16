/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.provisioning.marketplace.rest.client.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.marketplace.rest.client.resource.v1_0.AppLicenseKeyResource;
import com.liferay.osb.provisioning.rest.client.resource.v1_0.LicenseKeyResource;

import java.net.URL;

import java.time.ZonedDateTime;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Caleb Hall
 */
@Component
public class ProvisioningService extends BaseService {

	public AppLicenseKeyResource getAppLicenseKeyResource() throws Exception {
		return AppLicenseKeyResource.builder(
		).header(
			"Authorization",
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"external-provisioning")
		).endpoint(
			_externalProvisioningHomePageURL
		).build();
	}

	public LicenseKeyResource getLicenseKeyResource() throws Exception {
		return LicenseKeyResource.builder(
		).header(
			"Authorization",
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"external-provisioning")
		).endpoint(
			_externalProvisioningHomePageURL
		).build();
	}

	public AppLicenseKey postAppLicenseKey(AppLicenseKey appLicenseKey, Jwt jwt)
		throws Exception {

		appLicenseKey.setActive(true);
		appLicenseKey.setCreateDate(new Date());

		ProductPurchase productPurchase = _koroneikiService.getProductPurchase(
			appLicenseKey.getProductPurchaseKey());

		Date expirationDate = productPurchase.getEndDate();

		if (productPurchase.getPerpetual()) {
			expirationDate = Date.from(
				ZonedDateTime.now(
				).plusYears(
					100
				).toInstant());
		}

		appLicenseKey.setExpirationDate(expirationDate);

		AppLicenseKey.LicenseType licenseType =
			AppLicenseKey.LicenseType.PRODUCTION;

		appLicenseKey.setLicenseType(licenseType);

		appLicenseKey.setOwner((String)jwt.getClaim("username"));

		if (appLicenseKey.getProductId() == null) {
			appLicenseKey.setProductId(productPurchase.getProductKey());
		}

		appLicenseKey.setProductName(
			productPurchase.getProduct(
			).getName());
		appLicenseKey.setProductVersion("1");

		Date startDate = productPurchase.getStartDate();

		if (startDate == null) {
			startDate = new Date();
		}

		appLicenseKey.setStartDate(startDate);
		appLicenseKey.setUserName((String)jwt.getClaim("username"));
		appLicenseKey.setUserUuid((String)jwt.getClaim("sub"));

		AppLicenseKeyResource appLicenseKeyResource =
			getAppLicenseKeyResource();

		appLicenseKey = appLicenseKeyResource.postAppLicenseKey(
			jwt.getClaim("username"), jwt.getClaim("sub"), appLicenseKey);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Created app license key for " +
					appLicenseKey.getProductName());
		}

		return appLicenseKey;
	}

	private static final Log _log = LogFactory.getLog(
		ProvisioningService.class);

	@Value("${external.provisioning.oauth2.headless.server.home.page.url}")
	private URL _externalProvisioningHomePageURL;

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}