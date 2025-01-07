/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UseFormSetValue} from 'react-hook-form';
import {z} from 'zod';

import zodSchema from '../../../schema/zod';
import {ConsoleUserProject} from '../../../services/oauth/types';
import {ConsoleUserProjectWithExtension} from '../../CustomerDashboard/pages/Apps/App/CloudProvisioning/pages/CloudProvisioningOutlet';

export type AccountSelectionStepType = {
	myUserAccount: UserAccount;
	selectedAccount: Account;
	setSelectedAccount: any;
};

export type CongratulationsStepType = {
	myUserAccount: UserAccount;
	selectedAccount: Account;
};

export type EnvironmentSelectionStepType = {
	environment: {isExtensionEnvironment: boolean; projectId: string};
	myUserAccount: UserAccount;
	project: ConsoleUserProjectWithExtension;
	selectedAccount: Account;
	setValue: UseFormSetValue<z.infer<typeof zodSchema.installProductSchema>>;
};

export type ProjectSelectionStepType = {
	myUserAccount: UserAccount;
	projects: ConsoleUserProjectWithExtension[];
	selProject: ConsoleUserProject;
	selectedAccount: Account;
	setValue: UseFormSetValue<z.infer<typeof zodSchema.installProductSchema>>;
};
