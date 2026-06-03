import CodeSnippet from '../CodeSnippet';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('CodeSnippet', () => {
	it('should render', () => {
		const {container} = render(
			<CodeSnippet codeLines={['console.log(variable);']} />
		);
		expect(container).toMatchSnapshot();
	});

	it('should represent as a string when receiving a list of code lines', () => {
		const {container} = render(
			<CodeSnippet
				codeLines={[
					"Analytics.send('viewArticle', {",
					"'firstTest': '1',",
					'});'
				]}
			/>
		);

		expect(container.querySelector('.copy-button')).toHaveAttribute(
			'data-clipboard-text',
			[
				"Analytics.send('viewArticle', {",
				"\n\t'firstTest': '1',",
				'\n});'
			].join('')
		);
	});
});
