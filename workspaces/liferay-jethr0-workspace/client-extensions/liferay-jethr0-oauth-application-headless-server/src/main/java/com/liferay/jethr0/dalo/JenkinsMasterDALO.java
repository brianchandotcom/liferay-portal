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

package com.liferay.jethr0.dalo;

import com.liferay.jethr0.jenkins.master.JenkinsMaster;
import com.liferay.jethr0.jenkins.master.JenkinsMasterFactory;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsMasterDALO extends BaseDALO {

	public JenkinsMaster createJenkinsMaster(
		boolean goodBattery, String name, int slaveCount, int slaveRAM) {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"goodBattery", goodBattery
		).put(
			"name", name
		).put(
			"slaveCount", slaveCount
		).put(
			"slaveRAM", slaveRAM
		);

		JSONObject responseJSONObject = create(requestJSONObject);

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return JenkinsMasterFactory.newJenkinsMaster(responseJSONObject);
	}

	public void deleteJenkinsMaster(JenkinsMaster jenkinsMaster) {
		if (jenkinsMaster == null) {
			return;
		}

		delete(jenkinsMaster.getId());

		JenkinsMasterFactory.removeJenkinsMaster(jenkinsMaster);
	}

	public List<JenkinsMaster> retrieveGitBranches() {
		List<JenkinsMaster> jenkinsMasters = new ArrayList<>();

		for (JSONObject responseJSONObject : retrieve()) {
			jenkinsMasters.add(
				JenkinsMasterFactory.newJenkinsMaster(responseJSONObject));
		}

		return jenkinsMasters;
	}

	public JenkinsMaster updateJenkinsMaster(JenkinsMaster jenkinsMaster) {
		JSONObject responseJSONObject = update(jenkinsMaster.getJSONObject());

		if (responseJSONObject == null) {
			throw new RuntimeException("No response");
		}

		return jenkinsMaster;
	}

	@Override
	protected String getObjectDefinitionLabel() {
		return "Jenkins Master";
	}

	@Override
	protected String getObjectDefinitionPluralLabel() {
		return "Jenkins Masters";
	}

}