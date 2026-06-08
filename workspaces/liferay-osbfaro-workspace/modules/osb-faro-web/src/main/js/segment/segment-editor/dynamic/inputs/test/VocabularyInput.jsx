/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '~/test/mock-modal';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {Property} from '~/shared/util/records';
import mockStore from '~/test/mock-store';

import {
	createCustomValueMap,
	getIndexFromPropertyName,
} from '../../utils/custom-inputs';
import VocabularyInput, {
	buildValue,
	getAssetTypeFromValue,
	getCategoriesFromValue,
	getEventTypeFromValue,
} from '../VocabularyInput';

jest.unmock('react-dom');

const VOC_ID = 'vocab-id';
const VOC_NAME = 'My Vocabulary';

const DAY_CRITERION = {
	operatorName: 'gt',
	propertyName: 'day',
	touched: false,
	valid: true,
	value: '2023-01-01',
};

const BASE_ITEMS = [
	{
		operatorName: 'eq',
		propertyName: 'vocabularies/id',
		touched: false,
		valid: true,
		value: VOC_ID,
	},
	{
		operatorName: 'eq',
		propertyName: 'vocabularies/name',
		touched: false,
		valid: true,
		value: VOC_NAME,
	},
];

function makeValue(items, {count = 1, operator = 'ge'} = {}) {
	return createCustomValueMap([
		{key: 'criterionGroup', value: items},
		{key: 'operator', value: operator},
		{key: 'value', value: count},
	]);
}

