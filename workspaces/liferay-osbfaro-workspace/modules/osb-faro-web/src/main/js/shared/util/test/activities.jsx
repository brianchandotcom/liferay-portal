/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as data from '~/test/data';

import {
	buildLegendItems,
	formatEvents,
	formatGroupingTime,
	formatSessions,
	getActivityLabel,
	getSafeRangeKey,
} from '../activities';

describe('activities', () => {
	describe('buildLegendItems', () => {
		it('returns an array formatted for use as items in ChangeLegend', () => {
			const mockChangeData = {
				activityChange: 20,
				activityCount: 10,
			};

			const result = buildLegendItems(mockChangeData);

			expect(Array.isArray(result)).toBe(true);
			expect(result.length).toBe(1);
			expect(result[0].change).toBe(20);
			expect(result[0].id).toBe('activities');
			expect(result[0].secondaryInfo).toContain('30');
			expect(result[0].title).toContain('10');
		});
	});

	describe('formatGroupingTime', () => {
		it('formats grouping time', () => {
			const result = formatGroupingTime(data.getTimestamp());

			expect(typeof result).toBe('string');
			expect(result.length).toBeGreaterThan(0);
		});
	});

	describe('formatEvents', () => {
		it('decodes canonicalUrl into subtitle for DXP events', () => {
			const result = formatEvents([
				{
					applicationId: 'Page',
					assetTitle: 'this is a page title',
					canonicalUrl:
						'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88',
					eventId: 'pageViewed',
					name: 'eventName',
					pageDescription: 'this is a page description',
					pageTitle: 'this is a page title',
					referrer:
						'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88',
					url: 'http://localhost:7400/%e6%96%b0%e3%81%97%e3%81%84%e3%82%b5%e3%82%a4%e3%83%88',
				},
			]);

			expect(result).toMatchObject([
				{
					attributes: {
						applicationId: 'Page',
						eventId: 'pageViewed',
					},
					description: 'this is a page title',
					subtitle: 'http://localhost:7400/新しいサイト',
					title: 'eventName',
				},
			]);
		});

		it('does not set subtitle for HubSpot events', () => {
			const result = formatEvents([
				{
					applicationId: 'HubSpot',
					assetTitle: null,
					canonicalUrl: 'https://hubspot.com',
					eventId: 'emailView',
					name: 'emailView',
				},
			]);

			expect(result[0].subtitle).toBeUndefined();
		});

		it('transforms properties array into an object in attributes', () => {
			const result = formatEvents([
				{
					applicationId: 'HubSpot',
					eventId: 'formSubmit',
					name: 'formSubmit',
					properties: [
						{name: 'formId', value: 'abc123'},
						{name: 'pageUrl', value: 'https://hubspot.com/landing'},
					],
				},
			]);

			expect(result[0].attributes.properties).toEqual({
				formId: 'abc123',
				pageUrl: 'https://hubspot.com/landing',
			});
		});

		it('includes eventDate in attributes only when present', () => {
			const withDate = formatEvents([
				{
					applicationId: 'Page',
					eventDate: '2026-05-07T19:57:21.000Z',
					eventId: 'pageViewed',
					name: 'pageViewed',
				},
			]);

			const withoutDate = formatEvents([
				{
					applicationId: 'Page',
					eventId: 'pageViewed',
					name: 'pageViewed',
				},
			]);

			expect(withDate[0].attributes.eventDate).toBe(
				'2026-05-07T19:57:21.000Z'
			);
			expect(withoutDate[0].attributes).not.toHaveProperty('eventDate');
		});
	});

	describe('formatSessions', () => {
		it('formats sessions', () => {
			const result = formatSessions(
				[data.mockSession(2, {}, {assetType: 'foo'})],
				'123',
				'321'
			);

			expect(Array.isArray(result)).toBe(true);
			expect(result.length).toBeGreaterThan(0);

			const header = result[0];
			expect(header.header).toBe(true);
			expect(typeof header.title).toBe('string');
			expect(typeof header.totalEvents).toBe('number');

			const session = result[1];
			expect(session).toHaveProperty('attributes');
			expect(session).toHaveProperty('device');
			expect(session).toHaveProperty('nestedItems');
			expect(Array.isArray(session.nestedItems)).toBe(true);
		});
	});

	describe('getActivityLabel', () => {
		it('gets singular label', () => {
			const result = getActivityLabel(1);

			expect(Array.isArray(result)).toBe(true);
			expect(result.length).toBe(2);
			expect(result[0]).toContain('Event');
		});

		it('plurals label', () => {
			const result = getActivityLabel(2);

			expect(Array.isArray(result)).toBe(true);
			expect(result.length).toBe(2);
			expect(result[0]).toContain('Events');
		});
	});

	describe('getSafeRangeKey', () => {
		it('returns the rangeKey when it is different of CUSTOM', () => {
			const rangeKey = getSafeRangeKey('30');

			expect(rangeKey).toBe('30');
		});

		it('returns null when it is CUSTOM', () => {
			const rangeKey = getSafeRangeKey('CUSTOM');

			expect(rangeKey).toBe(null);
		});
	});
});
