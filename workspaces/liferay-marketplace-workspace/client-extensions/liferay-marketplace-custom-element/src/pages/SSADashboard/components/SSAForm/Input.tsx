/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Select from '@clayui/form/lib/Select';

import Form from '../../../../components/MarketplaceForm';
import {FieldProps} from './FormSection';

const Input = ({
	className,
	disabled = false,
	error,
	handleChange,
	label,
	maxLength,
	options,
	placeholder,
	title,
	type,
	value,
}: FieldProps) => {
	if (type === 'select') {
		return (
			<>
				<Select
					className={`${className} marketplace-form-select`}
					disabled={disabled}
					name={title}
					onChange={(event) =>
						handleChange({label, value: event.target.value})
					}
				>
					<Select.Option label={placeholder} />
					{options?.map((opt) => (
						<Select.Option
							key={opt}
							label={opt}
							value={opt || value}
						/>
					))}
				</Select>

				{error && <p className="mb-0 mt-1 text-danger">{error}</p>}
			</>
		);
	}

	return (
		<>
			<Form.Input
				className={`${className} marketplace-form-select`}
				disabled={disabled}
				maxLength={maxLength || undefined}
				name={title}
				onChange={(event) =>
					handleChange({label, value: event.target.value})
				}
				placeholder={placeholder}
				type={type}
				value={value}
			/>
			{error && <p className="mb-0 mt-1 text-danger">{error}</p>}
		</>
	);
};

export {Input};
