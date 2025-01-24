/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jenny Chen
 */
@Configuration
public class CacheConfiguration {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(
			"affectedVersions", "issue", "issues");

		caffeineCacheManager.setCaffeine(
			Caffeine.newBuilder(
			).maximumSize(
				1000
			));

		return caffeineCacheManager;
	}

}