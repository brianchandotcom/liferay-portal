/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Ryan Schuhler
 */
@Component
public class VersionPicklistsService extends BaseService {

	@Scheduled(cron = "${liferay.customer.version.picklists.cron}")
	public void scheduledPicklistsUpdate() throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Updating version picklists");
		}

		JSONArray releasesJSONArray = new JSONArray(
			get(StringPool.BLANK, _liferayCustomerVersionPicklistsReleasesURL));

		Map<String, List<String>> versionsMap = _extractVersionsMap(
			releasesJSONArray);

		List<String> dxpMajorVersionsMap = versionsMap.get("dxpMajor");
		List<String> dxpMinorVersionsMap = versionsMap.get("dxpMinor");
		List<String> portalMajorVersionsMap = versionsMap.get("portalMajor");
		List<String> portalMinorVersionsMap = versionsMap.get("portalMinor");

		List<String> dxpMinorVersionsMapAndPortalMajorVersionsMap =
			new ArrayList<>();

		dxpMinorVersionsMapAndPortalMajorVersionsMap.addAll(
			dxpMinorVersionsMap);
		dxpMinorVersionsMapAndPortalMajorVersionsMap.addAll(
			portalMajorVersionsMap);

		_updateListTypeDefinition(
			_liferayCustomerVersionPicklistsDXPMajorERC, "DXP Major Version",
			dxpMajorVersionsMap);

		_updateListTypeDefinition(
			_liferayCustomerVersionPicklistsDXPMinorERC, "DXP Minor Version",
			dxpMinorVersionsMap);

		_updateListTypeDefinition(
			_liferayCustomerVersionPicklistsDXPMinorPortalMajorERC,
			"DXP Minor Version and Portal Major Version",
			dxpMinorVersionsMapAndPortalMajorVersionsMap);

		_updateListTypeDefinition(
			_liferayCustomerVersionPicklistsPortalMajorERC,
			"Portal Major Version", portalMajorVersionsMap);

		_updateListTypeDefinition(
			_liferayCustomerVersionPicklistsPortalMinorERC,
			"Portal Minor Version", portalMinorVersionsMap);
	}

	private void _addVersion(
		Map<String, List<String>> versionsMap, String key, String version) {

		List<String> versions = versionsMap.get(key);

		if ((versions != null) && !versions.contains(version)) {
			versions.add(version);

			Collections.sort(versions);
		}
	}

	private Map<String, List<String>> _extractVersionsMap(
		JSONArray releasesJSONArray) {

		Map<String, List<String>> versionsMap =
			HashMapBuilder.<String, List<String>>put(
				"dxpMajor", new ArrayList<>()
			).put(
				"dxpMinor", new ArrayList<>()
			).put(
				"portalMajor", new ArrayList<>()
			).put(
				"portalMinor", new ArrayList<>()
			).build();

		for (int i = 0; i < releasesJSONArray.length(); i++) {
			JSONObject releaseJSONObject = releasesJSONArray.getJSONObject(i);

			String product = releaseJSONObject.getString("product");
			String productGroupVersion = releaseJSONObject.getString(
				"productGroupVersion");

			if (Validator.isNull(product) ||
				Validator.isNull(productGroupVersion)) {

				continue;
			}

			String productVersion = releaseJSONObject.getString(
				"productVersion");

			if (product.equals("dxp")) {
				productGroupVersion =
					StringUtil.toUpperCase(product) + StringPool.SPACE +
						StringUtil.toUpperCase(productGroupVersion);
			}
			else {
				productGroupVersion =
					StringUtil.getTitleCase(product, false) + StringPool.SPACE +
						productGroupVersion;
			}

			_addVersion(versionsMap, product + "Major", productGroupVersion);
			_addVersion(versionsMap, product + "Minor", productVersion);
		}

		return versionsMap;
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	private void _updateListTypeDefinition(
			String externalReferenceCode, String name, List<String> values)
		throws Exception {

		JSONObject listTypeDefinitionJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				"/o/headless-admin-list-type/v1.0/list-type-definitions" +
					"/by-external-reference-code/" + externalReferenceCode));

		JSONArray listTypeEntriesJSONArray = new JSONArray();

		for (String value : values) {
			if (Validator.isNull(value)) {
				continue;
			}

			listTypeEntriesJSONArray.put(
				new JSONObject(
				).put(
					"externalReferenceCode",
					value.toUpperCase(
					).replaceAll(
						"[^A-Z0-9]", "_"
					)
				).put(
					"key",
					value.toLowerCase(
					).replaceAll(
						"[^a-z0-9]", ""
					)
				).put(
					"name", value
				).put(
					"name_i18n",
					new JSONObject(
					).put(
						"en-US", value
					)
				));
		}

		put(
			_getAuthorization(),
			new JSONObject(
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"listTypeEntries", listTypeEntriesJSONArray
			).put(
				"name", name
			).put(
				"name_i18n",
				new JSONObject(
				).put(
					"en-US", name
				)
			).toString(),
			"/o/headless-admin-list-type/v1.0/list-type-definitions/" +
				listTypeDefinitionJSONObject.getInt("id"));

		if (_log.isInfoEnabled()) {
			_log.info("Updated list type definition " + externalReferenceCode);
		}
	}

	private static final Log _log = LogFactory.getLog(
		VersionPicklistsService.class);

	@Value("${liferay.customer.version.picklists.dxp.major.erc}")
	private String _liferayCustomerVersionPicklistsDXPMajorERC;

	@Value("${liferay.customer.version.picklists.dxp.minor.erc}")
	private String _liferayCustomerVersionPicklistsDXPMinorERC;

	@Value("${liferay.customer.version.picklists.dxp.minor.portal.major.erc}")
	private String _liferayCustomerVersionPicklistsDXPMinorPortalMajorERC;

	@Value("${liferay.customer.version.picklists.portal.major.erc}")
	private String _liferayCustomerVersionPicklistsPortalMajorERC;

	@Value("${liferay.customer.version.picklists.portal.minor.erc}")
	private String _liferayCustomerVersionPicklistsPortalMinorERC;

	@Value("${liferay.customer.version.picklists.releases.url}")
	private String _liferayCustomerVersionPicklistsReleasesURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}