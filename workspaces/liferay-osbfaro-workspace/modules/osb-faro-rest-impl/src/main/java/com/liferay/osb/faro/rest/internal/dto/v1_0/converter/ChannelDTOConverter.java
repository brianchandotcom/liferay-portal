/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.osb.faro.rest.dto.v1_0.Channel;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leslie Wong
 */
@Component(
	property = "dto.class.name=com.liferay.osb.faro.engine.client.model.Channel",
	service = DTOConverter.class
)
public class ChannelDTOConverter implements DTOConverter<FaroChannel, Channel> {

	@Override
	public String getContentType() {
		return Channel.class.getSimpleName();
	}

	@Override
	public Channel toDTO(
		DTOConverterContext dtoConverterContext, FaroChannel channel) {

		if (channel == null) {
			return null;
		}

		return new Channel() {
			{
				setId(channel::getChannelId);
				setName(channel::getName);
			}
		};
	}

}