/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutBranchWrapper;
import com.liferay.portal.kernel.model.LayoutWrapper;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletWrapper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class AUIUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetGroupId() {
		Assert.assertEquals(1, AUIUtil.getGroupId(null, 1));
		Assert.assertEquals(1, AUIUtil.getGroupId(new Object(), 1));
		Assert.assertEquals(
			2,
			AUIUtil.getGroupId(
				new LayoutWrapper(null) {

					@Override
					public long getGroupId() {
						return 2;
					}

				},
				1));
		Assert.assertEquals(
			3,
			AUIUtil.getGroupId(
				new LayoutBranchWrapper(null) {

					@Override
					public Map<String, Function<LayoutBranch, Object>>
						getAttributeGetterFunctions() {

						return Collections.singletonMap(
							"groupId", layoutBranch -> 3L);
					}

				},
				1));
		Assert.assertEquals(
			1,
			AUIUtil.getGroupId(
				new PortletWrapper(null) {

					@Override
					public Map<String, Function<Portlet, Object>>
						getAttributeGetterFunctions() {

						return Collections.emptyMap();
					}

				},
				1));
	}

}