/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.node;

import com.liferay.jethr0.event.jenkins.client.JenkinsClient;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class MasterJenkinsNodeEntity extends BaseJenkinsNodeEntity {

	protected MasterJenkinsNodeEntity(
		JenkinsClient jenkinsClient, JSONObject jsonObject) {

		super(jenkinsClient, jsonObject);
	}

}