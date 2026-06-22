import React from 'react';
import SourceCell from '../Source';
import {cleanup, render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('SourceCell', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<StaticRouter>
				<SourceCell
					data={{
						dataSourceId: '456',
						dataSourceName: 'Test Data Source'
					}}
					groupId='123'
				/>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render without an href', () => {
		const {container} = render(
			<StaticRouter>
				<SourceCell
					data={{
						dataSourceName: 'Test Data Source'
					}}
					groupId='123'
				/>
			</StaticRouter>
		);

		expect(container.querySelector('a')).toBeNull();
	});
});
