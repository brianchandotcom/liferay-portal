/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SearchContainerReference;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.List;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.jsp.JspException;

/**
 * @author Raymond Augé
 * @author Roberto Díaz
 */
public class SearchContainerTag<R> extends ParamAndPropertyAncestorTagImpl {

	@Override
	public int doEndTag() {
		pageContext.setAttribute(
			_searchContainer.getTotalVar(), _searchContainer.getTotal());

		_cssClass = StringPool.BLANK;
		_curParam = SearchContainer.DEFAULT_CUR_PARAM;
		_delta = SearchContainer.DEFAULT_DELTA;
		_deltaConfigurable = SearchContainer.DEFAULT_DELTA_CONFIGURABLE;
		_deltaParam = SearchContainer.DEFAULT_DELTA_PARAM;
		_displayTerms = null;
		_emptyResultsMessage = null;
		_headerNames = null;
		_id = null;
		_iteratorURL = null;
		_orderByCol = null;
		_orderByColParam = SearchContainer.DEFAULT_ORDER_BY_COL_PARAM;
		_orderByComparator = null;
		_orderByType = null;
		_orderByTypeParam = SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM;
		_rowChecker = null;
		_searchContainer = null;
		_searchTerms = null;
		_total = 0;
		_totalVar = SearchContainer.DEFAULT_TOTAL_VAR;
		_var = SearchContainer.DEFAULT_VAR;

		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			PortletRequest portletRequest =
				(PortletRequest)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);
			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			if (_iteratorURL == null) {
				_iteratorURL =
					((MimeResponse)portletResponse).createRenderURL();
			}

			if (_searchContainer == null) {
				_searchContainer = new SearchContainer<R>(
					portletRequest, _displayTerms, _searchTerms, getCurParam(),
					getDelta(), _iteratorURL, null, _emptyResultsMessage);
			}

			_searchContainer.setDeltaConfigurable(_deltaConfigurable);

			if (Validator.isNotNull(_emptyResultsMessage)) {
				_searchContainer.setEmptyResultsMessage(_emptyResultsMessage);
			}

			if (_headerNames != null) {
				_searchContainer.setHeaderNames(_headerNames);
			}

			if (Validator.isNotNull(_id)) {
				_searchContainer.setId(_id);
			}

			if (Validator.isNotNull(_orderByColParam)) {
				_searchContainer.setOrderByColParam(_orderByColParam);
			}

			if (Validator.isNotNull(_orderByCol)) {
				_searchContainer.setOrderByCol(_orderByCol);
			}
			else {
				String orderByCol = ParamUtil.getString(
					request, _searchContainer.getOrderByColParam(), null);

				if (orderByCol != null) {
					_searchContainer.setOrderByCol(orderByCol);
				}
			}

			if (_orderByComparator != null) {
				_searchContainer.setOrderByComparator(_orderByComparator);
			}

			if (Validator.isNotNull(_orderByTypeParam)) {
				_searchContainer.setOrderByTypeParam(_orderByTypeParam);
			}

			if (Validator.isNotNull(_orderByType)) {
				_searchContainer.setOrderByType(_orderByType);
			}
			else {
				String orderByType = ParamUtil.getString(
					request, _searchContainer.getOrderByTypeParam(), null);

				if (orderByType != null) {
					_searchContainer.setOrderByType(orderByType);
				}
			}

			if (_rowChecker != null) {
				_searchContainer.setRowChecker(_rowChecker);
			}

			if (_total != 0) {
				_searchContainer.setTotal(_total);
			}

			if (Validator.isNotNull(_totalVar)) {
				_searchContainer.setTotalVar(_totalVar);
			}

			pageContext.setAttribute(
				_searchContainer.getTotalVar(), _searchContainer.getTotal());
			pageContext.setAttribute(_var, _searchContainer);

			SearchContainerReference searchContainerReference =
				(SearchContainerReference)pageContext.getAttribute(
					"searchContainerReference");

			if ((searchContainerReference != null) &&
				!_var.equals(SearchContainer.DEFAULT_VAR)) {

				searchContainerReference.register(_var, _searchContainer);
			}

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getCurParam() {
		return _curParam;
	}

	public int getDelta() {
		return _delta;
	}

	public String getDeltaParam() {
		return _deltaParam;
	}

	public DisplayTerms getDisplayTerms() {
		return _displayTerms;
	}

