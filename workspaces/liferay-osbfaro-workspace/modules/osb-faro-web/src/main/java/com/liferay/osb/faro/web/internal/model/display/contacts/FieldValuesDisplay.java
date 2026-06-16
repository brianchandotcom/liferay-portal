/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import java.util.List;

/**
 * @author Matthew Kong
 */
public class FieldValuesDisplay {

	public FieldValuesDisplay() {
	}

	public FieldValuesDisplay(String name, List<String> values) {
		_name = name;
		_values = values;
	}

	public String getName() {
		return _name;
	}

	public List<String> getValues() {
		return _values;
	}

	private String _name;
	private List<String> _values;

}