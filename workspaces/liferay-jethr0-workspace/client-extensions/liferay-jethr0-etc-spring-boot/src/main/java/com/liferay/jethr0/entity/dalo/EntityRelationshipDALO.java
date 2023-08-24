/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.dalo;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.entity.factory.EntityFactory;

import java.util.Set;

/**
 * @author Michael Hashimoto
 */
public interface EntityRelationshipDALO<T extends Entity, U extends Entity>
	extends DALO {

	public void create(T parentEntity, U childEntity);

	public void delete(T parentEntity, U childEntity);

	public Set<U> getChildEntities(T parentEntity);

	public EntityFactory<U> getChildEntityFactory();

	public Set<Long> getChildEntityIds(T parentEntity);

	public Set<T> getParentEntities(U childEntity);

	public EntityFactory<T> getParentEntityFactory();

	public Set<Long> getParentEntityIds(U childEntity);

	public void updateChildEntities(T parentEntity);

	public void updateParentEntities(U childEntity);

}