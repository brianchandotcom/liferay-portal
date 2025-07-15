/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormFields} from '.';
import Form from '../../../../components/MarketplaceForm';
import {InputType} from './Input';

type SSAFormSectionProps = {
	bottomSection?: SectionProps;
	title?: string;
	leftSection: SectionProps;
	rightSection: SectionProps;
};

export type SectionProps = {
	disabled?: boolean;
	error?: string;
	handleChange: ({
		label,
		value,
	}: {
		label: keyof FormFields;
		value: string;
	}) => void;
	label: keyof FormFields;
	options?: string[];
	maxLength?: number;
	placeholder?: string;
	required?: boolean;
	title: string;
	tooltip?: string;
	type?: 'input' | 'number' | 'select';
	value?: string;
};

const SSAFormSection = ({
	bottomSection,
	title,
	leftSection,
	rightSection,
}: SSAFormSectionProps) => {
	return (
		<div className="mb-5">
			<Form.FormControl>
				{title && (
					<>
						<h3 className="text-muted">{title}</h3>
						<hr className="mb-1" />
					</>
				)}
				<div className="d-flex justify-content-between">
					<div className="mb-3 pr-2 w-50">
						<Form.Label
							className="mt-5"
							htmlFor={leftSection.title}
							info={leftSection.tooltip || ''}
							required={leftSection.required}
						>
							{leftSection.title}
						</Form.Label>
						<InputType {...leftSection} />
					</div>
					<div className="mb-3 pr-2 w-50">
						<Form.Label
							className="mt-5"
							htmlFor={rightSection.title}
							info={rightSection.tooltip || ''}
							required={rightSection.required}
						>
							{rightSection.title}
						</Form.Label>
						<InputType {...rightSection} />
					</div>
				</div>
			</Form.FormControl>
			{bottomSection && (
				<div className="mb-3 pr-2 w-100">
					<Form.Label
						className="mt-5"
						htmlFor={bottomSection.title}
						info={bottomSection.tooltip || ''}
						required={bottomSection.required}
					>
						{bottomSection.title}
					</Form.Label>
					<InputType {...bottomSection} />
				</div>
			)}
		</div>
	);
};

export {SSAFormSection};