describe('VocabularyInput', () => {
	describe('buildValue', () => {
		it('includes applicationId and eventId items and no activityKey for any asset type', () => {
			const value = buildValue(
				'all',
				'any',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(
				getIndexFromPropertyName(value, 'applicationId')
			).toBeGreaterThanOrEqual(0);
			expect(
				getIndexFromPropertyName(value, 'eventId')
			).toBeGreaterThanOrEqual(0);
			expect(getIndexFromPropertyName(value, 'activityKey')).toBe(-1);
		});

		it('sets applicationId and eventId for a specific asset type and event', () => {
			const value = buildValue(
				'view',
				'web-content',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);
			const appIdx = getIndexFromPropertyName(value, 'applicationId');
			const eventIdx = getIndexFromPropertyName(value, 'eventId');

			expect(appIdx).toBeGreaterThanOrEqual(0);
			expect(
				value.getIn(['criterionGroup', 'items', appIdx, 'value']).toJS()
			).toEqual(['WebContent']);

			expect(eventIdx).toBeGreaterThanOrEqual(0);
			expect(
				value
					.getIn(['criterionGroup', 'items', eventIdx, 'value'])
					.toJS()
			).toEqual(['webContentViewed']);

			expect(getIndexFromPropertyName(value, 'activityKey')).toBe(-1);
		});

		it('sets applicationId and all compatible eventIds when event type is all', () => {
			const value = buildValue(
				'all',
				'web-content',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);
			const appIdx = getIndexFromPropertyName(value, 'applicationId');
			const eventIdx = getIndexFromPropertyName(value, 'eventId');

			expect(appIdx).toBeGreaterThanOrEqual(0);
			expect(
				value.getIn(['criterionGroup', 'items', appIdx, 'value']).toJS()
			).toEqual(['WebContent']);

			expect(eventIdx).toBeGreaterThanOrEqual(0);
			expect(
				value
					.getIn(['criterionGroup', 'items', eventIdx, 'value'])
					.toJS()
			).toEqual(
				expect.arrayContaining([
					'webContentViewed',
					'webContentImpressionMade',
				])
			);

			expect(getIndexFromPropertyName(value, 'activityKey')).toBe(-1);
		});

		it('includes a categories item when categories are provided', () => {
			const categories = [{id: 'cat-1', name: 'Cat 1'}];
			const value = buildValue(
				'all',
				'any',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				categories
			);
			const index = getIndexFromPropertyName(value, 'categories');

			expect(index).toBeGreaterThanOrEqual(0);
			expect(
				value.getIn(['criterionGroup', 'items', index, 'value']).toJS()
			).toEqual(categories);
		});

		it('does not include a categories item when categories is empty', () => {
			const value = buildValue(
				'all',
				'any',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getIndexFromPropertyName(value, 'categories')).toBe(-1);
		});

		it('alwayses include vocabularies/id, vocabularies/name and day items', () => {
			const value = buildValue(
				'all',
				'any',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(
				getIndexFromPropertyName(value, 'vocabularies/id')
			).toBeGreaterThanOrEqual(0);
			expect(
				getIndexFromPropertyName(value, 'vocabularies/name')
			).toBeGreaterThanOrEqual(0);
			expect(
				getIndexFromPropertyName(value, 'day')
			).toBeGreaterThanOrEqual(0);
		});
	});

	describe('getAssetTypeFromValue', () => {
		it('returns any when value is undefined', () => {
			expect(getAssetTypeFromValue(undefined)).toBe('any');
		});

		it('returns any when value has an applicationId item', () => {
			const value = buildValue(
				'all',
				'any',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getAssetTypeFromValue(value)).toBe('any');
		});

		it('returns the correct asset type from activityKey with eventId', () => {
			const value = buildValue(
				'view',
				'web-content',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getAssetTypeFromValue(value)).toBe('web-content');
		});

		it('returns the correct asset type from activityKey without eventId', () => {
			const value = buildValue(
				'all',
				'blogs',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getAssetTypeFromValue(value)).toBe('blogs');
		});

		it('returns any for an unrecognized applicationId', () => {
			const value = makeValue([
				...BASE_ITEMS,
				{
					operatorName: 'eq',
					propertyName: 'activityKey',
					touched: false,
					valid: true,
					value: 'Unknown#someEvent',
				},
				DAY_CRITERION,
			]);

			expect(getAssetTypeFromValue(value)).toBe('any');
		});
	});

	describe('getEventTypeFromValue', () => {
		it('returns all when value is undefined', () => {
			expect(getEventTypeFromValue(undefined)).toBe('all');
		});

		it('returns all when value has no activityKey item', () => {
			const value = buildValue(
				'all',
				'any',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getEventTypeFromValue(value)).toBe('all');
		});

		it('returns the correct event type from activityKey with eventId', () => {
			const value = buildValue(
				'view',
				'web-content',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getEventTypeFromValue(value)).toBe('view');
		});

		it('returns download for a download event', () => {
			const value = buildValue(
				'download',
				'documents-and-media',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getEventTypeFromValue(value)).toBe('download');
		});

		it('returns all when activityKey has no eventId part', () => {
			const value = buildValue(
				'all',
				'web-content',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getEventTypeFromValue(value)).toBe('all');
		});
	});

	describe('getCategoriesFromValue', () => {
		it('returns an empty array when value is undefined', () => {
			expect(getCategoriesFromValue(undefined)).toEqual([]);
		});

		it('returns categories from direct format (created by editor)', () => {
			const categories = [{id: 'cat-1', name: 'Cat 1'}];
			const value = buildValue(
				'all',
				'any',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				categories
			);

			expect(getCategoriesFromValue(value)).toEqual(categories);
		});

		it('returns categories from OR group format (multiple categories after round-trip)', () => {
			const value = makeValue([
				...BASE_ITEMS,
				{
					operatorName: 'eq',
					propertyName: 'activityKey',
					touched: false,
					valid: true,
					value: 'WebContent',
				},
				{
					conjunctionName: 'or',
					criteriaGroupId: 'or-group',
					items: [
						{
							conjunctionName: 'and',
							criteriaGroupId: 'and-1',
							items: [
								{
									operatorName: 'eq',
									propertyName: 'categories/id',
									touched: false,
									valid: true,
									value: 'cat-1',
								},
								{
									operatorName: 'eq',
									propertyName: 'categories/name',
									touched: false,
									valid: true,
									value: 'Cat 1',
								},
							],
						},
						{
							conjunctionName: 'and',
							criteriaGroupId: 'and-2',
							items: [
								{
									operatorName: 'eq',
									propertyName: 'categories/id',
									touched: false,
									valid: true,
									value: 'cat-2',
								},
								{
									operatorName: 'eq',
									propertyName: 'categories/name',
									touched: false,
									valid: true,
									value: 'Cat 2',
								},
							],
						},
					],
				},
				DAY_CRITERION,
			]);

			expect(getCategoriesFromValue(value)).toEqual([
				{id: 'cat-1', name: 'Cat 1'},
				{id: 'cat-2', name: 'Cat 2'},
			]);
		});

		it('returns categories from flat format (single category after round-trip)', () => {
			const value = makeValue([
				...BASE_ITEMS,
				{
					operatorName: 'eq',
					propertyName: 'activityKey',
					touched: false,
					valid: true,
					value: 'WebContent',
				},
				{
					operatorName: 'eq',
					propertyName: 'categories/id',
					touched: false,
					valid: true,
					value: 'cat-1',
				},
				{
					operatorName: 'eq',
					propertyName: 'categories/name',
					touched: false,
					valid: true,
					value: 'Cat 1',
				},
				DAY_CRITERION,
			]);

			expect(getCategoriesFromValue(value)).toEqual([
				{id: 'cat-1', name: 'Cat 1'},
			]);
		});

		it('returns an empty array when value has no category items', () => {
			const value = buildValue(
				'all',
				'any',
				'ge',
				1,
				DAY_CRITERION,
				VOC_ID,
				VOC_NAME,
				[]
			);

			expect(getCategoriesFromValue(value)).toEqual([]);
		});
	});

	describe('render', () => {
		afterEach(cleanup);

		it('calls onChange on mount with a built value when value has no criterionGroup', () => {
			const onChange = jest.fn();

			render(
				<Provider store={mockStore()}>
					<VocabularyInput
						channelId="ch-1"
						displayValue="My Vocabulary"
						groupId="gp-1"
						onChange={onChange}
						operatorRenderer={() => <div />}
						property={new Property({name: 'vocab-id-1'})}
					/>
				</Provider>
			);

			expect(onChange).toHaveBeenCalled();

			const {value} = onChange.mock.calls[0][0];

			expect(
				getIndexFromPropertyName(value, 'vocabularies/id')
			).toBeGreaterThanOrEqual(0);
			expect(
				getIndexFromPropertyName(value, 'day')
			).toBeGreaterThanOrEqual(0);
		});

		it('calls onChange on mount when value has operator and count but no criterionGroup (getDefaultValue stub)', () => {
			const onChange = jest.fn();

			const stubValue = createCustomValueMap([
				{key: 'operator', value: 'ge'},
				{key: 'value', value: 1},
			]);

			render(
				<Provider store={mockStore()}>
					<VocabularyInput
						channelId="ch-1"
						displayValue="Formula 1"
						groupId="gp-1"
						onChange={onChange}
						operatorRenderer={() => <div />}
						property={new Property({name: '35063'})}
						value={stubValue}
					/>
				</Provider>
			);

			expect(onChange).toHaveBeenCalled();

			const {value} = onChange.mock.calls[0][0];

			const vocIdIdx = getIndexFromPropertyName(value, 'vocabularies/id');

			expect(vocIdIdx).toBeGreaterThanOrEqual(0);
			expect(
				value.getIn(['criterionGroup', 'items', vocIdIdx, 'value'])
			).toBe('35063');
			expect(
				getIndexFromPropertyName(value, 'vocabularies/name')
			).toBeGreaterThanOrEqual(0);
			expect(
				getIndexFromPropertyName(value, 'applicationId')
			).toBeGreaterThanOrEqual(0);
			expect(
				getIndexFromPropertyName(value, 'day')
			).toBeGreaterThanOrEqual(0);
		});
	});
});
