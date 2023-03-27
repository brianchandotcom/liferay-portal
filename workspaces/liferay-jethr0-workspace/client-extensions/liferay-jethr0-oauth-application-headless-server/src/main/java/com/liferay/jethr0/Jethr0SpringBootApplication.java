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

package com.liferay.jethr0;

import com.liferay.client.extension.util.spring.ClientExtensionUtilSpringComponentScan;
import com.liferay.jethr0.dalo.ProjectComparatorDALO;
import com.liferay.jethr0.dalo.ProjectDALO;
import com.liferay.jethr0.dalo.ProjectPrioritizerDALO;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.comparator.ProjectComparator;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.queue.ProjectQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author Michael Hashimoto
 */
@Import(ClientExtensionUtilSpringComponentScan.class)
@SpringBootApplication
public class Jethr0SpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(Jethr0SpringBootApplication.class, args);
	}

	@Bean
	public ProjectQueue getProjectQueue(
		ProjectComparatorDALO projectComparatorDALO, ProjectDALO projectDALO,
		ProjectPrioritizerDALO projectPrioritizerDALO) {

		ProjectQueue projectQueue = new ProjectQueue();

		projectQueue.setProjectPrioritizer(
			_getDefaultProjectPrioritizer(
				projectComparatorDALO, projectPrioritizerDALO));

		projectQueue.addProjects(projectDALO.retrieveProjects());

		for (Project project : projectQueue.getProjects()) {
			System.out.println(project);
		}

		return projectQueue;
	}

	private ProjectPrioritizer _getDefaultProjectPrioritizer(
		ProjectComparatorDALO projectComparatorDALO,
		ProjectPrioritizerDALO projectPrioritizerDALO) {

		for (ProjectPrioritizer projectPrioritizer :
				projectPrioritizerDALO.retrieveProjectPrioritizers()) {

			String projectPrioritizerName = projectPrioritizer.getName();

			if (projectPrioritizerName.equals(_liferayProjectPrioritizer)) {
				return projectPrioritizer;
			}
		}

		ProjectPrioritizer projectPrioritizer =
			projectPrioritizerDALO.createProjectPrioritizer(
				_liferayProjectPrioritizer);

		projectComparatorDALO.createProjectComparator(
			projectPrioritizer, 1, ProjectComparator.Type.PROJECT_PRIORITY,
			null);
		projectComparatorDALO.createProjectComparator(
			projectPrioritizer, 2, ProjectComparator.Type.FIFO, null);

		return projectPrioritizer;
	}

	@Value("${liferay.project.prioritizer}")
	private String _liferayProjectPrioritizer;

}