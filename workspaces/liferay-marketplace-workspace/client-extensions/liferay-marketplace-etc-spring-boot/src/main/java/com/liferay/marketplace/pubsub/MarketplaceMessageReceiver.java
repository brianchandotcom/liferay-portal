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
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.PostalAddressResource;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.client.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.marketplace.constants.MarketplaceConstants;
import com.liferay.marketplace.service.KoroneikiService;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.service.ProvisioningHubService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.http.HttpStatus;

/**
 * @author Caleb Hall
 */
public class MarketplaceMessageReceiver implements MessageReceiver {

	public MarketplaceMessageReceiver(
		KoroneikiService koroneikiService,
		MarketplaceService marketplaceService, List<String> productKeys,
		ProvisioningHubService provisioningHubService, String topicName) {

		_koroneikiService = koroneikiService;
		_marketplaceService = marketplaceService;
		_productKeys = productKeys;
		_provisioningHubService = provisioningHubService;
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
						PUBSUB_TOPIC_NAME_KORONEIKI_ACCOUNT_CREATE) ||
				Objects.equals(
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

				_processKoroneikiAccount(koroneikiAccount);
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

	private String _getExternalLinkValue(
		ExternalLink[] externalLinks, String domain, String entityName) {

		for (ExternalLink externalLink : externalLinks) {
			if (Objects.equals(externalLink.getDomain(), domain) &&
				Objects.equals(externalLink.getEntityName(), entityName)) {

				return externalLink.getEntityId();
			}
		}

		return null;
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

	private String _getSkuExternalReferenceCode(Product product) {
		Map<String, String> properties = product.getProperties();

		return "SF-" + properties.get("salesforce-product-id");
	}

	private UserAccount _getUserAccount(String emailAddress) throws Exception {
		Page<UserAccount> userAccountsPage =
			_marketplaceService.getUserAccountsPage(
				"emailAddress eq '" + emailAddress + "'", Pagination.of(1, 1),
				"", "");

		for (UserAccount userAccount : userAccountsPage.getItems()) {
			if (Objects.equals(emailAddress, userAccount.getEmailAddress())) {
				return userAccount;
			}
		}

		return null;
	}

	private boolean _isOKStatusCode(int statusCode) {
		if ((statusCode / 100) == 2) {
			return true;
		}

		return false;
	}

	private void _processKoroneikiAccount(
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount)
		throws Exception {

		String project = _getExternalLinkValue(
			koroneikiAccount.getExternalLinks(), "salesforce", "project");

		if (project != null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skipping over account " + koroneikiAccount.getKey() +
						" because it is a project");
			}

			return;
		}

		Account account = _getAccount(koroneikiAccount.getKey());

		if (account == null) {
			account = _syncAccount(koroneikiAccount);
		}

		_syncAccountAddresses(account, koroneikiAccount);
		_syncAccountContacts(account, koroneikiAccount);

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

		String opportunityId = _getExternalLinkValue(
			productPurchase.getExternalLinks(), "salesforce", "opportunity");

		if (opportunityId == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Opportunity ID not found to process product purchase");
			}

			return;
		}

		if (_channelId == null) {
			synchronized (this) {
				if (_channelId == null) {
					ChannelResource channelResource =
						_marketplaceService.getChannelResource();

					Channel channel =
						channelResource.getChannelByExternalReferenceCode(
							_MARKETPLACE_CHANNEL);

					_channelId = channel.getId();
				}
			}
		}

		String accountKey = productPurchase.getAccountKey();

		com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account account =
			_koroneikiService.getKoroneikiAccount(accountKey);

		if (Validator.isNotNull(
				_getExternalLinkValue(
					account.getExternalLinks(), "salesforce", "project"))) {

			accountKey = account.getParentAccountKey();
		}

		OrderResource orderResource = _marketplaceService.getOrderResource();

		Order order;

