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

package com.liferay.portal.app.license.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Amos Fong
 */
public class AppBundleTracker extends BundleTracker {

	public AppBundleTracker(
		BundleContext bundleContext, int stateMask,
		BundleTrackerCustomizer bundleTrackerCustomizer) {

		super(bundleContext, stateMask, bundleTrackerCustomizer);

		_bundleContext = bundleContext;
	}

	@Override
	public Object addingBundle(Bundle bundle, BundleEvent bundleEvent) {
		Properties properties = _getAppLicenseProperties(bundle);

		String productId = (String)properties.get("product-id");

		if (productId != null) {
			synchronized (_bundleIdsMap) {
				Set<Long> bundleIds = _bundleIdsMap.get(productId);

				if (bundleIds == null) {
					bundleIds = new HashSet<>();

					_bundleIdsMap.put(productId, bundleIds);
				}

				bundleIds.add(bundle.getBundleId());

				if (_log.isDebugEnabled()) {
					_log.debug("Tracking " + bundle.getSymbolicName());
				}
			}
		}

		return bundle;
	}

	public List<Bundle> getBundles(String productId) {
		List<Bundle> bundles = new ArrayList<>();

		synchronized (_bundleIdsMap) {
			Set<Long> bundleIds = _bundleIdsMap.get(productId);

			if (bundleIds != null) {
				for (long bundleId : bundleIds) {
					Bundle bundle = _bundleContext.getBundle(bundleId);

					if (bundle != null) {
						bundles.add(bundle);
					}
				}
			}
		}

		return bundles;
	}

	public List<Bundle> getTrackedBundles() {
		List<Bundle> bundles = new ArrayList<>();

		synchronized (_bundleIdsMap) {
			for (String key : _bundleIdsMap.keySet()) {
				bundles.addAll(getBundles(key));
			}
		}

		return bundles;
	}

	@Override
	public void modifiedBundle(
		Bundle bundle, BundleEvent bundleEvent, Object object) {
	}

	@Override
	public void removedBundle(
		Bundle bundle, BundleEvent bundleEvent, Object object) {

		Properties properties = _getAppLicenseProperties(bundle);

		String productId = (String)properties.get("product-id");

		if (productId == null) {
			return;
		}

		synchronized (_bundleIdsMap) {
			Set<Long> bundleIds = _bundleIdsMap.get(productId);

			if (bundleIds == null) {
				return;
			}

			bundleIds.remove(bundle.getBundleId());

			if (bundleIds.isEmpty()) {
				_bundleIdsMap.remove(productId);
			}
		}
	}

	private Properties _getAppLicenseProperties(Bundle bundle) {
		Properties properties = new Properties();

		try {
			URL url = bundle.getEntry("/META-INF/marketplace.properties");

			if (url != null) {
				properties.load(url.openStream());
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return properties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppBundleTracker.class);

	private final BundleContext _bundleContext;
	private final Map<String, Set<Long>> _bundleIdsMap = new HashMap<>();

}