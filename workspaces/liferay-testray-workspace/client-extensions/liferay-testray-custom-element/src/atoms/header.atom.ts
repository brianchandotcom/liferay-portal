/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {atom} from 'jotai';
import i18n from '~/i18n';
import {Action} from '~/types';

export type HeaderActions = {
	actions: Action[];
	item?: any;
	mutate?: any;
};

type DropdownItem = {
	divider?: boolean;
	icon?: string;
	label: string;
	onClick?: () => void;
	path?: string;
};

type DropdownSection = {
	items: DropdownItem[];
	title?: string;
};

export type Dropdown = DropdownSection[];

export type HeaderTabs = {
	active: boolean;
	path: string;
	title: string;
};

export type HeaderTitle = {
	category?: string;
	path?: string;
	title: string;
};

export const headerAtom = {
	dropdown: atom<Dropdown>([]),
	headerActions: atom<HeaderActions>({actions: []}),
	heading: atom<HeaderTitle[]>([
		{
			category: i18n.translate('project'),
			title: i18n.translate('project-directory'),
		},
	]),
	symbol: atom<string>(''),
	tabs: atom<HeaderTabs[]>([]),
};
