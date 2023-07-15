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

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectComparatorFactory
	extends BaseEntityFactory<ProjectComparator> {

	@Override
	public ProjectComparator newEntity(JSONObject jsonObject) {
		ProjectComparator.Type type = ProjectComparator.Type.get(
			jsonObject.getJSONObject("type"));

		if (type == ProjectComparator.Type.FIFO) {
			return new FIFOProjectComparator(jsonObject);
		}
		else if (type == ProjectComparator.Type.PROJECT_PRIORITY) {
			return new PriorityProjectComparator(jsonObject);
		}
		else if (type == ProjectComparator.Type.PROJECT_START_DATE) {
			return new StartDateProjectComparator(jsonObject);
		}

		throw new UnsupportedOperationException();
	}

	protected ProjectComparatorFactory() {
		super(ProjectComparator.class);
	}

}