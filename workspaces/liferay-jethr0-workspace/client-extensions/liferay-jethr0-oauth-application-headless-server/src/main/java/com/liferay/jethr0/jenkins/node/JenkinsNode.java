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

/**
 * @author Michael Hashimoto
 */
public interface JenkinsNode extends Entity {

	public boolean getGoodBattery();

	public JenkinsServer getJenkinsServer();

	public String getName();

	public int getNodeCount();

	public int getNodeRAM();

	public URL getURL();

	public boolean isCompatible(Build build);

	public void setGoodBattery(boolean goodBattery);

	public void setJenkinsServer(JenkinsServer jenkinsServer);

	public void setName(String name);

	public void setNodeCount(int nodeCount);

	public void setNodeRAM(int nodeRAM);

	public void setURL(URL url);

}