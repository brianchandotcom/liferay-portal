/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.engine.client.model.GraphQLRequest;
import com.liferay.osb.faro.rest.resource.v1_0.AssetSummaryMetricResource;
import com.liferay.osb.faro.rest.resource.v1_0.test.util.FaroGraphQLClientTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Leslie Wong
 */
@RunWith(Arquillian.class)
public class AssetSummaryMetricResourceTest
	extends BaseAssetSummaryMetricResourceTestCase {

	@Override
	@Test
	public void testGetWorkspaceGroupChannelAssetSummariesPage()
		throws Exception {

		AssetSummaryMetricResource assetSummaryMetricResource =
			ReflectionTestUtil.getFieldValue(
				this, "_assetSummaryMetricResource");

		String objectType = "FILE";

		GraphQLRequest graphQLRequest =
			FaroGraphQLClientTestUtil.getGraphQLRequest(
				Collections.emptyMap(), assetSummaryMetricResource,
				() ->
					assetSummaryMetricResource.
						getWorkspaceGroupChannelAssetSummariesPage(
							testGroup.getGroupId(),
							String.valueOf(RandomTestUtil.randomLong()), null,
							null, objectType, null, null, null, null, null,
							Pagination.of(1, 10), null));

		Map<String, Object> variables = graphQLRequest.getVariables();

		Assert.assertEquals(objectType, variables.get("objectType"));
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAssetSummariesPageWithPagination()
		throws Exception {

		super.testGetWorkspaceGroupChannelAssetSummariesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAssetSummariesPageWithSortDateTime()
		throws Exception {

		super.testGetWorkspaceGroupChannelAssetSummariesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAssetSummariesPageWithSortDouble()
		throws Exception {

		super.testGetWorkspaceGroupChannelAssetSummariesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAssetSummariesPageWithSortInteger()
		throws Exception {

		super.testGetWorkspaceGroupChannelAssetSummariesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelAssetSummariesPageWithSortString()
		throws Exception {

		super.testGetWorkspaceGroupChannelAssetSummariesPageWithSortString();
	}

}