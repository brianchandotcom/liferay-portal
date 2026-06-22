/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Collections;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class Results<T> {

	public Results() {
	}

	public Results(List<T> items, int total) {
		_items = items;
		_total = total;
	}

	public List<T> getItems() {
		return _items;
	}

	public int getTotal() {
		return _total;
	}

	public void setItems(List<T> items) {
		_items = items;
	}

	public void setTotal(int total) {
		_total = total;
	}

	private List<T> _items = Collections.emptyList();
	private int _total;

}