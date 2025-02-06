/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import Skeleton from '~/components/Skeleton';
import i18n from '~/utils/I18n';

import SearchBar from './components/SearchBar/SearchBar';

interface IProps {
	count: number;
	handleSearch: (onSearchTerm: string) => void;
	loading: boolean;
	searchTerm: string;
}

const SearchHeader: React.FC<IProps> = ({
	count,
	handleSearch,
	loading,
	searchTerm,
}) => {
	const [hasOnSearchTerm, setHasOnSearchTerm] = useState(false);

	const getCounter = (): string => {
		return `${count} ${
			hasOnSearchTerm
				? i18n.pluralize(count, 'result')
				: i18n.pluralize(count, 'project')
		}`;
	};

	return (
		<div className="align-items-center d-flex justify-content-between mb-4 pb-2">
			<SearchBar
				handleSearch={(onSearchTerm) => {
					handleSearch(onSearchTerm);
					setHasOnSearchTerm(!!onSearchTerm);
				}}
				searchTerm={searchTerm}
			/>

			{loading ? (
				<Skeleton height={22} width={85} />
			) : (
				<h5 className="m-0 text-neutral-7">{getCounter()}</h5>
			)}
		</div>
	);
};

export default SearchHeader;
