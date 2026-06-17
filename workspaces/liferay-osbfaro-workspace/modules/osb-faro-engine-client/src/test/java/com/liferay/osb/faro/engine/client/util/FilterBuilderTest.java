/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Inácio Nery
 */
public class FilterBuilderTest {

	@Test
	public void testBuild() {
		Assert.assertNull(_filterBuilder.build());
	}

	private final FilterBuilder _filterBuilder = new FilterBuilder();

}