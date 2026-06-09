/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.pubsub;

import com.liferay.one.constants.CommerceCatalogConstants;
import com.liferay.one.model.SalesforcePricebookEntry;
import com.liferay.one.model.SalesforceProduct2;
import com.liferay.one.service.CommerceCatalogService;
import com.liferay.one.service.CommercePricingService;
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
			String salesforceObjectName = jsonObject.getString("sObjectName");

			JSONArray recordsJSONArray = jsonObject.getJSONArray("records");

			for (int i = 0; i < recordsJSONArray.length(); i++) {
				JSONObject recordJSONObject = recordsJSONArray.getJSONObject(i);

				if (Objects.equals(salesforceObjectName, "Product2")) {
					_processProduct2(action, recordJSONObject);
				}
				else if (Objects.equals(
							salesforceObjectName, "PricebookEntry")) {

					_processPricebookEntry(action, recordJSONObject);
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
			_commercePricingService.deletePriceEntry(
				salesforcePricebookEntry.getId());

			return;
		}

		String priceListExternalReferenceCode =
			CommerceCatalogConstants.priceListErc(
				salesforcePricebookEntry.getCurrencyIsoCode());

		Long priceListId = _commercePricingService.fetchPriceListId(
			priceListExternalReferenceCode);

		if (priceListId == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find price list " +
						priceListExternalReferenceCode +
							" for a skipped Salesforce price entry");
			}

			return;
		}

		String skuExternalReferenceCode =
			salesforcePricebookEntry.getProduct2Id();

		Long skuId = _commerceCatalogService.fetchSkuId(
			skuExternalReferenceCode);

		if (skuId == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find SKU for Salesforce product " +
						skuExternalReferenceCode);
			}

			return;
		}

		_commercePricingService.upsertPriceEntry(
			salesforcePricebookEntry.getId(), priceListExternalReferenceCode,
			priceListId, skuId, salesforcePricebookEntry.getUnitPrice(),
			salesforcePricebookEntry.isActive());
	}

	private void _processProduct2(String action, JSONObject recordJSONObject)
		throws Exception {

		SalesforceProduct2 salesforceProduct2 = new SalesforceProduct2(
			recordJSONObject);

		if (Objects.equals(action, "delete")) {
			_commerceCatalogService.deactivateProduct(
				salesforceProduct2.getId());
		}
		else {
			_commerceCatalogService.upsertProduct(
				salesforceProduct2.getId(), salesforceProduct2.getName(),
				salesforceProduct2.getDescription());
		}
	}

	private static final Log _log = LogFactory.getLog(
		SalesforceObjectSubscriber.class);

	@Autowired
	private CommerceCatalogService _commerceCatalogService;

	@Autowired
	private CommercePricingService _commercePricingService;

	@Value("${liferay.one.pubsub.subscriber.salesforce.object.project.id}")
	private String _projectId;

	@Value("${liferay.one.pubsub.subscriber.salesforce.object.subscription}")
	private String _subscription;

}