/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.comparator;

import com.liferay.jethr0.job.ProjectEntity;
import com.liferay.jethr0.job.prioritizer.ProjectPrioritizerEntity;

import java.util.Date;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class StartDateProjectComparatorEntity
	extends BaseProjectComparatorEntity {

	public StartDateProjectComparatorEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	public StartDateProjectComparatorEntity(
		ProjectPrioritizerEntity projectPrioritizerEntity,
		JSONObject jsonObject) {

		super(projectPrioritizerEntity, jsonObject);
	}

	@Override
	public int compare(
		ProjectEntity projectEntity1, ProjectEntity projectEntity2) {

		Date startDate1 = projectEntity1.getStartDate();
		Date startDate2 = projectEntity2.getStartDate();

		if ((startDate1 == null) && (startDate2 == null)) {
			return 0;
		}

		if (startDate1 == null) {
			return 1;
		}

		if (startDate2 == null) {
			return -1;
		}

		return startDate1.compareTo(startDate2);
	}

}