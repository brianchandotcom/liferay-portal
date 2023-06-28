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

package com.liferay.jethr0.environment;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.task.Task;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface Environment extends Entity {

	public String getAppServer();

	public String getBatchName();

	public String getBrowser();

	public Build getBuild();

	public String getDatabase();

	public String getJavaVersion();

	public LiferayBundle getLiferayBundle();

	public LiferayPortalBranch getLiferayPortalBranch();

	public String getOperatingSystem();

	public Task getTask();

	public void setAppServer(String appServer);

	public void setBatchName(String batchName);

	public void setBrowser(String browser);

	public void setBuild(Build build);

	public void setDatabase(String database);

	public void setJavaVersion(String javaVersion);

	public void setLiferayBundle(LiferayBundle liferayBundle);

	public void setLiferayPortalBranch(LiferayPortalBranch liferayPortalBranch);

	public void setOperatingSystem(String operatingSystem);

	public void setTask(Task task);

	public enum LiferayBundle {

		DXP_FIXPACK_RELEASE("dxpFixpackRelease"),
		DXP_HOTFIX_RELEASE("dxpHotfixRelease"), DXP_RELEASE("dxpRelease"),
		DXP_SOURCE("dxpSource"), PORTAL_RELEASE("portalRelease"),
		PORTAL_SOURCE("portalSource");

		public static LiferayBundle get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static LiferayBundle getByKey(String key) {
			return _liferayBundles.get(key);
		}

		public JSONObject getJSONObject() {
			return new JSONObject("{\"key\": \"" + getKey() + "\"}");
		}

		public String getKey() {
			return _key;
		}

		private LiferayBundle(String key) {
			_key = key;
		}

		private static final Map<String, LiferayBundle> _liferayBundles =
			new HashMap<>();

		static {
			for (LiferayBundle liferayBundle : values()) {
				_liferayBundles.put(liferayBundle.getKey(), liferayBundle);
			}
		}

		private final String _key;

	}

	public enum LiferayPortalBranch {

		BRANCH_7_0_X("70x"), BRANCH_7_1_X("71x"), BRANCH_7_2_X("72x"),
		BRANCH_7_3_X("73x"), BRANCH_EE_6_1_x("ee61x"),
		BRANCH_EE_6_2_10("ee6210"), BRANCH_EE_6_2_x("ee62x"),
		BRANCH_MASTER("master");

		public static LiferayPortalBranch get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static LiferayPortalBranch getByKey(String key) {
			return _liferayPortalBranches.get(key);
		}

		public JSONObject getJSONObject() {
			return new JSONObject("{\"key\": \"" + getKey() + "\"}");
		}

		public String getKey() {
			return _key;
		}

		private LiferayPortalBranch(String key) {
			_key = key;
		}

		private static final Map<String, LiferayPortalBranch>
			_liferayPortalBranches = new HashMap<>();

		static {
			for (LiferayPortalBranch liferayPortalBranch : values()) {
				_liferayPortalBranches.put(
					liferayPortalBranch.getKey(), liferayPortalBranch);
			}
		}

		private final String _key;

	}

}