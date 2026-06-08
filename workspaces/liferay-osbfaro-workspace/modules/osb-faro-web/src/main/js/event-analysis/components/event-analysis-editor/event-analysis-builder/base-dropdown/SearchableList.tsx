/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropdown from '@clayui/drop-down';
import React from 'react';
import {
	Attribute,
	Breakdown,
	Event,
	Filter,
} from '~/event-analysis/utils/types';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';

import ListItem from './ListItem';

interface ISearchableListProps {
	activeId?: string;
	disabledIds?: string[];
	items: (Attribute | Event)[];
	onEditClick: (item?: Attribute | Event) => void;
	onItemClick: (
		item: Attribute | Event,
		breakdown?: Breakdown,
		filter?: Filter
	) => void;
	onItemOptionsClick?: (item: Attribute) => void;
	onQueryChange: (query: string) => void;
	query: string;
	showInfoCard?: boolean;
	showOptionsCondition?: (item: Attribute) => boolean;
	uneditableIds?: string[];
}

const SearchableList: React.FC<ISearchableListProps> = ({
	activeId,
	disabledIds,
	items,
	onEditClick,
	onItemClick,
	onItemOptionsClick,
	onQueryChange,
	query,
	showInfoCard,
	showOptionsCondition = () => false,
	uneditableIds,
}) => {
	const filteredItems = items.filter(({displayName, name}) =>
		(displayName || name)
			.toString()
			.toLowerCase()
			.includes(query.toLowerCase())
	);

	const noResults = !filteredItems.length;

	return (
		<>
			<ClayDropdown.Search
				formProps={{onSubmit: (event) => event.preventDefault()}}
				onChange={onQueryChange}
				placeholder={Liferay.Language.get('search')}
				value={query}
			/>
			{noResults && <NoResultsDisplay spacer />}
			{!noResults && (
				<ClayDropdown.ItemList className="base-dropdown-list">
					{filteredItems.map((item) => {
						const active = activeId === item.id;

						const disabled =
							disabledIds &&
							disabledIds.some(
								(id) => id === item.id && id !== activeId
							);

						const editable =
							!(
								uneditableIds &&
								uneditableIds.some((id) => id === item.id)
							) && !active;

						return (
							<ListItem
								active={active}
								disabled={disabled}
								editable={editable}
								item={item}
								key={item.id}
								onClick={() => onItemClick(item)}
								onEditClick={() => onEditClick(item)}
								onOptionsClick={
									showOptionsCondition(item as Attribute) &&
									onItemOptionsClick
										? () =>
												onItemOptionsClick(
													item as Attribute
												)
										: undefined
								}
								showInfoCard={showInfoCard}
							/>
						);
					})}
				</ClayDropdown.ItemList>
			)}
		</>
	);
};

export default SearchableList;
