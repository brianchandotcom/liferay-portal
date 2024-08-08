/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.GitBranchEntityFactory;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitBranchEntityDALO extends BaseEntityDALO<GitBranchEntity> {

	public Set<GitBranchEntity> getByType(GitBranchEntity.Type... types) {
		if (ArrayUtil.isEmpty(types)) {
			return Collections.emptySet();
		}

		Set<GitBranchEntity> gitBranchEntities = new HashSet<>();

		Set<String> typeQueries = new HashSet<>();

		for (GitBranchEntity.Type type : types) {
			typeQueries.add("(type eq '" + type.getKey() + "')");
		}

		String filterString = StringUtil.join(" or ", typeQueries);

		List<GitBranchEntity.Type> typesList = Arrays.asList(types);

		for (GitBranchEntity gitBranchEntity :
				getAll(filterString, null, null)) {

			if (!typesList.contains(gitBranchEntity.getType())) {
				continue;
			}

			gitBranchEntities.add(gitBranchEntity);
		}

		return gitBranchEntities;
	}

	@Override
	public EntityFactory<GitBranchEntity> getEntityFactory() {
		return _gitBranchEntityFactory;
	}

	@Autowired
	private GitBranchEntityFactory _gitBranchEntityFactory;

}