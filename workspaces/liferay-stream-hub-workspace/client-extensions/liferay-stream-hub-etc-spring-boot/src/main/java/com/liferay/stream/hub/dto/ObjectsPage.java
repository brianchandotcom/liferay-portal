/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.dto;

import java.io.Serializable;

import java.util.List;

/**
 * @author Mahmoud Hussein Tayem
 */
public class ObjectsPage<T> implements Serializable {

	public ObjectsPage() {
	}

	public ObjectsPage(List<T> items, int page, int pageSize, long totalCount) {
		_items = items;
		_page = page;
		_pageSize = pageSize;
		_totalCount = totalCount;
	}

	public List<T> getItems() {
		return _items;
	}

	public int getPage() {
		return _page;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public long getTotalCount() {
		return _totalCount;
	}

	public void setItems(List<T> items) {
		_items = items;
	}

	public void setPage(int page) {
		_page = _page;
	}

	public void setPageSize(int pageSize) {
		_pageSize = pageSize;
	}

	public void setTotalCount(long totalCount) {
		_totalCount = totalCount;
	}

	private List<T> _items;
	private int _page;
	private int _pageSize;
	private long _totalCount;

}