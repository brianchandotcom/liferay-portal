/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fromJS} from 'immutable';
import {mockSubscription} from '~/test/data';

import {Plan} from '../../util/records';
import {
	INDIVIDUALS,
	PAGEVIEWS,
	SubscriptionNames,
	formatPlanData,
	getPlanAddOns,
	getPlanLabel,
	getPropIcon,
	getPropLabel,
	isLDPPlan,
} from '../subscriptions';

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({
		timeZoneId: 'UTC',
	}),
}));

describe('subscriptions', () => {
	describe('getPlanAddOns', () => {
		it('returns the correct plan addons', () => {
			const planAddOns = getPlanAddOns(
				formatPlanData(
					fromJS(
						mockSubscription({
							individualsCount: 5000,
							name: SubscriptionNames.LiferayAnalyticsCloudEnterprise,
							pageViewsCount: 5000000,
						})
					)
				)
			);

			expect(planAddOns).toEqual({
				individuals: '10,000',
				pageViews: '5,000,000',
			});
		});

		it('does not have addons for LXC customers', () => {
			const planAddOns = getPlanAddOns(
				formatPlanData(
					fromJS(
						mockSubscription({
							individualsCount: 5000,
							name: SubscriptionNames.LxcSubscriptionEngageSite,
							pageViewsCount: 5000000,
						})
					)
				)
			);

			expect(planAddOns).toEqual({});
		});
	});

	describe('getPlanLabel', () => {
		it('returns the label for the Liferay Data Platform plan', () => {
			expect(getPlanLabel(SubscriptionNames.LiferayDataPlatform)).toEqual(
				'Liferay Data Platform'
			);
		});

		it('returns the label for the Liferay Data Platform Private Beta plan', () => {
			expect(
				getPlanLabel(SubscriptionNames.LiferayDataPlatformPrivateBeta)
			).toEqual('Liferay Data Platform (Private Beta)');
		});

		it('returns the label for the Liferay Data Platform Enterprise plan', () => {
			expect(
				getPlanLabel(SubscriptionNames.LiferayDataPlatformEnterprise)
			).toEqual('Liferay Data Platform Enterprise');
		});

		it('returns an empty string for an unknown plan', () => {
			expect(getPlanLabel('something-unknown')).toEqual('');
		});
	});

	describe('getPropIcon', () => {
		it('returns the prop icon symbol', () => {
			const symbol = getPropIcon(INDIVIDUALS);

			expect(symbol).toEqual('ac_individual');
		});
	});

	describe('getPropLabel', () => {
		it('returns the correct prop label', () => {
			const label = getPropLabel(PAGEVIEWS);

			expect(label).toEqual('Page Views');
		});
	});

	describe('formatPlanData', () => {
		it('formats the plan data as a basic Plan record', () => {
			const plan = formatPlanData(
				fromJS(
					mockSubscription({
						name: SubscriptionNames.LiferayAnalyticsCloudBasic,
					})
				)
			);

			expect(plan).toBeInstanceOf(Plan);

			const metrics = plan.metrics;

			const individualsMetrics = metrics.get('individuals');

			expect(individualsMetrics.count).toEqual(2057);

			const pageViewsMetrics = metrics.get('pageViews');

			expect(pageViewsMetrics.count).toEqual(100023);
		});

		it('formats the plan data as an enterprise Plan record', () => {
			const plan = formatPlanData(fromJS(mockSubscription()));

			expect(plan).toBeInstanceOf(Plan);

			const metrics = plan.metrics;

			const individualsMetrics = metrics.get('individuals');

			expect(individualsMetrics.count).toEqual(2057);

			const pageViewsMetrics = metrics.get('pageViews');

			expect(pageViewsMetrics.count).toEqual(100023);
		});

		it('formats the plan data when faroSusbcription is null', () => {
			const plan = formatPlanData(null);

			expect(plan).toMatchSnapshot();
		});
	});

	describe('isLDPPlan', () => {
		it.each([
			SubscriptionNames.LiferayDataPlatform,
			SubscriptionNames.LiferayDataPlatformEnterprise,
			SubscriptionNames.LiferayDataPlatformPrivateBeta,
		])('returns true for %s', (name) => {
			expect(isLDPPlan(name)).toBe(true);
		});

		it.each([
			SubscriptionNames.LiferayAnalyticsCloudBasic,
			SubscriptionNames.LiferayAnalyticsCloudBusiness,
			SubscriptionNames.LiferayAnalyticsCloudEnterprise,
			SubscriptionNames.LiferaySaasEnterprisePlan,
			SubscriptionNames.LxcBusinessPlan,
		])('returns false for non-LDP plan %s', (name) => {
			expect(isLDPPlan(name)).toBe(false);
		});

		it('returns false when the subscription is missing (null/undefined)', () => {
			expect(isLDPPlan(null)).toBe(false);
			expect(isLDPPlan(undefined)).toBe(false);
		});
	});
});
