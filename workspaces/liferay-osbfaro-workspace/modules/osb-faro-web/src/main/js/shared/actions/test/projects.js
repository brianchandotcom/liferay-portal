/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import {
	configureProject,
	createProject,
	createTrialProject,
	fetchProject,
	fetchProjectViaCorpProjectUuid,
	fetchProjects,
	updateProject,
} from '../projects';

describe('Projects Actions', () => {
	describe('createProject', () => {
		it('returns an action', () => {
			const action = createProject();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('configureProject', () => {
		it('returns an action', () => {
			const action = configureProject({
				emailAddressDomains: [],
				friendlyURL: 'ggwp',
				groupId: '123',
				name: 'Configure',
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('createTrialProject', () => {
		it('returns an action', () => {
			const action = createTrialProject();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchProject', () => {
		it('returns an action', () => {
			const action = fetchProject({groupId: '23'});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchProjects', () => {
		it('returns an action', () => {
			const action = fetchProjects();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchProjectViaCorpProjectUuid', () => {
		it('returns an action', () => {
			const action = fetchProjectViaCorpProjectUuid({
				corpProjectUuid: '23-44',
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('updateProject', () => {
		it('returns an action', () => {
			const action = updateProject({
				emailAddressDomains: [],
				friendlyURL: 'bananas',
				groupId: '23',
				name: 'Test Test',
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
