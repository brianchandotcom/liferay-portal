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

package com.liferay.remote.app.config.factory.configuration.v1;

import aQute.bnd.annotation.metatype.Meta;
import aQute.bnd.annotation.metatype.Meta.Type;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@DDMForm
@DDMFormLayout(
	paginationMode = com.liferay.dynamic.data.mapping.model.DDMFormLayout.SINGLE_PAGE_MODE,
	value = {
		@DDMFormLayoutPage(
			{
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"name",
								"companyId",
								"description",
								"elementName",
								"webComponentUrl"
							}
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 6,
							value = {
								"portletAlias",
								"instanceable"
							}
						),
						@DDMFormLayoutColumn(
							size = 6,
							value = {
								"portletDisplayCategory"
							}
						)
					}
				),
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"webComponentCssUrl",
								"webComponentTopJsUrl",
								"portletServiceProperties"
							}
						)
					}
				)
			}
		)
	}
)
@ExtendedObjectClassDefinition(
	category = "empty",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.remote.app.config.factory.configuration.v1.RemoteAppConfiguration",
	localization = "content/Language", name = "remote-app-configuration-name"
)
public interface RemoteAppConfiguration {

	@Meta.AD(
		name = "name",
		description = "name-description",
		type = Type.String
	)
	@DDMFormField(
		name = "%name",
		tip = "%name-description",
		required = true,
		type = "text"
	)
	public String name();

	@Meta.AD(
		name = "company-id",
		description = "company-id-description",
		type = Type.Long
	)
	@DDMFormField(
		name = "%company-id",
		tip = "%company-id-description",
		required = true,
		type = "decimal"
	)
	public long companyId();

	@Meta.AD(
		name = "description",
		description = "description-description",
		required = false,
		type = Type.String
	)
	@DDMFormField(
		name = "%description",
		tip = "%description-description",
		type = "text"
	)
	public String description();

	@Meta.AD(
		name = "element-name",
		description = "element-name-description"
	)
	@DDMFormField(
		label = "%element-name",
		tip = "%element-name-description",
		required = true
	)
	public String elementName();

	@Meta.AD(
		name = "web-component-url",
		description = "web-component-url-description"
	)
	@DDMFormField(
		label = "%web-component-url",
		tip = "%web-component-url-description",
		required = true
	)
	public String[] webComponentUrl();

	@Meta.AD(
		name = "web-component-css-url",
		description = "web-component-css-url-description",
		required = false
	)
	@DDMFormField(
		label = "%web-component-css-url",
		tip = "%web-component-css-url-description"
	)
	public String[] webComponentCssUrl();

	@Meta.AD(
		name = "web-component-top-js-url",
		description = "web-component-top-js-url-description",
		required = false
	)
	@DDMFormField(
		label = "%web-component-top-js-url",
		tip = "%web-component-top-js-url-description"
	)
	public String[] webComponentTopJsUrl();

	@Meta.AD(
		name = "friendly-url-mapping",
		description = "friendly-url-mapping-description",
		required = false
	)
	@DDMFormField(
		label = "%friendly-url-mapping",
		tip = "%friendly-url-mapping-description"
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
	@DDMFormField(
		label = "%portlet-display-category",
		tip = "%portlet-display-category-desciption",
		predefinedValue = "remote-app"
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
