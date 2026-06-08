/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getEntityDisplay} from '../getEntityDisplay';
import {Entity} from '../types';

describe('getEntityDisplay', () => {
	it('returns Accounts label and briefcase icon', () => {
		expect(getEntityDisplay(Entity.Accounts)).toEqual({
			icon: 'briefcase',
			label: 'Accounts',
		});
	});

	it('returns Events label and click icon', () => {
		expect(getEntityDisplay(Entity.Events)).toEqual({
			icon: 'click',
			label: 'Events',
		});
	});

	it('returns Individuals label and user icon', () => {
		expect(getEntityDisplay(Entity.Individuals)).toEqual({
			icon: 'user',
			label: 'Individuals',
		});
	});

	it('returns Sites label and sites icon', () => {
		expect(getEntityDisplay(Entity.Sites)).toEqual({
			icon: 'sites',
			label: 'Sites',
		});
	});

	it('returns Users label and users icon', () => {
		expect(getEntityDisplay(Entity.Users)).toEqual({
			icon: 'users',
			label: 'Users',
		});
	});
});
