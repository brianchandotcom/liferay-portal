/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {MetricName} from '~/shared/types/MetricName';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {
	mockAudienceReportReq,
	mockPreferenceReq,
	mockTimeRangeReq,
} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import AudienceReport from '../AudienceReport';
import {PageAudienceReportQuery} from '../queries';
import {Name} from '../types';

jest.unmock('react-dom');

const tooltipEnabled = jest.fn();

jest.mock('recharts', () => {
	const OriginalModule = jest.requireActual('recharts');

	return {
		...OriginalModule,
		ResponsiveContainer: ({children}: {children: React.ReactNode}) => (
			<OriginalModule.ResponsiveContainer height={350} width={800}>
				{children}
			</OriginalModule.ResponsiveContainer>
		),
		Tooltip: ({
			children,
			...props
		}: {
			active?: boolean;
			children: React.ReactNode;
		}) => {
			if (props.active) {
				tooltipEnabled();
			}

			return (
				<OriginalModule.Tooltip {...props} active>
					{children}
				</OriginalModule.Tooltip>
			);
		},
	};
});

const WrappedComponent = ({queryProps}: {queryProps: any}) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				'/workspace/123/456/Home%20Page/https%3A%2F%2Fwww.liferay.com',
			]}
		>
			<Route path="/workspace/:groupId/:channelId/:title/:touchpoint">
				<MockedProvider
					{...({freezeResults: false} as any)}
					mocks={[
						mockTimeRangeReq(),
						mockPreferenceReq(),
						mockAudienceReportReq({queryProps}),
					]}
				>
					<AudienceReport
						Query={PageAudienceReportQuery(queryProps)}
						filters={{devices: [], location: []}}
						mapper={(result: any) =>
							result?.[queryProps.name]?.[queryProps.metricName]
						}
						name={Name.Page}
						rangeSelectors={{
							rangeEnd: '',
							rangeKey: RangeKeyTimeRanges.Last30Days,
							rangeStart: '',
						}}
					/>
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('AudienceReport', () => {
	it('renders', async () => {
		const {container} = render(
			<WrappedComponent
				queryProps={{
					metricName: MetricName.Views,
					name: Name.Page,
				}}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders a tooltip when donuts mouse over', async () => {
		const {container} = render(
			<WrappedComponent
				queryProps={{
					metricName: MetricName.Views,
					name: Name.Page,
				}}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		const donut = container.querySelector('.recharts-pie-sector');

		expect(donut).toBeInTheDocument();

		fireEvent.mouseEnter(donut!);

		expect(tooltipEnabled).toHaveBeenCalledTimes(1);
	});
});
