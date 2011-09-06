/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.taglib.TagSupport;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CustomAttributesAvailableTag
	extends TagSupport {

	@Override
	public int doStartTag() {
		try {
			long companyId = _companyId;

			if (companyId == 0) {
				companyId = CompanyThreadLocal.getCompanyId();
			}

			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					companyId, _className);

			List<String> attributeNames = Collections.list(
				expandoBridge.getAttributeNames());

			if (attributeNames.isEmpty()) {
				return SKIP_BODY;
			}
			else {
				return EVAL_BODY_INCLUDE;
			}
		}
		finally {
			if (!ServerDetector.isResin()) {
				_className = null;
				_companyId = 0;
			}
		}
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	private String _className;
	private long _companyId;

}