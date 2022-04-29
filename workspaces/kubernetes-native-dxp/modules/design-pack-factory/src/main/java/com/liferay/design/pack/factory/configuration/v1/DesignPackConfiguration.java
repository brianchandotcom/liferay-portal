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

package com.liferay.design.pack.factory.configuration.v1;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(
	category = "hidden", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.design.pack.factory.configuration.v1.DesignPackConfiguration",
	localization = "content/Language", name = "design-pack-configuration-name"
)
public interface DesignPackConfiguration {

	@Meta.AD(
		description = "company-id-description", name = "company-id",
		type = Meta.Type.Long
	)
	public long companyId();

	@Meta.AD(
		description = "charset-description", name = "charset",
		type = Meta.Type.Long
	)
	public String charset();

	@Meta.AD(
		description = "clay-css-description",
		name = "clay-css"
	)
	public String clayCss();

	@Meta.AD(
		description = "main-css-description",
		name = "main-css"
	)
	public String mainCss();

}