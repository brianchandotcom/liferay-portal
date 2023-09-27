/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.dalo;

import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.bui1d.parameter.BuildParameterEntityFactory;
import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildParameterEntityDALO
	extends BaseEntityDALO<BuildParameterEntity> {

	@Override
	protected EntityFactory<BuildParameterEntity> getEntityFactory() {
		return _buildParameterEntityFactory;
	}

	@Autowired
	private BuildParameterEntityFactory _buildParameterEntityFactory;

}