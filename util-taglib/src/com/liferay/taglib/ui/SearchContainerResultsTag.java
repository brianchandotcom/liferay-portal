/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.util.ServerDetector;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Raymond Augé
 * @author Roberto Díaz
 */
public class SearchContainerResultsTag<R> extends TagSupport {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             SearchContainer#DEFAULT_RESULTS_VAR}.
	 */
	public static final String DEFAULT_RESULTS_VAR =
		SearchContainer.DEFAULT_RESULTS_VAR;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             SearchContainer#DEFAULT_DEPRECATED_TOTAL_VAR}.
	 */
	public static final String DEFAULT_TOTAL_VAR =
		SearchContainer.DEFAULT_DEPRECATED_TOTAL_VAR;

	@Override
	public int doEndTag() throws JspException {
		try {
			SearchContainerTag<R> searchContainerTag =
				(SearchContainerTag<R>)findAncestorWithClass(
					this, SearchContainerTag.class);

			SearchContainer<R> searchContainer =
				searchContainerTag.getSearchContainer();

			int total = searchContainer.getTotal();

			if (_deprecatedTotal == 0) {
				_deprecatedTotal = total;
			}

			String totalVar = searchContainer.getTotalVar();

			if (!_deprecatedTotalVar.equals(
					SearchContainer.DEFAULT_DEPRECATED_TOTAL_VAR) &&
				totalVar.equals(SearchContainer.DEFAULT_TOTAL_VAR)) {

				pageContext.removeAttribute(totalVar);

				searchContainer.setTotalVar(_deprecatedTotalVar);
			}
			else {
				pageContext.removeAttribute(_deprecatedTotalVar);

				_deprecatedTotalVar = totalVar;
			}

			if (_results == null) {
				_results = searchContainer.getResults();

				if (_results.isEmpty()) {
					_results = (List<R>)pageContext.getAttribute(_resultsVar);
				}

				_deprecatedTotal = (Integer)pageContext.getAttribute(
					_deprecatedTotalVar);
			}

			if (_results != null) {
				if (_deprecatedTotal < _results.size()) {
					_deprecatedTotal = _results.size();
				}
			}

			searchContainer.setResults(_results);

			if (total == 0) {
				searchContainer.setTotal(_deprecatedTotal);
			}

			pageContext.setAttribute(_resultsVar, _results);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_deprecatedTotal = 0;
				_deprecatedTotalVar =
					SearchContainer.DEFAULT_DEPRECATED_TOTAL_VAR;
				_results = null;
				_resultsVar = SearchContainer.DEFAULT_RESULTS_VAR;
			}
		}
	}

	@Override
	public int doStartTag() throws JspException {
		SearchContainerTag<R> searchContainerTag =
			(SearchContainerTag<R>)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (searchContainerTag == null) {
			throw new JspTagException("Requires liferay-ui:search-container");
		}

		if (_results == null) {
			pageContext.setAttribute(_deprecatedTotalVar, 0);
			pageContext.setAttribute(_resultsVar, new ArrayList<R>());
		}

		return EVAL_BODY_INCLUDE;
	}

	public List<R> getResults() {
		return _results;
	}

	public String getResultsVar() {
		return _resultsVar;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             SearchContainerTag#getTotal()}.
	 */
	public int getTotal() {
		return _deprecatedTotal;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             SearchContainerTag#getTotalVar()}.
	 */
	public String getTotalVar() {
		return _deprecatedTotalVar;
	}

	public void setResults(List<R> results) {
		_results = results;
	}

	public void setResultsVar(String resultsVar) {
		_resultsVar = resultsVar;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             SearchContainerTag#setTotal(int)}.
	 */
	public void setTotal(int deprecatedTotal) {
		_deprecatedTotal = deprecatedTotal;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             SearchContainerTag#setTotalVar(String)}.
	 */
	public void setTotalVar(String deprecatedTotalVar) {
		_deprecatedTotalVar = deprecatedTotalVar;
	}

	private int _deprecatedTotal;
	private String _deprecatedTotalVar =
		SearchContainer.DEFAULT_DEPRECATED_TOTAL_VAR;
	private List<R> _results;
	private String _resultsVar = SearchContainer.DEFAULT_RESULTS_VAR;

}