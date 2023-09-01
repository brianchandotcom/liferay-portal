/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.JobEntityFactory;
import com.liferay.jethr0.util.StringUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobEntityDALO extends BaseEntityDALO<JobEntity> {

	public Set<JobEntity> getJobsByState(JobEntity.State... states) {
		Set<JobEntity> jobEntities = new HashSet<>();

		String filter = null;

		if (states.length > 0) {
			Set<String> stateQueries = new HashSet<>();

			for (JobEntity.State state : states) {
				stateQueries.add("(state eq '" + state.getKey() + "')");
			}

			filter = StringUtil.join(" or ", stateQueries);
		}

		List<JobEntity.State> statesList = Arrays.asList(states);

		for (JobEntity jobEntity : getAll(filter, null)) {
			if (!statesList.contains(jobEntity.getState())) {
				continue;
			}

			jobEntities.add(jobEntity);
		}

		return jobEntities;
	}

	@Override
	protected EntityFactory<JobEntity> getEntityFactory() {
		return _jobEntityFactory;
	}

	@Autowired
	private JobEntityFactory _jobEntityFactory;

}