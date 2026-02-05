/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.pubsub;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

import com.liferay.headless.admin.user.client.custom.field.CustomField;
import com.liferay.headless.admin.user.client.custom.field.CustomValue;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.PostalAddressResource;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Caleb Hall
 */
public class MarketplaceMessageReceiver implements MessageReceiver {

	public MarketplaceMessageReceiver(
		KoroneikiService koroneikiService,
		MarketplaceService marketplaceService, String topicName) {

		_koroneikiService = koroneikiService;
		_marketplaceService = marketplaceService;
		_topicName = topicName;
	}

	@Override
	public void receiveMessage(
		PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {

		ByteString byteString = pubsubMessage.getData();

		JSONObject jsonObject = new JSONObject(byteString.toStringUtf8());

		if (_log.isInfoEnabled()) {
			_log.info("Found message: " + jsonObject);
		}

		try {
			if (Objects.equals(
					_topicName,
					MarketplaceConstants.
						PUBSUB_TOPIC_NAME_KORONEIKI_ACCOUNT_CREATE)) {

				// TODO

			}
			else if (Objects.equals(
						_topicName,
						MarketplaceConstants.
							PUBSUB_TOPIC_NAME_KORONEIKI_ACCOUNT_UPDATE)) {

				com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
					koroneikiAccount =
						com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
							Account.toDTO(
								jsonObject.getJSONObject(
									"account"
								).toString());

				_processKoroneikiAccountUpdate(koroneikiAccount);
			}
			else if (Objects.equals(
						_topicName,
						MarketplaceConstants.
							PUBSUB_TOPIC_NAME_KORONEIKI_ENTITLEMENT_CREATE)) {

				// TODO

			}

			ackReplyConsumer.ack();
		}
		catch (Exception exception) {
			_log.error(exception);

			ackReplyConsumer.nack();
		}
	}

	private PostalAddress _getPostalAddress(
		Account account, String streetAddressLine1) {

		for (PostalAddress postalAddress : account.getPostalAddresses()) {
			if (Objects.equals(
					postalAddress.getStreetAddressLine1(),
					streetAddressLine1)) {

				return postalAddress;
			}
		}

		return null;
	}

	private void _processKoroneikiAccountUpdate(
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount)
		throws Exception {

		Account account = _marketplaceService.getAccountByExternalReferenceCode(
			koroneikiAccount.getKey());

		if (account == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Account " + koroneikiAccount.getName() +
						" does not exists in Marketplace");
			}

			return;
		}

		PostalAddressResource postalAddressResource =
			_marketplaceService.getPostalAddressResource();

		for (com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.PostalAddress
				koroneikiPostalAddress :
					koroneikiAccount.getPostalAddresses()) {

			PostalAddress postalAddress = _getPostalAddress(
				account, koroneikiPostalAddress.getStreetAddressLine1());

			if (postalAddress != null) {
				continue;
			}

			postalAddress = PostalAddress.toDTO(
				koroneikiPostalAddress.toString());

			postalAddress.setAddressType(() -> "billing-and-shipping");

			postalAddressResource.postAccountPostalAddress(
				account.getId(), postalAddress);
		}

		AccountResource accountResource =
			_marketplaceService.getAccountResource();

		accountResource.patchAccount(
			account.getId(),
			new Account() {

				private final CustomField[] _customFields = {
					new CustomField() {
						{
							setCustomValue(
								new CustomValue() {
									{
										setData(
											koroneikiAccount.
												getParentAccountKey());
									}
								});
							setName("koroneiki-parent-account-key");
						}
					},
					new CustomField() {
						{
							setCustomValue(
								new CustomValue() {
									{
										setData(
											_koroneikiService.
												getSalesforceAccountKey(
													koroneikiAccount));
									}
								});
							setName("salesforce-account-key");
						}
					}
				};

				{
					setCustomFields(() -> _customFields);
					setDescription(koroneikiAccount::getDescription);
					setName(koroneikiAccount::getName);
				}
			});

		if (_log.isInfoEnabled()) {
			_log.info(
				"Account " + koroneikiAccount.getName() +
					"synced in Marketplace");
		}
	}

	private static final Log _log = LogFactory.getLog(
		MarketplaceMessageReceiver.class);

	private final KoroneikiService _koroneikiService;
	private final MarketplaceService _marketplaceService;
	private final String _topicName;

}