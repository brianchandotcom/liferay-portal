/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.git.dalo.GitUserEntityDALO;
import com.liferay.jethr0.git.user.GitUserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitUserEntityRepository
	extends BaseEntityRepository<GitUserEntity> {

	@Override
	public GitUserEntityDALO getEntityDALO() {
		return _gitUserEntityDALO;
	}

	@Autowired
	private GitUserEntityDALO _gitUserEntityDALO;

}