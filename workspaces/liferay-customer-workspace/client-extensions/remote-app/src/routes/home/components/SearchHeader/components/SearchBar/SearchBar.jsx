/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {memo, useEffect, useState} from 'react';
import i18n from '../../../../../../common/I18n';
import useDebounce from '../../../../../../common/hooks/useDebounce';

const SearchBar = ({onSearchSubmit, search}) => {
	const [term, setTerm] = useState('');
	const debouncedTerm = useDebounce(term, 500);

	useEffect(() => {
		if (!search) {
			setTerm(search);
		}
	}, [search]);

	useEffect(() => onSearchSubmit(debouncedTerm), [
		debouncedTerm,
		onSearchSubmit,
	]);

	return (
		<div className="flex-grow-1 mr-3 position-relative">
			<ClayInput
				className="border border-brand-primary-lighten-4 cp-search-bar font-weight-semi-bold px-5 py-3 rounded-pill shadow-lg"
				onChange={(event) => setTerm(event.target.value)}
				placeholder={i18n.translate('find-a-project')}
				type="text"
				value={term}
			/>

			<ClayIcon
				className="cp-search-icon position-absolute text-brand-primary"
				symbol="search"
			/>
		</div>
	);
};

export default memo(SearchBar);
