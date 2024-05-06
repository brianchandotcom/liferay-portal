/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntityFactory;
import com.liferay.jethr0.util.StringUtil;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsNodeEntityDALO extends BaseEntityDALO<JenkinsNodeEntity> {

	public JenkinsNodeEntity getByName(String name) {
		if (StringUtil.isNullOrEmpty(name)) {
			return null;
		}

		String filterString = "name eq '" + name + "'";

		for (JenkinsNodeEntity jenkinsNodeEntity :
				getAll(filterString, null, null)) {

			if (Objects.equals(jenkinsNodeEntity.getName(), name)) {
				return jenkinsNodeEntity;
			}
		}

		return null;
	}

	@Override
	public EntityFactory<JenkinsNodeEntity> getEntityFactory() {
		return _jenkinsNodeEntityFactory;
	}

	@Autowired
	private JenkinsNodeEntityFactory _jenkinsNodeEntityFactory;

}