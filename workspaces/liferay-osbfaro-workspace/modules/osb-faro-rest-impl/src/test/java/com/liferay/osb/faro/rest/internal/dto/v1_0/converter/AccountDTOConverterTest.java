/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.Account;
import com.liferay.osb.faro.rest.dto.v1_0.CalculatedField;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leslie Wong
 */
public class AccountDTOConverterTest {

	@Test
	public void testToDTO() {
		String accountName = RandomTestUtil.randomString();
		String accountType = RandomTestUtil.randomString();
		Long activitiesCount = RandomTestUtil.randomLong();
		String activityStatus = RandomTestUtil.randomString();
		Double annualRevenue = RandomTestUtil.randomDouble();
		com.liferay.osb.faro.engine.client.model.Account.CalculatedField
			engineClientCalculatedField = _createCalculatedField(
				RandomTestUtil.nextDate(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());
		Date firstActivityDate = RandomTestUtil.nextDate();
		String id = RandomTestUtil.randomString();
		Date lastActivityDate = RandomTestUtil.nextDate();
		Integer numberOfEmployees = RandomTestUtil.randomInt();
		String website = RandomTestUtil.randomString();

		Account account = _toDTO(
			accountName, accountType, activitiesCount, activityStatus,
			annualRevenue,
			Collections.singletonList(engineClientCalculatedField),
			firstActivityDate, id, lastActivityDate, numberOfEmployees,
			website);

		Assert.assertEquals(accountName, account.getAccountName());
		Assert.assertEquals(accountType, account.getAccountType());
		Assert.assertEquals(activitiesCount, account.getActivitiesCount());
		Assert.assertEquals(activityStatus, account.getActivityStatus());
		Assert.assertEquals(annualRevenue, account.getAnnualRevenue());

		CalculatedField[] calculatedFields = account.getCalculatedFields();

		Assert.assertEquals(
			Arrays.toString(calculatedFields), 1, calculatedFields.length);

		CalculatedField calculatedField = calculatedFields[0];

		Assert.assertEquals(
			engineClientCalculatedField.getModifiedDate(),
			calculatedField.getDateModified());
		Assert.assertEquals(
			engineClientCalculatedField.getName(), calculatedField.getName());
		Assert.assertEquals(
			engineClientCalculatedField.getNamespace(),
			calculatedField.getNamespace());
		Assert.assertEquals(
			engineClientCalculatedField.getValue(), calculatedField.getValue());

		Assert.assertEquals(firstActivityDate, account.getFirstActivityDate());
		Assert.assertEquals(id, account.getId());
		Assert.assertEquals(lastActivityDate, account.getLastActivityDate());
		Assert.assertNull(account.getLifecycleStage());
		Assert.assertEquals(numberOfEmployees, account.getNumberOfEmployees());
		Assert.assertEquals(website, account.getWebsite());

		Assert.assertNull(
			_accountDTOConverter.toDTO(
				new FaroDTOConverterContext(false, null, null), null));
	}

	private com.liferay.osb.faro.engine.client.model.Account.CalculatedField
		_createCalculatedField(
			Date modifiedDate, String name, String namespace, String value) {

		com.liferay.osb.faro.engine.client.model.Account.CalculatedField
			calculatedField =
				new com.liferay.osb.faro.engine.client.model.Account.
					CalculatedField();

		calculatedField.setModifiedDate(modifiedDate);
		calculatedField.setName(name);
		calculatedField.setNamespace(namespace);
		calculatedField.setValue(value);

		return calculatedField;
	}

	private Account _toDTO(
		String accountName, String accountType, Long activitiesCount,
		String activityStatus, Double annualRevenue,
		List<com.liferay.osb.faro.engine.client.model.Account.CalculatedField>
			calculatedFields,
		Date firstActivityDate, String id, Date lastActivityDate,
		Integer numberOfEmployees, String website) {

		com.liferay.osb.faro.engine.client.model.Account account =
			new com.liferay.osb.faro.engine.client.model.Account();

		account.setAccountName(accountName);
		account.setAccountType(accountType);
		account.setActivitiesCount(activitiesCount);
		account.setActivityStatus(activityStatus);
		account.setAnnualRevenue(annualRevenue);
		account.setCalculatedFields(calculatedFields);
		account.setFirstActivityDate(firstActivityDate);
		account.setId(id);
		account.setLastActivityDate(lastActivityDate);
		account.setNumberOfEmployees(numberOfEmployees);
		account.setWebsite(website);

		return _accountDTOConverter.toDTO(
			new FaroDTOConverterContext(false, id, null), account);
	}

	private final AccountDTOConverter _accountDTOConverter =
		new AccountDTOConverter();

}