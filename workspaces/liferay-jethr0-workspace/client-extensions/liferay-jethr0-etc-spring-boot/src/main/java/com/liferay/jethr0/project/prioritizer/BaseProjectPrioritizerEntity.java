/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.prioritizer;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.comparator.ProjectComparatorEntity;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProjectPrioritizerEntity
	extends BaseEntity implements ProjectPrioritizerEntity {

	@Override
	public void addProjectComparatorEntities(
		Set<ProjectComparatorEntity> projectComparatorEntities) {

		addRelatedEntities(projectComparatorEntities);
	}

	@Override
	public void addProjectComparatorEntity(
		ProjectComparatorEntity projectComparatorEntity) {

		addRelatedEntity(projectComparatorEntity);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("name", getName());

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Set<ProjectComparatorEntity> getProjectComparatorEntities() {
		return getRelatedEntities(ProjectComparatorEntity.class);
	}

	@Override
	public void removeProjectComparatorEntities(
		Set<ProjectComparatorEntity> projectComparatorEntities) {

		removeRelatedEntities(projectComparatorEntities);
	}

	@Override
	public void removeProjectComparatorEntity(
		ProjectComparatorEntity projectComparatorEntity) {

		removeRelatedEntity(projectComparatorEntity);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	protected BaseProjectPrioritizerEntity(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");
	}

	private String _name;

}