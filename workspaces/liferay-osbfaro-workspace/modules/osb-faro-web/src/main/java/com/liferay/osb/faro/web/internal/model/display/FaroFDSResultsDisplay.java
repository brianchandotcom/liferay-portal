/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display;

import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Marcos Martins
 */
public class FaroFDSResultsDisplay<T> {

	public FaroFDSResultsDisplay() {
	}

	public FaroFDSResultsDisplay(Results<?> results, int page, int pageSize) {
		_page = page;
		_pageSize = pageSize;

		_items = results.getItems();
		_totalCount = results.getTotal();
	}

	public FaroFDSResultsDisplay(
		Results<T> results, Function<T, ?> function, int page, int pageSize) {

		_page = page;
		_pageSize = pageSize;

		_items = TransformUtil.transform(
			results.getItems(), item -> function.apply(item));
		_totalCount = results.getTotal();
	}

	public List<?> getItems() {
		return _items;
	}

	public long getLastPage() {
		if (_totalCount == 0) {
			return 1;
		}

		return -Math.floorDiv(-_totalCount, _pageSize);
	}

	public int getPage() {
		return _page;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public int getTotalCount() {
		return _totalCount;
	}

	private List<?> _items = Collections.emptyList();
	private int _page;
	private int _pageSize;
	private int _totalCount;

}