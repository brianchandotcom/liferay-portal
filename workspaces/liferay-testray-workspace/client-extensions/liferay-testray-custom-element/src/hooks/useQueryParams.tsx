/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useMemo, useState} from 'react';
import {useParams, useSearchParams} from 'react-router-dom';
import useSWR from 'swr';
import i18n from '~/i18n';
import fetcher from '~/services/fetcher';
import {safeJSONParse} from '~/util';

import {
	FilterSchema as FilterSchemaType,
	filterSchema as filterSchemas,
} from '../schema/filter';

type CustomFilterFieldsProps = {
	[key: string]: string;
};

type Options = {
	label: string;
	value: string;
};

type Params = {
	[key: string]: string | number | boolean;
};

type FieldOptions = {
	[fieldName: string]: string | Array<Options>;
};

const useQueryParams = (customFilterFields?: CustomFilterFieldsProps) => {
	const [searchParams, setSearchParams] = useSearchParams();
	const [filterWithOptions, setFilterWithOptions] = useState<FieldOptions>(
		{}
	);

	const params = useParams();
	const page = searchParams.get('page');

	const pageSize = searchParams.get('pageSize');

	const serializedFilter = useMemo(() => {
		return JSON.parse(searchParams.get('filter') as string) || '';
	}, [searchParams]);

	const filterSchemaKey = searchParams.get('filterSchema');
	const filterSchema = (filterSchemas as any)[
		filterSchemaKey as string
	] as FilterSchemaType;

	const filterKeys = useMemo(() => Object.keys(serializedFilter), [
		serializedFilter,
	]);
	const filterFields = useMemo(
		() =>
			filterSchema?.fields?.filter((field) =>
				filterKeys.includes(field.name)
			),
		[filterKeys, filterSchema?.fields]
	);

	const {data: filterResponse = {}} = useSWR(
		filterSchema?.fields?.length
			? `/filterResponse-${filterSchema?.name}`
			: null,
		async () => {
			const parameters = safeJSONParse(JSON.stringify(params));
			const resourceFields =
				filterFields?.filter(({resource}) => resource) || {};
			const _resourceFieldOptions: any = {};

			if (resourceFields) {
				await Promise.all(
					resourceFields.map((field) =>
						fetcher(
							(typeof field.resource === 'function'
								? field.resource({
										...parameters,
										...customFilterFields,
								  })
								: field.resource) as string
						)
					)
				).then((results) =>
					results.forEach((result, index) => {
						const field = resourceFields[index];

						if (field.transformData) {
							const parsedValue = field.transformData(result);

							_resourceFieldOptions[field.name] = parsedValue;
						}
					})
				);
			}

			return _resourceFieldOptions;
		}
	);

	const getFilterWithOptions = useCallback(() => {
		const updatedFilterOptions: any = {...serializedFilter};

		Object.keys(updatedFilterOptions).forEach((key) => {
			if (
				Array.isArray(updatedFilterOptions[key]) &&
				updatedFilterOptions[key].some(
					(item: Options) => typeof item !== 'object'
				)
			) {
				updatedFilterOptions[key] = updatedFilterOptions[key].map(
					(value: string) => ({
						label: value,
						value,
					})
				);
			}
		});

		Object.keys(filterResponse).forEach((key) => {
			if (Array.isArray(serializedFilter[key])) {
				const filteredOptions = filterResponse[
					key
				]?.filter((option: Options) =>
					serializedFilter[key].includes(option.value)
				);

				if (filteredOptions.length) {
					updatedFilterOptions[key] = filteredOptions;
				}
			} else {
				const matchingValues = filterResponse[key]?.filter(
					(options: Options) =>
						options.value === serializedFilter[key]
				);
				if (matchingValues.length) {
					updatedFilterOptions[key] = matchingValues;
				}
			}
		});

		setFilterWithOptions(updatedFilterOptions);
	}, [filterResponse, serializedFilter]);

	useEffect(() => {
		if (serializedFilter || customFilterFields?.key) {
			getFilterWithOptions();
		}
	}, [customFilterFields, getFilterWithOptions, serializedFilter]);

	const filterEntries = useMemo(
		() =>
			filterFields?.map((filteredField) => {
				const filterValue =
					serializedFilter[filteredField.name as string];
				const filterValueOptions =
					filterWithOptions[filteredField.name as string];

				return {
					label: i18n.translate(filteredField.label),
					name: filteredField.name,
					value: Array.isArray(filterValueOptions)
						? filterValueOptions?.map(({label}) => label)
						: filterValue,
				};
			}) || [],
		[filterFields, serializedFilter, filterWithOptions]
	);

	const updateUrlParams = (param: Params) => {
		setSearchParams(
			new URLSearchParams({
				...(serializedFilter && {
					filter: JSON.stringify(serializedFilter),
				}),
				...(page && {page}),
				...(pageSize && {pageSize}),
				...param,
			})
		);
	};

	const filterInitialContext = useMemo(
		() => ({
			entries: filterEntries,
			filter: filterWithOptions,
		}),
		[filterEntries, filterWithOptions]
	);

	return {
		filterInitialContext,
		updateUrlParams,
	};
};

export default useQueryParams;
