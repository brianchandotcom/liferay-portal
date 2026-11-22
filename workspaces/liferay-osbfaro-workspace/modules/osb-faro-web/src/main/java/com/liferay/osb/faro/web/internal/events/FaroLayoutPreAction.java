/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.events;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.web.internal.util.FaroNavigationUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "key=servlet.service.events.pre", service = LifecycleAction.class
)
public class FaroLayoutPreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout == null) {
			return;
		}

		try {
			if (_isBlocked(layout)) {
				throw new NoSuchLayoutException();
			}
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	private boolean _hasAny(
		List<String> friendlyURLs, String[] blockedFriendlyURLs) {

		for (String blockedFriendlyURL : blockedFriendlyURLs) {
			if (friendlyURLs.contains(blockedFriendlyURL)) {
				return true;
			}
		}

		return false;
	}

	private boolean _hasLDPPlan(Layout layout, boolean ldpPlan)
		throws Exception {

		FaroProject faroProject =
			_faroProjectLocalService.fetchFaroProjectByGroupId(
				layout.getGroupId());

		if (faroProject == null) {
			return false;
		}

		if (FaroNavigationUtil.isLDPEnabled(faroProject) == ldpPlan) {
			return true;
		}

		return false;
	}

	private boolean _hasRecommendationsDisabled(Layout layout) {
		FaroProject faroProject =
			_faroProjectLocalService.fetchFaroProjectByGroupId(
				layout.getGroupId());

		if (faroProject == null) {
			return false;
		}

		return !faroProject.isRecommendationsEnabled();
	}

	private boolean _isBlocked(Layout layout) throws Exception {
		List<String> friendlyURLs = TransformUtil.transform(
			layout.getAncestors(), Layout::getFriendlyURL);

		friendlyURLs.add(layout.getFriendlyURL());

		if (_hasAny(
				friendlyURLs,
				new String[] {
					"/asset-known-individuals", "/page-known-individuals"
				})) {

			return _hasLDPPlan(layout, true);
		}

		if (_hasAny(
				friendlyURLs,
				new String[] {
					"/accounts", "/asset-accounts", "/campaigns", "/lifecycles",
					"/page-accounts"
				})) {

			return _hasLDPPlan(layout, false);
		}

		if (friendlyURLs.contains("/recommendations")) {
			return _hasRecommendationsDisabled(layout);
		}

		return false;
	}

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}