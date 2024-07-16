/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.prioritizer;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.job.comparator.JobComparatorEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJobPrioritizerEntity
	extends BaseEntity implements JobPrioritizerEntity {

	@Override
	public void addJobComparatorEntities(
		Set<JobComparatorEntity> jobComparatorEntities) {

		addRelatedEntities(jobComparatorEntities);
	}

	@Override
	public void addJobComparatorEntity(
		JobComparatorEntity jobComparatorEntity) {

		addRelatedEntity(jobComparatorEntity);
	}

	@Override
	public Set<JobComparatorEntity> getJobComparatorEntities() {
		return getRelatedEntities(JobComparatorEntity.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"name", getName()
		).put(
			"prioritizedJobIds", String.valueOf(getPrioritizedJobIds())
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<Long> getPrioritizedJobIds() {
		return _prioritizedJobIds;
	}

	@Override
	public void removeJobComparatorEntities(
		Set<JobComparatorEntity> jobComparatorEntities) {

		removeRelatedEntities(jobComparatorEntities);
	}

	@Override
	public void removeJobComparatorEntity(
		JobComparatorEntity jobComparatorEntity) {

		removeRelatedEntity(jobComparatorEntity);
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_name = jsonObject.getString("name");

		String prioritizedJobIds = jsonObject.getString("prioritizedJobIds");

		Matcher matcher = _jobIdsPattern.matcher(prioritizedJobIds);

		_prioritizedJobIds = new ArrayList<>();

		while (matcher.find()) {
			_prioritizedJobIds.add(Long.valueOf(matcher.group()));
		}
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPrioritizedJobIds(List<Long> prioritizedJobIDs) {
		_prioritizedJobIds = prioritizedJobIDs;
	}

	protected BaseJobPrioritizerEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private static final Pattern _jobIdsPattern = Pattern.compile("\\d+");

	private String _name;
	private List<Long> _prioritizedJobIds;

}