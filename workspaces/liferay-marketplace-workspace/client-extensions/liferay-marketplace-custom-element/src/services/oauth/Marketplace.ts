/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CreateFilters from '../../core/CreateFilters';
import {
	FilterSchemaOption,
	filterSchema as filterSchemas,
} from '../../schema/filters';
import {downloadFile} from '../../utils/file';
import {MarketplaceSpringBootOAuth2} from './OAuth2Client';

class MarketplaceOAuth2 extends MarketplaceSpringBootOAuth2 {
	async createAccount(accountData: any): Promise<Account> {
		const formData = new FormData();

		if (accountData.accountImage) {
			const blob = new Blob([accountData.accountImage]);
			formData.append('file', blob, accountData.accountImage.name);
		}

		const data = {
			customFields: [
				{
					customValue: {
						data: accountData.emailAddress,
					},
					name: 'Contact Email',
				},
			],
			name: accountData.accountName,
			postalAddresses: [
				{
					addressCountry: accountData.billingAddress.country,
					addressLocality: accountData.billingAddress.city,
					addressRegion:
						accountData.billingAddress.regionISOCode ?? '',
					name: accountData.billingAddress.name,
					phoneNumber: accountData.billingAddress.phoneNumber,
					postalCode: accountData.billingAddress.zip,
					primary: true,
					streetAddressLine1: accountData.billingAddress.street1,
					streetAddressLine2:
						accountData.billingAddress.street2 ?? '',
				},
			],
			taxId: accountData.taxNumber,
			type: accountData.accountType,
		};

		formData.append('account', JSON.stringify(data));

		const account = await this.post<Account>(`/account`, formData, {
			earlyReturn: true,
		});

		return account;
	}

	async downloadOrderReport(
		filter: {
			[key: string]: string;
		},
		filterSchema?: FilterSchemaOption
	) {
		const searchBuilder = CreateFilters.createFilter({
			appliedFilter: filter,
			filterSchema: (filterSchemas as any)[filterSchema ?? ''],
		});

		const response = await this.get<Response>(
			`/orders/export?filters=${searchBuilder}`,
			{earlyReturn: true}
		);

		await downloadFile('orders.csv', response);
	}

	async taxCalculate(orderId: number): Promise<Order> {
		const order = await this.post<Order>(
			`/tax-calculate/${orderId}`,
			{},
			{earlyReturn: true}
		);

		return order;
	}
}

const marketplaceOAuth2 = new MarketplaceOAuth2('/marketplace');

export default marketplaceOAuth2;
