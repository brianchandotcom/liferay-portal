/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form from '../../../../components/MarketplaceForm';
import {FormFields} from '../../pages/CreateTrialModalForm';
import {Input} from './Input';

type FieldGroupProps = {
	primaryField: FieldProps;
	secondaryField?: FieldProps;
	title?: string;
};

export type FieldProps = {
	className?: string;
	disabled?: boolean;
	error?: string;
	handleChange: ({label, value}: {label: string; value: string}) => void;
	label: keyof FormFields;
	maxLength?: number;
	options?: string[];
	placeholder?: string;
	required?: boolean;
	title: string;
	tooltip?: string;
	type?: 'input' | 'number' | 'select';
	value?: string;
};

const FormField = ({section}: {section: FieldProps}) => (
	<div className="mb-3 pr-2 w-100">
		<Form.Label
			className="mb-2"
			info={section.tooltip || ''}
			required={section.required}
		>
			{section.title}
		</Form.Label>
		<Input {...section} />
	</div>
);

const FieldGroup = ({primaryField, secondaryField, title}: FieldGroupProps) => {
	const hasSecondaryField = !!secondaryField;

	return (
		<div className="mb-5">
			<Form.FormControl>
				{title && (
					<>
						<h4>{title}</h4>
						<hr className="mb-5" />
					</>
				)}

				<div
					className={
						hasSecondaryField
							? 'd-flex justify-content-between mb-5'
							: ''
					}
				>
					<FormField section={primaryField} />
					{hasSecondaryField && (
						<FormField section={secondaryField} />
					)}
				</div>
			</Form.FormControl>
		</div>
	);
};

export {FieldGroup};
