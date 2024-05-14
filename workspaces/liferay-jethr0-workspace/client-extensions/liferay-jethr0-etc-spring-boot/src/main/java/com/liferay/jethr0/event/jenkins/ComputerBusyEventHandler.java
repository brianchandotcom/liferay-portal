/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ComputerBusyEventHandler extends ComputerUpdateEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		super.process();

		JenkinsNodeEntity jenkinsNodeEntity = getJenkinsNodeEntity();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Jenkins node ", jenkinsNodeEntity.getName(),
					" is busy at ", StringUtil.toString(new Date())));
		}

		return jenkinsNodeEntity.toString();
	}

	protected ComputerBusyEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		ComputerBusyEventHandler.class);

}