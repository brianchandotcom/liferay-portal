/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.petra.string.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author José Abelenda
 */
@RequestMapping("/object/action/crawler")
@RestController
public class ObjectActionCrawlerRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isDebugEnabled()) {
			_log.debug(json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		Path path = null;

		try {
			path = Files.createTempFile("crawler-config", ".yml");

			String crawlerConfig = null;

			try (InputStream inputStream = getClass().getResourceAsStream(
					"/crawler-config-template.yml")) {

				crawlerConfig = new String(
					inputStream.readAllBytes(), StandardCharsets.UTF_8);
			}

			String seedUrl = valuesJSONObject.getString("url");

			URI seedURI = URI.create(seedUrl);

			String domainUrl =
				seedURI.getScheme() + "://" + seedURI.getAuthority();

			crawlerConfig = _replace(
				Map.of(
					"[$CRAWLER_DOMAIN_URL$]", domainUrl,
					"[$CRAWLER_ELASTICSEARCH_HOST$]", _crawlerElasticsearchHost,
					"[$CRAWLER_ELASTICSEARCH_PIPELINE$]",
					_crawlerElasticsearchPipeline,
					"[$CRAWLER_ELASTICSEARCH_PORT$]",
					String.valueOf(_crawlerElasticsearchPort),
					"[$CRAWLER_MAX_CRAWL_DEPTH$]",
					String.valueOf(_crawlerMaxCrawlDepth),
					"[$CRAWLER_MAX_DURATION$]",
					String.valueOf(_crawlerMaxDuration),
					"[$CRAWLER_OUTPUT_INDEX$]",
					valuesJSONObject.getString("indexName"),
					"[$CRAWLER_SEED_URL$]", seedUrl,
					"[$CRAWLER_URL_QUEUE_SIZE_LIMIT$]",
					String.valueOf(_crawlerUrlQueueSizeLimit)),
				crawlerConfig);

			Files.writeString(path, crawlerConfig, StandardCharsets.UTF_8);

			ProcessBuilder processBuilder = new ProcessBuilder(
				"bundle", "exec", "bin/crawler", "crawl",
				path.toAbsolutePath(
				).toString());

			processBuilder.directory(new File("/opt/liferay/crawler"));

			processBuilder.redirectErrorStream(true);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Launching crawler: " +
						String.join(" ", processBuilder.command()));
			}

			Process process = processBuilder.start();

			try (BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
						process.getInputStream(), StandardCharsets.UTF_8))) {

				String line;

				while ((line = bufferedReader.readLine()) != null) {
					if (_log.isInfoEnabled()) {
						_log.info("[crawler] " + line);
					}
				}
			}

			int exitCode = process.waitFor();

			if (_log.isInfoEnabled()) {
				_log.info("Crawler finished with exit code " + exitCode);
			}

			if (exitCode == 0) {
				return ResponseEntity.ok(
				).build();
			}

			return new ResponseEntity<>(
				"Crawler finished with exit code " + exitCode,
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception exception) {
			if (exception instanceof InterruptedException) {
				Thread.currentThread(
				).interrupt();
			}

			_log.error("Crawler execution failed", exception);

			return new ResponseEntity<>(
				"Crawler execution failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally {
			if (path != null) {
				try {
					Files.deleteIfExists(path);
				}
				catch (Exception exception) {
					_log.error(
						"Unable to delete temporary crawler config", exception);
				}
			}
		}
	}

	private String _replace(Map<String, String> map, String string) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			string = StringUtil.replace(
				string, entry.getKey(), entry.getValue());
		}

		return string;
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionCrawlerRestController.class);

	@Value("${liferay.ai.hub.crawler.elasticsearch.host}")
	private String _crawlerElasticsearchHost;

	@Value("${liferay.ai.hub.crawler.elasticsearch.pipeline}")
	private String _crawlerElasticsearchPipeline;

	@Value("${liferay.ai.hub.crawler.elasticsearch.port}")
	private int _crawlerElasticsearchPort;

	@Value("${liferay.ai.hub.crawler.max.crawl.depth}")
	private int _crawlerMaxCrawlDepth;

	@Value("${liferay.ai.hub.crawler.max.duration}")
	private int _crawlerMaxDuration;

	@Value("${liferay.ai.hub.crawler.url.queue.size.limit}")
	private int _crawlerUrlQueueSizeLimit;

}