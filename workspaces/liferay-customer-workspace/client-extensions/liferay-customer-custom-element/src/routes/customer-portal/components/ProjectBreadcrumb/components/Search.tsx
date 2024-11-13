/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {Dispatch, useEffect, useState} from 'react';
import i18n from '~/common/I18n';
import useDebounce from '~/common/hooks/useDebounce';

const DELAY_TYPING_TIME = 500;

type SearchProps = {
	setSearchTerm: Dispatch<string>;
};

const Search: React.FC<SearchProps> = ({setSearchTerm}) => {
	const [value, setValue] = useState('');
	const debouncedValue = useDebounce(value, DELAY_TYPING_TIME);

	const [isClear, setIsClear] = useState(false);

	useEffect(() => setIsClear(!!value), [value]);

	useEffect(
		() => setSearchTerm(debouncedValue),
		[debouncedValue, setSearchTerm]
	);

	return (
		<ClayInput.Group className="m-0" small>
			<ClayInput.GroupItem>
				<ClayInput
					className="border-brand-primary-lighten-5 font-weight-semi-bold text-neutral-10 text-paragraph-sm"
					insetAfter
					onChange={(event) => setValue(event.target.value)}
					placeholder={i18n.translate('search')}
					type="text"
					value={value}
				/>

				<ClayInput.GroupInsetItem
					after
					className="border-brand-primary-lighten-5"
					tag="span"
				>
					<Button
						aria-label={i18n.translate('search')}
						displayType="unstyled"
						onClick={() =>
							setValue((previousValue) =>
								isClear ? '' : previousValue
							)
						}
					>
						<ClayIcon symbol={isClear ? 'times' : 'search'} />
					</Button>
				</ClayInput.GroupInsetItem>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

export default Search;
