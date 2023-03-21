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

package com.liferay.jethr0.jenkins.master;

import com.liferay.jethr0.builds.Build;
import com.liferay.jethr0.builds.parameter.BuildParameter;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJenkinsMaster implements JenkinsMaster {

	@Override
	public boolean getGoodBattery() {
		return _goodBattery;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"goodBattery", getGoodBattery()
		).put(
			"id", getId()
		).put(
			"name", getName()
		).put(
			"slaveCount", getSlaveCount()
		).put(
			"slaveRAM", getSlaveRAM()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getSlaveCount() {
		return _slaveCount;
	}

	@Override
	public int getSlaveRAM() {
		return _slaveRAM;
	}

	@Override
	public void setGoodBattery(boolean goodBattery) {
		_goodBattery = goodBattery;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setSlaveCount(int slaveCount) {
		_slaveCount = slaveCount;
	}

	@Override
	public void setSlaveRAM(int slaveRAM) {
		_slaveRAM = slaveRAM;
	}

	@Override
	public String toString() {
		return String.valueOf(getJSONObject());
	}

	protected BaseJenkinsMaster(JSONObject jsonObject) {
		_id = jsonObject.getLong("id");

		_goodBattery = jsonObject.getBoolean("goodBattery");
		_name = jsonObject.getString("name");
		_slaveCount = jsonObject.getInt("slaveCount");
		_slaveRAM = jsonObject.getInt("slaveRAM");
	}

	private boolean _goodBattery;
	private final long _id;
	private String _name;
	private int _slaveCount;
	private int _slaveRAM;

}