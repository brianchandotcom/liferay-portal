/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.client.dto.v1_0;

import com.liferay.osb.faro.rest.client.function.UnsafeSupplier;
import com.liferay.osb.faro.rest.client.serdes.v1_0.DataSourceSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
public class DataSource implements Cloneable, Serializable {

	public static DataSource toDTO(String json) {
		return DataSourceSerDes.toDTO(json);
	}

	public Long getAccountsCount() {
		return accountsCount;
	}

	public void setAccountsCount(Long accountsCount) {
		this.accountsCount = accountsCount;
	}

	public void setAccountsCount(
		UnsafeSupplier<Long, Exception> accountsCountUnsafeSupplier) {

		try {
			accountsCount = accountsCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long accountsCount;

	public Long getCampaignsCount() {
		return campaignsCount;
	}

	public void setCampaignsCount(Long campaignsCount) {
		this.campaignsCount = campaignsCount;
	}

	public void setCampaignsCount(
		UnsafeSupplier<Long, Exception> campaignsCountUnsafeSupplier) {

		try {
			campaignsCount = campaignsCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long campaignsCount;

	public String[] getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(String[] channelIds) {
		this.channelIds = channelIds;
	}

	public void setChannelIds(
		UnsafeSupplier<String[], Exception> channelIdsUnsafeSupplier) {

		try {
			channelIds = channelIdsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] channelIds;

	public Boolean getContactsSynced() {
		return contactsSynced;
	}

	public void setContactsSynced(Boolean contactsSynced) {
		this.contactsSynced = contactsSynced;
	}

	public void setContactsSynced(
		UnsafeSupplier<Boolean, Exception> contactsSyncedUnsafeSupplier) {

		try {
			contactsSynced = contactsSyncedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean contactsSynced;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		try {
			createDate = createDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date createDate;

	public Long getEventsCount() {
		return eventsCount;
	}

	public void setEventsCount(Long eventsCount) {
		this.eventsCount = eventsCount;
	}

	public void setEventsCount(
		UnsafeSupplier<Long, Exception> eventsCountUnsafeSupplier) {

		try {
			eventsCount = eventsCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long eventsCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setModifiedDate(
		UnsafeSupplier<Date, Exception> modifiedDateUnsafeSupplier) {

		try {
			modifiedDate = modifiedDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date modifiedDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public void setProviderType(
		UnsafeSupplier<String, Exception> providerTypeUnsafeSupplier) {

		try {
			providerType = providerTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String providerType;

	public Long getSitesCount() {
		return sitesCount;
	}

	public void setSitesCount(Long sitesCount) {
		this.sitesCount = sitesCount;
	}

	public void setSitesCount(
		UnsafeSupplier<Long, Exception> sitesCountUnsafeSupplier) {

		try {
			sitesCount = sitesCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long sitesCount;

	public Boolean getSitesSynced() {
		return sitesSynced;
	}

	public void setSitesSynced(Boolean sitesSynced) {
		this.sitesSynced = sitesSynced;
	}

	public void setSitesSynced(
		UnsafeSupplier<Boolean, Exception> sitesSyncedUnsafeSupplier) {

		try {
			sitesSynced = sitesSyncedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean sitesSynced;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setState(
		UnsafeSupplier<String, Exception> stateUnsafeSupplier) {

		try {
			state = stateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String state;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<String, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String status;

	public Long getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(Long usersCount) {
		this.usersCount = usersCount;
	}

	public void setUsersCount(
		UnsafeSupplier<Long, Exception> usersCountUnsafeSupplier) {

		try {
			usersCount = usersCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long usersCount;

	@Override
	public DataSource clone() throws CloneNotSupportedException {
		return (DataSource)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataSource)) {
			return false;
		}

		DataSource dataSource = (DataSource)object;

		return Objects.equals(toString(), dataSource.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DataSourceSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:79846034