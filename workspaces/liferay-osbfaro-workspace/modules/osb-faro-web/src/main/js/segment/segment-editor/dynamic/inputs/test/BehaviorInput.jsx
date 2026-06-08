/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {Map, OrderedMap} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {SegmentTypes} from '~/shared/util/constants';
import {Property, Segment} from '~/shared/util/records';
import mockStore from '~/test/mock-store';

import {ReferencedObjectsProvider} from '../../context/referencedObjects';
import {ACTIVITY_KEY, RelationalOperators} from '../../utils/constants';
import {createCustomValueMap} from '../../utils/custom-inputs';
import BehaviorInput, {AssetItem} from '../BehaviorInput';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.EQ,
				propertyName: ACTIVITY_KEY,
				value: 'test#test#123123123',
			},
		],
	},
	{key: 'operator', value: RelationalOperators.GE},
	{key: 'value', value: ''},
]);

const defaultProps = {
	onChange: jest.fn(),
	operatorRenderer: () => <div>test</div>,
	property: new Property(),
	referencedAssetsIMap: new Map(),
	segmentType: SegmentTypes.Batch,
	touched: {asset: false, occurenceCount: false},
	valid: {asset: false, occurenceCount: false},
	value: mockValue,
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<ReferencedObjectsProvider
			segment={
				new Segment({
					referencedObjects: new Map({
						assets: new Map({'123_title': 'test'}),
					}),
				})
			}
		>
			<BehaviorInput {...defaultProps} {...props} />
		</ReferencedObjectsProvider>
	</Provider>
);

describe('BehaviorInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<DefaultComponent />
		);

		fireEvent.click(getByText('at least'));
		fireEvent.click(getByText('ever'));

		expect(getAllByText('at least')[1]).toBeTruthy();
		expect(getByText('at most')).toBeTruthy();

		expect(getByText('since')).toBeTruthy();
		expect(getByText('before')).toBeTruthy();
		expect(getByText('between')).toBeTruthy();
		expect(getAllByText('ever')[1]).toBeTruthy();
		expect(getByText('on')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});

	it('renders w/ data', () => {
		const {container} = render(
			<DefaultComponent
				referencedAssetsIMap={
					new Map({
						assets: new Map({'123_title': 'test'}),
					})
				}
				valid={{asset: true, occurenceCount: true}}
				value={mockValue.set('value', 123)}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with has-error for asset', () => {
		const {container} = render(
			<DefaultComponent
				touched={{asset: true, occurenceCount: false}}
				valid={{asset: false, occurenceCount: true}}
				value={mockValue.set('value', 123)}
			/>
		);

		expect(
			container.querySelector('.form-group-item-shrink.has-error')
		).toBeNull();

		expect(container.querySelector('.has-error')).toBeTruthy();
	});

	it('renders with has-error for occurenceCount', () => {
		const {container} = render(
			<DefaultComponent touched={{asset: false, occurenceCount: true}} />
		);

		expect(
			container.querySelector('.form-group-item-shrink.has-error')
		).toBeTruthy();
	});

	describe('handleAssetSelect', () => {
		it('calls onChange with a plain object when a single asset is selected', () => {
			const onChange = jest.fn();
			const ref = React.createRef();

			render(
				<Provider store={mockStore()}>
					<ReferencedObjectsProvider segment={new Segment({})}>
						<BehaviorInput
							{...defaultProps}
							onChange={onChange}
							ref={ref}
						/>
					</ReferencedObjectsProvider>
				</Provider>
			);

			const asset = {id: 'asset-1', name: 'Test Asset'};

			ref.current.handleAssetSelect(OrderedMap([[asset.id, asset]]));

			expect(onChange).toHaveBeenCalledTimes(1);
			expect(Array.isArray(onChange.mock.calls[0][0])).toBe(false);
		});

		it('calls onChange with an array when multiple assets are selected', () => {
			const onChange = jest.fn();
			const ref = React.createRef();

			render(
				<Provider store={mockStore()}>
					<ReferencedObjectsProvider segment={new Segment({})}>
						<BehaviorInput
							{...defaultProps}
							onChange={onChange}
							ref={ref}
						/>
					</ReferencedObjectsProvider>
				</Provider>
			);

			const items = OrderedMap([
				['asset-1', {id: 'asset-1', name: 'Asset 1'}],
				['asset-2', {id: 'asset-2', name: 'Asset 2'}],
			]);

			ref.current.handleAssetSelect(items);

			expect(onChange).toHaveBeenCalledTimes(1);
			expect(Array.isArray(onChange.mock.calls[0][0])).toBe(true);
			expect(onChange.mock.calls[0][0]).toHaveLength(2);
		});
	});

	describe('AssetItem', () => {
		afterEach(cleanup);

		it('renders', () => {
			const {container} = render(<AssetItem />);

			expect(container).toMatchSnapshot();
		});
	});
});
