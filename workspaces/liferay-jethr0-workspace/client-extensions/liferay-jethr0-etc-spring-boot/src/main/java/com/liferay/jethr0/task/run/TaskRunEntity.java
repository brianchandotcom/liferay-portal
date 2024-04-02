/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task.run;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.util.EntityUtil;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface TaskRunEntity extends Entity {

	public long getDuration();

	public Result getResult();

	public TaskEntity getTaskEntity();

	public void setDuration(long duration);

	public void setResult(Result result);

	public void setTaskEntity(TaskEntity taskEntity);

	public enum Result {

		FAILED("failed"), PASSED("passed");

		public static Result get(Object picklistValue) {
			return _results.get(
				EntityUtil.getKeyFromPicklistValue(picklistValue));
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