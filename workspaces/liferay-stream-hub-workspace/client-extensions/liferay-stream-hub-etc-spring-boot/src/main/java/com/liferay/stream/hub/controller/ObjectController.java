/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.controller;

import com.liferay.stream.hub.client.ObjectDefinitionUtils;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahmoud Hussein Tayem
 */
@RequestMapping("/ui/object/definition")
@RestController
public class ObjectController {

	public ObjectController(ObjectDefinitionUtils objectDefinitionUtils) {
		_objectDefinitionUtils = objectDefinitionUtils;
	}

	@GetMapping("/list")
	public List<Map<String, Object>> getObjectDefinitions() throws Exception {
		return _objectDefinitionUtils.getObjectDefinitionsItems();
	}

	private final ObjectDefinitionUtils _objectDefinitionUtils;

}