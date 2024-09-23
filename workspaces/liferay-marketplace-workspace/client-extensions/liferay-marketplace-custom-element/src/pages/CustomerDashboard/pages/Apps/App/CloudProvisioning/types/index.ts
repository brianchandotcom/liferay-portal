/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

export enum StepCloudInstallation {
	ENVIRONMENT = 'environment',
	INSTALLATION = 'installation',
	PROJECT = 'project',
	SUCCESS = 'success',
}

export type CreateLicenseForm = {
	description: string;
	hostname: string;
	ipAddress: string;
	licenseKeyData: any;
	macAddress: string;
	subscription: any;
};

export type StepsInformationProps = {
	backStep: string;
	cardContent: ReactNode;
	cardTitle: string;
	footerHelper: ReactNode;
	nextStep: string;
	subTitle: any;
};

export type StepsInformation = {
	[StepCloudInstallation.ENVIRONMENT]: StepsInformationProps;
	[StepCloudInstallation.INSTALLATION]: StepsInformationProps;
	[StepCloudInstallation.PROJECT]: StepsInformationProps;
	[StepCloudInstallation.SUCCESS]: StepsInformationProps;
};
