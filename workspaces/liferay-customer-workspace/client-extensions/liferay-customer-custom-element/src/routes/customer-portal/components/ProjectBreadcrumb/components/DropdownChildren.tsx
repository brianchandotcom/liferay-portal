/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {useEffect} from 'react';
import i18n from '~/common/I18n';
import useIntersectionObserver from '~/common/hooks/useIntersectionObserver';

import DropdownItems from './DropdownItems';
import Search from './Search';

const DropdownChildren = ({
	dropdownProjectsExceeded,
	fetching,
	initialTotalCount,
	koroneikiAccounts,
	koroneikiAccountsItems,
	onIntersecting,
	onSearch,
	searching,
	selectedKoroneikiAccount,
}: any) => {
	const [trackedRef, isIntersecting] = useIntersectionObserver();

	const isLastPage = koroneikiAccounts?.page === koroneikiAccounts?.lastPage;
	const allowFetching = !isLastPage && !fetching;

	useEffect(() => {
		if ((isIntersecting || searching) && allowFetching) {
			onIntersecting(koroneikiAccounts?.page);
		}
	}, [
		allowFetching,
		isIntersecting,
		koroneikiAccounts?.page,
		onIntersecting,
		searching,
	]);

	return (
		<>
			<div className="dropdown-section px-3">
				{dropdownProjectsExceeded && (
					<Search setSearchTerm={onSearch} />
				)}
			</div>

			{searching && !koroneikiAccountsItems.length && (
				<ClayDropDown.Section className="px-3">
					<div className="font-weight-semi-bold text-neutral-5 text-paragraph-sm">
						{i18n.translate('loading')}
					</div>
				</ClayDropDown.Section>
			)}

			{!searching &&
				!koroneikiAccountsItems?.length &&
				initialTotalCount > 1 && (
					<div className="dropdown-section px-3">
						<div className="font-weight-semi-bold text-neutral-5 text-paragraph-sm">
							{i18n.translate('no-projects-match-that-name')}
						</div>
					</div>
				)}

			{!!koroneikiAccountsItems?.length && initialTotalCount > 1 && (
				<ClayDropDown.ItemList>
					<DropdownItems
						koroneikiAccounts={koroneikiAccountsItems}
						selectedKoroneikiAccount={selectedKoroneikiAccount}
					/>

					{dropdownProjectsExceeded && !isLastPage && (
						<ClayDropDown.Section className="px-3">
							<div ref={trackedRef as any}>&nbsp;</div>
						</ClayDropDown.Section>
					)}
				</ClayDropDown.ItemList>
			)}
		</>
	);
};

export default DropdownChildren;
