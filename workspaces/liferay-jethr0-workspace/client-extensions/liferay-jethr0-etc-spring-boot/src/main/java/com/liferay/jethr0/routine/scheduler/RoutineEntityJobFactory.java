/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine.scheduler;

import org.quartz.spi.TriggerFiredBundle;

import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @author Michael Hashimoto
 */
@Component
public class RoutineEntityJobFactory extends AdaptableJobFactory {

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) {
		return new RoutineEntityJob();
	}

}