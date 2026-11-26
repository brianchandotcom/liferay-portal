/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "A data source connected to the LDP workspace, such as a Liferay DXP instance, a CRM or a CSV upload, that feeds it individuals, accounts, campaigns or analytics. Credentials are never returned.",
	value = "DataSource"
)
@io.swagger.v3.oas.annotations.media.Schema(
	description = "A data source connected to the LDP workspace, such as a Liferay DXP instance, a CRM or a CSV upload, that feeds it individuals, accounts, campaigns or analytics. Credentials are never returned."
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DataSource")
public class DataSource implements Serializable {

	public static DataSource toDTO(String json) {
		return ObjectMapperUtil.readValue(DataSource.class, json);
	}

	public static DataSource unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(DataSource.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Number of accounts synced from the data source. Only `getWorkspaceGroupDataSource` returns it, for DEMANDBASE, MARKETO_CAMPAIGN (Marketo companies) and SALESFORCE data sources; it is null otherwise."
	)
	public Long getAccountsCount() {
		if (_accountsCountSupplier != null) {
			accountsCount = _accountsCountSupplier.get();

			_accountsCountSupplier = null;
		}

		return accountsCount;
	}

	public void setAccountsCount(Long accountsCount) {
		this.accountsCount = accountsCount;

		_accountsCountSupplier = null;
	}

	@JsonIgnore
	public void setAccountsCount(
		UnsafeSupplier<Long, Exception> accountsCountUnsafeSupplier) {

		_accountsCountSupplier = () -> {
			try {
				return accountsCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Number of accounts synced from the data source. Only `getWorkspaceGroupDataSource` returns it, for DEMANDBASE, MARKETO_CAMPAIGN (Marketo companies) and SALESFORCE data sources; it is null otherwise."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long accountsCount;

	@JsonIgnore
	private Supplier<Long> _accountsCountSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Number of campaigns synced from the data source. Only `getWorkspaceGroupDataSource` returns it, for SALESFORCE data sources; it is null otherwise."
	)
	public Long getCampaignsCount() {
		if (_campaignsCountSupplier != null) {
			campaignsCount = _campaignsCountSupplier.get();

			_campaignsCountSupplier = null;
		}

		return campaignsCount;
	}

	public void setCampaignsCount(Long campaignsCount) {
		this.campaignsCount = campaignsCount;

		_campaignsCountSupplier = null;
	}

	@JsonIgnore
	public void setCampaignsCount(
		UnsafeSupplier<Long, Exception> campaignsCountUnsafeSupplier) {

		_campaignsCountSupplier = () -> {
			try {
				return campaignsCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Number of campaigns synced from the data source. Only `getWorkspaceGroupDataSource` returns it, for SALESFORCE data sources; it is null otherwise."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long campaignsCount;

	@JsonIgnore
	private Supplier<Long> _campaignsCountSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "IDs of the channels (also known as properties) the data source sends data to. Use them as `channelId` in the channel tools."
	)
	public String[] getChannelIds() {
		if (_channelIdsSupplier != null) {
			channelIds = _channelIdsSupplier.get();

			_channelIdsSupplier = null;
		}

		return channelIds;
	}

	public void setChannelIds(String[] channelIds) {
		this.channelIds = channelIds;

		_channelIdsSupplier = null;
	}

	@JsonIgnore
	public void setChannelIds(
		UnsafeSupplier<String[], Exception> channelIdsUnsafeSupplier) {

		_channelIdsSupplier = () -> {
			try {
				return channelIdsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "IDs of the channels (also known as properties) the data source sends data to. Use them as `channelId` in the channel tools."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String[] channelIds;

	@JsonIgnore
	private Supplier<String[]> _channelIdsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Whether the data source syncs the users of the Liferay DXP instance as contacts. Only set for LIFERAY data sources."
	)
	public Boolean getContactsSynced() {
		if (_contactsSyncedSupplier != null) {
			contactsSynced = _contactsSyncedSupplier.get();

			_contactsSyncedSupplier = null;
		}

		return contactsSynced;
	}

	public void setContactsSynced(Boolean contactsSynced) {
		this.contactsSynced = contactsSynced;

		_contactsSyncedSupplier = null;
	}

	@JsonIgnore
	public void setContactsSynced(
		UnsafeSupplier<Boolean, Exception> contactsSyncedUnsafeSupplier) {

		_contactsSyncedSupplier = () -> {
			try {
				return contactsSyncedUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Whether the data source syncs the users of the Liferay DXP instance as contacts. Only set for LIFERAY data sources."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Boolean contactsSynced;

	@JsonIgnore
	private Supplier<Boolean> _contactsSyncedSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "When the data source was created."
	)
	public Date getCreateDate() {
		if (_createDateSupplier != null) {
			createDate = _createDateSupplier.get();

			_createDateSupplier = null;
		}

		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;

		_createDateSupplier = null;
	}

	@JsonIgnore
	public void setCreateDate(
		UnsafeSupplier<Date, Exception> createDateUnsafeSupplier) {

		_createDateSupplier = () -> {
			try {
				return createDateUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "When the data source was created.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date createDate;

	@JsonIgnore
	private Supplier<Date> _createDateSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Number of events received from the data source. Only `getWorkspaceGroupDataSource` returns it, for HUBSPOT and MARKETO data sources; it is null otherwise."
	)
	public Long getEventsCount() {
		if (_eventsCountSupplier != null) {
			eventsCount = _eventsCountSupplier.get();

			_eventsCountSupplier = null;
		}

		return eventsCount;
	}

	public void setEventsCount(Long eventsCount) {
		this.eventsCount = eventsCount;

		_eventsCountSupplier = null;
	}

	@JsonIgnore
	public void setEventsCount(
		UnsafeSupplier<Long, Exception> eventsCountUnsafeSupplier) {

		_eventsCountSupplier = () -> {
			try {
				return eventsCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Number of events received from the data source. Only `getWorkspaceGroupDataSource` returns it, for HUBSPOT and MARKETO data sources; it is null otherwise."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long eventsCount;

	@JsonIgnore
	private Supplier<Long> _eventsCountSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Data source ID. Use it as `dataSourceId` in `getWorkspaceGroupDataSource`."
	)
	public String getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(String id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		_idSupplier = () -> {
			try {
				return idUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Data source ID. Use it as `dataSourceId` in `getWorkspaceGroupDataSource`."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String id;

	@JsonIgnore
	private Supplier<String> _idSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "When the data source was last modified."
	)
	public Date getModifiedDate() {
		if (_modifiedDateSupplier != null) {
			modifiedDate = _modifiedDateSupplier.get();

			_modifiedDateSupplier = null;
		}

		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;

		_modifiedDateSupplier = null;
	}

	@JsonIgnore
	public void setModifiedDate(
		UnsafeSupplier<Date, Exception> modifiedDateUnsafeSupplier) {

		_modifiedDateSupplier = () -> {
			try {
				return modifiedDateUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "When the data source was last modified.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Date modifiedDate;

	@JsonIgnore
	private Supplier<Date> _modifiedDateSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Name of the data source."
	)
	public String getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setName(String name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		_nameSupplier = () -> {
			try {
				return nameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "Name of the data source.")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String name;

	@JsonIgnore
	private Supplier<String> _nameSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Kind of system the data source connects to: CSV, DEMANDBASE, HUBSPOT, LIFERAY, MARKETO, MARKETO_CAMPAIGN or SALESFORCE."
	)
	public String getProviderType() {
		if (_providerTypeSupplier != null) {
			providerType = _providerTypeSupplier.get();

			_providerTypeSupplier = null;
		}

		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;

		_providerTypeSupplier = null;
	}

	@JsonIgnore
	public void setProviderType(
		UnsafeSupplier<String, Exception> providerTypeUnsafeSupplier) {

		_providerTypeSupplier = () -> {
			try {
				return providerTypeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Kind of system the data source connects to: CSV, DEMANDBASE, HUBSPOT, LIFERAY, MARKETO, MARKETO_CAMPAIGN or SALESFORCE."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String providerType;

	@JsonIgnore
	private Supplier<String> _providerTypeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Number of sites the data source assigns to its channels (also known as properties)."
	)
	public Long getSitesCount() {
		if (_sitesCountSupplier != null) {
			sitesCount = _sitesCountSupplier.get();

			_sitesCountSupplier = null;
		}

		return sitesCount;
	}

	public void setSitesCount(Long sitesCount) {
		this.sitesCount = sitesCount;

		_sitesCountSupplier = null;
	}

	@JsonIgnore
	public void setSitesCount(
		UnsafeSupplier<Long, Exception> sitesCountUnsafeSupplier) {

		_sitesCountSupplier = () -> {
			try {
				return sitesCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Number of sites the data source assigns to its channels (also known as properties)."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long sitesCount;

	@JsonIgnore
	private Supplier<Long> _sitesCountSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Whether the data source syncs the sites of the Liferay DXP instance. Only set for LIFERAY data sources."
	)
	public Boolean getSitesSynced() {
		if (_sitesSyncedSupplier != null) {
			sitesSynced = _sitesSyncedSupplier.get();

			_sitesSyncedSupplier = null;
		}

		return sitesSynced;
	}

	public void setSitesSynced(Boolean sitesSynced) {
		this.sitesSynced = sitesSynced;

		_sitesSyncedSupplier = null;
	}

	@JsonIgnore
	public void setSitesSynced(
		UnsafeSupplier<Boolean, Exception> sitesSyncedUnsafeSupplier) {

		_sitesSyncedSupplier = () -> {
			try {
				return sitesSyncedUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Whether the data source syncs the sites of the Liferay DXP instance. Only set for LIFERAY data sources."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Boolean sitesSynced;

	@JsonIgnore
	private Supplier<Boolean> _sitesSyncedSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Connection state of the data source, such as CREDENTIALS_INVALID, CREDENTIALS_VALID, DISCONNECTED, IN_PROGRESS_DELETING, READY or URL_INVALID."
	)
	public String getState() {
		if (_stateSupplier != null) {
			state = _stateSupplier.get();

			_stateSupplier = null;
		}

		return state;
	}

	public void setState(String state) {
		this.state = state;

		_stateSupplier = null;
	}

	@JsonIgnore
	public void setState(
		UnsafeSupplier<String, Exception> stateUnsafeSupplier) {

		_stateSupplier = () -> {
			try {
				return stateUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Connection state of the data source, such as CREDENTIALS_INVALID, CREDENTIALS_VALID, DISCONNECTED, IN_PROGRESS_DELETING, READY or URL_INVALID."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String state;

	@JsonIgnore
	private Supplier<String> _stateSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Whether the data source is in use: ACTIVE, AUTHENTICATED, CONFIGURING, INACTIVE or OAUTH_EXPIRED. AUTHENTICATED and CONFIGURING mean it is still being set up, and OAUTH_EXPIRED means it needs to be authorized again."
	)
	public String getStatus() {
		if (_statusSupplier != null) {
			status = _statusSupplier.get();

			_statusSupplier = null;
		}

		return status;
	}

	public void setStatus(String status) {
		this.status = status;

		_statusSupplier = null;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<String, Exception> statusUnsafeSupplier) {

		_statusSupplier = () -> {
			try {
				return statusUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Whether the data source is in use: ACTIVE, AUTHENTICATED, CONFIGURING, INACTIVE or OAUTH_EXPIRED. AUTHENTICATED and CONFIGURING mean it is still being set up, and OAUTH_EXPIRED means it needs to be authorized again."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected String status;

	@JsonIgnore
	private Supplier<String> _statusSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Number of users synced from the data source: the users that sent events for HUBSPOT and MARKETO, Liferay DXP users for LIFERAY, leads for MARKETO_CAMPAIGN, and contacts and leads for SALESFORCE. Only `getWorkspaceGroupDataSource` returns it; it is null for DEMANDBASE and CSV data sources."
	)
	public Long getUsersCount() {
		if (_usersCountSupplier != null) {
			usersCount = _usersCountSupplier.get();

			_usersCountSupplier = null;
		}

		return usersCount;
	}

	public void setUsersCount(Long usersCount) {
		this.usersCount = usersCount;

		_usersCountSupplier = null;
	}

	@JsonIgnore
	public void setUsersCount(
		UnsafeSupplier<Long, Exception> usersCountUnsafeSupplier) {

		_usersCountSupplier = () -> {
			try {
				return usersCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "Number of users synced from the data source: the users that sent events for HUBSPOT and MARKETO, Liferay DXP users for LIFERAY, leads for MARKETO_CAMPAIGN, and contacts and leads for SALESFORCE. Only `getWorkspaceGroupDataSource` returns it; it is null for DEMANDBASE and CSV data sources."
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected Long usersCount;

	@JsonIgnore
	private Supplier<Long> _usersCountSupplier;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		DateFormat liferayToJSONDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		Long accountsCount = getAccountsCount();

		if (accountsCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"accountsCount\": ");

			sb.append(accountsCount);
		}

		Long campaignsCount = getCampaignsCount();

		if (campaignsCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"campaignsCount\": ");

			sb.append(campaignsCount);
		}

		String[] channelIds = getChannelIds();

		if (channelIds != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"channelIds\": ");

			sb.append("[");

			for (int i = 0; i < channelIds.length; i++) {
				sb.append("\"");

				sb.append(_escape(channelIds[i]));

				sb.append("\"");

				if ((i + 1) < channelIds.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Boolean contactsSynced = getContactsSynced();

		if (contactsSynced != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contactsSynced\": ");

			sb.append(contactsSynced);
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"createDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(createDate));

			sb.append("\"");
		}

		Long eventsCount = getEventsCount();

		if (eventsCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"eventsCount\": ");

			sb.append(eventsCount);
		}

		String id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append("\"");

			sb.append(_escape(id));

			sb.append("\"");
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"modifiedDate\": ");

			sb.append("\"");

			sb.append(liferayToJSONDateFormat.format(modifiedDate));

			sb.append("\"");
		}

		String name = getName();

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		String providerType = getProviderType();

		if (providerType != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"providerType\": ");

			sb.append("\"");

			sb.append(_escape(providerType));

			sb.append("\"");
		}

		Long sitesCount = getSitesCount();

		if (sitesCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sitesCount\": ");

			sb.append(sitesCount);
		}

		Boolean sitesSynced = getSitesSynced();

		if (sitesSynced != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"sitesSynced\": ");

			sb.append(sitesSynced);
		}

		String state = getState();

		if (state != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"state\": ");

			sb.append("\"");

			sb.append(_escape(state));

			sb.append("\"");
		}

		String status = getStatus();

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(status));

			sb.append("\"");
		}

		Long usersCount = getUsersCount();

		if (usersCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"usersCount\": ");

			sb.append(usersCount);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.osb.faro.rest.dto.v1_0.DataSource",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value instanceof Collection) {
			return String.valueOf(
				JSONFactoryUtil.createJSONArray((Collection<?>)value));
		}
		else if (value instanceof Map) {
			return String.valueOf(
				JSONFactoryUtil.createJSONObject((Map<?, ?>)value));
		}
		else if (value instanceof Object[]) {
			return String.valueOf(
				JSONFactoryUtil.createJSONArray(
					Arrays.asList((Object[])value)));
		}
		else if (value instanceof String) {
			return StringBundler.concat("\"", _escape(value), "\"");
		}

		return String.valueOf(value);
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:182803213