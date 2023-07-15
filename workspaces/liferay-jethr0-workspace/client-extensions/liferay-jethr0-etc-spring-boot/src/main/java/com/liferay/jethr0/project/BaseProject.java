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

package com.liferay.jethr0.project;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProject extends BaseEntity implements Project {

	@Override
	public void addBuild(Build build) {
		addRelatedEntity(build);

		build.setProject(this);
	}

	@Override
	public void addBuilds(Set<Build> builds) {
		for (Build build : builds) {
			addBuild(build);
		}
	}

	@Override
	public void addGitBranch(GitBranch gitBranch) {
		addRelatedEntity(gitBranch);
	}

	@Override
	public void addGitBranches(Set<GitBranch> gitBranches) {
		addRelatedEntities(gitBranches);
	}

	@Override
	public void addTask(Task task) {
		addRelatedEntity(task);
	}

	@Override
	public void addTasks(Set<Task> tasks) {
		addRelatedEntities(tasks);
	}

	@Override
	public void addTestSuite(TestSuite testSuite) {
		addRelatedEntity(testSuite);
	}

	@Override
	public void addTestSuites(Set<TestSuite> testSuites) {
		addRelatedEntities(testSuites);
	}

	@Override
	public Set<Build> getBuilds() {
		return getRelatedEntities(Build.class);
	}

	@Override
	public Set<GitBranch> getGitBranches() {
		return getRelatedEntities(GitBranch.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		Project.State state = getState();
		Project.Type type = getType();

		jsonObject.put(
			"name", getName()
		).put(
			"priority", getPriority()
		).put(
			"startDate", StringUtil.toString(getStartDate())
		).put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public State getState() {
		return _state;
	}

	@Override
	public Set<Task> getTasks() {
		return getRelatedEntities(Task.class);
	}

	@Override
	public Set<TestSuite> getTestSuites() {
		return getRelatedEntities(TestSuite.class);
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public void removeBuild(Build build) {
		removeRelatedEntity(build);
	}

	@Override
	public void removeBuilds(Set<Build> builds) {
		removeRelatedEntities(builds);
	}

	@Override
	public void removeGitBranch(GitBranch gitBranch) {
		removeRelatedEntity(gitBranch);
	}

	@Override
	public void removeGitBranches(Set<GitBranch> gitBranches) {
		removeRelatedEntities(gitBranches);
	}

	@Override
	public void removeTask(Task task) {
		removeRelatedEntity(task);
	}

	public void removeTasks(Set<Task> tasks) {
		removeRelatedEntities(tasks);
	}

	@Override
	public void removeTestSuite(TestSuite testSuite) {
		removeRelatedEntity(testSuite);
	}

	@Override
	public void removeTestSuites(Set<TestSuite> testSuites) {
		removeRelatedEntities(testSuites);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPriority(int priority) {
		_priority = priority;
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseProject(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
		_priority = jsonObject.optInt("priority");
		_startDate = StringUtil.toDate(jsonObject.optString("startDate"));
		_state = State.get(jsonObject.getJSONObject("state"));
		_type = Type.get(jsonObject.getJSONObject("type"));
	}

	private String _name;
	private int _priority;
	private Date _startDate;
	private State _state;
	private final Type _type;

}