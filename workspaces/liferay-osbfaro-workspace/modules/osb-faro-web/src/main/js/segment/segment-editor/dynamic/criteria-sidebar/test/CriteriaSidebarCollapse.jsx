/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import {List} from 'immutable';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {FieldOwnerTypes} from '~/shared/util/constants';
import {Property, PropertyGroup, PropertySubgroup} from '~/shared/util/records';
import * as data from '~/test/data';

import {PropertyTypes} from '../../utils/constants';
import {getIndexFromPropertyName} from '../../utils/custom-inputs';
import CriteriaSidebarCollapse, {
	getDefaultValue,
} from '../CriteriaSidebarCollapse';

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({isAdmin: () => true}),
}));

jest.mock('react-router-dom', () => ({
	useParams: () => ({groupId: '12345'}),
}));

const mockLiferayLanguage = (key) => {
	const messages = {
		'connect-a-data-source-containing-account-data':
			'connect-a-data-source-containing-account-data',
		'connect-data-source': 'connect-data-source',
		'learn-more-about-data-sources': 'learn-more-about-data-sources',
		'no-account-data-synced': 'no-account-data-synced',
		'no-results-found': 'no-results-found',
		'no-results-were-found': 'no-results-were-found',
		'review-your-search-and-try-again': 'review-your-search-and-try-again',
	};

	return messages[key] || key;
};

global.Liferay = {
	Language: {
		get: mockLiferayLanguage,
	},
};

jest.unmock('react-dom');

describe('CriteriaSidebarCollapse', () => {
	const ACCOUNT_KEY = FieldOwnerTypes.Account;

	const propertyGroupsIList = new List([
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

		new PropertyGroup({
			label: 'Account Data',
			name: 'Account Data',
			propertyKey: ACCOUNT_KEY,
			propertySubgroups: List(),
		}),
	]);

	const propertyGroupsIListCustomEvents = new List([
		new PropertyGroup({
			label: 'Events',
			name: 'Events',
			propertyKey: 'events',
			propertySubgroups: new List([
				new PropertySubgroup({
					label: 'Custom Events',
					properties: new List([
						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Custom Event 1',
							name: 'Custom Event 1',
						}),

						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Custom Event 2',
							name: 'Custom Event 2',
						}),

						data.getImmutableMock(Property, data.mockProperty, 0, {
							label: 'Custom Event 3',
							name: 'Custom Event 3',
						}),
					]),
				}),
			]),
		}),
	]);

	afterEach(cleanup);

	it('renders correctly', () => {
		render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey="interests"
					searchValue=""
				/>
			</DndProvider>
		);

		expect(screen.getByText('Page Views')).toBeInTheDocument();
		expect(screen.getByText('DXP Custom Fields')).toBeInTheDocument();
	});

	it('filters properties and show "no-results-were-found" for the empty subgroup', () => {
		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey="interests"
					searchValue="Views"
				/>
			</DndProvider>
		);

		expect(queryByText('Page Views')).toBeInTheDocument();

		expect(queryByText('Page Actions')).toBeNull();

		const secondListItem = document.querySelector(
			'.property-subgroups-list > li:nth-child(2)'
		);
		expect(secondListItem).toHaveTextContent('no-results-were-found');

		expect(queryByText('no-results-found')).toBeNull();
	});

	it('renders w/ global EmptyState "no-results-found" when no properties match the search', () => {
		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey="interests"
					searchValue="should not exist"
				/>
			</DndProvider>
		);

		expect(queryByText('no-results-found')).toBeInTheDocument();
		expect(
			queryByText('review-your-search-and-try-again')
		).toBeInTheDocument();

		expect(queryByText('Page Views')).toBeNull();
		expect(queryByText('Page Actions')).toBeNull();
		expect(queryByText('no-results-were-found')).toBeNull();
	});

	it('renders custom events in the sidebar', () => {
		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIListCustomEvents}
					propertyKey="events"
					searchValue=""
				/>
			</DndProvider>
		);

		expect(queryByText('Custom Events')).toBeInTheDocument();
		expect(queryByText('Custom Event 1')).toBeInTheDocument();
		expect(queryByText('Custom Event 2')).toBeInTheDocument();
		expect(queryByText('Custom Event 3')).toBeInTheDocument();
	});

	it('renders the Account EmptyState when propertyKey is Account and there are no properties', () => {
		const {getByRole, queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsIList}
					propertyKey={ACCOUNT_KEY}
					searchValue=""
				/>
			</DndProvider>
		);

		expect(queryByText('no-account-data-synced')).toBeInTheDocument();
		expect(
			queryByText('connect-a-data-source-containing-account-data')
		).toBeInTheDocument();
		expect(
			queryByText('learn-more-about-data-sources')
		).toBeInTheDocument();
		expect(queryByText('connect-data-source')).toBeInTheDocument();

		const connectLink = getByRole('link', {
			name: /connect-data-source/i,
		});
		expect(connectLink).toHaveAttribute(
			'href',
			'/workspace/12345/settings/data-source'
		);
	});

	it('does not render Account EmptyState when there are properties, even if propertyKey is Account', () => {
		const propertyGroupsWithAccountData = new List([
			new PropertyGroup({
				label: 'Account Data',
				name: 'Account Data',
				propertyKey: ACCOUNT_KEY,
				propertySubgroups: new List([
					new PropertySubgroup({
						properties: new List([
							data.getImmutableMock(
								Property,
								data.mockProperty,
								0,
								{
									label: 'Account Name',
									name: 'accountName',
								}
							),
						]),
					}),
				]),
			}),
		]);

		const {queryByText} = render(
			<DndProvider backend={HTML5Backend}>
				<CriteriaSidebarCollapse
					propertyGroupsIList={propertyGroupsWithAccountData}
					propertyKey={ACCOUNT_KEY}
					searchValue=""
				/>
			</DndProvider>
		);

		expect(queryByText('Account Name')).toBeInTheDocument();

		expect(queryByText('no-account-data-synced')).toBeNull();
	});
});

