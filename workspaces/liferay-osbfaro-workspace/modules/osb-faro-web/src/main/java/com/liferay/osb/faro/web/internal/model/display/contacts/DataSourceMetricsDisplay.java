/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

/**
 * @author Rachael Koestartyo
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DataSourceMetricsDisplay {

	public DataSourceMetricsDisplay() {
	}

	public DataSourceMetricsDisplay(
		long channelsCount, long groupsCount, long individualsCount) {

		_channelsCount = channelsCount;
		_groupsCount = groupsCount;
		_individualsCount = individualsCount;
	}

	public long getChannelsCount() {
		return _channelsCount;
	}

	public long getGroupsCount() {
		return _groupsCount;
	}

	public long getIndividualsCount() {
		return _individualsCount;
	}

	private long _channelsCount;
	private long _groupsCount;
	private long _individualsCount;

}