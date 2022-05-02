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

package com.liferay.oauth2.application.factory.user.agent.configuration.v1;

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
	id = "com.liferay.oauth2.application.factory.user.agent.configuration.v1.OAuth2ApplicationUserAgentConfiguration",
	localization = "content/Language",
	name = "oauth2-application-user-agent-configuration-name"
)
public interface OAuth2ApplicationUserAgentConfiguration {

	@Meta.AD(
		description = "name-description", name = "name", type = Meta.Type.String
	)
	public String name();

	@Meta.AD(
		description = "description-description", name = "description",
		required = false, type = Meta.Type.String
	)
	public String description();

	@Meta.AD(
		description = "company-id-description", name = "company-id",
		type = Meta.Type.Long
	)
	public long companyId();

	@Meta.AD(
		description = "home-page-url-description", name = "home-page-url",
		type = Meta.Type.String
	)
	public String homePageURL();

	@Meta.AD(
		description = "privacy-policy-url-description",
		name = "privacy-policy-url", required = false, type = Meta.Type.String
	)
	public String privacyPolicyURL();

	@Meta.AD(
		description = "redirect-url-description", name = "redirect-url",
		required = false, type = Meta.Type.String
	)
	public String redirectURL();

	@Meta.AD(
		description = "scopes-description", name = "scopes",
		type = Meta.Type.String
	)
	public String[] scopes();

}