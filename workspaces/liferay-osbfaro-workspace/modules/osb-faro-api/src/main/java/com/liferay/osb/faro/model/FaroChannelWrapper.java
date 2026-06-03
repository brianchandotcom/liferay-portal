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
 * This class is a wrapper for {@link FaroChannel}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroChannel
 * @generated
 */
public class FaroChannelWrapper
	extends BaseModelWrapper<FaroChannel>
	implements FaroChannel, ModelWrapper<FaroChannel> {

	public FaroChannelWrapper(FaroChannel faroChannel) {
		super(faroChannel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("faroChannelId", getFaroChannelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("channelId", getChannelId());
		attributes.put("name", getName());
		attributes.put("permissionType", getPermissionType());
		attributes.put("workspaceGroupId", getWorkspaceGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long faroChannelId = (Long)attributes.get("faroChannelId");

		if (faroChannelId != null) {
			setFaroChannelId(faroChannelId);
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

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Long createTime = (Long)attributes.get("createTime");

		if (createTime != null) {
			setCreateTime(createTime);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		String channelId = (String)attributes.get("channelId");

		if (channelId != null) {
			setChannelId(channelId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer permissionType = (Integer)attributes.get("permissionType");

		if (permissionType != null) {
			setPermissionType(permissionType);
		}

		Long workspaceGroupId = (Long)attributes.get("workspaceGroupId");

		if (workspaceGroupId != null) {
			setWorkspaceGroupId(workspaceGroupId);
		}
	}

	@Override
	public FaroChannel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the channel ID of this faro channel.
	 *
	 * @return the channel ID of this faro channel
	 */
	@Override
	public String getChannelId() {
		return model.getChannelId();
	}

	/**
	 * Returns the company ID of this faro channel.
	 *
	 * @return the company ID of this faro channel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create time of this faro channel.
	 *
	 * @return the create time of this faro channel
	 */
	@Override
	public long getCreateTime() {
		return model.getCreateTime();
	}

	/**
	 * Returns the faro channel ID of this faro channel.
	 *
	 * @return the faro channel ID of this faro channel
	 */
	@Override
	public long getFaroChannelId() {
		return model.getFaroChannelId();
	}

	/**
	 * Returns the group ID of this faro channel.
	 *
	 * @return the group ID of this faro channel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified time of this faro channel.
	 *
	 * @return the modified time of this faro channel
	 */
	@Override
	public long getModifiedTime() {
		return model.getModifiedTime();
	}

	/**
	 * Returns the mvcc version of this faro channel.
	 *
	 * @return the mvcc version of this faro channel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this faro channel.
	 *
	 * @return the name of this faro channel
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the permission type of this faro channel.
	 *
	 * @return the permission type of this faro channel
	 */
	@Override
	public int getPermissionType() {
		return model.getPermissionType();
	}

	/**
	 * Returns the primary key of this faro channel.
	 *
	 * @return the primary key of this faro channel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this faro channel.
	 *
	 * @return the user ID of this faro channel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this faro channel.
	 *
	 * @return the user name of this faro channel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this faro channel.
	 *
	 * @return the user uuid of this faro channel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the workspace group ID of this faro channel.
	 *
	 * @return the workspace group ID of this faro channel
	 */
	@Override
	public long getWorkspaceGroupId() {
		return model.getWorkspaceGroupId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the channel ID of this faro channel.
	 *
	 * @param channelId the channel ID of this faro channel
	 */
	@Override
	public void setChannelId(String channelId) {
		model.setChannelId(channelId);
	}

	/**
	 * Sets the company ID of this faro channel.
	 *
	 * @param companyId the company ID of this faro channel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create time of this faro channel.
	 *
	 * @param createTime the create time of this faro channel
	 */
	@Override
	public void setCreateTime(long createTime) {
		model.setCreateTime(createTime);
	}

	/**
	 * Sets the faro channel ID of this faro channel.
	 *
	 * @param faroChannelId the faro channel ID of this faro channel
	 */
	@Override
	public void setFaroChannelId(long faroChannelId) {
		model.setFaroChannelId(faroChannelId);
	}

	/**
	 * Sets the group ID of this faro channel.
	 *
	 * @param groupId the group ID of this faro channel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified time of this faro channel.
	 *
	 * @param modifiedTime the modified time of this faro channel
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		model.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the mvcc version of this faro channel.
	 *
	 * @param mvccVersion the mvcc version of this faro channel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this faro channel.
	 *
	 * @param name the name of this faro channel
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the permission type of this faro channel.
	 *
	 * @param permissionType the permission type of this faro channel
	 */
	@Override
	public void setPermissionType(int permissionType) {
		model.setPermissionType(permissionType);
	}

	/**
	 * Sets the primary key of this faro channel.
	 *
	 * @param primaryKey the primary key of this faro channel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this faro channel.
	 *
	 * @param userId the user ID of this faro channel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this faro channel.
	 *
	 * @param userName the user name of this faro channel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this faro channel.
	 *
	 * @param userUuid the user uuid of this faro channel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the workspace group ID of this faro channel.
	 *
	 * @param workspaceGroupId the workspace group ID of this faro channel
	 */
	@Override
	public void setWorkspaceGroupId(long workspaceGroupId) {
		model.setWorkspaceGroupId(workspaceGroupId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected FaroChannelWrapper wrap(FaroChannel faroChannel) {
		return new FaroChannelWrapper(faroChannel);
	}

}