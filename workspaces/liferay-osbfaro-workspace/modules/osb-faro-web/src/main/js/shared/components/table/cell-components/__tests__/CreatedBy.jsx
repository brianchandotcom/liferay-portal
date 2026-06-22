import * as data from 'test/data';
import CreatedByCell from '../CreatedBy';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

const tableRow = document.createElement('tr');

describe('CreatedByCell', () => {
	it('should render', () => {
		const {container} = render(
			<CreatedByCell
				data={{
					dateModified: data.getTimestamp(),
					userName: 'Test Test'
				}}
			/>,
			{container: document.body.appendChild(tableRow)}
		);

		expect(container).toMatchSnapshot();
	});
});
