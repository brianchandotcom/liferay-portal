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

				String salesforceAccountKey = _fetchSalesforceAccountKey(
					accountJSONObject);

				String accountName = accountJSONObject.getString("name");

				if (_log.isInfoEnabled()) {
					StringBundler sb = new StringBundler(2);

					sb.append("Account: ");
					sb.append(accountName);

					_log.info(sb.toString());
				}

				if (salesforceAccountKey == null) {
					return;
				}

				String partnerCountry = _fetchAccountCountryISOCode(
					accountJSONObject);

				JSONObject partnerAccountJSONObject = new JSONObject();

				partnerAccountJSONObject.put(
					"externalReferenceCode", salesforceAccountKey
				).put(
					"name", accountName
				);

				if (partnerCountry != null) {
					partnerAccountJSONObject.put(
						"partnerCountry", partnerCountry);
				}

				String accountRegion = "";

				try {
					JSONObject accountsfJSONObject = _get(
						uriBuilder -> uriBuilder.path(
							"/o/c/accountsfs/by-external-reference-code/" +
								salesforceAccountKey
						).build());

					if (accountsfJSONObject.has("partnerLevelType")) {
						JSONObject partnerLevelTypeJSONObject =
							accountsfJSONObject.getJSONObject(
								"partnerLevelType");

						String partnerLevelType =
							partnerLevelTypeJSONObject.getString("key");

						Map<String, String> partnerLevelERCs =
							_fetchPartnerLevelERCs();

						partnerAccountJSONObject.put(
							"r_prtLvlToAcc_c_partnerLevelERC",
							partnerLevelERCs.get(partnerLevelType));
					}

					if (accountsfJSONObject.has("currency")) {
						JSONObject accountCurrencyJSONObject =
							accountsfJSONObject.getJSONObject("currency");

						String accountCurrency =
							accountCurrencyJSONObject.getString("key");

						partnerAccountJSONObject.put(
							"currency", accountCurrency);
					}

					if (accountsfJSONObject.has("region")) {
						accountRegion = accountsfJSONObject.getString("region");
					}
				}
				catch (Exception exception) {
					_log.error(
						"Could not get Account from SALESFORCE: " + exception);
				}

				try {
					_put(
						partnerAccountJSONObject.toString(),
						StringBundler.concat(
							"/o/headless-admin-user/v1.0/accounts",
							"/by-external-reference-code/",
							salesforceAccountKey));

					if (_log.isInfoEnabled()) {
						StringBundler sb = new StringBundler(6);

						sb.append("Account: ");
						sb.append(accountName);
						sb.append(" (");
						sb.append(salesforceAccountKey);
						sb.append(") was UPDATED: ");
						sb.append(partnerAccountJSONObject);

						_log.info(sb.toString());
					}

					if (!accountRegion.isEmpty()) {
						JSONObject updatedAccountJSONObject = _get(
							uriBuilder -> uriBuilder.path(
								StringBundler.concat(
									"/o/headless-admin-user/v1.0/accounts",
									"/by-external-reference-code/",
									salesforceAccountKey)
							).build());

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

				String salesforceAccountKey = _fetchSalesforceAccountKey(
					accountJSONObject);

				if (salesforceAccountKey == null) {
					return;
				}

				String accountName = accountJSONObject.getString("name");

				try {
					_delete(
						"/o/headless-admin-user/v1.0/accounts" +
							"/by-external-reference-code/" +
								salesforceAccountKey);

					if (_log.isInfoEnabled()) {
						StringBundler sb = new StringBundler(5);

						sb.append("Account: ");
						sb.append(accountName);
						sb.append(" (");
						sb.append(salesforceAccountKey);
						sb.append(") was DELETED");

						_log.info(sb.toString());
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

		Map<String, Long> regionsIDs = _fetchRegionsIDs();

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
					StringBundler sb = new StringBundler(6);

					sb.append("Account: ");
					sb.append(accountName);
					sb.append(" (");
					sb.append(accountExternalReferenceCode);
					sb.append(") was assigned to: ");
					sb.append(accountRegion);

					_log.info(sb.toString());
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
					StringBundler sb = new StringBundler(6);

					sb.append("Account: ");
					sb.append(accountName);
					sb.append(" (");
					sb.append(accountExternalReferenceCode);
					sb.append(") moved to: ");
					sb.append(accountRegion);

					_log.info(sb.toString());
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

	private String _fetchAccountCountryISOCode(JSONObject accountJSONObject) {
		Map<String, String> countriesISOcodes = new HashMap<>();

		for (String iso : Locale.getISOCountries()) {
			Locale locale = new Locale("", iso);

			countriesISOcodes.put(locale.getDisplayCountry(), iso);
		}

		JSONArray postalAddressesJSONArray = accountJSONObject.getJSONArray(
			"postalAddresses");

		if (postalAddressesJSONArray == null) {
			return null;
		}

		String countryISOcode = "";

		for (int k = 0; k < postalAddressesJSONArray.length(); k++) {
			JSONObject postalAddressesJSONObject =
				postalAddressesJSONArray.getJSONObject(k);

			Boolean primary = postalAddressesJSONObject.getBoolean("primary");

			if (primary && postalAddressesJSONObject.has("addressCountry")) {
				String addressCountry = postalAddressesJSONObject.getString(
					"addressCountry");

				countryISOcode = countriesISOcodes.get(addressCountry);
			}
		}

		if (countryISOcode.isEmpty()) {
			return null;
		}

		return countryISOcode;
	}

	private Map<String, String> _fetchPartnerLevelERCs() {
		JSONObject partnerLevelResponseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/partnerlevels/"
			).queryParam(
				"pageSize", "-1"
			).build());

		Map<String, String> partnerLevelERCs = new HashMap<>();

		if (partnerLevelResponseJSONObject.getInt("totalCount") > 0) {
			JSONArray partnerLevelJSONArray =
				partnerLevelResponseJSONObject.getJSONArray("items");

			for (int r = 0; r < partnerLevelJSONArray.length(); r++) {
				JSONObject partnerLevelJSONObject =
					partnerLevelJSONArray.getJSONObject(r);

				JSONObject partnerLevelTypeJSONObject =
					partnerLevelJSONObject.getJSONObject("partnerLevelType");

				String partnerLevelType = partnerLevelTypeJSONObject.optString(
					"key");

				String partnerLevelERC = partnerLevelJSONObject.getString(
					"externalReferenceCode");

				partnerLevelERCs.put(partnerLevelType, partnerLevelERC);
			}
		}

		return partnerLevelERCs;
	}

	private Map<String, Long> _fetchRegionsIDs() {
		Map<String, Long> regionsIDs = new HashMap<>();

		JSONObject globalOrganizationResponseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/headless-admin-user/v1.0/organizations" +
					"/by-external-reference-code/PRM-ORG-GLOBAL"
			).queryParam(
				"pageSize", "-1"
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

			for (int o = 0; o < organizationsJSONArray.length(); o++) {
				JSONObject organizationJSONObject =
					organizationsJSONArray.getJSONObject(o);

				String organizationName = organizationJSONObject.getString(
					"name");
				Long organizationID = organizationJSONObject.getLong("id");

				regionsIDs.put(organizationName, organizationID);
			}
		}

		return regionsIDs;
	}

	private String _fetchSalesforceAccountKey(JSONObject accountJSONObject) {
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

			if (StringUtil.equalsIgnoreCase(
					externalLinkJSONObject.getString("entityName"),
					"account")) {

				return externalLinkJSONObject.getString("entityId");
			}
		}

		return null;
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

	private void _patch(String bodyValue, String path) {
		_getWebClient(
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
			Void.class
		).block();
	}

	private void _post(String bodyValue, String path) {
		_getWebClient(
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
			Void.class
		).block();
	}

	private void _put(String bodyValue, String path) {
		_getWebClient(
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
			Void.class
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