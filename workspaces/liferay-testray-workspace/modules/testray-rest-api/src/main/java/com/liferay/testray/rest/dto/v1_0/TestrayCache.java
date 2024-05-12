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
		_objectDefinitionsMap.put(
			objectDefinition.getShortName(), objectDefinition);
	}

	public void addObjectEntryId(String key, Long value) {
		_objectEntryIds.put(key, value);
	}

	public long getNextTestrayRunNumber() {
		return _testrayRunNumber++;
	}

	public ObjectDefinition getObjectDefinition(String shortName) {
		return _objectDefinitionsMap.get(shortName);
	}

	public Long getObjectEntryId(String key) {
		return _objectEntryIds.get(key);
	}

	private final Map<String, ObjectDefinition> _objectDefinitionsMap =
		new HashMap<>();
	private final Map<String, Long> _objectEntryIds = new HashMap<>();
	private long _testrayRunNumber = 1;

}