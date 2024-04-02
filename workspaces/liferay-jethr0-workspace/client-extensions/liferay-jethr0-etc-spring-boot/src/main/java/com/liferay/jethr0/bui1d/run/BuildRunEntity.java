/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.run;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.util.EntityUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface BuildRunEntity extends Entity {

	public BuildEntity getBuildEntity();

	public long getBuildEntityId();

	public long getDuration();

	public JSONObject getInvokeJSONObject(JenkinsNodeEntity jenkinsNodeEntity);

	public URL getJenkinsBuildURL();

	public Result getResult();

	public State getState();

	public boolean isBlocked();

	public void setBuildEntity(BuildEntity buildEntity);

	public void setDuration(long duration);

	public void setJenkinsBuildURL(URL jenkinsBuildURL);

	public void setResult(Result result);

	public void setState(State state);

	public enum Result {

		FAILED("failed", "Failed"), PASSED("passed", "Passed");

		public static Result get(Object picklistValue) {
			return _results.get(
				EntityUtil.getKeyFromPicklistValue(picklistValue));
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"key", _key
			).put(
				"name", _name
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		private Result(String key, String name) {
			_key = key;
			_name = name;
		}

		private static final Map<String, Result> _results = new HashMap<>();

		static {
			for (Result result : values()) {
				_results.put(result.getKey(), result);
			}
		}

		private final String _key;
		private final String _name;

	}

	public enum State {

		BLOCKED("blocked", "Blocked"), COMPLETED("completed", "Completed"),
		OPENED("opened", "Opened"), QUEUED("queued", "Queued"),
		RUNNING("running", "Running");

		public static State get(Object picklistValue) {
			return _states.get(
				EntityUtil.getKeyFromPicklistValue(picklistValue));
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"key", _key
			).put(
				"name", _name
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		private State(String key, String name) {
			_key = key;
			_name = name;
		}

		private static final Map<String, State> _states = new HashMap<>();

		static {
			for (State state : values()) {
				_states.put(state.getKey(), state);
			}
		}

		private final String _key;
		private final String _name;

	}

}