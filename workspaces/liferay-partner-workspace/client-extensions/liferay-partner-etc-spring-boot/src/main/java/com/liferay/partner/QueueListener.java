/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;

import com.rabbitmq.client.Channel;

import java.net.URI;

import java.util.Locale;
import java.util.function.Function;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.amqp.core.ExchangeTypes;
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
				exchange = @Exchange(type = ExchangeTypes.TOPIC, value = "koroneiki_exchange"),
				value = @Queue("${spring.rabbitmq.template.default-receive-queue}")
			)
		}
	)
	public void listener(
		Message message, Channel channel,
		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {

		String receivedRoutingKey = message.getMessageProperties(
		).getReceivedRoutingKey();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Receiving message ", deliveryTag, " with routing key ",
					receivedRoutingKey));
		}

		try {
			String body = new String(message.getBody(), "UTF-8");

			if ((body == null) || body.isEmpty()) {
				if (_log.isWarnEnabled()) {
					_log.warn("Contained no data");
				}

				channel.basicReject(deliveryTag, false);

				return;
			}

			if (receivedRoutingKey.equals("koroneiki.account.update") ||
				receivedRoutingKey.equals("koroneiki.account.create")) {

				JSONObject jsonObject = new JSONObject(body);

				JSONObject accountJSONObject = jsonObject.getJSONObject(
					"account");

				// JSONArray entitlementsJSONArray =
				// 	accountJSONObject.getJSONArray("entitlements");

				// if (entitlementsJSONArray == null) {
				// 	channel.basicReject(deliveryTag, false);

				// 	return;
				// }

				// boolean partner = false;

				// for (int i = 0; i < entitlementsJSONArray.length(); i++) {
				// 	JSONObject entitlementJSONObject =
				// 		entitlementsJSONArray.getJSONObject(i);

				// 	if (StringUtil.equalsIgnoreCase(
				// 			entitlementJSONObject.getString("name"),
				// 			"Partner")) {

				// 		partner = true;

				// 		break;
				// 	}
				// }

				// if (!partner) {
				// 	channel.basicReject(deliveryTag, false);

				// 	return;
				// }

				JSONArray externalLinksJSONArray =
					accountJSONObject.getJSONArray("externalLinks");

				String accountName = accountJSONObject.getString("name");

				JSONObject partnerAccountJSONObject = new JSONObject() {
					{
						put("name", accountName);
					}
				};

				for (int i = 0; i < externalLinksJSONArray.length(); i++) {
					JSONObject externalLinkJSONObject =
						externalLinksJSONArray.getJSONObject(i);

					String salesforceAccountKey = null;

					if ((StringUtil.equalsIgnoreCase(
							externalLinkJSONObject.getString("domain"),
							"salesforce") ||
						 StringUtil.equalsIgnoreCase(
							 externalLinkJSONObject.getString("domain"),
							 "dossiera")) &&
						StringUtil.equalsIgnoreCase(
							externalLinkJSONObject.getString("entityName"),
							"account")) {

						salesforceAccountKey = externalLinkJSONObject.getString(
							"entityId");

						partnerAccountJSONObject.put(
							"externalReferenceCode", salesforceAccountKey);

						break;
					}

					if (salesforceAccountKey == null) {
						channel.basicReject(deliveryTag, false);

						return;
					}
				}

				JSONArray postalAddressesJSONArray =
					accountJSONObject.getJSONArray("postalAddresses");

				for (int i = 0; i < postalAddressesJSONArray.length(); i++) {
					JSONObject postalAddressesJSONObject =
						postalAddressesJSONArray.getJSONObject(i);

					if (postalAddressesJSONObject.getBoolean("primary") &&
						postalAddressesJSONObject.has("addressCountry")) {

						for (String countryISOCode : Locale.getISOCountries()) {
							Locale countryNameLocale = new Locale(
								"", countryISOCode);

							if (StringUtil.equalsIgnoreCase(
									postalAddressesJSONObject.getString(
										"addressCountry"),
									countryNameLocale.toString())) {

								partnerAccountJSONObject.put(
									"partnerCountry", countryISOCode);
							}
						}
					}
				}

				String accountExternalReferenceCode =
					partnerAccountJSONObject.getString("externalReferenceCode");

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Updating account ", accountExternalReferenceCode,
							" with name ", accountName));
				}

				try {
					JSONObject proxyAccountJSONObject = _get(
						uriBuilder -> uriBuilder.path(
							"/o/c/proxyaccounts/by-external-reference-code/" +
								accountExternalReferenceCode
						).build());

					if (proxyAccountJSONObject.has("partnerLevelType")) {
						JSONObject partnerLevelsResponseJSONObject = _get(
							uriBuilder -> uriBuilder.path(
								"/o/c/partnerlevels/"
							).queryParam(
								"pageSize", "-1"
							).build());

						Long partnerLevelResponseTotalCoult =
							partnerLevelsResponseJSONObject.getLong(
								"totalCount");

						if (partnerLevelResponseTotalCoult > 0) {
							JSONObject partnerLevelTypeJSONObject =
								proxyAccountJSONObject.getJSONObject(
									"partnerLevelType");

							JSONArray partnerLevelsResponseJSONArray =
								partnerLevelsResponseJSONObject.getJSONArray(
									"items");

							for (int i = 0;
								 i < partnerLevelsResponseJSONArray.length();
								 i++) {

								JSONObject partnerLevelResponseJSONObject =
									partnerLevelsResponseJSONArray.
										getJSONObject(i);

								JSONObject partnerLevelTypeResponseJSONObject =
									partnerLevelResponseJSONObject.
										getJSONObject("partnerLevelType");

								if (StringUtil.equalsIgnoreCase(
										partnerLevelTypeResponseJSONObject.
											getString("key"),
										partnerLevelTypeJSONObject.getString(
											"key"))) {

									partnerAccountJSONObject.put(
										"r_prtLvlToAcc_c_partnerLevelERC",
										partnerLevelResponseJSONObject.
											getString("externalReferenceCode"));
								}
							}
						}
					}

					if (proxyAccountJSONObject.has("currency")) {
						JSONObject accountCurrencyJSONObject =
							proxyAccountJSONObject.getJSONObject("currency");

						String accountCurrency =
							accountCurrencyJSONObject.getString("key");

						partnerAccountJSONObject.put(
							"currency", accountCurrency);
					}

					String updatedAccount = _put(
						partnerAccountJSONObject.toString(),
						StringBundler.concat(
							"/o/headless-admin-user/v1.0/accounts",
							"/by-external-reference-code/",
							accountExternalReferenceCode));

					if (proxyAccountJSONObject.has("region")) {
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
									globalOrganizationId +
										"/child-organizations"
							).queryParam(
								"pageSize", "-1"
							).build());

						Long organizationsResponseTotalCount =
							organizationsResponseJSONObject.getLong(
								"totalCount");

						if (organizationsResponseTotalCount > 0) {
							String accountRegion =
								proxyAccountJSONObject.getString("region");

							JSONArray organizationsJSONArray =
								organizationsResponseJSONObject.getJSONArray(
									"items");

							for (int i = 0; i < organizationsJSONArray.length();
								 i++) {

								JSONObject organizationJSONObject =
									organizationsJSONArray.getJSONObject(i);

								if (accountRegion.equals(
										organizationJSONObject.getString(
											"name"))) {

									Long regionId =
										organizationJSONObject.getLong("id");

									if (_log.isInfoEnabled()) {
										_log.info(
											"Assigning Account to " +
												accountRegion);
									}

									JSONObject updatedAccountJSONObject =
										new JSONObject(updatedAccount);

									JSONArray organizationIdsJSONArray =
										updatedAccountJSONObject.getJSONArray(
											"organizationIds");

									if (organizationIdsJSONArray.isEmpty()) {
										_post(
											"",
											StringBundler.concat(
												"/o/headless-admin-user/v1.0",
												"/accounts",
												"/by-external-reference-code/",
												accountExternalReferenceCode,
												"/organizations/", regionId));

										break;
									}

									Long organizationId =
										organizationIdsJSONArray.getLong(0);

									if (organizationId != regionId) {
										JSONArray
											accountExternalReferenceCodeJSONArray =
												new JSONArray();

										accountExternalReferenceCodeJSONArray.
											put(accountExternalReferenceCode);

										_patch(
											accountExternalReferenceCodeJSONArray.
												toString(),
											StringBundler.concat(
												"/o/headless-admin-user/v1.0",
												"/organizations/move-accounts/",
												organizationId, "/", regionId,
												"/by-external-reference-code"));
									}
								}
							}
						}
					}

					channel.basicAck(deliveryTag, false);
				}
				catch (Exception exception) {
					_log.error(exception);

					channel.basicReject(deliveryTag, false);
				}
			}

			channel.basicAck(deliveryTag, false);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
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