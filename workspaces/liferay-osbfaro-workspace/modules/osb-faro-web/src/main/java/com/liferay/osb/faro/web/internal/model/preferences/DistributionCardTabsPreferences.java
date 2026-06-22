/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.preferences;

import com.liferay.osb.faro.web.internal.exception.FaroException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DistributionCardTabsPreferences {

	public void addDistributionTab(
		String id,
		DistributionCardTabPreferences distributionCardTabPreferences) {

		if (_order.contains(id)) {
			throw new FaroException(
				"Distribution tab title already exists: " +
					StringUtil.quote(id, StringPool.QUOTE));
		}

		_distributionCardTabPreferencesMap.put(
			id, distributionCardTabPreferences);

		_order.add(id);
	}

	public Map<String, DistributionCardTabPreferences>
		getDistributionCardTabPreferencesMap() {

		return _distributionCardTabPreferencesMap;
	}

	public List<String> getOrder() {
		return _order;
	}

	public void removeDistributionTab(String id) {
		_distributionCardTabPreferencesMap.remove(id);
		_order.remove(id);
	}

	public void setDistributionCardTabPreferencesMap(
		Map<String, DistributionCardTabPreferences>
			distributionCardTabPreferencesMap) {

		_distributionCardTabPreferencesMap = distributionCardTabPreferencesMap;
	}

	public void setOrder(List<String> order) {
		_order = order;
	}

	private Map<String, DistributionCardTabPreferences>
		_distributionCardTabPreferencesMap = new HashMap<>();
	private List<String> _order = new ArrayList<>();

}