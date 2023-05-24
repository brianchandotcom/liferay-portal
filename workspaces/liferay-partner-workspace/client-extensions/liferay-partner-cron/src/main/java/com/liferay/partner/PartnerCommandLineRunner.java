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

import com.liferay.headless.admin.list.type.client.dto.v1_0.ListTypeEntry;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.partner.dto.Activity;
import com.liferay.partner.service.ActivityService;
import com.liferay.petra.function.transform.TransformUtil;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collection;

import org.json.JSONArray;

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
		ZonedDateTime nowZonedDateTime = ZonedDateTime.ofInstant(
			Instant.now(), ZoneOffset.UTC);

		Page<Activity> activitiesPage = _activityService.getEntriesPage(
			null,
			"endDate lt " +
				nowZonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
			Pagination.of(1, -1), null);

		if (activitiesPage.getTotalCount() > 0) {
			Collection<Activity> activities = TransformUtil.transform(
				activitiesPage.getItems(),
				activity -> {
					ListTypeEntry expiredListTypeEntry = new ListTypeEntry() {
						{
							setKey("expired");
							setName("Expired");
						}
					};

					activity.setActivityStatus(expiredListTypeEntry);

					return activity;
				});

			System.out.println(new JSONArray(activities));
		}
	}

	@Autowired
	private ActivityService _activityService;

}