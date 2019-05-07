/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.aui;

import com.liferay.taglib.aui.base.BaseModelContextTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class ModelContextTag extends BaseModelContextTag {

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		Class<?> model = getModel();

		if (model != null) {
			pageContext.setAttribute("aui:model-context:bean", getBean());
			pageContext.setAttribute(
				"aui:model-context:defaultLanguageId", getDefaultLanguageId());
			pageContext.setAttribute("aui:model-context:model", model);
		}
		else {
			pageContext.removeAttribute("aui:model-context:bean");
			pageContext.removeAttribute("aui:model-context:defaultLanguageId");
			pageContext.removeAttribute("aui:model-context:model");
		}
	}

}