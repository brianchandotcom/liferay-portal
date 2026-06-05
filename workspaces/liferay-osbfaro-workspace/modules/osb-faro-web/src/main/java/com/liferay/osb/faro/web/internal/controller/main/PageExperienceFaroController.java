/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.osb.faro.engine.client.model.PageExperience;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.portal.kernel.model.RoleConstants;

import jakarta.annotation.security.RolesAllowed;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Thiago Buarque
 */
@Component
@Path("/{groupId}/page-experiences")
@Produces(MediaType.APPLICATION_JSON)
public class PageExperienceFaroController extends BaseFaroController {

	@GET
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public List<PageExperience> getPageExperiences(
			@PathParam("groupId") long groupId,
			@QueryParam("canonicalUrl") String canonicalUrl,
			@QueryParam("channelId") String channelId,
			@QueryParam("pageTitle") String pageTitle)
		throws Exception {

		return contactsEngineClient.getPageExperiences(
			faroProjectLocalService.getFaroProjectByGroupId(groupId),
			canonicalUrl, channelId, pageTitle);
	}

}