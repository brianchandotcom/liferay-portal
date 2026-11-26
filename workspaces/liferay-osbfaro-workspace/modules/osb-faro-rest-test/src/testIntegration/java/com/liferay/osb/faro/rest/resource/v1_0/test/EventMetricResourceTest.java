/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.engine.client.model.GraphQLRequest;
import com.liferay.osb.faro.rest.resource.v1_0.EventMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.test.util.FaroGraphQLClientTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leslie Wong
 */
@RunWith(Arquillian.class)
public class EventMetricResourceTest extends BaseEventMetricResourceTestCase {

	@Override
	@Test
	public void testGetWorkspaceGroupChannelAccountEventMetric()
		throws Exception {

		EventMetricResource eventMetricResource =
			ReflectionTestUtil.getFieldValue(this, "_eventMetricResource");

		String segmentId = String.valueOf(RandomTestUtil.randomLong());

		GraphQLRequest graphQLRequest =
			FaroGraphQLClientTestUtil.getGraphQLRequest(
				Collections.emptyMap(), eventMetricResource,
				() ->
					eventMetricResource.
						getWorkspaceGroupChannelAccountEventMetric(
							testGroup.getGroupId(),
							String.valueOf(RandomTestUtil.randomLong()),
							RandomTestUtil.randomString(), null, null, null,
							null, null, segmentId));

		Map<String, Object> variables = graphQLRequest.getVariables();

		Assert.assertEquals(segmentId, variables.get("segmentId"));
	}

	@Override
	@Test
	public void testGetWorkspaceGroupChannelIndividualEventMetric()
		throws Exception {

		EventMetricResource eventMetricResource =
			ReflectionTestUtil.getFieldValue(this, "_eventMetricResource");

		String segmentId = String.valueOf(RandomTestUtil.randomLong());

		GraphQLRequest graphQLRequest =
			FaroGraphQLClientTestUtil.getGraphQLRequest(
				Collections.emptyMap(), eventMetricResource,
				() ->
					eventMetricResource.
						getWorkspaceGroupChannelIndividualEventMetric(
							testGroup.getGroupId(),
							String.valueOf(RandomTestUtil.randomLong()),
							RandomTestUtil.randomString(), null, null, null,
							null, null, segmentId));

		Map<String, Object> variables = graphQLRequest.getVariables();

		Assert.assertEquals(segmentId, variables.get("segmentId"));
	}

}