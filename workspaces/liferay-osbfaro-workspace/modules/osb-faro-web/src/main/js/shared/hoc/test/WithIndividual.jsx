/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {renderWithStore} from '~/test/mock-store';

import withIndividual from '../WithIndividual';

jest.unmock('react-dom');

jest.mock(
	'~/shared/hoc/WithAction',
	() => () => (wrappedComponent) => wrappedComponent
);

describe('WithIndividual', () => {
	it('passes the individual to the WrappedComponent', () => {
		let result = null;

		const MockComponent = (props) => {
			result = props;

			return null;
		};

		const WrappedComponent = withIndividual(MockComponent);

		renderWithStore(WrappedComponent, {
			id: 'test',
			individual: 'fooIndividual',
		});

		expect(result.individual).toBe('fooIndividual');
	});
});