	public String getEmptyResultsMessage() {
		return _emptyResultsMessage;
	}

	public PortletURL getIteratorURL() {
		return _iteratorURL;
	}

	public String getOrderByCol() {
		return _orderByCol;
	}

	public String getOrderByColParam() {
		return _orderByColParam;
	}

	public OrderByComparator<R> getOrderByComparator() {
		return _orderByComparator;
	}

	public String getOrderByType() {
		return _orderByType;
	}

	public String getOrderByTypeParam() {
		return _orderByTypeParam;
	}

	public RowChecker getRowChecker() {
		return _rowChecker;
	}

	public SearchContainer<R> getSearchContainer() {
		return _searchContainer;
	}

	public DisplayTerms getSearchTerms() {
		return _searchTerms;
	}

	public int getTotal() {
		return _total;
	}

	public String getTotalVar() {
		return _totalVar;
	}

	public String getVar() {
		return _var;
	}

	public boolean isDeltaConfigurable() {
		return _deltaConfigurable;
	}

	/**
	 * @deprecated As of 6.2.0, with no direct replacement. See LPS-41307.
	 */
	@Deprecated
	public boolean isHasResults() {
		return true;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setCurParam(String curParam) {
		_curParam = curParam;
	}

	public void setDelta(int delta) {
		_delta = delta;
	}

	public void setDeltaConfigurable(boolean deltaConfigurable) {
		_deltaConfigurable = deltaConfigurable;
	}

	public void setDeltaParam(String deltaParam) {
		_deltaParam = deltaParam;
	}

	public void setDisplayTerms(DisplayTerms displayTerms) {
		_displayTerms = displayTerms;
	}

	public void setEmptyResultsMessage(String emptyResultsMessage) {
		_emptyResultsMessage = emptyResultsMessage;
	}

	/**
	 * @deprecated As of 6.2.0, see LPS-41307
	 */
	@Deprecated
	public void setHasResults(boolean hasResults) {
	}

	public void setHeaderNames(String headerNames) {
		_headerNames = ListUtil.toList(StringUtil.split(headerNames));
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIteratorURL(PortletURL iteratorURL) {
		_iteratorURL = iteratorURL;
	}

	public void setOrderByCol(String orderByCol) {
		_orderByCol = orderByCol;
	}

	public void setOrderByColParam(String orderByColParam) {
		_orderByColParam = orderByColParam;
	}

	public void setOrderByComparator(OrderByComparator<R> orderByComparator) {
		_orderByComparator = orderByComparator;
	}

	public void setOrderByType(String orderByType) {
		_orderByType = orderByType;
	}

	public void setOrderByTypeParam(String orderByTypeParam) {
		_orderByTypeParam = orderByTypeParam;
	}

	public void setRowChecker(RowChecker rowChecker) {
		_rowChecker = rowChecker;
	}

	public void setSearchContainer(SearchContainer<R> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setSearchTerms(DisplayTerms searchTerms) {
		_searchTerms = searchTerms;
	}

	public void setTotal(int total) {
		_total = total;
	}

	public void setTotalVar(String totalVar) {
		_totalVar = totalVar;
	}

	public void setVar(String var) {
		_var = var;
	}

	private String _cssClass = StringPool.BLANK;
	private String _curParam = SearchContainer.DEFAULT_CUR_PARAM;
	private int _delta = SearchContainer.DEFAULT_DELTA;
	private boolean _deltaConfigurable =
		SearchContainer.DEFAULT_DELTA_CONFIGURABLE;
	private String _deltaParam = SearchContainer.DEFAULT_DELTA_PARAM;
	private DisplayTerms _displayTerms;
	private String _emptyResultsMessage;
	private List<String> _headerNames;
	private String _id;
	private PortletURL _iteratorURL;
	private String _orderByCol;
	private String _orderByColParam =
		SearchContainer.DEFAULT_ORDER_BY_COL_PARAM;
	private OrderByComparator<R> _orderByComparator;
	private String _orderByType;
	private String _orderByTypeParam =
		SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM;
	private RowChecker _rowChecker;
	private SearchContainer<R> _searchContainer;
	private DisplayTerms _searchTerms;
	private int _total;
	private String _totalVar = SearchContainer.DEFAULT_TOTAL_VAR;
	private String _var = SearchContainer.DEFAULT_VAR;

}