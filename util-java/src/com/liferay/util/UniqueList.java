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

import java.util.Collection;

/**
 * @author     Brian Wing Shun Chan
 * @author     Shuyang Zhou
 * @deprecated As of 6.2.0, replaced by {@link
 *             com.liferay.portal.kernel.util.UniqueList}
 */
@Deprecated
public class UniqueList<E>
	extends com.liferay.portal.kernel.util.UniqueList<E> {

	public UniqueList() {
	}

	public UniqueList(Collection<E> c) {
		super(c);
	}

	public UniqueList(int initialCapacity) {
		super(initialCapacity);
	}

}