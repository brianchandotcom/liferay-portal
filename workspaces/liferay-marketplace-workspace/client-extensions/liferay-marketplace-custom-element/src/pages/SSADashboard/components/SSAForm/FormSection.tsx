/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form from '../../../../components/MarketplaceForm';
import {Input} from './Input';
import {FormFields} from './ModalFormBody';

type FormSectionProps = {
	leftSection: FieldProps;
	rightSection: FieldProps;
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
	<div className="mb-3 pr-2 w-50">
		<Form.Label
			className="mt-5"
			info={section.tooltip || ''}
			required={section.required}
		>
			{section.title}
		</Form.Label>
		<Input {...section} />
	</div>
);

const FormSection = ({leftSection, rightSection, title}: FormSectionProps) => {
	return (
		<div className="mb-5">
			<Form.FormControl>
				{title && (
					<>
						<h4>{title}</h4>
						<hr className="mb-1" />
					</>
				)}
				<div className="d-flex justify-content-between">
					<FormField section={leftSection} />
					<FormField section={rightSection} />
				</div>
			</Form.FormControl>
		</div>
	);
};

export {FormSection};
