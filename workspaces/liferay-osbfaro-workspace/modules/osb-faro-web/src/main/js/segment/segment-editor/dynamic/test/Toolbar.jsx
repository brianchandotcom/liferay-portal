/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {Formik} from 'formik';
import React from 'react';
import {StaticRouter} from 'react-router';
import * as API from '~/shared/api';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import {Toolbar} from '../Toolbar';

jest.unmock('react-dom');

describe('Toolbar', () => {
	afterEach(() => {
		jest.clearAllMocks();

		cleanup();
	});

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<Formik>
					<Toolbar
						channelId="321"
						criteria={data.mockNewCriteria(1, {valid: false})}
						groupId="123"
						segmentType="BATCH"
					/>
				</Formik>
			</StaticRouter>
		);
		expect(container).toMatchSnapshot();
	});

	it('renders w/ preview button disabled if criteria is valid and total members count is equal to 0', () => {
		const {getByTestId} = render(
			<StaticRouter>
				<Formik>
					<Toolbar
						channelId="321"
						criteria={data.mockNewCriteria(1, {valid: true})}
						groupId="123"
						segmentType="BATCH"
					/>
				</Formik>
			</StaticRouter>
		);

		expect(getByTestId('preview-criteria-button')).toBeDisabled();
	});

	it('renders w/ preview button disabled if criteria is not valid', () => {
		const {getByTestId} = render(
			<StaticRouter>
				<Formik>
					<Toolbar
						channelId="321"
						criteria={data.mockNewCriteria(1, {valid: false})}
						groupId="123"
						segmentType="BATCH"
					/>
				</Formik>
			</StaticRouter>
		);

		expect(getByTestId('preview-criteria-button')).toBeDisabled();
	});

	it('renders w/ preview button enabled if total members count is bigger thant 0', async () => {
		API.individuals.search.mockReturnValue(Promise.resolve({total: 1}));

		const {container, getByTestId} = render(
			<StaticRouter>
				<Formik>
					<Toolbar
						channelId="321"
						criteria={data.mockNewCriteria(1, {valid: true})}
						groupId="123"
						segmentType="BATCH"
					/>
				</Formik>
			</StaticRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getByTestId('preview-criteria-button')).toBeEnabled();
	});
});
