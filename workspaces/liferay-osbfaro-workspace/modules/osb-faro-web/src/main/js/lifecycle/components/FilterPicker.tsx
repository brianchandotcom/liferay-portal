import * as API from 'shared/api';
import React from 'react';
import SharedFilterPicker, {
	IFilterPickerItem,
} from 'shared/components/FilterPicker';
import {
	IPaginatedDataSourceResult,
	PaginatedDataSourceFn,
} from 'shared/hooks/usePaginatedRequest';
import {useLifecycle} from '../context/LifecycleContext';
import {useParams} from 'react-router-dom';

// Field values are plain strings, so each one is its own id and name.

const toFilterPickerItem = (item: string): IFilterPickerItem => ({
	id: item,
	name: item,
});

interface IProps {
	className?: string;
	entityLabel: string;
	fieldMappingFieldName: string;
	filterKey: 'countryFilter' | 'industryFilter';
}

const FilterPicker = ({
	className,
	entityLabel,
	fieldMappingFieldName,
	filterKey,
}: IProps) => {
	const {filters, updateFilters} = useLifecycle();

	const {channelId, groupId} = useParams();

	const selectedValue = filters[filterKey];

	const paginatedDataSourceFn: PaginatedDataSourceFn<IFilterPickerItem> = ({
		page,
		pageSize,
		query,
	}) =>
		API.accounts
			.fetchFieldValues({
				channelId,
				delta: pageSize,
				fieldMappingFieldName,
				groupId,
				page,
				query,
			})
			.then(({items = [], total}: IPaginatedDataSourceResult) => ({
				items: items.map(toFilterPickerItem),
				total,
			}));

	return (
		<SharedFilterPicker
			className={className}
			entityLabel={entityLabel}
			onFilterChange={(item) =>
				updateFilters({[filterKey]: item?.id ?? ''})
			}
			paginatedDataSourceFn={paginatedDataSourceFn}
			selected={
				selectedValue ? {id: selectedValue, name: selectedValue} : null
			}
		/>
	);
};

export default FilterPicker;
