/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.queue;

import com.liferay.jethr0.entity.Entity;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public interface JobQueueOrderEntity extends Entity {

	public List<Long> getJobIds();

	public void setJobIds(List<Long> jobIDs);

}