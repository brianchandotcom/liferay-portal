import EntityRowActions from '../EntityRowActions';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Set} from 'immutable';

jest.unmock('react-dom');

describe('EntityRowActions', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<EntityRowActions />);
		expect(container).toMatchSnapshot();
	});

	it('should render with an added label and undo link', () => {
		const {container} = render(
			<EntityRowActions
				addIdsISet={new Set([12, 14])}
				data={{id: 12}}
				showAdded
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with a removed label and undo link', () => {
		const {container} = render(
			<EntityRowActions
				data={{id: 12}}
				removeIdsISet={new Set([12, 14])}
			/>
		);
		expect(container).toMatchSnapshot();
	});

	it('should render nothing if items are selected but it is not part of added/removed', () => {
		const {container} = render(
			<EntityRowActions data={{id: 22}} itemsSelected />
		);

		expect(container).toBeTruthy();
	});
});
