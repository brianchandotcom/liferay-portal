/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ACCOUNTS, Routes, SEGMENTS, toRoute} from '~/shared/util/router';

type IBasicRouteArgs = {
	groupId: string;
	label?: string | null;
};

type IBasicSidebarRouteArgs = IBasicRouteArgs & {
	channelId: string;
};

export type IBreadcrumbArgs = {
	active?: boolean;
	groupId?: string;
	href?: string | null;
	id?: string;
	label: string;
	truncate?: boolean;
};

export const getEntityName = function getEntityName({
	active = true,
	label,
	truncate = true,
	...otherData
}: IBreadcrumbArgs) {
	return {
		active,
		label,
		truncate,
		...otherData,
	};
};

/**
 * Home
 */
export const getHome = function getHome({
	channelId,
	groupId,
	label,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.SITES, {channelId, groupId}),
		label: label ? label : Liferay.Language.get('home'),
	};
};

/**
 * Entities
 */
export const getAccounts = function getAccounts({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.CONTACTS_LIST_ENTITY, {
			channelId,
			groupId,
			type: ACCOUNTS,
		}),

		label: Liferay.Language.get('accounts'),
	};
};

export const getIndividuals = function getIndividuals({
	LDPEnabled,
	channelId,
	groupId,
}: IBasicSidebarRouteArgs & {LDPEnabled: boolean}) {
	return {
		href: toRoute(
			LDPEnabled
				? Routes.CONTACTS_INDIVIDUALS
				: Routes.CONTACTS_INDIVIDUALS_KNOWN_INDIVIDUALS,
			{
				channelId,
				groupId,
			}
		),

		label: Liferay.Language.get('individuals'),
	};
};

export const getSegments = function getSegments({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.CONTACTS_LIST_ENTITY, {
			channelId,
			groupId,
			type: SEGMENTS,
		}),

		label: Liferay.Language.get('segments'),
	};
};

export const getSites = function getSites({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.SITES, {
			channelId,
			groupId,
		}),

		label: Liferay.Language.get('sites'),
		truncate: true,
	};
};

export const getPages = function getPages({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.SITES_TOUCHPOINTS, {
			channelId,
			groupId,
		}),

		label: Liferay.Language.get('pages'),
		truncate: true,
	};
};

export const getTests = function getTests({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.TESTS, {channelId, groupId}),
		label: Liferay.Language.get('tests'),
	};
};

/**
 * Assets
 */
export const getAssets = function getAssets({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.ASSETS, {channelId, groupId}),
		label: Liferay.Language.get('assets'),
	};
};

export const getBlogs = function getBlogs({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.ASSETS_BLOGS, {channelId, groupId}),
		label: Liferay.Language.get('blogs'),
	};
};

export const getCustomContent = function getCustomContent({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.ASSETS_CUSTOM, {
			channelId,
			groupId,
		}),

		label: Liferay.Language.get('custom'),
	};
};

export const getDocumentsAndMedia = function getDocumentsAndMedia({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.ASSETS_DOCUMENTS_AND_MEDIA, {
			channelId,
			groupId,
		}),

		label: Liferay.Language.get('documents-and-media'),
	};
};

export const getForms = function getForms({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.ASSETS_FORMS, {
			channelId,
			groupId,
		}),

		label: Liferay.Language.get('forms'),
	};
};

export const getWebContent = function getWebContent({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.ASSETS_WEB_CONTENT, {
			channelId,
			groupId,
		}),

		label: Liferay.Language.get('web-content'),
	};
};

/**
 * Settings
 */
export const getChannels = function getChannels({groupId}: IBasicRouteArgs) {
	return {
		href: toRoute(Routes.SETTINGS_CHANNELS, {groupId}),
		label: Liferay.Language.get('properties'),
	};
};

export const getChannelName = function getChannelName({
	active = false,
	groupId,
	id,
	label,
}: IBreadcrumbArgs) {
	return {
		active,

		href:
			groupId && id
				? toRoute(Routes.SETTINGS_CHANNELS_VIEW, {
						groupId,
						id,
					})
				: null,

		label,
		truncate: true,
	};
};

export const getDataPrivacy = function getDataPrivacy({
	groupId,
}: IBasicRouteArgs) {
	return {
		href: toRoute(Routes.SETTINGS_DATA_PRIVACY, {groupId}),
		label: Liferay.Language.get('data-control-&-privacy'),
	};
};

export const getDataSources = function getDataSources({
	groupId,
}: IBasicRouteArgs) {
	return {
		href: toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {groupId}),
		label: Liferay.Language.get('data-sources'),
	};
};

export const getDataSourceName = function getDataSourceName({
	active = false,
	groupId,
	id,
	label,
}: IBreadcrumbArgs) {
	return {
		active,

		href:
			groupId && id
				? toRoute(Routes.SETTINGS_DATA_SOURCE, {
						groupId,
						id,
					})
				: null,

		label,
		truncate: true,
	};
};

export const getDefinitions = function getDefinitions({
	groupId,
}: IBasicRouteArgs) {
	return {
		href: toRoute(Routes.SETTINGS_DEFINITIONS, {groupId}),
		label: Liferay.Language.get('definitions'),
	};
};

export const getRecommendations = function getRecommendations({
	groupId,
}: IBasicRouteArgs) {
	return {
		href: toRoute(Routes.SETTINGS_RECOMMENDATIONS, {groupId}),
		label: Liferay.Language.get('recommendations'),
	};
};

export const getEvents = function getEvents({groupId}: IBasicRouteArgs) {
	return {
		href: toRoute(Routes.SETTINGS_DEFINITIONS_EVENTS_DEFAULT, {groupId}),
		label: Liferay.Language.get('events'),
	};
};

export const getEventAnalysis = function getEventAnalysis({
	channelId,
	groupId,
}: IBasicSidebarRouteArgs) {
	return {
		href: toRoute(Routes.EVENT_ANALYSIS, {
			channelId,
			groupId,
		}),

		label: Liferay.Language.get('event-analysis'),
	};
};

export const getEventAttributes = function getEventAttributes({
	groupId,
}: IBasicRouteArgs) {
	return {
		href: toRoute(Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_GLOBAL, {
			groupId,
		}),

		label: Liferay.Language.get('event-attributes'),
	};
};
