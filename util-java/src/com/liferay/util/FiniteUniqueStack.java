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

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), replaced by
 *             {com.liferay.petra.collection.stack.FiniteStack}
 */
@Deprecated
public class FiniteUniqueStack<E> extends FiniteStack<E> {

	public FiniteUniqueStack(int maxSize) {
		super(maxSize);
	}

	@Override
	public E push(E item) {
		if (!contains(item)) {
			super.push(item);
		}
		else {
			if (!item.equals(peek())) {
				remove(item);
				super.push(item);
			}
		}

		return item;
	}

}