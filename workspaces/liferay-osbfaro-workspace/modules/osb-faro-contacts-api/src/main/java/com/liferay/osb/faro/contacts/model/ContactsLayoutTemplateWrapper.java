/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ContactsLayoutTemplate}.
 * </p>
 *
 * @author Shinn Lok
 * @see ContactsLayoutTemplate
 * @generated
 */
public class ContactsLayoutTemplateWrapper
	extends BaseModelWrapper<ContactsLayoutTemplate>
	implements ContactsLayoutTemplate, ModelWrapper<ContactsLayoutTemplate> {

	public ContactsLayoutTemplateWrapper(
		ContactsLayoutTemplate contactsLayoutTemplate) {

		super(contactsLayoutTemplate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"contactsLayoutTemplateId", getContactsLayoutTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createTime", getCreateTime());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put(
			"headerContactsCardTemplateIds",
			getHeaderContactsCardTemplateIds());
		attributes.put("name", getName());
		attributes.put("settings", getSettings());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long contactsLayoutTemplateId = (Long)attributes.get(
			"contactsLayoutTemplateId");

		if (contactsLayoutTemplateId != null) {
			setContactsLayoutTemplateId(contactsLayoutTemplateId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long createTime = (Long)attributes.get("createTime");

		if (createTime != null) {
			setCreateTime(createTime);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		String headerContactsCardTemplateIds = (String)attributes.get(
			"headerContactsCardTemplateIds");

		if (headerContactsCardTemplateIds != null) {
			setHeaderContactsCardTemplateIds(headerContactsCardTemplateIds);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public ContactsLayoutTemplate cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this contacts layout template.
	 *
	 * @return the company ID of this contacts layout template
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the contacts layout template ID of this contacts layout template.
	 *
	 * @return the contacts layout template ID of this contacts layout template
	 */
	@Override
	public long getContactsLayoutTemplateId() {
		return model.getContactsLayoutTemplateId();
	}

	/**
	 * Returns the create time of this contacts layout template.
	 *
	 * @return the create time of this contacts layout template
	 */
	@Override
	public long getCreateTime() {
		return model.getCreateTime();
	}

	/**
	 * Returns the group ID of this contacts layout template.
	 *
	 * @return the group ID of this contacts layout template
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the header contacts card template IDs of this contacts layout template.
	 *
	 * @return the header contacts card template IDs of this contacts layout template
	 */
	@Override
	public String getHeaderContactsCardTemplateIds() {
		return model.getHeaderContactsCardTemplateIds();
	}

	/**
	 * Returns the modified time of this contacts layout template.
	 *
	 * @return the modified time of this contacts layout template
	 */
	@Override
	public long getModifiedTime() {
		return model.getModifiedTime();
	}

	/**
	 * Returns the mvcc version of this contacts layout template.
	 *
	 * @return the mvcc version of this contacts layout template
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this contacts layout template.
	 *
	 * @return the name of this contacts layout template
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this contacts layout template.
	 *
	 * @return the primary key of this contacts layout template
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the settings of this contacts layout template.
	 *
	 * @return the settings of this contacts layout template
	 */
	@Override
	public String getSettings() {
		return model.getSettings();
	}

	/**
	 * Returns the type of this contacts layout template.
	 *
	 * @return the type of this contacts layout template
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this contacts layout template.
	 *
	 * @return the user ID of this contacts layout template
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this contacts layout template.
	 *
	 * @return the user name of this contacts layout template
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this contacts layout template.
	 *
	 * @return the user uuid of this contacts layout template
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this contacts layout template.
	 *
	 * @param companyId the company ID of this contacts layout template
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the contacts layout template ID of this contacts layout template.
	 *
	 * @param contactsLayoutTemplateId the contacts layout template ID of this contacts layout template
	 */
	@Override
	public void setContactsLayoutTemplateId(long contactsLayoutTemplateId) {
		model.setContactsLayoutTemplateId(contactsLayoutTemplateId);
	}

	/**
	 * Sets the create time of this contacts layout template.
	 *
	 * @param createTime the create time of this contacts layout template
	 */
	@Override
	public void setCreateTime(long createTime) {
		model.setCreateTime(createTime);
	}

	/**
	 * Sets the group ID of this contacts layout template.
	 *
	 * @param groupId the group ID of this contacts layout template
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the header contacts card template IDs of this contacts layout template.
	 *
	 * @param headerContactsCardTemplateIds the header contacts card template IDs of this contacts layout template
	 */
	@Override
	public void setHeaderContactsCardTemplateIds(
		String headerContactsCardTemplateIds) {

		model.setHeaderContactsCardTemplateIds(headerContactsCardTemplateIds);
	}

	/**
	 * Sets the modified time of this contacts layout template.
	 *
	 * @param modifiedTime the modified time of this contacts layout template
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		model.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the mvcc version of this contacts layout template.
	 *
	 * @param mvccVersion the mvcc version of this contacts layout template
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this contacts layout template.
	 *
	 * @param name the name of this contacts layout template
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this contacts layout template.
	 *
	 * @param primaryKey the primary key of this contacts layout template
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the settings of this contacts layout template.
	 *
	 * @param settings the settings of this contacts layout template
	 */
	@Override
	public void setSettings(String settings) {
		model.setSettings(settings);
	}

	/**
	 * Sets the type of this contacts layout template.
	 *
	 * @param type the type of this contacts layout template
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this contacts layout template.
	 *
	 * @param userId the user ID of this contacts layout template
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this contacts layout template.
	 *
	 * @param userName the user name of this contacts layout template
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this contacts layout template.
	 *
	 * @param userUuid the user uuid of this contacts layout template
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
	protected ContactsLayoutTemplateWrapper wrap(
		ContactsLayoutTemplate contactsLayoutTemplate) {

		return new ContactsLayoutTemplateWrapper(contactsLayoutTemplate);
	}

}