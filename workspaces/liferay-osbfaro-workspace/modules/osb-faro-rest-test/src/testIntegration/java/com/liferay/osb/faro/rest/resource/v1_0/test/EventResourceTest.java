/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.rest.dto.v1_0.Event;
import com.liferay.osb.faro.rest.resource.v1_0.EventResource;
import com.liferay.osb.faro.rest.resource.v1_0.test.util.FaroGraphQLClientTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leslie Wong
 */
@RunWith(Arquillian.class)
public class EventResourceTest extends BaseEventResourceTestCase {

	@Override
	@Test
	public void testGetWorkspaceGroupChannelEventsPage() throws Exception {
		Event event = _getEvent(null);

		Assert.assertNull(event.getIndividualId());

		String individualId = RandomTestUtil.randomString();

		event = _getEvent(individualId);

		Assert.assertEquals(individualId, event.getIndividualId());
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelEventsPageWithPagination()
		throws Exception {

		super.testGetWorkspaceGroupChannelEventsPageWithPagination();
	}

	private Event _getEvent(String individualId) throws Exception {
		AtomicReference<Page<Event>> atomicReference = new AtomicReference<>();

		EventResource eventResource = ReflectionTestUtil.getFieldValue(
			this, "_eventResource");

		FaroGraphQLClientTestUtil.getGraphQLRequest(
			HashMapBuilder.<String, Object>put(
				"eventBag",
				HashMapBuilder.<String, Object>put(
					"events",
					Collections.singletonList(
						Collections.singletonMap(
							"name", RandomTestUtil.randomString()))
				).put(
					"total", 1
				).build()
			).build(),
			eventResource,
			() -> atomicReference.set(
				eventResource.getWorkspaceGroupChannelEventsPage(
					testGroup.getGroupId(),
					String.valueOf(RandomTestUtil.randomLong()), null, null,
					individualId, null, null, null, null, null,
					Pagination.of(1, 10))));

		Page<Event> page = atomicReference.get();

		List<Event> events = new ArrayList<>(page.getItems());

		Assert.assertEquals(events.toString(), 1, events.size());

		return events.get(0);
	}

}