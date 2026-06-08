/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	flattenDepth,
	flow,
	groupBy,
	map,
	mapValues,
	orderBy,
	toPairs,
} from 'lodash/fp';
import moment from 'moment';
import React from 'react';
import {DEFAULT_ACTIVITY_MAX} from '~/shared/api/activities';
import {UserSession, UserSessionEvent} from '~/shared/queries/UserSessionQuery';
import {RangeSelectors} from '~/shared/types';
import {TimeIntervals} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';

import {getSafeDecodedURIComponent} from './util';

export const CHART_ACTIVITY_ID = 'activities';
export const CHART_ID = 'individualActivity';

export const INTERVAL_MAP = {
	D: TimeIntervals.Day,
	M: TimeIntervals.Month,
	W: TimeIntervals.Week,
};

type SessionEvent = {
	attributes: Record<string, unknown>;
	description: string;
	subtitle: string | undefined;
	time: moment.Moment;
	title: string;
};

export type UserSessionAttributes = {
	contentLanguageID: string;
	description: string;
	devicePixelRatioz: number;
	header: string;
	keywords: string;
	languageID: string;
	screenHeight: number;
	screenWidth: number;
	timezoneOffset: string;
	userAgent: string;
};

export type VerticalTimelineHeader = {
	header: boolean;
	title: moment.Moment;
	totalEvents: number;
};

export type VerticalTimelineSession = {
	applicationId: string;
	attributes: UserSessionAttributes;
	device: string;
	endTime: Date;
	nestedItems: SessionEvent[];
	time: moment.Moment;
	userAgent: string;
};

/**
 * Format actvitiy metrics for use in ChangeLegend
 * @param {Object} changeMetrics - History data points.
 * @param {number} changeMetrics.activityChange - The activity count change from
 *                                                previous period.
 * @param {number} changeMetrics.activityCount - The activity count.
 * @return {Array} Activity metrics formatted for use in ChangeLegend.
 */
export const buildLegendItems = function buildLegendItems({
	activityChange,
	activityCount,
}: {
	activityChange: number;
	activityCount: number;
}): {change: number; id: string; secondaryInfo: string; title: string}[] {
	return [
		{
			change: activityChange,
			id: CHART_ACTIVITY_ID,
			secondaryInfo: sub(Liferay.Language.get('x-day-change'), [
				DEFAULT_ACTIVITY_MAX,
			]) as string,
			title: sub(Liferay.Language.get('total-activity-count-x'), [
				activityCount.toLocaleString(),
			]) as string,
		},
	];
};

/**
 * Formats UserSessions events and maps its attributes to the required to be used in VerticalTimeline component.
 * @param {Array} events Array of UserSessions events.
 * @returns {Array.<Object>} Array of objects for a vertical timeline.
 */
export const formatEvents = function formatEvents(
	events: UserSessionEvent[]
): Array<SessionEvent> {
	return events.map(
		({
			applicationId,
			assetTitle,
			canonicalUrl,
			createDate,
			eventDate,
			eventId,
			name,
			properties,
		}) => ({
			attributes: {
				applicationId,
				...(eventDate && {eventDate}),
				eventId,
				...(properties?.length && {
					properties: Object.fromEntries(
						properties.map(({name: propName, value}) => [
							propName,
							value,
						])
					),
				}),
			},
			description: assetTitle,
			subtitle:
				applicationId !== 'HubSpot'
					? getSafeDecodedURIComponent(canonicalUrl)
					: undefined,
			time: moment(createDate),
			title: name,
		})
	);
};

/**
 * Formats datetime to today or the current date.
 * @param {Date|string|number} datetime - Any value accepeted by Moment.
 * @returns {Moment} Date label to be displayed.
 */
export const formatGroupingTime = function formatGroupingTime(
	datetime: Date | string | number
): string {
	const time = moment(datetime);

	return time.isSame(moment(), 'day')
		? Liferay.Language.get('today')
		: time.utc().format('ll');
};

/**
 * Format sessions into a format usable by the VerticalTimeline component while grouping them by day.
 * @param {Array} sessions
 * @returns {Array.<Object>} An array of session objects.
 */
export const formatSessions = function formatSessions(
	sessions: UserSession[]
): (VerticalTimelineHeader | VerticalTimelineSession)[] {
	return flow(
		groupBy(({createDate}: UserSession) =>
			moment.utc(createDate).startOf('day').format()
		),
		mapValues((items: unknown) =>
			(items as (UserSession & {createDate: string})[]).map(
				({
					browserName,
					completeDate,
					contentLanguageID,
					createDate,
					devicePixelRatioz,
					deviceType,
					events,
					languageID,
					screenHeight,
					screenWidth,
					timezoneOffset,
					userAgent,
				}) => ({
					applicationId:
						(events as unknown as UserSessionEvent[])[0]
							?.applicationId ?? '',
					attributes: {
						contentLanguageID,
						devicePixelRatioz,
						header: Liferay.Language.get('session-attributes'),
						languageID,
						screenHeight,
						screenWidth,
						timezoneOffset,
						userAgent,
					},
					browserName,
					device: deviceType,
					endTime: completeDate,
					nestedItems: formatEvents(
						events as unknown as UserSessionEvent[]
					),
					time: createDate,
					userAgent,
				})
			)
		),
		toPairs,
		orderBy([([time]) => moment(time).unix()], ['desc']),
		map(([time, items]: [string, {nestedItems: unknown[]}[]]) => [
			{
				header: true,
				title: formatGroupingTime(time),
				totalEvents: items.reduce(
					(
						previousValue: number,
						currentValue: {nestedItems: unknown[]}
					) => previousValue + currentValue.nestedItems.length,
					0
				),
			},
			items,
		]),
		flattenDepth(3)
	)(sessions);
};

/**
 * Helper function get the correct pluralization of count label.
 * @param {Number} totalEvents
 * @returns {Array} Label to be displayed.
 */
export const getActivityLabel = function getActivityLabel(
	totalEvents: number
): React.ReactNode[] {
	return sub(
		totalEvents === 1
			? Liferay.Language.get('event-x')
			: Liferay.Language.get('events-x'),
		[<b key="ACTIVITIES">{totalEvents}</b>],
		false
	) as React.ReactNode[];
};

export const getSafeRangeKey = function getSafeRangeKey(
	rangeKey: RangeSelectors['rangeKey']
): RangeSelectors['rangeKey'] | null {
	if (rangeKey === 'CUSTOM') {
		return null;
	}

	return rangeKey;
};
