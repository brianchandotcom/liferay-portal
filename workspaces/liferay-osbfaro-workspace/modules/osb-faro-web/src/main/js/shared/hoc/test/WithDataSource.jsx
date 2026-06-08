/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {renderWithStore} from '~/test/mock-store';

import withDataSource from '../WithDataSource';

jest.unmock('react-dom');

jest.mock(
	'~/shared/hoc/WithAction',
	() => () => (wrappedComponent) => wrappedComponent
);

describe('WithDataSource', () => {
	it('passes dataSource to the WrappedComponent', () => {
		let result = null;

		const MockComponent = (props) => {
			result = props;

			return null;
		};

		const WrappedComponent = withDataSource(MockComponent);

		renderWithStore(WrappedComponent, {
			dataSource: 'fooDataSource',
			id: 'test',
		});

		expect(result.dataSource).toBe('fooDataSource');
	});
});
