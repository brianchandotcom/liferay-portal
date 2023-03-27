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

import org.json.JSONObject;

import com.liferay.jethr0.build.Build;

/**
 * @author Michael Hashimoto
 */
public interface JenkinsMaster {

	public boolean getGoodBattery();

	public long getId();

	public JSONObject getJSONObject();

	public String getName();

	public int getSlaveCount();

	public int getSlaveRAM();

	public boolean isCompatible(Build build);

	public void setGoodBattery(boolean goodBattery);

	public void setName(String name);

	public void setSlaveCount(int slaveCount);

	public void setSlaveRAM(int slaveRAM);

}