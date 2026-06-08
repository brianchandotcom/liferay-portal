/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import {List} from 'immutable';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {SegmentTypes} from '~/shared/util/constants';
import {Property, PropertyGroup, PropertySubgroup} from '~/shared/util/records';
import * as data from '~/test/data';

import CriteriaSidebar from '../index';

const mockLiferayLanguage = (key) => {
	const messages = {
		'no-results-were-found': 'No results were found.',
	};

	return messages[key] || key;
};

global.Liferay = {
	Language: {
		get: mockLiferayLanguage,
	},
};

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({isAdmin: () => true}),
}));

jest.mock('react-router-dom', () => ({
	useParams: () => ({groupId: '12345'}),
}));

jest.unmock('react-dom');

describe('CriteriaSidebar', () => {
	const fullPropertyGroupList = new List([
		new PropertyGroup({
			label: 'Interests',
			name: 'Interests',
			propertyKey: 'interests',
			propertySubgroups: new List([
				new PropertySubgroup({
					properties: new List([
						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Page Views',
							name: 'Page Views',
						}),
					]),
				}),
				new PropertySubgroup({
					label: 'DXP Custom Fields',
					properties: new List([
						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Page Actions',
							name: 'Page Actions',
						}),
					]),
				}),
			]),
		}),
	]);

	const realTimePropertyGroupList = new List([
		new PropertyGroup({
			label: 'Events',
			name: 'Events',
			propertyKey: 'web',
			propertySubgroups: new List([
				new PropertySubgroup({
					label: 'Default Event Properties',
					properties: new List([
						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Asset View',
							name: 'Asset View',
						}),
					]),
				}),
			]),
		}),
	]);

	afterEach(cleanup);

	it('renders the sidebar structure with property groups', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebar
					propertyGroupsIList={fullPropertyGroupList}
					type={SegmentTypes.Batch}
				/>
			</DndProvider>
		);

		expect(
			screen.getByText('Interests', {selector: '[role="combobox"]'})
		).toBeInTheDocument();
		expect(screen.getByText('Page Views')).toBeInTheDocument();
		expect(screen.getByText('DXP Custom Fields')).toBeInTheDocument();
	});

	it('renders w/ "No results were found." when propertyGroupsIList is empty', () => {
		render(
			<CriteriaSidebar
				propertyGroupsIList={new List()}
				type={SegmentTypes.Batch}
			/>
		);

		expect(
			screen.queryByText('No results were found.')
		).not.toBeInTheDocument();
	});

	it('does not render the property-group picker in Real-Time mode', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebar
					propertyGroupsIList={realTimePropertyGroupList}
					type={SegmentTypes.RealTime}
				/>
			</DndProvider>
		);

		expect(screen.queryByRole('combobox')).not.toBeInTheDocument();
		expect(
			screen.getByText('Default Event Properties')
		).toBeInTheDocument();
	});
});
