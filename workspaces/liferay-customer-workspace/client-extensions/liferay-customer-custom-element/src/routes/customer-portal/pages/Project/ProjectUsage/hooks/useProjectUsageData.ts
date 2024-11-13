/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import i18n from '~/common/I18n';
import {Liferay} from '~/common/services/liferay';
import {useCustomerPortal} from '~/routes/customer-portal/context';

export enum SiteAndUserDataEnum {
	APVS = 'apvs',
	MALUS = 'malus',
	SITES = 'sites',
}

interface ChartData {
	infoText: string;
	maxCount: number;
	title: string;
	usedCount: number;
}

const useProjectUsageData = () => {
	const [siteAndUsersData, setSiteAndUsersData] = useState<ChartData[]>([]);

	const [{project}] = useCustomerPortal();

	const getSiteAndUsers = useCallback(async () => {
		if (project?.id) {
			const response =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				)
					.fetch(`/accounts/${project?.id}/usage`)
					.then((response) => response.json());

			const formatedData = [
				{
					...response[SiteAndUserDataEnum.SITES],
					infoText: i18n.translate(
						'number-of-unique-liferay-dxp-sites-which-comprises-a-set-of-pages-and-their-related-content'
					),
					title: i18n.translate('number-of-sites'),
				},
				{
					...response[SiteAndUserDataEnum.MALUS],
					infoText: i18n.translate(
						'total-unique-authenticated-users-who-visited-sites-on-this-account-at-least-once-per-month'
					),
					title: i18n.translate('authenticated-logins-malus'),
				},
				{
					...response[SiteAndUserDataEnum.APVS],
					infoText: i18n.translate(
						'total-count-of-anonymous-page-views-on-all-customer-sites'
					),
					title: i18n.translate('anonymous-page-views-apv'),
				},
			];

			setSiteAndUsersData(formatedData);
		}
	}, [project?.id, setSiteAndUsersData]);

	useEffect(() => {
		getSiteAndUsers();
	}, [getSiteAndUsers]);

	return {siteAndUsersData};
};

export default useProjectUsageData;
