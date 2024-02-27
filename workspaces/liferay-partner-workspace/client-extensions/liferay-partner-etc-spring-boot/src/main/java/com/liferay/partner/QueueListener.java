/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.partner;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
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

		String routingKey = message.getMessageProperties(
		).getReceivedRoutingKey();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Received message ", deliveryTag, " with routing key ",
					routingKey));
		}

		if (!routingKey.equals("koroneiki.account.update")) {
			return;
		}

		try {
			String body = new String(message.getBody(), "UTF-8");

			JSONObject jsonObject = new JSONObject(body);

			JSONObject koroneikiAccountJSONObject = jsonObject.getJSONObject(
				"account");

			String salesforceAccountKey = _getSalesforceKey(
				koroneikiAccountJSONObject);

			if (salesforceAccountKey.equals("")) {
				channel.basicReject(deliveryTag, false);

				return;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Updating account ", salesforceAccountKey,
						" with name ",
						koroneikiAccountJSONObject.getString("name")));
			}

			String countryISOCode = _getCountryISOCode(
				koroneikiAccountJSONObject);

			_updateAccount(
				salesforceAccountKey,
				koroneikiAccountJSONObject.getString("name"), countryISOCode);

			channel.basicAck(deliveryTag, false);
		}
		catch (Exception exception1) {
			_log.error(exception1);

			try {
				channel.basicReject(deliveryTag, false);
			}
			catch (Exception exception2) {
				_log.error(exception2);
			}
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
				HttpHeaders.AUTHORIZATION, _getAuthorization()
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-partner-etc-spring-boot-oauth-application-headless-" +
				"server");
	}

	private String _getCountryISOCode(JSONObject koroneikiAccountJSONObject) {
		JSONArray postalAddressesJSONArray =
			koroneikiAccountJSONObject.getJSONArray("postalAddresses");

		for (int i = 0; i < postalAddressesJSONArray.length(); i++) {
			JSONObject postalAddressesJSONObject =
				postalAddressesJSONArray.getJSONObject(i);

			if (postalAddressesJSONObject.getBoolean("primary") &&
				postalAddressesJSONObject.has("addressCountry")) {

				for (String countryISOCode : Locale.getISOCountries()) {
					Locale countryNameLocale = new Locale("", countryISOCode);

					if (StringUtil.equalsIgnoreCase(
							postalAddressesJSONObject.getString(
								"addressCountry"),
							countryNameLocale.toString())) {

						return countryISOCode;
					}
				}
			}
		}

		return "";
	}

	private String _getPartnerLevelExternalReferenceCode(
		JSONObject proxyAccountJSONObject) {

		if (!proxyAccountJSONObject.has("partnerLevelType")) {
			return "";
		}

		JSONObject partnerLevelsJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/partnerlevels/"
			).queryParam(
				"pageSize", "-1"
			).build());

		Long partnerLevelTotalCount = partnerLevelsJSONObject.getLong(
			"totalCount");

		if (partnerLevelTotalCount > 0) {
			JSONObject proxyPartnerLevelTypeJSONObject =
				proxyAccountJSONObject.getJSONObject("partnerLevelType");

			JSONArray partnerLevelsJSONArray =
				partnerLevelsJSONObject.getJSONArray("items");

			for (int i = 0; i < partnerLevelsJSONArray.length(); i++) {
				JSONObject partnerLevelJSONObject =
					partnerLevelsJSONArray.getJSONObject(i);

				JSONObject partnerLevelTypeJSONObject =
					partnerLevelJSONObject.getJSONObject("partnerLevelType");

				if (StringUtil.equalsIgnoreCase(
						partnerLevelTypeJSONObject.getString("key"),
						proxyPartnerLevelTypeJSONObject.getString("key"))) {

					return partnerLevelJSONObject.getString(
						"externalReferenceCode");
				}
			}
		}

		return "";
	}

	private long _getRegionOrganizationId(String regionName) {
		JSONObject globalOrganizationJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/headless-admin-user/v1.0/organizations" +
					"/by-external-reference-code/PRM-ORG-GLOBAL"
			).build());

		JSONObject organizationsJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/headless-admin-user/v1.0/organizations/" +
					globalOrganizationJSONObject.getLong("id") +
						"/child-organizations"
			).queryParam(
				"pageSize", "-1"
			).build());

		if (organizationsJSONObject.getLong("totalCount") <= 0) {
			return 0;
		}

		JSONArray organizationsJSONArray = organizationsJSONObject.getJSONArray(
			"items");

		for (int i = 0; i < organizationsJSONArray.length(); i++) {
			JSONObject organizationJSONObject =
				organizationsJSONArray.getJSONObject(i);

			if (regionName.equals(organizationJSONObject.getString("name"))) {
				return organizationJSONObject.getLong("id");
			}
		}

		return 0;
	}

	private String _getSalesforceKey(JSONObject koroneikiAccountJSONObject) {
		JSONArray entitlementsJSONArray =
			koroneikiAccountJSONObject.getJSONArray("entitlements");

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
			return "";
		}

		JSONArray externalLinksJSONArray =
			koroneikiAccountJSONObject.getJSONArray("externalLinks");

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

		return "";
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
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
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
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).bodyValue(
			bodyValue
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	private JSONObject _put(String bodyValue, String path) {
		return new JSONObject(
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
				HttpHeaders.AUTHORIZATION, _getAuthorization()
			).bodyValue(
				bodyValue
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private void _updateAccount(
			String externalReferenceCode, String name, String countryISOCode)
		throws Exception {

		JSONObject accountJSONObject = new JSONObject() {
			{
				put("externalReferenceCode", externalReferenceCode);
				put("name", name);
			}
		};

		JSONObject proxyAccountJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/c/proxyaccounts/by-external-reference-code/" +
					externalReferenceCode
			).build());

		if (proxyAccountJSONObject.has("currency")) {
			JSONObject proxyCurrencyJSONObject =
				proxyAccountJSONObject.getJSONObject("currency");

			accountJSONObject.put(
				"currency", proxyCurrencyJSONObject.getString("key"));
		}

		if (!countryISOCode.equals("")) {
			accountJSONObject.put("partnerCountry", countryISOCode);
		}

		String partnerLevelExternalReferenceCode =
			_getPartnerLevelExternalReferenceCode(proxyAccountJSONObject);

		if (!partnerLevelExternalReferenceCode.equals("")) {
			accountJSONObject.put(
				"r_prtLvlToAcc_c_partnerLevelERC",
				partnerLevelExternalReferenceCode);
		}

		JSONObject updatedAccountJSONObject = _put(
			accountJSONObject.toString(),
			"/o/headless-admin-user/v1.0/accounts/by-external-reference-code/" +
				externalReferenceCode);

		if (proxyAccountJSONObject.has("region")) {
			_updateAccountRegion(
				updatedAccountJSONObject,
				proxyAccountJSONObject.getString("region"));
		}
	}

	private void _updateAccountRegion(
		JSONObject accountJSONObject, String proxyAccountRegion) {

		long regionOrganizationId = _getRegionOrganizationId(
			proxyAccountRegion);

		if (regionOrganizationId <= 0) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Assigning account to " + proxyAccountRegion);
		}

		JSONArray organizationIdsJSONArray = accountJSONObject.getJSONArray(
			"organizationIds");

		if (organizationIdsJSONArray.isEmpty()) {
			_post(
				"",
				StringBundler.concat(
					"/o/headless-admin-user/v1.0/accounts",
					"/by-external-reference-code/",
					accountJSONObject.getString("externalReferenceCode"),
					"/organizations/", regionOrganizationId));
		}
		else {
			Long organizationId = organizationIdsJSONArray.getLong(0);

			if (organizationId != regionOrganizationId) {
				JSONArray accountExternalReferenceCodeJSONArray =
					new JSONArray();

				accountExternalReferenceCodeJSONArray.put(
					accountJSONObject.getString("externalReferenceCode"));

				_patch(
					accountExternalReferenceCodeJSONArray.toString(),
					StringBundler.concat(
						"/o/headless-admin-user/v1.0/organizations",
						"/move-accounts/", organizationId, "/",
						regionOrganizationId, "/by-external-reference-code"));
			}
		}
	}

	private static final Log _log = LogFactory.getLog(QueueListener.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}