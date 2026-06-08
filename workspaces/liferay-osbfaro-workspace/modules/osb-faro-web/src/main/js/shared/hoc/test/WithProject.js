/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {renderWithStore} from '~/test/mock-store';

import withProject from '../WithProject';

jest.unmock('react-dom');

jest.mock(
	'~/shared/hoc/WithAction',
	() => () => (wrappedComponent) => wrappedComponent
);

describe('WithProject', () => {
	it('passes the project to the WrappedComponent', () => {
		let result = null;

		const MockComponent = (props) => {
			result = props;

			return null;
		};

		const WrappedComponent = withProject(MockComponent);

		renderWithStore(WrappedComponent, {
			id: 'test',
			project: 'fooProject',
		});

		expect(result.project).toBe('fooProject');
	});
});
