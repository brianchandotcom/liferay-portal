/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

export enum InstallStatus {
	EXPIRED = 'Expired',
	INSTALLED = 'Installed',
	IN_PROGRESS = 'In Progress',
	READY_TO_INSTALL = 'Ready to Install',
}

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

export type Provisioning = {
	deployments: Deployment[];
	orderItemId: number;
	quantity: number;
	shippedQuantity: number;
	sku: string;
};

export type Deployment = {
	appId: string;
	createdAt: number;
	id: string;
	loading: boolean;
	orderId: number;
	projectId: string;
};

export type StepsInformation = {
	[StepCloudInstallation.ENVIRONMENT]: StepsInformationProps;
	[StepCloudInstallation.INSTALLATION]: StepsInformationProps;
	[StepCloudInstallation.PROJECT]: StepsInformationProps;
	[StepCloudInstallation.SUCCESS]: StepsInformationProps;
};
