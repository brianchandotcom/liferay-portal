/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
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
