import AccountInput from '../AccountInput';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import {Property} from 'shared/util/records';
import {PropertyTypes, RelationalOperators} from '../../utils/constants';

jest.unmock('react-dom');

const {EQ} = RelationalOperators;

describe('AccountInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container, getAllByText, getByText} = render(
			<AccountInput
				property={new Property()}
				value={fromJS({criterionGroup: {items: [{operatorName: EQ}]}})}
			/>
		);

		fireEvent.click(getByText('is'));

		expect(getAllByText('is')[1]).toBeTruthy();
		expect(getByText('is not')).toBeTruthy();
		expect(getByText('contains')).toBeTruthy();
		expect(getByText('does not contain')).toBeTruthy();
		expect(getByText('is known')).toBeTruthy();
		expect(getByText('is unknown')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('should render a CustomNumberInput', () => {
		const {queryByText} = render(
			<AccountInput
				property={new Property({type: PropertyTypes.AccountNumber})}
				value={fromJS({criterionGroup: {items: [{operatorName: EQ}]}})}
			/>
		);

		expect(queryByText('is equal to')).not.toBeNull();
	});
});
