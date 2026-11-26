/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osb.faro.engine.client.model.GraphQLRequest;
import com.liferay.osb.faro.rest.resource.v1_0.SearchTermResource;
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
public class SearchTermResourceTest extends BaseSearchTermResourceTestCase {

	@Override
	@Test
	public void testGetWorkspaceGroupChannelSearchTermsPage() throws Exception {
		SearchTermResource searchTermResource =
			ReflectionTestUtil.getFieldValue(this, "_searchTermResource");

		String accountId = RandomTestUtil.randomString();
		String search = RandomTestUtil.randomString();
		String segmentId = String.valueOf(RandomTestUtil.randomLong());

		GraphQLRequest graphQLRequest =
			FaroGraphQLClientTestUtil.getGraphQLRequest(
				Collections.emptyMap(), searchTermResource,
				() ->
					searchTermResource.getWorkspaceGroupChannelSearchTermsPage(
						testGroup.getGroupId(),
						String.valueOf(RandomTestUtil.randomLong()), accountId,
						null, null, null, search, segmentId,
						Pagination.of(1, 10)));

		Map<String, Object> variables = graphQLRequest.getVariables();

		Assert.assertEquals(accountId, variables.get("accountId"));
		Assert.assertEquals(search, variables.get("keywords"));
		Assert.assertEquals(segmentId, variables.get("segmentId"));
	}

	@Ignore
	@Override
	@Test
	public void testGetWorkspaceGroupChannelSearchTermsPageWithPagination()
		throws Exception {

		super.testGetWorkspaceGroupChannelSearchTermsPageWithPagination();
	}

}