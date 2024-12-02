/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import i18n from '~/common/I18n';

import './SVSearch.css';

interface IProps {
	keywords?: string;
	onChange: (keywords: string) => void;
}

const SVSearch = ({keywords, onChange}: IProps) => {
	const [localKeywords, setLocalKeywords] = useState(keywords || '');
	const [debounceTimeout, setDebounceTimeout] = useState<NodeJS.Timeout>();

	const handleSearchChange = (newKeywords: string) => {
		setLocalKeywords(newKeywords);

		if (debounceTimeout) {
			clearTimeout(debounceTimeout);
		}

		setDebounceTimeout(
			setTimeout(() => {
				onChange(newKeywords);
			}, 500)
		);
	};

	return (
		<div className="flex-grow-1 position-relative sv-search">
			<ClayInput
				className="border border-brand-primary-lighten-4 font-weight-semi-bold px-5 py-3 rounded-pill shadow-lg sv-search-input"
				onChange={(event) => handleSearchChange(event.target.value)}
				placeholder={i18n.translate(
					'search-for-security-vulnerability-by-keyword-or-cve-id'
				)}
				type="text"
				value={localKeywords}
			/>

			<ClayIcon
				className="position-absolute sv-search-icon text-brand-primary"
				symbol="search"
			/>
		</div>
	);
};

export default SVSearch;
