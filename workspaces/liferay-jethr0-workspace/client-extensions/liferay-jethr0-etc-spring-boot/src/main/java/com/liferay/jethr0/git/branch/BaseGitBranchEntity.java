/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.branch;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.util.PropertiesUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class BaseGitBranchEntity extends BaseEntity implements GitBranchEntity {

	@Override
	public void addJobEntities(Set<JobEntity> jobEntities) {
		_jobEntities.addAll(jobEntities);
	}

	@Override
	public void addJobEntity(JobEntity jobEntity) {
		addJobEntities(Collections.singleton(jobEntity));
	}

	@Override
	public void addRoutineEntities(Set<RoutineEntity> routineEntities) {
		addRelatedEntities(routineEntities);
	}

	@Override
	public void addRoutineEntity(RoutineEntity routineEntity) {
		addRelatedEntities(Collections.singleton(routineEntity));
	}

	@Override
	public String getBranchName() {
		return _getURLGroupValue(getBranchURL(), "branchName");
	}

	@Override
	public String getBranchSHA() {
		return _branchSHA;
	}

	@Override
	public URL getBranchURL() {
		return _branchURL;
	}

	@Override
	public String getBranchUserName() {
		return _getURLGroupValue(getBranchURL(), "userName");
	}

	@Override
	public String getFileContent(String filePath) {
		return _gitHubClient.requestGet(
			StringUtil.toURL(
				StringUtil.combine(
					"https://raw.githubusercontent.com/", getBranchUserName(),
					"/", getRepositoryName(), "/", getBranchName(), "/",
					filePath)));
	}

	@Override
	public Set<JobEntity> getJobEntities() {
		return _jobEntities;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"branchSHA", getBranchSHA()
		).put(
			"branchURL", getBranchURL()
		).put(
			"rebased", getRebased()
		);

		Type type = getType();

		if (type != null) {
			jsonObject.put("type", type.getJSONObject());
		}

		jsonObject.put(
			"upstreamBranchSHA", getUpstreamBranchSHA()
		).put(
			"upstreamBranchURL", getUpstreamBranchURL()
		);

		return jsonObject;
	}

	@Override
	public Properties getProperties(String propertiesFilePath)
		throws IOException {

		synchronized (_propertiesFiles) {
			Properties properties = _propertiesFiles.get(propertiesFilePath);

			if (properties == null) {
				properties = PropertiesUtil.getProperties(
					getFileContent(propertiesFilePath));

				_propertiesFiles.put(propertiesFilePath, properties);
			}

			return _propertiesFiles.get(propertiesFilePath);
		}
	}

	@Override
	public boolean getRebased() {
		return _rebased;
	}

	@Override
	public String getRepositoryName() {
		return _getURLGroupValue(getBranchURL(), "repositoryName");
	}

	@Override
	public Set<RoutineEntity> getRoutineEntities() {
		return getRelatedEntities(RoutineEntity.class);
	}

	@Override
	public String getShortBranchSHA() {
		String branchSHA = getBranchSHA();

		if (StringUtil.isNullOrEmpty(branchSHA)) {
			return null;
		}

		return branchSHA.substring(0, 7);
	}

	@Override
	public String getShortUpstreamBranchSHA() {
		String upstreamBranchSHA = getUpstreamBranchSHA();

		if (StringUtil.isNullOrEmpty(upstreamBranchSHA)) {
			return null;
		}

		return upstreamBranchSHA.substring(0, 7);
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String getUpstreamBranchName() {
		return _getURLGroupValue(getUpstreamBranchURL(), "branchName");
	}

	@Override
	public String getUpstreamBranchSHA() {
		return _upstreamBranchSHA;
	}

	@Override
	public URL getUpstreamBranchURL() {
		return _upstreamBranchURL;
	}

	@Override
	public String getUpstreamBranchUserName() {
		return _getURLGroupValue(getUpstreamBranchURL(), "userName");
	}

	@Override
	public void removeJobEntities(Set<JobEntity> jobEntities) {
		_jobEntities.removeAll(jobEntities);
	}

	@Override
	public void removeJobEntity(JobEntity jobEntity) {
		_jobEntities.remove(jobEntity);
	}

	@Override
	public void removeRoutineEntities(Set<RoutineEntity> routineEntities) {
		removeRelatedEntities(routineEntities);
	}

	@Override
	public void removeRoutineEntity(RoutineEntity routineEntity) {
		removeRelatedEntities(Collections.singleton(routineEntity));
	}

	@Override
	public void setBranchSHA(String branchSHA) {
		synchronized (_propertiesFiles) {
			_branchSHA = branchSHA;

			_propertiesFiles.clear();
		}
	}

	@Override
	public void setBranchURL(URL branchURL) {
		_branchURL = branchURL;
	}

	@Override
	public void setGitHubClient(GitHubClient gitHubClient) {
		_gitHubClient = gitHubClient;
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_branchSHA = jsonObject.getString("branchSHA");
		_branchURL = StringUtil.toURL(jsonObject.getString("branchURL"));
		_rebased = jsonObject.getBoolean("rebased");
		_type = Type.get(jsonObject.getJSONObject("type"));
		_upstreamBranchSHA = jsonObject.getString("upstreamBranchSHA");
		_upstreamBranchURL = StringUtil.toURL(
			jsonObject.getString("upstreamBranchURL"));
	}

	@Override
	public void setRebased(boolean rebased) {
		_rebased = rebased;
	}

	@Override
	public void setUpstreamBranchSHA(String upstreamBranchSHA) {
		synchronized (_propertiesFiles) {
			_upstreamBranchSHA = upstreamBranchSHA;

			_propertiesFiles.clear();
		}
	}

	@Override
	public void setUpstreamBranchURL(URL upstreamBranchURL) {
		_upstreamBranchURL = upstreamBranchURL;
	}

	protected BaseGitBranchEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private String _getURLGroupValue(URL url, String groupName) {
		if (url == null) {
			return null;
		}

		Matcher matcher = _urlPattern.matcher(String.valueOf(url));

		if (!matcher.find()) {
			return null;
		}

		return matcher.group(groupName);
	}

	private static final Pattern _urlPattern = Pattern.compile(
		"https://github.com/(?<userName>[^/]+)/(?<repositoryName>[^/]+)/" +
			"(commits|tree)/(?<branchName>[^/]+)");

	private String _branchSHA;
	private URL _branchURL;
	private GitHubClient _gitHubClient;
	private final Set<JobEntity> _jobEntities = new HashSet<>();
	private final Map<String, Properties> _propertiesFiles = new HashMap<>();
	private boolean _rebased;
	private Type _type;
	private String _upstreamBranchSHA;
	private URL _upstreamBranchURL;

}