/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {memo, useMemo, useState} from 'react';
import {Params} from 'react-router-dom';

import {Operators} from '../../core/SearchBuilder';
import i18n from '../../i18n';
import Form from './index';

type AutoCompleteProps = {
	label?: string;
	onSearch: (keyword: string) => any;
	resource?: ((params: Readonly<Params<string>>) => string) | string;
	transformData?: (item: any) => any;
};

type RenderedFieldOptions = string[] | {label: string; value: string}[];

export type RendererFields = {
	disabled?: boolean;
	isCustomFilter?: boolean;
	label: string;
	name: string;
	operator?: Operators;
	optionalOperator?: Operators;
	options?: RenderedFieldOptions;
	placeholder?: string;
	removeQuoteMark?: boolean;
	requestOperator?: string;
	type:
		| 'autocomplete'
		| 'checkbox'
		| 'date'
		| 'date-range'
		| 'multiselect'
		| 'number'
		| 'select'
		| 'text'
		| 'textarea';
} & Partial<AutoCompleteProps>;

export type Options = {
	label: string;
	value: string;
};

export type FieldOptions = {[key: string]: any[]};

type RendererProps = {
	fieldOptions?: FieldOptions;
	fields: RendererFields[];
	filter?: string;
	filterSchema: string;
	form: any;
	isLoading?: boolean;
	onApply: () => void;
	onChange: (event: any) => void;
};

const Renderer: React.FC<RendererProps> = ({
	fieldOptions = {},
	fields,
	filter,
	form,
	isLoading = false,
	onApply,
	onChange,
}) => {
	const [fieldDisabled, setFieldDisabled] = useState({});

	const fieldsMemoized = useMemo(() => fields, [fields]);

	const fieldsFilteredMemoized = useMemo(
		() =>
			fieldsMemoized.filter(({label}) =>
				filter
					? label.toLowerCase().includes(filter.toLowerCase())
					: true
			),
		[fieldsMemoized, filter]
	);

	return (
		<div className="form-renderer">
			{fieldsFilteredMemoized.map((field, index) => {
				const {disabled, label, name, options = [], type} = field;

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

				if (type === 'date-range') {
					const onDateChange = (
						event: string,
						setValue: React.Dispatch<React.SetStateAction<string>>
					) => {
						setValue(event);

						onChange({
							target: {
								name,
								type: 'date-range',
								value: event,
							},
						});
					};

					return (
						<div className="my-1" key={index}>
							<Form.DateRange
								label={label}
								onChange={onDateChange}
								value={currentValue[0]?.value || currentValue}
							/>
						</div>
					);
				}

				if (['date', 'number', 'text', 'textarea'].includes(type)) {
					return (
						<div key={index}>
							<Form.Input
								disabled={isFieldDisabled()}
								onChange={onChange}
								onKeyDown={(event) => {
									if (event.key === 'Enter') {
										onApply();
									}
								}}
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
						<>
							<label>{label}</label>
							<Form.Select
								disabled={disabled}
								isLoading={field.resource ? isLoading : false}
								key={index}
								label={label}
								name={name}
								onChange={onChange}
								options={getOptions()}
								value={currentValue[0]?.value || currentValue}
							/>
						</>
					);
				}

				if (type === 'checkbox') {
					const onCheckboxChange = (event: any) => {
						const labelValue = event.target.labels[0].innerText;
						const inputValue = event.target.value;

						const formValue: unknown[] = Array.isArray(form[name])
							? [...form[name]]
							: [];

						const simplifiedFormValue = formValue.map(
							(item: any) =>
								typeof item === 'object' && item !== null
									? item.value
									: item
						);

						const newEntry =
							inputValue !== labelValue
								? {label: labelValue, value: inputValue}
								: inputValue;

						const isSelected =
							simplifiedFormValue.includes(inputValue);

						const newValue = isSelected
							? formValue.filter((item: any) =>
									typeof item === 'object'
										? item.value !== inputValue
										: item !== inputValue
								)
							: [...formValue, newEntry];

						onChange({
							target: {
								name,
								value: newValue,
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
										checked={
											Array.isArray(form[name]) &&
											form[name].some(
												(option: Options | string) =>
													typeof option === 'string'
														? option === optionValue
														: option.value ===
															optionValue
											)
										}
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

				if (type === 'multiselect') {
					return (
						<div className="my-2" key={index}>
							<label>{label}</label>

							<Form.MultiSelect
								disabled={disabled}
								isLoading={field.resource ? isLoading : false}
								name={name}
								onChange={onChange}
								options={getOptions()}
								value={currentValue}
							/>
						</div>
					);
				}

				return null;
			})}

			{!fieldsFilteredMemoized.length && (
				<p>There are no matching results</p>
			)}
		</div>
	);
};

export default memo(Renderer);
