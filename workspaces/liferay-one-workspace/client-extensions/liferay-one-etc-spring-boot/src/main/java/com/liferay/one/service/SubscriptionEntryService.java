/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.problem.Problem;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import com.liferay.one.constants.ClassNameConstants;
import com.liferay.one.constants.ProductGroupConstants;
import com.liferay.one.model.LicenseKey;
import com.liferay.one.model.SubscriptionEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.Year;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jenny Chen
 * @author Amos Fong
 */
@Component
public class SubscriptionEntryService extends BaseService {

	public SubscriptionEntry addSubscriptionEntry(
			String className, long classPK, long userId)
		throws Exception {

		SubscriptionEntry existingSubscriptionEntry = fetchSubscriptionEntry(
			className, classPK, userId);

		if (existingSubscriptionEntry != null) {
			return existingSubscriptionEntry;
		}

		JSONObject subscriptionEntryJSONObject = new JSONObject(
		).put(
			"className", className
		).put(
			"classPK", classPK
		).put(
			"customUserId", userId
		);

		String response = post(
			_getAuthorization(), subscriptionEntryJSONObject.toString(),
			UriComponentsBuilder.fromPath(
				"/o/c/subscriptionentries"
			).build(
			).toUri());

		return new SubscriptionEntry(new JSONObject(response));
	}

	public void deleteSubscriptionEntries(long userId) throws Exception {
		List<SubscriptionEntry> subscriptionEntries = getSubscriptionEntries(
			"(customUserId eq " + userId + ")");

		for (SubscriptionEntry subscriptionEntry : subscriptionEntries) {
			_deleteSubscriptionEntry(
				subscriptionEntry.getSubscriptionEntryId());
		}
	}

	public void deleteSubscriptionEntry(
			String className, long classPK, long userId)
		throws Exception {

		SubscriptionEntry subscriptionEntry = fetchSubscriptionEntry(
			className, classPK, userId);

		if (subscriptionEntry == null) {
			return;
		}

		_deleteSubscriptionEntry(subscriptionEntry.getSubscriptionEntryId());
	}

	public SubscriptionEntry fetchSubscriptionEntry(
			String className, long classPK, long userId)
		throws Exception {

		List<SubscriptionEntry> subscriptionEntries = getSubscriptionEntries(
			StringBundler.concat(
				"(className eq '", className, "') and (classPK eq ", classPK,
				") and (customUserId eq ", userId, ")"));

		if (subscriptionEntries.isEmpty()) {
			return null;
		}

		return subscriptionEntries.get(0);
	}

	public List<SubscriptionEntry> getSubscriptionEntries(String filterString)
		throws Exception {

		UriComponentsBuilder uriComponentsBuilder =
			UriComponentsBuilder.fromPath("/o/c/subscriptionentries");

		if (filterString != null) {
			uriComponentsBuilder.queryParam("filter", filterString);
		}

		String response = get(
			_getAuthorization(),
			uriComponentsBuilder.build(
			).toUri());

		List<SubscriptionEntry> subscriptionEntries = new ArrayList<>();

		if (Validator.isNull(response)) {
			return subscriptionEntries;
		}

		try {
			JSONObject jsonObject = new JSONObject(response);

			JSONArray jsonArray = jsonObject.getJSONArray("items");

			for (int i = 0; i < jsonArray.length(); i++) {
				subscriptionEntries.add(
					new SubscriptionEntry(jsonArray.getJSONObject(i)));
			}

			return subscriptionEntries;
		}
		catch (Exception exception) {
			_log.error("Unable to parse JSON: " + response, exception);

			return subscriptionEntries;
		}
	}

	@Scheduled(cron = "0 0 0 * * *")
	protected void scheduledSendExpiringLicenseKeyEmails() throws Exception {
		_sendExpiringLicenseKeyEmails(30);
		_sendExpiringLicenseKeyEmails(14);
		_sendExpiringLicenseKeyEmails(0);
	}

	private void _deleteSubscriptionEntry(long subscriptionEntryId)
		throws Exception {

		delete(
			_getAuthorization(), StringPool.BLANK,
			UriComponentsBuilder.fromPath(
				"/o/c/subscriptionentries/" + subscriptionEntryId
			).build(
			).toUri());
	}

	private Account _fetchAccount(long accountEntryId) throws Exception {
		AccountResource accountResource = AccountResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-one-etc-spring-boot-oahs")
		).build();

