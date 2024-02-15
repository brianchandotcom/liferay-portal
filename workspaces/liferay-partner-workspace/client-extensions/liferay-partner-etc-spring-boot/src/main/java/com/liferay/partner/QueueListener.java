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
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;

/**
 * @author Jair Medeiros
 * @author Thaynam Lazaro
 */
@Component
public class QueueListener {

	public static final String ACCOUNT_ROLE_NAME_PARTNER_MANAGER =
		"[Account] Partner Manager (PM)";

	public static final String ACCOUNT_ROLE_NAME_PARTNER_MARKETING_USER =
		"[Account] Partner Marketing User (PMU)";

	public static final String ACCOUNT_ROLE_NAME_PARTNER_SALES_USER =
		"[Account] Partner Sales User (PSU)";

	public static final String REGULAR_ROLE_NAME_PARTNER_MANAGER =
		"Partner Manager (PM)";

	public static final String REGULAR_ROLE_NAME_PARTNER_MARKETING_USER =
		"Partner Marketing User (PMU)";

	public static final String REGULAR_ROLE_NAME_PARTNER_SALES_USER =
		"Partner Sales User (PSU)";

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
						"Could NOT get Account from SALESFORCE: " + exception);

					channel.basicReject(deliveryTag, true);

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
							"Could NOT Update Account with name: ", accountName,
							" ERROR: ", exception));

					channel.basicReject(deliveryTag, true);
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

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Received Account: ", accountName,
							", from message ", deliveryTag));
				}

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
							"Could NOT Delete Account with name: ", accountName,
							" ERROR: ", exception));
				}
			}

			if (receivedRoutingKey.equals(
					"koroneiki.account.contactrole.assigned") ||
				receivedRoutingKey.equals(
					"koroneiki.account.contactrole.unassigned")) {

				JSONObject jsonObject = new JSONObject(body);

				JSONObject accountJSONObject = jsonObject.getJSONObject(
					"account");

				String salesforceAccountKey = _getSalesforceAccountKey(
					accountJSONObject);

				JSONObject contactJSONObject = jsonObject.getJSONObject(
					"contact");

				String contactEmailAddress = contactJSONObject.optString(
					"emailAddress");

				JSONObject contactRoleJSONObject = jsonObject.getJSONObject(
					"contactRole");

				String contactRoleName = contactRoleJSONObject.getString(
					"name");

				Map<String, String> roleNames = new HashMap<>();

				if (contactRoleName.equals("Partner Manager")) {
					roleNames.put(
						ACCOUNT_ROLE_NAME_PARTNER_MANAGER,
						REGULAR_ROLE_NAME_PARTNER_MANAGER);
				}

				if (contactRoleName.equals("Partner Member")) {
					roleNames.put(
						ACCOUNT_ROLE_NAME_PARTNER_MARKETING_USER,
						REGULAR_ROLE_NAME_PARTNER_MARKETING_USER);
					roleNames.put(
						ACCOUNT_ROLE_NAME_PARTNER_SALES_USER,
						REGULAR_ROLE_NAME_PARTNER_SALES_USER);
				}

				if ((contactEmailAddress == null) || roleNames.isEmpty() ||
					(salesforceAccountKey == null)) {

					channel.basicAck(deliveryTag, false);

					return;
				}

				String accountName = accountJSONObject.getString("name");

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Received User: ", contactEmailAddress,
							", from message ", deliveryTag));
				}

				try {
					_getAccountJSONObject(salesforceAccountKey);
				}
				catch (WebClientResponseException.NotFound
							webClientResponseException) {

					_log.error(
						StringBundler.concat(
							"Account: ", accountName, " (",
							salesforceAccountKey, ") was NOT FOUND ",
							webClientResponseException));

					channel.basicAck(deliveryTag, false);

					return;
				}

				JSONArray contanctTeamsJSONArray =
					contactJSONObject.getJSONArray("teams");

				boolean unassignUser = true;

				for (int i = 0; i < contanctTeamsJSONArray.length(); i++) {
					JSONObject teamsJSONObject =
						contanctTeamsJSONArray.getJSONObject(i);

					if (StringUtil.equalsIgnoreCase(
							teamsJSONObject.getString("accountKey"),
							accountJSONObject.getString("key"))) {

						unassignUser = false;

						break;
					}
				}

				for (Map.Entry<String, String> roleName :
						roleNames.entrySet()) {

					String accountRoleName = roleName.getKey();

					if (receivedRoutingKey.equals(
							"koroneiki.account.contactrole.assigned")) {

						try {
							_assignUserAccount(
								salesforceAccountKey, contactEmailAddress);

							_log.info(
								StringBundler.concat(
									"User with Email Address: ",
									contactEmailAddress,
									" was assigned to the Account: ",
									accountName, " (", salesforceAccountKey,
									")"));

							_assignUserAccountToAccountRole(
								accountRoleName, salesforceAccountKey,
								contactEmailAddress);

							_log.info(
								StringBundler.concat(
									"User with Email Address: ",
									contactEmailAddress,
									" was assigned to the Account Role: ",
									accountRoleName));

							JSONObject userAccountJSONObject =
								_getUserAccountJSONObject(contactEmailAddress);

							Long userAccountId = userAccountJSONObject.getLong(
								"id");

							_assignUserAccountToRegularRole(
								roleNames.get(accountRoleName), userAccountId);

							_log.info(
								StringBundler.concat(
									"User with Email Address: ",
									contactEmailAddress,
									" was assigned to the Regular Role: ",
									roleNames.get(accountRoleName)));
						}
						catch (Exception exception) {
							_log.error(
								StringBundler.concat(
									"Could NOT assign User with Email",
									"Address: ", contactEmailAddress,
									" to the Account: ", accountName,
									" ERROR: ", exception));

							channel.basicReject(deliveryTag, false);

							return;
						}
					}

					if (receivedRoutingKey.equals(
							"koroneiki.account.contactrole.unassigned")) {

						try {
							_unassignUserAccountToAccountRole(
								accountRoleName, salesforceAccountKey,
								contactEmailAddress);

							_log.info(
								StringBundler.concat(
									"User with Email Address: ",
									contactEmailAddress,
									" was unassigned from the Account Role: ",
									accountRoleName));

							JSONObject userAccountJSONObject =
								_getUserAccountJSONObject(contactEmailAddress);

							Long userAccountId = userAccountJSONObject.getLong(
								"id");

							_unassignUserAccountToRegularRole(
								roleNames.get(accountRoleName), userAccountId);

							_log.info(
								StringBundler.concat(
									"User with Email Address: ",
									contactEmailAddress,
									" was unassigned from the Regular Role: ",
									roleNames.get(accountRoleName)));

							if (unassignUser) {
								_unassignUserAccount(
									salesforceAccountKey, contactEmailAddress);

								unassignUser = false;

								_log.info(
									StringBundler.concat(
										"User with Email Address: ",
										contactEmailAddress,
										" was unassigned from the Account: ",
										accountName, " (",
										salesforceAccountKey));
							}
						}
						catch (Exception exception) {
							_log.error(
								StringBundler.concat(
									"Could NOT unassign User with Email ",
									"Address: ", contactEmailAddress,
									" to the Account: ", accountName,
									" ERROR: ", exception));

							channel.basicReject(deliveryTag, false);

							return;
						}
					}
				}
			}

			if (receivedRoutingKey.equals("koroneiki.contact.delete")) {
				JSONObject jsonObject = new JSONObject(body);

				JSONObject contactJSONObject = jsonObject.getJSONObject(
					"contact");

				String contactEmailAddress = contactJSONObject.optString(
					"emailAddress");

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Received User: ", contactEmailAddress,
							", from message ", deliveryTag));
				}

				try {
					JSONObject userAccountJSONObject =
						_getUserAccountJSONObject(contactEmailAddress);

					if (userAccountJSONObject == null) {
						channel.basicAck(deliveryTag, false);

						return;
					}

					Long userAccountId = userAccountJSONObject.getLong("id");

					_delete(
						"/o/headless-admin-user/v1.0/user-accounts/" +
							userAccountId);

					_log.info(
						StringBundler.concat(
							"User with Email Address: ", contactEmailAddress,
							" was deleted"));
				}
				catch (WebClientResponseException.NotFound
							webClientResponseException) {

					_log.error(
						StringBundler.concat(
							"User with Email Address: ", contactEmailAddress,
							" was NOT FOUND ", webClientResponseException));

					channel.basicAck(deliveryTag, false);

					return;
				}
				catch (Exception exception) {
					_log.error(
						StringBundler.concat(
							"Could NOT delete User with Email Address: ",
							contactEmailAddress, " ERROR: ", exception));

					channel.basicReject(deliveryTag, false);

					return;
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

	private void _assignUserAccount(
		String salesforceAccountKey, String contactEmailAddress) {

		_post(
			"",
			StringBundler.concat(
				"/o/headless-admin-user/v1.0/accounts",
				"/by-external-reference-code/", salesforceAccountKey,
				"/user-accounts/by-email-address/", contactEmailAddress));
	}

	private void _assignUserAccountToAccountRole(
		String accountRole, String salesforceAccountKey,
		String contactEmailAddress) {

		Map<String, Long> accountRolesIds = _getAccountRolesIds(
			salesforceAccountKey);

		Long accountRoleID = accountRolesIds.get(accountRole);

		_post(
			"",
			StringBundler.concat(
				"/o/headless-admin-user/v1.0/accounts",
				"/by-external-reference-code/", salesforceAccountKey,
				"/account-roles/", accountRoleID,
				"/user-accounts/by-email-address/", contactEmailAddress));
	}

	private void _assignUserAccountToRegularRole(
		String regularRole, Long userAccontID) {

		Map<String, Long> regularRolesIDs = _getRegularRolesIds();

		Long regularRoleID = regularRolesIDs.get(regularRole);

		_post(
			"",
			StringBundler.concat(
				"/o/headless-admin-user/v1.0/roles/", regularRoleID,
				"/association/user-account/", userAccontID));
	}

	private String _delete(String path) {
		return _getWebClient(
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
			String.class
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

	private JSONObject _getAccountJSONObject(String salesforceAccountKey) {
		return _get(
			uriBuilder -> uriBuilder.path(
				StringBundler.concat(
					"/o/headless-admin-user/v1.0/accounts",
					"/by-external-reference-code/", salesforceAccountKey)
			).build());
	}

	private Map<String, Long> _getAccountRolesIds(
		String accountExternalReferenceCode) {

		JSONObject accountRolesResponseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				StringBundler.concat(
					"/o/headless-admin-user/v1.0/accounts",
					"/by-external-reference-code/",
					accountExternalReferenceCode, "/account-roles")
			).queryParam(
				"pageSize", "-1"
			).build());

		Map<String, Long> accountRolesIds = new HashMap<>();

		if (accountRolesResponseJSONObject.getInt("totalCount") > 0) {
			JSONArray accountRolesJSONArray =
				accountRolesResponseJSONObject.getJSONArray("items");

			for (int r = 0; r < accountRolesJSONArray.length(); r++) {
				JSONObject accountRoleJSONObject =
					accountRolesJSONArray.getJSONObject(r);

				String roleName = accountRoleJSONObject.getString("name");
				Long roleID = accountRoleJSONObject.getLong("id");

				accountRolesIds.put(roleName, roleID);
			}
		}

		return accountRolesIds;
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

	private Map<String, Long> _getRegularRolesIds() {
		JSONObject regularRolesResponseJSONObject = _get(
			uriBuilder -> uriBuilder.path(
				"/o/headless-admin-user/v1.0/roles"
			).queryParam(
				"pageSize", "-1"
			).build());

		Map<String, Long> regularRolesIds = new HashMap<>();

		if (regularRolesResponseJSONObject.getInt("totalCount") > 0) {
			JSONArray regularRolesJSONArray =
				regularRolesResponseJSONObject.getJSONArray("items");

			for (int r = 0; r < regularRolesJSONArray.length(); r++) {
				JSONObject regularRoleJSONObject =
					regularRolesJSONArray.getJSONObject(r);

				String roleName = regularRoleJSONObject.getString("name");
				Long roleID = regularRoleJSONObject.getLong("id");

				regularRolesIds.put(roleName, roleID);
			}
		}

		return regularRolesIds;
	}

	private String _getSalesforceAccountKey(JSONObject accountJSONObject) {

		// JSONArray entitlementsJSONArray = accountJSONObject.getJSONArray(
		// 	"entitlements");

		// if (entitlementsJSONArray == null) {
		// 	return null;
		// }

		// boolean partner = false;

		// for (int i = 0; i < entitlementsJSONArray.length(); i++) {
		// 	JSONObject entitlementJSONObject =
		// 		entitlementsJSONArray.getJSONObject(i);

		// 	if (StringUtil.equalsIgnoreCase(
		// 			entitlementJSONObject.getString("name"), "Partner")) {

		// 		partner = true;

		// 		break;
		// 	}
		// }

		// if (!partner) {
		// 	return null;
		// }

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

	private JSONObject _getUserAccountJSONObject(String contactEmailAddress) {
		return _get(
			uriBuilder -> uriBuilder.path(
				"/o/headless-admin-user/v1.0/user-accounts/by-email-address/" +
					contactEmailAddress
			).build());
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

	private void _unassignUserAccount(
		String salesforceAccountKey, String contactEmailAddress) {

		_delete(
			StringBundler.concat(
				"/o/headless-admin-user/v1.0/accounts",
				"/by-external-reference-code/", salesforceAccountKey,
				"/user-accounts/by-email-address/", contactEmailAddress));
	}

	private void _unassignUserAccountToAccountRole(
		String accountRole, String salesforceAccountKey,
		String contactEmailAddress) {

		Map<String, Long> accountRolesIds = _getAccountRolesIds(
			salesforceAccountKey);

		Long accountRoleID = accountRolesIds.get(accountRole);

		_delete(
			StringBundler.concat(
				"/o/headless-admin-user/v1.0/accounts",
				"/by-external-reference-code/", salesforceAccountKey,
				"/account-roles/", accountRoleID,
				"/user-accounts/by-email-address/", contactEmailAddress));
	}

	private void _unassignUserAccountToRegularRole(
		String regularRole, Long userAccontID) {

		Map<String, Long> regularRolesIDs = _getRegularRolesIds();

		Long regularRoleID = regularRolesIDs.get(regularRole);

		_delete(
			StringBundler.concat(
				"/o/headless-admin-user/v1.0/roles/", regularRoleID,
				"/association/user-account/", userAccontID));
	}

	private static final Log _log = LogFactory.getLog(QueueListener.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}