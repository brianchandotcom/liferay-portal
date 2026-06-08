/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {Conjunctions} from '../../utils/constants';
import CriteriaBuilder from '../index';

jest.mock('../CriteriaGroup', () => () => <div />);

jest.unmock('react-dom');

describe('CriteriaBuilder', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaBuilder />
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('does not render the clear-all button when criteria is empty', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaBuilder
					criteria={{
						conjunctionName: Conjunctions.And,
						criteriaGroupId: 'group',
						items: [],
					}}
					onChange={() => {}}
				/>
			</DndProvider>
		);

		expect(
			screen.queryByRole('button', {name: /clear all/i})
		).not.toBeInTheDocument();
	});

	it('does not render the clear-all button when criteria has a single item', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaBuilder
					criteria={{
						conjunctionName: Conjunctions.And,
						criteriaGroupId: 'group',
						items: [{rowId: 'r0', valid: true}],
					}}
					onChange={() => {}}
				/>
			</DndProvider>
		);

		expect(
			screen.queryByRole('button', {name: /clear all/i})
		).not.toBeInTheDocument();
	});

	it('preserves the root conjunction when a nested group becomes the only root item', () => {
		const onChange = jest.fn();

		const ref = React.createRef();

		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaBuilder
					criteria={{
						conjunctionName: Conjunctions.And,
						criteriaGroupId: 'root',
						items: [{rowId: 'r0', valid: true}],
					}}
					onChange={onChange}
					ref={ref}
				/>
			</DndProvider>
		);

		ref.current.handleCriteriaChange({
			conjunctionName: Conjunctions.And,
			criteriaGroupId: 'root',
			items: [
				{
					conjunctionName: Conjunctions.Or,
					criteriaGroupId: 'nested',
					items: [
						{rowId: 'r0', valid: true},
						{rowId: 'r1', valid: true},
					],
				},
			],
		});

		expect(onChange).toHaveBeenCalledWith(
			expect.objectContaining({
				conjunctionName: Conjunctions.And,
				items: expect.arrayContaining([
					expect.objectContaining({conjunctionName: Conjunctions.Or}),
				]),
			})
		);
	});

	it('renders the clear-all button when criteria has more than one item', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaBuilder
					criteria={{
						conjunctionName: Conjunctions.And,
						criteriaGroupId: 'group',
						items: [
							{rowId: 'r0', valid: true},
							{rowId: 'r1', valid: true},
						],
					}}
					onChange={() => {}}
				/>
			</DndProvider>
		);

		expect(
			screen.getByRole('button', {name: /clear all/i})
		).toBeInTheDocument();
	});
});
