/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';
import {useHotkeys} from 'react-hotkeys-hook';
import {useLocation, useNavigate, useParams} from 'react-router-dom';
import useSWR from 'swr';
import {FilterSchema, RendererFields} from '../../../../schema/filters';
import useUpdateUrlParams from '../../hooks/useUpdateUrlParams';
import {ListViewContext, ListViewTypes} from '../../hooks/ListViewContext';
import i18n from '../../../../i18n';
import SearchBuilder from '../../../../core/SearchBuilder';
import fetcher from '../../../../services/fetcher';
import {safeJSONParse} from '../../../../utils/util';
import Form from '../../../Form/index';

import './ManagementToolbarFilters.scss';

type ManagementToolbarFilterProps = {
	applyFilters?: boolean;
	customFilterFields?: {[key: string]: string};
	filterSchema?: FilterSchema;
};

type Option = {label: string; value: string};

type FilterBodyProps = {
	applyFilters?: boolean;
	customFilterFields?: {[key: string]: string};
	filterSchema: FilterSchema | undefined;

	isVisible: boolean;
	setIsVisible: React.Dispatch<React.SetStateAction<boolean>>;
};

const FilterBody: React.FC<FilterBodyProps> = ({
	applyFilters = true,
	customFilterFields,
	filterSchema,
	isVisible,
	setIsVisible,
}) => {
	const [filter, setFilter] = useState('');
	const updateUrlParams = useUpdateUrlParams();

	const inputRef = useRef<HTMLInputElement>(null);

	useEffect(() => {
		const timeout = setTimeout(() => {
			if (isVisible) {
				inputRef?.current?.focus();
			}
		}, 100);

		return () => clearTimeout(timeout);
	}, [isVisible]);

	const fields = useMemo(
		() => filterSchema?.fields as RendererFields[],
		[filterSchema?.fields]
	);

	const initialFilters = useMemo(() => {
		const initialValues: {[key: string]: string} = {};

		for (const field of fields) {
			initialValues[field.name] = '';
		}

		return initialValues;
	}, [fields]);

	const [listViewContext, dispatch] = useContext(ListViewContext);
	const location = useLocation();
	const navigate = useNavigate();
	const params = useParams();
	const [form, setForm] = useState(() => ({
		...initialFilters,
		...listViewContext.filters.filter,
	}));

	const onChange =
		({form, setForm}: any) =>
		(event: any) => {
			const {
				target: {checked, name, options, type},
			} = event;

			let {value} = event.target;

			if (type === 'date-picker') {
				value = [
					{
						label: value,
						value,
					},
				];
			}
			if (type === 'checkbox') {
				value = checked;
			}
			else if (type === 'select-one') {
				value = [
					{
						label: options.item(options.selectedIndex).label,
						value: Number(value) || value,
					},
				];
			}

			setForm({
				...form,
				[name]: value,
			});
		};

	const onClear = () => {
		setForm(initialFilters);
	};

	const clearButtonDisabled = Object.values(form).every(
		(value) => !value || !value.length
	);

	const handleRemoveItemFromFilter = useCallback(() => {
		const searchParams = new URLSearchParams(location.search);
		searchParams.delete('filter');
		searchParams.delete('filterSchema');

		return navigate({
			search: `?${searchParams.toString()}`,
		});
	}, [location.search, navigate]);

	const paramsMemoized = useMemo(() => {
		const testrayModalParams = document.getElementById(
			'testray-modal-params'
		);

		if (testrayModalParams) {
			return testrayModalParams.textContent;
		}

		return JSON.stringify({...params, ...customFilterFields});
	}, [params, customFilterFields]);

	const fieldsMemoized = useMemo(() => filterSchema?.fields, [filterSchema]);

	const {data: fieldOptions = {}, isLoading} = useSWR(
		filterSchema?.fields?.length ? `/filter-${filterSchema?.name}` : null,
		async () => {
			const parameters = safeJSONParse(paramsMemoized, {});

			const fieldsWithResource = fieldsMemoized?.filter(
				({resource}) => resource
			);

			const _fieldOptions: any = {};

			if (fieldsWithResource) {
				await Promise.all(
					fieldsWithResource.map((field) =>
						fetcher(
							(typeof field.resource === 'function'
								? field.resource(parameters)
								: field.resource) as string
						)
					)
				).then((results) =>
					results.forEach((result, index) => {
						const field = fieldsWithResource[index];

						if (field.transformData) {
							const parsedValue = field.transformData(result);

							_fieldOptions[field.name] = parsedValue;
						}
					})
				);
			}

			return _fieldOptions;
		}
	);

	const onApply = useCallback(() => {
		const filterCleaned = SearchBuilder.removeEmptyFilter(form);

		const entries = Object.keys(filterCleaned).map((key) => {
			const field = fields?.find(({name}) => {
				return name === key;
			});

			const value = filterCleaned[key];

			return {
				label: field?.label,
				name: key,
				value,
			};
		});

		const filters = Object.keys(filterCleaned).map((key) => {
			if (Array.isArray(filterCleaned[key])) {
				return {
					name: key,
					value: (filterCleaned as any)[key].map((options: Option) =>
						options?.label
							? options?.label
							: options?.value || options
					),
				};
			}
			else {
				return {
					name: key,
					value: filterCleaned[key],
				};
			}
		});

		const formattedFilter = filters.reduce(
			(previousValue, currentValue) => {
				return {
					...previousValue,
					[currentValue.name]: currentValue.value,
				};
			},
			{}
		);

		if (applyFilters && filterSchema) {
			updateUrlParams({
				filter: JSON.stringify(formattedFilter),
				filterSchema: filterSchema?.name as string,
				page: '1',
			});
		}

		if (!Object.keys(formattedFilter).length) {
			handleRemoveItemFromFilter();
		}

		dispatch({
			payload: {filters: {entries, filter: filterCleaned}},
			type: ListViewTypes.SET_FILTERS,
		});

		setIsVisible(false);
	}, [
		applyFilters,
		dispatch,
		fields,
		filterSchema,
		form,
		handleRemoveItemFromFilter,
		setIsVisible,
		updateUrlParams,
	]);

	useEffect(() => {
		const searchParams = new URLSearchParams(location.search);

		if (!searchParams.get('filter')) {
			setForm(initialFilters);
		}
	}, [initialFilters, location.search]);

	useHotkeys('enter', onApply, {enabled: true}, [fields, form]);

	return (
		<div className="align-content-between d-flex flex-column">
			<ClayDropDown.Section className="dropdown-header">
				{fields.length > 2 && (
					<>
						<p className="font-weight-bold my-2">
							{i18n.translate('filter')}
						</p>
						<div className="d-flex justify-content-between align-items-center">
							<Form.Input
								name="search-filter"
								onChange={({target: {value}}) => {
									setFilter(value);
								}}
								placeholder={i18n.translate('search')}
								ref={inputRef}
								value={filter}
							/>

							<ClayButtonWithIcon
								aria-label={i18n.translate('clear')}
								className="clear-button ml-3"
								displayType="unstyled"
								onClick={() => setFilter('')}
								symbol="times"
								title={i18n.translate('clear')}
							/>
						</div>

						<br />
					</>
				)}
			</ClayDropDown.Section>
			<ClayDropDown.Section>
				<div className="management-toolbar-body">
					<div className="dropdown-filter-content" tabIndex={1}>
						<Form.Renderer
							fieldOptions={fieldOptions}
							fields={fields}
							filter={filter}
							filterSchema={filterSchema?.name as string}
							form={form}
							isLoading={isLoading}
							onApply={onApply}
							onChange={onChange({form, setForm})}
						/>
					</div>
				</div>
			</ClayDropDown.Section>
			<ClayDropDown.Section className="dropdown-footer d-flex justify-content-center">
				<ClayButton className="mt-2" onClick={onApply}>
					{i18n.translate('apply')}
				</ClayButton>
				<ClayButton
					className="ml-3 mt-2"
					disabled={clearButtonDisabled}
					displayType="secondary"
					onClick={onClear}
				>
					{i18n.translate('clear')}
				</ClayButton>
			</ClayDropDown.Section>
		</div>
	);
};

