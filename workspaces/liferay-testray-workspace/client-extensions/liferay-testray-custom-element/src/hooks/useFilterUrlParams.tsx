/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useMemo, useState} from 'react';
import {useParams, useSearchParams} from 'react-router-dom';
import i18n from '~/i18n';
import fetcher from '~/services/fetcher';
import {safeJSONParse} from '~/util';
import {filterStatuses} from '~/util/statuses';

import {
	FilterSchema as FilterSchemaType,
	filterSchema as filterSchemas,
} from '../schema/filter';

type CustomFilterFieldsProps = {
	[key: string]: string;
};

type FieldOptions = {
	[fieldName: string]: Array<Options>;
};

type Options = {
	label: string;
	value: string;
};

const useFilterUrlParams = (customFilterFields?: CustomFilterFieldsProps) => {
	const [searchParams] = useSearchParams();
	const [filterResponse, setFilterResponse] = useState<FieldOptions>({});
	const [cache, setCache] = useState<Record<string, any>>({});

	const params = useParams();

	const serializedFilter = useMemo(() => {
		return JSON.parse(searchParams.get('filter') as string) || '';
	}, [searchParams]);

	const filterSchemaKey = searchParams.get('filterSchema');
	const filterSchema = (filterSchemas as any)[
		filterSchemaKey as string
	] as FilterSchemaType;

	const filterKeys = useMemo(
		() => Object.keys(serializedFilter),
		[serializedFilter]
	);
	const filterFields = useMemo(
		() =>
			filterSchema?.fields?.filter((field) =>
				filterKeys.includes(field.name)
			),
		[filterKeys, filterSchema?.fields]
	);

	const getFilterResponse = useCallback(async () => {
		if (filterSchema) {
			const parameters = safeJSONParse(JSON.stringify(params));
			const resourceFields =
				filterFields?.filter(({resource}) => resource) || {};
			const _resourceFieldOptions: any = {};

			if (resourceFields.length) {
				await Promise.all(
					resourceFields.map(async (field) => {
						const cacheKey =
							typeof field.resource === 'function'
								? (field.resource({
										...parameters,
										...customFilterFields,
									}) as string)
								: (field.resource as string);

						if (cache[cacheKey]) {
							_resourceFieldOptions[field.name] = cache[cacheKey];
						}
						else {
							const result = await fetcher(cacheKey);
							const parsedValue = field.transformData
								? field.transformData(result)
								: result;
							_resourceFieldOptions[field.name] = parsedValue;
							setCache((prevCache) => ({
								...prevCache,
								[cacheKey]: parsedValue,
							}));
						}
					})
				);
			}

			setFilterResponse(_resourceFieldOptions);
		}
	}, [filterSchema, params, filterFields, customFilterFields, cache]);

	useEffect(() => {
		getFilterResponse();
	}, [getFilterResponse]);

	const filterWithOptions = useMemo(() => {
		if (serializedFilter || customFilterFields) {
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
							label:
								key === 'dueStatus'
									? filterStatuses[value]
									: value,

							value,
						})
					);
				}
			});

			Object.keys(filterResponse).forEach((key) => {
				if (Array.isArray(serializedFilter[key])) {
					const filteredOptions = filterResponse[key]?.filter(
						(option: Options) =>
							serializedFilter[key].includes(option.value)
					);

					if (filteredOptions.length) {
						updatedFilterOptions[key] = filteredOptions;
					}
				}
				else {
					const matchingValues = filterResponse[key]?.filter(
						(options: Options) =>
							options.value === serializedFilter[key]
					);
					if (matchingValues.length) {
						updatedFilterOptions[key] = matchingValues;
					}
				}
			});

			return updatedFilterOptions;
		}
	}, [customFilterFields, filterResponse, serializedFilter]);

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

	const filterInitialContext = useMemo(
		() => ({
			entries: filterEntries,
			filter: filterWithOptions,
		}),
		[filterEntries, filterWithOptions]
	);

	return {
		filterInitialContext,
	};
};

export default useFilterUrlParams;
