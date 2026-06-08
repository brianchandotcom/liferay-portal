/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {MemoryRouter} from 'react-router-dom';
import {useQueryRangeSelectors} from '~/shared/hooks/useQueryRangeSelectors';

jest.unmock('react-dom');

const DefaultComponent = ({rangeEnd, rangeKey, rangeStart}) => (
	<>
		{`rangeEnd ${rangeEnd}`}

		{`rangeKey: ${rangeKey}`}

		{`rangeStart: ${rangeStart}`}
	</>
);
const WrapperComponent = ({children, queryString = ''}) => (
	<MemoryRouter
		initialEntries={[
			`/workspace/23/321321/contacts/accounts/123123/interests/test${queryString}`,
		]}
	>
		{children}
	</MemoryRouter>
);

describe('useQueryRangeSelectors', () => {
	it('returns initial values', () => {
		const TestComponent = () => {
			const queryRangeSelectorsParams = useQueryRangeSelectors({
				rangeEnd: 123123,
				rangeKey: 30,
				rangeStart: 321321,
			});

			return <DefaultComponent {...queryRangeSelectorsParams} />;
		};

		const {container} = render(
			<WrapperComponent>
				<TestComponent />
			</WrapperComponent>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('returns RangeSelectors params from query params values', () => {
		const TestComponent = () => {
			const queryRangeSelectorsParams = useQueryRangeSelectors({
				rangeEnd: 123123,
				rangeKey: 30,
				rangeStart: 321321,
			});

			return <DefaultComponent {...queryRangeSelectorsParams} />;
		};

		const {container} = render(
			<WrapperComponent queryString="?rangeKey=90&rangeEnd=9087&rangeStart=54546">
				<TestComponent />
			</WrapperComponent>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
