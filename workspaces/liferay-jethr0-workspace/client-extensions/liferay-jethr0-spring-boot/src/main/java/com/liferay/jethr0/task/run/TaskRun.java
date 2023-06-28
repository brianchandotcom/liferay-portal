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

package com.liferay.jethr0.task.run;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.task.Task;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface TaskRun extends Entity {

	public long getDuration();

	public Result getResult();

	public Task getTask();

	public void setDuration(long duration);

	public void setResult(Result result);

	public void setTask(Task task);

	public enum Result {

		FAILED("failed"), PASSED("passed");

		public static Result get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static Result getByKey(String key) {
			return _results.get(key);
		}

		public JSONObject getJSONObject() {
			return new JSONObject("{\"key\": \"" + getKey() + "\"}");
		}

		public String getKey() {
			return _key;
		}

		private Result(String key) {
			_key = key;
		}

		private static final Map<String, Result> _results = new HashMap<>();

		static {
			for (Result result : values()) {
				_results.put(result.getKey(), result);
			}
		}

		private final String _key;

	}

}