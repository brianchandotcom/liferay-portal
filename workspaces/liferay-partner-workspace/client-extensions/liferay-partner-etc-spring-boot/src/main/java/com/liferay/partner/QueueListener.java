/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;

import com.rabbitmq.client.Channel;

import java.net.URI;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

/**
 * @author Jair Medeiros
 * @author Thaynam Lazaro
 */
@Component
public class QueueListener {

	@RabbitListener(
		bindings = {
			@QueueBinding(
				exchange = @Exchange("koroneiki"), key = "account.update",
				value = @Queue("${spring.rabbitmq.template.default-receive-queue}")
			)
		}
	)
	public void accountUpdateListener(
		Message message, Channel channel,
		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {

		String receivedRoutingKey = message.getMessageProperties(
		).getReceivedRoutingKey();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Received message ", deliveryTag, " with routing key ",
					receivedRoutingKey));
		}

		try {
			String body = new String(message.getBody(), "UTF-8");

			if ((body == null) || body.isEmpty()) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Message ", deliveryTag, " with routing key ",
							receivedRoutingKey, " contained no data"));
				}

				channel.basicReject(deliveryTag, false);

				return;
			}

			if (receivedRoutingKey.equals("koroneiki.account.update") ||
				receivedRoutingKey.equals("koroneiki.account.create")) {

				JSONObject jsonObject = new JSONObject(body);

				JSONObject accountJSONObject = jsonObject.getJSONObject(
					"account");

				String salesforceAccountKey = _getSalesforceAccountKey(
					accountJSONObject);

				if (salesforceAccountKey == null) {
					channel.basicAck(deliveryTag, false);

					return;
				}

				String accountName = accountJSONObject.getString("name");

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Received Account: ", accountName,
							", from message ", deliveryTag));
				}

				String accountCountryISOCode = _getAccountcountryISOCode(
					accountJSONObject);

				JSONObject partnerAccountJSONObject = new JSONObject() {
					{
						put("externalReferenceCode", salesforceAccountKey);
						put("name", accountName);

						if (accountCountryISOCode != null) {
							put("partnerCountry", accountCountryISOCode);
						}
					}
				};

				String accountRegion = null;

				try {
					JSONObject proxyAccountJSONObject = _get(
						uriBuilder -> uriBuilder.path(
							"/o/c/proxyaccounts/by-external-reference-code/" +
								salesforceAccountKey
						).build());

					if (proxyAccountJSONObject.has("partnerLevelType")) {
						JSONObject partnerLevelTypeJSONObject =
							proxyAccountJSONObject.getJSONObject(
								"partnerLevelType");

						String partnerLevelType =
							partnerLevelTypeJSONObject.getString("key");

						Map<String, String> partnerLevelExternalReferenceCodes =
							_getPartnerLevelExternalReferenceCodes();

						partnerAccountJSONObject.put(
							"r_prtLvlToAcc_c_partnerLevelERC",
							partnerLevelExternalReferenceCodes.get(
								partnerLevelType));
					}

					if (proxyAccountJSONObject.has("currency")) {
						JSONObject accountCurrencyJSONObject =
							proxyAccountJSONObject.getJSONObject("currency");

						String accountCurrency =
							accountCurrencyJSONObject.getString("key");

						partnerAccountJSONObject.put(
							"currency", accountCurrency);
					}

					if (proxyAccountJSONObject.has("region")) {
						accountRegion = proxyAccountJSONObject.getString(
							"region");
					}
				}
				catch (Exception exception) {
					_log.error(
						"Could not get Account from SALESFORCE: " + exception);

					channel.basicReject(deliveryTag, false);

					return;
				}

				try {
					String updatedAccount = _put(
						partnerAccountJSONObject.toString(),
						StringBundler.concat(
							"/o/headless-admin-user/v1.0/accounts",
							"/by-external-reference-code/",
							salesforceAccountKey));

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Account: ", accountName, " (",
								salesforceAccountKey, ") was UPDATED: ",
								partnerAccountJSONObject));
					}

					if (accountRegion != null) {
						JSONObject updatedAccountJSONObject = new JSONObject(
							updatedAccount);

						_assignAccountToRegion(
							updatedAccountJSONObject, accountRegion);
					}
				}
				catch (Exception exception) {
					_log.error(
						StringBundler.concat(
							"Could not Update Account with name: ", accountName,
							" ERROR: ", exception));
				}
			}

			if (receivedRoutingKey.equals("koroneiki.account.delete")) {
				JSONObject jsonObject = new JSONObject(body);

				JSONObject accountJSONObject = jsonObject.getJSONObject(
					"account");

				String salesforceAccountKey = _getSalesforceAccountKey(
					accountJSONObject);

				if (salesforceAccountKey == null) {
					channel.basicAck(deliveryTag, false);

					return;
				}

				String accountName = accountJSONObject.getString("name");

				try {
					_delete(
						"/o/headless-admin-user/v1.0/accounts" +
							"/by-external-reference-code/" +
								salesforceAccountKey);

					if (_log.isInfoEnabled()) {
						StringBundler.concat(
							"Account: ", accountName, " (",
							salesforceAccountKey, ") was DELETED");
					}
				}
				catch (Exception exception) {
					_log.error(
						StringBundler.concat(
							"Could not Delete Account with name: ", accountName,
							" ERROR: ", exception));
				}
			}

			channel.basicAck(deliveryTag, false);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _assignAccountToRegion(
		JSONObject accountJSONObject, String accountRegion) {

		Map<String, Long> regionsIDs = _getRegionsIDs();

		Long regionID = regionsIDs.get(accountRegion);

		if (regionID == null) {
			return;
		}

		JSONArray organizationIdsJSONArray = accountJSONObject.getJSONArray(
			"organizationIds");
		String accountExternalReferenceCode = accountJSONObject.getString(
			"externalReferenceCode");
		String accountName = accountJSONObject.getString("name");

		if (organizationIdsJSONArray.isEmpty()) {
			try {
				_post(
					"",
					StringBundler.concat(
						"/o/headless-admin-user/v1.0/accounts",
						"/by-external-reference-code/",
						accountExternalReferenceCode, "/organizations/",
						regionID));

				if (_log.isInfoEnabled()) {
					StringBundler.concat(
						"Account: ", accountName, " (",
						accountExternalReferenceCode, ") was assigned to: ",
						accountRegion);
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
		else if (organizationIdsJSONArray.getLong(0) != regionID) {
			JSONArray accountExternalReferenceCodeJSONArray = new JSONArray();

			accountExternalReferenceCodeJSONArray.put(
				accountExternalReferenceCode);

			try {
				_patch(
					accountExternalReferenceCodeJSONArray.toString(),
					StringBundler.concat(
						"/o/headless-admin-user/v1.0/organizations",
						"/move-accounts/", organizationIdsJSONArray.getLong(0),
						"/", regionID, "/by-external-reference-code"));

				if (_log.isInfoEnabled()) {
					StringBundler.concat(
						"Account: ", accountName, " (",
						accountExternalReferenceCode, ") moved to: ",
						accountRegion);
				}
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private void _delete(String path) {
		_getWebClient(
		).delete(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION,
			"Bearer " + _oAuth2AccessToken.getTokenValue()
		).retrieve(
		).bodyToMono(
			Void.class
		).block();
	}

	private JSONObject _get(Function<UriBuilder, URI> uriFunction) {
		return new JSONObject(
			_getWebClient(
			).get(
			).uri(
				uriBuilder -> uriFunction.apply(uriBuilder)
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + _oAuth2AccessToken.getTokenValue()
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private String _getAccountcountryISOCode(JSONObject accountJSONObject) {
		Map<String, String> countriesISOCodes = new HashMap<>();

		for (String iso : Locale.getISOCountries()) {
			Locale locale = new Locale("", iso);

			countriesISOCodes.put(locale.getDisplayCountry(), iso);
		}

		JSONArray postalAddressesJSONArray = accountJSONObject.getJSONArray(
			"postalAddresses");

		if (postalAddressesJSONArray == null) {
			return null;
		}

		String countryISOCode = null;

		for (int i = 0; i < postalAddressesJSONArray.length(); i++) {
			JSONObject postalAddressesJSONObject =
				postalAddressesJSONArray.getJSONObject(i);

			if (postalAddressesJSONObject.getBoolean("primary") &&
				postalAddressesJSONObject.has("addressCountry")) {

				String addressCountry = postalAddressesJSONObject.getString(
					"addressCountry");

				countryISOCode = countriesISOCodes.get(addressCountry);
			}
		}

		return countryISOCode;
	}

	private Map<String, String> _getPartnerLevelExternalReferenceCodes() {
		JSONObject partnerLevelResponseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/partnerlevels/"
			).queryParam(
				"pageSize", "-1"
			).build());

		Map<String, String> partnerLevelExternalReferenceCodes =
			new HashMap<>();

		if (partnerLevelResponseJSONObject.getInt("totalCount") > 0) {
			JSONArray partnerLevelJSONArray =
				partnerLevelResponseJSONObject.getJSONArray("items");

			for (int i = 0; i < partnerLevelJSONArray.length(); i++) {
				JSONObject partnerLevelJSONObject =
					partnerLevelJSONArray.getJSONObject(i);

				JSONObject partnerLevelTypeJSONObject =
					partnerLevelJSONObject.getJSONObject("partnerLevelType");

				String partnerLevelType = partnerLevelTypeJSONObject.optString(
					"key");

				String partnerLevelExternalReferenceCode =
					partnerLevelJSONObject.getString("externalReferenceCode");

				partnerLevelExternalReferenceCodes.put(
					partnerLevelType, partnerLevelExternalReferenceCode);
			}
		}

		return partnerLevelExternalReferenceCodes;
	}

	private Map<String, Long> _getRegionsIDs() {
		Map<String, Long> regionsIDs = new HashMap<>();

		JSONObject globalOrganizationResponseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/headless-admin-user/v1.0/organizations" +
					"/by-external-reference-code/PRM-ORG-GLOBAL"
			).build());

		Long globalOrganizationId =
			globalOrganizationResponseJSONObject.getLong("id");

		JSONObject organizationsResponseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/headless-admin-user/v1.0/organizations/" +
					globalOrganizationId + "/child-organizations"
			).queryParam(
				"pageSize", "-1"
			).build());

		if (organizationsResponseJSONObject.getInt("totalCount") > 0) {
			JSONArray organizationsJSONArray =
				organizationsResponseJSONObject.getJSONArray("items");

			for (int i = 0; i < organizationsJSONArray.length(); i++) {
				JSONObject organizationJSONObject =
					organizationsJSONArray.getJSONObject(i);

				String organizationName = organizationJSONObject.getString(
					"name");
				Long organizationID = organizationJSONObject.getLong("id");

				regionsIDs.put(organizationName, organizationID);
			}
		}

		return regionsIDs;
	}

	private String _getSalesforceAccountKey(JSONObject accountJSONObject) {
		JSONArray entitlementsJSONArray = accountJSONObject.getJSONArray(
			"entitlements");

		if (entitlementsJSONArray == null) {
			return null;
		}

		boolean partner = false;

		for (int i = 0; i < entitlementsJSONArray.length(); i++) {
			JSONObject entitlementJSONObject =
				entitlementsJSONArray.getJSONObject(i);

			if (StringUtil.equalsIgnoreCase(
					entitlementJSONObject.getString("name"), "Partner")) {

				partner = true;

				break;
			}
		}

		if (!partner) {
			return null;
		}

		JSONArray externalLinksJSONArray = accountJSONObject.getJSONArray(
			"externalLinks");

		if (externalLinksJSONArray == null) {
			return null;
		}

		for (int i = 0; i < externalLinksJSONArray.length(); i++) {
			JSONObject externalLinkJSONObject =
				externalLinksJSONArray.getJSONObject(i);

			if ((StringUtil.equalsIgnoreCase(
					externalLinkJSONObject.getString("domain"), "salesforce") ||
				 StringUtil.equalsIgnoreCase(
					 externalLinkJSONObject.getString("domain"), "dossiera")) &&
				StringUtil.equalsIgnoreCase(
					externalLinkJSONObject.getString("entityName"),
					"account")) {

				return externalLinkJSONObject.getString("entityId");
			}
		}

		return null;
	}

	private WebClient _getWebClient() {
		return WebClient.builder(
		).baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).exchangeStrategies(
			ExchangeStrategies.builder(
			).codecs(
				clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs(
				).maxInMemorySize(
					5 * 1024 * 1024
				)
			).build()
		).build();
	}

	private String _patch(String bodyValue, String path) {
		return _getWebClient(
		).patch(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION,
			"Bearer " + _oAuth2AccessToken.getTokenValue()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private String _post(String bodyValue, String path) {
		return _getWebClient(
		).post(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION,
			"Bearer " + _oAuth2AccessToken.getTokenValue()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private String _put(String bodyValue, String path) {
		return _getWebClient(
		).put(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).header(
			HttpHeaders.AUTHORIZATION,
			"Bearer " + _oAuth2AccessToken.getTokenValue()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private static final Log _log = LogFactory.getLog(QueueListener.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}