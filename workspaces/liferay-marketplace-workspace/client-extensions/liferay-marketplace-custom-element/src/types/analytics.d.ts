/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type AnalyticsProject = {
	accountKey: string;
	accountName: string;
	corpProjectName: string;
	corpProjectUuid: string;
	faroSubscription: FaroSubscription;
	faroSubscriptionDisplay: FaroSubscription;
	friendlyURL: string;
	groupId: number;
	incidentReportEmailAddresses: string[];
	name: string;
	ownerEmailAddress: string;
	recommendationsEnabled: boolean;
	serverLocation: string;
	state: string;
	stateEndDate: null;
	stateStartDate: null;
	timeZone: TimeZone;
	userId: number;
};

type AnalyticsViews = {
	results: {
		metrics: {
			avgTimeOnPageMetric: {
				value: number;
			};
			bounceMetric: {
				value: number;
			};
			bounceRateMetric: {
				value: number;
			};
			ctaClicksMetric: {
				value: number;
			};
			directAccessMetric: {
				value: number;
			};
			entrancesMetric: {
				value: number;
			};
			exitRateMetric: {
				value: number;
			};
			indirectAccessMetric: {
				value: number;
			};
			readsMetric: {
				value: number;
			};
			sessionsMetric: {
				value: number;
			};
			timeOnPageMetric: {
				value: number;
			};
			viewsMetric: {
				value: number;
			};
			visitorsMetric: {
				value: number;
			};
		};
		title: string;
		url: string;
	}[];
	total: number;
};

type FaroSubscription = {
	active: boolean;
	addOns: any[];
	endDate: null;
	individualsCountSinceLastAnniversary: number;
	individualsCounts: null;
	individualsLimit: number;
	individualsStatus: number;
	lastAnniversaryDate: null;
	name: string;
	pageViewsCountSinceLastAnniversary: number;
	pageViewsCounts: null;
	pageViewsLimit: number;
	pageViewsStatus: number;
	startDate: number;
	syncedIndividualsCount: number;
};

type TimeZone = {
	country: string;
	displayTimeZone: string;
	timeZoneId: string;
};