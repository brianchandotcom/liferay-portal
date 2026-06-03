import FilterOptions from '../index';
import React from 'react';
import {render} from '@testing-library/react';
import {withAttributesProvider} from 'event-analysis/components/event-analysis-editor/context/attributes';

jest.unmock('react-dom');

describe('FilterOptions', () => {
	it('should render', () => {
		const WrappedFilterOptions = withAttributesProvider(FilterOptions);

		const {container} = render(
			<WrappedFilterOptions
				attribute={{
					dataType: 'DATE',
					displayName: 'Filed Ticket',
					id: '4',
					name: 'filedTicket'
				}}
				onActiveChange={jest.fn()}
				onAttributeChange={jest.fn()}
				onEditClick={jest.fn()}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
