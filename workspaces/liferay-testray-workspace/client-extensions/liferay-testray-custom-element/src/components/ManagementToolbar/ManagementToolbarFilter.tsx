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
import {ListViewContext, ListViewTypes} from '~/context/ListViewContext';
import SearchBuilder from '~/core/SearchBuilder';
import useFormActions from '~/hooks/useFormActions';
import useUpdateUrlParams from '~/hooks/useUpdateUrlParams';
import i18n from '~/i18n';
import {FilterSchema} from '~/schema/filter';
import fetcher from '~/services/fetcher';
import {safeJSONParse} from '~/util';

import Form from '../Form';
import {RendererFields} from '../Form/Renderer';

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

	const formActions = useFormActions();
	const [listViewContext, dispatch] = useContext(ListViewContext);
	const location = useLocation();
	const navigate = useNavigate();
	const params = useParams();
	const [form, setForm] = useState(() => ({
		...initialFilters,
		...listViewContext.filters.filter,
	}));

	const onChange = formActions.form.onChange({form, setForm});

	const onClear = () => {
		setForm(initialFilters);
	};

	const clearDisabled = Object.values(form).every(
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
			const parameters = safeJSONParse(paramsMemoized);

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
			const field = fields?.find(({name}) => name === key);

			const value = filterCleaned[key];

			return {
				label: field?.label,
				name: key,
				value,
			};
		});

		const filters = Object.keys(filterCleaned).map((key) => {
			const field = fields?.find(({name}) => name === key);

			const valueOption =
				field?.name.includes('teamToComponents/name') ||
				field?.name.includes('componentToCaseResult/name');

			if (Array.isArray(filterCleaned[key])) {
				return {
					name: key,
					value: (filterCleaned as any)[key].map((options: Option) =>
						valueOption ? options?.label : options?.value || options
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
				{fields.length > 1 && (
					<>
						<p className="font-weight-bold my-2">
							{i18n.translate('filter-results')}
						</p>

						<Form.Input
							name="search-filter"
							onChange={({target: {value}}) => setFilter(value)}
							placeholder={i18n.translate('search-filters')}
							ref={inputRef}
							value={filter}
						/>

						<ClayButtonWithIcon
							aria-label={i18n.translate('clear')}
							className="clear-button"
							displayType="unstyled"
							onClick={() => setFilter('')}
							symbol="times"
							title={i18n.translate('clear')}
						/>

						<Form.Divider />
					</>
				)}
			</ClayDropDown.Section>

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
						onChange={onChange}
					/>
				</div>
			</div>

			<ClayDropDown.Section className="dropdown-footer">
				<ClayButton className="mt-2" onClick={onApply}>
					{i18n.translate('apply')}
				</ClayButton>
				<ClayButton
					className="ml-3 mt-2"
					disabled={clearDisabled}
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
			<ClayButton
				className="management-toolbar-buttons nav-link"
				displayType="unstyled"
				onClick={handleExpand}
			>
				<span>
					<ClayIcon
						className="inline-item inline-item-after inline-item-before"
						symbol="filter"
					/>
				</span>
			</ClayButton>
			{isVisible && (
				<ClayDropDown.Menu
					active={isVisible}
					alignElementRef={buttonRef}
					alignmentPosition={3}
					className={classNames('dropdown-management-toolbar', {
						'dropdown-management-toolbar-small': hasOneFilter,
					})}
					closeOnClickOutside
					onActiveChange={() =>
						setIsVisible((isVisible) => !isVisible)
					}
				>
					<FilterBody
						applyFilters={applyFilters}
						customFilterFields={customFilterFields}
						filterSchema={filterSchema}
						isVisible={isVisible}
						setIsVisible={setIsVisible}
					/>
				</ClayDropDown.Menu>
			)}
		</>
	);
};

export default ManagementToolbarFilter;
