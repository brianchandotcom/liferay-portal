/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.subscription.internal.messaging.listener;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.provisioning.koroneiki.web.service.AccountWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ContactWebService;
import com.liferay.osb.provisioning.license.model.LicenseKey;
import com.liferay.osb.provisioning.license.service.LicenseKeyLocalService;
import com.liferay.osb.provisioning.subscription.model.SubscriptionEntry;
import com.liferay.osb.provisioning.subscription.service.SubscriptionEntryLocalService;
import com.liferay.osb.provisioning.util.CustomerPortalRelease;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true, service = LicenseKeySubscriptionMessageListener.class
)
public class LicenseKeySubscriptionMessageListener extends BaseMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, "0 0 0 * * ?");

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
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

	@Reference
	private AccountWebService _accountWebService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private ContactWebService _contactWebService;

	@Reference
	private CustomerPortalRelease _customerPortalRelease;

	@Reference
	private LicenseKeyLocalService _licenseKeyLocalService;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private SubscriptionEntryLocalService _subscriptionEntryLocalService;

	@Reference
	private TriggerFactory _triggerFactory;

}
