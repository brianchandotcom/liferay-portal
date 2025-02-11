/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useCallback, useEffect, useMemo, useState} from 'react';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';

import ProjectList from './components/ProjectsList';
import SearchHeader from './components/SearchHeader';

import './Home.css';

import {NetworkStatus} from '@apollo/client';
import useKoroneikiAccounts from '~/hooks/useKoroneikiAccounts';
import SearchBuilder from '~/lib/SearchBuilder';

import ProjectCategoryDropdown from './components/ProjectCategoryDropdown';
import useProjectCategoryItems from './hooks/useProjectCategoryItems';

const THRESHOLD_COUNT = 4;

const Home: React.FC = () => {
	const [accountTotal, setAccountTotal] = useState<number | null>(null);
	const [filter, setFilter] = useState('');
	const [selectedProjectCategoryKey, setSelectedProjectCategoryKey] =
		useState<string>('all-projects');

	const projectCategoryItems = useProjectCategoryItems();

	const getFilter = useCallback(
		(searchTerm: string) => {
			const categoryItem = projectCategoryItems.find(
				({key}) => key === selectedProjectCategoryKey
			);
			let searchBuilder = new SearchBuilder();

			if (
				categoryItem?.filter &&
				typeof categoryItem.filter === 'function'
			) {
				searchBuilder = categoryItem.filter(searchBuilder);
			}

			if (searchTerm) {
				searchBuilder.contains('name', searchTerm);
				searchBuilder.or();
				searchBuilder.contains('code', searchTerm);
			}

			return searchBuilder.build();
		},
		[projectCategoryItems, selectedProjectCategoryKey]
	);

	const {data, fetchMore, networkStatus, refetch} = useKoroneikiAccounts({
		filter,
	});

	useEffect(() => {
		refetch({filter});

		if (accountTotal === null && data?.c?.koroneikiAccounts?.totalCount) {
			setAccountTotal(data.c.koroneikiAccounts.totalCount);
		}
	}, [data?.c?.koroneikiAccounts?.totalCount, filter, accountTotal, refetch]);

	const handleOnSelect = useCallback((key: string): void => {
		setSelectedProjectCategoryKey(key);
	}, []);

	const handleSearch = useCallback(
		(searchTerm: string) => {
			setFilter(getFilter(searchTerm));
		},
		[getFilter]
	);

	const {featureFlags} = useAppPropertiesContext();
	const koroneikiAccounts = data?.c?.koroneikiAccounts;
	const koroneikiAccountTotal = koroneikiAccounts?.totalCount;

	const hasManyProjects: boolean = accountTotal
		? accountTotal > THRESHOLD_COUNT
		: false;
	const hasAvailableCategoriesToDisplay = useMemo(
		() =>
			projectCategoryItems.some(
				({disabled, key}) =>
					['liferay-contact', 'fls-partner'].includes(key) &&
					!disabled
			),
		[projectCategoryItems]
	);

	return (
		<>
			{featureFlags?.includes('LPS-191380') &&
				hasAvailableCategoriesToDisplay && (
					<ProjectCategoryDropdown
						loading={networkStatus === NetworkStatus.loading}
						onSelect={handleOnSelect}
						projectCategoryItems={projectCategoryItems}
						selectedProjectCategoryKey={selectedProjectCategoryKey}
					/>
				)}

			<ClayLayout.ContainerFluid
				className="cp-home-wrapper"
				onPointerEnterCapture={() => {}}
				onPointerLeaveCapture={() => {}}
				placeholder=""
				size={
					hasManyProjects && networkStatus !== NetworkStatus.loading
						? 'md'
						: 'xl'
				}
			>
				<ClayLayout.Row>
					<ClayLayout.Col>
						{hasManyProjects && (
							<SearchHeader
								count={koroneikiAccountTotal}
								handleSearch={handleSearch}
							/>
						)}

						<ProjectList
							compressed={
								hasManyProjects &&
								networkStatus !== NetworkStatus.loading
							}
							fetching={networkStatus === NetworkStatus.fetchMore}
							koroneikiAccounts={koroneikiAccounts}
							loading={networkStatus === NetworkStatus.loading}
							maxCardsLoading={THRESHOLD_COUNT}
							onIntersect={(currentPage: number) => {
								fetchMore({
									variables: {page: currentPage + 1},
								});
							}}
						/>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</>
	);
};

export default Home;
