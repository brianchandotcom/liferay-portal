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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEnvironment implements Environment {

	@Override
	public String getAppServer() {
		return _appServer;
	}

	@Override
	public String getBatchName() {
		return _batchName;
	}

	@Override
	public String getBrowser() {
		return _browser;
	}

	@Override
	public String getDatabase() {
		return _database;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public String getJavaVersion() {
		return _javaVersion;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		LiferayBundle liferayBundle = getLiferayBundle();
		LiferayPortalBranch liferayPortalBranch = getLiferayPortalBranch();

		jsonObject.put(
			"appServer", getAppServer()
		).put(
			"batchName", getBatchName()
		).put(
			"browser", getBrowser()
		).put(
			"database", getDatabase()
		).put(
			"id", getId()
		).put(
			"javaVersion", getJavaVersion()
		).put(
			"liferayBundle", liferayBundle.getJSONObject()
		).put(
			"liferayPortalBranch", liferayPortalBranch.getJSONObject()
		).put(
			"operatingSystem", getOperatingSystem()
		);

		return jsonObject;
	}

	@Override
	public LiferayBundle getLiferayBundle() {
		return _liferayBundle;
	}

	@Override
	public LiferayPortalBranch getLiferayPortalBranch() {
		return _liferayPortalBranch;
	}

	@Override
	public String getOperatingSystem() {
		return _operatingSystem;
	}

	@Override
	public void setAppServer(String appServer) {
		_appServer = appServer;
	}

	@Override
	public void setBatchName(String batchName) {
		_batchName = batchName;
	}

	@Override
	public void setBrowser(String browser) {
		_browser = browser;
	}

	@Override
	public void setDatabase(String database) {
		_database = database;
	}

	@Override
	public void setJavaVersion(String javaVersion) {
		_javaVersion = javaVersion;
	}

	@Override
	public void setLiferayBundle(LiferayBundle liferayBundle) {
		_liferayBundle = liferayBundle;
	}

	@Override
	public void setLiferayPortalBranch(
		LiferayPortalBranch liferayPortalBranch) {

		_liferayPortalBranch = liferayPortalBranch;
	}

	@Override
	public void setOperatingSystem(String operatingSystem) {
		_operatingSystem = operatingSystem;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseEnvironment(JSONObject jsonObject) {
		_appServer = jsonObject.getString("appServer");
		_batchName = jsonObject.getString("batchName");
		_browser = jsonObject.getString("browser");
		_database = jsonObject.getString("database");
		_id = jsonObject.getLong("id");
		_javaVersion = jsonObject.getString("operatingSystem");
		_liferayBundle = LiferayBundle.get(
			jsonObject.getJSONObject("liferayBundle"));
		_liferayPortalBranch = LiferayPortalBranch.get(
			jsonObject.getJSONObject("liferayPortalBranch"));
		_operatingSystem = jsonObject.getString("operatingSystem");
	}

	private String _appServer;
	private String _batchName;
	private String _browser;
	private String _database;
	private final long _id;
	private String _javaVersion;
	private LiferayBundle _liferayBundle;
	private LiferayPortalBranch _liferayPortalBranch;
	private String _operatingSystem;

}