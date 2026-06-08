/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const addDistributionTab = jest.fn(() =>
	Promise.resolve({
		distributionCardTabPreferencesMap: {
			tab1: {context: 'demographics', id: 'tab1', title: 'Tab 1'},
			tab2: {context: 'demographics', id: 'tab2', title: 'Tab 2'},
		},
		order: ['tab1', 'tab2'],
	})
);

export const removeDistributionTab = jest.fn(() =>
	Promise.resolve({distributionCardTabPreferencesMap: {}, order: []})
);

export const fetchDistributionTabs = jest.fn(() =>
	Promise.resolve({
		distributionCardTabPreferencesMap: {
			tab1: {context: 'demographics', id: 'tab1', title: 'Tab 1'},
		},
		order: ['tab1'],
	})
);

export const fetchDefaultChannelId = jest.fn(() =>
	Promise.resolve({defaultChannelId: '123456'})
);

export const fetchUpgradeModalSeen = jest.fn(() => Promise.resolve(false));

export const updateDefaultChannelId = jest.fn(() =>
	Promise.resolve({defaultChannelId: '123456'})
);

export const updateUpgradeModalSeen = jest.fn(() => Promise.resolve(true));
