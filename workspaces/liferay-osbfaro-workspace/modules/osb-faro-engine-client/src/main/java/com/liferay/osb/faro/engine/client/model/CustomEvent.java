/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Marcos Martins
 */
public class CustomEvent {

	public String getDisplayName() {
		return _displayName;
	}

	public String getName() {
		return _name;
	}

	public boolean isBlocked() {
		return _blocked;
	}

	public boolean isHidden() {
		return _hidden;
	}

	public void setBlocked(boolean blocked) {
		_blocked = blocked;
	}

	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	public void setHidden(boolean hidden) {
		_hidden = hidden;
	}

	public void setName(String name) {
		_name = name;
	}

	private boolean _blocked;
	private String _displayName;
	private boolean _hidden;
	private String _name;

}