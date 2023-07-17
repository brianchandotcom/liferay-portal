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

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
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

	public JenkinsServer add(String url) {
		Matcher jenkinsURLMatcher = _jenkinsURLPattern.matcher(url);

		if (!jenkinsURLMatcher.find()) {
			throw new RuntimeException("Invalid Jenkins URL: " + url);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsUserName", _jenkinsUserName
		).put(
			"jenkinsUserPassword", _jenkinsUserPassword
		).put(
			"name", jenkinsURLMatcher.group("name")
		).put(
			"url", url
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