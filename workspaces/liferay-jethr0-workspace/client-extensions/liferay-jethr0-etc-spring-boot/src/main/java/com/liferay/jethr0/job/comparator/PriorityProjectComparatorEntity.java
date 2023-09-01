/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.comparator;

import com.liferay.jethr0.job.ProjectEntity;
import com.liferay.jethr0.job.prioritizer.ProjectPrioritizerEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PriorityProjectComparatorEntity
	extends BaseProjectComparatorEntity {

	public PriorityProjectComparatorEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	public PriorityProjectComparatorEntity(
		ProjectPrioritizerEntity projectPrioritizerEntity,
		JSONObject jsonObject) {

		super(projectPrioritizerEntity, jsonObject);
	}

	@Override
	public int compare(
		ProjectEntity projectEntity1, ProjectEntity projectEntity2) {

		return Integer.compare(
			projectEntity1.getPriority(), projectEntity2.getPriority());
	}

}