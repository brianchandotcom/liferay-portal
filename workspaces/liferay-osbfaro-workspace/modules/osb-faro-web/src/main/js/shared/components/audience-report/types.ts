/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type DataPoint = {
	color: string;
	count: number;
	label: string;
};

export type Dataset = {
	data: DataPoint[];
	empty: Empty;
	total: number;
};

export type Empty = {
	message: string;
	show: boolean;
};

export type TData = {
	audienceReport: {
		anonymousUsersCount: number;
		knownUsersCount: number;
		nonsegmentedKnownUsersCount: number;
		segmentedAnonymousUsersCount: number;
		segmentedKnownUsersCount: number;
	};
	segment: {
		metrics: {
			value: string;
			valueKey: string;
		}[];
		total: number;
	};
};

export enum Name {
	Blog = 'blog',
	Document = 'document',
	Form = 'form',
	Journal = 'journal',
	ObjectEntry = 'objectEntry',
	Page = 'page',
}

export interface IAudienceReportBaseCardProps {
	knownIndividualsTitle: string;
	query: {
		metricName: string;
		name: Name;
	};
	segmentsTitle?: string;
	uniqueVisitorsTitle?: string;
}
