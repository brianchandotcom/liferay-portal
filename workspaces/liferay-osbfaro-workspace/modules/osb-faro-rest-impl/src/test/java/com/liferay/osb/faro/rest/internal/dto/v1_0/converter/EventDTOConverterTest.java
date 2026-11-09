/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.Event;
import com.liferay.osb.faro.rest.internal.graphql.dto.GetWorkspaceGroupChannelEventsPageResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Drew Hoffer
 */
public class EventDTOConverterTest {

	@Test
	public void testToDTO() {
		GetWorkspaceGroupChannelEventsPageResponse.Event responseEvent =
			new GetWorkspaceGroupChannelEventsPageResponse.Event();

		responseEvent.setEventDate(RandomTestUtil.nextDate());
		responseEvent.setIndividualName(RandomTestUtil.randomString());
		responseEvent.setName(RandomTestUtil.randomString());

		String individualId = RandomTestUtil.randomString();

		Event event = _eventDTOConverter.toDTO(
			new FaroDTOConverterContext(false, individualId, null),
			responseEvent);

		Assert.assertEquals(responseEvent.getEventDate(), event.getEventDate());
		Assert.assertEquals(individualId, event.getIndividualId());
		Assert.assertEquals(
			responseEvent.getIndividualName(), event.getIndividualName());
		Assert.assertEquals(responseEvent.getName(), event.getName());

		event = _eventDTOConverter.toDTO(
			new FaroDTOConverterContext(false, "", null), responseEvent);

		Assert.assertNull(event.getIndividualId());

		event = _eventDTOConverter.toDTO(
			new FaroDTOConverterContext(false, null, null), responseEvent);

		Assert.assertNull(event.getIndividualId());

		Assert.assertNull(
			_eventDTOConverter.toDTO(
				new FaroDTOConverterContext(false, null, null), null));
	}

	private final EventDTOConverter _eventDTOConverter =
		new EventDTOConverter();

}