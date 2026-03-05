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
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.PostalAddressResource;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.client.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;

import java.math.BigDecimal;

import java.util.List;
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
		MarketplaceService marketplaceService, List<String> productKeys,
		String topicName) {

		_koroneikiService = koroneikiService;
		_marketplaceService = marketplaceService;
		_productKeys = productKeys;
		_topicName = topicName;
	}

	@Override
	public void receiveMessage(
		PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {

		ByteString byteString = pubsubMessage.getData();

		JSONObject jsonObject = new JSONObject(byteString.toStringUtf8());

		try {
			if (Objects.equals(
					_topicName,
					MarketplaceConstants.
						PUBSUB_TOPIC_NAME_KORONEIKI_ACCOUNT_CREATE)) {

				com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
					koroneikiAccount =
						com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.
							Account.toDTO(
								jsonObject.getJSONObject(
									"account"
								).toString());

				_processKoroneikiAccountCreate(koroneikiAccount);
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
							PUBSUB_TOPIC_NAME_KORONEIKI_PRODUCT_PURCHASE_CREATE)) {

				ProductPurchase productPurchase = ProductPurchase.toDTO(
					jsonObject.getJSONObject(
						"productPurchase"
					).toString());

				_processKoroneikiProductPurchaseCreate(productPurchase);
			}

			ackReplyConsumer.ack();
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to process ", jsonObject, " for topic ",
					_topicName),
				exception);

			ackReplyConsumer.nack();
		}
	}

	private Account _getAccount(String externalReferenceCode) throws Exception {
		AccountResource accountResource =
			_marketplaceService.getAccountResource();

		Page<Account> accountsPage = accountResource.getAccountsPage(
			externalReferenceCode, "", Pagination.of(0, -1), "");

		for (Account account : accountsPage.getItems()) {
			if (Objects.equals(
					account.getExternalReferenceCode(),
					externalReferenceCode)) {

				return account;
			}
		}

		return null;
	}

	private CustomField[] _getCustomFields(
		com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
			koroneikiAccount) {

		return new CustomField[] {
			new CustomField() {
				{
					setCustomValue(
						new CustomValue() {
							{
								setData(koroneikiAccount.getParentAccountKey());
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
									_koroneikiService.getSalesforceAccountKey(
										koroneikiAccount));
							}
						});
					setName("salesforce-account-key");
				}
			}
		};
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

	private PostalAddress[] _getPostalAddresses(
		com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
			koroneikiAccount) {

		return TransformUtil.transform(
			koroneikiAccount.getPostalAddresses(),
			koroneikiPostalAddress -> {
				PostalAddress postalAddress = PostalAddress.toDTO(
					koroneikiPostalAddress.toString());

				postalAddress.setAddressType(() -> "billing-and-shipping");

				return postalAddress;
			},
			PostalAddress.class);
	}

	private void _processKoroneikiAccountCreate(
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount)
		throws Exception {

		AccountResource accountResource =
			_marketplaceService.getAccountResource();

		Account account = accountResource.postAccount(
			new Account() {
				{
					setCustomFields(() -> _getCustomFields(koroneikiAccount));
					setDescription(koroneikiAccount::getDescription);
					setExternalReferenceCode(koroneikiAccount::getKey);
					setName(koroneikiAccount::getName);
					setPostalAddresses(
						() -> _getPostalAddresses(koroneikiAccount));
				}
			});

		com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page<Contact>
			contactsPage = _koroneikiService.getContactsPage(
				koroneikiAccount.getKey(),
				com.liferay.osb.koroneiki.phloem.rest.client.pagination.
					Pagination.of(1, -1));

		for (Contact contact : contactsPage.getItems()) {
			String emailAddress = contact.getEmailAddress();

			Page<UserAccount> userAccountsPage =
				_marketplaceService.getUserAccountsPage(
					"emailAddress eq '" + emailAddress + "'",
					Pagination.of(1, 1), "", "");

			if (userAccountsPage.fetchFirstItem() == null) {
				_marketplaceService.postUserAccount(
					new UserAccount() {
						{
							setEmailAddress(contact::getEmailAddress);
							setFamilyName(contact::getLastName);
							setGivenName(contact::getFirstName);
						}
					});
			}

			_marketplaceService.postAccountUserAccountByEmailAddress(
				account.getId(), emailAddress);
		}
	}

	private void _processKoroneikiAccountUpdate(
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount)
		throws Exception {

		Account account = _getAccount(koroneikiAccount.getKey());

		if (account == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Account \"" + koroneikiAccount.getKey() +
						"\" not found in Marketplace");
			}

			return;
		}

		PostalAddressResource postalAddressResource =
			_marketplaceService.getPostalAddressResource();

		for (PostalAddress postalAddress :
				_getPostalAddresses(koroneikiAccount)) {

			PostalAddress existingPostalAddress = _getPostalAddress(
				account, postalAddress.getStreetAddressLine1());

			if (existingPostalAddress != null) {
				continue;
			}

			postalAddressResource.postAccountPostalAddress(
				account.getId(), postalAddress);
		}

		AccountResource accountResource =
			_marketplaceService.getAccountResource();

		accountResource.patchAccount(
			account.getId(),
			new Account() {
				{
					setCustomFields(() -> _getCustomFields(koroneikiAccount));
					setDescription(koroneikiAccount::getDescription);
					setName(koroneikiAccount::getName);
				}
			});

		if (_log.isInfoEnabled()) {
			_log.info(
				"Account \"" + koroneikiAccount.getKey() +
					"\" updated in Marketplace");
		}
	}

	private void _processKoroneikiProductPurchaseCreate(
			ProductPurchase productPurchase)
		throws Exception {

		if (!_productKeys.contains(productPurchase.getProductKey())) {
			return;
		}

		ChannelResource channelResource =
			_marketplaceService.getChannelResource();

		Order order = new Order() {
			{
				setAccountExternalReferenceCode(productPurchase::getAccountKey);
				setCurrencyCode(() -> "USD");
				setExternalReferenceCode(productPurchase::getKey);
				setOrderItems(
					() -> new OrderItem[] {
						new OrderItem() {
							{
								setQuantity(
									() -> new BigDecimal(
										productPurchase.getQuantity()));
								setSkuExternalReferenceCode(
									productPurchase::getProductKey);
							}
						}
					});
				setOrderStatus(
					() -> MarketplaceConstants.ORDER_STATUS_COMPLETED);
				setOrderTypeExternalReferenceCode(() -> "SALESFORCE-ORDER");
				setPaymentStatus(
					() -> MarketplaceConstants.ORDER_PAYMENT_STATUS_COMPLETED);
			}
		};

		Channel channel = channelResource.getChannelByExternalReferenceCode(
			"MARKETPLACE-CHANNEL");

		order.setChannelId(channel::getId);

		_marketplaceService.postOrder(order);
	}

	private static final Log _log = LogFactory.getLog(
		MarketplaceMessageReceiver.class);

	private final KoroneikiService _koroneikiService;
	private final MarketplaceService _marketplaceService;
	private final List<String> _productKeys;
	private final String _topicName;

}