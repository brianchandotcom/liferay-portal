/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.internal.util;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.provisioning.constants.ProductGroupConstants;
import com.liferay.osb.provisioning.koroneiki.constants.ProductPurchaseConstants;
import com.liferay.osb.provisioning.koroneiki.reader.AccountReader;
import com.liferay.osb.provisioning.license.helper.constants.LicenseSizing;
import com.liferay.osb.provisioning.license.model.LicenseKey;
import com.liferay.osb.provisioning.util.CustomerPortalRelease;
import com.liferay.petra.content.ContentUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Validator;

import java.net.URL;

import java.text.Format;

import java.time.Instant;
import java.time.Year;
import java.time.temporal.ChronoUnit;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(immediate = true, service = CustomerPortalRelease.class)
public class CustomerPortalReleaseImpl implements CustomerPortalRelease {

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

	@Reference
	private AccountReader _accountReader;

	@Reference
	private Portal _portal;

}