const ManagementToolbarFilter: React.FC<ManagementToolbarFilterProps> = ({
	applyFilters = true,
	customFilterFields,
	filterSchema,
}) => {
	const buttonRef = useRef<HTMLButtonElement | null>(null);

	const [isVisible, setIsVisible] = useState(false);

	const hasOneFilter = filterSchema?.fields?.length === 1;

	const handleExpand = (
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) => {
		buttonRef.current = event.target as HTMLButtonElement;

		setIsVisible((isVisible) => !isVisible);
	};

	return (
		<>
			<div className="justify-content-between align-items-center d-flex">
				<ClayButton
					className="management-toolbar-filter-button d-flex justify-content-between align-items-center px-2 mr-2 ml-3 btn-secondary"
					displayType="unstyled"
					onClick={handleExpand}
				>
					<ClayIcon className="mr-2" symbol="filter" />
					{i18n.translate('filter')}
				</ClayButton>
			</div>
			{isVisible && (
				<ClayDropDown.Menu
					active={isVisible}
					alignElementRef={buttonRef}
					alignmentPosition={3}
					className={classNames('management-toolbar-dropdown', {
						'dropdown-management-toolbar-small': hasOneFilter,
					})}
					closeOnClickOutside
					onActiveChange={() =>
						setIsVisible((isVisible) => !isVisible)
					}
				>
					<div className="management-toolbar-dropdown-body">
						<FilterBody
							applyFilters={applyFilters}
							customFilterFields={customFilterFields}
							filterSchema={filterSchema}
							isVisible={isVisible}
							setIsVisible={setIsVisible}
						/>
					</div>
				</ClayDropDown.Menu>
			)}
		</>
	);
};

export default ManagementToolbarFilter;
