/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {MemoryRouter} from 'react-router-dom';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';
import {
	createOrderIOMap,
	getSortFromOrderIOMap,
} from '~/shared/util/pagination';

jest.unmock('react-dom');

const DefaultComponent = ({delta, orderIOMap, page, query}) => {
	const {column, type} = getSortFromOrderIOMap(orderIOMap);

	return (
		<>
			{`delta: ${delta}`}

			{`field: ${column}`}

			{`sortOrder: ${type}`}

			{`page: ${page}`}

			{`query: ${query}`}
		</>
	);
};
const WrapperComponent = ({children, queryString = ''}) => (
	<MemoryRouter
		initialEntries={[
			`/workspace/23/321321/contacts/accounts/123123/interests/test${queryString}`,
		]}
	>
		{children}
	</MemoryRouter>
);

describe('useQueryPagination', () => {
	it('returns initial values', () => {
		const TestComponent = () => {
			const queryPaginationParams = useQueryPagination({
				initialDelta: 6,
				initialOrderIOMap: createOrderIOMap('initial orderIOMap'),
				initialPage: 13,
				initialQuery: 'initial query',
			});

			return <DefaultComponent {...queryPaginationParams} />;
		};

		const {container} = render(
			<WrapperComponent>
				<TestComponent />
			</WrapperComponent>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('returns pagination params from query params values', () => {
		const TestComponent = () => {
			const queryPaginationParams = useQueryPagination({
				initialDelta: 6,
				initialOrderIOMap: createOrderIOMap('initial orderIOMap'),
				initialPage: 13,
				initialQuery: 'initial query',
			});

			return <DefaultComponent {...queryPaginationParams} />;
		};

		const {container} = render(
			<WrapperComponent queryString="?delta=5&field=name&sortOrder=DESC&query=meow&page=1">
				<TestComponent />
			</WrapperComponent>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
