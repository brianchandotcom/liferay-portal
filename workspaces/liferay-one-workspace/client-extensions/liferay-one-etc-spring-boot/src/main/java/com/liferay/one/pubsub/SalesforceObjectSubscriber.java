/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.pubsub;

import com.liferay.one.constants.SalesforceObjectConstants;
import com.liferay.one.model.SalesforcePricebookEntry;
import com.liferay.one.model.SalesforceProduct2;
import com.liferay.one.service.CommerceCatalogService;
import com.liferay.one.service.CommercePricingService;
import com.liferay.osb.spring.boot.client.pubsub.Message;
import com.liferay.osb.spring.boot.client.pubsub.subscriber.BasePubsubSubscriber;

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

			String action = jsonObject.getString(
				SalesforceObjectConstants.KEY_ACTION);
			String salesforceObjectName = jsonObject.getString(
				SalesforceObjectConstants.KEY_SALESFORCE_OBJECT_NAME);

			JSONArray recordsJSONArray = jsonObject.getJSONArray(
				SalesforceObjectConstants.KEY_RECORDS);

			boolean delete = SalesforceObjectConstants.ACTION_DELETE.equals(
				action);

			for (int i = 0; i < recordsJSONArray.length(); i++) {
				JSONObject recordJSONObject = recordsJSONArray.getJSONObject(i);

				if (SalesforceObjectConstants.SALESFORCE_OBJECT_PRODUCT2.equals(
						salesforceObjectName)) {

					_processProduct2(recordJSONObject, delete);
				}
				else if (SalesforceObjectConstants.
							SALESFORCE_OBJECT_PRICEBOOK_ENTRY.equals(
								salesforceObjectName)) {

					_processPricebookEntry(recordJSONObject, delete);
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
			JSONObject recordJSONObject, boolean delete)
		throws Exception {

		SalesforcePricebookEntry salesforcePricebookEntry =
			new SalesforcePricebookEntry(recordJSONObject);

		if (delete) {
			_commercePricingService.deletePriceEntry(salesforcePricebookEntry);
		}
		else {
			_commercePricingService.upsertPriceEntry(salesforcePricebookEntry);
		}
	}

	private void _processProduct2(JSONObject recordJSONObject, boolean delete)
		throws Exception {

		SalesforceProduct2 salesforceProduct2 = new SalesforceProduct2(
			recordJSONObject);

		if (delete) {
			_commerceCatalogService.deactivateProduct(
				salesforceProduct2.getId());
		}
		else {
			_commerceCatalogService.upsertProduct(salesforceProduct2);
		}
	}

	private static final Log _log = LogFactory.getLog(
		SalesforceObjectSubscriber.class);

	@Autowired
	private CommerceCatalogService _commerceCatalogService;

	@Autowired
	private CommercePricingService _commercePricingService;

	@Value("${pubsub.subscribers.salesforce-sobjects.project-id}")
	private String _projectId;

	@Value("${pubsub.subscribers.salesforce-sobjects.subscription}")
	private String _subscription;

}