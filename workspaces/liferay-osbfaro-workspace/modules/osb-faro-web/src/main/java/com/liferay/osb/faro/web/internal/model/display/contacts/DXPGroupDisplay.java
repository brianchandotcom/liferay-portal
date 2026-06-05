/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.DXPGroup;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DXPGroupDisplay {

	public DXPGroupDisplay() {
	}

	public DXPGroupDisplay(DXPGroup dxpGroup) {
		_friendlyURL = dxpGroup.getFriendlyURL();
		_groupId = dxpGroup.getGroupId();
		_id = GetterUtil.getString(
			dxpGroup.getId(), String.valueOf(dxpGroup.getGroupId()));
		_name = GetterUtil.getString(
			dxpGroup.getDescriptiveName(), dxpGroup.getName());
	}

	private String _friendlyURL;
	private long _groupId;
	private String _id;
	private String _name;

}