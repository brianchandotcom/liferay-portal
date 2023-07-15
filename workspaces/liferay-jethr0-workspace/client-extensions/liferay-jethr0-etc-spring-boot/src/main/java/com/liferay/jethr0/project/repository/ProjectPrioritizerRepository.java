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

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerDALO;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerFactory;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerRepository
	extends BaseEntityRepository<ProjectPrioritizer> {

	public ProjectPrioritizer add(String name) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return add(jsonObject);
	}

	public ProjectPrioritizer getByName(String name) {
		for (ProjectPrioritizer projectPrioritizer : getAll()) {
			if (!Objects.equals(projectPrioritizer.getName(), name)) {
				continue;
			}

			return projectPrioritizer;
		}

		return null;
	}

	@Override
	public ProjectPrioritizerDALO getEntityDALO() {
		return _projectPrioritizerDALO;
	}

	@Autowired
	private ProjectPrioritizerDALO _projectPrioritizerDALO;

	@Autowired
	private ProjectPrioritizerFactory _projectPrioritizerFactory;

}