/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useMemo, useState} from 'react';
import {
	useLocation,
	useNavigate,
	useParams,
	useSearchParams,
} from 'react-router-dom';
import SearchBuilder from '~/core/SearchBuilder';
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

type FieldOptions = {
	[fieldName: string]: string | Array<Options>;
};

type Params = {
	[key: string]: string | number | boolean;
};

const useQueryParams = (customFilterFields?: CustomFilterFieldsProps) => {
	const location = useLocation();
	const navigate = useNavigate();
	const [searchParams] = useSearchParams();
	const [filterWithOptions, setFilterWithOptions] = useState<FieldOptions>(
		{}
	);

	const routeParams = useParams();
	const page = searchParams.get('page');

	const pageSize = searchParams.get('pageSize');

	const serializedFilter = useMemo(() => {
		return JSON.parse(searchParams.get('filter') as string) || '';
	}, [searchParams]);

	const filterSchemaKey = searchParams.get('filterSchema') || '';
	const filterSchema = (filterSchemas as any)[
		filterSchemaKey
	] as FilterSchemaType;
	const filterFields = filterSchema?.fields;

	const filterKeys = useMemo(() => Object.keys(serializedFilter), [
		serializedFilter,
	]);
	const filteredFields = useMemo(
		() => filterFields?.filter((field) => filterKeys.includes(field.name)),
		[filterFields, filterKeys]
	);

	const getFilterWithOptions = useCallback(async () => {
		const parameters = safeJSONParse(JSON.stringify(routeParams));
		const resourceFields =
			filteredFields?.filter(({resource}) => resource) || {};
		const _resourceFieldOptions: any = {};

		for (const field of resourceFields) {
			const resource =
				typeof field.resource === 'function'
					? field.resource({...parameters, ...customFilterFields})
					: (field.resource as string);

			const filter = SearchBuilder.in('id', serializedFilter[field.name]);

			let resourceFilter = resource;

			if (resource.includes('filter=')) {
				resourceFilter = resource.replace(
					/(filter=.*?)(&|$)/,
					`$1 and ${filter}$2`
				);
			}
			else {
				resourceFilter = `${resource}${
					resource.includes('?') ? '&' : '?'
				}filter=${filter}`;
			}

			const response = await fetcher(resourceFilter);

			if (field.transformData) {
				const parsedValue = field.transformData(response);

				if (Array.isArray(parsedValue)) {
					_resourceFieldOptions[field.name] = parsedValue;
				}
				else {
					if (
						filterKeys.every(
							(key) => parsedValue && key in parsedValue
						)
					) {
						const filteredObjects = parsedValue.filter(
							(options: any) =>
								filterKeys.every((key) =>
									Array.isArray(serializedFilter[key])
										? serializedFilter[key].includes(
												options[key]
										  )
										: options[key] === serializedFilter[key]
								)
						);

						_resourceFieldOptions[
							field.name
						] = filteredObjects.length
							? filteredObjects
							: parsedValue;
					}
				}
			}
		}

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

		Object.keys(_resourceFieldOptions).forEach((key) => {
			if (Array.isArray(serializedFilter[key])) {
				const filteredOptions = _resourceFieldOptions[
					key
				]?.filter((option: Options) =>
					serializedFilter[key].includes(option.value)
				);

				if (filteredOptions.length) {
					updatedFilterOptions[key] = filteredOptions;
				}
			}
			else {
				const matchingValues = _resourceFieldOptions[key]?.filter(
					(options: Options) =>
						options.value === serializedFilter[key]
				);
				if (matchingValues.length) {
					updatedFilterOptions[key] = matchingValues;
				}
			}
		});

		setFilterWithOptions(updatedFilterOptions);
	}, [
		customFilterFields,
		filteredFields,
		filterKeys,
		routeParams,
		serializedFilter,
	]);

	const updateUrlParams = (param: Params) => {
		const existingParams = new URLSearchParams(location.search);

		for (const [key, value] of Object.entries(param)) {
			existingParams.set(key, value as string);
		}

		const newUrl = `${location.pathname}?${existingParams.toString()}`;

		navigate(newUrl, {replace: true});
	};

	useEffect(() => {
		if (serializedFilter || customFilterFields?.key) {
			getFilterWithOptions();
		}
	}, [customFilterFields, getFilterWithOptions, serializedFilter]);

	const filterEntries = useMemo(
		() =>
			filteredFields?.map((filteredField) => {
				const filterValue =
					serializedFilter[filteredField.name as string];
				const filterValueOptions =
					filterWithOptions[filteredField.name as string];

				return {
					label: i18n.translate(filteredField.label),
					name: filteredField.name,
					value: Array.isArray(filterValueOptions)
						? filterValueOptions.map(({label}) => label)
						: filterValue,
				};
			}) || [],
		[filterWithOptions, serializedFilter, filteredFields]
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
		page,
		pageSize,
		updateUrlParams,
	};
};

export default useQueryParams;
