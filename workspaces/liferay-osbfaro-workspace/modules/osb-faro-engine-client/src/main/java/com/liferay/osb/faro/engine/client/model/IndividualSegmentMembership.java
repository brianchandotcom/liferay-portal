/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class IndividualSegmentMembership {

	public Date getDateCreated() {
		if (_dateCreated == null) {
			return null;
		}

		return new Date(_dateCreated.getTime());
	}

	public Date getDateRemoved() {
		if (_dateRemoved == null) {
			return null;
		}

		return new Date(_dateRemoved.getTime());
	}

	public String getIndividualId() {
		return _individualId;
	}

	public String getIndividualSegmentId() {
		return _individualSegmentId;
	}

	public String getStatus() {
		return _status;
	}

	public void setDateCreated(Date dateCreated) {
		if (dateCreated != null) {
			_dateCreated = new Date(dateCreated.getTime());
		}
	}

	public void setDateRemoved(Date dateRemoved) {
		if (dateRemoved != null) {
			_dateRemoved = new Date(dateRemoved.getTime());
		}
	}

	public void setIndividualId(String individualId) {
		_individualId = individualId;
	}

	public void setIndividualSegmentId(String individualSegmentId) {
		_individualSegmentId = individualSegmentId;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public enum Status {

		ACTIVE, INACTIVE

	}

	private Date _dateCreated;
	private Date _dateRemoved;
	private String _individualId;
	private String _individualSegmentId;
	private String _status = Status.ACTIVE.name();

}