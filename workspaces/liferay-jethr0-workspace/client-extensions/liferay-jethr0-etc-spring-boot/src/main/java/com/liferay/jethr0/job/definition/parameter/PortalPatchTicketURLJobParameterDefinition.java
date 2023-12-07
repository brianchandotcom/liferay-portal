/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition.parameter;

/**
 * @author Michael Hashimoto
 */
public class PortalPatchTicketURLJobParameterDefinition
	extends BaseJobParameterDefinition {

	@Override
	public String getKey() {
		return "portalPatchTicketURL";
	}

	@Override
	public String getLabel() {
		return "Portal Patch Ticket URL";
	}

	@Override
	public JobParameterDefinition.Type getType() {
		return JobParameterDefinition.Type.URL;
	}

	@Override
	public String getValueDefault() {
		return "https://help.liferay.com/hc/en-us/requests/PATCHERTEST";
	}

	@Override
	public String getValueDescription() {
		return "e.g. https://help.liferay.com/hc/en-us/requests/PATCHERTEST";
	}

	@Override
	public String getValueRegex() {
		return "https?://.+";
	}

}