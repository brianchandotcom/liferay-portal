/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.comparator;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.job.prioritizer.JobPrioritizerEntity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface JobComparatorEntity extends Entity {

	public JobPrioritizerEntity getJobPrioritizerEntity();

	public long getJobPrioritizerEntityId();

	public int getPosition();

	public Type getType();

	public String getValue();

	public void setJobPrioritizerEntity(
		JobPrioritizerEntity jobPrioritizerEntity);

	public void setPosition(int position);

	public void setValue(String value);

	public static enum Type {

		FIFO("fifo"), JOB_PRIORITY("jobPriority"),
		JOB_START_DATE("jobStartDate"), JOB_TYPE("jobType"),
		TEST_SUITE_NAME("testSuiteName"),
		UPSTREAM_GIT_BRANCH_NAME("upstreamGitBranchName");

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