		try {
			order = orderResource.getOrderByExternalReferenceCode(
				opportunityId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			String finalAccountKey = accountKey;

			order = orderResource.postOrder(
				new Order() {
					{
						setAccountExternalReferenceCode(() -> finalAccountKey);
						setChannelId(() -> _channelId);
						setCurrencyCode(() -> "USD");
						setExternalReferenceCode(() -> opportunityId);
						setOrderItems(
							() -> new OrderItem[] {
								new OrderItem() {
									{
										setQuantity(
											() -> new BigDecimal(
												productPurchase.getQuantity()));
										setSkuExternalReferenceCode(
											() -> _getSkuExternalReferenceCode(
												productPurchase.getProduct()));
									}
								}
							});
						setOrderTypeExternalReferenceCode(() -> "ADDONS");
					}
				});
		}

		_provisioningHubService.provision(order, productPurchase);

		_marketplaceService.updateOrder(
			null, order.getId(), MarketplaceConstants.ORDER_STATUS_COMPLETED);
	}

	private Account _syncAccount(
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount)
		throws Exception {

		AccountResource accountResource =
			_marketplaceService.getAccountResource();

		HttpInvoker.HttpResponse httpResponse =
			accountResource.postAccountHttpResponse(
				new Account() {
					{
						setCustomFields(
							() -> _getCustomFields(koroneikiAccount));
						setDescription(koroneikiAccount::getDescription);
						setExternalReferenceCode(koroneikiAccount::getKey);
						setName(koroneikiAccount::getName);
					}
				});

		int statusCode = httpResponse.getStatusCode();

		if (_isOKStatusCode(statusCode)) {
			return Account.toDTO(httpResponse.getContent());
		}
		else if ((statusCode == HttpStatus.BAD_REQUEST.value()) ||
				 (statusCode == HttpStatus.CONFLICT.value())) {

			throw new Exception(
				StringBundler.concat(
					"Race condition detected for account ",
					koroneikiAccount.getKey(), " with HTTP status code ",
					statusCode));
		}

		throw new Exception(
			StringBundler.concat(
				"Failed to process account for ", koroneikiAccount.getKey(),
				"with HTTP status code ", statusCode, " - ",
				httpResponse.getContent()));
	}

	private void _syncAccountAddresses(
			Account account,
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount)
		throws Exception {

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
	}

	private void _syncAccountContacts(
			Account account,
			com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account
				koroneikiAccount)
		throws Exception {

		com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page<Contact>
			contactsPage = _koroneikiService.getContactsPage(
				koroneikiAccount.getKey(),
				com.liferay.osb.koroneiki.phloem.rest.client.pagination.
					Pagination.of(1, -1));

		for (Contact contact : contactsPage.getItems()) {
			String emailAddress = contact.getEmailAddress();

			UserAccount userAccount = _getUserAccount(emailAddress);

			if (userAccount == null) {
				HttpInvoker.HttpResponse httpResponse =
					_marketplaceService.postUserAccountHttpResponse(
						new UserAccount() {
							{
								setEmailAddress(contact::getEmailAddress);
								setFamilyName(contact::getLastName);
								setGivenName(contact::getFirstName);
							}
						});

				int statusCode = httpResponse.getStatusCode();

				if (_isOKStatusCode(statusCode)) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Creating user with email address " + emailAddress);
					}

					_marketplaceService.postAccountUserAccountByEmailAddress(
						account.getId(), emailAddress);
				}
				else if ((statusCode == HttpStatus.CONFLICT.value()) ||
						 (statusCode == HttpStatus.BAD_REQUEST.value())) {

					if (_log.isInfoEnabled()) {
						_log.info(
							"Another thread is processing user " +
								emailAddress);
					}
				}
				else {
					throw new Exception(
						StringBundler.concat(
							"Failed to process user account for ", emailAddress,
							". HTTP ", statusCode, " - ",
							httpResponse.getContent()));
				}
			}
			else {
				_marketplaceService.postAccountUserAccountByEmailAddress(
					account.getId(), emailAddress);
			}
		}
	}

	private static final String _MARKETPLACE_CHANNEL = "MARKETPLACE-CHANNEL";

	private static final Log _log = LogFactory.getLog(
		MarketplaceMessageReceiver.class);

	private volatile Long _channelId;
	private final KoroneikiService _koroneikiService;
	private final MarketplaceService _marketplaceService;
	private final List<String> _productKeys;
	private final ProvisioningHubService _provisioningHubService;
	private final String _topicName;

}