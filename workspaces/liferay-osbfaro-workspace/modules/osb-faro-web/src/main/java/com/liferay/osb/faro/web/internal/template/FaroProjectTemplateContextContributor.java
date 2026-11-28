/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.template;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.web.internal.util.FaroNavigationUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"template.context.keys=ldpEnabled",
		"template.context.keys=recommendationsEnabled",
		"type=" + TemplateContextContributor.TYPE_GLOBAL
	},
	service = TemplateContextContributor.class
)
public class FaroProjectTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return;
		}

		FaroProject faroProject =
			_faroProjectLocalService.fetchFaroProjectByGroupId(
				themeDisplay.getScopeGroupId());

		if (faroProject == null) {
			return;
		}

		try {
			contextObjects.put(
				"ldpEnabled", FaroNavigationUtil.isLDPEnabled(faroProject));
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		contextObjects.put(
			"recommendationsEnabled", faroProject.isRecommendationsEnabled());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaroProjectTemplateContextContributor.class);

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

}