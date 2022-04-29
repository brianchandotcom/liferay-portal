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

package com.liferay.remote.app.factory.configuration.v1;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(
	category = "hidden", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.remote.app.factory.configuration.v1.RemoteAppFactoryConfiguration",
	localization = "content/Language", name = "remote-app-configuration-name"
)
public interface RemoteAppFactoryConfiguration {

	@Meta.AD(
		description = "name-description", name = "name", type = Meta.Type.String
	)
	public String name();

	@Meta.AD(
		description = "company-id-description", name = "company-id",
		type = Meta.Type.Long
	)
	public long companyId();

	@Meta.AD(
		description = "description-description", name = "description",
		required = false, type = Meta.Type.String
	)
	public String description();

	@Meta.AD(description = "element-name-description", name = "element-name")
	public String elementName();

	@Meta.AD(
		description = "web-component-url-description",
		name = "web-component-url"
	)
	public String[] webComponentUrl();

	@Meta.AD(
		description = "web-component-css-url-description",
		name = "web-component-css-url", required = false
	)
	public String[] webComponentCssUrl();

	@Meta.AD(
		description = "web-component-top-js-url-description",
		name = "web-component-top-js-url", required = false
	)
	public String[] webComponentTopJsUrl();

	@Meta.AD(
		description = "friendly-url-mapping-description",
		name = "friendly-url-mapping", required = false
	)
	public String friendlyURLMapping();

	@Meta.AD(
		deflt = "false", description = "instanceable-desciption",
		name = "instanceable", required = false
	)
	public boolean instanceable();

	@Meta.AD(
		deflt = "sample", description = "portlet-display-category-desciption",
		name = "portlet-display-category", required = false
	)
	public String portletDisplayCategory();

	@Meta.AD(
		deflt = "", description = "portlet-service-properties-description",
		name = "portlet-service-properties", required = false
	)
	public String portletServiceProperties();

}