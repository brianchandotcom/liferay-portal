/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Account, EntityLayout, Individual, Project, Segment} from '../index';

describe('Records', () => {
	it('returns an Account Record', () => {
		expect(new Account()).toMatchSnapshot();
	});

	it('returns an Individual Record', () => {
		expect(new Individual()).toMatchSnapshot();
	});

	it('returns a Segment Record', () => {
		expect(new Segment()).toMatchSnapshot();
	});

	it('returns an EntityLayout Record', () => {
		expect(new EntityLayout()).toMatchSnapshot();
	});

	it('returns a Project Record', () => {
		expect(new Project()).toMatchSnapshot();
	});
});
