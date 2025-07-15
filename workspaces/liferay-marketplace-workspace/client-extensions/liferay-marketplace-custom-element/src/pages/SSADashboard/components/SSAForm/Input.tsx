/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Select from '@clayui/form/lib/Select';
import Form from '../../../../components/MarketplaceForm';
import {SectionProps} from './Section';

const InputType = ({
	disabled = false,
	error,
	handleChange,
	label,
	maxLength,
	options,
	placeholder,
	required = false,
	title,
	type,
	value,
}: SectionProps) => {
	if (type === 'select') {
		return (
			<>
				<Select
					className="marketplace-form-select bg-white"
					disabled={disabled}
					name={title}
					required={required}
					onChange={(event) =>
						handleChange({label, value: event.target.value})
					}
				>
					<Select.Option label={placeholder} />
					{options?.map((opt) => (
						<Select.Option
							key={opt}
							value={opt || value}
							label={opt}
						/>
					))}
				</Select>

				{error && <p className="text-danger mt-1 mb-0">{error}</p>}
			</>
		);
	}

	return (
		<>
			<Form.Input
				disabled={disabled}
				name={title}
				type={type}
				placeholder={placeholder}
				maxLength={maxLength || undefined}
				value={value}

				// required={required}

				onChange={(event) =>
					handleChange({label, value: event.target.value})
				}
			/>
			{error && <p className="text-danger mt-1 mb-0">{error}</p>}
		</>
	);
};

export {InputType};
