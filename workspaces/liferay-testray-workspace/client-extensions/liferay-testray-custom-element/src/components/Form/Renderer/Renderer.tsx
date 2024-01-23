/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form from '..';
import React, {
	Dispatch,
	memo,
	useCallback,
	useEffect,
	useMemo,
	useState,
} from 'react';
import {useParams} from 'react-router-dom';

import {Operators} from '../../../core/SearchBuilder';
import i18n from '../../../i18n';
import fetcher from '../../../services/fetcher';
import {safeJSONParse} from '../../../util';
import {AutoCompleteProps} from '../AutoComplete';

type RenderedFieldOptions = string[] | {label: string; value: string}[];

export type RendererFields = {
	disabled?: boolean;
	label: string;
	name: string;
	operator?: Operators;
	optionalOperator?: Operators;
	options?: RenderedFieldOptions;
	placeholder?: string;
	removeQuoteMark?: boolean;
	type:
		| 'autocomplete'
		| 'checkbox'
		| 'date'
		| 'multiselect'
		| 'number'
		| 'select'
		| 'text'
		| 'textarea';
} & Partial<AutoCompleteProps>;

export type FieldOptions = {[key: string]: any[]};

type RendererProps = {
	fieldOptions: FieldOptions;
	fields: RendererFields[];
	filter?: string;
	form: any;
	onChange: (event: any) => void;
	setFieldOptions: Dispatch<FieldOptions>;
};

const Renderer: React.FC<RendererProps> = ({
	fieldOptions,
	fields,
	filter,
	form,
	onChange,
	setFieldOptions,
}) => {
	const params = useParams();

	const [fieldDisabled, setFieldDisabled] = useState({});
	const [isLoading, setIsLoading] = useState(true);

	const paramsMemoized = useMemo(() => {
		const testrayModalParams = document.getElementById(
			'testray-modal-params'
		);

		if (testrayModalParams) {
			return testrayModalParams.textContent!;
		}

		return JSON.stringify(params);
	}, [params]);

	const fieldsMemoized = useMemo(() => fields, [fields]);

	const setFieldOpt = useCallback(
		(abc: any) => {
			setFieldOptions(abc);
		},
		[setFieldOptions]
	);

	const fieldsFilteredMemoized = useMemo(
		() =>
			fieldsMemoized.filter(({label}) =>
				filter
					? label.toLowerCase().includes(filter.toLowerCase())
					: true
			),
		[fieldsMemoized, filter]
	);

	const fetchResources = useCallback(async () => {
		const parameters = safeJSONParse(paramsMemoized);

		const fieldsWithResource = fieldsMemoized.filter(
			({resource}) => resource
		);

		const _fieldOptions: any = {};

		for (const field of fieldsWithResource) {
			const result = await fetcher(
				(typeof field.resource === 'function'
					? field.resource(parameters)
					: field.resource) as string
			);

			if (field.transformData) {
				const parsedValue = field.transformData(result);

				_fieldOptions[field.name] = parsedValue;
			}
		}

		setFieldOpt(_fieldOptions);
		setIsLoading(false);
	}, [fieldsMemoized, paramsMemoized, setFieldOpt]);

	useEffect(() => {
		fetchResources();
	}, [fetchResources]);

	return (
		<div className="form-renderer">
			{fieldsFilteredMemoized.map((field, index) => {
				const {
					disabled,
					label,
					name,
					options = [],
					resource,
					type,
				} = field;

				const currentValue = form[name];

				const isFieldDisabled = () => {
					const isValueIncluded = currentValue.includes(
						i18n.sub('no-x', field.label)
					);

					return disabled ?? isValueIncluded;
				};

				const getFieldValue = () => {
					return currentValue === i18n.sub('no-x', field.label)
						? ''
						: currentValue;
				};

				const handleFieldChange = (
					event: React.ChangeEvent<HTMLInputElement>
				) => {
					const isChecked = event.target.checked;

					const value = isChecked
						? i18n.sub('no-x', field.label)
						: currentValue
								.replace(i18n.sub('no-x', field.label), '')
								.trim();

					onChange({
						target: {name, value},
					});

					setFieldDisabled(
						isChecked
							? {
									...fieldDisabled,
									[name]: !(fieldDisabled as any)[name],
							  }
							: false
					);
				};

				const getOptions = () => {
					const _options =
						fieldOptions[name] ||
						(options || []).map((option) =>
							typeof option === 'object'
								? option
								: {
										label: option,
										value: option,
								  }
						);

					return _options;
				};

				if (['date', 'number', 'text', 'textarea'].includes(type)) {
					return (
						<div key={index}>
							<Form.Input
								disabled={isFieldDisabled()}
								onChange={onChange}
								value={getFieldValue()}
								{...(field as any)}
							/>

							{type === 'textarea' && (
								<Form.Checkbox
									checked={currentValue.includes(
										i18n.sub('no-x', field.label)
									)}
									disabled={disabled}
									label={i18n.sub('no-x', field.label)}
									onChange={handleFieldChange}
								/>
							)}
						</div>
					);
				}

				if (type === 'select') {
					return (
						<Form.Select
							disabled={disabled}
							key={index}
							label={label}
							name={name}
							onChange={onChange}
							options={getOptions()}
							value={currentValue[0]?.value || currentValue}
						/>
					);
				}

				if (type === 'checkbox') {
					const onCheckboxChange = (event: any) => {
						const inputValue = event.target.value;
						const formValue: unknown[] = form[name];

						onChange({
							target: {
								name,
								value: formValue.includes(inputValue)
									? formValue.filter(
											(value) => value !== inputValue
									  )
									: [...formValue, inputValue],
							},
						});
					};

					return (
						<div key={index}>
							<label>{label}</label>

							{options.map((option, index) => {
								const optionValue =
									typeof option === 'string'
										? option
										: option.value;

								return (
									<Form.Checkbox
										checked={form[name]?.includes(
											optionValue
										)}
										disabled={disabled}
										key={index}
										label={
											typeof option === 'string'
												? option
												: option.label
										}
										name={name}
										onChange={onCheckboxChange}
										value={optionValue}
									/>
								);
							})}
						</div>
					);
				}

				if (type === 'autocomplete') {
					return (
						<Form.AutoComplete
							onSearch={() => null}
							resource={resource as string}
							transformData={field.transformData}
						/>
					);
				}

				if (type === 'multiselect') {
					return (
						<div className="mb-2" key={index}>
							<Form.MultiSelect
								disabled={disabled}
								label={label}
								name={name}
								onChange={onChange}
								options={getOptions()}
								value={currentValue}
								isLoading={isLoading}	
							/>
						</div>
					);
				}

				return null;
			})}

			{!fieldsFilteredMemoized.length && (
				<p>{i18n.translate('there-are-no-matching-results')}</p>
			)}
		</div>
	);
};

export default memo(Renderer);
