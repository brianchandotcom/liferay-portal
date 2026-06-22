/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class Workspace {

	public String getWeDeployKey() {
		return _weDeployKey;
	}

	public boolean isReady() {
		return _ready;
	}

	public void setReady(boolean ready) {
		_ready = ready;
	}

	public void setWeDeployKey(String weDeployKey) {
		_weDeployKey = weDeployKey;
	}

	public enum Health {

		healthy, none, starting, unhealthy

	}

	private boolean _ready;
	private String _weDeployKey;

}