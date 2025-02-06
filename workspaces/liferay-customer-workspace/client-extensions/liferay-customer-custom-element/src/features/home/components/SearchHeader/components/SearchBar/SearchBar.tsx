/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {memo, useEffect, useState} from 'react';
import useDebounce from '~/hooks/useDebounce';
import i18n from '~/utils/I18n';

import './SearchBar.css';

interface IProps {
	handleSearch: (onSearchTerm: string) => void;
	searchTerm: string;
}

const SearchBar: React.FC<IProps> = ({handleSearch, searchTerm}) => {
	const [onSearchTerm, setOnSearchTerm] = useState<string>('');
	const debouncedTerm = useDebounce(onSearchTerm, 500);

	useEffect(() => {
		if (!searchTerm) {
			setOnSearchTerm(searchTerm);
		}
	}, [searchTerm]);

	useEffect(() => handleSearch(debouncedTerm), [debouncedTerm, handleSearch]);

	const handleChange: React.ChangeEventHandler<HTMLInputElement> = (
		event
	) => {
		setOnSearchTerm(event.target.value);
	};

	return (
		<div className="flex-grow-1 mr-3 position-relative">
			<ClayInput
				className="border border-brand-primary-lighten-4 cp-search-bar font-weight-semi-bold px-5 py-3 rounded-pill shadow-lg"
				onChange={handleChange}
				placeholder={i18n.translate('find-a-project')}
				type="text"
				value={onSearchTerm}
			/>

			<ClayIcon
				className="cp-search-icon position-absolute text-brand-primary"
				symbol="search"
			/>
		</div>
	);
};

export default memo(SearchBar);
