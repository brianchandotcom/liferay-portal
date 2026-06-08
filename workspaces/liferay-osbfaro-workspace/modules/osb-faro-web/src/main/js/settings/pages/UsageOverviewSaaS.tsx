/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {GenericBarsCard} from '~/settings/components/usage-overview/GenericBarsCard';
import {GenericDonutChart} from '~/settings/components/usage-overview/GenericDonutChart';
import {compose, withProject} from '~/shared/hoc';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';

export type Resource = {
	capacity: string;
	measurement: string;
};

export const UsageOverviewSaaS = function UsageOverviewSaaS() {
	const currentUser = useCurrentUser();

	let pageActions: {
		displayType: string;
		href: string;
		icon: {symbol: string};
		label: string;
		target: string;
	}[] = [];

	if (currentUser.isAdmin()) {
		pageActions = [
			{
				displayType: 'primary',
				href: 'https://support.liferay.com/',
				icon: {
					symbol: 'shortcut',
				},
				label: Liferay.Language.get('go-to-customer-portal'),
				target: '_blank',
			},
		];
	}

	const cardTitles = [
		Liferay.Language.get('number-of-sites'),
		Liferay.Language.get('authenticated-logins-malus'),
		Liferay.Language.get('anonymous-page-views-apv'),
	];

	const resources: Array<Resource> = [
		{
			capacity: Liferay.Language.get('extension-capacity'),
			measurement: 'RAM',
		},
		{
			capacity: Liferay.Language.get('extension-capacity'),
			measurement: 'vCPU',
		},
		{
			capacity: Liferay.Language.get('storage-capacity'),
			measurement: '',
		},
	];

	return (
		<BasePage
			key="UsageOverview"
			pageActions={pageActions}
			pageDescription={Liferay.Language.get(
				'saas-plan-usage-is-determined-by-malus-and-apvs'
			)}
			pageTitle={Liferay.Language.get('subscription-&-usage')}
		>
			<div className="md:p-10 p-5 saas-banner sm:p-8 xl:p-4">
				<div className="text-white">
					<h2 className="title">
						{Liferay.Language.get('view-your-saas-project-metrics')}
					</h2>
					<p className="d-flex mb-0 w-50">
						{Liferay.Language.get('as-a-saas-customer-description')}
					</p>
				</div>
			</div>
			<div className="gradient-opaque mt-5">
				<div className="mt-2">
					<div className="w-100">
						<h2 className="title">
							{Liferay.Language.get('sites-and-users')}
						</h2>
					</div>
					<ClayLayout.Row>
						{cardTitles.map((title) => (
							<GenericBarsCard cardTitle={title} key={title} />
						))}
					</ClayLayout.Row>
				</div>
				<div className="mt-2">
					<div className="w-100">
						<h2 className="title">
							{Liferay.Language.get('resource-usage')}
						</h2>
					</div>
					<ClayLayout.Row>
						{resources.map(({capacity, measurement}) => (
							<GenericDonutChart
								capacity={capacity}
								key={`${measurement}-${measurement}`}
								measurement={measurement}
							/>
						))}
					</ClayLayout.Row>
				</div>
			</div>
		</BasePage>
	);
};

export default compose(withProject)(UsageOverviewSaaS);
