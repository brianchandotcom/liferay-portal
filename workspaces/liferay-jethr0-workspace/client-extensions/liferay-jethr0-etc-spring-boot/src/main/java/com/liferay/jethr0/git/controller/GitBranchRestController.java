/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.controller;

import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Hashimoto
 */
@RequestMapping("/git-branches")
@RestController
public class GitBranchRestController {

	@GetMapping("{id}")
	public ResponseEntity<String> gitBranch(
		@AuthenticationPrincipal Jwt jwt,
		@PathVariable("id") int gitBranchEntityId) {

		GitBranchEntity gitBranchEntity = _gitBranchEntityRepository.getById(
			gitBranchEntityId);

		JSONObject gitBranchJSONObject = gitBranchEntity.getJSONObject();

		return new ResponseEntity<>(
			gitBranchJSONObject.toString(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<String> gitBranches(
		@AuthenticationPrincipal Jwt jwt) {

		JSONArray gitBranchesJSONArray = new JSONArray();

		List<GitBranchEntity> gitBranchEntities = new ArrayList<>(
			_gitBranchEntityRepository.getAll());

		Collections.sort(
			gitBranchEntities,
			new Comparator<GitBranchEntity>() {

				@Override
				public int compare(
					GitBranchEntity gitBranchEntity1,
					GitBranchEntity gitBranchEntity2) {

					String gitBranch1URL = String.valueOf(
						gitBranchEntity1.getURL());
					String gitBranch2URL = String.valueOf(
						gitBranchEntity2.getURL());

					return gitBranch2URL.compareTo(gitBranch1URL);
				}

			});

		for (GitBranchEntity gitBranchEntity : gitBranchEntities) {
			gitBranchesJSONArray.put(gitBranchEntity.getJSONObject());
		}

		return new ResponseEntity<>(
			gitBranchesJSONArray.toString(), HttpStatus.OK);
	}

	@GetMapping("/upstream")
	public ResponseEntity<String> upstreamGitBranches(
		@AuthenticationPrincipal Jwt jwt) {

		JSONArray gitBranchesJSONArray = new JSONArray();

		List<GitBranchEntity> gitBranchEntities = new ArrayList<>(
			_gitBranchEntityRepository.getAllByType(
				GitBranchEntity.Type.UPSTREAM));

		Collections.sort(
			gitBranchEntities,
			new Comparator<GitBranchEntity>() {

				@Override
				public int compare(
					GitBranchEntity gitBranchEntity1,
					GitBranchEntity gitBranchEntity2) {

					String gitBranch1URL = String.valueOf(
						gitBranchEntity1.getURL());
					String gitBranch2URL = String.valueOf(
						gitBranchEntity2.getURL());

					return gitBranch2URL.compareTo(gitBranch1URL);
				}

			});

		for (GitBranchEntity gitBranchEntity : gitBranchEntities) {
			gitBranchesJSONArray.put(gitBranchEntity.getJSONObject());
		}

		return new ResponseEntity<>(
			gitBranchesJSONArray.toString(), HttpStatus.OK);
	}

	@Autowired
	private GitBranchEntityRepository _gitBranchEntityRepository;

}