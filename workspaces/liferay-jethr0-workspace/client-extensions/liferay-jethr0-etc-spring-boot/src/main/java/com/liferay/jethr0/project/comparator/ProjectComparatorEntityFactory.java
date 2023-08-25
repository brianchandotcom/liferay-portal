/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
	extends BaseEntityFactory<ProjectComparatorEntity> {

	@Override
	public String getEntityLabel() {
		return "Project Comparator";
	}

	@Override
	public ProjectComparatorEntity newEntity(JSONObject jsonObject) {
		ProjectComparatorEntity.Type type = ProjectComparatorEntity.Type.get(
			jsonObject.getJSONObject("type"));

		if (type == ProjectComparatorEntity.Type.FIFO) {
			return new FIFOProjectComparatorEntity(jsonObject);
		}
		else if (type == ProjectComparatorEntity.Type.PROJECT_PRIORITY) {
			return new PriorityProjectComparatorEntity(jsonObject);
		}
		else if (type == ProjectComparatorEntity.Type.PROJECT_START_DATE) {
			return new StartDateProjectComparatorEntity(jsonObject);
		}

		throw new UnsupportedOperationException();
	}

	protected ProjectComparatorEntityFactory() {
		super(ProjectComparatorEntity.class);
	}

}