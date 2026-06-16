/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.preferences;

/**
 * @author Marcos Martins
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class GlobalPreferences {

	public boolean getIncidentAlertEnabled() {
		return _incidentAlertEnabled;
	}

	public void setIncidentAlertEnabled(boolean incidentAlertEnabled) {
		_incidentAlertEnabled = incidentAlertEnabled;
	}

	private boolean _incidentAlertEnabled;

}