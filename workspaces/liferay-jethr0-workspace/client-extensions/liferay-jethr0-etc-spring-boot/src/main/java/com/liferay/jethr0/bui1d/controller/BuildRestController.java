/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.controller;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RequestMapping("/builds")
@RestController
public class BuildRestController {

	@GetMapping("/{id}")
	public ResponseEntity<String> build(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable("id") int buildEntityId) {

		BuildEntity buildEntity = _buildEntityRepository.getById(buildEntityId);

		JSONObject buildJSONObject = buildEntity.getJSONObject();

		JobEntity jobEntity = buildEntity.getJobEntity();

		if (jobEntity != null) {
			buildJSONObject.put("job", jobEntity.getJSONObject());
		}

		return new ResponseEntity<>(buildJSONObject.toString(), HttpStatus.OK);
	}

	@GetMapping("/{id}/runs")
	public ResponseEntity<String> buildRuns(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable("id") int buildEntityId) {

		JSONArray buildRunsJSONArray = new JSONArray();

		BuildEntity buildEntity = _buildEntityRepository.getById(buildEntityId);

		for (BuildRunEntity buildRunEntity :
				buildEntity.getBuildRunEntities()) {

			buildRunsJSONArray.put(buildRunEntity.getJSONObject());
		}

		return new ResponseEntity<>(
			buildRunsJSONArray.toString(), HttpStatus.OK);
	}

	@Autowired
	private BuildEntityRepository _buildEntityRepository;

	@Autowired
	private JobEntityRepository _jobEntityRepository;

}