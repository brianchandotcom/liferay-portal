/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import TouchpointRoutes from '../TouchpointRoutes';

jest.unmock('react-dom');

jest.mock('~/shared/components/download-report/DownloadCSVReport', () => ({
	__esModule: true,
	default: ({assetId}) => (
		<div data-asset-id={assetId} data-testid="download-csv-report" />
	),
}));

jest.mock('~/shared/components/download-report/DownloadPDFReport', () => ({
	__esModule: true,
	default: () => null,
}));

jest.mock('~/shared/components/dropdown-range-key/DropdownRangeKey', () => ({
	DropdownRangeKey: () => null,
}));

jest.mock('~/shared/components/Loading', () => ({
	__esModule: true,
	default: () => null,
}));

jest.mock('~/shared/components/RouteNotFound', () => ({
	__esModule: true,
	default: () => null,
}));

jest.mock('~/route-middleware/BundleRouter', () => ({
	__esModule: true,
	default: () => null,
}));

jest.mock('../../components/ExperienceDropdown', () => ({
	__esModule: true,
	default: () => null,
}));

jest.mock('../../components/FilterBySegment', () => ({
	__esModule: true,
	default: () => null,
}));

jest.mock('~/shared/context/channel', () => ({
	useChannelContext: () => ({selectedChannel: {name: 'test channel'}}),
}));

jest.mock('~/shared/context/dataSources', () => ({
	useDataSources: () => ({empty: false}),
}));

jest.mock('~/shared/hooks/useQueryRangeSelectors', () => ({
	useQueryRangeSelectors: () => ({rangeKey: '30'}),
}));

jest.mock('~/shared/util/router', () => {
	const actual = jest.requireActual('~/shared/util/router');

	return {
		...actual,
		getMatchedRoute: jest.fn(
			() => actual.Routes.SITES_TOUCHPOINTS_KNOWN_INDIVIDUALS
		),
	};
});

describe('TouchpointRoutes', () => {
	it('forwards a percent-encoded touchpoint as assetId for the Known Individuals CSV download', () => {
		const router = {
			params: {
				channelId: '1',
				experienceId: '',
				groupId: '2',
				title: 'page',
				touchpoint: 'http://example.com/web/site/人事発告',
			},
		};

		render(
			<Provider store={mockStore()}>
				<MemoryRouter>
					<TouchpointRoutes router={router} />
				</MemoryRouter>
			</Provider>
		);

		expect(
			screen
				.getByTestId('download-csv-report')
				.getAttribute('data-asset-id')
		).toBe(
			'http://example.com/web/site/%E4%BA%BA%E4%BA%8B%E7%99%BA%E5%91%8A'
		);
	});
});
