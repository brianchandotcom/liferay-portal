/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class IndividualSegmentMembershipChangeAggregation {

	public long getAddedIndividualsCount() {
		return _addedIndividualsCount;
	}

	public long getAnonymousIndividualsCount() {
		return _anonymousIndividualsCount;
	}

	public long getIndividualsCount() {
		return _individualsCount;
	}

	public Date getIntervalInitDate() {
		if (_intervalInitDate == null) {
			return null;
		}

		return new Date(_intervalInitDate.getTime());
	}

	public long getKnownIndividualsCount() {
		return _knownIndividualsCount;
	}

	public long getRemovedIndividualsCount() {
		return _removedIndividualsCount;
	}

	public void setAddedIndividualsCount(long addedIndividualsCount) {
		_addedIndividualsCount = addedIndividualsCount;
	}

	public void setAnonymousIndividualsCount(long anonymousIndividualsCount) {
		_anonymousIndividualsCount = anonymousIndividualsCount;
	}

	public void setIndividualsCount(long individualsCount) {
		_individualsCount = individualsCount;
	}

	public void setIntervalInitDate(Date intervalInitDate) {
		if (intervalInitDate != null) {
			_intervalInitDate = new Date(intervalInitDate.getTime());
		}
	}

	public void setKnownIndividualsCount(long knownIndividualsCount) {
		_knownIndividualsCount = knownIndividualsCount;
	}

	public void setRemovedIndividualsCount(long removedIndividualsCount) {
		_removedIndividualsCount = removedIndividualsCount;
	}

	private long _addedIndividualsCount;
	private long _anonymousIndividualsCount;
	private long _individualsCount;
	private Date _intervalInitDate;
	private long _knownIndividualsCount;
	private long _removedIndividualsCount;

}