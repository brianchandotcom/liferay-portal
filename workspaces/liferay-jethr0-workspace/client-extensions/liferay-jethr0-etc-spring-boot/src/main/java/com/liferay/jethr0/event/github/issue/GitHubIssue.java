/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.issue;

import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubIssue {

	public GitHubIssue(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	public URL getCommentsURL() {
		return StringUtil.toURL(_jsonObject.getString("comments_url"));
	}

	public URL getHTMLURL() {
		return StringUtil.toURL(_jsonObject.getString("html_url"));
	}

	public URL getPullRequestAPIURL() {
		JSONObject jsonObject = _jsonObject.getJSONObject("pull_request");

		return StringUtil.toURL(jsonObject.getString("url"));
	}

	private final JSONObject _jsonObject;

}