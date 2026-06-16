/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class FieldMapping {

	public String getContext() {
		return _context;
	}

	public String getDataSourceFieldName(String dataSourceUUID) {
		return _dataSourceFieldNames.get(dataSourceUUID);
	}

	public Map<String, String> getDataSourceFieldNames() {
		return _dataSourceFieldNames;
	}

	public List<DataSourceFieldName> getDataSources() {
		return _dataSources;
	}

	public Date getDateModified() {
		if (_dateModified == null) {
			return null;
		}

		return new Date(_dateModified.getTime());
	}

	public String getDisplayName() {
		return _displayName;
	}

	public String getDisplayType() {
		return _displayType;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getFieldType() {
		return _fieldType;
	}

	public String getOwnerType() {
		return _ownerType;
	}

	public Boolean getRepeatable() {
		return _repeatable;
	}

	public void setContext(String context) {
		_context = context;
	}

	public void setDataSourceFieldNames(
		Map<String, String> dataSourceFieldNames) {

		_dataSourceFieldNames = dataSourceFieldNames;
	}

	public void setDataSources(List<DataSourceFieldName> dataSources) {
		_dataSources = dataSources;
	}

	public void setDateModified(Date dateModified) {
		if (dateModified != null) {
			_dateModified = new Date(dateModified.getTime());
		}
	}

	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setFieldType(String fieldType) {
		_fieldType = fieldType;
	}

	public void setOwnerType(String ownerType) {
		_ownerType = ownerType;
	}

	public void setRepeatable(Boolean repeatable) {
		_repeatable = repeatable;
	}

	public static class DataSourceFieldName {

		public String getDataSourceFieldName() {
			return _dataSourceFieldName;
		}

		public String getDataSourceName() {
			return _dataSourceName;
		}

		public void setDataSourceFieldName(String dataSourceFieldName) {
			_dataSourceFieldName = dataSourceFieldName;
		}

		public void setDataSourceName(String dataSourceName) {
			_dataSourceName = dataSourceName;
		}

		private String _dataSourceFieldName;
		private String _dataSourceName;

	}

	private String _context;
	private Map<String, String> _dataSourceFieldNames = new HashMap<>();
	private List<DataSourceFieldName> _dataSources;
	private Date _dateModified;
	private String _displayName;
	private String _displayType;
	private String _fieldName;
	private String _fieldType;
	private String _ownerType;
	private Boolean _repeatable;

}