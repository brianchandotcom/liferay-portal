/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/settings/components/base-page/BasePage';
import {getDefinitions} from '~/shared/util/breadcrumbs';

import SearchCard from './SearchCard';

interface ISearchProps {
	groupId: string;
}

export const Search = function Search({groupId}: ISearchProps) {
	return (
		<BasePage
			breadcrumbItems={[
				getDefinitions({groupId}),
				{active: true, label: Liferay.Language.get('search')},
			]}
			pageDescription={Liferay.Language.get(
				'collect-your-propertys-search-data-by-defining-search-query-parameters'
			)}
			pageTitle={Liferay.Language.get('search')}
		>
			<SearchCard groupId={groupId} />
		</BasePage>
	);
};

export default Search;
