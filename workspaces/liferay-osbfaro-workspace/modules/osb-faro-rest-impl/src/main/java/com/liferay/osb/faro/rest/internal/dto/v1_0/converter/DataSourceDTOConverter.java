/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.engine.client.model.ChannelsConfiguration;
import com.liferay.osb.faro.engine.client.model.Provider;
import com.liferay.osb.faro.rest.dto.v1_0.DataSource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Nilton Vieira
 */
@Component(
	property = "dto.class.name=com.liferay.osb.faro.engine.client.model.DataSource",
	service = DTOConverter.class
)
public class DataSourceDTOConverter
	implements DTOConverter
		<com.liferay.osb.faro.engine.client.model.DataSource, DataSource> {

	@Override
	public String getContentType() {
		return DataSource.class.getSimpleName();
	}

	@Override
	public DataSource toDTO(
		DTOConverterContext dtoConverterContext,
		com.liferay.osb.faro.engine.client.model.DataSource dataSource) {

		if (dataSource == null) {
			return null;
		}

		List<ChannelsConfiguration.Channel> channels = _getChannels(
			dataSource.getProvider());

		return new DataSource() {
			{
				setChannelIds(
					() -> TransformUtil.transformToArray(
						channels, ChannelsConfiguration.Channel::getChannelId,
						String.class));
				setContactsSynced(
					() -> _isSelected(dataSource.getContactsSyncDetails()));
				setCreateDate(dataSource::getCreateDate);
				setId(dataSource::getId);
				setModifiedDate(dataSource::getModifiedDate);
				setName(dataSource::getName);
				setProviderType(
					() -> {
						Provider provider = dataSource.getProvider();

						if (provider == null) {
							return null;
						}

						return provider.getType();
					});
				setSitesCount(
					() -> {
						long sitesCount = 0;

						for (ChannelsConfiguration.Channel channel : channels) {
							Set<Long> groupIds = channel.getGroupIds();

							if (groupIds == null) {
								continue;
							}

							sitesCount += groupIds.size();
						}

						return sitesCount;
					});
				setSitesSynced(
					() -> _isSelected(dataSource.getSitesSyncDetails()));
				setState(dataSource::getState);
				setStatus(dataSource::getStatus);
			}
		};
	}

	private List<ChannelsConfiguration.Channel> _getChannels(
		Provider provider) {

		if (provider == null) {
			return Collections.emptyList();
		}

		ChannelsConfiguration channelsConfiguration =
			provider.getChannelsConfiguration();

		if ((channelsConfiguration == null) ||
			(channelsConfiguration.getChannels() == null)) {

			return Collections.emptyList();
		}

		return channelsConfiguration.getChannels();
	}

	private Boolean _isSelected(
		com.liferay.osb.faro.engine.client.model.DataSource.Details details) {

		if (details == null) {
			return null;
		}

		return details.isSelected();
	}

}