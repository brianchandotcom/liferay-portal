/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.controller;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.event.liferay.LiferayEventHandlerFactory;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RequestMapping("/jobs")
@RestController
public class JobRestController {

	@PostMapping("/action")
	public ResponseEntity<String> action(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String body) {

		try {
			EventHandler eventHandler =
				_liferayEventHandlerFactory.newEventHandler(
					new JSONObject(body));

			return new ResponseEntity<>(eventHandler.process(), HttpStatus.OK);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<String> createJob(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String body) {

		JobEntity jobEntity = _jobEntityRepository.create(new JSONObject(body));

		for (JSONObject initialBuildJSONObject :
				jobEntity.getInitialBuildJSONObjects()) {

			_buildEntityRepository.create(jobEntity, initialBuildJSONObject);
		}

		if (jobEntity.getState() == JobEntity.State.QUEUED) {
			_buildQueue.addJobEntity(jobEntity);

			_jenkinsQueue.invoke();
		}

		return new ResponseEntity<>(jobEntity.toString(), HttpStatus.OK);
	}

	@PostMapping("/delete/{id}")
	public ResponseEntity<String> deleteJob(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("id") int jobEntityId) {

		JobEntity jobEntity = _jobEntityRepository.getById(jobEntityId);

		_jobEntityRepository.remove(jobEntity);

		JSONObject jobJSONObject = jobEntity.getJSONObject();

		return new ResponseEntity<>(jobJSONObject.toString(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> job(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("id") int jobEntityId) {

		JobEntity jobEntity = _jobEntityRepository.getById(jobEntityId);

		JSONObject jobJSONObject = jobEntity.getJSONObject();

		return new ResponseEntity<>(jobJSONObject.toString(), HttpStatus.OK);
	}

	@GetMapping("/{id}/builds")
	public ResponseEntity<String> jobBuilds(
		@AuthenticationPrincipal Jwt jwt, @PathVariable("id") int jobEntityId) {

		JobEntity jobEntity = _jobEntityRepository.getById(jobEntityId);

		JSONArray buildsJSONArray = new JSONArray();

		List<BuildEntity> buildEntities = new ArrayList<>(
			jobEntity.getBuildEntities());

		Collections.sort(
			buildEntities,
			new Comparator<BuildEntity>() {

				@Override
				public int compare(
					BuildEntity buildEntity1, BuildEntity buildEntity2) {

					if (buildEntity1.isInitialBuild() &&
						buildEntity2.isInitialBuild()) {

						return _compareBuildNames(buildEntity1, buildEntity2);
					}

					if (buildEntity1.isInitialBuild()) {
						return -1;
					}

					if (buildEntity2.isInitialBuild()) {
						return 1;
					}

					return _compareBuildNames(buildEntity1, buildEntity2);
				}

				private int _compareBuildNames(
					BuildEntity buildEntity1, BuildEntity buildEntity2) {

					String buildName1 = buildEntity1.getName();
					String buildName2 = buildEntity2.getName();

					return buildName1.compareTo(buildName2);
				}

			});

		for (BuildEntity buildEntity : buildEntities) {
			JSONObject buildJSONObject = buildEntity.getJSONObject();

			BuildRunEntity latestBuildRunEntity =
				buildEntity.getLatestBuildRunEntity();

			if (latestBuildRunEntity != null) {
				buildJSONObject.put(
					"latestDuration", latestBuildRunEntity.getDuration()
				).put(
					"latestJenkinsBuildURL",
					latestBuildRunEntity.getJenkinsBuildURL()
				);
			}

			buildsJSONArray.put(buildJSONObject);
		}

		return new ResponseEntity<>(buildsJSONArray.toString(), HttpStatus.OK);
	}

	@GetMapping("/queue")
	public ResponseEntity<String> jobQueue(@AuthenticationPrincipal Jwt jwt) {
		JSONArray jobsJSONArray = new JSONArray();

		int position = 0;

		for (JobEntity jobEntity : _jobQueue.getJobEntities()) {
			if (jobEntity.getState() == JobEntity.State.COMPLETED) {
				continue;
			}

			position++;

			JSONObject jobJSONObject = jobEntity.getJSONObject();

			jobJSONObject.put("position", position);

			int completedBuilds = 0;
			int queuedBuilds = 0;
			int runningBuilds = 0;
			int totalBuilds = 0;

			for (BuildEntity buildEntity : jobEntity.getBuildEntities()) {
				if (buildEntity.getState() == BuildEntity.State.COMPLETED) {
					completedBuilds++;
				}
				else if (buildEntity.getState() == BuildEntity.State.RUNNING) {
					runningBuilds++;
				}
				else {
					queuedBuilds++;
				}

				totalBuilds++;
			}

			jobJSONObject.put(
				"completedBuilds", completedBuilds
			).put(
				"queuedBuilds", queuedBuilds
			).put(
				"runningBuilds", runningBuilds
			).put(
				"totalBuilds", totalBuilds
			);

			jobsJSONArray.put(jobJSONObject);
		}

		return new ResponseEntity<>(jobsJSONArray.toString(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<String> jobs(@AuthenticationPrincipal Jwt jwt) {
		JSONArray jobsJSONArray = new JSONArray();

		List<JobEntity> jobEntities = new ArrayList<>(
			_jobEntityRepository.getAll());

		Collections.sort(
			jobEntities,
			new Comparator<JobEntity>() {

				@Override
				public int compare(JobEntity jobEntity1, JobEntity jobEntity2) {
					Long jobEntity1Id = jobEntity1.getId();
					Long jobEntity2Id = jobEntity2.getId();

					return jobEntity2Id.compareTo(jobEntity1Id);
				}

			});

		for (JobEntity jobEntity : jobEntities) {
			jobsJSONArray.put(jobEntity.getJSONObject());
		}

		return new ResponseEntity<>(jobsJSONArray.toString(), HttpStatus.OK);
	}

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Autowired
	private JobEntityRepository _jobEntityRepository;

	@Autowired
	private JobQueue _jobQueue;

	@Autowired
	private LiferayEventHandlerFactory _liferayEventHandlerFactory;

}