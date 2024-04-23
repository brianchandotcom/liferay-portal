/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.scheduler;

import org.quartz.Job;

/**
 * @author Michael Hashimoto
 */
public interface RoutineEntityJob extends Job {

	public RoutineEntityJobFactory getRoutineEntityJobFactory();

	public void setRoutineEntityJobFactory(
		RoutineEntityJobFactory routineEntityJobFactory);

}