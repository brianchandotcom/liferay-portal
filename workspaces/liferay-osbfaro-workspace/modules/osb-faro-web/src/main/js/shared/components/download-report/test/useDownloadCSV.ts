import {renderHook} from '@testing-library/react';
import {RangeKeyTimeRanges} from '~/shared/util/constants';

import {CSVType, useDownloadCSV} from '../utils';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: jest.fn(() => ({
		channelId: '123',
		groupId: '456',
		title: 'my asset title',
	})),
}));

describe('useDownloadCSV', () => {
	beforeEach(() => {
		delete (window as {location?: Location}).location;

		(window as {location: unknown}).location = new URL(
			'https://analytics.liferay.com/workspace/liferay.com/420253908131944590'
		);
	});

	it('returns the correct URL for default parameters', () => {
		const {result} = renderHook(() =>
			useDownloadCSV({type: CSVType.Individual})
		);

		const url = result.current({
			rangeEnd: '',
			rangeKey: RangeKeyTimeRanges.Last30Days,
			rangeStart: '',
		});

		expect(url).toBe(
			'/o/faro/main/456/reports/export/csv/individual?channelId=123&rangeKey=30&assetTitle=my asset title'
		);
	});

	it('includes custom date range in the URL', () => {
		const {result} = renderHook(() =>
			useDownloadCSV({type: CSVType.Individual})
		);

		const url = result.current({
			rangeEnd: '2024-01-31',
			rangeKey: RangeKeyTimeRanges.CustomRange,
			rangeStart: '2024-01-01',
		});
		expect(url).toContain('&rangeKey=CUSTOM');
		expect(url).toContain('&fromDate=2024-01-01');
		expect(url).toContain('&toDate=2024-01-31');
	});

	it('includes optional parameters when provided', () => {
		const {result} = renderHook(() =>
			useDownloadCSV({
				assetId: '12345',
				assetType: 'blog',
				individualId: '67890',
				segmentId: 'segment123',
				type: CSVType.Individual,
			})
		);

		const url = result.current({
			rangeEnd: '',
			rangeKey: RangeKeyTimeRanges.Last30Days,
			rangeStart: '',
		});

		expect(url).toBe(
			'/o/faro/main/456/reports/export/csv/individual?channelId=123&rangeKey=30&assetId=12345&assetTitle=my asset title&assetType=blog&individualId=67890&segmentId=segment123'
		);
	});

	it('includes order by fields if field and sortOrder are present', () => {
		(window as {location: unknown}).location = new URL(
			'https://analytics.liferay.com/workspace/liferay.com/420253908131944590/?field=name&page=1&sortOrder=DESC'
		);

		const {result} = renderHook(() =>
			useDownloadCSV({type: CSVType.Individual})
		);

		const url = result.current({
			rangeEnd: '',
			rangeKey: RangeKeyTimeRanges.Last30Days,
			rangeStart: '',
		});

		expect(url).toBe(
			'/o/faro/main/456/reports/export/csv/individual?channelId=123&rangeKey=30&assetTitle=my asset title&orderByFields=%5B%7B%22fieldName%22%3A%22givenName%22%2C%22orderBy%22%3A%22desc%22%2C%22system%22%3Afalse%7D%2C%7B%22fieldName%22%3A%22familyName%22%2C%22orderBy%22%3A%22desc%22%2C%22system%22%3Afalse%7D%5D'
		);
	});
});
