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

package com.liferay.partner.service;

import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.resource.v1_0.ObjectDefinitionResource;
import com.liferay.partner.util.configuration.ResourceClientConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jair Medeiros
 */
@Service
public class ObjectDefinitionService {

	public Page<ObjectDefinition> getSalesforceObjectDefinitionsPage()
		throws Exception {

		ObjectDefinitionResource objectDefinitionResource =
			_resourceClientConfiguration.getObjectDefinitionResource();

		return objectDefinitionResource.getObjectDefinitionsPage(
			null, null, "contains(name, 'SF')", null, null);
	}

	@Autowired
	private ResourceClientConfiguration _resourceClientConfiguration;

}