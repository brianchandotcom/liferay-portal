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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leslie Wong
 */
public class AccountDTOConverterTest {

	@Test
	public void testToDTO() {
		com.liferay.osb.faro.engine.client.model.Account engineClientAccount =
			new com.liferay.osb.faro.engine.client.model.Account();

		com.liferay.osb.faro.engine.client.model.Account.CalculatedField
			engineClientCalculatedField =
				new com.liferay.osb.faro.engine.client.model.Account.
					CalculatedField();

		engineClientCalculatedField.setModifiedDate(RandomTestUtil.nextDate());
		engineClientCalculatedField.setName(RandomTestUtil.randomString());
		engineClientCalculatedField.setNamespace(RandomTestUtil.randomString());
		engineClientCalculatedField.setValue(RandomTestUtil.randomString());

		engineClientAccount.setAccountName(RandomTestUtil.randomString());
		engineClientAccount.setAccountType(RandomTestUtil.randomString());
		engineClientAccount.setActivitiesCount(RandomTestUtil.randomLong());
		engineClientAccount.setActivityStatus(RandomTestUtil.randomString());
		engineClientAccount.setAnnualRevenue(RandomTestUtil.randomDouble());
		engineClientAccount.setCalculatedFields(
			Collections.singletonList(engineClientCalculatedField));
		engineClientAccount.setFirstActivityDate(RandomTestUtil.nextDate());
		engineClientAccount.setId(RandomTestUtil.randomString());
		engineClientAccount.setLastActivityDate(RandomTestUtil.nextDate());
		engineClientAccount.setNumberOfEmployees(RandomTestUtil.randomInt());
		engineClientAccount.setWebsite(RandomTestUtil.randomString());

		Account account = _accountDTOConverter.toDTO(
			new FaroDTOConverterContext(
				false, engineClientAccount.getId(), null),
			engineClientAccount);

		Assert.assertEquals(
			engineClientAccount.getAccountName(), account.getAccountName());
		Assert.assertEquals(
			engineClientAccount.getAccountType(), account.getAccountType());
		Assert.assertEquals(
			engineClientAccount.getActivitiesCount(),
			account.getActivitiesCount());
		Assert.assertEquals(
			engineClientAccount.getActivityStatus(),
			account.getActivityStatus());
		Assert.assertEquals(
			engineClientAccount.getAnnualRevenue(), account.getAnnualRevenue());

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

		Assert.assertEquals(
			engineClientAccount.getFirstActivityDate(),
			account.getFirstActivityDate());
		Assert.assertEquals(engineClientAccount.getId(), account.getId());
		Assert.assertEquals(
			engineClientAccount.getLastActivityDate(),
			account.getLastActivityDate());
		Assert.assertNull(account.getLifecycleStage());
		Assert.assertEquals(
			engineClientAccount.getNumberOfEmployees(),
			account.getNumberOfEmployees());
		Assert.assertEquals(
			engineClientAccount.getWebsite(), account.getWebsite());

		Assert.assertNull(
			_accountDTOConverter.toDTO(
				new FaroDTOConverterContext(false, null, null), null));
	}

	private final AccountDTOConverter _accountDTOConverter =
		new AccountDTOConverter();

}