describe('getDefaultValue', () => {
	it('returns a YYYY-MM-DD string for PropertyTypes.Date', () => {
		const result = getDefaultValue(
			new Property({name: 'myDate', type: PropertyTypes.Date})
		);

		expect(result).toMatch(/^\d{4}-\d{2}-\d{2}$/);
	});

	it('returns "true" for PropertyTypes.Boolean', () => {
		const result = getDefaultValue(
			new Property({name: 'myBool', type: PropertyTypes.Boolean})
		);

		expect(result).toBe('true');
	});

	it('returns empty string for PropertyTypes.Text with no options', () => {
		const result = getDefaultValue(
			new Property({name: 'myText', type: PropertyTypes.Text})
		);

		expect(result).toBe('');
	});

	it('returns first option value for PropertyTypes.Text with options', () => {
		const result = getDefaultValue(
			new Property({
				name: 'myText',
				options: [{label: 'Option 1', value: 'opt1'}],
				type: PropertyTypes.Text,
			})
		);

		expect(result).toBe('opt1');
	});

	it('returns CustomValueMap with name and score items for PropertyTypes.Interest', () => {
		const result = getDefaultValue(
			new Property({name: 'myInterest', type: PropertyTypes.Interest})
		);
		const nameIdx = getIndexFromPropertyName(result, 'name');
		const scoreIdx = getIndexFromPropertyName(result, 'score');

		expect(nameIdx).toBeGreaterThanOrEqual(0);
		expect(scoreIdx).toBeGreaterThanOrEqual(0);
		expect(
			result.getIn(['criterionGroup', 'items', nameIdx, 'value'])
		).toBe('myInterest');
		expect(
			result.getIn(['criterionGroup', 'items', scoreIdx, 'value'])
		).toBe('true');
	});

	it('returns CustomValueMap with activityKey, day, operator and value for PropertyTypes.Behavior', () => {
		const result = getDefaultValue(
			new Property({name: 'myBehavior', type: PropertyTypes.Behavior})
		);

		expect(
			getIndexFromPropertyName(result, 'activityKey')
		).toBeGreaterThanOrEqual(0);
		expect(getIndexFromPropertyName(result, 'day')).toBeGreaterThanOrEqual(
			0
		);
		expect(result.get('operator')).toBeTruthy();
		expect(result.get('value')).toBe(1);
	});

	it('returns CustomValueMap with eventId set to property name for PropertyTypes.Event', () => {
		const result = getDefaultValue(
			new Property({name: 'myEvent', type: PropertyTypes.Event})
		);
		const eventIdx = getIndexFromPropertyName(result, 'eventId');

		expect(eventIdx).toBeGreaterThanOrEqual(0);
		expect(
			result.getIn(['criterionGroup', 'items', eventIdx, 'value'])
		).toBe('myEvent');
		expect(getIndexFromPropertyName(result, 'day')).toBeGreaterThanOrEqual(
			0
		);
	});

	it('returns CustomValueMap with property name as propertyName and empty value for PropertyTypes.AccountText', () => {
		const result = getDefaultValue(
			new Property({name: 'myAccount', type: PropertyTypes.AccountText})
		);
		const index = getIndexFromPropertyName(result, 'myAccount');

		expect(index).toBeGreaterThanOrEqual(0);
		expect(result.getIn(['criterionGroup', 'items', index, 'value'])).toBe(
			''
		);
	});

	it('returns empty string for an unrecognized property type', () => {
		const result = getDefaultValue(
			new Property({name: 'myProp', type: 'unknown-type'})
		);

		expect(result).toBe('');
	});
});
