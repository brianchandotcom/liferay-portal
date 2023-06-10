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

package com.liferay.jethr0.jms;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.parameter.BuildParameter;
import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildParameterRepository;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.gitbranch.repository.GitBranchRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.queue.ProjectQueue;
import com.liferay.jethr0.project.repository.ProjectRepository;
import com.liferay.jethr0.task.repository.TaskRepository;
import com.liferay.jethr0.testsuite.repository.TestSuiteRepository;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.jethr0.workflow.Workflow;
import com.liferay.jethr0.workflow.WorkflowFactory;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JMSEventHandler {

	@JmsListener(destination = "${jms.jenkins.event.queue}")
	public void process(String message) {
		if (_log.isDebugEnabled()) {
			_log.debug("Received " + message);
		}

		JSONObject messageJSONObject = new JSONObject(message);

		String eventTrigger = messageJSONObject.getString("eventTrigger");

		if (eventTrigger.equals("BUILD_COMPLETED")) {
			_eventTriggerBuildCompleted(messageJSONObject);
		}
		else if (eventTrigger.equals("BUILD_STARTED")) {
			_eventTriggerBuildStarted(messageJSONObject);
		}
		else if (eventTrigger.equals("COMPUTER_BUSY") ||
				 eventTrigger.equals("COMPUTER_IDLE") ||
				 eventTrigger.equals("COMPUTER_OFFLINE") ||
				 eventTrigger.equals("COMPUTER_ONLINE") ||
				 eventTrigger.equals("COMPUTER_TEMPORARILY_OFFLINE") ||
				 eventTrigger.equals("COMPUTER_TEMPORARILY_ONLINE")) {

			_updateJenkinsNode(messageJSONObject);

			if (eventTrigger.equals("COMPUTER_IDLE")) {
				_eventTriggerComputerIdle(messageJSONObject);
			}
		}
		else if (eventTrigger.equals("CREATE_BUILD")) {
			_eventTriggerCreateBuild(messageJSONObject);
		}
		else if (eventTrigger.equals("CREATE_PROJECT") ||
				 eventTrigger.equals("QUEUE_PROJECT")) {

			Workflow workflow = _workflowFactory.newWorkflow(messageJSONObject);

			if (workflow == null) {
				throw new RuntimeException();
			}

			try {
				workflow.process();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}

				throw new RuntimeException();
			}
		}
	}

	public void send(String message) {
		if (_log.isDebugEnabled()) {
			_log.debug(
				StringUtil.combine(
					"[", _jmsJenkinsBuildQueue, "] Send ", message));
		}

		_jmsTemplate.convertAndSend(_jmsJenkinsBuildQueue, message);
	}

	private void _eventTriggerBuildCompleted(JSONObject messageJSONObject) {
		BuildRun buildRun = _getBuildRun(messageJSONObject);

		if (buildRun == null) {
			return;
		}

		buildRun.setDuration(_getBuildRunDuration(messageJSONObject));
		buildRun.setResult(_getBuildRunResult(messageJSONObject));
		buildRun.setState(BuildRun.State.COMPLETED);

		Build build = buildRun.getBuild();

		build.setState(Build.State.COMPLETED);

		Project project = build.getProject();

		Project.State projectState = Project.State.COMPLETED;

		for (Build projectBuild : project.getBuilds()) {
			Build.State buildState = projectBuild.getState();

			if (buildState != Build.State.COMPLETED) {
				projectState = Project.State.RUNNING;

				break;
			}
		}

		if (projectState == Project.State.COMPLETED) {
			project.setState(projectState);

			_projectRepository.update(project);
		}

		_buildRepository.update(build);
		_buildRunRepository.update(buildRun);
	}

	private void _eventTriggerBuildStarted(JSONObject messageJSONObject) {
		BuildRun buildRun = _getBuildRun(messageJSONObject);

		if (buildRun == null) {
			return;
		}

		buildRun.setBuildURL(_getBuildURL(messageJSONObject));
		buildRun.setState(BuildRun.State.RUNNING);

		Build build = buildRun.getBuild();

		build.setState(Build.State.RUNNING);

		Project project = build.getProject();

		if (project.getState() != Project.State.RUNNING) {
			project.setState(Project.State.RUNNING);

			_projectRepository.update(project);
		}

		_buildRepository.update(build);
		_buildRunRepository.update(buildRun);
	}

	private void _eventTriggerComputerIdle(JSONObject messageJSONObject) {
		JenkinsNode jenkinsNode = _getJenkinsNode(messageJSONObject);

		if (jenkinsNode == null) {
			return;
		}

		Build build = _buildQueue.nextBuild(jenkinsNode);

		if (build == null) {
			return;
		}

		build.setState(Build.State.QUEUED);

		BuildRun buildRun = _buildRunRepository.add(
			build, BuildRun.State.QUEUED);

		send(String.valueOf(buildRun.getInvokeJSONObject()));

		_buildRepository.update(build);
		_buildRunRepository.update(buildRun);
	}

	private void _eventTriggerCreateBuild(JSONObject messageJSONObject) {
		JSONObject buildJSONObject = messageJSONObject.getJSONObject("build");

		Project project = _projectRepository.getById(
			buildJSONObject.getLong("projectId"));

		_buildRepository.getAll(project);
		_gitBranchRepository.getAll(project);
		_taskRepository.getAll(project);
		_testSuiteRepository.getAll(project);

		Build build = _buildRepository.add(
			project, buildJSONObject.getString("buildName"),
			buildJSONObject.getString("jobName"), Build.State.OPENED);

		JSONObject parametersJSONObject = buildJSONObject.getJSONObject(
			"parameters");

		for (String key : parametersJSONObject.keySet()) {
			BuildParameter buildParameter = _buildParameterRepository.add(
				build, key, parametersJSONObject.getString(key));

			build.addBuildParameter(buildParameter);

			buildParameter.setBuild(build);
		}

		if (project.getState() == Project.State.COMPLETED) {
			project.setState(Project.State.QUEUED);

			_buildQueue.addProject(project);

			_jenkinsQueue.invoke();

			_projectRepository.update(project);
		}
	}

	private BuildRun _getBuildRun(JSONObject messageJSONObject) {
		JSONObject buildJSONObject = messageJSONObject.getJSONObject("build");

		JSONObject parmetersJSONObject = buildJSONObject.getJSONObject(
			"parameters");

		String buildRunID = parmetersJSONObject.optString("BUILD_RUN_ID");

		if ((buildRunID == null) || !buildRunID.matches("\\d+")) {
			return null;
		}

		return _buildRunRepository.getById(Long.valueOf(buildRunID));
	}

	private long _getBuildRunDuration(JSONObject messageJSONObject) {
		JSONObject buildJSONObject = messageJSONObject.getJSONObject("build");

		return buildJSONObject.getLong("duration");
	}

	private BuildRun.Result _getBuildRunResult(JSONObject messageJSONObject) {
		JSONObject buildJSONObject = messageJSONObject.getJSONObject("build");

		String result = buildJSONObject.getString("result");

		if (result.equals("SUCCESS")) {
			return BuildRun.Result.PASSED;
		}

		return BuildRun.Result.FAILED;
	}

	private URL _getBuildURL(JSONObject messageJSONObject) {
		JSONObject buildJSONObject = messageJSONObject.getJSONObject("build");
		JSONObject jobJSONObject = messageJSONObject.getJSONObject("job");
		JSONObject jenkinsJSONObject = messageJSONObject.getJSONObject(
			"jenkins");

		return StringUtil.toURL(
			StringUtil.combine(
				jenkinsJSONObject.getString("url"), "job/",
				jobJSONObject.getString("name"), "/",
				buildJSONObject.getInt("number")));
	}

	private JenkinsNode _getJenkinsNode(JSONObject messageJSONObject) {
		JSONObject computerJSONObject = messageJSONObject.getJSONObject(
			"computer");

		return _jenkinsNodeRepository.get(computerJSONObject.getString("name"));
	}

	private JenkinsNode _updateJenkinsNode(JSONObject messageJSONObject) {
		JenkinsNode jenkinsNode = _getJenkinsNode(messageJSONObject);

		JSONObject computerJSONObject = messageJSONObject.getJSONObject(
			"computer");

		computerJSONObject.put(
			"idle", !computerJSONObject.getBoolean("busy")
		).put(
			"offline", !computerJSONObject.getBoolean("online")
		);

		jenkinsNode.update(computerJSONObject);

		return jenkinsNode;
	}

	private static final Log _log = LogFactory.getLog(JMSEventHandler.class);

	@Autowired
	private BuildParameterRepository _buildParameterRepository;

	@Autowired
	private BuildQueue _buildQueue;

	@Autowired
	private BuildRepository _buildRepository;

	@Autowired
	private BuildRunRepository _buildRunRepository;

	@Autowired
	private GitBranchRepository _gitBranchRepository;

	@Autowired
	private JenkinsNodeRepository _jenkinsNodeRepository;

	@Autowired
	private JenkinsQueue _jenkinsQueue;

	@Value("${jms.jenkins.build.queue}")
	private String _jmsJenkinsBuildQueue;

	@Value("${jms.jenkins.event.queue}")
	private String _jmsJenkinsEventQueue;

	@Autowired
	private JmsTemplate _jmsTemplate;

	@Autowired
	private ProjectQueue _projectQueue;

	@Autowired
	private ProjectRepository _projectRepository;

	@Autowired
	private TaskRepository _taskRepository;

	@Autowired
	private TestSuiteRepository _testSuiteRepository;

	@Autowired
	private WorkflowFactory _workflowFactory;

}