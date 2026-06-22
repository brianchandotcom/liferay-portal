/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class Organization {

	public String getDataSourceId() {
		return _dataSourceId;
	}

	public Date getDateCreated() {
		if (_dateCreated == null) {
			return null;
		}

		return new Date(_dateCreated.getTime());
	}

	public Date getDateModified() {
		if (_dateModified == null) {
			return null;
		}

		return new Date(_dateModified.getTime());
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getNameTreePath() {
		return _nameTreePath;
	}

	public long getOrganizationPK() {
		return _organizationPK;
	}

	public String getParentName() {
		return _parentName;
	}

	public long getParentOrganizationPK() {
		return _parentOrganizationPK;
	}

	public String getType() {
		return _type;
	}

	public void setDataSourceId(String dataSourceId) {
		_dataSourceId = dataSourceId;
	}

	public void setDateCreated(Date dateCreated) {
		if (dateCreated != null) {
			_dateCreated = new Date(dateCreated.getTime());
		}
	}

	public void setDateModified(Date dateModified) {
		if (dateModified != null) {
			_dateModified = new Date(dateModified.getTime());
		}
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNameTreePath(String nameTreePath) {
		_nameTreePath = nameTreePath;
	}

	public void setOrganizationPK(long organizationPK) {
		_organizationPK = organizationPK;
	}

	public void setParentName(String parentName) {
		_parentName = parentName;
	}

	public void setParentOrganizationPK(long parentOrganizationPK) {
		_parentOrganizationPK = parentOrganizationPK;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _dataSourceId;
	private Date _dateCreated;
	private Date _dateModified;
	private String _id;
	private String _name;
	private String _nameTreePath;
	private long _organizationPK;
	private String _parentName;
	private long _parentOrganizationPK;
	private String _type;

}