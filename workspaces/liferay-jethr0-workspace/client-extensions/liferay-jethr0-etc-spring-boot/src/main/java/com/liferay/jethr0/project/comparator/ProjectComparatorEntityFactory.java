/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectComparatorEntityFactory
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

	protected ProjectComparatorEntityFactory() {
		super(ProjectComparator.class);
	}

}