/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Riccardo Ferrari
 */
public class AccountLifecycleStageRule {

	public String getFilterMetadata() {
		return _filterMetadata;
	}

	@JsonProperty("filter")
	public String getFilterString() {
		return _filterString;
	}

	public String getName() {
		return _name;
	}

	public void setFilterMetadata(String filterMetadata) {
		_filterMetadata = filterMetadata;
	}

	public void setFilterString(String filterString) {
		_filterString = filterString;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _filterMetadata;
	private String _filterString;
	private String _name;

}