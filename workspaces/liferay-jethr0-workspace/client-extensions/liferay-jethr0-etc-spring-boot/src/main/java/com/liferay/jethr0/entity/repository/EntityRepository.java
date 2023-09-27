/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.repository;

import com.liferay.jethr0.entity.Entity;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface EntityRepository<T extends Entity> {

	public T create(JSONObject jsonObject);

	public Set<T> getAll();

	public T getById(long id);

	public void initialize();

	public void initializeRelationships();

	public void remove(Set<T> entities);

	public void remove(T entity);

	public T update(T entity);

}