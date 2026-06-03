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
 * This class is a wrapper for {@link FaroProjectEmailDomain}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroProjectEmailDomain
 * @generated
 */
public class FaroProjectEmailDomainWrapper
	extends BaseModelWrapper<FaroProjectEmailDomain>
	implements FaroProjectEmailDomain, ModelWrapper<FaroProjectEmailDomain> {

	public FaroProjectEmailDomainWrapper(
		FaroProjectEmailDomain faroProjectEmailDomain) {

		super(faroProjectEmailDomain);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"faroProjectEmailDomainId", getFaroProjectEmailDomainId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("faroProjectId", getFaroProjectId());
		attributes.put("emailDomain", getEmailDomain());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long faroProjectEmailDomainId = (Long)attributes.get(
			"faroProjectEmailDomainId");

		if (faroProjectEmailDomainId != null) {
			setFaroProjectEmailDomainId(faroProjectEmailDomainId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long faroProjectId = (Long)attributes.get("faroProjectId");

		if (faroProjectId != null) {
			setFaroProjectId(faroProjectId);
		}

		String emailDomain = (String)attributes.get("emailDomain");

		if (emailDomain != null) {
			setEmailDomain(emailDomain);
		}
	}

	@Override
	public FaroProjectEmailDomain cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this faro project email domain.
	 *
	 * @return the company ID of this faro project email domain
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the email domain of this faro project email domain.
	 *
	 * @return the email domain of this faro project email domain
	 */
	@Override
	public String getEmailDomain() {
		return model.getEmailDomain();
	}

	/**
	 * Returns the faro project email domain ID of this faro project email domain.
	 *
	 * @return the faro project email domain ID of this faro project email domain
	 */
	@Override
	public long getFaroProjectEmailDomainId() {
		return model.getFaroProjectEmailDomainId();
	}

	/**
	 * Returns the faro project ID of this faro project email domain.
	 *
	 * @return the faro project ID of this faro project email domain
	 */
	@Override
	public long getFaroProjectId() {
		return model.getFaroProjectId();
	}

	/**
	 * Returns the group ID of this faro project email domain.
	 *
	 * @return the group ID of this faro project email domain
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this faro project email domain.
	 *
	 * @return the mvcc version of this faro project email domain
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this faro project email domain.
	 *
	 * @return the primary key of this faro project email domain
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this faro project email domain.
	 *
	 * @param companyId the company ID of this faro project email domain
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the email domain of this faro project email domain.
	 *
	 * @param emailDomain the email domain of this faro project email domain
	 */
	@Override
	public void setEmailDomain(String emailDomain) {
		model.setEmailDomain(emailDomain);
	}

	/**
	 * Sets the faro project email domain ID of this faro project email domain.
	 *
	 * @param faroProjectEmailDomainId the faro project email domain ID of this faro project email domain
	 */
	@Override
	public void setFaroProjectEmailDomainId(long faroProjectEmailDomainId) {
		model.setFaroProjectEmailDomainId(faroProjectEmailDomainId);
	}

	/**
	 * Sets the faro project ID of this faro project email domain.
	 *
	 * @param faroProjectId the faro project ID of this faro project email domain
	 */
	@Override
	public void setFaroProjectId(long faroProjectId) {
		model.setFaroProjectId(faroProjectId);
	}

	/**
	 * Sets the group ID of this faro project email domain.
	 *
	 * @param groupId the group ID of this faro project email domain
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this faro project email domain.
	 *
	 * @param mvccVersion the mvcc version of this faro project email domain
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this faro project email domain.
	 *
	 * @param primaryKey the primary key of this faro project email domain
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected FaroProjectEmailDomainWrapper wrap(
		FaroProjectEmailDomain faroProjectEmailDomain) {

		return new FaroProjectEmailDomainWrapper(faroProjectEmailDomain);
	}

}