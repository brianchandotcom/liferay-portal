/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

		if (Objects.equals(product.getName(), "Liferay Data Platform")) {
			_provisionLDP(koroneikiAccount, order);
		}
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
						"Missing properties to provision LDP. Account ",
						koroneikiAccount.getKey(), ", properties: ",
						properties));
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
				"ownerEmailAddress",
				properties.get("securityContactEmailAddress")
			).put(
				"serverLocation",
				_getServerLocation(properties.get("dataCenterLocation"))
			));

		_marketplaceService.completeOrder(
			HashMapBuilder.put(
				"order-metadata",
				new JSONObject(
					GetterUtil.get(
						order.getCustomFields(
						).get(
							"order-metadata"
						),
						"{}")
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

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private MarketplaceService _marketplaceService;

}