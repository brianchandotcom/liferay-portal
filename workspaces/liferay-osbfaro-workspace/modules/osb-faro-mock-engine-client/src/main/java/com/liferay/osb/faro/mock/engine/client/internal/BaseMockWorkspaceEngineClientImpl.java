/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.mock.engine.client.internal;

import com.liferay.osb.faro.engine.client.WorkspaceEngineClient;
import com.liferay.osb.faro.engine.client.model.Workspace;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
public abstract class BaseMockWorkspaceEngineClientImpl
	implements WorkspaceEngineClient {

	@Override
	public Workspace getWorkspace(String weDeployKey) throws Exception {
		return workspaceEngineClient.getWorkspace(weDeployKey);
	}

	@Reference(
		target = "(component.name=com.liferay.osb.faro.engine.client.internal.WorkspaceEngineClientImpl)"
	)
	protected WorkspaceEngineClient workspaceEngineClient;

}