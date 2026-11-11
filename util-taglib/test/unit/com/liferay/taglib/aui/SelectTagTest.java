/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.PhoneWrapper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.ToLongFunction;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Shuyang Zhou
 */
public class SelectTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetAttributes() {
		SelectTag selectTag = new SelectTag();

		selectTag.setListType(ListTypeConstants.ORGANIZATION_SERVICE);
		selectTag.setName("orgLaborListTypeId0");
		selectTag.setPageContext(new MockPageContext());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		selectTag.setAttributes(mockHttpServletRequest);

		Assert.assertEquals(
			"listTypeId",
			mockHttpServletRequest.getAttribute("listTypeFieldName"));

		Phone phone = new PhoneWrapper(null) {

			@Override
			public long getListTypeId() {
				return 1;
			}

			@Override
			public long getPhoneId() {
				return 2;
			}

		};

		_assertValueFunction(1, mockHttpServletRequest, phone);

		selectTag.setValueFunction(Phone::getPhoneId);

		selectTag.setAttributes(mockHttpServletRequest);

		_assertValueFunction(2, mockHttpServletRequest, phone);

		selectTag.setBean(phone);
		selectTag.setListType(null);

		selectTag.setAttributes(mockHttpServletRequest);

		Assert.assertEquals("2", mockHttpServletRequest.getAttribute("value"));
	}

	private void _assertValueFunction(
		long expectedValue, MockHttpServletRequest mockHttpServletRequest,
		Phone phone) {

		ToLongFunction<Object> valueFunction =
			(ToLongFunction<Object>)mockHttpServletRequest.getAttribute(
				"valueFunction");

		Assert.assertEquals(expectedValue, valueFunction.applyAsLong(phone));
	}

}