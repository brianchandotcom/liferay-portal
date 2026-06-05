/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.SegmentActivation;

import java.util.Date;

/**
 * @author Shinn Lok
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class IndividualSegmentActivationDisplay {

	public IndividualSegmentActivationDisplay() {
	}

	public IndividualSegmentActivationDisplay(
		SegmentActivation segmentActivation) {

		_cronExpression = segmentActivation.getCronExpression();
		_frequencyType = String.valueOf(segmentActivation.getFrequencyType());
		_scheduleEndDate = segmentActivation.getScheduleEndDate();
		_scheduleStartDate = segmentActivation.getScheduleStartDate();
		_scheduleType = String.valueOf(segmentActivation.getScheduleType());
	}

	public String getCronExpression() {
		return _cronExpression;
	}

	public String getFrequencyType() {
		return _frequencyType;
	}

	public Date getScheduleEndDate() {
		if (_scheduleEndDate == null) {
			return null;
		}

		return new Date(_scheduleEndDate.getTime());
	}

	public Date getScheduleStartDate() {
		if (_scheduleStartDate == null) {
			return null;
		}

		return new Date(_scheduleStartDate.getTime());
	}

	public String getScheduleType() {
		return _scheduleType;
	}

	private String _cronExpression;
	private String _frequencyType;
	private Date _scheduleEndDate;
	private Date _scheduleStartDate;
	private String _scheduleType;

}