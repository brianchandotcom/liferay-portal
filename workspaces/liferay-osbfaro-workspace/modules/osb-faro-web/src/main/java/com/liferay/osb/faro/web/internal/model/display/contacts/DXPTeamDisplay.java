/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.DXPTeam;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DXPTeamDisplay {

	public DXPTeamDisplay() {
	}

	public DXPTeamDisplay(DXPTeam dxpTeam) {
		_teamId = dxpTeam.getGroupId();
		_id = dxpTeam.getId();
		_name = dxpTeam.getName();
	}

	private String _id;
	private String _name;
	private long _teamId;

}