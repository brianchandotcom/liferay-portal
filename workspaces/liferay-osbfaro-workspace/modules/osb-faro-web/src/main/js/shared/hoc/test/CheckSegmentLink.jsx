/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import * as API from '~/shared/api';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import CheckSegmentLink from '../CheckSegmentLink';

jest.unmock('react-dom');

const RenderText = () => 'Wrapped component text';

describe('CheckSegmentLink', () => {
	afterEach(cleanup);

	it('renders', () => {
		const WrappedComponent = CheckSegmentLink(RenderText);

		const {getByText} = render(
			<WrappedComponent
				location={{
					pathname:
						'/workspace/faro-dev-liferay/123/contacts/segments/456',
				}}
			/>
		);

		expect(getByText('Wrapped component text')).toBeTruthy();
	});

	it('requests and replace url if channelId is not in location', async () => {
		const spy = jest.fn();
		const WrappedComponent = CheckSegmentLink(RenderText);

		API.individualSegment.fetch.mockReturnValueOnce(
			Promise.resolve({channelId: 123, id: 456})
		);

		const {container} = render(
			<WrappedComponent
				groupId="faro-dev-liferay"
				history={{replace: spy}}
				location={{
					pathname:
						'/workspace/faro-dev-liferay/contacts/segments/456',
				}}
			/>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(spy).toBeCalledWith(
			'/workspace/faro-dev-liferay/123/contacts/segments/456'
		);
	});
});
