/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/i18n';

export enum BuildStatuses {
	ACTIVATED = 'ACTIVATED',
	ARCHIVED = 'ARCHIVED',
	DEACTIVATED = 'DEACTIVATED',
}

export enum BuildImportStatuses {
	DONE = 'DONE',
	INPROGRESS = 'INPROGRESS',
	PENDING = 'PENDING',
}

export enum CaseResultStatuses {
	BLOCKED = 'BLOCKED',
	DID_NOT_RUN = 'DIDNOTRUN',
	FAILED = 'FAILED',
	IN_PROGRESS = 'INPROGRESS',
	INCOMPLETE = 'INCOMPLETE',
	PASSED = 'PASSED',
	TEST_FIX = 'TESTFIX',
	UNTESTED = 'UNTESTED',
}

export enum DispatchTriggerStatuses {
	COMPLETED = 'COMPLETED',
	FAILED = 'FAILED',
	INPROGRESS = 'INPROGRESS',
	SCHEDULED = 'SCHEDULED',
}

export enum TaskStatuses {
	ABANDONED = 'ABANDONED',
	COMPLETE = 'COMPLETE',
	IN_ANALYSIS = 'INANALYSIS',
	OPEN = 'OPEN',
	PROCESSING = 'PROCESSING',
}

export enum SubtaskStatuses {
	COMPLETE = 'COMPLETE',
	IN_ANALYSIS = 'INANALYSIS',
	MERGED = 'MERGED',
	OPEN = 'OPEN',
}

export const filterStatuses: {[key: string]: string} = {
	ABANDONED: i18n.translate('abandoned'),
	BLOCKED: i18n.translate('blocked'),
	COMPLETE: i18n.translate('complete'),
	FAILED: i18n.translate('failed'),
	INANALYSIS: i18n.translate('in-analysis'),
	INPROGRESS: i18n.translate('in-progress'),
	OPEN: i18n.translate('open'),
	PASSED: i18n.translate('passed'),
	TESTFIX: i18n.translate('test-fix'),
	UNTESTED: i18n.translate('untested'),
};
