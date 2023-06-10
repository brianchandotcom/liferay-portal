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

package com.liferay.jethr0.workflow;

import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class QueueProjectWorkflow extends BaseWorkflow {

	@Override
	public String process() throws Exception {
		WorkflowHelper workflowHelper = getWorkflowHelper();

		ProjectRepository projectRepository =
			workflowHelper.getProjectRepository();

		Project project = projectRepository.getById(_getProjectID());

		BuildRepository buildRepository = workflowHelper.getBuildRepository();

		buildRepository.getAll(project);

		project.setState(Project.State.QUEUED);

		projectRepository.update(project);

		BuildQueue buildQueue = workflowHelper.getBuildQueue();

		buildQueue.addProject(project);

		JenkinsQueue jenkinsQueue = workflowHelper.getJenkinsQueue();

		jenkinsQueue.invoke();

		return project.toString();
	}

	protected QueueProjectWorkflow(
		JSONObject jsonObject, WorkflowHelper workflowHelper) {

		super(jsonObject, workflowHelper);
	}

	private Long _getProjectID() throws Exception {
		JSONObject jsonObject = getJSONObject();

		JSONObject projectJSONObject = jsonObject.optJSONObject("project");

		if (projectJSONObject == null) {
			throw new Exception("Missing 'project' JSON object");
		}

		if (!projectJSONObject.has("id")) {
			throw new Exception("Missing project 'id'");
		}

		return projectJSONObject.getLong("id");
	}

}