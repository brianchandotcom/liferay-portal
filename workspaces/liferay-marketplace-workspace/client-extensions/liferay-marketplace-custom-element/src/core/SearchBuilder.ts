/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FilterVariables, RendererFields} from '../schema/filters';

type Filter = {
	[key: string]: string | number | string[] | number[];
};
type Key = string;
type Value = string | number | boolean | null;

export type Operators =
	| 'contains'
	| 'eq'
	| 'ge'
	| 'gt'
	| 'lambda'
	| 'le'
	| 'lt'
	| 'ne'
	| 'startsWith';

export type SearchBuilderConstructor = {
	useURIEncode?: boolean;
};

export default class SearchBuilder {
	private lock: boolean = false;
	private query: string = '';
	private useURIEncode?: boolean = true;

	constructor({useURIEncode}: SearchBuilderConstructor = {}) {
		this.useURIEncode = useURIEncode;
	}

	static unquote(criteria: string) {
		return criteria.replaceAll("'", '');
	}

	/**
	 * @description Contains
	 * @example contains(title,'edmon')
	 */

	static contains(key: Key, value: Value) {
		return `contains(${key}, '${value}')`;
	}

	static eq(key: Key, value: Value) {
		return `${key} eq ${typeof value === 'boolean' ? value : `'${value}'`}`;
	}

	static in(key: Key, values: Value[]) {
		if (values) {
			const operator = `${key} in ({values})`;

			return operator
				.replace(
					'{values}',
					values
						.map((value) =>
							typeof value === 'number' ? value : `'${value}'`
						)
						.join(',')
				)
				.trim();
		}

		return '';
	}

	static lambda(key: Key, value: Value) {
		return `(${key}/any(x:(x eq '${value}')))`;
	}

	static lambdaContains(key: Key, value: Value) {
		return `(${key}/any(x:contains(x, '${value}')))`;
	}

	static ne(key: Key, value: Value) {
		return `${key} ne '${value}'`;
	}

	static gt(key: Key, value: Value) {
		return `${key} gt ${value}`;
	}

	static ge(key: Key, value: Value) {
		return `${key} ge ${value}`;
	}

	static lt(key: Key, value: Value) {
		return `${key} lt ${value}`;
	}

	static le(key: Key, value: Value) {
		return `${key} le ${value}`;
	}

	static group(type: 'CLOSE' | 'OPEN') {
		return type === 'OPEN' ? '(' : ')';
	}

	static startsWith(key: Key, value: Value) {
		return `${key} startsWith '${value}'`;
	}

	static removeEmptyFilter(filter: Filter) {
		const _filter: Filter = {};

		for (const key in filter) {
			const value = filter[key];

			if (
				!value ||
				!(value as string).length ||
				(Array.isArray(value) &&
					value.some((item: any) => item.value === ''))
			) {
				continue;
			}

			_filter[key] = value;
		}

		return _filter;
	}

	static formatValuesToString(values: Value[]) {
		if (values) {
			return values
				.map((value) => `${value}`)
				.join(',')
				.trim();
		}

		return '';
	}

	static createCustomFilter(schema: RendererFields, filter: any) {
		const customOperator = schema?.operator;
		const requestOperator = schema?.requestOperator as string;
		const optionalOperator = schema?.optionalOperator as Operators;

		const isNoFilterApplied =
			filter.includes('false') || filter.includes('No');

		if (customOperator && SearchBuilder[customOperator]) {
			if (optionalOperator === 'ne') {
				if (isNoFilterApplied) {
					return `not (${SearchBuilder[optionalOperator](
						requestOperator,
						null
					)})`;
				}
			}

			if (Array.isArray(filter)) {
				const filters = filter
					.map((item) =>
						typeof item === 'object' ? item.value : item
					)
					.join(',');

				if (filters.includes('DIDNOTRUN')) {
					return SearchBuilder[optionalOperator](
						requestOperator,
						filters
					);
				}

				return SearchBuilder[customOperator](requestOperator, filters);
			}
			else if (typeof filter === 'object' && 'value' in filter) {
				return SearchBuilder[customOperator](
					requestOperator,
					filter.value
				);
			}

			return SearchBuilder[customOperator](requestOperator, filter);
		}

		if (typeof filter === 'string') {
			return filter;
		}

		return this.formatValuesToString(
			filter.map((_value: any) =>
				typeof _value === 'object' ? _value.value : _value
			)
		);
	}

