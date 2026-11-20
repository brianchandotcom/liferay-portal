/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.events;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.web.internal.constants.FaroConstants;
import com.liferay.osb.faro.web.internal.util.FaroNavigationUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
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

		FaroProject faroProject =
			_faroProjectLocalService.fetchFaroProjectByGroupId(
				layout.getGroupId());

		if (faroProject == null) {
			return;
		}

		try {
			if (_isBlocked(faroProject, layout)) {
				throw new NoSuchLayoutException();
			}
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	private boolean _isBlocked(FaroProject faroProject, Layout layout)
		throws Exception {

		List<String> friendlyURLs = TransformUtil.transform(
			layout.getAncestors(), Layout::getFriendlyURL);

		friendlyURLs.add(layout.getFriendlyURL());

		for (String friendlyURL : friendlyURLs) {
			if (ArrayUtil.contains(
					FaroConstants.AC_FRIENDLY_URLS, friendlyURL)) {

				return FaroNavigationUtil.isLDPEnabled(faroProject);
			}

			if (ArrayUtil.contains(
					FaroConstants.LDP_FRIENDLY_URLS, friendlyURL)) {

				return !FaroNavigationUtil.isLDPEnabled(faroProject);
			}
		}

		return false;
	}

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}