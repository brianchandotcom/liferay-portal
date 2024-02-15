/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Dispatch} from 'react';

import i18n from '../../i18n';

type SearchProps = {
	search: string;
	setSearch: Dispatch<string>;
};

const Search: React.FC<SearchProps> = ({search = '', setSearch}) => {
	const isClear = !!search.trim()?.length;

	return (
		<ClayInput.Group className="mb-4">
			<ClayInput.GroupItem>
				<ClayInput
					className="border-brand-primary-lighten-5 font-weight-semi-bold text-neutral-10 text-paragraph-sm"
					insetAfter
					onChange={(event) => setSearch(event.target.value)}
					placeholder={i18n.translate('search' as any)}
					type="text"
					value={search}
				/>

				<ClayInput.GroupInsetItem
					after
					className="border-brand-primary-lighten-5"
					tag="span"
				>
					<ClayButton
						aria-labelledby="search"
						displayType="unstyled"
						onClick={() => setSearch('')}
					>
						<ClayIcon symbol={isClear ? 'times' : 'search'} />
					</ClayButton>
				</ClayInput.GroupInsetItem>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

export default Search;
