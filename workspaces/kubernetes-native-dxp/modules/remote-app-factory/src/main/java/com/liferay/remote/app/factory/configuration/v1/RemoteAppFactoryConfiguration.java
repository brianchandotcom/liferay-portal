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
import aQute.bnd.annotation.metatype.Meta.Type;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
	category = "hidden",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.remote.app.factory.configuration.v1.RemoteAppFactoryConfiguration",
	localization = "content/Language", name = "remote-app-configuration-name"
)
public interface RemoteAppFactoryConfiguration {

	@Meta.AD(
		name = "name",
		description = "name-description",
		type = Type.String
	)
	public String name();

	@Meta.AD(
		name = "company-id",
		description = "company-id-description",
		type = Type.Long
	)
	public long companyId();

	@Meta.AD(
		name = "description",
		description = "description-description",
		required = false,
		type = Type.String
	)
	public String description();

	@Meta.AD(
		name = "element-name",
		description = "element-name-description"
	)
	public String elementName();

	@Meta.AD(
		name = "web-component-url",
		description = "web-component-url-description"
	)
	public String[] webComponentUrl();

	@Meta.AD(
		name = "web-component-css-url",
		description = "web-component-css-url-description",
		required = false
	)
	public String[] webComponentCssUrl();

	@Meta.AD(
		name = "web-component-top-js-url",
		description = "web-component-top-js-url-description",
		required = false
	)
	public String[] webComponentTopJsUrl();

	@Meta.AD(
		name = "friendly-url-mapping",
		description = "friendly-url-mapping-description",
		required = false
	)
	public String friendlyURLMapping();

	@Meta.AD(
		name = "instanceable",
		description = "instanceable-desciption",
		deflt = "false",
		required = false
	)
	public boolean instanceable();

	@Meta.AD(
		name = "portlet-display-category",
		description = "portlet-display-category-desciption",
		deflt = "sample",
		required = false
	)
	public String portletDisplayCategory();

	@Meta.AD(
		name = "portlet-service-properties",
		description = "portlet-service-properties-description",
		deflt = "",
		required = false
	)
	public String portletServiceProperties();

}
