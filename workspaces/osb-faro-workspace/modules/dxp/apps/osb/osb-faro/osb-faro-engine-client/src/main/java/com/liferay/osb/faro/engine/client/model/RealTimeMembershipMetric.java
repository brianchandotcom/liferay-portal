/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Nilton Vieira
 */
public class RealTimeMembershipMetric {

	public Metric getAverageSegmentMembershipDurationMetric() {
		return _averageSegmentMembershipDurationMetric;
	}

	public Metric getEntryRateMetric() {
		return _entryRateMetric;
	}

	public Metric getExitRateMetric() {
		return _exitRateMetric;
	}

	public TotalMembersMetric getTotalMembersMetric() {
		return _totalMembersMetric;
	}

	public void setAverageSegmentMembershipDurationMetric(
		Metric averageSegmentMembershipDurationMetric) {

		_averageSegmentMembershipDurationMetric =
			averageSegmentMembershipDurationMetric;
	}

	public void setEntryRateMetric(Metric entryRateMetric) {
		_entryRateMetric = entryRateMetric;
	}

	public void setExitRateMetric(Metric exitRateMetric) {
		_exitRateMetric = exitRateMetric;
	}

	public void setTotalMembersMetric(TotalMembersMetric totalMembersMetric) {
		_totalMembersMetric = totalMembersMetric;
	}

	private Metric _averageSegmentMembershipDurationMetric;
	private Metric _entryRateMetric;
	private Metric _exitRateMetric;
	private TotalMembersMetric _totalMembersMetric;

}