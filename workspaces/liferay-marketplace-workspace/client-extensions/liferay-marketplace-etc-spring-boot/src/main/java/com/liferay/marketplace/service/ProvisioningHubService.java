/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.model.AnalyticsForm;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Caleb Hall
 */
@Component
public class ProvisioningHubService extends BaseService {

	public void provision(Order order, ProductPurchase productPurchase)
		throws Exception {

		if (1 == 1) {
			return;
		}

		Product product = productPurchase.getProduct();

		if (Objects.equals(product.getName(), "Liferay Data Platform")) {
			Account account = _koroneikiService.getKoroneikiAccount(
				productPurchase.getAccountKey());

			Map<String, String> accountProperties = account.getProperties();

			AnalyticsForm analyticsForm = new AnalyticsForm(
				account.getName(), account.getKey(),
				accountProperties.get(
					"securityContactEmailAddress"
				).split(
					","
				),
				accountProperties.get("ldpWorkspaceName"),
				_getServerLocation(accountProperties.get("dataCenterLocation")),
				accountProperties.get("securityContactEmailAddress"));

			_analyticsService.provision(analyticsForm, order.getId());
		}
	}

	private String _getServerLocation(String dataCenterLocation) {
		if (Objects.equals(dataCenterLocation, "asiasouth1")) {
			return "asia-south1-ac5-c1";
		}

		if (Objects.equals(dataCenterLocation, "europewest2")) {
			return "europe-west2-ac2-c1";
		}

		if (Objects.equals(dataCenterLocation, "europewest3")) {
			return "europe-west3-ac3-c1";
		}

		if (Objects.equals(dataCenterLocation, "southamericaeast1")) {
			return "southamerica-east1-ac1-c1";
		}

		if (Objects.equals(dataCenterLocation, "uswest1")) {
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