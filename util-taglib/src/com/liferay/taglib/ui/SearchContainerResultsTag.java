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

import com.liferay.portal.kernel.dao.search.SearchContainer;

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

	@Override
	public int doEndTag() throws JspException {
		try {
			SearchContainerTag<R> searchContainerTag =
				(SearchContainerTag<R>)findAncestorWithClass(
					this, SearchContainerTag.class);

			SearchContainer<R> searchContainer =
				searchContainerTag.getSearchContainer();

			String totalVar = searchContainer.getTotalVar();

			if (totalVar.equals(SearchContainer.DEFAULT_TOTAL_VAR)) {
				pageContext.removeAttribute(totalVar);
			}

			if (_results == null) {
				_results = searchContainer.getResults();

				if (_results.isEmpty()) {
					_results = (List<R>)pageContext.getAttribute(_resultsVar);
				}
			}

			searchContainer.setResults(_results);

			pageContext.setAttribute(_resultsVar, _results);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_results = null;
			_resultsVar = SearchContainer.DEFAULT_RESULTS_VAR;
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

	public void setResults(List<R> results) {
		_results = results;
	}

	public void setResultsVar(String resultsVar) {
		_resultsVar = resultsVar;
	}

	private List<R> _results;
	private String _resultsVar = SearchContainer.DEFAULT_RESULTS_VAR;

}