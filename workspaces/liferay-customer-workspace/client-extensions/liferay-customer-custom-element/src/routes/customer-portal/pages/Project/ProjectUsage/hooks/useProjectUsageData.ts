/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useMemo, useState} from 'react';
import i18n from '~/common/I18n';
import {Liferay} from '~/common/services/liferay';
import {useGetAccountSubscriptions} from '~/common/services/liferay/graphql/account-subscriptions';
import {useCustomerPortal} from '~/routes/customer-portal/context';

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

const ACCEPTED_PROJECTS = ['Business Plan', 'Enterprise Plan', 'Pro Plan'];

export enum SiteAndUserDataEnum {
	ANONYMOUS_PAGE_VIEWS = 'anonymousPageViews',
	CLIENT_EXTENSIONS_CAPACITY_CPU = 'clientExtensionsCapacityCPU',
	CLIENT_EXTENSIONS_CAPACITY_RAM = 'clientExtensionsCapacityRAM',
	MONTHLY_ACTIVE_LOGGED_IN_USERS = 'monthlyActiveLoggedInUsers',
	SITES = 'sites',
	STORAGE_CAPACITY_DOCUMENT_LIBRARY = 'storageCapacityDocumentLibrary',
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

const formatedAcceptedProjects = () =>
	ACCEPTED_PROJECTS.map((projectName) => `'${projectName}'`).join(',');

const useProjectUsageData = () => {
	const [usageData, setUsageData] = useState<IUsageData>(
		DEFAULT_USAGE_DATA_VALUES
	);
	const [isLoading, setIsLoading] = useState(true);

	const [{project}] = useCustomerPortal();

	const {data} = useGetAccountSubscriptions({
		filter: `name in (${formatedAcceptedProjects()}) and accountSubscriptionGroupERC eq '${
			project?.accountKey
		}_liferay-saas'`,
	});

	const displayUsage = useMemo(
		() => !!data?.c?.accountSubscriptions?.items.length,
		[data]
	);

	const getSiteAndUsers = useCallback(async () => {
		if (project?.id) {
			if (!displayUsage) {
				setIsLoading(false);

				return;
			}

			const response =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch(`/accounts/${project?.id}/usage`)
					.then((response) => response.json())
					.catch(console.error);

			if (!response) {
				setIsLoading(false);

				return;
			}

			const formatedData = {
				resourceUsage: [
					{
						...response[
							SiteAndUserDataEnum.CLIENT_EXTENSIONS_CAPACITY_RAM
						],
						dataSizeUnits: 'GB',
						infoText: i18n.translate('extension-capacity-ram'),
						maxCountText: 'RAM',
						title: i18n.translate('extension-capacity-ram'),
					},
					{
						...response[
							SiteAndUserDataEnum.CLIENT_EXTENSIONS_CAPACITY_CPU
						],
						infoText: i18n.translate('extension-capacity-vcpu'),
						maxCountText: 'vCPU',
						title: i18n.translate('extension-capacity-vcpu'),
					},
					{
						...response[
							SiteAndUserDataEnum
								.STORAGE_CAPACITY_DOCUMENT_LIBRARY
						],
						dataSizeUnits: 'GB',
						infoText: i18n.translate('storage-capacity'),
						maxCountText: 'Storage',
						title: i18n.translate('storage-capacity'),
					},
				],
				siteAndUsers: [
					{
						...response[SiteAndUserDataEnum.SITES],
						infoText: i18n.translate('number-of-sites'),
						title: i18n.translate('number-of-sites'),
					},
					{
						...response[
							SiteAndUserDataEnum.MONTHLY_ACTIVE_LOGGED_IN_USERS
						],
						infoText: i18n.translate('authenticated-logins-malus'),
						title: i18n.translate('authenticated-logins-malus'),
					},
					{
						...response[SiteAndUserDataEnum.ANONYMOUS_PAGE_VIEWS],
						infoText: i18n.translate('anonymous-page-views-apv'),
						title: i18n.translate('anonymous-page-views-apv'),
					},
				],
			};

			setUsageData(formatedData);

			setIsLoading(false);
		}
	}, [displayUsage, project?.id, setUsageData]);

	useEffect(() => {
		getSiteAndUsers();
	}, [getSiteAndUsers]);

	return {displayUsage, isLoading, usageData};
};

export default useProjectUsageData;
