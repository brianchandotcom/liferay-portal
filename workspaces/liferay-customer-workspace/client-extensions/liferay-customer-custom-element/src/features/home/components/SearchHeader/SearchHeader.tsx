/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/utils/I18n';

import SearchBar from './components/SearchBar/SearchBar';

interface IProps {
	count?: number;
	handleSearch: (onSearchTerm: string) => void;
	total?: number;
}

const SearchHeader: React.FC<IProps> = ({count, handleSearch, total}) => {
	const getCounter = (): string | undefined => {
		if (count === undefined) {
			return undefined;
		}

		return `${count} ${
			count === total
				? i18n.pluralize(count, 'project')
				: i18n.pluralize(count, 'result')
		}`;
	};

	return (
		<div className="align-items-center d-flex justify-content-between mb-4 pb-2">
			<SearchBar handleSearch={handleSearch} />

			<h5 className="m-0 text-neutral-7">{getCounter()}</h5>
		</div>
	);
};

export default SearchHeader;
