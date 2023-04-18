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

package com.liferay.jethr0.jenkins.node;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.jenkins.server.JenkinsServer;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface JenkinsNode extends Entity {

	public boolean getGoodBattery();

	public JenkinsServer getJenkinsServer();

	public String getName();

	public int getNodeCount();

	public int getNodeRAM();

	public Type getType();

	public URL getURL();

	public boolean isAvailable();

	public boolean isCompatible(Build build);

	public boolean isIdle();

	public boolean isOffline();

	public void setGoodBattery(boolean goodBattery);

	public void setJenkinsServer(JenkinsServer jenkinsServer);

	public void setName(String name);

	public void setNodeCount(int nodeCount);

	public void setNodeRAM(int nodeRAM);

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