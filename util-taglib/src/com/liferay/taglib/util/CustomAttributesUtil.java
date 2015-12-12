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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class CustomAttributesUtil {

	public static boolean hasCustomAttributes(
			long companyId, String className, long classPK,
			String ignoreAttributeNames)
		throws Exception {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			companyId, className, classPK);

		List<String> attributeNames = ListUtil.remove(
			Collections.list(expandoBridge.getAttributeNames()),
			ListUtil.fromString(ignoreAttributeNames, StringPool.COMMA));

		if (ListUtil.isEmpty(attributeNames)) {
			return false;
		}

		return true;
	}

}