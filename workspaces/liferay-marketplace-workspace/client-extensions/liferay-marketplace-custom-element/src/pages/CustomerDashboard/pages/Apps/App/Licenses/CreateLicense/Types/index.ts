/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum StepCreateLicense {
	LICENSE_KEY_DETAILS = 'licenseKeyDetails',
	SUBSCRIPTION = 'subscription',
}

export type CreateLicenseForm = {
	description: string;
	hostname: string;
	ipAddress: string;
	licenseKeyData: any;
	macAddress: string;
	subscription: any;
};

type StepsInformationProps = {
	backStep: string;
	nextStep: string;
	stepTitle: string;
	title: string;
};

export type StepsInformation = {
	[StepCreateLicense.LICENSE_KEY_DETAILS]: StepsInformationProps;
	[StepCreateLicense.SUBSCRIPTION]: StepsInformationProps;
};
