/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useMemo, useState} from 'react';
import {useAppContext} from '~/features/project/context';
import {Liferay} from '~/services/liferay';
import {useGetAccountSubscriptions} from '~/services/liferay/graphql/account-subscriptions';
import i18n from '~/utils/I18n';

interface IAddOn {
	infoText?: string;
	name: string;
	title: string;
}

interface IData {
	infoText?: string;
	maxCount?: number;
	title: string;
	usedCount?: number;
}

export interface IChartData extends IData {
	dataSizeUnits?: string;
	maxCountText?: string;
}

interface IUsageData {
	resourceUsage: IChartData[];
	siteAndUsers: IData[];
}

export enum SiteAndUserDataEnum {
	ANONYMOUS_PAGE_VIEWS = 'anonymousPageViews',
	CLIENT_EXTENSIONS_CAPACITY_CPU = 'clientExtensionsCapacityCPU',
	CLIENT_EXTENSIONS_CAPACITY_RAM = 'clientExtensionsCapacityRAM',
	MONTHLY_ACTIVE_LOGGED_IN_USERS = 'monthlyActiveLoggedInUsers',
	SITES = 'sites',
	STORAGE_CAPACITY_DOCUMENT_LIBRARY = 'storageCapacityDocumentLibrary',
}

enum ADD_ON_NAMES {
	DEDICATED_RESOURCES = 'Dedicated Resources',
	PRIVATE_CLUSTER = 'Private Cluster',
}

const DEFAULT_USAGE_DATA_VALUES = {
	resourceUsage: [
		{
			title: i18n.translate('extension-capacity-ram'),
		},
		{
			title: i18n.translate('extension-capacity-vcpu'),
		},
		{
			title: i18n.translate('storage-capacity'),
		},
	],
	siteAndUsers: [
		{
			title: i18n.translate('number-of-sites'),
		},
		{
			title: i18n.translate('authenticated-logins-malus'),
		},
		{
			title: i18n.translate('anonymous-page-views-apv'),
		},
	],
};

const ADD_ONS_CARDS = [
	{
		infoText: i18n.translate(
			'dedicated-resources-provide-customers-with-a-private-liferay-installation'
		),
		name: ADD_ON_NAMES.DEDICATED_RESOURCES,
		title: i18n.translate('dedicated-resources'),
	},
	{
		infoText: i18n.translate(
			'a-private-cluster-separates-all-infrastructure-resources-and-allows-for-site-to-site-vpn-configuration'
		),
		name: ADD_ON_NAMES.PRIVATE_CLUSTER,
		title: i18n.translate('private-cluster'),
	},
];

const ACCEPTED_SUBSCRIPTIONS = ['Business Plan', 'Enterprise Plan', 'Pro Plan'];
const ADD_ONS = ['Dedicated Resources', 'Private Cluster'];

const formatedSubscriptions = () =>
	[...ACCEPTED_SUBSCRIPTIONS, ...ADD_ONS]
		.map((projectName) => `'${projectName}'`)
		.join(',');

const useProjectUsageData = () => {
	const [usageData, setUsageData] = useState<IUsageData>(
		DEFAULT_USAGE_DATA_VALUES
	);
	const [isLoading, setIsLoading] = useState(true);

	const [{project}] = useAppContext();

	const {data: subscriptionsData} = useGetAccountSubscriptions({
		filter: `name in (${formatedSubscriptions()}) and accountSubscriptionGroupERC eq '${
			project?.accountKey
		}_liferay-saas'`,
	});

	const displayUsage = useMemo(() => {
		return !!subscriptionsData?.c?.accountSubscriptions?.items.some(
			({name}: {name: string}) => ACCEPTED_SUBSCRIPTIONS.includes(name)
		);
	}, [subscriptionsData]);

	const addOns = useMemo<IAddOn[]>(() => {
		const filteredAddOns =
			subscriptionsData?.c?.accountSubscriptions?.items?.filter(
				({name}: {name: string}) => ADD_ONS.includes(name)
			);

		return ADD_ONS_CARDS.filter(
			(card) =>
				!filteredAddOns?.some(
					({name}: {name: string}) => card.name === name
				)
		);
	}, [subscriptionsData]);

	const getSiteAndUsers = useCallback(async () => {
		if (project?.externalReferenceCode) {
			if (!displayUsage && subscriptionsData) {
				return setIsLoading(false);
			}

			const response =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch(`/accounts/${project?.externalReferenceCode}/usage`)
					.then((response: {json: () => any}) => response.json())
					.catch(console.error);

			if (response) {
				const formatedData = {
					resourceUsage: [
						{
							...response[
								SiteAndUserDataEnum
									.CLIENT_EXTENSIONS_CAPACITY_RAM
							],
							dataSizeUnits: 'GB',
							infoText:
								i18n.translate(
									'amount-of-ram-allocated-across-all-extension-environments'
								) +
								' ' +
								i18n.translate('this-data-is-refreshed-daily'),
							maxCountText: i18n.translate('total-ram'),
							title: i18n.translate('extension-capacity-ram'),
						},
						{
							...response[
								SiteAndUserDataEnum
									.CLIENT_EXTENSIONS_CAPACITY_CPU
							],
							infoText:
								i18n.translate(
									'amount-of-virtual-cpus-allocated-across-all-extension-environments'
								) +
								' ' +
								i18n.translate('this-data-is-refreshed-daily'),
							maxCountText: i18n.translate('total-vcpu'),
							title: i18n.translate('extension-capacity-vcpu'),
						},
						{
							...response[
								SiteAndUserDataEnum
									.STORAGE_CAPACITY_DOCUMENT_LIBRARY
							],
							dataSizeUnits: 'GB',
							infoText:
								i18n.translate(
									'amount-of-storage-space-available-for-your-projects'
								) +
								' ' +
								i18n.translate('this-data-is-refreshed-daily'),
							maxCountText: i18n.translate('total-storage'),
							title: i18n.translate('storage-capacity'),
						},
					],
					siteAndUsers: [
						{
							...response[SiteAndUserDataEnum.SITES],
							infoText:
								i18n.translate(
									'total-number-of-unique-liferay-dxp-sites-each-comprising-a-set-of-pages-and-their-related-content'
								) +
								' ' +
								i18n.translate(
									'this-data-is-refreshed-monthly'
								),
							title: i18n.translate('number-of-sites'),
						},
						{
							...response[
								SiteAndUserDataEnum
									.MONTHLY_ACTIVE_LOGGED_IN_USERS
							],
							infoText:
								i18n.translate(
									'total-unique-authenticated-users-who-visited-sites-on-this-account-at-least-once-per-month'
								) +
								' ' +
								i18n.translate('this-data-is-refreshed-daily'),
							title: i18n.translate('authenticated-logins-malus'),
						},
						{
							...response[
								SiteAndUserDataEnum.ANONYMOUS_PAGE_VIEWS
							],
							infoText:
								i18n.translate(
									'total-count-of-anonymous-page-views-on-all-customer-sites'
								) +
								' ' +
								i18n.translate('this-data-is-refreshed-daily'),
							title: i18n.translate('anonymous-page-views-apv'),
						},
					],
				};

				setUsageData(formatedData);
			}

			setIsLoading(false);
		}
	}, [
		displayUsage,
		project?.externalReferenceCode,
		setUsageData,
		subscriptionsData,
	]);

	useEffect(() => {
		getSiteAndUsers();
	}, [getSiteAndUsers]);

	return {addOns, displayUsage, isLoading, usageData};
};

export default useProjectUsageData;
