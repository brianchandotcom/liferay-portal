/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import CodeSnippet from '../CodeSnippet';

jest.unmock('react-dom');

describe('CodeSnippet', () => {
	it('renders', () => {
		const {container} = render(
			<CodeSnippet codeLines={['console.log(variable);']} />
		);
		expect(container).toMatchSnapshot();
	});

	it('represents as a string when receiving a list of code lines', () => {
		const {container} = render(
			<CodeSnippet
				codeLines={[
					"Analytics.send('viewArticle', {",
					"'firstTest': '1',",
					'});',
				]}
			/>
		);

		expect(container.querySelector('.copy-button')).toHaveAttribute(
			'data-clipboard-text',
			[
				"Analytics.send('viewArticle', {",
				"\n\t'firstTest': '1',",
				'\n});',
			].join('')
		);
	});
});
