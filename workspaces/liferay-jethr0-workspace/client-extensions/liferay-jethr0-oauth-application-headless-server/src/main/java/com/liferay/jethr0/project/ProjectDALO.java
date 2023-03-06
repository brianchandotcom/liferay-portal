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

package com.liferay.jethr0.project;

import com.liferay.jethr0.util.LiferayOAuthConfiguration;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.jethr0.util.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectDALO {

	public Project createProject(
		String name, int priority, Project.State state, Project.Type type) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("name", name);
		requestJSONObject.put("priority", priority);
		requestJSONObject.put("state", state.getJSONObject());
		requestJSONObject.put("type", type.getJSONObject());

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					_liferayPortalURL + "/o/c/projects"
				).post(
				).accept(
					MediaType.APPLICATION_JSON
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuthConfiguration.getAuthorization()
				).body(
					BodyInserters.fromValue(requestJSONObject.toString())
				).retrieve(
				).bodyToMono(
					String.class
				).block();

				if (response == null) {
					throw new RuntimeException("No response");
				}

				Project project = new DefaultProject(new JSONObject(response));

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Created Project ", String.valueOf(project.getID()),
							" on Liferay"));
				}

				return project;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Failed to create Projects on Liferay retry",
							" in ", String.valueOf(_RETRY_DELAY_DURATION),
							"ms ", exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	public void deleteProject(Project project) {
		if (project == null) {
			return;
		}

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				WebClient.create(
					_liferayPortalURL + "/o/c/projects/" + project.getID()
				).delete(
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuthConfiguration.getAuthorization()
				).retrieve(
				).bodyToMono(
					Void.class
				).block();

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Deleted Project ", String.valueOf(project.getID()),
							" from Liferay"));
				}

				break;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Failed to delete Project ",
							String.valueOf(project.getID()),
							" on Liferay retry in ",
							String.valueOf(_RETRY_DELAY_DURATION), "ms ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}
	}

	public List<Project> retrieveProjects() {
		List<Project> projects = new ArrayList<>();

		int currentPage = 1;
		int lastPage = -1;

		while (true) {
			int finalCurrentPage = currentPage;

			for (int i = 0; i <= _RETRY_COUNT; i++) {
				try {
					String response = WebClient.create(
						_liferayPortalURL + "/o/c/projects"
					).get(
					).uri(
						uriBuilder -> uriBuilder.queryParam(
							"page", String.valueOf(finalCurrentPage)
						).build()
					).accept(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization",
						_liferayOAuthConfiguration.getAuthorization()
					).retrieve(
					).bodyToMono(
						String.class
					).block();

					if (response == null) {
						throw new RuntimeException("No response");
					}

					JSONObject responseJSONObject = new JSONObject(response);

					lastPage = responseJSONObject.getInt("lastPage");

					JSONArray itemsJSONArray = responseJSONObject.getJSONArray(
						"items");

					if (itemsJSONArray.isEmpty()) {
						break;
					}

					for (int j = 0; j < itemsJSONArray.length(); j++) {
						Project project = new DefaultProject(
							itemsJSONArray.getJSONObject(j));

						projects.add(project);
					}

					break;
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Failed to retrieve Projects on Liferay retry",
								" in ", String.valueOf(_RETRY_DELAY_DURATION),
								"ms ", exception.getMessage()));
					}

					ThreadUtil.sleep(_RETRY_DELAY_DURATION);
				}
			}

			if ((currentPage >= lastPage) || (lastPage == -1)) {
				break;
			}

			currentPage++;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine(
					"Retrieved ", String.valueOf(projects.size()),
					" Projects on Liferay"));
		}

		return projects;
	}

	public Project updateProject(Project project) {
		JSONObject requestJSONObject = new JSONObject();

		Project.State state = project.getState();
		Project.Type type = project.getType();

		requestJSONObject.put("name", project.getName());
		requestJSONObject.put("priority", project.getPriority());
		requestJSONObject.put("state", state.getJSONObject());
		requestJSONObject.put("type", type.getJSONObject());

		for (int i = 0; i <= _RETRY_COUNT; i++) {
			try {
				String response = WebClient.create(
					_liferayPortalURL + "/o/c/projects/" + project.getID()
				).put(
				).accept(
					MediaType.APPLICATION_JSON
				).contentType(
					MediaType.APPLICATION_JSON
				).header(
					"Authorization",
					_liferayOAuthConfiguration.getAuthorization()
				).body(
					BodyInserters.fromValue(requestJSONObject.toString())
				).retrieve(
				).bodyToMono(
					String.class
				).block();

				if (response == null) {
					throw new RuntimeException("No response");
				}

				JSONObject responseJSONObject = new JSONObject(response);

				long responseID = responseJSONObject.getLong("id");

				if (!Objects.equals(responseID, project.getID())) {
					throw new RuntimeException(
						"Updated wrong project " + responseID);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringUtil.combine(
							"Updated Project ", String.valueOf(project.getID()),
							" on Liferay"));
				}

				return project;
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringUtil.combine(
							"Failed to update Project ",
							String.valueOf(project.getID()),
							" on Liferay retry in ",
							String.valueOf(_RETRY_DELAY_DURATION), "ms ",
							exception.getMessage()));
				}

				ThreadUtil.sleep(_RETRY_DELAY_DURATION);
			}
		}

		return null;
	}

	private static final long _RETRY_COUNT = 3;

	private static final long _RETRY_DELAY_DURATION = 1000;

	private static final Log _log = LogFactory.getLog(ProjectDALO.class);

	@Autowired
	private LiferayOAuthConfiguration _liferayOAuthConfiguration;

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

}