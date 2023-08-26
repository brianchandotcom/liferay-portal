/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.environment;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.task.TaskEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEnvironmentEntity
	extends BaseEntity implements EnvironmentEntity {

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
	public BuildEntity getBuildEntity() {
		return _buildEntity;
	}

	@Override
	public String getDatabase() {
		return _database;
	}

	@Override
	public String getJavaVersion() {
		return _javaVersion;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

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
	public TaskEntity getTaskEntity() {
		return _taskEntity;
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
	public void setBuildEntity(BuildEntity buildEntity) {
		_buildEntity = buildEntity;
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
	public void setTaskEntity(TaskEntity taskEntity) {
		_taskEntity = taskEntity;
	}

	protected BaseEnvironmentEntity(JSONObject jsonObject) {
		super(jsonObject);

		_appServer = jsonObject.getString("appServer");
		_batchName = jsonObject.getString("batchName");
		_browser = jsonObject.getString("browser");
		_database = jsonObject.getString("database");
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
	private BuildEntity _buildEntity;
	private String _database;
	private String _javaVersion;
	private LiferayBundle _liferayBundle;
	private LiferayPortalBranch _liferayPortalBranch;
	private String _operatingSystem;
	private TaskEntity _taskEntity;

}