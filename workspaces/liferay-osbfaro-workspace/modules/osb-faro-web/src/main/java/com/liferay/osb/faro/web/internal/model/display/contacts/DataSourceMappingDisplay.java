/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import java.util.List;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DataSourceMappingDisplay extends FieldValuesDisplay {

	public DataSourceMappingDisplay() {
	}

	public DataSourceMappingDisplay(
		String name, List<String> values, FieldMappingValuesDisplay mapping,
		List<FieldMappingValuesDisplay> suggestions) {

		super(name, values);

		_mapping = mapping;
		_suggestions = suggestions;
	}

	private FieldMappingValuesDisplay _mapping;
	private List<FieldMappingValuesDisplay> _suggestions;

}