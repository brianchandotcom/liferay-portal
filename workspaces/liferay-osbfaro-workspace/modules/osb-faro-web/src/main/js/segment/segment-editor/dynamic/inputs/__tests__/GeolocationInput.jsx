import * as data from 'test/data';
import client from 'shared/apollo/client';
import GeolocationInput from '../GeolocationInput';
import React from 'react';
import {ApolloProvider} from '@apollo/client';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {createCustomValueMap} from '../../utils/custom-inputs';
import {MockedProvider} from '@apollo/client/testing';
import {mockPreferenceReq} from 'test/graphql-data';
import {Property} from 'shared/util/records';
import {RelationalOperators, TimeSpans} from '../../utils/constants';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/country',
				value: 'foo country'
			},
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'completeDate',
				value: TimeSpans.Last7Days
			},
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/region',
				value: 'foo region'
			},
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/city',
				value: 'foo city'
			}
		]
	}
]);

const emptyMockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/country',
				value: ''
			},
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'completeDate',
				value: TimeSpans.Last7Days
			}
		]
	}
]);

const WrapperComponent = ({children}) => (
	<ApolloProvider client={client}>
		<MockedProvider mocks={[mockPreferenceReq()]}>
			{children}
		</MockedProvider>
	</ApolloProvider>
);

describe('GeolocationInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {getAllByText, getByText} = render(
			<WrapperComponent>
				<GeolocationInput
					onChange={jest.fn()}
					property={new Property(data.mockProperty({}))}
					touched={false}
					valid={false}
					value={emptyMockValue}
				/>
			</WrapperComponent>
		);
		fireEvent.click(getByText('was'));
		fireEvent.click(getByText('on'));

		expect(getAllByText('was')[1]).toBeInTheDocument();
		expect(getByText('was not')).toBeInTheDocument();
		expect(getByText('contained')).toBeInTheDocument();
		expect(getByText('did not contain')).toBeInTheDocument();

		expect(getByText('since')).toBeInTheDocument();
		expect(getByText('after')).toBeInTheDocument();
		expect(getByText('before')).toBeInTheDocument();
		expect(getByText('between')).toBeInTheDocument();
		expect(getByText('ever')).toBeInTheDocument();
		expect(getAllByText('on')[1]).toBeInTheDocument();
	});

	it('should render with has-error', () => {
		const {container} = render(
			<WrapperComponent>
				<GeolocationInput
					onChange={jest.fn()}
					property={new Property(data.mockProperty({}))}
					touched
					valid={false}
					value={emptyMockValue}
				/>
			</WrapperComponent>
		);
		expect(container.querySelector('.select-input-root')).toHaveClass(
			'has-error'
		);
	});

	it('should render inputs for all location values if they all have non-zero length values', () => {
		const {container} = render(
			<WrapperComponent>
				<GeolocationInput
					onChange={jest.fn()}
					property={new Property(data.mockProperty({}))}
					touched={false}
					valid
					value={mockValue}
				/>
			</WrapperComponent>
		);
		expect(container.querySelectorAll('input').length).toBe(4);
	});

	it('should render the region input as a button if the initial value for the input was empty', () => {
		const {container} = render(
			<WrapperComponent>
				<GeolocationInput
					onChange={jest.fn()}
					property={new Property(data.mockProperty({}))}
					touched={false}
					valid
					value={createCustomValueMap([
						{
							key: 'criterionGroup',
							value: [
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'context/country',
									value: 'foo country'
								},
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'completeDate',
									value: TimeSpans.Last7Days
								},
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'context/region',
									value: ''
								}
							]
						}
					])}
				/>
			</WrapperComponent>
		);

		expect(container.querySelectorAll('.button-root')[0]).toHaveTextContent(
			'Add Region'
		);
	});

	it('should render the city input as a button if the initial value for the input was empty', () => {
		const {container} = render(
			<WrapperComponent>
				<GeolocationInput
					onChange={jest.fn()}
					property={new Property(data.mockProperty({}))}
					touched={false}
					valid
					value={createCustomValueMap([
						{
							key: 'criterionGroup',
							value: [
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'context/country',
									value: 'foo country'
								},
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'completeDate',
									value: TimeSpans.Last7Days
								},
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'context/region',
									value: 'foo region'
								},
								{
									operatorName: RelationalOperators.EQ,
									propertyName: 'context/city',
									value: ''
								}
							]
						}
					])}
				/>
			</WrapperComponent>
		);

		expect(container.querySelector('.button-root')).toHaveTextContent(
			'Add City'
		);
	});
});
