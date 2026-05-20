/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.one.model.SubscriptionEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
			"userId", userId
		);

		String response = post(
			getAuthorization(), subscriptionEntryJSONObject.toString(),
			UriComponentsBuilder.fromPath(
				"/o/c/subscriptionentries"
			).build(
			).toUri());

		return new SubscriptionEntry(new JSONObject(response));
	}

	public void deleteSubscriptionEntry(
			String className, long classPK, long userId)
		throws Exception {

		SubscriptionEntry subscriptionEntry = fetchSubscriptionEntry(
			className, classPK, userId);

		if (subscriptionEntry == null) {
			return;
		}

		delete(
			getAuthorization(), "",
			UriComponentsBuilder.fromPath(
				"/o/c/subscriptionentries/" +
					subscriptionEntry.getSubscriptionEntryId()
			).build(
			).toUri());
	}

	public SubscriptionEntry fetchSubscriptionEntry(
			String className, long classPK, long userId)
		throws Exception {

		List<SubscriptionEntry> subscriptionEntries = getSubscriptionEntries(
			StringBundler.concat(
				"(className eq '", className, "') and (classPK eq ", classPK,
				") and (userId eq ", userId, ")"));

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
			getAuthorization(),
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
	protected void doReceive(Message message) throws Exception {
		_sendActivationKeyEmails(30);
		_sendActivationKeyEmails(14);
		_sendActivationKeyEmails(0);
	}

	private void _sendActivationKeyEmails(int licenseKeyExpirationDateOffset)
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

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("active", true);

		List<LicenseKey> licenseKeys = _licenseKeyLocalService.search(
			null, null, null, null, null, null, null, null, null, null,
			startDateLTCalendar.getTime(), new long[0], new String[0], null,
			null, new String[0], new long[0], null, null, null, null, null,
			null, null, expirationDateGTCalendar.getTime(),
			expirationDateLTCalendar.getTime(), params, true, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (LicenseKey licenseKey : licenseKeys) {
			if (Validator.isNull(licenseKey.getAccountKey())) {
				continue;
			}

			Account account = _accountWebService.fetchAccount(
				licenseKey.getAccountKey());

			if (account == null) {
				continue;
			}

			long classNameId = _classNameLocalService.getClassNameId(
				LicenseKey.class);

			List<SubscriptionEntry> subscriptionEntries =
				_subscriptionEntryLocalService.getSubscriptionEntries(
					classNameId, licenseKey.getLicenseKeyId());

			for (SubscriptionEntry subscriptionEntry : subscriptionEntries) {
				Contact contact = _contactWebService.fetchContactByUuid(
					subscriptionEntry.getContactUuid());

				_customerPortalRelease.sendContactAccountActivationKeyEmail(
					contact, account, licenseKey);
			}
		}
	}

	protected String getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-one-etc-spring-boot-oaua");
	}

	public void sendContactAccountActivationKeyEmail(
		Contact contact, Account account, LicenseKey licenseKey) {

		if ((contact == null) ||
			!GetterUtil.getBoolean(contact.getEmailAddressVerified())) {

			return;
		}

		String subscriptionState = _accountReader.getSubscriptionState(account);

		if (!subscriptionState.equals(ProductPurchaseConstants.STATE_ACTIVE)) {
			return;
		}

		if (!licenseKey.isActive()) {
			return;
		}

		_sendContactAccountActivationKeyEmail(contact, licenseKey);
	}

	private String _getActivationKeyExpirationMessage(
		long days, String expirationDate, String productGroup,
		ResourceBundle resourceBundle) {

		if (days == 0) {
			return LanguageUtil.format(
				resourceBundle,
				"one-of-your-projects-activation-keys-expires-today-x",
				new Object[] {expirationDate, productGroup}, false);
		}

		return LanguageUtil.format(
			resourceBundle,
			"one-of-your-projects-activation-keys-will-expire-on-x",
			new Object[] {expirationDate, productGroup}, false);
	}

	private String _getActivationKeySubject(
		long days, String productGroup, ResourceBundle resourceBundle) {

		if (days == 0) {
			return LanguageUtil.format(
				resourceBundle, "liferay-x-activation-key-expires-today",
				productGroup);
		}

		return LanguageUtil.format(
			resourceBundle, "liferay-x-activation-key-will-expire-in-x-days",
			new Object[] {productGroup, days}, false);
	}

	private String _getContactFullName(Contact contact) {
		StringBundler sb = new StringBundler(5);

		if (Validator.isNotNull(contact.getFirstName())) {
			sb.append(contact.getFirstName());
		}

		if (Validator.isNotNull(contact.getMiddleName())) {
			if (sb.length() > 0) {
				sb.append(StringPool.SPACE);
			}

			sb.append(contact.getMiddleName());
		}

		if (Validator.isNotNull(contact.getLastName())) {
			if (sb.length() > 0) {
				sb.append(StringPool.SPACE);
			}

			sb.append(contact.getLastName());
		}

		return sb.toString();
	}

	private String _getEmailTemplate(
		String templateName, String defaultTemplateName) {

		ClassLoader classLoader =
			CustomerPortalReleaseImpl.class.getClassLoader();

		String templateDirName =
			"com/liferay/osb/provisioning/internal/dependencies/";

		URL url = classLoader.getResource(templateDirName + templateName);

		if (url != null) {
			return ContentUtil.get(classLoader, templateDirName + templateName);
		}

		return ContentUtil.get(
			classLoader, templateDirName + defaultTemplateName);
	}

	private void _sendContactAccountActivationKeyEmail(
		Contact contact, LicenseKey licenseKey) {

		Date expirationDate = licenseKey.getExpirationDate();

		long days = ChronoUnit.DAYS.between(
			Instant.now(), expirationDate.toInstant());

		if (days < 0) {
			return;
		}

		String languageId = contact.getLanguageId();

		if (Validator.isNull(languageId)) {
			languageId = LocaleUtil.toLanguageId(LocaleUtil.US);
		}

		String body = _getEmailTemplate(
			"email_provisioning_activation_key_body_" + languageId + ".tmpl",
			"email_provisioning_activation_key_body.tmpl");

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(_portal.getDefaultCompanyId());

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"MMMM dd, yyyy");

		String productGroup = ProductGroupConstants.getProductGroup(
			licenseKey.getProductName());

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		Year year = Year.now();

		subscriptionSender.setContextAttribute(
			"[$ACCOUNT_KEY$]", licenseKey.getAccountKey());
		subscriptionSender.setContextAttribute(
			"[$CONTACT_FIRST_NAME$]", contact.getFirstName());
		subscriptionSender.setContextAttribute(
			"[$LICENSE_KEY_EXPIRATION_MESSAGE$]",
			_getActivationKeyExpirationMessage(
				days, dateFormat.format(expirationDate), productGroup,
				resourceBundle));
		subscriptionSender.setContextAttribute(
			"[$LICENSE_KEY_LICENSE_ENTRY_NAME$]",
			licenseKey.getLicenseEntryName());
		subscriptionSender.setContextAttribute(
			"[$LICENSE_KEY_PRODUCT_GROUP$]", productGroup);
		subscriptionSender.setContextAttribute(
			"[$LICENSE_KEY_PRODUCT_VERSION$]", licenseKey.getProductVersion());
		subscriptionSender.setContextAttribute(
			"[$LICENSE_KEY_SIZING$]",
			LicenseSizing.getSizing(licenseKey.getSizing()));
		subscriptionSender.setContextAttribute(
			"[$YEAR$]", year.getValue(), false);
		subscriptionSender.setFrom(
			"customer-service@liferay.com", "Liferay Support");
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId("provisioning");
		subscriptionSender.setReplyToAddress("customer-service@liferay.com");
		subscriptionSender.setSubject(
			_getActivationKeySubject(days, productGroup, resourceBundle));

		subscriptionSender.addRuntimeSubscribers(
			contact.getEmailAddress(), _getContactFullName(contact));

		subscriptionSender.flushNotificationsAsync();
	}
	
	private static final Log _log = LogFactory.getLog(
		SubscriptionEntryService.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}