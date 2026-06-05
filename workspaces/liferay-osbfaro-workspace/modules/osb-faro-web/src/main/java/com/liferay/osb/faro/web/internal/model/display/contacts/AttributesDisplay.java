/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.FieldMapping;

import java.util.Date;
import java.util.List;

/**
 * @author Rachael Koestartyo
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class AttributesDisplay {

	public AttributesDisplay() {
	}

	public AttributesDisplay(FieldMapping fieldMapping) {
		_dataSources = fieldMapping.getDataSources();
		_dateModified = fieldMapping.getDateModified();
		_fieldName = fieldMapping.getDisplayName();
	}

	private List<FieldMapping.DataSourceFieldName> _dataSources;
	private Date _dateModified;
	private String _fieldName;

}