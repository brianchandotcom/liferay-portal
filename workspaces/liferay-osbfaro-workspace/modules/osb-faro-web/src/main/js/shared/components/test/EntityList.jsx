/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {mockIndividual, mockSegment} from '~/test/data';
import {withStaticRouter} from '~/test/mock-router';

import EntityList from '../EntityList';

jest.unmock('react-dom');

const items = [
	mockIndividual(0, {total: 123}),
	mockIndividual(1, {total: 123}),
	mockIndividual(2, {total: 123}),
];

const segments = [mockSegment(1, {segmentType: 'BATCH'})];

const WrappedComponent = withStaticRouter(EntityList);

describe('EntityList', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<WrappedComponent groupId="23" items={items} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders noItemsContent', () => {
		const noItemsContent = <h1>No Items!</h1>;
		const {queryByText} = render(
			<WrappedComponent
				groupId="23"
				noItemsContent={noItemsContent}
				total={0}
			/>
		);

		expect(queryByText('No Items!')).toBeTruthy();
	});

	it('renders a list of segments with segment icons', () => {
		const {container} = render(
			<WrappedComponent groupId="23" header="foo bar" items={segments} />
		);

		expect(
			container.querySelectorAll(
				'.lexicon-icon-individual_dynamic_segment'
			).length
		).toBe(1);
	});
});
