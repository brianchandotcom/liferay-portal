/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.pubsub;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceList;
import com.liferay.one.model.SalesforcePricebookEntry;
import com.liferay.one.model.SalesforceProduct2;
import com.liferay.one.service.CommercePriceEntryService;
import com.liferay.one.service.CommercePriceListService;
import com.liferay.one.service.CommerceProductService;
import com.liferay.one.service.CommerceSkuService;
import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.subscriber.BasePubsubSubscriber;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Kyle Bischof
 */
@Component
public class SalesforceObjectSubscriber extends BasePubsubSubscriber {

	@Override
	protected String getProjectId() {
		return _projectId;
	}

	@Override
	protected String getSubscriptionName() {
		return _subscription;
	}

	@Override
	protected boolean isAutoCreateTopic() {
		return false;
	}

	@Override
	protected void receive(Message message) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Parsing message: " + message.getPayload());
		}

		try {
			JSONObject jsonObject = new JSONObject(message.getPayload());

			String action = jsonObject.getString("action");
			String salesforceObjectName = jsonObject.getString(
				"salesforceObjectName");

			JSONArray recordsJSONArray = jsonObject.getJSONArray("records");

			for (int i = 0; i < recordsJSONArray.length(); i++) {
				JSONObject recordJSONObject = recordsJSONArray.getJSONObject(i);

				if (Objects.equals(salesforceObjectName, "PricebookEntry")) {
					_processPricebookEntry(action, recordJSONObject);
				}
				else if (Objects.equals(salesforceObjectName, "Product2")) {
					_processProduct2(action, recordJSONObject);
				}
				else if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to handle Salesforce object " +
							salesforceObjectName);
				}
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to process Salesforce message " + message.getPayload(),
				exception);
		}
	}

	private void _processPricebookEntry(
			String action, JSONObject recordJSONObject)
		throws Exception {

		SalesforcePricebookEntry salesforcePricebookEntry =
			new SalesforcePricebookEntry(recordJSONObject);

		if (Objects.equals(action, "delete")) {
			_commercePriceEntryService.deletePriceEntry(
				salesforcePricebookEntry.getId());

			return;
		}

		String priceListExternalReferenceCode =
			"SALESFORCE_PRICE_LIST_" +
				salesforcePricebookEntry.getCurrencyIsoCode();

		PriceList priceList = _commercePriceListService.fetchPriceList(
			priceListExternalReferenceCode);

		if (priceList == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find price list " +
						priceListExternalReferenceCode);
			}

			return;
		}

		Sku sku = _commerceSkuService.fetchSku(
			salesforcePricebookEntry.getProduct2Id());

		if (sku == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find SKU for Salesforce product " +
						salesforcePricebookEntry.getProduct2Id());
			}

			return;
		}

		_commercePriceEntryService.addOrUpdatePriceEntry(
			salesforcePricebookEntry.isActive(),
			salesforcePricebookEntry.getId(),
			salesforcePricebookEntry.getUnitPrice(),
			priceListExternalReferenceCode, priceList.getId(), sku.getId());
	}

	private void _processProduct2(String action, JSONObject recordJSONObject)
		throws Exception {

		SalesforceProduct2 salesforceProduct2 = new SalesforceProduct2(
			recordJSONObject);

		if (Objects.equals(action, "delete")) {
			_commerceProductService.deactivateProduct(
				salesforceProduct2.getId());
		}
		else {
			_commerceProductService.addOrUpdateProduct(
				salesforceProduct2.getDescription(), salesforceProduct2.getId(),
				salesforceProduct2.getName());
		}
	}

	private static final Log _log = LogFactory.getLog(
		SalesforceObjectSubscriber.class);

	@Autowired
	private CommercePriceEntryService _commercePriceEntryService;

	@Autowired
	private CommercePriceListService _commercePriceListService;

	@Autowired
	private CommerceProductService _commerceProductService;

	@Autowired
	private CommerceSkuService _commerceSkuService;

	@Value("${liferay.one.pubsub.subscriber.salesforce.object.project.id}")
	private String _projectId;

	@Value("${liferay.one.pubsub.subscriber.salesforce.object.subscription}")
	private String _subscription;

}