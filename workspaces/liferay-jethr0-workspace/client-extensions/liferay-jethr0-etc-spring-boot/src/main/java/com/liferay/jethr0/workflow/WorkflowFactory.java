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

package com.liferay.jethr0.workflow;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class WorkflowFactory {

	public Workflow newWorkflow(JSONObject jsonObject) {
		Workflow.EventType eventType = Workflow.EventType.valueOf(
			jsonObject.optString("eventTrigger"));

		Workflow workflow = null;

		if (eventType == Workflow.EventType.CREATE_PROJECT) {
			workflow = new CreateProjectWorkflow(jsonObject, _workflowHelper);
		}
		else if (eventType == Workflow.EventType.QUEUE_PROJECT) {
			workflow = new QueueProjectWorkflow(jsonObject, _workflowHelper);
		}

		return workflow;
	}

	@Autowired
	private WorkflowHelper _workflowHelper;

}