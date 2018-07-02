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

package com.liferay.util;

import java.util.Stack;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), replaced by
 *             {com.liferay.petra.collection.stack.FiniteStack}
 */
@Deprecated
public class FiniteStack<E> extends Stack<E> {

	public FiniteStack(int maxSize) {
		_maxSize = maxSize;
	}

	@Override
	public E push(E item) {
		super.push(item);

		int size = size();

		if (size > _maxSize) {
			removeElementAt(size - 1);
		}

		return item;
	}

	private final int _maxSize;

}