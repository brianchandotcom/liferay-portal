/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.user;

import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.organization.GitHubOrganization;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubUser {

	public GitHubUser(GitHubFactory gitHubFactory, JSONObject jsonObject) {
		_gitHubFactory = gitHubFactory;
		_jsonObject = jsonObject;
	}

	public String getEmail() {
		return _jsonObject.optString("email");
	}

	public GitHubClient getGitHubClient() {
		return _gitHubFactory.getGitHubClient();
	}

	public List<GitHubOrganization> getGitHubOrganizations() {
		if (_gitHubOrganizations != null) {
			return _gitHubOrganizations;
		}

		_gitHubOrganizations = new ArrayList<>();

		GitHubClient gitHubClient = getGitHubClient();

		JSONArray organizationsJSONArray = new JSONArray(
			gitHubClient.requestGet(getOrganizationsURL()));

		for (int i = 0; i < organizationsJSONArray.length(); i++) {
			JSONObject organizationJSONObject =
				organizationsJSONArray.getJSONObject(i);

			_gitHubOrganizations.add(
				_gitHubFactory.newGitHubOrganization(organizationJSONObject));
		}

		return _gitHubOrganizations;
	}

	public URL getHTMLURL() {
		return StringUtil.toURL(_jsonObject.getString("html_url"));
	}

	public String getName() {
		return _jsonObject.getString("login");
	}

	public URL getOrganizationsURL() {
		return StringUtil.toURL(_jsonObject.getString("organizations_url"));
	}

	public URL getURL() {
		return StringUtil.toURL(_jsonObject.getString("url"));
	}

	public boolean isLiferayUser() {
		String name = getName();

		if (name.equals("liferay")) {
			return true;
		}

		for (GitHubOrganization gitHubOrganization : getGitHubOrganizations()) {
			String gitHubOrganizationName = gitHubOrganization.getName();

			if (gitHubOrganizationName.equals("liferay")) {
				return true;
			}
		}

		String email = getEmail();

		if (!StringUtil.isNullOrEmpty(email) &&
			email.endsWith("@liferay.com")) {

			return true;
		}

		return false;
	}

	private final GitHubFactory _gitHubFactory;
	private List<GitHubOrganization> _gitHubOrganizations;
	private final JSONObject _jsonObject;

}