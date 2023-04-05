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

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.environment.EnvironmentFactory;
import com.liferay.jethr0.task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskToEnvironmentsDALO extends BaseRelationshipDALO {

	public JSONObject createRelationship(Task task, Environment environment) {
		return create("/o/c/tasks", task.getId(), environment.getId());
	}

	public void deleteRelationship(Task task, Environment environment) {
		delete("/o/c/tasks", task.getId(), environment.getId());
	}

	public List<Environment> retrieveEnvironments(Task task) {
		List<Environment> environments = new ArrayList<>();

		for (JSONObject responseJSONObject :
				retrieve("/o/c/tasks", task.getId())) {

			environments.add(
				EnvironmentFactory.newEnvironment(responseJSONObject));
		}

		return environments;
	}

	public void updateRelationships(Task task) {
		List<Environment> remoteEnvironments = retrieveEnvironments(task);

		for (Environment environment : task.getEnvironments()) {
			if (remoteEnvironments.contains(environment)) {
				remoteEnvironments.removeAll(
					Collections.singletonList(environment));

				continue;
			}

			createRelationship(task, environment);
		}

		for (Environment remoteEnvironment : remoteEnvironments) {
			deleteRelationship(task, remoteEnvironment);
		}
	}

	@Override
	protected String getObjectRelationshipName() {
		return "taskToEnvironments";
	}

}