/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {Map, fromJS} from 'immutable';
import React from 'react';
import {Property, Segment} from '~/shared/util/records';

import {
	ACTION_TYPES,
	EntityType,
	ReferencedObjectsContext,
	ReferencedObjectsProvider,
	referencedEntitiesReducer,
	referencedPropertiesReducer,
	withReferencedObjectsConsumer,
	withReferencedObjectsProvider,
} from '../referencedObjects';

jest.unmock('react-dom');

const initialReferencedEntities = new Map({
	assets: new Map(),
	organizations: new Map(),
});

const initialReferencedProperties = new Map({
	account: new Map({
		organization: new Map(),
	}),
	individual: new Map({
		custom: new Map(),
		demographics: new Map(),
	}),
	organization: new Map({
		custom: new Map(),
	}),
});

describe('referencedObjects', () => {
	describe('referencedEntitiesReducer', () => {
		afterEach(cleanup);

		it('returns referencedEntities state with added Asset', () => {
			const referencedEntities = referencedEntitiesReducer(
				initialReferencedEntities,
				{
					entityType: EntityType.Assets,
					payload: new Map({id: '123123'}),
					type: ACTION_TYPES.addEntity,
				}
			);

			expect(referencedEntities.getIn(['assets', '123123'])).toBeTruthy();
		});

		it('returns referencedEntities state with multiple added Assets', () => {
			const referencedEntities = referencedEntitiesReducer(
				initialReferencedEntities,
				{
					entityType: EntityType.Assets,
					payload: [new Map({id: '123123'}), new Map({id: '321321'})],
					type: ACTION_TYPES.addEntities,
				}
			);

			expect(referencedEntities.getIn(['assets', '123123'])).toBeTruthy();
			expect(referencedEntities.getIn(['assets', '321321'])).toBeTruthy();
		});
	});

	describe('referencedPropertiesReducer', () => {
		afterEach(cleanup);

		it('returns referencedProperties state with added properties', () => {
			const referencedProperties = referencedPropertiesReducer(
				initialReferencedProperties,
				{
					payload: new Property({
						name: 'demographics/city/value',
						propertyKey: 'individual',
					}),
					type: ACTION_TYPES.addProperty,
				}
			);

			expect(
				referencedProperties.getIn([
					'individual',
					'demographics',
					'city',
				])
			).toBeTruthy();
		});

		it('stores account property under account > context > name', () => {
			const referencedProperties = referencedPropertiesReducer(
				initialReferencedProperties,
				{
					payload: new Property({
						name: 'organization/revenue',
						propertyKey: 'account',
					}),
					type: ACTION_TYPES.addProperty,
				}
			);

			expect(
				referencedProperties.getIn([
					'account',
					'organization',
					'revenue',
				])
			).toBeTruthy();
		});

		it('stores organization property under organization > context > name', () => {
			const referencedProperties = referencedPropertiesReducer(
				initialReferencedProperties,
				{
					payload: new Property({
						name: 'custom/employeeCount',
						propertyKey: 'organization',
					}),
					type: ACTION_TYPES.addProperty,
				}
			);

			expect(
				referencedProperties.getIn([
					'organization',
					'custom',
					'employeeCount',
				])
			).toBeTruthy();
		});

		it('stores event property under event > name', () => {
			const referencedProperties = referencedPropertiesReducer(
				initialReferencedProperties,
				{
					payload: new Property({
						name: 'myEvent',
						propertyKey: 'event',
					}),
					type: ACTION_TYPES.addProperty,
				}
			);

			expect(
				referencedProperties.getIn(['event', 'myEvent'])
			).toBeTruthy();
		});

		it('returns unchanged state for an unrecognized property key', () => {
			const referencedProperties = referencedPropertiesReducer(
				initialReferencedProperties,
				{
					payload: new Property({
						name: 'something',
						propertyKey: 'unknown',
					}),
					type: ACTION_TYPES.addProperty,
				}
			);

			expect(referencedProperties).toBe(initialReferencedProperties);
		});
	});

	describe('ReferencedObjectsProvider', () => {
		afterEach(cleanup);

		it('allows an initial context value for referencedProperties segment prop', () => {
			const name = 'city';

			const ChildComponent = () => {
				const {referencedProperties} = React.useContext(
					ReferencedObjectsContext
				);

				return referencedProperties.getIn([
					'individual',
					'demographics',
					name,
					'name',
				]);
			};

			const {container} = render(
				<ReferencedObjectsProvider
					segment={
						new Segment(
							fromJS({
								referencedObjects: {
									fieldMappings: {
										individual: {
											demographics: {
												city: {
													context: 'demographics',
													id: name,
													name,
													ownerType: 'individual',
													rawType: 'Text',
													type: 'Text',
												},
											},
										},
									},
								},
							})
						)
					}
				>
					<ChildComponent />
				</ReferencedObjectsProvider>
			);

			expect(container).toHaveTextContent(name);
		});

		it('allows an initial context value for referencedEntities segment prop', () => {
			const mockAssetName = 'foo asset name';
			const mockOrganizationName = 'foo organization name';

			const ChildComponent = () => {
				const {referencedEntities} = React.useContext(
					ReferencedObjectsContext
				);

				return `${referencedEntities.getIn([
					'assets',
					'123123',
					'name',
				])} ${referencedEntities.getIn([
					'organizations',
					'456456',
					'name',
				])}`;
			};

			const {container} = render(
				<ReferencedObjectsProvider
					segment={
						new Segment(
							fromJS({
								referencedObjects: {
									assets: {
										123123: {
											id: '123123',
											name: mockAssetName,
										},
									},
									organizations: {
										456456: {
											id: '456456',
											name: mockOrganizationName,
										},
									},
								},
							})
						)
					}
				>
					<ChildComponent />
				</ReferencedObjectsProvider>
			);

			expect(container).toHaveTextContent(mockAssetName);
			expect(container).toHaveTextContent(mockOrganizationName);
		});
	});

	describe('withReferencedObjectsConsumer', () => {
		afterEach(cleanup);

		it('passes the WrappedComponent', () => {
			const ChildComponent = ({
				addEntities,
				addEntity,
				addProperty,
				referencedEntities,
				referencedProperties,
			}) => {
				if (
					addEntities &&
					addEntity &&
					addProperty &&
					referencedEntities &&
					referencedProperties
				) {
					return <div>contains all</div>;
				}

				return <div>missing some</div>;
			};

			const WrappedComponent = withReferencedObjectsProvider(() => {
				const WrappedChildComponent =
					withReferencedObjectsConsumer(ChildComponent);

				return <WrappedChildComponent />;
			});

			const {container} = render(<WrappedComponent />);

			expect(container).toHaveTextContent('contains all');
		});
	});

	describe('withReferencedObjectsProvider', () => {
		afterEach(cleanup);

		it('passes the WrappedComponent', () => {
			const WrappedComponent = withReferencedObjectsProvider(() => (
				<div>foo</div>
			));

			const {container} = render(<WrappedComponent />);

			expect(container).toHaveTextContent('foo');
		});
	});
});