		try {
			return accountResource.getAccount(accountEntryId);
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			if ((problem != null) &&
				(GetterUtil.getInteger(problem.getStatus()) ==
					HttpStatus.NOT_FOUND.value())) {

				return null;
			}

			throw problemException;
		}
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-one-etc-spring-boot-oaua");
	}

	private String _getExpirationMessage(
		String languageId, int days, String expirationDate,
		String productGroup) {

		if (days == 0) {
			return _messageSource.getMessage(
				"expiration-message-today",
				new Object[] {expirationDate, productGroup},
				_toLocale(languageId));
		}

		return _messageSource.getMessage(
			"expiration-message-future",
			new Object[] {expirationDate, productGroup}, _toLocale(languageId));
	}

	private String _getExpirationStatus(String languageId, int days) {
		if (days == 0) {
			return _messageSource.getMessage(
				"expiration-status-today", null, _toLocale(languageId));
		}

		return _messageSource.getMessage(
			"expiration-status-future", new Object[] {days},
			_toLocale(languageId));
	}

	private String _getLanguageId(UserAccount userAccount) {
		String languageId = userAccount.getLanguageId();

		if (Validator.isNotNull(languageId) &&
			_supportedLanguageIds.contains(languageId)) {

			return languageId;
		}

		return _DEFAULT_LANGUAGE_ID;
	}

	private UserAccount _getUserAccount(long userId) throws Exception {
		UserAccountResource userAccountResource = UserAccountResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION,
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-one-etc-spring-boot-oahs")
		).build();

		return userAccountResource.getUserAccount(userId);
	}

	private void _sendExpiringLicenseKeyEmail(
			LicenseKey licenseKey, long userId, int days)
		throws Exception {

		UserAccount userAccount = _getUserAccount(userId);

		if ((userAccount == null) ||
			Validator.isNull(userAccount.getEmailAddress())) {

			return;
		}

		String languageId = _getLanguageId(userAccount);

		// TODO "ACCOUNT_URL"

		Map<String, String> placeholders = HashMapBuilder.put(
			"EXPIRATION_STATUS", _getExpirationStatus(languageId, days)
		).put(
			"LICENSE_KEY_EXPIRATION_MESSAGE",
			_getExpirationMessage(
				languageId, days, licenseKey.getCustomExpirationDate(),
				licenseKey.getProductName())
		).put(
			"LICENSE_KEY_LICENSE_NAME", licenseKey.getLicenseName()
		).put(
			"LICENSE_KEY_PRODUCT_GROUP",
			ProductGroupConstants.getProductGroup(licenseKey.getProductName())
		).put(
			"LICENSE_KEY_PRODUCT_VERSION", licenseKey.getProductVersion()
		).put(
			"LICENSE_KEY_SIZING", licenseKey.getSizing()
		).put(
			"USER_FIRST_NAME", userAccount.getGivenName()
		).put(
			"YEAR",
			Year.now(
			).toString()
		).build();

		JSONObject processedTemplateJSONObject =
			_notificationTemplateService.getAndProcessTemplateJSONObject(
				"LICENSE-KEY-EXPIRATION-WARNING", languageId, placeholders);

		_notificationQueueEntryService.addNotificationQueueEntry(
			"customer-service@liferay.com", "Liferay Support",
			userAccount.getEmailAddress(),
			processedTemplateJSONObject.getString("subject"),
			processedTemplateJSONObject.getString("body"));
	}

	private void _sendExpiringLicenseKeyEmails(
			int licenseKeyExpirationDateOffset)
		throws Exception {

		Calendar expirationDateGTCalendar = Calendar.getInstance();

		expirationDateGTCalendar.add(
			Calendar.DATE, licenseKeyExpirationDateOffset);

		Calendar expirationDateLTCalendar = Calendar.getInstance();

		expirationDateLTCalendar.add(
			Calendar.DATE, licenseKeyExpirationDateOffset + 1);

		Calendar startDateLTCalendar = Calendar.getInstance();

		startDateLTCalendar.add(
			Calendar.DATE, licenseKeyExpirationDateOffset - 60);

		List<LicenseKey> licenseKeys = _licenseKeyService.getLicenseKeys(
			StringBundler.concat(
				"(active eq true) and (customExpirationDate gt ",
				expirationDateGTCalendar.toInstant(),
				") and (customExpirationDate lt ",
				expirationDateLTCalendar.toInstant(), ") and (startDate lt ",
				startDateLTCalendar.toInstant(), ")"));

		for (LicenseKey licenseKey : licenseKeys) {
			Account account = _fetchAccount(licenseKey.getAccountEntryId());

			if (account == null) {
				continue;
			}

			List<SubscriptionEntry> subscriptionEntries =
				getSubscriptionEntries(
					StringBundler.concat(
						"(className eq '", ClassNameConstants.LICENSE_KEY,
						"') and (classPK eq ", licenseKey.getLicenseKeyId(),
						")"));

			for (SubscriptionEntry subscriptionEntry : subscriptionEntries) {
				_sendExpiringLicenseKeyEmail(
					licenseKey, subscriptionEntry.getCustomUserId(),
					licenseKeyExpirationDateOffset);
			}
		}
	}

	private Locale _toLocale(String languageId) {
		return Locale.forLanguageTag(StringUtil.replace(languageId, '_', '-'));
	}

	private static final String _DEFAULT_LANGUAGE_ID = "en_US";

	private static final Log _log = LogFactory.getLog(
		SubscriptionEntryService.class);

	private static final Set<String> _supportedLanguageIds = SetUtil.fromArray(
		"en_US", "es_ES", "ja_JP", "pt_BR");

	@Autowired
	private LicenseKeyService _licenseKeyService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private MessageSource _messageSource;

	@Autowired
	private NotificationQueueEntryService _notificationQueueEntryService;

	@Autowired
	private NotificationTemplateService _notificationTemplateService;

}