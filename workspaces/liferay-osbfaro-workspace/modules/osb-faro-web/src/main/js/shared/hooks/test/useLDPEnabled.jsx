/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {renderHook} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {useLDPEnabled} from '~/shared/hooks/useLDPEnabled';
import {SubscriptionNames} from '~/shared/util/subscriptions';
import mockStore from '~/test/mock-store';

jest.unmock('react-dom');

describe('useLDPEnabled', () => {
	it('returns true when the plan name contains "Data Platform"', () => {
		const groupId = '23';

		const initialState = fromJS({
			projects: {
				[groupId]: {
					data: {
						faroSubscription: {
							name: SubscriptionNames.LiferayDataPlatformPrivateBeta,
						},
					},
				},
			},
		});

		const store = mockStore(initialState);
		const wrapper = ({children}) => (
			<Provider store={store}>{children}</Provider>
		);

		const {result} = renderHook(() => useLDPEnabled({groupId}), {wrapper});

		expect(result.current).toBe(true);
	});

	it('returns false when the plan name does NOT contain "Data Platform"', () => {
		const groupId = '123';
		const initialState = fromJS({
			projects: {
				[groupId]: {
					data: {
						faroSubscription: {
							name: SubscriptionNames.LiferayAnalyticsCloudEnterprise,
						},
					},
				},
			},
		});

		const store = mockStore(initialState);
		const wrapper = ({children}) => (
			<Provider store={store}>{children}</Provider>
		);

		const {result} = renderHook(() => useLDPEnabled({groupId}), {wrapper});

		expect(result.current).toBe(false);
	});
});
