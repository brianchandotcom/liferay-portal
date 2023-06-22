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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.app.license.AppLicenseVerifier;
import com.liferay.portal.app.license.internal.AppBundleTracker;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.module.framework.ModuleFrameworkUtil;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.osgi.internal.hookregistry.ActivatorHookFactory;
import org.eclipse.osgi.internal.hookregistry.HookConfigurator;
import org.eclipse.osgi.internal.hookregistry.HookRegistry;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.wiring.FrameworkWiring;

/**
 * @author Amos Fong
 */
public class MarketplaceAppLicenseHookConfigurator
	implements ActivatorHookFactory, BundleActivator, HookConfigurator {

	@Override
	public void addHooks(HookRegistry hookRegistry) {
		hookRegistry.addActivatorHookFactory(this);
	}

	@Override
	public BundleActivator createActivator() {
		return this;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_marketplaceAppLicenseVerifier = new MarketplaceAppLicenseVerifier(
			bundleContext);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("version", "1.0.0");

		_serviceRegistration = bundleContext.registerService(
			AppLicenseVerifier.class, _marketplaceAppLicenseVerifier,
			properties);

		_appBundleTracker = new AppBundleTracker(
			bundleContext, Bundle.ACTIVE, null);

		_appBundleTracker.open();

		PortalLifecycleUtil.register(
			new BasePortalLifecycle() {

				@Override
				protected void doPortalDestroy() {
				}

				@Override
				protected void doPortalInit() {
					if (_appBundleTracker != null) {
						List<Bundle> bundles =
							_appBundleTracker.getTrackedBundles();

						Framework framework =
							(Framework)ModuleFrameworkUtil.getFramework();

						FrameworkWiring frameworkWiring = framework.adapt(
							FrameworkWiring.class);

						DefaultNoticeableFuture<FrameworkEvent>
							defaultNoticeableFuture =
								new DefaultNoticeableFuture<>();

						frameworkWiring.refreshBundles(
							bundles,
							frameworkEvent -> defaultNoticeableFuture.set(
								frameworkEvent));

						try {
							FrameworkEvent frameworkEvent =
								defaultNoticeableFuture.get();

							if (frameworkEvent.getType() !=
									FrameworkEvent.PACKAGES_REFRESHED) {

								throw frameworkEvent.getThrowable();
							}
						}
						catch (Throwable throwable) {
							ReflectionUtil.throwException(throwable);
						}
					}
				}

			},
			PortalLifecycle.METHOD_INIT);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		if (_appBundleTracker != null) {
			_appBundleTracker.close();
		}

		if (_marketplaceAppLicenseVerifier != null) {
			_marketplaceAppLicenseVerifier.destroy();
		}

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private AppBundleTracker _appBundleTracker;
	private MarketplaceAppLicenseVerifier _marketplaceAppLicenseVerifier;
	private ServiceRegistration<AppLicenseVerifier> _serviceRegistration;

}