	static createFilter({
		appliedFilter,
		defaultFilter,
		filterSchema,
	}: FilterVariables) {
		const _filter = defaultFilter ? [defaultFilter] : [];

		for (const key in appliedFilter) {
			let searchCondition = '';
			const rawValue = appliedFilter[key];

			if (!rawValue) {
				continue;
			}

			const schema = filterSchema.fields.find(
				({name}) => key === name
			) as RendererFields;

			const normalizeValue = (val: any) =>
				typeof val === 'object' && val !== null ? val.value : val;

			let value: any;
			if (Array.isArray(rawValue)) {
				value = rawValue.map(normalizeValue);
			}
			else {
				value = normalizeValue(rawValue);
			}

			const isFilterChanged =
				typeof value === 'string' &&
				(value.includes('false') || value.includes('No'));

			const removeQuoteMark =
				schema?.removeQuoteMark ||
				schema?.type === 'number' ||
				isFilterChanged;

			const customOperator = schema?.operator;

			if (customOperator && SearchBuilder[customOperator]) {
				const [filterKey] = key.split('|');
				const formattedKey = filterKey.replace('$', '');

				if (customOperator === 'lambda') {
					if (Array.isArray(value)) {
						const lambdas = value
							.map((filter) =>
								SearchBuilder.lambda(filterKey, filter)
							)
							.join(' or ');

						searchCondition = lambdas;
					}
					else {
						searchCondition = SearchBuilder.lambda(
							filterKey,
							value
						);
					}
				}
				else {
					const getOptionalSearchCondition = () => {
						if (schema?.optionalOperator === 'ne') {
							if (isFilterChanged) {
								return `not (${SearchBuilder[
									schema.optionalOperator
								](formattedKey, null)})`;
							}

							if (
								typeof value === 'string' &&
								value.includes('true')
							) {
								return SearchBuilder[schema.optionalOperator](
									formattedKey,
									null
								);
							}
						}

						return SearchBuilder[customOperator](
							formattedKey,
							value
						);
					};

					searchCondition = getOptionalSearchCondition();
				}
			}
			else {
				if (schema?.type === 'date-range' && Array.isArray(value)) {
					const [start, end] = value[0]
						.split(' - ')
						.map((dateStr: string) =>
							new Date(dateStr.trim()).toISOString()
						);

					searchCondition = [
						SearchBuilder.gt(key, start),
						SearchBuilder.lt(key, end),
					].join(' and ');
				}
				else {
					if (Array.isArray(value)) {
						searchCondition = SearchBuilder.in(key, value);
					}
					else {
						searchCondition = SearchBuilder.eq(key, value);
					}
				}
			}

			_filter.push(
				removeQuoteMark
					? searchCondition.replaceAll(`'`, '')
					: searchCondition
			);
		}

		return _filter.join(' and ');
	}

	public and() {
		return this.setContext('and');
	}

	public build() {
		const query = this.query.trim();

		if (query.endsWith('or') || query.endsWith('and')) {
			return query.substring(0, query.length - 3);
		}

		this.lock = true;

		return this.useURIEncode ? encodeURIComponent(query) : query;
	}

	public clone() {
		const clone = new SearchBuilder({useURIEncode: this.useURIEncode});

		clone.lock = this.lock;
		clone.query = this.query;

		return clone;
	}

	public contains(key: Key, value: Value) {
		return this.setContext(SearchBuilder.contains(key, value));
	}

	public eq(key: Key, value: Value, options = {unquote: false}) {
		const parseFn = options.unquote
			? SearchBuilder.unquote
			: (fn: any) => fn;

		return this.setContext(parseFn(SearchBuilder.eq(key, value)));
	}

	public lambda(key: Key, value: Value, options = {unquote: false}) {
		const parseFn = options.unquote
			? SearchBuilder.unquote
			: (fn: any) => fn;

		return this.setContext(parseFn(SearchBuilder.lambda(key, value)));
	}

	public lambdaContains(key: Key, value: Value, options = {unquote: false}) {
		const parseFn = options.unquote
			? SearchBuilder.unquote
			: (fn: any) => fn;

		return this.setContext(
			parseFn(SearchBuilder.lambdaContains(key, value))
		);
	}

	public gt(key: Key, values: Value) {
		return this.setContext(SearchBuilder.gt(key, values));
	}

	public lt(key: Key, values: Value) {
		return this.setContext(SearchBuilder.lt(key, values));
	}

	public in(key: Key, values: Value[]) {
		return this.setContext(SearchBuilder.in(key, values));
	}

	public inEqualNumbers(key: Key, values: Value[]) {
		if (!values.length) {
			return this;
		}

		this.setContext(SearchBuilder.group('OPEN'));

		const lastIndex = values.length - 1;

		values.map((value, index) => {
			this.setContext(SearchBuilder.eq(key, value).replaceAll("'", ''));

			if (lastIndex !== index) {
				this.or();
			}
		});

		return this.group('CLOSE');
	}

	public ne(key: Key, value: Value) {
		return this.setContext(SearchBuilder.ne(key, value));
	}

	public group(type: 'CLOSE' | 'OPEN') {
		return this.setContext(SearchBuilder.group(type));
	}

	private setContext(query: string) {
		if (!this.lock) {
			this.query += ` ${query}`;
		}

		return this;
	}

	public or() {
		return this.setContext('or');
	}
}
