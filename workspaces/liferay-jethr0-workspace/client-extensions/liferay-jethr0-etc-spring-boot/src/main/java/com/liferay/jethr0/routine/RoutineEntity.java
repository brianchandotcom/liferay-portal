/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.EntityUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface RoutineEntity extends Entity {

	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void addGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void addJobEntities(Set<JobEntity> jobEntities);

	public void addJobEntity(JobEntity jobEntity);

	public Set<GitBranchEntity> getGitBranchEntities();

	public Set<JobEntity> getJobEntities();

	public String getJobName();

	public Map<String, String> getJobParameters();

	public String getJobParameterValue(String name);

	public int getJobPriority();

	public JobEntity.Type getJobType();

	public String getName();

	public Type getType();

	public void removeGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void removeJobEntities(Set<JobEntity> jobEntities);

	public void removeJobEntity(JobEntity jobEntity);

	public void setJobName(String jobName);

	public void setJobParameterValue(String name, String value);

	public void setJobPriority(int jobPriority);

	public void setJobType(JobEntity.Type jobType);

	public void setName(String name);

	public void setType(Type type);

	public enum Type {

		CRON("cron", "Cron"), MANUAL("manual", "Manual");

		public static Type get(Object picklistValue) {
			return _types.get(
				EntityUtil.getKeyFromPicklistValue(picklistValue));
		}

		public static Set<String> getKeys() {
			return _types.keySet();
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"key", getKey()
			).put(
				"name", getName()
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		public String getName() {
			return _name;
		}

		private Type(String key, String name) {
			_key = key;
			_name = name;
		}

		private static final Map<String, Type> _types = new HashMap<>();

		static {
			for (Type type : values()) {
				_types.put(type.getKey(), type);
			}
		}

		private final String _key;
		private final String _name;

	}

}