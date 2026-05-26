/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Caleb Hall
 */
@Component
public class ProvisioningHubService extends BaseService {

	public void provision(
			Account koroneikiAccount, Order order,
			ProductPurchase productPurchase)
		throws Exception {

		Product product = productPurchase.getProduct();

		if (Objects.equals(
				product.getName(), "Liferay Data Platform (Private Beta)")) {

			_provisionLDP(koroneikiAccount, order);
		}
	}

	public String provisionAIHub(JSONObject jsonObject) {
		String response = post(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"external-ai-hub"),
			jsonObject.toString(),
			UriComponentsBuilder.fromUriString(
				_externalAIHubHomePageURL.toString()
			).path(
				"/o/ai-hub/v1.0/provisioning"
			).build(
			).toUri());

		if (_log.isInfoEnabled()) {
			_log.info("AI Hub provisioned " + jsonObject);
		}

		return response;
	}

	private Contact _getContact(String key) throws Exception {
		Page<Contact> contactsPage = _koroneikiService.getContactsPage(
			key, null);

		for (Contact contact : contactsPage.getItems()) {
			for (ContactRole contactRole : contact.getContactRoles()) {
				if (Objects.equals(
						contactRole.getName(), "LDP Administrator")) {

					return contact;
				}
			}
		}

		return null;
	}

	private String _getServerLocation(String dataCenterLocation) {
		if (Objects.equals(dataCenterLocation, "asia-south1")) {
			return "asia-south1-ac5-c1";
		}

		if (Objects.equals(dataCenterLocation, "europe-west2")) {
			return "europe-west2-ac2-c1";
		}

		if (Objects.equals(dataCenterLocation, "europe-west3")) {
			return "europe-west3-ac3-c1";
		}

		if (Objects.equals(dataCenterLocation, "southamerica-east1")) {
			return "southamerica-east1-ac1-c1";
		}

		if (Objects.equals(dataCenterLocation, "us-west1")) {
			return "us-west1-ac4-c1";
		}

		return "us-west1-s2-c1";
	}

	private void _provisionLDP(Account koroneikiAccount, Order order)
		throws Exception {

		Map<String, String> properties = koroneikiAccount.getProperties();

		if (Validator.isNull(properties.get("dataCenterLocation")) ||
			Validator.isNull(properties.get("ldpWorkspaceName"))) {

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Missing properties to provision LDP for account ",
						koroneikiAccount.getKey(), ": ", properties));
			}

			return;
		}

		String securityContactEmailAddress = properties.get(
			"securityContactEmailAddress");

		JSONArray incidentReportEmailAddressesJSONArray = new JSONArray();

		if (Validator.isNotNull(securityContactEmailAddress)) {
			incidentReportEmailAddressesJSONArray = new JSONArray(
				securityContactEmailAddress.split(","));
		}

		Contact contact = _getContact(koroneikiAccount.getKey());

		String ownerEmailAddress = securityContactEmailAddress;

		if (contact != null) {
			ownerEmailAddress = contact.getEmailAddress();
		}

		String analyticsProject = _analyticsService.provision(
			new JSONObject(
			).put(
				"corpProjectName", koroneikiAccount.getName()
			).put(
				"corpProjectUuid", koroneikiAccount.getKey()
			).put(
				"incidentReportEmailAddresses",
				incidentReportEmailAddressesJSONArray
			).put(
				"name", properties.get("ldpWorkspaceName")
			).put(
				"ownerEmailAddress", ownerEmailAddress
			).put(
				"serverLocation",
				_getServerLocation(properties.get("dataCenterLocation"))
			));

		_marketplaceService.completeOrder(
			HashMapBuilder.put(
				"order-metadata",
				MarketplaceUtil.getOrderMetadata(
					order
				).put(
					"analyticsProject", new JSONObject(analyticsProject)
				).toString()
			).build(),
			order.getId(), order.getPaymentStatus());
	}

	private static final Log _log = LogFactory.getLog(
		ProvisioningHubService.class);

	@Autowired
	private AnalyticsService _analyticsService;

	@Value("${external.ai.hub.oauth2.headless.server.home.page.url}")
	private URL _externalAIHubHomePageURL;

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private MarketplaceService _marketplaceService;

}