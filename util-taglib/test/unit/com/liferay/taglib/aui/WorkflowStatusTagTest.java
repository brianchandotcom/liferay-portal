/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.model.LayoutBranchWrapper;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutRevisionWrapper;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Shuyang Zhou
 */
public class WorkflowStatusTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetAttributes() {
		WorkflowStatusTag workflowStatusTag = new WorkflowStatusTag();

		workflowStatusTag.setBean(
			new LayoutRevisionWrapper(null) {

				@Override
				public long getCompanyId() {
					return 1;
				}

				@Override
				public long getGroupId() {
					return 2;
				}

				@Override
				public Serializable getPrimaryKeyObj() {
					return 3L;
				}

			});
		workflowStatusTag.setModel(LayoutRevision.class);
		workflowStatusTag.setPageContext(new MockPageContext());
		workflowStatusTag.setStatus(WorkflowConstants.STATUS_PENDING);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		workflowStatusTag.setAttributes(mockHttpServletRequest);

		Assert.assertEquals(
			"3", mockHttpServletRequest.getAttribute("classPK"));
		Assert.assertEquals(
			"1", mockHttpServletRequest.getAttribute("companyId"));
		Assert.assertEquals(
			"2", mockHttpServletRequest.getAttribute("groupId"));

		workflowStatusTag.setBean(
			new LayoutBranchWrapper(null) {

				@Override
				public long getCompanyId() {
					return 4;
				}

				@Override
				public Map<String, Object> getModelAttributes() {
					return Collections.singletonMap("groupId", 5L);
				}

				@Override
				public Serializable getPrimaryKeyObj() {
					return 6L;
				}

			});

		workflowStatusTag.setAttributes(mockHttpServletRequest);

		Assert.assertEquals(
			"6", mockHttpServletRequest.getAttribute("classPK"));
		Assert.assertEquals(
			"4", mockHttpServletRequest.getAttribute("companyId"));
		Assert.assertEquals(
			"5", mockHttpServletRequest.getAttribute("groupId"));
	}

}