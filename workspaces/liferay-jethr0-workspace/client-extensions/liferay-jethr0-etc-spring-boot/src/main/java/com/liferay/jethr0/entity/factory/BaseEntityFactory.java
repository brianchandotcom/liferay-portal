/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		String entityLabel = _clazz.getSimpleName();

		if (entityLabel.endsWith("Entity")) {
			entityLabel = entityLabel.replaceAll("(.*)Entity", "$1");
		}

		return entityLabel;
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