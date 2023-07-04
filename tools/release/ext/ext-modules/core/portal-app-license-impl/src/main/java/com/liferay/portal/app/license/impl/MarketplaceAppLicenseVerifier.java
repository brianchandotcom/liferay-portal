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

package com.liferay.portal.app.license.impl;

import com.liferay.portal.app.license.AppLicenseVerifier;
import com.liferay.portal.app.license.impl.events.MarketplaceAppServicePreAction;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.license.util.LicenseManager;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.license.util.DefaultLicenseManagerImpl;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PortalImpl;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Amos Fong
 */
public class MarketplaceAppLicenseVerifier implements AppLicenseVerifier {

	public MarketplaceAppLicenseVerifier(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public void destroy() {
		if (_bundleListener != null) {
			_bundleContext.removeBundleListener(_bundleListener);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #verify(String, String, String, String[])}
	 */
	@Deprecated
	@Override
	public void verify(
			Bundle bundle, String productId, String productType,
			String productVersion)
		throws Exception {

		verify(
			productId, productType, productVersion, bundle.getSymbolicName());
	}

	@Override
	public void verify(
			String productId, String productType, String productVersion,
			String... bundleSymbolicNames)
		throws Exception {

		_init();

		if (_log.isDebugEnabled()) {
			_log.debug("Verifying " + productId + "-" + productVersion + ".");
		}

		_verify(
			productId, GetterUtil.getInteger(productType),
			GetterUtil.getInteger(productVersion), bundleSymbolicNames);
	}

	private int _getLicenseState(String productId, int productVersion)
		throws Exception {

		Map<String, String> licenseProperties = new HashMap<>();

		licenseProperties.put("productId", productId);
		licenseProperties.put("productVersion", String.valueOf(productVersion));

		return LicenseManagerUtil.getLicenseState(licenseProperties);
	}

	private void _init() {
		if (_initialized) {
			return;
		}

		_bundleListener = new MarketplaceAppBundleListener();

		_bundleContext.addBundleListener(_bundleListener);

		if (DigesterUtil.getDigester() == null) {
			DigesterUtil digesterUtil = new DigesterUtil();

			digesterUtil.setDigester(new DigesterImpl());
		}

		if (FileUtil.getFile() == null) {
			FileUtil fileUtil = new FileUtil();

			fileUtil.setFile(new FileImpl());
		}

		if (LicenseManagerUtil.getLicenseManager() == null) {
			LicenseManagerUtil licenseManagerUtil = new LicenseManagerUtil();

			try {
				Class<?> clazz = Class.forName(
					"com.liferay.portal.license.util.LocalLicenseManagerImpl");

				licenseManagerUtil.setLicenseManager(
					(LicenseManager)clazz.newInstance());

				if (_log.isDebugEnabled()) {
					_log.debug("Using LocalLicenseManagerImpl");
				}
			}
			catch (Exception exception) {
				licenseManagerUtil.setLicenseManager(
					new DefaultLicenseManagerImpl());

				if (_log.isDebugEnabled()) {
					_log.debug("Using DefaultLicenseManagerImpl");
				}
			}
		}

		if (PortalUtil.getPortal() == null) {
			PortalUtil portalUtil = new PortalUtil();

			portalUtil.setPortal(new PortalImpl());
		}

		_initialized = true;
	}

	private void _registerServicePreAction(String... bundleSymbolicNames) {
		synchronized (_developerBundleNames) {
			for (String bundleSymbolicName : bundleSymbolicNames) {
				_developerBundleNames.add(bundleSymbolicName);
			}

			if (_serviceRegistration != null) {
				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Enabling developer mode");
			}

			Dictionary<String, Object> dictionary = new HashMapDictionary<>();

			dictionary.put("key", PropsKeys.SERVLET_SERVICE_EVENTS_PRE);

			_serviceRegistration = _bundleContext.registerService(
				LifecycleAction.class,
				new MarketplaceAppServicePreAction(
					_developerBundleNames,
					PortalUtil.getPathContext() + "/c/portal/license"),
				dictionary);
		}
	}

	private void _verify(
			String productId, int productType, int productVersion,
			String... bundleSymbolicNames)
		throws Exception {

		if ((productType == _PRODUCT_TYPE_CE_ONLY) ||
			(productType == _PRODUCT_TYPE_EE_ONLY)) {

			int buildNumber = ReleaseInfo.getBuildNumber();

			if ((buildNumber % 100) < 10) {
				if (productType == _PRODUCT_TYPE_EE_ONLY) {
					throw new Exception(
						"This application requires a valid Liferay DXP " +
							"license");
				}
			}
			else {
				if (productType == _PRODUCT_TYPE_CE_ONLY) {
					throw new Exception(
						"This Liferay DXP version does not support this " +
							"application");
				}
			}
		}

		if (Validator.isNull(productId)) {
			return;
		}

		int licenseState = _getLicenseState(productId, productVersion);

		if (licenseState != _STATE_GOOD) {
			LicenseManagerUtil.checkLicense(productId);

			licenseState = _getLicenseState(productId, productVersion);
		}

		if (licenseState != _STATE_GOOD) {
			throw new Exception(
				"This application does not have a valid license");
		}

		Map<String, String> licenseProperties =
			LicenseManagerUtil.getLicenseProperties(productId);

		if (licenseProperties == null) {
			throw new Exception(
				"This Liferay version does not support this application");
		}

		if (licenseProperties != null) {
			int maxValidProductVersion = GetterUtil.getInteger(
				licenseProperties.get("productVersion"));

			if ((productVersion > 0) &&
				(productVersion > maxValidProductVersion)) {

				throw new Exception(
					"The version of your application is not compatible with " +
						"the registered license");
			}
		}

		String description = GetterUtil.getString(
			licenseProperties.get("description"));

		if (description.startsWith("Developer License")) {
			try {
				_registerServicePreAction(bundleSymbolicNames);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				throw new Exception(
					"Unable to initialize Liferay package filter");
			}
		}
	}

	private static final int _PRODUCT_TYPE_CE_ONLY = 1;

	private static final int _PRODUCT_TYPE_EE_ONLY = 2;

	private static final int _STATE_GOOD = 3;

	private static final Log _log = LogFactoryUtil.getLog(
		MarketplaceAppLicenseVerifier.class);

	private final BundleContext _bundleContext;
	private BundleListener _bundleListener;
	private final Set<String> _developerBundleNames = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private boolean _initialized;
	private ServiceRegistration<LifecycleAction> _serviceRegistration;

	private class MarketplaceAppBundleListener implements BundleListener {

		@Override
		public void bundleChanged(BundleEvent event) {
			if (event.getType() != BundleEvent.STOPPED) {
				return;
			}

			Bundle bundle = event.getBundle();

			synchronized (_developerBundleNames) {
				if (_developerBundleNames.remove(bundle.getSymbolicName()) &&
					(_serviceRegistration != null) &&
					_developerBundleNames.isEmpty()) {

					_serviceRegistration.unregister();

					_serviceRegistration = null;

					if (_log.isDebugEnabled()) {
						_log.debug("Disabling developer mode");
					}
				}
			}
		}

	}

}