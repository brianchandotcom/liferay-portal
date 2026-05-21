/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.provisioning.subscription.service.impl;

import com.liferay.osb.provisioning.subscription.exception.NoSuchSubscriptionEntryException;
import com.liferay.osb.provisioning.subscription.exception.SubscriptionEntryClassNameIdException;
import com.liferay.osb.provisioning.subscription.exception.SubscriptionEntryClassPKException;
import com.liferay.osb.provisioning.subscription.exception.SubscriptionEntryContactUuidException;
import com.liferay.osb.provisioning.subscription.model.SubscriptionEntry;
import com.liferay.osb.provisioning.subscription.service.base.SubscriptionEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jenny Chen
 */
@Component(
	property = "model.class.name=com.liferay.osb.provisioning.subscription.model.SubscriptionEntry",
	service = AopService.class
)
public class SubscriptionEntryLocalServiceImpl
	extends SubscriptionEntryLocalServiceBaseImpl {

	public SubscriptionEntry addSubscriptionEntry(
			long classNameId, long classPK, String contactUuid)
		throws Exception {

		Date now = new Date();

		validate(classNameId, classPK, contactUuid);

		SubscriptionEntry subscriptionEntry =
			subscriptionEntryPersistence.fetchByC_C_CU(
				classNameId, classPK, contactUuid);

		if (subscriptionEntry != null) {
			return subscriptionEntry;
		}

		long subscriptionEntryId = counterLocalService.increment();

		subscriptionEntry = subscriptionEntryPersistence.create(
			subscriptionEntryId);

		subscriptionEntry.setCreateDate(now);
		subscriptionEntry.setClassNameId(classNameId);
		subscriptionEntry.setClassPK(classPK);
		subscriptionEntry.setContactUuid(contactUuid);

		return subscriptionEntryPersistence.update(subscriptionEntry);
	}

	public void deleteSubscriptionEntry(
			long classNameId, long classPK, String contactUuid)
		throws NoSuchSubscriptionEntryException {

		subscriptionEntryPersistence.removeByC_C_CU(
			classNameId, classPK, contactUuid);
	}

	public SubscriptionEntry fetchSubscriptionEntry(
		long classNameId, long classPK, String contactUuid) {

		return subscriptionEntryPersistence.fetchByC_C_CU(
			classNameId, classPK, contactUuid);
	}

	public List<SubscriptionEntry> getSubscriptionEntries(
		long classNameId, long classPK) {

		return subscriptionEntryPersistence.findByC_C(classNameId, classPK);
	}

	public List<SubscriptionEntry> getSubscriptionEntries(
		long classNameId, String contactUuid) {

		return subscriptionEntryPersistence.findByC_CU(
			classNameId, contactUuid);
	}

	public List<SubscriptionEntry> getSubscriptionEntries(String contactUuid) {
		return subscriptionEntryPersistence.findByContactUuid(contactUuid);
	}

	public SubscriptionEntry getSubscriptionEntry(
			long classNameId, long classPK, String contactUuid)
		throws NoSuchSubscriptionEntryException {

		return subscriptionEntryPersistence.findByC_C_CU(
			classNameId, classPK, contactUuid);
	}

	protected void validate(long classNameId, long classPK, String contactUuid)
		throws Exception {

		if (classNameId <= 0) {
			throw new SubscriptionEntryClassNameIdException();
		}

		if (classPK <= 0) {
			throw new SubscriptionEntryClassPKException();
		}

		if (Validator.isNull(contactUuid)) {
			throw new SubscriptionEntryContactUuidException();
		}
	}

}
