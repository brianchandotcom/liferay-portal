/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.branch;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.routine.RoutineEntity;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface GitBranchEntity extends Entity {

	public void addJobEntities(Set<JobEntity> jobEntities);

	public void addJobEntity(JobEntity jobEntity);

	public void addRoutineEntities(Set<RoutineEntity> routineEntities);

	public void addRoutineEntity(RoutineEntity routineEntity);

	public String getBranchName();

	public String getBranchSHA();

	public URL getBranchURL();

	public String getBranchUserName();

	public String getFileContent(String filePath);

	public Set<JobEntity> getJobEntities();

	public Properties getProperties(String propertiesFilePath)
		throws IOException;

	public boolean getRebased();

	public String getRepositoryName();

	public Set<RoutineEntity> getRoutineEntities();

	public String getShortBranchSHA();

	public String getShortUpstreamBranchSHA();

	public Type getType();

	public String getUpstreamBranchName();

	public String getUpstreamBranchSHA();

	public URL getUpstreamBranchURL();

	public String getUpstreamBranchUserName();

	public void removeJobEntities(Set<JobEntity> jobEntities);

	public void removeJobEntity(JobEntity jobEntity);

	public void removeRoutineEntities(Set<RoutineEntity> routineEntities);

	public void removeRoutineEntity(RoutineEntity routineEntity);

	public void setBranchSHA(String branchSHA);

	public void setBranchURL(URL branchURL);

	public void setGitHubClient(GitHubClient gitHubClient);

	public void setRebased(boolean rebased);

	public void setUpstreamBranchSHA(String upstreamBranchSHA);

	public void setUpstreamBranchURL(URL upstreamBranchURL);

	public static enum Type {

		DEFAULT("default"), SENDER("sender"), UPSTREAM("upstream");

		public static Type get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static Type getByKey(String key) {
			return _types.get(key);
		}

		public JSONObject getJSONObject() {
			return new JSONObject("{\"key\": \"" + getKey() + "\"}");
		}

		public String getKey() {
			return _key;
		}

		private Type(String key) {
			_key = key;
		}

		private static final Map<String, Type> _types = new HashMap<>();

		static {
			for (Type type : values()) {
				_types.put(type.getKey(), type);
			}
		}

		private final String _key;

	}

}