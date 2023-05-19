/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.partner;

import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.partner.dto.Activity;
import com.liferay.partner.service.ActivityService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Jair Medeiros
 */
@Component
public class PartnerCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		if (_log.isInfoEnabled()) {
			Page<Activity> activitiesPage = _activityService.getEntriesPage(
				null, null, null, null);

			_log.info("Activities: " + activitiesPage);
		}
	}

	private static final Log _log = LogFactory.getLog(
		PartnerCommandLineRunner.class);

	@Autowired
	private ActivityService _activityService;

}