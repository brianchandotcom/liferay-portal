/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useCallback, useContext, useEffect, useRef, useState} from 'react';
import {ListViewContext, ListViewTypes} from '~/context/ListViewContext';
import i18n from '~/i18n';

const ManagementToolbarSearch = () => {
	const [listViewContext, dispatch] = useContext(ListViewContext);
	const [search, setSearch] = useState(listViewContext.search || '');

	const inputRef = useRef<HTMLInputElement>(null);

	useEffect(() => {
		const timeout = setTimeout(() => {
			inputRef?.current?.focus();
		}, 100);

		return () => clearTimeout(timeout);
	}, [inputRef]);

	const onApply = useCallback(() => {
		dispatch({
			payload: search,
			type: ListViewTypes.SET_SEARCH,
		});
	}, [dispatch, search]);

	const onClear = useCallback(() => {
		setSearch('');
		dispatch({
			payload: null,
			type: ListViewTypes.SET_CLEAR,
		});
	}, [dispatch]);

	return (
		<ClayManagementToolbar>
			<ClayManagementToolbar.ItemList>
				<ClayManagementToolbar.Search
					onSubmit={(event: React.FormEvent<HTMLFormElement>) => {
						event.preventDefault();
						onApply();
					}}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={i18n.translate('search')}
								className="form-control input-group-inset input-group-inset-after"
								onChange={({target: {value}}) =>
									setSearch(value)
								}
								ref={inputRef}
								type="text"
								value={search}
							/>
							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									aria-label={i18n.translate('clear')}
									displayType="unstyled"
									onClick={onClear}
									symbol="times"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayManagementToolbar.Search>

				<ClayManagementToolbar.Item>
					<ClayButton onClick={onApply}>
						{i18n.translate('Search')}
					</ClayButton>
				</ClayManagementToolbar.Item>
			</ClayManagementToolbar.ItemList>
		</ClayManagementToolbar>
	);
};

export default ManagementToolbarSearch;
