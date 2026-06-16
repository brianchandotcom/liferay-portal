/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class DXPUserGroup {

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		if (_createDate == null) {
			return null;
		}

		return new Date(_createDate.getTime());
	}

	public String getDescription() {
		return _description;
	}

	public String getId() {
		return _id;
	}

	public Date getModifiedDate() {
		if (_modifiedDate == null) {
			return null;
		}

		return new Date(_modifiedDate.getTime());
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public String getName() {
		return _name;
	}

	public long getParentUserGroupId() {
		return _parentUserGroupId;
	}

	public long getUserGroupId() {
		return _userGroupId;
	}

	public long getUserId() {
		return _userId;
	}

	public String getUserName() {
		return _userName;
	}

	public long getUsersCount() {
		return _usersCount;
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean isAddedByLDAPImport() {
		return _addedByLDAPImport;
	}

	public void setAddedByLDAPImport(boolean addedByLDAPImport) {
		_addedByLDAPImport = addedByLDAPImport;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateDate(Date createDate) {
		if (createDate != null) {
			_createDate = new Date(createDate.getTime());
		}
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (modifiedDate != null) {
			_modifiedDate = new Date(modifiedDate.getTime());
		}
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParentUserGroupId(long parentUserGroupId) {
		_parentUserGroupId = parentUserGroupId;
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupId = userGroupId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public void setUsersCount(long usersCount) {
		_usersCount = usersCount;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private boolean _addedByLDAPImport;
	private long _companyId;
	private Date _createDate;
	private String _description;
	private String _id;
	private Date _modifiedDate;
	private long _mvccVersion;
	private String _name;
	private long _parentUserGroupId;
	private long _userGroupId;
	private long _userId;
	private String _userName;
	private long _usersCount;
	private String _uuid;

}