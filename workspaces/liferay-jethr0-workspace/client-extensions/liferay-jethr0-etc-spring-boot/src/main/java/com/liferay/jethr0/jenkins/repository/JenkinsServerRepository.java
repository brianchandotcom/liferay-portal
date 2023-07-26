/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohort;
import com.liferay.jethr0.jenkins.dalo.JenkinsServerDALO;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsServerRepository
	extends BaseEntityRepository<JenkinsServer> {

	public JenkinsServer add(
		JenkinsCohort jenkinsCohort, JSONObject jsonObject) {

		jsonObject.put(
			"r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId",
			jenkinsCohort.getId());

		JenkinsServer jenkinsServer = add(jsonObject);

		jenkinsServer.setJenkinsCohort(jenkinsCohort);

		jenkinsCohort.addJenkinsServer(jenkinsServer);

		return jenkinsServer;
	}

	@Override
	public JenkinsServer add(JSONObject jsonObject) {
		URL url = StringUtil.toURL(jsonObject.getString("url"));

		Matcher jenkinsURLMatcher = _jenkinsURLPattern.matcher(
			String.valueOf(url));

		if (!jenkinsURLMatcher.find()) {
			throw new RuntimeException("Invalid Jenkins URL: " + url);
		}

		String name = jsonObject.optString("name");

		if (StringUtil.isNullOrEmpty(name)) {
			jsonObject.put("name", jenkinsURLMatcher.group("name"));
		}

		return super.add(jsonObject);
	}

	public JenkinsServer add(
		String jenkinsUserName, String jenkinsUserPassword, String name,
		URL url) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsUserName", jenkinsUserName
		).put(
			"jenkinsUserPassword", jenkinsUserPassword
		).put(
			"name", name
		).put(
			"url", String.valueOf(url)
		);

		return add(jsonObject);
	}

	public JenkinsServer add(URL url) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsUserName", _jenkinsUserName
		).put(
			"jenkinsUserPassword", _jenkinsUserPassword
		).put(
			"url", String.valueOf(url)
		);

		return add(jsonObject);
	}

	public JenkinsServer getByURL(URL url) {
		for (JenkinsServer jenkinsServer : getAll()) {
			if (!StringUtil.equals(jenkinsServer.getURL(), url)) {
				continue;
			}

			return jenkinsServer;
		}

		return null;
	}

	@Override
	public JenkinsServerDALO getEntityDALO() {
		return _jenkinsServerDALO;
	}

	private static final Pattern _jenkinsURLPattern = Pattern.compile(
		"https?://(?<name>[^/]+)(\\.liferay\\.com)?(/.*)?");

	@Autowired
	private JenkinsServerDALO _jenkinsServerDALO;

	@Value("${jenkins.user.name}")
	private String _jenkinsUserName;

	@Value("${jenkins.user.password}")
	private String _jenkinsUserPassword;

}