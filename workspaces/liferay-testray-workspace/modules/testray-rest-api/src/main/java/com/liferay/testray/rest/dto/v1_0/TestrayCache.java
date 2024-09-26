/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.dto.v1_0;

import com.liferay.object.model.ObjectDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author José Abelenda
 */
public class TestrayCache {

	public void addObjectDefinition(ObjectDefinition objectDefinition) {
		_objectDefinitions.put(
			objectDefinition.getShortName(), objectDefinition);
	}

	public void addObjectEntryId(String key, Long value) {
		_objectEntryIds.put(key, value);
	}

	public long getNextTestrayRunNumber() {
		return _testrayRunNumber++;
	}

	public ObjectDefinition getObjectDefinition(String shortName) {
		return _objectDefinitions.get(shortName);
	}

	public Long getObjectEntryId(String key) {
		return _objectEntryIds.get(key);
	}

	public long getTestrayBuildId() {
		return _testrayBuildId;
	}

	public String getTestrayBuildName() {
		return _testrayBuildName;
	}

	public long getTestrayCaseResultAmount() {
		return _testrayCaseResultAmount;
	}

	public long getTestrayRoutineId() {
		return _testrayRoutineId;
	}

	public void incrementTestrayCaseResultAmount() {
		_testrayCaseResultAmount++;
	}

	public void setTestrayBuildId(long testrayBuildId) {
		_testrayBuildId = testrayBuildId;
	}

	public void setTestrayBuildName(String testrayBuildName) {
		_testrayBuildName = testrayBuildName;
	}

	public void setTestrayRoutineId(long testrayRoutineId) {
		_testrayRoutineId = testrayRoutineId;
	}

	private final Map<String, ObjectDefinition> _objectDefinitions =
		new HashMap<>();
	private final Map<String, Long> _objectEntryIds = new HashMap<>();
	private long _testrayBuildId;
	private String _testrayBuildName;
	private long _testrayCaseResultAmount;
	private long _testrayRoutineId;
	private long _testrayRunNumber = 1;

}