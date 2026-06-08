/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import * as actions from '../preferences';

describe('Preferences Actions', () => {
	describe('addDistributionTabs', () => {
		it('returns an action', () => {
			const action = actions.addDistributionTab({
				distributionKey: '123',
				distributionTab: {},
				distributionTabId: 'Test Tab',
				groupId: '321',
				id: '123',
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchDistributionTabs', () => {
		it('returns an action', () => {
			const action = actions.fetchDistributionTabs({
				distributionKey: '321',
				groupId: '123',
				id: '321',
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('removeDistributionTabs', () => {
		it('returns an action', () => {
			const action = actions.removeDistributionTab({
				distributionKey: '123',
				distributionTabId: 'Test Tab',
				groupId: '321',
				id: '123',
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchDefaultChannelId', () => {
		it('returns an action', () => {
			const action = actions.fetchDefaultChannelId('321');

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchUpgradeModalSeen', () => {
		it('returns an action', () => {
			const action = actions.fetchUpgradeModalSeen();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateDefaultChannelId', () => {
		it('returns an action', () => {
			const action = actions.updateDefaultChannelId({
				defaultChannelId: '122',
				groupId: '321',
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateUpgradeModalSeen', () => {
		it('returns an action', () => {
			const action = actions.updateUpgradeModalSeen({
				groupId: '321',
				upgradeModalSeen: true,
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
