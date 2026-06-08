/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';
import {useParams} from 'react-router-dom';
import LifecycleChart from '~/lifecycle/components/LifecycleChart';
import * as API from '~/shared/api';
import AccountsDataSet from '~/shared/components/AccountsDataSet';
import Loading from '~/shared/components/Loading';
import {SectionHeader} from '~/shared/components/SectionHeader';
import BasePage from '~/shared/components/base-page';
import {ChannelContext} from '~/shared/context/channel';
import {useRequest} from '~/shared/hooks/useRequest';
import * as breadcrumbs from '~/shared/util/breadcrumbs';

import GlobalFilters from '../components/GlobalFilters';
import OverviewSection from '../components/OverviewSection';
import {
	LifecycleContextProvider,
	useLifecycle,
} from '../context/LifecycleContext';

const LifecycleOverview = () => {
	const {filters, lifecycleId} = useLifecycle();

	const {groupId} = useParams();

	const {data: overviewData, loading: overviewLoading} = useRequest({
		dataSourceFn: API.lifecycle.fetchOverviewMetrics,
		variables: {
			country: filters.countryFilter,
			groupId: groupId!,
			industry: filters.industryFilter,
			lifecycleId,
		},
	});

	return <OverviewSection loading={overviewLoading} metrics={overviewData} />;
};

const LifecycleStagesSection = () => {
	const {filters, lifecycleId} = useLifecycle();

	const {groupId} = useParams();

	const {
		data: stagesData,
		error: stagesError,
		loading: stagesLoading,
	} = useRequest({
		dataSourceFn: API.lifecycle.fetchLifecycleStages,
		variables: {
			country: filters.countryFilter,
			groupId,
			industry: filters.industryFilter,
			lifecycleId,
		},
	});

	return (
		<LifecycleChart
			error={!!stagesError}
			loading={stagesLoading}
			stages={stagesData}
		/>
	);
};

const LifecycleAccounts = () => {
	const {filters, lifecycleId} = useLifecycle();

	const {channelId, groupId} = useParams();

	return (
		<section>
			<SectionHeader
				icon="box-container"
				title={Liferay.Language.get('accounts')}
			/>

			<AccountsDataSet
				accountLifecycleId={lifecycleId}
				apiURL={`/o/faro/contacts/${groupId!}/account-lifecycle/${lifecycleId}/accounts`}
				channelId={channelId!}
				countryFilter={filters.countryFilter}
				groupId={groupId!}
				industryFilter={filters.industryFilter}
				lifecycleStageFilter={filters.lifecycleStageFilter}
			/>
		</section>
	);
};

const BaseLifecycle = () => {
	const {selectedChannel} = useContext(ChannelContext);

	const {channelId, groupId} = useParams();

	const {data: lifecycles, loading: lifecyclesLoading} = useRequest({
		dataSourceFn: API.lifecycle.fetchLifecycles,
		variables: {groupId: groupId!},
	});

	const lifecycleId = lifecycles?.[0]?.id ?? '1';

	return (
		<LifecycleContextProvider lifecycleId={lifecycleId}>
			<BasePage documentTitle={Liferay.Language.get('lifecycles')}>
				<BasePage.Header
					breadcrumbs={[
						breadcrumbs.getHome({
							channelId: channelId!,
							groupId: groupId!,
							label: selectedChannel?.name,
						}),
					]}
					groupId={groupId!}
				>
					<BasePage.Row>
						<BasePage.Header.TitleSection
							className="mb-3"
							title={Liferay.Language.get('lifecycles')}
						/>
					</BasePage.Row>
				</BasePage.Header>
				<BasePage.SubHeader>
					<div className="d-flex justify-content-between w-100">
						<GlobalFilters />
					</div>
				</BasePage.SubHeader>
				<BasePage.Body>
					{lifecyclesLoading || !lifecycleId ? (
						<Loading />
					) : (
						<>
							<LifecycleOverview />

							<LifecycleStagesSection />

							<LifecycleAccounts />
						</>
					)}
				</BasePage.Body>
			</BasePage>
		</LifecycleContextProvider>
	);
};

export default BaseLifecycle;
