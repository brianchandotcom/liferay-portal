/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Matthew Kong
 */
public class DataSourceField {

	public DataSourceField() {
	}

	public DataSourceField(String name) {
		_name = name;
	}

	public DataSourceField(String name, List<String> values) {
		_name = name;
		_values = values;
	}

	public Date getDateCreated() {
		if (_dateCreated == null) {
			return null;
		}

		return new Date(_dateCreated.getTime());
	}

	public Date getDateModified() {
		if (_dateModified == null) {
			return null;
		}

		return new Date(_dateModified.getTime());
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public List<String> getValues() {
		return _values;
	}

	public void setDateCreated(Date dateCreated) {
		if (dateCreated != null) {
			_dateCreated = new Date(dateCreated.getTime());
		}
	}

	public void setDateModified(Date dateModified) {
		if (dateModified != null) {
			_dateModified = new Date(dateModified.getTime());
		}
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValues(List<String> values) {
		_values = values;
	}

	private Date _dateCreated;
	private Date _dateModified;
	private String _id;
	private String _name;
	private List<String> _values = new ArrayList<>();

}