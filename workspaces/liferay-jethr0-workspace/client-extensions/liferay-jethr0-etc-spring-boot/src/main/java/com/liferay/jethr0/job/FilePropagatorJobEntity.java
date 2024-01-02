/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class FilePropagatorJobEntity extends BaseJobEntity {

	public String getCleanUpCommand() {
		return getParameterValue("cleanUpCommand");
	}

	public String getFileNames() {
		return getParameterValue("fileNames");
	}

	public URL getSourceDirURL() {
		return getParameterValueURL("sourceDirURL");
	}

	public String getTargetDirPath() {
		return getParameterValue("targetDirPath");
	}

	public void setCleanUpCommand(String cleanUpCommand) {
		setParameterValue("cleanUpCommand", cleanUpCommand);
	}

	public void setFileNames(String fileNames) {
		setParameterValue("fileNames", fileNames);
	}

	public void setSourceDirURL(URL sourceDirURL) {
		setParameterValueURL("sourceDirURL", sourceDirURL);
	}

	public void setTargetDirPath(String targetDirPath) {
		setParameterValue("targetDirPath", targetDirPath);
	}

	protected FilePropagatorJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("CLEAN_UP_COMMAND", getCleanUpCommand());
		initialBuildParameters.put("FILE_NAMES", getFileNames());
		initialBuildParameters.put(
			"SOURCE_DIR_URL", String.valueOf(getSourceDirURL()));
		initialBuildParameters.put("TARGET_DIR_PATH", getTargetDirPath());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "file-propagator";
	}

}