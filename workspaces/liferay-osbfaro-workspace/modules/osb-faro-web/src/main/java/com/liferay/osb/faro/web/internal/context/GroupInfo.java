/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.context;

/**
 * @author Marcellus Tavares
 */
public class GroupInfo {

	public GroupInfo(long groupId) {
		_groupId = groupId;
	}

	public long getGroupId() {
		return _groupId;
	}

	private final long _groupId;

}