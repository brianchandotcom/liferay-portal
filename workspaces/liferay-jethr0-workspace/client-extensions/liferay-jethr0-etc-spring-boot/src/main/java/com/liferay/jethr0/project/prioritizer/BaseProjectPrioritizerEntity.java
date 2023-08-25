/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.prioritizer;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.comparator.ProjectComparator;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProjectPrioritizerEntity
	extends BaseEntity implements ProjectPrioritizerEntity {

	@Override
	public void addProjectComparator(ProjectComparator projectComparator) {
		addRelatedEntity(projectComparator);
	}

	@Override
	public void addProjectComparators(
		Set<ProjectComparator> projectComparators) {

		addRelatedEntities(projectComparators);
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
	public Set<ProjectComparator> getProjectComparators() {
		return getRelatedEntities(ProjectComparator.class);
	}

	@Override
	public void removeProjectComparator(ProjectComparator projectComparator) {
		removeRelatedEntity(projectComparator);
	}

	@Override
	public void removeProjectComparators(
		Set<ProjectComparator> projectComparators) {

		removeRelatedEntities(projectComparators);
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