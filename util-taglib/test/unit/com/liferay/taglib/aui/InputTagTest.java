/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupWrapper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.Function;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Shuyang Zhou
 */
public class InputTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetAttributes() {
		InputTag inputTag = new InputTag();

		inputTag.setName("typeSettings");
		inputTag.setPageContext(new MockPageContext());
		inputTag.setType("textarea");

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		inputTag.setAttributes(mockHttpServletRequest);

		Group group = new GroupWrapper(null) {

			@Override
			public long getGroupId() {
				return 1;
			}

			@Override
			public String getGroupKey() {
				return null;
			}

			@Override
			public String getName() {
				return "overloaded";
			}

			@Override
			public String getTypeSettings() {
				return "by name";
			}

		};

		_assertValueFunction("by name", group, mockHttpServletRequest);

		inputTag.setName("groupKey");

		inputTag.setAttributes(mockHttpServletRequest);

		_assertValueFunction(null, group, mockHttpServletRequest);

		inputTag.setValueFunction(Group::getGroupId);

		inputTag.setAttributes(mockHttpServletRequest);

		_assertValueFunction("1", group, mockHttpServletRequest);

		inputTag.setValueFunction(Group::getGroupKey);

		inputTag.setAttributes(mockHttpServletRequest);

		_assertValueFunction(null, group, mockHttpServletRequest);

		inputTag.setValueFunction((Group curGroup) -> curGroup.getName());

		inputTag.setAttributes(mockHttpServletRequest);

		_assertValueFunction("overloaded", group, mockHttpServletRequest);
	}

	private void _assertValueFunction(
		String expectedValue, Group group,
		MockHttpServletRequest mockHttpServletRequest) {

		Function<Object, String> valueFunction =
			(Function<Object, String>)mockHttpServletRequest.getAttribute(
				"valueFunction");

		Assert.assertEquals(expectedValue, valueFunction.apply(group));
	}

}