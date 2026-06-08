/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import Name from '../Name';

jest.unmock('react-dom');

const FilledComponent = (props) => (
	<Name
		data={{id: 'test', name: 'foo'}}
		routeFn={({data: {id}}) => `/foo/${id}`}
		{...props}
	/>
);

describe('Name', () => {
	it('renders', () => {
		const {container} = render(
			<Name data={{name: 'foo'}} renderSecondaryInfo={() => 'bar'} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders without a link in the name if disabled is true', () => {
		const {container, getAllByText} = render(<FilledComponent disabled />);

		expect(container.querySelector('a')).toBeFalsy();
		expect(getAllByText('foo')).toBeTruthy();
	});

	it('renders the name as a link if a route is passed', () => {
		const {container} = render(
			<StaticRouter>
				<FilledComponent />
			</StaticRouter>
		);

		expect(container.querySelector('a')).toHaveAttribute(
			'href',
			'/foo/test'
		);
	});

	it('renders a link even if it has no asset title', () => {
		const {container} = render(
			<StaticRouter>
				<FilledComponent data={{assetId: '123', name: 'foo'}} />
			</StaticRouter>
		);

		expect(container.querySelector('a')).toHaveAttribute(
			'href',
			'/foo/undefined'
		);
	});

	it('renders with secondary info', () => {
		const {container} = render(
			<StaticRouter>
				<FilledComponent renderSecondaryInfo={() => 'bar'} />
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with an icon', () => {
		const {container, getByText} = render(
			<Name
				data={{id: 'test', name: 'foo'}}
				renderIcon={() => <div>foo icon</div>}
			/>
		);

		expect(container.querySelector('.icon-container')).toBeTruthy();
		expect(getByText('foo icon')).toBeTruthy();
	});

	it('renders using the nameKey', () => {
		const {getByText} = render(
			<Name data={{id: 'test', title: 'foo'}} nameKey="title" />
		);

		expect(getByText('foo')).toBeTruthy();
	});

	it('renders the display name in TextTruncate if the tooltip prop is true', () => {
		const {getByText} = render(
			<Name
				data={{id: 'test', name: 'Name in text-truncate', title: 'foo'}}
				tooltip
			/>
		);

		expect(getByText('Name in text-truncate')).toHaveClass('text-truncate');
	});
});
