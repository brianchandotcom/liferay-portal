/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Brian Wing Shun Chan
 */
public class HtmlTopTag extends OutputTag {

	public HtmlTopTag() {
		super(WebKeys.PAGE_TOP);
	}

}