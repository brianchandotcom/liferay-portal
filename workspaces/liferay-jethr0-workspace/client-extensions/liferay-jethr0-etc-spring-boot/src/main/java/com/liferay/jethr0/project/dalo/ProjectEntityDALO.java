/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.ProjectFactory;
import com.liferay.jethr0.util.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectEntityDALO extends BaseEntityDALO<Project> {

	public Set<Project> getProjectsByState(Project.State... states) {
		Set<Project> projects = new HashSet<>();

		String filter = null;

		if (states.length > 0) {
			Set<String> stateQueries = new HashSet<>();

			for (Project.State state : states) {
				stateQueries.add("(state eq '" + state.getKey() + "')");
			}

			filter = StringUtil.join(" or ", stateQueries);
		}

		List<Project.State> statesList = Arrays.asList(states);

		for (Project project : getAll(filter, null)) {
			if (!statesList.contains(project.getState())) {
				continue;
			}

			projects.add(project);
		}

		return projects;
	}

	@Override
	protected EntityFactory<Project> getEntityFactory() {
		return _projectFactory;
	}

	@Autowired
	private ProjectFactory _projectFactory;

}