/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.repository.model.RepositoryModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Raymond Augé
 */
public class SearchContainerRowTag<R>
	extends ParamAndPropertyAncestorTagImpl implements BodyTag {

	public static final String DEFAULT_INDEX_VAR = "index";

	public static final String DEFAULT_MODEL_VAR = "model";

	public static final String DEFAULT_ROW_VAR = "row";

	@Override
	public void addParam(String name, String value) {
		if (name.equals("className")) {
			_row.setClassName(value);
		}
		else if (name.equals("classHoverName")) {
			_row.setClassHoverName(value);
		}
		else if (name.equals("restricted")) {
			_row.setRestricted(GetterUtil.getBoolean(value, false));
		}
		else {
			Object obj = pageContext.getAttribute(value);

			if (obj == null) {
				obj = value;
			}

			_row.setParameter(name, obj);
		}
	}

	@Override
	public int doAfterBody() {
		if (!_headerNamesAssigned) {
			SearchContainerTag<R> searchContainerTag =
				(SearchContainerTag<R>)findAncestorWithClass(
					this, SearchContainerTag.class);

			SearchContainer<R> searchContainer =
				searchContainerTag.getSearchContainer();

			searchContainer.setHeaderNames(_headerNames);
			searchContainer.setOrderableHeaders(_orderableHeaders);

			_headerNamesAssigned = true;
		}

		if (!_row.isSkip()) {
			_resultRows.add(_row);
		}

		_rowIndex++;

		if (_rowIndex < (_results.size())) {
			processRow();

			return EVAL_BODY_AGAIN;
		}
		else {
			return SKIP_BODY;
		}
	}

	@Override
	public int doEndTag() {
		_headerNames = null;
		_headerNamesAssigned = false;
		_resultRows = null;
		_rowIndex = 0;
		_row = null;

		if (!ServerDetector.isResin()) {
			_bold = false;
			_className = null;
			_escapedModel = false;
			_indexVar = DEFAULT_INDEX_VAR;
			_keyProperty = null;
			_modelVar = DEFAULT_MODEL_VAR;
			_orderableHeaders = null;
			_rowVar = DEFAULT_ROW_VAR;
			_stringKey = false;
		}

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		SearchContainerTag<R> searchContainerTag =
			(SearchContainerTag<R>)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (searchContainerTag == null) {
			throw new JspException("Requires liferay-ui:search-container");
		}
		else if (!searchContainerTag.isHasResults()) {
			throw new JspException(
				"Requires liferay-ui:search-container-results");
		}

		SearchContainer<R> searchContainer =
			searchContainerTag.getSearchContainer();

		_resultRows = searchContainer.getResultRows();
		_results = searchContainer.getResults();

		if ((_results != null) && (!_results.isEmpty())) {
			processRow();

			return EVAL_BODY_INCLUDE;
		}
		else {
			return SKIP_BODY;
		}
	}

	public String getClassName() {
		return _className;
	}

	public List<String> getHeaderNames() {
		if (_headerNames == null) {
			_headerNames = new ArrayList<String>();
		}

		return _headerNames;
	}

	public String getIndexVar() {
		return _indexVar;
	}

	public String getKeyProperty() {
		return _keyProperty;
	}

	public String getModelVar() {
		return _modelVar;
	}

	public Map<String, String> getOrderableHeaders() {
		if (_orderableHeaders == null) {
			_orderableHeaders = new LinkedHashMap<String, String>();
		}

		return _orderableHeaders;
	}

	public ResultRow getRow() {
		return _row;
	}

	public String getRowVar() {
		return _rowVar;
	}

	public boolean isBold() {
		return _bold;
	}

	public boolean isEscapedModel() {
		return _escapedModel;
	}

	public boolean isHeaderNamesAssigned() {
		return _headerNamesAssigned;
	}

	public boolean isStringKey() {
		return _stringKey;
	}

	public void setBold(boolean bold) {
		_bold = bold;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setEscapedModel(boolean escapedModel) {
		_escapedModel = escapedModel;
	}

	public void setHeaderNames(List<String> headerNames) {
		_headerNames = headerNames;
	}

	public void setHeaderNamesAssigned(boolean headerNamesAssigned) {
		_headerNamesAssigned = headerNamesAssigned;
	}

	public void setIndexVar(String indexVar) {
		_indexVar = indexVar;
	}

	public void setKeyProperty(String keyProperty) {
		_keyProperty = keyProperty;
	}

	public void setModelVar(String var) {
		_modelVar = var;
	}

	public void setOrderableHeaders(Map<String, String> orderableHeaders) {
		_orderableHeaders = orderableHeaders;
	}

	public void setRow(ResultRow row) {
		_row = row;
	}

	public void setRowVar(String rowVar) {
		_rowVar = rowVar;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
	}

	public void setStringKey(boolean stringKey) {
		_stringKey = stringKey;
	}

	protected void processRow() {
		Object model = _results.get(_rowIndex);

		if (isEscapedModel()) {
			if (model instanceof BaseModel) {
				BaseModel<?> baseModel = (BaseModel<?>)model;

				model = baseModel.toEscapedModel();
			}
			else if (model instanceof RepositoryModel) {
				RepositoryModel<?> repositoryModel = (RepositoryModel<?>)model;

				model = repositoryModel.toEscapedModel();
			}
 		}

		if (Validator.isNull(_keyProperty)) {
			String primaryKey = String.valueOf(model);

			_row = new ResultRow(model, primaryKey, _rowIndex, _bold);
		}
		else if (isStringKey()) {
			String primaryKey = BeanPropertiesUtil.getString(
				model, _keyProperty);

			_row = new ResultRow(model, primaryKey, _rowIndex, _bold);
		}
		else {
			Object primaryKey = BeanPropertiesUtil.getObject(
				model, _keyProperty);

			_row = new ResultRow(
				model, String.valueOf(primaryKey), _rowIndex, _bold);
		}

		pageContext.setAttribute(_indexVar, _rowIndex);
		pageContext.setAttribute(_modelVar, model);
		pageContext.setAttribute(_rowVar, _row);
	}

	private boolean _bold;
	private String _className;
	private boolean _escapedModel;
	private List<String> _headerNames;
	private boolean _headerNamesAssigned;
	private String _indexVar = DEFAULT_INDEX_VAR;
	private String _keyProperty;
	private String _modelVar = DEFAULT_MODEL_VAR;
	private Map<String, String> _orderableHeaders;
	private List<ResultRow> _resultRows;
	private List<R> _results;
	private ResultRow _row;
	private int _rowIndex;
	private String _rowVar = DEFAULT_ROW_VAR;
	private boolean _stringKey;

}