/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.model.provider.DemandbaseProvider;
import com.liferay.osb.faro.engine.client.model.provider.HubSpotProvider;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.osb.faro.engine.client.model.provider.MarketoCampaignProvider;
import com.liferay.osb.faro.engine.client.model.provider.MarketoProvider;
import com.liferay.osb.faro.engine.client.model.provider.SalesforceProvider;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.rest.dto.v1_0.DataSource;
import com.liferay.osb.faro.rest.internal.dto.v1_0.converter.FaroDTOConverterContext;
import com.liferay.osb.faro.rest.internal.dto.v1_0.util.FaroPaginationUtil;
import com.liferay.osb.faro.rest.resource.v1_0.DataSourceResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Leslie Wong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-source.properties",
	scope = ServiceScope.PROTOTYPE, service = DataSourceResource.class
)
public class DataSourceResourceImpl extends BaseDataSourceResourceImpl {

	@Override
	public DataSource getWorkspaceGroupDataSource(
			Long groupId, String dataSourceId)
		throws Exception {

		FaroProject faroProject =
			_faroProjectLocalService.getFaroProjectByGroupId(groupId);

		DataSource dataSource = _toDataSource(
			_contactsEngineClient.getDataSource(faroProject, dataSourceId));

		String providerType = dataSource.getProviderType();

		if (ArrayUtil.contains(_ACCOUNTS_COUNT_PROVIDER_TYPES, providerType)) {
			dataSource.setAccountsCount(
				_contactsEngineClient.getDataSourceMetricsAccountsCount(
					faroProject, dataSourceId));
		}

		if (Objects.equals(providerType, SalesforceProvider.TYPE)) {
			dataSource.setCampaignsCount(
				_contactsEngineClient.getDataSourceMetricsCampaignsCount(
					faroProject, dataSourceId));
		}

		if (ArrayUtil.contains(_EVENTS_COUNT_PROVIDER_TYPES, providerType)) {
			dataSource.setEventsCount(
				_contactsEngineClient.getDataSourceMetricsEventsCount(
					faroProject, dataSourceId));
		}

		if (Objects.equals(providerType, LiferayProvider.TYPE)) {
			dataSource.setUsersCount(
				_contactsEngineClient.getDXPUsersCount(
					faroProject, dataSourceId));
		}
		else if (ArrayUtil.contains(
					_USERS_COUNT_PROVIDER_TYPES, providerType)) {

			dataSource.setUsersCount(
				_contactsEngineClient.getDataSourceMetricsUsersCount(
					faroProject, dataSourceId));
		}

		return dataSource;
	}

	@Override
	public Page<DataSource> getWorkspaceGroupDataSourcesPage(
			Long groupId, String providerType, String search, String state,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		List<String> states = null;

		if (state != null) {
			states = Collections.singletonList(state);
		}

		Results<com.liferay.osb.faro.engine.client.model.DataSource> results =
			_contactsEngineClient.getDataSources(
				_faroProjectLocalService.getFaroProjectByGroupId(groupId), null,
				search, null, providerType, states,
				FaroPaginationUtil.getCur(pagination),
				FaroPaginationUtil.getDelta(pagination),
				FaroPaginationUtil.toOrderByFields(sorts));

		return Page.of(
			transform(results.getItems(), this::_toDataSource), pagination,
			results.getTotal());
	}

	private DataSource _toDataSource(
			com.liferay.osb.faro.engine.client.model.DataSource dataSource)
		throws Exception {

		return _dataSourceDTOConverter.toDTO(
			new FaroDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				dataSource.getId(), contextAcceptLanguage.getPreferredLocale()),
			dataSource);
	}

	private static final String[] _ACCOUNTS_COUNT_PROVIDER_TYPES = {
		DemandbaseProvider.TYPE, MarketoCampaignProvider.TYPE,
		SalesforceProvider.TYPE
	};

	private static final String[] _EVENTS_COUNT_PROVIDER_TYPES = {
		HubSpotProvider.TYPE, MarketoProvider.TYPE
	};

	private static final String[] _USERS_COUNT_PROVIDER_TYPES = {
		HubSpotProvider.TYPE, MarketoCampaignProvider.TYPE,
		MarketoProvider.TYPE, SalesforceProvider.TYPE
	};

	@Reference
	private ContactsEngineClient _contactsEngineClient;

	@Reference(
		target = "(component.name=com.liferay.osb.faro.rest.internal.dto.v1_0.converter.DataSourceDTOConverter)"
	)
	private DTOConverter
		<com.liferay.osb.faro.engine.client.model.DataSource, DataSource>
			_dataSourceDTOConverter;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}