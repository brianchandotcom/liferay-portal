/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.branch;

import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class UpstreamGitBranchEntity extends BaseGitBranchEntity {

	@Override
	public URL getEntityURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				Jethr0ContextUtil.getLiferayPortalURL(),
				"/#/upstream-branches/", getId()));
	}

	protected UpstreamGitBranchEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}