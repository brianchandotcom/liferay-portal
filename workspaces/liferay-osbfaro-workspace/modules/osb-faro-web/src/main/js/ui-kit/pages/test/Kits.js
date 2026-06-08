/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import * as fs from 'fs';
import * as path from 'path';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

jest.unmock('react-dom');

describe('Kits', () => {
	const files = fs
		.readdirSync(path.resolve(__dirname, '..'))
		.filter((file) => file.match(/Kit\.(jsx|tsx)$/));

	files.forEach((file) => {
		const kitName = file.replace(/\.(jsx|tsx)$/, '');

		describe(kitName, () => {
			const kitPath = `../${file}`;

			// eslint-disable-next-line @liferay/no-dynamic-require -- test fixture loader discovers kit files at runtime
			const {default: Kit} = require(kitPath);

			it('renders', () => {
				const {container} = render(
					<Provider store={mockStore()}>
						<MemoryRouter>
							<MockedProvider addTypename={false}>
								<Kit groupId="23" />
							</MockedProvider>
						</MemoryRouter>
					</Provider>
				);

				expect(container).toBeTruthy();
			});
		});
	});
});
