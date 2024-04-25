/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.branch;

import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.git.commit.GitCommitEntity;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface GitBranchEntity extends Entity {

	public void addGitCommitEntities(Set<GitCommitEntity> gitCommitEntities);

	public void addGitCommitEntity(GitCommitEntity gitCommitEntity);

	public String getFileContent(String filePath);

	public Set<GitCommitEntity> getGitCommitEntities();

	public String getLatestSHA();

	public String getName();

	public Properties getProperties(String propertiesFilePath)
		throws IOException;

	public String getRepositoryName();

	public String getShortLatestSHA();

	public Type getType();

	public URL getURL();

	public String getUserName();

	public void removeGitCommitEntities(Set<GitCommitEntity> gitCommitEntities);

	public void removeGitCommitEntity(GitCommitEntity gitCommitEntity);

	public void setGitHubClient(GitHubClient gitHubClient);

	public void setLatestSHA(String latestSHA);

	public void setURL(URL url);

	public static enum Type {

		DEFAULT("default"), UPSTREAM("upstream");

		public static Type get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static Type getByKey(String key) {
			return _types.get(key);
		}

		public JSONObject getJSONObject() {
			return new JSONObject("{\"key\": \"" + getKey() + "\"}");
		}

		public String getKey() {
			return _key;
		}

		private Type(String key) {
			_key = key;
		}

		private static final Map<String, Type> _types = new HashMap<>();

		static {
			for (Type type : values()) {
				_types.put(type.getKey(), type);
			}
		}

		private final String _key;

	}

}