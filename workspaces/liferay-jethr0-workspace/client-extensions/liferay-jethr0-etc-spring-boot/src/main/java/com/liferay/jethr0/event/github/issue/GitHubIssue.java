/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.issue;

import com.liferay.jethr0.util.StringUtil;
import org.json.JSONObject;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class GitHubIssue {

	public URL getCommentsURL() {
		return StringUtil.toURL(
			_jsonObject.getString("comments_url"));
	}

	public GitHubIssue(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	private final JSONObject _jsonObject;

}