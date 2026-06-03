/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FaroNotification}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroNotification
 * @generated
 */
public class FaroNotificationWrapper
	extends BaseModelWrapper<FaroNotification>
	implements FaroNotification, ModelWrapper<FaroNotification> {

	public FaroNotificationWrapper(FaroNotification faroNotification) {
		super(faroNotification);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("faroNotificationId", getFaroNotificationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("ownerId", getOwnerId());
		attributes.put("scope", getScope());
		attributes.put("read", isRead());
		attributes.put("type", getType());
		attributes.put("subtype", getSubtype());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long faroNotificationId = (Long)attributes.get("faroNotificationId");

		if (faroNotificationId != null) {
			setFaroNotificationId(faroNotificationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long createTime = (Long)attributes.get("createTime");

		if (createTime != null) {
			setCreateTime(createTime);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		String scope = (String)attributes.get("scope");

		if (scope != null) {
			setScope(scope);
		}

		Boolean read = (Boolean)attributes.get("read");

		if (read != null) {
			setRead(read);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String subtype = (String)attributes.get("subtype");

		if (subtype != null) {
			setSubtype(subtype);
		}
	}

	@Override
	public FaroNotification cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this faro notification.
	 *
	 * @return the company ID of this faro notification
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create time of this faro notification.
	 *
	 * @return the create time of this faro notification
	 */
	@Override
	public long getCreateTime() {
		return model.getCreateTime();
	}

	/**
	 * Returns the faro notification ID of this faro notification.
	 *
	 * @return the faro notification ID of this faro notification
	 */
	@Override
	public long getFaroNotificationId() {
		return model.getFaroNotificationId();
	}

	/**
	 * Returns the group ID of this faro notification.
	 *
	 * @return the group ID of this faro notification
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified time of this faro notification.
	 *
	 * @return the modified time of this faro notification
	 */
	@Override
	public long getModifiedTime() {
		return model.getModifiedTime();
	}

	/**
	 * Returns the mvcc version of this faro notification.
	 *
	 * @return the mvcc version of this faro notification
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the owner ID of this faro notification.
	 *
	 * @return the owner ID of this faro notification
	 */
	@Override
	public long getOwnerId() {
		return model.getOwnerId();
	}

	/**
	 * Returns the primary key of this faro notification.
	 *
	 * @return the primary key of this faro notification
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the read of this faro notification.
	 *
	 * @return the read of this faro notification
	 */
	@Override
	public boolean getRead() {
		return model.getRead();
	}

	/**
	 * Returns the scope of this faro notification.
	 *
	 * @return the scope of this faro notification
	 */
	@Override
	public String getScope() {
		return model.getScope();
	}

	/**
	 * Returns the subtype of this faro notification.
	 *
	 * @return the subtype of this faro notification
	 */
	@Override
	public String getSubtype() {
		return model.getSubtype();
	}

	/**
	 * Returns the type of this faro notification.
	 *
	 * @return the type of this faro notification
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this faro notification.
	 *
	 * @return the user ID of this faro notification
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this faro notification.
	 *
	 * @return the user uuid of this faro notification
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this faro notification is read.
	 *
	 * @return <code>true</code> if this faro notification is read; <code>false</code> otherwise
	 */
	@Override
	public boolean isRead() {
		return model.isRead();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this faro notification.
	 *
	 * @param companyId the company ID of this faro notification
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create time of this faro notification.
	 *
	 * @param createTime the create time of this faro notification
	 */
	@Override
	public void setCreateTime(long createTime) {
		model.setCreateTime(createTime);
	}

	/**
	 * Sets the faro notification ID of this faro notification.
	 *
	 * @param faroNotificationId the faro notification ID of this faro notification
	 */
	@Override
	public void setFaroNotificationId(long faroNotificationId) {
		model.setFaroNotificationId(faroNotificationId);
	}

	/**
	 * Sets the group ID of this faro notification.
	 *
	 * @param groupId the group ID of this faro notification
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified time of this faro notification.
	 *
	 * @param modifiedTime the modified time of this faro notification
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		model.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the mvcc version of this faro notification.
	 *
	 * @param mvccVersion the mvcc version of this faro notification
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the owner ID of this faro notification.
	 *
	 * @param ownerId the owner ID of this faro notification
	 */
	@Override
	public void setOwnerId(long ownerId) {
		model.setOwnerId(ownerId);
	}

	/**
	 * Sets the primary key of this faro notification.
	 *
	 * @param primaryKey the primary key of this faro notification
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this faro notification is read.
	 *
	 * @param read the read of this faro notification
	 */
	@Override
	public void setRead(boolean read) {
		model.setRead(read);
	}

	/**
	 * Sets the scope of this faro notification.
	 *
	 * @param scope the scope of this faro notification
	 */
	@Override
	public void setScope(String scope) {
		model.setScope(scope);
	}

	/**
	 * Sets the subtype of this faro notification.
	 *
	 * @param subtype the subtype of this faro notification
	 */
	@Override
	public void setSubtype(String subtype) {
		model.setSubtype(subtype);
	}

	/**
	 * Sets the type of this faro notification.
	 *
	 * @param type the type of this faro notification
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this faro notification.
	 *
	 * @param userId the user ID of this faro notification
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this faro notification.
	 *
	 * @param userUuid the user uuid of this faro notification
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected FaroNotificationWrapper wrap(FaroNotification faroNotification) {
		return new FaroNotificationWrapper(faroNotification);
	}

}