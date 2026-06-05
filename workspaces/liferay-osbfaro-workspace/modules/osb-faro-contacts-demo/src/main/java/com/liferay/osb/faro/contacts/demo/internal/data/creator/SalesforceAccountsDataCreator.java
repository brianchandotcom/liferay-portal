/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.demo.internal.data.creator;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class SalesforceAccountsDataCreator extends DataCreator {

	public SalesforceAccountsDataCreator(
		ContactsEngineClient contactsEngineClient, FaroProject faroProject,
		String dataSourceId) {

		super(
			contactsEngineClient, faroProject, "osbasahsalesforceraw",
			"Account");

		_dataSourceId = dataSourceId;

		_salesforceAuditEventsDataCreator =
			new SalesforceAuditEventsDataCreator(
				contactsEngineClient, faroProject, "Account");
	}

	@Override
	public void execute() {
		super.execute();

		_salesforceAuditEventsDataCreator.execute();
	}

	@Override
	protected Map<String, Object> doCreate(Object[] params) {
		Map<String, Object> salesforceAccount =
			HashMapBuilder.<String, Object>put(
				"dataSourceId", _dataSourceId
			).put(
				"fields",
				HashMapBuilder.<String, Object>put(
					"AnnualRevenue", number.numberBetween(0, 1000) * 1000
				).put(
					"BillingCity", address.city()
				).put(
					"BillingCountry", address.country()
				).put(
					"BillingPostalCode", address.zipCode()
				).put(
					"BillingState", address.state()
				).put(
					"BillingStreet", address.streetAddress()
				).put(
					"CurrencyIsoCode",
					_currencyIsoCodes.get(
						random.nextInt(_currencyIsoCodes.size()))
				).put(
					"Description", company.catchPhrase()
				).put(
					"Fax", phoneNumber.phoneNumber()
				).put(
					"Industry", company.industry()
				).put(
					"LastModifiedDate", formatDate(new Date())
				).put(
					"Name", company.name()
				).put(
					"NumberOfEmployees", number.numberBetween(1, 100000)
				).put(
					"Ownership", "Private"
				).put(
					"Phone", phoneNumber.phoneNumber()
				).put(
					"ShippingCity", address.city()
				).put(
					"ShippingCountry", address.country()
				).put(
					"ShippingPostalCode", address.zipCode()
				).put(
					"ShippingState", address.state()
				).put(
					"ShippingStreet", address.streetAddress()
				).put(
					"Type", "Customer"
				).put(
					"Website", "https://" + internet.url()
				).put(
					"YearStarted", number.numberBetween(1900, 2019)
				).build()
			).put(
				"id", internet.uuid()
			).build();

		_salesforceAuditEventsDataCreator.create(
			new Object[] {salesforceAccount});

		return salesforceAccount;
	}

	private static final List<String> _currencyIsoCodes = Arrays.asList(
		"CNY", "EUR", "GBP", "USD");

	private final String _dataSourceId;
	private final SalesforceAuditEventsDataCreator
		_salesforceAuditEventsDataCreator;

}