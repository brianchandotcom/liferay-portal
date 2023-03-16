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

package com.liferay.jethr0.gitbranch;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitBranchFactory {

	public static GitBranch newGitBranch(JSONObject jsonObject) {
		long id = jsonObject.getLong("id");

		GitBranch gitBranch;

		synchronized (_gitBranches) {
			if (_gitBranches.containsKey(id)) {
				return _gitBranches.get(id);
			}

			gitBranch = new DefaultGitBranch(jsonObject);

			_gitBranches.put(gitBranch.getId(), gitBranch);
		}

		return gitBranch;
	}

	public static void removeGitBranch(GitBranch gitBranch) {
		if (gitBranch == null) {
			return;
		}

		synchronized (_gitBranches) {
			_gitBranches.remove(gitBranch.getId());
		}
	}

	private static final Map<Long, GitBranch> _gitBranches =
		Collections.synchronizedMap(new HashMap<>());

}