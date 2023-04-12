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

import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.queue.ProjectQueue;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RestController
public class Jethr0RestController {

	@PostMapping(
		consumes = "application/json", produces = "application/json",
		value = "/createProject"
	)
	public ResponseEntity<Project> createProject(
		@RequestBody String requestBody) {

		JSONObject requestJSONObject = new JSONObject(requestBody);

		requestJSONObject.put("state", Project.State.OPENED.getJSONObject());

		return new ResponseEntity<>(
			_projectRepository.add(requestJSONObject), HttpStatus.CREATED);
	}

	@PostMapping(
		consumes = "application/json", produces = "application/json",
		value = "/startProject"
	)
	public ResponseEntity<Project> startProject(
		@RequestBody String requestBody) {

		JSONObject requestJSONObject = new JSONObject(requestBody);

		Project project = _projectRepository.getById(
			requestJSONObject.getLong("id"));

		project.setState(Project.State.RUNNING);

		project = _projectRepository.update(project);

		_projectQueue.addProject(project);

		return new ResponseEntity<>(project, HttpStatus.CREATED);
	}

	@Autowired
	private ProjectQueue _projectQueue;

	@Autowired
	private ProjectRepository _projectRepository;

}