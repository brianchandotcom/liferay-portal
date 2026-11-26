/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.engine.client.model.ChannelsConfiguration;
import com.liferay.osb.faro.engine.client.model.provider.CSVProvider;
import com.liferay.osb.faro.engine.client.model.provider.LiferayProvider;
import com.liferay.osb.faro.rest.dto.v1_0.DataSource;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nilton Vieira
 */
public class DataSourceDTOConverterTest {

	@Test
	public void testToDTO() {
		Assert.assertNull(_dataSourceDTOConverter.toDTO(null, null));

		com.liferay.osb.faro.engine.client.model.DataSource
			engineClientDataSource =
				new com.liferay.osb.faro.engine.client.model.DataSource();

		engineClientDataSource.setCreateDate(RandomTestUtil.nextDate());
		engineClientDataSource.setId(RandomTestUtil.randomString());
		engineClientDataSource.setModifiedDate(RandomTestUtil.nextDate());
		engineClientDataSource.setName(RandomTestUtil.randomString());
		engineClientDataSource.setState(RandomTestUtil.randomString());
		engineClientDataSource.setStatus(RandomTestUtil.randomString());

		DataSource dataSource = _dataSourceDTOConverter.toDTO(
			null, engineClientDataSource);

		Assert.assertArrayEquals(new String[0], dataSource.getChannelIds());
		Assert.assertNull(dataSource.getContactsSynced());
		Assert.assertEquals(
			engineClientDataSource.getCreateDate(), dataSource.getCreateDate());
		Assert.assertEquals(engineClientDataSource.getId(), dataSource.getId());
		Assert.assertEquals(
			engineClientDataSource.getModifiedDate(),
			dataSource.getModifiedDate());
		Assert.assertEquals(
			engineClientDataSource.getName(), dataSource.getName());
		Assert.assertNull(dataSource.getProviderType());
		Assert.assertEquals(Long.valueOf(0), dataSource.getSitesCount());
		Assert.assertNull(dataSource.getSitesSynced());
		Assert.assertEquals(
			engineClientDataSource.getState(), dataSource.getState());
		Assert.assertEquals(
			engineClientDataSource.getStatus(), dataSource.getStatus());

		engineClientDataSource.setProvider(new CSVProvider());

		dataSource = _dataSourceDTOConverter.toDTO(
			null, engineClientDataSource);

		Assert.assertArrayEquals(new String[0], dataSource.getChannelIds());
		Assert.assertEquals(CSVProvider.TYPE, dataSource.getProviderType());

		ChannelsConfiguration channelsConfiguration =
			new ChannelsConfiguration();

		ChannelsConfiguration.Channel channel1 =
			new ChannelsConfiguration.Channel();

		channel1.setChannelId(RandomTestUtil.randomString());
		channel1.setGroupIds(
			SetUtil.fromArray(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()));

		ChannelsConfiguration.Channel channel2 =
			new ChannelsConfiguration.Channel();

		channel2.setChannelId(RandomTestUtil.randomString());

		channelsConfiguration.setChannels(Arrays.asList(channel1, channel2));

		LiferayProvider liferayProvider = new LiferayProvider();

		liferayProvider.setChannelsConfiguration(channelsConfiguration);

		engineClientDataSource.setProvider(liferayProvider);

		com.liferay.osb.faro.engine.client.model.DataSource.Details
			contactsSyncDetails =
				new com.liferay.osb.faro.engine.client.model.DataSource.
					Details();

		contactsSyncDetails.setSelected(true);

		engineClientDataSource.setContactsSyncDetails(contactsSyncDetails);

		com.liferay.osb.faro.engine.client.model.DataSource.Details
			sitesSyncDetails =
				new com.liferay.osb.faro.engine.client.model.DataSource.
					Details();

		sitesSyncDetails.setSelected(false);

		engineClientDataSource.setSitesSyncDetails(sitesSyncDetails);

		dataSource = _dataSourceDTOConverter.toDTO(
			null, engineClientDataSource);

		Assert.assertArrayEquals(
			new String[] {channel1.getChannelId(), channel2.getChannelId()},
			dataSource.getChannelIds());
		Assert.assertTrue(dataSource.getContactsSynced());
		Assert.assertEquals(LiferayProvider.TYPE, dataSource.getProviderType());
		Assert.assertEquals(Long.valueOf(2), dataSource.getSitesCount());
		Assert.assertFalse(dataSource.getSitesSynced());
	}

	private final DataSourceDTOConverter _dataSourceDTOConverter =
		new DataSourceDTOConverter();

}