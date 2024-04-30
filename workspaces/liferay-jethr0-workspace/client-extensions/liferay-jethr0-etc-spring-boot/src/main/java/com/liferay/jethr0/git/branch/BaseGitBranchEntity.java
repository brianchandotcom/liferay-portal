/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.branch;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
import com.liferay.jethr0.git.user.GitUserEntity;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.util.PropertiesUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitBranchEntity
	extends BaseEntity implements GitBranchEntity {

	@Override
	public void addGitCommitEntities(Set<GitCommitEntity> gitCommitEntities) {
		addRelatedEntities(gitCommitEntities);
	}

	@Override
	public void addGitCommitEntity(GitCommitEntity gitCommitEntity) {
		addRelatedEntity(gitCommitEntity);
	}

	@Override
	public void addGitPullRequestEntities(
		Set<GitPullRequestEntity> gitPullRequestEntities) {

		addRelatedEntities(gitPullRequestEntities);
	}

	@Override
	public void addGitPullRequestEntity(
		GitPullRequestEntity gitPullRequestEntity) {

		addRelatedEntity(gitPullRequestEntity);
	}

	@Override
	public void addRoutineEntities(Set<RoutineEntity> routineEntities) {
		addRelatedEntities(routineEntities);
	}

	@Override
	public void addRoutineEntity(RoutineEntity routineEntity) {
		addRelatedEntity(routineEntity);
	}

	@Override
	public String getFileContent(String filePath) {
		return _gitHubClient.requestGet(
			StringUtil.toURL(
				StringUtil.combine(
					"https://raw.githubusercontent.com/", getUserName(), "/",
					getRepositoryName(), "/", getName(), "/", filePath)));
	}

	@Override
	public Set<GitCommitEntity> getGitCommitEntities() {
		return getRelatedEntities(GitCommitEntity.class);
	}

	@Override
	public Set<GitPullRequestEntity> getGitPullRequestEntities() {
		return getRelatedEntities(GitPullRequestEntity.class);
	}

	@Override
	public GitUserEntity getGitUserEntity() {
		return _gitUserEntity;
	}

	@Override
	public long getGitUserEntityId() {
		return _gitUserEntityId;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"latestSHA", getLatestSHA()
		).put(
			"r_gitUserToGitBranches_c_gitUserId", getGitUserEntityId()
		).put(
			"url", getURL()
		);

		Type type = getType();

		if (type != null) {
			jsonObject.put("type", type.getJSONObject());
		}

		return jsonObject;
	}

	@Override
	public GitCommitEntity getLatestGitCommitEntity() {
		String latestSHA = getLatestSHA();

		if (StringUtil.isNullOrEmpty(latestSHA)) {
			return null;
		}

		for (GitCommitEntity gitCommitEntity : getGitCommitEntities()) {
			if (Objects.equals(gitCommitEntity.getSHA(), latestSHA)) {
				return gitCommitEntity;
			}
		}

		return null;
	}

	@Override
	public String getLatestSHA() {
		return _latestSHA;
	}

	@Override
	public String getName() {
		return _getURLGroupValue(getURL(), "branchName");
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
	public String getRepositoryName() {
		return _getURLGroupValue(getURL(), "repositoryName");
	}

	@Override
	public Set<RoutineEntity> getRoutineEntities() {
		return getRelatedEntities(RoutineEntity.class);
	}

	@Override
	public String getShortLatestSHA() {
		String branchSHA = getLatestSHA();

		if (StringUtil.isNullOrEmpty(branchSHA)) {
			return null;
		}

		return branchSHA.substring(0, 7);
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public URL getURL() {
		return _url;
	}

	@Override
	public String getUserName() {
		return _getURLGroupValue(getURL(), "userName");
	}

	@Override
	public void removeGitCommitEntities(
		Set<GitCommitEntity> gitCommitEntities) {

		removeRelatedEntities(gitCommitEntities);
	}

	@Override
	public void removeGitCommitEntity(GitCommitEntity gitCommitEntity) {
		removeRelatedEntity(gitCommitEntity);
	}

	@Override
	public void removeGitPullRequestEntities(
		Set<GitPullRequestEntity> gitPullRequestEntities) {

		removeRelatedEntities(gitPullRequestEntities);
	}

	@Override
	public void removeGitPullRequestEntity(
		GitPullRequestEntity gitPullRequestEntity) {

		removeRelatedEntity(gitPullRequestEntity);
	}

	@Override
	public void removeRoutineEntities(Set<RoutineEntity> routineEntities) {
		removeRelatedEntities(routineEntities);
	}

	@Override
	public void removeRoutineEntity(RoutineEntity routineEntity) {
		removeRelatedEntity(routineEntity);
	}

	@Override
	public void setGitHubClient(GitHubClient gitHubClient) {
		_gitHubClient = gitHubClient;
	}

	@Override
	public void setGitUserEntity(GitUserEntity gitUserEntity) {
		_gitUserEntity = gitUserEntity;

		if (_gitUserEntity == null) {
			_gitUserEntityId = 0;
		}
		else {
			_gitUserEntityId = _gitUserEntity.getId();
		}
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_gitUserEntityId = jsonObject.optLong(
			"r_gitUserToGitBranches_c_gitUserId");
		_latestSHA = jsonObject.optString("latestSHA");
		_url = StringUtil.toURL(jsonObject.getString("url"));
		_type = Type.get(jsonObject.getJSONObject("type"));
	}

	@Override
	public void setLatestSHA(String latestSHA) {
		synchronized (_propertiesFiles) {
			_latestSHA = latestSHA;

			_propertiesFiles.clear();
		}
	}

	@Override
	public void setURL(URL url) {
		_url = url;
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

	private GitHubClient _gitHubClient;
	private GitUserEntity _gitUserEntity;
	private long _gitUserEntityId;
	private String _latestSHA;
	private final Map<String, Properties> _propertiesFiles = new HashMap<>();
	private Type _type;
	private URL _url;

}