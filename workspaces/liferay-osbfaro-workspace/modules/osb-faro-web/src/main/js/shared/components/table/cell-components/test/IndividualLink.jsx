/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import IndividualLinkCell from '../IndividualLink';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<StaticRouter>
		<IndividualLinkCell groupId="123" {...props} />
	</StaticRouter>
);

const tableRow = document.createElement('tr');

describe('IndividualLinkCell', () => {
	it('renders', () => {
		const {container} = render(
			<DefaultComponent
				data={{
					emailAddress: 'foo456@email',
					id: '456',
					name: 'Test Test',
				}}
			/>,
			{container: document.body.appendChild(tableRow)}
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with individual data', () => {
		const {container} = render(
			<DefaultComponent
				data={{
					individualDeleted: false,
					individualEmail: 'foo456@email',
					individualId: 'individual456',
					individualName: 'individual Test',
				}}
			/>,
			{container: document.body.appendChild(tableRow)}
		);

		expect(container).toMatchSnapshot();
	});

	it('does not render as a link if the individual was deleted', () => {
		const {container} = render(
			<DefaultComponent
				data={{
					individualDeleted: true,
					individualEmail: 'foo456@email',
					individualId: 'individual456',
					individualName: 'individual Test',
				}}
			/>
		);

		expect(container.querySelectorAll('a').length).toEqual(0);
	});

	it('does not render as a link if the individual is anonymous', () => {
		const {container} = render(
			<DefaultComponent
				data={{
					individualDeleted: true,
					individualId: 'individual456',
					individualName: 'individual Test',
				}}
			/>
		);

		expect(container.querySelectorAll('a').length).toEqual(0);
	});

	it('renders with individualId in the link', () => {
		const individualId = 'individual456';

		const {container} = render(
			<DefaultComponent
				data={{
					id: 'id123',
					individualDeleted: false,
					individualEmail: 'foo456@email',
					individualId,
					individualName: 'individual Test',
				}}
			/>
		);

		expect(container.querySelector('a').getAttribute('href')).toContain(
			individualId
		);
	});
});
