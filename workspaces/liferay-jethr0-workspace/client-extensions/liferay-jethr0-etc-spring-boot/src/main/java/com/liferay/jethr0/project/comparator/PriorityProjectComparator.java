/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PriorityProjectComparator extends BaseProjectComparator {

	public PriorityProjectComparator(JSONObject jsonObject) {
		super(jsonObject);
	}

	public PriorityProjectComparator(
			ProjectPrioritizerEntity projectPrioritizerEntity, JSONObject jsonObject) {

		super(projectPrioritizerEntity, jsonObject);
	}

	@Override
	public int compare(Project project1, Project project2) {
		return Integer.compare(project1.getPriority(), project2.getPriority());
	}

}