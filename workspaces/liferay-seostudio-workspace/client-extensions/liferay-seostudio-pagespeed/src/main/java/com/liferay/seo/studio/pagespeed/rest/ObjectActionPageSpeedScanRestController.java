/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.rest;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.seo.studio.pagespeed.PageSpeedConstants;
import com.liferay.seo.studio.pagespeed.scanner.LiferayHeadlessClient;
import com.liferay.seo.studio.pagespeed.scanner.PageSpeedScanner;

import java.net.http.HttpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kiana Suetani
 */
@RequestMapping("/object/action/pagespeed/scan")
@RestController
public class ObjectActionPageSpeedScanRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(@RequestBody String json) {
		if (_log.isDebugEnabled()) {
			_log.debug(json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		long domainId = valuesJSONObject.optLong(
			"r_seoStudioDomainToSEOStudioScans_seoStudioDomainId", 0);

		if (domainId <= 0) {
			return new ResponseEntity<>(
				"Scan is missing a domain", HttpStatus.BAD_REQUEST);
		}

		String portalURL = lxcDXPServerProtocol + "://" + lxcDXPMainDomain;

		try {
			String authToken = _liferayOAuth2AccessTokenManager.getTokenValue(
				PageSpeedConstants.OAHS_EXTERNAL_REFERENCE_CODE);

			LiferayHeadlessClient liferayHeadlessClient =
				new LiferayHeadlessClient(authToken, _httpClient, portalURL);

			String[] domainInfo = liferayHeadlessClient.getDomainInfo(domainId);

			String hostname = domainInfo[0];
			String apiKey = domainInfo[1];

			// Dispatch scan to background thread so the object action
			// does not timeout waiting for the full scan to complete

			_pageSpeedScanner.scanAsync(
				apiKey, hostname, _httpClient, _liferayOAuth2AccessTokenManager,
				portalURL, () -> null, _DEFAULT_STRATEGY,
				() -> {
					if (_log.isInfoEnabled()) {
						_log.info("PageSpeed scan completed");
					}
				},
				errorMessage -> _log.error(
					"PageSpeed scan failed: " + errorMessage));

			return ResponseEntity.ok(
			).build();
		}
		catch (Exception exception) {
			if (exception instanceof InterruptedException) {
				Thread.currentThread(
				).interrupt();
			}

			_log.error("Unable to start PageSpeed scan", exception);

			return new ResponseEntity<>(
				"Unable to start PageSpeed scan",
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private static final String _DEFAULT_STRATEGY = "DESKTOP";

	private static final Log _log = LogFactory.getLog(
		ObjectActionPageSpeedScanRestController.class);

	private static final HttpClient _httpClient = HttpClient.newHttpClient();

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private PageSpeedScanner _pageSpeedScanner;

}