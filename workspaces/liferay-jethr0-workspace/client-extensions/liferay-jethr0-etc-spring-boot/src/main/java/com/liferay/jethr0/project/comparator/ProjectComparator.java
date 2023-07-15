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

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface ProjectComparator extends Entity {

	public int getPosition();

	public ProjectPrioritizer getProjectPrioritizer();

	public Type getType();

	public String getValue();

	public void setPosition(int position);

	public void setProjectPrioritizer(ProjectPrioritizer projectPrioritizer);

	public void setValue(String value);

	public static enum Type {

		FIFO("fifo"), PROJECT_PRIORITY("projectPriority"),
		PROJECT_START_DATE("projectStartDate"), PROJECT_TYPE("projectType"),
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