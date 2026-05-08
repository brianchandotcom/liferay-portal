/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.service.ProvisioningHubService;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 */
@RequestMapping("/object/action/ai/hub/provisioning")
@RestController
public class ObjectActionAIHubProvisioningRestController
	extends BaseRestController {

	@PostMapping
	public void post(@AuthenticationPrincipal Jwt jwt, @RequestBody String json)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("POST AI Hub provisioning " + json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject commerceOrderJSONObject = jsonObject.getJSONObject(
			"commerceOrder");

		if (commerceOrderJSONObject.getInt("orderStatus") ==
				MarketplaceConstants.ORDER_STATUS_COMPLETED) {

			return;
		}

		Order order = _marketplaceService.getOrder(
			commerceOrderJSONObject.getLong("id"));

		_koroneikiService.postAccountProductPurchases(
			jwt, "3 Months Limited Beta", order);

		JSONObject orderMetadataJSONObject = MarketplaceUtil.getOrderMetadata(
			order);

		JSONObject aiHubFormJSONObject = orderMetadataJSONObject.getJSONObject(
			"aiHubForm");

		Account account = order.getAccount();

		orderMetadataJSONObject.put(
			"aiHub",
			_provisioningHubService.provisionAIHub(
				new JSONObject(
				).put(
					"accountExternalReferenceCode",
					order.getAccountExternalReferenceCode()
				).put(
					"accountName",
					aiHubFormJSONObject.optString(
						"aiHubAccountName", account.getName())
				).put(
					"userAccounts",
					new JSONArray(
					).put(
						new JSONObject(
						).put(
							"emailAddress",
							aiHubFormJSONObject.optString(
								"administratorEmailAddress",
								order.getCreatorEmailAddress())
						)
					)
				)));

		_marketplaceService.updateOrder(
			HashMapBuilder.put(
				"order-metadata", orderMetadataJSONObject.toString()
			).build(),
			order.getId(), MarketplaceConstants.ORDER_STATUS_COMPLETED);
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionAIHubProvisioningRestController.class);

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private MarketplaceService _marketplaceService;

	@Autowired
	private ProvisioningHubService _provisioningHubService;

}