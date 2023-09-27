/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.factory;

import com.liferay.jethr0.entity.Entity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface EntityFactory<T extends Entity> {

	public Class<T> getEntityClass();

	public String getEntityLabel();

	public String getEntityPluralLabel();

	public T newEntity(JSONObject jsonObject);

}