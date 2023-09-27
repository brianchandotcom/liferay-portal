/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity;

import java.util.Date;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface Entity {

	public Date getCreatedDate();

	public long getId();

	public JSONObject getJSONObject();

	public Date getModifiedDate();

	public Set<Entity> getRelatedEntities();

	public void setCreatedDate(Date createdDate);

	public void setId(long id);

	public void setModifiedDate(Date modifiedDate);

}