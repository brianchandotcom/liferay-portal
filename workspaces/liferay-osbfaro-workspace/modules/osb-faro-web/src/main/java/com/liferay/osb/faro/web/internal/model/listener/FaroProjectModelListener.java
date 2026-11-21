/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.listener;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.util.FaroNavigationUtil;
import com.liferay.osb.faro.web.internal.util.SiteInitializerUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.site.initializer.SiteInitializer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ModelListener.class)
public class FaroProjectModelListener extends BaseModelListener<FaroProject> {

	@Override
	public void onAfterCreate(FaroProject faroProject) {
		if (!FeatureFlagManagerUtil.isEnabled(
				faroProject.getCompanyId(), "LPD-114293")) {

			return;
		}

		try {
			SiteInitializerUtil.initialize(
				_groupLocalService.getGroup(faroProject.getGroupId()),
				_siteInitializer);

			if (!FaroNavigationUtil.isLDPEnabled(faroProject)) {
				_updateLayouts(
					faroProject.getGroupId(), true, _LDP_FRIENDLY_URLS);
				_updateMasterLayouts("AC_FAVICON", faroProject.getGroupId());
			}

			if (faroProject.isRecommendationsEnabled()) {
				_updateLayouts(
					faroProject.getGroupId(), false, "/recommendations");
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	public void onAfterUpdate(
		FaroProject originalFaroProject, FaroProject faroProject) {

		if (!FeatureFlagManagerUtil.isEnabled(
				faroProject.getCompanyId(), "LPD-114293")) {

			return;
		}

		try {
			boolean ldpEnabled = FaroNavigationUtil.isLDPEnabled(faroProject);

			if (FaroNavigationUtil.isLDPEnabled(originalFaroProject) !=
					ldpEnabled) {

				if (ldpEnabled) {
					_updateLayouts(
						faroProject.getGroupId(), false, _LDP_FRIENDLY_URLS);
					_updateMasterLayouts(
						"LDP_FAVICON", faroProject.getGroupId());
				}
				else {
					_updateLayouts(
						faroProject.getGroupId(), true, _LDP_FRIENDLY_URLS);
					_updateMasterLayouts(
						"AC_FAVICON", faroProject.getGroupId());
				}
			}

			if (originalFaroProject.isRecommendationsEnabled() !=
					faroProject.isRecommendationsEnabled()) {

				if (faroProject.isRecommendationsEnabled()) {
					_updateLayouts(
						faroProject.getGroupId(), false, "/recommendations");
				}
				else {
					_updateLayouts(
						faroProject.getGroupId(), true, "/recommendations");
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _updateLayouts(
		long groupId, boolean hidden, String... friendlyURLs) {

		for (String friendlyURL : friendlyURLs) {
			Layout layout = _layoutLocalService.fetchLayoutByFriendlyURL(
				groupId, false, friendlyURL);

			if (layout == null) {
				continue;
			}

			layout.setHidden(hidden);

			_layoutLocalService.updateLayout(layout);
		}
	}

	private void _updateMasterLayouts(
		String faviconFileEntryERC, long groupId) {

		for (String layoutPageTemplateEntryKey :
				new String[] {
					"liferay-data-platform-master",
					"liferay-data-platform-settings-master"
				}) {

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntry(
						groupId, layoutPageTemplateEntryKey);

			if (layoutPageTemplateEntry == null) {
				continue;
			}

			Layout layout = _layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());

			layout.setFaviconFileEntryERC(faviconFileEntryERC);

			_layoutLocalService.updateLayout(layout);
		}
	}

	private static final String[] _LDP_FRIENDLY_URLS = {
		"/accounts", "/campaigns", "/lifecycles"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		FaroProjectModelListener.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference(
		target = "(site.initializer.key=com.liferay.osb.faro.workspace.site.initializer)"
	)
	private SiteInitializer _siteInitializer;

}