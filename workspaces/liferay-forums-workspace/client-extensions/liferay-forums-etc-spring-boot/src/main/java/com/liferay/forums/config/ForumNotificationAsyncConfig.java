/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.forums.config;

import com.liferay.petra.string.StringBundler;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Roselaine Marques
 * @author Neil Griffin
 */
@Configuration
public class ForumNotificationAsyncConfig {

	@Bean
	public ThreadPoolTaskExecutor forumNotificationExecutor(
		@Value("${forums.notification.async.core.size:2}") int coreSize,
		@Value("${forums.notification.async.max.size:8}") int maxSize,
		@Value("${forums.notification.async.queue.capacity:100}") int
			queueCapacity,
		@Value("${forums.notification.async.await.termination.seconds:10}") int
			awaitTerminationSeconds) {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(coreSize);
		executor.setMaxPoolSize(maxSize);

		executor.setQueueCapacity(queueCapacity);

		executor.setThreadNamePrefix("forum-notify-");

		executor.setRejectedExecutionHandler(
			new ThreadPoolExecutor.CallerRunsPolicy());

		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(awaitTerminationSeconds);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Forum notification executor: core=", coreSize, ", max=",
					maxSize, ", queue=", queueCapacity));
		}

		return executor;
	}

	private static final Log _log = LogFactory.getLog(
		ForumNotificationAsyncConfig.class);

}