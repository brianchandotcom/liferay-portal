/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {KeyedMutator} from 'swr';

import {APIResponse} from '../services/rest';

export type ActionsHookParameter = {isHeaderActions?: boolean};

export type Action<T = any> = {
	action?: (item: T, mutate: KeyedMutator<APIResponse<T> | T>) => void;
	disabled?: boolean;
	hidden?: ((item: T) => boolean) | boolean;
	icon?: string;
	name: ((item: T) => string) | string;
	permission?: keyof typeof TestrayActions | boolean;
};

export type ActionMap<M extends {[index: string]: any}> = {
	[Key in keyof M]: M[Key] extends undefined
		? {
				type: Key;
		  }
		: {
				payload: M[Key];
				type: Key;
		  };
};

export type Actions = keyof typeof TestrayActions;

export enum DescriptionType {
	MARKDOWN = 'markdown',
	PLAINTEXT = 'plaintext',
}

export enum SortOption {
	ASC = 'ASC',
	DESC = 'DESC',
}

export type SortDirection = keyof typeof SortOption;

export enum TestrayActions {
	'CREATE',
	'DELETE',
	'INDEX',
	'PERMISSIONS',
	'UPDATE',
}
