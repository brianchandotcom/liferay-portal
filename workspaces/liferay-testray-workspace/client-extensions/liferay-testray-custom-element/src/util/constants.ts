/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../i18n';
import {BuildImportStatuses, TaskStatuses} from './statuses';

export enum CaseResultStatuses {
	BLOCKED = 'caseResultBlocked',
	FAILED = 'caseResultFailed',
	INCOMPLETE = 'caseResultIncomplete',
	INPROGRESS = 'caseResultInProgress',
	PASSED = 'caseResultPassed',
	TEST_FIX = 'caseResultTestFix',
	UNTESTED = 'caseResultUntested',
}

export enum RoleTypes {
	REGULAR = 1,
	SITE = 2,
	ORGANIZATION = 3,
	ASSET_LIBRARY = 5,
}

export enum Statuses {
	PASSED = 'PASSED',
	FAILED = 'FAILED',
	BLOCKED = 'BLOCKED',
	TEST_FIX = 'TESTFIX',
	INCOMPLETE = 'INCOMPLETE',
	SELF = 'SELF COMPLETED',
	OTHER = 'OTHERS COMPLETED',
}

export enum StatusesProgressScore {
	SELF = 'SELF COMPLETED',
	OTHER = 'OTHERS COMPLETED',
	INCOMPLETE = 'INCOMPLETE',
}

export enum TestrayRole {
	TESTRAY_ADMINISTRATOR = 'Testray Administrator',
	TESTRAY_ANALYST = 'Testray Analyst',
	TESTRAY_LEAD = 'Testray Lead',
}

export const chartClassNames = {
	[Statuses.BLOCKED]: 'blocked',
	[Statuses.FAILED]: 'failed',
	[Statuses.INCOMPLETE]: 'test-incomplete',
	[Statuses.PASSED]: 'passed',
	[Statuses.SELF]: 'self-completed',
	[Statuses.TEST_FIX]: 'testfix',
	[Statuses.OTHER]: 'others-completed',
};

export const DATA_COLORS = {
	'metrics.blocked': '#F8D72E',
	'metrics.failed': '#E73A45',
	'metrics.incomplete': '#E3E9EE',
	'metrics.passed': '#3CD587',
	'metrics.testfix': '#59BBFC',
};

export const chartColors = {
	[Statuses.PASSED]: DATA_COLORS['metrics.passed'],
	[Statuses.FAILED]: DATA_COLORS['metrics.failed'],
	[Statuses.BLOCKED]: DATA_COLORS['metrics.blocked'],
	[Statuses.TEST_FIX]: DATA_COLORS['metrics.testfix'],
	[Statuses.INCOMPLETE]: DATA_COLORS['metrics.incomplete'],
};

export const LABEL_GREATER_THAN_99 = '> 99';
export const LABEL_LESS_THAN_1 = '< 1';

export const PAGINATION_DELTA = [20, 50, 75, 100, 150];

export const PAGINATION = {
	delta: PAGINATION_DELTA,
	ellipsisBuffer: 3,
};

export const ACTIONS = {
	CREATE: 'CREATE',
	UPDATE: 'UPDATE',
};

type AlertProperties = {
	[key: string]: {
		color: string;
		displayType: string;
		label: string;
		text: string;
	};
};

export const testrayBuildAlertProperties: AlertProperties = {
	[BuildImportStatuses.DONE]: {
		color: 'label-chart passed',
		displayType: 'success',
		label: i18n.translate('done'),
		text: i18n.translate('this-build-has-finished-it-import-process'),
	},
	[BuildImportStatuses.INPROGRESS]: {
		color: 'label-chart-in-analysis',
		displayType: 'warning',
		label: i18n.translate('in-progress'),
		text: i18n.translate('test-results-are-being-imported-into-this-build'),
	},
	[BuildImportStatuses.PENDING]: {
		color: 'label-chart open',
		displayType: 'secondary',
		label: i18n.translate('pending'),
		text: i18n.translate('test-results-are-queued-to-be-imported'),
	},
};

export const testrayTaskAlertProperties: AlertProperties = {
	[TaskStatuses.ABANDONED]: {
		color: 'label-secondary',
		displayType: 'secondary',
		label: i18n.translate('abandoned'),
		text: i18n.translate('this-builds-task-has-been-abandoned'),
	},
	[TaskStatuses.COMPLETE]: {
		color: 'label-primary',
		displayType: 'primary',
		label: i18n.translate('complete'),
		text: i18n.translate('this-build-has-been-analyzed'),
	},
	[TaskStatuses.IN_ANALYSIS]: {
		color: 'label-chart-in-analysis',
		displayType: 'warning',
		label: i18n.translate('in-analysis'),
		text: i18n.translate('this-build-is-currently-in-analysis'),
	},
	[TaskStatuses.OPEN]: {
		color: 'label-secondary',
		displayType: 'secondary',
		label: i18n.translate('open'),
		text: i18n.translate('this-build-is-currently-in-open'),
	},
	[TaskStatuses.PROCESSING]: {
		color: 'label-info',
		displayType: 'info',
		label: i18n.translate('processing'),
		text: i18n.translate('this-build-is-currently-in-processing'),
	},
};
