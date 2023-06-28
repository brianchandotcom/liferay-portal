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
public class ProjectDALO extends BaseEntityDALO<Project> {

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