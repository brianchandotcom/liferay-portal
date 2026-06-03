import * as API from 'shared/api';
import client from 'shared/apollo/client';
import React from 'react';
import withPropertyGroups from '../WithPropertyGroups';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.mock('shared/apollo/client', () => ({
	query: jest.fn()
}));

jest.unmock('react-dom');

const TestComponent = ({propertyGroupsIList}) => (
	<div>
		{propertyGroupsIList &&
			propertyGroupsIList.map((attribute, i) => {
				if (attribute) {
					return (
						<div key={i}>
							{attribute.label}

							{attribute.propertySubgroups.map(
								({label, properties}, i) => (
									<div key={i}>{`${
										label || attribute.label
									}-${i}: ${properties.size}`}</div>
								)
							)}
						</div>
					);
				}
			})}
	</div>
);

describe('WithPropertyGroups', () => {
	it('should pass propertyGroups to the WrappedComponent', async () => {
		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'demographics',
						displayName: 'Individual Value',
						id: '123',
						name: 'Individual val',
						ownerType: 'individual',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'custom',
						displayName: 'Individual Custom',
						id: '123',
						name: 'Individual Custom',
						ownerType: 'individual',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'organization',
						displayName: 'Account Value',
						id: '123',
						name: 'Account Value',
						ownerType: 'account',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'account',
						displayName: 'Account Custom Field',
						id: '123',
						name: 'Account Custom',
						ownerType: 'account',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		API.fieldMappings.search.mockReturnValueOnce(
			Promise.resolve({
				items: [
					{
						context: 'custom',
						displayName: 'Organization Custom',
						id: '123',
						name: 'Organization Custom',
						ownerType: 'organization',
						rawType: 'Text',
						type: 'Text'
					}
				],
				total: 1
			})
		);

		client.query.mockReturnValueOnce(
			Promise.resolve({
				data: {
					eventDefinitions: {
						__typename: 'EventDefinitionBag',
						eventDefinitions: [
							{
								__typename: 'EventDefinition',
								description: null,
								displayName: 'displayName-1',
								id: '1',
								name: 'name-1',
								type: 'DEFAULT'
							}
						],
						total: 1
					}
				}
			})
		);

		const WrappedComponent = withPropertyGroups(TestComponent);

		const {container} = render(
			<StaticRouter>
				<WrappedComponent channelId='123' groupId='123' type='BATCH' />
			</StaticRouter>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	describe('Testing Conditional Requests', () => {
		beforeEach(() => {
			API.interests.searchKeywords = jest.fn();
		});

		test('Should return mocked data when segmentType is Batch', async () => {
			API.interests.searchKeywords.mockReturnValueOnce(
				Promise.resolve({
					items: [
						{id: 'kw1', name: 'Keyword 1'},
						{id: 'kw2', name: 'Keyword 2'},
						{id: 'kw2', name: 'Keyword 3'},
						{id: 'kw2', name: 'Keyword 4'},
						{id: 'kw2', name: 'Keyword 5'}
					],
					total: 5
				})
			);

			const SESSION_PROPERTIES = [
				{key: 'prop1', value: 'value1'},
				{key: 'prop2', value: 'value2'}
			];

			const segmentType = 'BATCH';

			const [keywordsResponse, sessionPropertiesResponse] =
				await Promise.all([
					segmentType === 'BATCH'
						? API.interests.searchKeywords({
								channelId: 123,
								delta: 50,
								groupId: 123
						  })
						: Promise.resolve({items: []}),

					segmentType === 'BATCH'
						? Promise.resolve(SESSION_PROPERTIES)
						: Promise.resolve([])
				]);

			expect(API.interests.searchKeywords).toHaveBeenCalledWith({
				channelId: 123,
				delta: 50,
				groupId: 123
			});

			expect(keywordsResponse.items.length).toBe(5);
			expect(keywordsResponse.items[0].name).toBe('Keyword 1');

			expect(sessionPropertiesResponse).toEqual(SESSION_PROPERTIES);
		});
	});
});
