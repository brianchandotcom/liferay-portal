/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.api;

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.osb.faro.web.internal.application.ApiApplication;
import com.liferay.osb.faro.web.internal.context.GroupInfo;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * @author Nilton Vieira
 */
@Component(service = {})
@Path("/hubspot_webhooks")
@Produces(MediaType.APPLICATION_JSON)
@RequiresScope(ApiApplication.OAuth2ScopeAliases.HUBSPOT_WRITE)
public class HubSpotWebhookFaroController extends BaseWebhookFaroController {

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{any:.*}")
	@POST
	public Response postHubSpotWebhook(
			@Context GroupInfo groupInfo, String requestBody)
		throws Exception {

		return post(groupInfo, "/webhook-events/hubspot", requestBody);
	}

}