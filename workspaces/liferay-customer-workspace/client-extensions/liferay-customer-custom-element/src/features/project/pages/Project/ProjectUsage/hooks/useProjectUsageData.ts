/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as OAuth2 from '@liferay/oauth2-provider-web/client';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {useAppContext} from '~/features/project/context';
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

export enum ResourceUsageDataEnum {
	CLIENT_EXTENSIONS_CPU = 'clientExtensionsCPU',
	CLIENT_EXTENSIONS_RAM = 'clientExtensionsRAM',
	DATABASE_STORAGE = 'databaseStorage',
	DOCUMENT_LIBRARY_AND_BACKUP_STORAGE = 'documentLibraryAndBackupStorage',
	LOG_STORAGE = 'logStorage',
	NETWORK_TRAFFIC = 'networkTraffic',
}

enum ADD_ON_NAMES {
	DEDICATED_RESOURCES = 'Dedicated Resources',
	PRIVATE_CLUSTER = 'Private Cluster',
}

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

const ADD_ONS = ['Dedicated Resources', 'Private Cluster'];

const formatedSubscriptions = (acceptedSubscriptions: string[]) =>
	[...acceptedSubscriptions, ...ADD_ONS]
		.map((projectName) => `'${projectName}'`)
		.join(',');

const useProjectUsageData = (
	acceptedSubscriptions: string[],
	hasExperienceSubscription: boolean
) => {
	const [response, setResponse] = useState<any>(undefined);
	const [isLoading, setIsLoading] = useState(true);

	const [{project}] = useAppContext();

	const {data: subscriptionsData} = useGetAccountSubscriptions({
		filter: `name in (${formatedSubscriptions(
			acceptedSubscriptions
		)}) and accountSubscriptionGroupERC eq '${
			project?.accountKey
		}_liferay-cloud'`,
	});

	const displayUsage = useMemo(() => {
		if (hasExperienceSubscription) {
			return true;
		}

		const subscriptions = subscriptionsData?.c?.accountSubscriptions?.items;

		if (!subscriptions) {
			return false;
		}

		return subscriptions.some(
			({name}: {name: string}) =>
				name && acceptedSubscriptions.includes(name.trim())
		);
	}, [acceptedSubscriptions, hasExperienceSubscription, subscriptionsData]);

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

			const oauth2Client = await OAuth2.FromUserAgentApplication(
				'liferay-customer-etc-spring-boot-oaua'
			);

			const dataResponse = await oauth2Client
				.fetch(`/accounts/${project?.externalReferenceCode}/usage`)
				.then((response: {json: () => any}) => response.json())
				.catch(console.error);

			if (dataResponse) {
				setResponse(dataResponse);
			}

			setIsLoading(false);
		}
	}, [displayUsage, project?.externalReferenceCode, subscriptionsData]);

	const usageData = useMemo<IUsageData>(() => {
		const resourceUsage = hasExperienceSubscription
			? [
					{
						...response?.[
							ResourceUsageDataEnum.CLIENT_EXTENSIONS_RAM
						],
						dataSizeUnits: 'GB',
						infoText: i18n.translate(
							'additional-ram-in-gb-allocated-to-customer-s-extension-environments-with-the-liferay-cloud-infrastructure-to-run-client-extensions'
						),
						maxCountText: i18n.translate('total-ram'),
						title: i18n.translate('extensions-ram'),
					},
					{
						...response?.[
							ResourceUsageDataEnum.CLIENT_EXTENSIONS_CPU
						],
						infoText: i18n.translate(
							'additional-vcpus-allocated-to-customer-s-extension-environments-within-the-liferay-cloud-infrastructure-to-run-client-extensions'
						),
						maxCountText: i18n.translate('total-vcpu'),
						title: i18n.translate('extensions-vcpu'),
					},
					{
						...response?.[
							ResourceUsageDataEnum
								.DOCUMENT_LIBRARY_AND_BACKUP_STORAGE
						],
						dataSizeUnits: 'GB',
						infoText: i18n.translate(
							'the-amount-of-data-in-gbs-stored-in-the-backup-service-and-document-library-service-provided-through-liferay-cloud-infrastructure'
						),
						maxCountText: i18n.translate('total-storage'),
						title: i18n.translate('storage'),
					},
					{
						...response?.[ResourceUsageDataEnum.DATABASE_STORAGE],
						dataSizeUnits: 'GB',
						infoText: i18n.translate(
							'the-amount-of-data-in-gibs-used-by-the-sql-database-instance-provisioned-as-part-of-liferay-cloud-infrastructure-including-the-database-data-itself-and-any-other-storage-needed-in-an-high-availability-scenario'
						),
						maxCountText: i18n.translate('total-storage'),
						title: i18n.translate('database'),
					},
					{
						...response?.[ResourceUsageDataEnum.NETWORK_TRAFFIC],
						dataSizeUnits: 'GB',
						infoText: i18n.translate(
							'amount-of-data-transfered-out-of-the-customer-application-s-environment-by-load-balancer-response-to-end-users-external-integrations-and-services-in-different-zones-of-the-same-region'
						),
						maxCountText: i18n.translate(
							'monthly-inbound-and-outbound'
						),
						title: i18n.translate('traffic-networking'),
					},
					{
						...response?.[ResourceUsageDataEnum.LOG_STORAGE],
						dataSizeUnits: 'GB',
						infoText: i18n.translate(
							'volume-of-logs-in-gib-pertaining-to-the-customer-application-ingested-by-liferay-s-cloud-infrastructure-this-can-include-logs-from-the-default-services-custom-services-or-liferay-s-cloud-platform-itself'
						),
						maxCountText: i18n.translate('total-volume'),
						title: i18n.translate('logs'),
					},
				]
			: [
					{
						...response?.[
							SiteAndUserDataEnum.CLIENT_EXTENSIONS_CAPACITY_RAM
						],
						dataSizeUnits: 'GB',
						infoText: i18n.translate(
							'additional-ram-in-gb-allocated-to-customer-s-extension-environments-with-the-liferay-cloud-infrastructure-to-run-client-extensions'
						),
						maxCountText: i18n.translate('total-ram'),
						title: i18n.translate('extensions-ram'),
					},
					{
						...response?.[
							SiteAndUserDataEnum.CLIENT_EXTENSIONS_CAPACITY_CPU
						],
						infoText: i18n.translate(
							'additional-vcpus-allocated-to-customer-s-extension-environments-within-the-liferay-cloud-infrastructure-to-run-client-extensions'
						),
						maxCountText: i18n.translate('total-vcpu'),
						title: i18n.translate('extensions-vcpu'),
					},
					{
						...response?.[
							SiteAndUserDataEnum
								.STORAGE_CAPACITY_DOCUMENT_LIBRARY
						],
						dataSizeUnits: 'GB',
						infoText: i18n.translate(
							'the-amount-of-data-in-gbs-stored-in-the-backup-service-and-document-library-service-provided-through-liferay-cloud-infrastructure'
						),
						maxCountText: i18n.translate('total-storage'),
						title: i18n.translate('storage'),
					},
				];

		const siteAndUsers = [
			{
				...response?.[SiteAndUserDataEnum.SITES],
				infoText:
					i18n.translate(
						'total-number-of-unique-liferay-dxp-sites-each-comprising-a-set-of-pages-and-their-related-content'
					) +
					' ' +
					i18n.translate('this-data-is-refreshed-monthly'),
				title: i18n.translate('number-of-sites'),
			},
			{
				...response?.[
					SiteAndUserDataEnum.MONTHLY_ACTIVE_LOGGED_IN_USERS
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
				...response?.[SiteAndUserDataEnum.ANONYMOUS_PAGE_VIEWS],
				infoText:
					i18n.translate(
						'total-count-of-anonymous-page-views-on-all-customer-sites'
					) +
					' ' +
					i18n.translate('this-data-is-refreshed-daily'),
				title: i18n.translate('anonymous-page-views-apv'),
			},
		];

		return {resourceUsage, siteAndUsers};
	}, [hasExperienceSubscription, response]);

	useEffect(() => {
		getSiteAndUsers();
	}, [getSiteAndUsers]);

	return {addOns, displayUsage, isLoading, usageData};
};

export default useProjectUsageData;
