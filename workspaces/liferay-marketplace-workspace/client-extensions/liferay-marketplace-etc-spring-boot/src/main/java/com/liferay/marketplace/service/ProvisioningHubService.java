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

import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Caleb Hall
 */
@Component
public class ProvisioningHubService extends BaseService {

	public void provision(Order order, ProductPurchase productPurchase)
		throws Exception {

		Product product = productPurchase.getProduct();

		if (Objects.equals(product.getName(), "Liferay Data Platform")) {
			Account koroneikiAccount = _koroneikiService.getKoroneikiAccount(
				productPurchase.getAccountKey());

			Map<String, String> properties = koroneikiAccount.getProperties();

			String securityContactEmailAddress = properties.get(
				"securityContactEmailAddress");

			JSONObject jsonObject = new JSONObject(
			).put(
				"corpProjectName", koroneikiAccount.getName()
			).put(
				"corpProjectUuid", koroneikiAccount.getKey()
			).put(
				"incidentReportEmailAddresses",
				securityContactEmailAddress.split(",")
			).put(
				"name", properties.get("ldpWorkspaceName")
			).put(
				"ownerEmailAddress", order.getCreatorEmailAddress()
			).put(
				"serverLocation",
				_getServerLocation(properties.get("dataCenterLocation"))
			);

			_analyticsService.provision(jsonObject, order.getId());
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

		throw new IllegalArgumentException(
			"Invalid data center location: " + dataCenterLocation);
	}

	@Autowired
	private AnalyticsService _analyticsService;

	@Autowired
	private KoroneikiService _koroneikiService;

}