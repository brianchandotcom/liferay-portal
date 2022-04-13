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

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;
import aQute.bnd.annotation.metatype.Meta.Type;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(
	category = "hidden",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.oauth2.application.factory.user.agent.configuration.v1.OAuth2ApplicationUserAgentConfiguration",
	localization = "content/Language", name = "oauth2-application-user-agent-configuration-name"
)
public interface OAuth2ApplicationUserAgentConfiguration {

	@Meta.AD(
		name = "name",
		description = "name-description",
		type = Type.String
	)
	public String name();

	@Meta.AD(
		name = "description",
		description = "description-description",
		type = Type.String,
		required = false
	)
	public String description();

	@Meta.AD(
		name = "company-id",
		description = "company-id-description",
		type = Type.Long
	)
	public long companyId();

	@Meta.AD(
		name = "home-page-url",
		description = "home-page-url-description",
		type = Type.String
	)
	public String homePageUrl();

	@Meta.AD(
		name = "privacy-policy-url",
		description = "privacy-policy-url-description",
		type = Type.String,
		required = false
	)
	public String privacyPolicyUrl();

	@Meta.AD(
		name = "redirect-url",
		description = "redirect-url-description",
		type = Type.String
	)
	public String redirectUrl();

	@Meta.AD(
		name = "scopes",
		description = "scopes-description",
		type = Type.String
	)
	public String[] scopes();

}