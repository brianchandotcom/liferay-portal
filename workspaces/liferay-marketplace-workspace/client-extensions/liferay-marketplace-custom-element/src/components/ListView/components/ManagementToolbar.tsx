/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import DropDown, {ClayDropDownWithItems} from '@clayui/drop-down';
import {Item} from '@clayui/drop-down/lib/Items';
import {ClayInput} from '@clayui/form';
import Icon from '@clayui/icon';
import {ClayResultsBar} from '@clayui/management-toolbar';
import ManagementToolbar from '@clayui/management-toolbar/lib/ManagementToolbar';
import {useContext, useState} from 'react';

import i18n from '../../../i18n';
import {
	AppActions,
	ListViewContext,
	ListViewTypes,
} from '../hooks/ListViewContext';

type Items = {
	children: ModifiedItem[];
	id: number;
	name: string;
}

export type ModifiedItem = Omit<Item, 'onClick'> & {
	onClick: ((param: React.Dispatch<AppActions>) => void) | (() => void);
};

export type ManagementToolbarProps = {
	filterItems?: Items[];
	results?: number;
};

export function ListViewManagementToolbar({
	filterItems,
	results,
}: ManagementToolbarProps) {
	const [{filters, keywords}, dispatch] = useContext(ListViewContext);
	const [searchInput, setSearchInput] = useState(keywords);

	const [filterKey] = Object.keys(filters.filter);
	const filter = filters.filter[filterKey];

	const clearSearch = () => {
		dispatch({
			payload: null,
			type: ListViewTypes.SET_CLEAR,
		});

		setSearchInput('');
	};

	return (
		<ManagementToolbar>
			{filterItems && (
				<DropDown
				trigger={
					<Button className="nav-link" displayType="unstyled">
						<span className="mr-3">
							<Icon symbol="filter" />
						</span>
						<span className="navbar-text-truncate">
							{i18n.translate('filter')}
						</span>
					</Button>
				}
				>
					<DropDown.ItemList>
						{filterItems.map((items) => {
							return (
								<DropDown.Group
								header={items.name}
								items={items.children}
								key={items.name}
							>
								{(item) => (
									<DropDown.Item
										key={item.name}
										onClick={() => {
											item.onClick?.(dispatch);
										}}
									>
										{item.name}
									</DropDown.Item>
								)}
							</DropDown.Group>
							)
						})}
					</DropDown.ItemList>
				</DropDown>
			)}
			<ManagementToolbar.Search
				onSubmit={(event) => {
					event.preventDefault();

					dispatch({
						payload: searchInput,
						type: ListViewTypes.SET_SEARCH,
					});
				}}
			>
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							aria-label="Search"
							className="form-control input-group-inset input-group-inset-after"
							onChange={(event) =>
								setSearchInput(event.target.value)
							}
							placeholder="Search"
							type="text"
							value={searchInput}
						/>

						<ClayInput.GroupInsetItem after tag="span">
							<ClayButtonWithIcon
								aria-label="Search"
								displayType="unstyled"
								onClick={() =>
									dispatch({
										payload: searchInput,
										type: ListViewTypes.SET_SEARCH,
									})
								}
								symbol="search"
							/>

							{(keywords || filter) && (
								<ClayButtonWithIcon
									aria-label="Clear"
									displayType="unstyled"
									onClick={clearSearch}
									symbol="times"
								/>
							)}
						</ClayInput.GroupInsetItem>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ManagementToolbar.Search>

			{(filter || keywords) && (
				<div className="d-block w-100">
					<ClayResultsBar>
						<ClayResultsBar.Item>
							<span className="component-text text-truncate-inline">
								<span className="text-truncate">
									{i18n.sub('x-results-for', String(results))}

									<strong className="m-1">
										{keywords || filter}
									</strong>

									{keywords && filter && (
										<span>
											{i18n.translate('and')}
											<strong className="ml-1">
												{filter}
											</strong>
										</span>
									)}
								</span>
							</span>
						</ClayResultsBar.Item>

						<ClayResultsBar.Item className="ml-auto">
							<Button
								className="component-link tbar-link"
								displayType="unstyled"
								onClick={clearSearch}
							>
								{i18n.translate('clear')}
							</Button>
						</ClayResultsBar.Item>
					</ClayResultsBar>
				</div>
			)}
		</ManagementToolbar>
	);
}
