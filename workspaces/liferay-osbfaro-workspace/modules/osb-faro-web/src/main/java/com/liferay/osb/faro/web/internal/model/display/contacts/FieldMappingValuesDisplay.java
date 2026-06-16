/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.Field;
import com.liferay.osb.faro.engine.client.model.FieldMapping;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class FieldMappingValuesDisplay extends FieldMappingDisplay {

	public FieldMappingValuesDisplay() {
	}

	public FieldMappingValuesDisplay(FieldMapping fieldMapping, Field field) {
		super(fieldMapping);

		if (field == null) {
			return;
		}

		_values = Collections.singletonList(field.getValue());
	}

	public FieldMappingValuesDisplay(
		FieldMapping fieldMapping, List<Field> fields) {

		super(fieldMapping);

		if (fields == null) {
			return;
		}

		_values = TransformUtil.transform(fields, Field::getValue);
	}

	private List<String> _values = new ArrayList<>();

}