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

package com.liferay.jethr0.entity.factory;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.util.StringUtil;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEntityFactory<T extends Entity>
	implements EntityFactory<T> {

	@Override
	public Class<T> getEntityClass() {
		return _clazz;
	}

	@Override
	public String getEntityLabel() {
		return _clazz.getSimpleName();
	}

	@Override
	public String getEntityPluralLabel() {
		return StringUtil.combine(getEntityLabel(), "s");
	}

	protected BaseEntityFactory(Class<T> clazz) {
		_clazz = clazz;
	}

	private final Class<T> _clazz;

}