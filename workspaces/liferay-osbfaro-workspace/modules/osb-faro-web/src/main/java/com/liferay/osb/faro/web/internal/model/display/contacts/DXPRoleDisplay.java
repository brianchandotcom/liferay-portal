/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.DXPRole;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DXPRoleDisplay {

	public DXPRoleDisplay() {
	}

	public DXPRoleDisplay(DXPRole dxpRole) {
		_roleId = dxpRole.getRoleId();
		_id = dxpRole.getId();
		_name = dxpRole.getName();
	}

	private String _id;
	private String _name;
	private long _roleId;

}