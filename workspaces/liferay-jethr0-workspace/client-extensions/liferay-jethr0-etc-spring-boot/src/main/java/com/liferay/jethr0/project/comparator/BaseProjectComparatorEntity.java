/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.comparator;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.project.ProjectEntity;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerEntity;

import java.util.Comparator;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProjectComparatorEntity
	extends BaseEntity
	implements Comparator<ProjectEntity>, ProjectComparatorEntity {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		ProjectComparatorEntity.Type type = getType();

		jsonObject.put(
			"position", getPosition()
		).put(
			"r_projectPrioritizerToProjectComparators_c_projectPrioritizerId",
			getProjectPrioritizerEntityId()
		).put(
			"type", type.getJSONObject()
		).put(
			"value", getValue()
		);

		return jsonObject;
	}

	@Override
	public int getPosition() {
		return _position;
	}

	@Override
	public ProjectPrioritizerEntity getProjectPrioritizerEntity() {
		return _projectPrioritizerEntity;
	}

	@Override
	public long getProjectPrioritizerEntityId() {
		return _projectPrioritizerEntityId;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setPosition(int position) {
		_position = position;
	}

	@Override
	public void setProjectPrioritizerEntity(
		ProjectPrioritizerEntity projectPrioritizerEntity) {

		_projectPrioritizerEntity = projectPrioritizerEntity;

		if (_projectPrioritizerEntity != null) {
			_projectPrioritizerEntityId = _projectPrioritizerEntity.getId();
		}
		else {
			_projectPrioritizerEntityId = 0;
		}
	}

	@Override
	public void setValue(String value) {
		_value = value;
	}

	protected BaseProjectComparatorEntity(JSONObject jsonObject) {
		super(jsonObject);

		_position = jsonObject.getInt("position");
		_projectPrioritizerEntityId = jsonObject.optLong(
			"r_projectPrioritizerToProjectComparators_c_projectPrioritizerId");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_value = jsonObject.optString("value");
	}

	protected BaseProjectComparatorEntity(
		ProjectPrioritizerEntity projectPrioritizerEntity,
		JSONObject jsonObject) {

		super(jsonObject);

		setProjectPrioritizerEntity(projectPrioritizerEntity);

		_position = jsonObject.getInt("position");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_value = jsonObject.optString("value");
	}

	private int _position;
	private ProjectPrioritizerEntity _projectPrioritizerEntity;
	private long _projectPrioritizerEntityId;
	private final Type _type;
	private String _value;

}