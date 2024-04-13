/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.util;

import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.util.Date;

/**
 * @author Michael Hashimoto
 */
public class JobUtil {

	public static void updateJobEntityName(
		JobEntityRepository jobEntityRepository, JobEntity jobEntity) {

		if (jobEntity == null) {
			return;
		}

		String jobName = jobEntity.getName();

		if (jobName == null) {
			return;
		}

		if (jobName.contains("$(create_date)")) {
			Date createdDate = jobEntity.getCreatedDate();

			if (createdDate != null) {
				jobName = jobName.replaceAll(
					"\\$\\(create_date\\)", String.valueOf(createdDate));
			}
		}

		if (!jobName.equals(jobEntity.getName())) {
			jobEntity.setName(jobName);

			jobEntityRepository.update(jobEntity);
		}
	}

}