/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.node;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface JenkinsNodeEntity extends Entity {

	public boolean getGoodBattery();

	public JenkinsCohortEntity getJenkinsCohortEntity();

	public JenkinsServerEntity getJenkinsServerEntity();

	public long getJenkinsServerEntityId();

	public String getName();

	public int getNodeCount();

	public int getNodeRAM();

	public String getPrimaryLabel();

	public Type getType();

	public URL getURL();

	public boolean isAvailable();

	public boolean isCompatible(BuildEntity buildEntity);

	public boolean isIdle();

	public boolean isOffline();

	public void setGoodBattery(boolean goodBattery);

	public void setJenkinsServerEntity(JenkinsServerEntity jenkinsServerEntity);

	public void setName(String name);

	public void setNodeCount(int nodeCount);

	public void setNodeRAM(int nodeRAM);

	public void setPrimaryLabel(String primaryLabel);

	public void setURL(URL url);

	public void update();

	public void update(JSONObject computerJSONObject);

	public static enum Type {

		MASTER("master"), SLAVE("slave");

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