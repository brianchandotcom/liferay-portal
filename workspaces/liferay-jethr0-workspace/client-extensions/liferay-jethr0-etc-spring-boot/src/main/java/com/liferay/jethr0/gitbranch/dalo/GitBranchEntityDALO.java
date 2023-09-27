/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.gitbranch.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.gitbranch.GitBranchEntity;
import com.liferay.jethr0.gitbranch.GitBranchEntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitBranchEntityDALO extends BaseEntityDALO<GitBranchEntity> {

	@Override
	protected EntityFactory<GitBranchEntity> getEntityFactory() {
		return _gitBranchEntityFactory;
	}

	@Autowired
	private GitBranchEntityFactory _gitBranchEntityFactory;

}