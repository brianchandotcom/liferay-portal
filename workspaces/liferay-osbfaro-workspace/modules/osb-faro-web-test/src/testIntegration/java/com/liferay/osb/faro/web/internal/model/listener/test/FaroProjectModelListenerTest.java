/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.provisioning.client.constants.ProductConstants;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@FeatureFlags(featureFlags = @FeatureFlag("LPD-114293"))
@RunWith(Arquillian.class)
public class FaroProjectModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOnAfterCreate() throws Exception {
		FaroProject faroProject = _faroProjectLocalService.addFaroProject(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			null, null, null, Collections.emptyList(), null, null, null, null,
			null, FaroProjectConstants.STATE_READY, StringPool.BLANK,
			StringPool.UTC, null);

		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-editor-master");
		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-master");
		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-settings-master");
	}

	@Test
	public void testOnAfterCreateWithLDPPlan() throws Exception {
		FaroProject faroProject = _faroProjectLocalService.addFaroProject(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			null, null, null, Collections.emptyList(), null, null, null, null,
			null, FaroProjectConstants.STATE_READY,
			JSONUtil.put(
				"name", ProductConstants.PRODUCT_ENTRY_NAME_DATA_PLATFORM
			).toString(),
			StringPool.UTC, null);

		_assertFaviconFileEntryERC(
			"LDP_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-editor-master");
		_assertFaviconFileEntryERC(
			"LDP_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-master");
		_assertFaviconFileEntryERC(
			"LDP_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-settings-master");
	}

	@FeatureFlags(
		featureFlags = @FeatureFlag(enable = false, value = "LPD-114293")
	)
	@Test
	public void testOnAfterCreateWithoutFeatureFlag() throws Exception {
		FaroProject faroProject = _faroProjectLocalService.addFaroProject(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			null, null, null, Collections.emptyList(), null, null, null, null,
			null, FaroProjectConstants.STATE_READY, StringPool.BLANK,
			StringPool.UTC, null);

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				faroProject.getGroupId(), "liferay-data-platform-master"));
	}

	@Test
	public void testOnAfterUpdate() throws Exception {
		FaroProject faroProject = _faroProjectLocalService.addFaroProject(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), null,
			null, null, null, Collections.emptyList(), null, null, null, null,
			null, FaroProjectConstants.STATE_READY, StringPool.BLANK,
			StringPool.UTC, null);

		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-editor-master");
		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-master");
		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-settings-master");

		faroProject.setSubscription(
			JSONUtil.put(
				"name", ProductConstants.PRODUCT_ENTRY_NAME_DATA_PLATFORM
			).toString());

		faroProject = _faroProjectLocalService.updateFaroProject(faroProject);

		_assertFaviconFileEntryERC(
			"LDP_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-editor-master");
		_assertFaviconFileEntryERC(
			"LDP_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-master");
		_assertFaviconFileEntryERC(
			"LDP_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-settings-master");

		faroProject.setSubscription(StringPool.BLANK);

		faroProject = _faroProjectLocalService.updateFaroProject(faroProject);

		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-editor-master");
		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-master");
		_assertFaviconFileEntryERC(
			"AC_FAVICON", faroProject.getGroupId(),
			"liferay-data-platform-settings-master");
	}

	private void _assertFaviconFileEntryERC(
			String faviconFileEntryERC, long groupId,
			String layoutPageTemplateEntryKey)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				groupId, layoutPageTemplateEntryKey);

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Assert.assertEquals(
			faviconFileEntryERC, layout.getFaviconFileEntryERC());
	}

	@Inject
	private FaroProjectLocalService _faroProjectLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}