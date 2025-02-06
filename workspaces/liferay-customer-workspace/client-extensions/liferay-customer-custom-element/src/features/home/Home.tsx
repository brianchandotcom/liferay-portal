/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';

import ProjectList from './components/ProjectsList';
import SearchHeader from './components/SearchHeader';

import './Home.css';

import React, {useMemo, useState} from 'react';
import useKoroneikiAccounts from '~/hooks/useKoroneikiAccounts';

import ProjectCategoryDropdown from './components/ProjectCategoryDropdown';
import useProjectCategoryItems from './hooks/useProjectCategoryItems';

const THRESHOLD_COUNT = 4;

const Home: React.FC = () => {
	const [selectedProjectCategoryKey, setSelectedProjectCategoryKey] =
		useState<string>('all-projects');

	const projectCategoryItems = useProjectCategoryItems();

	const {
		data,
		fetchMore,
		fetching,
		firstKoroneikiAccountsTotal,
		handleSearch,
		loading,
		searchTerm,
		searching,
	} = useKoroneikiAccounts({
		selectedFilterCategory: {
			filter:
				projectCategoryItems.find(
					({key}) => key === selectedProjectCategoryKey
				)?.filter || '',
			key: selectedProjectCategoryKey,
			label: '',
			pageSize: 20,
		},
	});

	const handleOnSelect = (key: string): void => {
		setSelectedProjectCategoryKey(key);
	};

	const {featureFlags} = useAppPropertiesContext();

	const koroneikiAccounts = data?.c?.koroneikiAccounts;
	const koroneikiAccountTotal = koroneikiAccounts?.totalCount;

	const koroneikiCount =
		firstKoroneikiAccountsTotal[selectedProjectCategoryKey];

	const hasManyProjects: boolean = koroneikiCount > THRESHOLD_COUNT;

	const hasAvailableCategoriesToDisplay: boolean = useMemo(
		() =>
			projectCategoryItems
				.filter((projectCategoryItem) =>
					['liferay-contact', 'fls-partner'].includes(
						projectCategoryItem.key
					)
				)
				.some(({disabled}) => !disabled),
		[projectCategoryItems]
	);

	return (
		<>
			{featureFlags?.includes('LPS-191380') &&
				hasAvailableCategoriesToDisplay && (
					<ProjectCategoryDropdown
						loading={loading || koroneikiCount === null}
						onSelect={handleOnSelect}
						projectCategoryItems={projectCategoryItems}
						selectedProjectCategoryKey={selectedProjectCategoryKey}
					/>
				)}

			<ClayLayout.ContainerFluid
				className="cp-home-wrapper"
				hidden={!hasManyProjects || loading}
				onPointerEnterCapture={() => {}}
				onPointerLeaveCapture={() => {}}
				placeholder=""
				size={hasManyProjects && !loading ? 'md' : 'xl'}
			>
				<ClayLayout.Row>
					<ClayLayout.Col>
						{hasManyProjects && !loading && (
							<SearchHeader
								count={koroneikiAccountTotal}
								handleSearch={handleSearch}
								loading={searching}
								searchTerm={searchTerm}
							/>
						)}

						<ProjectList
							compressed={hasManyProjects && !loading}
							fetching={fetching}
							koroneikiAccounts={koroneikiAccounts}
							loading={
								loading || searching || koroneikiCount === null
							}
							maxCardsLoading={THRESHOLD_COUNT}
							onIntersect={(currentPage: number) => {
								fetchMore({
									variables: {
										page: currentPage + 1,
									},
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
