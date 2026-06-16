/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class ActivityAggregation {

	public Date getIntervalInitDate() {
		if (_intervalInitDate == null) {
			return null;
		}

		return new Date(_intervalInitDate.getTime());
	}

	public int getTotalElements() {
		return _totalElements;
	}

	public void setIntervalInitDate(Date intervalInitDate) {
		if (intervalInitDate != null) {
			_intervalInitDate = new Date(intervalInitDate.getTime());
		}
	}

	public void setTotalElements(int totalElements) {
		_totalElements = totalElements;
	}

	private Date _intervalInitDate;
	private int _totalElements;

}