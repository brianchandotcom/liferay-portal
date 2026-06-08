/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import SearchInput from '~/shared/components/SearchInput';

interface ICriteriaSidebarSearchBarProps {
	onChange: (value: string) => void;
	searchValue: string;
}

const CriteriaSidebarSearchBar: React.FC<ICriteriaSidebarSearchBarProps> = ({
	onChange,
	searchValue,
}) => (
	<div className="input-group">
		<div className="input-group-item">
			<SearchInput
				onChange={onChange}
				placeholder={Liferay.Language.get('search')}
				type="text"
				value={searchValue}
			/>
		</div>
	</div>
);

export default CriteriaSidebarSearchBar;
