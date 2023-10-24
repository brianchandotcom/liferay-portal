/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum StepCreateLicense {
	LICENSE_KEY_DETAILS = 'licenseKeyDetails',
	SUBSCRIPTION = 'subscription',
}

export type CreateLicenseForm = {
	IP: string;
	description: string;
	hostName: string;
	macAddresses: string;
	subscription: string;
};

export type ProductCardProps = {
	licenseKeyData: {[key: string]: string};
	product: {
		attachments: [];
		name: {
			en_US: string;
		};
		productSpecifications: [];
		skus: {
			price: number;
			sku: string;
			skuOptions: [];
		}[];
	};
	productCreatorAccount: {logoURL: undefined; name: string};
	userAccount: {[key: string]: string};
};

export type StepsInformationProps = {
	backStep: string;
	component: JSX.Element;
	nextStep: string;
	stepTitle: string;
	title: string;
};

export type StepsInformation = {
	[StepCreateLicense.LICENSE_KEY_DETAILS]: StepsInformationProps;
	[StepCreateLicense.SUBSCRIPTION]: StepsInformationProps;
};
